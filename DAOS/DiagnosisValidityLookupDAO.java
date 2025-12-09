package DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.DiagnosisValidityLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DiagnosisValidityLookupDAO implements IDAO<DiagnosisValidityLookup>, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`diagnosisValidityLookup`";
        
    private final ArrayList<String> fields = new ArrayList<>();
    
    public DiagnosisValidityLookupDAO()
    {
        fields.add("diagnosisValidityId");
        fields.add("cptCodeId");
        fields.add("diagnosisCodeId");
        fields.add("validityStatusId");
        fields.add("startDate");
        fields.add("endDate");
        fields.add("active");
    }
    
    /**
     * Inserts the diagnosis validity row and returns a copy containing the
     *  newly-generated database identifier.
     * 
     * @param diagnosisValidityLookup
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public DiagnosisValidityLookup Insert(DiagnosisValidityLookup diagnosisValidityLookup)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidityLookup == null)
            throw new IllegalArgumentException(
                "DiagnosisValidityLookupDAO::Insert: "
                + "Supplied diagnosis validity lookup object was [NULL]");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        pStmt = SetInsertStatementFromDiagnosisValidityLookup(diagnosisValidityLookup, pStmt);
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String errorMsg = "DiagnosisValidityLookupDAO::Insert: Row was not inserted! CPTCodeId "
                    + diagnosisValidityLookup.getCptCodeId() + " DiagnosisCodeId " + diagnosisValidityLookup.getDiagnosisCodeId()
                    + " diagnosisValidityId " + diagnosisValidityLookup.getDiagnosisValidityId();
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
        diagnosisValidityLookup.setIdDiagnosisValidityLookup(newId);
        return diagnosisValidityLookup;
    }

    /**
     * Updates the supplied DiagnosisValidity object.
     * @param diagnosisValidityLookup
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public DiagnosisValidityLookup Update(DiagnosisValidityLookup diagnosisValidityLookup)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidityLookup == null || diagnosisValidityLookup.getIdDiagnosisValidityLookup() == null || diagnosisValidityLookup.getIdDiagnosisValidityLookup().equals(0))
        {
            throw new IllegalArgumentException(
                    "DiagnosisValidityLookupDAO::Update: "
                + "Supplied diagnosis validity lookup object was [NULL]");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateUpdateStatement(fields);
        sql += " WHERE idDiagnosisValidityLookup = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetUpdateStatementFromDiagnosisValidityLookup(diagnosisValidityLookup, pStmt);
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String errorMsg = "DiagnosisValidityLookupDAO::Update: "
                    + "Row could not be updated! " + diagnosisValidityLookup.getIdDiagnosisValidityLookup().toString();
            throw new SQLException(errorMsg);
        }
        pStmt.close();
        return diagnosisValidityLookup;
    }

    /**
     * Marks a diagnosis validity row as not active.
     *  Note: calling code must perform logging
     * @param diagnosisValidityLookup
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public void Delete(DiagnosisValidityLookup diagnosisValidityLookup)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (diagnosisValidityLookup == null || diagnosisValidityLookup.getIdDiagnosisValidityLookup()== null || diagnosisValidityLookup.getIdDiagnosisValidityLookup().equals(0))
        {
            throw new IllegalArgumentException(
                    "DiagnosisValidityLookupDAO::Delete: "
                + "Supplied diagnosis validity object was [NULL]");
        }
        diagnosisValidityLookup.setActive(false);
        Update(diagnosisValidityLookup);
    }

    /**
     * Retrieves a DiagnosisValidityLookup object using its unique database identifier
     * @param id
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public DiagnosisValidityLookup GetById(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id <= 0) throw new IllegalArgumentException("DiagnosisValidityLookupDAO::GetById: Parameter id was: " + id);
        
        String sql = "SELECT * FROM " + table + " WHERE idDiagnosisValidityLookup = " + id;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        DiagnosisValidityLookup diagnosisValidityLookup = null;
        if (rs.next())
        {
            diagnosisValidityLookup = GetDiagnosisValidityLookupFromResultSet(rs);
        }
        pStmt.close();
        return diagnosisValidityLookup;        
    }
    
    public List<DiagnosisValidityLookup> GetByCptCodeId(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id <= 0) throw new IllegalArgumentException("DiagnosisValidityLookupDAO::GetByCptCodeId: Parameter id was: " + id);
        
        String sql = "SELECT * FROM " + table + " WHERE cptCodeId = " + id;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<DiagnosisValidityLookup> dvlList = new ArrayList<DiagnosisValidityLookup>();
        DiagnosisValidityLookup diagnosisValidityLookup = null;
        while (rs.next())
        {
            diagnosisValidityLookup = GetDiagnosisValidityLookupFromResultSet(rs);
            dvlList.add(diagnosisValidityLookup);
        }
        pStmt.close();
        return dvlList;        
    }

    /**
     * Gets the current, active diagnosis validity setting for the arguments
     *  provided.
     *  
     * @param diagnosisValidity
     * @param diagnosisCodeId
     * @param cptCodeId
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public List<DiagnosisValidityLookup> GetCurrentDiagnosisValidity(int diagnosisValidity, int cptCodeId, int diagnosisCodeId)
            throws IllegalArgumentException, SQLException
    {
        if (diagnosisValidity <= 0) throw new IllegalArgumentException("DiagnosisValidityLookupDAO::GetCurrentDiagnosisValidity: Parameter diagnosisValidity was: " + diagnosisValidity);
        if (diagnosisCodeId <=0) throw new IllegalArgumentException("DiagnosisValidityLookupDAO::GetCurrentDiagnosisValidity: Parameter diagnosisCodeId was: " + diagnosisCodeId);
        if (cptCodeId <=0) throw new IllegalArgumentException("DiagnosisValidityLookupDAO::GetCurrentDiagnosisValidity: Parameter cptCodeId was: " + cptCodeId);
        
        List<DiagnosisValidityLookup> diagnosisValidityLookup = new LinkedList<>();
        
        String sql = "SELECT * FROM " + table + " WHERE `diagnosisValidityId` = ? AND `diagnosisCodeId` = ? AND `cptCodeId` = ? AND `active` = 1 AND F_DATE_RANGES_OVERLAP(NOW(), NOW(), `startDate`, `endDate`) = 1";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int index = 1;
        pStmt.setInt(index++, diagnosisValidity);
        pStmt.setInt(index++, diagnosisCodeId);
        pStmt.setInt(index++, cptCodeId);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            diagnosisValidityLookup.add(GetDiagnosisValidityLookupFromResultSet(rs));
        }
        pStmt.close();
        return diagnosisValidityLookup;
    }
    
    public DiagnosisValidityLookup GetDiagnosisValidityLookupFromResultSet(ResultSet rs)
            throws SQLException
    {
        DiagnosisValidityLookup diagnosisValidityLookup = new DiagnosisValidityLookup();
        diagnosisValidityLookup.setIdDiagnosisValidityLookup(rs.getInt("idDiagnosisValidityLookup"));
        diagnosisValidityLookup.setDiagnosisValidityId(rs.getInt("diagnosisValidityId"));
        diagnosisValidityLookup.setCptCodeId(rs.getInt("cptCodeId"));
        diagnosisValidityLookup.setDiagnosisCodeId(rs.getInt("diagnosisCodeId"));
        diagnosisValidityLookup.setValidityStatusId(rs.getInt("validityStatusId"));
        diagnosisValidityLookup.setStartDate(rs.getDate("startDate"));
        diagnosisValidityLookup.setEndDate(rs.getDate("endDate"));
        diagnosisValidityLookup.setActive(rs.getBoolean("active"));
        return diagnosisValidityLookup;
    }
    
    private PreparedStatement SetInsertStatementFromDiagnosisValidityLookup(DiagnosisValidityLookup diagnosisValidityLookup, PreparedStatement pStmt)
            throws SQLException
    {
        int i=0;
        pStmt.setInt(++i, diagnosisValidityLookup.getDiagnosisValidityId());
        pStmt.setInt(++i, diagnosisValidityLookup.getCptCodeId());
        pStmt.setInt(++i, diagnosisValidityLookup.getDiagnosisCodeId());
        pStmt.setInt(++i, diagnosisValidityLookup.getValidityStatusId());
        pStmt.setDate(++i, Convert.ToSQLDate(diagnosisValidityLookup.getStartDate()));
        pStmt.setDate(++i, Convert.ToSQLDate(diagnosisValidityLookup.getEndDate()));
        pStmt.setBoolean(++i, diagnosisValidityLookup.getActive());
        
        return pStmt;
    }
    
    private PreparedStatement SetUpdateStatementFromDiagnosisValidityLookup(DiagnosisValidityLookup diagnosisValidityLookup, PreparedStatement pStmt)
            throws SQLException
    {
        int i=0;
        pStmt.setInt(++i, diagnosisValidityLookup.getDiagnosisValidityId());
        pStmt.setInt(++i, diagnosisValidityLookup.getCptCodeId());
        pStmt.setInt(++i, diagnosisValidityLookup.getDiagnosisCodeId());
        pStmt.setInt(++i, diagnosisValidityLookup.getValidityStatusId());
        pStmt.setDate(++i, Convert.ToSQLDate(diagnosisValidityLookup.getStartDate()));
        pStmt.setDate(++i, Convert.ToSQLDate(diagnosisValidityLookup.getEndDate()));
        pStmt.setBoolean(++i, diagnosisValidityLookup.getActive());
        pStmt.setInt(++i, diagnosisValidityLookup.getIdDiagnosisValidityLookup());
        
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