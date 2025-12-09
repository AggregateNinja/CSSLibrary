
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentTestLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttachmentTestLookupDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentTestLookup`";
    private final ArrayList<String> fields = new ArrayList<>();

    public enum SearchType
    {
        ALL,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    public AttachmentTestLookupDAO()
    {
        fields.add("id");
        fields.add("testId");
        fields.add("testNumber");
        fields.add("attachmentTypeId");
        fields.add("requiresAttachmentToPost");
        fields.add("missingAttachmentRemarkId");
        // "Created" is timestamped field
        fields.add("active");
    }

    public AttachmentTestLookup GetAttachmentTestLookupByTestId(int testId, SearchType searchType) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE testId = " + testId;
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        AttachmentTestLookup attachmentTestLookup = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachmentTestLookup = GetAttachmentTestFromResultSet(rs);
        }
        return attachmentTestLookup;
    }
    
    public void InsertAttachmentTestLookup(AttachmentTestLookup attachmentTestLookup)
            throws SQLException, IllegalArgumentException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = SetStatementFromObject(pStmt, attachmentTestLookup);
        pStmt.execute();
    }

    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentTestLookup attachmentTestLookup)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTestLookup.getId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTestLookup.getTestId());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTestLookup.getTestNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTestLookup.getAttachmentTypeId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentTestLookup.requiresAttachmentToPost());
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentTestLookup.getMissingAttachmentRemarkId());
        // "Created" column is auto-timestamped
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentTestLookup.isActive());
        
        return pStmt;
    }
    
    private AttachmentTestLookup GetAttachmentTestFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentTestLookup attachmentTestLookup = new AttachmentTestLookup();
        attachmentTestLookup.setId(rs.getInt("id"));
        attachmentTestLookup.setTestId(rs.getInt("testId"));
        attachmentTestLookup.setTestNumber(rs.getInt("testNumber"));
        attachmentTestLookup.setAttachmentTypeId(rs.getInt("attachmentTypeId"));
        attachmentTestLookup.setRequiresAttachmentToPost(rs.getBoolean("requiresAttachmentToPost"));
        attachmentTestLookup.setMissingAttachmentRemarkId(rs.getInt("missingAttachmentRemarkId"));
        attachmentTestLookup.setCreated(rs.getDate("created"));
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
