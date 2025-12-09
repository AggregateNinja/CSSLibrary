package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PreauthRequestCptLookup;
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


public class PreauthRequestCptLookupDAO implements IStructureCheckable
{
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
	public static final String table = "`preauthRequestCptLookup`";

        /**
        * All fields except idPreauthRequestCptLookup
        */
       private final ArrayList<String> fields = new ArrayList<>();
            
        public PreauthRequestCptLookupDAO()
        {
            fields.add("preauthRequestId");
            fields.add("cptCode");
            fields.add("modifier");
        }
        
	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthRequestCptLookup)">
	public static PreauthRequestCptLookup insert(Connection con, PreauthRequestCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO::Insert: Received a NULL PreauthRequestCptLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthRequestId,"
			+ "  cptCode,"
			+ "  modifier"
			+ ")"
			+ "VALUES (?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthRequestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModifier());

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
				throw new NullPointerException("PreauthRequestCptLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthRequestCptLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthRequestCptLookup)">
	public static PreauthRequestCptLookup insert(PreauthRequestCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO::Insert: Received a NULL PreauthRequestCptLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthRequestCptLookup)">
	public static void update(Connection con, PreauthRequestCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestCptLookupDAO::Update: Received a NULL PreauthRequestCptLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthRequestId = ?,"
			+ " cptCode = ?,"
			+ " modifier = ?"
			+ " WHERE idpreauthRequestCptLookup = " + obj.getIdpreauthRequestCptLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthRequestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModifier());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthRequestCptLookup)">
	public static void update(PreauthRequestCptLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestCptLookupDAO::Update: Received a NULL PreauthRequestCptLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthRequestCptLookup, boolean (forUpdate))">
	public static PreauthRequestCptLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO::get: Received a NULL or empty PreauthRequestCptLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthRequestCptLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthRequestCptLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthRequestCptLookup)">
	public static PreauthRequestCptLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestCptLookupDAO::get: Received a NULL or empty PreauthRequestCptLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthRequestCptLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthRequestCptLookup obj = new PreauthRequestCptLookup();
		obj.setIdpreauthRequestCptLookup(SQLUtil.getInteger(rs,"idpreauthRequestCptLookup"));
		obj.setPreauthRequestId(SQLUtil.getInteger(rs,"preauthRequestId"));
		obj.setCptCode(rs.getString("cptCode"));
		obj.setModifier(rs.getString("modifier"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
