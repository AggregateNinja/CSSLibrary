package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ReflexMultichoiceLog;
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


public class ReflexMultichoiceLogDAO implements IStructureCheckable
{
	public static final String table = "`reflexMultichoiceLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ReflexMultichoiceLog)">
	public static ReflexMultichoiceLog insert(Connection con, ReflexMultichoiceLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO::Insert: Received a NULL ReflexMultichoiceLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  testId,"
			+ "  reflexTestId,"
			+ "  resultPostLogId,"
			+ "  reason,"
			+ "  userId,"
			+ "  reflexDate"
			+ ")"
			+ "VALUES (?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexTestId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultPostLogId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getReason());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getReflexDate()));

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
				throw new NullPointerException("ReflexMultichoiceLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdReflexMultichoiceLog(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ReflexMultichoiceLog)">
	public static ReflexMultichoiceLog insert(ReflexMultichoiceLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO::Insert: Received a NULL ReflexMultichoiceLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="update (Connection, ReflexMultichoiceLog)">
	public static void update(Connection con, ReflexMultichoiceLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ReflexMultichoiceLogDAO::Update: Received a NULL ReflexMultichoiceLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " testId = ?,"
			+ " reflexTestId = ?,"
			+ " resultPostLogId = ?,"
			+ " reason = ?,"
			+ " userId = ?,"
			+ " reflexDate = ?"
			+ " WHERE idReflexMultichoiceLog = " + obj.getIdReflexMultichoiceLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getReflexTestId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultPostLogId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getReason());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getReflexDate()));
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

	//<editor-fold defaultstate="collapsed" desc="update (ReflexMultichoiceLog)">
	public static void update(ReflexMultichoiceLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ReflexMultichoiceLogDAO::Update: Received a NULL ReflexMultichoiceLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ReflexMultichoiceLog, boolean (forUpdate))">
	public static ReflexMultichoiceLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO::get: Received a NULL or empty ReflexMultichoiceLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idReflexMultichoiceLog = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ReflexMultichoiceLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ReflexMultichoiceLog)">
	public static ReflexMultichoiceLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ReflexMultichoiceLogDAO::get: Received a NULL or empty ReflexMultichoiceLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ReflexMultichoiceLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ReflexMultichoiceLog obj = new ReflexMultichoiceLog();
		obj.setIdReflexMultichoiceLog(SQLUtil.getInteger(rs,"idReflexMultichoiceLog"));
		obj.setTestId(SQLUtil.getInteger(rs,"testId"));
		obj.setReflexTestId(SQLUtil.getInteger(rs,"reflexTestId"));
		obj.setResultPostLogId(SQLUtil.getInteger(rs,"resultPostLogId"));
		obj.setReason(rs.getString("reason"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
		obj.setReflexDate(rs.getDate("reflexDate"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `reflexMultichoiceLog`.`idReflexMultichoiceLog`,\n"
                + "    `reflexMultichoiceLog`.`testId`,\n"
                + "    `reflexMultichoiceLog`.`reflexTestId`,\n"
                + "    `reflexMultichoiceLog`.`resultPostLogId`,\n"
                + "    `reflexMultichoiceLog`.`reason`,\n"
                + "    `reflexMultichoiceLog`.`userId`,\n"
                + "    `reflexMultichoiceLog`.`reflexDate`\n"
                + "FROM `css`.`reflexMultichoiceLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
        
        
}
