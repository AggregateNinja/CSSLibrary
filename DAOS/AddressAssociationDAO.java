package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AddressAssociation;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Address associations provide search suggestions for addresses, tying together
 *  city, state, zip, region, country information that has already been entered
 *  into the system.
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class AddressAssociationDAO implements IStructureCheckable
{

    public static final String table = "`addressAssociations`";

    public static AddressAssociation insert(AddressAssociation obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AddressAssociationDAO::Insert:"
                    + " Received a NULL AddressAssociation object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  cityId,"
                + "  stateId,"
                + "  zipCodeId,"
                + "  regionId,"
                + "  countryId,"
                + "  active,"
                + "  created,"
                + "  createdById,"
                + "  lastModified,"
                + "  lastModifiedById"
                + ")"
                + "VALUES (?,?,?,?,?,?,NOW(),?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCityId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getStateId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getZipCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRegionId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCountryId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedById());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getLastModified()));
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getLastModifiedById());

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("AddressAssociationDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdaddressAssociations(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(AddressAssociation addressAssociation)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (addressAssociation == null)
        {
            throw new IllegalArgumentException("AddressAssociationDAO::Update:"
                    + " Received a NULL AddressAssociation object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " cityId = ?,"
                + " stateId = ?,"
                + " zipCodeId = ?,"
                + " regionId = ?,"
                + " countryId = ?,"
                + " active = ?,"
                + " createdById = ?,"
                + " lastModified = ?,"
                + " lastModifiedById = ?"
                + " WHERE idaddressAssociations = " + addressAssociation.getIdaddressAssociations();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getCityId());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getStateId());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getZipCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getRegionId());            
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getCountryId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, addressAssociation.isActive());
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getCreatedById());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(addressAssociation.getLastModified()));
            SQLUtil.SafeSetInteger(pStmt, ++i, addressAssociation.getLastModifiedById());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static AddressAssociation get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("AddressAssociationDAO::Get:"
                    + " Received a NULL or empty AddressAssociation object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idaddressAssociations = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        AddressAssociation addressAssociation = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                addressAssociation = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return addressAssociation;
    }

    public static AddressAssociation ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        AddressAssociation addressAssociation = new AddressAssociation();
        addressAssociation.setIdaddressAssociations(rs.getInt("idaddressAssociations"));
        addressAssociation.setCityId(SQLUtil.NullIfZero(rs.getInt("cityId")));
        addressAssociation.setStateId(SQLUtil.NullIfZero(rs.getInt("stateId")));
        addressAssociation.setZipCodeId(SQLUtil.NullIfZero(rs.getInt("zipCodeId")));
        addressAssociation.setRegionId(SQLUtil.NullIfZero(rs.getInt("regionId")));
        addressAssociation.setCountryId(SQLUtil.NullIfZero(rs.getInt("countryId")));        
        addressAssociation.setActive(rs.getBoolean("active"));
        addressAssociation.setCreatedById(rs.getInt("createdById"));
        addressAssociation.setLastModified(rs.getDate("lastModified"));
        addressAssociation.setLastModifiedById(rs.getInt("lastModifiedById"));

        return addressAssociation;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `addressAssociations`.`idAddressAssociations`,\n"
                + "    `addressAssociations`.`cityId`,\n"
                + "    `addressAssociations`.`stateId`,\n"
                + "    `addressAssociations`.`regionId`,\n"
                + "    `addressAssociations`.`zipCodeId`,\n"
                + "    `addressAssociations`.`countryId`,\n"
                + "    `addressAssociations`.`active`,\n"
                + "    `addressAssociations`.`created`,\n"
                + "    `addressAssociations`.`createdById`,\n"
                + "    `addressAssociations`.`lastModified`,\n"
                + "    `addressAssociations`.`lastModifiedById`\n"
                + "FROM `css`.`addressAssociations` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
        
    }
}
