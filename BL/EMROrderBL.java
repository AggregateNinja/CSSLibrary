/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAOS.EmrXrefDAO;
import DAOS.InsuranceDAO;
import DAOS.InsuranceXrefDAO;
import DAOS.PanelDAO;
import DAOS.SysLogDAO;
import DAOS.TestDAO;
import DAOS.XrefsDAO;
import DOS.EmrXref;
import DOS.IDOS.IDO;
import DOS.InsuranceXref;
import DOS.Insurances;
import DOS.Orders;
import DOS.Panels;
import DOS.Results;
import DOS.Tests;
import DOS.Xrefs;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import EMR.DAOS.EmrEventLogDAO;
import EMR.DAOS.EmrFileLogDAO;
import EMR.DAOS.EmrIssueDAO;
import EMR.DAOS.EmrIssueTypeDAO;
import EMR.DAOS.OrderDAO;
import EMR.DOS.EmrEventLog;
import EMR.DOS.EmrFile;
import EMR.DOS.EmrFileLog;
import EMR.DOS.EmrIssue;
import EMR.DOS.EmrIssueType;
import Utility.ExceptionUtil;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rypip_000
 */
public class EMROrderBL
{

    public EMROrderBL() {}

    public ArrayList<Results> CreateResultsForEMROrder(ArrayList<Results> m_results)
    {
        //The array to be returned
        ArrayList<Results> results = new ArrayList<Results>();
        int id = 1;
        //Create the DAOS needed
        TestDAO tdao = new TestDAO();
        PanelDAO pdao = new PanelDAO();

        /*
         * Each Result has one test on it that could be a Battery or Panel
         * This will break down batteries and panels, and add all the needed
         * Results objects to the new results ArrayList
         */
        for (Results res : m_results) {

            if (res.getPanelId() == null || res.getPanelId() > 0) {
                continue;
            }

            //TestId is this situation is the test number
            Tests test = tdao.GetTestOrCalcByNumber(res.getTestId());
            
            //Make sure the test exists
            if(test == null || test.getIdtests() == null){
                continue;
            }

            //Fist check if it is a Battery
            if (test.getHeader() == true) {
                //Test is a Battery.  Add it's header to results
                Results res1 = new Results();
                res1.setOrderId(res.getOrderId());
                res1.setCreated(res.getCreated());
                int batteryId = test.getIdtests();
                res1.setTestId(batteryId);
                res1.setIdResults(id);
                id++;
                results.add(res1);

                //Get the Battery's subtests
                ArrayList<Panels> batteryList = (ArrayList<Panels>) pdao.GetOrderedPanels(test.getIdtests());

                //Now go through each batteryList entry. If a panel, must break it down
                //if it is a single test, add it to results
                for (Panels pnl : batteryList) {
                    Tests sub1 = tdao.GetTestByID(pnl.getSubtestId());

                    //Now check if this is a Panel
                    if (sub1.getTestType() == 0) {
                        //It is a panel
                        //Add the Panel Header to the Array
                        Results res2 = new Results();
                        res2.setOrderId(res.getOrderId());
                        res2.setCreated(res.getCreated());
                        int panelId = sub1.getIdtests();
                        res2.setTestId(panelId);
                        res2.setPanelId(batteryId);
                        res2.setIdResults(id);
                        res2.setOptional(pnl.isOptional());
                        id++;
                        results.add(res2);

                        //Get the Panel's Subtests
                        ArrayList<Panels> panelList = (ArrayList<Panels>) pdao.GetOrderedPanels(panelId);

                        for (Panels pn2 : panelList) {
                            //These will all be single tests, so just add them
                            Results res3 = new Results();
                            res3.setOrderId(res.getOrderId());
                            res3.setCreated(res.getCreated());
                            res3.setTestId(pn2.getSubtestId());
                            res3.setPanelId(panelId);
                            res3.setIdResults(id);
                            res3.setOptional(pn2.isOptional());
                            id++;
                            results.add(res3);
                        }

                    } else {
                        //It is a single
                        Results res4 = new Results();
                        res4.setOrderId(res.getOrderId());
                        res4.setCreated(res.getCreated());
                        res4.setTestId(pnl.getSubtestId());
                        res4.setPanelId(batteryId);
                        res4.setOptional(pnl.isOptional());
                        res4.setIdResults(id);
                        id++;
                        results.add(res4);
                    }
                }
            } else if (test.getTestType() == 0) {
                //It is a panel
                //Add the Panel Header to the Array
                Results res5 = new Results();
                res5.setOrderId(res.getOrderId());
                res5.setCreated(res.getCreated());
                int panelId = test.getIdtests();
                res5.setTestId(panelId);
                res5.setIdResults(id);
                id++;
                results.add(res5);

                //Get the Panels Subtests
                ArrayList<Panels> panelList = (ArrayList<Panels>) pdao.GetOrderedPanels(panelId);

                for (Panels pn2 : panelList) {
                    //These will all be single tests, so just add them
                    Results res6 = new Results();
                    res6.setOrderId(res.getOrderId());
                    res6.setCreated(res.getCreated());
                    res6.setTestId(pn2.getSubtestId());
                    res6.setPanelId(panelId);
                    res6.setIdResults(id);
                    res6.setOptional(pn2.isOptional());
                    id++;
                    results.add(res6);
                }
            } else {
                //It is a single Test
                Results res7 = new Results();
                res7.setOrderId(res.getOrderId());
                res7.setCreated(res.getCreated());
                res7.setTestId(test.getIdtests());
                res7.setIdResults(id);
                id++;
                results.add(res7);
            }
        }

        return results;
    }
    
    //**************************************************************************
    
    /**
     * Sets the insurance for an EMR order, logs the change, sets the emrXref
     *  insuranceInterface mapping (if not already set), creates the insurance
     *  xref for the vendor (if one does not exist), and removes the issue row
     *  and data so the record can be approved into the LIS.
     * 
     * @param userId
     * @param emrOrder The emr order to apply this change
     * @param insurance The insurance to map this emr order and emr insurance xref to
     * @param issue The issue row that will be removed when the operation is complete
     * @param emrXref The cross-reference for the vendor interface
     * @param emrEventLog
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static void MapExistingInsurance(
            Integer userId,
            Orders emrOrder,
            Insurances insurance,
            EmrIssue issue,
            EmrXref emrXref,
            EmrEventLog emrEventLog)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (emrOrder == null || emrOrder.getIdOrders() == null || emrOrder.getIdOrders().equals(0))
            throw new IllegalArgumentException("EMROrderBL::MapExistingInsurance: Received a NULL or invalid EMR orders object");
        
        if (insurance == null || insurance.getIdinsurances() == null || insurance.getIdinsurances().equals(0))
            throw new IllegalArgumentException("EMROrderBL::MapExistingInsurance: Received a NULL or invalid insurance object");
        
        if (issue == null || issue.getIdEmrIssues() == null || issue.getIdEmrIssues().equals(0))
            throw new IllegalArgumentException("EMROrderBL::MapExistingInsurance: Received a NULL or invalid EmrIssue object");
        
        if (issue.getVendorCode() == null || issue.getVendorCode().isEmpty())
        {
            throw new IllegalArgumentException("EMROrderBL::MapExistingInsurance: "
                    + "Received a NULL or blank vendor code for insurance. Cannot create mapping without a vendor identifier");
        }
        
        if (emrEventLog == null)
            throw new IllegalArgumentException("EMROrderBL::MapExistingInsurance: "
                    + "Received a NULL emrEventLog argument");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        try
        {
            // Transaction begin
            conn.setAutoCommit(false);
            
            // Set the insurance on the order
            System.out.println("Updating insurance on emr order row..");
            emrOrder.setInsurance(insurance.getIdinsurances());
            EMR.DAOS.OrderDAO odao = new EMR.DAOS.OrderDAO();
            odao.UpdateOrder(emrOrder);
        
            // Create or retrieve xref for EMR interface (used for insurance Xref)
            System.out.println("Attempting to get xref row.. ");
            Xrefs xref = AddOrGetXRef(
                    emrXref.getInsuranceInterface(),
                    emrXref.getName() + " Insurance Interface",
                    emrXref.getDescription(),
                    "Insurance Interface");
            
            // Save the xref id onto the emrXref table for future use
            if (emrXref.getInsuranceInterface() == null || emrXref.getInsuranceInterface().equals(0))
            {
                System.out.println("Found it on emrXref table");
                
                // Save this on the emr x ref for future use
                emrXref.setInsuranceInterface(xref.getIdxrefs());
                EmrXrefDAO exdao = new EmrXrefDAO();
                exdao.Update(emrXref);
            }
            
            // Check to see if an insurance cross reference already exists for
            // this mapping before inserting new (this can happen when multiple
            // orders are being processed and the insurance cross reference has
            // already been set)
            System.out.println("Checking for insurance cross-reference..");
            InsuranceXrefDAO ixdao = new InsuranceXrefDAO();
            List<InsuranceXref> insuranceXrefs = InsuranceXrefDAO.Search(
                    InsuranceXrefDAO.SearchType.ACTIVE_ONLY,
                    insurance.getIdinsurances(),
                    xref.getIdxrefs(),
                    issue.getVendorCode());
            
            if (insuranceXrefs.isEmpty())
            {
                System.out.println("Adding new insurance cross reference..");
                InsuranceXref insuranceXref = new InsuranceXref();
                insuranceXref.setIdinsurances(insurance.getIdinsurances());
                insuranceXref.setIdxrefs(xref.getIdxrefs());
                insuranceXref.setTransformedIn(issue.getVendorCode());
                insuranceXref.setTransformedOut(issue.getVendorCode());
                insuranceXref.setDescription("Created via EMR Import Action");
                insuranceXref.setActive(true);
                ixdao.Insert(insuranceXref);
            }
            else
            {
                System.out.println(
                        "Skipping insurance cross-reference insert as an active"
                                + "row already exists for this insurance id /"
                                + "xref / vendorCode combination");
            }
            
            // Add event log row
            System.out.println("Logging event..");
            
            EmrEventLogDAO eeldao = new EmrEventLogDAO();   
            eeldao.Insert(emrEventLog);            
            
            System.out.println("Removing issue associated data..");
            InsuranceDAO.Delete(IDO.DatabaseSchema.EMRORDERS, issue.getDatabaseIdentifier());
            
            System.out.println("Removing issue line..");
            EmrIssueDAO.Delete(issue.getIdEmrIssues());
            
            conn.commit();
        }
        catch (SQLException | NullPointerException | IllegalArgumentException ex)
        {
            // Undo any partially-completed work
            conn.rollback();
            
            // Rethrow to calling code
            throw ex;
        }
        finally
        {
            // Always set the driver mode back to immediate commit
            conn.setAutoCommit(true);
        }
    }
    
    /**
     * Retrieves the xref row for the provided emr x ref ID. If ID is NULL,
     *  a new Xref is inserted using the supplied name, description, and type.
     *  Otherwise name and description are ignored.
     *
     * An object representing the existing/new row is returned.
     * @param emrXref
     * @return 
     */
    private static Xrefs AddOrGetXRef(Integer xrefId, String name, String description, String typeName)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (xrefId == null && (name == null || name.isEmpty()))
            throw new IllegalArgumentException("EMROrderBL::AddOrGetXRef: "
                    + "Received a NULL xrefId AND name. Can't insert a new xref without a name");
        
        XrefsDAO xrdao = new XrefsDAO();
        Xrefs xref = null;
        
        if (xrefId != null && xrefId.equals(0) == false)
        {
            xref = xrdao.GetXrefById(xrefId);
            if (xref == null || xref.getIdxrefs() == null || xref.getIdxrefs().equals(0))
            {
                throw new SQLException("EMROrderBL::AddOrGetXRef: "
                        + "Attempted to load existing emr xref for id " 
                        + xrefId + " but was returned NULL object");
            }
        }
        
        // We don't have an xref, try to create one:
        if (xref == null || xref.getIdxrefs() == null || xref.getIdxrefs().equals(0))
        {
            xref = new Xrefs();
            xref.setName(name);
            xref.setType(typeName);
            xref.setDescription(description);
            xref = XrefsDAO.Insert(xref);
            if (xref == null || xref.getIdxrefs() == null || xref.getIdxrefs().equals(0))
            {
                throw new SQLException("EMROrderBL::AddOrGetXRef: "
                        + "Attempted to insert new Xref but was returneda NULL object");
            }                
        }        
        
         return xref;
    }
    
    //**************************************************************************    
    /**
     * Marks the emr order as no longer active, logs action.
     * @param orderId The emrorders.idorders column
     * @param userId User Id of individual performing action
     * @param eventLog
     * @throws java.sql.SQLException
     */
    public static void DeleteOrder(Integer orderId,
            Integer userId, EmrEventLog eventLog)
            
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderId == null || orderId <= 0) throw new IllegalArgumentException(
                "EMROrderBL::DeleteOrder: Received a NULL orderId argument!");
        if (userId == null || userId <= 0)  throw new IllegalArgumentException(
                "EMROrderBL::DeleteOrder: Received a NULL userId argument!");
        
        EMR.DAOS.OrderDAO odao = new OrderDAO();
        Orders order = odao.GetOrderById(orderId);
        
        DeleteOrder(order, userId, eventLog);
        
    }
        
    //**************************************************************************    
    /**
     * Marks the emr order as no longer active, logs action.
     * NOTE: Orders object should be an emrorders record, not an LIS order.
     * @param order EMR order
     * @param userId User Id of individual performing action
     * @param eventLog
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static void DeleteOrder(Orders order, Integer userId, EmrEventLog eventLog)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (order == null || order.getIdOrders() == null || order.getIdOrders() <= 0)
            throw new IllegalArgumentException("EMROrderBL::DeleteOrder: "
                    + "Received a NULL orderId argument!");
        
        if (userId == null || userId <= 0)  throw new IllegalArgumentException(
                "EMROrderBL::DeleteOrder: Received a NULL userId argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        try
        {
            conn.setAutoCommit(false);
            
            // "Delete" order by marking it inactive
            order.setActive(false);
            EMR.DAOS.OrderDAO odao = new OrderDAO();
            odao.UpdateOrder(order);
            
            // Insert a line into the emrorders.eventLog
            EmrEventLogDAO eeldao = new EmrEventLogDAO();
            eeldao.Insert(eventLog);
            
            conn.commit();
        }
        catch (Exception ex)
        {
            // Undo any half-done work
            conn.rollback();
            
            // Re-throw the exception for the calling code
            throw ex;
        }
        finally
        {
            conn.setAutoCommit(true);
        }
    }
    
    //**************************************************************************    
    /**
     * NULLs out the insurance value on an EMR order, logs action. If an EMR
     *  issue is supplied, it will remove the issue's data and the issue record.
     * 
     * @param order EMR order
     * @param userId User Id of individual performing action
     * @param emrIssue Optional. Deletes insurance line and issue if provided
     * @param eventLog
     * @throws java.sql.SQLException
     */
    public static void ClearInsurance(Orders order, Integer userId, EmrIssue emrIssue, EmrEventLog eventLog)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (order == null || order.getIdOrders() == null || order.getIdOrders() <= 0)
            throw new IllegalArgumentException("EMROrderBL::ClearInsurance: "
                    + "Received a NULL orderId argument!");
        if (userId == null || userId <= 0)  throw new IllegalArgumentException(
                "EMROrderBL::ClearInsurance: Received a NULL userId argument!");
        
        if (eventLog == null) throw new IllegalArgumentException(
                "EMROrderBL::ClearInsurance: Received a NULL eventLog object argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        try
        {
            conn.setAutoCommit(false);
            
            order.setInsurance(null);
            EMR.DAOS.OrderDAO odao = new EMR.DAOS.OrderDAO();
            odao.UpdateOrder(order);
            
            EmrEventLogDAO eeldao = new EmrEventLogDAO();
            eeldao.Insert(eventLog);
            
            // Clear out insurance record and issue
            if (emrIssue != null && emrIssue.getIdEmrIssues() != null && emrIssue.getIdEmrIssues() > 0)
            {
                DeleteEMRIssueAndData(emrIssue);
            }
            
            conn.commit();
        }
        catch (SQLException | NullPointerException | IllegalArgumentException ex)
        {
            // Undo any half-done work
            conn.rollback();
            
            // Re-throw the exception for the calling code
            throw ex;
        }
        finally
        {
            conn.setAutoCommit(true);
        }
        
    }
    
    //**************************************************************************    
    /**
     * Returns the database identifier for the emr interface, given the unique
     *  database identifier of the EMR order.
     *  
     * @param emrOrderId emrorders.orders.idorders column
     * @return Integer EMR Xref identifier, NULL if not found
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static Integer GetEMRXRefIdForEMROrderId(Integer emrOrderId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (emrOrderId == null) throw new IllegalArgumentException(
                "EMROrerBL::GetEMRXRefForEMROrder: Received a NULL emrOrderId argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT c.emrInterface "
                + " FROM clients c "
                + " INNER JOIN emrorders.orders eo ON c.idclients = eo.clientId"
                + " WHERE eo.idOrders = ?";

        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1,emrOrderId);
        ResultSet rs = pStmt.executeQuery();
        Integer emrXrefId = null;
        if (rs.next())
        {
            emrXrefId = rs.getInt(1);
        }
        else
        {
            String errorMessage = "EmrOrderBL::GetEMRXRefIdForEMROrderId: Could not retrieve an emr xref for emr orderId";
            System.out.println(errorMessage);
            System.out.println(sql);
            SysLogDAO.Add(1, errorMessage, sql);
        }
        pStmt.close();
        
        return emrXrefId;
    }
    
    //**************************************************************************    
    /**
     * Removes the emrIssue row and any associated data.
     * e.g. if the issue was an unrecognized insurance and the user selected
     *  an existing insurance from the LIS, this would remove the insurance
     *  data as it came in from the EMR vendor
     * @param emrIssueId 
     * @throws java.sql.SQLException 
     */
    private void DeleteEMRIssueAndData(Integer emrIssueId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (emrIssueId == null) throw new IllegalArgumentException(
                "EMROrerBL::DeleteEMRIssueAndData: Received a NULL emrIssueId argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        EmrIssueDAO eidao = new EmrIssueDAO();
        EmrIssue issue = eidao.GetById(emrIssueId);
        DeleteEMRIssueAndData(issue);
    }
    
    //**************************************************************************    
    private static void DeleteEMRIssueAndData(EmrIssue issue)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (issue == null) throw new IllegalArgumentException(
                "EMROrerBL::DeleteEMRIssueAndData: Received a NULL EmrIssue object argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        EmrIssueType issueType = EmrIssueTypeDAO.getById(issue.getIssueTypeId());
        
        // Insurance issue
        if (issueType.getName().equals(EmrIssueTypeDAO.IssueTypeEnum.INSURANCE.getDatabaseName()))
        {
            
            // Remove the data for the insurance row if present
            Integer emrInsuranceId = issue.getDatabaseIdentifier();
            if (emrInsuranceId != null && emrInsuranceId > 0)
            {
                InsuranceDAO.Delete(IDO.DatabaseSchema.EMRORDERS, emrInsuranceId);
            }
            
            // Remove the issue row
            EmrIssueDAO.Delete(issue.getIdEmrIssues());
            
        }

        // TODO: when other issue types are added, provide code to remove the data
        
    }
    
    /**
     * Returns the part of the order code after the client number.
     * 
     * ex. An EMR Order Id as found in the emrorders.orders.accession column
     *  might look like "EMR-(2313)8717361". This returns "8717361".
     * 
     * @param emrOrderId 
     * @return  
     */
    public static String ExtractEmrAccession(String emrOrderId)
            
    {
        if (emrOrderId == null) return null;
        if (emrOrderId.isEmpty()) return "";
        return emrOrderId.substring(emrOrderId.indexOf(')') + 1, emrOrderId.length());
    }
    
    //**************************************************************************
    //**************************************************************************
    
    
    /**
     * Returns the accession representing the most recently approved EMR Order
     * @return 
     * @throws java.sql.SQLException 
     */
    public static String getMostRecentlyApprovedAccession()
            throws SQLException, NullPointerException
    {
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        // TODO: fix the DatabaseProperties object to include emrorders schema
        //  and use the variable instead of a static hardcoded schema
        String sql = "SELECT approvedAcc FROM `emrorders`.`emrApprovalLog`"
                + " WHERE approvedAcc IS NOT NULL AND LENGTH(approvedAcc) > 0"
                + " ORDER BY dateApproved DESC LIMIT 1";
        
        PreparedStatement pStmt = conn.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        String accession = null;
        if (rs.next())
        {
            accession = rs.getString("approvedAcc");
        }
        
        if (accession == null) throw new NullPointerException(
                "EMROrderBL::Could not find an approved accession");
        
        return accession;
    }
    
    //**************************************************************************
    
    /**
     * Returns any files associated with an emrorders.orders.idorders identifier
     * @param idOrders
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<EmrFile> getFilesForEMRIdOrders(Integer idOrders)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (idOrders == null || idOrders <= 0)
        {
            throw new IllegalArgumentException("EMROrderBL:getFilesForEMRIdOrders:"
                    + " Received a [NULL] or invalid idOrders argument");
        }
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT ef.* FROM orders o"
                + "INNER JOIN emrFileLog efl on o.idorders = efl.idOrders"
                + "INNER JOIN emrFiles ef on efl.id = ef.idEmrFileLog"
                + "WHERE o.idOrders = ?";
        
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, idOrders);
        
        ResultSet rs = pStmt.executeQuery();

        List<EmrFile> emrFiles = new LinkedList<>();
        while (rs.next())
        {
            emrFiles.add(EMR.DAOS.EmrFileDAO.ObjectFromResultSet(rs));
        }
        
        return emrFiles;
    }
    
    //**************************************************************************
    
    /**
     * Returns whether the provided AR is unique in both the patient and
     *  subscriber tables on the main schema.
     * 
     * If includeInactive is true, inactive patient and subscriber records
     *  will be taken into consideration 
     * 
     * A [NULL] or empty AR will throw an exception
     * 
     * @param arNo
     * @param includeInactive
     * @return boolean is the AR unique?
     * @throws java.sql.SQLException
     */
    public static boolean isUniqueAR(String arNo, boolean includeInactive)
            throws IllegalArgumentException, SQLException
    {
        if (arNo == null || arNo.isEmpty()) throw new IllegalArgumentException(
                "EMROrderBL::isUniqueAr: arNo argument was [NULL] or empty!");

        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql =
                "SELECT SUM(ARCount) AS foundCount FROM " +
                "(" +
                "   SELECT COUNT(*) AS ARCount FROM patients WHERE ARNo = ?" +
                    (includeInactive == false ? " AND active=b'1'" : "") +
                "   UNION" +
                "   SELECT COUNT(*) AS ARCount FROM subscriber WHERE ARNo = ?" +
                    (includeInactive == false ? " AND active=b'1'" : "") +
                ") totalArCount;";
        
        boolean isUnique = true;
        
        try (PreparedStatement pStmt = conn.prepareStatement(sql))
        {
            pStmt.setString(1, String.valueOf(arNo));
            pStmt.setString(2, String.valueOf(arNo));
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                Integer count = SQLUtil.getInteger(rs, "foundCount");
                if (count == null) throw new Exception();
                isUnique = (count.equals(0));
            }
        }
        catch (Exception ex)
        {
            String errorMessage = "EMROrderBL::isUniqueAR: Could not determine whether"
                    + " ARNo " + arNo + " was unique to both patient/subscriber tables";
            errorMessage = ExceptionUtil.getMessage(errorMessage, ex);
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }

        return isUnique;
    }
    
    //**************************************************************************
    
    
    /**
     * Return an AR number that is not currently being used by a patient or a subscriber
     * 
     * If includeInactive is true it will not return an AR even if there is an
     *  inactive patient/subscriber using it.
     * 
     * 
     * @param lowRange 
     * @param highRange
     * @param includeInactive If true, even deactivated records will be considered
     *              when checking if a randomly generated AR is unique
     * @param maxAttempts How many times to try before the method throws an exception
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Integer generateNewAR(
            int lowRange, int highRange, boolean includeInactive, int maxAttempts)
            throws IllegalArgumentException, SQLException
    {
        if (lowRange < 0 || highRange <= 0) throw new IllegalArgumentException(
                "EMROrderBL::generateNewAR: input arguments were invalid -"
                        + " lowRange:" + lowRange + " highRange:" + highRange);
        if (lowRange >= highRange) throw new IllegalArgumentException(
                "EMROrderBL::generateNewAR: low range argument was >= high range argument -"
                        + " lowRange:" + lowRange + " highRange:" + highRange);
        
        if (maxAttempts < 1) throw new IllegalArgumentException(
            "EMROrderBL::generateNewAr: maxAttempts parameter was " + maxAttempts);
        
        Integer newAr = null;
        
        int attemptCount = 1;
        
        do
        {
            newAr = Utility.RandomsGenerator.getRandomNumberBetween(lowRange, highRange);
            newAr = (isUniqueAR(newAr.toString(),includeInactive) ? newAr : null);

            attemptCount +=1;

        }
        while (newAr == null && attemptCount <= maxAttempts);

        if (attemptCount >= maxAttempts)
        {
            String errorMessage = "EMROrderBL::Exceeded max number of attempts"
                    + " to get an AR number unique to both patients and"
                    + " subscriber tables. Provided input range was"
                    + " lowRange:" + lowRange + " highRange:" + highRange
                    + ". Tried " + maxAttempts + " times.";
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }

        return newAr;
    }
    
    /**
     * Returns a list of EmrFileLog entries that represent EMR files that 
     *  encountered an error while being processed. This is usually a data
     *  issue, e.g. a date of birth was not supplied with the order. It is
     *  denoted by an emrorders.emrFileLog row that has a [NULL] idOrders value.
     * 
     * Once a user acknowledges an error, it is no longer presented to them in
     *  the errors tab of EMR order approval.
     * 
     * If an emrInterfaceId is not passed in ( it is [NULL] ), the method will
     *  return all unacknowledged error files.
     * 
     * @param con
     * @param emrInterfaceId
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<EmrFileLog> getUnacknowledgedErrorFiles(
            Connection con, Integer emrInterfaceId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("EMROrderBL::getErrorFiles:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        String sql = "SELECT * FROM emrorders.emrFileLog WHERE idOrders IS NULL AND errorAcknowledged = 0";
        if (emrInterfaceId != null && emrInterfaceId > 0)
        {
            sql += " AND emrInterface = " + emrInterfaceId.toString();
        }
        
        List<EmrFileLog> fileLogs = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                fileLogs.add(EmrFileLogDAO.ObjectFromResultSet(rs));    
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = ExceptionUtil.getMessage(
                    "EMROrderBL::getUnacknowledgedErrorFiles:"
                            + " Encountered an error retrieving log rows", ex);            
            System.out.println(errorMessage);
            throw new SQLException(ex);
        }
        return fileLogs;
    }
}
