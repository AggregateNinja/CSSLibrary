/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOETests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author TomR
 */
public class AOETestsDAO implements IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeTests`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public AOETestsDAO()
    {
        // Excluding unique database identifier
        fields.add("testNumber");
        fields.add("questionId");
        fields.add("questionOrder");  
    }
    
    public ArrayList<AOETests> GetAOETestsByTestNumber(int testNumber)
    {        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        try
        {
            ArrayList<AOETests> results = new ArrayList<>();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `testNumber` = " + testNumber
                    + " ORDER BY questionOrder";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                results.add(setAOETestsFromResultset(new AOETests(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            return null;
        }
    }
    
    public boolean InsertAOETests(AOETests tests)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            //fields.add("id");
            //fields.add("idtests");
            //fields.add("questionId");
            //fields.add("questionOrder");
            
            SQLUtil.SafeSetInteger(pStmt, 1, tests.getQuestionId());
            SQLUtil.SafeSetInteger(pStmt, 2, tests.getTestNumber());
            SQLUtil.SafeSetInteger(pStmt, 3, tests.getQuestionId());
            SQLUtil.SafeSetInteger(pStmt, 4, tests.getQuestionOrder());
            
            pStmt.executeUpdate();
            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }        
    }
    
    public boolean UpdateAOETests(AOETests tests)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        String stmt = GenerateUpdateStatement(fields) +
        " WHERE `id` = " + tests.getId();
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            SQLUtil.SafeSetInteger(pStmt, 1, tests.getQuestionId());
            SQLUtil.SafeSetInteger(pStmt, 2, tests.getTestNumber());
            SQLUtil.SafeSetInteger(pStmt, 3, tests.getQuestionId());
            SQLUtil.SafeSetInteger(pStmt, 4, tests.getQuestionOrder());
            pStmt.executeUpdate();
            pStmt.close();

            return true;            
        }
        catch (SQLException ex)
        {
            String message = ex.toString();
            System.out.println(message);
            System.out.println(stmt.toString());
            return false;
        }

    }
    
    private AOETests setAOETestsFromResultset(AOETests tests,
            ResultSet rs) throws SQLException
    {
        tests.setId(rs.getInt("id"));
        tests.setTestNumber(rs.getInt("testNumber"));
        tests.setQuestionId(rs.getInt("questionId"));
        tests.setQuestionOrder(rs.getInt("questionOrder"));
        return tests;        
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
