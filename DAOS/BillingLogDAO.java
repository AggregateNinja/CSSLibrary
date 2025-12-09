
package DAOS;

import DOS.AdjustmentEvent;
import DOS.DetailOrderEvent;
import DOS.IDOS.IBillingDO;
import DOS.SubmissionEvent;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Handles logging for billing using data object diff annotations
 * -----------------------------------------------------------------------------
 * 
 * Different from main schema diff logging:
 * 
 *  - Logs all changes in one of three main log tables: detailOrderLog,
 *    adjustmentLog, submissionLog instead of having a separate log for
 *    every table.
 * 
 *  - Writes the Id of the relevant event that caused the change:
 *    detailOrderEventId, submissionEventId, adjustmentEventId
 * 
 *  - Writes the table name in addition to the field name
 * 
 * 
 * Goal of the changes:
 * 
 *  - Streamline the way you log an insert/update/delete diff logs. In
 *    the main schema that amounted to supplying a "dummy" second object to log
 *    a deleted row or a "dummy" first object to log an inserted row. Here you
 *    just call the method you want: logNew.. logUpdated.. logDeleted..
 * 
 *  - Instead of having separate diff logs for every table, they are combined
 *    by type and foreign keyed to the event tables. More centralized, 
 *    join-supported logging to help make drilling-down on events easier.
 * 
 *  - If tables are added in the future that we want to log, it's just a matter
 *    of deciding whether they are detailOrder, submission, or adjustment
 *    objects and calling the appropriate log method, and having diff
 *    annotations set up on the DO. DOs should implement IBillingDO so we can
 *    pull the table name for the log.
 * 
 *  - To log, you must supply an event object. Forces everything to use the
 *    event-based design and hopefully keeps things consistent.
 *  
 *  Notes:
 * 
 *  - The three log tables will be created if they don't exist and the
 *    connection isn't in a transaction (DDL changes will auto-commit)
 * 
 *  - The code in this class is not transactional. It is recommended to use
 *    a transaction outside of this class to ensure that all data gets written.
 * 
 *  - If you are logging a new row, a single diff line gets written for the
 *    new unique identifier of the data object, since it's not necessary to
 *    write out all of the new data. If there is no unique identifier found
 *    it will log ALL annotated data points.
 * 
 *  - The callback listener allows outside code to return a custom description
 *    string before the log is written. If the diff log change would not be
 *    meaningful to a user, we can prepare a human-readable description of what
 *    that change actually means. For example, a status identifier changes from
 *    2 to 3. The listener might return the string "Order status changed from
 *    'Incomplete' to 'Written Off'". This, combined with the isUserVisible
 *    flags means we can maintain internal and user-visible logs in a
 *    centralized place, and change the wording if we need to.
 * 
 */
public class BillingLogDAO
{
    // How many log insert retries to perform the action if an error is detected
    private static final int RETRIES = 3;
    
    public enum BillingDiffAction
    {
        Added("added"),
        Updated("updated"),
        Deleted("deleted");
        
        String name;
        
        BillingDiffAction(String name)
        {
            this.name = name;
        }
    }
    
    public interface BillingDiffLogListener
    {
        // Method is called for each diff found before it is written to the log.
        // If a string is returned (not null, length > 0) from this method call,
        // it is used as the row's description.
        String diffFound(BillingDiffAction action, Diff diff, int userId);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Diff (class)">
    /**
     * Wrapper class containing differences between two objects
     */
    public static class Diff implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        private final String tableName;
        private final String fieldName;
        private final String firstVal;
        private final String secondVal;
        private final boolean isUniqueIdentifier;
        private final boolean isUserVisible;
        
        public Diff(
                String tableName,
                String fieldName,
                String firstVal,
                String secondVal,
                boolean isUniqueIdentifier,
                boolean isUserVisible)
        {
            this.tableName = tableName;
            this.fieldName = fieldName;
            this.firstVal = firstVal;
            this.secondVal = secondVal;
            this.isUniqueIdentifier = isUniqueIdentifier;
            this.isUserVisible = isUserVisible;
        }

        public String getTableName()
        {
            return tableName;
        }
        
        public String getFieldName()
        {
            return fieldName;
        }

        public String getFirstVal()
        {
            return firstVal;
        }

        public String getSecondVal()
        {
            return secondVal;
        }
        
        public boolean isUniqueIdentifier()
        {
            return isUniqueIdentifier;
        }
        
        public boolean isUserVisible()
        {
            return isUserVisible;
        }
        
        @Override
        public String toString()
        {
            return ToStringBuilder.reflectionToString(this);
        }
    }    
    //</editor-fold>
    
    // =========================================================================
    //              Log ADDED
    // =========================================================================
    
    //<editor-fold defaultstate="collapsed" desc="Log New Detail Order Row">
    // No callback listener
    public static void logNewRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO newObject,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logNewRow(con, detailOrderEvent, newObject, userId, null);
    }    

    
    public static void logNewRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO newObject,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        if (newObject == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                   + " Received a [NULL] Object argument");
        }
        
        if (detailOrderEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                  + " Received a [NULL] DetailOrderEvent");
        }
        
        if (detailOrderEvent.getIdDetailOrderEvents() == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received a DetailOrderEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Could not test Connection object to see if it was valid");
        }
        
        
        logNewDiffData(
            con,
            newObject,
            "detailOrderEventId",
            detailOrderEvent.getIdDetailOrderEvents(),
            "detailOrderLog",
            userId,
            listener);

    }
    //</editor-fold>   
    
    //<editor-fold defaultstate="collapsed" desc="Log New Submission Row">
    // No callback listener
    public static void logNewRow(
            Connection con,
            SubmissionEvent submissionEvent,
            IBillingDO newObject,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logNewRow(con, submissionEvent, newObject, userId, null);
    }
    
    public static void logNewRow(
            Connection con,
            SubmissionEvent submissionEvent,
            IBillingDO newObject,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
        if (newObject == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                   + " Received a [NULL] Object argument");
        }
        
        if (submissionEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                  + " Received a [NULL] SubmissionEvent");
        }
        
        if (submissionEvent.getIdSubmissionEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received an SubmissionEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Could not test Connection object to see if it was valid");
        }
        
        logNewDiffData(
            con,
            newObject,
            "submissionEventId",
            submissionEvent.getIdSubmissionEvents(),
            "submissionLog",
            userId,
            listener);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Log New Adjustment Row">
    // No callback listener
    public static void logNewRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO newObject,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logNewRow(con, adjustmentEvent, newObject, userId, null);
    }
    
    public static void logNewRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO newObject,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {

        if (newObject == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                   + " Received a [NULL] Object argument");
        }
        
        if (adjustmentEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                  + " Received a [NULL] AdjustmentEvent");
        }
        
        if (adjustmentEvent.getIdAdjustmentEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received an AdjustmentEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logNewRow"
                    + " Could not test Connection object to see if it was valid");
        }
                
        
        logNewDiffData(
            con,
            newObject,
            "adjustmentEventId",
            adjustmentEvent.getIdAdjustmentEvents(),
            "adjustmentLog",
            userId,
            listener);
    }
    //</editor-fold>
    
    // =========================================================================
    //              Log UPDATED
    // =========================================================================
    
    
    //<editor-fold defaultstate="collapsed" desc="Log Updated Detail Order Row">
    // No callback listener
    public static void logUpdatedRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logUpdatedRow(con, detailOrderEvent, oldObjectVersion, newObjectVersion, userId, null);
    }
    
    public static void logUpdatedRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'old' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
       if (newObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'new' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
        if (detailOrderEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                  + " Received a [NULL] DetailOrderEvent object");
        }
        
        if (detailOrderEvent.getIdDetailOrderEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received an DetailOrderEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Could not test Connection object to see if it was valid");
        }
                 
        
        List<Diff> diffs = getDiffs(oldObjectVersion, newObjectVersion);
        
        insertLogs(con,
                BillingDiffAction.Updated,
                "detailOrderEventId",
                detailOrderEvent.getIdDetailOrderEvents(),
                "detailOrderLog", diffs, userId, listener);        
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Log Updated Submission Row">
    // No callback listener
    public static void logUpdatedRow(
            Connection con,
            SubmissionEvent submissionEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logUpdatedRow(con, submissionEvent, oldObjectVersion, newObjectVersion, userId, null);
    }    
    
    public static void logUpdatedRow(
            Connection con,
            SubmissionEvent submissionEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'old' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
       if (newObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'new' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
        if (submissionEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                  + " Received a [NULL] SubmissionEvent object");
        }
        
        if (submissionEvent.getIdSubmissionEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received a SubmissionEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Could not test Connection object to see if it was valid");
        }

        List<Diff> diffs = getDiffs(oldObjectVersion, newObjectVersion);
        
        insertLogs(con,
                BillingDiffAction.Updated,
                "submissionEventId",
                submissionEvent.getIdSubmissionEvents(),
                "submissionLog", diffs, userId, listener);
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="Log Updated Adjustment Row">
    // No callback listener
    public static void logUpdatedRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logUpdatedRow(con, adjustmentEvent, oldObjectVersion, newObjectVersion, userId, null);
    }    
    
    public static void logUpdatedRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO oldObjectVersion,
            IBillingDO newObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'old' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
       if (newObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                   + " Received a [NULL] 'new' object argument."
                    + " The 'old' and 'new' object versions cannot be [NULL]");
        }
        
        if (adjustmentEvent == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                  + " Received a [NULL] AdjustmentEvent object");
        }
        
        if (adjustmentEvent.getIdAdjustmentEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received an AdjustmentEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logUpdatedRow"
                    + " Could not test Connection object to see if it was valid");
        }
        
        
        List<Diff> diffs = getDiffs(oldObjectVersion, newObjectVersion);
        
        insertLogs(con,
                BillingDiffAction.Updated,
                "adjustmentEventId",
                adjustmentEvent.getIdAdjustmentEvents(),
                "adjustmentLog", diffs, userId, listener);
    }
    //</editor-fold>
    
    // =========================================================================
    //              Log DELETED
    // =========================================================================

   
    //<editor-fold defaultstate="collapsed" desc="Log Deleted Detail Order Row">
    // No callback listener
    public static void logDeletedRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO oldObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logDeletedRow(con, detailOrderEvent, oldObjectVersion, userId, null);
    }
    
    public static void logDeletedRow(
            Connection con,
            DetailOrderEvent detailOrderEvent,
            IBillingDO oldObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                   + " Received a [NULL] Object argument.");
        }
        
        if (detailOrderEvent.getIdDetailOrderEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received an DetailOrderEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Could not test Connection object to see if it was valid");
        }
        
        
        // If deleting a row, log every data point
        List<Diff> diffs = getDiffMethodOutputs(oldObjectVersion, false);
        
        insertLogs(con,
                BillingDiffAction.Deleted,
                "detailOrderEventId",
                detailOrderEvent.getIdDetailOrderEvents(),
                "detailOrderLog",
                diffs,
                userId,
                listener);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Log Deleted Submission Row">
    // No callback listener
    public static void logDeletedRow(
            Connection con,
            SubmissionEvent submissionEvent,
            IBillingDO oldObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logDeletedRow(con, submissionEvent, oldObjectVersion, userId, null);
    }

    public static void logDeletedRow(
            Connection con,            
            SubmissionEvent submissionEvent,
            IBillingDO oldObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                   + " Received a [NULL] Object argument.");
        }
        
        
        if (submissionEvent.getIdSubmissionEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received a SubmissionEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Could not test Connection object to see if it was valid");
        }
                
        
        
        // If deleting a row, log every data point
        List<Diff> diffs = getDiffMethodOutputs(oldObjectVersion, false);
        
        insertLogs(con,
                BillingDiffAction.Deleted,
                "submissionEventId",
                submissionEvent.getIdSubmissionEvents(),
                "submissionLog",
                diffs,
                userId,
                listener);        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Log Deleted Adjustment Row">
    // No callback listener
    public static void logDeletedRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO oldObjectVersion,
            int userId)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        logDeletedRow(con, adjustmentEvent, oldObjectVersion, userId, null);
    }
    
    public static void logDeletedRow(
            Connection con,
            AdjustmentEvent adjustmentEvent,
            IBillingDO oldObjectVersion,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        
        if (oldObjectVersion == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                   + " Received a [NULL] Object argument.");
        }
        
        if (adjustmentEvent.getIdAdjustmentEvents()== null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received an AdjustmentEvent that did not contain a"
                    + " unique database identifier. Ensure that the event row"
                    + " is inserted and has the Id included");
        }

        if (con == null)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Received a [NULL] Connection argument");
        }
        
        try
        {
            if (con.isValid(2) == false)
            {
                throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                        + " Received an invalid Connection object (lost connection?)");
            }            
        }
        catch (SQLException ex)
        {
            throw new IllegalArgumentException("BillingLogDAO::logDeletedRow"
                    + " Could not test Connection object to see if it was valid");
        }
        
        // If deleting a row, log every data point
        List<Diff> diffs = getDiffMethodOutputs(oldObjectVersion, false);
        
        insertLogs(con,
                BillingDiffAction.Deleted,
                "adjustmentEventId",
                adjustmentEvent.getIdAdjustmentEvents(),
                "adjustmentLog",
                diffs,
                userId,
                listener);
    }
    //</editor-fold>
    
    
    
    
    /**
     * Private method does the actual diff logging
     * @param con
     * @param eventIdentifierName
     * @param eventIdentifier
     * @param tableName
     * @param fieldName
     * @param diffs
     * @param userId
     * @param listener 
     */
    private static void insertLogs(
            Connection con,
            BillingDiffAction action,
            String eventIdentifierName, // e.g. detailOrderEventId, adjustmentEventId
            Integer eventIdentifier,
            String tableName,           // e.g. detailOrderLog
            List<Diff> diffs,           // what to write
            int userId,
            BillingDiffLogListener listener)   // callback to a listener to supply a custom user-readable description
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {

        String sql = "INSERT INTO `cssbilling`.`" + tableName + "`"
                + " (`" + eventIdentifierName + "`,`action`,`table`,`field`,`preValue`,"
                + "`postValue`,`isUserVisible`,`description`,`performedByUserId`)"
                + " VALUES"
                + " (?,?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            for (Diff diff : diffs)
            {
                // See if the caller is handling the descriptions for this object
                String description = null;
                if (listener != null)
                {
                    description = listener.diffFound(action, diff, userId);
                    if (description != null && description.isEmpty()) description = null;
                }
                
                int attemptCount = 0;
                while (attemptCount < RETRIES)
                {
                    try
                    {
                        pStmt.setInt(    1, eventIdentifier);
                        pStmt.setString( 2, action.name);
                        pStmt.setString( 3, diff.getTableName());
                        pStmt.setString( 4, diff.getFieldName());
                        pStmt.setString( 5, diff.getFirstVal());
                        pStmt.setString( 6, diff.getSecondVal());
                        pStmt.setBoolean(7, diff.isUserVisible);
                        pStmt.setString( 8, description);
                        pStmt.setInt(    9, userId);
                        
                        pStmt.execute();
                        
                        // Success; no need to retry
                        attemptCount = RETRIES;
                    }
                    catch (SQLException ex)
                    {
                        if (ex == null) throw new SQLException(ex);
                        if (ex.getMessage() != null && ex.getMessage().contains("exist"))
                        {
                            // One of the log tables doesn't exist
                            createDetailOrderLogTable(con);
                            createSubmissionLogTable(con);
                            createAdjustmentLogTable(con);
                        }
                    }
                    finally
                    {
                        ++attemptCount;
                    }
                } // insert retry loop

            } // diff loop

        }
        catch (SQLException ex)
        {
            String errorMessage = "BillingLogDAO::insertLogs: Error inserting log."
                    + " Diff action=" + action.name
                    + ", event identifer=" + eventIdentifierName
                    + ", id=" + eventIdentifier.toString()
                    + ", table=" + tableName;
            
            System.out.println(errorMessage);
            if (ex.getMessage() != null) System.out.println(errorMessage);
            SysLogDAO.Add(userId, errorMessage, ex.getMessage() != null ? ex.getMessage() : "");
            
        }
        
    }
    
    private static void createDetailOrderLogTable(Connection con)
            throws SQLException, NullPointerException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `cssbilling`.`detailOrderLog` ("
            + "  `id` BIGINT UNSIGNED NULL AUTO_INCREMENT,"
            + "  `detailOrderEventId` INT(11) UNSIGNED NOT NULL,"
            + "  `action` VARCHAR(10) NOT NULL,"
            + "  `table` VARCHAR(128) NOT NULL,"
            + "  `field` VARCHAR(128) NOT NULL,"
            + "  `preValue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `postvalue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `isUserVisible` TINYINT(1) NOT NULL,"
            + "  `description` VARCHAR(2048) NULL,"
            + "  `performedByUserId` INT(11) UNSIGNED NOT NULL,"
            + "  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
            + "  PRIMARY KEY (`id`),"
            + "  INDEX `FK_detailOrderLog_detailOrderEventId_idx` (`detailOrderEventId` ASC),"
            + "  INDEX `FK_detailOrderLog_userId_idx` (`performedByUserId` ASC),"
            + "  CONSTRAINT `FK_detailOrderLog_detailOrderEventId`"
            + "    FOREIGN KEY (`detailOrderEventId`)"
            + "    REFERENCES `cssbilling`.`detailOrderEvents` (`idDetailOrderEvents`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"                    
            + "  CONSTRAINT `FK_detailOrderLog_performedByUserId`"
            + "    FOREIGN KEY (`performedByUserId`)"
            + "    REFERENCES `css`.`users` (`idUser`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION)"
            + " ENGINE = InnoDB"
            + " DEFAULT CHARACTER SET = utf8"
            + " COLLATE = utf8_unicode_ci";
        
        try (Statement stmt = con.createStatement())
        {
            stmt.execute(sql);
        }
    }
    
    private static void createSubmissionLogTable(Connection con)
            throws SQLException, NullPointerException
    {
        
        String sql = "CREATE TABLE IF NOT EXISTS `cssbilling`.`submissionLog` ("
            + "  `id` BIGINT UNSIGNED NULL AUTO_INCREMENT,"
            + "  `submissionEventId` INT(11) NOT NULL,"
            + "  `action` VARCHAR(10) NOT NULL,"
            + "  `table` VARCHAR(128) NOT NULL,"
            + "  `field` VARCHAR(128) NOT NULL,"
            + "  `preValue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `postvalue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `isUserVisible` TINYINT(1) NOT NULL,"                
            + "  `description` VARCHAR(2048) NULL,"
            + "  `performedByUserId` INT(11) UNSIGNED NOT NULL,"
            + "  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
            + "  PRIMARY KEY (`id`),"
            + "  INDEX `FK_submissionLog_submissionEventId_idx` (`submissionEventId` ASC),"
            + "  INDEX `FK_submissionLog_userId_idx` (`performedByUserId` ASC),"
            + "  CONSTRAINT `FK_submissionLog_submissionEventId`"
            + "    FOREIGN KEY (`submissionEventId`)"
            + "    REFERENCES `cssbilling`.`submissionEvents` (`idSubmissionEvents`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"
            + "  CONSTRAINT `FK_submissionLog_performedByUserId`"
            + "    FOREIGN KEY (`performedByUserId`)"
            + "    REFERENCES `css`.`users` (`idUser`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION)"
            + " ENGINE = InnoDB"
            + " DEFAULT CHARACTER SET = utf8"
            + " COLLATE = utf8_unicode_ci;";
        
        try (Statement stmt = con.createStatement())
        {
            stmt.execute(sql);
        }        
    }
    
    private static void createAdjustmentLogTable(Connection con)
            throws SQLException, NullPointerException
    {
        String sql = "CREATE TABLE IF NOT EXISTS `cssbilling`.`adjustmentLog` ("
            + "  `id` BIGINT UNSIGNED NULL AUTO_INCREMENT,"
            + "  `adjustmentEventId` INT(11) UNSIGNED NOT NULL,"
            + "  `action` VARCHAR(10) NOT NULL,"
            + "  `table` VARCHAR(128) NOT NULL,"
            + "  `field` VARCHAR(128) NOT NULL,"
            + "  `preValue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `postvalue` VARCHAR(1028) NULL DEFAULT NULL,"
            + "  `isUserVisible` TINYINT(1) NOT NULL,"                
            + "  `description` VARCHAR(2048) NULL,"
            + "  `performedByUserId` INT(11) UNSIGNED NOT NULL,"
            + "  `date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,"
            + "  PRIMARY KEY (`id`),"
            + "  INDEX `FK_adjustmentLog_adjustmentEventId_idx` (`adjustmentEventId` ASC),"
            + "  INDEX `FK_adjustmentLog_userId_idx` (`performedByUserId` ASC),"
            + "  CONSTRAINT `FK_adjustmentLog_adjustmentEventId`"
            + "    FOREIGN KEY (`adjustmentEventId`)"
            + "    REFERENCES `cssbilling`.`adjustmentEvents` (`idAdjustmentEvents`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION,"
            + "  CONSTRAINT `FK_adjustmentLog_performedByUserId`"
            + "    FOREIGN KEY (`performedByUserId`)"
            + "    REFERENCES `css`.`users` (`idUser`)"
            + "    ON DELETE NO ACTION"
            + "    ON UPDATE NO ACTION)"
            + " ENGINE = InnoDB"
            + " DEFAULT CHARACTER SET = utf8"
            + " COLLATE = utf8_unicode_ci;";
        
        try (Statement stmt = con.createStatement())
        {
            stmt.execute(sql);
        }
    }
    
    /**
     * Runs any diff-annotated methods from a single object and builds a list of
     *  "diffs" of all of the outputs. Can be used to get all of the values
     *  when we want to log every value (e.g. when we delete a row, logging the
     *  values before it's removed).
     *  
     * If "asNewValue" is true, it sets the values to "secondVal" in the Diff
     *  objects. Otherwise to "firstVal".
     * 
     * @param object
     * @param asNewValue
     * @return 
     * @throws java.lang.reflect.InvocationTargetException 
     * @throws java.lang.IllegalAccessException 
     */
    private static List<Diff> getDiffMethodOutputs(IBillingDO object, boolean asNewValue)
            throws IllegalAccessException, InvocationTargetException
    {
        List<Diff> diffs = new ArrayList<>();
        
        Class objectClass = object.getClass();
        Method[] methods = objectClass.getMethods();
        for (Method method : methods)
        {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations)
            {
                Class<? extends Annotation> annotationType = annotation.annotationType();

                if (annotationType == Utility.Diff.class)
                {
                    String firstResultString = null;
                    String secondResultString = null;

                    Object resultObj = method.invoke(object, new Object[0]);
                    if (resultObj == null) continue;
                    String resultStr = getStringRepresentation(resultObj);
                    if (asNewValue)
                    {
                        secondResultString = resultStr;
                    }
                    else
                    {
                        firstResultString = resultStr;
                    }
                    Utility.Diff diff = (Utility.Diff)annotation;

                    boolean isUniqueIdentifier = false;
                    boolean isUserVisible = false;
                    String fieldName = "";
                    String tableName = object.getTableName();
                    
                    for (Method annotationMethod : annotationType.getDeclaredMethods())
                    {
                        Object methodOutputObj = annotationMethod.invoke(diff, new Object[0]);
                        switch (annotationMethod.getName())
                        {
                            case "fieldName":
                                // We need a field name for the log
                                if (methodOutputObj == null)
                                {
                                    System.out.println("Annotation name is incorrect."
                                            + " Please ensure that your annotation looks"
                                            + " like '@Diff(fieldName = \"name_of_column_changing\")' ");
                                    return null;                                
                                }
                                fieldName = methodOutputObj.toString();
                                break;

                            case "isUniqueId":
                                isUniqueIdentifier = (methodOutputObj != null
                                        && methodOutputObj instanceof Boolean
                                        && ((Boolean)methodOutputObj) == true);
                                break;

                            case "isUservisible":
                                isUserVisible = (methodOutputObj != null
                                        && methodOutputObj instanceof Boolean
                                        && ((Boolean)methodOutputObj) == true);
                                break;
                        }
                    }
                    diffs.add(new Diff(tableName, fieldName, firstResultString, secondResultString, isUniqueIdentifier, isUserVisible));
                }
            }
        }
        
        return diffs;
    }
    
    /**
     * Returns diff objects for each different result found between the
     *  two data objects.
     * 
     * @param firstObj
     * @param secondObj
     * @return 
     */
    private static List<Diff> getDiffs(IBillingDO firstObj, IBillingDO secondObj)
    {
        Class firstClass = firstObj.getClass();
        Class secondClass = secondObj.getClass();
        
        if (firstClass.equals(secondClass) == false)
        {
            System.out.println("BillingLogDAO.getDiffs::"
                    + " Object types did not match: "
                    + firstClass.toString() + " / "
                    + secondClass.toString());
            return null;
        }
        
        ArrayList<Diff> diffs = new ArrayList<>();
        
        try
        {
            Method[] methods = firstClass.getMethods();
            for (Method method : methods)
            {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations)
                {
                    Class<? extends Annotation> annotationClass = annotation.annotationType();
                    
                    if (annotationClass == Utility.Diff.class)
                    {
                        
                        Object firstResult = method.invoke(firstObj, new Object[0]);
                        Object secondResult = method.invoke(secondObj, new Object[0]);
                        
                        // Compare
                        
                        // Both [NULL], skip
                        if (firstResult == null && secondResult == null) continue;
                        
                        String firstResultString = "";
                        String secondResultString = "";
                        
                        if (firstResult != null) firstResultString = getStringRepresentation(firstResult);
                        if (secondResult != null) secondResultString = getStringRepresentation(secondResult);
                        
                        // Nothing changed, skip
                        if (firstResultString != null && secondResultString != null &&
                                firstResultString.equals(secondResultString))
                        {
                            continue;
                        }
                        
                        Utility.Diff diff = (Utility.Diff)annotation;
                        
                        boolean isUniqueIdentifier = false;
                        boolean isUserVisible = false;
                        String fieldName = "";
                        String tableName = firstObj.getTableName();
                        
                        // Break out each part of the annotation
                        for (Method annotationMethod : annotationClass.getDeclaredMethods())
                        {
                            Object annotationMethodValue = annotationMethod.invoke(diff, new Object[0]);
                            
                            switch (annotationMethod.getName())
                            {
                                
                                case "fieldName":
                                    // We need a field name for the log
                                    if (annotationMethodValue == null)
                                    {
                                        System.out.println("Annotation name is incorrect."
                                                + " Please ensure that your annotation looks"
                                                + " like '@Diff(fieldName = \"name_of_column_changing\")' ");
                                        return null;                                
                                    }
                                    fieldName = annotationMethodValue.toString();                                    
                                    break;
                                    
                                case "isUniqueId":
                                    isUniqueIdentifier = (annotationMethodValue != null
                                            && annotationMethodValue instanceof Boolean
                                            && ((Boolean)annotationMethodValue) == true);
                                    break;
                                    
                                case "isUserVisible":
                                    isUserVisible = (annotationMethodValue != null
                                            && annotationMethodValue instanceof Boolean
                                            && ((Boolean)annotationMethodValue) == true);
                                    break;
                            }
                        }
                        
                        diffs.add(new Diff(tableName, fieldName, firstResultString, secondResultString, isUniqueIdentifier, isUserVisible));
                    }
                }
            }
        }
        catch (IllegalAccessException ex)
        {
            System.out.println("Illegal Access Exception performing diff: " + ex.getMessage());
            return null;
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println("Illegal Argument Exception performing diff."
                    + " This is likely because the annotation was put on a setter method by accident!: " + ex.getMessage());
            return null;
        }
        catch (SecurityException ex)
        {
            System.out.println("Illegal Access Exception performing diff: " + ex.getMessage());
            return null;
        }
        catch (Exception ex)
        {
            System.out.println("General Exception performing diff: " + ex.getMessage());
            return null;            
        }
        return diffs;
    }
    
    
    /**
     * Called by all new log types: checks to see if there's a unique identifier
     *  in the new object. If so, it just creates a single log row since there's
     *  no reason to log every data point for a new record.
     * 
     *  If there's no unique identifier, it will log everything so we can track
     *   the insert.
     * 
     * @param con
     * @param obj
     * @param eventIdentifierName
     * @param eventIdentifier
     * @param tableName
     * @param userId
     * @param listener
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException 
     */
    private static void logNewDiffData(
            Connection con,
            IBillingDO obj,
            String eventIdentifierName,
            Integer eventIdentifier,
            String tableName,
            int userId,
            BillingDiffLogListener listener)
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
        // Get all of the diff outputs for the object
        List<Diff> allDiffs = getDiffMethodOutputs(obj, true);
        
        // What we're actually going to log
        List<Diff> writeDiffs = new ArrayList<>();
        
        Diff uniqueIdDiff = null;
        for (Diff diff : allDiffs)
        {
            if (diff.isUniqueIdentifier())
            {
                uniqueIdDiff = diff;
                break;
            }
        }
        
        // If there's a unique id as one of the diffs, just write that.
        // No need to log every data point for an inserted row
        if (uniqueIdDiff != null)
        {
            writeDiffs.add(uniqueIdDiff);
        }
        else
        {
            writeDiffs = allDiffs;
        }
        
        if (writeDiffs.isEmpty() == false)
        {
            insertLogs(con,
                    BillingDiffAction.Added,
                    eventIdentifierName,
                    eventIdentifier,
                    tableName,
                    writeDiffs,
                    userId,
                    listener);
        }

    }
    
    /**
     * Returns a string representation of the supplied object.
     * Handles some return types that cannot be obtained by a toString(), so 
     *  that blob data types can be used in diffs.
     * 
     * This method cannot catch every scenario, but should probably handle basic
     *  array types.
     * @param obj
     * @return String value
     */
    private static String getStringRepresentation(Object obj)
    {
        String value;
        if (obj instanceof byte[])
        {
            byte[] byteRepresentation = (byte[])obj;
            value = new String(byteRepresentation);
        }
        else if (obj instanceof int[])
        {
            int[] intRepresentation = (int[])obj;
            value = Arrays.toString(intRepresentation);
        }
        else // Regular string
        {
            value = obj.toString();
        }
        
        return value;
    }    
}
