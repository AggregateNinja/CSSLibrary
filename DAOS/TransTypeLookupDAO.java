package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TransTypeLookup;
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

public class TransTypeLookupDAO implements IStructureCheckable {

    public static final String table = "`transTypeLookup`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,TransTypeLookup)">
    public static TransTypeLookup insert(Connection con, TransTypeLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeLookupDAO::Insert: Received a NULL TransTypeLookup object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeLookupDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  clientId,"
                + "  transTypeId,"
                + "  addedDate,"
                + "  addedBy,"
                + "  deactivatedDate,"
                + "  deactivatedBy,"
                + "  active"
                + ")"
                + "VALUES (?,?,NOW(),?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTransTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAddedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDeactivatedBy());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0) {
                throw new NullPointerException("TransTypeLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdTransTypeLookup(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (TransTypeLookup)">
    public static TransTypeLookup insert(TransTypeLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeLookupDAO::Insert: Received a NULL TransTypeLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, TransTypeLookup)">
    public static void update(Connection con, TransTypeLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeLookupDAO::Update: Received a NULL TransTypeLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeLookupDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " clientId = ?,"
                + " transTypeId = ?,"
                + " addedBy = ?,"
                + " deactivatedDate = ?,"
                + " deactivatedBy = ?,"
                + " active = ?"
                + " WHERE idTransTypeLookup = " + obj.getIdTransTypeLookup();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTransTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAddedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDeactivatedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDeactivatedBy());
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

    //<editor-fold defaultstate="collapsed" desc="update (TransTypeLookup)">
    public static void update(TransTypeLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("TransTypeLookupDAO::Update: Received a NULL TransTypeLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, TransTypeLookup, boolean (forUpdate))">
    public static TransTypeLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("TransTypeLookupDAO::get: Received a NULL or empty TransTypeLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("TransTypeLookupDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idTransTypeLookup = " + String.valueOf(id);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        TransTypeLookup obj = null;

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

    //<editor-fold defaultstate="collapsed" desc="get (TransTypeLookup)">
    public static TransTypeLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("TransTypeLookupDAO::get: Received a NULL or empty TransTypeLookup object.");
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
    public static TransTypeLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        TransTypeLookup obj = new TransTypeLookup();
        obj.setIdTransTypeLookup(SQLUtil.getInteger(rs, "idTransTypeLookup"));
        obj.setClientId(SQLUtil.getInteger(rs, "clientId"));
        obj.setTransTypeId(SQLUtil.getInteger(rs, "transTypeId"));
        obj.setAddedBy(SQLUtil.getInteger(rs, "addedBy"));
        obj.setDeactivatedDate(rs.getDate("deactivatedDate"));
        obj.setDeactivatedBy(SQLUtil.getInteger(rs, "deactivatedBy"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="allForClientId (int clientId, boolean isActive)">
    public static List<TransTypeLookup> allForClientId(int clientId, boolean isActive)
    {
        ArrayList<TransTypeLookup> list = new ArrayList<TransTypeLookup>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        try
        {
            if (con.isValid(2) == false) 
                con = CheckDBConnection.Check(dbs, con);
        
            String sql = "SELECT * FROM " + table + " WHERE clientId = " + clientId + " AND active = " + isActive;
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                list.add(objectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
        
        
        return list;
    }
    // </editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `transTypeLookup`.`idTransTypeLookup`,\n"
                + "    `transTypeLookup`.`clientNo`,\n"
                + "    `transTypeLookup`.`transTypeId`,\n"
                + "    `transTypeLookup`.`addedDate`,\n"
                + "    `transTypeLookup`.`addedBy`,\n"
                + "    `transTypeLookup`.`deactivatedDate`,\n"
                + "    `transTypeLookup`.`deactivatedBy`,\n"
                + "    `transTypeLookup`.`active`\n"
                + "FROM `css`.`transTypeLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
    
}
