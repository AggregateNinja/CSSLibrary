/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.IDOS.BaseInstOrd;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.2 $
 * @since Build {insert version here} 12/12/2013
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class InstOrdDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private static final int CONNECTION_VALIDATION_TIMEOUT = 3;

    public boolean InsertLog(String table, BaseInstOrd ord) throws SQLException {
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
                    + "`patientID`, "
                    + "`patientLast`, "
                    + "`patientFirst`, "
                    + "`DOB`, "
                    + "`age`, "
                    + "`sex`, "
                    + "`orderDate`, "
                    + "`specimenDate`, "
                    + "`clientNumber`, "
                    + "`clientName`, "
                    + "`doctorNumber`, "
                    + "`doctorName`, "
                    + "`fasting`, "
                    + "`testName`, "
                    + "`testNumber`, "
                    + "`testXref`, "
                    + "`onlineCode`, "
                    + "`specimenType`, "
                    + "`sent`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, ord.getAccession());
            SQLUtil.SafeSetString(pStmt, 2, ord.getPatientID());
            SQLUtil.SafeSetString(pStmt, 3, ord.getPatientLast());
            SQLUtil.SafeSetString(pStmt, 4, ord.getPatientFirst());
            SQLUtil.SafeSetString(pStmt, 5, ord.getDob());
            SQLUtil.SafeSetString(pStmt, 6, ord.getAge());
            SQLUtil.SafeSetString(pStmt, 7, ord.getSex());
            SQLUtil.SafeSetString(pStmt, 8, ord.getOrderDate());
            SQLUtil.SafeSetString(pStmt, 9, ord.getSpecimenDate());
            SQLUtil.SafeSetString(pStmt, 10, ord.getClientNumber());
            SQLUtil.SafeSetString(pStmt, 11, ord.getClientName());
            SQLUtil.SafeSetString(pStmt, 12, ord.getDoctorNumber());
            SQLUtil.SafeSetString(pStmt, 13, ord.getDoctorName());
            SQLUtil.SafeSetString(pStmt, 14, ord.getFasting());
            SQLUtil.SafeSetString(pStmt, 15, ord.getTestName());
            SQLUtil.SafeSetString(pStmt, 16, ord.getTestNumber());
            SQLUtil.SafeSetString(pStmt, 17, ord.getTestXref());
            SQLUtil.SafeSetString(pStmt, 18, ord.getOnlineCode());
            SQLUtil.SafeSetString(pStmt, 19, ord.getSpecimenType());
            SQLUtil.SafeSetBoolean(pStmt, 20, ord.isSent());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println("Failed Adding Instrument Data: " + ex.toString());
            return false;
        }
    }

    public ArrayList<BaseInstOrd> GetAllOrders(String table) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            BaseInstOrd ord = null;
            ArrayList<BaseInstOrd> list = new ArrayList<BaseInstOrd>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM `" + table + "`";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ord = new BaseInstOrd();
                ord = InstOrdFromResultSet(ord, rs);

                list.add(ord);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<BaseInstOrd> GetAllUnsent(String table) throws SQLException
    {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try {
            BaseInstOrd ord = null;
            ArrayList<BaseInstOrd> unsentLines = new ArrayList<>();
            
            String query = "SELECT * FROM `" + table
                    + "` WHERE `sent` = 0 ORDER BY accession";
            Statement stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                ord = new BaseInstOrd();
                ord = InstOrdFromResultSet(ord, rs);

                unsentLines.add(ord);
            }

            rs.close();

            return unsentLines;
        } catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Map<String, String> GetAllAccessionAndPatientUnsent(String table) throws SQLException
    {
        try
        {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT)))
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            BaseInstOrd ord = null;
            Map<String, String> unsentAccessionPatientMap = new TreeMap<String, String>();
            
            String query = "SELECT DISTINCT `accession` AS accession, `patientId` AS patientId FROM `" + table
                    + "` WHERE `sent` = 0 GROUP BY `accession`";
            Statement stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                unsentAccessionPatientMap.put(rs.getString("accession"), rs.getString("patientId"));
            }

            rs.close();

            return unsentAccessionPatientMap;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Map<String, Map<String, List<BaseInstOrd>>> GetAllAccessionAndSpecimenUnsent(String table, Integer limit) throws SQLException
    {
        try
        {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT)))
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            Map<String, Map<String, List<BaseInstOrd>>> unsentAccessionSpecimenMap = new TreeMap<String, Map<String, List<BaseInstOrd>>>();
            Map<String, List<BaseInstOrd>> specimenMap;
            List<BaseInstOrd> instOrdList;
            
            String query = "SELECT `io1`.* FROM `" + table
                    + "` io1 INNER JOIN (SELECT DISTINCT `accession`, `specimenType` FROM `" + table + "`";
            if (limit != null)
            {
                query += " LIMIT " + limit;
            }
            query += ") io2 ON `io1`.`accession` = `io2`.`accession` WHERE `sent` = 0 ";
            Statement stmt = con.createStatement();            
            ResultSet rs = stmt.executeQuery(query);
            
            BaseInstOrd ord;
            while (rs.next())
            {
                ord = new BaseInstOrd();
                String accession = rs.getString("accession");
                String specimen = rs.getString("specimenType");
                if (unsentAccessionSpecimenMap.containsKey(accession))
                {
                    specimenMap = unsentAccessionSpecimenMap.get(accession);
                }
                else
                {
                    specimenMap = new TreeMap<String, List<BaseInstOrd>>();
                }
                
                if (specimenMap.containsKey(specimen))
                {
                    instOrdList = specimenMap.get(specimen);
                }
                else
                {
                    instOrdList = new ArrayList<BaseInstOrd>();
                }
                instOrdList.add(InstOrdFromResultSet(ord, rs));
                
                specimenMap.put(rs.getString("specimenType"), instOrdList);
                unsentAccessionSpecimenMap.put(accession, specimenMap);
            }

            rs.close();

            return unsentAccessionSpecimenMap;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Ignores instOrd table and builds BaseInstOrd objects based on any result
     * for the provided accession / instrument(s) that is not reported and not
     * invalidated.
     * @param accession
     * @param instrumentIds A list of instruments to include in this search.
     * @return
     * @throws SQLException 
     */
    public List<BaseInstOrd> GetUnreportedRowsForOnlineCodeInstrument(String accession, List<Integer> instrumentIds)
            throws SQLException, IllegalArgumentException
    {
        if (accession == null || accession.isEmpty())
        {
            throw new IllegalArgumentException("Recevied NULL/blank accession");
        }
        
        if (instrumentIds == null || instrumentIds.isEmpty())
        {
            throw new IllegalArgumentException("Received empty instrumentId list");
        }
        
        String instruments = "";
        for (Integer instrumentId : instrumentIds)
        {
            if (instruments.isEmpty() == false) instruments += ",";
            instruments += instrumentId;
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // Retrieve the "instOrd" data by using the order information instead
        // of the instOrd rows.
        String sql = 
            "SELECT"
            + " o.patientId AS 'PatientId',"
            + " p.lastName AS 'PatientLastName',"
            + " p.firstName AS 'PatientFirstName',"
            + " p.dob AS 'PatientDOB',"
            + " CASE WHEN p.dob IS NOT NULL AND LENGTH(p.dob) > 0 THEN TIMESTAMPDIFF(YEAR, p.dob, CURDATE()) ELSE NULL END AS 'Age',"
            + " p.sex AS 'Sex',"
            + " o.orderDate AS 'OrderDate',"
            + " o.specimenDate AS 'SpecimenDate',"
            + " c.clientNo AS 'ClientNumber',"
            + " c.clientName AS 'ClientName',"
            + " d.number AS 'DoctorNumber',"
            + " CASE WHEN d.lastName IS NOT NULL AND d.firstName IS NOT NULL THEN CONCAT(d.lastname, ', ', d.firstname) ELSE NULL END AS 'DoctorName',"
            + " o.isFasting AS 'IsFasting',"
            + " t.`name` AS 'TestName',"
            + " t.number AS 'TestNumber',"
            + " t.onlineCode1 AS 'OnlineCode1',"
            + " s.`name` AS 'SpecimenTypeName'"
            + " FROM orders o"
            + " INNER JOIN clients c ON o.clientId = c.idclients"
            + " INNER JOIN patients p ON o.patientId = p.idpatients"
            + " LEFT JOIN doctors d ON o.doctorId = d.iddoctors"
            + " INNER JOIN results r ON o.idorders = r.orderId"
            + " INNER JOIN tests t ON r.testId = t.idtests"
            + " LEFT JOIN specimenTypes s ON t.specimenType = s.idspecimenTypes"
            + " WHERE"
            + " r.isInvalidated = 0 AND r.dateReported IS NULL AND "
            // Special case for Dimension EXL: Only tests with a non-empty onlineCode1 and tests with online codes 1 and 2 that don't
            // match will be added. 
            + " t.onlineCode1 IS NOT NULL AND LENGTH(t.onlineCode1) > 0 AND (t.onlineCode2 IS NULL OR t.onlineCode2 != t.onlineCode1)"
            + " AND o.accession = ? AND t.instrument in (" + instruments + ")";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, accession);
        System.out.println(pStmt.toString());
        ResultSet rs = pStmt.executeQuery();
        
        LinkedList<BaseInstOrd> testRows = new LinkedList<>();
        while (rs.next())
        {
            BaseInstOrd row = new BaseInstOrd();
            row.setAccession(accession);
            row.setPatientID(rs.getString("PatientId"));
            row.setPatientLast(rs.getString("PatientLastName"));
            row.setPatientFirst(rs.getString("PatientFirstName"));
            row.setDob(rs.getString("PatientDOB"));
            row.setAge(rs.getString("Age"));
            row.setOrderDate(rs.getString("OrderDate"));
            row.setSpecimenType(rs.getString("SpecimenDate"));
            row.setClientNumber(rs.getString("ClientNumber"));
            row.setClientName(rs.getString("ClientName"));
            row.setDoctorNumber(rs.getString("DoctorNumber"));
            row.setDoctorName(rs.getString("DoctorName"));
            row.setFasting(rs.getString("IsFasting"));
            row.setTestName(rs.getString("TestName"));
            row.setTestNumber(rs.getString("TestNumber"));
            row.setOnlineCode(rs.getString("OnlineCode1"));
            row.setSpecimenType(rs.getString("SpecimenTypeName"));
            testRows.add(row);
        }
        return testRows;
    }    
        
    public ArrayList<BaseInstOrd> GetAllRowsForAnAccession(String table, String accession) throws SQLException {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            BaseInstOrd ord = null;
            ArrayList<BaseInstOrd> list = new ArrayList<BaseInstOrd>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `sent` = " + false;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ord = new BaseInstOrd();
                ord = InstOrdFromResultSet(ord, rs);

                list.add(ord);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<BaseInstOrd> GetAllRowsForAccessionSpecimen(String table, String accession, int specimen) throws SQLException {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            BaseInstOrd ord = null;
            ArrayList<BaseInstOrd> list = new ArrayList<BaseInstOrd>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `specimenType` = " + specimen + " "
                    + "AND `sent` = " + false;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ord = new BaseInstOrd();
                ord = InstOrdFromResultSet(ord, rs);

                list.add(ord);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<BaseInstOrd> GetAllRowsForAccessionSpecimen(String table, String accession, char specimen) throws SQLException {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            BaseInstOrd ord = null;
            ArrayList<BaseInstOrd> list = new ArrayList<BaseInstOrd>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `specimenType` = '" + specimen + "' "
                    + "AND `sent` = " + false;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ord = new BaseInstOrd();
                ord = InstOrdFromResultSet(ord, rs);

                list.add(ord);
            }

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean AccessionHasMultipleSpecimens(String table, String accession) throws SQLException {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        try {
            int x = 0;
            boolean multiSpecimen = false;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `specimenType` FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `sent` = " + false;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if(x == 0){
                    x = rs.getInt("specimenType");
                }else{
                    if(x != rs.getInt("specimenType"))
                        multiSpecimen = true;
                }
            }
            

            rs.close();
            stmt.close();

            return multiSpecimen;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public ArrayList<Integer> GetAccessionSpecimenTypes(String table, String accession) throws SQLException {
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            int x = 0;
            ArrayList<Integer> list = new ArrayList<>();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `specimenType` FROM `" + table + "` "
                    + "WHERE `accession` = ? "
                    + "AND `sent` = " + false;

            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if(x == 0){
                    x = rs.getInt("specimenType");
                    list.add(x);
                }else{
                    if(list.contains(rs.getInt("specimenType")) == false)
                        list.add(rs.getInt("specimenType"));
                }
            }
            

            rs.close();
            stmt.close();

            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<String> GetNewAccessions(String table){
        try {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try {
            ArrayList<String> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            String query = "SELECT DISTINCT `accession` FROM `" + table + "` "
                    + "WHERE `sent` = " + false;
            
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                list.add(rs.getString("accession"));
            }
            
            rs.close();
            stmt.close();

            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public int AccessionSent(String table, String accession){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ?";
            
            PreparedStatement pStmt = createStatement(con, stmt, accession); //con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            return updatedRows;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    /**
     * Given an instOrd table name and the unique identifier for a row,
     *   marks the row as Sent
     * @param table
     * @param instOrdId
     * @return 
     */
    public int MarkSent(String table, int instOrdId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        try
        {
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + " WHERE id = " + instOrdId;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            int updatedRows = pStmt.executeUpdate();
            pStmt.close();
            return updatedRows;
        }
        catch (SQLException ex)
        {
            if (table == null) table = "[null]";
            System.out.println("InstOrdDAO::MarkSent: Table Name: " + table + ": instOrdId = " + instOrdId);
            System.out.println(ex.toString());
            return 0;
        }        
    }
    
    public int AccessionSent(String table, String accession, int specimen){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ? "
                    + "AND `specimenType` = " + specimen;
            
            PreparedStatement pStmt = createStatement(con, stmt, accession);//con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            return updatedRows;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    /**
     * Marks row(s) in the instrument order buffer sent/unsent for a given
     *  accession and test number.
     * 
     * Depending on the interface, a row already marked "sent" will not be 
     *  re-transmitted to the instrument when it queries.
     * 
     * @param table         The table (instOrd_#) to apply changes to
     * @param accession     The order's accession
     * @param testNumber    The test number to apply this change to
     * @param sent          Whether or not this record should be marked 'sent'
     * @return int          The number of rows affected
     * 
     * @throws NullPointerException
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public int setTestSentToInstrument(String table, String accession, int testNumber, boolean sent)
            throws NullPointerException, SQLException, IllegalArgumentException
    {
        if (table == null || table.isEmpty()) throw new IllegalArgumentException(
            "InstOrdDAO::setTestSentToInstrument: Received a NULL or empty table name.");
        if (accession == null || accession.isEmpty()) throw new IllegalArgumentException(
            "InstOrdDAO::setTestSentToInstrument: Received a NULL or empty accession.");
        if (testNumber <= 0) throw new IllegalArgumentException(
            "InstOrdDAO::setTestSentToInstrument: Received a test number of " + testNumber);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

        String sql = "UPDATE `" + table + "` SET sent = ? WHERE accession = ? AND testNumber = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setBoolean(1, sent);
        pStmt.setString(2, accession);
        pStmt.setString(3, String.valueOf(testNumber));
        
        int rowsAffected = 0;
        
        try
        {
            rowsAffected = pStmt.executeUpdate();
        }
        catch (NullPointerException | SQLException ex)
        {
            sql = "InstOrdDAO::setTestSentToInstrument: failed update " + pStmt.toString();
            System.out.println(sql);
            SysLogDAO.Add(1, sql, ex.getMessage());
            
            throw ex;
        }
        finally
        {
            pStmt.close();
        }
        
        return rowsAffected;  
    }
    
    public boolean AccessionTestSent(String table, String accession, String test){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ? "
                    + "AND `testName` = ?";
            
            PreparedStatement pStmt = createStatement(con, test, accession, test); //con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            if(updatedRows>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean AccessionOnlineCodeSpecimenTypeSent(String table, String accession, String onlineCode, char specType){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ? "
                    + "AND `onlineCode` = ? "
                    + "AND `specimenType` = '" + specType + "'";
            
            PreparedStatement pStmt = createStatement(con, stmt, accession, onlineCode);//con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            if(updatedRows>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean AccessionOnlineCodeSent(String table, String accession, String onlineCode){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ? "
                    + "AND `onlineCode` = ?";
            
            PreparedStatement pStmt = createStatement(con, stmt, accession, onlineCode);//con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            if(updatedRows>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean isAccessionPreviouslySent(String table, String accession)
    {
        boolean isAccessionSent = false;
        try
        {
            if ((this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT))) {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return isAccessionSent;
        }
        
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT DISTINCT `accession` FROM `" + table + "` "
                    + "WHERE `sent` = " + true
                    + " AND `accession` = ?";
            
            stmt = createStatement(con, query, accession);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                isAccessionSent = true;
            }
            
            rs.close();
            stmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return isAccessionSent;
    }
    
    public boolean AccessionOnlineCodeSent(String table, String accession, List<String> onlineCodeList){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            StringBuilder onlineCodeString = new StringBuilder();
            for (String onlineCode : onlineCodeList)
            {
                onlineCodeString.append("'");
                onlineCodeString.append(onlineCode);
                onlineCodeString.append("',");
            }
            if (onlineCodeString.length() <= 0)
            {
                return true;
            }
            // delete last comma
            onlineCodeString.deleteCharAt(onlineCodeString.length()-1);
            String stmt = "UPDATE `" + table + "` SET `sent` = " + true + " "
                    + "WHERE `accession` = ? "
                    + "AND `onlineCode` IN (" + onlineCodeString + ")";
            
            PreparedStatement pStmt = createStatement(con, stmt, accession);//con.prepareStatement(stmt);
            
            int updatedRows =  pStmt.executeUpdate();
            
            pStmt.close();
            
            if(updatedRows>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
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
    
    public void RemoveRowsForOrderId(String table, int orderId)
            throws IllegalArgumentException, SQLException
    {
        if (table == null || table.isEmpty())
        {
            throw new IllegalArgumentException(
                    "InstOrdDAO::RemoveRowsForOrderId: Received NULL / blank 'table' argument");
        }
        
        if (orderId <= 0)
        {
            throw new IllegalArgumentException("InstOrdDAO::RemoveRowsForOrderId: received orderId of : " + orderId);
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "DELETE " + table + " FROM " + table + " INNER JOIN orders ON " + table + ".accession = orders.accession WHERE orders.idorders = " + orderId;
        
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            pStmt.executeUpdate();
        }
    }
    
    /**
     * Deletes rows in the specified instOrd table with a created date before
     *  the provided date
     * @param instOrdTableNumber
     * @param date
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public void RemoveOlderThan(int instOrdTableNumber, Date date)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (instOrdTableNumber <= 0)
            throw new IllegalArgumentException("InstOrdDAO::RemoveOlderThan: Received instOrdTableNumber of " + instOrdTableNumber);
        
        if (date == null)
            throw new IllegalArgumentException("InstOrdDAO::RemoveOlderThan: Received NULL date");
     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        java.sql.Date cutoffDate = new java.sql.Date(date.getTime());
        
        String sql = "DELETE FROM instOrd_" + instOrdTableNumber + " WHERE created IS NOT NULL AND created <= ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setDate(1, cutoffDate);
        System.out.println(sql);
        pStmt.execute();
    }
    
    public int RemoveRowsByLikeAccession(String table, String accession) 
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

    public BaseInstOrd InstOrdFromResultSet(BaseInstOrd ord, ResultSet rs) throws SQLException {
        ord.setId(rs.getInt("id"));
        ord.setAccession(rs.getString("accession"));
        ord.setPatientID(rs.getString("patientID"));
        ord.setPatientLast(rs.getString("patientLast"));
        ord.setPatientFirst(rs.getString("patientFirst"));
        ord.setDob(rs.getString("DOB"));
        ord.setAge(rs.getString("age"));
        ord.setSex(rs.getString("sex"));
        ord.setSpecimenDate(rs.getString("specimenDate"));
        ord.setOrderDate(rs.getString("orderDate"));
        ord.setClientNumber(rs.getString("clientNumber"));
        ord.setClientName(rs.getString("clientName"));
        ord.setDoctorNumber(rs.getString("doctorNumber"));
        ord.setDoctorName(rs.getString("doctorName"));
        ord.setFasting(rs.getString("fasting"));
        ord.setTestName(rs.getString("testName"));
        ord.setTestNumber(rs.getString("testNumber"));
        ord.setTestXref(rs.getString("testXref"));
        ord.setOnlineCode(rs.getString("onlineCode"));
        ord.setSpecimenType(rs.getString("specimenType"));
        ord.setSent(rs.getBoolean("sent"));

        return ord;
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
        String query = "SELECT `instOrd_1`.`id`,\n"
                + "    `instOrd_1`.`accession`,\n"
                + "    `instOrd_1`.`patientID`,\n"
                + "    `instOrd_1`.`patientLast`,\n"
                + "    `instOrd_1`.`patientFirst`,\n"
                + "    `instOrd_1`.`DOB`,\n"
                + "    `instOrd_1`.`age`,\n"
                + "    `instOrd_1`.`sex`,\n"
                + "    `instOrd_1`.`orderDate`,\n"
                + "    `instOrd_1`.`specimenDate`,\n"
                + "    `instOrd_1`.`clientNumber`,\n"
                + "    `instOrd_1`.`clientName`,\n"
                + "    `instOrd_1`.`doctorNumber`,\n"
                + "    `instOrd_1`.`doctorName`,\n"
                + "    `instOrd_1`.`fasting`,\n"
                + "    `instOrd_1`.`testName`,\n"
                + "    `instOrd_1`.`testNumber`,\n"
                + "    `instOrd_1`.`testXref`,\n"
                + "    `instOrd_1`.`onlineCode`,\n"
                + "    `instOrd_1`.`specimenType`,\n"
                + "    `instOrd_1`.`sent`,\n"
                + "    `instOrd_1`.`created`\n"
                + "FROM `css`.`instOrd_1` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "instOrd_1", con);
    }
}
