package EMR.DAOS;

import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.ExtraFields;
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


public class ExtraFieldsDAO implements IStructureCheckable
{
	public static final String table = "`extraFields`";
        public static final EMR.Database.EMRDatabaseSingleton dbs = EMR.Database.EMRDatabaseSingleton.getDatabaseSingleton();

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,ExtraFields)">
	public static ExtraFields insert(Connection con, ExtraFields obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ExtraFieldsDAO::Insert: Received a NULL ExtraFields object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ExtraFieldsDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  orderId,"
			+ "  type,"
			+ "  action,"
			+ "  name,"
			+ "  value"
			+ ")"
			+ "VALUES (?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getValue());

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
				throw new NullPointerException("ExtraFieldsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdExtraFields(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (ExtraFields)">
	public static ExtraFields insert(ExtraFields obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("ExtraFieldsDAO::Insert: Received a NULL ExtraFields object");
		}

		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, ExtraFields)">
	public static void update(Connection con, ExtraFields obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ExtraFieldsDAO::Update: Received a NULL ExtraFields object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ExtraFieldsDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " orderId = ?,"
			+ " type = ?,"
			+ " action = ?,"
			+ " name = ?,"
			+ " value = ?"
			+ " WHERE idExtraFields = " + obj.getIdExtraFields();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAction());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getValue());
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

	//<editor-fold defaultstate="collapsed" desc="update (ExtraFields)">
	public static void update(ExtraFields obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("ExtraFieldsDAO::Update: Received a NULL ExtraFields object.");

		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, ExtraFields, boolean (forUpdate))">
	public static ExtraFields get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ExtraFieldsDAO::get: Received a NULL or empty ExtraFields object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("ExtraFieldsDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idExtraFields = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		ExtraFields obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (ExtraFields)">
	public static ExtraFields get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("ExtraFieldsDAO::get: Received a NULL or empty ExtraFields object.");
		}

		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="getByOrderId (orderId)">
        public static ExtraFields getByOrderId (int orderId) throws SQLException
        {
            ExtraFields extraFields = null;
            
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(orderId);
            
            try (PreparedStatement pStmt = con.prepareStatement(sql))
            {
                    ResultSet rs = pStmt.executeQuery();
                    if (rs.next())
                    {
                            extraFields = objectFromResultSet(rs);
                    }
            }
            catch (SQLException ex)
            {
                    System.out.println(ex.getMessage() + " " + sql);
                    throw new SQLException(ex.getMessage() + " " + sql);
            }
            
            return extraFields;
        }
        //</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static ExtraFields objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		ExtraFields obj = new ExtraFields();
		obj.setIdExtraFields(SQLUtil.getInteger(rs,"idExtraFields"));
		obj.setOrderId(SQLUtil.getInteger(rs,"orderId"));
		obj.setType(rs.getString("type"));
		obj.setAction(rs.getString("action"));
		obj.setName(rs.getString("name"));
		obj.setValue(rs.getString("value"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `extraFields`.`idExtraFields`,\n"
                + "    `extraFields`.`orderId`,\n"
                + "    `extraFields`.`type`,\n"
                + "    `extraFields`.`action`,\n"
                + "    `extraFields`.`name`,\n"
                + "    `extraFields`.`value`\n"
                + "FROM `emrorders`.`extraFields` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, dbs.getConnection(true));
    }
        
}
