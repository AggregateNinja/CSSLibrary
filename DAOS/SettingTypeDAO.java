package DAOS;

import DOS.SettingType;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingTypeDAO
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`settingTypes`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,SettingTypes)">
    public static SettingType insert(Connection con, SettingType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingTypeDAO::insert:"
                    + " Received a NULL SettingType object");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingTypeDAO:insert:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  name,"
                + "  description,"
                + "  systemName"
                + ")"
                + "VALUES (?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());

            Integer newId = null;
            sql = pStmt.toString();
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("SettingTypeDAO::insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSettingTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (SettingTypes)">
    public static SettingType insert(SettingType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingTypeDAO::insert:"
                    + " Received a NULL SettingType object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, SettingTypes)">
    public static void update(Connection con, SettingType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingTypeDAO::update:"
                    + " Received a NULL SettingType object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingTypeDAO:update:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " description = ?,"
                + " systemName = ?"
                + " WHERE idSettingTypes = " + obj.getIdSettingTypes();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
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
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (SettingTypes)">
    public static void update(SettingType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingTypeDAO::update:"
                    + " Received a NULL SettingType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        update(con, obj);
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Connection, Integer (id), boolean (forUpdate))">
    public static SettingType get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingTypeDAO::get:"
                    + " Received a NULL or empty SettingType object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingTypeDAO:get:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idSettingTypes = " + String.valueOf(id);
        if (forUpdate)
        {
            sql += " FOR UPDATE ";
        }

        SettingType obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = objectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="get (Integer (id))">
    public static SettingType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingTypeDAO::get:"
                    + " Received a NULL or empty SettingType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static SettingType objectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        SettingType obj = new SettingType();
        obj.setIdSettingTypes(SQLUtil.getInteger(rs, "idSettingTypes"));
        obj.setName(rs.getString("name"));
        obj.setDescription(rs.getString("description"));
        obj.setSystemName(rs.getString("systemName"));

        return obj;
    }
    //</editor-fold>

}
