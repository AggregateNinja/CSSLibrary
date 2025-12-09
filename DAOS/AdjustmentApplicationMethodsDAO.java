package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentApplicationMethods;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdjustmentApplicationMethodsDAO implements IStructureCheckable {

    public enum ApplicationMethods
    {
        ManuallyApplied(1),
        AutomaticDistributeEvenly(2),
        AutomaticPayOffFirst(3),
        AutomaticRemittanceFile(4);
        
        private final int id;
        
        ApplicationMethods(int id)
        {
            this.id = id;
        }
        
        public int getID()
        {
            return id;
        }
    }

    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustmentApplicationMethods`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,AdjustmentApplicationMethods)">
    public static AdjustmentApplicationMethods insert(Connection con, AdjustmentApplicationMethods obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::Insert: Received a NULL AdjustmentApplicationMethods object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  idAdjustmentApplicationMethods,"
                + "  name"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdAdjustmentApplicationMethods());
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

    //<editor-fold defaultstate="collapsed" desc="insert (AdjustmentApplicationMethods)">
    public static AdjustmentApplicationMethods insert(AdjustmentApplicationMethods obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::Insert: Received a NULL AdjustmentApplicationMethods object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, AdjustmentApplicationMethods)">
    public static void update(Connection con, AdjustmentApplicationMethods obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::Update: Received a NULL AdjustmentApplicationMethods object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " idAdjustmentApplicationMethods = ?,"
                + " name = ?"
                + " WHERE idAdjustmentApplicationMethods = " + obj.getIdAdjustmentApplicationMethods();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdAdjustmentApplicationMethods());
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

    //<editor-fold defaultstate="collapsed" desc="update (AdjustmentApplicationMethods)">
    public static void update(AdjustmentApplicationMethods obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::Update: Received a NULL AdjustmentApplicationMethods object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, AdjustmentApplicationMethods, boolean (forUpdate))">
    public static AdjustmentApplicationMethods get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::get: Received a NULL or empty AdjustmentApplicationMethods object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE  = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        AdjustmentApplicationMethods obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (AdjustmentApplicationMethods)">
    public static AdjustmentApplicationMethods get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("AdjustmentApplicationMethodsDAO::get: Received a NULL or empty AdjustmentApplicationMethods object.");
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
    public static AdjustmentApplicationMethods objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        AdjustmentApplicationMethods obj = new AdjustmentApplicationMethods();
        obj.setIdAdjustmentApplicationMethods(SQLUtil.getInteger(rs, "idAdjustmentApplicationMethods"));
        obj.setName(rs.getString("name"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `adjustmentApplicationMethods`.`idAdjustmentApplicationMethods`,\n"
                + "    `adjustmentApplicationMethods`.`name`\n"
                + "FROM `cssbilling`.`adjustmentApplicationMethods` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
