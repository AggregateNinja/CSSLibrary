package DAOS;

import BL.SettingsBL.SettingTypeDefinition;
import DOS.SettingLookup;
import Database.CheckDBConnection;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Can change any setting lookup row. SettingLookup defines which table..
 */
public class SettingLookupDAO
{

    //<editor-fold defaultstate="collapsed" desc="insert (Connection,BillingPayorSettings)">
    public static SettingLookup insert(
            Connection con,
            SettingLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingLookupDAO::insert:"
                    + " Received a NULL BillingPayorSettings object");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingLookupDAO:insert:"
                    + " Received a [NULL] or invalid Connection object");
        }

        SettingTypeDefinition lookupDefinition = obj.getSettingTypeDefinition();
        
        if (lookupDefinition == null)
        {
            throw new IllegalArgumentException("SettingLookupDAO::insert:"
                    + " Generic SettingLookup DO argument had a [NULL] SettingTypeDefinition enum member."
                    + " This is necessary to identify the generic type.");
        }
                

        String sql = "INSERT INTO " + lookupDefinition.lookupTableName
                + "("
                + "  " + lookupDefinition.fieldName + ","
                + "  settingId,"
                + "  value,"
                + "  active,"
                + "  created,"
                + "  createdById,"
                + "  updated,"
                + "  updatedById"
                + ")"
                + "VALUES (?,?,?,?,NOW(),?,?,?)";

        try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getLookupId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getValue());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCreatedById());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getUpdated()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUpdatedById());

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
                throw new NullPointerException("SettingLookupDAO::insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setId(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw ex;
        }

        return obj;
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="insert (BillingPayorSettings)">
    public static SettingLookup insert(SettingLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return insert(con, obj);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="update (Connection, BillingPayorSettings)">
    public static void update(Connection con, SettingLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SettingLookupDAO::update:"
                    + " Received a [NULL] SettingLookup object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingLookupDAO:update:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        SettingTypeDefinition lookupDefinition = obj.getSettingTypeDefinition();
        
        if (lookupDefinition == null)
        {
            throw new IllegalArgumentException("SettingLookupDAO::update:"
                    + " Generic SettingLookup DO argument had a [NULL] SettingTypeDefinition enum member."
                    + " This is necessary to identify the generic type.");
        }

        String sql = "UPDATE " + lookupDefinition.lookupTableName + " SET "
                + " " + lookupDefinition.fieldName + " = ?,"
                + " settingId = ?,"
                + " value = ?,"
                + " active = ?,"
                + " updated = NOW(),"
                + " updatedById = ?"
                + " WHERE " + lookupDefinition.lookupTableIdName + " = " + obj.getId();

        String sqlOutput = "";
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getLookupId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSettingId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getValue());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUpdatedById());
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

    //<editor-fold defaultstate="collapsed" desc="update (BillingPayorSettings)">
    public static void update(SettingLookup obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
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
    public static SettingLookup get(Connection con, Integer id, 
            SettingTypeDefinition settingTypeDefinition, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingLookupDAO::get:"
                    + " Received a [NULL] or invalid Integer id object.");
        }

        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingLookupDAO:get:"
                    + " Received a [NULL] or invalid Connection object");
        }

        if (settingTypeDefinition == null)
        {
            throw new IllegalArgumentException("SettingLookupDAO::get:"
                    + " Received a [NULL] SettingTypeDefinition enum");
        }

        String sql = "SELECT * FROM " + settingTypeDefinition.lookupTableName
                + " WHERE " + settingTypeDefinition.lookupTableIdName + " = " + String.valueOf(id);
        
        if (forUpdate)
        {
            sql += " FOR UPDATE ";
        }

        SettingLookup obj = null;

        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {

            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = objectFromResultSet(rs, settingTypeDefinition);
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
    public static SettingLookup get(Integer id, SettingTypeDefinition settingTypeDefinition )
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SettingLookupDAO::get:"
                    + " Received a NULL or empty SettingTypeDefinition object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        return get(con, id, settingTypeDefinition, false); // not 'for update'
    }
	//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
    public static SettingLookup objectFromResultSet(ResultSet rs,
            SettingTypeDefinition settingLookupDefinition)
            throws SQLException, NullPointerException
    {
        SettingLookup obj = new SettingLookup(settingLookupDefinition);
        obj.setId(SQLUtil.getInteger(rs, settingLookupDefinition.lookupTableIdName));
        obj.setLookupId(SQLUtil.getInteger(rs, settingLookupDefinition.fieldName));
        obj.setSettingId(SQLUtil.getInteger(rs, "settingId"));
        obj.setValue(rs.getString("value"));
        obj.setActive(rs.getBoolean("active"));
        obj.setCreatedById(SQLUtil.getInteger(rs, "createdById"));
        obj.setUpdated(rs.getDate("updated"));
        obj.setUpdatedById(SQLUtil.getInteger(rs, "updatedById"));

        return obj;
    }
    //</editor-fold>

}
