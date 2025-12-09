package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.SubmissionBatch;
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
import java.util.List;

public class SubmissionBatchDAO implements IStructureCheckable
{

    private static final String table = "`cssbilling`.`submissionBatch`";

    /**
     * Uses the database singleton connection
     * @param obj
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static SubmissionBatch insert(SubmissionBatch obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }
    
    public static SubmissionBatch insert(Connection con, SubmissionBatch obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::Insert:"
                    + "Received a NULL SubmissionBatch object");
        }


        String sql = "INSERT INTO " + table
                + "("
                + "  insuranceTypeId,"
                + "  insuranceSubmissionTypeId,"
                + "  insuranceSubmissionModeId,"
                + "  orderCount,"
                + "  isTestBatch,"
                + "  submitted,"
                + "  submittedDate,"
                + "  completedDate,"
                + "  userId,"
                + "  created"
                + ")"
                + "VALUES (?,?,?,?,?,?,?,?,?,NOW())";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionTypeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionModeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCount());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.isTestBatch());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isSubmitted());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCompletedDate()));
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("SubmissionBatchDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdSubmissionBatch(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(SubmissionBatch obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::Update: Received a NULL SubmissionBatch object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " insuranceTypeId = ?,"
                + " insuranceSubmissionTypeId = ?,"
                + " insuranceSubmissionModeId = ?,"
                + " orderCount = ?,"
                + " isTestBatch = ?,"
                + " submitted = ?,"
                + " submittedDate = ?,"                
                + " completedDate = ?,"
                + " userId = ? "
                + " WHERE idSubmissionBatch = " + obj.getIdSubmissionBatch();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getInsuranceSubmissionModeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCount());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.isTestBatch());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isSubmitted());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getSubmittedDate()));            
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getCompletedDate()));
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        pStmt.close();
    }

    public static SubmissionBatch get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::Get: Received a NULL or empty SubmissionBatch object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idSubmissionBatch = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        SubmissionBatch obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }
    
    /**
     * Used in the EDI File Program - Retrieves batch information for submission batch Id
     * @param batchId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<String> GetBatchInformation(int batchId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<String> retList = new ArrayList<>();
        
        if (batchId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetBatchInformation: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#Avalon Billing Get Batch Information Query\n" +
            "SELECT DISTINCT\n" +
            "sb.idSubmissionBatch,\n" +
            "sb.created AS `runDate`,\n" +
            "sb.orderCount,\n" +
            "1 AS `insuranceRank`,\n" +
            "sb.isTestBatch,\n" +
            "false AS `isSecondary`\n" +
            "FROM cssbilling.submissionBatch sb\n" +
            "LEFT JOIN cssbilling.submissionCriteria sc ON sb.idSubmissionBatch = sc.batchId\n" +
            "INNER JOIN cssbilling.submissionQueue sq ON sb.idSubmissionBatch = sq.submissionBatchId\n" +
            "WHERE sb.idSubmissionBatch = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, batchId);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
        {
            retList.add(rs.getString("runDate"));
            retList.add(rs.getString("idSubmissionBatch"));
            retList.add(rs.getString("orderCount"));
            retList.add(rs.getString("insuranceRank"));
            retList.add(rs.getString("isTestBatch"));
            retList.add(rs.getString("isSecondary"));
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves CPT Code information given detailCptCodeId
     * @param detailCptCodeId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<String> GetCptInfoByDetailCptCodeId(int detailCptCodeId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<String> retList = new ArrayList<>();
        
        if (detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetCptInfoByDetailCptCodeId: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#GetCptInfoByDetailCptCodeId - EDIFile\n" +
            "SELECT\n" +
            "	dcc.iddetailCptCodes AS `DetailCptCodeId`,\n" +
            "    cc.code AS `CptCode`,\n" +
            "    CASE\n" +
            "		WHEN cc.code <> dcc.alias THEN dcc.alias\n" +
            "        WHEN cc.description IS NULL THEN ''\n" +
            "        ELSE cc.description END AS `CptDescription`\n" +
            "	FROM cssbilling.detailCptCodes dcc\n" +
            "    INNER JOIN css.cptCodes cc ON dcc.cptCodeId = cc.idCptCodes\n" +
            "    LEFT JOIN cssbilling.detailCptModifiers dcm ON dcc.iddetailCptCodes = dcm.detailCptCodeId\n" +
            "    LEFT JOIN css.cptModifiers cm ON dcm.cptModifierId = cm.idCptModifiers\n" +
            "    WHERE dcc.iddetailCptCodes = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, detailCptCodeId);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
        {
            retList.add(rs.getString("DetailCptCodeId"));
            retList.add(rs.getString("CptCode"));
            retList.add(rs.getString("CptDescription"));
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves Modifier information given detailCptCodeId
     * @param detailCptCodeId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<List<String>> GetCptModifiersByDetailCptCodeId(int detailCptCodeId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<List<String>> retList = new ArrayList<>();
        
        if (detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetCptModifiersByDetailCptCodeId: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#GetCptModifiersByDetailCptCodeId - EDIFile\n" +
            "SELECT\n" +
            "	dcm.detailCptCodeId AS `DetailCptCodeId`,\n" +
            "    dcm.cptModifierId AS `CptModId`,\n" +
            "    cm.modifier AS `CptMod`,\n" +
            "    CASE\n" +
            "		WHEN cm.description IS NULL THEN ''\n" +
            "        ELSE cm.description END AS `CptModDescription`\n" +
            "FROM cssbilling.detailCptModifiers dcm\n" +
            "LEFT JOIN css.cptModifiers cm ON dcm.cptModifierId = cm.idCptModifiers\n" +
            "WHERE dcm.detailCptCodeId = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, detailCptCodeId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            List<String> mod = new ArrayList<>();
            
            mod.add(rs.getString("DetailCptCodeId"));
            mod.add(rs.getString("CptModId"));
            mod.add(rs.getString("CptMod"));
            mod.add(rs.getString("CptModDescription"));
            
            retList.add(mod);
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves information about a Detail Order by detailOrderId
     * @param detailOrderId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<List<String>> GetDetailInfobyDetailOrderId(int detailOrderId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<List<String>> retList = new ArrayList<>();
        
        if (detailOrderId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetDetailInfobyDetailOrderId: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#GetDetailInfoByDetailOrderId - EDIFile\n" +
            "SELECT DISTINCT\n" +
            "	dcc.iddetailCptCodes AS `DetailCptCodeId`,\n" +
            "    dcc.detailOrderId AS `DetailOrderId`,\n" +
            "    dcc.cptCodeId AS `CptCodeId`,\n" +
            "    dcc.alias AS `Alias`,\n" +
            "    dcc.quantity AS `DetailQuantity`,\n" +
            "    dcc.billAmount AS `BilledAmount`,\n" +
            "    dcc.paid AS `PaidAmount`,\n" +
            "    dcc.lastPaymentDate AS `LastPaymentDate`,\n" +
            "    pos.code AS `PlaceOfServiceCode`,\n" +
            "    pos.name AS `PlaceOfServiceName`,\n" +
            "    dcc.testId AS `TestId`,\n" +
            "    tst.number AS `TestNumber`,\n" +
            "    tst.department AS `DepartmentId`\n" +
            "FROM cssbilling.detailCptCodes dcc\n" +
            "INNER JOIN css.placeOfService pos ON dcc.placeOfServiceId = pos.idPlaceOfService\n" +
            "INNER JOIN css.tests tst ON dcc.testId = tst.idTests\n" +
            "WHERE dcc.detailOrderId = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, detailOrderId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            List<String> detail = new ArrayList<>();
            
            detail.add(rs.getString("DetailCptCodeId"));
            detail.add(rs.getString("DetailOrderId"));
            detail.add(rs.getString("CptCodeId"));
            detail.add(rs.getString("Alias"));
            detail.add(rs.getString("DetailQuantity"));
            detail.add(rs.getString("BilledAmount"));
            detail.add(rs.getString("PaidAmount"));
            detail.add(rs.getString("LastPaymentDate"));
            detail.add(rs.getString("PlaceOfServiceCode"));
            detail.add(rs.getString("PlaceOfServiceName"));
            detail.add(rs.getString("TestId"));
            detail.add(rs.getString("TestNumber"));
            detail.add(rs.getString("DepartmentId"));
            
            retList.add(detail);
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves Modifier information given detailCptCodeId
     * @param detailCptCodeId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<List<String>> GetDiagnosisCodesByDetailCptCodeId(int detailCptCodeId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<List<String>> retList = new ArrayList<>();
        
        if (detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetDiagnosisCodesByDetailCptCodeId: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "##Billing Access GetDiagnosisCodesByDetailOrderId\n" +
            "SELECT\n" +
            "	dcc.detailOrderId,\n" +
            "    ddc.detailCptCodeId,\n" +
            "    ddc.diagnosisCodeId,\n" +
            "    dc.code,\n" +
            "    dc.description,\n" +
            "    dc.version\n" +
            "FROM cssbilling.detailCptCodes dcc\n" +
            "INNER JOIN cssbilling.detailDiagnosisCodes ddc ON dcc.iddetailCptCodes = ddc.detailCptCodeId\n" +
            "INNER JOIN css.diagnosisCodes dc ON ddc.diagnosisCodeId = dc.idDiagnosisCodes\n" +
            "WHERE dcc.iddetailCptCodes = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, detailCptCodeId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            List<String> mod = new ArrayList<>();
            
            mod.add(rs.getString("detailOrderId"));
            mod.add(rs.getString("detailCptCodeId"));
            mod.add(rs.getString("diagnosisCodeId"));
            mod.add(rs.getString("code"));
            mod.add(rs.getString("description"));
            mod.add(rs.getString("version"));
            
            retList.add(mod);
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves Diagnosis Code information by detailOrderId
     * @param detailOrderId
     * @return 
     * @throws SQLException
     */
    public static List<List<String>> GetDiagnosisCodesByDetailOrderId(int detailOrderId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<List<String>> retList = new ArrayList<>();
        
        if (detailOrderId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetDiagnosisCodesByDetailOrderId: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#Billing Access GetDiagnosisCodesByDetailOrderId\n" +
            "SELECT DISTINCT\n" +
            "dcc.detailOrderId,\n" +
            "ddc.detailCptCodeId,\n" +
            "ddc.diagnosisCodeId,\n" +
            "dc.code,\n" +
            "dc.description,\n" +
            "dc.version\n" +
            "FROM cssbilling.detailCptCodes dcc\n" +
            "INNER JOIN cssbilling.detailDiagnosisCodes ddc ON dcc.iddetailCptCodes = ddc.detailCptCodeId\n" +
            "INNER JOIN css.diagnosisCodes dc ON ddc.diagnosisCodeId = dc.idDiagnosisCodes\n" +
            "WHERE dcc.detailOrderId = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, detailOrderId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            List<String> diagCode = new ArrayList<>();
            
            diagCode.add(rs.getString("detailOrderId"));
            diagCode.add(rs.getString("detailCptCodeId"));
            diagCode.add(rs.getString("diagnosisCodeId"));
            diagCode.add(rs.getString("code"));
            diagCode.add(rs.getString("description"));
            diagCode.add(rs.getString("version"));
            
            retList.add(diagCode);
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Retrieves list of insurance ids for submission batch id
     * @param batchId
     * @param isSecondary
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<Integer> GetInsurancesByBatch(int batchId, boolean isSecondary) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<Integer> retList = new ArrayList<>();
        
        if (batchId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetInsurancesByBatch: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#Avalon Billing Get Insurances By Batch Query\n" +
            "SELECT DISTINCT\n" +
            "	dins.insuranceId\n" +
            "FROM cssbilling.submissionBatch sb\n" +
            "INNER JOIN cssbilling.submissionQueue sq ON sb.idSubmissionBatch = sq.submissionBatchId\n" +
            "INNER JOIN cssbilling.detailOrders dord ON sq.detailOrderId = dord.iddetailOrders\n" +
            "INNER JOIN cssbilling.detailInsurances dins ON dord.iddetailOrders = dins.detailOrderId AND dins.rank = ?\n" +
            "WHERE sb.idSubmissionBatch = ?\n" +
            "ORDER BY dins.insuranceId;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, isSecondary ? 2 : 1);
        SQLUtil.SafeSetInteger(pStmt, 2, batchId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            retList.add(rs.getInt("insuranceId"));
        }
        
        return retList;
    }
    
    public static List<Integer> GetLocationsByBatch(int batchId, int locationId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<Integer> retList = new ArrayList<>();
        
        if (batchId <= 0 || locationId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetLocationsByBatch: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#EDI File - Get locations by batch\n" +
            "SELECT DISTINCT\n" +
            "loc.idLocation\n" +
            "FROM cssbilling.submissionBatch sb\n" +
            "INNER JOIN cssbilling.submissionQueue sq ON sb.idSubmissionBatch = sq.submissionBatchId\n" +
            "INNER JOIN cssbilling.detailOrders dord ON sq.detailOrderId = dord.iddetailOrders\n" +
            "INNER JOIN css.orders ord ON dord.orderId = ord.idOrders\n" +
            "INNER JOIN css.locations loc ON ord.locationId = loc.idLocation\n" +
            "INNER JOIN cssbilling.detailInsurances dins ON dord.iddetailOrders = dins.detailOrderId\n" +
            "WHERE sb.idSubmissionBatch = ?\n" +
            "AND dins.insuranceId = ?\n" +
            "ORDER BY loc.idLocation;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, batchId);
        SQLUtil.SafeSetInteger(pStmt, 2, locationId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            retList.add(rs.getInt("idLocation"));
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program. Retrieves a list of detailOrderIds by submissionBatchId, locationId and insuranceId
     * @param batchId
     * @param locationId
     * @param insuranceId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<Integer> GetOrdersByBatchInsuranceLocation(int batchId, int locationId, int insuranceId) throws SQLException, NullPointerException,
            IllegalArgumentException
    {
        List<Integer> ords = new ArrayList<>();
        
        if (batchId <= 0 || locationId <= 0 || insuranceId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetOrdersByBatchInsuranceLocation: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT\n" +
            "dord.iddetailOrders\n" +
            "FROM cssbilling.detailOrders dord\n" +
            "INNER JOIN cssbilling.detailInsurances dins ON dord.iddetailOrders = dins.detailOrderId\n" +
            "INNER JOIN cssbilling.submissionQueue sq ON dord.iddetailOrders = sq.detailOrderId\n" +
            "INNER JOIN cssbilling.submissionBatch sb ON sq.submissionBatchId = sb.idSubmissionBatch\n" +
            "INNER JOIN css.orders ord ON dord.orderId = ord.idOrders\n" +
            "INNER JOIN css.locations loc ON ord.locationId = loc.idLocation\n" +
            "WHERE sb.idSubmissionBatch = ?\n" +
            "AND dins.insuranceId = ?\n" +
            "AND loc.idLocation = ?\n" +
            "AND sq.submitted = 0;";
        
        PreparedStatement ps = con.prepareStatement(sql);
        
        SQLUtil.SafeSetInteger(ps, 1, batchId);
        SQLUtil.SafeSetInteger(ps, 2, insuranceId);
        SQLUtil.SafeSetInteger(ps, 3, locationId);
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next())
        {
            ords.add(rs.getInt("iddetailOrders"));
        }
        
        return ords;
    }
    
    /**
     * Used in the EDI File Program - Returns a list of ids for pending submission batches.
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static List<Integer> GetPendingBatches() throws SQLException, NullPointerException
    {
        List<Integer> retList = new ArrayList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#Avalon Billing Get Pending Batches Query\n" +
            "SELECT DISTINCT\n" +
            "sb.idsubmissionBatch\n" +
            "FROM cssbilling.submissionBatch sb\n" +
            "WHERE sb.submitted = 0\n" +
            "AND sb.insuranceSubmissionTypeId IN(1,2);";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            retList.add(rs.getInt("idsubmissionBatch"));
        }
        
        return retList;
    }
    
    /**
     * Used in the EDI File Program - Flags a batch, by batchId as NULL.
     * @param batchId
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static void Flag(int batchId) throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (batchId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::Flag: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#Flagging Submission Batch as NULL\n" +
            "UPDATE cssbilling.submissionBatch\n" +
            "SET submitted = null,\n" +
            "	submittedDate = null\n" +
            "WHERE idSubmissionBatch = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, batchId);
        
        pStmt.executeUpdate();
    }
    
    public static void Flag(int batchId, boolean status) throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (batchId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::Flag: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = ""; 
        
        if (status)
            sql = "#Flagging Submission Batch as NULL\n" +
                "UPDATE cssbilling.submissionBatch\n" +
                "SET submitted = b'1',\n" +
                "	submittedDate = NOW()\n" +
                "WHERE idSubmissionBatch = ?;";
        else
            sql = "#Flagging Submission Batch as NULL\n" +
                "UPDATE cssbilling.submissionBatch\n" +
                "SET submitted = null,\n" +
                "	submittedDate = null\n" +
                "WHERE idSubmissionBatch = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        SQLUtil.SafeSetInteger(pStmt, 1, batchId);
        
        pStmt.executeUpdate();
    }
    
    /**
     * Used in the EDI File Program - Obtains a list of place of service codes from a detailOrderId
     * @param detailOrderId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<Integer> GetPlaceOfService(int detailOrderId) throws SQLException, NullPointerException,
           IllegalArgumentException
    {
        List<Integer> codes= new ArrayList<>();
        
        if (detailOrderId <= 0)
        {
            throw new IllegalArgumentException("SubmissionBatchDAO::GetPlaceOfService: Received a NULL or empty parameter.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "#GetPlaceOfService - EDIFile\n"
                + "SELECT pos.code\n"
                + "FROM cssbilling.detailCptCodes dcc\n"
                + "INNER JOIN css.placeOfService pos ON dcc.placeOfServiceId = pos.idPlaceOfService\n"
                + "WHERE dcc.detailOrderId = ?;";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        SQLUtil.SafeSetInteger(pStmt, 1, detailOrderId);
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            codes.add(rs.getInt("code"));
        }
        
        return codes;
    }

    private static SubmissionBatch ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        SubmissionBatch obj = new SubmissionBatch();
        obj.setIdSubmissionBatch(rs.getInt("idSubmissionBatch"));
        obj.setInsuranceTypeId(rs.getInt("insuranceTypeId"));
        obj.setInsuranceSubmissionTypeId(rs.getInt("insuranceSubmissionTypeId"));
        obj.setInsuranceSubmissionModeId(rs.getInt("insuranceSubmissionModeId"));
        obj.setOrderCount(rs.getInt("orderCount"));
        obj.setTestBatch(rs.getInt("isTestBatch"));
        obj.setSubmitted(rs.getBoolean("submitted"));
        obj.setSubmittedDate(rs.getDate("submittedDate"));
        obj.setCompletedDate(rs.getDate("completedDate"));
        obj.setUserId(rs.getInt("userId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `submissionBatch`.`idSubmissionBatch`,\n"
                + "    `submissionBatch`.`insuranceTypeId`,\n"
                + "    `submissionBatch`.`insuranceSubmissionTypeId`,\n"
                + "    `submissionBatch`.`insuranceSubmissionModeId`,\n"
                + "    `submissionBatch`.`orderCount`,\n"
                + "    `submissionBatch`.`isTestBatch`,\n"
                + "    `submissionBatch`.`submitted`,\n"
                + "    `submissionBatch`.`submittedDate`,\n"
                + "    `submissionBatch`.`completedDate`,\n"
                + "    `submissionBatch`.`userId`,\n"
                + "    `submissionBatch`.`created`\n"
                + "FROM `cssbilling`.`submissionBatch` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
