
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.CptModifierLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author TomR
 */
public class CptModifierLookupDAO implements IStructureCheckable
{
    public static final String table = "`cptModifierLookup`";
    
    public enum SearchType
    {
        ANY,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    // Static calls only
    public CptModifierLookupDAO() {};
    
    public static void Insert(CptModifierLookup lookup) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (lookup == null)
        {
            throw new IllegalArgumentException("CPTModifierLookupDAO::Insert: Received a NULL CptModifierLookup object");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "INSERT INTO " + table
                + " (cptLookupId,"
                + "  cptModifierId,"
                + "  createdById,"
                + "  active)"
                + "VALUES (?,?,?,?)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, lookup.getCptLookupId());
        pStmt.setInt(++i, lookup.getCptModifierId());
        pStmt.setInt(++i, lookup.getCreatedById());
        pStmt.setBoolean(++i, lookup.isActive());
        int result = 0;
        try
        {
            result = pStmt.executeUpdate();
            if (result < 0)
            {
                String errorDetails = " cptLookupId:" + lookup.getCptLookupId()
                        + " cptModifierId:" + lookup.getCptModifierId()
                        + " active:" + lookup.isActive();
                throw new SQLException("CPTModifierLookupDAO::Insert: Row was not inserted!" + errorDetails);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    /**
     * Marks the CptModifierLookup row associated with the given IDs as inactive
     * @param cptLookupId
     * @param cptModifierId
     * @param userId
     * @param deactivatedDate Use database server time
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void Deactivate(int cptLookupId, int cptModifierId, int userId, Date deactivatedDate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (userId <= 0) throw new IllegalArgumentException("CptModifierLookupDAO::Deactivate: Received userId of " + userId);
        if (deactivatedDate == null) throw new IllegalArgumentException("CptModifierLookupDAO::Deactivate: Received NULL deactivatedDate");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "UPDATE " + table
                + "   SET active = 0,"
                + "   deactivatedById = ?,"
                + "   deactivatedDate = ? "
                + " WHERE active = 1"
                + "   AND cptLookupId = ? AND cptModifierId = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, userId);
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, deactivatedDate);
        pStmt.setInt(++i, cptLookupId);
        pStmt.setInt(++i, cptModifierId);
        int affected = pStmt.executeUpdate();
        if (affected < 1)
        {
            String messageDetail = "cptLookupId = " + cptLookupId
                    + " cptModifierId = " + cptModifierId;
            throw new SQLException("CptModifierLookupDAO::Deactivate: Could not update row to deactivate " + messageDetail);
        }
        if (affected > 1)
        {
            String messageDetail = "cptLookupId = " + cptLookupId
                    + " cptModifierId = " + cptModifierId;
            throw new SQLException("CptModifierLookupDAO::Deactivate: Deactivating cptModifierLookup updated "
                    + affected + " rows! There should only be one. " + messageDetail);
        }
        pStmt.close();
    }
    
    public static List<CptModifierLookup> Get(int cptLookupId, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        return Get(cptLookupId, null, searchType);
    }
    
    /**
     * Returns CPT Modifier Lookup rows based on the supplied criteria.
     *  If CptModifierId is NULL, all lookups for the supplied cptLookupId will
     *  be returned.
     *    
     * @param cptLookupId
     * @param cptModifierId Can be NULL
     * @param searchType Cannot be NULL
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static List<CptModifierLookup> Get(int cptLookupId, Integer cptModifierId, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("CptModifierLookupDAO::Get: Received a NULL SearchType parameter");

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE cptLookupId = " + cptLookupId;
        
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
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<CptModifierLookup> lookups = new LinkedList<>();
        while (rs.next())
        {
            lookups.add(GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return lookups;
    }
    
    private static CptModifierLookup GetObjectFromResultSet(ResultSet rs) throws SQLException
    {
        CptModifierLookup cptModifierLookup = new CptModifierLookup();
        cptModifierLookup.setCptLookupId(rs.getInt("cptLookupId"));
        cptModifierLookup.setCptModifierId(rs.getInt("cptModifierId"));
        cptModifierLookup.setCreatedById(rs.getInt("createdById"));
        cptModifierLookup.setCreatedDate(rs.getTimestamp("createdDate"));
        cptModifierLookup.setDeactivatedById(rs.getInt("deactivatedById"));
        cptModifierLookup.setDeactivatedDate(rs.getTimestamp("deactivatedDate"));
        cptModifierLookup.setActive(rs.getBoolean("active"));
        return cptModifierLookup;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `cptModifierLookup`.`cptLookupId`,\n"
                + "    `cptModifierLookup`.`cptModifierId`,\n"
                + "    `cptModifierLookup`.`createdById`,\n"
                + "    `cptModifierLookup`.`createdDate`,\n"
                + "    `cptModifierLookup`.`deactivatedById`,\n"
                + "    `cptModifierLookup`.`deactivatedDate`,\n"
                + "    `cptModifierLookup`.`active`\n"
                + "FROM `css`.`cptModifierLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
