package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.MixedAnswerOption;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MixedAnswerOptionDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);    
    private final String table = "`mixedAnswerOptions`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MixedAnswerOptionDAO()
    {
        fields.add("name");
        fields.add("display");
        fields.add("description");
        fields.add("isAbnormal");
        fields.add("active");
    }
    
    /**
     * Gets the option set by its unique database identifier
     * @param id unique database identifier
     * @return mixed answer options set object
     * @throws SQLException 
     */
    public MixedAnswerOption GetByID(int id) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        MixedAnswerOption option = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `id` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, id);        
        ResultSet rs = pStmt.executeQuery();

        if (rs.next())
        {
            option = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return option;
    }
    
    /**
     * Searches the option name, display, and description for the
     *  provided text. Caller can specify whether to include inactive
     *  options.
     * 
     * @param text
     * @param returnInactive
     * @return Empty array if none found
     * @throws java.sql.SQLException 
     */
    public ArrayList<MixedAnswerOption> Search(String text, boolean returnInactive)
            throws SQLException, NullPointerException, Exception
    {
        ArrayList<MixedAnswerOption> options = new ArrayList<>();
        if (text == null || text.isEmpty()) return options;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table + " WHERE "
                + ((!returnInactive)? " active = 1 AND " : "")
                + " (name LIKE ? OR "
                + " display LIKE ? OR "
                + " description LIKE ?)";
        
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, text + "%");
        pStmt.setString(2, "%" + text + "%");
        pStmt.setString(3, "%" + text + "%");
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            MixedAnswerOption mao = getFromResultSet(rs);
            if (mao != null && mao.getId() != null && mao.getId() > 0)
            {
                options.add(mao);
            }
        }
        rs.close();
        pStmt.close();
        return options;
    }
    
    public MixedAnswerOption GetActiveByName(String name)
            throws SQLException, NullPointerException, Exception
    {
        MixedAnswerOption option = null;
        if (name == null || name.isEmpty()) return option;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table + " WHERE active = 1 AND name = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        MixedAnswerOption mao = null;
        if (rs.next())
        {
            mao = getFromResultSet(rs);
            
        }        
        rs.close();
        pStmt.close();
        if (mao == null || mao.getId() == null || mao.getId() < 1) mao = null;
        return mao;        
    }
    
    /**
     * Returns all options (with or without inactive), sorted by name.
     * 
     * @param includeInactive
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws Exception 
     */
    public ArrayList<MixedAnswerOption> GetAll(boolean includeInactive)
            throws SQLException, NullPointerException, Exception
    {
        ArrayList<MixedAnswerOption> options = new ArrayList<>();
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table
                + ((!includeInactive)? " WHERE active = 1":"")
                + " ORDER BY name";
        PreparedStatement pStmt = con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            MixedAnswerOption option = getFromResultSet(rs);
            if (option != null && option.getId() != null && option.getId() > 0)
            {
                options.add(option);
            }
        }
        return options;
    }
    
    /**
     * Returns true if the mixed answer option is being used by any existing
     *  order (ignoring invalidated or deleted results).
     *  
     *  Used when modifying an option text or abnormal flag to warn the
     *  user that the changes will immediately apply to existing orders.
     * 
     * @param mixedAnswerOptionId
     * @return 
     * @throws java.sql.SQLException 
     */
    public Boolean IsBeingUsed(int mixedAnswerOptionId) throws SQLException, NullPointerException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT COUNT(distinct(orderId)) "
            + " FROM orders o "
            + " INNER JOIN results r ON o.idorders = r.orderid "
            + " INNER JOIN mixedAnswerResults mar ON r.idresults = mar.resultId "
            + " WHERE "
            + " (r.resultText IS NULL OR (r.resultText IS NOT NULL AND UPPER(r.resultText) != 'DELETED')) "
            + " AND r.isInvalidated = 0 "
            + " AND mar.mixedAnswerOptionId = ?";
        
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, mixedAnswerOptionId);
        ResultSet rs = pStmt.executeQuery();
        int count = 0;
        if (rs.next())
        {
            count = rs.getInt(1);
        }
        return count > 0;
    }
    
    public Boolean Insert(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerOption option = (MixedAnswerOption)obj;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(option, pStmt);
        return pStmt.execute();
    }
    
    public Integer InsertGetNewId(MixedAnswerOption option)
            throws SQLException, ClassCastException, NullPointerException
    {
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        Integer newId = null;
        pStmt = SetStatementValuesFromObject(option, pStmt);
        pStmt.executeUpdate();
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next()) newId = rs.getInt(1);
        pStmt.close();
        return newId;
    }

    public Boolean Update(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerOption option = (MixedAnswerOption)obj;        
        String query = GenerateUpdateStatement(fields) + " WHERE `id` = " + option.getId();
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(option, pStmt);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    public boolean Deactivate(int id) throws SQLException, NullPointerException
    {
        String query = "UPDATE ? SET active = 0 WHERE id = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, table);
        pStmt.setInt(2, id);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }

    private MixedAnswerOption getFromResultSet(ResultSet rs) throws SQLException
    {
        MixedAnswerOption option = new MixedAnswerOption();
        option.setId(rs.getInt("id"));
        option.setName(rs.getString("name"));
        option.setDisplay(rs.getString("display"));
        option.setDescription(rs.getString("description"));
        option.setIsAbnormal(rs.getBoolean("isAbnormal"));
        option.setActive(rs.getBoolean("active"));
        return option;
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

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }    
    
    private PreparedStatement SetStatementValuesFromObject(
            MixedAnswerOption optionSet,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;        
        SQLUtil.SafeSetString(pStmt, ++i, optionSet.getName());
        SQLUtil.SafeSetString(pStmt, ++i, optionSet.getDisplay());
        SQLUtil.SafeSetString(pStmt, ++i, optionSet.getDescription());        
        SQLUtil.SafeSetBoolean(pStmt, ++i, optionSet.isAbnormal());
        SQLUtil.SafeSetBoolean(pStmt, ++i, optionSet.isActive());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}