/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOEAnswers;
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
public class AOEAnswersDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeAnswers`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<>();

    public AOEAnswersDAO()
    {
        // Excluding unique database identifier
        fields.add("idorders");
        fields.add("questionId");
        fields.add("answerText");
        fields.add("answerNumber");
        fields.add("answerBool");
        fields.add("answerDate");
        fields.add("aoeChoiceId");
        fields.add("refLabAnswer");
        fields.add("created");
        fields.add("idusers");
    }
    
    /**
     * Provided an order ID, returns all answers for the given tests.
     * @param idOrders
     * @return 
     */
    public ArrayList<AOEAnswers> GetAnswersByOrderID(int idOrders)
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
            ArrayList<AOEAnswers> results = new ArrayList<>();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `idorders` = " + idOrders;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                 results.add(setAOEAnswersFromResultset(new AOEAnswers(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("AOEAnswersDAO::GetAnswersByOrderID: Error getting " +
                "for idOrders " + idOrders);            
            return null;
        }        
    }
    
    public AOEAnswers GetAOEAnswersByQuestionIDOrderID(
        int idQuestions,
        int idOrders)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        AOEAnswers answer = null;
        String query = "SELECT * FROM " + table +
                " WHERE `questionId` = " + idQuestions +
                " AND `idorders` = " + idOrders;
        
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                answer = setAOEAnswersFromResultset(new AOEAnswers(), rs);
            }
            
        }
        catch (SQLException ex)
        {
            System.out.println("AOEAnswersDAO::GetAOEAnswersByQuestionIDOrderID: " +
                    " Cannot get answer object for orderID " + idOrders +
                    " and questionId " + idQuestions);
            return null;
        }
        
        return answer;
    }
    /*
    public ArrayList<AOEAnswers> GetAOEAnswersByQuestionID(int idQuestions)
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
            ArrayList<AOEAnswers> results = new ArrayList<>();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `questionId` = " + idQuestions;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                results.add(setAOEAnswersFromResultset(new AOEAnswers(), rs));        
            }
            
            rs.close();
            stmt.close();
            
            return results;
        }
        catch (SQLException ex)
        {
            System.out.println("AOEAnswersDAO::GetAOEAnswersByQuestionID: Error getting " +
                "for questionid " + idQuestions);
            return null;
        }
    }*/
    
    /**
     * Get by the aoeAnswerID (called id in the database)
     *  Null on error
     * @param id
     * @return 
     */
    public AOEAnswers GetById(int id)
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
            AOEAnswers result = new AOEAnswers();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + id;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                result = setAOEAnswersFromResultset(result, rs);        
            }
            
            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            System.out.println("AOEAnswersDAO::GetById: Error getting " +
                    "for id " + id);
            return null;
        }        
        
    }
    
    public boolean InsertAOEAnswers(AOEAnswers tests)
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

            SQLUtil.SafeSetInteger(pStmt, 1, tests.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, 2, tests.getQuestionId());
            SQLUtil.SafeSetString(pStmt, 3, tests.getAnswerText());
            
            SQLUtil.SafeSetRangeDouble(pStmt, 4, tests.getAnswerNumber());
            SQLUtil.SafeSetBoolean(pStmt, 5, tests.getAnswerBool());
            SQLUtil.SafeSetDate(pStmt, 6, tests.getAnswerDate());
            SQLUtil.SafeSetRangeInteger(pStmt, 7, tests.getAoeChoiceId());
            // We want to allow a reference lab value of '' for questions that
            // have a minimumValueLength of 0            
            if (tests.getRefLabAnswer() == null) {
                throw new SQLException("Null reference lab value being set");
                //pStmt.setNull(8, java.sql.Types.VARCHAR);
            } else {
                pStmt.setString(8, tests.getRefLabAnswer());
            }            
            SQLUtil.SafeSetDate(pStmt, 9, tests.getCreated());
            SQLUtil.SafeSetInteger(pStmt, 10, tests.getIdusers() );
            
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
    
    public boolean UpdateAOEAnswers(AOEAnswers tests)
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
            SQLUtil.SafeSetInteger(pStmt, 1, tests.getIdOrders());
            SQLUtil.SafeSetInteger(pStmt, 2, tests.getQuestionId());
            SQLUtil.SafeSetString(pStmt, 3, tests.getAnswerText());
            SQLUtil.SafeSetRangeDouble(pStmt, 4, tests.getAnswerNumber());
            SQLUtil.SafeSetBoolean(pStmt, 5, tests.getAnswerBool());
            SQLUtil.SafeSetDate(pStmt, 6, tests.getAnswerDate());
            SQLUtil.SafeSetInteger(pStmt, 7, tests.getAoeChoiceId());
            
            //
            if (tests.getRefLabAnswer() == null) {
                throw new SQLException("Null reference lab value being set");
            } else {                
                    pStmt.setString(8, tests.getRefLabAnswer());                                
            }
            SQLUtil.SafeSetDate(pStmt, 9, tests.getCreated());
            SQLUtil.SafeSetInteger(pStmt, 10, tests.getIdusers() );
            
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
    
    private AOEAnswers setAOEAnswersFromResultset(AOEAnswers answer,
            ResultSet rs) throws SQLException
    {
        answer.setId(rs.getInt("id"));
        answer.setIdOrders(rs.getInt("idorders"));
        answer.setQuestionId(rs.getInt("questionId"));
        answer.setAnswerText(rs.getString("answerText"));
        // In rare cases, number values can be blank
        if (rs.getString("answerNumber") == null)
        {
            answer.setAnswerNumber(null);
        }
        else
        {
            answer.setAnswerNumber(rs.getDouble("answerNumber"));
        }    
        
        answer.setAnswerBool(rs.getBoolean("answerBool"));
        answer.setAnswerDate(rs.getDate("answerDate"));
        answer.setAoeChoiceId(rs.getInt("aoeChoiceId"));
        answer.setRefLabAnswer(rs.getString("refLabAnswer"));
        answer.setCreated(rs.getDate("created"));
        answer.setIdusers(rs.getInt("idusers"));
        
        return answer;
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
