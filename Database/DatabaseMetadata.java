
package Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Supplies methods to retrieve database metadata for field type / length
 * checking within the application. Uses ResultSetMetaData instead of
 * accessing MySQL's information_schema directly.
 * 
 * @author TomR
 */
public final class DatabaseMetadata
{    
    /**
     * Provides the column type as a string (e.g. "VARCHAR")
     * column names. Null on error.
     * @param tableName Case-sensitive name of the table as it appears in the database
     * @param columnName Case-sensitive name of the column as it appears in the database
     * @return 
     */
    public static String getColumnSQLTypeName(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return null;
        }
        
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return null;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return null;        

        try
        {
            return rsmd.getColumnTypeName(columnIndex);
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to select from table" + tableName);
        }
        return null;
    }
    
    /**
     * Returns the java.sql.Types integer associated with this table/column
     * Null on error.
     * @param tableName Case-sensitive table name
     * @param columnName Case-sensitive column name
     * @return Integer representing java.sql.Types description
     */
    public static Integer getColumnSQLType(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return null;
        }
        
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return null;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return null;

        try
        {
            return rsmd.getColumnType(columnIndex);
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get column display size for " + 
                    tableName + "/" + columnIndex.toString());
        }
        return null;        
        
    }
    
    /**
     * Provides the character length of the supplied text-type column
     * (varchar, char, etc). Null on error or if a non text-based column supplied
     * @param tableName Case-sensitive database table name
     * @param columnName Case-sensitive database column name
     * @return Integer length; null on error or not text-based column
     */
    public static Integer getColumnCharacterLength(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return null;
        }
        
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return null;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return null;
        
        // This method should be used for text-type columns
        if (!isColumnTextType(getColumnSQLType(rsmd, columnIndex))) return null;

        try
        {
            return rsmd.getColumnDisplaySize(columnIndex);
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get column display size for " + 
                    tableName + "/" + columnIndex.toString());
        }
        return null;
    }
    
    /**
     * Gets the maximum precision size for the supplied numeric column.
     * Null if error or if non-numeric column supplied.
     * @param tableName Case-sensitive database table name
     * @param columnName Case-sensitive database column name
     * @return Integer length; null on error or not numeric-based column
     */
    public static Integer getNumericColumnMaxPrecision(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return null;
        }
        
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return null;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return null;

        // This method should be used for numeric-type columns
        if (!isColumnNumericType(getColumnSQLType(rsmd, columnIndex))) return null;        
        
        try
        {
            return rsmd.getPrecision(columnIndex);
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get column display size for " + 
                    tableName + "/" + columnIndex.toString());
        }
        return null;        
    }
    
    /**
     * Returns whether the supplied column is of a numeric type.
     * False on error
     * @param tableName Case-sensitive database table name
     * @param columnName Case-sensitive database column name
     * @return 
     */
    public static boolean isColumnNumericType(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return false;
        }
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return false;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return false;
        return isColumnNumericType(getColumnSQLType(rsmd, columnIndex));
        
    }
    
    /**
     * Returns whether the supplied column is of a numeric type.
     * False on error
     * @param tableName Case-sensitive database table name
     * @param columnName Case-sensitive database column name
     * @return 
     */    
    public static boolean isColumnTextType(String tableName, String columnName)
    {
        if (tableName == null || columnName == null || 
                tableName.length() == 0 || columnName.length() == 0)
        {
            return false;
        }
        ResultSetMetaData rsmd = getMetaData(tableName);
        if (rsmd == null) return false;
        Integer columnIndex = getColumnIndex(rsmd, columnName);
        if (columnIndex == null) return false;
        return isColumnTextType(getColumnSQLType(rsmd, columnIndex));        
    }

    /**
     * Retrieves the column index (1-based) for the supplied column name.
     * Null on any error (ResultSetMetaData doesn't provide this functionality)
     * @param rsmd ResultSetMetaData from a ResultSet
     * @param columnName Case-sensitive column name as it appears in the database
     * @return Integer of column index; null on error
     */
    public static Integer getColumnIndex(ResultSetMetaData rsmd, String columnName)
    {
        if (rsmd == null || columnName == null) return null;
        try
        {
            Integer columnCount = rsmd.getColumnCount();
            if (columnCount == 0) return null;
            for (int i = 1; i < columnCount; i++)
            {
                if (rsmd.getColumnName(i).equals(columnName)) return i;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get column index for " + 
                    columnName);
        }
        return null;
    }
    
    /**
     * Returns a ResulSetMetaData object by querying the supplied table
     * Returns null on any error.
     * @param tableName Case-sensitive table name to query
     * @return ResultSetMetaData; null on error
     */
    public static ResultSetMetaData getMetaData(String tableName)
    {
        if (tableName == null) return null;
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        ResultSetMetaData rsmd = null;
        try
        {
            String query = "SELECT * FROM `" + tableName + "` LIMIT 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rsmd = rs.getMetaData();
        }
        catch (SQLException ex)
        {
            
        }
        return rsmd;
    }
    
    /**
     * Whether the supplied java.sql.Types identifier matches one of the
     * numeric column types.
     * @param sqlType Integer java.sql.Types
     * @return boolean
     */
    public static boolean isColumnNumericType(Integer sqlType)
    {
        if (sqlType == null) return false;
        
        return (sqlType == java.sql.Types.BIGINT ||
                sqlType == java.sql.Types.DECIMAL ||
                sqlType == java.sql.Types.DOUBLE ||
                sqlType == java.sql.Types.FLOAT ||
                sqlType == java.sql.Types.INTEGER ||
                sqlType == java.sql.Types.NUMERIC ||
                sqlType == java.sql.Types.REAL ||
                sqlType == java.sql.Types.SMALLINT ||
                sqlType == java.sql.Types.TINYINT);
    }
    
    /**
     * Whether the supplied java.sql.Types identifier matches one of the
     * text column types.
     * @param sqlType Integer java.sql.Types
     * @return boolean
     */    
    public static boolean isColumnTextType(Integer sqlType)
    {
        if (sqlType == null) return false;
        
        return (sqlType == java.sql.Types.CHAR ||
                sqlType == java.sql.Types.LONGNVARCHAR ||
                sqlType == java.sql.Types.LONGVARCHAR ||
                sqlType == java.sql.Types.NCHAR ||
                sqlType == java.sql.Types.NVARCHAR ||
                sqlType == java.sql.Types.VARCHAR);
    }
    
    /**
     * Returns the java.sql.Types id of the supplied column.
     * @param rsmd ResultSetMetaData
     * @param columnIndex 1-based index of the column to 
     * @return 
     */
    public static Integer getColumnSQLType(ResultSetMetaData rsmd, int columnIndex)
    {
        if (rsmd == null) return null;
        try
        {
            return rsmd.getColumnType(columnIndex);
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get column SQL Type");
        }
        return null;          
    }    
    
}
