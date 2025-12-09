package DAOS;

import DOS.Setting;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SettingDAO
{
    public static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`settings`";

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,Setting)">
    public static Setting insert(Connection con, Setting obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingDAO::insert:"
                    + " Received a NULL Setting object");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingDAO:insert:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  settingTypeId,"
                + "  name,"
                + "  description,"
                + "  defaultValue,"
                + "  systemName,"
                + "  settingValueTypeId,"
                + "  isUserVisible,"
                + "  allowBlank,"
                + "  sortOrder"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingTypeId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDefaultValue());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingValueTypeId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getIsUserVisible());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isAllowBlank());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSortOrder());

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
                throw new NullPointerException("SettingDAO::insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSettings(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (Setting)">
    public static Setting insert(Setting obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingDAO::insert: Received a NULL Setting object");
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

    //<editor-fold defaultstate="collapsed" desc="update (Connection, Setting)">
    public static void update(Connection con, Setting obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingDAO::update: Received a NULL Setting object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingDAO:update: Received a [NULL] or invalid Connection object");
        }

        String sql = "UPDATE " + table + " SET "
                + " settingTypeId = ?,"
                + " name = ?,"
                + " description = ?,"
                + " defaultValue = ?,"
                //+ " systemName = ?," Should never change
                + " settingValueTypeId = ?,"
                + " isUserVisible = ?,"
                + " allowBlank = ?,"
                + " sortOrder = ?"
                + " WHERE idSettings = " + obj.getIdSettings();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingTypeId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDefaultValue());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingValueTypeId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getIsUserVisible());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isAllowBlank());
            pStmt.setInt(++i, obj.getSortOrder());
            
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

    //<editor-fold defaultstate="collapsed" desc="update (Setting)">
    public static void update(Setting obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingDAO::update:"
                    + " Received a NULL setting object.");
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
    public static Setting get(Connection con, Integer id, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingsDAO::get:"
                    + " Received a NULL or empty Setting object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingsDAO:get:"
                    + " Received a [NULL] or invalid Connection object");
        }

        String sql = "SELECT * FROM " + table + " WHERE idSettings = " + String.valueOf(id);
        if (forUpdate)
        {
            sql += " FOR UPDATE ";
        }

        Setting obj = null;

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
    public static Setting get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingsDAO::get:"
                    + " Received a NULL or empty Setting object.");
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
    public static Setting objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        Setting obj = new Setting();
        obj.setIdSettings(SQLUtil.getInteger(rs, "idSettings"));
        obj.setSettingTypeId(SQLUtil.getInteger(rs, "settingTypeId"));
        obj.setName(rs.getString("name"));
        obj.setDescription(rs.getString("description"));
        obj.setDefaultValue(rs.getString("defaultValue"));
        obj.setSystemName(rs.getString("systemName"));
        obj.setSettingValueTypeId(SQLUtil.getInteger(rs, "settingValueTypeId"));
        obj.setIsUserVisible(rs.getBoolean("isUserVisible"));
        obj.setAllowBlank(rs.getBoolean("allowBlank"));
        obj.setSortOrder(SQLUtil.getInteger(rs, "sortOrder"));

        return obj;
    }
    //</editor-fold>

}
