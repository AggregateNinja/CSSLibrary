package DAOS;

import DAOS.IDAOS.DAOInterface;
import Database.CheckDBConnection;
import DAOS.IDAOS.IPatientDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Patients;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Mar 12, 2012
 * @updated: Sept 9, 2014
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class PatientDAO implements IPatientDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private static final String table = "`patients`";
    
     /**
     * All fields except idPatients
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public PatientDAO() 
    {
        fields.add("arNo");
        fields.add("lastName");
        fields.add("firstName");
        fields.add("middleName");
        fields.add("sex");
        fields.add("ssn");
        fields.add("dob");
        fields.add("addressStreet");
        fields.add("addressStreet2");
        fields.add("addressCity");
        fields.add("addressState");
        fields.add("addressZip");
        fields.add("phone");
        fields.add("workPhone");
        fields.add("subscriber");
        fields.add("relationship");
        fields.add("counselor");
        fields.add("species");
        fields.add("height");
        fields.add("weight");
        fields.add("ethnicity");
        fields.add("smoker");
        fields.add("active");
        fields.add("deactivatedDate");
        fields.add("patientMRN");
        fields.add("DOI");
        fields.add("EOA");
        fields.add("labPatientId");
    }

    @Override
    public boolean InsertPatient(Patients patient) throws SQLException {
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
        
        // if(GetPatientIdByAR(patient.getArNo()) != 0)
        // return UpdatePatient(patient);
        try {
 
            pStmt.setString(1, patient.getArNo());
            SQLUtil.SafeSetString(pStmt, 2, patient.getLastName());
            pStmt.setString(3, patient.getFirstName());
            SQLUtil.SafeSetString(pStmt, 4, patient.getMiddleName());
            pStmt.setString(5, patient.getSex());
            pStmt.setString(6, patient.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, patient.getDob());
            SQLUtil.SafeSetString(pStmt, 8, patient.getAddressStreet());
            SQLUtil.SafeSetString(pStmt, 9, patient.getAddressStreet2());
            SQLUtil.SafeSetString(pStmt, 10, patient.getAddressCity());
            SQLUtil.SafeSetString(pStmt, 11, patient.getAddressState());
            SQLUtil.SafeSetString(pStmt, 12, patient.getAddressZip());
            pStmt.setString(13, patient.getPhone());
            pStmt.setString(14, patient.getWorkPhone());
            SQLUtil.SafeSetInteger(pStmt, 15, patient.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 16, patient.getRelationship());
            SQLUtil.SafeSetInteger(pStmt, 17, patient.getCounselor());
            SQLUtil.SafeSetInteger(pStmt, 18, patient.getSpecies());
            pStmt.setInt(19, patient.getHeight());
            pStmt.setInt(20, patient.getWeight());
            pStmt.setString(21, patient.getEthnicity());
            pStmt.setBoolean(22, patient.getSmoker());
            pStmt.setBoolean(23, patient.getActive());
            SQLUtil.SafeSetDate(pStmt, 24, patient.getDeactivatedDate());
            SQLUtil.SafeSetString(pStmt, 25, patient.getPatientMRN());
            SQLUtil.SafeSetDate(pStmt, 26, patient.getDOI());
            SQLUtil.SafeSetString(pStmt, 27, patient.getEOA());
            SQLUtil.SafeSetString(pStmt, 28, patient.getLabPatientId());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = patient.getArNo() + ": " + ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            System.out.println(pStmt.toString());
            return false;
        }
    }

    @Override
    public boolean UpdatePatient(Patients patient) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        String stmt = GenerateUpdateStatement(fields)
                + " WHERE `idPatients` = " + patient.getIdPatients();

        PreparedStatement pStmt = con.prepareStatement(stmt);

        try {
                     
            SQLUtil.SafeSetString(pStmt, 1, patient.getArNo());
            SQLUtil.SafeSetString(pStmt, 2, patient.getLastName());
            pStmt.setString(3, patient.getFirstName());
            SQLUtil.SafeSetString(pStmt, 4, patient.getMiddleName());
            SQLUtil.SafeSetString(pStmt, 5, patient.getSex());
            SQLUtil.SafeSetString(pStmt, 6, patient.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, patient.getDob());
            SQLUtil.SafeSetString(pStmt, 8, patient.getAddressStreet());
            SQLUtil.SafeSetString(pStmt, 9, patient.getAddressStreet2());
            SQLUtil.SafeSetString(pStmt, 10, patient.getAddressCity());
            SQLUtil.SafeSetString(pStmt, 11, patient.getAddressState());
            SQLUtil.SafeSetString(pStmt, 12, patient.getAddressZip());
            SQLUtil.SafeSetString(pStmt, 13, patient.getPhone());
            SQLUtil.SafeSetString(pStmt, 14, patient.getWorkPhone());
            SQLUtil.SafeSetInteger(pStmt, 15, patient.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 16, patient.getRelationship());
            SQLUtil.SafeSetInteger(pStmt, 17, patient.getCounselor());
            SQLUtil.SafeSetInteger(pStmt, 18, patient.getSpecies());
            SQLUtil.SafeSetInteger(pStmt, 19, patient.getHeight());
            SQLUtil.SafeSetInteger(pStmt, 20, patient.getWeight());
            SQLUtil.SafeSetString(pStmt, 21, patient.getEthnicity());
            SQLUtil.SafeSetBoolean(pStmt, 22, patient.getSmoker());
            SQLUtil.SafeSetBoolean(pStmt, 23, patient.getActive());
            SQLUtil.SafeSetDate(pStmt, 24, patient.getDeactivatedDate());
            SQLUtil.SafeSetString(pStmt, 25, patient.getPatientMRN());
            SQLUtil.SafeSetDate(pStmt, 26, patient.getDOI());
            SQLUtil.SafeSetString(pStmt, 27, patient.getEOA());  
            SQLUtil.SafeSetString(pStmt, 28, patient.getLabPatientId());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = patient.getArNo() + ": " + ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            System.out.println(pStmt.toString());
            return false;
        }
    }
    
    @Override
    public Patients GetPatient(String MasterNumber) throws SQLException
    {
        return GetPatient(MasterNumber, true);
    }

    @Override
    public int GetPatientIdByAR(int ar)
    {
        return GetPatientIdByAR(ar, true);
    }
    
    @Override
    public int GetPatientIdByAR(String ar)
    {
        return GetPatientIdByAR(ar, true);
    }
    
    public int GetPatientIdByLabId(String labPatientId)
    {
        return GetPatientIdByLabId(labPatientId, true);
    }
    
    public static List<Patients> getPatients(boolean activeOnly) throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String sql = "SELECT * FROM " + table;
        if (activeOnly) sql += " WHERE active = 1";
        
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        List<Patients> patients = new ArrayList<>();
        while (rs.next())
        {
            patients.add(SetPatientFromResultSet(new Patients(), rs));
        }
        
        rs.close();
        pStmt.close();
        
        return patients;        
    }
    
    public int GetPatientIdByLabId(String labId, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;
        String ids = "";
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idPatients` FROM "
                    + table
                    + " WHERE `labPatientId` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, labId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            if (ids.isEmpty() == false) {
                id = Integer.parseInt(ids);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (SQLException | NumberFormatException ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
     public Patients GetPatientByNameAndDOB(String lastName, String firstName, String dob) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            Patients patient = new Patients();
            
            String query = "SELECT p.* "
                    + "FROM " + table + " p "
                    + "WHERE p.lastName = '" + lastName +"'"
                    + " AND p.firstName = '" + firstName + "'"
                    + " AND p.dob = '" + dob +  " 00:00:00'";
            PreparedStatement pStmt = con.prepareStatement(query);
            
          
            
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            pStmt.close();

            return patient;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    @Override
    public Patients GetTemporaryPatient(Date OrderDate, int Accession)
    {
        return GetTemporaryPatient(OrderDate, Accession, true);
    }
    
    public boolean PatientExistsByAr(String ar)
    {
        return PatientExistsByAr(ar, true);
    }
    
    @Override
    public boolean PatientExistsByAr(int ar)
    {
        return PatientExistsByAr(ar, true);
    }
    
    public Patients GetPatient(String MasterNumber, boolean IsActive) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Patients patient = new Patients();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `arNo` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, MasterNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            stmt.close();

            return patient;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Patients GetPatientById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Patients patient = new Patients();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idPatients` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            stmt.close();

            return patient;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public int GetPatientIdByAR(int ar, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;
        String ids = "";
        try {
            Statement stmt = con.createStatement();

            String query = "SELECT `idPatients` FROM "
                    + table
                    + " WHERE `arNo` = '" + ar + "'"
                    + " AND `active` = " + (IsActive?1:0);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                ids = rs.getString(1);
            }

            if (ids.isEmpty() == false) {
                id = Integer.parseInt(ids);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (SQLException | NumberFormatException ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }

    public int GetPatientIdByAR(String ar, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;
        String ids = "";
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idPatients` FROM "
                    + table
                    + " WHERE `arNo` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            if (ids.isEmpty() == false) {
                id = Integer.parseInt(ids);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (SQLException | NumberFormatException ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public int GetPatientIdByMRN(String MRN)
    {
        return GetPatientIdByMRN(MRN, true);
    }
    
    public int GetPatientIdByMRN(String MRN, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        int id = 0;
        String ids = "";
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT `idPatients` FROM "
                    + table
                    + " WHERE `patientMRN` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, MRN);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ids = rs.getString(1);
            }

            if (ids.isEmpty() == false) {
                id = Integer.parseInt(ids);
            }

            rs.close();
            stmt.close();

            return id;
        } catch (SQLException | NumberFormatException ex) {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public Patients GetPatientByMRN(String MRN, boolean IsActive) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Patients patient = new Patients();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `patientMRN` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, MRN);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            stmt.close();

            return patient;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Patients GetTemporaryPatient(Date OrderDate, int Accession, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(OrderDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String genAR = String.format("%04d%02d%02d%06d", year, month, day, Accession);
        try {
            Patients patient = new Patients();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `arNo` = '" + genAR + "'"
                    + " AND `active` = " + (IsActive?1:0);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            stmt.close();

            return patient;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Patients GetPatientByOrderId(int orderId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            Patients patient = new Patients();
            
            String query = "SELECT p.* "
                    + "FROM " + table + " p "
                    + "INNER JOIN orders o ON p.idPatients = o.patientId "
                    + "WHERE o.idOrders = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            
            pStmt.setInt(1, orderId);
            
            ResultSet rs = pStmt.executeQuery();
            
            
            //String sql = "SELECT * FROM " + table + " WHERE `labPatientID` LIKE ?";
            //PreparedStatement pStmt = con.prepareStatement(sql);
            //pStmt.setString(1, labPatientID);
            //ResultSet rs = pStmt.executeQuery();
            
            
            
          
            if (rs.next()) {
                SetPatientFromResultSet(patient, rs);
            }

            rs.close();
            pStmt.close();

            return patient;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean PatientExistsByAr(int ar, boolean IsActive)
    {
        try{
        return PatientExistsByAr(String.valueOf(ar), IsActive);
        } catch (NumberFormatException sex) {
            System.out.println(sex.toString());
            return false;
        }
    }
    
    public boolean PatientExistsByAr(String ar, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `arNo` = '" + ar + "'"
                    + " AND `active` = " + (IsActive?1:0);

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean PatientExistsByMRN(String MRN, boolean IsActive) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PreparedStatement stmt = null; //con.createStatement();
            int rowCount = -1;

            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `patientMRN` = ?"
                    + " AND `active` = " + (IsActive?1:0);

            stmt = createStatement(con, query, MRN);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean PatientExistsByID(int id) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `idPatients` = " + id;

            ResultSet rs = stmt.executeQuery(query);

            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();
            return rowCount > 0;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    @Override
    public ResultSet GetResultSetByQuery(String Select, String Where) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Statement stmt = con.createStatement();
            int rowCount = -1;

            String query = Select + " FROM " + table
                    + Where;

            ResultSet rs = stmt.executeQuery(query);

            return rs;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public ResultSet SearchLabPatientID(String labPatientID, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (labPatientID == null) labPatientID = "";
        labPatientID = labPatientID.replaceAll("%", "");
        String sql = "SELECT * FROM " + table + " WHERE `labPatientID` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, labPatientID);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchLastName(String lastNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (lastNameFragment == null) lastNameFragment = "";
        lastNameFragment = lastNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `lastName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, lastNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchFirstName(String firstNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (firstNameFragment == null) firstNameFragment = "";
        firstNameFragment = firstNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `firstName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, firstNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchFullName(String lastNameFragment, String firstNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (firstNameFragment == null) firstNameFragment = "";
        firstNameFragment = firstNameFragment.replaceAll("%", "") + '%';
        if (lastNameFragment == null) lastNameFragment = "";
        lastNameFragment = lastNameFragment.replaceAll("%", "") + '%';
        
        String sql = "SELECT * FROM " + table + " WHERE `firstName` LIKE ? AND `lastName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, firstNameFragment);
        pStmt.setString(2, lastNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;        
    }
    
    public static Patients SetPatientFromResultSet(ResultSet rs) throws SQLException
    {
        return SetPatientFromResultSet(new Patients(), rs);
    }
    
    public Long[] GetAllArNos() {
        
        ArrayList<Long> arNos = new ArrayList<>();
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT DISTINCT CAST(p.arNo AS UNSIGNED) AS `NumericArNo` " +
                "FROM " + table + " p " +
                "WHERE CAST(p.arNo AS UNSIGNED) <= 9223372036854775807 " +
                "ORDER BY NumericArNo ASC";

            stmt = createStatement(con, query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                arNos.add(rs.getLong("NumericArNo"));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
        
        int arCount = arNos.size();
        Object[] tmpArNos = arNos.toArray();
        Long[] aryArNos = new Long[arCount];
        System.arraycopy(tmpArNos, 0, aryArNos, 0, arCount);
        
        return aryArNos;
    }
    
    public static Patients SetPatientFromResultSet(Patients patient, ResultSet rs) throws SQLException
    {
        patient.setIdPatients(rs.getInt("idPatients"));
        patient.setArNo(rs.getString("arNo"));
        patient.setLastName(rs.getString("lastName"));
        patient.setFirstName(rs.getString("firstName"));
        patient.setMiddleName(rs.getString("middleName"));
        patient.setSex(rs.getString("sex"));
        patient.setSsn(rs.getString("ssn"));
        patient.setDob(rs.getDate("dob"));
        patient.setAddressStreet(rs.getString("addressStreet"));
        patient.setAddressStreet2(rs.getString("addressStreet2"));
        patient.setAddressCity(rs.getString("addressCity"));
        patient.setAddressState(rs.getString("addressState"));
        patient.setAddressZip(rs.getString("addressZip"));
        patient.setPhone(rs.getString("phone"));
        patient.setWorkPhone(rs.getString("workPhone"));
        patient.setSubscriber(rs.getInt("subscriber"));
        patient.setRelationship(rs.getString("relationship"));
        patient.setCounselor(rs.getInt("counselor"));
        patient.setSpecies(rs.getInt("species"));
        patient.setHeight(rs.getInt("height"));
        patient.setWeight(rs.getInt("weight"));
        patient.setEthnicity(rs.getString("ethnicity"));
        patient.setSmoker(rs.getBoolean("smoker"));
        patient.setActive(rs.getBoolean("active"));
        patient.setDeactivatedDate(rs.getDate("deactivatedDate"));
        patient.setPatientMRN(rs.getString("patientMRN"));
        patient.setDOI(rs.getDate("DOI"));
        patient.setEOA(rs.getString("EOA"));
        patient.setLabPatientId(rs.getString("labPatientId"));

        return patient;
    }
    
    /**
     * Deletes a patient record and patient comment (if one exists)
     * @param ID
     * @return 
     */
    public boolean DeleteByPatientID(int ID)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try
        {
            
            String stmt = "DELETE FROM " + table + " WHERE idPatients = ?";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);            
            pStmt.setInt(1, ID);            
            pStmt.executeUpdate();            
            pStmt.close();            
            
            
        }catch(SQLException ex){
            System.out.println("PatientDAO::Delete: Unable to delete patient id:" + ID);
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }
    
    public java.sql.Date DeactivateByID(int ID) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try
        {
            java.util.Date uDate = new java.util.Date();
            java.sql.Date sDate = Convert.ToSQLDate(uDate);
            
            String stmt = "UPDATE " + table + "SET "
                    + "`active` = ?, "
                    + "`deactivatedDate` = ? "
                    + "WHERE `active` = ? "
                    + "AND `idPatients` = " + ID;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setBoolean(1, false);
            pStmt.setDate(2, sDate);
            pStmt.setBoolean(3, true);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return sDate;
            
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) 
    {

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

    private String GenerateUpdateStatement(ArrayList<String> fields) 
    {

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
        try
        {
            return InsertPatient((Patients)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdatePatient((Patients)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        int ID = ((Patients)obj).getIdPatients();
        return (DeactivateByID(ID) != null ? true : null);
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetPatientById(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
