package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailCptCodeLog;
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


public class DetailCptCodeLogDAO implements IStructureCheckable
{
	public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailCptCodeLog`";

	//<editor-fold defaultstate="collapsed" desc="insert (Connection,DetailCptCodeLog)">
	public static DetailCptCodeLog insert(Connection con, DetailCptCodeLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO::Insert: Received a NULL DetailCptCodeLog object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  detailCptCodeId,"
			+ "  userId,"
			+ "  time,"
			+ "  prevCptCodeId,"
			+ "  newCptCodeId,"
			+ "  prevQuantity,"
			+ "  newQuantity,"
			+ "  prevBillAmount,"
			+ "  newBillAmount,"
			+ "  prevPaidAmount,"
			+ "  newPaidAmount,"
			+ "  prevPaymentDate,"
			+ "  newPaymentDate,"
			+ "  prevPlaceOfServiceId,"
			+ "  newPlaceOfServiceId,"
                        + "  prevDiagnosisCodeIds,"
                        + "  newDiagnosisCodeIds,"
                        + "  prevCptModifierIds,"
                        + "  newCptModifierIds,"
                        + "  prevClaimAdjustmentGroupCodeIds,"
                        + "  newClaimAdjustmentGroupCodeIds,"
                        + "  prevClaimAdjustmentReasonCodeIds,"
                        + "  newClaimAdjustmentReasonCodeIds"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getTime()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevQuantity());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewQuantity());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getPrevBillAmount());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getNewBillAmount());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getPrevPaidAmount());
                        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getNewPaidAmount());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPrevPaymentDate()));
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getNewPaymentDate()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevPlaceOfServiceId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewPlaceOfServiceId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevDiagnosisCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewDiagnosisCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevCptModifierIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewCptModifierIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevClaimAdjustmentGroupCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewClaimAdjustmentGroupCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevClaimAdjustmentReasonCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewClaimAdjustmentReasonCodeIds());

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
				throw new NullPointerException("DetailCptCodeLogDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIddetailCptCodeLog(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (DetailCptCodeLog)">
	public static DetailCptCodeLog insert(DetailCptCodeLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO::Insert: Received a NULL DetailCptCodeLog object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, DetailCptCodeLog)">
	public static void update(Connection con, DetailCptCodeLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCodeLogDAO::Update: Received a NULL DetailCptCodeLog object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " detailCptCodeId = ?,"
			+ " userId = ?,"
			+ " time = ?,"
			+ " prevCptCodeId = ?,"
			+ " newCptCodeId = ?,"
			+ " prevQuantity = ?,"
			+ " newQuantity = ?,"
			+ " prevBillAmount = ?,"
			+ " newBillAmount = ?,"
			+ " prevPaidAmount = ?,"
			+ " newPaidAmount = ?,"
			+ " prevPaymentDate = ?,"
			+ " newPaymentDate = ?,"
			+ " prevPlaceOfServiceId = ?,"
			+ " newPlaceOfServiceId = ?"
                        + " prevDiagnosisCodeIds = ?"
                        + " newDiagnosisCodeIds = ?"
                        + " prevCptModifierIds = ?"
                        + " newCptModifierIds = ?"
			+ " WHERE iddetailCptCodeLog = " + obj.getIddetailCptCodeLog();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getTime()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewCptCodeId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevQuantity());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewQuantity());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getPrevBillAmount());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getNewBillAmount());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getPrevPaidAmount());
			SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getNewPaidAmount());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPrevPaymentDate()));
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getNewPaymentDate()));
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPrevPlaceOfServiceId());
			SQLUtil.SafeSetInteger(pStmt, ++i, obj.getNewPlaceOfServiceId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevDiagnosisCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewDiagnosisCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevCptModifierIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewCptModifierIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevClaimAdjustmentGroupCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewClaimAdjustmentGroupCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getPrevClaimAdjustmentReasonCodeIds());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getNewClaimAdjustmentReasonCodeIds());
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

	//<editor-fold defaultstate="collapsed" desc="update (DetailCptCodeLog)">
	public static void update(DetailCptCodeLog obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("DetailCptCodeLogDAO::Update: Received a NULL DetailCptCodeLog object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, DetailCptCodeLog, boolean (forUpdate))">
	public static DetailCptCodeLog get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO::get: Received a NULL or empty DetailCptCodeLog object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE iddetailCptCodeLog = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		DetailCptCodeLog obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (DetailCptCodeLog)">
	public static DetailCptCodeLog get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("DetailCptCodeLogDAO::get: Received a NULL or empty DetailCptCodeLog object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="objectFromResultSet (ResultSet)">
	public static DetailCptCodeLog objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		DetailCptCodeLog obj = new DetailCptCodeLog();
		obj.setIddetailCptCodeLog(SQLUtil.getInteger(rs,"iddetailCptCodeLog"));
		obj.setDetailCptCodeId(SQLUtil.getInteger(rs,"detailCptCodeId"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
		obj.setTime(rs.getTimestamp("time"));
		obj.setPrevCptCodeId(SQLUtil.getInteger(rs,"prevCptCodeId"));
		obj.setNewCptCodeId(SQLUtil.getInteger(rs,"newCptCodeId"));
		obj.setPrevQuantity(SQLUtil.getInteger(rs,"prevQuantity"));
		obj.setNewQuantity(SQLUtil.getInteger(rs,"newQuantity"));
		obj.setPrevBillAmount(rs.getBigDecimal("prevBillAmount"));
		obj.setNewBillAmount(rs.getBigDecimal("newBillAmount"));
		obj.setPrevPaidAmount(rs.getBigDecimal("prevPaidAmount"));
		obj.setNewPaidAmount(rs.getBigDecimal("newPaidAmount"));
		obj.setPrevPaymentDate(rs.getTimestamp("prevPaymentDate"));
		obj.setNewPaymentDate(rs.getTimestamp("newPaymentDate"));
		obj.setPrevPlaceOfServiceId(SQLUtil.getInteger(rs,"prevPlaceOfServiceId"));
		obj.setNewPlaceOfServiceId(SQLUtil.getInteger(rs,"newPlaceOfServiceId"));
                obj.setPrevDiagnosisCodeIds(rs.getString("prevDiagnosisCodeIds"));
                obj.setNewDiagnosisCodeIds(rs.getString("newDiagnosisCodeIds"));
                obj.setPrevCptModifierIds(rs.getString("prevCptModifierIds"));
                obj.setNewCptModifierIds(rs.getString("newCptModifierIds"));
                obj.setPrevClaimAdjustmentGroupCodeIds("prevClaimAdjustmentGroupCodeIds");
                obj.setNewClaimAdjustmentGroupCodeIds("newClaimAdjustmentGroupCodeIds");
                obj.setPrevClaimAdjustmentReasonCodeIds("prevClaimAdjustmentReasonCodeIds");
                obj.setNewClaimAdjustmentReasonCodeIds("newClaimAdjustmentReasonCodeIds");

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        String query = "SELECT `detailCptCodeLog`.`iddetailCptCodeLog`,\n"
                + "    `detailCptCodeLog`.`detailCptCodeId`,\n"
                + "    `detailCptCodeLog`.`userId`,\n"
                + "    `detailCptCodeLog`.`time`,\n"
                + "    `detailCptCodeLog`.`prevCptCodeId`,\n"
                + "    `detailCptCodeLog`.`newCptCodeId`,\n"
                + "    `detailCptCodeLog`.`prevQuantity`,\n"
                + "    `detailCptCodeLog`.`newQuantity`,\n"
                + "    `detailCptCodeLog`.`prevBillAmount`,\n"
                + "    `detailCptCodeLog`.`newBillAmount`,\n"
                + "    `detailCptCodeLog`.`prevPaidAmount`,\n"
                + "    `detailCptCodeLog`.`newPaidAmount`,\n"
                + "    `detailCptCodeLog`.`prevPaymentDate`,\n"
                + "    `detailCptCodeLog`.`newPaymentDate`,\n"
                + "    `detailCptCodeLog`.`prevPlaceOfServiceId`,\n"
                + "    `detailCptCodeLog`.`newPlaceOfServiceId`,\n"
                + "    `detailCptCodeLog`.`prevDiagnosisCodeIds`,\n"
                + "    `detailCptCodeLog`.`newDiagnosisCodeIds`,\n"
                + "    `detailCptCodeLog`.`prevCptModifierIds`,\n"
                + "    `detailCptCodeLog`.`newCptModifierIds`,\n"
                + "    `detailCptCodeLog`.`prevClaimAdjustmentGroupCodeIds`,\n"
                + "    `detailCptCodeLog`.`newClaimAdjustmentGroupCodeIds`,\n"
                + "    `detailCptCodeLog`.`prevClaimAdjustmentReasonCodeIds`,\n"
                + "    `detailCptCodeLog`.`newClaimAdjustmentReasonCodeIds`\n"
                + "FROM `cssbilling`.`detailCptCodeLog` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
        
}
