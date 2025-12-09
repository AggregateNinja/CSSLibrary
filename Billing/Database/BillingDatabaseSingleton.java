
package Billing.Database;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: Database
 * @file name: DatabaseSingleton.java
 *
 * @Description:  Creates and returns a single database connection.  Since this 
 *                is a client side app, we are going to limit their connections
 *                to one per instance.  This will prevent loose ends, and dead 
 *                connections.
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */


import EMR.Database.*;
import Database.*;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class BillingDatabaseSingleton
{

    public static  BillingDatabaseSingleton ref;
    private DatabaseProperties properties;
    private Connection connection = null;
   
    
    private BillingDatabaseSingleton()
    {
        try {
            //System.out.println("1");
            Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("2");
        } catch (ClassNotFoundException e) {
            //TODO: Add exception handling
            System.out.println(e.toString());
        }
        try {
            properties = DatabaseProperties.FetchConfig();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public static  BillingDatabaseSingleton getDatabaseSingleton()
    {
        if(ref == null)
        {
            ref = new BillingDatabaseSingleton();
        }
        return ref;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
    
    public Connection getConnection(boolean autoCommit)
    {        
        try
        {
            if(connection == null || connection.isClosed())
            {
                String url = String.format("jdbc:mysql://%s:%s/%s",  properties.getDBIPAddress(),  properties.getDBPort(),  "cssbilling");
                connection = DriverManager.getConnection(url, properties.getUserName(), properties.getPassword());
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
        return connection;
    }
}
