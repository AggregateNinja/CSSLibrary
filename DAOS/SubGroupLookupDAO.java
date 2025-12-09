package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubGroupLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubGroupLookupDAO implements IStructureCheckable {

    public static final String table = "`subGroupLookup`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,SubGroupLookup)">
    public static SubGroupLookup insert(Connection con, SubGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SubGroupLookupDAO::Insert: Received a NULL SubGroupLookup object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  salesGroupId,"
                + "  subGroupId"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesGroupId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubGroupId());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("SubGroupLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSubGroupLookup(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (SubGroupLookup)">
    public static SubGroupLookup insert(SubGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SubGroupLookupDAO::Insert: Received a NULL SubGroupLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, SubGroupLookup)">
    public static void update(Connection con, SubGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SubGroupLookupDAO::Update: Received a NULL SubGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " salesGroupId = ?,"
                + " subGroupId = ?"
                + " WHERE idSubGroupLookup = " + obj.getIdSubGroupLookup();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSalesGroupId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubGroupId());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (SubGroupLookup)">
    public static void update(SubGroupLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("SubGroupLookupDAO::Update: Received a NULL SubGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, SubGroupLookup, boolean (forUpdate))">
    public static SubGroupLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::get: Received a NULL or empty SubGroupLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idSubGroupLookup = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        SubGroupLookup obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (SubGroupLookup)">
    public static SubGroupLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::get: Received a NULL or empty SubGroupLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllForSalesGroupId (id)">
    public static List<SubGroupLookup> getAllForSalesGroupId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllForSalesGroupId: Received a NULL or empty Id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllForSalesGroupId(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllForSalesGroupId (Connection, Integer id, boolean (forUpdate))">
    public static List<SubGroupLookup> getAllForSalesGroupId(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllForSalesGroupId: Received a NULL or empty Id object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:getAllForSalesGroupId: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE salesGroupId = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        List<SubGroupLookup> objList = new ArrayList<SubGroupLookup>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                SubGroupLookup obj = objectFromResultSet(rs);
                objList.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }

    //<editor-fold defaultstate="collapsed" desc="getAllIncludingSubTiersForSalesGroupId (id)">
    public static Set<Integer> getAllLevelsOfSubgroupsForSalesGroupId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllLevelsOfSubgroupsForSalesGroupId: Received a NULL or empty Id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllLevelsOfSubgroupsForSalesGroupId(con, id, false); // not 'for update'
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getAllIncludingSubTiersForSalesGroupId (Connection, Integer id, boolean (forUpdate))">
    public static Set<Integer> getAllLevelsOfSubgroupsForSalesGroupId(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllLevelsOfSubgroupsForSalesGroupId: Received a NULL or empty Id object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:getAllLevelsOfSubgroupsForSalesGroupId: Received a [NULL] or invalid Connection object");
        }
        
        String sql = "SELECT salesGroupId, subGroupId FROM " + table + " s1 where s1.salesGroupId = " + String.valueOf(id) + "\n" +
            "OR s1.subGroupId IN (SELECT subGroupId FROM " + table + " WHERE salesGroupId = " + String.valueOf(id) + ")\n" +
            "OR s1.subGroupId IN (SELECT subGroupId FROM " + table + " WHERE salesGroupId IN (SELECT subGroupId FROM " + table + " WHERE salesGroupId = " + String.valueOf(id) + "))\n" +
            "OR (s1.salesGroupId IN (SELECT salesGroupId FROM " + table + " WHERE subGroupId = " + String.valueOf(id) + ") and s1.subGroupId = " + String.valueOf(id) + ")\n" +
            "OR (s1.idSubGroupLookup IN (SELECT s1.idSubGroupLookup FROM " + table + " s1 LEFT JOIN " + table + " s2 ON s1.subGroupId = s2.salesGroupId WHERE s2.subGroupId = " + String.valueOf(id) + "))\n" +
            "OR (s1.salesGroupId IN (SELECT s1.idSubGroupLookup FROM " + table + " s1 LEFT JOIN " + table + " s2 ON s1.salesGroupId = s2.subGroupId WHERE s2.salesGroupId = " + String.valueOf(id) + "))";
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        Set<Integer> objList = new HashSet<Integer>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                objList.add(rs.getInt("salesGroupId"));
                objList.add(rs.getInt("subGroupId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllIncludingSubTiersForSalesGroupId (id)">
    public static List<Integer> getAllIDsForSalesGroupId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllLevelsOfSubgroupsForSalesGroupId: Received a NULL or empty Id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllIDsForSalesGroupId(con, id, false); // not 'for update'
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getAllIncludingSubTiersForSalesGroupId (Connection, Integer id, boolean (forUpdate))">
    public static List<Integer> getAllIDsForSalesGroupId(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::getAllLevelsOfSubgroupsForSalesGroupId: Received a NULL or empty Id object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:getAllLevelsOfSubgroupsForSalesGroupId: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT DISTINCT subGroupId FROM " + table + " s1 where s1.salesGroupId = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        List<Integer> objList = new ArrayList<Integer>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                objList.add(rs.getInt("subGroupId"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="deleteAllForSalesGroupId (id)">
    public static boolean deleteAllForSalesGroupId(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::deleteAllForSalesGroupId: Received a NULL or empty Id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return deleteAllForSalesGroupId(con, id);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteAllForSalesGroupId (Connection, Integer id)">
    public static boolean deleteAllForSalesGroupId(Connection con, Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("SubGroupLookupDAO::deleteAllForSalesmanId: Received a NULL or empty Id object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("SubGroupLookupDAO:deleteAllForSalesmanId: Received a [NULL] or invalid Connection object");
        }

        String sql = "DELETE FROM " + table + " WHERE salesGroupId = " + String.valueOf(id);

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
    public static SubGroupLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        SubGroupLookup obj = new SubGroupLookup();
        obj.setIdSubGroupLookup(SQLUtil.getInteger(rs, "idSubGroupLookup"));
        obj.setSalesGroupId(SQLUtil.getInteger(rs, "salesGroupId"));
        obj.setSubGroupId(SQLUtil.getInteger(rs, "subGroupId"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `subGroupLookup`.`idSubGroupLookup`,\n"
                + "    `subGroupLookup`.`salesGroupId`,\n"
                + "    `subGroupLookup`.`subGroupId`\n"
                + "FROM `css`.`subGroupLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
