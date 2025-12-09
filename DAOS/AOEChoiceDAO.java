/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AOEChoice;
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
public class AOEChoiceDAO implements IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`aoeChoice`";
    /**
     * All fields except unique identifier
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public AOEChoiceDAO()
    {
        fields.add("questionId");
        fields.add("choice");
        fields.add("choiceOrder");
        fields.add("refLabValue");  
    }
    
    public ArrayList<AOEChoice> GetChoicesByAOEQuestionID(int questionID)
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
            ArrayList<AOEChoice> results = new ArrayList<>();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `questionId` = " + questionID +
                    " ORDER BY choiceOrder";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                results.add(setAOEChoiceFromResultSet(new AOEChoice(), rs));        
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
    
    public boolean InsertAOEChoice(AOEChoice choice)
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
            SQLUtil.SafeSetInteger(pStmt, 1, choice.getQuestionId());
            SQLUtil.SafeSetString(pStmt, 2, choice.getChoice());
            SQLUtil.SafeSetInteger(pStmt, 3, choice.getChoiceOrder());
            SQLUtil.SafeSetString(pStmt, 4, choice.getRefLabValue());
            
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
    
    public boolean UpdateAOEChoice(AOEChoice choice) //throws SQLException
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
        " WHERE `id` = " + choice.getId();
        
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            SQLUtil.SafeSetInteger(pStmt, 1, choice.getQuestionId());
            SQLUtil.SafeSetString(pStmt, 2, choice.getChoice());
            SQLUtil.SafeSetInteger(pStmt, 3, choice.getChoiceOrder());
            SQLUtil.SafeSetString(pStmt, 4, choice.getRefLabValue());
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
    
    private AOEChoice setAOEChoiceFromResultSet(AOEChoice choice,
            ResultSet rs) throws SQLException
    {
        choice.setId(rs.getInt("id"));
        choice.setQuestionId(rs.getInt("questionId"));
        choice.setChoice(rs.getString("choice"));
        choice.setChoiceOrder(rs.getInt("choiceOrder"));
        choice.setRefLabValue(rs.getString("refLabValue"));
        return choice;
        
    }
    
    public ArrayList<AOEChoice> GetChoicesByAOEQuestion(AOEQuestion question)
    {
        if (question == null || question.getId() == null) return null;
        return GetChoicesByAOEQuestionID(question.getId());
    }
    
    /**
     * Gets an AOEChoice object from its unique table identifier (id)
     * @param id
     * @return 
     */
    public AOEChoice GetAOEChoiceById(int id)
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
            AOEChoice result = null;
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + id;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                result = setAOEChoiceFromResultSet(new AOEChoice(), rs);
            }

            rs.close();
            stmt.close();
            
            return result;
        }
        catch (SQLException ex)
        {
            return null;
        }        
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

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
