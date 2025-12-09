package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.MixedAnswerResult;
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

public class MixedAnswerResultDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);    
    private final String table = "`mixedAnswerResults`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MixedAnswerResultDAO()
    {
        fields.add("resultId");
        fields.add("mixedAnswerOptionId");
        fields.add("invalidated");
        fields.add("invalidatedDate");
        fields.add("invalidatedBy");
        fields.add("deleted");
        fields.add("deletedDate");
        fields.add("deletedBy");
        fields.add("displayOrder");
    }
    
    /**
     * Gets the option set by its unique database identifier
     * @param id unique database identifier
     * @return mixed answer options set object
     * @throws SQLException 
     */
    public MixedAnswerResult GetByID(int id) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        MixedAnswerResult result = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `id` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
        {
            result = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return result;
    }

    public ArrayList<MixedAnswerResult> GetByResultId(int resultId, boolean returnInvalidated, boolean returnDeleted)
            throws SQLException, NullPointerException, Exception
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table
                + " WHERE `resultId` = ?"
                + ((!returnInvalidated)? " AND invalidated = 0 " : "")
                + ((!returnDeleted)? " AND deleted = 0" : "");

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, resultId);
        ResultSet rs = pStmt.executeQuery();

        ArrayList<MixedAnswerResult> options = new ArrayList<>();
        while (rs.next())
        {
            MixedAnswerResult result = getFromResultSet(rs);
            if (result != null && result.getId() != null && result.getId() > 0)
            {
                options.add(result);
            }
        }
        rs.close();
        pStmt.close();
        return options;
    }

    
    public Boolean Insert(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerResult result = (MixedAnswerResult)obj;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(result, pStmt);
        return pStmt.execute();
    }
    
    public Integer InsertGetNewId(MixedAnswerResult result)
            throws SQLException, ClassCastException, NullPointerException
    {
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        Integer newId = null;
        pStmt = SetStatementValuesFromObject(result, pStmt);
        pStmt.executeUpdate();
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next()) newId = rs.getInt(1);
        pStmt.close();
        return newId;
    }

    public Boolean Update(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerResult result = (MixedAnswerResult)obj;        
        String query = GenerateUpdateStatement(fields) + " WHERE `id` = " + result.getId();
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(result, pStmt);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    /**
     * Marks all of the mixed answers for the provided resultId as "deleted"
     * @param resultId
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public boolean DeleteAllForResultId(Integer resultId, int userId) throws SQLException, NullPointerException
    {
        if (resultId == null || resultId <= 0) return false;
        String query = "UPDATE " + table + " SET deleted = 1, deletedBy = ?, deletedDate = NOW() WHERE resultId = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, userId);
        pStmt.setInt(2, resultId);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    /**
     * Marks a single mixed answer result line as "deleted"
     * @param id
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public boolean Delete(Integer id, int userId) throws SQLException, NullPointerException
    {
        if (id == null || id <= 0) return false;
        String query = "UPDATE " + table + " SET deleted = 1, deletedBy = ?, deletedDate = NOW() WHERE id = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, userId);
        pStmt.setInt(2, id);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    /**
     * Marks all mixed answer results for this result line as "invalidated"
     * Used when changing results to keep track of exact changes
     * @param resultId
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public boolean InvalidateAllForResultId(Integer resultId, int userId) throws SQLException, NullPointerException
    {
        if (resultId == null || resultId <= 0 ) return false;
        String query = "UPDATE " + table + " SET invalidated = 1, invalidatedBy = ?, invalidatedDate = NOW() WHERE resultId = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, userId);
        pStmt.setInt(2, resultId);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    /**
     * Marks a single mixed answer result as invalidated
     * @param id
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public boolean Invalidate(Integer id, int userId) throws SQLException, NullPointerException
    {
        if (id == null || id <= 0) return false;
        String query = "UPDATE " + table + " SET invalidated = 1, invalidatedBy = ?, invalidatedDate = NOW() WHERE id = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, userId);
        pStmt.setInt(2, id);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    private MixedAnswerResult getFromResultSet(ResultSet rs) throws SQLException
    {
        MixedAnswerResult result = new MixedAnswerResult();
        result.setId(rs.getInt("id"));
        result.setMixedAnswerOptionId(rs.getInt("mixedAnswerOptionId"));
        result.setInvalidated(rs.getBoolean("invalidated"));
        result.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
        result.setInvalidatedBy(rs.getInt("invalidatedBy"));
        result.setDeleted(rs.getBoolean("deleted"));
        result.setDeletedDate(rs.getTimestamp("deletedDate"));
        result.setDeletedBy(rs.getInt("deletedBy"));
        result.setDisplayOrder(rs.getInt("displayOrder"));
        return result;
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
            MixedAnswerResult result,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, result.getResultId());
        SQLUtil.SafeSetInteger(pStmt, ++i, result.getMixedAnswerOptionId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, result.isInvalidated());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getInvalidatedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, result.getInvalidatedBy());
        SQLUtil.SafeSetBoolean(pStmt, ++i, result.isDeleted());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, result.getDeletedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, result.getDeletedBy());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, result.getDisplayOrder());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
