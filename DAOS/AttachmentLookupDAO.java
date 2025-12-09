
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AttachmentLookupDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentLookup`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public enum SearchType
    {
        ALL,
        ACTIVE,
        ACTIVE_AND_APPROVED,
        INACTIVE_ONLY
    }
    
    public AttachmentLookupDAO()
    {
        fields.add("id");
        fields.add("orderId");
        fields.add("patientId");
        fields.add("resultId");
        fields.add("attachmentTypeId");
        fields.add("attachmentDataId");
        fields.add("attachmentMetadataId");
        fields.add("description");
        fields.add("isWebAttachment");
        fields.add("approvedForTransmission");
        fields.add("attachedDate");
        fields.add("attachedById");
        fields.add("modifiedDate");
        fields.add("active"); // aka not deleted
        
    }
    
    /**
     * Returns an AttachmentLookup object with the provided unique identifier.
     *  If the SearchType parameter filters out the row, this will return a 
     *  NULL searchType object.
     * @param id
     * @param searchType
     * @return
     * @throws SQLException 
     */
    public AttachmentLookup GetAttachmentLookupById(int id, SearchType searchType) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        
        if (searchType == SearchType.ACTIVE)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        if (searchType == SearchType.ACTIVE_AND_APPROVED)
        {
            sql += " AND active = 1 AND approvedForTransmission = 1";
        }

        AttachmentLookup attachmentLookup = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachmentLookup = GetAttachmentFromResultSet(rs);
        }
        return attachmentLookup;
    }
    
    /**
     * Returns any attachments associated with this orderId as filtered by
     *  the search type. If none are found, returns an empty List.
     * 
     * @param id
     * @param searchType
     * @return List of AttachmentLookup objects. Empty if none found.
     * @throws SQLException 
     */
    public List<AttachmentLookup> GetAttachmentLookupsByOrderId(int id, SearchType searchType) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        LinkedList<AttachmentLookup> attachmentLookups = new LinkedList<>();
        
        String sql = "SELECT * FROM " + table + " WHERE orderId = " + id;
        if (searchType == SearchType.ACTIVE)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0;";
        }
        if (searchType == SearchType.ACTIVE_AND_APPROVED)
        {
            sql += " AND active = 1 AND approvedForTransmission = 1";
        }
  
        AttachmentLookup attachmentLookup = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            attachmentLookup = GetAttachmentFromResultSet(rs);
            attachmentLookups.add(attachmentLookup);
        }
        return attachmentLookups;
    }
    
    public List<AttachmentLookup> GetAttachmentLookupsByResultId(int id, SearchType searchType) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        LinkedList<AttachmentLookup> attachmentLookups = new LinkedList<>();
        
        String sql = "SELECT * FROM " + table + " WHERE resultId = " + id;
        if (searchType == SearchType.ACTIVE)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        if (searchType == SearchType.ACTIVE_AND_APPROVED)
        {
            sql += " AND active = 1 AND approvedForTransmission = 1";
        }
  
        AttachmentLookup attachmentLookup = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            attachmentLookup = GetAttachmentFromResultSet(rs);
            attachmentLookups.add(attachmentLookup);
        }
        return attachmentLookups;        
    }
    
    public int InsertAttachmentLookupGetId(AttachmentLookup attachmentLookup)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pStmt = SetStatementFromObject(pStmt, attachmentLookup);
        pStmt.execute();
        Integer attachmentLookupId;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                attachmentLookupId = generatedKeys.getInt(1);
            }
            else
            {
                throw new SQLException("AttachmentLookupDAO::InsertAttachmentLookupGetId: Insert failed, no unique ID obtained from insert.");
            }
        }
        return attachmentLookupId;
    }
    
    public void UpdateAttachmentLookup(AttachmentLookup attachmentLookup)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateUpdateStatement(fields);
        sql += " WHERE id = " + attachmentLookup.getId();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromObject(pStmt, attachmentLookup);
        pStmt.execute();
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentLookup attachmentLookup)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getPatientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getResultId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getAttachmentTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getAttachmentDataId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getAttachmentMetadataId());
        SQLUtil.SafeSetString(pStmt, ++i, attachmentLookup.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentLookup.isIsWebAttachment());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentLookup.isApprovedForTransmission());
        SQLUtil.SafeSetDate(pStmt, ++i, attachmentLookup.getAttachedDate());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentLookup.getAttachedById());
        SQLUtil.SafeSetDate(pStmt, ++i, attachmentLookup.getModifiedDate());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentLookup.isActive());
        
        return pStmt;
    }
    
    private AttachmentLookup GetAttachmentFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentLookup attachmentLookup = new AttachmentLookup();
        attachmentLookup.setId(rs.getInt("id"));
        attachmentLookup.setOrderId(rs.getInt("orderId"));
        attachmentLookup.setPatientId(rs.getInt("patientId"));
        attachmentLookup.setResultId(rs.getInt("resultId"));
        attachmentLookup.setAttachmentTypeId(rs.getInt("attachmentTypeId"));
        attachmentLookup.setAttachmentDataId(rs.getInt("attachmentDataId"));
        attachmentLookup.setAttachmentMetadataId(rs.getInt("attachmentMetadataId"));
        attachmentLookup.setDescription(rs.getString("description"));
        attachmentLookup.setIsWebAttachment(rs.getBoolean("isWebAttachment"));
        attachmentLookup.setApprovedForTransmission(rs.getBoolean("approvedForTransmission"));
        attachmentLookup.setAttachedDate(rs.getDate("attachedDate"));
        attachmentLookup.setAttachedById(rs.getInt("attachedById"));
        attachmentLookup.setModifiedDate(rs.getDate("modifiedDate"));
        attachmentLookup.setActive(rs.getBoolean("active"));
        
        return attachmentLookup;
    }

    private String GenerateInsertStatement(ArrayList<String> fields)
    {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
