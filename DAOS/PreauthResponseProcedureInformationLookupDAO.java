package DAOS;

import DOS.PreauthResponseProcedureInformationLookup;
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


public class PreauthResponseProcedureInformationLookupDAO implements IStructureCheckable
{
	public static final String table = "`preauthResponseProcedureInformationLookup`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthResponseProcedureInformationLookup)">
	public static PreauthResponseProcedureInformationLookup insert(Connection con, PreauthResponseProcedureInformationLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::Insert: Received a NULL PreauthResponseProcedureInformationLookup object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  preauthResponseId,"
			+ "  quantityType,"
			+ "  modifier,"
			+ "  unitsUsed,"
			+ "  description,"
			+ "  requestedQuantity,"
			+ "  id,"
			+ "  cptCode,"
			+ "  bodyPart"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getQuantityType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModifier());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getUnitsUsed());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getRequestedQuantity());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getBodyPart());

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
				throw new NullPointerException("PreauthResponseProcedureInformationLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthResponseProcedureInformationLookup(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthResponseProcedureInformationLookup)">
	public static PreauthResponseProcedureInformationLookup insert(PreauthResponseProcedureInformationLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::Insert: Received a NULL PreauthResponseProcedureInformationLookup object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthResponseProcedureInformationLookup)">
	public static void update(Connection con, PreauthResponseProcedureInformationLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::Update: Received a NULL PreauthResponseProcedureInformationLookup object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " preauthResponseId = ?,"
			+ " quantityType = ?,"
			+ " modifier = ?,"
			+ " unitsUsed = ?,"
			+ " description = ?,"
			+ " requestedQuantity = ?,"
			+ " id = ?,"
			+ " cptCode = ?,"
			+ " bodyPart = ?"
			+ " WHERE idpreauthResponseProcedureInformationLookup = " + obj.getIdpreauthResponseProcedureInformationLookup();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPreauthResponseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getQuantityType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModifier());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getUnitsUsed());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getRequestedQuantity());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCptCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getBodyPart());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthResponseProcedureInformationLookup)">
	public static void update(PreauthResponseProcedureInformationLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::Update: Received a NULL PreauthResponseProcedureInformationLookup object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthResponseProcedureInformationLookup, boolean (forUpdate))">
	public static PreauthResponseProcedureInformationLookup get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::get: Received a NULL or empty PreauthResponseProcedureInformationLookup object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthResponseProcedureInformationLookup = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthResponseProcedureInformationLookup obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthResponseProcedureInformationLookup)">
	public static PreauthResponseProcedureInformationLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponseProcedureInformationLookupDAO::get: Received a NULL or empty PreauthResponseProcedureInformationLookup object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static List<PreauthResponseProcedureInformationLookup> getProcedureInformationLookupsByResponseId(int preauthResponseId) throws SQLException, NullPointerException
        {
            ArrayList<PreauthResponseProcedureInformationLookup> infos = new ArrayList<>();
            
            if (preauthResponseId <= 0)
                throw new NullPointerException("PreauthResponseInsuranceLookupDAO::getProcedureInformationLookups received invalid preauthResponseId.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `preauthResponseId` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, preauthResponseId);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                infos.add(objectFromResultSet(rs));
            }
            
            return infos;
        }

	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthResponseProcedureInformationLookup objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthResponseProcedureInformationLookup obj = new PreauthResponseProcedureInformationLookup();
		obj.setIdpreauthResponseProcedureInformationLookup(SQLUtil.getInteger(rs,"idpreauthResponseProcedureInformationLookup"));
		obj.setPreauthResponseId(SQLUtil.getInteger(rs,"preauthResponseId"));
		obj.setQuantityType(rs.getString("quantityType"));
		obj.setModifier(rs.getString("modifier"));
		obj.setUnitsUsed(rs.getString("unitsUsed"));
		obj.setDescription(rs.getString("description"));
		obj.setRequestedQuantity(rs.getString("requestedQuantity"));
		obj.setId(rs.getString("id"));
		obj.setCptCode(rs.getString("cptCode"));
		obj.setBodyPart(rs.getString("bodyPart"));

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="structureCheck()">
	@Override
	public String structureCheck() {
		String query = "SELECT `preauthResponseProcedureInformationLookup`.`idpreauthResponseProcedureInformationLookup`,"
			+"	`preauthResponseProcedureInformationLookup`.`preauthResponseId`,"
			+"	`preauthResponseProcedureInformationLookup`.`quantityType`,"
			+"	`preauthResponseProcedureInformationLookup`.`modifier`,"
			+"	`preauthResponseProcedureInformationLookup`.`unitsUsed`,"
			+"	`preauthResponseProcedureInformationLookup`.`description`,"
			+"	`preauthResponseProcedureInformationLookup`.`requestedQuantity`,"
			+"	`preauthResponseProcedureInformationLookup`.`id`,"
			+"	`preauthResponseProcedureInformationLookup`.`cptCode`,"
			+"	`preauthResponseProcedureInformationLookup`.`bodyPart`"
			+ "FROM `css`.`preauthResponseProcedureInformationLookup` LIMIT 1";
		return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
	}
	//</editor-fold>

}
