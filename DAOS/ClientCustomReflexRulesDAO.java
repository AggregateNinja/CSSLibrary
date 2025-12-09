/*
 * Computer Service & Support, Inc. All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientCustomReflexRules;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ammar
 */
public class ClientCustomReflexRulesDAO implements IStructureCheckable {
    
    public static final String table = "`clientCustomReflexRules`";
    
    public static ClientCustomReflexRules insert(Connection con, ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::Insert: Received a NULL ClientCustomReflexRules object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  clientID,"
                + "  testNumber,"
                + "  isHigh,"
                + "  isLow,"
                + "  isCIDHigh,"
                + "  isCIDLow,"
                + "  reflexRemark,"
                + "  remarkTest,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsHigh());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsLow());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsCIDHigh());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsCIDLow());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexRemark());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkTest());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getActive());

        try {
            Integer idClientCustomReflexRules = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next()) {
                idClientCustomReflexRules = rs.getInt(1);
            }
            pStmt.close();
            if (idClientCustomReflexRules == null || idClientCustomReflexRules <= 0) {
                throw new NullPointerException("ClientCustomReflexRulesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdClientCustomReflexRules(idClientCustomReflexRules);
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        return obj;

    }

    public static ClientCustomReflexRules insert(ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);

    }
    
    public static ClientCustomReflexRules insertOrEnable(Connection con, ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::InsertOrEnable: Received a NULL ClientCustomReflexRules object");
        }
        
        ClientCustomReflexRules previousRule = getForClientIdTestNumberReflexColumns(obj);
        ClientCustomReflexRules newRule = null;
        if (previousRule != null)
        {
            previousRule.setActive(Boolean.TRUE);
            update(con, previousRule);
            newRule = previousRule;
        }
        else
        {
            newRule = insert(con, obj);
        }
        
        return newRule;
    }
    
    public static ClientCustomReflexRules insertOrEnable(ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException { 
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insertOrEnable(con, obj);
    }
    
    public static ClientCustomReflexRules getForClientIdTestNumberReflexColumns(Connection con, ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::get: Received a NULL or empty ClientCustomReflexRules object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE clientId = " + obj.getClientId() + " AND testNumber = " + obj.getTestNumber();
        if (obj.getIsLow() != null) sql += " AND isLow = " + obj.getIsLow();
        else sql += " AND isLow IS NULL";
        if (obj.getIsCIDLow() != null) sql += " AND isCIDLow = " + obj.getIsCIDLow();
        else sql += " AND isCIDLow IS NULL";
        if (obj.getIsHigh() != null) sql += " AND isHigh = " + obj.getIsHigh();
        else sql += " AND isHigh IS NULL";
        if (obj.getIsCIDHigh() != null) sql += " AND isCIDHigh = " + obj.getIsCIDHigh();
        else sql += " AND isCIDHigh IS NULL";
        if (obj.getReflexRemark() != null && obj.getRemarkTest() != null) sql += " AND reflexRemark = " + obj.getReflexRemark() + " AND remarkTest = " + obj.getRemarkTest();
        else sql += " AND reflexRemark IS NULL AND remarkTest IS NULL";

        ClientCustomReflexRules newObj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                newObj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return newObj;
    }
    
    public static ClientCustomReflexRules getForClientIdTestNumberReflexColumns(ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        return getForClientIdTestNumberReflexColumns(con, obj);

    }
    
    public static void update(Connection con, ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
    if (obj == null) {
        throw new IllegalArgumentException("ClientCustomReflexRulesDAO::Update: Received a NULL ClientCustomReflexRules object.");
    }

    String sql = "UPDATE " + table + " SET "
            + "  clientID = ?,"
            + "  testNumber = ?,"
            + "  isHigh = ?,"
            + "  isLow = ?,"
            + "  isCIDHigh = ?,"
            + "  isCIDLow = ?,"
            + "  reflexRemark = ?,"
            + "  remarkTest = ?,"
            + "  active = ?"
            + " WHERE idClientCustomReflexRules = " + obj.getIdClientCustomReflexRules();

    String sqlOutput = "";
    PreparedStatement pStmt = con.prepareStatement(sql);
    try {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsHigh());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsLow());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsCIDHigh());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIsCIDLow());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexRemark());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemarkTest());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getActive());

        sqlOutput = pStmt.toString();
        pStmt.executeUpdate();
    } catch (Exception ex) {
        String errorMsg = ex.getMessage() + " " + sqlOutput;
        System.out.println(errorMsg);
        throw new SQLException(errorMsg);
    }
    pStmt.close();
}
    
    public static void update(ClientCustomReflexRules obj) throws SQLException, IllegalArgumentException, NullPointerException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        update(con, obj);
    }
    
    
    public static ClientCustomReflexRules get(Integer idClientCustomReflexRules) throws SQLException, IllegalArgumentException, NullPointerException {
        if (idClientCustomReflexRules == null) {
            throw new IllegalArgumentException("ClientCustomReflexRules::Get:" + " Received a NULL or empty identifier object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idClientCustomReflexRules = " + String.valueOf(idClientCustomReflexRules);

        PreparedStatement pStmt = con.prepareStatement(sql);

        ClientCustomReflexRules obj = null;
        try {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next()) {
                obj = objectFromResultSet(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;

    }
    
    
    public static ClientCustomReflexRules get(Connection con, Integer idClientCustomReflexRules, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException {
        if (idClientCustomReflexRules == null || idClientCustomReflexRules <= 0) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::get: Received a NULL or empty ClientCustomReflexRules object.");
        }

        if (con == null || false == con.isValid(2)) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO:get: Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idClientCustomReflexRules = " + String.valueOf(idClientCustomReflexRules);
        if (forUpdate) {
            sql += " FOR UPDATE ";
        }

        ClientCustomReflexRules obj = null;

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

    public static ClientCustomReflexRules objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException {
        
        ClientCustomReflexRules obj = new ClientCustomReflexRules();
        
        obj.setIdClientCustomReflexRules(rs.getInt("idClientCustomReflexRules"));
        obj.setClientId(rs.getInt("clientId"));
        obj.setTestNumber(rs.getInt("testNumber"));
        obj.setIsHigh(rs.getInt("isHigh"));
        obj.setIsLow(rs.getInt("isLow"));
        obj.setIsCIDHigh(rs.getInt("isCIDHigh"));
        obj.setIsCIDLow(rs.getInt("isCIDLow"));
        obj.setReflexRemark(rs.getInt("reflexRemark"));
        obj.setRemarkTest(rs.getInt("remarkTest"));
        obj.setActive(rs.getBoolean("active"));

        return obj;
    }
    
    public static List<ClientCustomReflexRules> getAllForClientId(Integer clientId) throws SQLException {
        // check if parameter clientId is valid.
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("ClientCustomReflexRules::Get: Received a NULL clientId");
        }

        // connect to database.
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        // check database connection.
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        // sql.
        String sql = "SELECT * FROM " + table + " WHERE clientId = " + clientId;
        
        // prepare the sql statement.
        PreparedStatement pStmt = con.prepareStatement(sql);

        // initialize the ClientCustomReflexRules ArrayList that is returned.
        List<ClientCustomReflexRules> result = new ArrayList<>(); 

        // try to execute the query and get ResultSet.
        try {

            ResultSet rs = pStmt.executeQuery();

            // create ClientCustomReflexRules objects for each item in the ResultSet and add them to the result List.
            while (rs.next()) {

                ClientCustomReflexRules obj = new ClientCustomReflexRules();

                obj.setIdClientCustomReflexRules(rs.getInt("idClientCustomReflexRules"));
                obj.setClientId(rs.getInt("clientId"));
                obj.setTestNumber(rs.getInt("testNumber"));
                obj.setIsHigh(rs.getInt("isHigh"));
                obj.setIsLow(rs.getInt("isLow"));
                obj.setIsCIDHigh(rs.getInt("isCIDHigh"));
                obj.setIsCIDLow(rs.getInt("isCIDLow"));
                obj.setReflexRemark(rs.getInt("reflexRemark"));
                obj.setRemarkTest(rs.getInt("remarkTest"));
                obj.setActive(rs.getBoolean("active"));

                result.add(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return result;  
     }
    
    public static List<ClientCustomReflexRules> getAllForTestNumber(Integer testNumber) throws SQLException {
        
       // check if parameter testNumber is valid.
       if (testNumber == null || testNumber <= 0) {
            throw new IllegalArgumentException("ClientCustomReflexRules::Get: Received a NULL testNumber");
        }

        // connect to database.
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        // check database connection.
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }

        // sql.
        String sql = "SELECT * FROM " + table + " WHERE testNumber = " + testNumber;

        // prepare the sql statement.
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        // initialize the ClientCustomReflexRules ArrayList that is returned.
        List<ClientCustomReflexRules> result = new ArrayList<>(); 

        // try to execute the query and get ResultSet.
        try {

            pStmt.executeQuery();
            ResultSet rs = pStmt.getGeneratedKeys();

            // create ClientCustomReflexRules objects for each item in the ResultSet and add them to the result List.
            while (rs.next()) {

                ClientCustomReflexRules obj = new ClientCustomReflexRules();

                obj.setIdClientCustomReflexRules(rs.getInt("idClientCustomReflexRules"));
                obj.setClientId(rs.getInt("clientId"));
                obj.setTestNumber(rs.getInt("testNumber"));
                obj.setIsHigh(rs.getInt("isHigh"));
                obj.setIsLow(rs.getInt("isLow"));
                obj.setIsCIDHigh(rs.getInt("isCIDHigh"));
                obj.setIsCIDLow(rs.getInt("isCIDLow"));
                obj.setReflexRemark(rs.getInt("reflexRemark"));
                obj.setRemarkTest(rs.getInt("remarkTest"));
                obj.setActive(rs.getBoolean("active"));

                result.add(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return result;  
     }
     
    public static List<ClientCustomReflexRules> getAllForTestNumberAndClientId(Integer testNumber, Integer clientId, boolean activeOnly) throws SQLException {
       // check if parameters are valid.
       if (testNumber == null || testNumber <= 0) 
       {
            throw new IllegalArgumentException("ClientCustomReflexRules::Get: Received a NULL testNumber");
       }
       else if (clientId == null || clientId < 0)
       {
            throw new IllegalArgumentException("ClientCustomReflexRules::Get: Received a NULL clientId");
       }
       
       // connect to database.
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        // sql.
        String sql = "SELECT * FROM " + table + " WHERE testNumber = " + testNumber + " AND clientId = " + clientId;
        if (activeOnly) sql += " AND active = b'1'";
        
        // prepare the sql statement.
        PreparedStatement pStmt = con.prepareStatement(sql);
       
        // initialize the ClientCustomReflexRules ArrayList that is returned.
        List<ClientCustomReflexRules> result = new ArrayList<>(); 
        
        // try to execute the query and get ResultSet.
        try {

            pStmt.executeQuery();
            ResultSet rs = pStmt.executeQuery();

            // create ClientCustomReflexRules objects for each item in the ResultSet and add them to the result List.
            while (rs.next()) {

                ClientCustomReflexRules obj = new ClientCustomReflexRules();

                obj.setIdClientCustomReflexRules(rs.getInt("idClientCustomReflexRules"));
                obj.setClientId(rs.getInt("clientId"));
                obj.setTestNumber(rs.getInt("testNumber"));
                obj.setIsHigh(rs.getInt("isHigh"));
                obj.setIsLow(rs.getInt("isLow"));
                obj.setIsCIDHigh(rs.getInt("isCIDHigh"));
                obj.setIsCIDLow(rs.getInt("isCIDLow"));
                obj.setReflexRemark(rs.getInt("reflexRemark"));
                obj.setRemarkTest(rs.getInt("remarkTest"));
                obj.setActive(rs.getBoolean("active"));

                result.add(obj);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return result;
    }
     
    public static boolean delete(ClientCustomReflexRules obj) throws IllegalArgumentException, SQLException {
        
        // check if ClientCustomReflexRules object is valid.
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::delete: Received a NULL or empty ClientCustomReflexRules object.");
        }
        
        // connect to database.
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        // check database connection.
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        // sql
        String sql = "UPDATE " + table + " SET `active` = b'0' WHERE idClientCustomReflexRules = " + obj.getIdClientCustomReflexRules();

        // prepare statement and execute deletion query.
        try {
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            
            // return true if record is deleted.
            if (pStmt.executeUpdate() > 0) {
                return true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return false;
    }
    
    public static boolean delete(Connection con, ClientCustomReflexRules obj) throws IllegalArgumentException, SQLException {
        
        // check if ClientCustomReflexRules object is valid.
        if (obj == null) {
            throw new IllegalArgumentException("ClientCustomReflexRulesDAO::delete: Received a NULL or empty ClientCustomReflexRules object.");
        }
        
        // sql.
        String sql = "UPDATE " + table + " SET `active` = b'0' WHERE idClientCustomReflexRules = " + obj.getIdClientCustomReflexRules();

        // prepare statement and execute deletion query.
        try {
            
            PreparedStatement pStmt = con.prepareStatement(sql);
            
            // return true if record is deleted.
            if (pStmt.executeUpdate() > 0) {
                return true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return false;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `clientCustomReflexRules`.`clientId`,\n"
                + "    `clientCustomReflexRules`.`testNumber`,\n"
                + "    `clientCustomReflexRules`.`isHigh`,\n"
                + "    `clientCustomReflexRules`.`isLow`,\n"
                + "    `clientCustomReflexRules`.`isCIDHigh`,\n"
                + "    `clientCustomReflexRules`.`isCIDLow`,\n"
                + "    `clientCustomReflexRules`.`reflexRemark`,\n"
                + "    `clientCustomReflexRules`.`remarkTest`,\n"
                + "    `clientCustomReflexRules`.`active`\n"
                + "FROM `css`.`clientCustomReflexRules` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
