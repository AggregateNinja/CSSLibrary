package EMR.DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.EmrXref;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import EMR.DOS.EmrIssue;
import EMR.DOS.EmrIssueType;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmrIssueDAO implements IDAO<EmrIssue>, IStructureCheckable
{
    
    public enum SearchType
    {
        ALL,
        NON_DELETED_ORDERS_ONLY,  // "Deleted" EMR orders are those that are marked active = 0
        DELETED_ORDERS_ONLY
    };
    
    public static final String table = "`emrorders`.`issues`";
    List<String> fields = new LinkedList<>();
    
    public EmrIssueDAO()
    {
        fields.add("orderId");
        fields.add("emrXrefId");
        fields.add("created");
        fields.add("issueTypeId");
        fields.add("vendorCode");
        fields.add("databaseIdentifier");
        fields.add("description");
    }
    
    @Override
    public EmrIssue Insert(EmrIssue emrIssue)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (emrIssue == null) throw new IllegalArgumentException(
                "EmrIssueDAO::Insert: Received a NULL EmrIssue object!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = GenerateInsertStatement(fields);
        PreparedStatement pStmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = pStmt.executeQuery();
        EmrIssue issue = getFromResultSet(rs);
        
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            issue.setIdEmrIssues(generatedKeys.getInt(1));
        }        
        pStmt.close();
        return issue;
    }

    private EmrIssue getFromResultSet(ResultSet rs)
            throws SQLException
    {
        EmrIssue issue = new EmrIssue();
        issue.setIdEmrIssues(rs.getInt("idIssues"));
        issue.setOrderId(rs.getInt("orderId"));
        issue.setEmrXrefId(rs.getInt("emrXrefId"));
        issue.setCreated(rs.getDate("created"));
        issue.setIssueTypeId(rs.getInt("issueTypeId"));
        issue.setVendorCode(rs.getString("vendorCode"));
        issue.setDatabaseIdentifier(rs.getInt("databaseIdentifier"));
        issue.setDescription(rs.getString("description"));
        return issue;
    }
    
    @Override
    public EmrIssue Update(EmrIssue emrIssue)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Updating lines not implemented");
    }

    @Override
    public void Delete(EmrIssue emrIssue)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
            if (emrIssue == null) throw new IllegalArgumentException(
                    "EmrIssueDAO::Delete: Received a NULL EmrIssue object!");
            if (emrIssue.getIdEmrIssues() == null || emrIssue.getIdEmrIssues().equals(0))
                throw new IllegalArgumentException("EmrIssueDAO::Delete: Received an EmrIssue with a missing unique identifier!");            
            
            Delete(emrIssue.getIdEmrIssues());
    }
    
    public static void Delete(Integer id)
        throws SQLException, IllegalArgumentException, NullPointerException        
    {
        if (id == null || id.equals(0)) throw new IllegalArgumentException(
                "EmrIssueDAO::Delete: Received a NULL identifier argument!");
     
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);        
        
        String sql = "DELETE FROM " + table + " WHERE idIssues = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        pStmt.executeUpdate();
        pStmt.close();
    }

    @Override
    public EmrIssue GetById(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id.equals(0)) throw new IllegalArgumentException(
                "EmrIssueDAO::Delete: Received a NULL or empty identifier!");
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);        
        
        String sql = "SELECT * FROM " + table + " WHERE idIssues = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        EmrIssue issue = null;
        if (rs.next())
        {
            issue = getFromResultSet(rs);
        }
        pStmt.close();
        return issue;
    }
    
    /**
     * Retrieves Emr issues for the given criteria. SearchType is the only
     *  required argument, all others will not be considered if suppled NULL
     * 
     * @param searchType Required field
     * @param startDate Pass in NULL for "no start date"
     * @param endDate Pass in NULL for "no end date"
     * @param emrXref Pass in NULL for "all interfaces"
     * @param issueType Pass in NULL for "all"
     * @param vendorCode Pass in NULL for "all"
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<EmrIssue> Get(SearchType searchType,
            Date startDate, Date endDate, EmrXref emrXref, EmrIssueType issueType, String vendorCode)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("EmrIssueDAO::Get: Recieved NULL searchType argument!");

        // Force NULL vendorcode if empty string
        if (vendorCode != null && vendorCode.isEmpty()) vendorCode = null;
        
        DatabaseSingleton dbs = DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT ei.* FROM " + table + " ei "
                + " INNER JOIN `emrorders`.`orders` eo ON eo.idOrders = ei.orderId "
                + "  WHERE 1=1 ";

        if (searchType.equals(SearchType.NON_DELETED_ORDERS_ONLY))
        {
            sql += " AND eo.active = 1";
        }
        if (searchType.equals(SearchType.DELETED_ORDERS_ONLY))
        {
            sql += " AND eo.active = 0";
        }
        
        if (emrXref != null)
        {
            sql += " AND ei.emrXrefId = " + emrXref.getIdEmrXref();
        }
        
        if (issueType != null)
        {
            sql += " AND ei.issueTypeId = " + issueType.getIdIssueTypes();
        }        
        
        if (startDate != null)
        {
            sql += " AND ei.created >= ?";
        }
        
        if (endDate != null)
        {
            sql += " AND ei.created <= ?";
        }
        
        if (vendorCode != null)
        {
            sql += " AND vendorCode = ?";
        }
        
        List<EmrIssue> issues = new LinkedList<>();
        
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
            
            if (vendorCode != null)
            {
                SQLUtil.SafeSetString(pStmt, ++i, vendorCode);
            }
            
            ResultSet rs = pStmt.executeQuery();
            

            while (rs.next())
            {
                issues.add(getFromResultSet(rs));
            }

        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }        
        
        return issues;
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
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
