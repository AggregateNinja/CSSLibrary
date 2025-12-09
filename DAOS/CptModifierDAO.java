
package DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CptModifier;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CptModifierDAO implements IDAO<CptModifier>, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`cptModifiers`";
    private final String identityColumn = "`idCptModifiers`";
        
    private final ArrayList<String> fields = new ArrayList<>();
    
    public CptModifierDAO()
    {
        fields.add("modifier");
        fields.add("description");
        fields.add("createdById");
        fields.add("createdDate");
        fields.add("deactivatedById");
        fields.add("deactivatedDate");
        fields.add("active");
    }

    @Override
    public CptModifier Insert(CptModifier modifier) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (modifier == null)
        {
            throw new IllegalArgumentException("CPTModifierDAO::Insert: Received a NULL CptModifier object");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int result = pStmt.executeUpdate();
        if (result < 0)
        {
            String code = "[NULL]";
            if (modifier.getModifier() != null) code = modifier.getModifier();
            String errorMsg = "CptModifierDAO::Insert: Row was not inserted!"
                    + "Modifier being inserted was:" + code;
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
                if (modifier.getModifier() != null) code = modifier.getModifier();                
                throw new SQLException("CptModifierDAO::Insert: Insert failed!"
                        + "No unique identifier returned. Modifier being inserted was:" + code);
            }
        }
        
        pStmt.close();
        modifier.setIdCptModifiers(newId);
        return modifier;
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
    @Deprecated
    public CptModifier Update(CptModifier cptCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @Deprecated
    public void Delete(CptModifier cptCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Returns the modifier by its unique database identifier
     * @param id
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    @Override
    public CptModifier GetById(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0) throw new IllegalArgumentException("CptModifierDAO::GetById: Received NULL identifier");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE " + identityColumn + " = " + id;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        CptModifier modifier = null;
        if (rs.next())
        {
            modifier = GetObjectFromResultSet(rs);
        }
        pStmt.close();
        return modifier;
    }
    
    public CptModifier GetByModifier(String code, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (code == null || code.isEmpty()) throw new IllegalArgumentException("CptModifierDAO::GetByModifier: Received a NULL or empty modifier argument");
        if (searchType == null) throw new IllegalArgumentException("CptModifierDAO::GetByModifier: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE modifier = ?";
        
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
        CptModifier modifier = null;
        if (rs.next())
        {
            modifier = GetObjectFromResultSet(rs);
        }
        pStmt.close();
        return modifier;
    }
    
    /**
     * Searches modifiers for the given search type (active/inactive/all)
     * @param searchType
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public List<CptModifier> GetModifiers(SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("CptModifierDAO::GetModifiers: Received a NULL searchType argument");
        
        String sql = "SELECT * FROM " + table;
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " WHERE active = 1";
        }
        
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " WHERE active = 0";
        }        
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        List<CptModifier> modifiers = new LinkedList<>();
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            modifiers.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return modifiers;
    }
    
    /**
     * Fuzzy search for a modifier based on a fragment (does not search description)
     *  Will return any modifier with that fragment present in any location.
     *  If modifier argument is not supplied, throws exception.
     * 
     * If no modifiers found, returns empty list.
     * 
     * @param modifier
     * @param searchType
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public List<CptModifier> SearchByModifierFragment(String modifier, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (modifier == null || modifier.isEmpty()) throw new IllegalArgumentException("CptModifierDAO::SearchByModifierFragment: Received a NULL or empty modifier argument");
        if (searchType == null) throw new IllegalArgumentException("CptModifierDAO::SearchByModifierFragment: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE modifier LIKE ?";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        modifier = modifier.replace("%", "");
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, "%" + modifier + "%");
        ResultSet rs = pStmt.executeQuery();

        List<CptModifier> modifiers = new LinkedList<>();
        while (rs.next())
        {
            modifiers.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return modifiers;
    }
    
    /**
     * Fuzzy search for a modifier based on modifier and/or description fragments
     *  If both modifier and description arguments are not supplied, throws exception.
     * 
     * If no modifiers found, returns empty list.
     * 
     * @param modifier
     * @param description
     * @param searchType
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public List<CptModifier> SearchByModifierDescriptionFragment(String modifier, String description, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        boolean useModifier = modifier != null && !modifier.isEmpty();
        boolean useDescription = description != null && !description.isEmpty();
        if (!useModifier && !useDescription) throw new IllegalArgumentException("CptModifierDAO::SearchByModifierDescriptionFragment: Received NULL or empty modifier and description arguments");
        if (searchType == null) throw new IllegalArgumentException("CptModifierDAO::SearchByModifierDescriptionFragment: Received a NULL search type argument");
        
        String sql = "SELECT * FROM " + table + " WHERE ";
        if (useModifier)
        {
            sql += "modifier LIKE ? ";
        }
        if (useDescription)
        {
            if (useModifier)
            {
                sql += "AND ";
            }
            sql += "description LIKE ?";
        }
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        modifier = modifier.replace("%", "");
        description = description.replace("%", "");
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 1;
        if (useModifier)
        {
            pStmt.setString(i++, "%" + modifier + "%");
        }
        if (useDescription)
        {
            pStmt.setString(i++, "%" + description + "%");
        }
        ResultSet rs = pStmt.executeQuery();

        List<CptModifier> modifiers = new LinkedList<>();
        while (rs.next())
        {
            modifiers.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return modifiers;
    }
    
    public static CptModifier GetObjectFromResultSet(ResultSet rs) throws SQLException
    {
        CptModifier code = new CptModifier();
        code.setIdCptModifiers(rs.getInt("idCptModifiers"));
        code.setModifier(rs.getString("modifier"));
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
