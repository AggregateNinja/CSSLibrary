/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import DAOS.PanelDAO;
import DAOS.ResultDAO;
import DOS.Results;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author Ryan
 */
public class ResultApprovalBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public ResultApprovalBL() {
    }

    /**
     * Used in ResultApproval. Returns Abnormal flags in the form of an int. 3 =
     * critical; 2 = high/low/abnormal; 3 = normal This returns the flags in the
     * exact order that the search for the results does and is meant to be used
     * in conjunction with the same where clauses.
     *
     * @param where String WHERE clause for first SELECT of Union
     * @param where2 String WHERE clause for second SELECT of Union
     * @param POC int the test number for their POC test.
     * @return int[] flag numbers for the equivilant results.
     */
    public int[] BuildAbnormalFlagQuery(String where, String where2, int POC) {
        //Make sure the connection is still open
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String query = "SELECT `AbnormalFlag` "
                    + "FROM "
                    + "(SELECT  o.accession AS 'Accession', "
                    + "t.number AS 'Test Number', "
                    + "NULL AS 'Subtest Number', "
                    + "t.name AS 'Test Name', "
                    + "CASE WHEN r.isCIDHigh OR r.isCIDLow THEN 1 "
                    + "WHEN r.isLow OR r.isHigh OR r.isAbnormal THEN 2 "
                    + "ELSE 3 END AS 'AbnormalFlag' "
                    + "FROM results r "
                    + "JOIN orders o ON r.orderid = o.idOrders "
                    + "JOIN tests t ON r.testId = t.idTests "
                    + "WHERE r.dateReported IS NOT NULL "
                    + "AND r.approvedDate IS NULL "
                    + "AND r.panelId IS NULL "
                    + "AND t.header = false "
                    + "AND t.testType != 0 "
                    + where + " "
                    + "UNION "
                    + "SELECT  o.accession as 'Accession', "
                    + "CASE WHEN r.panelId IS NOT NULL AND t.testType = 0 THEN t.number "
                    + "WHEN r.panelId IS NOT NULL AND (SELECT tt.header FROM tests tt WHERE tt.idtests = r.panelId) = true THEN t.number "
                    + "WHEN r.panelId IS NOT NULL THEN (SELECT number FROM tests WHERE idtests = r.panelId) "
                    + "WHEN r.panelId IS NULL THEN t.number "
                    + "ELSE t.number END AS 'Test Number', "
                    + "CASE WHEN t.testtype = 0 THEN 0 "
                    + "WHEN r.panelId IS NOT NULL AND (SELECT tt.header FROM tests tt WHERE tt.idtests = r.panelId) = true THEN null "
                    + "ELSE pl.subtestOrder END AS 'Subtest Number', "
                    + "t.name AS 'Test Name', "
                    + "CASE WHEN r.isCIDHigh OR r.isCIDLow THEN 1 "
                    + "WHEN r.isLow OR r.isHigh OR r.isAbnormal THEN 2 "
                    + "ELSE 3 END AS 'AbnormalFlag' "
                    + "FROM results r "
                    + "JOIN orders o ON r.orderid = o.idOrders "
                    + "JOIN tests t ON r.testId = t.idTests "
                    + "JOIN panels pl ON t.idtests = pl.subtestId AND r.panelId = pl.idpanels "
                    + "WHERE r.dateReported IS NOT NULL "
                    + "AND r.approvedDate IS NULL "
                    + "AND r.panelId IS NOT NULL "
                    + "AND t.header = false "
                    + "AND t.testType != 0 "
                    + where2 + " "
                    + ") a "
                    + "WHERE `Test Number` != " + POC + " "
                    + "ORDER BY `Accession`, `Test Number`, `Subtest Number`";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ArrayList<Integer> list = new ArrayList<>();

            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            
            rs.close();
            stmt.close();

            int[] abnormFlags = ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()]));

            return abnormFlags;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Results> CreateApprovedPanels(ArrayList<Integer> OrderIDs, Integer UserID) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        ArrayList<Results> panelList = new ArrayList<>();
        ArrayList<Results> ordPanels = new ArrayList<>();
        ResultDAO rdao = new ResultDAO();
        
        for(Integer orderID : OrderIDs)
        {
            ordPanels = GetPanelsOnOrder(orderID);
            for(Results r : ordPanels)
            {
                System.out.println("ParentTest: " + r.getTestId());
                if(getIsResulted(r, UserID))
                {
                    System.out.println("Mark as Posted: " + r.getTestId());
                    r.setReportedBy(UserID);
                    r.setDateReported(new Date());
                    boolean update = rdao.UpdateResult(r);
                    if(update == false)
                    {
                        System.out.println("Unable to update parent panel " + r.getTestId() + " for Order " + r.getOrderId());
                    }
                }
            }
            
        }
        
        return panelList;
    }
    
    public ArrayList<Results> GetPanelsOnOrder(Integer OrderID) throws SQLException
    {
        Results res;
        ArrayList<Results> panelRes = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        final String query = "SELECT r.* " +
                            "FROM `results` r " +
                            "JOIN `tests` t ON r.`testId` = t.`idTests` " +
                            "WHERE t.`testType` = 0 " +
                            "AND r.`panelId` IS NULL " +
                            "AND r.`reportedBy` IS NULL " +
                            "AND r.`dateReported` IS NULL " +
                            "AND r.`approvedDate` IS NULL " +
                            "AND r.`invalidatedDate` IS NULL " +
                            "AND r.`orderId` = " + OrderID;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next())
        {
            res = new Results();

            res.setIdResults(rs.getInt("idResults"));
            res.setOrderId(rs.getInt("orderId"));
            res.setTestId(rs.getInt("testId"));
            res.setPanelId(rs.getInt("panelId"));
            res.setResultNo(rs.getDouble("resultNo"));
            res.setResultText(rs.getString("resultText"));
            res.setResultRemark(rs.getInt("resultRemark"));
            res.setResultChoice(rs.getInt("resultChoice"));
            res.setCreated(rs.getTimestamp("created"));
            res.setReportedBy(rs.getInt("reportedBy"));
            res.setDateReported(rs.getTimestamp("dateReported"));
            res.setIsApproved(rs.getBoolean("isApproved"));
            res.setApprovedDate(rs.getTimestamp("approvedDate"));
            res.setApprovedBy(rs.getInt("approvedBy"));
            res.setIsInvalidated(rs.getBoolean("isInvalidated"));
            res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
            res.setInvalidatedBy(rs.getInt("invalidatedBy"));
            res.setIsUpdated(rs.getBoolean("isUpdated"));
            res.setUpdatedBy(rs.getInt("updatedBy"));
            res.setUpdatedDate(rs.getTimestamp("updatedDate"));
            res.setIsAbnormal(rs.getBoolean("isAbnormal"));
            res.setIsHigh(rs.getBoolean("isHigh"));
            res.setIsLow(rs.getBoolean("isLow"));
            res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
            res.setIsCIDLow(rs.getBoolean("isCIDLow"));
            res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));

            panelRes.add(res);
        }

        rs.close();
        stmt.close();
        
        return panelRes;
    }
    
    private boolean getIsResulted(Results parentResult, Integer UserId) throws SQLException
    {
        Results result;
        PanelDAO pdao = new PanelDAO();
        ResultDAO rdao = new ResultDAO();
        ArrayDeque<Results> rqueue = new ArrayDeque<Results>();
        boolean isReported = true;
        
        ArrayList<Results> pRes = GetOrderedResultsUnderPanelHeaderShallow(parentResult.getOrderId(), parentResult.getTestId());
        for(Results r : pRes)
        {
            rqueue.addFirst(r);
        }
        
        while(rqueue.isEmpty() == false)
        {
            //Get the result
            result = rqueue.pop();
            System.out.println("\tTest: " + result.getTestId());
            //Is it reported?
            if(result.getDateReported() == null || (result.getReportedBy() == null || result.getReportedBy() == 0))
            {
                //If not, is it a panel?
                if(pdao.CheckIfPanelHeader(result.getTestId()))
                {
                    System.out.println("\tPanel: " + result.getTestId());
                    //traverse it. Did the traverse come back true? 
                    if(getIsResulted(result, UserId))
                    {
                        System.out.println("\tPanel: " + result.getTestId() + " Marked as posted");
                        // If yes, set it Reported
                        result.setReportedBy(UserId);
                        result.setDateReported(new Date());
                        boolean update = rdao.UpdateResult(result);
                        if(update == false)
                        {
                            System.out.println("Unable to update panel " + result.getTestId() + " for Order " + result.getOrderId());
                            return false;
                        }
                    }
                    else
                    {
                        // If not, return false
                        isReported = false;
                    }
                    
                }
                //If not, return false
                else
                {
                    isReported = false;
                }
            }
        }
        return isReported;
    }
    
    public ArrayList<Results> GetOrderedResultsUnderPanelHeaderShallow(Integer OrderID, Integer PanelID) throws SQLException
    {
        Results res;
        ArrayList<Results> panelRes = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        final String query = "SELECT r.* " +
                            "FROM `results` r " +
                            "JOIN `tests` t ON r.`testId` = t.`idTests` " +
                            "WHERE t.`header` != 1 " +
                            "AND r.`panelId` = " + PanelID + " " +//"AND r.`panelId` = " + PanelID + " " +
                            "AND r.`orderId` = " + OrderID;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        
        while (rs.next())
        {
            res = new Results();

            res.setIdResults(rs.getInt("idResults"));
            res.setOrderId(rs.getInt("orderId"));
            res.setTestId(rs.getInt("testId"));
            res.setPanelId(rs.getInt("panelId"));
            res.setResultNo(rs.getDouble("resultNo"));
            res.setResultText(rs.getString("resultText"));
            res.setResultRemark(rs.getInt("resultRemark"));
            res.setResultChoice(rs.getInt("resultChoice"));
            res.setCreated(rs.getTimestamp("created"));
            res.setReportedBy(rs.getInt("reportedBy"));
            res.setDateReported(rs.getTimestamp("dateReported"));
            res.setIsApproved(rs.getBoolean("isApproved"));
            res.setApprovedDate(rs.getTimestamp("approvedDate"));
            res.setApprovedBy(rs.getInt("approvedBy"));
            res.setIsInvalidated(rs.getBoolean("isInvalidated"));
            res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
            res.setInvalidatedBy(rs.getInt("invalidatedBy"));
            res.setIsUpdated(rs.getBoolean("isUpdated"));
            res.setUpdatedBy(rs.getInt("updatedBy"));
            res.setUpdatedDate(rs.getTimestamp("updatedDate"));
            res.setIsAbnormal(rs.getBoolean("isAbnormal"));
            res.setIsHigh(rs.getBoolean("isHigh"));
            res.setIsLow(rs.getBoolean("isLow"));
            res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
            res.setIsCIDLow(rs.getBoolean("isCIDLow"));
            res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));

            panelRes.add(res);
        }

        rs.close();
        stmt.close();
        
        return panelRes;
    }
}
