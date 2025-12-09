package Web.DAOS;

import DAOS.*;
import DAOS.IDAOS.IPatientDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Patients;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.*;
import java.util.Calendar;

import static Utility.SQLUtil.createStatement;

/**
 * @date:   Mar 12, 2012
 * @author: CSS Dev
 * 
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: PatientDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class PatientDAO implements IPatientDAO, IStructureCheckable
{
    Web.Database.WebDatabaseSingleton dbs = Web.Database.WebDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`patients`";
    
    public boolean InsertPatient(Patients patient) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        /*if(GetPatientIdByAR(patient.getArNo()) != 0)
            return UpdatePatient(patient);*/
        try
        {
            String stmt = "INSERT INTO " + table + "("
                    + " `arNo`,"
                    + " `lastName`,"
                    + " `firstName`,"
                    + " `middleName`,"
                    + " `sex`,"
                    + " `ssn`,"
                    + " `dob`,"
                    + " `addressStreet`,"
                    + " `addressStreet2`,"
                    + " `addressCity`,"
                    + " `addressState`,"
                    + " `addressZip`,"
                    + " `phone`,"
                    + " `workPhone`,"
                    + " `subscriber`,"
                    + " `relationship`,"
                    + " `counselor`,"
                    + " `species`,"
                    + " `height`,"
                    + " `weight`,"
                    + " `ethnicity`,"
                    + " `smoker`"
                    + ")"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1,patient.getArNo());
            pStmt.setString(2,patient.getLastName());
            pStmt.setString(3,patient.getFirstName());
            pStmt.setString(4,patient.getMiddleName());
            pStmt.setString(5,patient.getSex());
            pStmt.setString(6,patient.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, patient.getDob());
            /*pStmt.setTimestamp(7,Convert.ToSQLDateTime(patient.getDob()));*/
            //pStmt.setDate(7,Convert.ToSQLDate(patient.getDob()));
            pStmt.setString(8,patient.getAddressStreet());
            pStmt.setString(9,patient.getAddressStreet2());
            pStmt.setString(10,patient.getAddressCity());
            pStmt.setString(11,patient.getAddressState());
            pStmt.setString(12,patient.getAddressZip());
            pStmt.setString(13,patient.getPhone());
            pStmt.setString(14,patient.getWorkPhone());
            SQLUtil.SafeSetInteger(pStmt, 15, patient.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 16, patient.getRelationship());
            SQLUtil.SafeSetInteger(pStmt, 17, patient.getCounselor());
            SQLUtil.SafeSetInteger(pStmt, 18, patient.getSpecies());
            pStmt.setInt(19,patient.getHeight());
            pStmt.setInt(20,patient.getWeight());
            pStmt.setString(21,patient.getEthnicity());
            pStmt.setBoolean(22,patient.getSmoker());
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = patient.getArNo() + ": " + ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    public boolean UpdatePatient(Patients patient) throws SQLException
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
            
            
            String stmt = "UPDATE " + table + " SET"
                    + " `arNo` = ?,"
                    + " `lastName` = ?,"
                    + " `firstName` = ?,"
                    + " `middleName` = ?,"
                    + " `sex` = ?,"
                    + " `ssn` = ?,"
                    + " `dob` = ?,"
                    + " `addressStreet` = ?,"
                    + " `addressStreet2` = ?,"
                    + " `addressCity` = ?,"
                    + " `addressState` = ?,"
                    + " `addressZip` = ?,"
                    + " `phone` = ?,"
                    + " `workPhone` = ?,"
                    + " `subscriber` = ?,"
                    + " `relationship` = ?,"
                    + " `counselor` = ?,"
                    + " `species` = ?,"
                    + " `height` = ?,"
                    + " `weight` = ?,"
                    + " `ethnicity` = ?,"
                    + " `smoker` = ? "
                    + "WHERE `idPatients` = " + patient.getIdPatients();
            
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1,patient.getArNo());
            pStmt.setString(2,patient.getLastName());
            pStmt.setString(3,patient.getFirstName());
            pStmt.setString(4,patient.getMiddleName());
            pStmt.setString(5,patient.getSex());
            pStmt.setString(6,patient.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, patient.getDob());
            /*pStmt.setTimestamp(7,Convert.ToSQLDateTime(patient.getDob()));*/
            //pStmt.setDate(7,Convert.ToSQLDate(patient.getDob()));
            pStmt.setString(8,patient.getAddressStreet());
            pStmt.setString(9,patient.getAddressStreet2());
            pStmt.setString(10,patient.getAddressCity());
            pStmt.setString(11,patient.getAddressState());
            pStmt.setString(12,patient.getAddressZip());
            pStmt.setString(13,patient.getPhone());
            pStmt.setString(14,patient.getWorkPhone());
            SQLUtil.SafeSetInteger(pStmt, 15, patient.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 16, patient.getRelationship());
            SQLUtil.SafeSetInteger(pStmt, 17, patient.getCounselor());
            SQLUtil.SafeSetInteger(pStmt, 18, patient.getSpecies());
            pStmt.setInt(19,patient.getHeight());
            pStmt.setInt(20,patient.getWeight());
            pStmt.setString(21,patient.getEthnicity());
            pStmt.setBoolean(22,patient.getSmoker());

            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = patient.getArNo() + ": " + ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    public Patients GetPatient(String MasterNumber) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            Patients patient = new Patients();
            PreparedStatement stmt  = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `arNo` = ?";
            
            stmt = createStatement(con, query, MasterNumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                SetPatientFromResultSet(patient, rs);
            }
            
            rs.close();
            stmt.close();
            
            return patient;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    public Patients GetPatientById(int Id) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            Patients patient = new Patients();
            Statement stmt  = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `idPatients` = " + Id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetPatientFromResultSet(patient, rs);
            }
            
            rs.close();
            stmt.close();
            
            return patient;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }
    
    public int GetPatientIdByAR(int ar)
    {
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
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idPatients` FROM "
                    + table +
                    "WHERE `arNo` = '" + ar + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                ids = rs.getString(1);
            }
            
            if(ids.isEmpty() == false)
                id = Integer.parseInt(ids);
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public int GetPatientIdByAR(String ar)
    {
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
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT `idPatients` FROM "
                    + table +
                    "WHERE `arNo` = ?";
            
            stmt = createStatement(con, query, ar);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                ids = rs.getString(1);
            }
            
            if(ids.isEmpty() == false)
                id = Integer.parseInt(ids);
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public Patients GetTemporaryPatient(Date OrderDate, int Accession)
    {
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
        int year  = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day   = cal.get(Calendar.DAY_OF_MONTH);
        String genAR = String.format("%04d%02d%02d%06d", year, month, day, Accession);
        try
        {
            Patients patient = new Patients();
            Statement stmt  = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `arNo` = '" + genAR + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetPatientFromResultSet(patient, rs);
            }
            
            rs.close();
            stmt.close();
            
            return patient;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        } 
    }
    
    public boolean PatientExistsByAr(int ar)
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
            Statement stmt = con.createStatement();
            int rowCount = -1;
            
            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `arNo` = '" + ar + "'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            return false;
        }
    }
    
    public boolean PatientExistsByID(int id)
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
            Statement stmt = con.createStatement();
            int rowCount = -1;
            
            String query = "Select COUNT(*) FROM " + table
                    + " WHERE `idPatients` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            return false;
        }
    }
    
    public ResultSet GetResultSetByQuery(String Select, String Where)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            Statement stmt = con.createStatement();
            int rowCount = -1;
            
            String query = Select + " FROM " + table
                    + Where;
            
            ResultSet rs = stmt.executeQuery(query);
            
            return rs;
        }catch(Exception ex)
        {
            return null;
        }
    }
    
    private Patients SetPatientFromResultSet(Patients patient, ResultSet rs) throws SQLException
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
        

        
        return patient;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `patients`.`idPatients`,\n"
                + "    `patients`.`arNo`,\n"
                + "    `patients`.`lastName`,\n"
                + "    `patients`.`firstName`,\n"
                + "    `patients`.`middleName`,\n"
                + "    `patients`.`sex`,\n"
                + "    `patients`.`ssn`,\n"
                + "    `patients`.`dob`,\n"
                + "    `patients`.`addressStreet`,\n"
                + "    `patients`.`addressStreet2`,\n"
                + "    `patients`.`addressCity`,\n"
                + "    `patients`.`addressState`,\n"
                + "    `patients`.`addressZip`,\n"
                + "    `patients`.`phone`,\n"
                + "    `patients`.`workPhone`,\n"
                + "    `patients`.`subscriber`,\n"
                + "    `patients`.`relationship`,\n"
                + "    `patients`.`counselor`,\n"
                + "    `patients`.`species`,\n"
                + "    `patients`.`height`,\n"
                + "    `patients`.`weight`,\n"
                + "    `patients`.`ethnicity`,\n"
                + "    `patients`.`smoker`,\n"
                + "    `patients`.`active`,\n"
                + "    `patients`.`deactivatedDate`,\n"
                + "    `patients`.`patientMRN`,\n"
                + "    `patients`.`DOI`,\n"
                + "    `patients`.`EOA`\n"
                + "FROM `cssweb`.`patients` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
    
}
