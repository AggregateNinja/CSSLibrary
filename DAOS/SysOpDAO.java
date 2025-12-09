package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.SysOps;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Encryptor;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Oct 15, 2013
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: SysOpsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SysOpDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`sys_ops`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    
    public SysOpDAO(){
        fields.add("name");
        fields.add("setting");
        fields.add("option");
    }
    
        @Override
    public Boolean Insert(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            SysOps prefs = (SysOps) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromSysOps(prefs, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            SysOps prefs = (SysOps) obj;
            String stmt = GenerateInsertStatement(fields)+ " WHERE `id` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetInteger(pStmt, 1, prefs.getId());
            SetStatementFromSysOps(prefs, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try{
            return GetSysOpByID(ID);
        }catch(Exception ex){
            Logger.getLogger(SysOps.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SysOpDAO::getByID - " + ex.toString());
            return null;
        }
    }

    public boolean InsertSysOp(SysOps preference) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        String query = "INSERT INTO " + table + " "
                + "(`name`, "
                + "`setting`, "
                + "`option` ) "
                + "VALUES "
                + "(?, ?, ?);";
        PreparedStatement pStmt = con.prepareStatement(query);

        try {
            //pStmt.setString(1, Encryptor.EncryptString(preference.getName(), true));
            SQLUtil.SafeSetString(pStmt, 1, Encryptor.EncryptString(preference.getName(), true));
            SQLUtil.SafeSetString(pStmt, 2, Encryptor.EncryptString(preference.getSetting(), true));
            SQLUtil.SafeSetString(pStmt, 3, Encryptor.EncryptString(preference.getOption(), true));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }

    }

    public boolean UpdateSysOp(SysOps preference) throws SQLException {
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
                + "`name` = ?, "
                + "`setting` = ?, "
                + "`option` = ? "
                + "WHERE `id` = " + preference.getId();
        PreparedStatement pStmt = con.prepareStatement(query);

        try {
            //pStmt.setString(1, Encryptor.EncryptString(preference.getName(), true));
            SQLUtil.SafeSetString(pStmt, 1, Encryptor.EncryptString(preference.getName(), true));
            SQLUtil.SafeSetString(pStmt, 2, Encryptor.EncryptString(preference.getSetting(), true));
            SQLUtil.SafeSetString(pStmt, 3, Encryptor.EncryptString(preference.getOption(), true));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException sqlException) {
            String message = sqlException.toString();
            System.out.println(message);
            return false;
        }


    }

    public String getString(String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String eName = Encryptor.EncryptString(name, true);
            String setting = null;
            

            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(stmt, 1, eName);

            //Clear eName memory
            eName = "000000000000000000000";
            name = "0000000000000000000000";
            eName = null;
            name = null;
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (Encryptor.EncryptString("STRING", true).equals(rs.getString("option"))) {
                    setting = rs.getString("setting");
                }
            }

            rs.close();
            stmt.close();

            return Encryptor.EncryptString(setting, false);
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Integer getInteger(String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String eName = Encryptor.EncryptString(name, true);
            Integer setting = null;

            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(stmt, 1, eName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String option = rs.getString("option");
                if (Encryptor.EncryptString("INT", true).equals(option))
                {
                    String temp = rs.getString("setting");
                    temp = Encryptor.EncryptString(temp, false);
                    setting = Integer.parseInt(temp);
                }
            }

            rs.close();
            stmt.close();

            return setting;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Boolean getBoolean(String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String eName = Encryptor.EncryptString(name, true);
            Boolean setting = null;

            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(stmt, 1, eName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (Encryptor.EncryptString("BOOL", true).equals(rs.getString("option"))) {
                    String temp = rs.getString("setting");
                    temp = Encryptor.EncryptString(temp, false);
                    setting = Boolean.parseBoolean(temp);
                }
            }

            rs.close();
            stmt.close();

            return setting;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public SysOps GetSysOp(String name)
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
            String eName = Encryptor.EncryptString(name, true);
            SysOps pref = new SysOps();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(stmt, 1, eName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pref.setId(rs.getInt("id"));
                pref.setName(Encryptor.EncryptString(rs.getString("name"), false));
                pref.setSetting(Encryptor.EncryptString(rs.getString("setting"), false));
                pref.setOption(Encryptor.EncryptString(rs.getString("option"), false));
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
    
    public SysOps GetSysOpByID(int id)
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
            SysOps pref = new SysOps();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `name` = ?";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(stmt, 1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                pref.setId(rs.getInt("id"));
                pref.setName(Encryptor.EncryptString(rs.getString("name"), false));
                pref.setSetting(Encryptor.EncryptString(rs.getString("setting"), false));
                pref.setOption(Encryptor.EncryptString(rs.getString("option"), false));
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

    public HashMap<String, SysOps> getAllSysOps() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            HashMap<String, SysOps> map = new HashMap<String, SysOps>();
            SysOps pref = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                pref = new SysOps();
                pref.setId(rs.getInt("id"));
                pref.setName(Encryptor.EncryptString(rs.getString("name"), false));
                pref.setSetting(Encryptor.EncryptString(rs.getString("setting"), false));
                pref.setOption(Encryptor.EncryptString(rs.getString("option"), false));
                map.put(pref.getName(), pref);
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
    
    public HashMap<Integer, SysOps> getAllEncryptedSysOps() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            HashMap<Integer, SysOps> map = new HashMap<Integer, SysOps>();
            SysOps pref = null;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                pref = new SysOps();
                pref.setId(rs.getInt("id"));
                pref.setName(rs.getString("name"));
                pref.setSetting(rs.getString("setting"));
                pref.setOption(rs.getString("option"));
                map.put(rs.getInt("id"), pref);
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
    
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    private SysOps SetSysOpsFromResultSet(SysOps ops, ResultSet rs) throws SQLException{
        ops.setId(rs.getInt("id"));
        ops.setName(Encryptor.EncryptString(rs.getString("name"), false));
        ops.setSetting(Encryptor.EncryptString(rs.getString("setting"), false));
        ops.setOption(Encryptor.EncryptString(rs.getString("option"), false));
        
        return ops;
    }
    
    private PreparedStatement SetStatementFromSysOps(SysOps ops, PreparedStatement pStmt) throws SQLException{
        SQLUtil.SafeSetString(pStmt, 1, Encryptor.EncryptString(ops.getName(), true));
        SQLUtil.SafeSetString(pStmt, 2, Encryptor.EncryptString(ops.getSetting(), true));
        SQLUtil.SafeSetString(pStmt, 3, Encryptor.EncryptString(ops.getOption(), true));
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
