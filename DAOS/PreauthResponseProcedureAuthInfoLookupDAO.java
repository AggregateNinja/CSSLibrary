package DAOS;

import DOS.PreauthResponseProcedureAuthInfoLookup;
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


public class PreauthResponseProcedureAuthInfoLookupDAO implements IStructureCheckable
{
	public static final String table = "`preauthResponseProcedureAuthInfoLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthResponseProcedureAuthInfoLookup)">
	public static PreauthResponseProcedureAuthInfoLookup insert(Connection con, PreauthResponseProcedureAuthInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::Insert: Received a NULL PreauthResponseProcedureAuthInfoLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthResponseId,"
			+ "  expiryDate,"
			+ "  unitsAuthorized,"
			+ "  denialReason,"
			+ "  authNumber,"
			+ "  insuranceId,"
			+ "  id,"
			+ "  trackingNumber,"
			+ "  cptCode,"
			+ "  effectiveDate,"
			+ "  status"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getExpiryDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getUnitsAuthorized());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDenialReason());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAuthNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getTrackingNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getEffectiveDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getStatus());

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
				throw new NullPointerException("PreauthResponseProcedureAuthInfoLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthResponseProcedureAuthInfoLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthResponseProcedureAuthInfoLookup)">
	public static PreauthResponseProcedureAuthInfoLookup insert(PreauthResponseProcedureAuthInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::Insert: Received a NULL PreauthResponseProcedureAuthInfoLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthResponseProcedureAuthInfoLookup)">
	public static void update(Connection con, PreauthResponseProcedureAuthInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::Update: Received a NULL PreauthResponseProcedureAuthInfoLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthResponseId = ?,"
			+ " expiryDate = ?,"
			+ " unitsAuthorized = ?,"
			+ " denialReason = ?,"
			+ " authNumber = ?,"
			+ " insuranceId = ?,"
			+ " id = ?,"
			+ " trackingNumber = ?,"
			+ " cptCode = ?,"
			+ " effectiveDate = ?,"
			+ " status = ?"
			+ " WHERE idpreauthResponseProcedureAuthInfoLookup = " + obj.getIdpreauthResponseProcedureAuthInfoLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getExpiryDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getUnitsAuthorized());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDenialReason());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAuthNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getTrackingNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getEffectiveDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getStatus());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthResponseProcedureAuthInfoLookup)">
	public static void update(PreauthResponseProcedureAuthInfoLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::Update: Received a NULL PreauthResponseProcedureAuthInfoLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthResponseProcedureAuthInfoLookup, boolean (forUpdate))">
	public static PreauthResponseProcedureAuthInfoLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::get: Received a NULL or empty PreauthResponseProcedureAuthInfoLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthResponseProcedureAuthInfoLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthResponseProcedureAuthInfoLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthResponseProcedureAuthInfoLookup)">
	public static PreauthResponseProcedureAuthInfoLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureAuthInfoLookupDAO::get: Received a NULL or empty PreauthResponseProcedureAuthInfoLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static List<PreauthResponseProcedureAuthInfoLookup> getProcedureAuthInfoLookupsByResponseId(int preauthResponseId) throws SQLException, NullPointerException
        {
            ArrayList<PreauthResponseProcedureAuthInfoLookup> authInfos = new ArrayList<>();
            
            if (preauthResponseId <= 0)
                throw new NullPointerException("PreauthResponseProcedureAuthInfoLookup::getProcedureAuthInfoLookupsByResponseId received invalid preauthResponseId.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `preauthResponseId` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, preauthResponseId);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                authInfos.add(objectFromResultSet(rs));
            }
            
            return authInfos;
        }

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthResponseProcedureAuthInfoLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthResponseProcedureAuthInfoLookup obj = new PreauthResponseProcedureAuthInfoLookup();
		obj.setIdpreauthResponseProcedureAuthInfoLookup(SQLUtil.getInteger(rs,"idpreauthResponseProcedureAuthInfoLookup"));
		obj.setPreauthResponseId(SQLUtil.getInteger(rs,"preauthResponseId"));
                obj.setExpiryDate(rs.getString("expiryDate"));
		obj.setUnitsAuthorized(rs.getString("unitsAuthorized"));
		obj.setDenialReason(rs.getString("denialReason"));
		obj.setAuthNumber(rs.getString("authNumber"));
		obj.setInsuranceId(rs.getString("insuranceId"));
		obj.setId(rs.getString("id"));
		obj.setTrackingNumber(rs.getString("trackingNumber"));
		obj.setCptCode(rs.getString("cptCode"));
                obj.setEffectiveDate(rs.getString("effectiveDate"));
		obj.setStatus(rs.getString("status"));

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="structureCheck()">
	@Override
	public String structureCheck() {
		String query = "SELECT `preauthResponseProcedureAuthInfoLookup`.`idpreauthResponseProcedureAuthInfoLookup`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`preauthResponseId`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`expiryDate`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`unitsAuthorized`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`denialReason`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`authNumber`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`insuranceId`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`id`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`trackingNumber`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`cptCode`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`effectiveDate`,"
			+"	`preauthResponseProcedureAuthInfoLookup`.`status`"
			+ "FROM `css`.`preauthResponseProcedureAuthInfoLookup` LIMIT 1";
		return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
	}
	//</editor-fold>

}
