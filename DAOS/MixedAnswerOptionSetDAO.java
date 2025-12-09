package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.MixedAnswerOptionSet;
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

public class MixedAnswerOptionSetDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);    
    private final String table = "`mixedAnswerOptionSets`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MixedAnswerOptionSetDAO()
    {
        fields.add("name");
        fields.add("description");
        fields.add("active");
    }
    
    /**
     * Gets the option set by its unique database identifier
     * @param id unique database identifier
     * @return mixed answer options set object
     * @throws SQLException 
     */
    public MixedAnswerOptionSet GetByID(int id) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        MixedAnswerOptionSet optionSet = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `id` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next())
        {
            optionSet = getFromResultSet(rs);
        }
        rs.close();
        stmt.close();
        return optionSet;
    }
    
    public MixedAnswerOptionSet GetByName(String name) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        MixedAnswerOptionSet optionSet = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `name` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            optionSet = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return optionSet;        
    }
    
    public ArrayList<MixedAnswerOptionSet> GetAllActive() throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        ArrayList<MixedAnswerOptionSet> optionSets = new ArrayList<>();
        
        String query = "SELECT * FROM " + table + " WHERE active = 1";
        PreparedStatement pStmt = con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            MixedAnswerOptionSet optionSet = getFromResultSet(rs);
            if (optionSet != null && optionSet.getId() != null && optionSet.getId() > 0)
            {
                optionSets.add(optionSet);
            }
        }
        rs.close();
        pStmt.close();
        return optionSets;
    }

    public Boolean Insert(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerOptionSet optionSet = (MixedAnswerOptionSet)obj;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(optionSet, pStmt);
        return pStmt.execute();
    }
    
    public Integer InsertGetNewId(MixedAnswerOptionSet optionSet)
            throws SQLException, ClassCastException, NullPointerException
    {
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        Integer newId = null;
        pStmt = SetStatementValuesFromObject(optionSet, pStmt);
        pStmt.executeUpdate();
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next()) newId = rs.getInt(1);
        pStmt.close();
        return newId;
    }

    public Boolean Update(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerOptionSet optionSet = (MixedAnswerOptionSet)obj;        
        String query = GenerateUpdateStatement(fields) + " WHERE `id` = " + optionSet.getId();
        PreparedStatement pStmt = con.prepareStatement(query);        
        pStmt = SetStatementValuesFromObject(optionSet, pStmt);
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

    private MixedAnswerOptionSet getFromResultSet(ResultSet rs) throws SQLException
    {
        MixedAnswerOptionSet optionSet = new MixedAnswerOptionSet();
        optionSet.setId(rs.getInt("id"));
        optionSet.setName(rs.getString("name"));
        optionSet.setDescription(rs.getString("description"));
        optionSet.setActive(rs.getBoolean("active"));
        return optionSet;
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
            MixedAnswerOptionSet optionSet,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, optionSet.getName());
        SQLUtil.SafeSetString(pStmt, ++i, optionSet.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, ++i, optionSet.isActive());
        return pStmt;
    }  
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}