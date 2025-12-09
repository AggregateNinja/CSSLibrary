/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package EMR.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IPrescriptionDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Prescriptions;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ryan
 */
public class PrescriptionDAO implements IPrescriptionDAO, DAOInterface, IStructureCheckable {
    EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`prescriptions`";
    
    public boolean InsertPrescription(Prescriptions prescription) throws SQLException {
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
                    + " `orderId`,"
                    + " `drugId`)"
                    + " values (?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, prescription.getOrderId());
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

    public boolean UpdatePrescription(Prescriptions prescription) throws SQLException {
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
                    + " `orderId` = ?,"
                    + " `drugId` = ? "
                    + "WHERE `idprescriptions` = " + prescription.getIdprescriptions();


            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, prescription.getOrderId());
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

    public Prescriptions GetPrescription(int Id) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            Prescriptions prescription = new Prescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idprescriptions` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {


                prescription.setIdprescriptions(rs.getInt("idprescriptions"));
                prescription.setOrderId(rs.getInt("orderId"));
                prescription.setDrugId(rs.getInt("drugId"));

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

    public Prescriptions[] GetPrescriptionsByOrderId(int OrderId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList< Prescriptions> presList = new ArrayList< Prescriptions>();
            Prescriptions prescription = new Prescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + OrderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                prescription = new Prescriptions();
                prescription.setIdprescriptions(rs.getInt("idprescriptions"));
                prescription.setOrderId(rs.getInt("orderId"));
                prescription.setDrugId(rs.getInt("drugId"));
                presList.add(prescription);
            }

            rs.close();
            stmt.close();

            Prescriptions[] outpres = new Prescriptions[presList.size()];
            outpres = presList.toArray(outpres);
            return outpres;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public boolean PrescriptionExists(int OrderId, int DrugId) {
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
                    + " WHERE `orderId` = " + OrderId
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

    public int GetPrescriptionId(int OrderId, int DrugId) {
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

            String query = "SELECT `idprescriptions` FROM " + table
                    + " WHERE `orderId` = " + OrderId
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
        String query = "SELECT `prescriptions`.`idprescriptions`,\n"
                + "    `prescriptions`.`orderId`,\n"
                + "    `prescriptions`.`drugId`\n"
                + "FROM `emrorders`.`prescriptions` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
