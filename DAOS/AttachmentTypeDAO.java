
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AttachmentType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class AttachmentTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`attachmentTypes`";    
    private final List<String> fields = new LinkedList<>();
    
    public enum SearchType
    {
        ALL,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    public enum Visibility
    {
        ANY,
        LIS_VISIBLE,
        WEB_VISIBLE
    }
    
    public AttachmentTypeDAO()
    {
        fields.add("name");
        fields.add("description");
        fields.add("departmentId");
        fields.add("lisVisible");
        fields.add("webVisible");
        fields.add("allowedExtensions");
        fields.add("allowMultipleAttachments");
        fields.add("maxFileSizeMB");
        fields.add("approveOnPost");
        fields.add("active");
    }
    
    /**
     * Returns any attachment types matching the search type argument
     * @param searchType
     * @param visibility
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<AttachmentType> GetAttachmentTypes(SearchType searchType, Visibility visibility)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE 1=1";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (visibility == Visibility.LIS_VISIBLE)
        {
            sql += " AND lisVisible = 1";
        }
        
        if (visibility == Visibility.WEB_VISIBLE)
        {
            sql += " AND webVisible = 1";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<AttachmentType> attachmentTypes = new LinkedList<>();
        while (rs.next())
        {
            attachmentTypes.add(GetAttachmentTypeFromResultSet(rs));
        }
        return attachmentTypes;        
    }
    
    /**
     * Returns an AttachmentType object with the provided unique identifier.
     *  If the SearchType parameter filters out the row, this will return a 
     *  NULL searchType object.
     * @param id
     * @param searchType
     * @return
     * @throws SQLException 
     */
    public AttachmentType GetAttachmentTypeById(int id, SearchType searchType)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        AttachmentType attachmentType = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachmentType = GetAttachmentTypeFromResultSet(rs);
        }
        return attachmentType;
    }
    
    public AttachmentType GetAttachmentTypeByName(String name, SearchType searchType)
            throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE name = ?";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        AttachmentType attachmentType = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            attachmentType = GetAttachmentTypeFromResultSet(rs);
        }
        return attachmentType;        
    }
    
    public List<AttachmentType> getAttachmentTypesByDepartmentId(Integer departmentId, SearchType searchType, Visibility visibility)
            throws SQLException, IllegalArgumentException
    {
        if (departmentId == null) throw new IllegalArgumentException("AttachmentTypeDAO::getAttachmentTypesByDepartmentId: received a NULL departmentId");
        if (searchType == null) throw new IllegalArgumentException("AttachmentTypeDAO::getAttachmentTypesByDepartmentId: received a NULL SearchType");
        if (visibility == null) throw new IllegalArgumentException("AttachmentTypeDAO::getAttachmentTypesByDepartmentId: received a NULL Visibility setting");
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE departmentId = ?";
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (visibility == Visibility.LIS_VISIBLE)
        {
            sql += " AND lisVisible = 1";
        }
        if (visibility == Visibility.WEB_VISIBLE)
        {
            sql += " AND webVisible = 1";
        }
        List<AttachmentType> attachmentTypes = new LinkedList<>();
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, departmentId);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            attachmentTypes.add(GetAttachmentTypeFromResultSet(rs));
        }
        return attachmentTypes;
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, AttachmentType attachmentType)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, attachmentType.getId());
        SQLUtil.SafeSetString(pStmt, ++i, attachmentType.getName());
        SQLUtil.SafeSetString(pStmt, ++i, attachmentType.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentType.isLisVisible());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentType.isWebVisible());
        SQLUtil.SafeSetString(pStmt, ++i, attachmentType.getAllowedExtensions());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentType.isAllowMultipleAttachments());
        SQLUtil.SafeSetDouble(pStmt, ++i, attachmentType.getMaxFileSizeMB());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentType.isApproveOnPost());
        SQLUtil.SafeSetBoolean(pStmt, ++i, attachmentType.isActive());
        
        return pStmt;
    }
    
    public AttachmentType GetAttachmentTypeFromResultSet(ResultSet rs) throws SQLException
    {
        AttachmentType attachmentType = new AttachmentType();
        attachmentType.setId(rs.getInt("id"));
        attachmentType.setName(rs.getString("name"));
        attachmentType.setDescription(rs.getString("description"));
        attachmentType.setLisVisible(rs.getBoolean("lisVisible"));
        attachmentType.setWebVisible(rs.getBoolean("webVisible"));
        attachmentType.setAllowedExtensions(rs.getString("allowedExtensions"));
        attachmentType.setAllowMultipleAttachments(rs.getBoolean("allowMultipleAttachments"));
        attachmentType.setMaxFileSizeMB(rs.getDouble("maxFileSizeMB"));
        attachmentType.setApproveOnPost(rs.getBoolean("approveOnPost"));
        attachmentType.setActive(rs.getBoolean("active"));
        return attachmentType;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
