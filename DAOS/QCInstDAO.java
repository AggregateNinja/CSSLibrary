/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 3, 2014
 */
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.QcInst;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import Utility.WriteTextFile;
//import com.mysql.jdbc.MysqlErrorNumbers;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Jun 3, 2014 12:33:19 AM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: QCInstDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class QCInstDAO implements IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final int RETRY_ATTEMPTS = 3;
    private final int CONNECTION_VALIDATION_TIMEOUT = 3; // seconds

    public boolean InsertQCData(String table, QcInst inst) {

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
                    + "`name`, "
                    + "`level`, "
                    + "`result`, "
                    + "`specimenType`, "
                    + "`control`, "
                    + "`fileName`, "
                    + "`acquisitionDate`, "
                    + "`createdDate`, "
                    + "`postedBy`, "
                    + "`posted`, "
                    + "`postedDate`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, inst.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, inst.getLevel());
            SQLUtil.SafeSetString(pStmt, 3, inst.getResult());
            SQLUtil.SafeSetInteger(pStmt, 4, inst.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 5, inst.getControl());
            SQLUtil.SafeSetString(pStmt, 6, inst.getFileName());
            SQLUtil.SafeSetTimeStamp(pStmt, 7, Convert.ToSQLDateTime(inst.getAcquisitionDate()));
            SQLUtil.SafeSetTimeStamp(pStmt, 8, Convert.ToSQLDateTime(inst.getCreatedDate()));
            SQLUtil.SafeSetInteger(pStmt, 9, inst.getPostedBy());
            SQLUtil.SafeSetBoolean(pStmt, 10, inst.getPosted());
            SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(inst.getPostedDate()));

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Adding QC Data: " + ex.toString());
            return false;
        }
    }

    public boolean InsertQCDataLog(String table, QcInst inst, WriteTextFile log) {

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
                if (log != null)
                {
                    log.write("Connection attempt " + i + " failed : " + sex.toString());
                }
                if (i == RETRY_ATTEMPTS)
                {
                    return false;
                }
            }
        }

        try {
            String stmt = "INSERT INTO `" + table + "` ("
                    + "`name`, "
                    + "`level`, "
                    + "`result`, "
                    + "`specimenType`, "
                    + "`control`, "
                    + "`fileName`, "
                    + "`acquisitionDate`, "
                    + "`createdDate`, "
                    + "`postedBy`, "
                    + "`posted`, "
                    + "`postedDate`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?) ";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, inst.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, inst.getLevel());
            SQLUtil.SafeSetString(pStmt, 3, inst.getResult());
            SQLUtil.SafeSetInteger(pStmt, 4, inst.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 5, inst.getControl());
            SQLUtil.SafeSetString(pStmt, 6, inst.getFileName());
            SQLUtil.SafeSetTimeStamp(pStmt, 7, Convert.ToSQLDateTime(inst.getAcquisitionDate()));
            SQLUtil.SafeSetTimeStamp(pStmt, 8, Convert.ToSQLDateTime(inst.getCreatedDate()));
            SQLUtil.SafeSetInteger(pStmt, 9, inst.getPostedBy());
            SQLUtil.SafeSetBoolean(pStmt, 10, inst.getPosted());
            SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(inst.getPostedDate()));

            boolean success = ExecuteUpdate(pStmt, 0);
            pStmt.close();

            return success;
        } catch (Exception ex) {
            String error = "QCInstDAO::InsertQCDataLog: Unable to perform insert";
            if (table != null)
            {
                error += " to table " + table;
            }
            if (inst != null && inst.getControl()!= null)
            {
                error += " for control " + inst.getControl();
            }
            System.out.println(error);
            if (log != null)
            {
                log.write(error);
            }
            return false;
        }
    }
    
    public boolean UpdateQCData(String table, QcInst inst) {

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
                    + "`name` = ?, "
                    + "`level` = ?, "
                    + "`result` = ?, "
                    + "`specimenType` = ?, "
                    + "`control` = ?, "
                    + "`fileName` = ?, "
                    + "`acquisitionDate` = ?, "
                    + "`createdDate` = ?, "
                    + "`postedBy` = ?, "
                    + "`posted` = ?, "
                    + "`postedDate` = ? "
                    + "WHERE `id` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, inst.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, inst.getLevel());
            SQLUtil.SafeSetString(pStmt, 3, inst.getResult());
            SQLUtil.SafeSetInteger(pStmt, 4, inst.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, 5, inst.getControl());
            SQLUtil.SafeSetString(pStmt, 6, inst.getFileName());
            SQLUtil.SafeSetTimeStamp(pStmt, 7, Convert.ToSQLDateTime(inst.getAcquisitionDate()));
            SQLUtil.SafeSetTimeStamp(pStmt, 8, Convert.ToSQLDateTime(inst.getCreatedDate()));
            SQLUtil.SafeSetInteger(pStmt, 9, inst.getPostedBy());
            SQLUtil.SafeSetBoolean(pStmt, 10, inst.getPosted());
            SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(inst.getPostedDate()));
            SQLUtil.SafeSetInteger(pStmt, 12, inst.getId());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Updating QC Data: " + ex.toString());
            return false;
        }
    }
    

    public boolean UpdateAcquisitionDateByFilename(String table, String filename, Date acquisitionDate) {

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
                    + "`acquisitionDate` = ? "
                    + "WHERE `fileName` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetTimeStamp(pStmt, 1, Convert.ToSQLDateTime(acquisitionDate));
            SQLUtil.SafeSetString(pStmt, 2, filename);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Updating QC Data: " + ex.toString());
            return false;
        }
    }
    
    public boolean DeleteById(String table, Integer id)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `id` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, id);
            
            pStmt.executeUpdate();

            pStmt.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }
    
    public boolean DeleteByFilename(String table, String fileName)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `fileName` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, fileName);
            
            pStmt.executeUpdate();

            pStmt.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }

    public QcInst GetByID(String table, int ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            QcInst inst = new QcInst();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE id = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                QcInstFromResultSet(inst, rs);
            }

            rs.close();
            stmt.close();

            return inst;

        } catch (Exception ex) {
            System.out.println("Failed fetching QC Data by ID: " + ex.toString());
            return null;
        }
    }

    public ArrayList<QcInst> GetDataByFile(String table, String fileName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<QcInst> list = new ArrayList<>();
            QcInst inst = new QcInst();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `fileName` = ? "
                    + "AND `posted` = " + false;

            stmt = createStatement(con, query, fileName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                QcInstFromResultSet(inst, rs);

                list.add(inst);
            }

            rs.close();
            stmt.close();

            return list;

        } catch (Exception ex) {
            System.out.println("Failed fetching QC Data File Name: " + ex.toString());
            return null;
        }
    }

    public ArrayList<QcInst> GetDataByCreatedRange(String table, java.util.Date From, java.util.Date To) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<QcInst> list = new ArrayList<>();
            QcInst inst = new QcInst();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `createdDate` BETWEEN '" + Convert.ToSQLDateTime(From) + "' "
                    + "AND '" + Convert.ToSQLDateTime(To) + "' "
                    + "AND `posted` = " + false;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                QcInstFromResultSet(inst, rs);

                list.add(inst);
            }

            rs.close();
            stmt.close();

            return list;

        } catch (Exception ex) {
            System.out.println("Failed fetching QC Data Created Date: " + ex.toString());
            return null;
        }
    }

    public String getRunFileByID(String table, Integer ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            String fileName = null;
            Statement stmt = con.createStatement();

            String query = "SELECT DISTINCT `fileName` FROM `" + table + "` "
                    + "WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fileName = rs.getString("fileName");
            }

            rs.close();
            stmt.close();

            return fileName;
        } catch (Exception ex) {
            System.out.println("Failed fetching a file name: " + ex.toString());
            return null;
        }
    }

    public ArrayList<String> GetNewFiles(String table) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<String> list = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT DISTINCT `fileName` FROM `" + table + "` "
                    + "WHERE `posted` = " + false;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("fileName");

                list.add(name);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println("Failed fetching file names: " + ex.toString());
            return null;
        }
    }

    public int SetDataPosted(String table, ArrayList<Integer> ids, int user) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        StringBuilder strIDs = new StringBuilder();
        for (int i : ids) {
            strIDs.append(i + ", ");
        }
        //Convert StringBUilder to a String, then make sure to get rid of any
        //trailing commas and white space
        String in = strIDs.toString().trim();
        in = in.substring(0, in.length() - 1).trim();

        java.util.Date date = new java.util.Date();

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`postedBy` = ?, "
                    + "`posted` = ?, "
                    + "`postedDate` = ? "
                    + "WHERE `id` IN (" + in + ")";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, user);
            SQLUtil.SafeSetBoolean(pStmt, 2, true);
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(date));

            int x = pStmt.executeUpdate();

            pStmt.close();

            return x;
        } catch (Exception ex) {
            System.out.println("Failed Updating QC Posted Data: " + ex.toString());
            return -1;
        }
    }

    public int SetDataPostedByFileName(String table, String fileName, int user) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        java.util.Date date = new java.util.Date();

        try {
            String stmt = "UPDATE `" + table + "` SET "
                    + "`postedBy` = ?, "
                    + "`posted` = ?, "
                    + "`postedDate` = ? "
                    + "WHERE `fileName` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, user);
            SQLUtil.SafeSetBoolean(pStmt, 2, true);
            SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(date));
            pStmt.setString(4, fileName);
            int x = pStmt.executeUpdate();

            pStmt.close();

            return x;
        } catch (Exception ex) {
            System.out.println("Failed Updating QC Posted Data: " + ex.toString());
            return -1;
        }
    }

    
    /**
     * Deletes rows in the specified qcInst table with a created date before
     *  the provided date
     * @param qcInstTableNumber
     * @param date
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public void RemoveOlderThan(int qcInstTableNumber, Date date)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (qcInstTableNumber <= 0)
            throw new IllegalArgumentException("QCInstDAO::RemoveOlderThan: Received qcInstTableNumber of " + qcInstTableNumber);
        
        if (date == null)
            throw new IllegalArgumentException("QCInstDAO::RemoveOlderThan: Received NULL date");
     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        java.sql.Date cutoffDate = new java.sql.Date(date.getTime());
        
        String sql = "DELETE FROM instOrd_" + qcInstTableNumber + " WHERE created IS NOT NULL AND created <= ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setDate(1, cutoffDate);
        System.out.println(sql);
        pStmt.execute();
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
                            "QCInstDAO::ExecuteUpdate: InterruptedException "
                            + " thrown while waiting for deadlock retry");
                    
                    return false;
                }
            }
            else
            {
                System.out.println("QCInstDAO::ExecuteUpdate: "
                        + " Unable to execute update for sql: " + pStmt.toString()
                        + " (Non-deadlock error)");
                return false;
            }
        }
        return true;
    }
    
    public boolean IsFileADuplicate(String table, String fileName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "SELECT COUNT(*) AS 'Files' "
                    + "FROM `" + table + "` "
                    + "WHERE `fileName` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, fileName);

            ResultSet rs = pStmt.executeQuery();
            int count = 0;

            if (rs.next()) {
                count = rs.getInt("Files");
            }

            rs.close();
            pStmt.close();
            
            //debug
            System.out.println("File Duplicate count = " + count);
            return count > 0;

        } catch (SQLException ex) {
            System.out.println("Exception looking for fileName: " + ex.toString());
            return false;
        }

    }

    public QcInst QcInstFromResultSet(QcInst inst, ResultSet rs) throws SQLException {
        inst.setId(rs.getInt("id"));
        inst.setName(rs.getString("name"));
        inst.setLevel(rs.getInt("level"));
        inst.setResult(rs.getString("result"));
        inst.setSpecimenType(rs.getInt("specimenType"));
        inst.setControl(rs.getString("control"));
        inst.setFileName(rs.getString("fileName"));
        inst.setAcquisitionDate(rs.getTimestamp("acquisitionDate"));
        inst.setCreatedDate(rs.getTimestamp("createdDate"));
        inst.setPostedBy(rs.getInt("postedBy"));
        inst.setPosted(rs.getBoolean("posted"));
        inst.setPostedDate(rs.getTimestamp("postedDate"));

        return inst;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `qcInst_1`.`id`,\n"
                + "    `qcInst_1`.`name`,\n"
                + "    `qcInst_1`.`level`,\n"
                + "    `qcInst_1`.`control`,\n"
                + "    `qcInst_1`.`result`,\n"
                + "    `qcInst_1`.`specimenType`,\n"
                + "    `qcInst_1`.`fileName`,\n"
                + "    `qcInst_1`.`acquisitionDate`,\n"
                + "    `qcInst_1`.`createdDate`,\n"
                + "    `qcInst_1`.`postedBy`,\n"
                + "    `qcInst_1`.`posted`,\n"
                + "    `qcInst_1`.`postedDate`\n"
                + "FROM `css`.`qcInst_1` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "qcInst_1", con);
    }
}
