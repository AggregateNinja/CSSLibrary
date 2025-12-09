/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 29, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PatientComment;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Jul 29, 2014  3:26:08 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: PatientCommentDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class PatientCommentDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`patientComment`";

    private final ArrayList<String> fields = new ArrayList<>();
    
    /**
     * Add all field names except id
     */
    public PatientCommentDAO() {
        fields.add("idpatients");
        fields.add("comment");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PatientComment com = (PatientComment) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromComment(com, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::Insert - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            PatientComment com = (PatientComment) obj;
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + com.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromComment(com, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::Update - " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj) {
         try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        return false;
    }

    @Override
    public Serializable getByID(Integer ID) {
        try{
            return GetCommentByID(ID);
        }catch(Exception ex){
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::getByID - " + ex.toString());
            return null;
        }
    }
    
    public boolean Exists(int patientId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            int count = 0;
            
            String query = "SELECT COUNT(*) AS 'count' FROM " + table + " "
                    + "WHERE `idpatients` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, patientId);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("count");
            }
            
            rs.close();
            pStmt.close();
            
            return count > 0;
        }catch(Exception ex){
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::Exists - " + ex.toString());
            return false;
        }
    }
    
    public PatientComment GetCommentByID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            PatientComment com = new PatientComment();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `id` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ID);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetCommentFromResultSet(com,rs);
            }
            
            rs.close();
            pStmt.close();
            
            return com;
        }catch(Exception ex){
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::GetCommentByID - " + ex.toString());
            return null;
        }
    }
    
    public PatientComment GetCommentByPatientID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            PatientComment com = new PatientComment();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idpatients` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, ID);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetCommentFromResultSet(com,rs);
            }
            
            rs.close();
            pStmt.close();
            
            return com;
        }catch(Exception ex){
            Logger.getLogger(PatientComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("PatientCommentDAO::GetCommentByPatientID - " + ex.toString());
            return null;
        }
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
    
    private PatientComment SetCommentFromResultSet(PatientComment com, ResultSet rs) throws SQLException {
        com.setId(rs.getInt("id"));
        com.setIdpatients(rs.getInt("idpatients"));
        com.setComment(rs.getString("comment"));
        
        return com;
    }
    
    private PreparedStatement SetStatementFromComment(PatientComment com, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, com.getIdpatients());
        pStmt.setString(2, com.getComment());
        //SQLUtil.SafeSetString(pStmt, 2, com.getComment());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
