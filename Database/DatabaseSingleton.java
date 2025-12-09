
package Database;

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


import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseSingleton {

    public static DatabaseSingleton ref;
    private DatabaseProperties properties;
    private Connection connection = null;
   
    
    private DatabaseSingleton(){
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
    
    public static  DatabaseSingleton getDatabaseSingleton() {
        if(ref == null){
            ref = new DatabaseSingleton();
        }
        return ref;
    }
    
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public Connection getConnection(boolean autoCommit)  {        
        try {
            if(connection == null || connection.isClosed())
            {
                //System.out.println("3");
                //String url = String.format("jdbc:mysql://%s:%s/%s",  properties.ipaddress,  properties.dbport,  properties.css_schema);
                String url = String.format("jdbc:mysql://%s:%s/%s", properties.db_ipaddress, properties.dbport, properties.css_schema);
                //url += "?useUnicode=yes&characterEncoding=UTF-8";
                connection = DriverManager.getConnection(url, properties.username, properties.password);
                //TODO read up more on this connection.setNetworkTimeout(Executors.newFixedThreadPool(2), DatabaseProperties.CONNECTION_TIMEOUT);
                //System.out.println("4");
            }
            else
            {
                //System.out.println("5");
            }
        } catch (SQLException e) {
            //TODO: Add exception handling
            System.out.println(e.toString());
        }
        return connection;
    }
    
    public DatabaseProperties getProperties()
    {
        return properties;
    }
}
