/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 29, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.SubscriberComment;
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
 * @date:   Jul 29, 2014  5:04:42 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SubscriberCommentDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class SubscriberCommentDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`subscriberComment`";

    private final ArrayList<String> fields = new ArrayList<>();
    
    public SubscriberCommentDAO(){
        fields.add("subscriber");
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
            SubscriberComment com = (SubscriberComment) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromComment(com, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::Insert - " + ex.toString());
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
            SubscriberComment com = (SubscriberComment) obj;
            String stmt = GenerateUpdateStatement(fields) + " WHERE `subscriber` = ?";
            //String stmt = GenerateInsertStatement(fields)+ " WHERE `subscriber` = ?";
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetString(pStmt, 1, com.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 2, com.getComment());
            
            SubscriberComment sc = (SubscriberComment)obj;
            SQLUtil.SafeSetString(pStmt, 3, sc.getSubscriber());
            
            SetStatementFromComment(com, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::Update - " + ex.toString());
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
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberComment::getByID - " + ex.toString());
            return null;
        }
    }
    
    public Boolean UpdateComment(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            SubscriberComment com = (SubscriberComment) obj;
            String stmt = "UPDATE "+table+" SET "
                    + "`comment` = ? "
                    + "WHERE `subscriber` = ?";
     
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1, com.getComment());
            SQLUtil.SafeSetString(pStmt, 2, com.getSubscriber());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::Update - " + ex.toString());
            return false;
        }
    }
    
    public boolean Exists(String subscriber){
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
                    + "WHERE `subscriber` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, subscriber);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                count = rs.getInt("count");
            }
            
            rs.close();
            pStmt.close();
            
            return count > 0;
        }catch(Exception ex){
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::Exists - " + ex.toString());
            return false;
        }
    }
    
    public SubscriberComment GetCommentByID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SubscriberComment com = new SubscriberComment();
            
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
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::GetCommentByID - " + ex.toString());
            return null;
        }
    }
    
    public SubscriberComment GetCommentBySubscriberAR(String ar){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SubscriberComment com = new SubscriberComment();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `subscriber` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, ar);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                SetCommentFromResultSet(com,rs);
            }
            
            rs.close();
            pStmt.close();
            
            return com;
        }catch(Exception ex){
            Logger.getLogger(SubscriberComment.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SubscriberCommentDAO::GetCommentBySubscriberID - " + ex.toString());
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
    
    private SubscriberComment SetCommentFromResultSet(SubscriberComment com, ResultSet rs) throws SQLException {
        com.setId(rs.getInt("id"));
        com.setSubscriber(rs.getString("subscriber"));
        com.setComment(rs.getString("comment"));
        
        return com;
    }
    
    private PreparedStatement SetStatementFromComment(SubscriberComment com, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, com.getSubscriber());
        SQLUtil.SafeSetString(pStmt, 2, com.getComment());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
