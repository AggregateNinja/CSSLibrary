/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOEQuestion;
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
public class AOEQuestionDAO implements IStructureCheckable//implements DAOInterface
{
    Database.DatabaseSingleton dbs = 
            Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeQuestions`";

    private final ArrayList<String> fields = new ArrayList<>();

    public AOEQuestionDAO()
    {
        // Excluding unique database identifier
        fields.add("aoeGroupingTypeId");
        fields.add("question");
        fields.add("isDate");
        fields.add("isBool");
        fields.add("isNumber");
        fields.add("isString");
        fields.add("isMultiChoice");
        fields.add("minimumLength");
        fields.add("maximumLength");
        fields.add("minimumNumericValue");
        fields.add("maximumNumericValue");
        fields.add("isFloatingPoint");
        fields.add("minimumDate");
        fields.add("maximumDate");
        fields.add("allowFutureDate");
        fields.add("placeHolderCharacter");
        fields.add("paddedSize");
        fields.add("valueFormatMask");
        fields.add("isRightJustified");
        fields.add("hidden");
    }
    
    public ArrayList<AOEQuestion> GetAOEQuestionsByOrderID(int idOrders)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        ArrayList<AOEQuestion> results = new ArrayList<>();

        try
        {
            String query = "SELECT DISTINCT q.* " +
                    "FROM aoeQuestions q " +
                    "INNER JOIN aoeTests t on t.questionId = q.id " +
                    "INNER JOIN results r on t.idtests = r.testId " +
                    "WHERE r.orderId = " + idOrders;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                results.add(setAOEQuestionsFromResultset(new AOEQuestion(), rs));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException ex)
        {
            return null;
        }
        
        return results;                
    }
    
    public AOEQuestion GetByQuestionID(int idquestion)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        
        AOEQuestion result = null;
        try
        {
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + idquestion;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                result = setAOEQuestionsFromResultset(new AOEQuestion(), rs);        
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException ex)
        {
            return null;
        }
        
        return result;
        
    }
    
    public ArrayList<AOEQuestion> GetAOEQuestionsByAOETestsID(int idAOETests)
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
            ArrayList<AOEQuestion> results = new ArrayList<>();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `idtests` = " + idAOETests;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                results.add(setAOEQuestionsFromResultset(new AOEQuestion(), rs));        
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
    
    public boolean InsertAOEQuestions(AOEQuestion question)
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
            SQLUtil.SafeSetInteger(pStmt, 1, question.getAoeGroupingTypeId());
            SQLUtil.SafeSetString(pStmt, 2, question.getQuestion());
            SQLUtil.SafeSetBoolean(pStmt, 3, question.getIsDate());
            SQLUtil.SafeSetBoolean(pStmt, 4, question.getIsBool());
            SQLUtil.SafeSetBoolean(pStmt, 5, question.getIsNumber());
            SQLUtil.SafeSetBoolean(pStmt, 6, question.getIsString());
            SQLUtil.SafeSetBoolean(pStmt, 7, question.getIsMultiChoice());
            SQLUtil.SafeSetRangeInteger(pStmt, 8, question.getMinimumLength());
            SQLUtil.SafeSetRangeInteger(pStmt, 9, question.getMaximumLength());
            SQLUtil.SafeSetDouble(pStmt, 10, question.getMinimumNumericValue());
            SQLUtil.SafeSetDouble(pStmt, 11, question.getMaximumNumericValue());
            SQLUtil.SafeSetBoolean(pStmt, 12, question.isFloatingPoint());
            SQLUtil.SafeSetDate(pStmt, 13, question.getMinimumDate());
            SQLUtil.SafeSetDate(pStmt, 14, question.getMaximumDate());
            SQLUtil.SafeSetBoolean(pStmt, 15, question.isAllowFutureDate());
            SQLUtil.SafeSetString(pStmt, 16, question.getPlaceHolderCharacter());
            SQLUtil.SafeSetRangeInteger(pStmt,17,question.getPaddedSize());
            SQLUtil.SafeSetString(pStmt, 18, question.getValueFormatMask());
            SQLUtil.SafeSetBoolean(pStmt, 19, question.isRightJustified());
            SQLUtil.SafeSetBoolean(pStmt, 20, question.isHidden());
            
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
    
    public boolean UpdateAOEQuestions(AOEQuestion question)
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
        " WHERE `id` = " + question.getId();
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        { 
            SQLUtil.SafeSetInteger(pStmt, 1, question.getAoeGroupingTypeId());
            SQLUtil.SafeSetString(pStmt, 2, question.getQuestion());
            SQLUtil.SafeSetBoolean(pStmt, 3, question.getIsDate());
            SQLUtil.SafeSetBoolean(pStmt, 4, question.getIsBool());
            SQLUtil.SafeSetBoolean(pStmt, 5, question.getIsNumber());
            SQLUtil.SafeSetBoolean(pStmt, 6, question.getIsString());
            SQLUtil.SafeSetBoolean(pStmt, 7, question.getIsMultiChoice());
            SQLUtil.SafeSetRangeInteger(pStmt, 8, question.getMinimumLength());
            SQLUtil.SafeSetRangeInteger(pStmt, 9, question.getMaximumLength());
            SQLUtil.SafeSetDouble(pStmt, 10, question.getMinimumNumericValue());
            SQLUtil.SafeSetDouble(pStmt, 11, question.getMaximumNumericValue());
            SQLUtil.SafeSetBoolean(pStmt, 12, question.isFloatingPoint());
            SQLUtil.SafeSetDate(pStmt, 13, question.getMinimumDate());
            SQLUtil.SafeSetDate(pStmt, 14, question.getMaximumDate());
            SQLUtil.SafeSetBoolean(pStmt, 15, question.isAllowFutureDate());
            SQLUtil.SafeSetString(pStmt, 16, question.getPlaceHolderCharacter());
            SQLUtil.SafeSetRangeInteger(pStmt,17,question.getPaddedSize());            
            SQLUtil.SafeSetString(pStmt, 18, question.getValueFormatMask());
            SQLUtil.SafeSetBoolean(pStmt, 19, question.isRightJustified());
            SQLUtil.SafeSetBoolean(pStmt, 20, question.isHidden());
            
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
    
    private AOEQuestion setAOEQuestionsFromResultset(AOEQuestion question,
            ResultSet rs) throws SQLException
    {
        question.setId(rs.getInt("id"));
        question.setAoeGroupingTypeId(rs.getInt("aoeGroupingTypeId"));
        question.setQuestion(rs.getString("question"));
        question.setIsDate(rs.getBoolean("isDate"));
        question.setIsBool(rs.getBoolean("isBool"));
        question.setIsNumber(rs.getBoolean("isNumber"));
        question.setIsString(rs.getBoolean("isString"));
        question.setIsMultiChoice(rs.getBoolean("isMultiChoice"));
        
        if (rs.getString("minimumLength") != null)
        {
            question.setMinimumLength(rs.getInt("minimumLength"));
        }
        
        if (rs.getString("maximumLength") != null)
        {
            question.setMaximumLength(rs.getInt("maximumLength"));
        }
        
        question.setMinimumNumericValue(rs.getDouble("minimumNumericValue"));
        question.setMaximumNumericValue(rs.getDouble("maximumNumericValue"));
        question.setFloatingPoint(rs.getBoolean("isFloatingPoint"));
        question.setMinimumDate(rs.getDate("minimumDate"));
        question.setMaximumDate(rs.getDate("maximumDate"));
        question.setAllowFutureDate(rs.getBoolean("allowFutureDate"));
        question.setPlaceHolderCharacter(rs.getString("placeHolderCharacter"));
        question.setPaddedSize(rs.getInt("paddedSize"));
        question.setValueFormatMask(rs.getString("valueFormatMask"));
        question.setIsRightJustified(rs.getBoolean("isRightJustified"));
        question.setHidden(rs.getBoolean("hidden"));

        return question;
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
