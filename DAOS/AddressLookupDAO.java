package DAOS;


import DAOS.IDAOS.IStructureCheckable;
import DOS.AddressLookup;
import Database.CheckDBConnection;
import Utility.AddressLookupDefinition;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic class for creating lookups that link Avalon objects (e.g. Location,
 *  Department) to Address objects.
 * 
 * The AddressLookupDefinition enum provides information about the lookup table.
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class AddressLookupDAO implements IStructureCheckable
{
    
    public static Integer insert(
            AddressLookup addressLookup,
            AddressLookupDefinition addressLookupDefinition)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (addressLookup == null)
        {
            throw new IllegalArgumentException("AddressLookupDAO::insert:"
                    + " Received a NULL AddressLookup object");
        }

        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressLookupDAO::insert:"
                    + " Received a NULL AddressLookupDefinition enum");            
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + addressLookupDefinition.tableName
                + "("
                + "  " + addressLookupDefinition.foreignKeyName + ","
                + "  addressId,"
                + "  addressTypeId,"
                + "  assigned,"
                + "  active,"
                + "  createdDate,"
                + "  createdById,"
                + "  modifiedDate,"
                + "  modifiedById"
                + ")"
                + "VALUES (?,?,?,?,?,NOW(),?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getObjectId());
        SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getAddressId());
        SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getAddressTypeId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, addressLookup.isAssigned());
        SQLUtil.SafeSetBoolean(pStmt, ++i, addressLookup.isActive());
        SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getCreatedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(addressLookup.getModifiedDate()));
        SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getModifiedById());

        Integer newId = null;
        try
        {
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("AddressLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return newId;
    }

    public static void update(AddressLookup addressLookup,
            AddressLookupDefinition addressLookupDefinition)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (addressLookup == null)
        {
            throw new IllegalArgumentException("AddressLookupDAO::update:"
                    + " Received a NULL AddressLookup object");
        }
        
        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("AddressLookupDAO::update:"
                    + " Received a NULL AddressLookupDefinition object");            
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + addressLookupDefinition.tableName + " SET "
                + " " + addressLookupDefinition.foreignKeyName + " = ?,"
                + " addressId = ?,"
                + " addressTypeId = ?,"
                + " assigned = ?,"
                + " active = ?,"
                + " createdById = ?,"
                + " modifiedDate = ?,"
                + " modifiedById = ?"
                + " WHERE " + addressLookupDefinition.uniqueIdName + " = " + addressLookup.getUniqueDatabaseId();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getObjectId());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getAddressId());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getAddressTypeId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, addressLookup.isAssigned());
            SQLUtil.SafeSetBoolean(pStmt, ++i, addressLookup.isActive());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getCreatedById());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(addressLookup.getModifiedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, addressLookup.getModifiedById());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }
    
    public static AddressLookup get(
            AddressLookupDefinition addressLookupDefinition,
            Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("LocationAddressesDAO::get:"
                    + " Received a NULL or empty identifier integer.");
        }

        if (addressLookupDefinition == null)
        {
            throw new IllegalArgumentException("LocationAddressesDAO::get:"
                    + " Received a NULL or empty AddressLookupDefinition object.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + addressLookupDefinition.tableName
                + " WHERE " + addressLookupDefinition.uniqueIdName + " = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        AddressLookup obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs, addressLookupDefinition);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }
 
    public static AddressLookup ObjectFromResultSet(ResultSet rs,
            AddressLookupDefinition addressLookupDefinition)
            throws SQLException, NullPointerException
    {
        AddressLookup obj = new AddressLookup();
        obj.setUniqueDatabaseId(rs.getInt(addressLookupDefinition.uniqueIdName));
        obj.setObjectId(rs.getInt(addressLookupDefinition.foreignKeyName));
        obj.setAddressId(rs.getInt("addressId"));
        obj.setAddressTypeId(rs.getInt("addressTypeId"));
        obj.setAssigned(rs.getBoolean("assigned"));
        obj.setActive(rs.getBoolean("active"));
        obj.setCreatedById(rs.getInt("createdById"));
        obj.setModifiedDate(rs.getDate("modifiedDate"));
        obj.setModifiedById(rs.getInt("modifiedById"));

        return obj;
    }

    @Override
    public String structureCheck() {
        
        // locationAddresses
        String locationAddresses = "SELECT `locationAddresses`.`idLocationAddresses`,\n"
                + "    `locationAddresses`.`locationId`,\n"
                + "    `locationAddresses`.`addressId`,\n"
                + "    `locationAddresses`.`addressTypeId`,\n"
                + "    `locationAddresses`.`assigned`,\n"
                + "    `locationAddresses`.`active`,\n"
                + "    `locationAddresses`.`createdDate`,\n"
                + "    `locationAddresses`.`createdById`,\n"
                + "    `locationAddresses`.`modifiedDate`,\n"
                + "    `locationAddresses`.`modifiedById`\n"
                + "FROM `css`.`locationAddresses` LIMIT 1;";
        
        // locationAddressLog
        String locationAddressLog = "SELECT `locationAddressLog`.`id`,\n"
                + "    `locationAddressLog`.`idLocationAddresses`,\n"
                + "    `locationAddressLog`.`action`,\n"
                + "    `locationAddressLog`.`field`,\n"
                + "    `locationAddressLog`.`preValue`,\n"
                + "    `locationAddressLog`.`postValue`,\n"
                + "    `locationAddressLog`.`description`,\n"
                + "    `locationAddressLog`.`performedByUserId`,\n"
                + "    `locationAddressLog`.`date`,\n"
                + "    `locationAddressLog`.`isUserVisible`\n"
                + "FROM `css`.`locationAddressLog` LIMIT 1;";
        
        
        // departmentAddresses
        String departmentAddresses = "SELECT `departmentAddresses`.`idDepartmentAddresses`,\n"
                + "    `departmentAddresses`.`departmentId`,\n"
                + "    `departmentAddresses`.`addressId`,\n"
                + "    `departmentAddresses`.`addressTypeId`,\n"
                + "    `departmentAddresses`.`assigned`,\n"
                + "    `departmentAddresses`.`active`,\n"
                + "    `departmentAddresses`.`createdDate`,\n"
                + "    `departmentAddresses`.`createdById`,\n"
                + "    `departmentAddresses`.`modifiedDate`,\n"
                + "    `departmentAddresses`.`modifiedById`\n"
                + "FROM `css`.`departmentAddresses` LIMIT 1;";
        
        
        // departmentAddressLog
        String departmentAddressLog = "SELECT `departmentAddressLog`.`id`,\n"
                + "    `departmentAddressLog`.`idDepartmentAddresses`,\n"
                + "    `departmentAddressLog`.`action`,\n"
                + "    `departmentAddressLog`.`field`,\n"
                + "    `departmentAddressLog`.`preValue`,\n"
                + "    `departmentAddressLog`.`postValue`,\n"
                + "    `departmentAddressLog`.`description`,\n"
                + "    `departmentAddressLog`.`performedByUserId`,\n"
                + "    `departmentAddressLog`.`date`,\n"
                + "    `departmentAddressLog`.`isUserVisible`\n"
                + "FROM `css`.`departmentAddressLog` LIMIT 1;";
        
        Connection con = Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        
        String result = "";
        String check1 = Database.DatabaseStructureCheck.structureCheck(locationAddresses, "locationAddresses", con);
        String check2 = Database.DatabaseStructureCheck.structureCheck(locationAddressLog, "locationAddressLog", con);
        String check3 = Database.DatabaseStructureCheck.structureCheck(departmentAddresses, "departmentAddresses", con);
        String check4 = Database.DatabaseStructureCheck.structureCheck(departmentAddressLog, "departmentAddressLog", con);
        String[] checks = {check1, check2, check3, check4};
        for (String check : checks) {
            if (check != null) {
                result += check + "\r\n";
            }
        }
        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }
}
