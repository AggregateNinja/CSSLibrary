/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Xrefs;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
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

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 02/27/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class XrefsDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`xrefs`";
    
    /*
     * All fields except idxrefs
     */
    private final ArrayList<String> fields = new ArrayList<>();
    
    public XrefsDAO()
    {
        fields.add("name");
        fields.add("type");
        fields.add("description");
        fields.add("created");
    }
    
    private void CheckDBConnection(){
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    public boolean InsertXref(Xrefs xref){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            String stmt = GenerateInsertStatement(fields);
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, xref.getName());
            SQLUtil.SafeSetString(pStmt, 2, xref.getType());
            SQLUtil.SafeSetString(pStmt, 3, xref.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(xref.getCreated()));
            
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean UpdateXref(Xrefs xref){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idxrefs` = " + xref.getIdxrefs();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, xref.getName());
            SQLUtil.SafeSetString(pStmt, 2, xref.getType());
            SQLUtil.SafeSetString(pStmt, 3, xref.getDescription());
            SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(xref.getCreated()));
            
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public Xrefs GetXrefById(int id){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            Xrefs xref = new Xrefs();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetXrefFromResultSet(xref, rs);
            }
            rs.close();
            stmt.close();
            return xref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Xrefs GetXrefByName(String name){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            Xrefs xref = new Xrefs();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?";
            
            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                SetXrefFromResultSet(xref, rs);
            }
            rs.close();
            stmt.close();
            return xref;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Xrefs> GetAllXrefs(){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            ArrayList<Xrefs> list = new ArrayList<>();
            Xrefs xref = new Xrefs();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                xref = new Xrefs();
                
                SetXrefFromResultSet(xref, rs);
                
                list.add(xref);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Xrefs> GetAllXrefsByType(String type){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            ArrayList<Xrefs> list = new ArrayList<>();
            Xrefs xref = new Xrefs();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `type` = ?";
            
            stmt = createStatement(con, query, type);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                xref = new Xrefs();
                
                SetXrefFromResultSet(xref, rs);
                
                list.add(xref);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if(i != fields.size() - 1)
            {
                stmt += ", ";
                values +=",";
            }
        }
        values += ")";
        return stmt + values;
    }
    
    private String GenerateUpdateStatement(ArrayList<String> fields) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        String stmt = "UPDATE " + table + " SET";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if(i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    public Xrefs SetXrefFromResultSet(Xrefs xref, ResultSet rs) throws SQLException
    {
        xref.setIdxrefs(rs.getInt("idxrefs"));
        xref.setName(rs.getString("name"));
        xref.setType(rs.getString("type"));
        xref.setDescription(rs.getString("description"));
        xref.setCreated(rs.getTimestamp("created"));
        
        return xref;
    }
    
    public static Xrefs Insert(Xrefs xref)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (xref == null) throw new IllegalArgumentException("XrefsDAO::Insert: Received a NULL Xrefs object argument!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);        
        
        String sql = "INSERT INTO `xrefs`"
                + "(`name`,`type`,`description`,`created`)"
                + "VALUES"
                + "(?,?,?,NOW())";
        
        PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        SQLUtil.SafeSetString(pStmt, 1, xref.getName());
        SQLUtil.SafeSetString(pStmt, 2, xref.getType());
        SQLUtil.SafeSetString(pStmt, 3, xref.getDescription());
        
        pStmt.executeUpdate();
        
        Integer newId = null;
        ResultSet rs = pStmt.getGeneratedKeys();
        if (rs.next())
        {
            newId = rs.getInt(1);
        }

        if (newId == null) throw new SQLException("XrefsDAO::Insert: Could not return generated database identifier");
        
        pStmt.close();
        xref.setIdxrefs(newId);
        return xref;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
