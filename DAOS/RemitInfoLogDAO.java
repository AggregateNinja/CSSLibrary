package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RemitInfoLog;
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


public class RemitInfoLogDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".remitInfoLog";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,RemitInfoLog)">
	public static RemitInfoLog insert(Connection con, RemitInfoLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RemitInfoLogDAO::Insert: Received a NULL RemitInfoLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemitInfoLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  remitInfoId,"
			+ "  action,"
			+ "  actionTime,"
			+ "  user"
			+ ")"
			+ "VALUES (?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemitInfoId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getActionTime()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());

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
				throw new NullPointerException("RemitInfoLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdremitInfoLog(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (RemitInfoLog)">
	public static RemitInfoLog insert(RemitInfoLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RemitInfoLogDAO::Insert: Received a NULL RemitInfoLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, RemitInfoLog)">
	public static void update(Connection con, RemitInfoLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RemitInfoLogDAO::Update: Received a NULL RemitInfoLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemitInfoLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " remitInfoId = ?,"
			+ " action = ?,"
			+ " actionTime = ?,"
			+ " user = ?"
			+ " WHERE idremitInfoLog = " + obj.getIdremitInfoLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRemitInfoId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getActionTime()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUser());
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

	//<editor-fold defaultstate="collapsed" desc="update (RemitInfoLog)">
	public static void update(RemitInfoLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RemitInfoLogDAO::Update: Received a NULL RemitInfoLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, RemitInfoLog, boolean (forUpdate))">
	public static RemitInfoLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RemitInfoLogDAO::get: Received a NULL or empty RemitInfoLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemitInfoLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idremitInfoLog = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		RemitInfoLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (RemitInfoLog)">
	public static RemitInfoLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RemitInfoLogDAO::get: Received a NULL or empty RemitInfoLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static RemitInfoLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		RemitInfoLog obj = new RemitInfoLog();
		obj.setIdremitInfoLog(SQLUtil.getInteger(rs,"idremitInfoLog"));
		obj.setRemitInfoId(SQLUtil.getInteger(rs,"remitInfoId"));
		obj.setAction(rs.getString("action"));
		obj.setActionTime(rs.getTimestamp("actionTime"));
		obj.setUser(SQLUtil.getInteger(rs,"user"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `remitInfoLog`.`idremitInfoLog`,\n"
                + "    `remitInfoLog`.`remitInfoId`,\n"
                + "    `remitInfoLog`.`action`,\n"
                + "    `remitInfoLog`.`actionTime`,\n"
                + "    `remitInfoLog`.`user`\n"
                + "FROM `cssbilling`.`remitInfoLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
        
}
