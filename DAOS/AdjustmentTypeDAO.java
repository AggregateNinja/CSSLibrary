package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentType;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Utility.SQLUtil.createStatement;

public class AdjustmentTypeDAO implements IStructureCheckable {
    
    public enum AdjustmentTypes
    {
        ManualPayment("Manual Payment"),
        ManualCorrection("Manual Correction"),
        ManualWriteoff("Manual Write-off"),
        OrderPrepayment("Order Prepayment"),
        AutomaticReconciliationPayment("Automatic Reconciliation Payment"),
        InsuranceTransfer("Insurance Transfer"),
        AutomaticReconciliationWriteoff("Automatic Reconciliation Write-off"),
        ClientCashApplication("Client Cash Application");
        
        private String name;
        
        AdjustmentTypes(String name)
        {
            this.name = name;
        }
        
        public String getName()
        {
            return name;
        }
    }
    
    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustmentType`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,AdjustmentType)">
    public static AdjustmentType insert(Connection con, AdjustmentType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::Insert: Received a NULL AdjustmentType object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentTypeDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentCategoryId,"
                + "  name"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentCategoryId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("AdjustmentTypeDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdAdjustmentType(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (AdjustmentType)">
    public static AdjustmentType insert(AdjustmentType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::Insert: Received a NULL AdjustmentType object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, AdjustmentType)">
    public static void update(Connection con, AdjustmentType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::Update: Received a NULL AdjustmentType object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentTypeDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " adjustmentCategoryId = ?,"
                + " name = ?"
                + " WHERE idAdjustmentType = " + obj.getIdAdjustmentType();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentCategoryId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (AdjustmentType)">
    public static void update(AdjustmentType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::Update: Received a NULL AdjustmentType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, AdjustmentCategoryId, Name, boolean (forUpdate))">
    public static AdjustmentType get(Connection con, Integer adjustmentCategoryId, String name, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (adjustmentCategoryId == null || adjustmentCategoryId <= 0) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty adjustmentCategoryId object.");
        }
        
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty name object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentTypeDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE adjustmentCategoryId = " + String.valueOf(adjustmentCategoryId) +
                " AND name = ?";
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        AdjustmentType obj = null;
        
        System.out.println(sql);

        try (PreparedStatement pStmt = createStatement(con, sql, name)) {

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
    
    //<editor-fold defaultstate="collapsed" desc="get (Connection, AdjustmentType, boolean (forUpdate))">
    public static AdjustmentType get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty AdjustmentType object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentTypeDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idAdjustmentType = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        AdjustmentType obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (AdjustmentType)">
    public static AdjustmentType get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty AdjustmentType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (AdjustmentCategoryId, Name, boolean (forUpdate))">
    public static AdjustmentType get(Integer adjustmentCategoryId, String name, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (adjustmentCategoryId == null || adjustmentCategoryId <= 0) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty adjustmentCategoryId object.");
        }
        
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("AdjustmentTypeDAO::get: Received a NULL or empty name object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT * FROM " + table + " WHERE adjustmentCategoryId = " + String.valueOf(adjustmentCategoryId) +
                " AND name = ?";
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        AdjustmentType obj = null;

        try (PreparedStatement pStmt = createStatement(con, sql, name)) {

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
    
    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static AdjustmentType objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        AdjustmentType obj = new AdjustmentType();
        obj.setIdAdjustmentType(SQLUtil.getInteger(rs, "idAdjustmentType"));
        obj.setAdjustmentCategoryId(SQLUtil.getInteger(rs, "adjustmentCategoryId"));
        obj.setName(rs.getString("name"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `adjustmentType`.`idAdjustmentType`,\n"
                + "    `adjustmentType`.`adjustmentCategoryId`,\n"
                + "    `adjustmentType`.`name`\n"
                + "FROM `cssbilling`.`adjustmentType` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
