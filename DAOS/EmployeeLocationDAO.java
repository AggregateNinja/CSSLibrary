/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.EmployeeLocations;
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
public class EmployeeLocationDAO implements IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`employeeLocations`";

    /**
     * Insert new employee location in the table
     *
     * @param employeeLocation new
     * @return true if the employeeLocation is added to the table
     * @throws SQLException
     */
    public boolean InsertEmployeeLocation(EmployeeLocations employeeLocation) throws SQLException {
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
                    + " `employeeId`,"
                    + " `locationId`,"
                    + " `defaultLocation`)"
                    + " values (?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);
            SQLUtil.SafeSetInteger(pStmt, 1, employeeLocation.getEmployeeId());
            SQLUtil.SafeSetInteger(pStmt, 2, employeeLocation.getLocationId());
            pStmt.setBoolean(3, employeeLocation.getDefaultLocation());

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
     * Update existing employee location in the table
     *
     * @param employeeLocation (existing)
     * @return true if the employeeLocation is added
     * @throws SQLException
     */
    public boolean UpdateEmployeeLocation(EmployeeLocations employeeLocation) throws SQLException {
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
                    + " `employeeId` = ?,"
                    + " `locationId` = ?,"
                    + " `defaultLocation` = ?"
                    + " WHERE `employeeId` = " + employeeLocation.getEmployeeId();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, employeeLocation.getEmployeeId());
            SQLUtil.SafeSetInteger(pStmt, 2, employeeLocation.getLocationId());
            pStmt.setBoolean(3, employeeLocation.getDefaultLocation());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (SQLException ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    /**
     * Returns employee locations for the given employeeId
     *
     * @param employeeId
     * @return EmployeeLocations
     * @throws SQLException
     */
    public List<EmployeeLocations> GetEmployeeLocationByEmployeeId(int employeeId) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            List<EmployeeLocations> employeeLocationList = new ArrayList<EmployeeLocations>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `employeeId` = " + employeeId;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                EmployeeLocations employeeLocation = new EmployeeLocations();
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
     * Returns all employee locations
     *
     * @return array of existing employeeLocations
     * @throws SQLException
     */
    public EmployeeLocations[] GetAllEmployeeLocations() throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            ArrayList< EmployeeLocations> employeeLocationList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `locationId` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                EmployeeLocations empLocations = new EmployeeLocations();
                fromResultSet(empLocations, rs);
                employeeLocationList.add(empLocations);
            }

            rs.close();
            stmt.close();
            EmployeeLocations[] employeeLocationsArray = new EmployeeLocations[employeeLocationList.size()];
            employeeLocationsArray = employeeLocationList.toArray(employeeLocationsArray);
            return employeeLocationsArray;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    /**
     * Returns an array of employee locations for given locationId
     *
     * @param locationId
     * @return EmployeeLocations[]
     */
    public List<EmployeeLocations> GetEmployeeLocationByLocationId(int locationId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            ArrayList<EmployeeLocations> employeeLocationList = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `locationId` = " + locationId
                    + " ORDER BY `locationId` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                EmployeeLocations employeeLocation = new EmployeeLocations();
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
     * Returns default EmployeeLocations for the given employee id
     *
     * @param employeeId
     * @return EmployeeLocations
     */
    public EmployeeLocations GetDefaultEmployeeLocation(int employeeId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            EmployeeLocations employeeLocation = new EmployeeLocations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `employeeId` = " + employeeId
                    + " AND `defaultLocation` = 1";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fromResultSet(employeeLocation, rs);
            }

            rs.close();
            stmt.close();

            return employeeLocation;
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
            EmployeeLocations empLoc = (EmployeeLocations) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `employeeId` = " + empLoc.getEmployeeId() + " AND `locationId` = " + empLoc.getLocationId();
            
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

    public int DeleteAllForEmployee(int employeeId)
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
                    + "WHERE `employeeId` = " + employeeId;
            
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
     * Returns the employee location for a given employee id and location id,
     * if it exists. Otherwise null.
     *
     * @param employeeId
     * @param locationId
     * @return EmployeeLocations
     */
    public EmployeeLocations GetEmployeeLocation(int employeeId, int locationId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return null;
        }

        try {
            EmployeeLocations employeeLocation = new EmployeeLocations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `employeeId` = " + employeeId
                    + " AND `locationId` = " + locationId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                fromResultSet(employeeLocation, rs);
            }

            rs.close();
            stmt.close();

            return employeeLocation;
        } catch (SQLException ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }

    }

    /**
     * Sets parameters for the given employee location from the given result
     * set.
     *
     * @param employeeLocation
     * @param rs
     * @throws SQLException
     */
    private void fromResultSet(EmployeeLocations employeeLocation, ResultSet rs) throws SQLException {
        employeeLocation.setEmployeeId(rs.getInt("employeeId"));
        employeeLocation.setLocationId(rs.getInt("locationId"));
        employeeLocation.setDefaultLocation(rs.getBoolean("defaultLocation"));
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `employeeLocations`.`employeeId`,\n"
                + "    `employeeLocations`.`locationId`,\n"
                + "    `employeeLocations`.`defaultLocation`\n"
                + "FROM `css`.`employeeLocations` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }

}
