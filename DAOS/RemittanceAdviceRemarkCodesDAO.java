package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RemittanceAdviceRemarkCodes;
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


public class RemittanceAdviceRemarkCodesDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`remittanceAdviceRemarkCodes`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,RemittanceAdviceRemarkCodes)">
	public static RemittanceAdviceRemarkCodes insert(Connection con, RemittanceAdviceRemarkCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::Insert: Received a NULL RemittanceAdviceRemarkCodes object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  remarkCode,"
			+ "  insuranceId,"
			+ "  description"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getRemarkCode());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());

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
				throw new NullPointerException("RemittanceAdviceRemarkCodesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdRemittanceAdviceRemarkCodes(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (RemittanceAdviceRemarkCodes)">
	public static RemittanceAdviceRemarkCodes insert(RemittanceAdviceRemarkCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::Insert: Received a NULL RemittanceAdviceRemarkCodes object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, RemittanceAdviceRemarkCodes)">
	public static void update(Connection con, RemittanceAdviceRemarkCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::Update: Received a NULL RemittanceAdviceRemarkCodes object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " remarkCode = ?,"
			+ " insuranceId = ?,"
			+ " description = ?"
			+ " WHERE idRemittanceAdviceRemarkCodes = " + obj.getIdRemittanceAdviceRemarkCodes();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getRemarkCode());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
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

	//<editor-fold defaultstate="collapsed" desc="update (RemittanceAdviceRemarkCodes)">
	public static void update(RemittanceAdviceRemarkCodes obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::Update: Received a NULL RemittanceAdviceRemarkCodes object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, RemittanceAdviceRemarkCodes, boolean (forUpdate))">
	public static RemittanceAdviceRemarkCodes get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::get: Received a NULL or empty RemittanceAdviceRemarkCodes object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idRemittanceAdviceRemarkCodes = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		RemittanceAdviceRemarkCodes obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (RemittanceAdviceRemarkCodes)">
	public static RemittanceAdviceRemarkCodes get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RemittanceAdviceRemarkCodesDAO::get: Received a NULL or empty RemittanceAdviceRemarkCodes object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static RemittanceAdviceRemarkCodes objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		RemittanceAdviceRemarkCodes obj = new RemittanceAdviceRemarkCodes();
		obj.setIdRemittanceAdviceRemarkCodes(SQLUtil.getInteger(rs,"idRemittanceAdviceRemarkCodes"));
		obj.setRemarkCode(rs.getString("remarkCode"));
		obj.setInsuranceId(SQLUtil.getInteger(rs,"insuranceId"));
		obj.setDescription(rs.getString("description"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `remittanceAdviceRemarkCodes`.`idRemittanceAdviceRemarkCodes`,\n"
                + "    `remittanceAdviceRemarkCodes`.`remarkCode`,\n"
                + "    `remittanceAdviceRemarkCodes`.`insuranceId`,\n"
                + "    `remittanceAdviceRemarkCodes`.`description`\n"
                + "FROM `cssbilling`.`remittanceAdviceRemarkCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
        
}
