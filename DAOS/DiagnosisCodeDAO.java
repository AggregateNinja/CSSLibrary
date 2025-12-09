package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DiagnosisCodes;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Jan 15, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: DiagnosisCodeDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class DiagnosisCodeDAO implements IStructureCheckable//implements DAOInterface
{
    public enum DiagnosisSearchType
    {
        ByDiagnosisCode,
        ByLOINCCode,
        ByDescription,
        ByCPTCode;
    }
    
    public enum DiagnosisFilterType
    {
        SEARCHABLE_AND_ACTIVE_ONLY,
        ALL_ACTIVE,
        ALL
    }
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public final static String table = "`diagnosisCodes`";
    private final ArrayList<String> fields = new ArrayList<>();    


    public DiagnosisCodeDAO()
    {
        // Excluding unique database identifier
        fields.add("code");
        fields.add("description");
        fields.add("FullDescription");
        fields.add("validity"); // "I" = Incomplete, "C" = Complete
        fields.add("type"); // The letter category of this code, e.g. (G00.0 would be "G")
        fields.add("dateCreated");
        fields.add("createdBy");
        fields.add("version"); // 9 for ICD9, 10 for ICD10, etc.
        fields.add("active");
        fields.add("searchable");
        fields.add("deactivatedDate");
        fields.add("deactivatedBy");
        fields.add("isHeader");
    }
    
    public static DiagnosisCodes setDiagnosisCodesFromResultset(
            DiagnosisCodes diagnosisCode,
            ResultSet rs) throws SQLException
    {
        diagnosisCode.setIdDiagnosisCodes(rs.getInt("idDiagnosisCodes"));
        diagnosisCode.setCode(rs.getString("code"));
        diagnosisCode.setDescription(rs.getString("description"));
        diagnosisCode.setFullDescription(rs.getString("FullDescription"));
        diagnosisCode.setValidity(rs.getString("validity"));
        diagnosisCode.setType(rs.getString("type"));
        diagnosisCode.setDateCreated(rs.getDate("dateCreated"));
        diagnosisCode.setCreatedBy(rs.getInt("createdBy"));
        diagnosisCode.setVersion(rs.getInt("version"));
        diagnosisCode.setActive(rs.getBoolean("active"));
        diagnosisCode.setSearchable(rs.getBoolean("searchable"));
        diagnosisCode.setDeactivatedDate(rs.getDate("deactivatedDate"));
        diagnosisCode.setDeactivatedBy(rs.getInt("deactivatedBy"));
        diagnosisCode.setIsHeader(rs.getBoolean("isHeader"));
        return diagnosisCode;
    }
    
    private PreparedStatement setStatementFromDiagnosisCode(
            DiagnosisCodes code,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, code.getCode());
        SQLUtil.SafeSetString(pStmt, ++i, code.getDescription());
        SQLUtil.SafeSetString(pStmt, ++i, code.getFullDescription());
        SQLUtil.SafeSetString(pStmt, ++i, code.getValidity());
        SQLUtil.SafeSetString(pStmt, ++i, code.getType());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, code.getDateCreated());
        SQLUtil.SafeSetInteger(pStmt, ++i, code.getCreatedBy());
        SQLUtil.SafeSetInteger(pStmt, ++i, code.getVersion());
        SQLUtil.SafeSetBoolean(pStmt, ++i, code.isActive());
        SQLUtil.SafeSetBoolean(pStmt, ++i, code.isSearchable());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, code.getDeactivatedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, code.getDeactivatedBy());
        SQLUtil.SafeSetBoolean(pStmt, ++i, code.isHeader());
        
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
    
    /*
    public boolean InsertDiagnosisCodeDAO(DiagnosisCodes diagnosis) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql);
        setStatementFromDiagnosisCode(diagnosis, pStmt);
        pStmt.executeUpdate();
        pStmt.close();

        return true;
    
    }

    public boolean UpdateDiagnosisCodeDAO(DiagnosisCodes diagnosis) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        String sql = GenerateUpdateStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql);
        setStatementFromDiagnosisCode(diagnosis, pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }
    */
    
    public DiagnosisCodes GetDiagnosisCode(int Id) throws SQLException
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
            DiagnosisCodes diagnosis = new DiagnosisCodes();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idDiagnosisCodes` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                setDiagnosisCodesFromResultset(diagnosis, rs);
            }

            rs.close();
            stmt.close();

            return diagnosis;
        }
        catch (Exception ex)
        {
            throw new SQLException(ex.getMessage());
        }
    }

    /**
     * Returns the active diagnosis code that matches the supplied string.
     *  Note: Does not filter on "searchable", or "header"
     * @param code
     * @return 
     * @throws java.sql.SQLException
    */
    public DiagnosisCodes GetDiagnosisCode(String code) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        DiagnosisCodes diagnosis = new DiagnosisCodes();
        PreparedStatement stmt = null; //con.createStatement();

        String query = "SELECT * FROM " + table
                + " WHERE `code` = ? AND active = 1";

        stmt = createStatement(con, query, code);
        
        ResultSet rs = stmt.executeQuery();

        if (rs.next())
        {
            setDiagnosisCodesFromResultset(diagnosis, rs);
        }
        rs.close();
        stmt.close();
        return diagnosis;
    }
    
    
    public Boolean CodeExists(String code, DiagnosisFilterType filterType, boolean includeHeaders)
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
            int c = 0;
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT COUNT(*) AS 'countCode' FROM " + table
                    + " WHERE `code` = ?";

            switch (filterType)
            {
                case SEARCHABLE_AND_ACTIVE_ONLY:
                    query += " AND searchable = 1 AND active = 1";
                    break;
                case ALL_ACTIVE:
                    query += " AND active = 1";
                    break;
                default:
                    // Fall through for all
                    break;
            }
            
            if (includeHeaders == false)
            {
                query += " AND isHeader = 0";
            }
            stmt = createStatement(con, query, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                c = rs.getInt("countCode");
            }
            rs.close();
            stmt.close();
            return (c > 0);
        }catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Searches diagnosis codes by code/LOINC/CPT/description. If version is
     *  supplied (not NULL), will restrict results to the ICD version specified.
     * @param searchString
     * @param type
     * @param filterType
     * @param version
     * @param includeHeaders
     * @return 
     */
    public List<DiagnosisCodes> SearchDiagnosisCodes(
            String searchString,
            DiagnosisSearchType type,
            Integer version,
            DiagnosisFilterType filterType,
            boolean includeHeaders) throws SQLException, IllegalArgumentException, NullPointerException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        String outputQuery = "";
        PreparedStatement pStmt = null;
        try
        {
            ArrayList<DiagnosisCodes> diagList = new ArrayList<>();
            
            String query = "SELECT * FROM " + table;
            searchString = searchString + "%";
            switch (type) {
                case ByDiagnosisCode:
                    query += " WHERE `code` LIKE ?";
                    break;
                case ByLOINCCode:
                    query += " WHERE `loinc` LIKE ?";
                    break;
                case ByCPTCode:
                    query += " WHERE `cptCode` LIKE ?";
                    break;
                case ByDescription:
                    searchString = "%" + searchString;
                    query += " WHERE `description` LIKE ?";
                    break;
                default:
                    return diagList;
            }
            if (version != null)
            {
                query += " AND version = ?";
            }
            
            switch (filterType)
            {
                case ALL_ACTIVE:
                    query += " AND active = 1";
                    break;
                case SEARCHABLE_AND_ACTIVE_ONLY:
                    query += " AND active = 1 AND searchable = 1";
                default: // Fall through if ALL
                    break;
            }
            
            if (includeHeaders == false)
            {
                query += " AND isHeader = 0";
            }
            
            query += " ORDER BY `code` ASC";
            
            pStmt = con.prepareStatement(query);
            pStmt.setString(1, searchString);
            if (version != null) pStmt.setInt(2, version);
            outputQuery = pStmt.toString();
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                DiagnosisCodes diagnosis = new DiagnosisCodes();
                setDiagnosisCodesFromResultset(diagnosis, rs);
                diagList.add(diagnosis);
            }
            
            rs.close();
            pStmt.close();
            return diagList;
            
        }
        catch (SQLException ex)
        {
            throw new SQLException(ex.getMessage() + " Query: " + outputQuery);
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    public DiagnosisCodes insert(DiagnosisCodes newCode)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (newCode == null) throw new IllegalArgumentException(
                "DiagnosisCodeDAO::insert: Received NULL DiagnosisCodes object for insert");        
        
        String sql = "";
        PreparedStatement pStmt = null;
        try
        {
            sql = GenerateInsertStatement(fields);
            pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt = setStatementFromDiagnosisCode(newCode, pStmt);
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected == 0) throw new SQLException("Insert could not be performed (zero rows affected)");
            
            Integer newId;
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    newId = generatedKeys.getInt(1);
                    newCode.setIdDiagnosisCodes(newId);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
        }
        catch (SQLException ex)
        {
            if (sql == null) sql = "[NULL]";
            throw new SQLException(ex.getMessage() + ". SQL run: " + sql);
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        
        return newCode;
    }
    
    public void update(DiagnosisCodes code)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (code == null) throw new IllegalArgumentException(
                "DiagnosisCodeDAO::update: Received NULL DiagnosisCodes object for update");
        
        String sql = "";
        try
        {
            sql = GenerateUpdateStatement(fields);
            sql += " WHERE idDiagnosisCodes = " + code.getIdDiagnosisCodes();
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt = setStatementFromDiagnosisCode(code, pStmt);
            pStmt.execute();            
        }
        catch (SQLException ex)
        {
            if (sql == null) sql = "[NULL]";
            throw new SQLException(ex.getMessage() + ". SQL run:" + sql);
        }
    }
    
    /*
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertDiagnosisCodeDAO((DiagnosisCodes)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DiagnosisCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateDiagnosisCodeDAO((DiagnosisCodes)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DiagnosisCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            return GetDiagnosisCode(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DiagnosisCodeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    */
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
