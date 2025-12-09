/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.EmployeeDepartmentLocations;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @date January 22, 2018
 * @author Aanchal
 * @package DAOS
 * @file name: EmployeeLocationDAO
 */
public class EmployeeDepartmentLocationDAO implements IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`employeeDepartmentLocations`";

    /**
     * Insert new employee department location in the table
     *
     * @param employeeDepartmentLocation new
     * @return true if the employeeDepartmentLocation is added to the table
     * @throws SQLException
     */
    public boolean InsertEmployeeDepartmentLocation(EmployeeDepartmentLocations employeeDepartmentLocation) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        try {
            String stmt = "INSERT INTO " + table + "("
                    + " `employeeDepartmentId`,"
                    + " `locationId`,"
                    + " `defaultLocation`)"
                    + " values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetInteger(pStmt, 1, employeeDepartmentLocation.getEmployeeDepartmentId());
            SQLUtil.SafeSetInteger(pStmt, 2, employeeDepartmentLocation.getLocationId());
            pStmt.setBoolean(3, employeeDepartmentLocation.getDefaultLocation());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    /**
     * Update existing employee department Location in the table
     *
     * @param employeeDepartmentLocation (existing)
     * @return true if the employeeDepartmentLocation is added
     * @throws SQLException
     */
    public boolean UpdateEmployeeDepartmentLocation(EmployeeDepartmentLocations employeeDepartmentLocation) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = "UPDATE " + table + " SET"
                    + " `employeeDepartmentId` = ?,"
                    + " `locationId` = ?,"
                    + " `defaultLocation` = ?"
                    + " WHERE `employeeDepartmentId` = " + employeeDepartmentLocation.getEmployeeDepartmentId();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, employeeDepartmentLocation.getEmployeeDepartmentId());
            SQLUtil.SafeSetInteger(pStmt, 2, employeeDepartmentLocation.getLocationId());
            pStmt.setBoolean(3, employeeDepartmentLocation.getDefaultLocation());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    /**
     * Returns employee department location for the given employeeDepartmentId
     *
     * @param employeeDepartmentId
     * @return EmployeeDepartmentLocations
     * @throws SQLException
     */
    public EmployeeDepartmentLocations GetEmpDepartmentLocationByEmployeeId(int employeeDepartmentId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            EmployeeDepartmentLocations empDepartmenetLocation = new EmployeeDepartmentLocations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `employeeDepartmentId` = " + employeeDepartmentId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fromResultSet(empDepartmenetLocation, rs);
            }

            rs.close();
            stmt.close();

            return empDepartmenetLocation;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Returns all employee department locations
     *
     * @return array of existing employeeDepartmentLocations
     * @throws SQLException
     */
    public EmployeeDepartmentLocations[] GetAllEmployeeDepartmentLocations() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            ArrayList< EmployeeDepartmentLocations> empDeptLocationList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `locationId` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                EmployeeDepartmentLocations empDeptLocations = new EmployeeDepartmentLocations();
                fromResultSet(empDeptLocations, rs);
                empDeptLocationList.add(empDeptLocations);
            }

            rs.close();
            stmt.close();
            EmployeeDepartmentLocations[] empDeptLocationsArray = new EmployeeDepartmentLocations[empDeptLocationList.size()];
            empDeptLocationsArray = empDeptLocationList.toArray(empDeptLocationsArray);
            return empDeptLocationsArray;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Returns an array of employee department locations for given locationId
     *
     * @param locationId
     * @return EmployeeDepartmentLocations[]
     */
    public List<EmployeeDepartmentLocations> GetEmployeeDeptLocationByLocationId(int locationId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            List<EmployeeDepartmentLocations> employeeLocationList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `locationId` = " + locationId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                EmployeeDepartmentLocations employeeLocation = new EmployeeDepartmentLocations();
                fromResultSet(employeeLocation, rs);
                employeeLocationList.add(employeeLocation);
            }

            rs.close();
            stmt.close();
            return employeeLocationList;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Returns default Employee Department Locations for the given employee
     * department id
     *
     * @param employeeDepartmentId
     * @return EmployeeDepartmentLocations
     */
    public EmployeeDepartmentLocations GetDefaultEmployeeDeptLocation(int employeeDepartmentId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            EmployeeDepartmentLocations empDeptLocation = new EmployeeDepartmentLocations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `employeeDepartmentId` = " + employeeDepartmentId
                    + " AND `defaultLocation` = 1";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fromResultSet(empDeptLocation, rs);
            }

            rs.close();
            stmt.close();

            return empDeptLocation;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }

    }
    
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            EmployeeDepartmentLocations empLocDep = (EmployeeDepartmentLocations) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `employeeDepartmentId` = " + empLocDep.getEmployeeDepartmentId()+ " AND `locationId` = " + empLocDep.getLocationId();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    public int DeleteAllForLocation(int locationId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return 0;
        }
        try{
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `locationId` = " + locationId;
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return result;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return 0;
        }
    }

    /**
     * Returns the employee department location for the given employee department id
     * and the location id, if it exists.
     *
     * @param employeeDepartmentId
     * @param locationId
     * @return EmployeeDepartmentLocations
     */
    public EmployeeDepartmentLocations GetEmployeeDeptLocation(int employeeDepartmentId, int locationId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            EmployeeDepartmentLocations empDeptLocation = new EmployeeDepartmentLocations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `employeeDepartmentId` = " + employeeDepartmentId
                    + " AND `locationId` = " + locationId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fromResultSet(empDeptLocation, rs);
            }

            rs.close();
            stmt.close();

            return empDeptLocation;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }

    }

    /**
     * Sets parameters for the given employee department location from the given
     * result set.
     *
     * @param employeeDepartmentLocation
     * @param rs
     * @throws SQLException
     */
    private void fromResultSet(EmployeeDepartmentLocations employeeDepartmentLocation, ResultSet rs) throws SQLException {
        employeeDepartmentLocation.setEmployeeDepartmentId(rs.getInt("employeeDepartmentId"));
        employeeDepartmentLocation.setLocationId(rs.getInt("locationId"));
        employeeDepartmentLocation.setDefaultLocation(rs.getBoolean("defaultLocation"));
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `employeeDepartmentLocations`.`employeeDepartmentId`,\n"
                + "    `employeeDepartmentLocations`.`locationId`,\n"
                + "    `employeeDepartmentLocations`.`defaultLocation`\n"
                + "FROM `css`.`employeeDepartmentLocations` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }

}
