/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 16, 2014
 */

package BL;

import DAOS.DiffLogDAO;
import DAOS.LogDAO;
import DAOS.OrderDAO;
import DAOS.PanelDAO;
import DAOS.PatientDAO;
import DAOS.ResultDAO;
import DAOS.SysLogDAO;
import DAOS.TestDAO;
import DOS.OrderEntryLog;
import DOS.Orders;
import DOS.Panels;
import DOS.Patients;
import DOS.Remarks;
import DOS.ResultDetails;
import DOS.ResultPostLog;
import DOS.Results;
import DOS.Tests;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @date:   Jul 16, 2014  3:07:51 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ResultsBL.java  (UTF-8)
 *
 * @Description: BL For Generic Result Table related jobs.
 *
 */

public class ResultsBL {

    public ResultsBL(){
        
    }
    
    public Results GetOneResult(int id){
        return null;
        
    }
    
    /**
     * Purges the provided result, any children (subtests) of that
     *  result, and any grandchildren of that result.
     * 
     * Logs actions.
     * 
     *  NOTE: Validate to ensure the result line can be purged before calling
     *   this method!
     * @param idResults
     * @param userId Gets logged
     * @throws java.sql.SQLException 
     */
    public static void PurgeResultAndChildren(int idResults, int userId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (idResults <= 0) throw new IllegalArgumentException(
                "ResultsBL::PurgeResultAndChildren: Received an idResults argument of " + idResults);
        
        if (userId <= 0) throw new IllegalArgumentException(
                "ResultsBL::PurgeResultAndChildren: Received a userId argument of " + userId);
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        ResultDAO resultdao = new ResultDAO();
        Results result = resultdao.GetResultByID(idResults);        
        
        if (result == null || result.getIdResults() == null || result.getIdResults().equals(0))
        {
            String errorMessage = "ResultsBL::PurgeResultAndChildren: "
                    + "results object retrieved for resultId " + idResults + " was NULL/empty!";
            System.out.println(errorMessage);
            SysLogDAO.Add(userId, errorMessage, "");
            throw new SQLException(errorMessage);
        }
        
        // The final list of results that will be purged
        ArrayList<Results> purgeList = new ArrayList<>();
        
        // Add the given result to the purge list
        purgeList.add(result);
        
        // Get any child tests as defined on the result rows
        ArrayList<Results> childResults = resultdao.GetSubtestResults(idResults);
        
        // Add child results and their children to the purge list
        for (Results childResult : childResults)
        {
            // Add all of the grandchild results
            purgeList.addAll(resultdao.GetSubtestResults(childResult.getIdResults()));
            
            // Add the child result
            purgeList.add(childResult);
        }
        
        Date currentDate = new Date();
        OrderEntryLog olog;// = new OrderEntryLog();
        ResultPostLog rlog;// = new ResultPostLog();        
        
        // Used for diff
        Results blankResult = new Results();
        
        // Create a rollback point
        Savepoint savePoint = null;
        try
        {
            savePoint = con.setSavepoint();
            if (savePoint == null) throw new SQLException("NULL save point returned");
        }
        catch (SQLException ex)
        {
            String errorMessage = "ResultBL::PurgeResultAndChildren: resultId " + idResults + ". Unable to set a connection savepoint for rollback. ";
            SysLogDAO.Add(userId, errorMessage, ex.getMessage());
            System.out.println(errorMessage + "  " + ex.getMessage());
            throw new SQLException(errorMessage + " " + ex.getMessage());
        }
        
        try
        {
            
            con.setAutoCommit(false);
            
            // Go through and purge all of the results
            for (Results purgeResult : purgeList)
            {
                
                // Log the data that's being removed from the result line (if any)
                DiffLogDAO.Insert("resultLog", "idresults", purgeResult.getIdResults(), purgeResult, blankResult, "", userId);
                
                ResultDAO.delete(purgeResult.getIdResults());
                
                olog = new OrderEntryLog();
                rlog = new ResultPostLog();

                olog.setIdUser(userId);
                olog.setCreatedDate(currentDate);
                olog.setAction("Test Removed");
                olog.setIdOrders(result.getOrderId());
                olog.setIdResults(purgeResult.getIdResults());
                olog.setIdTests(purgeResult.getTestId());

                rlog.setIdUser(userId);
                rlog.setCreatedDate(currentDate);
                rlog.setAction("Test Removed");
                rlog.setIdOrders(result.getOrderId());
                rlog.setIdResults(purgeResult.getIdResults());
                rlog.setIdTests(purgeResult.getTestId());

                LogDAO ldao = new LogDAO();
                ldao.InsertLog(LogDAO.LogTable.OrderEntry, olog);
                ldao.InsertLog(LogDAO.LogTable.ResultPost, rlog);
                
            }
            
            con.commit();
        }
        catch (Exception ex)
        {
            // Revert any partially processed data
            con.rollback(savePoint);
            
            String errorMessage = "ResultsBL::PurgeResultsAndChildren: resultId " + idResults;
            System.out.println(errorMessage + " " + ex.getMessage());
            SysLogDAO.Add(userId, errorMessage, ex.getMessage());
            throw new SQLException(errorMessage + " " + ex.getMessage());
        }
        finally
        {
            // Resume autocommit mode
            con.setAutoCommit(true);            
        }
    }
    
    /**
     * 
     * Uses the supplied test and returns a list of result lines that represents
     *  that test and any of its subtests.
     * @param testId The id of the test to generate result lines for
     * @param orderId The orderId to associate the result lines to (can be NULL)
     * @param createdDate When the result lines should be time-stamped
     * @return List of results
     * @throws java.sql.SQLException 
     */
    public List<Results> BuildResultsForTestId(int testId, Integer orderId, Date createdDate)
            throws IllegalArgumentException, SQLException
    {
        if (testId <= 0) throw new IllegalArgumentException("Argument test id was " + testId);
        if (createdDate == null) throw new IllegalArgumentException("Argument createdDate was null");
        
        List<Results> results = new LinkedList<>();
        
        // Main test
        Results result = new Results();
        result.setPanelId(null);
        result.setTestId(testId);
        result.setOrderId(orderId == null ? 0 : orderId);
        result.setCreated(createdDate);
        result.setOptional(false);

        results.add(result);

        // Check for subtests
        PanelDAO pdao = new PanelDAO();
        List<Panels> subtestPanels = pdao.GetOrderedPanels(testId);
        if (subtestPanels == null) subtestPanels = new LinkedList<>();
        for (Panels subtestPanel : subtestPanels)
        {
            if (subtestPanel != null && subtestPanel.getSubtestId() > 0)
            {
                result = new Results();
                result.setPanelId(testId);
                result.setTestId(subtestPanel.getSubtestId());
                result.setOrderId(orderId == null ? 0 : orderId);
                result.setCreated(createdDate);
                result.setOptional(subtestPanel.isOptional());

                results.add(result);

                // Check for sub-subtests
                List<Panels> subSubtestPanels = pdao.GetOrderedPanels(subtestPanel.getSubtestId());
                if (subSubtestPanels == null) subSubtestPanels = new LinkedList<>();
                for (Panels subSubtestPanel : subSubtestPanels)
                {
                    if (subSubtestPanel != null && subSubtestPanel.getSubtestId() > 0)
                    {
                        result = new Results();
                        result.setPanelId(subtestPanel.getSubtestId());
                        result.setTestId(subSubtestPanel.getSubtestId());
                        result.setOrderId(orderId == null ? 0 : orderId);
                        result.setCreated(createdDate);
                        result.setOptional(subSubtestPanel.isOptional());
                        results.add(result);
                    }
                }
            }
        }
        return results;        
    }
    
    /**
     * Uses the latest version of the supplied test and returns a list of result lines
     *  representing that test an any of its subtests.
     * @param testNumber The number of the test to generate result lines for
     * @param orderId The orderId to associate the result lines to (can be NULL)
     * @param createdDate When the result lines should be time-stamped
     * @return List of results
     * @throws java.sql.SQLException 
     */
    public List<Results> BuildResultsForTestNumber(int testNumber, Integer orderId, Date createdDate)
            throws IllegalArgumentException, SQLException
    {
        if (testNumber <= 0) throw new IllegalArgumentException("Argument test number was " + testNumber);
        if (createdDate == null) throw new IllegalArgumentException("Argument createdDate was null");
        List<Results> results = new LinkedList<>();
        
        try
        {
            TestDAO tdao = new TestDAO();
            Tests mainTest = tdao.GetTestByNumber(testNumber);
            if (mainTest == null || mainTest.getIdtests() == null || mainTest.getIdtests().equals(0))
            {
                throw new SQLException("Returned Tests object is NULL or empty!");
            }
            results = BuildResultsForTestId(mainTest.getIdtests(), orderId, createdDate);
        }
        catch (SQLException ex)
        {
            String orderIdStr = "[NULL]";
            if (orderId != null) orderIdStr = orderId.toString();
            // Add some context to the exception for the calling code
            throw new SQLException("ResultsBL::BuildResultsForTestNumber: testNumber=" + testNumber + " orderId=" + orderIdStr + " createdDate=" + createdDate.toString() + ". Error=" + ex.getMessage());
        }
        return results;
    }
    
    /**
     * Invalidates a panel and logs the action.
     * 
     *  If a remark is supplied, only the subtest(s) are invalidated. The panel
     *   header itself is assigned the remark
     * 
     *  If the remark is NULL, the panel is invalidated as well
     * 
     * NOTE: Does not check the result's reported or print & transmit status!
     *  Calling code should handle that validation!
     * 
     *  Remark is optional.
     * @param result 
     * @param remark 
     * @param noCharge 
     * @param optionalInternalComment 
     * @param userId 
     * @param invalidateDate 
     * @throws java.sql.SQLException 
     */
    public static void invalidatePanel(
            Results result,
            Remarks remark,
            boolean noCharge,
            String optionalInternalComment,
            int userId,
            Date invalidateDate)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults() == 0)
        {
            throw new IllegalArgumentException("ResultsBL::invalidatePanel: Results object argument is NULL or empty");
        }
        
        if (invalidateDate == null) invalidateDate = new Date();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        Savepoint savePoint = con.setSavepoint();
        try
        {
            con.setAutoCommit(false);
            
            // If there's a remark, assign it to the panel and do not
            // invalidate the result so it shows on reports
            if (remark != null
                    && remark.getIdremarks() != null
                    && remark.getIdremarks().equals(0) == false)
            {
                result.setResultRemark(remark.getIdremarks());
                result.setNoCharge(noCharge);
                ResultDAO rdao = new ResultDAO();
                boolean success = rdao.UpdateResult(result);
                if (success == false) throw new SQLException("");
                
                // Log that the subtests are being invalidated
                LogDAO ldao = new LogDAO();
                ResultPostLog rpl = new ResultPostLog();
                rpl.setIdResults(result.getIdResults());
                rpl.setIdOrders(result.getOrderId());
                rpl.setAction("Subtests Invalidated");
                rpl.setIdUser(userId);
                rpl.setCreatedDate(invalidateDate);
                if (optionalInternalComment != null && optionalInternalComment.trim().isEmpty() == false)
                {
                    rpl.setDescription(optionalInternalComment);
                }
                success = ldao.InsertLog(LogDAO.LogTable.ResultPost, rpl);
                if (success == false)
                {
                    throw new SQLException("ResultsBL::invalidateResult: "
                            + "Unable to log invalidation of resultId: " + result.getIdResults());
                }
            }
            else // Otherwise invalidate the panel header itself
            {
                invalidateResult(result, userId, invalidateDate, true, optionalInternalComment);
            }
            
            // Invalidate all subtests
            invalidateSubtestCascade(result, userId, invalidateDate);
            
        }
        catch (SQLException ex)
        {
            con.rollback(savePoint);
            
            String error = "ResultsBL::invalidatePanel: Error encountered invalidating idresult : " 
                    + result.getIdResults() + " by userId " + userId;
            
            // Log the error
            SysLogDAO.Add(userId, error, ex.getMessage());
            
            // Rethrow to caller
            throw ex;
        }
        finally
        {
            con.setAutoCommit(true);
        }
    }
    
    /**
     * Reinstates a panel and logs the action.
     * 
     * @param result 
     * @param noCharge 
     * @param optionalInternalComment 
     * @param userId 
     * @param reinstateDate 
     * @throws java.sql.SQLException 
     */
    public static void reinstatePanel(
            Results result,
            boolean noCharge,
            String optionalInternalComment,
            int userId,
            Date reinstateDate)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults() == 0)
        {
            throw new IllegalArgumentException("ResultsBL::reinstatePanel: Results object argument is NULL or empty");
        }
        
        if (reinstateDate == null) reinstateDate = new Date();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        Savepoint savePoint = con.setSavepoint();
        try
        {
            con.setAutoCommit(false);

            // Reinstate the panel
            reinstateResult(result, userId, reinstateDate, true, optionalInternalComment);

            // Reinstate all subtests
            reinstateSubtestCascade(result, userId, reinstateDate);
            
        }
        catch (SQLException ex)
        {
            con.rollback(savePoint);
            
            String error = "ResultsBL::reinstatePanel: Error encountered reinstating idresult : " 
                    + result.getIdResults() + " by userId " + userId;
            
            // Log the error
            SysLogDAO.Add(userId, error, ex.getMessage());
            
            // Rethrow to caller
            throw ex;
        }
        finally
        {
            con.setAutoCommit(true);
        }
    }    
    
    
    /**
     * Invalidates the subtest(s) of the provided result.
     *  
     *  NOTE: Does not invalidate the result that is passed in, only subtests!
     *  NOTE: Logging must be done by the calling code!
     * 
     * @param result 
     * @param userId 
     * @param invalidateDate 
     * @throws java.sql.SQLException 
     */
    public static void invalidateSubtestCascade(Results result, int userId, Date invalidateDate)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults().equals(0))
        {
            throw new IllegalArgumentException("ResultsBL::invaldiateResultCascade: Results object argument is NULL or empty");
        }
        ResultDAO rdao = new ResultDAO();
        List<Results> subtestResults = rdao.GetSubtestResults(result.getIdResults());
        for (Results subtestResult : subtestResults)
        {
            // Don't log each result invalidation
            invalidateResult(subtestResult, userId, invalidateDate, false, null);
            invalidateSubtestCascade(subtestResult, userId, invalidateDate);
        }
    }
    
    /**
     * Reinstates the subtest(s) of the provided result (un-sets invalidated data)
     *  
     *  NOTE: Does not reinstate the result that is passed in, only subtests!
     *  NOTE: Logging must be done by the calling code!
     * 
     * @param result 
     * @param userId 
     * @param invalidateDate 
     * @throws java.sql.SQLException 
     */
    public static void reinstateSubtestCascade(Results result, int userId, Date invalidateDate)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults().equals(0))
        {
            throw new IllegalArgumentException("ResultsBL::reinstateSubtestCascade: Results object argument is NULL or empty");
        }
        ResultDAO rdao = new ResultDAO();
        List<Results> subtestResults = rdao.GetSubtestResults(result.getIdResults());
        for (Results subtestResult : subtestResults)
        {
            // Don't log each result invalidation
            reinstateResult(subtestResult, userId, invalidateDate, false, null);
            reinstateSubtestCascade(subtestResult, userId, invalidateDate);
        }
    }
    
    /**
     * Removes the invalidated flags and data from the supplied result; logs
     *  the change if instructed (result post log). Will not reinstate results
     *  that are not marked invalidated
     * 
     * @param result
     * @param userId
     * @param reinstateDate
     * @param logReinstatement
     * @param optionalComment
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public static void reinstateResult(
            Results result,
            int userId,
            Date reinstateDate,
            boolean logReinstatement,
            String optionalComment)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults().equals(0))
        {
            throw new IllegalArgumentException("ResultsBL::reinstateResult: Results object argument is NULL or empty");
        }
        
        boolean isPanel = false;
        
        // If this is a panel header, remove existing remark:
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestByID(result.getTestId());
        if (test != null && test.getIdtests() != null && test.getIdtests() > 0)
        {
            if (test.getTestType() == 0)
            {
                isPanel = true;
            }
        }
        
        // Don't invalidate an invalidated result
        if (result.getIsInvalidated() == false && isPanel == false) return;
        
        result.setInvalidatedBy(null);
        result.setInvalidatedDate(null);
        result.setIsInvalidated(false);
        if (isPanel) result.setResultRemark(null);
        
        ResultDAO rdao = new ResultDAO();
        boolean success = rdao.UpdateResult(result);
        if (success == false)
        {
            throw new SQLException("ResultsBL::reinstateResult: Unable to reinstate resultId: " + result.getIdResults());
        }
        
        // Put this in the result post log
        if (logReinstatement)
        {
            LogDAO ldao = new LogDAO();
            ResultPostLog rpl = new ResultPostLog();
            rpl.setIdResults(result.getIdResults());
            rpl.setIdOrders(result.getOrderId());
            rpl.setAction("Test Reinstated");
            rpl.setIdUser(userId);
            rpl.setCreatedDate(reinstateDate);
            if (optionalComment != null && optionalComment.trim().isEmpty() == false)
            {
                rpl.setDescription(optionalComment);
            }
            success = ldao.InsertLog(LogDAO.LogTable.ResultPost, rpl);
            if (success == false)
            {
                throw new SQLException("ResultsBL::reinstateResult: "
                        + "Unable to log reinstatement of resultId: " + result.getIdResults());
            }
        }        
    }
    
    /**
     * Sets the invalidation flags on the provided result; logs the change
     *  if instructed (result post log). Will not invalidate results
     *  that are already invalidated.
     * 
     *  NOTE: does not check the reported/printed&transmitted status!
     *    Code should ensure that these results have not been made
     *    available to a doctor before calling this method.
     * 
     * @param result
     * @param userId
     * @param invalidateDate 
     * @param logInvalidation 
     * @param optionalComment 
     * @throws java.sql.SQLException 
     */
    public static void invalidateResult(
            Results result,
            int userId,
            Date invalidateDate,
            boolean logInvalidation,
            String optionalComment)
            throws IllegalArgumentException, SQLException
    {
        if (result == null
                || result.getIdResults() == null
                || result.getIdResults().equals(0))
        {
            throw new IllegalArgumentException("ResultsBL::invalidateResult:"
                    + " Results object argument is NULL or empty");
        }
        
        // Don't invalidate an invalidated result
        if (result.getIsInvalidated() == true) return;
        
        result.setInvalidatedBy(userId);
        result.setInvalidatedDate(invalidateDate);
        result.setIsInvalidated(true);
        
        ResultDAO rdao = new ResultDAO();
        boolean success = rdao.UpdateResult(result);
        if (success == false)
        {
            throw new SQLException("ResultsBL::invalidateResult:"
                    + " Unable to invalidate resultId: " + result.getIdResults());
        }
        
        // Put this in the result post log
        if (logInvalidation)
        {
            LogDAO ldao = new LogDAO();
            ResultPostLog rpl = new ResultPostLog();
            rpl.setIdResults(result.getIdResults());
            rpl.setIdOrders(result.getOrderId());
            rpl.setAction("Test Invalidated");
            rpl.setIdUser(userId);
            rpl.setCreatedDate(invalidateDate);
            if (optionalComment != null && optionalComment.trim().isEmpty() == false)
            {
                rpl.setDescription(optionalComment);
            }
            success = ldao.InsertLog(LogDAO.LogTable.ResultPost, rpl);
            if (success == false)
            {
                throw new SQLException("ResultsBL::invalidateResult: "
                        + "Unable to log invalidation of resultId: " + result.getIdResults());
            }
        }
    }
    
    
    /**
     * Only gets tests on an order that are valid and directly ordered (i.e.
     *  if a panel or battery was ordered, none of the sub-tests would be
     *  returned, only the header).
     * @param orderId
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<Tests> getDirectlyOrderedTests(Integer orderId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (orderId == null)
        {
            throw new IllegalArgumentException("ResultsBL::getDirectlyOrderedTests:"
                    + " Received a [NULL] or empty orderId argument.");
        }
        
        String sql = "SELECT t.* FROM results r"
                + " INNER JOIN tests t ON r.testid = t.idtests"
                + " WHERE r.orderId = " + orderId.toString()
                + " AND r.isInvalidated = 0";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);        
        
        List<Tests> tests = new ArrayList<>();
        
        try
        {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                tests.add(TestDAO.SetTestFromResultSet(new Tests(), rs));
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = "ResultsBL::getDirectlyOrderedTests:"
                    + " Error running SQL: " + sql + "    " + ex.getMessage();
        }
        
        return tests;
    }
    
    
    
}
