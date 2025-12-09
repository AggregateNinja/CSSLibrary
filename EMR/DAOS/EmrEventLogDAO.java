
package EMR.DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.EmrEventLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmrEventLogDAO implements IDAO<EmrEventLog>, IStructureCheckable
{
    private final static String table = "`emrorders`.`eventLogs`";
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection conn = dbs.getConnection(true);    
    List<String> fields = new LinkedList<>();
    
    public enum SearchType
    {
        ALL,
        ERRORS_ONLY,
        NON_ERRORS_ONLY
    }
    
    public enum Visibility
    {
        ALL,
        USER_VISIBLE,
        NON_USER_VISIBLE
    }
    
    public EmrEventLogDAO()
    {
        fields.add("eventTypeId");
        fields.add("created");
        fields.add("userId");
        fields.add("emrXrefId");
        fields.add("emrOrderId");
        fields.add("emrIdOrders");
        fields.add("description");
        fields.add("userNote");
    }
    
    @Override
    public EmrEventLog Insert(EmrEventLog log) throws SQLException, IllegalArgumentException, NullPointerException
    {
        String sql = "";
        
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs,conn);
        
        sql = "INSERT INTO `emrorders`.`eventLogs` (eventTypeId, created, userId, emrXrefId, emrOrderId, emrIdOrders, description, userNote)"
                + "VALUES (?,NOW(),?,?,?,?,?,?)";
        PreparedStatement pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        
        pStmt.setInt(++i, log.getEmrEventTypeId());
        
        pStmt.setInt(++i, log.getUserId());
        pStmt.setInt(++i, log.getEmrXrefId());
        pStmt.setString(++i, log.getEmrOrderId());
        pStmt.setInt(++i, log.getEmrIdOrders());
        pStmt.setString(++i, log.getDescription());
        pStmt.setString(++i, log.getUserNote());
        
        try
        {
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected == 0) throw new SQLException("Insert could not be performed (zero rows affected)");

            Integer newId;
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    newId = generatedKeys.getInt(1);
                    log.setIdEmrEventLog(newId);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
        }
        catch (SQLException ex)
        {
            if (sql == null) sql = "[NULL]";
            throw new SQLException(ex.getMessage() + ". SQL run: " + sql);
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
        return log;
    }

    // Logs won't be edited
    @Override
    public EmrEventLog Update(EmrEventLog obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Probably shouldn't be deleting logs
    @Override
    public void Delete(EmrEventLog obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmrEventLog GetById(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null) throw new IllegalArgumentException("EmrEventLogDAO::GetById: Received a NULL id argument!");
        
        if (conn.isValid(2) == false) conn = CheckDBConnection.Check(dbs, conn);
        String sql = "SELECT * FROM " + table + " WHERE idEmrEventLog = " + id;
        PreparedStatement pStmt = conn.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        EmrEventLog eventLog = null;
        if (rs.next())
        {
            eventLog = getFromResultSet(rs);
        }
        return eventLog;
    }
    
    /**
     *  Searches and returns EMR event logs that match the supplied arguments.
     *  SearchType and visibility are required; all other parameters can be NULL.
     *  If NULL, they are excluded from the search filter.
     * 
     * @param searchType
     * @param visibility
     * @param xrefId The unique database identifier of the interface (xrefs table)
     * @param startDate Optional start range; if missing will not limit
     * @param endDate Optional end range; if missing will not limit
     * @param emrOrderId The accession column of the emrorders.orders table
     * @param emrIdOrders The unique database identifier of the emrorders.orders table
     * @return List of emrEventLog rows. Empty list if none found.
     * @throws java.sql.SQLException 
     */
    public List<EmrEventLog> search(
            SearchType searchType,
            Visibility visibility,
            Integer xrefId,
            Date startDate,
            Date endDate,
            String emrOrderId,   // Accession column of emrorders.orders
            Integer emrIdOrders) // Unique database identifier
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("EmrEventLogDAO::search : Received a NULL SearchType argument");
        if (searchType == null) throw new IllegalArgumentException("EmrEventLogDAO::search : Received a NULL Visibility argument");
        if (startDate != null && endDate != null && startDate.after(endDate))
            throw new IllegalArgumentException("EmrEventLogDAO::search : Start date argument was after End date");
        
        if (conn.isValid(2) == false) conn = CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM " + table + " WHERE 1=1 ";
        
        if (searchType.equals(SearchType.ERRORS_ONLY))
        {
            sql += " AND isError = 1 ";
        }
        if (searchType.equals(SearchType.NON_ERRORS_ONLY))
        {
            sql += " AND isError = 0 ;";
        }
        // No filter for SearchType.ALL
        
        if (visibility.equals(Visibility.USER_VISIBLE))
        {
            sql += " AND isUserVisible = 1";
        }
        if (visibility.equals(Visibility.NON_USER_VISIBLE))
        {
            sql += " AND isUserVisible = 0";
        }
        
        // No filter for Visibilty.ALL
        
        if (xrefId != null)
        {
            sql += " AND emrXrefId = " + xrefId;
        }
        
        if (emrOrderId != null && emrOrderId.isEmpty() == false)
        {
            sql += " AND emrOrderId like '%" + emrOrderId + "%'";
        }
        
        if (startDate != null)
        {
            
            sql += " AND created >= ?";
        }
        
        if (endDate != null)
        {
            sql += " AND created <= ?";
        }
        
        if (emrIdOrders != null)
        {
            sql += " AND emrIdOrders = " + emrIdOrders;
        }
        
        List<EmrEventLog> eventLogs = new LinkedList<>();
        
        PreparedStatement pStmt = null;
        try
        {
            pStmt = conn.prepareStatement(sql);
            int i = 0;
            if (startDate != null)
            {
                java.sql.Date startDateSql = new java.sql.Date(startDate.getTime());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, startDateSql);
            }

            if (endDate != null)
            {
                java.sql.Date endDateSql = new java.sql.Date(endDate.getTime());
                SQLUtil.SafeSetTimeStamp(pStmt, ++i, endDateSql);
            }
            
            ResultSet rs = pStmt.executeQuery();
            

            while (rs.next())
            {
                eventLogs.add(getFromResultSet(rs));
            }

        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }

        return eventLogs;
    }
    
    private EmrEventLog getFromResultSet(ResultSet rs) throws SQLException
    {
        EmrEventLog eventLog = new EmrEventLog();
        eventLog.setIdEmrEventLog(rs.getInt("ideventLogs"));
        eventLog.setEmrEventTypeId(rs.getInt("idEmrEventTypeId"));
        eventLog.setCreated(rs.getDate("created"));
        eventLog.setUserId(rs.getInt("userId"));
        eventLog.setEmrXrefId(rs.getInt("emrXrefId"));
        eventLog.setEmrOrderId(rs.getString("emrOrderId"));
        eventLog.setEmrIdOrders(rs.getInt("emrIdOrders"));
        eventLog.setDescription(rs.getString("description"));
        eventLog.setUserNote(rs.getString("userNote"));

        return eventLog;
    }
    
    private String GenerateInsertStatement(List<String> fields)
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

    private String GenerateUpdateStatement(List<String> fields)
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
        return DatabaseStructureCheck.structureCheck(fields, table, conn);
    }
}
