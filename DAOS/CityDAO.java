package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.City;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityDAO implements IStructureCheckable
{

    public static final String table = "`cities`";

    public static City insert(City city) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (city == null)
        {
            throw new IllegalArgumentException("CityDAO::Insert: Received a NULL City object");
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
                + "  abbr,"
                + "  active"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, city.getName());
        SQLUtil.SafeSetString(pStmt, ++i, city.getAbbr());
        SQLUtil.SafeSetInteger(pStmt, ++i, city.getActive());

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
                throw new NullPointerException("CityDAO::Insert: Cannot retrieve"
                        + " generated identifier for inserted row.");
            }
            city.setIdCities(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return city;
    }

    public static void update(City city)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (city == null)
        {
            throw new IllegalArgumentException("CityDAO::Update: Received a NULL City object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " abbr = ?,"
                + " active = ?"
                + " WHERE idCities = " + city.getIdCities();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, city.getName());
            SQLUtil.SafeSetString(pStmt, ++i, city.getAbbr());
            SQLUtil.SafeSetInteger(pStmt, ++i, city.getActive());
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

    public static City get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("CityDAO::Get: Received a NULL or empty City object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idCities = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        City obj = null;
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

    public static City ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        City city = new City();
        city.setIdCities(rs.getInt("idCities"));
        city.setName(rs.getString("name"));
        city.setAbbr(rs.getString("abbr"));
        city.setActive(rs.getInt("active"));

        return city;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `cities`.`idCities`,\n"
                + "    `cities`.`name`,\n"
                + "    `cities`.`abbr`,\n"
                + "    `cities`.`active`\n"
                + "FROM `css`.`cities` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
