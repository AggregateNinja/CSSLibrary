package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ZipCode;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZipCodeDAO implements IStructureCheckable
{

    public static final String table = "`zipCodes`";

    public static ZipCode insert(ZipCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("ZipCodeDAO::Insert: Received a NULL ZipCode object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  code,"
                + "  extension,"
                + "  active"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getCode());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getExtension());
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
                throw new NullPointerException("ZipCodeDAO::Insert: "
                        + "Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdZipCodes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(ZipCode zipCode) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (zipCode == null)
        {
            throw new IllegalArgumentException("ZipCodeDAO::Update: Received a NULL ZipCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " code = ?,"
                + " extension = ?,"
                + " active = ?"
                + " WHERE idZipCodes = " + zipCode.getIdZipCodes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, zipCode.getCode());
            SQLUtil.SafeSetString(pStmt, ++i, zipCode.getExtension());
            SQLUtil.SafeSetInteger(pStmt, ++i, zipCode.getActive());
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

    public static ZipCode get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("ZipCodeDAO::Get: Received a NULL or empty ZipCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idZipCodes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        ZipCode obj = null;
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

    public static ZipCode ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        ZipCode zipCode = new ZipCode();
        zipCode.setIdZipCodes(rs.getInt("idZipCodes"));
        zipCode.setCode(rs.getString("code"));
        zipCode.setExtension(rs.getString("extension"));
        zipCode.setActive(rs.getInt("active"));

        return zipCode;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `zipCodes`.`idZipCodes`,\n"
                + "    `zipCodes`.`code`,\n"
                + "    `zipCodes`.`extension`,\n"
                + "    `zipCodes`.`active`\n"
                + "FROM `css`.`zipCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
