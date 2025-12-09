package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ChartUserLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


public class ChartUserLookupDAO implements IStructureCheckable
{
	public static final String table = "`chartUserLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ChartUserLookup)">
	public static ChartUserLookup insert(Connection con, ChartUserLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartUserLookupDAO::Insert: Received a NULL ChartUserLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  userId,"
			+ "  chartId,"
			+ "  active,"
			+ "  dateCreated"
			+ ")"
			+ "VALUES (?,?,?,NOW())";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChartId());
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
				throw new NullPointerException("ChartUserLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdUserCharts(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ChartUserLookup)">
	public static ChartUserLookup insert(ChartUserLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ChartUserLookupDAO::Insert: Received a NULL ChartUserLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, ChartUserLookup)">
	public static void update(Connection con, ChartUserLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartUserLookupDAO::Update: Received a NULL ChartUserLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " userId = ?,"
			+ " chartId = ?,"
			+ " active = ?,"
			+ " WHERE idUserCharts = " + obj.getIdUserCharts();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getChartId());
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

	//<editor-fold defaultstate="collapsed" desc="update (ChartUserLookup)">
	public static void update(ChartUserLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ChartUserLookupDAO::Update: Received a NULL ChartUserLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ChartUserLookup, boolean (forUpdate))">
	public static ChartUserLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartUserLookupDAO::get: Received a NULL or empty ChartUserLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idUserCharts = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ChartUserLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ChartUserLookup)">
	public static ChartUserLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ChartUserLookupDAO::get: Received a NULL or empty ChartUserLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="getAllActiveForUser (userId)">
        public static ArrayList<ChartUserLookup> getAllActiveForUser (int userId)
        {
            try
            {
                ArrayList<ChartUserLookup> activeChartUsers = new ArrayList<>();
                
                Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
                
                if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:get: Received a [NULL] or invalid Connection object");
		}
                
                String sql = "SELECT * FROM " + table + " WHERE userId = " + String.valueOf(userId)
                        + " AND active = '1'";
                
                PreparedStatement pStmt = con.prepareStatement(sql);
                
                ResultSet rs = pStmt.executeQuery();
                
                while (rs.next())
                {
                    activeChartUsers.add(objectFromResultSet(rs));
                }
                
                return activeChartUsers;
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
                return null;
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="delete (userId, chartId)">
        public static boolean delete(int userId, int chartId)
        {
            try
            {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
                
                if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:get: Received a [NULL] or invalid Connection object");
		}
                
                String sql = "DELETE FROM " + table + " WHERE userId = '" + String.valueOf(userId)
                        + "' AND chartId = '" + String.valueOf(chartId) + "'";
                
                PreparedStatement pStmt = con.prepareStatement(sql);
                pStmt.execute();
                
                return true;
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
                return false;
            }
        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="exists(userId, chartId)">
        public static boolean exists(int userId, int chartId)
        {
            try
            {
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
                
                if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ChartUserLookupDAO:get: Received a [NULL] or invalid Connection object");
		}
                
                String sql = "SELECT * FROM " + table + " WHERE userId = '" + String.valueOf(userId)
                        + "' AND chartId = '" + String.valueOf(chartId) + "'";
                
                PreparedStatement pStmt = con.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery();
                
                return rs.next();
            }
            catch (SQLException sex)
            {
                System.out.println(sex.getMessage());
                return false;
            }
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ChartUserLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ChartUserLookup obj = new ChartUserLookup();
		obj.setIdUserCharts(SQLUtil.getInteger(rs,"idUserCharts"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
		obj.setChartId(SQLUtil.getInteger(rs,"chartId"));
		obj.setActive(rs.getBoolean("active"));

		return obj;
	}
	//</editor-fold>

        
    @Override
    public String structureCheck() {
        String query = "SELECT `chartUserLookup`.`idUserCharts`,\n"
                + "    `chartUserLookup`.`userId`,\n"
                + "    `chartUserLookup`.`chartId`,\n"
                + "    `chartUserLookup`.`active`,\n"
                + "    `chartUserLookup`.`dateCreated`\n"
                + "FROM `css`.`chartUserLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
