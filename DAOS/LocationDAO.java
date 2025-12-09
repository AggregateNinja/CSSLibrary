package DAOS;

/**
 * @date: Mar 12, 2012
 * @author: Keith "Magic" Maggio
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Locations;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`locations`";

    public boolean InsertLocation(Locations location) throws SQLException
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
                    + " `locationNo`,"
                    + " `locationName`,"
                    + " `address1`,"
                    + " `address2`,"
                    + " `city`,"
                    + " `state`,"
                    + " `zip`,"
                    + " `phone`,"
                    + " `fax`,"
                    + " `director`,"
                    + " `npi`,"
                    + " `taxId`,"
                    + " `medicareNo`,"
                    + " `active`,"
                    + " `deactivatedBy`,"
                    + " `deactivatedDate`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, location.getLocationNo());
            pStmt.setString(2, location.getLocationName());
            pStmt.setString(3, location.getAddress1());
            pStmt.setString(4, location.getAddress2());
            pStmt.setString(5, location.getCity());
            pStmt.setString(6, location.getState());
            pStmt.setString(7, location.getZip());
            pStmt.setString(8, location.getPhone());
            pStmt.setString(9, location.getFax());
            pStmt.setString(10, location.getDirector());
            pStmt.setString(11, location.getNpi());
            pStmt.setString(12, location.getTaxId());
            pStmt.setString(13, location.getMedicareNo());
            pStmt.setBoolean(14, location.getActive());
            
            SQLUtil.SafeSetInteger(pStmt, 15, location.getDeactivatedBy());
            SQLUtil.SafeSetDate(pStmt, 16, location.getDeactivatedDate());

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

    public boolean UpdateLocation(Locations location) throws SQLException
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
                    + " `locationNo` = ?,"
                    + " `locationName` = ?,"
                    + " `address1` = ?,"
                    + " `address2` = ?,"
                    + " `city` = ?,"
                    + " `state` = ?,"
                    + " `zip` = ?,"
                    + " `phone` = ?,"
                    + " `fax` = ?,"
                    + " `director` = ?,"
                    + " `npi` = ?,"
                    + " `taxId` = ?,"
                    + " `medicareNo` = ?,"
                     + " `active` = ?,"
                    + " `deactivatedBy` = ?,"
                    + " `deactivatedDate` = ?"
                    + " WHERE `locationNo` = " + location.getLocationNo();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setInt(1, location.getLocationNo());
            pStmt.setString(2, location.getLocationName());
            pStmt.setString(3, location.getAddress1());
            pStmt.setString(4, location.getAddress2());
            pStmt.setString(5, location.getCity());
            pStmt.setString(6, location.getState());
            pStmt.setString(7, location.getZip());
            pStmt.setString(8, location.getPhone());
            pStmt.setString(9, location.getFax());
            pStmt.setString(10, location.getDirector());
            pStmt.setString(11, location.getNpi());
            pStmt.setString(12, location.getTaxId());
            pStmt.setString(13, location.getMedicareNo());
            pStmt.setBoolean(14, location.getActive());
            SQLUtil.SafeSetInteger(pStmt, 15, location.getDeactivatedBy());
            SQLUtil.SafeSetDate(pStmt, 16, location.getDeactivatedDate());

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

    public Locations GetLocation(int LocationNumber) throws SQLException
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
            Locations location = new Locations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `locationNo` = " + LocationNumber;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                setDetails(location, rs);
            }

            rs.close();
            stmt.close();

            return location;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    
    public Locations GetLocation(int LocationNumber, Boolean active) throws SQLException
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
            Locations location = new Locations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `locationNo` = " + LocationNumber
                    + "AND `active` = " + (active?1:0);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                setDetails(location, rs);
            }

            rs.close();
            stmt.close();

            return location;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Locations GetLocationById(int Id) throws SQLException
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
            Locations location = new Locations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idLocation` = " + Id;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                setDetails(location, rs);
            }

            rs.close();
            stmt.close();

            return location;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Locations GetLocationById(int Id, Boolean active) throws SQLException
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
            Locations location = new Locations();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idLocation` = " + Id
                    + "AND `active` = " + (active?1:0);

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                setDetails(location, rs);
            }

            rs.close();
            stmt.close();

            return location;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    public Locations[] GetAllLocations() throws SQLException
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
            ArrayList< Locations> locationList = new ArrayList< Locations>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table + " "
                    + "ORDER BY `locationNo` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                locationList.add(fromResultSet(rs));
            }

            rs.close();
            stmt.close();
            Locations[] locationsArray = new Locations[locationList.size()];
            locationsArray = locationList.toArray(locationsArray);
            return locationsArray;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public Locations[] GetAllLocations(boolean active) throws SQLException
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
            ArrayList< Locations> locationList = new ArrayList< Locations>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table 
                    + " WHERE `active` = " + (active?1:0)
                    + " ORDER BY `locationNo` ASC;";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                locationList.add(fromResultSet(rs));
            }

            rs.close();
            stmt.close();
            Locations[] locationsArray = new Locations[locationList.size()];
            locationsArray = locationList.toArray(locationsArray);
            return locationsArray;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public List<Locations> searchByName(String name)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (name == null)
        {
            throw new IllegalArgumentException("LocationDAO::searchByName:"
                    + " Recieved a [NULL] name argument");
        }
        
        name = name.replace("%","");
        name = "%" + name + "%";
        
        String sql = "SELECT * FROM " + table + " WHERE `locationName` LIKE ?" ;
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        
        List<Locations> locations = new ArrayList<>();
        while (rs.next())
        {
            locations.add(fromResultSet(rs));
        }
        pStmt.close();
        return locations;
    }
    
    public List<Locations> searchByName(String name, Boolean activeOnly)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (name == null)
        {
            throw new IllegalArgumentException("LocationDAO::searchByName:"
                    + " Recieved a [NULL] name argument");
        }
        
        name = name.replace("%","");
        name = "%" + name + "%";
        
        String sql = "SELECT * FROM " + table + " WHERE locationName LIKE ?";
        if (activeOnly)
        {
            sql += " AND `active`= 1";
        }
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        
        List<Locations> locations = new ArrayList<>();
        while (rs.next())
        {
            locations.add(fromResultSet(rs));
        }
        pStmt.close();
        return locations;
    }
    
    public static Locations fromResultSet(ResultSet rs) throws SQLException
    {
        Locations location = new Locations();
        location.setIdLocation(rs.getInt("idLocation"));
        location.setLocationNo(rs.getInt("locationNo"));
        location.setLocationName(rs.getString("locationName"));
        location.setAddress1(rs.getString("address1"));
        location.setAddress2(rs.getString("address2"));
        location.setCity(rs.getString("city"));
        location.setState(rs.getString("state"));
        location.setZip(rs.getString("zip"));
        location.setPhone(rs.getString("phone"));
        location.setFax(rs.getString("fax"));
        location.setDirector(rs.getString("director"));
        location.setNpi(rs.getString("npi"));
        location.setTaxId(rs.getString("taxId"));
        location.setMedicareNo(rs.getString("medicareNo"));
        location.setActive(rs.getBoolean("active"));
        location.setDeactivatedBy(rs.getInt("deactivatedBy"));
        location.setDeactivatedDate(rs.getDate("deactivatedDate"));
        return location;
    }
    
    
    private void setDetails(Locations location, ResultSet rs) throws SQLException{
                location.setIdLocation(rs.getInt("idLocation"));
                location.setLocationNo(rs.getInt("locationNo"));
                location.setLocationName(rs.getString("locationName"));
                location.setAddress1(rs.getString("address1"));
                location.setAddress2(rs.getString("address2"));
                location.setCity(rs.getString("city"));
                location.setState(rs.getString("state"));
                location.setZip(rs.getString("zip"));
                location.setPhone(rs.getString("phone"));
                location.setFax(rs.getString("fax"));
                location.setDirector(rs.getString("director"));
                location.setNpi(rs.getString("npi"));
                location.setTaxId(rs.getString("taxId"));
                location.setMedicareNo(rs.getString("medicareNo"));
                location.setActive(rs.getBoolean("active"));
                location.setDeactivatedBy(rs.getInt("deactivatedBy"));
                location.setDeactivatedDate(rs.getDate("deactivatedDate"));        
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
        String query = "SELECT `locations`.`idLocation`,\n"
                + "    `locations`.`locationNo`,\n"
                + "    `locations`.`locationName`,\n"
                + "    `locations`.`address1`,\n"
                + "    `locations`.`address2`,\n"
                + "    `locations`.`city`,\n"
                + "    `locations`.`state`,\n"
                + "    `locations`.`zip`,\n"
                + "    `locations`.`phone`,\n"
                + "    `locations`.`fax`,\n"
                + "    `locations`.`director`,\n"
                + "    `locations`.`npi`,\n"
                + "    `locations`.`taxId`,\n"
                + "    `locations`.`medicareNo`,\n"
                + "    `locations`.`active`,\n"
                + "    `locations`.`deactivatedBy`,\n"
                + "    `locations`.`deactivatedDate`\n"
                + "FROM `css`.`locations` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
