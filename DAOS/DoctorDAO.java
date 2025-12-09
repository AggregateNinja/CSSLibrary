package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Doctors;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Mar 13, 2012
 * @author: Keith Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: DoctorDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class DoctorDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`doctors`";

    public boolean InsertDoctor(Doctors doctor) throws SQLException {
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
                    + " `number`,"
                    + " `firstName`,"
                    + " `lastName`,"
                    + " `NPI`,"
                    + " `UPIN`,"
                    + " `address1`,"
                    + " `address2`,"
                    + " `city`,"
                    + " `state`,"
                    + " `zip`,"
                    + " `externalId`,"
                    + " `phone`,"
                    + " `fax`,"
                    + " `email`,"
                    + " `active`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, doctor.getNumber());
            pStmt.setString(2, doctor.getFirstName());
            pStmt.setString(3, doctor.getLastName());
            if (doctor.getNpi() != null)
            {
                SQLUtil.SafeSetLong(pStmt, 4, doctor.getNpi().longValue());
            }
            //SQLUtil.SafeSetInteger(pStmt, 4, doctor.getNpi());
            pStmt.setString(5, doctor.getUpin());
            pStmt.setString(6, doctor.getAddress1());
            pStmt.setString(7, doctor.getAddress2());
            pStmt.setString(8, doctor.getCity());
            pStmt.setString(9, doctor.getState());
            pStmt.setString(10, doctor.getZip());
            pStmt.setString(11, doctor.getExternalId());
            pStmt.setString(12, doctor.getPhone());
            pStmt.setString(13, doctor.getFax());
            pStmt.setString(14, doctor.getEmail());
            pStmt.setBoolean(15, doctor.isActive());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateDoctor(Doctors doctor) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = "UPDATE " + table + " SET"
                    + " `number` = ?,"
                    + " `firstName` = ?,"
                    + " `lastName` = ?,"
                    + " `NPI` = ?,"
                    + " `UPIN` = ?,"
                    + " `address1` = ?,"
                    + " `address2` = ?,"
                    + " `city` = ?,"
                    + " `state` = ?,"
                    + " `zip` = ?,"
                    + " `externalId` = ?,"
                    + " `phone` = ?,"
                    + " `fax` = ?,"
                    + " `email` = ?,"
                    + " `active` = ? "
                    + "WHERE `iddoctors` = " + doctor.getIddoctors();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, doctor.getNumber());
            pStmt.setString(2, doctor.getFirstName());
            pStmt.setString(3, doctor.getLastName());
            
            try
            {
                Long npi = doctor.getNpi().longValue();
                pStmt.setLong(4, doctor.getNpi().longValue());
            }
            catch (Exception ex)
            {
                System.out.println("Unable to assign value for NPI");
            }            
            pStmt.setLong(4, doctor.getNpi().longValue());
            //pStmt.setInt(4, doctor.getNpi());
            pStmt.setString(5, doctor.getUpin());
            pStmt.setString(6, doctor.getAddress1());
            pStmt.setString(7, doctor.getAddress2());
            pStmt.setString(8, doctor.getCity());
            pStmt.setString(9, doctor.getState());
            pStmt.setString(10, doctor.getZip());
            pStmt.setString(11, doctor.getExternalId());
            pStmt.setString(12, doctor.getPhone());
            pStmt.setString(13, doctor.getFax());
            pStmt.setString(14, doctor.getEmail());
            pStmt.setBoolean(15, doctor.isActive());


            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Doctors GetDoctor(int DoctorNumber) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Doctors doctor = new Doctors();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `number` = " + DoctorNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                doctor.setIddoctors(rs.getInt("iddoctors"));
                doctor.setNumber(rs.getInt("number"));
                doctor.setFirstName(rs.getString("firstName"));
                doctor.setLastName(rs.getString("lastName"));
                doctor.setNpi(rs.getLong("NPI"));
                //doctor.setNpi(rs.getInt("NPI"));
                doctor.setUpin(rs.getString("UPIN"));
                doctor.setAddress1(rs.getString("address1"));
                doctor.setAddress2(rs.getString("address2"));
                doctor.setCity(rs.getString("city"));
                doctor.setState(rs.getString("state"));
                doctor.setZip(rs.getString("zip"));
                doctor.setExternalId(rs.getString("externalId"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setFax(rs.getString("fax"));
                doctor.setEmail(rs.getString("email"));
                doctor.setActive(rs.getBoolean("active"));

            }

            rs.close();
            stmt.close();

            return doctor;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Doctors GetDoctorById(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Doctors doctor = new Doctors();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `iddoctors` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                doctor.setIddoctors(rs.getInt("iddoctors"));
                doctor.setNumber(rs.getInt("number"));
                doctor.setFirstName(rs.getString("firstName"));
                doctor.setLastName(rs.getString("lastName"));
                doctor.setNpi(rs.getLong("NPI"));
                //doctor.setNpi(rs.getInt("NPI"));
                doctor.setUpin(rs.getString("UPIN"));
                doctor.setAddress1(rs.getString("address1"));
                doctor.setAddress2(rs.getString("address2"));
                doctor.setCity(rs.getString("city"));
                doctor.setState(rs.getString("state"));
                doctor.setZip(rs.getString("zip"));
                doctor.setExternalId(rs.getString("externalId"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setFax(rs.getString("fax"));
                doctor.setEmail(rs.getString("email"));
                doctor.setActive(rs.getBoolean("active"));
            }

            rs.close();
            stmt.close();

            return doctor;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean DoctorsExists(int DocNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`number` = " + DocNo);
            rs.next();
            rowCount = rs.getInt(1);

            rs.close();
            stmt.close();

            if (rowCount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            return false;
        }
    }
    
    /**
     * Returns true if a doctor currently marked "active" is using the provided number.
     * 
     * @param doctorNumber
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean isDoctorNumberInUse(Integer doctorNumber)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (doctorNumber == null || doctorNumber <= 0)
            throw new IllegalArgumentException("DoctorDAO::isDoctorNumberInUse: "
                    + "provided doctorNumber argument was NULL or <= 0");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);        
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE `active` = 1 AND `number` = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, doctorNumber);
        ResultSet rs = pStmt.executeQuery();
        
        int count = -1;
        if (rs.next())
        {
            count = rs.getInt(1);
        }
        if (count < 0) throw new SQLException("DoctorDAO::isDoctorNumberInUse: "
            + "Count returned for doctor " + String.valueOf(doctorNumber) + " was " + count);
        
        return (count > 0);
    }    

    public int GetDoctorID(int DocNo) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = null;
            ResultSet rs = null;
            int x = 0;

            stmt = con.createStatement();
            String query = "SELECT `iddoctors` FROM " + table + " "
                    + "WHERE `number` = " + DocNo;

            rs = stmt.executeQuery(query);

            if (rs.next()) {
                x = rs.getInt("iddoctors");
            }
            rs.close();
            stmt.close();
            return x;
        } catch (Exception ex) {
            return 0;
        }
    }

    public Integer GetNextAvailableDoctorNumber() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            int max = -1;
            Statement stmt = null;
            String query = "SELECT MAX(`number`) AS 'Next' FROM " + table + ";";
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                max = rs.getInt("Next");
            }
            rs.close();
            stmt.close();
            return max;
        } catch (Exception ex) {
            //TODO: Add exception handling
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public List<Doctors> SearchDoctorByNumber(String NumberFragment) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            List<Doctors> lstDoctors = new ArrayList<>();
            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " " +
                "WHERE `number` REGEXP ? " +
                " ORDER BY `number` ASC;";


            stmt = createStatement(con, query, NumberFragment);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Doctors doctor = new Doctors();
                doctor.setIddoctors(rs.getInt("iddoctors"));
                doctor.setNumber(rs.getInt("number"));
                doctor.setFirstName(rs.getString("firstName"));
                doctor.setLastName(rs.getString("lastName"));
                String npiStr = rs.getString("NPI");
                doctor.setNpi(rs.getLong("NPI"));
                //doctor.setNpi(rs.getInt("NPI"));
                doctor.setUpin(rs.getString("UPIN"));
                doctor.setAddress1(rs.getString("address1"));
                doctor.setAddress2(rs.getString("address2"));
                doctor.setCity(rs.getString("city"));
                doctor.setState(rs.getString("state"));
                doctor.setZip(rs.getString("zip"));
                doctor.setExternalId(rs.getString("externalId"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setFax(rs.getString("fax"));
                doctor.setEmail(rs.getString("email"));
                doctor.setActive(rs.getBoolean("active"));
                lstDoctors.add(doctor);
            }

            rs.close();
            stmt.close();
            return lstDoctors;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            if (stmt != null)
            {
                try
                {
                    stmt.close();
                }
                catch (SQLException ex1)
                {
                    System.err.println("And then... " + ex1.getMessage());
                }
            }
            return null;
        }
        
    }
    

    public List<Doctors> SearchDoctorByName(String FirstNameFragment, String LastNameFragment, boolean CaseSensitive, boolean ActiveOnly) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        List<Doctors> dlist = new ArrayList<>();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            String whereLName = "";
            String whereFName = "";
            String whereActive = "";
            String whereclause = "";
            String firstNameParam = "";
            String lastNameParam = "";
            if (LastNameFragment != null) {
                lastNameParam = SQLUtil.createSearchParam(LastNameFragment);
                if (CaseSensitive) {
                    whereLName = "WHERE `lastName` LIKE ? ";
                } else {
                    whereLName = "WHERE LOWER(`lastName`) LIKE LOWER(?) ";
                }
            }
            if (FirstNameFragment != null) {
                firstNameParam = SQLUtil.createSearchParam(FirstNameFragment);
                if (whereLName.isEmpty()) {
                    whereFName = "WHERE ";
                } else {
                    whereFName = "AND ";
                }
                if (CaseSensitive) {
                    whereFName += "`firstName` LIKE ? ";
                } else {
                    whereFName += "LOWER(`firstName`) LIKE LOWER(?) ";
                }
            }
            if(ActiveOnly)
            {
                if (whereLName.isEmpty() && whereFName.isEmpty()) {
                    whereActive = "WHERE ";
                } else {
                    whereActive = "AND ";
                }
                whereActive += " `active` = true ";
            }
            whereclause = whereLName + whereFName + whereActive;
            whereclause += "ORDER BY `lastName`, `firstName` ASC;";
            //stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + whereclause;
            
            if (LastNameFragment != null && FirstNameFragment != null){
                stmt = createStatement(con, query, lastNameParam, firstNameParam);
            }
            else if(LastNameFragment != null){
                stmt = createStatement(con, query, lastNameParam);
            }
            else {
                stmt = createStatement(con, query, firstNameParam);
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                Doctors doctor = new Doctors();
                doctor.setIddoctors(rs.getInt("iddoctors"));
                doctor.setNumber(rs.getInt("number"));
                doctor.setFirstName(rs.getString("firstName"));
                doctor.setLastName(rs.getString("lastName"));
                String npiStr = rs.getString("NPI");
                doctor.setNpi(rs.getLong("NPI"));
                //doctor.setNpi(rs.getInt("NPI"));
                doctor.setUpin(rs.getString("UPIN"));
                doctor.setAddress1(rs.getString("address1"));
                doctor.setAddress2(rs.getString("address2"));
                doctor.setCity(rs.getString("city"));
                doctor.setState(rs.getString("state"));
                doctor.setZip(rs.getString("zip"));
                doctor.setExternalId(rs.getString("externalId"));
                doctor.setPhone(rs.getString("phone"));
                doctor.setFax(rs.getString("fax"));
                doctor.setEmail(rs.getString("email"));
                doctor.setActive(rs.getBoolean("active"));
                dlist.add(doctor);
            }
            rs.close();
            stmt.close();
            return dlist;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    
    public List<Doctors> SearchDoctorByName(String FirstNameFragment, String LastNameFragment, boolean CaseSensitive) {

        return SearchDoctorByName(FirstNameFragment, LastNameFragment, CaseSensitive, false);
    }

    public List<Doctors> SearchDoctorByName(String FirstNameFragment, String LastNameFragment) {

        return SearchDoctorByName(FirstNameFragment, LastNameFragment, false, false);
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertDoctor((Doctors)obj);
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(DoctorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateDoctor((Doctors)obj);
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(DoctorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetDoctorById(ID);
        }
        catch (SQLException ex)
        {
            //Logger.getLogger(DoctorDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `doctors`.`iddoctors`,\n"
                + "    `doctors`.`number`,\n"
                + "    `doctors`.`firstName`,\n"
                + "    `doctors`.`lastName`,\n"
                + "    `doctors`.`NPI`,\n"
                + "    `doctors`.`UPIN`,\n"
                + "    `doctors`.`address1`,\n"
                + "    `doctors`.`address2`,\n"
                + "    `doctors`.`city`,\n"
                + "    `doctors`.`state`,\n"
                + "    `doctors`.`zip`,\n"
                + "    `doctors`.`externalId`,\n"
                + "    `doctors`.`phone`,\n"
                + "    `doctors`.`fax`,\n"
                + "    `doctors`.`email`,\n"
                + "    `doctors`.`active`,\n"
                + "    `doctors`.`locationId`\n"
                + "FROM `css`.`doctors` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, con);

    }
}
