package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailCptCommentLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;


public class DetailCptCommentLogDAO implements IStructureCheckable
{
	public static final String table = "`detailCptCommentLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailCptCommentLog)">
	public static DetailCptCommentLog insert(Connection con, DetailCptCommentLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO::Insert: Received a NULL DetailCptCommentLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  detailCptCodeId,"
			+ "  detailCptCodeCommentId,"
			+ "  commentId,"
			+ "  action,"
			+ "  comment,"
			+ "  performedBy,"
			+ "  datePerformed"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeCommentId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPerformedBy());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDatePerformed()));

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
				throw new NullPointerException("DetailCptCommentLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIddetailCptCommentLog(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (DetailCptCommentLog)">
	public static DetailCptCommentLog insert(DetailCptCommentLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO::Insert: Received a NULL DetailCptCommentLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, DetailCptCommentLog)">
	public static void update(Connection con, DetailCptCommentLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCommentLogDAO::Update: Received a NULL DetailCptCommentLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " detailCptCodeId = ?,"
			+ " detailCptCodeCommentId = ?,"
			+ " commentId = ?,"
			+ " action = ?,"
			+ " comment = ?,"
			+ " performedBy = ?,"
			+ " datePerformed = ?"
			+ " WHERE iddetailCptCommentLog = " + obj.getIddetailCptCommentLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeCommentId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCommentId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPerformedBy());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDatePerformed()));
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

	//<editor-fold defaultstate="collapsed" desc="update (DetailCptCommentLog)">
	public static void update(DetailCptCommentLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCommentLogDAO::Update: Received a NULL DetailCptCommentLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, DetailCptCommentLog, boolean (forUpdate))">
	public static DetailCptCommentLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO::get: Received a NULL or empty DetailCptCommentLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE iddetailCptCommentLog = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		DetailCptCommentLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (DetailCptCommentLog)">
	public static DetailCptCommentLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCommentLogDAO::get: Received a NULL or empty DetailCptCommentLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static DetailCptCommentLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		DetailCptCommentLog obj = new DetailCptCommentLog();
		obj.setIddetailCptCommentLog(SQLUtil.getInteger(rs,"iddetailCptCommentLog"));
		obj.setDetailCptCodeId(SQLUtil.getInteger(rs,"detailCptCodeId"));
		obj.setDetailCptCodeCommentId(SQLUtil.getInteger(rs,"detailCptCodeCommentId"));
		obj.setCommentId(SQLUtil.getInteger(rs,"commentId"));
		obj.setAction(rs.getString("action"));
		obj.setComment(rs.getString("comment"));
		obj.setPerformedBy(SQLUtil.getInteger(rs,"performedBy"));
		obj.setDatePerformed(rs.getTimestamp("datePerformed"));

		return obj;
	}
	//</editor-fold>

        
    @Override
    public String structureCheck() {
        String query = "SELECT `detailCptCommentLog`.`iddetailCptCommentLog`,\n"
                + "    `detailCptCommentLog`.`detailCptCodeId`,\n"
                + "    `detailCptCommentLog`.`detailCptCodeCommentId`,\n"
                + "    `detailCptCommentLog`.`commentId`,\n"
                + "    `detailCptCommentLog`.`action`,\n"
                + "    `detailCptCommentLog`.`comment`,\n"
                + "    `detailCptCommentLog`.`performedBy`,\n"
                + "    `detailCptCommentLog`.`datePerformed`\n"
                + "FROM `css`.`detailCptCommentLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
