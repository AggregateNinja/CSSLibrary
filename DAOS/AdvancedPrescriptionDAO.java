/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedDiagnosisCodes;
import DOS.AdvancedPrescriptions;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class AdvancedPrescriptionDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedPrescriptions`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();    
    
    public AdvancedPrescriptionDAO()
    {
        fields.add("advancedOrderId");
        fields.add("drugId");
    }
    
    public boolean InsertAdvancedPrescription(AdvancedPrescriptions ap)
    {
        return InsertAdvancedPrescription(ap.getAdvancedOrderId(), ap.getDrugId());
    }
    
    public boolean InsertAdvancedPrescription(
                    Integer advancedOrderId,
                    Integer drugId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        
        String stmt = GenerateInsertStatement(fields);
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, advancedOrderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, drugId);
            
            pStmt.executeUpdate();
            pStmt.close();
            
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }
        return true;
    }
    
    public AdvancedPrescriptions GetAdvancedPrescription(int idAdvancedPrescriptions)
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
            AdvancedPrescriptions ap = new AdvancedPrescriptions();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idAdvancedPrescriptions` = " + idAdvancedPrescriptions;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetAdvancedPrescriptionsFromRecordSet(ap, rs);
            }

            rs.close();
            stmt.close();

            return ap;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<AdvancedPrescriptions> GetByAdvancedOrderId(int advancedOrderId)
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
            ArrayList<AdvancedPrescriptions> advancedPrescriptions = 
                    new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `advancedOrderId` = " + advancedOrderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                advancedPrescriptions.add(
                        SetAdvancedPrescriptionsFromRecordSet(new AdvancedPrescriptions(), rs));
            }
            rs.close();
            stmt.close();

            return advancedPrescriptions;
            
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
        
    }
    
    public void DeleteById(int idAdvancedPrescriptions) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String query = "DELETE FROM " + table + " WHERE idAdvancedPrescriptions = " + idAdvancedPrescriptions;
        PreparedStatement pStmt = con.prepareCall(query);
        pStmt.execute();
    }
    
    public boolean DeleteByAdvancedOrderId(int advancedOrderId)
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
            AdvancedDiagnosisCodes adc = new AdvancedDiagnosisCodes();
            Statement stmt = con.createStatement();

            String query = "DELETE FROM " + table + " WHERE "
                    + " `advancedOrderId` = " + advancedOrderId;

            int delete = stmt.executeUpdate(query);
            return (delete == 1);

        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }        
    }
    
    private AdvancedPrescriptions SetAdvancedPrescriptionsFromRecordSet(
                AdvancedPrescriptions ap, ResultSet rs) throws SQLException
    {
        ap.setIdAdvancedPrescriptions(rs.getInt("idAdvancedPrescriptions"));
        ap.setAdvancedOrderId(rs.getInt("advancedOrderId"));
        ap.setDrugId(rs.getInt("drugId"));
        return ap;
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
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
