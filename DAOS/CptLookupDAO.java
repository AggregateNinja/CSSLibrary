
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.CptLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CptLookupDAO implements IStructureCheckable
{
    private static final String table = "`cptLookup`";
    
    public enum SearchType
    {
        ANY,
        ACTIVE_ONLY,
        INACTIVE_ONLY
    }
    
    public CptLookupDAO() {}

    public static void insert(CptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("CPTCodeDAO::Insert: Received a NULL CptCode object");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "INSERT INTO " + table
                + " (insuranceId,"
                + "  testNumber,"
                + "  cptCodeId,"
                + "  cost,"
                + "  quantity,"
                + "  breakDown,"
                + "  startDate,"
                + "  endDate,"
                + "  createdById,"
                + "  deactivatedById,"
                + "  deactivatedDate,"
                + "  active)"
                + "VALUES (?,?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, obj.getInsuranceId());
        pStmt.setInt(++i, obj.getTestNumber());
        pStmt.setInt(++i, obj.getCptCodeId());
        pStmt.setBigDecimal(++i, obj.getCost());
        pStmt.setInt(++i, obj.getQuantity());
        pStmt.setBoolean(++i, obj.isBreakDown());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getStartDate());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getEndDate());
        pStmt.setInt(++i, obj.getCreatedById());
        // Created Date auto-set by table definition
        pStmt.setInt(++i, obj.getDeactivatedById());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getDeactivatedDate());
        pStmt.setBoolean(++i, obj.isActive());
        
        int result = 0;
        try
        {
            result = pStmt.executeUpdate();
            if (result < 0)
            {
                String errorDetails = " insuranceId:" + obj.getInsuranceId()
                        + " cptCodeId:" + obj.getCptCodeId()
                        + " testNumber:" + obj.getTestNumber();
                throw new SQLException("CptLookupDAO::Insert: Row was not inserted!" + errorDetails);
            }
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }

    /**
     * Marks the supplied CptLookup row as inactive
     * @param obj
     * @param userId
     * @param deactivatedDate Use database server time
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void deactivate(CptLookup obj, int userId, Date deactivatedDate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null) throw new IllegalArgumentException("CptLookupDAO::Deactivate: Received a NULL CptLookup object");
        if (userId <= 0) throw new IllegalArgumentException("CptLookupDAO::Deactivate: Received userId of " + userId);
        if (deactivatedDate == null) throw new IllegalArgumentException("CptLookupDAO::Deactivate: Received NULL deactivatedDate");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);     
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "UPDATE " + table
                + " SET active = 0,"
                + "   deactivatedById = ?,"
                + "   deactivatedDate = ? "
                + " WHERE active = 1"
                + "   AND idCptLookup = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, userId);
        SQLUtil.SafeSetDate(pStmt, ++i, deactivatedDate);
        pStmt.setInt(++i, obj.getIdCptLookup());
        int affected = pStmt.executeUpdate();
        if (affected < 1)
        {
            String messageDetail = "insuranceId = " + obj.getInsuranceId()
                    + " testNumber = " + obj.getTestNumber()
                    + " cptCodeId=" + obj.getCptCodeId()
                    + " active=" + obj.isActive();
            throw new SQLException("CptLookupDAO::Deactivate: Could not update row to deactivate " + messageDetail);
        }
        if (affected > 1)
        {
            String messageDetail = "insuranceId = " + obj.getInsuranceId()
                    + " testNumber = " + obj.getTestNumber()
                    + " cptCodeId=" + obj.getCptCodeId()
                    + " active=" + obj.isActive();
            throw new SQLException("CptLookupDAO::Deactivate: Deactivating cptLookup updated "
                    + affected + " rows! There should only be one. " + messageDetail);
        }
        pStmt.close();
    }

    /**
     * Returns CPT Lookup rows based on the supplied criteria. If an identifier
     *  argument is NULL or zero (0), procedure will exclude that criteria from
     *  the search.
     * 
     *  e.g. searching
     *      insuranceId = 5
     *      testNumber = NULL or 0
     *      cptId = NULL or 0
     * 
     *  Will search every lookup row for insuranceId 5 that matches the
     *   SearchType enum.
     *    
     * @param insuranceId
     * @param testNumber
     * @param cptId
     * @param searchType Cannot be NULL
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static List<CptLookup> get(Integer insuranceId, Integer testNumber, Integer cptId, SearchType searchType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (searchType == null) throw new IllegalArgumentException("CptLookupDAO::Get: Received a NULL SearchType parameter");

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE 1=1 ";
        
        if (searchType == SearchType.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        if (searchType == SearchType.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (insuranceId != null && insuranceId.equals(0) == false)
        {
            sql += " AND insuranceId = " + insuranceId;
        }
        
        if (testNumber != null && testNumber.equals(0) == false)
        {
            sql += " AND testNumber = " + testNumber;
        }
        
        if (cptId != null && cptId.equals(0) == false)
        {
            sql += " AND cptId = " + cptId;
        }
        
        List<CptLookup> lookups = new LinkedList<>();
        try
        {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                lookups.add(GetObjectFromResultSet(rs));
            }
            pStmt.close();
        }
        catch (SQLIntegrityConstraintViolationException ex)
        {
            System.out.println("Syntax exception (does cptLookup exist?): " + ex.toString() + " - " + ex.getMessage());
        }
        
        return lookups;
    }
    
    public static CptLookup getById(int id) throws IllegalArgumentException, SQLException
    {
        if (id <= 0) throw new IllegalArgumentException("Received a cptLookupId of " + id);
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE idCptLookup = " + id;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        CptLookup lookup = null;
        if (rs.next())
        {
            lookup = GetObjectFromResultSet(rs);
        }
        pStmt.close();
        return lookup;
    }
    
    private static CptLookup GetObjectFromResultSet(ResultSet rs) throws SQLException
    {
        CptLookup lookup = new CptLookup();
        lookup.setIdCptLookup(rs.getInt("idCptLookup"));
        lookup.setInsuranceId(rs.getInt("insuranceId"));
        lookup.setTestNumber(rs.getInt("testNumber"));
        lookup.setCptCodeId(rs.getInt("cptCodeId"));
        lookup.setCost(rs.getBigDecimal("cost"));
        lookup.setQuantity(rs.getInt("quantity"));
        lookup.setBreakDown(rs.getBoolean("breakDown"));
        lookup.setStartDate(rs.getDate("startDate"));
        lookup.setEndDate(rs.getDate("endDate"));
        lookup.setCreatedById(rs.getInt("createdById"));
        lookup.setCreatedDate(rs.getDate("createdDate"));
        lookup.setDeactivatedById(rs.getInt("deactivatedById"));
        lookup.setDeactivatedDate(rs.getDate("deactivatedDate"));
        lookup.setActive(rs.getBoolean("active"));
        return lookup;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `cptLookup`.`idCptLookup`,\n"
                + "    `cptLookup`.`insuranceId`,\n"
                + "    `cptLookup`.`testNumber`,\n"
                + "    `cptLookup`.`cptCodeId`,\n"
                + "    `cptLookup`.`cost`,\n"
                + "    `cptLookup`.`breakDown`,\n"
                + "    `cptLookup`.`startDate`,\n"
                + "    `cptLookup`.`endDate`,\n"
                + "    `cptLookup`.`createdById`,\n"
                + "    `cptLookup`.`createdDate`,\n"
                + "    `cptLookup`.`deactivatedById`,\n"
                + "    `cptLookup`.`deactivatedDate`,\n"
                + "    `cptLookup`.`active`\n"
                + "FROM `css`.`cptLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
