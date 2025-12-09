package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AddressType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressTypeDAO implements IStructureCheckable
{

    public static final String table = "`addressTypes`";

    public static AddressType insert(AddressType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AddressTypeDAO::Insert: Received a NULL AddressTypes object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  name,"
                + "  isMailing,"
                + "  isPhysical,"
                + "  description,"
                + "  active"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isMailing());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isMailing());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getActive());

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
                throw new NullPointerException("AddressTypeDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdaddressTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(AddressType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AddressTypeDAO::Update: Received a NULL AddressTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " isMailing = ?,"
                + " isPhysical = ?,"
                + " description = ?,"
                + " active = ?"
                + " WHERE idaddressTypes = " + obj.getIdaddressTypes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isMailing());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isPhysical());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getActive());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }
    
    public static AddressType get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("AddressTypeDAO::Get: Received a NULL or empty AddressTypes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idaddressTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        AddressType obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
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

    public static AddressType ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        AddressType obj = new AddressType();
        obj.setIdaddressTypes(rs.getInt("idaddressTypes"));
        obj.setName(rs.getString("name"));
        obj.setIsMailing(rs.getBoolean("isMailing"));
        obj.setIsPhysical(rs.getBoolean("isPhysical"));
        obj.setDescription(rs.getString("description"));
        obj.setActive(rs.getInt("active"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `addressTypes`.`idAddressTypes`,\n"
                + "    `addressTypes`.`name`,\n"
                + "    `addressTypes`.`isMailing`,\n"
                + "    `addressTypes`.`isPhysical`,\n"
                + "    `addressTypes`.`description`,\n"
                + "    `addressTypes`.`active`\n"
                + "FROM `css`.`addressTypes` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
