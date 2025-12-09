package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Preferences;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Oct 15, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: PreferencesDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class PreferencesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`preferences`";

    public boolean InsertPreference(Preferences preference) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        String query = "INSERT INTO " + table + " "
                + "(`key`, "
                + "`value`, "
                + "`type`, "
                + "`table`) "
                + "VALUES "
                + "(?, ?, ?, ?);";
        PreparedStatement pStmt = con.prepareStatement(query);

        try {
            pStmt.setString(1, preference.getKey());
            SQLUtil.SafeSetString(pStmt, 2, preference.getValue());
            SQLUtil.SafeSetString(pStmt, 3, preference.getType());
            SQLUtil.SafeSetString(pStmt, 4, preference.getTable());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }

    }

    public boolean UpdatePreference(Preferences preference) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        String query = "UPDATE " + table + " "
                + "SET "
                + "`key` = ?, "
                + "`value` = ?, "
                + "`type` = ?,"
                + "`table` = ? "
                + "WHERE `idpreferences` = " + preference.getIdpreferences();
        PreparedStatement pStmt = con.prepareStatement(query);

        try {
            pStmt.setString(1, preference.getKey());
            SQLUtil.SafeSetString(pStmt, 2, preference.getValue());
            SQLUtil.SafeSetString(pStmt, 3, preference.getType());
            SQLUtil.SafeSetString(pStmt, 4, preference.getTable());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }


    }

    public String getString(String key) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String value = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `key` = ?;";

            stmt = createStatement(con, query, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if ("STRING".equals(rs.getString("type"))) {
                    value = rs.getString("value");
                }
            }

            rs.close();
            stmt.close();
            
            if(value == null) {
                System.out.println("WARNING: Preference " + key + " is null!");
            }

            return value;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    private static <T extends Object> T returnOptional(String key, T result, T def) {
        if(result == null) {
            System.out.println("WARNING: Preference " + key + " is null, defaulting to " + def.toString());
            return def;
        }
        else{
            return result;
        }
    }
    
    /**
     * Fetch a string preference which returns a default string 'def' should the preference not exist.
     */
    public String getString(String key, String def) {
        String result = this.getString(key);
        return returnOptional(key, result, def);
    }
    
    public Integer getInteger(String key) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Integer value = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `key` = ?;";

            stmt = createStatement(con, query, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("type");
                if ("INT".equals(type) ||
                    "INDEX".equals(type)) 
                {
                    String temp = rs.getString("value");
                    value = Integer.parseInt(temp);
                }
            }

            rs.close();
            stmt.close();
            
            if(value == null) {
                System.out.println("WARNING: Preference " + key + " is null!");
            }

            return value;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
    * Fetch an integer preference which returns a default integer 'def' should the preference not exist.
    */
    public Integer getInteger(String key, Integer def) {
        Integer result = this.getInteger(key);
        return returnOptional(key, result, def);
    }

    public Boolean getBoolean(String key) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Boolean value = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `key` = ?;";

            stmt = createStatement(con, query, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if ("BOOL".equals(rs.getString("type"))) {
                    String temp = rs.getString("value");
                    value = Boolean.parseBoolean(temp);
                }
            }

            rs.close();
            stmt.close();
            
            if(value == null) {
                System.out.println("WARNING: Preference " + key + " is null!");
            }

            return value;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
    * Fetch a boolean preference which returns a default boolean 'def' should the preference not exist.
    */   
    public Boolean getBoolean(String key, Boolean def) {
        Boolean result = this.getBoolean(key);
        return returnOptional(key, result, def);
    }
    
    public Double getDouble(String key) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Double value = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `key` = ?;";

            stmt = createStatement(con, query, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if ("DOUBLE".equals(rs.getString("type"))) {
                    String temp = rs.getString("value");
                    value = Double.parseDouble(temp);
                }
            }

            rs.close();
            stmt.close();
            
            if(value == null) {
                System.out.println("WARNING: Preference " + key + " is null!");
            }

            return value;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
    * Fetch a double preference which returns a default double 'def' should the preference not exist.
    */   
    public Double getDouble(String key, Double def) {
        Double result = this.getDouble(key);
        return returnOptional(key, result, def);
    }
    
    /**
     * Attempts to retrieve a Double value from the preferences table for the
     *  provided key.
     * 
     *  Returns [NULL] if the input key is [NULL] or empty
     *  Returns [NULL] if the search is successful but the key is not found in the database
     *  Will only return the first result found (there should only be one anyway)
     *  Throws a SQLException on error retrieving row OR inability to parse out double value
     * 
     * @param key
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static Double GetDouble(String key)
            throws SQLException, NullPointerException
    {
        if (key == null || key.isEmpty()) return null;
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        key = key.trim();
        String sql = "SELECT * FROM " + table + " WHERE `key` = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        Double value = null;
        try
        {
            pStmt.setString(1, key);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                Preferences pref = getObjectFromResultSet(rs);
                if (pref == null || pref.getIdpreferences() == null ||
                        pref.getValue() == null || pref.getValue().isEmpty())
                {
                    throw new NoSuchFieldException();
                }
                
                value = Double.valueOf(pref.getValue());
                if (value == null || value.isNaN())
                {
                    throw new NumberFormatException();
                }
            }
        }
        catch (NoSuchFieldException nex)
        {
            // Not a show-stopping error; just return [NULL]
            System.out.println("Couldn't find or retrieve preference for key " + key);
        }
        catch (SQLException ex)
        {
            String errorMessage = "PreferenceDAO::GetDouble: Exception executing: " + sql;
            if (ex.getMessage() != null) errorMessage += "  ex: " + ex.getMessage();
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }
        catch (NumberFormatException ex)
        {
            String errorMessage = "PreferenceDAO::GetDouble: Exception casting preference"
                    + " value to a double for key " + key;
            if (ex.getMessage() != null) errorMessage += "  ex: " + ex.getMessage();
            throw new SQLException(errorMessage);
        }
        
        if(value == null) {
            System.out.println("WARNING: Preference " + key + " is null!");
        }
        
        return value;
    }
    
    public static Preferences getObjectFromResultSet(ResultSet rs) throws SQLException
    {
        Preferences pref = new Preferences();
        pref.setIdpreferences(rs.getInt("idpreferences"));
        pref.setKey(rs.getString("key"));
        pref.setValue(rs.getString("value"));
        pref.setType(rs.getString("type"));
        pref.setTable(rs.getString("table"));
        return pref;
    }

    public Preferences GetPreference(String key)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Boolean value = null;
            PreparedStatement stmt = null; //con.createStatement();
            Preferences pref = new Preferences();
            String query = "SELECT * FROM " + table
                    + "WHERE `key` = ?;";

            stmt = createStatement(con, query, key);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pref.setIdpreferences(rs.getInt("idpreferences"));
                pref.setKey(rs.getString("key"));
                pref.setValue(rs.getString("value"));
                pref.setType(rs.getString("type"));
                pref.setTable(rs.getString("table"));
            }

            rs.close();
            stmt.close();

            return pref;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public HashMap<String, Preferences> getAllPreferences() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            HashMap<String, Preferences> map = new HashMap<String, Preferences>();
            Preferences pref = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                pref = new Preferences();
                pref.setIdpreferences(rs.getInt("idpreferences"));
                pref.setKey(rs.getString("key"));
                pref.setValue(rs.getString("value"));
                pref.setType(rs.getString("type"));
                pref.setTable(rs.getString("table"));
                map.put(pref.getKey(), pref);
            }

            rs.close();
            stmt.close();

            return map;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `preferences`.`idpreferences`,\n"
                + "    `preferences`.`key`,\n"
                + "    `preferences`.`value`,\n"
                + "    `preferences`.`type`,\n"
                + "    `preferences`.`table`\n"
                + "FROM `css`.`preferences`;";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
