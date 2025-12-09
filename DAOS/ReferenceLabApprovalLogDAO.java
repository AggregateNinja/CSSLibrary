/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ReferenceLabApprovalLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author TomR
 */
public class ReferenceLabApprovalLogDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`referenceLabApprovalLog`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public ReferenceLabApprovalLogDAO()
    {
        // Excluding unique database identifier
        fields.add("idOrders");
        fields.add("deptNo");
        fields.add("idUser");
        fields.add("dateApproved");
        fields.add("rejectCodes");
        fields.add("runNo");
    }
    
    public ArrayList<ReferenceLabApprovalLog> GetApprovalLogsFromSpan(
        int deptNo, Date start, Date end)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<ReferenceLabApprovalLog> results = new ArrayList<>();
            
            java.sql.Date startSQLDate = new java.sql.Date(new DateTime(start).withTimeAtStartOfDay().toDate().getTime());
            java.sql.Date endSQLDate = new java.sql.Date(new DateTime(end).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate().getTime());

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            /*
            String query = "SELECT * FROM " + table +
                    " WHERE `deptNo` = " + deptNo +
                    " AND `dateApproved` >= '" + sdf.format(start) + "'" +
                    " AND `dateApproved` <= '" + sdf.format(end) + "'";
            */
            
            String query = "SELECT * FROM " + table
                    + " WHERE `deptNo` = ?"
                    + " AND `dateApproved` BETWEEN ? AND ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, deptNo);
            SQLUtil.SafeSetTimeStamp(pStmt, 2, startSQLDate);
            SQLUtil.SafeSetTimeStamp(pStmt, 3, endSQLDate);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                 results.add(
                         setReferenceLabApprovalLogFromResultSet(
                                 new ReferenceLabApprovalLog(), rs));        
            }
            
            rs.close();
            //stmt.close();
            pStmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetApprovalLogsFromDate: Error getting " +
                "for deptNo " + deptNo);            
            return null;
        }        
    }
    
    public ArrayList<ReferenceLabApprovalLog> GetAllApprovalLogsFromDate(
        Date fromDate)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        try
        {
            ArrayList<ReferenceLabApprovalLog> results = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            String query = "SELECT * FROM " + table +
                    " WHERE `dateApproved` > '" + sdf.format(fromDate) + "'";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                 results.add(
                         setReferenceLabApprovalLogFromResultSet(
                                 new ReferenceLabApprovalLog(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetAllApprovalLogsFromDate: Error getting " +
                "from date " + fromDate);            
            return null;
        }        
        
    }
    
    public ArrayList<ReferenceLabApprovalLog> GetApprovalLogsFromDate(
            int deptNo, Date fromDate)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<ReferenceLabApprovalLog> results = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            String query = "SELECT * FROM " + table +
                    " WHERE `deptNo` = " + deptNo +
                    " AND `dateApproved` > '" + sdf.format(fromDate) + "'";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                 results.add(
                         setReferenceLabApprovalLogFromResultSet(
                                 new ReferenceLabApprovalLog(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetApprovalLogsFromDate: Error getting " +
                "for deptNo " + deptNo);            
            return null;
        }
    }
    
    /**
     * Gets the last n approval logs, where n represents the parameter "count"
     * @param deptNo
     * @param count
     * @return 
     */
    public ArrayList<ReferenceLabApprovalLog> GetLastApprovalLogs(int deptNo, int count)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<ReferenceLabApprovalLog> results = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            String query = "SELECT * FROM " + table +
                    " WHERE `deptNo` = " + deptNo +
                    " ORDER BY `id` DESC LIMIT " + count;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                 results.add(
                         setReferenceLabApprovalLogFromResultSet(
                                 new ReferenceLabApprovalLog(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetApprovalLogsFromDate: Error getting " +
                "for deptNo " + deptNo);            
            return null;
        }        
    }
    
    public ArrayList<Integer> GetAllOrderIdsForPrintRun(int runNo)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Integer> results = new ArrayList<>();
            
            String query = "SELECT idOrders FROM " + table +
                    " WHERE `runNo` = " + runNo;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                results.add(rs.getInt("idOrders"));
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetAllOrderIdsForPrintRun: Error getting " +
                "for runNo " + runNo);            
            return null;
        }        
    }
    
    
    public ArrayList<Integer> GetOrderIdsForPrintRun(int deptNo, int runNo)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Integer> results = new ArrayList<>();
            
            String query = "SELECT idOrders FROM " + table +
                    " WHERE `deptNo` = " + deptNo +
                    " AND `runNo` = " + runNo;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                results.add(rs.getInt("idOrders"));
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetOrderIdsForPrintRun: Error getting " +
                "for deptNo " + deptNo);            
            return null;
        }
    }
    
    public boolean InsertReferenceLabApprovalLog(ReferenceLabApprovalLog approvalLog)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        String stmt = "INSERT INTO " + table 
                + " (`idOrders`,"
                + "`deptNo`,"
                + "`idUser`,"
                + "`rejectCodes`,"
                + "`runNo`) VALUES (?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            SQLUtil.SafeSetInteger(pStmt, 1, approvalLog.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, 2, approvalLog.getDeptNo());
            SQLUtil.SafeSetInteger(pStmt, 3, approvalLog.getIdUser());
//            SQLUtil.SafeSetDate(pStmt, 4, approvalLog.getDateApproved());
            SQLUtil.SafeSetString(pStmt, 4, approvalLog.getRejectCodes());            
            SQLUtil.SafeSetInteger(pStmt, 5, approvalLog.getRunNo());            
            
            pStmt.executeUpdate();
            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }
    }
    
    public Integer GetNextPrintRunNo()
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        Integer result = 1;
        try
        {            
            String query = "SELECT MAX(runNo) as maxRunNo FROM " + table;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                result = rs.getInt("maxRunNo") + 1;
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            System.out.println("ReferenceLabApprovalLogDAO::GetNextPrintRunNo: Can't " +
                    "get the max run number. This is expected behavior if there are " +
                    "no approval logs.");
        }        
        return result;        
    }

    public boolean UpdateReferenceLabApprovalLog(ReferenceLabApprovalLog approvalLog)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        String stmt = GenerateUpdateStatement(fields) +
        " WHERE `id` = " + approvalLog.getId();
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        { 
            SQLUtil.SafeSetInteger(pStmt, 1, approvalLog.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, 2, approvalLog.getDeptNo());
            SQLUtil.SafeSetInteger(pStmt, 3, approvalLog.getIdUser());
            SQLUtil.SafeSetDate(pStmt, 4, approvalLog.getDateApproved());
            SQLUtil.SafeSetString(pStmt, 5, approvalLog.getRejectCodes());
            SQLUtil.SafeSetInteger(pStmt, 6, approvalLog.getRunNo());
            
            pStmt.executeUpdate();
            pStmt.close();

            return true;            
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }

    }    
    
    
    private ReferenceLabApprovalLog setReferenceLabApprovalLogFromResultSet(
        ReferenceLabApprovalLog approvalLog, ResultSet rs) throws SQLException
    {
        approvalLog.setId(rs.getInt("id"));
        approvalLog.setIdOrders(rs.getInt("idOrders"));
        approvalLog.setDeptNo(rs.getInt("deptNo"));
        approvalLog.setIdUser(rs.getInt("idUser"));
        approvalLog.setDateApproved(rs.getTimestamp("dateApproved"));
        approvalLog.setRejectCodes(rs.getString("rejectCodes"));
        approvalLog.setRunNo(rs.getInt("runNo"));
        
        return approvalLog;
    }
    
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
