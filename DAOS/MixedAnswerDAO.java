package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.MixedAnswer;
import DOS.MixedAnswerOption;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MixedAnswerDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);    
    private final String table = "`mixedAnswers`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MixedAnswerDAO()
    {
        fields.add("mixedAnswerOptionSetId");
        fields.add("mixedAnswerOptionId");
        fields.add("answerOrder");
    }
    
    /**
     * Gets the option set by its unique database identifier
     * @param id unique database identifier
     * @return mixed answer options set object
     * @throws SQLException 
     */
    public MixedAnswer GetByID(int id) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        MixedAnswer answer = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `id` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            answer = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return answer;
    }
    
    public ArrayList<MixedAnswer> GetByOptionSetId(int optionSetId) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        ArrayList<MixedAnswer> mixedAnswers = new ArrayList<>();
        
        String query = "SELECT * FROM " + table +
                " WHERE `mixedAnswerOptionSetId` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, optionSetId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            MixedAnswer answer = getFromResultSet(rs);
            if (answer != null && answer.getId() != null && answer.getId() > 0)
            {
                mixedAnswers.add(answer);
            }
        }
        rs.close();
        pStmt.close();
        return mixedAnswers;
    }

    public Boolean Insert(MixedAnswer answer)
            throws SQLException, ClassCastException, NullPointerException
    {        
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(answer, pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }
    
    /*
    public Integer InsertGetNewId(MixedAnswerOption option)
            throws SQLException, ClassCastException, NullPointerException
    {
        
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        Integer newId = null;
        pStmt = SetStatementValuesFromObject(option, pStmt);
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next()) newId = rs.getInt(1);
        pStmt.close();
        return newId;
        return null;
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
        
        return false;
    }*/
    
    public boolean Delete(int id) throws SQLException, NullPointerException
    {
        String query = "DELETE FROM ? WHERE id = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, table);
        pStmt.setInt(2, id);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    private MixedAnswer getFromResultSet(ResultSet rs) throws SQLException
    {
        MixedAnswer answer = new MixedAnswer();
        answer.setId(rs.getInt("id"));
        answer.setMixedAnswerOptionSetId(rs.getInt("mixedAnswerOptionSetId"));
        answer.setMixedAnswerOptionId(rs.getInt("mixedAnswerOptionId"));
        answer.setAnswerOrder(rs.getInt("answerOrder"));        
        return answer;
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
            MixedAnswer answer,
            PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, answer.getMixedAnswerOptionSetId());
        SQLUtil.SafeSetInteger(pStmt, ++i, answer.getMixedAnswerOptionId());
        SQLUtil.SafeSetInteger(pStmt, ++i, answer.getAnswerOrder());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}