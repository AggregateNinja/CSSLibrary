
package DAOS;

import Utility.DiffDataObject;
import Utility.DiffDataObject.Diff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * TODO: it's kind of clunky how this DAO tracks "inserted" and "deleted" records.
 *   For an inserted object, you have to provide a dummy "existingObject" with
 *   a blank unique identifier, and the inserted object as the "newObject".
 * 
 *   For a deleted object, you have to provide a dummy "newObject" with a
 *   blank unique identifier, and the object to be deleted as the "existingObject"
 * 
 *   This should be changed to allow a NULL for either existingObject or 
 *   newObject, and based on that the method will determine whether it's an
 *   insert or a delete.
 * 
 *   One positive of the way it's currently working is that not allowing NULLs
 *   helps avoid erroneous logging when the calling code intends to send an 
 *   object, but sends NULL by accident.
 * 
 * 
 * 
 * @author TomR
 */
public class DiffLogDAO
{    
    // How many attempts to perform the action if an error is detected
    private static final int RETRIES = 3;
    
    public enum DiffAction
    {
        INSERT("insert"),
        UPDATE("update"),
        DELETE("delete");
        
        public final String name;
        
        DiffAction(String name)
        {
            this.name = name;
        }
    }
    
    /**
     * Allows calling code to write custom descriptions from the diffs that
     *  were found.
     */
    public interface DiffLogListener
    {
        // Method is called for each diff found before it is written to the log.
        // If a string is returned (not null, length > 0) from this method call,
        // it is used as the row's description.
        String diffFound(DiffAction action, Diff diff, int userId);
    }
    
    private DiffLogDAO() {}
    

    
    /**
     * Inserts a new diff log for the provided table and objects. Creates the
     *  log table if it does not exist.
     * 
     * Uses the database singleton connection
     * 
     * @param logTableName
     * @param uniqueIdentifierName
     * @param uniqueIdentifier
     * @param existingObject
     * @param newObject
     * @param description
     * @param performedByUserId
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static boolean Insert(
        String logTableName,
        String uniqueIdentifierName,
        int uniqueIdentifier,
        Object existingObject,
        Object newObject,
        String description,
        int performedByUserId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {    
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        return Insert(con, logTableName,uniqueIdentifierName,uniqueIdentifier,existingObject,newObject,description,performedByUserId);
    }
    
    /**
     * 
     * Inserts a new diff log for the provided table and objects. Creates the
     *  log table if it does not exist. Calls back to optional listener before
     *  logs are inserted so custom descriptions can be written.
     * 
     * Uses the database singleton connection
     * @param logTableName
     * @param uniqueIdentifierName
     * @param uniqueIdentifier
     * @param existingObject
     * @param newObject
     * @param description
     * @param performedByUserId
     * @param listener
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static boolean Insert(
            String logTableName,
            String uniqueIdentifierName,
            int uniqueIdentifier,
            Object existingObject,
            Object newObject,
            String description,
            int performedByUserId,
            DiffLogListener listener) throws IllegalArgumentException, NullPointerException, SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        return DiffLogDAO.Insert(
                con,
                logTableName,
                uniqueIdentifierName,
                uniqueIdentifier,
                existingObject,
                newObject,
                description,
                performedByUserId,
                listener);
    }
    
    /**
     * Inserts a new diff log for the provided table and objects. Creates the
     *  log table if it does not exist.
     * 
     * @param con
     * @param logTableName
     * @param uniqueIdentifierName
     * @param uniqueIdentifier
     * @param existingObject
     * @param newObject
     * @param description
     * @param performedByUserId 
     * @return Boolean - if false, there were no diffs so no logs were generated
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     * @throws java.sql.SQLException 
     */    
    public static boolean Insert(
        Connection con,
        String logTableName,
        String uniqueIdentifierName,
        int uniqueIdentifier,
        Object existingObject,
        Object newObject,
        String description,
        int performedByUserId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        // No listener method
        return DiffLogDAO.Insert(
                con,
                logTableName,
                uniqueIdentifierName,
                uniqueIdentifier,
                existingObject,
                newObject,
                description,
                performedByUserId,
                null);
    }
    
    /**
     * Inserts a new diff log for the provided table and objects. Creates the
     *  log table if it does not exist. Calls back to optional listener before
     *  logs are inserted so custom descriptions can be written.
     * 
     * @param con
     * @param logTableName
     * @param uniqueIdentifierName
     * @param uniqueIdentifier
     * @param existingObject
     * @param newObject
     * @param description
     * @param performedByUserId
     * @param listener  Optional; calls back before a diff log is written so
     *                  a custom description can be handled by the caller. 
     * 
     * @return Boolean - if false, there were no diffs so no logs were generated
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     * @throws java.sql.SQLException 
     */
    public static boolean Insert(
            Connection con,
            String logTableName,
            String uniqueIdentifierName,
            int uniqueIdentifier,
            Object existingObject,
            Object newObject,
            String description,
            int performedByUserId,
            DiffLogListener listener) throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (logTableName == null || logTableName.isEmpty())
            throw new IllegalArgumentException("Null/blank log table name supplied to DiffLogDAO Insert");
        
        if (uniqueIdentifierName == null || uniqueIdentifierName.isEmpty())
            throw new IllegalArgumentException("Null/blank unqiue identifier name supplied to DiffLogDAO Insert");
        
        if (uniqueIdentifier <= 0) 
            throw new IllegalArgumentException("DiffLogDAO received Unique Identifier Id of " + uniqueIdentifier + ". Must be > 0");
        
        if (performedByUserId <= 0)
            throw new IllegalArgumentException("DiffLogDAO received Performing user Id of " + performedByUserId + ". Must be > 0");
        
        // For now, the diff log only handles when an object in the database is getting updated with a new value
        if (existingObject == null || newObject == null)
            throw new IllegalArgumentException("DiffLogDAO received one or more NULL data object references");
        
        List<Diff> diffs = DiffDataObject.GetDiffs(existingObject, newObject);
        
        // The objects were the same. Nothing has to be written.
        if (diffs == null || diffs.isEmpty()) return false;

        int insertCount = 0;
        DiffAction action = DiffAction.UPDATE;
        
        PreparedStatement pStmt = null;
        for (Diff diff : diffs)
        {
            int attemptCount = 0;
            while (attemptCount < RETRIES)
            {
                try
                {
                    String sql = "INSERT INTO `" + logTableName + "` ("
                            + " `" + uniqueIdentifierName + "`,"
                            + " `action`,"
                            + " `field`,"
                            + " `preValue`, "
                            + " `postValue`, "
                            + " `description`, "
                            + " `performedByUserId`, "
                            + " `isUserVisible`"
                            + " ) VALUES (?,?,?,?,?,?,?,?)";

                    pStmt = con.prepareStatement(sql);
                    
                    // Trim down the field sizes if necessary (table is 255 max)
                    String firstValue = diff.getFirstVal();
                    if (firstValue.length() > 255)
                    {
                        firstValue = firstValue.substring(0, 254);
                    }
                    String secondValue = diff.getSecondVal();
                    if (secondValue.length() > 255)
                    {
                        secondValue = secondValue.substring(0, 254);
                    }                    

                    pStmt.setInt(1, uniqueIdentifier);
                    
                    // If this is marked as the table's unique ID column,
                    // use it to determine what the diff action should be
                    if (diff.isUniqueIdentifier())
                    {
                        try
                        {
                            Integer uniqueIdFirst = null;
                            if (firstValue.isEmpty() == false) uniqueIdFirst = Integer.valueOf(firstValue);
                            Integer uniqueIdSecond = null;
                            if (secondValue.isEmpty() == false) uniqueIdSecond = Integer.valueOf(secondValue);

                            if (
                                    (uniqueIdFirst == null || uniqueIdFirst <= 0)
                                    &&
                                    (uniqueIdSecond != null && uniqueIdSecond > 0)
                                )
                            {
                                action = DiffAction.INSERT;
                            }
                            
                            if (
                                    (uniqueIdFirst != null && uniqueIdFirst > 0)
                                    &&
                                    (uniqueIdSecond == null || uniqueIdSecond <= 0)
                                )
                            {
                                action = DiffAction.DELETE;
                            }                            
                            
                        }
                        catch (Exception ex) {}
                    }
                    
                    pStmt.setString(2, action.name);
                    pStmt.setString(3, diff.getFieldName());
                    pStmt.setString(4, firstValue);
                    pStmt.setString(5, secondValue);
                    
                    // Only use the argument description on the first row
                    if (insertCount > 0) description = null;
                    
                    // Call back to listeners for a custom description
                    String customDescription = null;
                    if (listener != null)
                    {
                        try
                        {
                            customDescription = listener.diffFound(action, diff, performedByUserId);

                            // Custom description should either be NULL or a non-empty
                            // string less than 255 characters.
                            if (customDescription != null)
                            {
                                if (customDescription.isEmpty())
                                {
                                    customDescription = null;
                                }
                                else
                                {
                                    if (customDescription.length() > 255)
                                    {
                                        customDescription = customDescription.substring(0, 255);
                                    }
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            String errorMessage = "DiffLogDAO::diffFound callback:"
                                    + " error while processing custom diff log description message";
                            System.out.println(errorMessage + " " + ex.getMessage());
                            SysLogDAO.Add(performedByUserId, errorMessage, ex.getMessage());
                            customDescription = null;
                        }
                    }                    
                    
                    // Use the argument description if available, otherwise
                    // use the custom description.
                    if (description != null && description.isEmpty() == false)
                    {
                        pStmt.setString(6, description);
                    }
                    else
                    {
                        pStmt.setString(6, customDescription);
                    }
                    
                    pStmt.setInt(7, performedByUserId);
                    pStmt.setBoolean(8, diff.isUserVisible());

                    pStmt.execute();
                    
                    ++insertCount;
                    attemptCount = RETRIES;

                }
                catch (SQLException ex)
                {
                    // It's possible that the log table doesn't exist yet.
                    // If so, create it and try again
                    if (ex == null) throw new SQLException(ex);
                    if (ex.getMessage() != null && ex.getMessage().contains("exist"))
                    {
                        CreateLogTable(con, logTableName, uniqueIdentifierName);
                    }
                }
                finally
                {
                    ++attemptCount;
                }
            } // retry loop
            
            
            // Insert diffs should only insert a single row representing
            // the new unique database identifier
            if (action.equals(DiffAction.INSERT))
            {
                break;
            }            
            
            
        } // diff loop
        
        if (pStmt != null)
        {
            try
            {
                pStmt.close();
            }
            catch (SQLException ex)
            {
                System.out.println(
                        "DiffLogDAO::Couldn't close out"
                                + " prepared statement after insert run");
            }
            pStmt = null;
        }
        
        return true;
    }

    /**
     * Creates a new diff log table using the supplied name and unique identifier
     *  field name. Unique identifier field is indexed.
     * 
     *  Unique identifier field name cannot match other column names.
     * 
     * @param logTableName Name of the log table to be created
     * @param uniqueIdentifierName Name of the unique identifier column
     */
    private static void CreateLogTable(Connection con, String logTableName, String uniqueIdentifierName) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (
                   uniqueIdentifierName.equalsIgnoreCase("id")
                || uniqueIdentifierName.equalsIgnoreCase("action")
                || uniqueIdentifierName.equalsIgnoreCase("field")
                || uniqueIdentifierName.equalsIgnoreCase("preValue")
                || uniqueIdentifierName.equalsIgnoreCase("postValue")
                || uniqueIdentifierName.equalsIgnoreCase("description")
                || uniqueIdentifierName.equalsIgnoreCase("performedByUserId")
                || uniqueIdentifierName.equalsIgnoreCase("date")
                || uniqueIdentifierName.equalsIgnoreCase("isUniqueIdentifier")
                || uniqueIdentifierName.equalsIgnoreCase("isUserVisible")
                )
        {
            throw new IllegalArgumentException("Log table unique identifier `" 
                    + uniqueIdentifierName + "` is a reserved column name for diff log tables");
        }
        
        String createTableSQL = "CREATE TABLE `" + logTableName + "` ("
                + " `id` INT(10) NOT NULL AUTO_INCREMENT, "
                + " `" + uniqueIdentifierName + "` INT(10) NOT NULL, "
                + " `action` VARCHAR(45) NOT NULL, "
                + " `field` VARCHAR(45) DEFAULT NULL, "
                + " `preValue` VARCHAR(255) DEFAULT NULL, "
                + " `postValue` VARCHAR(255) DEFAULT NULL, "
                + " `description` VARCHAR(255) DEFAULT NULL, "
                + " `performedByUserId` INT(10) NOT NULL, "
                + " `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP, "
                + " `isUserVisible` TINYINT(1) DEFAULT 0,"
                + " PRIMARY KEY (`id`), "
                + " KEY `IND_" + logTableName + "_" + uniqueIdentifierName 
                + "` (`" + uniqueIdentifierName + "`), "
                + " KEY `IND_" + logTableName + "_performedByUserId`"
                + " (`performedByUserId`),"
                + " KEY `IND_" + logTableName + "_date`"
                + " (`date`)"
                + " ) ENGINE=InnoDB DEFAULT CHARSET=utf8";
        
        Statement stmt = con.createStatement();
        stmt.execute(createTableSQL);
    }
    
    
    
    
}
