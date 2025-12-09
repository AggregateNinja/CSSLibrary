
package BL;

import DAOS.AttachmentDataDAO;
import DAOS.AttachmentLookupDAO;
import DAOS.AttachmentMetadataDAO;
import DAOS.AttachmentTypeDAO;
import DAOS.ResultDAO;
import DAOS.SysLogDAO;
import DOS.AttachmentData;
import DOS.AttachmentLookup;
import DOS.AttachmentMetadata;
import DOS.AttachmentType;
import DOS.Results;
import Database.CheckDBConnection;
import Utility.FileUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;

public class AttachmentBL
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    //<editor-fold defaultstate="collapsed" desc="Transmission Type (enum)">
    public enum TransmissionType
    {
        CLIENT,
        BILLING;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Duplicate Attachment Exception (class)">
    public class DuplicateAttachmentException extends Exception
    {
        public DuplicateAttachmentException(String message)
        {
            super(message);
        }
        
        public DuplicateAttachmentException(String message, Throwable throwable)
        {
            super(message, throwable);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="AttachmentWrapper (class)">
    /**
     * Wrapper for individual attachment objects
     */
    public class AttachmentWrapper
    {
        AttachmentType attachmentType;
        AttachmentLookup attachmentLookup;
        AttachmentData attachmentData;
        AttachmentMetadata attachmentMetadata;
        
        public AttachmentWrapper(
                AttachmentType attachmentType,
                AttachmentLookup attachmentLookup,
                AttachmentData attachmentData,
                AttachmentMetadata attachmentMetadata)
        {
            this.attachmentType = attachmentType;
            this.attachmentLookup = attachmentLookup;
            this.attachmentData = attachmentData;
            this.attachmentMetadata = attachmentMetadata;
        }

        public AttachmentType getAttachmentType()
        {
            return attachmentType;
        }

        public void setAttachmentType(AttachmentType attachmentType)
        {
            this.attachmentType = attachmentType;
        }

        public AttachmentLookup getAttachmentLookup()
        {
            return attachmentLookup;
        }

        public void setAttachmentLookup(AttachmentLookup attachmentLookup)
        {
            this.attachmentLookup = attachmentLookup;
        }

        public AttachmentData getAttachmentData()
        {
            return attachmentData;
        }

        public void setAttachmentData(AttachmentData attachmentData)
        {
            this.attachmentData = attachmentData;
        }

        public AttachmentMetadata getAttachmentMetadata()
        {
            return attachmentMetadata;
        }

        public void setAttachmentMetadata(AttachmentMetadata attachmentMetadata)
        {
            this.attachmentMetadata = attachmentMetadata;
        }
    }
    //</editor-fold>
    
    /**
     * Checks to see if there is any active attachment on this order with the supplied md5
     * @param orderId
     * @param md5
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean attachmentHashExistsForOrderId(int orderId, String md5)
            throws SQLException, IllegalArgumentException
    {
        if (md5 == null || md5.isEmpty()) throw new IllegalArgumentException("Null/blank md5 argument");
        if (orderId <= 0) throw new IllegalArgumentException("OrderId argument is " + orderId);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = "SELECT COUNT(*)"
                + " FROM attachmentMetadata am"
                + " INNER JOIN attachmentLookup al ON am.id = al.attachmentMetadataId"
                + " WHERE al.orderId = " + orderId
                + " AND am.md5 = ?"
                + " AND al.active = 1";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, md5);
        System.out.println(pStmt.toString());
        ResultSet rs = pStmt.executeQuery();
        int count = 0;
        if (rs.next())
        {
            count = rs.getInt(1);
        }
        return (count > 0);
    }
    
    /**
     * Removes any attachments associated with the result id (delete command).
     * 
     * Removes rows from the following tables:
     *  AttachmentLookup
     *  AttachmentMetadata
     *  AttachmentData
     * 
     * Note: Logging should be performed by the calling code if necessary.
     * @param resultId 
     * @param searchType 
     * @throws java.sql.SQLException 
     */
    public void deleteResultAttachments(int resultId, AttachmentLookupDAO.SearchType searchType)
            throws SQLException, IllegalArgumentException
    {
        if (resultId <= 0) throw new IllegalArgumentException("AttachmentBL::deleteResultAttachments: Received resultId of " + resultId);
        if (searchType == null) throw new IllegalArgumentException("AttachmentBL::deleteResultAttachments: Received NULL searchType argument");
        AttachmentLookupDAO aldao = new AttachmentLookupDAO();
        List<AttachmentLookup> lookups = aldao.GetAttachmentLookupsByResultId(resultId, AttachmentLookupDAO.SearchType.ACTIVE);
        if (lookups == null) lookups = new LinkedList<>();
        for (AttachmentLookup lookup : lookups)
        {
            if (lookup.getId() > 0)
            {
                deleteAttachment(lookup.getId());
            }
        }
    }
    
    /**
     * Removes attachment data from the database (delete command) for a single
     *  attachment lookup id.
     * Removes rows from the following tables:
     * 
     *      AttachmentLookup
     *      AttachmentMetadata
     *      AttachmentData
     * 
     * Note: Logging should be performed in the calling code if necessary.
     * 
     * Adds log for delete
     * @param attachmentLookupId
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public void deleteAttachment(int attachmentLookupId)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        if (attachmentLookupId <= 0) throw new IllegalArgumentException("Recived attachmentLookupId : " + attachmentLookupId);
        
        // First get the data row identifier
        AttachmentLookupDAO aldao = new AttachmentLookupDAO();
        Integer attachmentDataId = null;
        Integer attachmentMetadataId = null;
        AttachmentLookup lookup = null;
        try
        {
            lookup = aldao.GetAttachmentLookupById(attachmentLookupId, AttachmentLookupDAO.SearchType.ALL);
            if (lookup == null) throw new SQLException("NULL AttachmentLookup object returned");
            if (lookup.getId() == 0) throw new SQLException("Empty AttachmentLookup object returned");
            
            attachmentDataId = lookup.getAttachmentDataId();
            if (attachmentDataId <= 0) throw new SQLException("AttachmentId : " + lookup.getId() + " has a NULL/invalid attachmentDataId");
            
            attachmentMetadataId = lookup.getAttachmentMetadataId();
            if (attachmentMetadataId == null) throw new SQLException("Attachment Id : " + lookup.getId() + " has a NULL/invalid attachmentMetadataId");
        }
        catch (SQLException ex)
        {
            SysLogDAO.Add(1, "AttachmentBL::deleteAttachment: Error getting attachmentLookup for id " + attachmentLookupId, ex.getMessage());
            throw ex;
        }
        
        Savepoint savePoint = con.setSavepoint();
        con.setAutoCommit(false);
        try
        {
            String sql = "DELETE FROM attachmentData WHERE id = " + attachmentDataId;
            PreparedStatement pStmt = con.prepareStatement(sql);
            int deletedCount = pStmt.executeUpdate();
            if (deletedCount == 0) throw new SQLException("SQL statement did not delete attachmentData row for id: " + attachmentDataId);
            
            sql = "DELETE FROM attachmentMetadata WHERE id = " + attachmentMetadataId;
            pStmt = con.prepareStatement(sql);
            deletedCount = pStmt.executeUpdate();
            if (deletedCount == 0) throw new SQLException("SQL statement could not delete metadata row for id: " + attachmentMetadataId);
            
            sql = "DELETE FROM attachmentLookup WHERE id = " + lookup.getId();
            pStmt = con.prepareStatement(sql);
            pStmt.execute();
            
            con.commit();
        }
        catch(SQLException ex)
        {
            SysLogDAO.Add(1, "AttachmentBL::deleteAttachment: Error deleting for attachmentLookup id: " + attachmentLookupId, ex.getMessage());
            con.rollback(savePoint);
        }
        finally
        {
            con.setAutoCommit(true);
        }
    }
    
    /**
     * Checks the status of the attachments and returns the data row for any
     *  that are ready for transmission (lookup row marked active). Data
     *  is Base64 encoded in the AttachmentData object.
     * 
     * =================================================================================
     *  NOTE: this method will need to change when we have an attachment transmission
     *   lookup that allows us to selectively include/exclude attachment types
     *   based on client or billing system. It should sort based on the printOrder.
     * ================================================================================= 
     * Order of precedence for transmission look-up:
     * 1) Check the order settings for transmission settings. If not present,
     * 2) Check the client for transmission settings. If not present,
     * 3) Provide a default template to use. If not present,
     * 4) Default to always transmit everything to any type
     * 
     * TODO: what about insurance? Does insurance settings take precedence over client?
     * 
     * @param orderId
     * @param type
     * @return 
     * @throws java.sql.SQLException 
     */
    public Map<AttachmentType, List<AttachmentData>> getTransmittableAttachments(int orderId, TransmissionType type)
            throws IllegalArgumentException, SQLException
    {
        if (type == null) throw new IllegalArgumentException(
                "AttachmentBL::getTransmittableAttachments: received NULL TransmissionType argument");
        
        if (orderId <= 0) throw new IllegalArgumentException(
            "AttachmentBL::getTransmittableAttachments : received orderId of " + orderId);
        
        AttachmentLookupDAO aldao = new AttachmentLookupDAO();
        
        // Only active and approved. Inactive attachments have been marked deleted by the user.
        List<AttachmentLookup> lookups = aldao.GetAttachmentLookupsByOrderId(orderId, AttachmentLookupDAO.SearchType.ACTIVE_AND_APPROVED);
        
        Map<AttachmentType, List<AttachmentData>> transmittableAttachments = new HashMap<>();
        
        AttachmentTypeDAO atdao = new AttachmentTypeDAO();
        AttachmentDataDAO addao = new AttachmentDataDAO();
        
        for (AttachmentLookup lookup : lookups)
        {
            if (lookup == null || lookup.getId() == 0)
            {
                throw new SQLException("AttachmentBL::getTransmittableAttachments: "
                        + "NULL lookup returned when searcing for active lookups for orderId " + orderId);
            }
            AttachmentType at = atdao.GetAttachmentTypeById(lookup.getAttachmentTypeId(), AttachmentTypeDAO.SearchType.ACTIVE_ONLY);
            if (at == null || at.getId() == 0) throw new SQLException("AttachmentBL::getTransmittableAttachments: Returned NULL/empty attachmentType for lookup Id: " + lookup.getId());
            List<AttachmentData> dataList = transmittableAttachments.get(at);
            if (dataList == dataList) dataList = new LinkedList<>();
            AttachmentData ad = addao.GetAttachmentDataById(lookup.getAttachmentDataId());
            if (ad == null || ad.getId() == 0) throw new SQLException("AttachmentBL::getTransmittableAttachments: Returned NULL/empty attachmentData for lookupId: " + lookup.getId());
            dataList.add(ad);
            transmittableAttachments.put(at, dataList);
        }
        return transmittableAttachments;
    }
    
    /**
     * Returns a list of the raw attachment data (base-64 decoded) for the
     *  given order/transmission type
     * @param orderId
     * @param type
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<byte[]> getTransmittableAttachmentRawData(int orderId, TransmissionType type)
            throws IllegalArgumentException, SQLException
    {
        Map<AttachmentType, List<AttachmentData>> attachments = getTransmittableAttachments(orderId, type);
        
        List<byte[]> dataList = new LinkedList<>();
        
        for (Entry<AttachmentType, List<AttachmentData>> entry : attachments.entrySet())
        {
            List<AttachmentData> attachmentData = entry.getValue();
            for (AttachmentData datum : attachmentData)
            {
                byte[] bytesB64 = datum.getAttachment();
                byte[] bytes = Base64.decodeBase64(bytesB64);
                
                dataList.add(bytes);
            }
        }
        return dataList;
    }
    
    /**
     * Adds a result attachment to the provided result line. Description is optional.
     * Inserts all rows as a transaction and sys logs any errors if rolling back
     * on an error condition.
     * @param attachmentType
     * @param resultId
     * @param userId
     * @param path
     * @param description Optional
     * @return 
     * @throws BL.AttachmentBL.DuplicateAttachmentException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public AttachmentWrapper addResultAttachment(AttachmentType attachmentType, int resultId, int userId, String path, String description)
            throws DuplicateAttachmentException, IOException, NoSuchAlgorithmException, SQLException, IllegalArgumentException
    {
        ResultDAO rdao = new ResultDAO();
        Results result = rdao.GetResultByID(resultId);
        
        return addAttachment(attachmentType, result.getOrderId(), resultId, 0, userId, path, description, attachmentType.isApproveOnPost());
    }
    
    /**
     * Adds an order attachment. Description is optional
     * Inserts all rows as a transaction and sys logs any errors if rolling back
     * on an error condition.
     * @param attachmentType
     * @param orderId
     * @param userId
     * @param path
     * @param description Optional
     * @return 
     * @throws BL.AttachmentBL.DuplicateAttachmentException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public AttachmentWrapper addOrderAttachment(AttachmentType attachmentType, int orderId, int userId, String path, String description)
            throws DuplicateAttachmentException, IOException, NoSuchAlgorithmException, SQLException, IllegalArgumentException
    {
        // New attachments are only flagged for transmission if the attachment type is set
        // to approve on post. Otherwise, users must use the result approval module.
        return addAttachment(attachmentType, orderId, 0, 0, userId, path, description, true);
    }
    
    /**
     * Adds a patient attachment. Description is optional.
     * Inserts all rows as a transaction and sys logs any errors if rolling back
     * on an error condition.
     * @param attachmentType
     * @param patientId
     * @param userId
     * @param path
     * @param description Optional
     * @return 
     * @throws BL.AttachmentBL.DuplicateAttachmentException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws SQLException
     * @throws IllegalArgumentException 
     */
    public AttachmentWrapper addPatientAttachment(AttachmentType attachmentType, int patientId, int userId, String path, String description)
            throws DuplicateAttachmentException, IOException, NoSuchAlgorithmException, SQLException, IllegalArgumentException
    {
        return addAttachment(attachmentType, 0, 0, patientId, userId, path, description, true);
    }
    
    private AttachmentWrapper addAttachment(
            AttachmentType attachmentType,
            int orderId,
            int resultId,
            int patientId,
            int userId,
            String path,
            String description,
            boolean approvedForTransmission)
            throws DuplicateAttachmentException, IOException, NoSuchAlgorithmException, SQLException, IllegalArgumentException
    {
        if (attachmentType == null) throw new IllegalArgumentException("NULL attachment type");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        MessageDigest digest = null;
        File attachmentFile = new File(path);
        try
        {
            digest = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException ex)
        {
            SysLogDAO.Add(userId, "AttachmentBL::addAttachment: No 'MD5' algorithm", ex.getMessage());
            throw ex;
        }
        
        byte[] fileBytes = Files.readAllBytes(attachmentFile.toPath());
        digest.update(fileBytes);
        String md5 = byteArrToString(digest.digest());
        
        if (attachmentHashExistsForOrderId(orderId, md5))
        {
            throw new DuplicateAttachmentException("");
        }
        
        String b64EncodedFile = FileUtil.encodeByteArrayToBase64Binary(fileBytes);
        
        AttachmentDataDAO adao = new AttachmentDataDAO();
        AttachmentData attachmentData = new AttachmentData();
        byte[] b64Bytes = b64EncodedFile.getBytes();
        attachmentData.setAttachment(b64Bytes);
        
        // Wrapper object is returned after inserts are complete
        AttachmentWrapper wrapper = null;
        
        // Start the transaction
        Savepoint savePoint = con.setSavepoint();
        try
        {
            con.setAutoCommit(false);
            
            // Insert the raw data
            int attachmentDataId = adao.InsertAttachmentDataGetId(attachmentData);
            
            // Build and insert the metadata for the file
            AttachmentMetadataDAO amdao = new AttachmentMetadataDAO();
            AttachmentMetadata attachmentMetadata = new AttachmentMetadata();
            attachmentMetadata.setAttachmentDataId(attachmentDataId);
            attachmentMetadata.setMd5(md5);
            attachmentMetadata.setPath(attachmentFile.getCanonicalPath());
            String ext = FilenameUtils.getExtension(attachmentFile.getCanonicalPath());
            String fileName = FilenameUtils.getName(attachmentFile.getCanonicalPath());
            if (fileName != null && fileName.trim().length() > 0) attachmentMetadata.setFileName(fileName);
            if (ext != null && ext.trim().length() > 0) attachmentMetadata.setExtension(ext);
            int fileSize = 0;
            try
            {
               fileSize = (int)attachmentFile.length();
            }
            catch (NumberFormatException ex){} // Couldn't determine file size
            attachmentMetadata.setFileSizeBytes(fileSize);
            int attachmentMetadataId = amdao.InsertAttachmentMetadataGetId(attachmentMetadata);

            // Insert the new attachment lookup row (links to order/result/patient)
            AttachmentLookup attachmentLookup = new AttachmentLookup();
            if (orderId > 0) attachmentLookup.setOrderId(orderId);
            attachmentLookup.setAttachmentDataId(attachmentDataId);
            attachmentLookup.setAttachmentMetadataId(attachmentMetadataId);
            if (resultId > 0) attachmentLookup.setResultId(resultId);
            if (patientId > 0) attachmentLookup.setPatientId(patientId);
            attachmentLookup.setAttachmentTypeId(attachmentType.getId());
            attachmentLookup.setDescription(description); // Optional
            attachmentLookup.setAttachedById(userId);
            attachmentLookup.setActive(true);
            attachmentLookup.setApprovedForTransmission(approvedForTransmission);
            AttachmentLookupDAO aldao = new AttachmentLookupDAO();
            aldao.InsertAttachmentLookupGetId(attachmentLookup);
            
            // Commit all changes
            con.commit();
            
            // Wrap up the newly created objects and return them
            wrapper = new AttachmentWrapper(attachmentType, attachmentLookup, attachmentData, attachmentMetadata);
        }
        catch(SQLException ex)
        {
            // Revert any partially-inserted data
            con.rollback(savePoint);
            
            // Log the error
            SysLogDAO.Add(1,
                    "AttachmentBL::addAttachment: Error adding orderId: "
                            + orderId + " resultId: " + resultId + " patientId: "
                            + patientId + " path: " + path, ex.getMessage());
            
            // Re-throw to calling code
            throw ex;
        }
        finally
        {
            // Resume autocommit mode
            con.setAutoCommit(true);
        }
        return wrapper;
    }
    
    /**
     * Returns any attachment type matching the search and visibility criteria
     *  that is associated with a reference lab department.
     * @param searchType
     * @param visibility
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<AttachmentType> getReferenceLabAttachmentTypes(
            AttachmentTypeDAO.SearchType searchType,
            AttachmentTypeDAO.Visibility visibility)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        List<AttachmentType> attachmentTypes = new LinkedList<>();
        String sql = "SELECT at.* FROM attachmentTypes at INNER JOIN departments d ON at.departmentId = d.idDepartment WHERE d.ReferenceLab = 1";
        
        if (searchType == AttachmentTypeDAO.SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == AttachmentTypeDAO.SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        if (visibility == AttachmentTypeDAO.Visibility.LIS_VISIBLE)
        {
            sql += " AND lisVisible = 1";
        }
        if (visibility == AttachmentTypeDAO.Visibility.WEB_VISIBLE)
        {
            sql += " AND webVisible = 1";
        }
        
        AttachmentTypeDAO atdao = new AttachmentTypeDAO();
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            attachmentTypes.add(atdao.GetAttachmentTypeFromResultSet(rs));
        }
        return attachmentTypes;
    }
    
    private static String byteArrToString(byte[] b)
    {
        String res = null;
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int j = b[i] & 0xff;
            if (j < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(j));
        }
        res = sb.toString();
        return res.toUpperCase();
    }    
}