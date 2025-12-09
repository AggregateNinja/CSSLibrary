
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentTransmissionLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentTransmissionLookupDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentTransmissionLookup`";
    private final ArrayList<String> fields = new ArrayList<>();

    public enum SearchType
    {
        ALL,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    public AttachmentTransmissionLookupDAO()
    {
        fields.add("id");
        fields.add("clientId");
        fields.add("attachmentTypeId");
        fields.add("transmissionTypeId");
        fields.add("printOrder");
        fields.add("createdById");
        fields.add("modifiedById");
        fields.add("active");
    }
    
    public AttachmentTransmissionLookup GetAttachmentTransmissionLookupByClientId(int clientId, SearchType searchType) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE clientId = " + clientId;
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        AttachmentTransmissionLookup attachmentTransmissionLookup = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachmentTransmissionLookup = GetAttachmentTransmissionFromResultSet(rs);
        }
        return attachmentTransmissionLookup;
    }
    
    public void InsertAttachmentTransmissionLookup(AttachmentTransmissionLookup attachmentTransmissionLookup)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromObject(pStmt, attachmentTransmissionLookup);
        pStmt.execute();
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentTransmissionLookup attachmentTransmissionLookup)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getClientId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getAttachmentTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getTransmissionTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getPrintOrder());
        // Created date
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getCreatedById());
        // Modified date
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTransmissionLookup.getModifiedById());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentTransmissionLookup.isActive());
        
        return pStmt;
    }
    
    private AttachmentTransmissionLookup GetAttachmentTransmissionFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentTransmissionLookup attachmentTestLookup = new AttachmentTransmissionLookup();
        attachmentTestLookup.setId(rs.getInt("id"));
        attachmentTestLookup.setClientId(rs.getInt("clientId"));
        attachmentTestLookup.setAttachmentTypeId(rs.getInt("attachmentTypeId"));
        attachmentTestLookup.setTransmissionTypeId(rs.getInt("transmissionTypeId"));
        attachmentTestLookup.setPrintOrder(rs.getInt("printOrder"));
        attachmentTestLookup.setCreated(rs.getDate("created"));
        attachmentTestLookup.setCreatedById(rs.getInt("createdById"));
        attachmentTestLookup.setModifiedDate(rs.getDate("modifiedDate"));
        attachmentTestLookup.setModifiedById(rs.getInt("modifiedById"));
        attachmentTestLookup.setActive(rs.getBoolean("active"));
        return attachmentTestLookup;
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
