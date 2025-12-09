package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedDiagnosisCodes;
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
public class AdvancedDiagnosisCodeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedDiagnosisCodes`";
/**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();    
    
    public AdvancedDiagnosisCodeDAO()
    {
        fields.add("advancedOrderId");
        fields.add("diagnosisCodeId");
    }
    
    public boolean InsertAdvancedDiagnosisCode(AdvancedDiagnosisCodes adc)
    {
        return InsertAdvancedDiagnosisCode(adc.getAdvancedOrderId(), adc.getDiagnosisCodeId());
    }    
    
    public boolean InsertAdvancedDiagnosisCode(
                    Integer advancedOrderId,
                    Integer diagnosisCodeId)
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
            SQLUtil.SafeSetInteger(pStmt, ++i, diagnosisCodeId);
            
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
    
    public AdvancedDiagnosisCodes GetAdvancedDiagnosisCodes(int idAdvancedDiagnosisCodes)
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
            AdvancedDiagnosisCodes adc = new AdvancedDiagnosisCodes();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idAdvancedDiagnosisCodes` = " + idAdvancedDiagnosisCodes;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetAdvancedDiagnosisCodeFromRecordSet(adc, rs);
            }

            rs.close();
            stmt.close();

            return adc;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<AdvancedDiagnosisCodes> GetByAdvancedOrderId(int advancedOrderId)
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
            ArrayList<AdvancedDiagnosisCodes> advancedDiagnosisCodes = 
                    new ArrayList<>();
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `advancedOrderId` = " + advancedOrderId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                advancedDiagnosisCodes.add(
                        SetAdvancedDiagnosisCodeFromRecordSet(new AdvancedDiagnosisCodes(), rs));
            }
            rs.close();
            stmt.close();

            return advancedDiagnosisCodes;
            
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public void DeleteById(int advancedDiagnosisCodeId) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String query = "DELETE FROM " + table + " WHERE idAdvancedDiagnosisCodes = " + advancedDiagnosisCodeId;
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
    
    private AdvancedDiagnosisCodes SetAdvancedDiagnosisCodeFromRecordSet(
                AdvancedDiagnosisCodes adc, ResultSet rs) throws SQLException
    {
        adc.setIdAdvancedDiagnosisCodes(rs.getInt("idAdvancedDiagnosisCodes"));
        adc.setAdvancedOrderId(rs.getInt("advancedOrderId"));
        adc.setDiagnosisCodeId(rs.getInt("diagnosisCodeId"));
        return adc;
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
        String query = "SELECT `advancedDiagnosisCodes`.`idAdvancedDiagnosisCodes`,\n"
                + "    `advancedDiagnosisCodes`.`advancedOrderId`,\n"
                + "    `advancedDiagnosisCodes`.`diagnosisCodeId`\n"
                + "FROM `css`.`advancedDiagnosisCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
