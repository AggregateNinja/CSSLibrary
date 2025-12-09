/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.LoginLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/29/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class LoginLogDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`loginLog`";
    /**
     * All fields except idloginLog
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public LoginLogDAO() {
        fields.add("idUsers");
        fields.add("login");
        fields.add("created");
    }

    public boolean InsertLoginLog(LoginLog log) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, log.getIdUsers());
            SQLUtil.SafeSetBoolean(pStmt, 2, log.getLogin());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(log.getCreated()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateLoginLog(LoginLog log) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateUpdateStatement(fields);

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, log.getIdUsers());
            SQLUtil.SafeSetBoolean(pStmt, 2, log.getLogin());
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(log.getCreated()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public ArrayList<LoginLog> GetUserHistoryByID(int userId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<LoginLog> list = new ArrayList<LoginLog>();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idUser` = " + userId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                LoginLog log = new LoginLog();
                log = LogFromResultSet(log, rs);
                list.add(log);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<LoginLog> GetUserHistoryByIDAndDateRange(int userId, java.util.Date from, java.util.Date to) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<LoginLog> list = new ArrayList<LoginLog>();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idUser` = " + userId + " "
                    + "AND `created` >= '" + Convert.ToSQLDateTime(from) + "' "
                    + "AND `created` <= '" + Convert.ToSQLDateTime(to) + "'";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                LoginLog log = new LoginLog();
                log = LogFromResultSet(log, rs);
                list.add(log);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<LoginLog> GetHistoryByDateRange(java.util.Date from, java.util.Date to) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<LoginLog> list = new ArrayList<LoginLog>();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `created` >= '" + Convert.ToSQLDateTime(from) + "' "
                    + "AND `created` <= '" + Convert.ToSQLDateTime(to) + "'";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                LoginLog log = new LoginLog();
                log = LogFromResultSet(log, rs);
                list.add(log);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public LoginLog GetLastLogByUserID(int userId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            LoginLog log = new LoginLog();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idUser` = " + userId + " "
                    + "ORDER BY `created` DESC LIMIT 1";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                log = LogFromResultSet(log, rs);
            }

            rs.close();
            stmt.close();

            return log;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public LoginLog LogFromResultSet(LoginLog log, ResultSet rs) throws SQLException {
        log.setIdloginLog(rs.getInt("idloginLog"));
        log.setIdUsers(rs.getInt("idUsers"));
        log.setLogin(rs.getBoolean("login"));
        log.setCreated(rs.getDate("created"));
        return log;
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
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
