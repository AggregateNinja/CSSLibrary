/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 9, 2014
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.SalesGroup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Jul 9, 2014 4:04:45 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: SalesGroupDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class SalesGroupDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`salesGroup`";

    private final ArrayList<String> fields = new ArrayList<>();

    /**
     * All fields except id
     */
    public SalesGroupDAO() {
        fields.add("groupName");
        fields.add("groupLeader");
        fields.add("created");
        fields.add("createdBy");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        SalesGroup sg = (SalesGroup) obj;

        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromSalesGroup(sg, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(SQLException ex){
            Logger.getLogger(SalesGroup.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception inserting SalesGroup: " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        SalesGroup sg = (SalesGroup) obj;

        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + sg.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromSalesGroup(sg, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(SQLException ex){
            Logger.getLogger(SalesGroup.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception updating SalesGroup: " + ex.toString());
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
            return GetSalesGroupByID(ID);
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean InsertWithID(SalesGroup sg) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + " ("
                    + "`id`, "
                    + "`groupName`, "
                    + "`groupLeader`, "
                    + "`created`, "
                    + "`createdBy`) "
                    + "VALUES (?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, sg.getId());
            SQLUtil.SafeSetString(pStmt, 2, sg.getGroupName());
            SQLUtil.SafeSetInteger(pStmt, 3, sg.getGroupLeader());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(sg.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 5, sg.getCreatedBy());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public SalesGroup GetSalesGroupByID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SalesGroup sg = new SalesGroup();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `id` = " + ID;
            
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetSalesGroupFromResultSet(sg,rs);
            }
            
            rs.close();
            stmt.close();
            
            return sg;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public SalesGroup GetSalesGroupByName(String name){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SalesGroup sg = new SalesGroup();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `groupName` = ? ";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(stmt, 1, name);

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                SetSalesGroupFromResultSet(sg,rs);
            }
            
            rs.close();
            stmt.close();
            
            return sg;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public SalesGroup GetSalesGroupByLeader(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SalesGroup sg = new SalesGroup();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `groupLeader` = ? ";
            
            PreparedStatement stmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(stmt, 1, ID);

            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                SetSalesGroupFromResultSet(sg,rs);
            }
            
            rs.close();
            stmt.close();
            
            return sg;
        }catch(SQLException ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<SalesGroup> GetAllSalesGroups(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SalesGroup sg;
            ArrayList<SalesGroup> list = new ArrayList<>();
            
            String query = "SELECT * FROM " + table;
            
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                sg = new SalesGroup();
                SetSalesGroupFromResultSet(sg,rs);
                list.add(sg);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(SQLException ex){
            System.out.println(ex.toString());
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

    private SalesGroup SetSalesGroupFromResultSet(SalesGroup sg, ResultSet rs) throws SQLException {
        sg.setId(rs.getInt("id"));
        sg.setGroupName(rs.getString("groupName"));
        sg.setGroupLeader(rs.getInt("groupLeader"));
        sg.setCreated(rs.getTimestamp("created"));
        sg.setCreatedBy(rs.getInt("createdBy"));
        
        return sg;
    }
    
    private PreparedStatement SetStatementFromSalesGroup(SalesGroup sg, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetString(pStmt, 1, sg.getGroupName());
        SQLUtil.SafeSetInteger(pStmt, 2, sg.getGroupLeader());
        SQLUtil.SafeSetTimeStamp(pStmt, 3, Convert.ToSQLDateTime(sg.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, 4, sg.getCreatedBy());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
