package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.UserGroupPreferences;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date 10/23/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */
public class UserGroupPreferencesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`userGroupPreferences`";

    private final ArrayList<String> fields = new ArrayList<>();

    public UserGroupPreferencesDAO() {
        fields.add("preferenceValueMapId");
        fields.add("userGroupId");
        fields.add("userId");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        UserGroupPreferences ugPref = (UserGroupPreferences) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromUserGroupPreferences(ugPref, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        UserGroupPreferences ugPref = (UserGroupPreferences) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = GenerateUpdateStatement(fields) + " WHERE `idUserGroupPreferences` = "
                    + ugPref.getIdUserGroupPreferences();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromUserGroupPreferences(ugPref, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::Update - " + ex.toString());
            return false;
        }
    }

    public Boolean Delete(int idUserGroupPreferences)
    {
        int affected = 0;
        try
        {
            String query = "DELETE FROM "+table+" WHERE `idUserGroupPreferences` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, idUserGroupPreferences);
            affected = pStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::Delete - " + ex.toString());
        }
        return (affected > 0);
    }

    @Override
    public Serializable getByID(Integer idUserGroupPreferences) {
        try{
            return GetUserGroupPreferencesById(idUserGroupPreferences);
        } catch (SQLException ex){
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::getByID - " + ex.toString());
            return false;
        }
    }

    public boolean InsertUserGroupPreferences(UserGroupPreferences ugPref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + "`preferenceValueMapId`, "
                    + "`userGroupId`, "
                    + "`userId`) "
                    + "values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, ugPref.getPreferenceValueMapId());
            SQLUtil.SafeSetInteger(pStmt, 2, ugPref.getUserGroupId());
            SQLUtil.SafeSetInteger(pStmt, 3, ugPref.getUserId());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::InsertUserGroupPreferences - " + ex.toString());
            return false;
        }
    }

    public boolean UpdateUserGroupPreferences(UserGroupPreferences ugPref) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET "
                    + "`preferenceValueMapId` = ?, "
                    + "`userGroupId` = ?, "
                    + "`userId` = ? "
                    + "WHERE `idUserGroupPreferences` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, ugPref.getPreferenceValueMapId());
            SQLUtil.SafeSetInteger(pStmt, 2, ugPref.getUserGroupId());
            SQLUtil.SafeSetInteger(pStmt, 3, ugPref.getUserId());
            SQLUtil.SafeSetInteger(pStmt, 4, ugPref.getIdUserGroupPreferences());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::UpdateUserGroupPreferences - " + ex.toString());
            return false;
        }
    }

    public UserGroupPreferences GetUserGroupPreferencesById(int idUserGroupPreferences) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref = new UserGroupPreferences();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idUserGroupPreferences` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, idUserGroupPreferences);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetUserGroupPreferencesFromResultSet(ugPref, rs);
            }

            rs.close();
            pStmt.close();

            return ugPref;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesById - " + ex.toString());
            return null;
        }
    }

    public List<UserGroupPreferences> GetUserGroupPreferencesByValueMap(Integer preferenceValueMapId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `preferenceValueMapId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, preferenceValueMapId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByValueMap - " + ex.toString());
            return null;
        }
    }

    public List<UserGroupPreferences> GetUserGroupPreferencesByKeyAndUserGroup(String key, Integer userGroupId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "INNER JOIN `preferenceKeys` ON `preferenceKey` = ? "
                    + "WHERE `userGroupId` = ? AND `userID` IS NULL";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, key);
            SQLUtil.SafeSetInteger(pStmt, 2, userGroupId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByKeyAndUserGroup - " + ex.toString());
            return null;
        }
    }
    
    public List<UserGroupPreferences> GetUserGroupPreferencesByUserGroup(Integer userGroupId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `userGroupId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, userGroupId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByUserGroup - " + ex.toString());
            return null;
        }
    }

    public List<UserGroupPreferences> GetUserGroupPreferencesByKeyAndUser(String key, Integer userId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "INNER JOIN `preferenceValueMap` ON `preferenceValueMapId` = `idPreferenceValueMap` "
                    + "INNER JOIN `preferenceKeys` ON `preferenceKeyId` = `idPreferenceKeys` "
                    + "WHERE `preferenceKey` = ? "
                    + "AND `userId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, key);
            SQLUtil.SafeSetInteger(pStmt, 2, userId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByKeyAndUser - " + ex.toString());
            return null;
        }
    }

    public List<UserGroupPreferences> GetUserGroupPreferencesByValueAndUser(String value, Integer userId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "INNER JOIN `preferenceValueMap` ON `preferenceValueMapId` = `idPreferenceValueMap` "
                    + "INNER JOIN `preferenceValues` ON `preferenceValueId` = `idPreferenceValues` "
                    + "WHERE `value` = ? "
                    + "AND `userId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, value);
            SQLUtil.SafeSetInteger(pStmt, 2, userId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByValueAndUser - " + ex.toString());
            return null;
        }
    }
    public List<UserGroupPreferences> GetUserGroupPreferencesByUser(Integer userId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `userId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, userId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetUserGroupPreferencesByUser - " + ex.toString());
            return null;
        }
    }

    public List<UserGroupPreferences> GetAllUserGroupPreferences() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroupPreferences ugPref;
        try {
            List<UserGroupPreferences> ugPrefList = new ArrayList<UserGroupPreferences>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ugPref = new UserGroupPreferences();

                SetUserGroupPreferencesFromResultSet(ugPref, rs);

                ugPrefList.add(ugPref);
            }

            rs.close();
            pStmt.close();

            return ugPrefList;
        } catch (Exception ex) {
            Logger.getLogger(UserGroupPreferences.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupPreferencesDAO::GetAllUserGroupPreferences - " + ex.toString());
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

    private UserGroupPreferences SetUserGroupPreferencesFromResultSet(UserGroupPreferences ugPref, ResultSet rs) throws SQLException {
        ugPref.setIdUserGroupPreferences(rs.getInt("idUserGroupPreferences"));
        ugPref.setPreferenceValueMapId(rs.getInt("preferenceValueMapId"));
        ugPref.setUserGroupId(rs.getInt("userGroupId"));
        ugPref.setUserId(rs.getInt("userId"));

        return ugPref;
    }

    private PreparedStatement SetStatementFromUserGroupPreferences(UserGroupPreferences ugPref, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, ugPref.getPreferenceValueMapId());
        SQLUtil.SafeSetInteger(pStmt, 2, ugPref.getUserGroupId());
        SQLUtil.SafeSetInteger(pStmt, 3, ugPref.getUserId());

        return pStmt;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        UserGroupPreferences userGroupPref = (UserGroupPreferences) obj;
        int userGroupPrefId = userGroupPref.getIdUserGroupPreferences();
        return Delete(userGroupPrefId);
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
