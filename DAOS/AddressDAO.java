package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Address;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO implements IStructureCheckable
{

    private static final String table = "`addresses`";

    public static Address insert(Address address)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (address == null)
        {
            throw new IllegalArgumentException("AddressDAO::Insert:"
                    + " Received a NULL Address object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  address1,"
                + "  address2,"
                + "  address3,"
                + "  cityId,"
                + "  stateId,"
                + "  regionId,"
                + "  zipCodeId,"
                + "  countryId,"
                + "  clia,"
                + "  cola,"
                + "  npi,"
                + "  taxId,"
                + "  director"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress1());
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress2());
        SQLUtil.SafeSetString(pStmt, ++i, address.getAddress3());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getCityId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getStateId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getRegionId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getZipCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, address.getCountryId());
        SQLUtil.SafeSetString(pStmt, ++i, address.getClia());
        SQLUtil.SafeSetString(pStmt, ++i, address.getCola());
        SQLUtil.SafeSetString(pStmt, ++i, address.getNpi());
        SQLUtil.SafeSetString(pStmt, ++i, address.getTaxId());
        SQLUtil.SafeSetString(pStmt, ++i, address.getDirector());

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
                throw new NullPointerException("AddressDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            address.setIdaddresses(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return address;
    }

    public static void update(Address address)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (address == null)
        {
            throw new IllegalArgumentException("AddressDAO::Update:"
                    + " Received a NULL Address object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " address1 = ?,"
                + " address2 = ?,"
                + " address3 = ?,"
                + " cityId = ?,"
                + " stateId = ?,"
                + " regionId = ?,"
                + " zipCodeId = ?,"
                + " countryId = ?,"
                + " clia = ?,"
                + " cola = ?,"
                + " npi = ?,"
                + " taxId = ?,"
                + " director = ?"
                + " WHERE idaddresses = " + address.getIdaddresses();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, address.getAddress1());
            SQLUtil.SafeSetString(pStmt, ++i, address.getAddress2());
            SQLUtil.SafeSetString(pStmt, ++i, address.getAddress3());
            SQLUtil.SafeSetInteger(pStmt, ++i, address.getCityId());
            SQLUtil.SafeSetInteger(pStmt, ++i, address.getStateId());
            SQLUtil.SafeSetInteger(pStmt, ++i, address.getRegionId());
            SQLUtil.SafeSetInteger(pStmt, ++i, address.getZipCodeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, address.getCountryId());
            SQLUtil.SafeSetString(pStmt, ++i, address.getClia());
            SQLUtil.SafeSetString(pStmt, ++i, address.getCola());
            SQLUtil.SafeSetString(pStmt, ++i, address.getNpi());
            SQLUtil.SafeSetString(pStmt, ++i, address.getTaxId());
            SQLUtil.SafeSetString(pStmt, ++i, address.getDirector());
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

    public static Address get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("AddressDAO::Get: Received a NULL or empty Address object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idaddresses = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Address address = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                address = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return address;
    }

    private static Address ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        Address address = new Address();
        address.setIdaddresses(rs.getInt("idaddresses"));
        address.setAddress1(rs.getString("address1"));
        address.setAddress2(rs.getString("address2"));
        address.setAddress3(rs.getString("address3"));
        
        address.setCityId(SQLUtil.NullIfZero(rs.getInt("cityId")));
        address.setStateId(SQLUtil.NullIfZero(rs.getInt("stateId")));
        address.setRegionId(SQLUtil.NullIfZero(rs.getInt("regionId")));
        address.setZipCodeId(SQLUtil.NullIfZero(rs.getInt("zipCodeId")));
        address.setCountryId(SQLUtil.NullIfZero(rs.getInt("countryId")));
        address.setClia(rs.getString("clia"));
        address.setCola(rs.getString("cola"));
        address.setNpi(rs.getString("npi"));
        address.setTaxId(rs.getString("taxId"));
        address.setDirector(rs.getString("director"));

        return address;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `addresses`.`idAddresses`,\n"
                + "    `addresses`.`address1`,\n"
                + "    `addresses`.`address2`,\n"
                + "    `addresses`.`address3`,\n"
                + "    `addresses`.`cityId`,\n"
                + "    `addresses`.`stateId`,\n"
                + "    `addresses`.`regionId`,\n"
                + "    `addresses`.`zipCodeId`,\n"
                + "    `addresses`.`countryId`,\n"
                + "    `addresses`.`clia`,\n"
                + "    `addresses`.`cola`,\n"
                + "    `addresses`.`npi`,\n"
                + "    `addresses`.`taxId`,\n"
                + "    `addresses`.`director`\n"
                + "FROM `css`.`addresses` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}