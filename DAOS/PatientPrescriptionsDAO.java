/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PatientPrescriptions;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eboss
 */
public class PatientPrescriptionsDAO implements DAOInterface, IStructureCheckable {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`patientPrescriptions`";
    
    public boolean InsertPrescription(PatientPrescriptions prescription) throws SQLException {
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
                    + " `patientId`,"
                    + " `drugId`)"
                    + " values (?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, prescription.getPatientId());
            pStmt.setInt(2, prescription.getDrugId());

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
    
    public boolean UpdatePrescription(PatientPrescriptions prescription) throws SQLException {
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
                    + " `patientId` = ?,"
                    + " `drugId` = ? "
                    + "WHERE `idPatientPrescriptions` = " + prescription.getIdPatientPrescriptions();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, prescription.getPatientId());
            pStmt.setInt(2, prescription.getDrugId());

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
    
    public PatientPrescriptions GetPrescription(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PatientPrescriptions prescription = new PatientPrescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idPatientPrescriptions` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                prescription.setIdPatientPrescriptions(rs.getInt("idPatientPrescriptions"));
                prescription.setPatientId(rs.getInt("patientId"));
                prescription.setDrugId(rs.getInt("drugId"));
                prescription.setIsActive(rs.getBoolean("isActive"));
                prescription.setDateCreated(rs.getDate("dateCreated"));

            }

            rs.close();
            stmt.close();

            return prescription;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public PatientPrescriptions[] GetPrescriptionsByPatientId(int PatientId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<PatientPrescriptions> presList = new ArrayList<PatientPrescriptions>();
            PatientPrescriptions prescription = new PatientPrescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `patientId` = " + PatientId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                prescription = new PatientPrescriptions();
                prescription.setIdPatientPrescriptions(rs.getInt("idPatientPrescriptions"));
                prescription.setPatientId(rs.getInt("patientId"));
                prescription.setDrugId(rs.getInt("drugId"));
                prescription.setIsActive(rs.getBoolean("isActive"));
                prescription.setDateCreated(rs.getDate("dateCreated"));
                
                presList.add(prescription);
            }

            rs.close();
            stmt.close();

            PatientPrescriptions[] outpres = new PatientPrescriptions[presList.size()];
            outpres = presList.toArray(outpres);
            return outpres;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean PrescriptionExists(int PatientId, int DrugId) {
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
            ResultSet rs;
            int rowCount;

            String query = "SELECT COUNT(*) FROM " + table
                    + " WHERE `patientId` = " + PatientId
                    + " AND `drugId` = " + DrugId + ";";

            rs = stmt.executeQuery(query);
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
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    public int GetPrescriptionId(int PatientId, int DrugId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs;
            int ID;

            String query = "SELECT `idPatientPrescriptions` FROM " + table
                    + " WHERE `patientId` = " + PatientId
                    + " AND `drugId` = " + DrugId + ";";

            rs = stmt.executeQuery(query);
            rs.next();
            ID = rs.getInt(1);

            rs.close();
            stmt.close();

            if (ID > 0) {
                return ID;
            } else {
                return -1;
            }
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return -1;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertPrescription((PatientPrescriptions)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientPrescriptionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdatePrescription((PatientPrescriptions)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientPrescriptionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
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
            PatientPrescriptions prescription = (PatientPrescriptions)obj;

            String stmt = "DELETE FROM " + table
                    + " WHERE `idPatientPrescriptions` = " + prescription.getIdPatientPrescriptions();


            PreparedStatement pStmt = con.prepareStatement(stmt);
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return (retVal > 0);
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetPrescription(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(PatientPrescriptionsDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `patientPrescriptions`.`idPatientPrescriptions`,\n"
                + "    `patientPrescriptions`.`patientId`,\n"
                + "    `patientPrescriptions`.`drugId`,\n"
                + "    `patientPrescriptions`.`isActive`,\n"
                + "    `patientPrescriptions`.`dateCreated`\n"
                + "FROM `css`.`patientPrescriptions` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
    
}
