package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ChartTypes;
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


public class ChartTypesDAO implements IStructureCheckable
{
	public static final String table = "`chartTypes`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ChartTypes)">
	public static ChartTypes insert(Connection con, ChartTypes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartTypesDAO::Insert: Received a NULL ChartTypes object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartTypesDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  chartTypeName,"
			+ "  chartTypeDescription,"
			+ "  active"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartTypeName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartTypeDescription());
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
				throw new NullPointerException("ChartTypesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdChartTypes(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ChartTypes)">
	public static ChartTypes insert(ChartTypes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartTypesDAO::Insert: Received a NULL ChartTypes object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, ChartTypes)">
	public static void update(Connection con, ChartTypes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartTypesDAO::Update: Received a NULL ChartTypes object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartTypesDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " chartTypeName = ?,"
			+ " chartTypeDescription = ?,"
			+ " active = ?"
			+ " WHERE idChartTypes = " + obj.getIdChartTypes();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartTypeName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getChartTypeDescription());
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

	//<editor-fold defaultstate="collapsed" desc="update (ChartTypes)">
	public static void update(ChartTypes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartTypesDAO::Update: Received a NULL ChartTypes object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ChartTypes, boolean (forUpdate))">
	public static ChartTypes get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartTypesDAO::get: Received a NULL or empty ChartTypes object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartTypesDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idChartTypes = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ChartTypes obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ChartTypes)">
	public static ChartTypes get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartTypesDAO::get: Received a NULL or empty ChartTypes object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ChartTypes objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ChartTypes obj = new ChartTypes();
		obj.setIdChartTypes(SQLUtil.getInteger(rs,"idChartTypes"));
		obj.setChartTypeName(rs.getString("chartTypeName"));
		obj.setChartTypeDescription(rs.getString("chartTypeDescription"));
		obj.setActive(rs.getBoolean("active"));

		return obj;
	}
	//</editor-fold>

        
    @Override
    public String structureCheck() {
        String query = "SELECT `chartTypes`.`idChartTypes`,\n"
                + "    `chartTypes`.`chartTypeName`,\n"
                + "    `chartTypes`.`chartTypeDescription`,\n"
                + "    `chartTypes`.`active`\n"
                + "FROM `css`.`chartTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
