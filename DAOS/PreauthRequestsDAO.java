package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.PreauthRequests;
import DOS.State;
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


public class PreauthRequestsDAO implements IStructureCheckable
{
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        public static final String table = "`preauthRequests`";
    
        /**
        * All fields except idPreauthRequests
        */
       private final ArrayList<String> fields = new ArrayList<>();
            
        public PreauthRequestsDAO()
        {
            fields.add("apiProvider");
            fields.add("orderId");
            fields.add("appointmentId");
            fields.add("caseNumber");
            fields.add("dateOfService");
            fields.add("priority");
            fields.add("physicianFirstName");
            fields.add("physicianLastName");
            fields.add("physicianNpi");
            fields.add("patientFirstName");
            fields.add("patientLastName");
            fields.add("patientArNo");
            fields.add("patientDob");
            fields.add("patientGender");
            fields.add("patientNote");
            fields.add("insuranceName");
            fields.add("insuranceType");
            fields.add("memberId");
            fields.add("facilityCode");
            fields.add("facilityName");
            fields.add("facilityNpi");
            fields.add("createdDate");
            fields.add("active");
        }
        
	//<editor-fold defaultstate="collapsed" desc="insert (Connection,PreauthRequests)">
	public static PreauthRequests insert(Connection con, PreauthRequests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestsDAO::Insert: Received a NULL PreauthRequests object");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestsDAO:Insert: Received a [NULL] or invalid Connection object");
		}

		String sql = "INSERT INTO " + table
			+ "("
			+ "  apiProvider,"
                        + "  orderId,"
			+ "  appointmentId,"
                        + "  caseNumber,"
			+ "  dateOfService,"
			+ "  priority,"
			+ "  physicianFirstName,"
			+ "  physicianLastName,"
			+ "  physicianNpi,"
			+ "  patientFirstName,"
			+ "  patientLastName,"
			+ "  patientArNo,"
			+ "  patientDob,"
			+ "  patientGender,"
			+ "  patientNote,"
			+ "  insuranceName,"
			+ "  insuranceType,"
			+ "  memberId,"
			+ "  facilityCode,"
			+ "  facilityName,"
			+ "  facilityNpi,"
			+ "  userId,"
                        + "  createdDate,"
                        + "  active"
			+ ")"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try (PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
		{
			int i = 0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getApiProvider());
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAppointmentId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseNumber());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateOfService()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPriority());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianNpi());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientArNo());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPatientDob()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientGender());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientNote());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityNpi());
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
				throw new NullPointerException("PreauthRequestsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
			}
			obj.setIdpreauthRequests(newId);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage() + " " + sql);
			throw ex;
		}

		return obj;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="insert (PreauthRequests)">
	public static PreauthRequests insert(PreauthRequests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null)
		{
			throw new IllegalArgumentException("PreauthRequestsDAO::Insert: Received a NULL PreauthRequests object");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return insert(con, obj);
	}
	//</editor-fold>


	//<editor-fold defaultstate="collapsed" desc="update (Connection, PreauthRequests)">
	public static void update(Connection con, PreauthRequests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestsDAO::Update: Received a NULL PreauthRequests object.");

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestsDAO:Update: Received a [NULL] or invalid Connection object");
		}

		String sql = "UPDATE " + table + " SET "
			+ " apiProvider = ?,"
                        + " orderId = ?,"
			+ " appointmentId = ?,"
                        + " caseNumber = ?,"
			+ " dateOfService = ?,"
			+ " priority = ?,"
			+ " physicianFirstName = ?,"
			+ " physicianLastName = ?,"
			+ " physicianNpi = ?,"
			+ " patientFirstName = ?,"
			+ " patientLastName = ?,"
			+ " patientArNo = ?,"
			+ " patientDob = ?,"
			+ " patientGender = ?,"
			+ " patientNote = ?,"
			+ " insuranceName = ?,"
			+ " insuranceType = ?,"
			+ " memberId = ?,"
			+ " facilityCode = ?,"
			+ " facilityName = ?,"
			+ " facilityNpi = ?,"
			+ " userId = ?,"
                        + " createdDate = ?,"
                        + " active = ?"
			+ " WHERE idpreauthRequests = " + obj.getIdpreauthRequests();

		String sqlOutput = "";
		try (PreparedStatement pStmt = con.prepareStatement(sql))
		{
			int i=0;
			SQLUtil.SafeSetString(pStmt, ++i, obj.getApiProvider());
                        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getAppointmentId());
                        SQLUtil.SafeSetString(pStmt, ++i, obj.getCaseNumber());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateOfService()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPriority());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPhysicianNpi());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientFirstName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientLastName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientArNo());
			SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getPatientDob()));
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientGender());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getPatientNote());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getInsuranceType());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getMemberId());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityCode());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityName());
			SQLUtil.SafeSetString(pStmt, ++i, obj.getFacilityNpi());
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

	//<editor-fold defaultstate="collapsed" desc="update (PreauthRequests)">
	public static void update(PreauthRequests obj) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (obj == null) throw new IllegalArgumentException("PreauthRequestsDAO::Update: Received a NULL PreauthRequests object.");

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		update(con, obj);
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="get (Connection, PreauthRequests, boolean (forUpdate))">
	public static PreauthRequests get(Connection con, Integer id, boolean forUpdate) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestsDAO::get: Received a NULL or empty PreauthRequests object.");
		}

		if (con == null || false == con.isValid(2))
		{
			throw new IllegalArgumentException("PreauthRequestsDAO:get: Received a [NULL] or invalid Connection object");
		}

		String sql = "SELECT * FROM " + table + " WHERE idpreauthRequests = " + String.valueOf(id);
		if (forUpdate)
		{
			sql += " FOR UPDATE ";
		}

		PreauthRequests obj = null;

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

	//<editor-fold defaultstate="collapsed" desc="get (PreauthRequests)">
	public static PreauthRequests get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
	{
		if (id == null || id <= 0)
		{
			throw new IllegalArgumentException("PreauthRequestsDAO::get: Received a NULL or empty PreauthRequests object.");
		}

		Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
		Connection con = dbs.getConnection(true);
		if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

		return get(con, id, false); // not 'for update'
	}
	//</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="getByCaseNumber (caseNumber, active)">
        public static PreauthRequests getByCaseNumber(String caseNumber, boolean active) throws SQLException, NullPointerException
        {
            if (caseNumber == null || caseNumber.isEmpty())
                throw new NullPointerException("PreauthRequestsDAO::getByCaseNumber received null/empty Case Number.");
            
            Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
            Connection con = dbs.getConnection(true);
            if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
            
            String query = "SELECT * FROM `css`." + table + " WHERE `caseNumber` = ? AND active = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(stmt, 1, caseNumber);
            SQLUtil.SafeSetBoolean(stmt, 2, active);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
                return objectFromResultSet(rs);
            else
                return null;
        }
        //</editor-fold>
        
        public static int deactivateAllForCaseNumber(String caseNumber) throws SQLException, NullPointerException
        {
            if (caseNumber == null || caseNumber.isEmpty())
                throw new NullPointerException("PreauthRequestsDAO::deactivateAllForCaseNumber received null/empty Case Number.");
            
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
	public static PreauthRequests objectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
	{
		PreauthRequests obj = new PreauthRequests();
		obj.setIdpreauthRequests(SQLUtil.getInteger(rs,"idpreauthRequests"));
		obj.setApiProvider(rs.getString("apiProvider"));
                obj.setOrderId(rs.getInt("orderId"));
		obj.setAppointmentId(rs.getString("appointmentId"));
                obj.setCaseNumber(rs.getString("caseNumber"));
		obj.setDateOfService(rs.getDate("dateOfService"));
		obj.setPriority(rs.getString("priority"));
		obj.setPhysicianFirstName(rs.getString("physicianFirstName"));
		obj.setPhysicianLastName(rs.getString("physicianLastName"));
		obj.setPhysicianNpi(rs.getString("physicianNpi"));
		obj.setPatientFirstName(rs.getString("patientFirstName"));
		obj.setPatientLastName(rs.getString("patientLastName"));
		obj.setPatientArNo(rs.getString("patientArNo"));
		obj.setPatientDob(rs.getDate("patientDob"));
		obj.setPatientGender(rs.getString("patientGender"));
		obj.setPatientNote(rs.getString("patientNote"));
		obj.setInsuranceName(rs.getString("insuranceName"));
		obj.setInsuranceType(rs.getString("insuranceType"));
		obj.setMemberId(rs.getString("memberId"));
		obj.setFacilityCode(rs.getString("facilityCode"));
		obj.setFacilityName(rs.getString("facilityName"));
		obj.setFacilityNpi(rs.getString("facilityNpi"));
		obj.setUserId(SQLUtil.getInteger(rs,"userId"));
                obj.setCreatedDate(rs.getDate("createdDate"));
                obj.setActive(rs.getBoolean("active"));

		return obj;
	}
	//</editor-fold>

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
