package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubmissionEvent;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubmissionEventDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`submissionEvents`";

    public static SubmissionEvent insert(SubmissionEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }
    
    public static SubmissionEvent insert(Connection con, SubmissionEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionEventDAO::Insert:"
                    + " Received a NULL SubmissionEvent object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  submissionBatchId,"
                + "  submissionQueueId,"
                + "  eventId,"
                + "  description,"
                + "  userId"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getSubmissionQueueId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());

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
                throw new NullPointerException("SubmissionEventDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSubmissionEvents(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(SubmissionEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionEventDAO::Update:"
                    + " Received a NULL SubmissionEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " submissionBatchId = ?,"
                + " submissionQueueId = ?,"
                + " eventId = ?,"
                + " description = ?,"
                + " userId = ?"
                + " WHERE idSubmissionEvents = " + obj.getIdSubmissionEvents();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getSubmissionQueueId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
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

    public static SubmissionEvent get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SubmissionEventDAO::Get: Received a NULL or empty SubmissionEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idSubmissionEvents = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        SubmissionEvent obj = null;
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

    public static SubmissionEvent ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        SubmissionEvent obj = new SubmissionEvent();
        obj.setIdSubmissionEvents(rs.getInt("idSubmissionEvents"));
        obj.setSubmissionBatchId(rs.getInt("submissionBatchId"));
        obj.setSubmissionQueueId(SQLUtil.getInteger(rs, "submissionQueueId"));
        obj.setEventId(rs.getInt("eventId"));
        obj.setDescription(rs.getString("description"));
        obj.setUserId(rs.getInt("userId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `submissionEvents`.`idSubmissionEvents`,\n"
                + "    `submissionEvents`.`submissionBatchId`,\n"
                + "    `submissionEvents`.`submissionQueueId`,\n"
                + "    `submissionEvents`.`eventId`,\n"
                + "    `submissionEvents`.`description`,\n"
                + "    `submissionEvents`.`userId`\n"
                + "FROM `cssbilling`.`submissionEvents` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
