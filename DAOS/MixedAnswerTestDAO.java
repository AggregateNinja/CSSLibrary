
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.MixedAnswerTest;
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


public class MixedAnswerTestDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`mixedAnswerTests`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public MixedAnswerTestDAO()
    {
        fields.add("testId");
        fields.add("mixedAnswerOptionId");
        fields.add("active");
    }
    
    /**
     * Gets the option set by its unique database identifier
     * @param id unique database identifier
     * @return mixed answer options set object
     * @throws SQLException 
     */
    public MixedAnswerTest GetByID(int id) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        MixedAnswerTest test = null;
        
        String query = "SELECT * FROM " + table +
                " WHERE `id` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next())
        {
            test = getFromResultSet(rs);
        }
        rs.close();
        stmt.close();
        return test;
    }

    /**
     * Gets the mixed answer options association by a test id
     * @param testId
     * @param includeInactive
     * @return mixed answer options set object
     * @throws SQLException 
     */    
    public ArrayList<MixedAnswerTest> GetByTestId(int testId, boolean includeInactive) throws SQLException
    {
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        
        String query = "SELECT * FROM " + table
                + " WHERE `testId` = ?"
                + ((!includeInactive)? " AND active = 1 " : "");

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setInt(1, testId);
        ResultSet rs = pStmt.executeQuery();

        ArrayList<MixedAnswerTest> tests = new ArrayList<>();
        while (rs.next())
        {
            MixedAnswerTest test = getFromResultSet(rs);
            if (test != null && test.getId() != null && test.getId() > 0)
            {
                tests.add(test);
            }
        }
        rs.close();
        pStmt.close();
        return tests;
    }
    
    public Boolean Insert(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerTest test = (MixedAnswerTest)obj;
        if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(test, pStmt);
        return pStmt.execute();
    }
    
    public Integer InsertGetNewId(MixedAnswerTest test)
            throws SQLException, ClassCastException, NullPointerException
    {
        String stmt = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(stmt,
                Statement.RETURN_GENERATED_KEYS);
        Integer newId = null;
        pStmt = SetStatementValuesFromObject(test, pStmt);
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next()) newId = rs.getInt(1);
        pStmt.close();
        return newId;
    }

    public Boolean Update(Serializable obj)
            throws SQLException, ClassCastException, NullPointerException
    {
        MixedAnswerTest test = (MixedAnswerTest)obj;        
        String query = GenerateUpdateStatement(fields) + " WHERE `id` = " + test.getId();
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(test, pStmt);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    public boolean Delete(int id) throws SQLException, NullPointerException
    {
        String query = "DELETE FROM ? WHERE id = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, table);
        pStmt.setInt(2, id);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }
    
    private MixedAnswerTest getFromResultSet(ResultSet rs) throws SQLException
    {
        MixedAnswerTest test = new MixedAnswerTest();
        test.setId(rs.getInt("id"));
        test.setTestId(rs.getInt("testId"));
        test.setMixedAnswerOptionId(rs.getInt("mixedAnswerOptionId"));
        test.setActive(rs.getBoolean("active"));
        return test;
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
            MixedAnswerTest test,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, test.getTestId());
        SQLUtil.SafeSetInteger(pStmt, ++i, test.getMixedAnswerOptionId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, test.isActive());
        return pStmt;
    }     
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
