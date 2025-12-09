package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Charts;
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


public class ChartsDAO implements IStructureCheckable
{
	public static final String table = "`charts`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,Charts)">
	public static Charts insert(Connection con, Charts obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartsDAO::Insert: Received a NULL Charts object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartsDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  chartTypeId,"
			+ "  chartName,"
			+ "  chartDescription,"
			+ "  active,"
			+ "  dateCreated"
			+ ")"
			+ "VALUES (?,?,?,?,NOW())";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChartTypeId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartDescription());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());

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
				throw new NullPointerException("ChartsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdCharts(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (Charts)">
	public static Charts insert(Charts obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartsDAO::Insert: Received a NULL Charts object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, Charts)">
	public static void update(Connection con, Charts obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartsDAO::Update: Received a NULL Charts object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartsDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " chartTypeId = ?,"
			+ " chartName = ?,"
			+ " chartDescription = ?,"
			+ " active = ?,"
			+ " WHERE idCharts = " + obj.getIdCharts();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChartTypeId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartDescription());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isActive());
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

	//<editor-fold defaultstate="collapsed" desc="update (Charts)">
	public static void update(Charts obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartsDAO::Update: Received a NULL Charts object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, Charts, boolean (forUpdate))">
	public static Charts get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartsDAO::get: Received a NULL or empty Charts object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartsDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idCharts = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		Charts obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (Charts)">
	public static Charts get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartsDAO::get: Received a NULL or empty Charts object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static Charts objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		Charts obj = new Charts();
		obj.setIdCharts(SQLUtil.getInteger(rs,"idCharts"));
		obj.setChartTypeId(SQLUtil.getInteger(rs,"chartTypeId"));
		obj.setChartName(rs.getString("chartName"));
		obj.setChartDescription(rs.getString("chartDescription"));
		obj.setActive(rs.getBoolean("active"));

		return obj;
	}
	//</editor-fold>

        
    @Override
    public String structureCheck() {
        String query = "SELECT `charts`.`idCharts`,\n"
                + "    `charts`.`chartTypeId`,\n"
                + "    `charts`.`chartName`,\n"
                + "    `charts`.`chartDescription`,\n"
                + "    `charts`.`displayOrder`,\n"
                + "    `charts`.`active`,\n"
                + "    `charts`.`dateCreated`\n"
                + "FROM `css`.`charts` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
