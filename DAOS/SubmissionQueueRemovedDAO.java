package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubmissionQueueRemoved;
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
import java.util.Date;


public class SubmissionQueueRemovedDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`submissionQueueRemoved`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,SubmissionQueueRemoved)">
	public static SubmissionQueueRemoved insert(Connection con, SubmissionQueueRemoved obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO::Insert: Received a NULL SubmissionQueueRemoved object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  idSubmissionQueue,"
			+ "  submissionBatchId,"
			+ "  detailOrderId,"
			+ "  detailInsuranceId,"
			+ "  billAmount,"
			+ "  submitted,"
			+ "  submittedDate,"
			+ "  rejected,"
			+ "  user,"
			+ "  removedDate"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdSubmissionQueue());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBillAmount());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getSubmitted());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getRejected());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getRemovedDate()));

			Integer newId = null;
			sql = pStmt.toString();
			pStmt.executeUpdate();
			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next())
			{
				newId = rs.getInt(1);
			}
			if (newId == null || newId <= 0)
			{
				throw new NullPointerException("SubmissionQueueRemovedDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdSubmissionQueueRemoved(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (SubmissionQueueRemoved)">
	public static SubmissionQueueRemoved insert(SubmissionQueueRemoved obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO::Insert: Received a NULL SubmissionQueueRemoved object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, SubmissionQueueRemoved)">
	public static void update(Connection con, SubmissionQueueRemoved obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SubmissionQueueRemovedDAO::Update: Received a NULL SubmissionQueueRemoved object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " idSubmissionQueue = ?,"
			+ " submissionBatchId = ?,"
			+ " detailOrderId = ?,"
			+ " detailInsuranceId = ?,"
			+ " billAmount = ?,"
			+ " submitted = ?,"
			+ " submittedDate = ?,"
			+ " rejected = ?,"
			+ " user = ?,"
			+ " removedDate = ?"
			+ " WHERE idSubmissionQueueRemoved = " + obj.getIdSubmissionQueueRemoved();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdSubmissionQueue());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSubmissionBatchId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailInsuranceId());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getBillAmount());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getSubmitted());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getRejected());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getRemovedDate()));
			sqlOutput = pStmt.toString();
			pStmt.executeUpdate();
		}
		catch (Exception ex)
		{
			String errorMsg = ex.getMessage() + " " + sqlOutput;
			System.out.println(errorMsg);
			throw new SQLException(errorMsg);
		}
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (SubmissionQueueRemoved)">
	public static void update(SubmissionQueueRemoved obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SubmissionQueueRemovedDAO::Update: Received a NULL SubmissionQueueRemoved object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, SubmissionQueueRemoved, boolean (forUpdate))">
	public static SubmissionQueueRemoved get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO::get: Received a NULL or empty SubmissionQueueRemoved object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idSubmissionQueueRemoved = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		SubmissionQueueRemoved obj = null;

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{

			ResultSet rs = pStmt.executeQuery();
			if (rs.next())
			{
				obj = objectFromResultSet(rs);
			}
		}
		catch (SQLException ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw new SQLException(ex.getMessage() + " " + sql);
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (SubmissionQueueRemoved)">
	public static SubmissionQueueRemoved get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SubmissionQueueRemovedDAO::get: Received a NULL or empty SubmissionQueueRemoved object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static SubmissionQueueRemoved objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		SubmissionQueueRemoved obj = new SubmissionQueueRemoved();
		obj.setIdSubmissionQueueRemoved(SQLUtil.getInteger(rs,"idSubmissionQueueRemoved"));
		obj.setIdSubmissionQueue(SQLUtil.getInteger(rs,"idSubmissionQueue"));
		obj.setSubmissionBatchId(SQLUtil.getInteger(rs,"submissionBatchId"));
		obj.setDetailOrderId(SQLUtil.getInteger(rs,"detailOrderId"));
		obj.setDetailInsuranceId(SQLUtil.getInteger(rs,"detailInsuranceId"));
		obj.setBillAmount(rs.getBigDecimal("billAmount"));
		obj.setSubmitted(rs.getBoolean("submitted"));
		obj.setSubmittedDate(rs.getDate("submittedDate"));
		obj.setRejected(rs.getBoolean("rejected"));
		obj.setUser(SQLUtil.getInteger(rs,"user"));
		obj.setRemovedDate(rs.getDate("removedDate"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `submissionQueueRemoved`.`idSubmissionQueueRemoved`,\n"
                + "    `submissionQueueRemoved`.`idSubmissionQueue`,\n"
                + "    `submissionQueueRemoved`.`submissionBatchId`,\n"
                + "    `submissionQueueRemoved`.`detailOrderId`,\n"
                + "    `submissionQueueRemoved`.`detailInsuranceId`,\n"
                + "    `submissionQueueRemoved`.`billAmount`,\n"
                + "    `submissionQueueRemoved`.`submitted`,\n"
                + "    `submissionQueueRemoved`.`submittedDate`,\n"
                + "    `submissionQueueRemoved`.`rejected`,\n"
                + "    `submissionQueueRemoved`.`user`,\n"
                + "    `submissionQueueRemoved`.`removedDate`\n"
                + "FROM `cssbilling`.`submissionQueueRemoved` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
