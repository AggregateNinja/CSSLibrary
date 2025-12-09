package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PreauthRequestDiagnosisLookup;
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


public class PreauthRequestDiagnosisLookupDAO implements IStructureCheckable
{
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
	public static final String table = "`preauthRequestDiagnosisLookup`";

        /**
        * All fields except idPreauthRequestCptLookup
        */
       private final ArrayList<String> fields = new ArrayList<>();
            
        public PreauthRequestDiagnosisLookupDAO()
        {
            fields.add("preauthRequestId");
            fields.add("diagnosisCode");
        }
        
	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthRequestDiagnosisLookup)">
	public static PreauthRequestDiagnosisLookup insert(Connection con, PreauthRequestDiagnosisLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::Insert: Received a NULL PreauthRequestDiagnosisLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthRequestId,"
			+ "  diagnosisCode"
			+ ")"
			+ "VALUES (?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthRequestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDiagnosisCode());

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
				throw new NullPointerException("PreauthRequestDiagnosisLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthRequestDiagnosisLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthRequestDiagnosisLookup)">
	public static PreauthRequestDiagnosisLookup insert(PreauthRequestDiagnosisLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::Insert: Received a NULL PreauthRequestDiagnosisLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthRequestDiagnosisLookup)">
	public static void update(Connection con, PreauthRequestDiagnosisLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::Update: Received a NULL PreauthRequestDiagnosisLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthRequestId = ?,"
			+ " diagnosisCode = ?"
			+ " WHERE idpreauthRequestDiagnosisLookup = " + obj.getIdpreauthRequestDiagnosisLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthRequestId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDiagnosisCode());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthRequestDiagnosisLookup)">
	public static void update(PreauthRequestDiagnosisLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::Update: Received a NULL PreauthRequestDiagnosisLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthRequestDiagnosisLookup, boolean (forUpdate))">
	public static PreauthRequestDiagnosisLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::get: Received a NULL or empty PreauthRequestDiagnosisLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthRequestDiagnosisLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthRequestDiagnosisLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthRequestDiagnosisLookup)">
	public static PreauthRequestDiagnosisLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestDiagnosisLookupDAO::get: Received a NULL or empty PreauthRequestDiagnosisLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthRequestDiagnosisLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthRequestDiagnosisLookup obj = new PreauthRequestDiagnosisLookup();
		obj.setIdpreauthRequestDiagnosisLookup(SQLUtil.getInteger(rs,"idpreauthRequestDiagnosisLookup"));
		obj.setPreauthRequestId(SQLUtil.getInteger(rs,"preauthRequestId"));
		obj.setDiagnosisCode(rs.getString("diagnosisCode"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
