package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentCategory;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdjustmentCategoryDAO implements IStructureCheckable {

    public enum AdjustmentCategories
    {
        Payment(1),
        Correction(2),
        WriteOff(3);
        
        private final int id;
        
        AdjustmentCategories(int id)
        {
            this.id = id;
        }
        
        public int getID()
        {
            return id;
        }
    }
    
    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustmentCategory`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,AdjustmentCategory)">
    public static AdjustmentCategory insert(Connection con, AdjustmentCategory obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::Insert: Received a NULL AdjustmentCategory object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  idAdjustmentCategory,"
                + "  name"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdAdjustmentCategory());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());

            sql = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (AdjustmentCategory)">
    public static AdjustmentCategory insert(AdjustmentCategory obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::Insert: Received a NULL AdjustmentCategory object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, AdjustmentCategory)">
    public static void update(Connection con, AdjustmentCategory obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::Update: Received a NULL AdjustmentCategory object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " idAdjustmentCategory = ?,"
                + " name = ?"
                + " WHERE idAdjustmentCategory = " + obj.getIdAdjustmentCategory();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdAdjustmentCategory());
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

    //<editor-fold defaultstate="collapsed" desc="update (AdjustmentCategory)">
    public static void update(AdjustmentCategory obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::Update: Received a NULL AdjustmentCategory object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, AdjustmentCategory, boolean (forUpdate))">
    public static AdjustmentCategory get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::get: Received a NULL or empty AdjustmentCategory object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE  = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        AdjustmentCategory obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (AdjustmentCategory)">
    public static AdjustmentCategory get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentCategoryDAO::get: Received a NULL or empty AdjustmentCategory object.");
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
    public static AdjustmentCategory objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        AdjustmentCategory obj = new AdjustmentCategory();
        obj.setIdAdjustmentCategory(SQLUtil.getInteger(rs, "idAdjustmentCategory"));
        obj.setName(rs.getString("name"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `adjustmentCategory`.`idAdjustmentCategory`,\n"
                + "    `adjustmentCategory`.`name`\n"
                + "FROM `cssbilling`.`adjustmentCategory` LIMIT 1;";
         return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
