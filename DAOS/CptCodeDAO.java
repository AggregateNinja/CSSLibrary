
package DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CptCode;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CptCodeDAO implements IDAO<CptCode>, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`cptCodes`";
    private final String identityColumn = "`idCptCodes`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public enum CptSearchType
    {
        ByCptCode,
        ByDescription;
    }
    
    public CptCodeDAO()
    {
        fields.add("code");
        fields.add("description");
        fields.add("createdById");
        fields.add("createdDate");
        fields.add("deactivatedById");
        fields.add("deactivatedDate");
        fields.add("active");
    }

    @Override
    public CptCode Insert(CptCode cptCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (cptCode == null)
        {
            throw new IllegalArgumentException("CPTCodeDAO::Insert: Received a NULL CptCode object");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int index = 1;
        SQLUtil.SafeSetString(pStmt, index, cptCode.getCode());
        SQLUtil.SafeSetString(pStmt, ++index, cptCode.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++index, cptCode.getCreatedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++index, cptCode.getCreatedDate());
        SQLUtil.SafeSetInteger(pStmt, ++index, cptCode.getDeactivatedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++index, cptCode.getDeactivatedDate());
        SQLUtil.SafeSetBoolean(pStmt, ++index, cptCode.isActive());
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String code = "[NULL]";
            if (cptCode.getCode() != null) code = cptCode.getCode();
            String errorMsg = "CptCodeDAO::Insert: Row was not inserted!"
                    + "Code being inserted was:" + code;
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
                String code = "[NULL]";
                if (cptCode.getCode() != null) code = cptCode.getCode();                
                throw new SQLException("CptCodeDAO::Insert: Insert failed!"
                        + "No unique identifier returned. Code being inserted was:" + code);
            }
        }
        
        pStmt.close();
        cptCode.setIdCptCodes(newId);
        return cptCode;
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

    @Override
    public CptCode Update(CptCode cptCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (cptCode == null) {
            throw new IllegalArgumentException("CptCodeDAO::Update: Received a NULL CptCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "UPDATE " + table + " SET "
                + " code = ?,"
                + " description = ?,"
                + " createdById = ?,"
                + " createdDate = ?,"
                + " deactivatedById = ?,"
                + " deactivatedDate = ?,"
                + " active = ?"
                + " WHERE idCptCodes = " + cptCode.getIdCptCodes();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql)) {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, cptCode.getCode());
            SQLUtil.SafeSetString(pStmt, ++i, cptCode.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, cptCode.getCreatedById());
            SQLUtil.SafeSetDate(pStmt, ++i, cptCode.getCreatedDate());
            SQLUtil.SafeSetInteger(pStmt, ++i, cptCode.getDeactivatedById());
            SQLUtil.SafeSetDate(pStmt, ++i, cptCode.getDeactivatedDate());
            SQLUtil.BooleanToInt(pStmt, ++i, cptCode.isActive(), true);
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex) {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        return cptCode;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Marks the cpt code as no longer active.
     * @param cptCode
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public void Delete(CptCode cptCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the cpt code by its unique database identifier
     * @param id
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public CptCode GetById(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0) throw new IllegalArgumentException("CptCodeDAO::GetById: Received NULL identifier");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE " + identityColumn + " = " + id;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        CptCode code = null;
        if (rs.next())
        {
            code = GetObjectFromResultSet(rs);
        }
        pStmt.close();
        return code;
    }
    
    public CptCode GetByCode(String code, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("CptCodeDAO::GetByCode: Received a NULL or empty code argument");
        if (searchType == null) throw new IllegalArgumentException("CptCodeDAO::GetByCode: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE code = ?";
        
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, code);
        ResultSet rs = pStmt.executeQuery();
        CptCode cptCode = null;
        if (rs.next())
        {
            cptCode = GetObjectFromResultSet(rs);
        }
        pStmt.close();
        return cptCode;
    }
    
    public List<CptCode> SearchCodeAndDescriptionByFragment(String fragment, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (fragment == null || fragment.isEmpty()) throw new IllegalArgumentException("CptCodeDAO::SearchCodeAndDescriptionByFragment: Received a NULL or empty code argument");
        if (searchType == null) throw new IllegalArgumentException("CptCodeDAO::SearchCodeAndDescriptionByFragment: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE (code LIKE ? OR description LIKE ?) ";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        fragment = fragment.replace("%", "");
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, "%" + fragment + "%");
        pStmt.setString(2, "%" + fragment + "%");
        ResultSet rs = pStmt.executeQuery();

        List<CptCode> codes = new LinkedList<>();
        while (rs.next())
        {
            codes.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return codes;        
    }
    
    public List<CptCode> SearchByCodeFragment(String code, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("CptCodeDAO::SearchByCodeFragment: Received a NULL or empty code argument");
        if (searchType == null) throw new IllegalArgumentException("CptCodeDAO::SearchByCodeFragment: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE code LIKE ?";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        code = code.replace("%", "");
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, "%" + code + "%");
        ResultSet rs = pStmt.executeQuery();

        List<CptCode> codes = new LinkedList<>();
        while (rs.next())
        {
            codes.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return codes;
    }
    
    public List<CptCode> SearchByFragment(String fragment, CptSearchType cptSearchType, SearchType activeSearchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (fragment == null || fragment.isEmpty()) throw new IllegalArgumentException("CptCodeDAO::SearchByCodeFragment: Received a NULL or empty fragment argument");
        if (cptSearchType == null) throw new IllegalArgumentException("CptCodeDAO::SearchByCodeFragment: Received a NULL CPT search type argument");
        if (activeSearchType == null) throw new IllegalArgumentException("CptCodeDAO::SearchByCodeFragment: Received a NULL active search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE ";
        if (cptSearchType.equals(CptSearchType.ByCptCode))
        {
            sql += " `code` ";
        }
        else if (cptSearchType.equals(CptSearchType.ByDescription))
        {
            sql += " `description` ";
        }
        sql += "LIKE ?";
        
        if (activeSearchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        else if (activeSearchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        fragment = fragment.replace("%", "");
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, "%" + fragment + "%");
        ResultSet rs = pStmt.executeQuery();

        List<CptCode> codes = new LinkedList<>();
        while (rs.next())
        {
            codes.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return codes;
    }
    
    private CptCode GetObjectFromResultSet(ResultSet rs) throws SQLException
    {
        CptCode code = new CptCode();
        
        code.setIdCptCodes(rs.getInt("idCptCodes"));
        code.setCode(rs.getString("code"));
        code.setDescription(rs.getString("description"));
        code.setCreatedById(rs.getInt("createdById"));
        code.setCreatedDate(rs.getTimestamp("createdDate"));
        code.setDeactivatedById(rs.getInt("deactivatedById"));
        code.setDeactivatedDate(rs.getTimestamp("deactivatedDate"));
        code.setActive(rs.getBoolean("active"));
        return code;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}