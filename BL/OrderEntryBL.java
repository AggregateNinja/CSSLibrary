package BL;

import DAOS.AdvancedResultDAO;
import DAOS.DiagnosisCodeDAO;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DOS.AdvancedResults;
import DOS.DiagnosisCodes;
import DOS.Results;
import DOS.Tests;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Utility.Pair;
import Utility.SQLUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @date: Sep 3, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: BL
 * @file name: OrderEntryBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class OrderEntryBL
{
    public enum OrderStages
    {
        Unknown(-1, "Processing"),
        WebOrder(0, "Pending Receipting"),
        LISOrder(1, "In LIS"),
        BillOrder(77, "Sent To Billing");
        ;
        
        int stage;
        String text;
        
        OrderStages(int s, String t)
        {
            this.stage = s;
            this.text = t;
        }
        
        @Override
        public String toString()
        {
            return text;
        }
        
        public int toInt()
        {
            return stage;
        }
        
        public static OrderStages fromString(String text)
        {
            OrderStages[] values = OrderStages.values();
            for(OrderStages v : values)
            {
                if(v.toString().equals(text))
                    return v;
            }
            
            return Unknown;
        }
        
        public static OrderStages fromInt(int stage)
        {
            OrderStages[] values = OrderStages.values();
            for(OrderStages v : values)
            {
                if(v.toInt() == stage)
                    return v;
            }
            
            return Unknown;
        }
    }
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public OrderEntryBL()
    {

    }

    public boolean RemoveOrder(Integer orderID, Integer UserID, String Accession, String comment)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        System.out.println("Removing Order " + orderID + " Accession " + Accession);
        final String delResultsStr = "DELETE FROM results WHERE orderId = ?";
        final String delPrescriptionsStr = "DELETE FROM prescriptions WHERE orderId = ?";
        final String delOrdComStr = "DELETE FROM orderComment WHERE orderId = ?";
        final String delDiagsStr = "DELETE FROM orderDiagnosisLookup WHERE idOrders = ?";
        final String delRequisitionStr = "DELETE FROM requisitions WHERE idOrders = ?";
        final String delGeneticReportStr = "DELETE FROM GeneticReport WHERE idorders = ?";
        final String delPendingCallsStr = "DELETE FROM pendingCalls WHERE idOrders = ?";
        final String delBillTransLogStr = "DELETE FROM billTransLog WHERE idOrders = ?";
        final String delTransLogStr = "DELETE FROM transLog WHERE idOrders = ?";
        final String delOrderStr = "DELETE FROM orders WHERE idOrders = ?";
        final String updateLogEntriesStr = "UPDATE orderEntryLog SET idOrders = NULL WHERE idOrders = ?";
        final String logRemoveStr
                = "INSERT INTO `css`.`orderEntryLog` ("
                + "`createdDate`, "
                + "`idUser`, "
                + "`action`, "
                + "`description`, "
                + "`preValue`, "
                + "`newValue`) "
                + "VALUES "
                + "(CURRENT_TIMESTAMP, ?, 'Removed', ?, ?, ?);";
        PreparedStatement delResults = null;
        PreparedStatement delPrescriptions = null;
        PreparedStatement delOrdCom = null;
        PreparedStatement delDiags = null;
        PreparedStatement delRequisition = null;
        PreparedStatement delGeneticReport = null;
        PreparedStatement delPendingCalls = null;
        PreparedStatement delBillTransLog = null;
        PreparedStatement delTransLog = null;
        PreparedStatement delOrder = null;
        PreparedStatement updateLogEntries = null;
        PreparedStatement logRemove = null;
        try
        {
            // Doing this Transaction style, so if there is a failure,
            // It won't completely screw up the order.
            con.setAutoCommit(false);
        }
        catch (SQLException ex)
        {
            System.out.println("AutoCommit could not be truned off for the connection. Aborting.");
            return false;
        }

        boolean success = false;
        int ret = 0;
        try
        {
            

            delResults = con.prepareStatement(delResultsStr);
            delPrescriptions = con.prepareStatement(delPrescriptionsStr);
            delOrdCom = con.prepareStatement(delOrdComStr);
            delDiags = con.prepareStatement(delDiagsStr);
            delRequisition = con.prepareStatement(delRequisitionStr);
            delGeneticReport = con.prepareStatement(delGeneticReportStr);
            delPendingCalls = con.prepareStatement(delPendingCallsStr);
            delBillTransLog = con.prepareStatement(delBillTransLogStr);
            delTransLog = con.prepareStatement(delTransLogStr);
            delOrder = con.prepareStatement(delOrderStr);
            updateLogEntries = con.prepareStatement(updateLogEntriesStr);
            logRemove = con.prepareStatement(logRemoveStr);

            delResults.setInt(1, orderID);
            ret = delResults.executeUpdate();
            System.out.println(" - " + ret + " Results removed.");

            delPrescriptions.setInt(1, orderID);
            ret = delPrescriptions.executeUpdate();
            System.out.println(" - " + ret + " Prescriptions removed.");

            delOrdCom.setInt(1, orderID);
            ret = delOrdCom.executeUpdate();
            System.out.println(" - " + ret + " OrderComments removed.");

            delDiags.setInt(1, orderID);
            ret = delDiags.executeUpdate();
            System.out.println(" - " + ret + " DiagnosisCodes removed.");
            
            delRequisition.setInt(1, orderID);
            ret = delRequisition.executeUpdate();
            System.out.println(" - " + ret + " Requisitions removed.");

            delGeneticReport.setInt(1, orderID);
            ret = delGeneticReport.executeUpdate();
            System.out.println(" - " + ret + " GeneticReports removed.");

            delPendingCalls.setInt(1, orderID);
            ret = delPendingCalls.executeUpdate();
            System.out.println(" - " + ret + " PendingCalls removed.");

            delBillTransLog.setInt(1, orderID);
            ret = delBillTransLog.executeUpdate();
            System.out.println(" - " + ret + " BillTransLogs removed.");

            delTransLog.setInt(1, orderID);
            ret = delTransLog.executeUpdate();
            System.out.println(" - " + ret + " TransLogs removed.");

            delOrder.setInt(1, orderID);
            ret = delOrder.executeUpdate();
            System.out.println(" - " + ret + " Orders removed.");

            updateLogEntries.setInt(1, orderID);
            ret = updateLogEntries.executeUpdate();
            System.out.println(" - " + ret + " Log Entries updated.");

            logRemove.setInt(1, UserID);
            SQLUtil.SafeSetString(logRemove, 2, comment);
            SQLUtil.SafeSetString(logRemove, 3, Accession);
            SQLUtil.SafeSetString(logRemove, 4, Accession);
            ret = logRemove.executeUpdate();
            System.out.println(" - 'Removed' Log Entry added.");

            con.commit();
            
            success = true;

        }
        catch (Exception ex)
        {
            System.out.println("Failure! " + ex.getMessage());
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            System.out.println("=========================================================================");
            for (StackTraceElement stack : stackTrace)
            {
                System.out.println(stack.toString());
            }
            System.out.println("=========================================================================");
            if (con != null)
            {
                try
                {
                    System.err.print("Rolling Back Changes.");
                    con.rollback();
                }
                catch (SQLException excep)
                {
                    System.out.println("Failure On Rollback! " + excep.getMessage());
                }
            }
        }
        finally
        {
            CloseConnection(delResults, "delResults");
            CloseConnection(delPrescriptions, "delPrescriptions");
            CloseConnection(delOrdCom, "delOrdCom");
            CloseConnection(delDiags, "delDiags");
            CloseConnection(delRequisition, "delRequisition");
            CloseConnection(delGeneticReport, "delGeneticReport");
            CloseConnection(delPendingCalls, "delPendingCalls");
            CloseConnection(delBillTransLog, "delBillTransLog");
            CloseConnection(delTransLog, "delTransLog");
            CloseConnection(delOrder, "delOrder");
            CloseConnection(updateLogEntries, "updateLogEntries");
            CloseConnection(logRemove, "logRemove");

            try
            {
                con.setAutoCommit(true);
            }
            catch (SQLException ex)
            {
                System.err.print("Exception turning AutoCommit back on: " + ex.getMessage());
                System.err.print("Connection nullified.");
                con = null;
            }
        }

        return success;
    }

    private void CloseConnection(PreparedStatement pStmt, String stmtName)
    {
        if (pStmt != null)
        {
            try
            {
                pStmt.close();
            }
            catch (SQLException ex)
            {
                System.err.print("Exception closing " + stmtName + ": " + ex.getMessage());
            }
        }
    }
    
    public Integer GetReportedCount(Integer orderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return Integer.MAX_VALUE;
        }
        
        try 
        {
            Integer out = 0;
            Statement stmt = con.createStatement();
            String query =  "SELECT COUNT(*) AS 'resCount' " +
                            "FROM results r, " +
                            "( " +
                            "	SELECT `value` FROM preferences " +
                            "	WHERE `key` = 'POCTest' " +
                            ") pref " +
                            "WHERE r.orderid = " + orderID + " " +
                            "AND r.testid != pref.value " +
                            "AND IF(r.panelid IS NOT NULL, r.panelid != pref.value, true) " +
                            "AND r.dateReported IS NOT NULL;";
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                out = rs.getInt("resCount");
            }
            
            rs.close();
            stmt.close();
            return out;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return Integer.MAX_VALUE;
        }
    }
    
    public String GetOrderStatus(Integer orderID)
    {
        String out = "New Order";
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return "Connection Failed";
        }
        
        if(orderID == null || orderID.intValue() <= 0)
            return out;
        
        try 
        {
            Statement stmt = con.createStatement();
            String query
                    = "SELECT  "
                    + "o.stage AS 'Stage', "
                    + "IFNULL(resTotal.count, 0) AS 'ResCount', "
                    + "IFNULL(rptTotal.count, 0) AS 'RptCount', "
                    + "IFNULL(apprTotal.count, 0) AS 'AprCount', "
                    + "IFNULL(ptTotal.count, 0) AS 'PTCount' "
                    + "FROM orders o "
                    + "LEFT JOIN "
                    + "( "
                    + "	SELECT orderid, COUNT(*) AS 'count' "
                    + "	FROM results r "
                    + " LEFT JOIN tests t ON r.testid = idtests"
                    + "	WHERE orderid = " + orderID + " "
                    + " AND t.testtype != 0"
                    + ") resTotal "
                    + "ON o.idOrders = resTotal.orderid "
                    + "LEFT JOIN "
                    + "( "
                    + "	SELECT orderid, COUNT(*) AS 'count' "
                    + "	FROM results r "
                    + " LEFT JOIN tests t ON r.testid = idtests"
                    + "	WHERE orderid = " + orderID + " "
                    + " AND t.testtype != 0"
                    + "	AND dateReported IS NOT NULL "
                    + ") rptTotal "
                    + "ON o.idOrders = rptTotal.orderid "
                    + "LEFT JOIN "
                    + "( "
                    + "	SELECT orderid, COUNT(*) AS 'count' "
                    + "	FROM results r "
                    + " LEFT JOIN tests t ON r.testid = idtests"
                    + "	WHERE orderid = " + orderID + " "
                    + " AND t.testtype != 0"
                    + "	AND approvedDate IS NOT NULL "
                    + ") apprTotal "
                    + "ON o.idOrders = apprTotal.orderid "
                    + "LEFT JOIN "
                    + "( "
                    + "	SELECT orderid, COUNT(*) AS 'count' "
                    + "	FROM results r "
                    + " LEFT JOIN tests t ON r.testid = idtests"
                    + "	WHERE orderid = " + orderID + " "
                    + " AND t.testtype != 0"
                    + "	AND printAndTransmitted = true "
                    + ") ptTotal "
                    + "ON o.idOrders = ptTotal.orderid "
                    + "WHERE idOrders = " + orderID;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                OrderStages stage = OrderStages.fromInt(rs.getInt("Stage"));
                int results = rs.getInt("ResCount");
                int reported = rs.getInt("RptCount");
                int approved = rs.getInt("AprCount");
                int prntrn = rs.getInt("PTCount");
                
                String resText = "";
                if(reported < results)
                {
                    resText = "Pending";
                }
                else
                {
                    resText = "Reported";
                    if(approved >= results)
                    {
                        resText = "Approved";
                        
                        if(prntrn >= results)
                        {
                            resText = "Finalized";
                        }
                    }
                }
                
                out = stage.toString() + ", " + resText;
            }
            
            rs.close();
            stmt.close();
            return out;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return "Query Failed";
        }
    }
    
    /**
     * Returns an order's result rows where the panel Id equals
     *  the test Id of the supplied result row (i.e. the subtests)
     * 
     * Only gets one level of subtests! If a row for a battery is supplied,
     *  only single tests and the panel headers subtests will be returned.
     * 
     * @param resultId
     * @return 
     */
    public ArrayList<Results> GetImmediateSubtestResults(int resultId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Results> results = new ArrayList<>();
        
        // First get the result row
        ResultDAO rdao = new ResultDAO();
        Results result = rdao.GetResultByID(resultId);
        if (result == null || result.getTestId() == 0) return null;
        
        String sql = "SELECT"
                    + " r.idResults"
                    + " FROM results r"
                    + " WHERE r.panelId = ?"
                    + " AND r.orderId = ?";
        try
        {
            PreparedStatement pStmt = con.prepareCall(sql);
            pStmt.setInt(1, result.getTestId());
            pStmt.setInt(2, result.getOrderId());
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                results.add(rdao.GetResultByID(rs.getInt("idResults")));
            }
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("OrderEntryBL::GetImmediateSubtestResults:"
             + " Unable to retrieve subtests for result id: " + resultId);
            return null;
        }        
    }
    
    /**
     * Returns an order's result rows where the panel Id equals
     *  the test Id of the supplied result row (i.e. the subtests)
     * 
     * Only gets one level of subtests! If a row for a battery is supplied,
     *  only single tests and the panel headers subtests will be returned.
     * 
     * @param resultId
     * @return 
     */
    public ArrayList<AdvancedResults> GetImmediateAdvancedSubtestResults(int advancedResultId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<AdvancedResults> advancedResults = new ArrayList<>();

        try
        {        
            // First get the result row
            AdvancedResultDAO ardao = new AdvancedResultDAO();        
            AdvancedResults result = ardao.GetAdvancedResults(advancedResultId);
            if (result == null || result.getTestId() == 0) return null;

            String sql = "SELECT"
                        + " r.idAdvancedResults"
                        + " FROM advancedResults r"
                        + " WHERE r.panelId = ?"
                        + " AND r.idAdvancedOrders = ?";
            
            PreparedStatement pStmt = con.prepareCall(sql);
            pStmt.setInt(1, result.getTestId());
            pStmt.setInt(2, result.getIdAdvancedOrder());
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                advancedResults.add(ardao.GetAdvancedResults(rs.getInt("idAdvancedResults")));
            }
            
            return advancedResults;
        }
        catch (SQLException ex)
        {
            System.out.println("OrderEntryBL::GetImmediateAdvancedSubtestResults:"
             + " Unable to retrieve subtests for advanced result id: " + advancedResultId);
            return null;
        }        
    }    
    
    /**
     * Returns an order's result rows where the panel Id equals
     *  the test Id of the supplied result row (i.e. the subtests)
     * 
     * Only gets one level of subtests! If a row for a battery is supplied,
     *  only single tests and the panel headers subtests will be returned.
     * 
     * @param resultId
     * @return 
     */
    public ArrayList<Results> GetImmediateWebSubtestResults(int webResultId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Results> results = new ArrayList<>();
        
        // First get the result row
        Web.DAOS.ResultDAO wrdao = new Web.DAOS.ResultDAO();
        
        Results result = wrdao.GetResultByID(webResultId);
        if (result == null || result.getTestId() == 0) return null;
        
        String sql = "SELECT"
                    + " r.idResults"
                    + " FROM cssweb.results r"
                    + " WHERE r.panelId = ?"
                    + " AND r.orderId = ?";
        try
        {
            PreparedStatement pStmt = con.prepareCall(sql);
            pStmt.setInt(1, result.getTestId());
            pStmt.setInt(2, result.getOrderId());
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                results.add(wrdao.GetResultByID(rs.getInt("idResults")));
            }
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("OrderEntryBL::GetImmediateSubtestResults:"
             + " Unable to retrieve subtests for web result id: " + webResultId);
            return null;
        }        
    }    
    
    /**
     * Gets the subtests as defined by the result lines for an order, NOT the
     *   not the tests/panels tables.
     * Used when populating the test tree from an existing order.
     *  (note: this should only be used to bring up the existing data
     *     for an order, not for adding tests to an order).
     * @param orderId
     * @param testId The parent testId to get subtests for
     * @return An Arraylist of Tests objects; null on error
     */
    public Tests[] GetSubtestsFromResultRows(int orderId, int testId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Tests> results = new ArrayList<>();
        TestDAO testdao = new TestDAO();
        
        String sql = "SELECT testId FROM results"
                + " WHERE panelId = " + testId
                + " AND orderId = " + orderId;

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Tests test = testdao.GetTestByID(rs.getInt("testId"));
                results.add(test);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to get subpanel result rows for " +
                    "test " + testId + " in orderId " + orderId + " Exception " + ex.getMessage());
            return null;
        }
        
        return results.toArray(new Tests[results.size()]);        
    }
    
    /**
     * Gets the subtests as defined by the Advanced result lines for an order, NOT the
     *   not the tests/panels tables.
     * Used when populating the test tree from an existing advanced order.
     *  (note: this should only be used to bring up the existing data
     *     for an order, not for adding tests to an order).
     * @param advancedOrderId
     * @param testId The parent testId to get subtests for
     * @return An Arraylist of Tests objects; null on error
     */
    public Tests[] GetSubtestsFromAdvancedResultRows(int advancedOrderId, int testId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Tests> results = new ArrayList<>();
        TestDAO testdao = new TestDAO();
        
        String sql = "SELECT testId FROM advancedResults"
                + " WHERE panelId = " + testId
                + " AND idAdvancedOrder = " + advancedOrderId;

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Tests test = testdao.GetTestByID(rs.getInt("testId"));
                results.add(test);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to get subpanel Advanced result rows for " +
                    "test " + testId + " in advancedOrderId " + advancedOrderId + " Exception " + ex.getMessage());
            return null;
        }
        
        return results.toArray(new Tests[results.size()]);        
    }
    
    
    public Tests[] GetSubtestsFromWebResultRows(int orderId, int testId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Tests> results = new ArrayList<>();
        TestDAO testdao = new TestDAO();
        
        String sql = "SELECT testId FROM `cssweb`.`results`"
                + " WHERE panelId = " + testId
                + " AND orderId = " + orderId;

        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Tests test = testdao.GetTestByID(rs.getInt("testId"));
                results.add(test);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to get subpanel result rows for " +
                    "test " + testId + " in orderId " + orderId + " Exception " + ex.getMessage());
            return null;
        }
        
        return results.toArray(new Tests[results.size()]);
                
    }
    
    /**
     * Purges the order by backing up order and result rows to the purgedOrders
     *  and purgedResults table, then calling delete on the provided row.
     * 
     *  Note: only the 'orders' table has delete called against it; any other
     *    tables need to have a foreign key to idOrders and be marked
     *    cascade on delete to be affected.
     * 
     *  Returns a boolean representing whether the operation was successful.
     *  On any error, the entire transaction is rolled back.
     * 
     * @param idOrders The order Id to purge
     * @param userId The user Id performing the operation
     * @param purgeComment Optional user-supplied comment
     * @return Boolean - whether the process completed successfully
     */
    public boolean PurgeOrderByOrderId(int idOrders, int userId, String purgeComment)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            boolean hadError = true;
            String sql = "CALL PurgeOrder(?,?,?,?);";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, idOrders);
            callable.setInt(2, userId);
            callable.setString(3, purgeComment);
            callable.registerOutParameter(4, java.sql.Types.BIT);
            
            ResultSet rs = callable.executeQuery();
            if (rs.next())
            {
                hadError = rs.getBoolean(1);
            }
            
            callable.close();
            rs.close();
            
            return hadError;
        }
        catch (SQLException ex)
        {
            System.out.println("SQL error while attempting to purge order " + idOrders);
            return false;
        }
    }
    
    /**
     * Given an order Id, determines whether any results 
     *  have been print and transmitted
     * @param idOrders
     * @return 
     */
    public Boolean OrderHasCompletedWork(int idOrders)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            String sql = "SELECT COUNT(*)"
                + " FROM results"
                + " WHERE (printAndTransmitted = 1 OR pAndTDate IS NOT NULL)"
                + " AND orderid = " + idOrders;
            int count = 0;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next())
            {
                count = rs.getInt(1);
            }
            
            return count > 0;
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to determine whether order has printed and transmitted work " +
                    "orderId " + idOrders + " Exception " + ex.getMessage());
            return null;
        }
    }
    
    /**
     * Gets the most recent order for the provided patient. If useSpecimenDate
     *  is false, it will use the orderDate.
     * @param patientId
     * @param useSpecimenDate If false, uses orderDate
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public Date GetLastDateOfService(Integer patientId, boolean useSpecimenDate) throws SQLException, IllegalArgumentException
    {
        if (patientId == null || patientId == 0)
        {
            throw new IllegalArgumentException("OrderEntryBL::GetLastDateOfService: Received a null/zero patientId");
        }
        
        if (con.isValid(3) == false) con = CheckDBConnection.Check(dbs, con);
        
        String dateToUse = (useSpecimenDate? "specimenDate" : "orderDate");
        
        Date lastDateOfService = null;
        
        String sql = "SELECT MAX(" + dateToUse + ") FROM orders where patientId = ? AND " + dateToUse + " < NOW()";
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.setInt(1, patientId);
        
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            lastDateOfService = rs.getDate(1);
        }
        return lastDateOfService;
    }
    
    public Boolean isInBillTransLog(int orderId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            String sql = "SELECT COUNT(*)"
                + " FROM billTransLog"
                + " WHERE idOrders = ?";
            
            int count = 0;
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, orderId);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                count = rs.getInt(1);
            }
            
            return count > 0;
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to determine whether order has been sent to billing " +
                    "orderId " + orderId + " Exception " + ex.getMessage());
            return null;
        }
    }
    
    public static List<DiagnosisCodes> getDiagnosisCodesByOrderId(Integer orderId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException("OrderEntryBL::getDiagnosisCodesByOrderId:"
                    + " Received a [NULL] or invalid orderId argument.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT dc.* FROM orderDiagnosisLookup odl"
                + " INNER JOIN diagnosisCodes dc ON odl.idDiagnosisCodes = dc.idDiagnosisCodes"
                + " WHERE odl.idOrders = " + orderId.toString();
        
        List<DiagnosisCodes> diagnosisCodes = new ArrayList<>();
        
        try
        {
            PreparedStatement pStmt = con.prepareCall(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                diagnosisCodes.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
            }
        }
        catch (SQLException ex)
        {
            throw new SQLException("OrderEntryBL::getDiagnosisCodesByOrderId:"
                    + " Error running: " + sql + ". " + ex.getMessage());
        }
        
        return diagnosisCodes;
    }
    
    /**
     * Clears out the current unbillable reasons for the provided order and
     *  writes the input. If the reasons array is NULL or empty (as it would
     *  be for a billable order), it will just clear out any existing reasons.
     * 
     * Uses the DatabaseSingleton connection. If this call is being done
     *  from an RMI using a transaction, use the other method that takes the
     *  connection parameter and supply an isolated connetion.
     * 
     * @param orderId
     * @param reasons
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public static void writeOrderUnbillableReasons(Integer orderId, List<Pair<String, String>> reasons)
            throws IllegalArgumentException, SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        writeOrderUnbillableReasons(con, orderId, reasons);
    }
    
    /**
     * Clears out the current unbillable reasons for the provided order and
     *  writes the input. If the reasons array is NULL or empty (as it would
     *  be for a billable order), it will just clear out any existing reasons.
     * 
     * @param orderId
     * @param reasons List of reasons to write
     * @param con
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public static void writeOrderUnbillableReasons(Connection con, Integer orderId, List<Pair<String, String>> reasons)
            throws IllegalArgumentException, SQLException
    {
        
        if (orderId == null)
        {
            throw new IllegalArgumentException(
                    "OrderEntryBL::Received a [NULL] orderId Integer argument");
        }
        
        if (con == null || con.isValid(2) == false)
        {
            throw new IllegalArgumentException("OrderEntryBL::writeOrderUnbillableReason:"
                    + " Received a [NULL] or invalid Connection object");
        }
        String errorMessage = "";
        int attempts = 1;
        boolean success = false;
        while (success == false && attempts <= 3)
        {
            try
            {
                System.out.println("Attempt " + attempts);
                // Delete any existing messages for this order
                String sql = "DELETE FROM " + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".orderUnbillableReasons WHERE orderId = ?";
                PreparedStatement pStmt = con.prepareStatement(sql);
                pStmt.setInt(1, orderId);
                System.out.println(pStmt.toString());
                pStmt.execute();
                
                // Insert any that were provided
                if (reasons != null)
                {
                    for (Pair<String, String> reason : reasons)
                    {
                        if (reason == null)
                        {
                            System.out.println("Can't write order unbillable reason; was given an empty pair of strings (type-->reason)");
                            continue;
                        }
                        
                        sql = "INSERT INTO " + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`orderUnbillableReasons` (orderId, type, reason) VALUES (?,?,?)";
                        pStmt = con.prepareStatement(sql);
                        pStmt.setInt(1, orderId);
                        pStmt.setString(2, reason.getFirst());
                        pStmt.setString(3, reason.getSecond());
                        System.out.println(pStmt.toString());
                        pStmt.execute();
                    }
                }
                success = true;
            }
            catch (Exception ex)
            {
                if (ex.getMessage() != null && ex.getMessage().contains("exist"))
                {
                    System.out.println("Creating css.orderUnbillableReasons table");
                    
                    String sql = "CREATE TABLE `css`.`orderUnbillableReasons` ("
                        + " `orderId` INT UNSIGNED NOT NULL,"
                        + " `type` VARCHAR(128) NOT NULL,"
                        + " `reason` VARCHAR(512) NOT NULL,"
                        + " `created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
                        + " INDEX `FK_orderUnbillableReasons_orderId_idx` (`orderId` ASC),"
                        + " CONSTRAINT `FK_orderUnbillableReasons_orderId`"
                        + "   FOREIGN KEY (`orderId`)"
                        + "   REFERENCES `css`.`orders` (`idOrders`)"
                        + "   ON DELETE CASCADE"
                        + "   ON UPDATE NO ACTION)"
                        + " ENGINE = InnoDB"
                        + " DEFAULT CHARACTER SET = utf8"
                        + " COLLATE = utf8_unicode_ci;";
                    
                    Statement stmt = con.createStatement();
                    stmt.execute(sql);
                }
                else
                {
                    errorMessage += " " + "OrderEntryBL::writeOrderUnbillableReasons:"
                            + " Encountered an error accessing orderUnbillableReasons table:"
                            + " " + ex.getMessage() == null ? "[NULL] exception message" : ex.getMessage();
                }
            }
            finally
            {
                attempts +=1;
            }
        }
        
        if (success == false)
        {
            throw new SQLException("OrderEntryBL::writeOrderUnbillableReasons: Could not process orderId " + orderId.toString() + ".. " + errorMessage);
        }
    }
    
    /**
     * Gets all, if any, unbillable reasons by orderId.
     * @param orderId
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public static List<Pair<String, String>> getUnbillableReasonsForOrder(Integer orderId)
            throws IllegalArgumentException, SQLException
    {
        List<Pair<String, String>> reasons = new ArrayList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        if (orderId == null)
        {
            throw new IllegalArgumentException(
                    "OrderEntryBL::getUnbillableReasonsForOrder: Received a [NULL] orderId Integer argument");
        }
        
        if (con == null || con.isValid(2) == false)
        {
            throw new IllegalArgumentException("OrderEntryBL::getUnbillableReasonsForOrder:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        boolean success = true;
        String errorMessage = "";
        
        String query = "SELECT * FROM " + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()
                + ".`orderUnbillableReasons` WHERE orderId = " + orderId;
        
        try 
        {
            Statement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                reasons.add(new Pair(rs.getString("type"), rs.getString("reason")));
            }
        }
        catch (SQLException e) 
        {
            success = false;
            errorMessage = e.getMessage();
        }
        
        if (!success)
            throw new SQLException("OrderEntryBL::getUnbillableReasonsForOrder:"
                + " Nested exception error is: " + errorMessage);
        
        return reasons;
    }
}
