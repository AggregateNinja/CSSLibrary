package Utility;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.joda.time.DateTime;
import java.sql.ResultSet;

/**
 * @date: Jul 17, 2013
 * @author: Keith Maggio <keith@csslis.com>, Derrick Piper <derrick@csslis.com>
 *
 * @project:
 * @package: Utility
 * @file name: SQLUtil.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class SQLUtil
{

    public static void SafeSetInteger(PreparedStatement pStmt, int Index, Integer value) throws SQLException
    {
        if (value == null || value == 0)
        {
            pStmt.setNull(Index, java.sql.Types.INTEGER);
        }
        else
        {
            pStmt.setInt(Index, value);
        }
    }

    public static void SafeSetRangeInteger(PreparedStatement pStmt, int Index, Integer value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.INTEGER);
        }
        else
        {
            pStmt.setInt(Index, value);
        }
    }

    public static void SafeSetString(PreparedStatement pStmt, int Index, String value) throws SQLException
    {
        if (value == null || value.isEmpty())
        {
            pStmt.setNull(Index, java.sql.Types.VARCHAR);
        }
        else
        {
            pStmt.setString(Index, value);
        }
    }

    public static void SafeSetDate(PreparedStatement pStmt, int Index, java.util.Date value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.DATE);
        }
        else
        {
            pStmt.setDate(Index, Convert.ToSQLDate(value));
        }
    }

    public static void SafeSetDate(PreparedStatement pStmt, int Index, java.sql.Date value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.DATE);
        }
        else
        {
            pStmt.setDate(Index, Convert.ToSQLDate(value));
        }
    }

    public static void SafeSetLong(PreparedStatement pStmt, int Index, Long value) throws SQLException
    {
        if (value == null || value == 0)
        {
            pStmt.setNull(Index, java.sql.Types.INTEGER);
        }
        else
        {
            pStmt.setLong(Index, value);
        }
    }

    public static void SafeSetLong(PreparedStatement pStmt, int Index, Integer value) throws SQLException
    {
        if (value == null || value == 0)
        {
            pStmt.setNull(Index, java.sql.Types.INTEGER);
        }
        else
        {
            pStmt.setLong(Index, value);
        }
    }

    public static void SafeSetTimeStamp(PreparedStatement pStmt, int Index, java.util.Date value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.TIMESTAMP);
        }
        else
        {
            pStmt.setTimestamp(Index, Convert.ToSQLDateTime(value));
        }
    }

    public static void SafeSetTimeStamp(PreparedStatement pStmt, int Index, java.sql.Date value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.TIMESTAMP);
        }
        else
        {
            pStmt.setTimestamp(Index, Convert.ToSQLDateTime(value));
        }
    }

    public static void SafeSetDouble(PreparedStatement pStmt, int Index, Double value) throws SQLException
    {
        if (value == null || value == 0)
        {
            pStmt.setNull(Index, java.sql.Types.DOUBLE);
        }
        else
        {
            pStmt.setDouble(Index, value);
        }
    }

    public static void SafeSetBigDecimal(PreparedStatement pStmt, int Index, BigDecimal value) throws SQLException
    {
        if (value == null || value.doubleValue() == 0)
        {
            pStmt.setNull(Index, java.sql.Types.DOUBLE);
        }
        else
        {
            pStmt.setBigDecimal(Index, value);
        }
    }

    public static void SafeSetRangeDouble(PreparedStatement pStmt, int Index, Double value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.DOUBLE);
        }
        else
        {
            pStmt.setDouble(Index, value);
        }
    }

    public static void SafeSetBoolean(PreparedStatement pStmt, int Index, Boolean value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.BIT);
        }
        else
        {
            pStmt.setBoolean(Index, value);
        }
    }

    public static void SafeSetBoolean(PreparedStatement pStmt, int Index, Boolean value, boolean defaultValue) throws SQLException
    {
        if (value == null)
        {
            pStmt.setBoolean(Index, defaultValue);
        }
        else
        {
            pStmt.setBoolean(Index, value);
        }
    }

    public static void SafeSetBytes(PreparedStatement pStmt, int Index, byte[] value) throws SQLException
    {
        if (value == null)
        {
            pStmt.setNull(Index, java.sql.Types.BLOB);
        }
        else
        {
            pStmt.setBytes(Index, value);
        }
    }
    
    //--------------------------------------------------------------------------
    // Note: our current sql driver allows automatic conversion to/from
    //   java.lang.Boolean and MySQL TinyInt:
    //      "if the configuration property tinyInt1isBit is set to true
    //      (the default) and the storage size is 1, or java.lang.Integer if not."
    //
    // To be more future-proof (e.g. if we change out our driver, or if that default
    // setting changes in a future release), static methods are provided to convert
    // to/from java.lang.Boolean and MySQL TinyInt before it gets to the driver level.
    
    
    /**
     * If the boxed Boolean is [NULL], sets the integer field to [NULL]
     * If it is true, sets the integer field to 1
     * If it is false, sets the integer field to 0
     * 
     * Call BooleanToInt with a default value set if you wish to always
     *  set either true or false (e.g. if there's an integer field that
     *  cannot be null and you'd like to make a [NULL] boolean to be set to 0)
     * 
     * @param pStmt
     * @param Index
     * @param value
     * @throws SQLException 
     */
    public static void SafeSetBooleanToInt(PreparedStatement pStmt, int Index,
            Boolean value) throws SQLException
    {
        if (value == null || value == false)
        {
            pStmt.setInt(Index, 0);
        }
        else
        {
            pStmt.setInt(Index, 1);
        }
    }
    
    /**
     * Writes a Java boolean into an int field. If the provided value is [NULL],
     *  the default value is used.
     * @param pStmt
     * @param Index
     * @param value
     * @param defaultValue
     * @throws SQLException 
     */
    public static void BooleanToInt(PreparedStatement pStmt, int Index,
            Boolean value, boolean defaultValue) throws SQLException
    {
        if (value == null)
        {
            value = defaultValue;
        }
        
        if (value)
        {
            pStmt.setInt(Index, 1);
        }
        else
        {
            pStmt.setInt(Index, 0);
        }
    }

    public static java.sql.Timestamp getMaximumTimestamp()
    {
        // Timestamp max
        DateTime max = new DateTime().withYear(2038).withMonthOfYear(1).withDayOfMonth(19).withHourOfDay(3).withMinuteOfHour(14).withSecondOfMinute(7);
        return new java.sql.Timestamp(max.toDate().getTime());
    }

    /**
     * If value is zero (0), returns a NULL Integer object, otherwise returns
     * the int value.
     *
     * Since MySQLConnector returns a primitive integer value from getInt(),
     * this can help prevent having to check for NULL and zero on DO getters.
     *
     * @param value
     * @return
     */
    public static Integer NullIfZero(int value)
    {
        if (value == 0)
        {
            return null;
        }
        return new Integer(value);
    }
    
    /**
     * Static replacement for JDBC's RecordSet.getInt() method that will return
     *  NULL if the database integer value is NULL instead of 0. 
     * 
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException 
     */
    public static Integer getInteger(ResultSet rs, String columnName)
            throws SQLException
    {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }

    /**
     * Helper Method for creating a PreparedStatement with String parameters. Used in fixing SQL Injection problems.
     * 
     * Can reduce code that looks like this:
     * stmt.setString(1, s);
     * stmt.setString(2, s2);
     * stmt.setString(3, s3); ...
     * 
     * To this:
     * createStatement(con, s, s2, s3);
     * 
     * @param con Connection used to create statement.
     * @param query The sql query string. Should contain "?" wildcards.
     * @param params A variable length set of strings.
     * @throws SQLException
     */
    public static PreparedStatement createStatement(Connection con, String query, String... params) throws SQLException
    {
        PreparedStatement pStmt = con.prepareStatement(query);
        for(int i = 0; i < params.length; i++)
        {
            SafeSetString(pStmt, i+1, params[i]);
        }
        return pStmt;
    }
    
    public static String createSearchParam(String rawParam)
    {
        String cleanParam = rawParam.replace("%", "");
        return cleanParam + "%";
    }
    
    public static String createSearchParam2Way(String rawParam)
    {
        String cleanParam = rawParam.replace("%", "");
        return "%" + cleanParam + "%";
    }
    
    
}
