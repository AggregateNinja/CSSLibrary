
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedOrderLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.joda.time.DateTime;

/**
 *
 * @author TomR
 */
public class AdvancedOrderLogDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedOrderLog`";
/**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();    
    
    public AdvancedOrderLogDAO()
    {
        fields.add("advancedOrderId");
        fields.add("action");
        fields.add("orderId");
        fields.add("scheduledFor");
        fields.add("userId");
        fields.add("date");
        fields.add("orderCreated");
        fields.add("errorFlag");
    }
    
    public boolean InsertAdvancedOrderLog(
            int advancedOrderId,
            String action,
            Integer orderId,
            Date scheduledFor,
            Boolean orderCreated,
            int userId,
            boolean errorFlag)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        String stmt = GenerateInsertStatement(fields);
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, advancedOrderId);
            SQLUtil.SafeSetString(pStmt, ++i, action);
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetDate(pStmt, ++i, scheduledFor);
            SQLUtil.SafeSetInteger(pStmt, ++i, userId);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, new Date());
            SQLUtil.SafeSetBoolean(pStmt, ++i, orderCreated);
            SQLUtil.SafeSetBoolean(pStmt, ++i, errorFlag);
            
            pStmt.executeUpdate();
            pStmt.close();
            
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;            
        }
        return true;
    }
    
    /**
     *  Returns the AdvancedOrderLog of the order that was most recently
     *   created from an advanced order (errorFlag = 0).
     * @return 
     */
    public AdvancedOrderLog GetLastOrderCreationLog()
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        AdvancedOrderLog aol = null;
        try
        {            
            String sql = "SELECT * FROM " + table
                    + " WHERE `errorFlag` = 0"
                    + " AND `date` IS NOT NULL AND orderCreated = 1"
                    + " ORDER BY `date` DESC LIMIT 1";
            
            PreparedStatement pStmt = con.prepareStatement(sql);            
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                aol = SetAdvancedOrderLogFromRecordSet(new AdvancedOrderLog(), rs);
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderLogDAO::GetLastCreatedOrderLog " +
                    " Cannot retrieve log for least created order.");
            return null;
        }        
        return aol;
    }
    
    /**
     * Returns created orders that were scheduled within the provided range. Used
     *  to prevent duplicate creation of orders.
     * @param start
     * @param end
     * @return 
     */
    public HashMap<Integer, AdvancedOrderLog> GetOrdersCreatedForDateRange(DateTime start, DateTime end)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        java.sql.Date startSQLDate = new java.sql.Date(start.toDate().getTime());
        java.sql.Date endSQLDate = new java.sql.Date(end.toDate().getTime());
        HashMap<Integer, AdvancedOrderLog> logs = new HashMap<>();
        try
        {
            
            String sql = "SELECT * FROM " + table
                    + " WHERE scheduledFor BETWEEN ? AND ? "
                    + " AND errorFlag = 0 AND orderCreated = 1";
            
            int i=0;
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, startSQLDate);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, endSQLDate);
            
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                AdvancedOrderLog aol;
                aol = SetAdvancedOrderLogFromRecordSet(new AdvancedOrderLog(), rs);
                logs.put(aol.getAdvancedOrderId(), aol);
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderLogDAO::GetOrdersCreatedForDateRange " +
                    "Error attempting to get logs in range " + start.toString() + 
                    ", " + end.toString());
            return null;
        }        
        return logs;        
    }
    
    
    /**
     * Gets logs without an errorFlag in the given date range.
     * Returns a map of AdvancedOrderId --> AdvancedOrderLog rows
     * @param start
     * @param end
     * @return 
     */
    public HashMap<Integer, AdvancedOrderLog> GetNonErrorLogsInDateRange(DateTime start, DateTime end)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        java.sql.Date startSQLDate = new java.sql.Date(start.toDate().getTime());
        java.sql.Date endSQLDate = new java.sql.Date(end.toDate().getTime());
        HashMap<Integer, AdvancedOrderLog> logs = new HashMap<>();
        try
        {
            
            String sql = "SELECT * FROM " + table
                    + " WHERE date BETWEEN ? AND ? "
                    + " AND errorFlag = 0";
            
            int i=0;            
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, startSQLDate);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, endSQLDate);
            
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                AdvancedOrderLog aol;
                aol = SetAdvancedOrderLogFromRecordSet(new AdvancedOrderLog(), rs);
                logs.put(aol.getAdvancedOrderId(), aol);
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderLogDAO::GetNonErrorLogsInDateRange " +
                    "Error attempting to get logs in range " + start.toString() + 
                    ", " + end.toString());
            return null;
        }        
        return logs;
    }
    
    /**
     * Gets the Advanced Order Id that created the supplied Order Id
     *  Null on error or not found.
     * @return 
     */
    public Integer GetAdvancedOrderIdFromOrderId(Integer orderId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        Integer advancedOrderId = null;
        try
        {
            
            String sql = "SELECT * FROM " + table
                    + " WHERE orderId = " + orderId
                    + " AND errorFlag = 0 ORDER BY date DESC";
            
            int i=0;            
            PreparedStatement pStmt = con.prepareStatement(sql);
            
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next())
            {
                AdvancedOrderLog aol;
                aol = SetAdvancedOrderLogFromRecordSet(new AdvancedOrderLog(), rs);
                advancedOrderId = aol.getAdvancedOrderId();
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedOrderLogDAO::GetAdvancedOrderIdFromOrderId " +
                    "Error attempting to get advancedOrderId from orderId: " + orderId);
            return null;
        }
        return advancedOrderId;        
    }
    
    private AdvancedOrderLog SetAdvancedOrderLogFromRecordSet(AdvancedOrderLog aol, ResultSet rs) throws SQLException
    {
        aol.setIdAdvancedOrderLog(rs.getInt("idAdvancedOrderLog"));
        aol.setAdvancedOrderId(rs.getInt("advancedOrderId"));
        aol.setAction(rs.getString("action"));
        aol.setOrderId(rs.getInt("orderId"));
        aol.setScheduledFor(rs.getDate("scheduledFor"));
        aol.setUserId(rs.getInt("userId"));
        aol.setDate(rs.getTimestamp("date"));
        aol.setOrderCreated(rs.getBoolean("orderCreated"));
        aol.setErrorFlag(rs.getBoolean("errorFlag"));
        return aol;
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
