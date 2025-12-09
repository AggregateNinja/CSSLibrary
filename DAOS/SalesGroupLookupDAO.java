package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SalesGroupLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesGroupLookupDAO implements IStructureCheckable {

    public static final String table = "`salesGroupLookup`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,SalesGroupLookup)">
    public static SalesGroupLookup insert(Connection con, SalesGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::Insert: Received a NULL SalesGroupLookup object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SalesGroupLookupDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  salesmanId,"
                + "  salesGroupId"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesmanId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesGroupId());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("SalesGroupLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSalesGroupLookup(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (SalesGroupLookup)">
    public static SalesGroupLookup insert(SalesGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::Insert: Received a NULL SalesGroupLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, SalesGroupLookup)">
    public static void update(Connection con, SalesGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::Update: Received a NULL SalesGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SalesGroupLookupDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " salesmanId = ?,"
                + " salesGroupId = ?"
                + " WHERE idSalesGroupLookup = " + obj.getIdSalesGroupLookup();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesmanId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesGroupId());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (SalesGroupLookup)">
    public static void update(SalesGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::Update: Received a NULL SalesGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, SalesGroupLookup, boolean (forUpdate))">
    public static SalesGroupLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::get: Received a NULL or empty SalesGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SalesGroupLookupDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idSalesGroupLookup = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        SalesGroupLookup obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                obj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (SalesGroupLookup)">
    public static SalesGroupLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::get: Received a NULL or empty SalesGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllForSalesmanId (id)">
    public static List<SalesGroupLookup> getAllForSalesmanId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::getAllForSalesmanId: Received a NULL or empty SalesGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllForSalesmanId(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllForSalesmanId (Connection, SalesGroupLookup, boolean (forUpdate))">
    public static List<SalesGroupLookup> getAllForSalesmanId(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::getAllForSalesmanId: Received a NULL or empty SalesGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SalesGroupLookupDAO:getAllForSalesmanId: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE salesmanId = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        List<SalesGroupLookup> objList = new ArrayList<SalesGroupLookup>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                SalesGroupLookup obj = objectFromResultSet(rs);
                objList.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="deleteAllForSalesmanId (id)">
    public static boolean deleteAllForSalesmanId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::deleteAllForSalesmanId: Received a NULL or empty SalesGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return deleteAllForSalesmanId(con, id);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="deleteAllForSalesmanId (Connection, SalesGroupLookup)">
    public static boolean deleteAllForSalesmanId(Connection con, Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SalesGroupLookupDAO::deleteAllForSalesmanId: Received a NULL or empty SalesGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SalesGroupLookupDAO:deleteAllForSalesmanId: Received a [NULL] or invalid Connection object");
        }

        String sql = "DELETE FROM " + table + " WHERE salesmanId = " + String.valueOf(id);
        
        boolean deleted = false;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            deleted = pStmt.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return deleted;
    }

    //</editor-fold>   
    
    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static SalesGroupLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        SalesGroupLookup obj = new SalesGroupLookup();
        obj.setIdSalesGroupLookup(SQLUtil.getInteger(rs, "idSalesGroupLookup"));
        obj.setSalesmanId(SQLUtil.getInteger(rs, "salesmanId"));
        obj.setSalesGroupId(SQLUtil.getInteger(rs, "salesGroupId"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `salesGroupLookup`.`idSalesGroupLookup`,\n"
                + "    `salesGroupLookup`.`salesmanId`,\n"
                + "    `salesGroupLookup`.`salesGroupId`\n"
                + "FROM `css`.`salesGroupLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
