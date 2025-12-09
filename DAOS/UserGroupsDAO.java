/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.UserGroups;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/23/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class UserGroupsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`userGroups`";

    private final ArrayList<String> fields = new ArrayList<>();

    public UserGroupsDAO() {
        fields.add("name");
        fields.add("securityLevel");
        fields.add("description");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        UserGroups group = (UserGroups) obj;
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
            SetStatementFromUserGroup(group, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserGroups.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupsDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        UserGroups group = (UserGroups) obj;
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }

            String stmt = GenerateUpdateStatement(fields) + " WHERE `iduserGroups` = "
                    + group.getIduserGroups();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromUserGroup(group, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UserGroups.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupsDAO::Update - " + ex.toString());
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
    public Serializable getByID(Integer ID) {
        try{
            return GetUserGroupByID(ID);
        } catch (SQLException ex){
            Logger.getLogger(UserGroups.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UserGroupsDAO::getByID - " + ex.toString());
            return false;
        }
    }

    public boolean InsertUserGroup(UserGroups ug) throws SQLException {
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
                    + "`name`, "
                    + "`securityLevel`, "
                    + "`description`) "
                    + "values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, ug.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, ug.getSecurityLevel());
            SQLUtil.SafeSetString(pStmt, 3, ug.getDescription());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateUserGroup(UserGroups ug) throws SQLException {
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
                    + "`name` = ?, "
                    + "`securityLevel` = ?, "
                    + "`description` = ? "
                    + "WHERE `iduserGroups` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, ug.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, ug.getSecurityLevel());
            SQLUtil.SafeSetString(pStmt, 3, ug.getDescription());
            SQLUtil.SafeSetInteger(pStmt, 4, ug.getIduserGroups());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public UserGroups GetUserGroupByID(int id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroups ug = new UserGroups();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `iduserGroups` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, id);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetUserGroupFromResultSet(ug,rs);
            }

            rs.close();
            pStmt.close();

            return ug;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public UserGroups GetUserGroupByName(String name) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroups ug = new UserGroups();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, name);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetUserGroupFromResultSet(ug,rs);
            }

            rs.close();
            pStmt.close();

            return ug;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<UserGroups> GetAllUserGroups() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        UserGroups ug;

        try {
            ArrayList<UserGroups> uList = new ArrayList<UserGroups>();
            String query = "SELECT * FROM " + table;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ug = new UserGroups();

                SetUserGroupFromResultSet(ug,rs);

                uList.add(ug);
            }

            rs.close();
            pStmt.close();

            return uList;
        } catch (Exception ex) {
            System.out.println(ex.toString());
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

    private UserGroups SetUserGroupFromResultSet(UserGroups group, ResultSet rs) throws SQLException {
        group.setIduserGroups(rs.getInt("iduserGroups"));
        group.setName(rs.getString("name"));
        group.setSecurityLevel(rs.getInt("securityLevel"));
        group.setDescription(rs.getString("description"));

        return group;
    }

    private PreparedStatement SetStatementFromUserGroup(UserGroups group, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, group.getName());
        SQLUtil.SafeSetInteger(pStmt, 2, group.getSecurityLevel());
        SQLUtil.SafeSetString(pStmt, 3, group.getDescription());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
