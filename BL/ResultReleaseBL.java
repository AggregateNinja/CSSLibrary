package BL;

import DAOS.OrderDAO;
import DAOS.ReleaseOrdersDAO;
import DAOS.ResultDAO;
import DAOS.TestDAO;
import DOS.Orders;
import DOS.Results;
import DOS.Tests;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Jun 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: BL
 * @file name: ResultReleaseBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



public class ResultReleaseBL 
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public ResultReleaseBL()
    {
        
    }
    
    public ArrayList<Integer> GetClientsWithinRange(Integer start, Integer end)
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
            String query = "SELECT clientNo FROM clients WHERE clientNo BETWEEN " + start + " AND " + end;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Integer> list = new ArrayList<>();
            
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Integer> GetClientsByRoute(Integer route)
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
            String query = "SELECT clientNo FROM clients WHERE route = " + route;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Integer> list = new ArrayList<>();
            
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Integer> GetClientsByLocation(Integer location)
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
            String query =  "SELECT DISTINCT c.clientNo FROM orders o " +
                            "INNER JOIN clients c ON o.clientid = c.idClients " +
                            "WHERE o.locationId = " + location + " " +
                            "ORDER BY c.clientNo";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Integer> list = new ArrayList<>();
            
            while (rs.next()) {
                list.add(rs.getInt(1));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<String> GetReportFormatsFromOrders(ArrayList<Integer> orderIDs)
    {
        if(orderIDs.isEmpty())
            return null;
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
            ArrayList<String> list = new ArrayList<>();
            String query =  "SELECT DISTINCT rt.format " +
                            "FROM orders o " +
                            "LEFT JOIN clients c " +
                            "ON c.idclients = o.clientid " +
                            "LEFT JOIN preferences p " +
                            "ON p.key = \"DefaultResultReport\" " +
                            "LEFT JOIN reportType rt " +
                            "ON rt.idreportType = (IFNULL(o.reportType, IFNULL(c.defaultReportType, p.value))) " +
                    "WHERE o.idOrders IN (";
            
            boolean first = true;
            for(Integer id : orderIDs)
            {
                if(!first)
                {
                    query += ",";
                }
                query += id;
                first = false;
            }
            
            query += ");";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                list.add(rs.getString(1));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Checks an Order for Batteries, then sets the printAndTransmittedFlag 
     * if the battery components have all been flagged printAndTransmitted
     * @param orderId int value of the orderId
     * @param user it value of the userId
     * @return Number of Batteries Updated
     */
    public ArrayList<Integer> UpdateBatteryPrintAndTransmitted(int orderId, int user) {

        java.util.Date date = new java.util.Date();
        ArrayList<Integer> batteryRows = new ArrayList<Integer>();
        ArrayList<Results> batteries = GetBatteriesOnOrder(orderId);
        TestDAO tdao = new TestDAO();
        ResultDAO rdao = new ResultDAO();

        //Now check each Battery to see if they are complete
        for (Results res : batteries) {
            //Get all Tests/Panels in Battery
            Tests[] components = tdao.GetSubtestsByHeaderID(res.getTestId());
            //variable to hold reported components
            int reported = 0;
            //ArrayList holding all reported results for this order
            ArrayList<Results> reportedResults
                    = (ArrayList<Results>) rdao.GetReportedResultByOrderID(orderId);

            //Now loop through componenets and results to count reported components
            for (Tests t : components) {
                for (Results r : reportedResults) {
                    if (t.getIdtests() == r.getTestId()) {
                        //Found a reported component, increment reported count
                        reported++;
                        }
                    }
                }

            //Now check to see if the number of reported components = the
            //total number of components in the Battery.  If so it is complete.
            if (reported == components.length) {
                try {
                    //Populate/Set the reported fields
                    res.setUpdatedBy(user);
                    res.setUpdatedDate(date);
                    res.setPrintAndTransmitted(true);
                    res.setPAndTDate(date);
                    //Update/Post the Battery
                    rdao.UpdateResult(res);
                    //Add the idResults to ArrayList for Logging
                    batteryRows.add(res.getIdResults());
                } catch (SQLException sx) {
                    System.out.println("ResultPostBL::BatteryReported - " + sx.toString());
                    return null;
                }
            }
        }

        return batteryRows;
    }
    
    /**
     * Returns an ArrayList of Results objects that are battery headers
     * for the passed in orderid
     * @param orderId int value of orderId
     * @return ArrayList<Results> of Battery Headers on an orderId
     */
    public ArrayList<Results> GetBatteriesOnOrder(int orderId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Results> batts = new ArrayList<>();
            ArrayList<Integer> resList = new ArrayList<>();

            String query = "SELECT DISTINCT r.idresults "
                    + "FROM results r "
                    + "LEFT JOIN tests t "
                    + "	ON r.testid = t.idtests "
                    + "WHERE r.orderid = ? "
                    + "AND t.testType = 0 "
                    + "AND t.header = true";

            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, orderId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                resList.add(rs.getInt(1));
            }

            rs.close();
            pStmt.close();

            ResultDAO rdao = new ResultDAO();

            for (Integer rid : resList) {
                batts.add(rdao.GetResultByID(rid));
            }

            return batts;

        } catch (Exception ex) {
            System.out.println("ResultPostBL::GetBatteriesOnOrder - " + ex.toString());
            return null;
        }
    }

    /**
     * Returns true if any of the results currently on the order have a 
     *  "noCharge" flag of zero (0), meaning that it is a billable test.
     * 
     *  NULL if there are any errors.
     * 
     * @param orderId
     * @return 
     */
    public Boolean HasBillableTest(int orderId)
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
            // Exclude rows that are invalidated or deleted
            String query = "SELECT"
                + " COUNT(*)"
                + " FROM results"
                + " WHERE noCharge = 0"
                + " AND panelId IS NULL" // <-- Only check "root" tests, as they are the ones sent to billing.
                + " AND (isInvalidated IS NULL OR isInvalidated = 0)"
                + " AND invalidatedDate IS NULL"
                + " AND invalidatedBy IS NULL"
                + " AND (resultText IS NULL OR UCASE(resultText) != 'DELETED')"
                + " AND orderId = ?";

            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, orderId);

            ResultSet rs = pStmt.executeQuery();
            int billableCount = 0;
            if (rs.next())
            {
                billableCount = rs.getInt(1);
            }

            rs.close();
            pStmt.close();

            return (billableCount > 0);

        } catch (Exception ex) {
            System.out.println("ResultPostBL::GetBatteriesOnOrder - " + ex.toString());
            return null;
        }        
    }
    
    /**
     * Updates any valid approved un-transmitted row to transmitted for a given order.
     * Called at the end of Print & Transmit for each order processed.
     * @param orderId
     * @throws IllegalArgumentException
     * @throws Exception 
     */
    public void SetUnsentApprovedToPrintAndTransmitted(Integer orderId) throws IllegalArgumentException, Exception
    {
        if (orderId == null || orderId.equals(0)) throw new IllegalArgumentException("Received a NULL/zero orderId!");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // Set any valid row that's approved but un-transmitted to transmitted
        String sql = "UPDATE results SET pAndTDate = NOW(), "
                + " printAndTransmitted = 1 "
                + " WHERE isInvalidated = 0"
                + " AND isApproved = 1"
                + " AND pAndTDate IS NULL"
                + " AND printAndTransmitted = 0"
                + " AND orderId = " + orderId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.execute();
        
    }
    
    public List<Orders> GetAllOrdersForReleaseClient(int releaseId, int clientId)
    {
        ReleaseOrdersDAO roDAO = new ReleaseOrdersDAO();
        List<Integer> orderIdList = roDAO.GetAllOrderIdsForRelease(releaseId);
        
        OrderDAO oDAO = new OrderDAO();
        List<Orders> orderList = new ArrayList<Orders>();
        for (Integer orderId : orderIdList)
        {
            try
            {
                orderList.add(oDAO.GetOrderById(orderId));
            }
            catch (SQLException ex)
            {
                System.out.println("ResultReleaseBL::GetAllOrdersForReleaseClient - " + ex.toString());
            }
        }
        return orderList;
    }
}
