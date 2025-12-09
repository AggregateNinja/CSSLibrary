package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeSchedule;
import DOS.FeeScheduleAssignment;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FeeScheduleAssignmentDAO implements IStructureCheckable
{

    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`feeScheduleAssignments`";

    public enum Status
    {

        ACTIVE, // The assignment is active based on the start/end dates
        FUTURE, // The assignment hasn't taken place yet based on start/end dates
        PAST    // The end date of the assignment is prior to the current day
    }
    
    public static FeeScheduleAssignment insert(FeeScheduleAssignment obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::Insert: Received a NULL FeeScheduleAssignment object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  feeScheduleId,"
                + "  billingPayorId,"
                + "  rateAdjustment,"
                + "  startDate,"
                + "  endDate"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBillingPayorId());
        pStmt.setFloat(++i, obj.getRateAdjustment());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getStartDate());
        SQLUtil.SafeSetDate(pStmt, ++i, obj.getEndDate());

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("FeeScheduleAssignmentDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeScheduleAssignments(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(FeeScheduleAssignment obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::Update: Received a NULL FeeScheduleAssignment object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " feeScheduleId = ?,"
                + " billingPayorId = ?,"
                + " rateAdjustment = ?,"
                + " startDate = ?,"
                + " endDate = ?"
                + " WHERE idFeeScheduleAssignments = " + obj.getIdFeeScheduleAssignments();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBillingPayorId());
            pStmt.setDouble(++i, obj.getRateAdjustment());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getStartDate());
            SQLUtil.SafeSetDate(pStmt, ++i, obj.getEndDate());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }

    public static FeeScheduleAssignment get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::Get: Received a NULL or empty FeeScheduleAssignment object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeScheduleAssignments = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        FeeScheduleAssignment obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }
    
    /**
     * Deletes the argument row - Log data being removed before calling!
     * @param feeScheduleAssignmentId
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static void delete(int feeScheduleAssignmentId)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (feeScheduleAssignmentId <= 0)
        {
            throw new IllegalArgumentException(
                    "FeeScheduleAssignmentDAO:delete: Received a feeScheduleAssignmentId of " + feeScheduleAssignmentId);
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "DELETE FROM " + table + " WHERE idFeeScheduleAssignments = ?";
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.setInt(1, feeScheduleAssignmentId);
        pStmt.execute();
        pStmt.close();
    }
    
    private static PreparedStatement setPreparedStatementRanges(PreparedStatement pStmt, Date startDate, Date endDate) throws SQLException
    {
        if (startDate == null)
        {
            pStmt.setNull(1, java.sql.Types.DATE);
        }
        else
        {
            pStmt.setDate(1, new java.sql.Date(startDate.getTime()));
        }
        
        if (endDate == null)
        {
            pStmt.setNull(2, java.sql.Types.DATE);
        }
        else
        {
            pStmt.setDate(2, new java.sql.Date(endDate.getTime()));
        }
        
        return pStmt;
    }
    
    public static List<FeeScheduleAssignment> getByDateRange(Date startDate, Date endDate)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        List<FeeScheduleAssignment> assignments = new LinkedList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE F_DATE_RANGES_OVERLAP(?, ?, startDate, endDate)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                assignments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return assignments;
    }
    
    public static List<FeeScheduleAssignment> getByFeeScheduleDateRange(int feeScheduleId, Date startDate, Date endDate)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        List<FeeScheduleAssignment> assignments = new LinkedList<>();
        if (feeScheduleId <= 0)
        {
            return assignments;
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE feeScheduleId = " + feeScheduleId
                + " AND F_DATE_RANGES_OVERLAP(?, ?, startDate, endDate)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                assignments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return assignments;
    }
    
    public static List<FeeScheduleAssignment> getByBillingPayorId(Integer billingPayorId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        List<FeeScheduleAssignment> assignments = new LinkedList<>();
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByBillingPayorId:"
                    + " Received a [NULL] or invalid billingPayorId integer argument");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE billingPayorId = " + billingPayorId.toString();
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                assignments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return assignments;        
    }
    
    public static List<FeeScheduleAssignment> getByBillingPayorIdDateRange(
            Integer billingPayorId, Date startDate, Date endDate)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        List<FeeScheduleAssignment> assignments = new LinkedList<>();
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByBillingPayorIdDateRange:"
                    + " Received a [NULL] or invalid billingPayorId integer argument");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE billingPayorId = "
                + billingPayorId.toString()
                + " AND  F_DATE_RANGES_OVERLAP(?, ?, startDate, endDate)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                assignments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return assignments;        
    }
    
    public static List<FeeScheduleAssignment> getByBillingPayorIdStatus(
            Integer billingPayorId, Date orderDate, Status assignmentStatus)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByBillingPayorIdStatus:"
                    + " Received a [NULL] or invalid billingPayorId integer argument");
        }
        
        if (assignmentStatus == null)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByBillingPayorIdStatus"
                    + " Received a [NULL] assignment status enumerator");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE billingPayorId = "
                + billingPayorId.toString();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        switch (assignmentStatus)
        {
            case ACTIVE:
                sql += " AND F_DATE_RANGES_OVERLAP(DATE('" + sdf.format(orderDate) + "'),DATE('" + sdf.format(orderDate) + "'),startDate, endDate) = TRUE;";
                break;
            case FUTURE:
                sql += " AND startDate >= DATE(DATE_ADD('" + sdf.format(orderDate) + "', INTERVAL 1 DAY))";
                break;
            case PAST:
                // Anything up to and including yesterday
                sql += " AND F_DATE_RANGES_OVERLAP(NULL, DATE_SUB(DATE('" + sdf.format(orderDate) + "'), INTERVAL 1 DAY, startDate, endDate) = TRUE;";
                break;
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<FeeScheduleAssignment> assignments = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                assignments.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return assignments;        
    }    
    
    /**
     * Returns a collection of any fee schedule assignment matching the status:
     *  past, active, or future.
     * 
     * Returns an empty collection if no assignments are found matching the status.
     * @param status
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<FeeScheduleAssignment> getByStatus(Status status)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (status == null)
        {
            throw new IllegalArgumentException("FeeScheduleAssignmentDAO:getByStatus:"
                    + " Received a [NULL] Status enum argument (PAST, ACTIVE, FUTURE)");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE ";
        
        switch (status)
        {
            case ACTIVE:
                sql += " F_DATE_RANGES_OVERLAP(DATE(NOW()),DATE(NOW()),startDate, endDate) = TRUE;";
                break;
            case FUTURE:
                sql += " startDate >= DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))";
                break;
            case PAST:
                // Anything up to and including yesterday
                sql += " F_DATE_RANGES_OVERLAP(NULL, DATE_SUB(DATE(NOW()), INTERVAL 1 DAY, startDate, endDate) = TRUE;";
                break;
        }
        
        Collection<FeeScheduleAssignment> feeScheduleAssignments= new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                feeScheduleAssignments.add(ObjectFromResultSet(rs));
            }
        }
        
        return feeScheduleAssignments;
    }
//
//    public static FeeScheduleAssignment getByInsuranceIdStatus(int insuranceId, Status assignmentStatus)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (insuranceId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByInsuranceIdStatus: Received insuranceId of " + insuranceId);
//        }
//        if (assignmentStatus == null)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO:getByInsuranceIdStatus: received NULL Status");
//        }
//
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE insuranceId = " + String.valueOf(insuranceId);
//
//        switch (assignmentStatus)
//        {
//            case ACTIVE:
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE(NOW()),DATE(NOW()),startDate, endDate) = TRUE;";
//                break;
//            case FUTURE:
//                // Anything from tomorrow forward
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE_ADD(DATE(NOW()), INTERVAL 1 DAY), NULL, startDate, endDate) = TRUE;";
//                break;
//            case PAST:
//                // Anything up to and including yesterday
//                sql += " AND F_DATE_RANGES_OVERLAP(NULL, DATE_SUB(DATE(NOW()), INTERVAL 1 DAY, startDate, endDate) = TRUE;";
//                break;
//        }
//
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment assignment = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            if (rs.next())
//            {
//                assignment = ObjectFromResultSet(rs);
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignment;
//    }
//
//    public static List<FeeScheduleAssignment> getByInsuranceId(int insuranceId)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (insuranceId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByInsuranceId: Received insuranceId of " + insuranceId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE insuranceId = " + String.valueOf(insuranceId);
//
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }
//
//    public static List<FeeScheduleAssignment> getByInsuranceIdDateRange(int insuranceId, Date startDate, Date endDate)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (insuranceId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByInsuranceId: Received insuranceId of " + insuranceId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE insuranceId = " + String.valueOf(insuranceId)
//                + " AND F_DATE_RANGES_OVERLAP(startDate, endDate, ?, ?) = TRUE";
//        
//        PreparedStatement pStmt = con.prepareStatement(sql);
//        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
//
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }
//
//    public static FeeScheduleAssignment getByClientIdStatus(int clientId, Status assignmentStatus)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (clientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByClientIdStatus: Received clientId of " + clientId);
//        }
//        if (assignmentStatus == null)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO:getByClientIdStatus: received NULL Status");
//        }
//
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(clientId);
//
//        switch (assignmentStatus)
//        {
//            case ACTIVE:
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE(NOW()),DATE(NOW()),startDate, endDate) = TRUE;";
//                break;
//            case FUTURE:
//                // Anything from tomorrow forward
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE_ADD(DATE(NOW()), INTERVAL 1 DAY), NULL, startDate, endDate) = TRUE;";
//                break;
//            case PAST:
//                // Anything up to and including yesterday
//                sql += " AND F_DATE_RANGES_OVERLAP(NULL, DATE_SUB(DATE(NOW()), INTERVAL 1 DAY, startDate, endDate) = TRUE;";
//                break;
//        }
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment assignment = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            if (rs.next())
//            {
//                assignment = ObjectFromResultSet(rs);
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignment;
//    }
//
//    public static List<FeeScheduleAssignment> getByClientId(int clientId)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (clientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByClientId: Received clientId of " + clientId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(clientId);
//
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }
//
//    /**
//     *
//     * @param clientId
//     * @param startDate
//     * @param endDate
//     * @return
//     * @throws java.sql.SQLException
//     */
//    public static List<FeeScheduleAssignment> getByClientIdDateRange(int clientId, Date startDate, Date endDate)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (clientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByClientIdDateRange: Received clientId of " + clientId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE clientId = " + String.valueOf(clientId)
//                + " AND F_DATE_RANGES_OVERLAP(startDate, endDate, ?, ?) = TRUE";
//        
//        PreparedStatement pStmt = con.prepareStatement(sql);
//        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
//        
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }
//    
//    public static FeeScheduleAssignment getByPatientIdStatus(int patientId, Status assignmentStatus)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (patientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByPatientIdStatus: Received patientId of " + patientId);
//        }
//        if (assignmentStatus == null)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO:getByPatientIdStatus: received NULL Status");
//        }
//
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE patientId = " + String.valueOf(patientId);
//
//        switch (assignmentStatus)
//        {
//            case ACTIVE:
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE(NOW()),DATE(NOW()),startDate, endDate) = TRUE;";
//                break;
//            case FUTURE:
//                // Anything from tomorrow forward
//                sql += " AND F_DATE_RANGES_OVERLAP(DATE_ADD(DATE(NOW()), INTERVAL 1 DAY), NULL, startDate, endDate) = TRUE;";
//                break;
//            case PAST:
//                // Anything up to and including yesterday
//                sql += " AND F_DATE_RANGES_OVERLAP(NULL, DATE_SUB(DATE(NOW()), INTERVAL 1 DAY, startDate, endDate) = TRUE;";
//                break;
//        }
//
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment assignment = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            if (rs.next())
//            {
//                assignment = ObjectFromResultSet(rs);
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignment;
//    }
//
//    public static List<FeeScheduleAssignment> getByPatientId(int patientId)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (patientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByPatientId: Received clientId of " + patientId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE patientId = " + String.valueOf(patientId);
//
//        PreparedStatement pStmt = con.prepareStatement(sql);
//
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }
//
//    /**
//     *
//     * @param patientId
//     * @param startDate
//     * @param endDate
//     * @return
//     * @throws java.sql.SQLException
//     */
//    public static List<FeeScheduleAssignment> getByPatientIdDateRange(int patientId, Date startDate, Date endDate)
//            throws SQLException, NullPointerException, IllegalArgumentException
//    {
//        if (patientId <= 0)
//        {
//            throw new IllegalArgumentException("FeeScheduleAssignmentDAO::getByPatientIdDateRange: Received clientId of " + patientId);
//        }
//        List<FeeScheduleAssignment> assignments = new LinkedList<>();
//        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
//        Connection con = dbs.getConnection(true);
//        if (con.isValid(2) == false)
//        {
//            con = CheckDBConnection.Check(dbs, con);
//        }
//
//        String sql = "SELECT * FROM " + table + " WHERE patientId = " + String.valueOf(patientId)
//                + " AND F_DATE_RANGES_OVERLAP(startDate, endDate, ?, ?) = TRUE";
//        
//        PreparedStatement pStmt = con.prepareStatement(sql);
//        pStmt = setPreparedStatementRanges(pStmt, startDate, endDate);
//        FeeScheduleAssignment obj = null;
//        try
//        {
//            ResultSet rs = pStmt.executeQuery();
//            while (rs.next())
//            {
//                assignments.add(ObjectFromResultSet(rs));
//            }
//        }
//        catch (SQLException ex)
//        {
//            System.out.println(ex.getMessage() + " " + sql);
//            throw new SQLException(ex.getMessage() + " " + sql);
//        }
//
//        return assignments;
//    }    
    
        // todo : get by patientid
    public static FeeScheduleAssignment ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        FeeScheduleAssignment obj = new FeeScheduleAssignment();
        obj.setIdFeeScheduleAssignments(rs.getInt("idFeeScheduleAssignments"));
        obj.setFeeScheduleId(rs.getInt("feeScheduleId"));
        obj.setBillingPayorId(rs.getInt("billingPayorId"));
        obj.setRateAdjustment(rs.getFloat("rateAdjustment"));
        obj.setStartDate(rs.getDate("startDate"));
        obj.setEndDate(rs.getDate("endDate"));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `feeScheduleAssignments`.`idFeeScheduleAssignments`,\n"
                + "    `feeScheduleAssignments`.`feeScheduleId`,\n"
                + "    `feeScheduleAssignments`.`billingPayorId`,\n"
                + "    `feeScheduleAssignments`.`rateAdjustment`,\n"
                + "    `feeScheduleAssignments`.`startDate`,\n"
                + "    `feeScheduleAssignments`.`endDate`\n"
                + "FROM `css`.`feeScheduleAssignments` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
