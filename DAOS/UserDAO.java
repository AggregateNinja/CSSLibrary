/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import java.sql.Connection;
import DOS.Users;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 05/13/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class UserDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`users`";
    
    public enum ResultType
    {
        All,
        ActiveOnly,
        InactiveOnly
    }

    public boolean InsertUser(Users user) throws SQLException {
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
                    + "`logon`, "
                    + "`password`, "
                    + "`lastLogon`, "
                    + "`isAdmin`, "
                    + "`ugroup`, "
                    + "`position`, "
                    + "`createdBy`, "
                    + "`created`, "
                    + "`employeeId`, "
                    + "`active`, "
                    + "`changePassword` ) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, user.getLogon());
            SQLUtil.SafeSetString(pStmt, 2, user.getPassword());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(user.getLastLogon()));
            
            // If usergroup == 0 (administrator group), set IsAdmin to true.
            // Could have the application check this directly, but don't want
            // to leave orphaned isAdmin field in the table that could
            // potentially yield incorrect data.
            if (IsAdminGroup(user)) SQLUtil.SafeSetInteger(pStmt, 4, 1);
            else SQLUtil.SafeSetInteger(pStmt, 4, 0);
            
            SQLUtil.SafeSetInteger(pStmt, 5, user.getUGroups());
            SQLUtil.SafeSetString(pStmt, 6, user.getPosition());
            SQLUtil.SafeSetInteger(pStmt, 7, user.getCreatedBy());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDateTime(user.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, user.getEmployeeId());
            SQLUtil.SafeSetBoolean(pStmt, 10, Boolean.TRUE);
            SQLUtil.SafeSetBoolean(pStmt, 11, Boolean.TRUE);

            pStmt.executeUpdate();

            pStmt.close();

            return true;

        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public Integer InsertUserGetId(Users user)
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
            String stmt = "INSERT INTO " + table + "("
                    + "`logon`, "
                    + "`password`, "
                    + "`lastLogon`, "
                    + "`isAdmin`, "
                    + "`ugroup`, "
                    + "`position`, "
                    + "`createdBy`, "
                    + "`created`, "
                    + "`employeeId`, "
                    + "`active`, "
                    + "`changePassword` ) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

            SQLUtil.SafeSetString(pStmt, 1, user.getLogon());
            SQLUtil.SafeSetString(pStmt, 2, user.getPassword());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(user.getLastLogon()));
            
            // If usergroup == 0 (administrator group), set IsAdmin to true.
            // Could have the application check this directly, but don't want
            // to leave orphaned isAdmin field in the table that could
            // potentially yield incorrect data.
            if (IsAdminGroup(user)) SQLUtil.SafeSetInteger(pStmt, 4, 1);
            else SQLUtil.SafeSetInteger(pStmt, 4, 0);
            
            SQLUtil.SafeSetInteger(pStmt, 5, user.getUGroups());
            SQLUtil.SafeSetString(pStmt, 6, user.getPosition());
            SQLUtil.SafeSetInteger(pStmt, 7, user.getCreatedBy());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDateTime(user.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, user.getEmployeeId());
            SQLUtil.SafeSetBoolean(pStmt, 10, Boolean.TRUE);
            SQLUtil.SafeSetBoolean(pStmt, 11, Boolean.TRUE);

            pStmt.executeUpdate();

            Integer idUsers;            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    idUsers = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }            
            
            pStmt.close();
            return idUsers;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }        
    }
    
    /**
     * Returns whether the user belongs to group 1: administrators
     * @param user Users object
     * @return Boolean whether member of administrator group.
     */
    private boolean IsAdminGroup(Users user)
    {
        // Usergroup 1 is always admin group
        try
        {
            if (user.getUGroups() == 1) return true;
        }
        catch (Exception ex)
        {}
        return false;
    }

    public boolean UpdateUser(Users user) throws SQLException {
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
                    + "`logon` = ?, "
                    + "`password` = ?, "
                    + "`lastLogon` = ?, "
                    + "`isAdmin` = ?, "
                    + "`ugroup` = ?, "
                    + "`position` = ?, "
                    + "`createdBy` = ?, "
                    + "`created` = ?, "
                    + "`employeeId` = ?, "
                    + "`active` = ?, "
                    + "`changePassword` = ? "
                    + "WHERE `idUser` = " + user.getIdUser();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, user.getLogon());
            SQLUtil.SafeSetString(pStmt, 2, user.getPassword());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(user.getLastLogon()));
            
            // If usergroup == 0 (administrator group), set IsAdmin to true.
            // Could have the application check this directly, but don't want
            // to leave orphaned isAdmin field in the table that could
            // potentially yield incorrect data.
            if (IsAdminGroup(user)) SQLUtil.SafeSetInteger(pStmt, 4, 1);
            else SQLUtil.SafeSetInteger(pStmt, 4, 0);

            SQLUtil.SafeSetInteger(pStmt, 5, user.getUGroups());
            SQLUtil.SafeSetString(pStmt, 6, user.getPosition());
            SQLUtil.SafeSetInteger(pStmt, 7, user.getCreatedBy());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDateTime(user.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, user.getEmployeeId());
            SQLUtil.SafeSetBoolean(pStmt, 10, user.isActive());
            SQLUtil.SafeSetBoolean(pStmt, 11, user.isChangePassword());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public Users GetUserByLogon(String logon) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Users user = new Users();
        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `logon` = ? "
                    + "AND `active` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.setString(1, logon);
            pStmt.setBoolean(2, Boolean.TRUE);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                user.setIdUser(rs.getInt("idUser"));
                user.setLogon(rs.getString("logon"));
                user.setPassword(rs.getString("password"));
                user.setLastLogon(rs.getTimestamp("lastLogon"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                user.setUGroups(rs.getInt("ugroup"));
                user.setPosition(rs.getString("position"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreated(rs.getTimestamp("created"));
                user.setEmployeeId(rs.getInt("employeeId"));
                user.setActive(rs.getBoolean("active"));
                user.setChangePassword(rs.getBoolean("changePassword"));
            }

            rs.close();
            pStmt.close();

            return user;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Users GetUserByID(int ID) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Users user = new Users();

        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `idUser` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.setInt(1, ID);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                user.setIdUser(rs.getInt("idUser"));
                user.setLogon(rs.getString("logon"));
                user.setPassword(rs.getString("password"));
                user.setLastLogon(rs.getTimestamp("lastLogon"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                user.setUGroups(rs.getInt("ugroup"));
                user.setPosition(rs.getString("position"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreated(rs.getTimestamp("created"));
                user.setEmployeeId(rs.getInt("employeeId"));
                user.setActive(rs.getBoolean("active"));
                user.setChangePassword(rs.getBoolean("changePassword"));
            }

            rs.close();
            pStmt.close();

            return user;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public Users GetUserByEmployeeID(int ID, ResultType resultType) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Users user = new Users();

        try {
            String stmt = "SELECT * FROM " + table + " "
                    + "WHERE `employeeId` = ? ";

            if (resultType == ResultType.ActiveOnly)
            {
                stmt += " AND `active` = 1";
            }
            else if (resultType == ResultType.InactiveOnly)
            {
                stmt += " AND `active` = 0";
            }
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt.setInt(1, ID);
            
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                user.setIdUser(rs.getInt("idUser"));
                user.setLogon(rs.getString("logon"));
                user.setPassword(rs.getString("password"));
                user.setLastLogon(rs.getTimestamp("lastLogon"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                user.setUGroups(rs.getInt("ugroup"));
                user.setPosition(rs.getString("position"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreated(rs.getTimestamp("created"));
                user.setEmployeeId(rs.getInt("employeeId"));
                user.setActive(rs.getBoolean("active"));
                user.setChangePassword(rs.getBoolean("changePassword"));
            }

            rs.close();
            pStmt.close();

            return user;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Users> GetAllUsers() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Users user;

        try {
            ArrayList<Users> uList = new ArrayList<Users>();
            String query = "SELECT * FROM " + table + " WHERE `active` = " + Boolean.TRUE;
            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                user = new Users();

                user.setIdUser(rs.getInt("idUser"));
                user.setLogon(rs.getString("logon"));
                user.setPassword(rs.getString("password"));
                user.setLastLogon(rs.getTimestamp("lastLogon"));
                user.setIsAdmin(rs.getInt("isAdmin"));
                user.setUGroups(rs.getInt("ugroup"));
                user.setPosition(rs.getString("position"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreated(rs.getTimestamp("created"));
                user.setEmployeeId(rs.getInt("employeeId"));
                user.setActive(rs.getBoolean("active"));
                user.setChangePassword(rs.getBoolean("changePassword"));

                uList.add(user);
            }

            rs.close();
            pStmt.close();

            return uList;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * DEPRECATED: Use EmployeeBL method, as it does logging for you
     * @param id
     * @return
     * @deprecated
     */
    @Deprecated
    public boolean DeactivateUser(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET `active` = ? WHERE `idUser` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetBoolean(pStmt, 1, Boolean.FALSE);
            SQLUtil.SafeSetInteger(pStmt, 2, id);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateChangePassword(int ID, boolean change) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE " + table + " SET `changePassword` = ? WHERE `idUser` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetBoolean(pStmt, 1, Boolean.TRUE);
            SQLUtil.SafeSetInteger(pStmt, 2, ID);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertUser((Users)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateUser((Users)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * DEPRECATED: Use EmployeeBL method, as it does logging for you
     * @param obj
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    public Boolean Delete(Serializable obj)
    {
        return DeactivateUser(((Users)obj).getIdUser());
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetUserByID(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `users`.`idUser`,\n"
                + "    `users`.`logon`,\n"
                + "    `users`.`password`,\n"
                + "    `users`.`lastLogon`,\n"
                + "    `users`.`isAdmin`,\n"
                + "    `users`.`ugroup`,\n"
                + "    `users`.`position`,\n"
                + "    `users`.`createdBy`,\n"
                + "    `users`.`created`,\n"
                + "    `users`.`employeeId`,\n"
                + "    `users`.`active`,\n"
                + "    `users`.`changePassword`\n"
                + "FROM `css`.`users` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
