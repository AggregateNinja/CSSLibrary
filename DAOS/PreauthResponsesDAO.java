package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PreauthResponses;
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


public class PreauthResponsesDAO implements IStructureCheckable
{
	public static final String table = "`preauthResponses`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthResponses)">
	public static PreauthResponses insert(Connection con, PreauthResponses obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponsesDAO::Insert: Received a NULL PreauthResponses object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponsesDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  patientId,"
			+ "  caseStatus,"
			+ "  sequenceDateStr,"
			+ "  physicianNpi,"
			+ "  sequenceId,"
			+ "  sequenceDate,"
			+ "  caseNumber,"
			+ "  appointmentId,"
			+ "  caseId,"
			+ "  patientDateOfBirth,"
			+ "  userId,"
                        + "  createdDate,"
                        + "  active"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseStatus());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceDateStr());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianNpi());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAppointmentId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientDateOfBirth());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
                        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getCreatedDate());
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
				throw new NullPointerException("PreauthResponsesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthResponses(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthResponses)">
	public static PreauthResponses insert(PreauthResponses obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthResponsesDAO::Insert: Received a NULL PreauthResponses object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthResponses)">
	public static void update(Connection con, PreauthResponses obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponsesDAO::Update: Received a NULL PreauthResponses object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponsesDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " patientId = ?,"
			+ " caseStatus = ?,"
			+ " sequenceDateStr = ?,"
			+ " physicianNpi = ?,"
			+ " sequenceId = ?,"
			+ " sequenceDate = ?,"
			+ " caseNumber = ?,"
			+ " appointmentId = ?,"
			+ " caseId = ?,"
			+ " patientDateOfBirth = ?,"
			+ " userId = ?,"
                        + " createdDate =?,"
                        + " active = ?"
			+ " WHERE idpreauthResponses = " + obj.getIdpreauthResponses();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseStatus());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceDateStr());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianNpi());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getSequenceDate());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseNumber());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAppointmentId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientDateOfBirth());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
                        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getCreatedDate());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthResponses)">
	public static void update(PreauthResponses obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthResponsesDAO::Update: Received a NULL PreauthResponses object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthResponses, boolean (forUpdate))">
	public static PreauthResponses get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponsesDAO::get: Received a NULL or empty PreauthResponses object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthResponsesDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthResponses = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthResponses obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthResponses)">
	public static PreauthResponses get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthResponsesDAO::get: Received a NULL or empty PreauthResponses object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>

        public static PreauthResponses getResponseByCaseNumber(String caseNumber) throws SQLException, NullPointerException
        {
            if (caseNumber == null || caseNumber.isEmpty())
                throw new NullPointerException("PreauthResponsesDAO::deactivateAllForCaseNumber received null/empty Case Number.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `caseNumber` = ? AND active = b'1'";
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, caseNumber);
            ResultSet rs = pStmt.executeQuery();
            
            if (rs.next())
                return objectFromResultSet(rs);
            else
                return null;
        }
        
        public static int deactivateAllForCaseNumber(String caseNumber) throws SQLException, NullPointerException
        {
            if (caseNumber == null || caseNumber.isEmpty())
                throw new NullPointerException("PreauthResponsesDAO::deactivateAllForCaseNumber received null/empty Case Number.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String update = "UPDATE `css`." + table + " SET `active`=b\'0\' WHERE `caseNumber`=?";
            PreparedStatement stmt = con.prepareStatement(update);
            SQLUtil.SafeSetString(stmt, 1, caseNumber);
            int result = stmt.executeUpdate();
            
            return result;
        }
        
	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static PreauthResponses objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthResponses obj = new PreauthResponses();
		obj.setIdpreauthResponses(SQLUtil.getInteger(rs,"idpreauthResponses"));
		obj.setPatientId(rs.getString("patientId"));
		obj.setCaseStatus(rs.getString("caseStatus"));
                obj.setSequenceDateStr(rs.getString("sequenceDateStr"));
		obj.setPhysicianNpi(rs.getString("physicianNpi"));
		obj.setSequenceId(rs.getString("sequenceId"));
                obj.setSequenceDate(rs.getString("sequenceDate"));
		obj.setCaseNumber(rs.getString("caseNumber"));
		obj.setAppointmentId(rs.getString("appointmentId"));
		obj.setCaseId(rs.getString("caseId"));
                obj.setPatientDateOfBirth(rs.getString("patientDateOfBirth"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
                obj.setCreatedDate(rs.getTimestamp("createdDate"));
                obj.setActive(rs.getBoolean("active"));

		return obj;
	}
	//</editor-fold>

        @Override
    public String structureCheck() {
        String query = "SELECT `preauthResponses`.`idpreauthResponses`,\n"
                + "    `preauthResponses`.`patientId`,\n"
                + "    `preauthResponses`.`caseStatus`,\n"
                + "    `preauthResponses`.`sequenceDateStr`,\n"
                + "    `preauthResponses`.`physicianNpi`,\n"
                + "    `preauthResponses`.`sequenceId`,\n"
                + "    `preauthResponses`.`caseNumber`,\n"
                + "    `preauthResponses`.`appointmentId`,\n"
                + "    `preauthResponses`.`caseId`,\n"
                + "    `preauthResponses`.`patientDateOfBirth`,\n"
                + "    `preauthResponses`.`userId`,\n"
                + "    `preauthResponses`.`createdDate`,\n"
                + "    `preauthResponses`.`active`\n"
                + "FROM `css`.`preauthResponses` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
