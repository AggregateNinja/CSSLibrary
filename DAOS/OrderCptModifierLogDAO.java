package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderCptModifierLog;
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


public class OrderCptModifierLogDAO implements IStructureCheckable
{
	public static final String table = "`orderCptModifierLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,OrderCptModifierLog)">
	public static OrderCptModifierLog insert(Connection con, OrderCptModifierLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO::Insert: Received a NULL OrderCptModifierLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  orderCptCodeId,"
			+ "  action,"
			+ "  field,"
			+ "  preValue,"
			+ "  postValue,"
			+ "  description,"
			+ "  performedByUserId,"
			+ "  date,"
			+ "  isUserVisible"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,NOW(),?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCptCodeId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getField());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPreValue());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPostValue());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPerformedByUserId());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getIsUserVisible());

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
				throw new NullPointerException("OrderCptModifierLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setId(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (OrderCptModifierLog)">
	public static OrderCptModifierLog insert(OrderCptModifierLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO::Insert: Received a NULL OrderCptModifierLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, OrderCptModifierLog)">
	public static void update(Connection con, OrderCptModifierLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("OrderCptModifierLogDAO::Update: Received a NULL OrderCptModifierLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " orderCptCodeId = ?,"
			+ " action = ?,"
			+ " field = ?,"
			+ " preValue = ?,"
			+ " postValue = ?,"
			+ " description = ?,"
			+ " performedByUserId = ?,"
			+ " isUserVisible = ?"
			+ " WHERE id = " + obj.getId();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCptCodeId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getField());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPreValue());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPostValue());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPerformedByUserId());
			SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getIsUserVisible());
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

	//<editor-fold defaultstate="collapsed" desc="update (OrderCptModifierLog)">
	public static void update(OrderCptModifierLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("OrderCptModifierLogDAO::Update: Received a NULL OrderCptModifierLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, OrderCptModifierLog, boolean (forUpdate))">
	public static OrderCptModifierLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO::get: Received a NULL or empty OrderCptModifierLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE id = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		OrderCptModifierLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (OrderCptModifierLog)">
	public static OrderCptModifierLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("OrderCptModifierLogDAO::get: Received a NULL or empty OrderCptModifierLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static OrderCptModifierLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		OrderCptModifierLog obj = new OrderCptModifierLog();
		obj.setId(SQLUtil.getInteger(rs,"id"));
		obj.setOrderCptCodeId(SQLUtil.getInteger(rs,"orderCptCodeId"));
		obj.setAction(rs.getString("action"));
		obj.setField(rs.getString("field"));
		obj.setPreValue(rs.getString("preValue"));
		obj.setPostValue(rs.getString("postValue"));
		obj.setDescription(rs.getString("description"));
		obj.setPerformedByUserId(SQLUtil.getInteger(rs,"performedByUserId"));
		obj.setIsUserVisible(rs.getBoolean("isUserVisible"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `orderCptModifierLog`.`id`,\n"
                + "    `orderCptModifierLog`.`orderCptCodeId`,\n"
                + "    `orderCptModifierLog`.`action`,\n"
                + "    `orderCptModifierLog`.`field`,\n"
                + "    `orderCptModifierLog`.`preValue`,\n"
                + "    `orderCptModifierLog`.`postValue`,\n"
                + "    `orderCptModifierLog`.`description`,\n"
                + "    `orderCptModifierLog`.`performedByUserId`,\n"
                + "    `orderCptModifierLog`.`date`,\n"
                + "    `orderCptModifierLog`.`isUserVisible`\n"
                + "FROM `css`.`orderCptModifierLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
