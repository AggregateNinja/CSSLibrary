package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Departments;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utility.SQLUtil;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Feb 7, 2013
 * @author: CSS Dev
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: Department.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class DepartmentDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    public static final String table = "`departments`";

    public boolean InsertDepartment(Departments department) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {
            String stmt = "INSERT INTO " + table + "("
                    + " `deptNo`,"
                    + " `deptName`,"
                    + " `ReferenceLab`,"
                    + " `promptPOC`,"
                    + " `comment`)"
                    + " values (?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, department.getDeptNo());
            pStmt.setString(2, department.getDeptName());
            pStmt.setBoolean(3, department.isReferenceLab());
            pStmt.setBoolean(4, department.isPromptPOC());
            pStmt.setString(5, department.getComment());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    public boolean UpdateDepartment(Departments department) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        try
        {

            String stmt = "UPDATE " + table + " SET"
                    + " `deptNo` = ?,"
                    + " `deptName` = ?,"
                    + " `ReferenceLab` = ?,"
                    + " `promptPOC` = ?,"
                    + " `comment` = ? "
                    + "WHERE `idDepartment` = " + department.getIdDepartment();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, department.getDeptNo());
            pStmt.setString(2, department.getDeptName());
            pStmt.setBoolean(3, department.isReferenceLab());
            pStmt.setBoolean(4, department.isPromptPOC());
            pStmt.setString(5, department.getComment());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    public Departments GetDepartment(int deptNumber) throws SQLException
    {
        try
        {
            if(con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
        
        try
        {
            Departments dept = new Departments();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " WHERE `deptNo = " + deptNumber;
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                dept = ObjectFromResultSet(rs);
            }
            rs.close();
            stmt.close();
            return dept;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public Departments GetDepartmentByID(int ID) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Departments dept = new Departments();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idDepartment` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
            }

            rs.close();
            stmt.close();

            return dept;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Departments GetDepartmentByNumber(int Number) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Departments dept = new Departments();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `deptNo` = " + Number;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {

                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
            }

            rs.close();
            stmt.close();

            return dept;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;

        }
    }

    public ArrayList<Departments> GetReferenceLabsByDepartment()
    {
        ArrayList<Departments> deptList = new ArrayList<>();
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `ReferenceLab` = 1 ";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Departments dept = new Departments();
                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
                deptList.add(dept);
            }

            rs.close();
            stmt.close();

            return deptList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Search for any department with a name starting with the supplied string.
     * Returns an empty list if none are found
     *
     * @param name
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public static Collection<Departments> searchDepartmentsByName(String name)
            throws SQLException, NullPointerException, IllegalArgumentException
    {

        if (name == null)
        {
            throw new IllegalArgumentException("DepartmentDAO::searchDepartmentsByName:"
                    + " Received a [NULL] String object for 'deptName'");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        name = SQLUtil.createSearchParam(name);
        String sql = "SELECT * FROM " + table + " WHERE LOWER(`deptName`) LIKE LOWER(?)";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);

        List<Departments> departments = new ArrayList<>();

        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                departments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = ex.getMessage() + " " + sql + " for `deptName`=" + name;
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }
        pStmt.close();
        return departments;
    }

    public Departments GetDepartmentByName(String name) throws SQLException
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Departments dept = new Departments();
            PreparedStatement stmt = null; //con.createStatement();

//            String s = name;
//            s = s.replace("'", "''");

            String query = "SELECT * FROM " + table
                    + "WHERE `deptName` = ?";

            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {

                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
            }

            rs.close();
            stmt.close();

            return dept;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * The method allows user to provide a name, a boolean value and other column values to perform search 
     * for departments. 
     * @param Aanchal Chaturvedi
     * @param CaseSensitive
     * @param OtherColumns
     * @return Result Set of the query being executed
     */
    
    public ResultSet SearchByDeptName(String name, boolean CaseSensitive, String... OtherColumns)
    {
       try {
            if (con.isClosed()) {
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
            
            String query = "SELECT `deptNo`, `deptName`"
                    + addedCols
                    + " FROM " + table + " ";
            if(CaseSensitive)
            {
                query += "WHERE `deptName` LIKE ? ";
            }
            else
            {
                query += "WHERE LOWER(`deptName`) LIKE LOWER(?) ";
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

    public Departments[] GetAllDepartments()
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ArrayList< Departments> deptList = new ArrayList< Departments>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `deptNo` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Departments dept = new Departments();
                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
                deptList.add(dept);
            }
            rs.close();
            stmt.close();

            Departments[] deptArray = new Departments[deptList.size()];
            deptArray = deptList.toArray(deptArray);
            return deptArray;

        }
        catch (Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }

    public ArrayList<Departments> GetAllDepartmentsComboBox()
    {
        ArrayList<Departments> deptList = new ArrayList<>();
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `deptNo` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                Departments dept = new Departments();
                dept.setIdDepartment(rs.getInt("idDepartment"));
                dept.setDeptNo(rs.getInt("deptNo"));
                dept.setDeptName(rs.getString("deptName"));
                dept.setReferenceLab(rs.getBoolean("ReferenceLab"));
                dept.setPromptPOC(rs.getBoolean("promptPOC"));
                dept.setComment(rs.getString("comment"));
                dept.setTestXref(rs.getInt("testXref"));
                dept.setResTable(rs.getString("resTable"));
                dept.setResultProcedure(rs.getString("resultProcedure"));
                deptList.add(dept);
            }

            rs.close();
            stmt.close();

            return deptList;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return InsertDepartment((Departments) obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return UpdateDepartment((Departments) obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetDepartmentByID(ID);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DepartmentDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Departments ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (rs == null)
        {
            throw new IllegalArgumentException("DepartmentDAO::ObjectFromResultSet:"
                    + " Received a [NULL] ResultSet object argument");
        }

        Departments department = new Departments();
        department.setIdDepartment(rs.getInt("idDepartment"));
        department.setDeptNo(rs.getInt("deptNo"));
        department.setDeptName(rs.getString("deptName"));
        department.setReferenceLab(rs.getBoolean("ReferenceLab"));
        department.setPromptPOC(rs.getBoolean("promptPOC"));
        department.setComment(rs.getString("comment"));
        department.setTestXref(rs.getInt("testXref"));
        department.setResTable(rs.getString("resTable"));
        department.setResultProcedure(rs.getString("resultProcedure"));
        return department;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `departments`.`idDepartment`,\n"
                + "    `departments`.`deptNo`,\n"
                + "    `departments`.`deptName`,\n"
                + "    `departments`.`ReferenceLab`,\n"
                + "    `departments`.`promptPOC`,\n"
                + "    `departments`.`comment`,\n"
                + "    `departments`.`testXref`,\n"
                + "    `departments`.`resTable`,\n"
                + "    `departments`.`resultProcedure`\n"
                + "FROM `css`.`departments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
