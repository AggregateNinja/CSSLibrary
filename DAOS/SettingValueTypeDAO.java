package DAOS;

import DOS.SettingValueType;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingValueTypeDAO
{

    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`settingValueTypes`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,SettingValueTypes)">
    public static SettingValueType insert(Connection con, SettingValueType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::insert:"
                    + " Received a NULL SettingValueTypes object");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingValueTypeDAO:insert:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  name,"
                + "  systemName"
                + ")"
                + "VALUES (?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
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
                throw new NullPointerException("SettingValueTypeDAO::insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSettingValueTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (SettingValueTypes)">
    public static SettingValueType insert(SettingValueType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::insert:"
                    + " Received a NULL SettingValueType object");
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

    //<editor-fold defaultstate="collapsed" desc="update (Connection, SettingValueTypes)">
    public static void update(Connection con, SettingValueType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::update:"
                    + " Received a NULL SettingValueType object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingValueTypeDAO:update:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " systemName = ?"
                + " WHERE idSettingValueTypes = " + obj.getIdSettingValueTypes();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
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

    //<editor-fold defaultstate="collapsed" desc="update (SettingValueTypes)">
    public static void update(SettingValueType obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::update:"
                    + "Received a NULL SettingValueType object.");
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
    public static SettingValueType get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::get:"
                    + " Received a NULL or empty SettingValueType object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingValueTypeDAO:get:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idSettingValueTypes = " + String.valueOf(id);
        if (forUpdate)
        {
            sql += " FOR UPDATE ";
        }

        SettingValueType obj = null;

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
    public static SettingValueType get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingValueTypeDAO::get:"
                    + "Received a NULL or empty SettingValueType object.");
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
    public static SettingValueType objectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        SettingValueType obj = new SettingValueType();
        obj.setIdSettingValueTypes(SQLUtil.getInteger(rs, "idSettingValueTypes"));
        obj.setName(rs.getString("name"));
        obj.setSystemName(rs.getString("systemName"));

        return obj;
    }
    //</editor-fold>

}
