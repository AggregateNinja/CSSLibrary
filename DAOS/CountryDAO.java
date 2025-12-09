package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Country;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryDAO implements IStructureCheckable
{

    public static final String table = "`countries`";

    public static Country insert(Country country)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (country == null)
        {
            throw new IllegalArgumentException("CountryDAO::Insert: Received a NULL Country object");
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
        SQLUtil.SafeSetString(pStmt, ++i, country.getName());
        SQLUtil.SafeSetString(pStmt, ++i, country.getAbbr());
        SQLUtil.SafeSetInteger(pStmt, ++i, country.getActive());

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
                throw new NullPointerException("CountryDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            country.setIdCountries(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return country;
    }

    public static void update(Country country) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (country == null)
        {
            throw new IllegalArgumentException("CountryDAO::Update: Received a NULL Country object.");
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
                + " WHERE idCountries = " + country.getIdCountries();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, country.getName());
            SQLUtil.SafeSetString(pStmt, ++i, country.getAbbr());
            SQLUtil.SafeSetInteger(pStmt, ++i, country.getActive());
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

    public static Country getByAbbr(String abbr)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (abbr == null || abbr.isEmpty())
        {
            throw new IllegalArgumentException("CountryDAO:getByAbbr: Received a [NULL] or empty country abbreviation argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);        
        
        String sql = "SELECT * FROM " + table + " WHERE abbr = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, abbr);
        
        Country country = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                country = CountryDAO.ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw new SQLException(ex.getMessage() + " " + pStmt.toString());            
        }
        pStmt.close();
        return country;
    }
    
    public static Country get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("CountryDAO::get: Received a NULL or empty Country object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE idCountries = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Country obj = null;
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

    public static Country ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        Country country = new Country();
        country.setIdCountries(rs.getInt("idCountries"));
        country.setName(rs.getString("name"));
        country.setAbbr(rs.getString("abbr"));
        country.setActive(rs.getInt("active"));

        return country;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `countries`.`idCountries`,\n"
                + "    `countries`.`name`,\n"
                + "    `countries`.`abbr`,\n"
                + "    `countries`.`active`\n"
                + "FROM `css`.`countries` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
