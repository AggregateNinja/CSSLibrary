package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubmissionQueue;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionQueueDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema()+ ".`submissionQueue`";

    public static SubmissionQueue insert(SubmissionQueue obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }
    
    public static SubmissionQueue insert(Connection con, SubmissionQueue obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionQueueDAO::Insert:"
                    + " Received a NULL SubmissionQueue object");
        }


        String sql = "INSERT INTO " + table
                + "("
                + "  submissionBatchId,"
                + "  detailOrderId,"
                + "  detailInsuranceId,"
                + "  billAmount,"
                + "  submitted,"
                + "  submittedDate,"
                + "  rejected"
                + ")"
                + "VALUES (?,?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
        pStmt.setBigDecimal(++i, obj.getBillAmount());
        SQLUtil.BooleanToInt(pStmt, ++i, obj.isSubmitted(), false);
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));
        SQLUtil.BooleanToInt(pStmt, ++i, obj.isRejected(), false);
        
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
                throw new NullPointerException("SubmissionQueueDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSubmissionQueue(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(SubmissionQueue obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionQueueDAO::Update:"
                    + " Received a NULL SubmissionQueue object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " submissionBatchId = ?,"
                + " detailOrderId = ?,"
                + " detailInsuranceId = ?,"
                + " billAmount = ?,"
                + " submitted = ?,"
                + " submittedDate = ?,"
                + " rejected = ?"
                + " WHERE idSubmissionQueue = " + obj.getIdSubmissionQueue();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
            pStmt.setBigDecimal(++i, obj.getBillAmount());
            SQLUtil.BooleanToInt(pStmt, ++i, obj.isSubmitted(), false);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));
            SQLUtil.BooleanToInt(pStmt, ++i, obj.isRejected(), false);
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

    public static SubmissionQueue get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SubmissionQueueDAO::get:"
                    + "Received a NULL or empty SubmissionQueue object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idSubmissionQueue = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        SubmissionQueue obj = null;
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
     * Returns a Submission Queue object searched by Submission Batch Id and Detail Order Id.
     * Used in the AvalonBilling Program
     * 
     * @param id The Submission Batch Id
     * @param detailOrderId The Detail Order Id
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static SubmissionQueue GetByBatchIdDetailOrderId(int id, int detailOrderId) throws SQLException,
            IllegalArgumentException, NullPointerException
    {
        SubmissionQueue obj = new SubmissionQueue();
        
        if (id <= 0 || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("SubmissionQueueDAO::GetByBatchIdDetailOrderId:"
                    + "Received a NULL or empty SubmissionQueue object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE submissionBatchId = ? AND detailOrderId = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        
        SQLUtil.SafeSetInteger(pStmt, 1, id);
        SQLUtil.SafeSetInteger(pStmt, 2, detailOrderId);
        
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
            obj = ObjectFromResultSet(rs);
        
        return obj;
    }
    
    // <editor-fold defaultstate="collapsed" desc="delete (SubmissionQueue)">
    public static void delete(SubmissionQueue obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        delete(con, obj);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="delete (Connection, SubmissionQueue)">
    public static void delete(Connection con, SubmissionQueue obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null || obj.getIdSubmissionQueue() == null || obj.getIdSubmissionQueue() <= 0)
        {
            throw new IllegalArgumentException("SubmissionQueueDAO::Insert:"
                    + " Received an invalid SubmissionQueue object");
        }
        
        String sql = "DELETE FROM " + table + " WHERE `idsubmissionQueue` = " + obj.getIdSubmissionQueue();
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    // </editor-fold>

    private static SubmissionQueue ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        SubmissionQueue obj = new SubmissionQueue();
        obj.setIdSubmissionQueue(rs.getInt("idSubmissionQueue"));
        obj.setSubmissionBatchId(rs.getInt("submissionBatchId"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setDetailInsuranceId(rs.getInt("detailInsuranceId"));
        obj.setBillAmount(rs.getBigDecimal("billAmount"));
        obj.setSubmitted((rs.getInt("submitted") == 0? false : true));
        obj.setSubmittedDate(rs.getDate("submittedDate"));
        obj.setRejected((rs.getInt("rejected") == 0? false : true));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `submissionQueue`.`idSubmissionQueue`,\n"
                + "    `submissionQueue`.`submissionBatchId`,\n"
                + "    `submissionQueue`.`detailOrderId`,\n"
                + "    `submissionQueue`.`detailInsuranceId`,\n"
                + "    `submissionQueue`.`billAmount`,\n"
                + "    `submissionQueue`.`submitted`,\n"
                + "    `submissionQueue`.`submittedDate`,\n"
                + "    `submissionQueue`.`rejected`\n"
                + "FROM `cssbilling`.`submissionQueue` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
