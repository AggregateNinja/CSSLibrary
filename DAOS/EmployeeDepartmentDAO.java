/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmployeeDepartments;
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

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/27/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class EmployeeDepartmentDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    private final String table = "`employeeDepartments`";
    
    public boolean InsertEmployeeDepartment(EmployeeDepartments ed) throws SQLException {
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = "INSERT INTO " + table + " ("
                    + "`name`, "
                    + "`defaultUserGroup`) "
                    + "VALUES (?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, ed.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, ed.getIdemployeeDepartments());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public boolean UpdateEmploeeDepartment(EmployeeDepartments ed) throws SQLException {
        try {
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = "UPDATE " + table + " SET "
                    + "`name` = ?, "
                    + "`defaultUserGroup` = ? "
                    + "WHERE `idemployeeDepartments` = " + ed.getIdemployeeDepartments();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, ed.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, ed.getDefaultUserGroup());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public EmployeeDepartments GetByID(int id) throws SQLException{
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
                    
            Statement stmt = con.createStatement();
            EmployeeDepartments ed = new EmployeeDepartments();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idemployeeDepartments` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                ed = EmployeeDeptFromResultSet(ed,rs);
            }
            
            rs.close();
            stmt.close();
            
            return ed;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public EmployeeDepartments GetByName(String name) throws SQLException{
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
            
            PreparedStatement stmt = null; //con.createStatement();
            EmployeeDepartments ed = new EmployeeDepartments();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `name` = ?";
            
            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                ed = EmployeeDeptFromResultSet(ed,rs);
            }
            
            rs.close();
            stmt.close();
            
            return ed;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<EmployeeDepartments> GetAllEmployeeDepartments() throws SQLException{
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return null;
            }
                    
            ArrayList<EmployeeDepartments> list = new ArrayList<EmployeeDepartments>();
            Statement stmt = con.createStatement();
            EmployeeDepartments ed = new EmployeeDepartments();
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ed = new EmployeeDepartments();
                ed = EmployeeDeptFromResultSet(ed,rs);
                list.add(ed);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public EmployeeDepartments EmployeeDeptFromResultSet(EmployeeDepartments ed, ResultSet rs) throws SQLException {
        ed.setIdemployeeDepartments(rs.getInt("idemployeeDepartments"));
        ed.setName(rs.getString("name"));
        ed.setDefaultUserGroup(rs.getInt("defaultUserGroup"));
        
        return ed;
    }
    
    /**
     * The method allows user to provide a name, a boolean value and other column values to perform search 
     * for employee departments. 
     * @param Aanchal Chaturvedi
     * @param CaseSensitive
     * @param OtherColumns
     * @return Result Set of the query being executed
     */
    
    public ResultSet SearchByDeptName(String name, boolean CaseSensitive, String... OtherColumns)
    {
       try {
            if (con.isClosed() || !con.isValid(2)) {
                CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
       
       String addedCols = "";
       for(String col : OtherColumns)
       {
           if(col != null && col.isEmpty() == false)
           {
               addedCols += ", " + col;
           }
       }
       
        try
        { 
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT `idemployeeDepartments`, `name`"
                    + addedCols
                    + " FROM " + table + " ";
            if(CaseSensitive)
            {
                query += "WHERE `name` LIKE ? ";
            }
            else
            {
                query += "WHERE LOWER(`name`) LIKE LOWER(?) ";
            }
            
            ResultSet rs;
            String searchParam = SQLUtil.createSearchParam(name);

            stmt = createStatement(con, query, searchParam);
            rs = stmt.executeQuery();
            
            return rs;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * The method allows user to search for an employee just by providing a name as parameter.
     * @param Aanchal Chaturvedi
     * @return
     * @throws SQLException 
     */
    public ResultSet SearchByDeptName(String name) throws SQLException
    {
        return SearchByDeptName(name, false);
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
        String query = "SELECT `employeeDepartments`.`idemployeeDepartments`,\n"
                + "    `employeeDepartments`.`name`,\n"
                + "    `employeeDepartments`.`defaultUserGroup`\n"
                + "FROM `css`.`employeeDepartments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
