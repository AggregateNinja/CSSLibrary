package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TransType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransTypeDAO implements IStructureCheckable {

    public static final String table = "`transType`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,TransType)">
    public static TransType insert(Connection con, TransType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeDAO::Insert: Received a NULL TransType object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  idTransType,"
                + "  transTypeName,"
                + "  active"
                + ")"
                + "VALUES (?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdTransType());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTransTypeName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

            sql = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
                System.out.println(ex.getMessage() + " " + sql);
                throw ex;
        }

            return obj;
        }
        //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (TransType)">
    public static TransType insert(TransType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeDAO::Insert: Received a NULL TransType object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, TransType)">
    public static void update(Connection con, TransType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeDAO::Update: Received a NULL TransType object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " idTransType = ?,"
                + " transTypeName = ?,"
                + " active = ?"
                + " WHERE idTransType = " + obj.getIdTransType();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdTransType());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTransTypeName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (TransType)">
    public static void update(TransType obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeDAO::Update: Received a NULL TransType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, TransType, boolean (forUpdate))">
    public static TransType get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("TransTypeDAO::get: Received a NULL or empty TransType object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idTransType = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        TransType obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (TransType)">
    public static TransType get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("TransTypeDAO::get: Received a NULL or empty TransType object.");
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
    public static TransType objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        TransType obj = new TransType();
        obj.setIdTransType(SQLUtil.getInteger(rs, "idTransType"));
        obj.setTransTypeName(rs.getString("transTypeName"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    //</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `transType`.`idTransType`,\n"
                + "    `transType`.`transTypeName`,\n"
                + "    `transType`.`active`\n"
                + "FROM `css`.`transType` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
