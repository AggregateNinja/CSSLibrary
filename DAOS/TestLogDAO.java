/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.TestLog;
import DOS.Tests;
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
 * @since Build {insert version here} 11/04/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class TestLogDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`test_log`";
    /**
     * All fields except idOrders
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public TestLogDAO() {
        fields.add("EventType");
        fields.add("ChangeType");
        fields.add("TestNumber");
        fields.add("TestName");
        fields.add("OldTestId");
        fields.add("NewTestID");
        fields.add("PreLow");
        fields.add("PreHigh");
        fields.add("PreAlertLow");
        fields.add("PreAlertHigh");
        fields.add("PreCritLow");
        fields.add("PreCritHigh");
        fields.add("PreRemark");
        fields.add("Comment");
        fields.add("User");
        fields.add("Created");
    }

    public boolean InsertTestLog(TestLog log) throws SQLException {
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

            SQLUtil.SafeSetString(pStmt, 1, log.getEventType());
            SQLUtil.SafeSetString(pStmt, 2, log.getChangeType());
            SQLUtil.SafeSetInteger(pStmt, 3, log.getTestNumber());
            SQLUtil.SafeSetString(pStmt, 4, log.getTestName());
            SQLUtil.SafeSetInteger(pStmt, 5, log.getOldTestID());
            SQLUtil.SafeSetInteger(pStmt, 6, log.getNewTestID());
            SQLUtil.SafeSetRangeDouble(pStmt, 7, log.getPreLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 8, log.getPreHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 9, log.getPreAlertLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 10, log.getPreAlertHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 11, log.getPreCritLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 12, log.getPreCritHigh());
            SQLUtil.SafeSetInteger(pStmt, 13, log.getPreRemark());
            SQLUtil.SafeSetString(pStmt, 14, log.getComment());
            SQLUtil.SafeSetInteger(pStmt, 15, log.getUser());
            SQLUtil.SafeSetTimeStamp(pStmt, 16, Convert.ToSQLDateTime(log.getCreated()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean UpdateTestLog(TestLog log) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idtest_log` = " + log.getIdtestLog();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, log.getEventType());
            SQLUtil.SafeSetString(pStmt, 2, log.getChangeType());
            SQLUtil.SafeSetInteger(pStmt, 3, log.getTestNumber());
            SQLUtil.SafeSetString(pStmt, 4, log.getTestName());
            SQLUtil.SafeSetInteger(pStmt, 5, log.getOldTestID());
            SQLUtil.SafeSetInteger(pStmt, 6, log.getNewTestID());
            SQLUtil.SafeSetRangeDouble(pStmt, 7, log.getPreLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 8, log.getPreHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 9, log.getPreAlertLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 10, log.getPreAlertHigh());
            SQLUtil.SafeSetRangeDouble(pStmt, 11, log.getPreCritLow());
            SQLUtil.SafeSetRangeDouble(pStmt, 12, log.getPreCritHigh());
            SQLUtil.SafeSetInteger(pStmt, 13, log.getPreRemark());
            SQLUtil.SafeSetString(pStmt, 14, log.getComment());
            SQLUtil.SafeSetInteger(pStmt, 15, log.getUser());
            SQLUtil.SafeSetTimeStamp(pStmt, 16, Convert.ToSQLDateTime(log.getCreated()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public ArrayList<TestLog> GetTestHistoryByNumber(int num) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<TestLog> list = new ArrayList<TestLog>();

            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "WHERE `TestNumber` = " + num;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                TestLog log = new TestLog();
                log = SetTestLogFromResultSet(log, rs);

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

    public TestLog SetTestLogFromResultSet(TestLog log, ResultSet rs) throws SQLException {
        log.setIdtestLog(rs.getInt("idtest_log"));
        log.setEventType(rs.getString("EventType"));
        log.setChangeType(rs.getString("ChangeType"));
        log.setTestNumber(rs.getInt("TestNumber"));
        log.setTestName(rs.getString("TestName"));
        log.setOldTestID(rs.getInt("OldTestId"));
        log.setNewTestID(rs.getInt("NewTestID"));
        log.setPreLow(rs.getDouble("PreLow"));
        log.setPreHigh(rs.getDouble("PreHigh"));
        log.setPreAlertLow(rs.getDouble("PreAlertLow"));
        log.setPreAlertHigh(rs.getDouble("PreAlertHigh"));
        log.setPreCritLow(rs.getDouble("PreCritLow"));
        log.setPreCritHigh(rs.getDouble("PreCritHigh"));
        log.setPreRemark(rs.getInt("PreRemark"));
        log.setComment(rs.getString("Comment"));
        log.setUser(rs.getInt("User"));
        log.setCreated(rs.getTimestamp("Created"));

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
