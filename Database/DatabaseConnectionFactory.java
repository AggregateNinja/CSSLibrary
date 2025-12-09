
package Database;

import Utility.ExceptionUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *  
 * RMIs using transactions will need to obtain isolated connections instead
 *  of using the singleton (can't have non-transactional queries being run
 *  within transactional queries).
 * 
 * Any database calls should really be made from a server-side program, but
 *  we are where we are.
 *  
 */
public class DatabaseConnectionFactory
{
    /**
     * Gets a new connection using the provided database properties.
     * NOTE: Additional properties is not currently being used
     * @param properties
     * @param additionalProperties
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static Connection getNewConnection(DatabaseProperties properties, HashMap<Object, Object> additionalProperties)
            throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        if (properties == null)
        {
            throw new IllegalArgumentException("DatabaseConnectionFactory::getConnection:"
                    + " Received a [NULL] DatabaseProperties argument");
        }
        
        if (properties.dbDriverClass == null || properties.dbDriverClass.isEmpty())
        {
            throw new IllegalArgumentException("DatabaseConnectionFactory::getConnection:"
                    + " DatabaseProperties argument had a [NULL] or empty dbDriverClass!");
        }
        
        try
        {
            Class.forName(properties.dbDriverClass);
        }
        catch (ClassNotFoundException ex)
        {
            String errorMessage = "DatabaseConnectionFactory::getConnection(DatabaseProperties, HashMap<Object, Object>:"
                    + " Could not load database driver for class name: " + properties.dbDriverClass;
            throw new ClassNotFoundException(ExceptionUtil.getMessage(errorMessage, ex));
        }
        Connection connection = null;
        
        // TODO: the connection string should be part of the properties, not this connection class
        try
        {
            String url = String.format("jdbc:mysql://%s:%s/%s", properties.db_ipaddress, properties.dbport, properties.css_schema);
            connection = DriverManager.getConnection(url, properties.username, properties.password);
            if (connection == null || connection.isValid(2) == false) 
            {
                throw new SQLException("Received a [NULL] or invalid connection object");
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = "DatabaseConnectionFactory::getConnection(DatabaseProperties, HashMap<Object, Object>";
            throw new SQLException(ExceptionUtil.getMessage(errorMessage, ex));
        }
        return connection;
    }
}
