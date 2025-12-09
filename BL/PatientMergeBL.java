package BL;

import DAOS.DiffLogDAO;
import DAOS.LogDAO;
import DAOS.OrderDAO;
import DAOS.PatientCommentDAO;
import DAOS.PatientDAO;
import DAOS.SubscriberCommentDAO;
import DAOS.SubscriberDAO;
import DAOS.SysLogDAO;
import DOS.OrderEntryLog;
import DOS.Orders;
import DOS.PatientComment;
import DOS.Patients;
import DOS.Subscriber;
import DOS.SubscriberComment;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 * @date:   Apr 9, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: BL
 * @file name: PatientMergeBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class PatientMergeBL 
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    final static String mergeQuery = 
            "UPDATE orders"
            + " SET `patientId` = ?"
            + " WHERE `patientId` = ?";
    
    final static String removeOldQuery = 
            "DELETE FROM patients WHERE `idPatients` = ?";
    
    public enum OrderMergeType
    {
        PatientOnly,        // Only replaces the patientId
        PatientAndInsurance // Replaces the patientId, subscriberId, and all
                            // insurance information on the order (policy #,
                            // group#, medicare, medicaid, etc..)
    }

    public PatientMergeBL()
    {
        
    }
    
    /**
     * 
     * @param oldPatient The patient to be removed and replaced.
     * @param mergedPatient The patient that will be replacing oldPatient.
     * @return 
     */
    @Deprecated
    public boolean MergePatients(Patients oldPatient, Patients mergedPatient) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        if( oldPatient == null || oldPatient.getIdPatients() == null ||
            mergedPatient == null || mergedPatient.getIdPatients() == null ||
            oldPatient.getIdPatients().intValue() == mergedPatient.getIdPatients().intValue())
            return false;
        
        PreparedStatement mergeStmt = con.prepareStatement(mergeQuery);
        
        mergeStmt.setInt(1, mergedPatient.getIdPatients());
        mergeStmt.setInt(2, oldPatient.getIdPatients());
        mergeStmt.executeUpdate();
        mergeStmt.close();
        
        // Delete the patient from the Patients table
        PreparedStatement delStmt = con.prepareStatement(removeOldQuery);
        
        delStmt.setInt(1, oldPatient.getIdPatients());
        delStmt.executeUpdate();
        delStmt.close();
        
        // Back it up
        oldPatient.setActive(false);
        oldPatient.setDeactivatedDate(new Date());
        return InsertPatientIntoBackup(oldPatient);
    }
    
    /**
     *  Used to merge patients and optionally orders. Logs actions to order
     *  production log, orderMergeLog, and purges patients/subscribers that
     *  are no longer used to the purgedSubscribers / purgedPatients table(s).
     * 
     *  Prompts users to append patient comments
     * 
     *  If the orderIds HashMap is null or empty, no orders are merged.
     *  Otherwise, each order in the map is merged to the new patient information.
     *  If the boolean value associated with the orderId is false, only the
     *  patient information is merged, otherwise, all of the order information
     *  (subscriber, insurance, policy/group/medicare/medicaid) is merged.
     * 
     * @param oldPatientId
     * @param newPatientId
     * @param newSubscriber
     * @param orderIds HashMap<orderId --> boolean> if boolean true, merges insurance information
     * @param userId
     * @return Arraylist of strings representing actions that were performed
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public ArrayList<String> MergePatients(int oldPatientId, int newPatientId,
            Subscriber newSubscriber,
            HashMap<Integer, Boolean> orderIds, int userId)
            throws SQLException, IllegalArgumentException
    {
        if (orderIds == null) orderIds = new HashMap<>();
        
        ArrayList<String> actionsPerformed = new ArrayList<>();
        
        // Handle the order merges first (if necessary) so we can try to
        // purge unused patients/subscribers later without constraint errors
        for (Integer orderId : orderIds.keySet())
        {
            if (orderId != null)
            {
                boolean mergeInsurance = (boolean)orderIds.get(orderId);
                if (mergeInsurance)
                {
                    try
                    {
                        actionsPerformed.addAll(MergeOrder(OrderMergeType.PatientAndInsurance,
                            orderId, newPatientId, newSubscriber, userId));     
                    }
                    catch (Exception ex)
                    {
                        SysLogDAO.Add(userId, "Error merging orderId " + orderId + "(patient and insurance)", ex.getMessage());
                    }
                }
                else
                {
                    try
                    {
                        actionsPerformed.addAll(MergeOrder(OrderMergeType.PatientOnly,
                                orderId, newPatientId, newSubscriber, userId));
                    }
                    catch (Exception ex)
                    {
                        SysLogDAO.Add(userId, "Error merging orderId " + orderId + "(just patient)", ex.getMessage());
                    }
                }
            }
        }
        
        PatientDAO pdao = new PatientDAO();
        SubscriberDAO sdao = new SubscriberDAO();
        
        // Patient -------------------------------------------------------------
        
        // Will hold our updated patient record:
        Patients oldPatient = pdao.GetPatientById(oldPatientId);
        
        // Check the MRN Number on old and new patients. If old patient has MRN, assign to new patient
        Patients newPatient = pdao.GetPatientById(newPatientId);
        
        // If the record being merged into has no MRN, check for an MRN on the record to merge
        if (newPatient.getPatientMRN() == null | (newPatient.getPatientMRN() != null && newPatient.getPatientMRN().isEmpty()))
        {
            if (oldPatient.getPatientMRN() != null && !oldPatient.getPatientMRN().isEmpty())
            {
                Patients oldPatient_0 = pdao.GetPatientById(oldPatientId);
                Patients newPatient_0 = pdao.GetPatientById(newPatientId);
                
                newPatient.setPatientMRN(oldPatient.getPatientMRN());
                oldPatient.setPatientMRN(null);
                
                if (!pdao.UpdatePatient(oldPatient))
                    System.out.println("Unable to update old patient record to remove PatientMRN.");
                else if (!pdao.UpdatePatient(newPatient))
                    System.out.println("Unable to update new patient record to add old PatientMRN.");
                else
                    actionsPerformed.add("Assigned patient MRN to main record.");
                
                // Logging Patient MRN change on old record
                DiffLogDAO.Insert("patientLog", "patientId",
                        oldPatientId, oldPatient_0, oldPatient,
                        "Merge Changed PatientMRN", userId);
                
                // Logging Patient MRN change on new record
                DiffLogDAO.Insert("patientLog", "patientId",
                        newPatientId, newPatient_0, newPatient,
                        "Merge Changed PatientMRN", userId);
            }
        }
        else // The record being merged into has an MRN, just set the MRN on the merge record to null
        {
            Patients oldPatient_0 = pdao.GetPatientById(oldPatientId);
                oldPatient.setPatientMRN(null);
                
            if (!pdao.UpdatePatient(oldPatient))
                System.out.println("Unable to update old patient record to remove PatientMRN.");
            
            // Logging Patient MRN change on old record
            DiffLogDAO.Insert("patientLog", "patientId",
                    oldPatientId, oldPatient_0, oldPatient,
                    "Merge Changed PatientMRN", userId);
        }
        
        // Check for patient comments before deactivating
        PatientCommentDAO pcdao = new PatientCommentDAO();
        PatientComment oldPatientComment = pcdao.GetCommentByPatientID(oldPatientId);

        // If there's actually a comment defined
        if (oldPatientComment != null && oldPatientComment.getId() != null && oldPatientComment.getId() > 0 &&
                oldPatientComment.getComment() != null && oldPatientComment.getComment().trim().isEmpty() == false)
        {
            Object[] options = { "Yes", "No" };
            int response = JOptionPane.showOptionDialog(null, "The patient being merged has a comment attached:"
                    + "\n\n'" + oldPatientComment.getComment() + "'\n\n"
                    + "Would you like to append this to the main patient record?",
                    "Apppend comment?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null, options, options[1]);
            boolean success = true;
            if (response == JOptionPane.YES_OPTION)
            {
                String concatComment = "";
                PatientComment newPatientComment = pcdao.GetCommentByPatientID(newPatientId);
                if (newPatientComment == null) newPatientComment = new PatientComment();
                if (newPatientComment.getId() != null && newPatientComment.getId() > 0)
                {
                    concatComment = newPatientComment.getComment();
                    if (concatComment == null) concatComment = "";
                }
                concatComment += (oldPatientComment.getComment() != null? "\n" + oldPatientComment.getComment() : "");
                newPatientComment.setComment(concatComment);
                if (newPatientComment.getId() == null || newPatientComment.getId() == 0)
                {
                    newPatientComment.setIdpatients(newPatientId);
                    success = pcdao.Insert(newPatientComment);
                    actionsPerformed.add("Moved patient comment to main record");
                }
                else
                {
                    success = pcdao.Update(newPatientComment);
                    actionsPerformed.add("Appended patient comment to main record");
                }
            }
            if (success == false)
            {
                throw new SQLException("Unable to insert/update patient comment");
            }
        }
            
        // Purge the patient
        //PurgePatient(oldPatient, userId, "Purged");

        // Deactivate the patient
        DeactivatePatient(oldPatient, userId, "Patient Deactivated");
        actionsPerformed.add("Merged patient deactivated.");

        // Subscriber ----------------------------------------------------------
        
        Subscriber oldSubscriber = sdao.GetSubscriberById(oldPatient.getSubscriber());

        // Check for subscriber comments
        SubscriberCommentDAO scdao = new SubscriberCommentDAO();
        SubscriberComment oldSubscriberComment = scdao.GetCommentBySubscriberAR(oldSubscriber.getArNo());

        // If there's actually a comment defined
        if (oldSubscriberComment != null && oldSubscriberComment.getId() != null && oldSubscriberComment.getId() > 0 &&
                oldSubscriberComment.getComment() != null && oldSubscriberComment.getComment().trim().isEmpty() == false)
        {
            Object[] options = { "Yes", "No" };
            int response = JOptionPane.showOptionDialog(null, "The subscriber being merged has a comment attached:"
                    + "\n\n'" + oldSubscriberComment.getComment() + "'\n\n"
                    + "Would you like to append this to the main subscriber record?",
                    "Append comment?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null, options, options[1]);
            boolean success = true;
            if (response == JOptionPane.YES_OPTION)
            {
                String concatComment = "";
                SubscriberComment newSubscriberComment = scdao.GetCommentBySubscriberAR(newSubscriber.getArNo());

                if (newSubscriberComment == null || newSubscriberComment.getId() == null ||
                        newSubscriberComment.getId() == 0)
                {
                    newSubscriberComment = new SubscriberComment();
                    newSubscriberComment.setSubscriber(newSubscriber.getArNo());
                }
                else
                {
                    concatComment = newSubscriberComment.getComment();
                    if (concatComment == null) concatComment = "";
                }

                concatComment += (oldSubscriberComment.getComment() != null? "\n" + oldSubscriberComment.getComment() : "");
                newSubscriberComment.setComment(concatComment);
                if (newSubscriberComment.getSubscriber() == null || newSubscriberComment.getSubscriber().isEmpty())
                {
                    success = scdao.Insert(newSubscriberComment);
                    actionsPerformed.add("Moved subscriber comment to main record");
                }
                else
                {
                    success = scdao.UpdateComment(newSubscriberComment);
                    actionsPerformed.add("Appended subscriber comment to main record");
                }
            }
            if (success == false)
            {
                throw new SQLException("Unable to insert/update subscriber comment");
            }
        } // Subscriber comment exists

        DeactivateSubscriber(oldSubscriber, userId, "Subscriber deactivated");
        actionsPerformed.add("Merged subscriber deactivated.");

        return actionsPerformed;
    }
    
    /**
     * True if any order is currently assigned to the specified patient
     * @param patientId
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean PatientUsed(int patientId) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT COUNT(*) FROM orders WHERE patientId = " + patientId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        Integer usedCount = 0;
        if (rs.next())
        {
            usedCount = rs.getInt(1);
        }
        return usedCount > 0;
    }
    
    /**
     * Checks to see if subscriber is currently assigned to any
     *  patient or order
     * @param subscriberId
     * @return 
     */
    public boolean SubscriberUsed(int subscriberId) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // All references on patients, and all references on orders
        String sql = "SELECT SUM(usageCount) FROM "
                    + "("
                    + "  SELECT COUNT(*) AS usageCount FROM patients WHERE subscriber = " + subscriberId
                    + "  UNION "
                    + "  SELECT COUNT(*) AS usageCount FROM orders WHERE subscriberId = " + subscriberId
                    + ") AS usageTotal;";
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        Integer usedCount = 0;
        if (rs.next())
        {
            usedCount = rs.getInt(1);
        }
        return usedCount > 0;
    }
    
    /**
     * Replaces data on the order with the provided patient/subscriber/insurance
     *  information depending on which merge type was specified. Logs actions
     *  taken.
     * 
     * @param mergeType What actions to perform
     * @param orderId
     * @param patientId
     * @param subscriber Can be NULL for merge types of "Patient"
     * @param userId 
     * @return  ArrayList of strings representing actions taken (messages for user)
     * @throws java.sql.SQLException 
     */
    public ArrayList<String> MergeOrder(OrderMergeType mergeType, int orderId,
            int patientId, Subscriber subscriber, int userId)
            throws SQLException, IllegalArgumentException
    {
        if (orderId < 1) throw new IllegalArgumentException("MergeOrder given an invalid orderId: " + orderId);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        OrderDAO odao = new OrderDAO();
        Orders order = odao.GetOrderById(orderId);
        
        if (order == null || order.getIdOrders() == null || order.getIdOrders() == 0)
        {
            throw new SQLException("Could not load order for Id:" + orderId);
        }
        
        if (mergeType == OrderMergeType.PatientAndInsurance &&
                subscriber == null || subscriber.getIdSubscriber() == null ||
                subscriber.getIdSubscriber() == 0)
        {
            throw new IllegalArgumentException("Merge order recieved null/blank subscriber for orderId " + orderId);
        }
        
        // Keep track of what was done for the user
        ArrayList<String> actionsPerformed = new ArrayList<>();
        
        // Log patient change
        LogDAO ldao = new LogDAO();
        OrderEntryLog log = new OrderEntryLog();
        log.setAction("Patient merged");
        log.setIdPatients(patientId);
        log.setPrePatient(order.getPatientId());
        log.setIdOrders(orderId);
        log.setIdUser(userId);
        boolean success = ldao.InsertLog(LogDAO.LogTable.OrderEntry, log);
        if (success == false)
        {
            throw new SQLException("Could not log patient change for orderId " +
                    orderId + " new patientId " + patientId);
        }
        
        // Merge patient
        order.setPatientId(patientId);
        
        actionsPerformed.add("Patient information on accession " + order.getAccession() + " now points to new record.");
        
        // Merge Insurance
        if (mergeType == OrderMergeType.PatientAndInsurance)
        {
            // Set the new insurance fields
            order.setSubscriberId(subscriber.getIdSubscriber());
            order.setInsurance(subscriber.getInsurance());
            order.setSecondaryInsurance(subscriber.getSecondaryInsurance());
            order.setPolicyNumber(subscriber.getPolicyNumber());
            order.setGroupNumber(subscriber.getGroupNumber());            
            order.setSecondaryPolicyNumber(subscriber.getSecondaryPolicyNumber());
            order.setSecondaryGroupNumber(subscriber.getSecondaryGroupNumber());
            order.setMedicareNumber(subscriber.getMedicareNumber());
            order.setMedicaidNumber(subscriber.getMedicaidNumber());
            
            log = new OrderEntryLog();
            log.setAction("Insurance information merged");
            log.setIdPatients(patientId);
            log.setPrePatient(order.getPatientId());
            log.setIdOrders(orderId);
            log.setIdUser(userId);
            success = ldao.InsertLog(LogDAO.LogTable.OrderEntry, log);
            if (success == false)
            {
                throw new SQLException("Could not log insurance change for orderID " +
                        orderId + " new patientId " + patientId);
            }
            String subscriberAr = subscriber.getArNo();
            if (subscriberAr != null)
            {
                subscriberAr = "(" + subscriberAr + ")";
            }
            else
            {
                subscriberAr = "";
            }
            actionsPerformed.add("Subscriber information on " 
                    + order.getAccession() + " now points to new subscriber " + subscriberAr);
        }

        // Get the existing order (pre-changes)
        Orders oldOrder = odao.GetOrderById(orderId);        

        // Update the existing order
        success = odao.UpdateOrder(order);
        if (success == false)
        {
            throw new SQLException("Could not update order patient record. OrderId " +
                    orderId + " new patientId " + patientId);
        }

        // Log all differences between the existing
        // data and the new data into the orderMergeLog
        DiffLogDAO.Insert(
                "orderMergeLog",
                "orderId",
                order.getIdOrders(),
                oldOrder,
                order,
                null, // Description
                userId);
        
        return actionsPerformed;
    }
    
    /**
     * Removes a subscriber comment from its table and inserts into purgedSubscriberComments
     *  purgedSubscriberComment
     * @param subscriber
     * @param comment
     * @param userId
     * @param purgeComment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void PurgeSubscriberComment(Subscriber subscriber, SubscriberComment comment, int userId, String purgeComment) throws SQLException, IllegalArgumentException
    {
        if (comment == null || comment.getId() == null || comment.getId() == 0)
        {
            throw new IllegalArgumentException("PurgeSubscriberComment received null/blank subscriber comment!");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

        
        String sql = "SHOW TABLES LIKE 'purgedSubscriberComments'";
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        // If not, create it
        if (!rs.next())
        {
            sql = "CREATE TABLE `purgedSubscriberComments` (	" +
                "   `idSubscriber` int(10) unsigned NOT NULL," +
                "   `comment` TEXT NULL," +
                "   `purgeComment` VARCHAR(255)" +
                " ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            pStmt.execute(sql);            
        }

        sql = "INSERT INTO `purgedSubscriberComments` (`idSubscriber`,`comment`,`purgeComment`)"
                + " VALUES (?, ?, ?)";
        
        pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, subscriber.getIdSubscriber());
        SQLUtil.SafeSetString(pStmt, ++i, comment.getComment());
        SQLUtil.SafeSetString(pStmt, ++i, purgeComment);
        int affectedRows = pStmt.executeUpdate();
        if (affectedRows < 1) throw new SQLException("Unable to insert purged subscriber comment row. SQL=" + sql);

        sql = "DELETE FROM subscriberComment WHERE subscriberId = ?";
        pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, subscriber.getIdSubscriber());
        pStmt.execute();
        
    }
    
    /**
     * Marks a patient as deactivated and writes to patientLog (diff log)
     * @param patient
     * @param userId
     * @param comment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void DeactivatePatient(Patients patient, int userId, String comment) throws SQLException, IllegalArgumentException
    {
        if (patient == null || patient.getIdPatients() == null ||
                patient.getIdPatients() == 0)
        {
            throw new IllegalArgumentException("PatientMergeBL::DeactivatePatient: Received a null/blank patient object");
        }
        
        patient.setActive(false);
        patient.setDeactivatedDate(new Date());
        
        PatientDAO pdao = new PatientDAO();
        Patients preUpdatePatient = pdao.GetPatientById(patient.getIdPatients());
        boolean success = pdao.UpdatePatient(patient);
        
        if (success == false)
        {
            throw new SQLException("Unable to update patientId " + patient.getIdPatients() + " and set inactive");
        }

        // Log the patient's subscriber change
        DiffLogDAO.Insert(
                "patientLog",
                "patientId",
                patient.getIdPatients(),
                preUpdatePatient,       // Before deactivation
                patient,                // After deactivation
                comment,                // Description
                userId);
    }
    
    /**
     * Marks a subscriber as deactivated and writes to subscriberLog (diff log)
     * @param subscriber
     * @param userId
     * @param comment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void DeactivateSubscriber(Subscriber subscriber, int userId, String comment) throws SQLException, IllegalArgumentException
    {
        if (subscriber == null || subscriber.getIdSubscriber()== null ||
                subscriber.getIdSubscriber() == 0)
        {
            throw new IllegalArgumentException("PatientMergeBL::DeactivateSubscriber: Received a null/blank subscriber object");
        }
        
        subscriber.setActive(false);
        subscriber.setDeactivatedDate(new Date());
        
        SubscriberDAO sdao = new SubscriberDAO();
        Subscriber preUpdateSubscriber = sdao.GetSubscriberById(subscriber.getIdSubscriber());
        boolean success = sdao.UpdateSubscriber(subscriber);
        
        if (success == false)
        {
            throw new SQLException("Unable to update subscriberId " + subscriber.getIdSubscriber() + " and set inactive");
        }

        // Log the subscriber change
        DiffLogDAO.Insert(
                "subscriberLog",
                "subscriberId",
                subscriber.getIdSubscriber(),
                preUpdateSubscriber, // Existing old subscriber
                subscriber,          // Modified version
                comment,             // Description
                userId);       
    }
    
    /**
     * Removes a patient comment from its table and inserts into purgedPatientComments
     * @param comment
     * @param userId
     * @param purgeComment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void PurgePatientComment(PatientComment comment, int userId, String purgeComment) throws SQLException, IllegalArgumentException
    {
        if (comment == null || comment.getId() == null || comment.getId() == 0)
        {
            throw new IllegalArgumentException("PurgePatientComment received null/blank patient comment!");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SHOW TABLES LIKE 'purgedPatientComments'";
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        // If not, create it
        if (!rs.next())
        {
            sql = "CREATE TABLE `purgedPatientComments` (	" +
                "   `idPatients` int(10) unsigned NOT NULL," +
                "   `comment` TEXT NULL," +
                "   `purgeComment` VARCHAR(255)" +
                " ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            pStmt.execute(sql);            
        }

        sql = "INSERT INTO `purgedPatientComments` (`idpatients`,`comment`,`purgeComment`)"
                + " VALUES (?, ?, ?)";
        
        pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, comment.getIdpatients());
        SQLUtil.SafeSetString(pStmt, ++i, comment.getComment());
        SQLUtil.SafeSetString(pStmt, ++i, purgeComment);
        int affectedRows = pStmt.executeUpdate();
        if (affectedRows < 1) throw new SQLException("Unable to insert purged patient comment row. SQL=" + sql);

        sql = "DELETE FROM patientComment WHERE idpatients = ?";
        pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, comment.getIdpatients());
        pStmt.execute();
        
    }
    
    /**
     * Writes the supplied subscriber to the purgedSubscribers table (creates table
     *  if it doesn't exist), deletes subscriber record from the subscriber table,
     *  and logs the purge in the orderEntryLog
     * 
     *  NOTE: all references to this subscriber on patients/orders must be
     *   removed prior to purging the subscriber, otherwise the constraint will
     *   fail.
     * @param subscriber
     * @param userId
     * @param purgeComment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void PurgeSubscriber(Subscriber subscriber, int userId, String purgeComment) throws SQLException, IllegalArgumentException
    {
        if (subscriber == null || subscriber.getIdSubscriber() == null || 
                subscriber.getIdSubscriber() == 0)
        {
            throw new IllegalArgumentException("PurgedSubscriber received a null/blank subscriber record!");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // Does the table exist?
        String sql = "SHOW TABLES LIKE 'purgedSubscribers'";
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        // If not, create it
        if (!rs.next())
        {
            sql = "CREATE TABLE `purgedSubscribers` (	" +
                "   `idSubscriber` int(10) unsigned NOT NULL," +
                "   `arNo` varchar(50) NOT NULL," +
                "   `lastName` varchar(50) NOT NULL," +
                "   `firstName` varchar(50) NOT NULL," +
                "   `middleName` varchar(50) DEFAULT NULL," +
                "   `sex` varchar(50) DEFAULT NULL," +
                "   `ssn` varchar(50) DEFAULT NULL," +
                "   `dob` datetime DEFAULT NULL," +
                "   `addressStreet` varchar(50) DEFAULT NULL," +
                "   `addressStreet2` varchar(50) DEFAULT NULL," +
                "   `addressCity` varchar(50) DEFAULT NULL," +
                "   `addressState` varchar(50) DEFAULT NULL," +
                "   `addressZip` varchar(50) DEFAULT NULL," +
                "   `phone` varchar(50) DEFAULT NULL," +
                "   `workPhone` varchar(50) DEFAULT NULL," +
                "   `insurance` int(10) unsigned NOT NULL," +
                "   `secondaryInsurance` int(10) unsigned DEFAULT NULL," +
                "   `policyNumber` varchar(45) DEFAULT NULL," +
                "   `groupNumber` varchar(45) DEFAULT NULL," +
                "   `secondaryPolicyNumber` varchar(45) DEFAULT NULL," +
                "   `secondaryGroupNumber` varchar(45) DEFAULT NULL," +
                "   `medicareNumber` varchar(45) DEFAULT NULL," +
                "   `medicaidNumber` varchar(45) DEFAULT NULL," +
                "   `purgedBy` INT(11) NOT NULL," +
                "   `purgedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                "   `purgeComment` VARCHAR(255) DEFAULT NULL" +
                " ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            pStmt.execute(sql);
        }
        
        // Insert our subscriber data
        sql = "INSERT INTO `purgedSubscribers` (`idsubscriber`,"
                + "`arNo`, `lastName`, `firstName`, `middleName`,"
                + "`sex`, `ssn`, `dob`, `addressStreet`, `addressStreet2`,"
                + "`addressCity`, `addressState`, `addressZip`, `phone`,"
                + "`workPhone`, `insurance`, `secondaryInsurance`, "
                + "`policyNumber`, `groupNumber`, `secondaryPolicyNumber`,"
                + "`secondaryGroupNumber`, `medicareNumber`,"
                + "`medicaidNumber`, `purgedBy`, `purgeComment`) VALUES "
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        pStmt = con.prepareStatement(sql);
        int i = 0;
        pStmt.setInt(++i, subscriber.getIdSubscriber());
        pStmt.setString(++i, subscriber.getArNo());
        pStmt.setString(++i, subscriber.getLastName());
        pStmt.setString(++i,  subscriber.getFirstName());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getMiddleName());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getSex());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getSsn());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, subscriber.getDob());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getAddressStreet());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getAddressStreet2());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getAddressCity());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getAddressState());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getAddressZip());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getPhone());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getWorkPhone());
        pStmt.setInt(++i, subscriber.getInsurance());
        pStmt.setInt(++i, subscriber.getSecondaryInsurance());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getPolicyNumber());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getGroupNumber());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getSecondaryPolicyNumber());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getSecondaryGroupNumber());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getMedicareNumber());
        SQLUtil.SafeSetString(pStmt, ++i, subscriber.getMedicaidNumber());
        pStmt.setInt(++i, userId);
        pStmt.setString(++i, purgeComment);

        int affectedRows = pStmt.executeUpdate();
        if (affectedRows < 1) throw new SQLException("Unable to insert purged subscriber row. SQL=" + sql);

        sql = "DELETE FROM subscriber WHERE idsubscriber = ?";
        pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, subscriber.getIdSubscriber());
        pStmt.execute();
        
        //if (success == false) throw new SQLException("Unable to delete subscriberId " + subscriber.getIdSubscriber());
        
        LogDAO ldao = new LogDAO();
        OrderEntryLog log = new OrderEntryLog();
        
        log.setAction("Subscriber Purged");
        log.setIdSubscriber(subscriber.getIdSubscriber());
        log.setIdUser(i);
        ldao.InsertLog(LogDAO.LogTable.OrderEntry, log);
    }
    
    /**
     * Writes the supplied patient to the purgedPatients table (creates table
     *  if it doesn't exist), deletes patient record from the patient table,
     *  and logs the purge in the orderEntryLog
     * 
     *  NOTE: all references to this patient on patients/orders must be
     *   removed prior to purging the subscriber, otherwise the constraint will
     *   fail.
     * @param patient
     * @param userId
     * @param purgeComment
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void PurgePatient(Patients patient, int userId, String purgeComment) throws SQLException, IllegalArgumentException
    {
        if (patient == null || patient.getIdPatients()== null || 
                patient.getIdPatients() == 0)
        {
            throw new IllegalArgumentException("PurgedSubscriber received a null/blank subscriber record!");
        }
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        
        // Does the table exist?
        String sql = "SHOW TABLES LIKE 'purgedPatients'";
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        // If not, create it
        if (!rs.next())
        {
            sql = "CREATE TABLE `purgedPatients` (" +
            "   `idPatients` INT(10) UNSIGNED NOT NULL," +
            "   `arNo` VARCHAR(50) NOT NULL," +
            "   `lastName` VARCHAR(50) NOT NULL," +
            "   `firstName` VARCHAR(50) NOT NULL," +
            "   `middleName` VARCHAR(50) DEFAULT NULL," +
            "   `sex` VARCHAR(50) DEFAULT NULL," +
            "   `ssn` VARCHAR(50) DEFAULT NULL," +
            "   `dob` DATETIME DEFAULT NULL," +
            "   `addressStreet` VARCHAR(50) DEFAULT NULL," +
            "   `addressStreet2` VARCHAR(50) DEFAULT NULL," +
            "   `addressCity` VARCHAR(50) DEFAULT NULL," +
            "   `addressState` VARCHAR(50) DEFAULT NULL," +
            "   `addressZip` VARCHAR(50) DEFAULT NULL," +
            "   `phone` VARCHAR(50) DEFAULT NULL," +
            "   `workPhone` VARCHAR(50) DEFAULT NULL," +
            "   `subscriber` INT(10) UNSIGNED DEFAULT NULL," +
            "   `relationship` VARCHAR(50) DEFAULT NULL," +
            "   `counselor` INT(10) UNSIGNED DEFAULT NULL," +
            "   `species` INT(10) UNSIGNED DEFAULT '65'," +
            "   `height` INT(11) DEFAULT NULL," +
            "   `weight` INT(11) DEFAULT NULL," +
            "   `ethnicity` VARCHAR(50) DEFAULT 'Other'," +
            "   `smoker` bit(1) DEFAULT b'0'," +
            "   `active` bit(1) NOT NULL DEFAULT b'1'," +
            "   `deactivatedDate` TIMESTAMP NULL DEFAULT NULL," +
            "   `patientMRN` VARCHAR(45) DEFAULT NULL," +
            "   `DOI` DATETIME DEFAULT NULL," +
            "   `EOA` VARCHAR(20) DEFAULT 'None'," +
            "   `purgedBy` INT(11) NOT NULL," +
            "   `purgedDate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
            "   `purgeComment` VARCHAR(255) DEFAULT NULL" +
            " ) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
            pStmt.execute(sql);
        }
        
        // Insert our patient data
        sql = "INSERT INTO `purgedPatients` (`idpatients`,"
                + "`arNo`, `lastName`, `firstName`, `middleName`,"
                + "`sex`, `ssn`, `dob`, `addressStreet`, `addressStreet2`,"
                + "`addressCity`, `addressState`, `addressZip`, `phone`,"
                + "`workPhone`, `subscriber`, `relationship`, "
                + "`counselor`, `species`, `height`, `weight`,"
                + "`ethnicity`, `smoker`, `active`,"
                + "`deactivatedDate`, `patientMRN`, `DOI`, `EOA`, `purgedBy`,"
                + "`purgeComment`) VALUES "
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        pStmt = con.prepareStatement(sql);
        
        int i = 0;
        pStmt.setInt(++i, patient.getIdPatients());
        pStmt.setString(++i, patient.getArNo());
        pStmt.setString(++i, patient.getLastName());
        pStmt.setString(++i,  patient.getFirstName());
        
        SQLUtil.SafeSetString(pStmt, ++i, patient.getMiddleName());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getSex());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getSsn());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, patient.getDob());        
        SQLUtil.SafeSetString(pStmt, ++i, patient.getAddressStreet());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getAddressStreet2());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getAddressCity());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getAddressState());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getAddressZip());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getPhone());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getWorkPhone());
        pStmt.setInt(++i,  patient.getSubscriber());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getRelationship());
        pStmt.setInt(++i, patient.getCounselor());
        pStmt.setInt(++i,  patient.getSpecies());
        pStmt.setInt(++i,  patient.getHeight());
        pStmt.setInt(++i,  patient.getWeight());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getEthnicity());
        pStmt.setBoolean(++i,  patient.getSmoker());
        pStmt.setBoolean(++i,  patient.getActive());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, patient.getDeactivatedDate());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getPatientMRN());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, patient.getDOI());
        SQLUtil.SafeSetString(pStmt, ++i, patient.getEOA());
        pStmt.setInt(++i, userId);
        SQLUtil.SafeSetString(pStmt, ++i, purgeComment);
        
        int affectedRows = pStmt.executeUpdate();
        if (affectedRows < 1) throw new SQLException("Unable to insert purged patient row. SQL=" + sql);

        sql = "DELETE FROM patients WHERE idpatients = ?";
        pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, patient.getIdPatients());
        pStmt.execute();
        
        //if (success == false) throw new SQLException("Unable to delete patientId " + patient.getIdPatients());
        
        LogDAO ldao = new LogDAO();
        OrderEntryLog log = new OrderEntryLog();
        
        log.setAction("Patient purged");
        log.setIdPatients(patient.getIdPatients());
        log.setIdUser(i);
        ldao.InsertLog(LogDAO.LogTable.OrderEntry, log);
    }
    
    // One day, I'll create a DAO for this. 
    // If it ever has any other application then here, that is.
    @Deprecated
    private boolean InsertPatientIntoBackup(Patients patient) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        /*if(GetPatientIdByAR(patient.getArNo()) != 0)
         return UpdatePatient(patient);*/
        try {
            String stmt = "INSERT INTO patientsBackup ("
                    + " `arNo`,"
                    + " `lastName`,"
                    + " `firstName`,"
                    + " `middleName`,"
                    + " `sex`,"
                    + " `ssn`,"
                    + " `dob`,"
                    + " `addressStreet`,"
                    + " `addressStreet2`,"
                    + " `addressCity`,"
                    + " `addressState`,"
                    + " `addressZip`,"
                    + " `phone`,"
                    + " `workPhone`,"
                    + " `subscriber`,"
                    + " `relationship`,"
                    + " `counselor`,"
                    + " `species`,"
                    + " `height`,"
                    + " `weight`,"
                    + " `ethnicity`,"
                    + " `smoker`,"
                    + " `active`,"
                    + " `deactivatedDate`,"
                    + " `oldID` "
                    + ")"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setString(1, patient.getArNo());
            pStmt.setString(2, patient.getLastName());
            pStmt.setString(3, patient.getFirstName());
            pStmt.setString(4, patient.getMiddleName());
            pStmt.setString(5, patient.getSex());
            pStmt.setString(6, patient.getSsn());
            SQLUtil.SafeSetDate(pStmt, 7, patient.getDob());
            pStmt.setString(8, patient.getAddressStreet());
            pStmt.setString(9, patient.getAddressStreet2());
            pStmt.setString(10, patient.getAddressCity());
            pStmt.setString(11, patient.getAddressState());
            pStmt.setString(12, patient.getAddressZip());
            pStmt.setString(13, patient.getPhone());
            pStmt.setString(14, patient.getWorkPhone());
            SQLUtil.SafeSetInteger(pStmt, 15, patient.getSubscriber());
            SQLUtil.SafeSetString(pStmt, 16, patient.getRelationship());
            SQLUtil.SafeSetInteger(pStmt, 17, patient.getCounselor());
            SQLUtil.SafeSetInteger(pStmt, 18, patient.getSpecies());
            pStmt.setInt(19, patient.getHeight());
            pStmt.setInt(20, patient.getWeight());
            pStmt.setString(21, patient.getEthnicity());
            pStmt.setBoolean(22, patient.getSmoker());
            pStmt.setBoolean(23, patient.getActive());
            SQLUtil.SafeSetDate(pStmt, 24, patient.getDeactivatedDate());
            pStmt.setInt(25, patient.getIdPatients());
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = patient.getArNo() + ": " + ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
}
