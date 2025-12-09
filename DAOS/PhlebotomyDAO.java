package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IPhlebotomyDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Phlebotomy;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PhlebotomyDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class PhlebotomyDAO implements IPhlebotomyDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`phlebotomy`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public PhlebotomyDAO() {
        fields.add("idAdvancedOrder");
        fields.add("idOrders");
        fields.add("startDate");
        fields.add("endDate");
        fields.add("occurrences");
        fields.add("scheduleTypeId");
        fields.add("frequency");
        fields.add("phlebotomist");
        fields.add("zone");
        fields.add("drawComment1");
        fields.add("drawComment2");
        fields.add("week");
        fields.add("monday");
        fields.add("tuesday");
        fields.add("wednesday");
        fields.add("thursday");
        fields.add("friday");
        fields.add("saturday");
        fields.add("sunday");
        fields.add("inactive");
        fields.add("redrawReasonId");
    }
    

    public enum ResultCategory
    {
        All,
        ActiveOnly,
        InactiveOnly
    }    

    @Override
    public boolean InsertPhlebotomy(Phlebotomy phlebotomy) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromPhlebotomy(phlebotomy, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    @Override
    public boolean UpdatePhlebotomy(Phlebotomy phlebotomy) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idPhlebotomy` = " + phlebotomy.getIdPhlebotomy();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromPhlebotomy(phlebotomy, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    public Phlebotomy GetPhlebotomyByAdvancedOrderId(int idAdvancedOrder)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Phlebotomy phlebotomy = new Phlebotomy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idAdvancedOrder` = " + idAdvancedOrder;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPhlebotomyFromResultSet(phlebotomy, rs);
            }

            rs.close();
            stmt.close();

            return phlebotomy;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }

    @Override
    public Phlebotomy GetPhlebotomy(int IdPhlebotomy) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Phlebotomy phlebotomy = new Phlebotomy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idPhlebotomy` = " + IdPhlebotomy;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPhlebotomyFromResultSet(phlebotomy, rs);
            }

            rs.close();
            stmt.close();

            return phlebotomy;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Phlebotomy GetPhlebotomy(int OrderID, boolean isAdvancedOrder) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Phlebotomy phlebotomy = new Phlebotomy();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            if (isAdvancedOrder) {
                query += " WHERE `idAdvancedOrder` = " + OrderID;
            } else {
                query += " WHERE `idOrders` = " + OrderID;
            }

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPhlebotomyFromResultSet(phlebotomy, rs);
            }

            rs.close();
            stmt.close();

            return phlebotomy;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    
    /**
     * Gets phlebotomy rows assigned to the given employee. Caller can
     *  conditionally exclude inactive.
     * 
     *  Note: does not check the reschedules table for re-assigns, so the
     *   returned list may not be a comprehensive list of all active 
     *   phlebotomy schedules for the provided employee. Calling code could
     *   look into the reschedules row for the returned phlebotomy Ids to
     *   see whether a draw was re-assigned, or check that table for rows
     *   where reassignedToPhlebotomistId is the employee being looked for.
     * 
     * @param employeeId
     * @param resultCat Which results to return
     * @return
     * @throws SQLException 
     */
    public List<Phlebotomy> GetPhlebotomiesFromEmployeeId(int employeeId, ResultCategory resultCat) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        ArrayList<Phlebotomy> phlebotomies = new ArrayList<>();
        
        String sql = "SELECT * FROM " + table + " WHERE phlebotomist = " + employeeId;
        
        if (resultCat == ResultCategory.ActiveOnly)
        {
            sql += " AND inactive = 0";
        }
        if (resultCat == ResultCategory.InactiveOnly)
        {
            sql += " AND inactive = 1";
        }
        
        Statement stmt = con.createStatement();
        
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next())
        {
            Phlebotomy phlebotomy = new Phlebotomy();
            SetPhlebotomyFromResultSet(phlebotomy, rs);
            phlebotomies.add(phlebotomy);
        }
        
        rs.close();
        stmt.close();
        
        return phlebotomies;
    }    

    private Phlebotomy SetPhlebotomyFromResultSet(Phlebotomy obj, ResultSet rs) throws SQLException {
        
        obj.setIdPhlebotomy(rs.getInt("idPhlebotomy"));
        obj.setIdAdvancedOrder(rs.getInt("idAdvancedOrder"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setStartDate(rs.getTimestamp("startDate"));
        obj.setEndDate(rs.getTimestamp("endDate"));
        obj.setOccurrences(rs.getInt("occurrences"));
        obj.setScheduleTypeId(rs.getInt("scheduleTypeId"));
        obj.setFrequency(rs.getInt("frequency"));
        obj.setPhlebotomist(rs.getInt("phlebotomist"));
        obj.setZone(rs.getString("zone"));
        obj.setDrawComment1(rs.getString("drawComment1"));
        obj.setDrawComment2(rs.getString("drawComment2"));
        obj.setWeek(rs.getInt("week"));
        obj.setMonday(rs.getBoolean("monday"));
        obj.setTuesday(rs.getBoolean("tuesday"));
        obj.setWednesday(rs.getBoolean("wednesday"));
        obj.setThursday(rs.getBoolean("thursday"));
        obj.setFriday(rs.getBoolean("friday"));
        obj.setSaturday(rs.getBoolean("saturday"));
        obj.setSunday(rs.getBoolean("sunday"));
        obj.setInactive(rs.getBoolean("inactive"));
        obj.setRedrawReasonId(rs.getInt("redrawReasonId"));

        return obj;
    }

    private PreparedStatement SetStatementFromPhlebotomy(Phlebotomy obj, PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdAdvancedOrder());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdOrders());
        pStmt.setTimestamp(++i, Convert.ToSQLDateTime(obj.getStartDate()));
        pStmt.setTimestamp(++i, Convert.ToSQLDateTime(obj.getEndDate()));
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getOccurrences());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getScheduleTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFrequency());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPhlebotomist());
        pStmt.setString(++i, obj.getZone());
        pStmt.setString(++i, obj.getDrawComment1());
        pStmt.setString(++i, obj.getDrawComment2());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getWeek());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isMonday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isTuesday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isWednesday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isThursday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isFriday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isSaturday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isSunday());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isInactive());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRedrawReasonId());
        return pStmt;
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
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
