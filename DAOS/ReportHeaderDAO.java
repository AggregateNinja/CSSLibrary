
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ReportHeader;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class ReportHeaderDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reportHeaders`";
    
    /**
     * Used in getter method to define which rows to retrieve
     */
    public enum ResultCategory
    {
        ALL,
        ACTIVE_ONLY,
        EDITABLE_ONLY;
    }
    
    public ReportHeaderDAO() {}
    
    /**
     * Inserts the new report header
     * @param reportHeader
     * @throws SQLException 
     */
    public void InsertReportHeader(ReportHeader reportHeader) throws SQLException
    {
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "INSERT INTO " + table
                    + " (`name`,`description`, `displayOrder`,"
                    + "`reportColumnFormatId`, `editable`, `active`,"
                    + "`updatedOn`, `updatedBy` ) "
                    + " VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
            
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, reportHeader.getName());
            SQLUtil.SafeSetString(pStmt, 2, reportHeader.getDescription());
            SQLUtil.SafeSetInteger(pStmt, 3, reportHeader.getDisplayOrder());
            SQLUtil.SafeSetInteger(pStmt, 4, reportHeader.getReportColumnFormatId());
            SQLUtil.SafeSetBoolean(pStmt, 5, reportHeader.isEditable());
            SQLUtil.SafeSetBoolean(pStmt, 6, reportHeader.isActive());
            SQLUtil.SafeSetInteger(pStmt, 7, reportHeader.getUpdatedBy());
            pStmt.executeUpdate();            
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    /**
     * Updates an existing report header. Argument report header must have
     *  a valid unique identifier.
     * @param reportHeader
     * @throws SQLException 
     */
    public void UpdateReportHeader(ReportHeader reportHeader) throws SQLException
    {
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            
            String sql = "UPDATE " + table + " SET "
                    + " `name` = ?,"
                    + " `description` = ?,"
                    + " `displayOrder` = ?,"
                    + " `reportColumnFormatId` = ?,"
                    + " `editable` = ?,"
                    + " `active` = ?,"
                    + " `updatedOn` = NOW(),"
                    + " `updatedBy` = ?"
                    + " WHERE `id` = ?";
            
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, reportHeader.getName());
            SQLUtil.SafeSetString(pStmt, 2, reportHeader.getDescription());
            //pStmt.setString(2, reportHeader.getDescription());
            pStmt.setInt(3, reportHeader.getDisplayOrder());
            SQLUtil.SafeSetInteger(pStmt, 4, reportHeader.getReportColumnFormatId());
            pStmt.setBoolean(5, reportHeader.isEditable());
            pStmt.setBoolean(6, reportHeader.isActive());
            pStmt.setInt(7, reportHeader.getUpdatedBy());
            pStmt.setInt(8, reportHeader.getId());
            
            pStmt.executeUpdate();
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }

    /**
     * Retrieves the report header object based on its unique database identifier
     * @param id
     * @return
     * @throws SQLException 
     */
    public ReportHeader GetReportHeaderById(Integer id) throws SQLException
    {
        if (id == null) return null;
        ReportHeader reportHeader = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " WHERE `id` = " + id;
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery(sql);
            if (rs.next())
            {
                reportHeader = BuildReportHeaderFromResultSet(rs);                
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportHeader;
    }
    
    /**
     * Retrieves the report header based on the string literal name passed in.
     *  Will only retrieve the first match that is entered.
     * @param name
     * @return
     * @throws SQLException 
     */
    public ReportHeader GetReportHeaderByName(String name) throws SQLException
    {
        if (name == null || name.isEmpty()) return null;
        ReportHeader reportHeader = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " WHERE `name` = ?";
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1, name);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                reportHeader = BuildReportHeaderFromResultSet(rs);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportHeader;
    }
    
    /**
     * Returns the active ReportHeader associated with the provided display order number.
     * Display order indices are 1-based
     * @param index
     * @return 
     * @throws java.sql.SQLException 
     */
    public ReportHeader GetActiveReportHeaderByDisplayOrder(int index) throws SQLException, IllegalArgumentException
    {
        if (index <= 0) throw new IllegalArgumentException(
                "ReportHeaderDAO::GetReportHeaderByDisplayOrder: Provided display order index was <= 0");
        ReportHeader reportHeader = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " WHERE `displayOrder` = ? AND `active` = 1";
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1, index);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                reportHeader = BuildReportHeaderFromResultSet(rs);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportHeader;
    }
    
    /**
     * Returns all of the report header objects that meet the criteria
     *  defined by the ResultCategory parameter (All, Active)
     * @param resultCategory
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException For invalid ResultCategory values
     */
    public List<ReportHeader> GetReportHeaders(ReportHeaderDAO.ResultCategory resultCategory) throws SQLException, IllegalArgumentException
    {
        if (resultCategory == null)
        {
            String err = "ReportHeaderDAO::GetReportHeaders: Received NULL ResultCategoryParameter";
            throw new IllegalArgumentException(err);
        }
        
        ArrayList<ReportHeader> reportHeaders = new ArrayList<>();
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
            sql += " ORDER BY `displayOrder` ASC";
            
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                reportHeaders.add(BuildReportHeaderFromResultSet(rs));
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportHeaders;        
    }
    
    /**
     * Cleans up any gaps in the active report header display order,
     *  which can happen if a header is deactivated.
     * @throws java.sql.SQLException
     */
    public void ReindexReportHeaders() throws SQLException
    {
        ReportHeaderDAO rhdao = new ReportHeaderDAO();
        
        // Orders by displayOrder:
        List<ReportHeader> reportHeaders = rhdao.GetReportHeaders(ReportHeaderDAO.ResultCategory.ACTIVE_ONLY);
        Integer previousIndex = null;
        for (ReportHeader reportHeader : reportHeaders)
        {
            if (previousIndex == null)
            {
                reportHeader.setDisplayOrder(1);
            }
            else
            {
                reportHeader.setDisplayOrder(previousIndex + 1);

            }
            rhdao.UpdateReportHeader(reportHeader);
            previousIndex = reportHeader.getDisplayOrder();
        }
    }
    
    /**
     * Re-indexes the display order of report headers, given a change in
     *  the ordering defined by the arguments.
     * 
     * Note: originalDisplayOrder and newDisplayOrder are 1-based indexes
     * 
     * @param originalDisplayOrder
     * @param newDisplayOrder
     * @throws java.sql.SQLException
     */
    public void ReorderReportHeader(int originalDisplayOrder, int newDisplayOrder)
            throws IllegalArgumentException, SQLException
    {
        if (originalDisplayOrder <= 0 || newDisplayOrder <= 0)
            throw new IllegalArgumentException("ReportHeaderDAO::ReindexReportHeaders: Received a <= 0 display order argument");
        
        boolean movedDown = (newDisplayOrder > originalDisplayOrder);
        ReportHeaderDAO rhdao = new ReportHeaderDAO();
        
        int userId = Preferences.userRoot().getInt("id", 0);
        
        // GetReportHeaders is ordered by displayOrder, ascending
        List<ReportHeader> reportHeaders  = GetReportHeaders(ResultCategory.ACTIVE_ONLY);
        for (ReportHeader reportHeader : reportHeaders)
        {   
            int testDisplayOrder = reportHeader.getDisplayOrder();
            
            // The item that's being moved
            if (testDisplayOrder == originalDisplayOrder)
            {
                ReportHeader original = reportHeader.copy();
                
                // Set it to the new display order
                reportHeader.setDisplayOrder(newDisplayOrder);
                rhdao.UpdateReportHeader(reportHeader);
                
                // Log the change
                DiffLogDAO.Insert(
                        "reportHeaderLog",
                        "reportHeaderId",
                        reportHeader.getId(),
                        original,
                        reportHeader,
                        "Reordered by user",
                        userId);
                
                continue;
            }
            
            // Index lower
            if (movedDown && testDisplayOrder <= newDisplayOrder && testDisplayOrder >= originalDisplayOrder)
            {
                reportHeader.setDisplayOrder(testDisplayOrder - 1);
                rhdao.UpdateReportHeader(reportHeader);
            }
            
            // Index higher
            if (movedDown == false && testDisplayOrder >= newDisplayOrder && testDisplayOrder <= originalDisplayOrder)
            {
                reportHeader.setDisplayOrder(testDisplayOrder + 1);
                rhdao.UpdateReportHeader(reportHeader);                
            }
        }
    }
    
    private ReportHeader BuildReportHeaderFromResultSet(ResultSet rs) throws SQLException
    {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setId(rs.getInt("id"));
        reportHeader.setName(rs.getString("name"));
        reportHeader.setDescription(rs.getString("description"));
        reportHeader.setDisplayOrder(rs.getInt("displayOrder"));
        reportHeader.setReportColumnFormatId(rs.getInt("reportColumnFormatId"));
        reportHeader.setEditable(rs.getBoolean("editable"));
        reportHeader.setActive(rs.getBoolean("active"));
        reportHeader.setUpdatedOn(rs.getTimestamp("updatedOn"));
        reportHeader.setUpdatedBy(rs.getInt("updatedBy"));
        return reportHeader;
    } 
    
    @Override
    public String structureCheck() {
        String query = "SELECT `reportHeaders`.`id`,\n"
                + "    `reportHeaders`.`name`,\n"
                + "    `reportHeaders`.`description`,\n"
                + "    `reportHeaders`.`displayOrder`,\n"
                + "    `reportHeaders`.`reportColumnFormatId`,\n"
                + "    `reportHeaders`.`editable`,\n"
                + "    `reportHeaders`.`active`,\n"
                + "    `reportHeaders`.`updatedOn`,\n"
                + "    `reportHeaders`.`updatedBy`\n"
                + "FROM `css`.`reportHeaders` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
