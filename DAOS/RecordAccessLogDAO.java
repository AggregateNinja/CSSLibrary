package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.RecordAccessLog;
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


public class RecordAccessLogDAO implements IStructureCheckable
{
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
	public static final String table = "`recordAccessLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,RecordAccessLog)">
	public static RecordAccessLog insert(Connection con, RecordAccessLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RecordAccessLogDAO::Insert: Received a NULL RecordAccessLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RecordAccessLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  userId,"
			+ "  time,"
			+ "  module,"
			+ "  accession,"
			+ "  patientArNo,"
			+ "  patientFirstName,"
			+ "  patientMiddleName,"
			+ "  patientLastName,"
			+ "  subscriberArNo,"
			+ "  subscriberFirstName,"
			+ "  subscriberMiddleName,"
			+ "  subscriberLastName"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getTime()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModule());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAccession());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientArNo());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientMiddleName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberArNo());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberMiddleName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberLastName());

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

	//<editor-fold defaultstate="collapsed" desc="insert (RecordAccessLog)">
	public static RecordAccessLog insert(RecordAccessLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("RecordAccessLogDAO::Insert: Received a NULL RecordAccessLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, RecordAccessLog)">
	public static void update(Connection con, RecordAccessLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RecordAccessLogDAO::Update: Received a NULL RecordAccessLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RecordAccessLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " userId = ?,"
			+ " time = ?,"
			+ " module = ?,"
			+ " accession = ?,"
			+ " patientArNo = ?,"
			+ " patientFirstName = ?,"
			+ " patientMiddleName = ?,"
			+ " patientLastName = ?,"
			+ " subscriberArNo = ?,"
			+ " subscriberFirstName = ?,"
			+ " subscriberMiddleName = ?,"
			+ " subscriberLastName = ?"
			+ " WHERE  = " + obj.getIdrecordAccessLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getTime()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getModule());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAccession());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientArNo());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientMiddleName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberArNo());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberMiddleName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getSubscriberLastName());
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

	//<editor-fold defaultstate="collapsed" desc="update (RecordAccessLog)">
	public static void update(RecordAccessLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("RecordAccessLogDAO::Update: Received a NULL RecordAccessLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, RecordAccessLog, boolean (forUpdate))">
	public static RecordAccessLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RecordAccessLogDAO::get: Received a NULL or empty RecordAccessLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("RecordAccessLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE  = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		RecordAccessLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (RecordAccessLog)">
	public static RecordAccessLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("RecordAccessLogDAO::get: Received a NULL or empty RecordAccessLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static RecordAccessLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		RecordAccessLog obj = new RecordAccessLog();
		obj.setIdrecordAccessLog(SQLUtil.getInteger(rs,"idrecordAccessLog"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
		obj.setTime(rs.getTimestamp("time"));
		obj.setModule(rs.getString("module"));
		obj.setAccession(rs.getString("accession"));
		obj.setPatientArNo(rs.getString("patientArNo"));
		obj.setPatientFirstName(rs.getString("patientFirstName"));
		obj.setPatientMiddleName(rs.getString("patientMiddleName"));
		obj.setPatientLastName(rs.getString("patientLastName"));
		obj.setSubscriberArNo(rs.getString("subscriberArNo"));
		obj.setSubscriberFirstName(rs.getString("subscriberFirstName"));
		obj.setSubscriberMiddleName(rs.getString("subscriberMiddleName"));
		obj.setSubscriberLastName(rs.getString("subscriberLastName"));

		return obj;
	}
	//</editor-fold>

        //<editor-fold desc="structureCheck" defaultstate="collapsed">
        @Override
        public String structureCheck() {
            String query = "SELECT `recordAccessLog`.`idRecordAccessLog`,\n"
                + "    `recordAccessLog`.`userId`,\n"
                + "    `recordAccessLog`.`time`,\n"
                + "    `recordAccessLog`.`module`,\n"
                + "    `recordAccessLog`.`accession`,\n"
                + "    `recordAccessLog`.`patientArNo`,\n"
                + "    `recordAccessLog`.`patientFirstName`,\n"
                + "    `recordAccessLog`.`patientMiddleName`,\n"
                + "    `recordAccessLog`.`patientLastName`,\n"
                + "    `recordAccessLog`.`subscriberArNo`,\n"
                + "    `recordAccessLog`.`subscriberFirstName`,\n"
                + "    `recordAccessLog`.`subscriberMiddleName`,\n"
                + "    `recordAccessLog`.`subscriberLastName`\n"
                + "FROM `css`.`recordAccessLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, "recordAccessLog", con);
    }
        //</editor-fold>
}
