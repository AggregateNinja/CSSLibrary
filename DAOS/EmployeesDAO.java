/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmployeeDepartments;
import DOS.Employees;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 05/13/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class EmployeesDAO implements DAOInterface, IStructureCheckable {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    private final String table = "`employees`";
    
    private void CheckDBConnection(){
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    public boolean InsertEmployee(Employees emp) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            String stmt = "INSERT INTO " + table + "("
                    + "`firstName`, "
                    + "`lastName`, "
                    + "`department`, "
                    + "`position`, "
                    + "`homephone`, "
                    + "`mobilephone`, "
                    + "`address`, "
                    + "`address2`, "
                    + "`city`, "
                    + "`state`, "
                    + "`zip`, "
                    + "`active`) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setString(1, emp.getFirstName());
            pStmt.setString(2, emp.getLastName());
            pStmt.setInt(3, emp.getDepartment());
            pStmt.setString(4, emp.getPosition());
            pStmt.setString(5, emp.getHomePhone());
            pStmt.setString(6, emp.getMobilePhone());
            pStmt.setString(7, emp.getAddress());
            pStmt.setString(8, emp.getAddress2());
            pStmt.setString(9, emp.getCity());
            pStmt.setString(10, emp.getState());
            pStmt.setString(11, emp.getZip());
            pStmt.setBoolean(12, emp.isActive());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;        
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public Integer InsertEmployeeGetId(Employees emp) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        try
        {
            String stmt = "INSERT INTO " + table + "("
                    + "`firstName`, "
                    + "`lastName`, "
                    + "`department`, "
                    + "`position`, "
                    + "`homephone`, "
                    + "`mobilephone`, "
                    + "`address`, "
                    + "`address2`, "
                    + "`city`, "
                    + "`state`, "
                    + "`zip`,"
                    + "`active`) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);
            
            pStmt.setString(1, emp.getFirstName());
            pStmt.setString(2, emp.getLastName());
            pStmt.setInt(3, emp.getDepartment());
            pStmt.setString(4, emp.getPosition());
            pStmt.setString(5, emp.getHomePhone());
            pStmt.setString(6, emp.getMobilePhone());
            pStmt.setString(7, emp.getAddress());
            pStmt.setString(8, emp.getAddress2());
            pStmt.setString(9, emp.getCity());
            pStmt.setString(10, emp.getState());
            pStmt.setString(11, emp.getZip());
            pStmt.setBoolean(12, emp.isActive());
            
            pStmt.executeUpdate();
            Integer idResults;            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    idResults = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return idResults;        
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean UpdateEmployee(Employees emp) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            String stmt = "UPDATE " + table + " SET "
                    + "`firstName` = ?, "
                    + "`lastName` = ?, "
                    + "`department` = ?, "
                    + "`position` = ?, "
                    + "`homephone` = ?, "
                    + "`mobilephone` = ?, "
                    + "`address` = ?, "
                    + "`address2` = ?, "
                    + "`city` = ?, "
                    + "`state` = ?, "
                    + "`zip` = ?, "
                    + "`active` = ? "                    
                    + "WHERE `idemployees` = " + emp.getIdemployees();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            //pStmt.setInt(1, emp.getIdemployees());
            pStmt.setString(1, emp.getFirstName());
            pStmt.setString(2, emp.getLastName());
            pStmt.setInt(3, emp.getDepartment());
            pStmt.setString(4, emp.getPosition());
            pStmt.setString(5, emp.getHomePhone());
            pStmt.setString(6, emp.getMobilePhone());
            pStmt.setString(7, emp.getAddress());
            pStmt.setString(8, emp.getAddress2());
            pStmt.setString(9, emp.getCity());
            pStmt.setString(10, emp.getState());
            pStmt.setString(11, emp.getZip());
            pStmt.setBoolean(12, emp.isActive());
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public Employees GetEmployeeByName(String fName, String lName, boolean activeOnly) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Employees emp = new Employees();
        
        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `firstName` = ? "
                    + "AND `lastName` = ?";
            
            if (activeOnly)
            {
                query += " AND `active` = 1";
            }
                        
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, fName);
            pStmt.setString(2, lName);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                emp.setIdemployees(rs.getInt("idemployees"));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));
                emp.setDepartment(rs.getInt("department"));
                emp.setPosition(rs.getString("position"));
                emp.setHomePhone(rs.getString("homePhone"));
                emp.setMobilePhone(rs.getString("mobilePhone"));
                emp.setAddress(rs.getString("address"));
                emp.setAddress2(rs.getString("address2"));
                emp.setCity(rs.getString("city"));
                emp.setState(rs.getString("state"));
                emp.setZip(rs.getString("zip"));
                emp.setActive(rs.getBoolean("active"));
            }
            
            rs.close();
            pStmt.close();
            
            return emp;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * 
     */
    public Employees GetEmployeeByName(String fName) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Employees emp = new Employees();
        
        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `firstName` = ? ";
            
                        
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setString(1, fName);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                emp.setIdemployees(rs.getInt("idemployees"));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));
                emp.setDepartment(rs.getInt("department"));
                emp.setPosition(rs.getString("position"));
                emp.setHomePhone(rs.getString("homePhone"));
                emp.setMobilePhone(rs.getString("mobilePhone"));
                emp.setAddress(rs.getString("address"));
                emp.setAddress2(rs.getString("address2"));
                emp.setCity(rs.getString("city"));
                emp.setState(rs.getString("state"));
                emp.setZip(rs.getString("zip"));
                emp.setActive(rs.getBoolean("active"));
            }
            
            rs.close();
            pStmt.close();
            
            return emp;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * 
     * @param activeOnly Whether to only return employees that are currently set to active
     * @return
     * @throws SQLException 
     */
    public ArrayList<Employees> GetAllEmployees(boolean activeOnly) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Employees emp = new Employees();
        ArrayList<Employees> empList = new ArrayList<>();
        
        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idemployees` != 1 "
                    + "AND `idemployees` != 2 ";
            
            if (activeOnly)
            {
                query += " AND `active` = 1 ";
            }
            query += " ORDER BY `lastName`, `firstName`";

            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                emp = new Employees();
                emp.setIdemployees(rs.getInt("idemployees"));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));
                emp.setDepartment(rs.getInt("department"));
                emp.setPosition(rs.getString("position"));
                emp.setHomePhone(rs.getString("homePhone"));
                emp.setMobilePhone(rs.getString("mobilePhone"));
                emp.setAddress(rs.getString("address"));
                emp.setAddress2(rs.getString("address2"));
                emp.setCity(rs.getString("city"));
                emp.setState(rs.getString("state"));
                emp.setZip(rs.getString("zip"));
                emp.setActive(rs.getBoolean("active"));
                
                empList.add(emp);
            }
            
            rs.close();
            stmt.close();
            
            return empList;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Employees> GetAllEmployeesByDepartment(int DepartmentID, boolean activeOnly) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Employees emp = new Employees();
        ArrayList<Employees> empList = new ArrayList<Employees>();
        
        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table +
                    " WHERE `department` = " + DepartmentID + " "
                    + "AND `idemployees` != 1 "
                    + "AND `idemployees` != 2";
            
            if (activeOnly)
            {
                query += " AND `active` = 1 ";
            }
            query += " ORDER BY `lastName`, `firstName` ";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                emp = new Employees();
                emp.setIdemployees(rs.getInt("idemployees"));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));
                emp.setDepartment(rs.getInt("department"));
                emp.setPosition(rs.getString("position"));
                emp.setHomePhone(rs.getString("homePhone"));
                emp.setMobilePhone(rs.getString("mobilePhone"));
                emp.setAddress(rs.getString("address"));
                emp.setAddress2(rs.getString("address2"));
                emp.setCity(rs.getString("city"));
                emp.setState(rs.getString("state"));
                emp.setZip(rs.getString("zip"));
                emp.setActive(rs.getBoolean("active"));
                
                empList.add(emp);
            }
            
            rs.close();
            stmt.close();
            
            return empList;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Employees> GetAllEmployeesByDepartment(EmployeeDepartments department, boolean activeOnly) throws SQLException
    {
        if(department != null && department.getIdemployeeDepartments() != null)
            return GetAllEmployeesByDepartment(department.getIdemployeeDepartments(), activeOnly);
        else
            return new ArrayList<Employees>();
    }
    
    public Employees GetEmployeeByID(int ID) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        Employees emp = new Employees();
        
        try
        {
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idemployees` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                emp.setIdemployees(rs.getInt("idemployees"));
                emp.setFirstName(rs.getString("firstName"));
                emp.setLastName(rs.getString("lastName"));
                emp.setDepartment(rs.getInt("department"));
                emp.setPosition(rs.getString("position"));
                emp.setHomePhone(rs.getString("homePhone"));
                emp.setMobilePhone(rs.getString("mobilePhone"));
                emp.setAddress(rs.getString("address"));
                emp.setAddress2(rs.getString("address2"));
                emp.setCity(rs.getString("city"));
                emp.setState(rs.getString("state"));
                emp.setZip(rs.getString("zip"));
                emp.setActive(rs.getBoolean("active"));
            }
            
            rs.close();
            pStmt.close();
            
            return emp;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
   
    /**
     * Search for an employee record using the given name which is considered case-insensitive
     * and can match the following forms: lastName, firstName, 'firstName lastName', and 'lastName, firstName'.
     * @param name the name fragment to search for
     * @return the ResultSet of employees matching the given name
     */
    public ResultSet SearchByEmployeeName(String name, boolean activeOnly) {
        ResultSet rs;
        String query;
        try {
            if (con.isClosed() || !con.isValid(2)) {
                CheckDBConnection();
            }
            
            PreparedStatement stmt = null; //con.createStatement();
            query = "SELECT `idemployees` AS 'ID', `lastName` AS 'Last name', "
                    + "`firstName` AS 'First name' FROM employees WHERE (LOWER(`lastName`) "
                    + "LIKE LOWER(?) OR LOWER(`firstName`) LIKE LOWER(?) "
                    + "OR CONCAT(LOWER(`lastName`), ', ', LOWER(`firstName`)) LIKE LOWER(?) "
                    + "OR CONCAT(LOWER(`firstName`), ' ', LOWER(`lastName`)) LIKE LOWER(?))";
            
            if (activeOnly)
            {
                query += " AND `active` = 1";
            }
            String searchParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, searchParam, searchParam, searchParam, searchParam);
            rs = stmt.executeQuery();
        }
        catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }
        return rs;
    }
    
    public ResultSet SearchLastName(String lastNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (lastNameFragment == null) lastNameFragment = "";
        lastNameFragment = lastNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `lastName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, lastNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
    }
    
    public ResultSet SearchFirstName(String firstNameFragment, boolean activeOnly)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (firstNameFragment == null) firstNameFragment = "";
        firstNameFragment = firstNameFragment.replaceAll("%", "") + '%';
        String sql = "SELECT * FROM " + table + " WHERE `firstName` LIKE ?";
        if (activeOnly) sql += " AND `active` = 1";
        sql += " ORDER BY `lastName` ASC, `firstName` ASC";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, firstNameFragment);
        ResultSet rs = pStmt.executeQuery();
        return rs;
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
        String query = "SELECT `employees`.`idemployees`,\n"
                + "    `employees`.`firstName`,\n"
                + "    `employees`.`lastName`,\n"
                + "    `employees`.`department`,\n"
                + "    `employees`.`position`,\n"
                + "    `employees`.`facilityName`,\n"
                + "    `employees`.`homePhone`,\n"
                + "    `employees`.`mobilePhone`,\n"
                + "    `employees`.`address`,\n"
                + "    `employees`.`address2`,\n"
                + "    `employees`.`city`,\n"
                + "    `employees`.`state`,\n"
                + "    `employees`.`zip`,\n"
                + "    `employees`.`active`\n"
                + "FROM `css`.`employees` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }

}
