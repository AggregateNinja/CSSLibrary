/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseInstRes;
import DOS.InstRes_1;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import Utility.WriteTextFile;
//import com.mysql.jdbc.MysqlErrorNumbers;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 12/12/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class InstResDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final int RETRY_ATTEMPTS = 3;
    private final int CONNECTION_VALIDATION_TIMEOUT = 3; // seconds
    
    @Deprecated
    public boolean InsertLog_Old(String table, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT IGNORE INTO `" + table + "` ("
                    + "`accession`, "
                    + "`identifier`, "
                    + "`name`, "
                    + "`result`, "
                    + "`comment`, "
                    + "`units`, "
                    + "`range`, "
                    + "`specimenType`, "
                    + "`dilution`, "
                    + "`interpretation`, "
                    + "`iteration`, "
                    + "`postedDate`, "
                    + "`user`, "
                    + "`posted`, "
                    + "`fileName`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 2, res.getIdentifier());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());
            SQLUtil.SafeSetString(pStmt, 4, res.getResult());
            SQLUtil.SafeSetString(pStmt, 5, res.getComment());
            SQLUtil.SafeSetString(pStmt, 6, res.getUnits());
            SQLUtil.SafeSetString(pStmt, 7, res.getRange());
            SQLUtil.SafeSetString(pStmt, 8, res.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 9, res.getDilution());
            SQLUtil.SafeSetString(pStmt, 10, res.getInterpretation());
            SQLUtil.SafeSetString(pStmt, 11, res.getIteration());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, Convert.ToSQLDateTime(res.getPostedDate()));
            SQLUtil.SafeSetString(pStmt, 13, res.getUser());
            SQLUtil.SafeSetBoolean(pStmt, 14, res.getPosted());
            SQLUtil.SafeSetString(pStmt, 15, res.getFileName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Adding Instrument Data: " + ex.toString());
            return false;
        }
    }

    public boolean InsertLog(String table, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO `" + table + "` ("
                    + "`accession`, "
                    + "`identifier`, "
                    + "`name`, "
                    + "`result`, "
                    + "`comment`, "
                    + "`units`, "
                    + "`range`, "
                    + "`specimenType`, "
                    + "`dilution`, "
                    + "`interpretation`, "
                    + "`iteration`, "
                    + "`postedDate`, "
                    + "`user`, "
                    + "`posted`, "
                    + "`fileName`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
                    + "ON DUPLICATE KEY UPDATE "
                    + "`result`= IF(`posted` = TRUE, `result`, VALUES(result)), "
                    + "`interpretation`= IF(`posted` = TRUE, `interpretation`, VALUES(interpretation))";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 2, res.getIdentifier());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());
            SQLUtil.SafeSetString(pStmt, 4, res.getResult());
            SQLUtil.SafeSetString(pStmt, 5, res.getComment());
            SQLUtil.SafeSetString(pStmt, 6, res.getUnits());
            SQLUtil.SafeSetString(pStmt, 7, res.getRange());
            SQLUtil.SafeSetString(pStmt, 8, res.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 9, res.getDilution());
            SQLUtil.SafeSetString(pStmt, 10, res.getInterpretation());
            SQLUtil.SafeSetString(pStmt, 11, res.getIteration());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, Convert.ToSQLDateTime(res.getPostedDate()));
            SQLUtil.SafeSetString(pStmt, 13, res.getUser());
            SQLUtil.SafeSetBoolean(pStmt, 14, res.getPosted());
            SQLUtil.SafeSetString(pStmt, 15, res.getFileName());

            boolean success = ExecuteUpdate(pStmt, 0);
            pStmt.close();

            return success;
        }
        catch (SQLException ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println("InstResDAO.InsertLog Exception: " + sStackTrace);
            
            
            String error = "InstResDAO::ExecuteUpdate: Unable to perform update";
            if (table != null)
            {
                error += " to table " + table;
            }
            if (res != null && res.getAccession() != null)
            {
                error += " for accession " + res.getAccession();
            }
            System.out.println(error);
            return false;
        }
    }

    /**
     * InsertLog with retries for connection checking and validation as well as logging of errors.
     * @param table
     * @param res
     * @param writeLog
     * @return true if the inst result was successfully inserted, false otherwise
     * @throws SQLException 
     */
    public boolean InsertLog(String table, BaseInstRes res, WriteTextFile writeLog) throws SQLException
    {
        for (int i = 0; i < RETRY_ATTEMPTS; i++)
        {
            try
            {
                if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT)))
                {
                    this.con = CheckDBConnection.Check(this.dbs, this.con);
                }
                else
                {
                    break;
                }
            }
            catch (SQLException sex)
            {
                System.out.println(sex.toString());
                if (writeLog != null)
                {
                    writeLog.write("Connection attempt " + i + " failed : " + sex.toString());
                }
                if (i == RETRY_ATTEMPTS)
                {
                    return false;
                }
            }
        }

        try
        {
            String stmt = "INSERT INTO `" + table + "` ("
                    + "`accession`, "
                    + "`identifier`, "
                    + "`name`, "
                    + "`result`, "
                    + "`comment`, "
                    + "`units`, "
                    + "`range`, "
                    + "`specimenType`, "
                    + "`dilution`, "
                    + "`interpretation`, "
                    + "`iteration`, "
                    + "`postedDate`, "
                    + "`user`, "
                    + "`posted`, "
                    + "`fileName`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
                    + "ON DUPLICATE KEY UPDATE "
                    + "`result`= IF(`posted` = TRUE, `result`, VALUES(result)), "
                    + "`interpretation`= IF(`posted` = TRUE, `interpretation`, VALUES(interpretation))";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 2, res.getIdentifier());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());
            SQLUtil.SafeSetString(pStmt, 4, res.getResult());
            SQLUtil.SafeSetString(pStmt, 5, res.getComment());
            SQLUtil.SafeSetString(pStmt, 6, res.getUnits());
            SQLUtil.SafeSetString(pStmt, 7, res.getRange());
            SQLUtil.SafeSetString(pStmt, 8, res.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 9, res.getDilution());
            SQLUtil.SafeSetString(pStmt, 10, res.getInterpretation());
            SQLUtil.SafeSetString(pStmt, 11, res.getIteration());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, Convert.ToSQLDateTime(res.getPostedDate()));
            SQLUtil.SafeSetString(pStmt, 13, res.getUser());
            SQLUtil.SafeSetBoolean(pStmt, 14, res.getPosted());
            SQLUtil.SafeSetString(pStmt, 15, res.getFileName());

            boolean success = ExecuteUpdate(pStmt, 0);
            pStmt.close();

            return success;
        }
        catch (SQLException ex)
        {
            String error = "InstResDAO::ExecuteUpdate: Unable to perform update";
            if (table != null)
            {
                error += " to table " + table;
            }
            if (res != null && res.getAccession() != null)
            {
                error += " for accession " + res.getAccession();
            }
            System.out.println(error);
            if (writeLog != null)
            {
                writeLog.write(error);
            }
            return false;
        }
    }
    
    /**
     * InsertLog with retries for connection checking and validation as well as logging of errors.
     * @param table
     * @param res
     * @param writeLog
     * @return true if the inst result was successfully inserted, false otherwise
     * @throws SQLException 
     */
    public boolean InsertLog(Connection suppliedConnection, String table, BaseInstRes res, WriteTextFile writeLog) throws SQLException
    {
        try
        {
            String stmt = "INSERT INTO `" + table + "` ("
                    + "`accession`, "
                    + "`identifier`, "
                    + "`name`, "
                    + "`result`, "
                    + "`comment`, "
                    + "`units`, "
                    + "`range`, "
                    + "`specimenType`, "
                    + "`dilution`, "
                    + "`interpretation`, "
                    + "`iteration`, "
                    + "`postedDate`, "
                    + "`user`, "
                    + "`posted`, "
                    + "`fileName`, "
                    + "`remarkId`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
                    + "ON DUPLICATE KEY UPDATE "
                    + "`result`= IF(`posted` = TRUE, `result`, VALUES(result)), "
                    + "`identifier`= IF(`posted` = TRUE, `identifier`, VALUES(identifier)), "
                    + "`interpretation`= IF(`posted` = TRUE, `interpretation`, VALUES(interpretation))";

            PreparedStatement pStmt = suppliedConnection.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 2, res.getIdentifier());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());
            SQLUtil.SafeSetString(pStmt, 4, res.getResult());
            SQLUtil.SafeSetString(pStmt, 5, res.getComment());
            SQLUtil.SafeSetString(pStmt, 6, res.getUnits());
            SQLUtil.SafeSetString(pStmt, 7, res.getRange());
            SQLUtil.SafeSetString(pStmt, 8, res.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 9, res.getDilution());
            SQLUtil.SafeSetString(pStmt, 10, res.getInterpretation());
            SQLUtil.SafeSetString(pStmt, 11, res.getIteration());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, Convert.ToSQLDateTime(res.getPostedDate()));
            SQLUtil.SafeSetString(pStmt, 13, res.getUser());
            SQLUtil.SafeSetBoolean(pStmt, 14, res.getPosted());
            SQLUtil.SafeSetString(pStmt, 15, res.getFileName());
            SQLUtil.SafeSetInteger(pStmt, 16, res.getRemarkId());

            boolean success = ExecuteUpdate(pStmt, 0);
            pStmt.close();

            return success;
        }
        catch (SQLException ex)
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            
            String error = "InstResDAO::ExecuteUpdate: Unable to perform update";
            if (table != null)
            {
                error += " to table " + table;
            }
            if (res != null && res.getAccession() != null)
            {
                error += " for accession " + res.getAccession();
            }
            
            System.out.println(error + ": " + sStackTrace);
            
            if (writeLog != null)
            {
                writeLog.write(error + ": " + sStackTrace);
            }
            return false;
        }
    }
    
    /**
     * Calls an execute update on a prepared statement and handles
     *  deadlock time-outs by retrying the update the number of times defined
     *  by RETRY_ATTEMPTS
     * 
     *  Returns true on success, false on failure.
     * 
     * @param pStmt
     * @param attempt Attempt number; pass in 0 from outside code
     * @return 
     */
    private boolean ExecuteUpdate(PreparedStatement pStmt, int attempt)
    {
        // If we've retried the specified number of times and still
        // cannot update, return false (unsuccessful)
        if (attempt > RETRY_ATTEMPTS)
        {
            System.out.println("Exceeded deadlock retry attempts ("
                    + RETRY_ATTEMPTS + "). Unable to execute prepared statement");
            return false;
        }
        
        try
        {
            if (pStmt != null)
            {
                pStmt.executeUpdate();
            }
        }
        catch (SQLException ex)
        {
            // Handle deadlocks by trying again until we either succeed or hit the retry maximum
            if (ex.getErrorCode() == 1213)
            {
                try
                {
                    // Give it a second and try again
                    Thread.sleep(1000);
                    ++attempt;
                    System.out.println("SQL Deadlock on update. Retry attempt #" + attempt);
                    return ExecuteUpdate(pStmt,attempt);
                }
                catch (InterruptedException iex)
                {
                    System.out.println(
                            "InstResDAO::ExecuteUpdate: InterruptedException "
                            + " thrown while waiting for deadlock retry");
                    
                    return false;
                }
            }
            else
            {
                System.out.println("InstResDAO::ExecuteUpdate: "
                        + " Unable to execute update for sql: " + pStmt.toString()
                        + " (Non-deadlock error)");
                return false;
            }
        }
        return true;
    }

    public boolean UpdateResult(String table, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`result` = ? "
                    + "WHERE `accession` = ? "
                    + "AND `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getResult());
            SQLUtil.SafeSetString(pStmt, 2, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("Failed Updating Instrument Data: " + ex.toString());
            return false;
        }
    }
    
    public boolean UpdateInterpertation(String table, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`interpretation` = ? "
                    + "WHERE `accession` = ? "
                    + "AND `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getInterpretation());
            SQLUtil.SafeSetString(pStmt, 2, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("Failed Updating Instrument Data: " + ex.toString());
            return false;
        }
    }
    
    public boolean UpdateComment(String table, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`comment` = ? "
                    + "WHERE `accession` = ? "
                    + "AND `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, res.getComment());
            SQLUtil.SafeSetString(pStmt, 2, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("Failed Updating Instrument Comment Data: " + ex.toString());
            return false;
        }
    }
    
    public boolean UpdateRemarkID(String table, int remarkID, BaseInstRes res) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`remarkId` = ? "
                    + "WHERE `accession` = ? "
                    + "AND `name` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, remarkID);
            SQLUtil.SafeSetString(pStmt, 2, res.getAccession());
            SQLUtil.SafeSetString(pStmt, 3, res.getName());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println("Failed Updating Instrument Remark ID: " + ex.toString());
            return false;
        }
    }
    
    public Set<String> GetFilenames(String table, boolean postedOnly) throws SQLException {
        Set<String> list = new HashSet<>();
        try {
            if (this.con.isClosed()) {
                this.con = CheckDBConnection.Check(this.dbs, this.con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return list;
        } 

        try {
            Statement stmt = this.con.createStatement();

            String query = "SELECT DISTINCT fileName FROM `" + table + "` WHERE `idinstresult` != 0 ";
            if (postedOnly) query = query + "AND `posted` = true ";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(rs.getString("fileName"));
            }

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } 
        return list;
    }
    
    public List<BaseInstRes> GetPostedResultsByAccession(String table, String accession) throws SQLException {
        ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return list;
        }

        try {
            BaseInstRes res = null;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `posted` = " + true + " "
                    + "AND `idinstresult` != " + 0;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                res = new InstRes_1();
                res = InstResFromResultSet(res, rs);

                list.add(res);
            }

            rs.close();
            stmt.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return list;
    }
    
    public ArrayList<BaseInstRes> GetNonPostedResults(String table) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstRes res = null;
            ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `posted` = " + false + " "
                    + "AND `idinstresult` != " + 0;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = new InstRes_1();
                res = InstResFromResultSet(res, rs);

                list.add(res);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<BaseInstRes> GetAllResults(String table) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstRes res = null;
            ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM `" + table + "`";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                res = InstResFromResultSet(res, rs);

                list.add(res);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<BaseInstRes> GetResultsFromFile(String table, String fileName) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstRes res = null;
            ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `fileName` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetString(pStmt, 1, fileName);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                res = new BaseInstRes() {};
                
                res = InstResFromResultSet(res, rs);

                list.add(res);
            }

            rs.close();
            pStmt.close();

            return list;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println("InstResDAO GetResultsFromFile Exception: " + sStackTrace);
            return null;
        }
    }
    
    public ArrayList<BaseInstRes> GetResultsFromFileAndAccession(String table, String fileName, String accession) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstRes res = null;
            ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();

            String query = "SELECT ir.*, t.units AS `testUnits` " 
                    + "FROM `" + table + "` ir "
                    + "INNER JOIN results r ON ir.postToResultId = r.idResults "
                    + "INNER JOIN tests t ON r.testId = t.idtests "
                    + "WHERE `fileName` = ? AND accession = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetString(pStmt, 1, fileName);
            SQLUtil.SafeSetString(pStmt, 2, accession);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                res = new BaseInstRes() {};
                
                //res = InstResFromResultSet(res, rs);
                res.setAccession(rs.getString("accession"));
                res.setIdentifier(rs.getString("identifier"));
                res.setName(rs.getString("name"));
                res.setResult(rs.getString("result"));
                res.setComment(rs.getString("comment"));
                
                if (rs.getString("units") != null) {
                    res.setUnits(rs.getString("units"));
                } else {
                    res.setUnits(rs.getString("testUnits"));
                }
                
                
                res.setRange(rs.getString("range"));
                res.setSpecimenType(rs.getString("specimenType"));
                res.setDilution(rs.getString("dilution"));
                res.setInterpretation(rs.getString("interpretation"));
                res.setIteration(rs.getString("iteration"));
                res.setPostedDate(rs.getTimestamp("postedDate"));
                res.setUser(rs.getString("user"));
                res.setPosted(rs.getBoolean("posted"));
                res.setFileName(rs.getString("fileName"));
                res.setCreated(rs.getTimestamp("created"));
                res.setPostToResultId(rs.getInt("postToResultId"));
                

                list.add(res);
            }

            rs.close();
            pStmt.close();

            return list;
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            System.out.println("InstResDAO GetResultsFromFile Exception: " + sStackTrace);
            return null;
        }
    }

    public ArrayList<BaseInstRes> GetNonPostedResultsFromFile(String table, String fileName) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstRes res = null;
            ArrayList<BaseInstRes> list = new ArrayList<BaseInstRes>();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `fileName` = ? "
                    + "AND `posted` = ? "
                    + "AND `idinstresult` != ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetString(pStmt, 1, fileName);
            SQLUtil.SafeSetBoolean(pStmt, 2, false);
            SQLUtil.SafeSetInteger(pStmt, 3, 0);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                res = InstResFromResultSet(res, rs);

                list.add(res);
            }

            rs.close();
            pStmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<String> GetFilesWithPendingResults(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try{
            ArrayList<String> list = new ArrayList<String>();

            String query = "SELECT DISTINCT `fileName` FROM `" + table + "` "
                    + "WHERE `posted` = ? "
                    + "AND `idinstresult` != ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetBoolean(pStmt, 1, false);
            SQLUtil.SafeSetRangeInteger(pStmt, 2, 0);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("fileName"));
            }

            rs.close();
            pStmt.close();

            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }

    public int RemoveRowsByExactAccession(String table, String accession) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `accession` = ?";

            PreparedStatement pStmt = createStatement(con, query, accession);//con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }

    /**
     * Deletes duplicate rows. It keeps the newest result row. It will not
     * delete anything that has been posted.
     *
     * @param table instRes_# table
     * @return number of rows deleted. Not always accurate because of inner join
     */
    @Deprecated
    public int CleanUpDuplicates(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE inst1 "
                    + "FROM `" + table + "` inst1, `" + table + "` inst2 "
                    + "WHERE inst1.`idinstresult` < inst2.`idinstresult` "
                    + "AND inst1.`accession` = inst2.`accession` "
                    + "AND inst1.`name` = inst2.`name` "
                    + "AND inst1.`posted` = false;";

            PreparedStatement pStmt = con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }

    public int RemoveRowsByLikeAccession(String table, String accession) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `accession` LIKE ?";

            PreparedStatement pStmt = createStatement(con, query, accession);//con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }

    /**
     * Remove from the instrument result table where the accession, name, and
     * result fields are all null.
     *
     * @param table String The table to remove the empty lines from.
     * @return int Number of deleted rows.
     */
    public int RemoveEmptyRows(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `accession` IS NULL"
                    + " AND `name` IS NULL"
                    + " AND `result` IS NULL";

            PreparedStatement pStmt = con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }

    /**
     * Remove from the instrument result table where the test name
     * fields are all null.
     *
     * @param table String The table to remove the empty lines from.
     * @return int Number of deleted rows.
     */
    public int RemoveEmptyNameRows(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `name` IS NULL";

            PreparedStatement pStmt = con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    /**
     * Update any rows with the results from their counterpart rows marked by
     * 'Accession' + 'R'
     *
     * @param table String The table to remove the empty lines from.
     * @return int Number of updated rows.
     */
    public int UpdateReruns(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "UPDATE `" + table + "` i "
                    + "INNER JOIN `" + table + "` ir "
                    + "ON CONCAT(i.`accession`, 'R') = ir.`accession` "
                    + "SET "
                    + "	i.`result` = ir.`result`, "
                    + "	i.`comment` = ir.`comment`;";

            PreparedStatement pStmt = con.prepareStatement(query);

            int updated = pStmt.executeUpdate();

            pStmt.close();

            return updated;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public InstRes_1 GetByAccessionName(String table, String accession, String name){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            InstRes_1 bir = new InstRes_1();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table
                    + "` WHERE `accession` = ? "
                    + "AND `name` = ?";
            
            stmt = createStatement(con, query, accession, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                InstResFromResultSet(bir, rs);
            }
            bir.setIdinstresult(rs.getInt("idinstresult"));

            rs.close();
            stmt.close();
            
            return bir;
        }catch (Exception sex) {
            System.out.println(sex.toString());
            return null;
        }
    }

    public int RemoveRowByAccessionAndName(String table, String accession, String name) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `accession` = ? "
                    + " AND `name` = ?";

            PreparedStatement pStmt = createStatement(con, query, accession, name);//con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public int RemoveRowsByFile(String table, String fileName){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + " WHERE `fileName` = ?";

            PreparedStatement pStmt = createStatement(con, query, fileName);//con.prepareStatement(query);

            int deletedRows = pStmt.executeUpdate();

            pStmt.close();

            return deletedRows;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public void RemovePostedOlderThan(String table, Date date)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (date == null)
            throw new IllegalArgumentException("InstResDAO::RemovePostedOlderThan: Received NULL date field");
        
        if (table == null)
            throw new IllegalArgumentException("InstResDAO::RemovePostedOlderThan: Received NULL table name");
     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        java.sql.Date cutoffDate = new java.sql.Date(date.getTime());
        
        String sql = "DELETE FROM " + table + " WHERE posted = 1 AND postedDate <= ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setDate(1, cutoffDate);
        System.out.println(sql);
        pStmt.execute();
    }
    
    public void RemoveUnpostedOlderThan(String table, Date date)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (date == null)
            throw new IllegalArgumentException("InstResDAO::RemoveUnpostedOlderThan: Received NULL date field");
        
        if (table == null)
            throw new IllegalArgumentException("InstResDAO::RemoveUnpostedOlderThan: Received NULL table name");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        java.sql.Date cutoffDate = new java.sql.Date(date.getTime());
        
        String sql = "DELETE FROM " + table + " WHERE posted = 0 AND created <= ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setDate(1, cutoffDate);
        System.out.println(sql);
        pStmt.execute();
    }
    
    public boolean SetRowPosted(String table, String accession, String user)
    {
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try
        {
            String query = "UPDATE `" + table + "` SET"
                    + " `postedDate` = CURRENT_TIMESTAMP,"
                    + " `posted` = " + true + ","
                    + " `user` = ?" 
                    + " WHERE `accession` = ?";

            PreparedStatement pStmt = createStatement(con, query, user, accession.trim());//con.prepareStatement(query);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }    

    public boolean SetRowPosted(String table, String accession, String name, String user) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String query = "UPDATE `" + table + "` SET"
                    + " `posted` = " + true + ","
                    + " `user` = ?"
                    + " WHERE `accession` LIKE ?"
                    + " AND `name` = ?";

            PreparedStatement pStmt = createStatement(con, query, user, accession, name);//con.prepareStatement(query);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public int ClearNonPosted(String table)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            
            String query = "DELETE FROM `" + table + "` "
                    + "WHERE idinstresult > 0 AND "
                    + "posted = 0";      

            PreparedStatement pStmt = con.prepareStatement(query);
            int num = pStmt.executeUpdate();

            pStmt.close();

            return num;
        } catch (Exception ex) {
            System.out.println("Exception clearing instRes table: " + ex.toString());
            return -1;
        }        
    }

    public int ClearInstTable(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            String query = "DELETE FROM `" + table + "` "
                    + "WHERE idinstresult > 0";

            PreparedStatement pStmt = con.prepareStatement(query);
            int num = pStmt.executeUpdate();

            pStmt.close();

            return num;
        } catch (Exception ex) {
            System.out.println("Exception clearing instRes table: " + ex.toString());
            return -1;
        }
    }
    
    /**
     * Marks the instrument result buffer table row as "ready to be posted" for
     *  the Automated Posting server process. 
     * @param table Instrument buffer table name
     * @param result Actual result value
     * @param resultId The LIS result line to post the result to
     * @param remarkId (optional) remark identifier to result
     * @param userId The user id of the person who queued the result
     * @param instResultId The instrument result buffer row unique identifier
     * @throws SQLException 
     */
    public void QueueResultForPosting(
            String table,
            String result,
            Integer resultId,
            Integer remarkId,
            int userId,
            int instResultId) throws SQLException
    {
        String sql = "UPDATE `" + table + "` SET "
                + " result = ?,"
                + " postToResultId = ?,"
                + " remarkId = ?,"
                + " queuedBy = ?,"
                + " queuedDate = NOW()"
                + " WHERE idinstresult = " + instResultId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        pStmt.setString(++i, result);
        pStmt.setInt(++i, resultId);
        SQLUtil.SafeSetInteger(pStmt, ++i, remarkId);
        pStmt.setInt(++i, userId);
        pStmt.executeUpdate();
    }
    
    public void UnqueueResultForPosting(
        String table,
        Integer idinstresult) throws SQLException
    {
        String sql = "UPDATE `" + table + "` SET "
                + " postToResultId = NULL, "
                + " queuedBy = NULL, "
                + " queuedDate = NULL "
                + " WHERE idinstresult = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, idinstresult);
        pStmt.executeUpdate();
    }

    public BaseInstRes InstResFromResultSet(BaseInstRes res, ResultSet rs) throws SQLException {
        res.setAccession(rs.getString("accession"));
        res.setIdentifier(rs.getString("identifier"));
        res.setName(rs.getString("name"));
        res.setResult(rs.getString("result"));
        res.setComment(rs.getString("comment"));
        res.setUnits(rs.getString("units"));
        res.setRange(rs.getString("range"));
        res.setSpecimenType(rs.getString("specimenType"));
        res.setDilution(rs.getString("dilution"));
        res.setInterpretation(rs.getString("interpretation"));
        res.setIteration(rs.getString("iteration"));
        res.setPostedDate(rs.getTimestamp("postedDate"));
        res.setUser(rs.getString("user"));
        res.setPosted(rs.getBoolean("posted"));
        res.setFileName(rs.getString("fileName"));
        res.setCreated(rs.getTimestamp("created"));
        res.setPostToResultId(rs.getInt("postToResultId"));

        return res;
    }
    
    /**
     * Marks the instrument buffer row as posted. Since the buffer can be
     *  cleared and re-populated, uses unique identifier of the instrument buffer
     *  and the unique result identifier to ensure that the correct row is updated.
     * 
     *  NOTE: Should only be used for instrument buffer rows that where queued
     *   up, not for rows that are being immediately posted.
     * @param table
     * @param idinstresult
     * @param postToResultId 
     * @param postedById 
     * @throws java.sql.SQLException 
     */
    public void SetQueuedRowPosted(String table, int idinstresult, int postToResultId, int postedById) throws SQLException
    {
        String sql = "UPDATE `" + table + "` SET "
                + " `posted` = " + true
                + " ,`user` = " + postedById
                + " ,`postedDate` = NOW()"                
                + " WHERE postToResultId = " + postToResultId
                + " AND idinstresult = " + idinstresult;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.executeUpdate();        
    }

    @Override
    public Boolean Insert(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `instRes_1`.`idinstresult`,\n"
                + "    `instRes_1`.`accession`,\n"
                + "    `instRes_1`.`identifier`,\n"
                + "    `instRes_1`.`name`,\n"
                + "    `instRes_1`.`result`,\n"
                + "    `instRes_1`.`comment`,\n"
                + "    `instRes_1`.`units`,\n"
                + "    `instRes_1`.`range`,\n"
                + "    `instRes_1`.`specimenType`,\n"
                + "    `instRes_1`.`dilution`,\n"
                + "    `instRes_1`.`interpretation`,\n"
                + "    `instRes_1`.`iteration`,\n"
                + "    `instRes_1`.`postedDate`,\n"
                + "    `instRes_1`.`user`,\n"
                + "    `instRes_1`.`posted`,\n"
                + "    `instRes_1`.`fileName`,\n"
                + "    `instRes_1`.`postToResultId`,\n"
                + "    `instRes_1`.`remarkId`,\n"
                + "    `instRes_1`.`queuedBy`,\n"
                + "    `instRes_1`.`queuedDate`,\n"
                + "    `instRes_1`.`created`\n"
                + "FROM `css`.`instRes_1` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "instRes_1", con);
    }
}
