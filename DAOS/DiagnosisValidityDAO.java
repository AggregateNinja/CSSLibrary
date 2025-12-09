/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.DiagnosisValidity;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rob
 */
public class DiagnosisValidityDAO implements IDAO<DiagnosisValidity>, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`diagnosisValidity`";
        
    private final ArrayList<String> fields = new ArrayList<>();
    
    public DiagnosisValidityDAO()
    {
        fields.add("billingPayorId");
        fields.add("active");
    }
    
    /**
     * Inserts the diagnosis validity row and returns a copy containing the
     *  newly-generated database identifier.
     * 
     * @param diagnosisValidity
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public DiagnosisValidity Insert(DiagnosisValidity diagnosisValidity)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidity == null)
            throw new IllegalArgumentException(
                "DiagnosisValidityDAO::Insert: "
                + "Supplied diagnosis validity object was [NULL]");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        pStmt = SetStatementFromDiagnosisValidity(diagnosisValidity, pStmt);
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String errorMsg = "DiagnosisValidityDAO::Insert: Row was not inserted! BillingPayorId: "
                    + diagnosisValidity.getBillingPayorId() == null? "[NULL]" : diagnosisValidity.getBillingPayorId().toString();
            throw new SQLException(errorMsg);
        }
        
        Integer newId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
        {
            if (generatedKeys.next())
            {
                newId = generatedKeys.getInt(1);
            }
            else
            {
                throw new SQLException("Insert failed, no ID obtained.");
            }
        }
        pStmt.close();
        diagnosisValidity.setIdDiagnosisValidity(newId);
        return diagnosisValidity;
    }

    /**
     * Updates the supplied DiagnosisValidity object.
     * @param diagnosisValidity
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public DiagnosisValidity Update(DiagnosisValidity diagnosisValidity)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidity == null || diagnosisValidity.getIdDiagnosisValidity() == null || diagnosisValidity.getIdDiagnosisValidity().equals(0))
        {
            throw new IllegalArgumentException(
                    "DiagnosisValidityDAO::Update: "
                + "Supplied diagnosis validity object was [NULL]");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateUpdateStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromDiagnosisValidity(diagnosisValidity, pStmt);
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String errorMsg = "DiagnosisValidityDAO::Update: "
                    + "Row could not be updated! " + diagnosisValidity.getIdDiagnosisValidity().toString();
            throw new SQLException(errorMsg);
        }
        pStmt.close();
        return diagnosisValidity;
    }

    /**
     * Marks a diagnosis validity row as not active.
     *  Note: calling code must perform logging
     * @param diagnosisValidity
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public void Delete(DiagnosisValidity diagnosisValidity)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidity == null || diagnosisValidity.getIdDiagnosisValidity()== null || diagnosisValidity.getIdDiagnosisValidity().equals(0))
        {
            throw new IllegalArgumentException(
                    "DiagnosisValidityDAO::Delete: "
                + "Supplied diagnosis validity object was [NULL]");
        }
        diagnosisValidity.setActive(false);
        Update(diagnosisValidity);
    }
    
    @Override
    public DiagnosisValidity GetById(Integer validityId) throws SQLException
    {
        String sql = "SELECT * from " + table + " WHERE idDiagnosisValidity = " + validityId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        DiagnosisValidity dv = null;
        if (rs.next())
        {
            dv = new DiagnosisValidity();
            dv = fromRecordSet(dv, rs);
        }
        return dv;
    }
    
    public DiagnosisValidity GetCurrentByPayorID(Integer payorId, boolean active) throws SQLException
    {
        if (payorId == null || payorId.equals(0))
        {
            throw new IllegalArgumentException("Provided payorId is null or 0");
        }
        String sql = "SELECT * from " + table + " `dv` WHERE `dv`.`active` = ? AND `dv`.`billingPayorId` = ?";
        
        int index = 1;
        java.sql.PreparedStatement pStmt = con.prepareStatement(sql);

        pStmt.setBoolean(index++, active);
        pStmt.setInt(index++, payorId);
        ResultSet rs = pStmt.executeQuery();
        DiagnosisValidity dv = null;
        if (rs.next())
        {
            dv = new DiagnosisValidity();
            dv = fromRecordSet(dv, rs);
        }
        pStmt.close();
        return dv;
    }
    
    private List<DiagnosisValidity> getAll() throws SQLException
    {
        String sql = "SELECT * FROM " + table;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ArrayList<DiagnosisValidity> validities = new ArrayList<>();
        while (rs.next())
        {
            DiagnosisValidity dv = fromRecordSet(new DiagnosisValidity(), rs);
            validities.add(dv);
        }
        pStmt.close();
        return validities;
    }
    
    private DiagnosisValidity fromRecordSet(DiagnosisValidity dv, ResultSet rs) throws SQLException
    {
        dv.setIdDiagnosisValidity(rs.getInt("idDiagnosisValidity"));
        dv.setBillingPayorId(rs.getInt("billingPayorId"));
        if (rs.wasNull()) dv.setBillingPayorId(null);
        dv.setActive(rs.getBoolean("active"));
        return dv;
    }
    
    public DiagnosisValidity GetDiagnosisValidityFromResultSet(ResultSet rs)
            throws SQLException
    {
        DiagnosisValidity diagnosisValidity = new DiagnosisValidity();
        diagnosisValidity.setIdDiagnosisValidity(rs.getInt("idDiagnosisValidity"));
        diagnosisValidity.setBillingPayorId(rs.getInt("billingPayorId"));
        diagnosisValidity.setActive(rs.getBoolean("active"));
        return diagnosisValidity;
    }
    
    private PreparedStatement SetStatementFromDiagnosisValidity(DiagnosisValidity diagnosisValidity, PreparedStatement pStmt)
            throws SQLException
    {
        int i=0;
        if (diagnosisValidity.getBillingPayorId()== null) pStmt.setNull(++i, java.sql.Types.INTEGER);
        else pStmt.setInt(++i, diagnosisValidity.getBillingPayorId());
        pStmt.setBoolean(++i, diagnosisValidity.getActive());
        
        return pStmt;
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
