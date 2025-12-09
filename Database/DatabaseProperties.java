
package Database;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: Database
 * @file name: DatabaseProperties.java
 *
 * @Description:  This class holds properties for the database.
 *                Right now it is hard coded, but I have code to implement
 *                so that it retrieves this information from the Java Hive.
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

// 2017-10-15 TODO:
//  None of the DAOs should include hardcoded schema prefixes, and should
//  instead point to the configuration.
//  Every schema should be change-able via the configuration file.


import Utility.Encryptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseProperties
{
    
    public final String dbDriverClass = "com.mysql.jdbc.Driver";
    
    String hostname = null;
    String ipaddress = null;
    String username = null;
    String password = null;
    String dbport = null;
    
    // System schema names
    String css_schema = null;
    String web_schema = null;
    String emr_schema = null; // emr orders
    String emr_billing_schema = null;
    String billing_schema = null; // avalon's billing schema
    
    
    Integer connection_timeout = null;
    String instance = null;
    String service_port = null;
    String db_hostname = null;
    String db_ipaddress = null;
    
    private static final int DEFAULT_CONNECTION_TIMEOUT = 300000;
    private static final int TIMEOUT_SECONDS_DIVISOR = 100000;
    private String key;
    
    public static String configFilePath = "";
    
    public static String getConfigFile()
    {
        String OSName = System.getProperty("os.name");
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        System.out.println("Current path: " + currentPath.toString());
        if(OSName.startsWith("Windows"))
        {
            if (currentPath.toFile().exists())
            {
                File configFile = new File(currentPath.toFile() + File.separator + "etc" + File.separator + "Config.xml");
                if (configFile.exists())
                {
                    return configFile.getAbsolutePath();
                }
            }
            return System.getenv("APPDATA") + File.separator + ".hybrid" + File.separator + "Config.xml";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            return "/usr/hybrid/Config.xml";
        }
        else if(OSName.toLowerCase().startsWith("mac") || OSName.toLowerCase().contains("os x"))
        {
            if (currentPath.toFile().exists())
            {
                File configFile = new File(currentPath.toFile() + File.separator + "etc" + File.separator + "Config.xml");
                if (configFile.exists())
                {
                    return configFile.getAbsolutePath();
                }
            }
            return System.getProperty("user.home") + "/Library/Application Support/.hybrid/Config.xml";
        }
        return System.getProperty("user.home") + "/Config.xml";
    }
    
    public static String getConfigFile(Integer instance)
    {
        if (instance == null || instance < 2) {
            return getConfigFile();
        } else {
            String OSName = System.getProperty("os.name");
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            System.out.println("Current path: " + currentPath.toString());
            if(OSName.startsWith("Windows"))
            {
                if (currentPath.toFile().exists())
                {
                    File configFile = new File(currentPath.toFile() + File.separator + "etc" + File.separator + "Config.xml");
                    if (configFile.exists())
                    {
                        return configFile.getAbsolutePath();
                    }
                }
                return System.getenv("APPDATA") + File.separator + ".hybrid" + File.separator + "Config.xml";
            } else if (OSName.startsWith("AIX") || OSName.startsWith("Linux")) {
                return "/usr/hybrid" + instance + "/Config.xml";
            } else if(OSName.toLowerCase().startsWith("mac") || OSName.toLowerCase().contains("os x")) {
                if (currentPath.toFile().exists())
                {
                    File configFile = new File(currentPath.toFile() + File.separator + "etc" + File.separator + "Config.xml");
                    if (configFile.exists())
                    {
                        return configFile.getAbsolutePath();
                    }
                }
                return System.getProperty("user.home") + "/Library/Application Support/.hybrid/Config.xml";
            }
            return System.getProperty("user.home") + "/Config.xml";
        }
    }
    
    public static boolean CreateConfig(String hostname, String ipAddress, Integer port, Integer timeout)
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        DatabaseProperties dbp = new DatabaseProperties();
        dbp.hostname = hostname;
        dbp.ipaddress = ipAddress;
        dbp.username = Encryptor.EncryptString("appMaster", true);
       // dbp.password = Encryptor.EncryptString("css2009", true);
        dbp.password = Encryptor.EncryptString("@VAl0n6o9", true);
        dbp.dbport = port.toString();
        dbp.css_schema = "css";
        dbp.web_schema = "cssweb";
        dbp.connection_timeout = timeout;
        //Following variables are for multiple instances
        dbp.instance = "";
        dbp.service_port = "";
        
        String toXML = xs.toXML(dbp);
        String OSName = System.getProperty("os.name");
        String appDataDirectory;
        String hybridDirectory;
        if(OSName.startsWith("Windows"))
        {
            appDataDirectory = System.getenv("APPDATA");
            hybridDirectory = ".hybrid";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            appDataDirectory = File.separator + "usr";
            hybridDirectory = "hybrid";
        }
        else if (OSName.toLowerCase().startsWith("mac") || (OSName.toLowerCase().contains("os x")))
        {
            appDataDirectory = System.getProperty("user.home") + "/Library/Application Support";
            hybridDirectory = ".hybrid";
        }
        else
        {
            appDataDirectory = System.getProperty("user.home");
            hybridDirectory = ".hybrid";
        }
        
        System.out.println("Hybrid directory: " + appDataDirectory + File.separator + hybridDirectory);
        
        File appData = new File(appDataDirectory);
        if(appData.exists() && appData.isDirectory())
        {
            File hybrid = new File(appData, hybridDirectory);
            if(!hybrid.exists())
            {
                hybrid.mkdir();
                hybrid.setWritable(true);
                hybrid.setReadable(true);
            }
            File config = new File(hybrid, "Config.xml");
            if(!config.exists())
            {
                try {
                    config.createNewFile();
                    config.setWritable(true);
                    config.setReadable(true);
                }
                catch (IOException ex) {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            else
            {
                try
                {
                    FileOutputStream eraser = new FileOutputStream(config);
                    eraser.write((new String()).getBytes()); 
                    eraser.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                catch (IOException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            
            try
            {
                FileOutputStream writer = new FileOutputStream(config);
                writer.write(toXML.getBytes());
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            return true;
        }
        
        return false;
    }
    
    public static boolean CreateConfig(String hostname, String ipAddress, Integer port, Integer timeout, Integer servInstance, Integer servPort)
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        DatabaseProperties dbp = new DatabaseProperties();
        dbp.hostname = hostname;
        dbp.ipaddress = ipAddress;
        dbp.username = Encryptor.EncryptString("appMaster", true);
       // dbp.password = Encryptor.EncryptString("css2009", true);
        dbp.password = Encryptor.EncryptString("@VAl0n6o9", true);
        dbp.dbport = port.toString();
        dbp.css_schema = "css";
        dbp.web_schema = "cssweb";
        dbp.connection_timeout = timeout;
        //Following variables are for multiple instances
        dbp.instance = servInstance.toString();
        dbp.service_port = servPort.toString();
        
        String toXML = xs.toXML(dbp);
        String OSName = System.getProperty("os.name");
        String appDataDirectory;
        String hybridDirectory;
        if(OSName.startsWith("Windows"))
        {
            appDataDirectory = System.getenv("APPDATA");
            hybridDirectory = ".hybrid";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            appDataDirectory = File.separator + "usr";
            hybridDirectory = "hybrid";
        }
        else if (OSName.toLowerCase().startsWith("mac") || (OSName.toLowerCase().contains("os x")))
        {
            appDataDirectory = System.getProperty("user.home") + "/Library/Application Support";
            hybridDirectory = ".hybrid";
        }
        else
        {
            appDataDirectory = System.getProperty("user.home");
            hybridDirectory = ".hybrid";
        }
        
        System.out.println("Hybrid directory: " + appDataDirectory + File.separator + hybridDirectory);
        
        File appData = new File(appDataDirectory);
        if(appData.exists() && appData.isDirectory())
        {
            File hybrid = new File(appData, hybridDirectory);
            if(!hybrid.exists())
            {
                hybrid.mkdir();
                hybrid.setWritable(true);
                hybrid.setReadable(true);
            }
            File config = new File(hybrid, "Config.xml");
            if(!config.exists())
            {
                try {
                    config.createNewFile();
                    config.setWritable(true);
                    config.setReadable(true);
                }
                catch (IOException ex) {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            else
            {
                try
                {
                    FileOutputStream eraser = new FileOutputStream(config);
                    eraser.write((new String()).getBytes()); 
                    eraser.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                catch (IOException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            
            try
            {
                FileOutputStream writer = new FileOutputStream(config);
                writer.write(toXML.getBytes());
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            return true;
        }
        
        return false;
    }
    
    public static boolean CreateConfig(String hostname, String ipAddress, Integer port, Integer timeout, Integer servInstance, Integer servPort, String dbHostname, String dbIpAddress)
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        DatabaseProperties dbp = new DatabaseProperties();
        dbp.hostname = hostname;
        dbp.ipaddress = ipAddress;
        dbp.username = Encryptor.EncryptString("appMaster", true);
       // dbp.password = Encryptor.EncryptString("css2009", true);
        dbp.password = Encryptor.EncryptString("@VAl0n6o9", true);
        dbp.dbport = port.toString();
        dbp.css_schema = "css";
        dbp.web_schema = "cssweb";
        dbp.connection_timeout = timeout;
        //Following variables are for multiple instances
        dbp.instance = servInstance.toString();
        dbp.service_port = servPort.toString();
        //The following variables are used with dedicated SQL Server, or SQL cluster server
        dbp.db_hostname = dbHostname;
        dbp.db_ipaddress = dbIpAddress;
        
        String toXML = xs.toXML(dbp);
        String OSName = System.getProperty("os.name");
        String appDataDirectory;
        String hybridDirectory;
        if(OSName.startsWith("Windows"))
        {
            appDataDirectory = System.getenv("APPDATA");
            hybridDirectory = ".hybrid";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            appDataDirectory = File.separator + "usr";
            hybridDirectory = "hybrid";
        }
        else if (OSName.toLowerCase().startsWith("mac") || (OSName.toLowerCase().contains("os x")))
        {
            appDataDirectory = System.getProperty("user.home") + "/Library/Application Support";
            hybridDirectory = ".hybrid";
        }
        else
        {
            appDataDirectory = System.getProperty("user.home");
            hybridDirectory = ".hybrid";
        }
        
        System.out.println("Hybrid directory: " + appDataDirectory + File.separator + hybridDirectory);
        
        File appData = new File(appDataDirectory);
        if(appData.exists() && appData.isDirectory())
        {
            File hybrid = new File(appData, hybridDirectory);
            if(!hybrid.exists())
            {
                hybrid.mkdir();
                hybrid.setWritable(true);
                hybrid.setReadable(true);
            }
            File config = new File(hybrid, "Config.xml");
            if(!config.exists())
            {
                try {
                    config.createNewFile();
                    config.setWritable(true);
                    config.setReadable(true);
                }
                catch (IOException ex) {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            else
            {
                try
                {
                    FileOutputStream eraser = new FileOutputStream(config);
                    eraser.write((new String()).getBytes()); 
                    eraser.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                catch (IOException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            
            try
            {
                FileOutputStream writer = new FileOutputStream(config);
                writer.write(toXML.getBytes());
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            return true;
        }
        
        return false;
    }
    
    public static boolean CreateConfig(String hostname, String ipAddress, Integer port, Integer timeout, String dbHostname, String dbIpAddress)
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        DatabaseProperties dbp = new DatabaseProperties();
        dbp.hostname = hostname;
        dbp.ipaddress = ipAddress;
        dbp.username = Encryptor.EncryptString("appMaster", true);
       // dbp.password = Encryptor.EncryptString("css2009", true);
        dbp.password = Encryptor.EncryptString("@VAl0n6o9", true);
        dbp.dbport = port.toString();
        dbp.css_schema = "css";
        dbp.web_schema = "cssweb";
        dbp.connection_timeout = timeout;
        //Following variables are for multiple instances
        dbp.instance = "";
        dbp.service_port = "";
        //The following variables are used with dedicated SQL Server, or SQL cluster server
        dbp.db_hostname = dbHostname;
        dbp.db_ipaddress = dbIpAddress;
        
        String toXML = xs.toXML(dbp);
        String OSName = System.getProperty("os.name");
        String appDataDirectory;
        String hybridDirectory;
        if(OSName.startsWith("Windows"))
        {
            appDataDirectory = System.getenv("APPDATA");
            hybridDirectory = ".hybrid";
        }
        else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            appDataDirectory = File.separator + "usr";
            hybridDirectory = "hybrid";
        }
        else if (OSName.toLowerCase().startsWith("mac") || (OSName.toLowerCase().contains("os x")))
        {
            appDataDirectory = System.getProperty("user.home") + "/Library/Application Support";
            hybridDirectory = ".hybrid";
        }
        else
        {
            appDataDirectory = System.getProperty("user.home");
            hybridDirectory = ".hybrid";
        }
        
        System.out.println("Hybrid directory: " + appDataDirectory + File.separator + hybridDirectory);
        
        File appData = new File(appDataDirectory);
        if(appData.exists() && appData.isDirectory())
        {
            File hybrid = new File(appData, hybridDirectory);
            if(!hybrid.exists())
            {
                hybrid.mkdir();
                hybrid.setWritable(true);
                hybrid.setReadable(true);
            }
            File config = new File(hybrid, "Config.xml");
            if(!config.exists())
            {
                try {
                    config.createNewFile();
                    config.setWritable(true);
                    config.setReadable(true);
                }
                catch (IOException ex) {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            else
            {
                try
                {
                    FileOutputStream eraser = new FileOutputStream(config);
                    eraser.write((new String()).getBytes()); 
                    eraser.close();
                }
                catch (FileNotFoundException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
                catch (IOException ex)
                {
                    Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            
            try
            {
                FileOutputStream writer = new FileOutputStream(config);
                writer.write(toXML.getBytes());
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(DatabaseProperties.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

            return true;
        }
        
        return false;
    }
    
    public static DatabaseProperties FetchConfig() throws FileNotFoundException
    {
        Class<?>[] classes = new Class[]{DatabaseProperties.class};
        
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        
        // Setting up the permission for XStream
        xs.addPermission(NoTypePermission.NONE);
        xs.addPermission(NullPermission.NULL);
        xs.addPermission(PrimitiveTypePermission.PRIMITIVES);
        
        // Need to explicitly define permission for this class: DatabaseProperties
        xs.allowTypes(classes);
        
        String config = getConfigFile();
        
        if (configFilePath != null && !configFilePath.isEmpty())
            config = configFilePath;
        
        System.out.println("Config file found at: " + config);
        FileInputStream fis = new FileInputStream(config);
        xs.aliasField("hostname", DatabaseProperties.class, "hostname");
        xs.alias("config", DatabaseProperties.class);
        DatabaseProperties xt = (DatabaseProperties) xs.fromXML(fis);

        //System.out.println(xt.toString());
        
        if (xt.getService_port() == null) xt.service_port = "2990"; // Default
        
        String decodeUser = Utility.Encryptor.EncryptString(xt.username, false);
        String decodePW = Utility.Encryptor.EncryptString(xt.password, false);
        
        xt.username = decodeUser;
        xt.password = decodePW;
        
        //Do a check incase using old Config.xml or DB server information is empty
        //If so default to host and ipaddress
        if(xt.db_hostname == null || xt.db_hostname.isEmpty()){
            xt.db_hostname = xt.hostname;
        }
        if(xt.db_ipaddress == null || xt.db_ipaddress.isEmpty()){
            xt.db_ipaddress = xt.ipaddress;
        }
        if(xt.connection_timeout == null) {
            xt.connection_timeout = DEFAULT_CONNECTION_TIMEOUT;
        }
        
        return xt;
        /*if (OSName.startsWith("Windows")) {
            String config = System.getenv("APPDATA") + File.separator + ".hybrid" + File.separator + "Config.xml";
            FileInputStream fis = new FileInputStream("c:/Temp/Config.xml");
            xs.aliasField("hostname", DatabaseProperties.class, "hostname");
            xs.alias("config", DatabaseProperties.class);
            DatabaseProperties xt = (DatabaseProperties) xs.fromXML(fis);

            System.out.println(xt.toString());
            
            return xt;
        }else if(OSName.startsWith("AIX") || OSName.startsWith("Linux"))
        {
            FileInputStream fis = new FileInputStream("/usr/hybrid/Config.xml");
            xs.aliasField("hostname", DatabaseProperties.class, "hostname");
            xs.alias("config", DatabaseProperties.class);
            DatabaseProperties xt = (DatabaseProperties) xs.fromXML(fis);

            System.out.println(xt.toString());
            
            return xt;
        }
        
        return null;*/
    }
    
    public String getHostname()
    {
        return hostname;
    }

    public String getIPAddress()
    {
        return ipaddress;
    }

    public String getUserName()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDBPort()
    {
        return dbport;
    }

    public String getCssSchema()
    {
        if (css_schema == null || css_schema.isEmpty())
        {
            css_schema = "css";
        }
        return css_schema;
    }
    
    public String getBillingSchema()
    {
        if (billing_schema == null || billing_schema.isEmpty())
        {
            billing_schema = "cssbilling";
        }
        return billing_schema;
    }
    
    
    public String getWebSchema()
    {
        if (web_schema == null || web_schema.isEmpty())
        {
            web_schema = "cssweb";
        }
        return web_schema;
    }
    
    public String getEmrSchema()
    {
        if (emr_schema == null || emr_schema.isEmpty())
        {
            emr_schema = "emrorders";
        }
        return emr_schema;
    }

    public String getEmrBillingSchema()
    {
        if (emr_billing_schema == null || emr_billing_schema.isEmpty())
        {
            emr_billing_schema = "emrbilling";
        }
        return emr_billing_schema;
    }
    
    public Integer getConnectionTimeout()
    {
        return connection_timeout;
    }
    
    public Integer getConnectionTimeoutSeconds()
    {
        return connection_timeout / TIMEOUT_SECONDS_DIVISOR;
    }

    public String getInstance() {
        return instance;
    }

    public String getService_port() {
        return service_port;
    }

    public String getDBHostname()
    {
        return db_hostname;
    }

    public String getDBIPAddress()
    {
        return db_ipaddress;
    }
    
    //public static  final String HOSTNAME = "premier";
    //public static  final String HOSTNAME = "jdbc:mysql://68.195.194.148:3306/css";
    //public static  final String HOSTNAME = "jdbc:mysql://192.168.1.20:3306/css";
    //public static  final String HOSTNAME = "jdbc:mysql://10.0.1.210:3306/css";
    //public static  final String HOSTNAME = "jdbc:mysql://50.77.155.225:3306/css";
    //Vessel
    //public static  final String HOSTNAME = "jdbc:mysql://70.43.37.166:3306/css";
    //ALS
    //public static  final String HOSTNAME = "jdbc:mysql://als:3306/css";
    //LTC
    //public static final String HOSTNAME = "jdbc:mysql://50.77.155.225:3306/css";
    //CSSNew
    //public static  final String HOSTNAME = "jdbc:mysql://10.0.0.8:3306/css";
    //CLSCI
    //public static  final String HOSTNAME = "jdbc:mysql://clsci:3306/css";
    //Local
    //public static  final String HOSTNAME = "jdbc:mysql://127.0.0.1:3306/css";
    //PSO
    //public static  final String HOSTNAME = "jdbc:mysql://pso:3306/css";
    //public static  final String USERNAME = "appMaster";
    //public static  final String PASSWORD = "css2009";
    //public static  final int    CONNECTION_TIMEOUT = 300 * 1000;    // seconds to milliseconds
    
    public static String getKey() {
        return "raven29desk";
    }
    
    @Override
    public String toString()
    {
        String output = "\n===========================================================";
        output += "\nLoaded database configuration:";
        output += "\n-----------------------------------------------------------";
        if (hostname != null && hostname.trim().isEmpty() == false)
        {
            output += "\nHostname: " + hostname;
        }
        
        if (ipaddress != null && ipaddress.trim().isEmpty() == false)
        {
            output += "\nIPAddress: " + ipaddress;
        }
        
        if (dbport != null && dbport.trim().isEmpty() == false)
        {
            output += "\nDBPort: " + dbport;
        }
        
        if (css_schema != null && css_schema.trim().isEmpty() == false)
        {
            output += "\nCSS Schema: " + css_schema;
        }
        
        if (web_schema != null && web_schema.trim().isEmpty() == false)
        {
            output += "\nWeb Schema: " + web_schema;
        }
        
        if (instance != null && instance.trim().isEmpty() == false)
        {
            output += "\nInstance: " + instance;
        }
        
        if (service_port != null && service_port.trim().isEmpty() == false)
        {
            output += "\nService Port: " + service_port;
        }

        if (db_hostname != null && db_hostname.trim().isEmpty() == false)
        {
            output += "\nDB HostName: " + db_hostname;
        }
        
        if (db_ipaddress != null && db_ipaddress.trim().isEmpty() == false)
        {
            output += "\nDB IP Address: " + db_ipaddress;
        }
        output += "\n-----------------------------------------------------------";
        return output;
    }
}
