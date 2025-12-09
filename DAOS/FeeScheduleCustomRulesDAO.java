package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeScheduleCustomRules;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeeScheduleCustomRulesDAO implements IStructureCheckable {

    public static final String table = "`feeScheduleCustomRules`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,FeeScheduleCustomRules)">
    public static FeeScheduleCustomRules insert(Connection con, FeeScheduleCustomRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::Insert: Received a NULL FeeScheduleCustomRules object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  feeScheduleId,"
                + "  testNumber,"
                + "  testAlias,"
                + "  cptCodeId,"
                + "  quantityFrom,"
                + "  quantityTo,"
                + "  cost,"
                + "  addedBy,"
                + "  createdDate,"
                + "  mod1,"
                + "  mod2,"
                + "  mod3,"
                + "  mod4,"
                + "  mod5"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTestAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantityFrom());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantityTo());
            pStmt.setBigDecimal(++i, obj.getCost());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAddedBy());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod1());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod2());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod3());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod4());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod5());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("FeeScheduleCustomRulesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeScheduleCustomRule(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (FeeScheduleCustomRules)">
    public static FeeScheduleCustomRules insert(FeeScheduleCustomRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::Insert: Received a NULL FeeScheduleCustomRules object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, FeeScheduleCustomRules)">
    public static void update(Connection con, FeeScheduleCustomRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::Update: Received a NULL FeeScheduleCustomRules object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " feeScheduleId = ?,"
                + " testNumber = ?,"
                + " testAlias = ?,"
                + " cptCodeId = ?,"
                + " quantityFrom = ?,"
                + " quantityTo = ?,"
                + " cost = ?,"
                + " addedBy = ?,"
                + " mod1 = ?,"
                + " mod2 = ?,"
                + " mod3 = ?,"
                + " mod4 = ?,"
                + " mod5 = ?"
                + " WHERE idFeeScheduleCustomRule = " + obj.getIdFeeScheduleCustomRule();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTestAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTestAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantityFrom());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantityTo());
            pStmt.setBigDecimal(++i, obj.getCost());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAddedBy());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod1());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod2());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod3());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod4());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getMod5());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (FeeScheduleCustomRules)">
    public static void update(FeeScheduleCustomRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::Update: Received a NULL FeeScheduleCustomRules object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, FeeScheduleCustomRules, boolean (forUpdate))">
    public static FeeScheduleCustomRules get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::get: Received a NULL or empty FeeScheduleCustomRules object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeScheduleCustomRule = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        FeeScheduleCustomRules obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (FeeScheduleCustomRules)">
    public static FeeScheduleCustomRules get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::get: Received a NULL or empty FeeScheduleCustomRules object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static FeeScheduleCustomRules objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        FeeScheduleCustomRules obj = new FeeScheduleCustomRules();
        obj.setIdFeeScheduleCustomRule(SQLUtil.getInteger(rs, "idFeeScheduleCustomRule"));
        obj.setFeeScheduleId(SQLUtil.getInteger(rs, "feeScheduleId"));
        obj.setTestNumber(SQLUtil.getInteger(rs, "testNumber"));
        obj.setTestAlias(rs.getString("testAlias"));
        obj.setCptCodeId(SQLUtil.getInteger(rs, "cptCodeId"));
        obj.setQuantityFrom(SQLUtil.getInteger(rs, "quantityFrom"));
        obj.setQuantityTo(SQLUtil.getInteger(rs, "quantityTo"));
        obj.setCost(rs.getBigDecimal("cost"));
        obj.setAddedBy(SQLUtil.getInteger(rs, "addedBy"));
        obj.setMod1(SQLUtil.getInteger(rs, "mod1"));
        obj.setMod2(SQLUtil.getInteger(rs, "mod2"));
        obj.setMod3(SQLUtil.getInteger(rs, "mod3"));
        obj.setMod4(SQLUtil.getInteger(rs, "mod4"));
        obj.setMod5(SQLUtil.getInteger(rs, "mod5"));

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getRuleForQuantity (Connection, quantity, boolean (forUpdate))">
    public static FeeScheduleCustomRules getRuleForQuantity(Connection con, Integer feeScheduleId, Integer quantity, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (feeScheduleId == null || feeScheduleId <= 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::getRuleForQuantity: Received a NULL or empty feeScheduleId object.");
        }
        
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::getRuleForQuantity: Received a NULL or empty quantity object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:getRuleForQuantity: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE quantityFrom <= " + quantity + " AND (quantityTo >= " + quantity + " OR quantityTo IS NULL) AND feeScheduleId = " + feeScheduleId;
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        FeeScheduleCustomRules obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="getAllRules (Connection, boolean (forUpdate))">
    public static List<FeeScheduleCustomRules> getAllRules(Connection con, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:getAllRules: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table;
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        List<FeeScheduleCustomRules> objList = new ArrayList<FeeScheduleCustomRules>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                objList.add(objectFromResultSet(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllRules (boolean (forUpdate))">
    public static List<FeeScheduleCustomRules> getAllRules(boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllRules(con, forUpdate);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllRules (Connection, boolean (forUpdate))">
    public static List<FeeScheduleCustomRules> getAllRulesForFeeSchedule(Connection con, Integer feeScheduleId, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (feeScheduleId == null || feeScheduleId.intValue() == 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:getAllRulesForFeeSchedule: Received a [NULL] or invalid feeScheduleId object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:getAllRulesForFeeSchedule: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE feeScheduleId = " + String.valueOf(feeScheduleId);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        List<FeeScheduleCustomRules> objList = new ArrayList<FeeScheduleCustomRules>();

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                objList.add(objectFromResultSet(rs));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return objList;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAllRules (boolean (forUpdate))">
    public static List<FeeScheduleCustomRules> getAllRulesForFeeSchedule(Integer feeScheduleId, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return getAllRulesForFeeSchedule(con, feeScheduleId, forUpdate);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteAllForScheduleId (Connection, Integer (feeScheduleId))">
    public static void deleteAllForScheduleId(Connection con, Integer feeScheduleId) throws SQLException, IllegalArgumentException, NullPointerException {
        if (feeScheduleId == null || feeScheduleId.intValue() == 0) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO::deleteAllForScheduleId: Received a NULL or 0 feeScheduleId object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("FeeScheduleCustomRulesDAO:deleteAllForScheduleId: Received a [NULL] or invalid Connection object");
        }
        
        String sql = "DELETE FROM " + table + " WHERE feeScheduleId = " + String.valueOf(feeScheduleId);
        
        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="deleteAllForScheduleId (Integer (feeScheduleId))">
    public static void deleteAllForScheduleId(Integer feeScheduleId) throws SQLException, IllegalArgumentException, NullPointerException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        deleteAllForScheduleId(con, feeScheduleId);
    }
	//</editor-fold>
 
    @Override
    public String structureCheck() {
        String query = "SELECT `feeScheduleCustomRules`.`idFeeScheduleCustomRule`,\n"
                + "    `feeScheduleCustomRules`.`feeScheduleId`,\n"
                + "    `feeScheduleCustomRules`.`testNumber`,\n"
                + "    `feeScheduleCustomRules`.`testAlias`,\n"
                + "    `feeScheduleCustomRules`.`cptCodeId`,\n"
                + "    `feeScheduleCustomRules`.`quantityFrom`,\n"
                + "    `feeScheduleCustomRules`.`quantityTo`,\n"
                + "    `feeScheduleCustomRules`.`cost`,\n"
                + "    `feeScheduleCustomRules`.`addedBy`,\n"
                + "    `feeScheduleCustomRules`.`createdDate`,\n"
                + "    `feeScheduleCustomRules`.`mod1`,\n"
                + "    `feeScheduleCustomRules`.`mod2`,\n"
                + "    `feeScheduleCustomRules`.`mod3`,\n"
                + "    `feeScheduleCustomRules`.`mod4`,\n"
                + "    `feeScheduleCustomRules`.`mod5`\n"
                + "FROM `css`.`feeScheduleCustomRules` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
