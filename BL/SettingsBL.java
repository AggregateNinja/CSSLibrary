
package BL;

import BOS.SettingBO;
import DAOS.SettingDAO;
import DAOS.SettingLookupDAO;
import DAOS.SettingTypeDAO;
import DAOS.SettingValueTypeDAO;
import DOS.Setting;
import DOS.SettingLookup;
import DOS.SettingValueType;
import Database.DatabaseSingleton;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Supplies static methods to interact with system settings
 */
public class SettingsBL
{
    
    /**
     * Enum defines setting types and contains information about the lookup
     *  tables used to map settings to system objects.
     */
    public enum SettingTypeDefinition
    {
        // If a setting is system-wide, it won't have any data for a lookup table
        // -------------------------------------------------------------------------------------------------------------------------------------
        //          user-visible name   /   system name   /     lookup table         /          lookup table unique id   /    lookup identifier
        // -------------------------------------------------------------------------------------------------------------------------------------
        LIS_SYSTEM(     "LIS",              "lis_system",       null,                               null,                       null),
        BILLING_SYSTEM( "Billing",          "billing_system",   null,                               null,                       null),
        EMR(            "EMR",              "emr",              null,                               null,                       null),
        PAYOR(          "Payor",            "payor",            "css.billingPayorSettings",         "idBillingPayorSettings",   "billingPayorId"),
        LIS_ORDER(      "LIS Order",        "lis_order",        "css.orderSettings",                "idOrderSettings",          "orderId"),
        PATIENT(        "Patient",          "patient",          "css.patientSettings",              "idPatientSettings",        "patientId"),
        SUBSCRIBER(     "Subscriber",       "subscriber",       "css.subscriberSettings",           "idSubscriberSettings",     "subscriberId"),
        TEST(           "Test",             "test",             "css.testSettings",                 "idTestSettings",           "testId"),
        CLIENT(         "Client",           "client",           "css.clientSettings",               "idClientSettings",         "clientId"),
        INSURANCE(      "Insurance",        "insurance",        "css.insuranceSettings",            "idInsuranceSettings",      "insuranceId"),
        DOCTOR(         "Doctor",           "doctor",           "css.doctorSettings",               "idDoctorSettings",         "doctorId"),
        BILLING_ORDER(  "Billing Order",    "billing_order",    "cssbilling.detailOrderSettings",   "idDetailOrderSettings",    "detailOrderId"),
        LOCATION(       "Location",         "location",         "css.locationSettings",             "idLocationSettings",       "locationId"),
        INSTRUMENT(     "Instrument",       "instrument",       "css.instrumentSettings",           "idInstrumentSettings",     "instrumentId"),
        USER(           "User",             "user",             "css.userSettings",                 "idUserSettings",           "userId"),
        ROUTE(          "Route",            "route",            "css.routeSettings",                "idRouteSettings",          "routeId"),
        SALESMEN(       "Salesmen",         "salesmen",         "css.salesmenSettings",             "idSalesmenSettings",       "salesmenId"),
        EMRXREF(        "EMR Vendor",       "emrxref",          "css.emrXrefSettings",              "idEmrXrefSettings",        "emrXrefId");
        
        //--------------------------------
        // Add setting types as necessary
        //--------------------------------
        
        public final String displayName;
        public final String systemName;
        public final String lookupTableName;
        public final String lookupTableIdName;
        public final String fieldName;
        
        SettingTypeDefinition(
                String displayName,
                String systemName,
                String tableName,
                String tableIdName,
                String fieldName)
        {
            this.displayName = displayName;
            this.systemName = systemName;
            this.lookupTableName = tableName;
            this.lookupTableIdName = tableIdName;
            this.fieldName = fieldName;
        }
    }
    
    /**
     * Maps a setting value type systemName to a Java class type
     */
    public enum SettingValueTypeDefinition
    {
        BOOLEAN(Boolean.class,"boolean"),
        INTEGER(Integer.class,"integer"),
        STRING(String.class,"string"),
        DOUBLE(Double.class,"double"),
        DATE(Date.class,"date");
        
        public final Class javaClass;
        public final String systemName;
        
        SettingValueTypeDefinition(
                Class javaClass,
                String systemName)
        {
            this.javaClass = javaClass;
            this.systemName = systemName;
        }
        
        public static SettingValueTypeDefinition getBySystemName(String systemName)
        {
            if (systemName == null) return null;
            for (SettingValueTypeDefinition val : values())
            {
                if (val.systemName.equals(systemName)) return val;
            }
            return null;
        }
    }
    
    /**
     * Retrieves all settings of a type that is not associated with a particular
     *  system object, e.g. "Billing System" settings.
     * 
     * To get settings attached to system objects, use a method that takes
     *  a lookup identifier
     * s
     * @param settingTypeDefinition
     * @param userVisibleOnly
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<SettingBO> getSettings(
            SettingTypeDefinition settingTypeDefinition,
            boolean userVisibleOnly
        )
        throws IllegalArgumentException, NullPointerException, SQLException
    {
        Connection con = DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        return getSettings(con, false, settingTypeDefinition, userVisibleOnly);
    }
    
    /**
     * Retrieves all settings of a type that is not associated with
     *  a particular system object, e.g. "Billing System" settings
     * 
     * To get settings attached to system objects, use the method that takes
     *  a lookup identifier
     * 
     * @param con
     * @param forUpdate
     * @param settingTypeDefinition
     * @param userVisibleOnly
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static List<SettingBO> getSettings(
            Connection con,
            boolean forUpdate,
            SettingTypeDefinition settingTypeDefinition,
            boolean userVisibleOnly)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (settingTypeDefinition == null)
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a [NULL] SettingTypeDefinition enum argument");
        }
        
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a [NULL] or invalid Connection object argument");
        }
        
        // Ensure that this method is being called for a system-wide setting type
        if (settingTypeDefinition.lookupTableName != null)
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a SettingTypeDefinition associated with a lookup table,"
                    + " but did not call the method for lookup tables");
        }
        
        String sql = "SELECT s.idSettings, s.settingTypeId, s.settingValueTypeId"
                + " FROM settings s"
                + " INNER JOIN settingTypes st ON s.settingTypeId = st.idSettingTypes"
                + " WHERE st.systemName = ?";

        if (userVisibleOnly)
        {
            sql += " AND s.isUservisible = 1";
        }
        
        sql += " ORDER BY s.sortOrder ASC";
        
        if (forUpdate)
        {
            sql += " FOR UPDATE";
        }
       
        List<SettingBO> settings = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            pStmt.setString(1, settingTypeDefinition.systemName);
             
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                Setting setting = SettingDAO.get(con, SQLUtil.getInteger(rs, "idSettings"), forUpdate);
                DOS.SettingType settingType = SettingTypeDAO.get(con, SQLUtil.getInteger(rs, "settingTypeId"), forUpdate);
                SettingValueType settingValueType = SettingValueTypeDAO.get(con, SQLUtil.getInteger(rs, "settingValueTypeId"), forUpdate);
                SettingLookup settingLookup = null;
                settings.add(new SettingBO(settingType, settingValueType, setting, settingLookup, settingTypeDefinition));
            }
        }
        return settings;
    }
    
    /**
     * 
     * Returns a single active setting based on setting type, unique object
     *  identifier, and the system name of the setting itself.
     * 
     * Throws exception if more than one active setting is found
     * 
     * If no setting is found, returns [NULL] if retrieveUnpopulated is false,
     *  otherwise returns a SettingBO without a lookup DO.
     * 
     * Uses the database singleton's connection for the query. Should not be 
     *  used in a transaction if the returned setting is to be modified and
     *  updated.
     * 
     * @param settingTypeDefinition
     * @param lookupIdentifier
     * @param settingSystemName
     * @param retrieveUnpopulated
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static SettingBO getSetting(
            SettingTypeDefinition settingTypeDefinition,
            Integer lookupIdentifier,
            String settingSystemName,
            boolean retrieveUnpopulated)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        Connection con = DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        List<SettingBO> settings = getSettings(con, false, settingTypeDefinition,
                lookupIdentifier, settingSystemName, false, retrieveUnpopulated);
        
        if (settings.size() > 1)
        {
            throw new SQLException("SettingBL::getSetting: Returned more than one"
                    + " setting row for setting type=" + settingTypeDefinition.displayName
                    + " lookupId=" + lookupIdentifier.toString()
                    + " settingSystemName=" + settingSystemName);
        }
        SettingBO setting = null;
        if (settings.size() == 1)
        {
            setting = settings.get(0);
        }
        return setting;
    }
    
    /**
     * 
     * Returns a single active setting based on setting type, unique object
     *  identifier, and the system name of the setting itself.
     * 
     * Throws exception if more than one active setting is found
     * 
     * Returns [NULL] if no active setting is found.
     * 
     * Uses the database singleton's connection for the query. Should not be 
     *  used in a transaction if the returned setting is to be modified and
     *  updated.
     * 
     * @param settingTypeDefinition
     * @param lookupIdentifier
     * @param settingSystemName
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static SettingBO getSetting(
            SettingTypeDefinition settingTypeDefinition,
            Integer lookupIdentifier,
            String settingSystemName)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        Connection con = DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        List<SettingBO> settings = getSettings(con, false, settingTypeDefinition,
                lookupIdentifier, settingSystemName, false, false);
        
        if (settings.size() > 1)
        {
            throw new SQLException("SettingBL::getSetting: Returned more than one"
                    + " setting row for setting type=" + settingTypeDefinition.displayName
                    + " lookupId=" + lookupIdentifier.toString()
                    + " settingSystemName=" + settingSystemName);
        }
        SettingBO setting = null;
        if (settings.size() == 1)
        {
            setting = settings.get(0);
        }
        return setting;
    }
    
    /**
     * 
     * Returns a list of active settings for the supplied lookup Id, 
     *  setting type, and (optionally) setting name. For use with settings
     *  that are attached to an object, not "overall" system settings.
     * 
     * If showUnpopulated is true, this will also return rows for settings that
     *  do not currently have values for the provided object, and will create
     *  a placeholder setting lookup row with the default value set.
     * 
     * @param con
     * @param settingTypeDefinition The setting type definition
     * @param lookupIdentifier The unique identifier for the system object
     * @param forUpdate Whether to select in anticipation for updating data
     * @param settingSystemName (OPTIONAL) The setting system name string. If null returns all.
     * @param userVisibleOnly Whether to restrict by user visible
     * @param retrieveUnpopulated If true, returns empty rows for setting(s) that don't currently have data
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<SettingBO> getSettings(
            Connection con,
            boolean forUpdate,
            SettingTypeDefinition settingTypeDefinition,
            Integer lookupIdentifier,
            String settingSystemName,
            boolean userVisibleOnly,
            boolean retrieveUnpopulated)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (settingTypeDefinition == null)
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a [NULL] SettingTypeDefinition enum argument");
        }
        
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a [NULL] or invalid Connection object argument");
        }
        
        if (lookupIdentifier == null || lookupIdentifier <=0)
        {
            throw new IllegalArgumentException("SettingsBL::getSetting: Received"
                    + " a [NULL] or invalid unique identifier for setting type "
                    + settingTypeDefinition.systemName + ". The method called requires a"
                    + " unique identifier for the lookup table. If you're trying"
                    + " to retrieve a setting that is not associated with a"
                    + " particular row, use the method that does not require an"
                    + " identifier argument");
        }
        
        // Get all settings for this type, whether they have a value populated or not
        String sql = "SELECT s.idSettings, s.settingTypeId, s.settingValueTypeId, lt." + settingTypeDefinition.lookupTableIdName + " AS lookupId"
                + " FROM settings s"
                + " INNER JOIN settingTypes st ON s.settingTypeId = st.idSettingTypes"
                + " LEFT JOIN " + settingTypeDefinition.lookupTableName + " lt ON lt.settingId = s.idSettings";
        
        // Include rows even if the object doesn't currently have data for that setting
        if (retrieveUnpopulated)
        {
            sql += " AND lt." + settingTypeDefinition.fieldName + " = ? AND lt.active = 1 WHERE 1=1";
        }
        else // Only show saved setting rows
        {
            sql += " WHERE lt." + settingTypeDefinition.fieldName + " = ? AND lt.active = 1";
        }
        
        sql += " AND st.systemName = ?";

        if (userVisibleOnly)
        {
            sql += " AND s.isUservisible = 1";
        }
        
        if (settingSystemName != null && settingSystemName.isEmpty() == false)
        {
            sql += " AND s.systemName = ?";
        }
        
        sql += " ORDER BY s.sortOrder ASC";
        
        if (forUpdate)
        {
            sql += " FOR UPDATE";
        }
       
        List<SettingBO> settings = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            pStmt.setInt(1, lookupIdentifier);
            pStmt.setString(2, settingTypeDefinition.systemName);
            
            if (settingSystemName != null && settingSystemName.isEmpty() == false)
            {
                pStmt.setString(3, settingSystemName);
            }
            
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                Setting setting = SettingDAO.get(con, SQLUtil.getInteger(rs, "idSettings"), forUpdate);
                DOS.SettingType settingType = SettingTypeDAO.get(con, SQLUtil.getInteger(rs, "settingTypeId"), forUpdate);
                SettingValueType settingValueType = SettingValueTypeDAO.get(con, SQLUtil.getInteger(rs, "settingValueTypeId"), forUpdate);
                SettingLookup settingLookup = null;
                Integer lookupId = SQLUtil.getInteger(rs, "lookupId");
                
                // Load the current object if it exists
                if (lookupId != null)
                {
                    settingLookup = SettingLookupDAO.get(con, lookupId, settingTypeDefinition, forUpdate);
                }
                // Set up a lookup row with the default value set:
                else if (retrieveUnpopulated)
                {
                    settingLookup = new SettingLookup(settingTypeDefinition);
                    settingLookup.setLookupId(lookupIdentifier);
                    settingLookup.setSettingId(setting.getIdSettings());
                    settingLookup.setValue(setting.getDefaultValue());
                    settingLookup.setActive(true);
                }
                
                settings.add(new SettingBO(settingType, settingValueType, setting, settingLookup, settingTypeDefinition));
            }
        }
        return settings;
    }
    
    /**
     * Saves (insert/updates) setting values. If a system-wide setting is being
     *  saved, the setting's defaultValue will be updated. If a lookup setting
     *  is being saved, the lookup row will be inserted/updated.
     * 
     * Does not perform logging currently!
     * 
     * @param setting
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static SettingBO save(
        SettingBO setting,
        int userId)
        throws SQLException, NullPointerException, IllegalArgumentException
    {
        Connection con = DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        return save(con, setting, userId);
    }
    
    
    /**
     * Saves (insert/updates) setting values. If a system-wide setting is being
     *  saved, the setting's defaultValue will be updated. If a lookup setting
     *  is being saved, the lookup row will be inserted/updated.
     * 
     * Does not perform logging currently!
     * 
     * @param con
     * @param setting
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static SettingBO save(
            Connection con,
            SettingBO setting,
            int userId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (setting == null)
        {
            throw new IllegalArgumentException("SettingsBL::save:"
                    + " Received a [NULL] SettingBO object argument");
        }
        
        // If the setting is system-wide, update the "default value" on the
        // setting row itself; that is the actual setting
        if (setting.isSystemWide())
        {
            SettingDAO.update(con, setting.getSetting());
        }
        else // This is a setting on a lookup
        {
            SettingLookup settingLookup = setting.getSettingLookup();
            if (settingLookup == null)
            {
                throw new IllegalArgumentException("SettingBL::save:"
                        + " Call to save a setting for a lookup row, but"
                        + " the provided SettingLookup object was [NULL]!");
            }
            
            // Insert if new
            if (settingLookup.getId() == null || settingLookup.getId().equals(0))
            {
                settingLookup.setCreatedById(userId);
                settingLookup = SettingLookupDAO.insert(con, settingLookup);
                
                // Return a setting BO object to the caller that contains the
                // new lookup row with its new unique database identifier
                setting = new SettingBO(
                        setting.getSettingType(),
                        setting.getSettingValueType(),
                        setting.getSetting(),settingLookup,
                        settingLookup.getSettingTypeDefinition());
            }
            else // Setting already exits; update
            {
                settingLookup.setUpdatedById(userId);
                SettingLookupDAO.update(con, settingLookup);
            }
        }
        return setting;
    }
}
