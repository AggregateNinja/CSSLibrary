
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RemarkType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Database access methods for remarkTypes lookup table rows.
 * 
 * RemarkTypes define the nature of a group of remarks.
 *  of remarks.
 * @author TomR
 */
public class RemarkTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`remarkTypes`";
    
    /**
     * Used in getter method to define which rows to retrieve
     */
    public enum ResultCategory
    {
        ALL,
        ACTIVE_ONLY,
        EDITABLE_ONLY;
    }
    
    public RemarkTypeDAO() {}
    
    /**
     * Inserts the new remark type
     * @param remarkType
     * @throws SQLException 
     */
    public void InsertRemarkType(RemarkType remarkType) throws SQLException
    {
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "INSERT INTO " + table
                    + " (`name`,`description`, `editable`, `active`, `updatedOn`, `updatedBy` ) "
                    + " VALUES (?, ?, ?, ?, NOW(), ?)";
            
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, remarkType.getName());
            SQLUtil.SafeSetString(pStmt, 2, remarkType.getDescription());
            SQLUtil.SafeSetBoolean(pStmt, 3, remarkType.isEditable());
            SQLUtil.SafeSetBoolean(pStmt, 4, remarkType.isActive());
            SQLUtil.SafeSetInteger(pStmt, 5, remarkType.getUpdatedBy());
            pStmt.executeUpdate();            
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    /**
     * Updates an existing remark type. Argument remark type must have
     *  a valid unique identifier.
     * @param remarkType
     * @throws SQLException 
     */
    public void UpdateRemarkType(RemarkType remarkType) throws SQLException
    {
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            
            String sql = "UPDATE " + table + " SET "
                    + " `name` = ?,"
                    + " `description` = ?,"
                    + " `editable` = ?,"
                    + " `active` = ?,"
                    + " `updatedOn` = NOW(),"
                    + " `updatedBy` = ?"
                    + " WHERE `id` = ?";
            
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, remarkType.getName());
            pStmt.setString(2, remarkType.getDescription());
            pStmt.setBoolean(3, remarkType.isEditable());
            pStmt.setBoolean(4, remarkType.isActive());
            pStmt.setInt(5, remarkType.getUpdatedBy());
            pStmt.setInt(6, remarkType.getId());
            
            
            pStmt.executeUpdate();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    /**
     * Retrieves the remark type object based on its unique database identifier
     * @param id
     * @return
     * @throws SQLException 
     */
    public RemarkType GetRemarkTypeById(Integer id) throws SQLException
    {
        if (id == null) return null;
        RemarkType remarkType = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " WHERE `id` = " + id;
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery(sql);
            if (rs.next())
            {
                remarkType = BuildRemarkTypeFromResultSet(rs);                
            }
            pStmt.close();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return remarkType;
    }
    
    /**
     * Retrieves the remark type based on the string literal name passed in.
     *  Will only retrieve the first match that is entered.
     * @param name
     * @return
     * @throws SQLException 
     */
    public RemarkType GetRemarkTypeByName(String name) throws SQLException
    {
        if (name == null || name.isEmpty()) return null;
        RemarkType remarkType = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM `" + table + "` WHERE `name` = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                remarkType = BuildRemarkTypeFromResultSet(rs);
            }
            pStmt.close();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return remarkType;
    }
    
    /**
     * Get all of the remarks matching the category. Remark types with a NULL
     *  category are by default result remarks (this was the setting prior to
     *  the introduction of remark types).
     * 
     * @param resultCategory
     * @param remarkCategory
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<RemarkType> getRemarkTypesByCategory(
            ResultCategory resultCategory,
            RemarkType.CategoryName remarkCategory)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (resultCategory == null)
        {
            throw new IllegalArgumentException("RemarkTypeDAO::getRemarkTypes:"
                    + " Received a [NULL] ResultCategory enum argument (e.g. active/inactive)");
        }
        
        if (remarkCategory == null)
        {
            throw new IllegalArgumentException("RemarkTypeDAO::getRemarkTypes:"
                    + " Received a [NULL] RemarkCategory enum argument");
        }
        
        String sql = "SELECT rt.* FROM `remarkTypes` rt"
                + " LEFT JOIN `remarkCategories` rc ON rt.`remarkCategoryId` = rc.idremarkCategories";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // NOTE: Result-category remarks are handled differently, since they
        //   were the only ones in the system when remarkTypes were added (see method documentation above)
        if (remarkCategory.getCategorySystemName().equals(RemarkType.CategoryName.RESULT.getCategorySystemName()))
        {
            sql += " WHERE (rt.remarkCategoryId IS NULL OR rc.systemName = '" + RemarkType.CategoryName.RESULT.getCategorySystemName() + "')";
        }
        else // This is a non-result remark category. The argument needs to match
        {
            sql += " WHERE rc.systemName = '" + remarkCategory.getCategorySystemName() + "'";
        }

        // inactive = deleted
        if (resultCategory == ResultCategory.ACTIVE_ONLY)
        {
            sql += " AND rt.`active` = 1";
        }
        
        // editable = user can change
        if (resultCategory == ResultCategory.EDITABLE_ONLY)
        {
            sql += " AND rt.`editable` = 1";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        List<RemarkType> remarkTypes = new LinkedList<>();
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            remarkTypes.add(BuildRemarkTypeFromResultSet(rs));
        }
        pStmt.close();
        return remarkTypes;
    }
    
    /**
     * Returns all of the remark type objects that meet the criteria
     *  defined by the ResultCategory parameter (All, Active)
     * @param resultCategory
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException For invalid ResultCategory values
     */
    public List<RemarkType> GetRemarkTypes(ResultCategory resultCategory) throws SQLException, IllegalArgumentException
    {
        if (resultCategory == null)
        {
            String err = "RemarkTypeDAO::GetRemarkTypes: Received NULL ResultCategoryParameter";
            throw new IllegalArgumentException(err);
        }
        
        ArrayList<RemarkType> remarkTypes = new ArrayList<>();
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " ";
            
            switch (resultCategory)
            {
                case ACTIVE_ONLY:
                    sql += " WHERE active = 1";
                    break;
                case EDITABLE_ONLY:
                    sql += " WHERE editable = 1";
            }
            sql += " ORDER BY `name`";
            
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                remarkTypes.add(BuildRemarkTypeFromResultSet(rs));
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return remarkTypes;        
    }
    
    /*
    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }    
    
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }
    */
    
    /*
    private PreparedStatement SetStatementFromObject(RemarkType remarkType, PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetString(pStmt, ++i, remarkType.getName());
        SQLUtil.SafeSetString(pStmt, ++i, remarkType.getDescription());
        SQLUtil.SafeSetBoolean(pStmt, ++i, remarkType.isEditable());
        SQLUtil.SafeSetBoolean(pStmt, ++i, remarkType.isActive());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, remarkType.getUpdatedOn());
        SQLUtil.SafeSetInteger(pStmt, ++i, remarkType.getUpdatedBy());
        return pStmt;
    }
    */
    
    public static RemarkType BuildRemarkTypeFromResultSet(ResultSet rs) throws SQLException
    {
        RemarkType remarkType = new RemarkType();
        remarkType.setId(rs.getInt("id"));
        Integer remarkCategoryId = rs.getInt("remarkCategoryId");
        if (remarkCategoryId.equals(0)) remarkCategoryId = null;
        remarkType.setRemarkCategoryId(remarkCategoryId);
        remarkType.setName(rs.getString("name"));
        remarkType.setDescription(rs.getString("description"));
        remarkType.setEditable(rs.getBoolean("editable"));
        remarkType.setActive(rs.getBoolean("active"));
        remarkType.setUpdatedOn(rs.getTimestamp("updatedOn"));
        remarkType.setUpdatedBy(rs.getInt("updatedBy"));
        return remarkType;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `remarkTypes`.`id`,\n"
                + "    `remarkTypes`.`remarkCategoryId`,\n"
                + "    `remarkTypes`.`name`,\n"
                + "    `remarkTypes`.`systemName`,\n"
                + "    `remarkTypes`.`description`,\n"
                + "    `remarkTypes`.`editable`,\n"
                + "    `remarkTypes`.`active`,\n"
                + "    `remarkTypes`.`updatedOn`,\n"
                + "    `remarkTypes`.`updatedBy`\n"
                + "FROM `css`.`remarkTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
