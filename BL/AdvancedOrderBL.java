package BL;


import DAOS.AdvancedOrderDAO;
import DAOS.AdvancedOrderLogDAO;
import DAOS.AdvancedResultDAO;
import DAOS.AdvancedTodayDAO;
import DAOS.LogDAO;
import DAOS.OrderDAO;
import DAOS.PanelDAO;
import DAOS.PhlebotomyDAO;
import DAOS.PreferencesDAO;
import DAOS.RescheduleDAO;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DOS.AdvancedOrders;
import DOS.AdvancedResults;
import DOS.IDOS.BaseLog;
import DOS.OrderEntryLog;
import DOS.Orders;
import DOS.Phlebotomy;
import DOS.Reschedule;
import DOS.ResultPostLog;
import DOS.Results;
import DOS.Tests;
import Utility.SQLUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.Weeks;

/**
 * Supplies methods to generate and retrieve rows associated with advanced
 *  orders (phlebotomy)
 * 
 * @author TomR
 */
public class AdvancedOrderBL
{
    public AdvancedOrderBL() {}
    
    /**
     * Purges the provided result, any children (subtests) of that
     *  result, and any grandchildren of that result.
     * 
     * Logs actions.
     * 
     *  NOTE: Validate to ensure the result line can be purged before calling
     *   this method! For instance, can't purge if Printed & Transmitted.
     * @param idAdvancedResults
     * @param userId Gets logged
     * @return 
     */
    public static boolean PurgeAdvancedResultAndChildren(int idAdvancedResults, int userId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        AdvancedResultDAO ardao = new AdvancedResultDAO();
        AdvancedResults advancedResult=null;
        try
        {
            advancedResult = ardao.GetAdvancedResults(idAdvancedResults);
            if (advancedResult == null) throw new SQLException();
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::PurgeResultAndChildren : Unable to retrieve advanced result for id:" + idAdvancedResults);
            return false;        
        }
        
        // The final list of advanced results that will be purged
        ArrayList<AdvancedResults> purgeList = new ArrayList<>();
        
        // Add the given advanced result to the purge list
        purgeList.add(advancedResult);
        
        // Get any child tests as defined on the advanced result rows
        ArrayList<AdvancedResults> childResults = ardao.GetSubtestAdvancedResults(idAdvancedResults);
        
        // Add child results and their children to the purge list
        for (AdvancedResults childResult : childResults)
        {
            // Add all of the grandchild results
            purgeList.addAll(ardao.GetSubtestAdvancedResults(childResult.getIdAdvancedResults()));
            
            // Add the child result
            purgeList.add(childResult);
        }
        
        Date currentDate = new Date();
        OrderEntryLog olog;// = new OrderEntryLog();
        ResultPostLog rlog;// = new ResultPostLog();        
        
        // Go through and purge all of the results
        for (AdvancedResults purgeResult : purgeList)
        {
            boolean success = ardao.PurgeResultByAdvancedResultID(purgeResult.getIdAdvancedResults());
            
            // Stop on failure
            if (!success) return false;
        }
        return true;
    }
    
    /**
     * Clears out any rows in the advanced today table in preparation
     *  for a new process. Warn user before calling method. 
     * @throws java.sql.SQLException 
     */
    public void PurgeAdvancedTodayTable() throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        AdvancedTodayDAO atdao = new AdvancedTodayDAO();        
        if (atdao.DropTable()) throw new SQLException();
        if (atdao.CreateTable()) throw new SQLException();        
    }
    
    /**
     * Returns all of the orders generated for the provided date
     *  Uses the advancedOrdersLog table.
     * @param date
     * @return 
     */
    public ArrayList<Orders> GetOrdersCreatedOnDate(Date date)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        OrderDAO orderdao = new OrderDAO();
        AdvancedOrderLogDAO aoldao = new AdvancedOrderLogDAO();
        
        java.sql.Date startSQLDate = new java.sql.Date(new DateTime(date).withTimeAtStartOfDay().toDate().getTime());
        java.sql.Date endSQLDate = new java.sql.Date(new DateTime(date).millisOfDay().withMaximumValue().toDate().getTime());
        
        String sql = "SELECT orderId"
                + " FROM advancedOrderLog "
                + " WHERE errorFlag = 0"
                + " AND orderId IS NOT NULL"
                + " AND scheduledFor BETWEEN ? AND ?";
        
        ArrayList<Orders> orders = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {            
            SQLUtil.SafeSetTimeStamp(pStmt, 1, startSQLDate);
            SQLUtil.SafeSetTimeStamp(pStmt, 2, endSQLDate);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                orders.add(orderdao.GetOrderById(rs.getInt("orderId")));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetOrdersCreatedOnDate: " +
                    " Unable to retrieve the orders generated on " + date.toString());
            return null;
        }
        return orders;
    }
    
    /**
     * Creates a single order (with result rows/diagnosis codes/prescriptions)
     *  from the provided advanced order id and accession. 
     *  Logs changes, using the provided "scheduled for" date to identify when
     *  this order was supposed to be created (if a daily process is not run,
     *  a user can go back and run it for a previous day, and this is how we
     *  will track order creation).
     * 
     *  IMPORTANT: Calling code must ensure that the provided accession is
     *   unique and locked before calling this method. It should also
     *   ensure that the order wasn't already created for this day.
     *   (can use the GetNonErrorLogsInDateRange method on AdvancedOrderLogDAO).
     *   Calling code should insert into the AdvancedToday table for report generation.
     * 
     * @param accession The Accession to use for the new order.
     * @param advancedOrderId
     * @param scheduledFor
     * @param userId
     * @return 
     */
    public Integer CreateOrderFromAdvancedOrderId(String accession,
            int advancedOrderId, Date scheduledFor, int userId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        Integer newOrderId = null;
        
        if (accession == null || accession.isEmpty()) return null;
        AdvancedOrderDAO aodao = new AdvancedOrderDAO();
        
        // Insert the order, retrieving the new order Id
        try
        {
            AdvancedOrders ao = aodao.GetAdvancedOrder(advancedOrderId);
            if (ao == null) throw new SQLException();
            
            String sql = "CALL GenerateAdvancedOrder(?,?,?,?,?);";            
            CallableStatement callable = con.prepareCall(sql);
            callable.setString(1, accession);
            callable.setInt(2, advancedOrderId);
            callable.setInt(3, userId);
            callable.setDate(4, new java.sql.Date(scheduledFor.getTime()));
            callable.registerOutParameter(5, java.sql.Types.INTEGER);

            callable.executeQuery();
            newOrderId = callable.getInt(5);
            callable.close();
            
            if (newOrderId <= 0) throw new SQLException();
            
            CreateAdvancedOrderLog(advancedOrderId,"Order Created",
                    newOrderId, scheduledFor, true, userId, false);
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::CreateOrderFromAdvancedOrderID : " +
                    "Error attempting to create advanced order for advancedOrderId " + advancedOrderId);
            // Create a log entry for error tracking
            CreateAdvancedOrderLog(advancedOrderId,"Order Creation Error", null, scheduledFor, false, userId, true);
            return null;
        }
        return newOrderId;
    }
    
    /**
     * Gets orders that have been created, but have been marked for re-draw
     *  at a further date. These can be identified by joining the orders table
     *  to phlebotomy (usually, only advanced orders have a phlebotomy schedule defined)
     * @param startDate
     * @param endDate
     * @return 
     */
    public ArrayList<Orders> GetRedrawsInRange(Date startDate, Date endDate)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);                
        ArrayList<Orders> orders = new ArrayList<>();
        OrderDAO orderdao = new OrderDAO();
        try
        {
            java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());

            String query = "SELECT p.idOrders "
                    + " FROM orders o"
                    + " INNER JOIN phlebotomy p ON o.idOrders = p.idOrders"
                    + " WHERE o.orderDate BETWEEN ? AND ?"
                    + " AND p.inactive = 0";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setDate(1, sqlStartDate);
            pStmt.setDate(2, sqlEndDate);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                Orders o = orderdao.GetOrderById(rs.getInt("idOrders"));
                if (o != null)
                {
                    orders.add(o);
                }
                else
                {
                    System.out.println("AdvancedOrderBL::GetRedrawsInRange:"
                     + " Cannot get order for id: " + rs.getInt("idOrders"));
                }
            }            
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetRedrawsInRange:"
                    + " Cannot get orders for redraws.");
            return null;
        }
        return orders;
    }
    
    public boolean DeactivateAdvancedOrder(Integer advancedOrderId, Integer userID)
    {        
        try
        {
            AdvancedOrderDAO aodao = new AdvancedOrderDAO();
            AdvancedOrders ao = aodao.GetActiveAdvancedOrder(advancedOrderId);
            ao.setInactive(true);
            aodao.UpdateAdvancedOrder(ao);
            
            // Prevent the schedule from showing up in the calendar:
            PhlebotomyDAO pdao = new PhlebotomyDAO();
            Phlebotomy p = pdao.GetPhlebotomyByAdvancedOrderId(advancedOrderId);
            p.setInactive(true);
            pdao.UpdatePhlebotomy(p);
            
            // Get rid of any future reschedule rows:            
            if (!DeleteFutureReschedules(advancedOrderId, new Date()))
            {
                throw new Exception();
            }
            
            // Log it
            CreateAdvancedOrderLog(advancedOrderId, "Deactivated", null, null, false, userID, false);
            
            try
            {
                // Add advancedOrder log entry for deactivation
                LogDAO logdao = new LogDAO();
                BaseLog log = new OrderEntryLog();
                log.setIdUser(userID);
                log.setIdOrders(advancedOrderId);
                log.setAction("Deactivated");
                log.setDescription("Standing order deactivated");
                log.setIdPatients(ao.getPatientId());
                log.setNewClient(ao.getClientId());
                logdao.InsertLog(LogDAO.LogTable.AdvancedOrderEntry, log);                
            }
            catch (Exception ex)
            {
                System.out.println("Unable to log deactivated advanced order into AdvancedOrderEntryLog");
            }
        }
        catch (Exception ex)
        {
            System.out.println("AdvancedOrderBL::DeactivateAdvancedOrder: "
                    + " Unable to deactivate Advanced Order / Phlebotomy row"
                    + " or remove associated reschedule rows.");
            CreateAdvancedOrderLog(advancedOrderId, "Error deactivating", null, null, false, userID, true);
            return false;
        }        
        return true;
    }
    
    /**
     * Deletes any reschedules attached to the advanced order that occur
     *  after the supplied date.
     * 
     *  Used when a user is deactivating an Advanced Order
     * @param advancedOrderId
     * @return 
     */
    public boolean DeleteFutureReschedules(Integer advancedOrderId, Date date)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);                
        
        try
        {
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            String query = "DELETE FROM reschedules WHERE"
                    + " advancedOrderId = " + advancedOrderId
                    + " AND (CASE WHEN rescheduleDate IS NULL THEN originalDate > '" + sqlDate + "' ELSE rescheduleDate > '" + sqlDate + "' END)";

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.execute();            
        }
        catch (SQLException ex)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Gets the advanced order that created the provided order. Null if not
     *  found.
     * @param orderId
     * @return 
     */
    public AdvancedOrders GetAdvancedOrderFromOrderId(Integer orderId)
    {
        AdvancedOrders advancedOrder = null;
        AdvancedOrderLogDAO aoldao = new AdvancedOrderLogDAO();
        Integer advancedOrderId = aoldao.GetAdvancedOrderIdFromOrderId(orderId);
        
        if (advancedOrderId != null)
        {
            AdvancedOrderDAO aodao = new AdvancedOrderDAO();
            advancedOrder = aodao.GetAdvancedOrder(orderId);
        }
        
        return advancedOrder;
    }
    
    /**
     * Retrieves the active advanced orders for the given patient that have
     *  the supplied root test number attached to it. Used to warn the user if they're
     *  creating an advanced order for a test when an advanced order for it already exists.
     * 
     *  "Root" is a test that has no panelId on the result line.
     * 
     *   Optional arraylist to exclude certain advanced order Ids from the search.
     * 
     * @param patientId
     * @param testNumber
     * @param excludeAdvancedOrderIds
     * @return 
     */
    public ArrayList<AdvancedOrders> GetAdvancedOrdersWithPatientAndRootTestNumber(
            int patientId, int testNumber, ArrayList<Integer> excludeAdvancedOrderIds)
    {
        ArrayList<AdvancedOrders> advancedOrdersWithTest = new ArrayList<>();
        
        if (excludeAdvancedOrderIds == null) excludeAdvancedOrderIds = new ArrayList<>();
        
        // First grab the active advanced orders for the patient
        ArrayList<AdvancedOrders> advancedOrders = GetAdvancedOrdersForPatient(patientId);
        
        if (advancedOrders != null)
        {
            for (AdvancedOrders ao : advancedOrders)
            {
                // Only check if this advanced order wasn't flagged for exclusion
                if (!excludeAdvancedOrderIds.contains(ao.getIdAdvancedOrders()))
                {
                    // Get the test numbers for the advanced results table that have no parent panelId
                    HashSet<Integer> rootTests = GetAdvancedOrderRootTestNumbers(ao.getIdAdvancedOrders());

                    if (rootTests != null)
                    {
                        // If this test is already on an advanced order for
                        // this patient, add to list.
                        if (rootTests.contains(testNumber))
                        {
                            if (!advancedOrdersWithTest.contains(ao))
                            {
                                advancedOrdersWithTest.add(ao);
                            }                        
                        }
                    }                    
                }
            }
        }
        
        return advancedOrdersWithTest;
    }
    
    /**
     * Gets the "root" tests of the advanced order - tests that have no 
     *  parent panel on the advanced results lines
     * @param advancedOrderId 
     * @return  
     */
    public HashSet<Integer> GetAdvancedOrderRootTestNumbers(int advancedOrderId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        Integer newOrderId = null;
        HashSet<Integer> testNumbers = new HashSet<>();
        
        String sql = "SELECT testId "
                + " FROM advancedResults "
                + " WHERE panelID IS NULL AND idAdvancedOrder = ?";
        try
        {
            TestDAO testdao = new TestDAO();
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetInteger(pStmt, 1, advancedOrderId);
            
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                // Grab the test associated with the advanced result row
                Tests advancedTest = testdao.GetTestByID(rs.getInt(1));
                
                // Get the active version of this test
                Tests activeRootTest = testdao.GetTestByNumber(advancedTest.getNumber());
                
                testNumbers.add(activeRootTest.getNumber());
            }
        } catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetAdvancedOrderRootTestNumbers:"
                    + " Unable to get active root tests for advancedOrderId = " + advancedOrderId);
            return null;
        }
        
        return testNumbers;
    }
    
    /**
     * Gets all of the active advanced orders currently assigned to the given
     *  patient.
     * @param patientId
     * @return 
     */
    public ArrayList<AdvancedOrders> GetAdvancedOrdersForPatient(int patientId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        Integer newOrderId = null;
        ArrayList<AdvancedOrders> advancedOrders = new ArrayList<>();
        
        String sql = "SELECT idAdvancedOrders "
                + " FROM advancedOrders "
                + " WHERE inactive = 0 AND patientId = ?";
        try
        {
            TestDAO testdao = new TestDAO();
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetInteger(pStmt, 1, patientId);
            
            ResultSet rs = pStmt.executeQuery();
            AdvancedOrderDAO aodao = new AdvancedOrderDAO();
            
            while (rs.next())
            {
                Integer advancedOrderId = rs.getInt("idAdvancedOrders");
                AdvancedOrders ao = aodao.GetAdvancedOrder(advancedOrderId);
                advancedOrders.add(ao);                
            }
        } catch (SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetAdvancedOrdersForPatient:"
                    + " Unable to get advanced orders for patientId = " + patientId);
            return null;
        }
        
        return advancedOrders;        
    }
    
    
    /**
     * Inserts a new result row. PanelId can be null.
     * @param orderId
     * @param testId
     * @param panelId
     * @return 
     */
    private void  InsertResultRow(int orderId, int testId, Integer panelId) throws SQLException
    {
        ResultDAO resultdao = new ResultDAO();
        
        Results result = new Results();
        result.setOrderId(orderId);
        result.setTestId(testId);        
        if (panelId != null)
        {
            result.setPanelId(panelId);
        }
        result.setCreated(new Date());
        result.setIsApproved(false);
        result.setIsInvalidated(false);
        result.setIsAbnormal(false);
        result.setIsHigh(false);
        result.setIsLow(false);
        result.setIsCIDHigh(false);
        result.setIsCIDLow(false);
        result.setNoCharge(false);
        result.setHl7Transmitted(false);
        
        result.setPrintAndTransmitted(false);
        
        resultdao.InsertResult(result);
    }
    
    /**
     * Inserts an order from the supplied advanced order and accession,
     *  returning the new orderId on success
     * @param ao
     * @param accession
     * @return
     * @throws Exception 
     */
    private Integer InsertOrder(AdvancedOrders ao, String accession) throws Exception
    {
        if (ao == null || accession == null || accession.isEmpty()) throw new Exception();
        
        OrderDAO orderdao = new OrderDAO();
        Orders o = new Orders();        
        o.setDoctorId(ao.getDoctorId());
        o.setClientId(ao.getClientId());
        o.setAccession(accession);
        o.setLocationId(ao.getLocationId());
        o.setOrderDate(new Date());
        
        // Set the specimen date to the current date, but append
        // the original specimen date's time to the end for scheduling purposes.
        DateTime specimenDateTime = new DateTime(ao.getSpecimenDate());
        DateTime todayWithSpecimenTime =
                new DateTime()
                        .withHourOfDay(specimenDateTime.getHourOfDay())
                        .withMinuteOfHour(specimenDateTime.getMinuteOfHour());        
        o.setSpecimenDate(todayWithSpecimenTime.toDate());
        o.setPatientId(ao.getPatientId());
        o.setSubscriberId(ao.getSubscriberId());
        o.setIsAdvancedOrder(true); // Is generated by advanced order
        o.setRoom(ao.getRoom());
        o.setBed(ao.getBed());
        o.setIsFasting(ao.getIsFasting());
        o.setInsurance(ao.getInsurance());
        o.setSecondaryInsurance(ao.getSecondaryInsurance());
        o.setPolicyNumber(ao.getPolicyNumber());
        o.setGroupNumber(ao.getGroupNumber());
        o.setSecondaryPolicyNumber(ao.getSecondaryPolicyNumber());
        o.setSecondaryGroupNumber(ao.getSecondaryGroupNumber());
        o.setMedicareNumber(ao.getMedicareNumber());
        o.setMedicaidNumber(ao.getMedicaidNumber());
        o.setReportType(ao.getReportType());
        o.setRequisition(ao.getRequisition());
        o.setBillOnly(ao.isBillOnly());
        o.setActive(ao.isActive());
        o.setHold(ao.isHold());
        o.setStage(1);
        o.setHoldComment(ao.getHoldComment());
        o.setResultComment(ao.getResultComment());
        o.setInternalComment(ao.getInternalComment());
        o.setHl7Transmitted(ao.getHl7Transmitted());
        o.setPayment(ao.getPayment());
        o.setBillable(ao.isBillable());
        o.setEmrOrderId(ao.getEmrOrderId());
        o.setDOI(ao.getDOI());
        o.setEOA(ao.getEOA());
        o.setReleaseJobID(ao.getReleaseJobID());
        o.setReleaseDate(ao.getReleaseDate());
        
        return orderdao.InsertOrderGetId(o);        
    }
    
    /**
     *   OrderId is optional, as an entry for an error may not have the created order Id
     * 
     * @param advancedOrderId
     * @param action
     * @param orderId
     * @param scheduledFor
     * @param userId 
     * @param orderCreated 
     * @param errorFlag 
     */
    public void CreateAdvancedOrderLog(int advancedOrderId,
            String action, Integer orderId, Date scheduledFor, boolean orderCreated, int userId,boolean errorFlag)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        AdvancedOrderLogDAO aoldao = new AdvancedOrderLogDAO();
        aoldao.InsertAdvancedOrderLog(advancedOrderId, action, orderId, scheduledFor, orderCreated, userId, errorFlag);
    }
    
    /**
     * Determines the end date of an advanced order schedule depending on the
     *  starting date, the type of frequency (i.e. weekly), and the total
     *  number of occurrences.
     *  
     * End date for a phlebotomy schedule is pre-calculated to make querying
     *  date ranges faster for the calendar.
     * 
     *  NOTE: occurrence count is not being used for scheduling - only the EndDate
     *    is used. Occurrence count is there for the user and so this method
     *    can calculate the End Date.
     * @param phlebotomy
     * @return 
     */
    public Date GetEndDate(Phlebotomy phlebotomy)
    {
        if (phlebotomy == null) return null;        
        if (phlebotomy.getStartDate()== null || phlebotomy.getOccurrences() < 1) return null;

        // The date we will be checking
        DateTime startDate = new DateTime(phlebotomy.getStartDate());
        DateTime currentDate = new DateTime(phlebotomy.getStartDate());
        
        Integer frequency = phlebotomy.getFrequency();
        
        // The end date to be returned
        DateTime endDateTime = null;
        
        ArrayList<Integer> days;
        Integer occurrences = phlebotomy.getOccurrences();
        
        // Get the weekend work preferences for the lab:
        PreferencesDAO prefsdao = new PreferencesDAO();
        Boolean saturdayWorkday = prefsdao.getBoolean("SaturdayWorkday");
        Boolean sundayWorkday = prefsdao.getBoolean("SundayWorkday");
        if (saturdayWorkday == null) saturdayWorkday = false;
        if (sundayWorkday == null) sundayWorkday = false;        
        
        // Day of the week (Monday = 1, Tuesday = 2)
        int day;
        
        switch (phlebotomy.getScheduleTypeId())
        {
            case 1: // Daily
                while (occurrences > 1)
                {
                    if (IsWorkday(currentDate, saturdayWorkday, sundayWorkday))
                    {
                        --occurrences;
                    } 
                    currentDate = currentDate.plusDays(frequency);
                }                
                endDateTime = currentDate;
                break;
            case 2: // Weekly
                
                days = GetDayArray(phlebotomy);
                
                // Get the latest weekday supplied (i.e. if its MWF, get Friday)                
                day = days.get(days.size()-1);
                
                while (occurrences > 0)
                {
                    currentDate = currentDate.withDayOfWeek(day);
                    if (currentDate.isAfter(startDate) || currentDate.isEqual(startDate))
                    {
                        --occurrences;
                        if (occurrences == 0) break;
                    }                    
                    currentDate = currentDate.plusWeeks(frequency);                    
                }
                endDateTime = currentDate;
                break;
            case 3: // Monthly
                
                // Two ways to handle month schedules:
                //   A fixed day of the month (e.g. the 15th of every month)
                //   A certain weekday in the month (e.g. the 2nd Wednesday)
                
                // If there's no week specified, it's by fixed day of month
                Integer week = phlebotomy.getWeek();
                if (week == null)
                {
                    int dayOfMonth = currentDate.getDayOfMonth();
                    
                    // Increment by the number of months
                    // (jodatime handles problems like if you add a month to
                    //  the 31st of January, it will get the last day of February)
                    currentDate = currentDate.plusMonths((occurrences-1)*frequency);
                     
                    // Need to make sure the final day is a workday
                    if (!IsWorkday(currentDate,saturdayWorkday,sundayWorkday))
                    {
                        int month = currentDate.getMonthOfYear();

                        DateTime foundWorkday = GetNextWorkday(currentDate, saturdayWorkday, sundayWorkday);
                        if (foundWorkday.getMonthOfYear() != month)
                        {
                            // Not good. We've gone into the next month.
                            // Instead look backwards until we find a
                            // valid workday
                            foundWorkday = foundWorkday.minusDays(1);
                            foundWorkday = GetPreviousWorkday(foundWorkday, saturdayWorkday, sundayWorkday);
                        }
                        currentDate = foundWorkday;
                    }
                    
                }
                else // A specific week # / weekday in the month
                {
                    // Get the specific day of the week this is scheduled for
                    // There should only be one day defined.
                    days = GetDayArray(phlebotomy);                    
                    day = days.get(0);
                    
                    DateTime weekday;
                    
                    // Get the first day of the month
                    currentDate = new DateTime(currentDate.getYear(),currentDate.getMonthOfYear(),1,0,0);
                    
                    // Add months depending on how many occurrences we have
                    currentDate = currentDate.plusMonths((occurrences-1)*frequency);
                    
                    // Figure out the Nth day of the Nth week of this month.
                    LocalDate nThWeekday = getNthWeekdayOfMonth(week, day, currentDate.getMonthOfYear(), currentDate.getYear());
                    currentDate = nThWeekday.toDateTimeAtStartOfDay();

                }
                endDateTime = currentDate;
                break;
            case 4: // Yearly
                
                currentDate = currentDate.plusYears((occurrences-1)*frequency);
                
                if (!IsWorkday(currentDate,saturdayWorkday,sundayWorkday))
                {
                    int month = currentDate.getMonthOfYear();

                    DateTime foundWorkday = GetNextWorkday(currentDate, saturdayWorkday, sundayWorkday);
                    if (foundWorkday.getMonthOfYear() != month)
                    {
                        // Not good. We've gone into the next month.
                        // Instead look backwards until we find a
                        // valid workday
                        foundWorkday = foundWorkday.minusDays(1);
                        foundWorkday = GetPreviousWorkday(foundWorkday, saturdayWorkday, sundayWorkday);
                    }
                    endDateTime = foundWorkday;
                }
                else
                {
                    endDateTime = currentDate;
                }
                break;
        }
        
        // Now, append the hour/minute of the specimen date to the end
        DateTime pTime = new DateTime(phlebotomy.getStartDate());
        return endDateTime.withHourOfDay(pTime.getHourOfDay()).withMinuteOfHour(pTime.getMinuteOfHour()).toDate();
    }
    
    /**
     * Gets each phlebotomist Id and all of their advanced order Ids for a single supplied day.
     * @param inputDay
     * @return 
     * @throws java.sql.SQLException 
     */
    public HashMap<Integer,ArrayList<Integer>> GetDaysOrders(Date inputDay) throws Exception
    {
        if (inputDay == null) return null;
        
        // Get the weekend work preferences for the lab:
        PreferencesDAO prefsdao = new PreferencesDAO();
        Boolean saturdayWorkday = prefsdao.getBoolean("SaturdayWorkday");
        Boolean sundayWorkday = prefsdao.getBoolean("SundayWorkday");
        if (saturdayWorkday == null) saturdayWorkday = false;
        if (sundayWorkday == null) sundayWorkday = false;        
        HashMap<Integer,ArrayList<Integer>> results = new HashMap<>();
        
        // If the date is not a work-day, exit
        DateTime inputDateTime = new DateTime(inputDay);
        if (IsWorkday(inputDateTime, saturdayWorkday, sundayWorkday) == false) return results;
        
        DateTime day = new DateTime(inputDay);
        DateTime startToday = day.withTimeAtStartOfDay();
        DateTime endToday = day.millisOfDay().withMaximumValue();

        // Get everything that has been canceled or rescheduled to another day:
        HashSet<Integer> canceledPhlebotomyIds = 
                GetPhlebotomyIdsCanceledOrRescheduledOutOfRange(startToday.toDate(),endToday.toDate(), null);

        // Get everything that has been added or rescheduled into the current day:
        HashSet<Integer> newPhlebotomyIds = 
                GetPhlebotomyIdsNewOrRescheduledIntoRange(startToday.toDate(), endToday.toDate(),null);
        
        // Get orders that were scheduled but reassigned today
        // (orderId-->reschedule)
        HashMap<Integer,Reschedule> reassigns =
                GetSingleDayReassigns(startToday.toDate(), endToday.toDate());        
        
        // Now grab all of the phlebotomy rows that should be considered:
        ArrayList<Phlebotomy> phlebotomyRows = 
                GetPhlebotomyRowsInRange(startToday.toDate(), endToday.toDate(), null, null, null, null);
        
        Reschedule reassign;
        
        // For each schedule in range:
        for (Phlebotomy p : phlebotomyRows)
        {
            // Don't consider if it's been canceled/rescheduled to another day
            if (!canceledPhlebotomyIds.contains(p.getIdPhlebotomy()))
            {
                // This method is normally used to get all dates between the
                //  range. In this case we just want to see if there's a single
                //  returned date, meaning that the order is scheduled for today.
                ArrayList<Date> date = GetOrderDates(p, startToday, endToday);
                if (date != null && date.size() > 0)
                {
                    Integer phlebotomistId;
                    
                    // Need to see if this order has been reassigned. If so,
                    //  set it to the phlebotomist it was reassigned to.
                    reassign = reassigns.get(p.getIdAdvancedOrder());
                    if (reassign != null)
                    {
                        // Use the phlebotomist this was reassigned to
                        phlebotomistId = reassign.getReassignedToPhlebotomistId();
                        reassigns.remove(p.getIdAdvancedOrder());
                    }
                    else
                    {
                        // Use the phlebotomist it was originally assigned to.
                        phlebotomistId = p.getPhlebotomist();
                    }
                    
                    // Grab the existing order list for this phlebotomist if
                    //  they already have draws assigned.
                    if (results.containsKey(phlebotomistId))
                    {
                        ArrayList<Integer> orders = results.get(phlebotomistId);
                        if (orders == null) orders = new ArrayList<>();
                        orders.add(p.getIdAdvancedOrder());
                    }
                    else // Or create a new list and add the order
                    {
                        ArrayList<Integer> orders = new ArrayList<>();
                        orders.add(p.getIdAdvancedOrder());
                        results.put(phlebotomistId, orders);
                    }
                }
            }
        }
        
        // Finally, add the rows that were rescheduled into the current day:
        for (Integer phlebotomyId : newPhlebotomyIds)
        {            
            PhlebotomyDAO phlebotomydao = new PhlebotomyDAO();
            Phlebotomy p = phlebotomydao.GetPhlebotomy(phlebotomyId);
            ArrayList<Integer> orders = results.get(p.getPhlebotomist());
            if (orders != null)
            {
                orders.add(p.getIdAdvancedOrder());                    
            }
            else
            {
                orders = new ArrayList<>();
                orders.add(p.getIdAdvancedOrder());
                results.put(p.getPhlebotomist(),orders);
            }
        }
        
        // And add any left-over reassigns
        for (Reschedule r : reassigns.values())
        {
            ArrayList<Integer> orders = results.get(r.getReassignedToPhlebotomistId());
            if (orders != null)
            {
                orders.add(r.getAdvancedOrderId());                    
            }
            else
            {
                orders = new ArrayList<>();
                orders.add(r.getAdvancedOrderId());
                results.put(r.getReassignedToPhlebotomistId(),orders);
            }                    
        }
        return results;
    }
    
    /**
     * Used for the daily run. Returns the OrderId --> Reschedule object
     *  for any orders that were reassigned in the provided day.
     * 
     * @param startDay
     * @param endDay
     * @return 
     */
    public HashMap<Integer, Reschedule> GetSingleDayReassigns(Date startDay, Date endDay)
    {        
        Reschedule reassign;
        // Order id --> Reschedule
        HashMap<Integer,Reschedule> reassigns = new HashMap<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Date startSQLDate = new java.sql.Date(startDay.getTime());
            java.sql.Date endSQLDate = new java.sql.Date(endDay.getTime());
            
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE"
                    + " (rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId IS NOT NULL) " // This is simply a reassignment
                    + " OR "
                    + "(rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId IS NOT NULL) "; // This is a reschedule and assignment

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            
            LocalDate localDate;
            while (rs.next())
            {
                reassign = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reassign, rs);
                reassigns.put(reassign.getAdvancedOrderId(), reassign);
            }            
            rs.close();
            stmt.close();
            return reassigns;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetSingleDayReassigns " + 
                    "Unable to obtain range for " + startDay.toString() + 
                    " - " + endDay.toString() + "\n\n" + ex.toString());
            return null;
        }
    }
    
    /**
     * Gets a hashmap representing the Phlebotomist ID and a corresponding
     *  HashMap of dates and the number of draws they have that day, using
     *  the date range supplied to search.
     * @param startDate Starting date range to search
     * @param endDate Ending date range
     * @return employeeId of phlebotomist, dates and corresponding draw counts
     */
    /*
    public HashMap<Integer, HashMap<Date, Integer>> GetAllPhlebotomistSchedulesWithinRange(Date startDate, Date endDate)
    {
        return GetPhlebotomistSchedulesWithinRange(startDate, endDate, null);
    }*/
    
    /**
     * Returns a hashmaps representing the Phlebotomist ID and a list of all
     *  their scheduled dates within the supplied start and end date and the
     *  corresponding draw count. If a hashset of phlebotomistIds is NOT supplied
     *  (NULL or empty), it searches all Phlebotomists within the range
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistIds Optional list of phlebotomists (employeeIds) to search for
     * @return 
     */
    /*
    public HashMap<Integer, HashMap<Date, Integer>>
        GetPhlebotomistSchedulesWithinRange(
                Date startDate, Date endDate, HashSet<Integer> phlebotomistIds)
    {
        if (startDate == null || endDate == null) return null;
        
        // If the user supplies a hashset of phlebotomistIds, flag to 
        //  just search those individuals
        boolean getAllPhlebotomists = true;
        if (phlebotomistIds != null && phlebotomistIds.size() > 0)
        {
            getAllPhlebotomists = false;
        }
        
        // First get any phlobotomy schedule rows that overlap the supplied range
        ArrayList<Phlebotomy> phlebotomyRows = GetPhlebotomyRowsInRange(startDate,endDate,phlebotomistIds);

        // There was an error
        if (phlebotomyRows == null)
        {
            System.out.println("AdvancedOrderBL::GetAllPhlebotomistSchedulesWithinRange: " +
                    "Unable to retrieve all phlebotomy rows within range " + startDate +
                    " - " + endDate);
            return null;
        }
        
        // The container for the final results
        // First integer is employeeId of phlebotomist
        //   Associated hashmap is the date they are scheduled to work, and
        //   the number of draws for that date.
        HashMap<Integer, HashMap<Date, Integer>> results = new HashMap<>();
        
        // TODO: the retrieval of each phlebotomy row (GetPhlebotomyRowsInRange)
        // can possibly be in-lined with this loop for performance improvement.
        
        for (Phlebotomy p : phlebotomyRows)
        {
            // Generate the order schedule based on the start date and 
            // frequency definition for this phlebotomy row.
            ArrayList<Date> orderSchedule = 
                    GetOrderDates(p, new DateTime(p.getStartDate()),new DateTime(endDate));
            
            int phlebotomistId = p.getPhlebotomist();
            
            // Determine whether we should get schedules for this phlebotomist.
            if (getAllPhlebotomists || phlebotomistIds.contains(phlebotomistId))
            {
                HashMap<Date, Integer> datesAndCounts = new HashMap<>();
                
                // If this is a new phlebotomist, add them to the hashmap
                // with the schedule rows.
                if (!results.containsKey(phlebotomistId))
                {
                    for (Date date : orderSchedule)
                    {
                        datesAndCounts.put(date, 1);
                    }
                    results.put(phlebotomistId, datesAndCounts);
                }
                else // Otherwise, append these dates to their existing schedule
                {
                    for (Date date : orderSchedule)
                    {
                        datesAndCounts = results.get(phlebotomistId);
                        
                        // If the phlebotomist already has this date associated
                        // with them, increment the draw count for that day.
                        if (datesAndCounts.containsKey(date))
                        {
                            int currentCount = datesAndCounts.get(date);
                            datesAndCounts.put(date, currentCount+1);
                        }
                        else // Otherwise, just add the new date
                        {
                            datesAndCounts.put(date, 1);
                        }
                    }
                } // contains phlebotomist
                
            } // should we check
            
        } // for each phlebotomist
        return results;
    }*/

    /**
     * Generates a list of dates within the supplied range that
     *  the phlebotomy schedule falls on.
     * 
     *  Ranges take time into consideration and returned dates have timestamp
     *   data attached, based on the phlebotomy schedule's start date.
     * 
     *  NOTE: Does not consider or process rescheduling! This must be
     *    handled by the calling code.
     * 
     * @param phlebotomy Phlebotomy row for an order
     * @param startRange The beginning of the range to search
     * @param endRange   The end of the range to search
     * @return ArrayList of Dates
     */
    public ArrayList<Date> GetOrderDates(
            Phlebotomy phlebotomy,
            DateTime startRange,
            DateTime endRange)
    {

        // Get the weekend work preferences for the lab:
        PreferencesDAO prefsdao = new PreferencesDAO();
        Boolean saturdayWorkday = prefsdao.getBoolean("SaturdayWorkday");
        Boolean sundayWorkday = prefsdao.getBoolean("SundayWorkday");
        if (saturdayWorkday == null) saturdayWorkday = false;
        if (sundayWorkday == null) sundayWorkday = false;
        
        // Fixed monthly and yearly schedules will occassionally fall on
        // weekends. If this is either type, check the days immedaitely prior
        // to the start of the date range to see if they were non-workdays.
        // If so, extend the start of the range to include them so we capture
        // those orders that were not generated over the weekend.
        if (phlebotomy.getScheduleTypeId().equals(4) ||
                (phlebotomy.getScheduleTypeId().equals(3)
                && (phlebotomy.getWeek() == null || phlebotomy.getWeek().equals(0))))
        {
            DateTime testDay = new DateTime(startRange);
            do
            {
                startRange = testDay;
                testDay = testDay.minusDays(1);
            }
            while (IsWorkday(testDay, saturdayWorkday, sundayWorkday) == false);            
        }

        // If there's an end date, it's not an indefinite schedule
        if (phlebotomy.getEndDate() != null)
        {
            
            // If the schedule ends before the range we are viewing,
            //  limit the view range to the schedule end.
            DateTime scheduleEndDate = new DateTime(phlebotomy.getEndDate());
            if (scheduleEndDate.isBefore(endRange))
            {
                endRange = scheduleEndDate;
            }
        }
        
        DateTime scheduleStartDate = new DateTime(phlebotomy.getStartDate());
        
        // If the schedule starts after the range we're considering,
        //  set our range start to the schedule start.
        
            startRange = scheduleStartDate;
        

        
        // Return value
        ArrayList<Date> dates = new ArrayList<>();
        
        ArrayList<Integer> days;
        int frequency = phlebotomy.getFrequency();
        if (frequency < 1)
        {
            frequency = 1;
        }
        
        switch (phlebotomy.getScheduleTypeId())
        {
            case (1): // Daily

                while (startRange.isBefore(endRange) || startRange.isEqual(endRange))
                {
                    if (IsWorkday(startRange,saturdayWorkday,sundayWorkday))
                    {
                        // Add the date, but make sure to keep the hour/minute defined by the schedule start.
                        dates.add(startRange.withHourOfDay(scheduleStartDate.getHourOfDay())
                                .withMinuteOfHour(scheduleStartDate.getMinuteOfHour()).toDate());
                    }
                    startRange = startRange.plusDays(frequency);
                }
                break;
            case (2): // Weekly
                
                // Get the selected days of the week (i.e. MWF) where Monday = 1
                days = GetDayArray(phlebotomy);
                
                for (Integer day : days)
                {
                    // Get every instance of the given weekday between the
                    //  start day (inclusive) and end day (inclusive)
                    
                    dates.addAll(GetWeekdaysInRange(scheduleStartDate, day, startRange, endRange, frequency));
                }                
                break;

            case (3): // Monthly
            {                
                // There are two ways to define a monthly schedule:
                //   By day of month (e.g. the 15th of every month) or
                //   By week # and weekday (e.g. the 2nd Wednesday of every month)
                Integer week = phlebotomy.getWeek();
                if (week == null || week == 0)
                {
                    dates = GetMonthlyScheduleByFixedDate(
                            scheduleStartDate,
                            startRange,
                            endRange,
                            saturdayWorkday,
                            sundayWorkday,
                            frequency);
                }
                else
                {
                    
                    // Get the day of the week to consider (there should only be one)
                    ArrayList<Integer> day = GetDayArray(phlebotomy);
                    
                    if (day.size() != 1)
                    {
                        // Months should only allow a single selection
                        System.out.println("Error: A monthly schedule has more than one weekday set in the database");
                        return null;
                    }
                    
                    dates = GetMonthlyScheduleByWeekday(
                            week,
                            day.get(0),
                            scheduleStartDate,
                            startRange,
                            endRange,
                            saturdayWorkday,
                            sundayWorkday,
                            frequency);
                }
                
            }
                break;
            case (4): // Yearly
                
                // Start with the schedule date and increment by year, adding
                // every date that falls within our range, and stopping
                // once we're out of our range

                // The date we will be incrementing by a year
                DateTime checkDate = scheduleStartDate;
                int occurrencesConsidered = 1;
                Integer totalOccurrences = phlebotomy.getOccurrences();
                
                // While our check date is still within our start and end range
                while (checkDate.isBefore(endRange) || checkDate.isEqual(endRange))
                {
                    // Check to see if we're in the range yet
                    if (checkDate.isAfter(startRange) || checkDate.isEqual(startRange))
                    {                                            
                        // If it's indefinite or if we haven't exceeded the
                        // maximum number of occurrences
                        if (totalOccurrences == null || totalOccurrences == 0 || occurrencesConsidered <= totalOccurrences)
                        {
                            // Need to check if this is a valid workday:
                            if (IsWorkday(checkDate, saturdayWorkday, sundayWorkday))
                            {
                                dates.add(checkDate.toDate());
                            }
                            else // If not, move forward until we find a workday
                            {
                                int monthCheck = checkDate.getMonthOfYear();

                                DateTime foundWorkday = GetNextWorkday(checkDate, saturdayWorkday, sundayWorkday);
                                if (foundWorkday.getMonthOfYear() != monthCheck)
                                {
                                    // Not good. We've gone into the next month.
                                    // Instead look backwards until we find a
                                    // valid workday
                                    foundWorkday = foundWorkday.minusDays(1);
                                    foundWorkday = GetPreviousWorkday(foundWorkday, saturdayWorkday, sundayWorkday);
                                }

                                // Already have the time data in here:
                                dates.add(foundWorkday.toDate());                            
                            }
                        }
                    }
                    occurrencesConsidered += 1;
                    checkDate = checkDate.plusYears(frequency);
                }
                
                break;
        }
        
        return dates;
    }
    
    /**
     * Options for how the schedule should react if a date is scheduled
     *  on a non-workday. Add as necessary.
     */
    public enum WorkdayAdjustmentStrategy
    {
        // If scheduled on a non work-day, moves forward by one day until it
        //  finds a workday. If that workday is in the same month as the
        //  starting date, it's used. If that day is in the following month,
        //  it will back up however many days until it finds a valid workday.
        ForwardUntilValid_BackwardIfDifferentMonth;
    }
    
    /**
     * Checks to see if the supplied date is marked as a working day for the
     *  client. If so, it is returned. Otherwise, the provided date adjustment
     *  strategy is used to alter the date and return the result.    
     */
    private DateTime AdjustToWorkDay(WorkdayAdjustmentStrategy strategy,
            DateTime inputDateTime,
            boolean saturdayWorkday,
            boolean sundayWorkday)
            throws IllegalArgumentException, UnsupportedOperationException
    {
        if (strategy == null) throw new IllegalArgumentException("NULL date adjustment strategy supplied!");
        
        DateTime outputDate = new DateTime(inputDateTime);
        
        if (!IsWorkday(outputDate, saturdayWorkday, sundayWorkday))
        {
            // Must adjust the date to fall on a workday
            switch (strategy)
            {
                case ForwardUntilValid_BackwardIfDifferentMonth:
                    // If scheduled on a non work-day, moves forward by one day until it
                    //  finds a valid workday. If that workday is in the same month as the
                    //  starting date, it's used. If that day is in the following month,
                    //  it will back up however many days until it finds a valid workday.                         
                    int month = inputDateTime.getMonthOfYear();

                    outputDate = GetNextWorkday(outputDate,
                            saturdayWorkday, sundayWorkday);
                    
                    if (outputDate.getMonthOfYear() != month)
                    {
                        // Not good. We've gone into the next month.
                        // Instead look backwards until we find a
                        // valid workday
                        outputDate = outputDate.minusDays(1);
                        outputDate = GetPreviousWorkday(outputDate, saturdayWorkday, sundayWorkday);
                    }
                    
                    break;
                default:
                    throw new UnsupportedOperationException("Supplied adjustment strategy does not have any corresponding logic!");
            }
        }
        return outputDate;
    }
    
    /**
     * Gets dates within the range that match the same day of the month
     *  as the phlebotomy start date.
     * 
     *  e.g. if the Phlebotomy start date was February 15th and your range
     *   was the entire year, you'll get every 15th of the month from 
     *   February to December 15, inclusive.
     * 
     * 
     * @param isIndefinite
     * @param occurrences
     * @param phlebotomy
     * @param calendarRangeStart
     * @param calendarRangeEnd
     * @param saturdayWorkday
     * @param sundayWorkday
     * @return 
     */
    private ArrayList<Date> GetMonthlyScheduleByFixedDate(
            DateTime scheduleStartDate,
            DateTime startRange,
            DateTime endRange,
            boolean saturdayWorkday,
            boolean sundayWorkday,
            int frequency)
    {
        ArrayList<Date> dates = new ArrayList<>();
        
        // The fixed day of the month to check:
        int dayOfMonth = scheduleStartDate.getDayOfMonth();
        
        DateTime testDate = null;         // The date we'll be incrementing and checking
        DateTime adjustedTestDate = null; // The test date adjusted for non-workdays
        
        while (true)
        {
            // First attempt to set the test date to the fixed day of month
            // If this is the first time through, begin with the start range month
            if (testDate == null)
            {
                try
                {
                    testDate = startRange.withDayOfMonth(dayOfMonth);
                }
                catch (IllegalArgumentException ex)
                {
                    // The month doesn't have this day (i.e. we can't set the
                    //  date to February 31st), so set it to the max for
                    //  the current month:
                    testDate = startRange.dayOfMonth().withMaximumValue();
                }
            }
            else // Otherwise, adjust the current test date
            {
                try
                {
                    testDate = testDate.withDayOfMonth(dayOfMonth);
                }
                catch (IllegalArgumentException ex)
                {
                    // The month doesn't have this day (i.e. we can't set the
                    //  date to February 31st), so set it to the max for 
                    //  the current month:
                    testDate = testDate.dayOfMonth().withMaximumValue();
                }
            }
            
            // Then attempt to adjust this date, based on whether it's a workday
            adjustedTestDate = AdjustToWorkDay(
                    WorkdayAdjustmentStrategy.ForwardUntilValid_BackwardIfDifferentMonth,
                    testDate,
                    saturdayWorkday,
                    sundayWorkday);
            
            // Months between our start range and the current adjusted date
            int months = Months.monthsBetween(scheduleStartDate.dayOfMonth().withMinimumValue(),
                    adjustedTestDate.dayOfMonth().withMaximumValue()).getMonths();
            
            if (IsInRange(adjustedTestDate, startRange, endRange) && months % frequency == 0)
            {
                dates.add(adjustedTestDate.withHourOfDay(scheduleStartDate.getHourOfDay()).withMinuteOfHour(scheduleStartDate.getMinuteOfHour()).toDate());
            }
            else
            {
                if (adjustedTestDate.isAfter(endRange))
                {
                    break;
                }
            }
            // Incrementing testDate, as it reflects the original fixed day of month
            testDate = testDate.plusMonths(1);
        }
        return dates;
    }
    
    private ArrayList<Date> GetMonthlyScheduleByWeekday(
            int week,
            int day,
            DateTime scheduleStartDate,
            DateTime startRange,
            DateTime endRange,
            boolean saturdayWorkday,
            boolean sundayWorkday,
            int frequency)
    {
        
        ArrayList<Date> dates = new ArrayList<>();        
        DateTime weekday = new DateTime(startRange);
        
        // check the frequency against the start range (which begins in previous month), the current month,
        // and the end range (which goes into the next month). use the first month which is correct for the frequency
        int months = Months.monthsBetween(scheduleStartDate.dayOfMonth().withMinimumValue(),
         weekday.dayOfMonth().withMaximumValue()).getMonths();
        
        if (months % frequency != 0)
        {
            weekday = weekday.plusDays(5);
            months = Months.monthsBetween(scheduleStartDate.dayOfMonth().withMinimumValue(),
                weekday.dayOfMonth().withMaximumValue()).getMonths();
            if (months % frequency != 0)
            {
                weekday = new DateTime(endRange).dayOfMonth().withMinimumValue();
                months = Months.monthsBetween(scheduleStartDate.dayOfMonth().withMinimumValue(),
                    weekday.dayOfMonth().withMaximumValue()).getMonths();
                if (months % frequency != 0)
                {
                    return dates;
                }
            }
        }
        
        while (weekday.isBefore(endRange) || weekday.isEqual(endRange))
        {
            // Get the first day of the month
            weekday = new DateTime(weekday.getYear(),weekday.getMonthOfYear(),1,0,0);

            // Sometimes Joda will get the weekday for the last week of the
            //  previous month (?). Need to keep this to check.
            int month = weekday.getMonthOfYear();
            int year = weekday.getYear();
            
            // Try to get the first instance of the weekday in the month
            weekday = weekday.withDayOfWeek(day);
            
            // Check to see if Joda backed us up into the previous month
            // if so, skip to the correct week.
            if (weekday.getMonthOfYear() < month || weekday.getYear() < year)
            {
                weekday = weekday.plusWeeks(1);
            }
            
            // Keep the month to make sure we don't go too far if the user
            //  specified week=5 (the last week of the month)
            month = weekday.getMonthOfYear();

            // Increment the weeks 
            DateTime previousValue = null;
            for (int i=1;i<week;i++)
            {
                previousValue = weekday;
                weekday = weekday.plusWeeks(1);
            }

            // If we went too far when trying to get the last week
            //  of the month, use the previous value.
            if (weekday.getMonthOfYear() != month)
            {
                // If so, use the previous value:
                weekday = previousValue;
            }

            // Match the time
             weekday = weekday.withHourOfDay(scheduleStartDate.getHourOfDay()).withMinuteOfHour(scheduleStartDate.getMinuteOfHour()).withSecondOfMinute(scheduleStartDate.getSecondOfMinute());
             
            // We shouldn't need to worry about checking for non-workdays
            //  here, as order entry makes them unavailable for data-entry
            if (
                    (weekday.isAfter(startRange) || weekday.isEqual(startRange))
                    && 
                    (weekday.isBefore(endRange) || weekday.isEqual(endRange))
                )
            {
                dates.add(weekday.withHourOfDay(scheduleStartDate.getHourOfDay()).withMinuteOfHour(scheduleStartDate.getMinuteOfHour()).toDate());
            }            
            weekday = weekday.plusMonths(frequency);
            weekday = new DateTime(weekday.getYear(),weekday.getMonthOfYear(),1,0,0);
        }
        return dates;
    }
    
    private boolean IsInRange(DateTime checkValue, DateTime startRange, DateTime endRange)
    {
        if (checkValue == null || startRange == null || endRange == null) return false;
        
        return (
                (checkValue.isAfter(startRange) || checkValue.isEqual(startRange))
                &&
                (checkValue.isBefore(endRange) || checkValue.isEqual(endRange))
                );
    }
    
    private ArrayList<Integer> GetDayArray(Phlebotomy p)
    {
        ArrayList<Integer> days = new ArrayList<>();        
        if (p.isSunday() != null && p.isSunday()) days.add(DateTimeConstants.SUNDAY);
        if (p.isMonday() != null && p.isMonday()) days.add(DateTimeConstants.MONDAY);
        if (p.isTuesday() != null && p.isTuesday()) days.add(DateTimeConstants.TUESDAY);
        if (p.isWednesday() != null && p.isWednesday()) days.add(DateTimeConstants.WEDNESDAY);
        if (p.isThursday() != null && p.isThursday()) days.add(DateTimeConstants.THURSDAY);
        if (p.isFriday() != null && p.isFriday()) days.add(DateTimeConstants.FRIDAY);
        if (p.isSaturday() != null && p.isSaturday()) days.add(DateTimeConstants.SATURDAY);
        return days;
    }
    
    public boolean IsWorkday(DateTime input, boolean isSaturdayWorkday, boolean isSundayWorkday)
    {
        int dayOfWeek = input.getDayOfWeek();
        // JodaTime starts the week with Monday = 1, Tuesday = 2, etc..
        
        if (dayOfWeek > 0 && dayOfWeek < 6) return true; // Monday - Friday
        if (dayOfWeek == 6 && isSaturdayWorkday) return true;
        return (dayOfWeek == 7 && isSundayWorkday);
    }
    
    /**
     * Searches forward until the next workday is found, depending on whether
     *  the lab has Saturday and Sunday workdays. If the provided day is a workday,
     *  it is returned.
     * @param input
     * @param isSaturdayWorkday
     * @param isSundayWorkday
     * @return 
     */
    public DateTime GetNextWorkday(DateTime input, boolean isSaturdayWorkday, boolean isSundayWorkday)
    {
        DateTime returnVal = new DateTime(input);
        while (!IsWorkday(returnVal, isSaturdayWorkday, isSundayWorkday))
        {            
            returnVal = returnVal.plusDays(1);
        }
        return returnVal;
    }
    
    /**
     * Searches backwards until the next workday is found, depending on whether
     *  the lab has Saturday and Sunday workdays. If the provided day is a workday,
     *  it is returned.
     * @param input
     * @param isSaturdayWorkday
     * @param isSundayWorkday
     * @return 
     */
    public DateTime GetPreviousWorkday(DateTime input, boolean isSaturdayWorkday, boolean isSundayWorkday)
    {
        DateTime returnVal = new DateTime(input);
        while (!IsWorkday(returnVal, isSaturdayWorkday, isSundayWorkday))
        {
            returnVal = returnVal.minusDays(1);
        }
        return returnVal;
    }
    
    /**
     * Given a weekday (where Monday = 1, Tuesday = 2, etc), and date range
     *  information, returns the dates that fall within the range.
     * 
     *  rangeStart / rangeEnd are the date ranges to consider
     *  
     */
    private ArrayList<Date> GetWeekdaysInRange(
            DateTime scheduleStartDate,
            Integer weekday,
            DateTime startRange,
            DateTime endRange,
            int frequency)
    {
        ArrayList<Date> dates = new ArrayList<>();
        
        // Set the start time to the specified day of the week
        DateTime current = startRange.withDayOfWeek(weekday);
        
        // See if it's in range
        while (current.isBefore(endRange) || current.isEqual(endRange))                
        {
            // use days at start and end of week to ensure complete weeks counted
            int weeks = Weeks.weeksBetween(scheduleStartDate.dayOfWeek().withMinimumValue(), 
                current.dayOfWeek().withMaximumValue()).getWeeks();
            if ( (current.isAfter(scheduleStartDate) || current.isEqual(scheduleStartDate)) && 
                    (current.isAfter(startRange) || current.isEqual(startRange)) && weeks % frequency == 0)
            {
                // Add the day with the schedule time
                dates.add(current.withHourOfDay(
                    scheduleStartDate.getHourOfDay()).withMinuteOfHour(scheduleStartDate.getMinuteOfHour()).toDate());
            }
            current = current.plusWeeks(1);
        }        
        return dates;
    }
    
    public LocalDate getNthWeekdayOfMonth(int n, int weekday, int month, int year)
    {
        LocalDate firstOccurrence = new LocalDate(year, month, 1).withDayOfWeek(weekday);
        
        // Sometimes joda grabs the week from the previous month. Make sure we start
        // in the correct month.
        if (firstOccurrence.getMonthOfYear() < month || firstOccurrence.getYear() < year) firstOccurrence = firstOccurrence.plusWeeks(1);
        
        LocalDate checkDate = firstOccurrence;
        for (int week=1; week<n; week++)
        {
            // User can supply "5" as the week, meaning the "last occurrence" of
            //  a weekday. If adding another week would put us in the next month,
            //  we have the last week.
            if (checkDate.plusWeeks(1).getMonthOfYear() > month)
            {
                break; // This is the last week of the month
            }
            else
            {
                checkDate = checkDate.plusWeeks(1);
            }
        }        
        return checkDate;
    }
    
    /**
     *  Gets any schedule that overlaps the supplied start and end date.
     * 
     *  All arguments are optional and should be supplied as NULL if not required.
     * 
     *  Note : Does not take into consideration reschedules! Those rows can be
     *   retrieved using the idPhlebotomy.
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistIds
     * @param patientId
     * @param clientId
     * @param doctorId
     * @return 
     * @throws java.lang.Exception 
     */
    public ArrayList<Phlebotomy> GetPhlebotomyRowsInRange(
            Date startDate,
            Date endDate,
            HashSet<Integer> phlebotomistIds,
            Integer patientId,
            Integer clientId,
            Integer doctorId) throws Exception
    {
        PhlebotomyDAO phlebotomydao = new PhlebotomyDAO();
        ArrayList<Phlebotomy> phlebotomyResults = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        //try
        //{
        java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
        java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());

        // Get any phlebotomy schedule templates that overlap the provided
        // range, or in the case where the template duration is indefinite,
        // get all rows that have already begun, i.e. their starting date
        // is before the supplied range's end date.
        String query =  "SELECT "
                + " idPhlebotomy"
                + " FROM phlebotomy"
                + " WHERE inactive = 0 AND"
                + " ((endDate IS NOT NULL AND "
                + "  ('" + startSQLDate + "' <= endDate AND '" + endSQLDate + "' >= startDate ))"
                + " OR endDate IS NULL AND startDate <= '" + endSQLDate + "') ";

        if (phlebotomistIds != null && !phlebotomistIds.isEmpty())
        {                
            String phlebotomistIdList = "";
            for (Integer phlebotomistId : phlebotomistIds)
            {
                if (phlebotomistId != null)
                {
                    if (phlebotomistIdList.length() == 0)
                    {
                        phlebotomistIdList += phlebotomistId;
                    }
                    else
                    {
                        phlebotomistIdList += "," + phlebotomistId;
                    }
                }
            }

            if (phlebotomistIdList.length() > 0)
            {
                query += " AND phlebotomist IN (" + phlebotomistIdList + ")";
            }                
        }

        query += " ORDER BY phlebotomist";

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next())
        {
            
            // Grab the Phlebotomy row
            Integer phlebotomyId = rs.getInt("idPhlebotomy");
            if (phlebotomyId <= 0)
            {
                throw new SQLException("Unable to load phlebotomy data: returned phlebotomy Id was null!");
            }
            Phlebotomy phlebotomy = phlebotomydao.GetPhlebotomy(rs.getInt("idPhlebotomy"));
            if (phlebotomy == null || phlebotomy.getIdPhlebotomy() == null)
            {
                throw new SQLException("Unable to load phlebotomy data object for Id: " + phlebotomyId);
            }

            // If the user didn't specify any other criteria, add to list
            if (patientId == null && clientId == null && doctorId == null)
            {
                //phlebotomyResults.add(phlebotomydao.GetPhlebotomy(rs.getInt("idPhlebotomy")));                    
                phlebotomyResults.add(phlebotomy);
            }
            else // Otherwise, need to get additional data based on whether 
                 // this is an advanced order or redraw
            {
                if (phlebotomy.getIdAdvancedOrder() != null && phlebotomy.getIdAdvancedOrder() > 0)
                {
                    // This is an advanced order, use the advancedOrders table

                    query = "SELECT COUNT(*) AS rowCount FROM advancedOrders ao ";
                    if (patientId != null) query += " INNER JOIN patients p ON ao.patientId = p.idpatients";
                    if (clientId != null) query += " INNER JOIN clients c ON ao.clientId = c.idClients";
                    if (doctorId != null) query += " INNER JOIN doctors d ON ao.doctorId = d.iddoctors";
                    query += " WHERE ao.idAdvancedOrders = " + phlebotomy.getIdAdvancedOrder();
                    if (patientId != null) query += " AND p.idpatients = " + patientId;
                    if (clientId != null) query += " AND c.idclients = " + clientId;
                    if (doctorId != null) query += " AND d.iddoctors = " + doctorId;
                    PreparedStatement pStmt = con.prepareStatement(query);
                    ResultSet rs2 = pStmt.executeQuery();
                    if (rs2.next())
                    {
                        try
                        {
                            int rowCount = rs2.getInt("rowCount");
                            // If there is a row returned, it means that the
                            // provided criteria was met for the advanced order
                            if (rowCount > 0)
                            {
                                phlebotomyResults.add(phlebotomy);
                            }
                        }
                        catch (SQLException ex)
                        {
                            System.out.println("Unable to retrieve advanced order sub-query for patient/client/doctor criteria");
                        }
                    }
                    rs2.close();
                    pStmt.close();
                }
                else if (phlebotomy.getIdOrders() != null && phlebotomy.getIdOrders() > 0)
                {
                    // This is a redraw. Use the orders table.

                    query += "SELECT COUNT(*) AS rowCount FROM orders o ";
                    if (patientId != null) query += " INNER JOIN patients p ON o.patientId = p.idpatients";
                    if (clientId != null) query += " INNER JOIN clients c ON o.clientId = c.idClients";
                    if (doctorId != null) query += " INNER JOIN doctors d ON o.doctorId = d.iddoctors";
                    query += " WHERE o.idOrders = " + phlebotomy.getIdOrders();
                    if (patientId != null) query += " AND p.idpatients = " + patientId;
                    if (clientId != null) query += " AND c.idclients = " + clientId;
                    if (doctorId != null) query += " AND d.iddoctors = " + doctorId;
                    PreparedStatement pStmt = con.prepareStatement(query);
                    ResultSet rs2 = pStmt.executeQuery();
                    if (rs2.next())
                    {
                        try
                        {
                            int rowCount = rs2.getInt("rowCount");
                            // If there is a row returned, it means that the
                            // provided criteria was met for the advanced order
                            if (rowCount > 0)
                            {
                                phlebotomyResults.add(phlebotomy);
                            }
                        }
                        catch (SQLException ex)
                        {
                            System.out.println("Unable to retrieve redraw order sub-query for patient/client/doctor criteria");
                        }
                    }
                    rs2.close();
                    pStmt.close();
                }
                else
                {
                    throw new Exception("Supplied phlebotomy row has neither advanced order id or regular order id");
                }
            }
        }

        rs.close();
        stmt.close();
        return phlebotomyResults;
            /*
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetPhlebotomyRowsInRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }*/
    }
    
    /**
     * Returns dates that were part of the dynamically generated schedule within
     *  the supplied range and have been rescheduled to another day within
     *  the the supplied range.
     * 
     *  clientId is optional; if not supplied, it will return all rows.
     *  HashMap is the original scheduled date --> ArrayList of Reschedule objects
     * 
     *   In terms of the calendar, this method returns dates that need to be
     *     moved from their default position to their rescheduled position
     *     before they are displayed.
     * 
     *  Does not take into consideration reassigned rows (use the GetReassignedTo method)
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @param clientId
     * @return 
     */
    public HashMap<LocalDate, ArrayList<Reschedule>> GetOrderReschedulesWithinRange(
            Date startDate, Date endDate, Integer phlebotomistId, Integer clientId)
    {
        HashMap<LocalDate, ArrayList<Reschedule>> dateToReschedules = new HashMap<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        try
        {
            java.sql.Date startSQLDate = new java.sql.Date(startDate.getTime());
            java.sql.Date endSQLDate = new java.sql.Date(endDate.getTime());
            
            // Three possibilities for reschedule rows:
            // 1) NULL originalDate, NOT NULL rescheduleDate = new draw (not part of the original schedule)
            // 2) NOT NULL originalDate, NULL rescheduleDate = cancelled draw
            // 3) NOT NULL originalDate, NOT NULL rescheduleDate = rescheduled draw
            
            // Since we want reschedules, grab #3
            // We also want reschedules where the original and reschedule date are
            // within the supplied range.
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE "
                    + " originalDate IS NOT NULL AND "
                    + " rescheduleDate IS NOT NULL AND "
                    + " originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'"
                    + " AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'";
                    
            if (phlebotomistId != null && phlebotomistId > 0)
            {
                query += " AND phlebotomistId = " + phlebotomistId + " AND reassignedToPhlebotomistId IS NULL ";
            }
            
            if (clientId != null && clientId > 0)
            {
                query += " AND clientId = " + clientId;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            ArrayList<Reschedule> reschedules;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    if (dateToReschedules.containsKey(LocalDate.fromDateFields(reschedule.getOriginalDate())))
                    {
                        reschedules = dateToReschedules.get(LocalDate.fromDateFields(reschedule.getOriginalDate()));
                        reschedules.add(reschedule);
                    }
                    else
                    {
                        reschedules = new ArrayList<>();
                        reschedules.add(reschedule);                        
                    }
                    dateToReschedules.put(LocalDate.fromDateFields(reschedule.getOriginalDate()), reschedules);
                    //(reschedule.getOriginalDate(), reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return dateToReschedules;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetOrderReschedulesWithinRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }
    }

    /**
     * Gets the phlebotomy Id for draws that were not scheduled within
     *  the supplied date range, but have been created or rescheduled into
     *  the date range.
     * 
     *  Note: does NOT consider re-assigned rows. To get the reassigns,
     *    use the GetReassign methods.
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @return 
     */
    public HashSet<Integer> GetPhlebotomyIdsNewOrRescheduledIntoRange(
        Date startDate, Date endDate, Integer phlebotomistId)
    {
        HashSet<Integer> phlebotomyIds = new HashSet<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Timestamp  startSQLDate = new java.sql.Timestamp (startDate.getTime());
            java.sql.Timestamp  endSQLDate = new java.sql.Timestamp (endDate.getTime());
            
            String query = "SELECT phlebotomyId "
                    + " FROM reschedules"
                    + " WHERE reassignedToPhlebotomistId IS NULL AND "
                    + " rescheduleDate BETWEEN '"+ startSQLDate + "' AND '" + endSQLDate + "'";

            if (phlebotomistId != null && phlebotomistId > 0)
            {
                query += " AND phlebotomistId = " + phlebotomistId; //+ " AND reassignedToPhlebotomistId IS NULL";
            }
            
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                phlebotomyIds.add(rs.getInt("phlebotomyId"));
            }
            
            rs.close();
            stmt.close();
            return phlebotomyIds;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetNewOrRescheduledIntoRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }        
    }
    
    
    /**
     *   Gets any reschedule rows that were either created within the calendar
     *     itself, or dates that were dynamically created outside of the 
     *     supplied range but were rescheduled within the supplied range.
     * 
     * 
     *   In terms of the calendar, this method returns dates that are not part
     *     of the dynamically created schedule but need to be displayed anyway.
     * 
     *  Does not take into consideration reassigned rows (use the GetReassignedTo method)
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @param clientId
     * @return 
     */
    public ArrayList<Reschedule> GetNewOrRescheduledIntoRange(
            Date startDate, Date endDate, Integer phlebotomistId, Integer clientId)
    {
        ArrayList<Reschedule> reschedules = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());
            
            // Three possibilities for reschedule rows:
            // 1) NULL originalDate, NOT NULL rescheduleDate = new draw (not part of the original schedule)
            // 2) NOT NULL originalDate, NULL rescheduleDate = cancelled draw
            // 3) NOT NULL originalDate, NOT NULL rescheduleDate = rescheduled draw
            
            // Since we want new or rescheduled in, grab #3
            // We also want rows that are rescheduled into the range, so the
            //  original date is not in the range, but the reschedule date is.
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE  "
                    + " ((originalDate IS NULL AND rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "')"
                    + " OR "  // original date is outside of range, but reschedule date is in:
                    + " (originalDate NOT BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'"
                    + " AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'))";
                    
            if (phlebotomistId != null && phlebotomistId > 0)
            {
                query += " AND phlebotomistId = " + phlebotomistId + " AND reassignedToPhlebotomistId IS NULL ";
            }
            
            if (clientId != null && clientId > 0)
            {
                query += " AND clientId = " + clientId;
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    reschedules.add(reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return reschedules;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetNewOrRescheduledIntoRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }
    }
    
    /**
     * Given an iterable of integers, returns a string of comma-separated
     *  values for use in a SQL query.
     * @param integers
     * @return 
     */
    private String IterableIntegersToSQLString(Iterable<Integer> integers)
    {        
        if (integers == null || !integers.iterator().hasNext())
        {
            return null;
        }
        
        String output = "";
        for (Integer integer : integers)
        {
            if (output.isEmpty())
            {
                output = integer.toString();
            }
            else
            {
                output += "," + integer.toString();
            }
        }
        return output;
    }
    
    public ArrayList<Reschedule> GetNewOrRescheduledIntoRange(Date startDate, Date endDate, Iterable<Integer> phlebotomistIds)
    {
        ArrayList<Reschedule> reschedules = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());
            
            String strPhlebotomistIds = IterableIntegersToSQLString(phlebotomistIds);

            if (strPhlebotomistIds == null || strPhlebotomistIds.isEmpty())
            {
                return null;
            }            
            
            // Three possibilities for reschedule rows:
            // 1) NULL originalDate, NOT NULL rescheduleDate = new draw (not part of the original schedule)
            // 2) NOT NULL originalDate, NULL rescheduleDate = cancelled draw
            // 3) NOT NULL originalDate, NOT NULL rescheduleDate = rescheduled draw
            
            // Since we want new or rescheduled in, grab #3
            // We also want rows that are rescheduled into the range, so the
            //  original date is not in the range, but the reschedule date is.
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE  "
                    + " ((originalDate IS NULL AND rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "')"
                    + " OR "  // original date is outside of range, but reschedule date is in:
                    + " (originalDate NOT BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'"
                    + " AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'))"
                    + " AND phlebotomistId IN ( " + strPhlebotomistIds + ") AND reassignedToPhlebotomistId IS NULL";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    reschedules.add(reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return reschedules;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetNewOrRescheduledIntoRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }        
    }
    
    public HashSet<Integer> GetPhlebotomyIdsCanceledOrRescheduledOutOfRange(
        Date startDate, Date endDate, Integer phlebotomisId)
    {
        HashSet<Integer> phlebotomyIds = new HashSet<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        try
        {
            java.sql.Date startSQLDate = new java.sql.Date(startDate.getTime());
            java.sql.Date endSQLDate = new java.sql.Date(endDate.getTime());
            
            String query = "SELECT phlebotomyId "
                    + " FROM reschedules"
                    + " WHERE reassignedToPhlebotomistId IS NULL AND "
                    + " (rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "') OR "
                    + " (originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate +"' AND"
                    + " rescheduleDate IS NOT NULL AND rescheduleDate NOT BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "')";

            if (phlebotomisId != null && phlebotomisId > 0)
            {
                query += " AND phlebotomistId = " + phlebotomisId;
            }
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
           
            while (rs.next())
            {
                phlebotomyIds.add(rs.getInt("phlebotomyId"));
            }
            
            rs.close();
            stmt.close();
            return phlebotomyIds;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetCanceledOrRescheduledOutOfRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }            
    }
    
    /**
     *  Gets any reschedule rows that either exist in the dynamically created
     *    schedule and were canceled, or exist in the dynamically created
     *    schedule but were rescheduled to a date outside of the range.
     * 
     *  In terms of the calendar, this method returns dates that are part of
     *    the dynamically created schedule but shouldn't be displayed.
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @param clientId
     * @return 
     */
    public HashMap<LocalDate, Reschedule> GetCanceledOrRescheduledOutOfRange(
            Date startDate, Date endDate, Integer phlebotomistId, Integer clientId)
    {
        HashMap<LocalDate, Reschedule> reschedules = new HashMap<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);        
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());
            
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE "
                    + " ((originalDate IS NOT NULL AND rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "') " // <-- canceled
                    + " OR " // Original date within range but rescheduled out of range:
                    + " (originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'"
                    + " AND rescheduleDate IS NOT NULL AND rescheduleDate NOT BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "'))";

            if (phlebotomistId != null && phlebotomistId > 0)
            {
                // 
                query += " AND phlebotomistId = " + phlebotomistId + " AND reassignedToPhlebotomistId IS NULL ";
            }
            
            if (clientId != null && clientId > 0)
            {
                query += " AND clientId = " + clientId;
            }
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    reschedules.put(LocalDate.fromDateFields(reschedule.getOriginalDate()), reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return reschedules;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetCanceledOrRescheduledOutOfRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all reassignment rows in the range. The supplied phlebotomistIds
     *  represent the phlebotomists that these rows are reassigned TO. It
     *  cannot be null.
     * 
     * @param startDate
     * @param endDate
     * @param phlebotomistIds
     * @return 
     */
    public ArrayList<Reschedule> GetReassignedTo(Date startDate, Date endDate, Iterable<Integer> phlebotomistIds)
    {
        ArrayList<Reschedule> reassignedTo = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String strPhlebotomistIds = IterableIntegersToSQLString(phlebotomistIds);
        
        if (strPhlebotomistIds == null || strPhlebotomistIds.isEmpty())
        {
            return null;
        }
        
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());

            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE"
                    + " (rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId IN (" + strPhlebotomistIds + ")) " // This is simply a reassignment
                    + " OR "
                    + "(rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId IN (" + strPhlebotomistIds + ")) "; // This is a reschedule and assignment

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    reassignedTo.add(reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return reassignedTo;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetCanceledOrRescheduledOutOfRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }
    }
    
    /**
     * Retrieves rows that have been reassigned to the phlebotomist within
     *  the provided range.
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @return 
     */
    public ArrayList<Reschedule> GetReassignedTo(Date startDate, Date endDate, Integer phlebotomistId)
    {
        
        ArrayList<Reschedule> reassignedTo = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());
            
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE"
                    + " (rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId = " + phlebotomistId + ") " // This is simply a reassignment
                    + " OR "
                    + "(rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND reassignedToPhlebotomistId = " + phlebotomistId + ") "; // This is a reschedule and assignment

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    reassignedTo.add(reschedule);
                    //reschedules.put(LocalDate.fromDateFields(reschedule.getOriginalDate()), reschedule);
                }
            }
            
            rs.close();
            stmt.close();
            return reassignedTo;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetCanceledOrRescheduledOutOfRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }        
        
    }
    
    /**
     * Retrieves rows that have been reassigned to the phlebotomist within
     *  the provided range (multiple orders can be for a single date)
     * @param startDate
     * @param endDate
     * @param phlebotomistId
     * @return 
     */
    public HashMap<LocalDate,ArrayList<Reschedule>> GetReassignedFrom(Date startDate, Date endDate, Integer phlebotomistId)
    {
        ArrayList<Reschedule> reschedules = new ArrayList<>();
        HashMap<LocalDate,ArrayList<Reschedule>> reassignedTo = new HashMap<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            java.sql.Timestamp startSQLDate = new java.sql.Timestamp(startDate.getTime());
            java.sql.Timestamp endSQLDate = new java.sql.Timestamp(endDate.getTime());
            
            String query = "SELECT * "
                    + " FROM reschedules"
                    + " WHERE"
                    + " (rescheduleDate IS NULL AND originalDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND phlebotomistId = " + phlebotomistId + " AND reassignedToPhlebotomistId IS NOT NULL) " // This is simply a reassignment
                    + " OR "
                    + "(rescheduleDate IS NOT NULL AND rescheduleDate BETWEEN '" + startSQLDate + "' AND '" + endSQLDate + "' "
                    + " AND phlebotomistId = " + phlebotomistId + " AND reassignedToPhlebotomistId IS NOT NULL) "; // This is a reschedule and assignment

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            RescheduleDAO rescheduledao = new RescheduleDAO();
            Reschedule reschedule;
            LocalDate localDate;
            while (rs.next())
            {
                reschedule = new Reschedule();
                rescheduledao.GetRescheduleFromResultSet(reschedule, rs);
                // If we have a valid reschedule
                if (reschedule.getIdReschedules() != null)
                {
                    localDate = new LocalDate(reschedule.getOriginalDate());
                    
                    if (reassignedTo.containsKey(localDate))
                    {
                        reschedules = reassignedTo.get(localDate);
                        if (reschedules == null) reschedules = new ArrayList<>();
                        reschedules.add(reschedule);
                    }
                    else
                    {
                        reschedules = new ArrayList<>();
                        reschedules.add(reschedule);
                        reassignedTo.put(localDate, reschedules);
                    }
                }
            }            
            rs.close();
            stmt.close();
            return reassignedTo;
        }
        catch(SQLException ex)
        {
            System.out.println("AdvancedOrderBL::GetCanceledOrRescheduledOutOfRange " + 
                    "Unable to obtain range for " + startDate.toString() + 
                    " - " + endDate.toString() + "\n\n" + ex.toString());
            return null;
        }        
    }
    
    /**
     * Checks the advanced order creation log and returns the highest accession
     *  that has been auto-generated by the process. Used to determine the
     *  starting accession for new generation runs.
     * 
     *  Returns NULL on error or if no accessions have been generated
     *  on error.
     * 
     * @return String accession
     * @throws java.lang.Exception
     */
    public String GetHighestGeneratedAccession() throws Exception
    {
        String sql = "SELECT accession FROM orders o"
                + " INNER JOIN advancedOrderLog aol ON o.idorders = aol.orderid"
                + " WHERE aol.orderCreated = 1 AND aol.errorFlag = 0"
                + " ORDER BY `date` DESC LIMIT 1";
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String accession = null;
        Statement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next())
        {
            accession = rs.getString("accession");
        }
        return accession;
    }
    
    /**
     * Calls stored procedure to get a map from advancedOrderId to the next scheduled draw date.
     * @param checkFrom - the start date for scheduling
     * @param patientId - the patient id for the advanced orders (null if none)
     * @param clientId - the client id for the advanced orders (null if none)
     * @param doctorId - the doctor id for the advanced orders (null if none)
     * @param scheduleTypeId - the schedule type id to filter advanced orders (null for no filtering on schedule type)
     * @param saturdayWorkday - whether Saturday is to be considered a workday
     * @param sundayWorkday - whether Sunday is to be considered a workday
     * @return a map from advanced order id to the next scheduled draw date
     * @throws java.sql.SQLException 
     */
    public Map<Integer, Date> GetNextDrawDates(Date checkFrom, Integer patientId,
            Integer clientId, Integer doctorId, Integer scheduleTypeId, boolean saturdayWorkday, boolean sundayWorkday) throws SQLException
    {
        Map<Integer, Date> advancedOrderNextDraw = new HashMap<Integer, Date>();
        Date nextDrawDate = null;
        if (checkFrom == null) checkFrom = new Date();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        String sql = "CALL PhlebotomyMonthlyDraws(?,?,?,?,?,?,?,?,?,?);";
        CallableStatement callable = con.prepareCall(sql);

        // ================ Parameters: ==================
        
        //    p_onlyFirstScheduled --> Will always be true for this method
        callable.setBoolean(1, true);
        
        //    p_startDate --> Parameter date that was passed in
        callable.setDate(2, new java.sql.Date(checkFrom.getTime()));
        
        //    p_endDate --> default to a year from the starting point
        DateTime endDate = new DateTime(checkFrom).plusYears(1);
        callable.setDate(3, new java.sql.Date(endDate.toDate().getTime()));
        
        //    p_patientId 
        if (patientId == null)
        {
            callable.setNull(4, java.sql.Types.INTEGER);
        }
        else
        {
            callable.setInt(4, patientId);
        }
        
        //    p_clientId
        if (clientId == null)
        {
            callable.setNull(5, java.sql.Types.INTEGER);
        }
        else
        {
            callable.setInt(5, clientId);
        }
        
        //    p_doctorId
        if (doctorId == null)
        {
            callable.setNull(6, java.sql.Types.INTEGER);
        }
        else
        {
            callable.setInt(6, doctorId);
        }
        
        //    p_scheduleType
        if (scheduleTypeId == null)
        {
            callable.setNull(7, java.sql.Types.INTEGER);
        }
        else
        {
            callable.setInt(7, scheduleTypeId);
        }
        
        //    p_saturdayWorkday
        callable.setBoolean(8, saturdayWorkday);
        
        //    p_sundayWorkday
        callable.setBoolean(9, sundayWorkday);
        
        // Out parameter (boolean success var)
        callable.registerOutParameter(10, java.sql.Types.BOOLEAN);
        
        ResultSet rs = callable.executeQuery();
        if (rs.next())
        {
            Boolean success = rs.getBoolean(1);
            if (success)
            {
                sql = "SELECT DISTINCT(advancedOrderId),drawDate FROM PhlebotomyUpcomingDraws";
                Statement stmt = con.createStatement();
                ResultSet procRs = stmt.executeQuery(sql);
                while (procRs.next())
                {
                    Integer procAdvancedOrderId = procRs.getInt("advancedOrderId");
                    if (procAdvancedOrderId < 1) continue;
                    nextDrawDate = procRs.getTimestamp("drawDate");
                    advancedOrderNextDraw.put(procAdvancedOrderId, nextDrawDate);
                }
                stmt.close();
                procRs.close();
            }
            else
            {
                throw new SQLException("Stored Procedure call failed");
            }
        }
        callable.close();
        rs.close();

        return advancedOrderNextDraw;
    }
    
    /**
     * Returns orders that were generated for the provided advanced order ID.
     * @param advancedOrderId
     * @return 
     * @throws SQLException 
     */
    public List<Orders> GetOrdersCreatedByAdvancedOrder(int advancedOrderId) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        ArrayList<Orders> orders = new ArrayList<>();
        String sql = "SELECT DISTINCT(orderId) "
                + "FROM advancedOrderLog where orderCreated = 1 "
                + "AND advancedOrderId = ?; ";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, advancedOrderId);
        ResultSet rs = pStmt.executeQuery();
        OrderDAO odao = new OrderDAO();
        while (rs.next())
        {
            try
            {
                Orders o = odao.GetOrderById(rs.getInt("orderId"));
                if (o == null || o.getIdOrders() == null || o.getIdOrders() == 0)
                {
                    throw new SQLException("Can't retrieve order object for advancedOrderId " + advancedOrderId);
                }
                orders.add(o);
            }
            catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        return orders;
    }
}
