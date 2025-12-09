
package DAOS;

import DOS.CptModifierResultLookup;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Rob
 */
public class CptModifierResultLookupDAO
{
    public static final String table = "`cptModifierResultLookup`";
    
    public enum SearchType
    {
        ANY,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    // Static calls only
    private CptModifierResultLookupDAO() {};
    
    public static void Insert(CptModifierResultLookup lookup) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (lookup == null)
        {
            throw new IllegalArgumentException("CptModifierResultLookupDAO::Insert: Received a NULL CptModifierResultLookup object");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "INSERT INTO " + table
                + " (resultId,"
                + "  cptModifierId,"
                + "  createdById,"
                + "  active)"
                + "VALUES (?,?,?,?)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, lookup.getResultId());
        pStmt.setInt(++i, lookup.getCptModifierId());
        pStmt.setInt(++i, lookup.getCreatedById());
        pStmt.setBoolean(++i, lookup.isActive());
        int result = 0;
        try
        {
            result = pStmt.executeUpdate();
            if (result < 0)
            {
                String errorDetails = " resultId:" + lookup.getResultId()
                        + " cptModifierId:" + lookup.getCptModifierId()
                        + " active:" + lookup.isActive();
                throw new SQLException("CptModifierResultLookupDAO::Insert: Row was not inserted!" + errorDetails);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    /**
     * Marks the CptModifierLookup row associated with the given IDs as inactive
     * @param resultId
     * @param cptModifierId
     * @param userId
     * @param deactivatedDate Use database server time
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void Deactivate(int resultId, int cptModifierId, int userId, Date deactivatedDate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (userId <= 0) throw new IllegalArgumentException("CptModifierResultLookupDAO::Deactivate: Received userId of " + userId);
        if (deactivatedDate == null) throw new IllegalArgumentException("CptModifierResultLookupDAO::Deactivate: Received NULL deactivatedDate");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "UPDATE " + table
                + " SET active = 0,"
                + "   deactivatedById = ?,"
                + "   deactivatedDate = ? "
                + " WHERE active = 1"
                + "   AND resultId = ? AND cptModifierId = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, userId);
        SQLUtil.SafeSetDate(pStmt, ++i, deactivatedDate);
        pStmt.setInt(++i, resultId);
        pStmt.setInt(++i, cptModifierId);
        int affected = pStmt.executeUpdate();
        if (affected < 1)
        {
            String messageDetail = "resultId = " + resultId
                    + " cptModifierId = " + cptModifierId;
            throw new SQLException("CptModifierResultLookupDAO::Deactivate: Could not update row to deactivate " + messageDetail);
        }
        if (affected > 1)
        {
            String messageDetail = "resultId = " + resultId
                    + " cptModifierId = " + cptModifierId;
            throw new SQLException("CptModifierResultLookupDAO::Deactivate: Deactivating cptModifierResultLookup updated "
                    + affected + " rows! There should only be one. " + messageDetail);
        }
    }
    
    /**
     * Returns CPT Modifier Lookup rows based on the supplied criteria.
     *  If CptModifierId is NULL, all lookups for the supplied cptLookupId will
     *  be returned.
     *    
     * @param resultId
     * @param cptModifierId Can be NULL
     * @param searchType Cannot be NULL
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static List<CptModifierResultLookup> Get(int resultId, Integer cptModifierId, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("CptModifierResultLookupDAO::Get: Received a NULL SearchType parameter");

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE resultId = " + resultId;
        
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (cptModifierId != null && cptModifierId.equals(0) == false)
        {
            sql += " AND cptModifierId = " + cptModifierId;
        }
        
        List<CptModifierResultLookup> lookups = new LinkedList<>();
        try
        {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                lookups.add(GetObjectFromResultSet(rs));
            }
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {
            System.out.println("Syntax exception (does cptModifierResultLookup exist?): " + ex.toString() + " - " + ex.getMessage());
        }
        return lookups;
    }
    
    private static CptModifierResultLookup GetObjectFromResultSet(ResultSet rs) throws SQLException
    {
        CptModifierResultLookup cptModifierLookup = new CptModifierResultLookup();
        cptModifierLookup.setResultId(rs.getInt("resultId"));
        cptModifierLookup.setCptModifierId(rs.getInt("cptModifierId"));
        cptModifierLookup.setCreatedById(rs.getInt("createdById"));
        cptModifierLookup.setCreatedDate(rs.getTimestamp("createdDate"));
        cptModifierLookup.setDeactivatedById(rs.getInt("deactivatedById"));
        cptModifierLookup.setDeactivatedDate(rs.getTimestamp("deactivatedDate"));
        cptModifierLookup.setActive(rs.getBoolean("active"));
        return cptModifierLookup;
    }
}
