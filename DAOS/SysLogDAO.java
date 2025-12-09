
package DAOS;

import Database.CheckDBConnection;
import Utility.Encryptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Error reporting log. Exceptions/errors are encrypted before being
 *  inserted into DB. Attempts to create sysLog table if it doesn't exist.
 */
public class SysLogDAO
{
    /**
     * Logs a problem into the sysLog table.
     * Action should be a human-readable description of what the user was doing,
     * including any useful identifiers
     * Message can be an exception / stack trace
     * 
     * @param userId
     * @param action What the user was doing at the time (module/method) and any identifiers that would be useful to track
     * @param message The error message itself, like an exception.getMessage()
     */
    public static void Add(int userId, String action, String message)
    {
        if (action == null) action = "[No action provided]";
        if (message == null) message = "";
        
        try
        {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            // Does the table exist?
            String sql = "SHOW TABLES LIKE 'sysLog'";
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            // If not, create it
            if (!rs.next())
            {
                sql = "CREATE TABLE `sysLog` (	"
                    + "   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,"
                    + "   `userId` int(10) NOT NULL,"
                    + "   `action` VARCHAR(1024) NULL,"
                    + "   `message` TEXT NULL,"
                    + "   `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "   PRIMARY KEY (`id`) "
                    + " ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
                boolean success = pStmt.execute(sql);
            }
            
            String encryptedAction = Encryptor.EncryptString(action, true);
            String encryptedMessage = Encryptor.EncryptString(message, true);
            
            sql = "INSERT into `sysLog` (`userId`, `action`, `message`) VALUES (?, ?, ?)";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, userId);
            pStmt.setString(2, encryptedAction);
            pStmt.setString(3, encryptedMessage);
            
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println("Can't write syslog! UserId=" + userId + " message " + message);
        }
    }
}
