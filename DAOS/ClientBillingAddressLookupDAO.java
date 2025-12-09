/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientBillingAddressLookup;
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

public class ClientBillingAddressLookupDAO implements IStructureCheckable {
    
    public static final String table = "`clientBillingAddressLookup`";
    
    //<editor-fold defaultstate="collapsed" desc="insert (Connection,ClientBillingAddressLookup)">
    public static ClientBillingAddressLookup insert(Connection con, ClientBillingAddressLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO::Insert: Received a NULL TransTypeLookup object");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO:Insert: Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  clientId,"
                + "  address,"
                + "  poBoxSuite,"
                + "  city,"
                + "  state,"
                + "  zip,"
                + "  addedDate,"
                + "  addedBy,"
                + "  deactivatedDate,"
                + "  deactivatedBy,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,NOW(),?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAddress());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPOBoxSuite());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCity());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getState());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getZip());
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
                throw new NullPointerException("ClientBillingAddressLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdclientBillingAddressLookup(newId);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="insert (ClientBillingAddressLookup)">
    public static ClientBillingAddressLookup insert(ClientBillingAddressLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO::Insert: Received a NULL TransTypeLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="update (Connection, ClientBillingAddressLookup)">
    public static void update(Connection con, ClientBillingAddressLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO::Update: Received a NULL TransTypeLookup object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO:Update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + "SET "
                + "  clientId = ?,"
                + "  address = ?,"
                + "  poBoxSuite = ?,"
                + "  city = ?,"
                + "  state = ?,"
                + "  zip = ?,"
                + "  addedBy = ?,"
                + "  deactivatedDate = NOW(),"
                + "  deactivatedBy = ?,"
                + "  active = ?"
                + " WHERE idclientBillingAddressLookup = " + obj.getIdclientBillingAddressLookup();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getAddress());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getPOBoxSuite());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getCity());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getState());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getZip());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAddedBy());
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

    //<editor-fold defaultstate="collapsed" desc="update (ClientBillingAddressLookup)">
    public static void update(ClientBillingAddressLookup obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientBillingAddressLookupDAO::Update: Received a NULL TransTypeLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static ClientBillingAddressLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        ClientBillingAddressLookup obj = new ClientBillingAddressLookup();
        obj.setIdclientBillingAddressLookup(SQLUtil.getInteger(rs, "idclientBillingAddressLookup"));
        obj.setClientId(SQLUtil.getInteger(rs, "clientId"));
        obj.setAddress(rs.getString("address"));
        obj.setPOBoxSuite(rs.getString("poBoxSuite"));
        obj.setCity(rs.getString("city"));
        obj.setState(rs.getString("state"));
        obj.setZip(rs.getString("zip"));
        obj.setAddedBy(SQLUtil.getInteger(rs, "addedBy"));
        obj.setDeactivatedDate(rs.getDate("deactivatedDate"));
        obj.setDeactivatedBy(SQLUtil.getInteger(rs, "deactivatedBy"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="allForClientId (int clientId, boolean isActive)">
    public static List<ClientBillingAddressLookup> allForClientId(int clientId, boolean isActive)
    {
        ArrayList<ClientBillingAddressLookup> list = new ArrayList<>();
        
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
        String query = "SELECT `clientBillingAddressLookup`.`idclientBillingAddressLookup`,\n"
                + "    `clientBillingAddressLookup`.`clientId`,\n"
                + "    `clientBillingAddressLookup`.`address`,\n"
                + "    `clientBillingAddressLookup`.`poBoxSuite`,\n"
                + "    `clientBillingAddressLookup`.`city`,\n"
                + "    `clientBillingAddressLookup`.`state`,\n"
                + "    `clientBillingAddressLookup`.`zip`,\n"
                + "    `clientBillingAddressLookup`.`addedDate`,\n"
                + "    `clientBillingAddressLookup`.`addedBy`,\n"
                + "    `clientBillingAddressLookup`.`deactivatedDate`,\n"
                + "    `clientBillingAddressLookup`.`deactivatedBy`,\n"
                + "    `clientBillingAddressLookup`.`active`\n"
                + "FROM `css`.`clientBillingAddressLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }

}
