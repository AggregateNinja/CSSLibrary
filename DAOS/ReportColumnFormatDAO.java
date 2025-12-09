
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ReportColumnFormat;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportColumnFormatDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`reportColumnFormats`";
    
    public enum ResultCategory
    {
        ALL,
        ACTIVE_ONLY;
    }
    
    public ReportColumnFormatDAO() {}
    
    /**
     * Retrieves the report header object based on its unique database identifier
     * @param id
     * @return
     * @throws SQLException 
     */
    public ReportColumnFormat GetReportColumnFormatById(Integer id) throws SQLException
    {
        if (id == null) return null;
        ReportColumnFormat reportColumnFormat = null;
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " WHERE `id` = " + id;
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery(sql);
            if (rs.next())
            {
                reportColumnFormat = BuildReportColumnFormatFromResultSet(rs);                
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportColumnFormat;
    }
    
    /**
     * Retrieves the report header based on the string literal name passed in.
     *  Will only retrieve the first match that is entered.
     * @param name
     * @return
     * @throws SQLException 
     */
    public ReportColumnFormat GetReportColumnFormatByName(String name) throws SQLException
    {
        if (name == null || name.isEmpty()) return null;
        ReportColumnFormat reportColumnFormat = null;
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
                reportColumnFormat = BuildReportColumnFormatFromResultSet(rs);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportColumnFormat;
    }
    
    /**
     * Returns all of the report column format objects that meet the criteria
     *  defined by the ResultCategory parameter (All, Active)
     * @param resultCategory
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException For invalid ResultCategory values
     */
    public List<ReportColumnFormat> GetReportColumnFormats(ReportColumnFormatDAO.ResultCategory resultCategory) throws SQLException, IllegalArgumentException
    {
        if (resultCategory == null)
        {
            String err = "ReportHeaderDAO::GetReportHeaders: Received NULL ResultCategoryParameter";
            throw new IllegalArgumentException(err);
        }
        
        ArrayList<ReportColumnFormat> reportColumnFormats = new ArrayList<>();
        PreparedStatement pStmt = null;
        try
        {
            if (con.isClosed()) con = CheckDBConnection.Check(dbs, con);
            String sql = "SELECT * FROM " + table + " ";
            
            if (resultCategory == ResultCategory.ACTIVE_ONLY)
            {
                sql += " WHERE active = 1";
            }
            sql += " ORDER BY id ASC;";
            pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                reportColumnFormats.add(BuildReportColumnFormatFromResultSet(rs));
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return reportColumnFormats;        
    }
    
    private ReportColumnFormat BuildReportColumnFormatFromResultSet(ResultSet rs) throws SQLException
    {
        ReportColumnFormat reportColumnFormat = new ReportColumnFormat();
        reportColumnFormat.setId(rs.getInt("id"));
        reportColumnFormat.setName(rs.getString("name"));
        reportColumnFormat.setDescription(rs.getString("description"));
        reportColumnFormat.setActive(rs.getBoolean("active"));
        return reportColumnFormat;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `reportColumnFormats`.`id`,\n"
                + "    `reportColumnFormats`.`name`,\n"
                + "    `reportColumnFormats`.`description`,\n"
                + "    `reportColumnFormats`.`active`\n"
                + "FROM `css`.`reportColumnFormats` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
