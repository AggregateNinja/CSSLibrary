package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SpecialPriceTestLog;
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


public class SpecialPriceTestLogDAO implements IStructureCheckable
{
	public static final String table = "`specialPriceTestLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,SpecialPriceTestLog)">
	public static SpecialPriceTestLog insert(Connection con, SpecialPriceTestLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO::Insert: Received a NULL SpecialPriceTestLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
                        + "  specialPriceTestId,"
			+ "  action,"
			+ "  clientId,"
			+ "  testNumber,"
			+ "  cost,"
			+ "  userId,"
			+ "  date"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i = 0;
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSpecialPriceTestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getCost());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDate()));

			sql = pStmt.toString();
			pStmt.executeUpdate();
                }
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (SpecialPriceTestLog)">
	public static SpecialPriceTestLog insert(SpecialPriceTestLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO::Insert: Received a NULL SpecialPriceTestLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, SpecialPriceTestLog)">
	public static void update(Connection con, SpecialPriceTestLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SpecialPriceTestLogDAO::Update: Received a NULL SpecialPriceTestLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " idspecialPriceTestLog = ?,"
			+ " specialPriceTestId = ?,"
			+ " action = ?,"
			+ " clientId = ?,"
			+ " testNumber = ?,"
			+ " cost = ?,"
			+ " userId = ?,"
			+ " date = ?"
			+ " WHERE  = " + obj.getIdspecialPriceTestLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdspecialPriceTestLog());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getSpecialPriceTestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getClientId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getCost());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDate()));
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

	//<editor-fold defaultstate="collapsed" desc="update (SpecialPriceTestLog)">
	public static void update(SpecialPriceTestLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("SpecialPriceTestLogDAO::Update: Received a NULL SpecialPriceTestLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, SpecialPriceTestLog, boolean (forUpdate))">
	public static SpecialPriceTestLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO::get: Received a NULL or empty SpecialPriceTestLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE  = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		SpecialPriceTestLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (SpecialPriceTestLog)">
	public static SpecialPriceTestLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("SpecialPriceTestLogDAO::get: Received a NULL or empty SpecialPriceTestLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static SpecialPriceTestLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		SpecialPriceTestLog obj = new SpecialPriceTestLog();
		obj.setIdspecialPriceTestLog(SQLUtil.getInteger(rs,"idspecialPriceTestLog"));
		obj.setSpecialPriceTestId(SQLUtil.getInteger(rs,"specialPriceTestId"));
		obj.setAction(rs.getString("action"));
		obj.setClientId(SQLUtil.getInteger(rs,"clientId"));
		obj.setTestNumber(SQLUtil.getInteger(rs,"testNumber"));
		obj.setCost(rs.getBigDecimal("cost"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
		obj.setDate(rs.getDate("date"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `specialPriceTestLog`.`idspecialPriceTestLog`,\n"
                + "    `specialPriceTestLog`.`specialPriceTestId`,\n"
                + "    `specialPriceTestLog`.`action`,\n"
                + "    `specialPriceTestLog`.`clientId`,\n"
                + "    `specialPriceTestLog`.`testNumber`,\n"
                + "    `specialPriceTestLog`.`cost`,\n"
                + "    `specialPriceTestLog`.`userId`,\n"
                + "    `specialPriceTestLog`.`date`\n"
                + "FROM `css`.`specialPriceTestLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
