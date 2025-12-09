package DAOS;

import DOS.PreauthResponseInsuranceInfoLookup;
import DAOS.IDAOS.IStructureCheckable;
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
import java.util.List;


public class PreauthResponseInsuranceInfoLookupDAO implements IStructureCheckable
{
	public static final String table = "`preauthResponseInsuranceInfoLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthResponseInsuranceInfoLookup)">
	public static PreauthResponseInsuranceInfoLookup insert(Connection con, PreauthResponseInsuranceInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::Insert: Received a NULL PreauthResponseInsuranceInfoLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthResponseId,"
			+ "  payerType,"
			+ "  payerName,"
			+ "  insuranceId,"
			+ "  memberId"
			+ ")"
			+ "VALUES (?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPayerType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPayerName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());

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
				throw new NullPointerException("PreauthResponseInsuranceInfoLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthResponseInsuranceInfoLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthResponseInsuranceInfoLookup)">
	public static PreauthResponseInsuranceInfoLookup insert(PreauthResponseInsuranceInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::Insert: Received a NULL PreauthResponseInsuranceInfoLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthResponseInsuranceInfoLookup)">
	public static void update(Connection con, PreauthResponseInsuranceInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::Update: Received a NULL PreauthResponseInsuranceInfoLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthResponseId = ?,"
			+ " payerType = ?,"
			+ " payerName = ?,"
			+ " insuranceId = ?,"
			+ " memberId = ?"
			+ " WHERE idpreauthResponseInsuranceInfoLookup = " + obj.getIdpreauthResponseInsuranceInfoLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPayerType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPayerName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthResponseInsuranceInfoLookup)">
	public static void update(PreauthResponseInsuranceInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::Update: Received a NULL PreauthResponseInsuranceInfoLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthResponseInsuranceInfoLookup, boolean (forUpdate))">
	public static PreauthResponseInsuranceInfoLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::get: Received a NULL or empty PreauthResponseInsuranceInfoLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthResponseInsuranceInfoLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthResponseInsuranceInfoLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthResponseInsuranceInfoLookup)">
	public static PreauthResponseInsuranceInfoLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseInsuranceInfoLookupDAO::get: Received a NULL or empty PreauthResponseInsuranceInfoLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static List<PreauthResponseInsuranceInfoLookup> getInsuranceLookupsByResponseId(int preauthResponseId) throws SQLException, NullPointerException
        {
            ArrayList<PreauthResponseInsuranceInfoLookup> insurances = new ArrayList<>();
            
            if (preauthResponseId <= 0)
                throw new NullPointerException("PreauthResponseInsuranceLookupDAO::getInsuranceLookups received invalid preauthResponseId.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `preauthResponseId` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, preauthResponseId);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                insurances.add(objectFromResultSet(rs));
            }
            
            return insurances;
        }

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthResponseInsuranceInfoLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthResponseInsuranceInfoLookup obj = new PreauthResponseInsuranceInfoLookup();
		obj.setIdpreauthResponseInsuranceInfoLookup(SQLUtil.getInteger(rs,"idpreauthResponseInsuranceInfoLookup"));
		obj.setPreauthResponseId(SQLUtil.getInteger(rs,"preauthResponseId"));
		obj.setPayerType(rs.getString("payerType"));
		obj.setPayerName(rs.getString("payerName"));
		obj.setInsuranceId(rs.getString("insuranceId"));
		obj.setMemberId(rs.getString("memberId"));

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="structureCheck()">
	@Override
	public String structureCheck() {
		String query = "SELECT `preauthResponseInsuranceInfoLookup`.`idpreauthResponseInsuranceInfoLookup`,"
			+"	`preauthResponseInsuranceInfoLookup`.`preauthResponseId`,"
			+"	`preauthResponseInsuranceInfoLookup`.`payerType`,"
			+"	`preauthResponseInsuranceInfoLookup`.`payerName`,"
			+"	`preauthResponseInsuranceInfoLookup`.`insuranceId`,"
			+"	`preauthResponseInsuranceInfoLookup`.`memberId`"
			+ "FROM `css`.`preauthResponseInsuranceInfoLookup` LIMIT 1";
		return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
	}
	//</editor-fold>

}
