
package BL;

import DAOS.BillingPayorDAO;
import DAOS.CptModifierDAO;
import DAOS.DetailCptCodeDAO;
import DAOS.DetailDiagnosisCodeDAO;
import DAOS.DetailInsuranceDAO;
import DAOS.DetailOrderEventDAO;
import DAOS.DiagnosisCodeDAO;
import DAOS.EventDAO;
import DAOS.FeeScheduleActionDAO;
import DAOS.FeeScheduleAssignmentDAO;
import DAOS.FeeScheduleAssignmentDAO.Status;
import DAOS.FeeScheduleDAO;
import DAOS.FeeScheduleTestLookupDAO;
import DAOS.InsuranceDAO;
import DAOS.LedgerDAO;
import DAOS.RemitDetailDAO;
import DAOS.RemitInfoDAO;
import DAOS.TestDAO;
import DOS.BillingPayor;
import DOS.CptModifier;
import DOS.DetailCptCode;
import DOS.DetailInsurance;
import DOS.DetailOrderEvent;
import DOS.DiagnosisCodes;
import DOS.FeeSchedule;
import DOS.FeeScheduleAction;
import DOS.FeeScheduleAssignment;
import DOS.FeeScheduleTestLookup;
import DOS.Insurances;
import DOS.Ledger;
import DOS.OrderCptCode;
import DOS.OrderedTest;
import DOS.Orders;
import DOS.RemitDetail;
import DOS.RemitInfo;
import DOS.TestContext;
import DOS.Tests;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Utility.ExceptionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BillingBL
{
    
    /**
     * The strategy used when applying an adjustment to detail lines.
     */
    public enum AdjustmentApplicationMethod
    {
        
        USER_APPLIED,               // The user has manually chosen which 
                                    // lines/orders to apply the adjustment to,
                                    // and the amount of each adjustment.
        
        AUTO_PAY_OFF_FIRST_FOUND,   // Automatically applies the adjustment in 
                                    // a way to completely 'pay off' lines 
                                    // as they are found.
        
        AUTO_DISTRIBUTE_EVENLY      // Attempts to apply adjustment evenly across
                                    // all lines chosen.
    }
    
    /**
     * If a line is being paid against: thrown if the amount being paid
     *  or written-off exceeds the remaining balance on the line or the billAmount
     * 
     * If a line is having its paid amount reduced: thrown if the paid amount
     * goes negative
     */
    public static class AdjustmentLimitExceededException extends Exception
    {

        public AdjustmentLimitExceededException()
        {
        }

        public AdjustmentLimitExceededException(String message)
        {
            super(message);
        }

        public AdjustmentLimitExceededException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public AdjustmentLimitExceededException(Throwable cause)
        {
            super(cause);
        }

        public AdjustmentLimitExceededException(String message, 
                Throwable cause, boolean enableSuppression, boolean writableStackTrace)
        {
            super(message, cause, enableSuppression, writableStackTrace);
        }
        
    }
    
    /**
     * Returns CPT Modifier object(s) for a CPT row attached to an order.
     * If no modifiers are present, returns an empty list.
     * 
     * @param orderCptCode
     * @return List of CptModifier objects. Empty if none found.
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    @Deprecated
    public static List<CptModifier> getModifiers(OrderCptCode orderCptCode)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderCptCode == null || orderCptCode.getIdorderCptCodes() == null ||
                orderCptCode.getIdorderCptCodes().equals(0))
        {
            throw new IllegalArgumentException("BillingBL::getModifiers(OrderCptCode): "
                    + "Received a NULL or empty OrderCptCode object");
        }
        
        return getModifiersForOrderCptCodeId(orderCptCode.getIdorderCptCodes());
    }
    
    @Deprecated
    public static List<CptModifier> getModifiersForOrderCptCodeId(Integer orderCptCodeId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0) 
            throw new IllegalArgumentException("BillingBL:getModifiers: "
                    + "Received a [NULL] orderCptCodeId argument");
        
        List<CptModifier> cptModifiers = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT cm.* FROM cptModifiers cm"
                + " INNER JOIN orderCptModifiers ocm ON cm.idCptModifiers = ocm.cptModifierId"
                + " WHERE ocm.orderCptCodeId = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderCptCodeId);
        
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            cptModifiers.add(CptModifierDAO.GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return cptModifiers;        
    }
    
    public static List<CptModifier> getOrderModifiersForCptCode(Integer orderId, Integer cptCodeId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (cptCodeId == null || cptCodeId <= 0) 
            throw new IllegalArgumentException("BillingBL:getOrderModifiersForCptCode: "
                    + "Received a [NULL] cptCodeId argument");
        
        if (orderId == null || orderId <=0)
            throw new IllegalArgumentException("BillingBL:getOrderModifiersForCptCode: "
                    + "Received a [NULL] orderId argument");
        
        List<CptModifier> cptModifiers = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT cm.* " +
            " FROM orderCptModifiers ocm " +
            " INNER JOIN orderCptCodes occ ON ocm.orderCptCodeId = occ.idorderCptCodes " +
            " INNER JOIN orderedTests ot ON occ.orderedTestId = ot.idorderedTests " +
            " INNER JOIN cptModifiers cm ON ocm.cptModifierId = cm.idCptModifiers " +
            " WHERE ot.orderid = ? AND occ.cptCodeId  = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        pStmt.setInt(2, cptCodeId);
        
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            cptModifiers.add(CptModifierDAO.GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return cptModifiers;        
    }
    
    public static List<CptModifier> getOrderModifiersForCptCode(Integer orderId, Integer cptCodeId, Integer testNumber)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (cptCodeId == null || cptCodeId <= 0) 
            throw new IllegalArgumentException("BillingBL:getOrderModifiersForCptCode: "
                    + "Received a [NULL] cptCodeId argument");
        
        if (orderId == null || orderId <=0)
            throw new IllegalArgumentException("BillingBL:getOrderModifiersForCptCode: "
                    + "Received a [NULL] orderId argument");
        
        if (testNumber == null || testNumber <=0)
            throw new IllegalArgumentException("BillingBL:getOrderModifiersForCptCode: "
                    + "Received a [NULL] testNumber argument");
        
        List<CptModifier> cptModifiers = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT cm.* " +
            " FROM orderCptModifiers ocm " +
            " INNER JOIN orderCptCodes occ ON ocm.orderCptCodeId = occ.idorderCptCodes " +
            " INNER JOIN orderedTests ot ON occ.orderedTestId = ot.idorderedTests " +
            " INNER JOIN cptModifiers cm ON ocm.cptModifierId = cm.idCptModifiers " +
            " WHERE ot.orderid = ? AND occ.cptCodeId  = ? AND" +
            " (ot.testNumber = ? OR ot.panelNumber = ? OR ot.batteryNumber = ?)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        pStmt.setInt(2, cptCodeId);
        pStmt.setInt(3, testNumber);
        pStmt.setInt(4, testNumber);
        pStmt.setInt(5, testNumber);
        
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            cptModifiers.add(CptModifierDAO.GetObjectFromResultSet(rs));
        }
        pStmt.close();
        return cptModifiers;        
    }
    
    
    public static List<DiagnosisCodes> getOrderDiagnosisCodesForCptCode(Integer orderId, Integer cptCodeId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (cptCodeId == null || cptCodeId <= 0) 
            throw new IllegalArgumentException("BillingBL:getOrderDiagnosisCodesForCptCode: "
                    + "Received a [NULL] cptCodeId argument");
        
        if (orderId == null || orderId <=0)
            throw new IllegalArgumentException("BillingBL:getOrderDiagnosisCodesForCptCode: "
                    + "Received a [NULL] orderId argument");
        
        List<DiagnosisCodes> diagnosisCodes = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
                
        String sql = "SELECT dc.*" +
            " FROM orderCptDiagnosisCodes ocd" +
            " INNER JOIN orderCptCodes occ ON ocd.orderCptCodeId = occ.idorderCptCodes" +
            " INNER JOIN orderedTests ot ON occ.orderedTestId = ot.idorderedTests" +
            " INNER JOIN diagnosisCodes dc ON ocd.diagnosisCodeId = dc.iddiagnosisCodes" +
            " WHERE ot.orderid = ? AND occ.cptCodeId  = ? AND ocd.attached = 1";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        pStmt.setInt(2, cptCodeId);
        
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            diagnosisCodes.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
        }
        pStmt.close();
        return diagnosisCodes;
    }
    
    public static List<DiagnosisCodes> getOrderDiagnosisCodesForCptCode(Integer orderId, Integer cptCodeId, Integer testNumber)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (cptCodeId == null || cptCodeId <= 0) 
            throw new IllegalArgumentException("BillingBL:getOrderDiagnosisCodesForCptCode: "
                    + "Received a [NULL] cptCodeId argument");
        
        if (orderId == null || orderId <=0)
            throw new IllegalArgumentException("BillingBL:getOrderDiagnosisCodesForCptCode: "
                    + "Received a [NULL] orderId argument");
        
        if (testNumber == null || testNumber <=0)
            throw new IllegalArgumentException("BillingBL:getOrderDiagnosisCodesForCptCode: "
                    + "Received a [NULL] testNumber argument");
        
        List<DiagnosisCodes> diagnosisCodes = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
                
        String sql = "SELECT dc.*" +
            " FROM orderCptDiagnosisCodes ocd" +
            " INNER JOIN orderCptCodes occ ON ocd.orderCptCodeId = occ.idorderCptCodes" +
            " INNER JOIN orderedTests ot ON occ.orderedTestId = ot.idorderedTests" +
            " INNER JOIN diagnosisCodes dc ON ocd.diagnosisCodeId = dc.iddiagnosisCodes" +
            " WHERE ot.orderid = ? AND occ.cptCodeId  = ? AND ocd.attached = 1 AND" +
            " (ot.testNumber = ? OR ot.panelNumber = ? OR ot.batteryNumber = ?)";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        pStmt.setInt(2, cptCodeId);
        pStmt.setInt(3, testNumber);
        pStmt.setInt(4, testNumber);
        pStmt.setInt(5, testNumber);
        
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            diagnosisCodes.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
        }
        pStmt.close();
        return diagnosisCodes;
    }

    /**
     * Returns Diagnosis Codes object(s) for a CPT row attached to an order.
     * If no modifiers are present, returns an empty list.
     * 
     * @param orderCptCode
     * @param attachedOnly Only rows attached to the cpt
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    @Deprecated
    public static List<DiagnosisCodes> getDiagnosisCodes(OrderCptCode orderCptCode, boolean attachedOnly)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderCptCode == null) throw new IllegalArgumentException("BillingBL:getDiagnosisCodes: Received a [NULL] OrderCptCode argument");
        return getDiagnosisCodesByOrderCptCodeId(orderCptCode.getIdorderCptCodes(), attachedOnly);
    }
    
    @Deprecated
    public static List<DiagnosisCodes> getDiagnosisCodesByOrderCptCodeId(Integer orderCptCodeId, boolean attachedOnly)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::getDiagnosisCodesByOrderCptCodeId: "
                    + "Received an orderCptCodeId object with a [NULL] or empty");
        }
        
        List<DiagnosisCodes> diagnosisCodes = new LinkedList<>();
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
                
        String sql = "SELECT dc.* FROM diagnosisCodes dc"
                + " INNER JOIN orderCptDiagnosisCodes odc ON dc.idDiagnosisCodes = odc.diagnosisCodeId"
                + " WHERE odc.orderCptCodeId = ?";
        
        if (attachedOnly)
        {
            sql += " AND odc.attached = 1";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderCptCodeId);
        
        ResultSet rs = pStmt.executeQuery();
        
        while (rs.next())
        {
            diagnosisCodes.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
        }
        pStmt.close();
        return diagnosisCodes;        
    }
    
    /**
     * Gets the unique database identifier for an insurance's type based on
     * the systemname
     * @param systemName
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    /*
    public static Integer getDetailInsuranceTypeId(String systemName)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (systemName == null || systemName.isEmpty())
            throw new IllegalArgumentException("BillingBL::getDetailInsuranceTypeId: "
                    + "Received a NULL or empty systemName argument");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT iddetailInsuranceTypes "
                + " FROM cssbilling.detailInsuranceTypes WHERE systemName = ?";
        
        Integer detailInsuranceTypeId = null;
        
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.setString(1, systemName);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            detailInsuranceTypeId = rs.getInt("iddetailInsuranceTypes");
            if (detailInsuranceTypeId.equals(0)) detailInsuranceTypeId = null;
        }
        return detailInsuranceTypeId;
    }
    */
    
    /**
     * Returns <systemName, iddetailInsuranceTypes> maps for each item in the
     *  detailInsuranceTypes table.
     * @return 
     * @throws java.sql.SQLException 
     */
    /*
    public static Map<String, Integer> getDetailInsuranceTypeMaps()
            throws SQLException, NullPointerException
    {
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT iddetailInsuranceTypes, systemName"
                + " FROM cssbilling.detailInsuranceTypes";
        
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        
        Map<String, Integer> maps = new HashMap<>();
        while (rs.next())
        {
            Integer detailInsuranceTypeId = rs.getInt("iddetailInsuranceTypes");
            if (detailInsuranceTypeId.equals(0)) detailInsuranceTypeId = null;
            
            String systemName = rs.getString("systemName");
            
            if (detailInsuranceTypeId == null || systemName == null || systemName.isEmpty())
            {
                throw new SQLException("BillingBL::getDetailInsuranceTypeMaps:"
                        + " Received a NULL or empty iddetailInsuranceTypes or"
                        + " systemName. Ensure the data in the "
                        + "cssbilling.detailInsuranceTypes table is correct.");
            }
            
            maps.put(systemName, detailInsuranceTypeId);
        }
        return maps;        
    }
    */
    /**
     * Returns all of the CPT modifiers associated with a billing schema
     *  cpt detail line.
     * @param detailCptCodeId
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public List<CptModifier> getModifiersForDetailOrderCptCode(Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::getModifiersForDetailOrderCptCode:"
                    + " Received a NULL or empty detailCptCodeId.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT cm.* FROM css.cptModifiers cm"
                + " INNER JOIN cssbilling.detailCptModifiers dcm ON cm.idCptModifiers = dcm.cptModifierId"
                + " WHERE dcm.detailCptCodeId = " + String.valueOf(detailCptCodeId);
        
        PreparedStatement pStmt = con.prepareStatement(sql);

        List<CptModifier> obj = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                obj.add(CptModifierDAO.GetObjectFromResultSet(rs));
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
    

    public List<DiagnosisCodes> getDiagnosisCodesForDetailOrderCptCode(Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::getDiagnosisCodesForDetailOrderCptCode:"
                    + " Received a NULL or empty detailCptCodeId.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT dc.* FROM css.diagnosisCodes dc"
                + " INNER JOIN cssbilling.detailDiagnosisCodes ddc ON dc.idDiagnosisCodes = ddc.diagnosisCodeId"
                + " WHERE ddc.detailCptCodeId = " + String.valueOf(detailCptCodeId);
        
        PreparedStatement pStmt = con.prepareStatement(sql);

        List<DiagnosisCodes> obj = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                obj.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
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
     * Returns the active assigned fee schedule for the order, based on the
     *  type of insurance attached. Provide the list of client/patient identifiers
     *  from the preferences table.
     * 
     * If no fee schedule is attached, returns NULL
     * 
     * @param order
     * @param clientBillingInsuranceIds Optional list of insuranceIds that denote "client billing"
     * @param patientBillingInsuranceIds Optional list of insuranceIds that denote "patient billing"
     * @return FeeScheduleAssignment NULL if not found
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static FeeScheduleAssignment getActiveFeeScheduleAssignmentForOrder(
            Orders order,
            List<Integer> clientBillingInsuranceIds,
            List<Integer> patientBillingInsuranceIds)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (order == null)
        {
            throw new IllegalArgumentException("BillingBL::getActiveFeeScheduleForOrder:"
                    + " Received a [NULL] or empty Orders object");
        }
        
        if (order.getInsurance() == null || order.getInsurance().equals(0))
        {
            throw new IllegalArgumentException("BillingBL::getActiveFeeScheduleForOrder: "
                    + " Insurance on argument order object was [NULL] or empty");
        }
        
        
        Integer insuranceId = order.getInsurance();
        return getActiveFeeScheduleAssignmentForOrderInsuranceId(insuranceId, order.getOrderDate(),
                clientBillingInsuranceIds, patientBillingInsuranceIds);
    }
    
    public static FeeScheduleAssignment getActiveFeeScheduleAssignmentForOrderInsuranceId(
            Integer insuranceId,
            Date orderDate,
            List<Integer> clientBillingInsuranceIds,
            List<Integer> patientBillingInsuranceIds)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (insuranceId == null || insuranceId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::"
                    + "getActiveFeeScheduleAssignmentForOrderInsuranceId:"
                    + " Received a [NULL] or empty InsuranceId argument");
        }
        
        BillingPayor billingPayor = null;
        FeeScheduleAssignment assignment = null;
        if (clientBillingInsuranceIds != null && clientBillingInsuranceIds.size() > 0 &&
                clientBillingInsuranceIds.contains(insuranceId))
        {
            // Allow NULL assignments to fall through and be handled by
            // the calling code
            try
            {
                billingPayor = getOrCreateBillingPayorForInsuranceId(insuranceId);
            }
            catch (SQLException ex)
            {
                throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentForOrderInsuranceId:"
                        + " Could not retrive fee schedule assignment for client billing. InsuranceId: "
                        + insuranceId.toString());
            }
        }
        else if (patientBillingInsuranceIds != null && patientBillingInsuranceIds.size() > 0 &&
                patientBillingInsuranceIds.contains(insuranceId))
        {
            try
            {
                //billingPayor = getOrCreateBillingPayorForPatientId(insuranceId);
                billingPayor = getOrCreateBillingPayorForInsuranceId(insuranceId);
            }
            catch (SQLException ex)
            {
                throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentForOrder:"
                        + " Could not retrive fee schedule assignment for patient billing. InsuranceId: "
                        + insuranceId.toString());
            }
        }
        else // Regular insurance
        {
            try
            {
                billingPayor = getOrCreateBillingPayorForInsuranceId(insuranceId);
            }
            catch (SQLException ex)
            {
                throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentForOrder:"
                        + " Could not retrive fee schedule assignment for insurance billing. InsuranceId: "
                        + insuranceId.toString());
            }
        }
        
        List<FeeScheduleAssignment> assignments = FeeScheduleAssignmentDAO.getByBillingPayorIdStatus(
                billingPayor.getIdbillingPayors(), orderDate, Status.ACTIVE);
        if (assignments.size() > 1)
        {
            throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentForOrder: "
                    + "Returned more than one active fee schedule assignment for order insuranceId " + insuranceId.toString());
        }
        else if (assignments.size() == 1)
        {
            assignment = assignments.get(0);
        }
        return assignment;    
    }    
    
    /**
     * NON TRANSACTIONAL; DOES NOT CREATE EVENTS OR UPDATE THE DETAIL ORDER LINE'S BALANCE.
     * 
     *  1) Updates the detailCptCode line's paid amount based on the adjustment
     *  2) Writes a ledger entry.
     *  3) If isPayment is true, updates the lastPaymentDate on the detailCptCode line
     *
     * There may be many adjustments made to a single order at a time, so the
     *  event creation application of the adjustment should all happen within
     *  one transaction.
     * 
     * CALLING CODE NEEDS TO EMBED THIS CALL AND OTHER WORK IN A SQL TRANSACTION
     *  
     * @param adjustmentAmount
     * @param detailCptCode
     * @param isPayment If true, sets the lastPaymentDate on the detailCptCode line
     * @throws java.sql.SQLException 
     * @throws BL.BillingBL.AdjustmentLimitExceededException 
     */
    private static void applyAdjustmentToDetailCptCode(
            BigDecimal adjustmentAmount,
            DetailCptCode detailCptCode,
            boolean isPayment)
            throws IllegalArgumentException, NullPointerException,
                SQLException, AdjustmentLimitExceededException
    {
        if (adjustmentAmount == null)
        {
            throw new IllegalArgumentException(
                    "BillingBL::applyAdjustmentToDetailCptCode:"
                            + " Received a [NULL] adjustment amount"
                            + " BigDecimal object");
        }
        
        if (adjustmentAmount.equals(BigDecimal.ZERO))
        {
            throw new IllegalArgumentException(
                    "BillingBL::applyAdjustmentToDetailCptCode:"
                            + " Received a BigDecimal adjustment amount "
                            + " of Zero.");
        }
        
        if (detailCptCode == null || detailCptCode.getIddetailCptCodes() == null ||
                detailCptCode.getIddetailCptCodes() <=0 || detailCptCode.getDetailOrderId() == null ||
                detailCptCode.getDetailOrderId() <= 0)
        {
            throw new IllegalArgumentException(
                    "BillingBL::applyAdjustmentToDetailCptCode:"
                            + " Received a [NULL] or unsaved DetailCptCode object");
        }
        
        if (detailCptCode.getPaid() == null)
        {
            throw new IllegalArgumentException("BillingBL:applyAdjustmentToDetailCptCode:"
                    + " DetailCptCode line has a [NULL] paid amount. May indicate a system error");
        }
        
        if (detailCptCode.getBillAmount() == null || detailCptCode.getBillAmount().equals(BigDecimal.ZERO))
        {
            throw new IllegalArgumentException("BillingBL:applyAdjustmentToDetailCptCode:"
                    + " DetailCptCode line has a [NULL] or Zero bill amount. May indicate a system error");
        }

        // If the adjustment is positive (i.e. there's money being paid or written off)
        if (adjustmentAmount.compareTo(BigDecimal.ZERO) > 0)
        {
            // If the current paid amount plus the new adjustment exceeds the bill amount
            if (detailCptCode.getPaid().add(adjustmentAmount).compareTo(detailCptCode.getBillAmount()) > 0)
            {
                throw new AdjustmentLimitExceededException("BillingBL::applyAdjustmentToDetailCptCode: "
                    + " For detailCptCodeId " + detailCptCode.getIddetailCptCodes().toString() + ", the"
                    + " paid amount (" + detailCptCode.getPaid().toString() + ") plus the adjustment"
                    + " amount (" + adjustmentAmount.toString() + ") would exceed the current"
                    + " bill amount (" + detailCptCode.getBillAmount().toString() + ") of the line.");
            }
        }
        else // If the adjustment is negative (paid or written-off amount is being reduced)
        {
            // If it would cause the paid amount to become negative
            if (detailCptCode.getPaid().add(adjustmentAmount).compareTo(BigDecimal.ZERO) < 0)
            {
                throw new AdjustmentLimitExceededException("BillingBL::applyAdjustmentToDetailCptCode: "
                    + " For detailCptCodeId " + detailCptCode.getIddetailCptCodes().toString() + ", the"
                    + " paid amount (" + detailCptCode.getPaid().toString() + ") plus the adjustment"
                    + " amount (" + adjustmentAmount.toString() + ") would cause the paid"
                    + " amount of the line to be negative.");                
            }
        }
        
        // Add the adjustment amount to the paid amount on the line
        detailCptCode.setPaid(detailCptCode.getPaid().add(adjustmentAmount));
        
        if (isPayment) detailCptCode.setLastPaymentDate(new Date());
        
        // If anything except one line is updated, this throws exception:
        DetailCptCodeDAO.update(detailCptCode);
        
        Ledger ledger = new Ledger();
        ledger.setDetailCptCodeId(detailCptCode.getIddetailCptCodes());
        ledger.setDetailOrderId(detailCptCode.getDetailOrderId());
        ledger.setPreviousAmount(detailCptCode.getPaid());
        ledger.setAdjustmentAmount(adjustmentAmount);
        
        // Throws an exception if not inserted successfully:
        LedgerDAO.insert(ledger);
        
    }
    
    public static BillingPayor getOrCreateBillingPayorForInsuranceId(Integer insuranceId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (insuranceId == null || insuranceId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::"
                    + "getOrCreateBillingPayorForInsuranceId: Received a"
                    + " [NULL] insuranceId.");
        }
        
        BillingPayor billingPayor = BillingPayorDAO.getByInsuranceId(insuranceId);
        if (billingPayor == null)
        {
            // A billing payor line for this insurance doesn't exist;
            // create and return one
            billingPayor = new BillingPayor();
            billingPayor.setInsuranceId(insuranceId);
            billingPayor = BillingPayorDAO.insert(billingPayor);
            if (billingPayor == null
                    || billingPayor.getIdbillingPayors() == null
                    || billingPayor.getIdbillingPayors() <= 0)
            {
                throw new SQLException("BillingBL::getOrCreateBillingPayorForInsuranceId:"
                        + " Insert returned a [NULL] or empty object");
            }
        }
        return billingPayor;
    }
    
    public static BillingPayor getOrCreateBillingPayorForClientId(Integer clientId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (clientId == null || clientId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::"
                    + "getOrCreateBillingPayorForInsuranceId: Received a"
                    + " [NULL] clientId.");
        }
        
        BillingPayor billingPayor = BillingPayorDAO.getByClientId(clientId);
        if (billingPayor == null)
        {
            // A billing payor line for this insurance doesn't exist;
            // create and return one
            billingPayor = new BillingPayor();
            billingPayor.setClientId(clientId);
            billingPayor = BillingPayorDAO.insert(billingPayor);
            if (billingPayor == null
                    || billingPayor.getIdbillingPayors() == null
                    || billingPayor.getIdbillingPayors() <= 0)
            {
                throw new SQLException("BillingBL::getOrCreateBillingPayorForClientId:"
                        + " Insert returned a [NULL] or empty object");
            }
        }
        return billingPayor;        
    }
    
    public static BillingPayor getOrCreateBillingPayorForPatientId(Integer patientId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (patientId == null || patientId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::"
                    + "getOrCreateBillingPayorForPatientId: Received a"
                    + " [NULL] patientId.");
        }
        
        BillingPayor billingPayor = BillingPayorDAO.getByPatientId(patientId);
        if (billingPayor == null)
        {
            // A billing payor line for this insurance doesn't exist;
            // create and return one
            billingPayor = new BillingPayor();
            billingPayor.setPatientId(patientId);
            billingPayor = BillingPayorDAO.insert(billingPayor);
            if (billingPayor == null
                    || billingPayor.getIdbillingPayors() == null
                    || billingPayor.getIdbillingPayors() <= 0)
            {
                throw new SQLException("BillingBL::getOrCreateBillingPayorForPatientId:"
                        + " Insert returned a [NULL] or empty object");
            }
        }
        return billingPayor;        
    }
    
    public static BillingPayor getOrCreateBillingPayorForAllId(Integer insuranceId, Integer clientId, Integer patientId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        BillingPayor billingPayor = BillingPayorDAO.getByAllId(insuranceId, clientId, patientId);
        if (billingPayor == null)
        {
            // A billing payor line for this insurance/client/patient doesn't exist;
            // create and return one
            billingPayor = new BillingPayor();
            billingPayor.setInsuranceId(insuranceId);
            billingPayor.setClientId(clientId);
            billingPayor.setPatientId(patientId);
            billingPayor = BillingPayorDAO.insert(billingPayor);
            if (billingPayor == null
                    || billingPayor.getIdbillingPayors() == null
                    || billingPayor.getIdbillingPayors() <= 0)
            {
                throw new SQLException("BillingBL::getOrCreateBillingPayorForPatientId:"
                        + " Insert returned a [NULL] or empty object");
            }
        }
        return billingPayor;        
    }
    
    /**
     * Returns all fee schedules assigned to the argument billingPayorId, regardless
     *  of whether they are active or not.
     * @param billingPayorId
     * @return 
     * @throws java.sql.SQLException 
     */
    public static List<FeeScheduleAssignment> getFeeScheduleAssignmentsByBillingPayorId(Integer billingPayorId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException(
                "BillingBL::getFeeScheduleAssignmentsByBillingPayorId:"
                        + " Received a [NULL] or invalid billingPayorId");
        }
        
        BillingPayor billingPayor = BillingPayorDAO.get(billingPayorId);
        
        if (billingPayor == null || billingPayor.getIdbillingPayors() == null)
        {
            throw new SQLException("BillingBL::getFeeScheduleAssignmentsByBillingPayorId:"
                    + " Returned a [NULL] or empty BillingPayor object");
        }
        
        return FeeScheduleAssignmentDAO.getByBillingPayorId(billingPayor.getIdbillingPayors());
    }
    
    /**
     * Gets a list of fee schedule assignments that overlap the provided date
     *  range for the argument billing payor Id. Start and end date can be
     *  null. Null values will exclude a boundary on that end.
     * @param billingPayorId
     * @param startDate Can be NULL
     * @param endDate Can be NULL
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static List<FeeScheduleAssignment> getFeeScheduleAssignmentsByBillingPayorIdDateRagne(
            Integer billingPayorId, Date startDate, Date endDate)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("BillingBL:"
                    + "getFeeScheduleAssignmentsByBillingPayorIdDateRagne:"
                    + " Received a [NULL] or invalid billingPayorId argument");
        }
        
        BillingPayor billingPayor = BillingPayorDAO.get(billingPayorId);
        
        if (billingPayor == null || billingPayor.getIdbillingPayors() == null)
        {
            throw new SQLException("BillingBL::getFeeScheduleAssignmentsByBillingPayorIdDateRagne:"
                    + " Returned a [NULL] or empty BillingPayor object");
        }
        
        return FeeScheduleAssignmentDAO.getByBillingPayorIdDateRange(
                billingPayor.getIdbillingPayors(), startDate, endDate);
    }
    
    /**
     * Returns any fee schedule assignments matching the status definition
     *  for the argument billing payor id.
     * @param billingPayorId
     * @param status
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static List<FeeScheduleAssignment> getFeeScheduleAssignmentsByBillingPayorIdStatus(
            Integer billingPayorId, Date orderDate, Status status)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("BillingBL:"
                    + "getFeeScheduleAssignmentsByBillingPayorIdStatus:"
                    + " Received a [NULL] or invalid billingPayorId argument");
        }
        
        if (status == null)
        {
            throw new IllegalArgumentException("BillingBL:"
                    + "getFeeScheduleAssignmentsByBillingPayorIdStatus"
                    + " Received a [NULL] Status object (past/active/future)");
        }
        
        
        BillingPayor billingPayor = BillingPayorDAO.get(billingPayorId);
        
        if (billingPayor == null || billingPayor.getIdbillingPayors() == null)
        {
            throw new SQLException("BillingBL::getFeeScheduleAssignmentsByBillingPayorIdStatus:"
                    + " Returned a [NULL] or empty BillingPayor object");
        }
        
        return FeeScheduleAssignmentDAO.getByBillingPayorIdStatus(
                billingPayor.getIdbillingPayors(), orderDate, status);
    }
    
    
    public static FeeScheduleAssignment getActiveFeeScheduleAssignmentByBillingPayorId(
            Integer billingPayorId, Date orderDate)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (billingPayorId == null || billingPayorId <= 0)
        {
            throw new IllegalArgumentException("BillingBL:"
                    + "getActiveFeeScheduleAssignmentByBillingPayorId:"
                    + " Received a [NULL] or invalid billingPayorId argument");
        }

        BillingPayor billingPayor = BillingPayorDAO.get(billingPayorId);
        
        if (billingPayor == null || billingPayor.getIdbillingPayors() == null)
        {
            throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentByBillingPayorId:"
                    + " Returned a [NULL] or empty BillingPayor object");
        }
        
        List<FeeScheduleAssignment> feeScheduleAssignments = 
                FeeScheduleAssignmentDAO.getByBillingPayorIdStatus(
                        billingPayor.getIdbillingPayors(), orderDate, Status.ACTIVE);    
        
        if (feeScheduleAssignments.isEmpty()) return null;
        
        if (feeScheduleAssignments.size() > 1)
        {
            throw new SQLException("BillingBL::getActiveFeeScheduleAssignmentByBillingPayorId"
                    + ": Returned more than one active fee schedule"
                    + " for billingPayorId " + billingPayorId.toString());
        }
        
        return feeScheduleAssignments.get(0);
    }
    
    /**
     * Given an orderId, returns a list of test contexts that represents
     *  the current valid ordered tests (not deleted, not invalidated)
     * 
     *  Will optionally exclude any tests or children of tests supplied in the
     *  excludeTestsAndChildren list (using test numbers)
     * 
     * @param orderId
     * @param excludeTestsAndChildren If supplied, contexts are not returned for the test number and any child tests
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<TestContext> buildTestContextsForOrderId(Integer orderId, List<Integer> excludeTestsAndChildren)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::buildTestContextsForOrderId:"
                    + " Received a [NULL] or invalid orderId argument");
        }
        
        if (excludeTestsAndChildren == null) excludeTestsAndChildren = new LinkedList<>();
        
        // Call proc to get contexts
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        List<TestContext> testContexts = new ArrayList<>();
        String sql = "CALL GetOrderTestContexts(" + orderId.toString() + ");";
        
        try
        {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                Integer batteryNumber = rs.getInt("batteryNumber");
                if (rs.wasNull()) batteryNumber = null;
                Integer panelNumber = rs.getInt("panelNumber");
                if (rs.wasNull()) panelNumber = null;
                Integer testNumber = rs.getInt("testNumber");
                if (rs.wasNull()) testNumber = null;
                
                boolean add = true;
                for (Integer excludeTestNumber : excludeTestsAndChildren)
                {
                    if (batteryNumber != null && batteryNumber.equals(excludeTestNumber))
                    {
                        add = false;
                        break;
                    }
                    
                    if (panelNumber != null && panelNumber.equals(excludeTestNumber))
                    {
                        add = false;
                        break;
                    }
                    
                    if (testNumber != null && testNumber.equals(excludeTestNumber))
                    {
                        add = false;
                        break;
                    }
                }
                
                if (add)
                {
                    testContexts.add(new TestContext(batteryNumber, panelNumber, testNumber));
                }
            }
            
            pStmt.close();
        }
        catch (SQLException ex)
        {
            String errorMessage = "BillingBL::buildTestContextsForOrderId:"
                    + " Error running sql: " + sql + ". " + ex.getMessage();
            throw new SQLException(errorMessage);
        }
        return testContexts;
    }
    
    /**
     * Given an orderId, builds orderedTests objects based on the result lines.
     *  ExcludeTestsAndChildren is an optional list of test numbers to not include
     *  in the results (e.g. POC test number).
     * 
     * @param orderId
     * @param excludeTestsAndChildren If supplied, contexts are not returned for the test number and any child tests
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static List<OrderedTest> buildOrderedTests(Integer orderId, List<Integer> excludeTestsAndChildren)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::buildOrderedTests:"
                    + " Received a [NULL] or invalid orderId argument");
        }
        
        if (excludeTestsAndChildren == null) excludeTestsAndChildren = new LinkedList<>();
        
        // Call proc to get contexts
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        List<OrderedTest> orderedTests = new ArrayList<>();
        String sql = "CALL GetOrderTestContexts(" + orderId.toString() + ");";
        
        try
        {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next())
            {
                Integer resultId = rs.getInt("resultId");
                if (rs.wasNull()) resultId = null;
                Integer batteryNumber = rs.getInt("batteryNumber");
                if (rs.wasNull()) batteryNumber = null;
                Integer panelNumber = rs.getInt("panelNumber");
                if (rs.wasNull()) panelNumber = null;
                Integer testNumber = rs.getInt("testNumber");
                if (rs.wasNull()) testNumber = null;
                
                boolean add = true;
                for (Integer excludeTestNumber : excludeTestsAndChildren)
                {
                    if (excludeTestNumber != null && excludeTestNumber > 0)
                    {
                        if (batteryNumber != null && batteryNumber.equals(excludeTestNumber))
                        {
                            add = false;
                            break;
                        }

                        if (panelNumber != null && panelNumber.equals(excludeTestNumber))
                        {
                            add = false;
                            break;
                        }

                        if (testNumber != null && testNumber.equals(excludeTestNumber))
                        {
                            add = false;
                            break;
                        }
                    }
                }
                
                if (add)
                {
                    OrderedTest orderedTest = new OrderedTest();
                    orderedTest.setOrderId(orderId);
                    orderedTest.setResultId(resultId);
                    orderedTest.setBatteryNumber(batteryNumber);
                    orderedTest.setPanelNumber(panelNumber);
                    orderedTest.setTestNumber(testNumber);
                    
                    orderedTests.add(orderedTest);
                }
            }
            
            pStmt.close();
        }
        catch (SQLException ex)
        {
            String errorMessage = "BillingBL::buildOrderedTests:"
                    + " Error running sql: " + sql + ". " + ex.getMessage();
            throw new SQLException(errorMessage);
        }
        return orderedTests;
    }
    
    public static List<Integer> getClientBillingInsuranceIds()
            throws SQLException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT idinsurances FROM insurances i "
                + "INNER JOIN insuranceSubmissionTypes ist ON i.insuranceSubmissionTypeId = ist.idInsuranceSubmissionTypes"
                + " WHERE ist.systemName = 'client'";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<Integer> clientBillingInsuranceIds = new LinkedList<>();
        while (rs.next())
        {
            clientBillingInsuranceIds.add(rs.getInt(1));
        }
        pStmt.close();
        return clientBillingInsuranceIds;
    }
    
    
    public static List<Integer> getPatientBillingInsuranceIds()
            throws SQLException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT idinsurances FROM insurances i "
                + "INNER JOIN insuranceSubmissionTypes ist ON i.insuranceSubmissionTypeId = ist.idInsuranceSubmissionTypes"
                + " WHERE ist.systemName = 'patient'";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<Integer> patientBillingInsuranceIds = new LinkedList<>();
        while (rs.next())
        {
            patientBillingInsuranceIds.add(rs.getInt(1));
        }
        pStmt.close();
        return patientBillingInsuranceIds;
    }

    /**
     * Gets the detail insurance line for the order that is marked rank 1.
     *  Rank 1 implies that it is the insurance currently being used by the
     *  order at that time.
     * 
     * If no such record exists, returns [NULL].
     * @param detailOrderId
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException
     * @throws NullPointerException 
     */
    public static DetailInsurance getRankOneInsurance(Integer detailOrderId)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (detailOrderId == null)
        {
            throw new IllegalArgumentException("BillingBL::geRankOneInsurance:"
                    + " Received a [NULL] detailOrderId Integer argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM cssbilling.detailInsurances di WHERE detailOrderId = ? AND rank = 1";
        
        DetailInsurance detailInsurance = null;
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            pStmt.setInt(1, detailOrderId);            
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                detailInsurance = DetailInsuranceDAO.ObjectFromResultSet(rs);
            }
        }
        return detailInsurance;
    }
    
    /**
     * Looks at the billing insuranceDetails records and only returns
     *  distinct insurance objects for which there is at least one row using the
     *  provided insurance / submission type and submission mode.
     * 
     * SearchType is required, but if a [NULL] reference is passed for any other
     *  argument, it is not considered in the search.
     * 
     * @param insuranceTypeId
     * @param insuranceSubmissionTypeId
     * @param insuranceSubmissionModeId
     * @param searchType
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Collection<Insurances> getInsurancesUsingSubmissionSettings(
            InsuranceDAO.SearchType searchType,
            Integer insuranceTypeId,
            Integer insuranceSubmissionTypeId,
            Integer insuranceSubmissionModeId
            )
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (searchType == null)
        {
            throw new IllegalArgumentException("BillingBL::getInsurancesUsingSubmissionSettings:"
                    + " Received a [NULL] InsuranceDAO.SearchType argument! (e.g. ACTIVE_ONLY, ALL)");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String billingSchema = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema();
        String cssSchema = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema();
        
        
        String sql = "SELECT i.* FROM " + cssSchema + ".insurances i"
                + " INNER JOIN " + billingSchema + ".detailInsurances di ON i.idinsurances = di.insuranceId"
                + " INNER JOIN " + billingSchema + ".detailOrders do ON di.detailOrderId = do.idDetailOrders"
                + " WHERE do.active = 1";
        
        if (searchType.equals(InsuranceDAO.SearchType.ACTIVE_ONLY))
        {
            sql += " AND i.active = b'1'";
        }
        
        if (searchType.equals(InsuranceDAO.SearchType.INACTIVE_ONLY))
        {
            sql += " AND i.active = b'0'";
        }
        
        if (insuranceTypeId != null && insuranceTypeId > 0)
        {
            sql += " AND di.insuranceTypeId = " + insuranceTypeId.toString();
        }
        
        if (insuranceSubmissionTypeId != null && insuranceSubmissionTypeId > 0)
        {
            sql += " AND di.insuranceSubmissionTypeId = " + insuranceSubmissionTypeId.toString();
        }
        
        if (insuranceSubmissionModeId != null && insuranceSubmissionModeId > 0)
        {
            sql += " AND di.insuranceSubmissionModeId = " + insuranceSubmissionModeId.toString();
        }
        
        sql += " GROUP BY i.idinsurances ORDER BY i.`name` ASC";
        
        List<Insurances> insurances = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                insurances.add(InsuranceDAO.SetInsuranceFromResultSet(rs));
            }
        }
        return insurances;
    }
    
    /**
     * Returns all of the events associated with this order chronologically
     * 
     * @param detailOrderId
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static Collection<DetailOrderEvent> getDetailOrderEvents(Integer detailOrderId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (detailOrderId == null)
        {
            throw new IllegalArgumentException("BillingBL::getDetailOrderEvents:"
                    + " Received a [NULL] detailOrderId argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT doe.* FROM"
                + " " + DetailOrderEventDAO.table + " doe"
                + " INNER JOIN " + EventDAO.table + " e ON doe.eventId = e.idEvents"
                + " WHERE detailOrderId = ?"
                + " ORDER BY e.`date` ASC";
        
        List<DetailOrderEvent> detailOrderEvents = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            pStmt.setInt(1, detailOrderId);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                detailOrderEvents.add(DetailOrderEventDAO.ObjectFromResultSet(rs));
            }
        }
        
        return detailOrderEvents;
    }
    
    
    public static Collection<DiagnosisCodes> getDiagnosisCodesForDetailCptCodeId(Integer detailCptCodeId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (detailCptCodeId == null)
        {
            throw new IllegalArgumentException("BillingBL::getDiagnosisCodesForDetailCptCodeId:"
                    + " Received a [NULL] detailCptCodeId Integer argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String mainSchema = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema();
        String sql = "SELECT d.* FROM " + DetailDiagnosisCodeDAO.table + " ddc"
                + " INNER JOIN " + mainSchema + "." + DiagnosisCodeDAO.table + " d"
                + " ON ddc.diagnosisCodeId = d.idDiagnosisCodes WHERE detailCptCodeId = ?"
                + " ORDER BY ddc.detailCptCodeId ASC";
        
        Collection<DiagnosisCodes> diagnosisCodes = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            pStmt.setInt(1, detailCptCodeId);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                diagnosisCodes.add(DiagnosisCodeDAO.setDiagnosisCodesFromResultset(new DiagnosisCodes(), rs));
            }
        }
        
        return diagnosisCodes;
    }
    
    /**
     * Returns any currently active fee schedule (or future fee schedule if boolean
     *  argument is true) where the provided container test is broken down.
     * 
     * If the provided test is not a container (panel/battery), or if there is
     *  no test with that number, returns an empty collection.
     * 
     * @param testNumber
     * @param includeFutureFeeSchedules Schedules that have a start date in the future
     * @return 
     */
    public static Collection<FeeSchedule> getFeeSchedulesWhereBrokenDown(Integer testNumber,
            boolean includeFutureFeeSchedules)
            throws SQLException, NullPointerException, SQLException
    {
        if (testNumber == null)
        {
            throw new IllegalArgumentException("BillingBL::getFeeSchedulesWhereBrokenDown:"
                    + " Received a [NULL] testNumber Integer argument");
        }
        Collection<FeeSchedule> feeSchedules = new ArrayList<>();
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestOrCalcByNumber(testNumber);
        
        // If not valid test or not panel, return
        if (test == null || test.getIdtests() == null || test.getIdtests().equals(0)) return feeSchedules;
        if (test.getTestType() != 0) return feeSchedules;
        
        boolean isBattery = test.getHeader();
        
        Collection<FeeScheduleAssignment> assignments = FeeScheduleAssignmentDAO.getByStatus(Status.ACTIVE);
        if (includeFutureFeeSchedules)
        {
            assignments.addAll(FeeScheduleAssignmentDAO.getByStatus(Status.FUTURE));
        }
        
        // Distinct fee schedules
        Set<Integer> feeScheduleIds = new HashSet<>();
        for (FeeScheduleAssignment assignment : assignments)
        {
            feeScheduleIds.add(assignment.getFeeScheduleId());
        }
                
        // Get the id for the "break down panel" action
        FeeScheduleAction action = FeeScheduleActionDAO.getBySystemNameTypeName(
                FeeScheduleAction.Action.BREAK_DOWN, FeeScheduleAction.TypeName.TEST);
        
        for (Integer feeScheduleId : feeScheduleIds)
        {
            // Get the panel header line
            FeeScheduleTestLookup testLookup = 
                    FeeScheduleTestLookupDAO.getByFeeScheduleTestContext(feeScheduleId,
                    (isBattery ? testNumber : null),
                    (isBattery == false ? testNumber : null),
                    null);
            
            // The panel is being broken down in this fee schedule; add to list
            if (testLookup != null &&
                    testLookup.getFeeScheduleActionId() != null &&
                    testLookup.getFeeScheduleActionId().equals(action.getIdFeeScheduleActions()))
            {
                feeSchedules.add(FeeScheduleDAO.get(feeScheduleId));
            }
        }
        
        return feeSchedules;
    }
    
    /**
     * Returns true if the LIS order id has at least one "orderedTest" row.
     *  This is generated when the order is saved and is necessary for billing
     *  work. This can be checked right before daily processing is run
     *  to auto-generate this data if it does not exist
     * @param orderId
     * @return 
     */
    public static boolean hasOrderedTestsRows(Integer orderId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (orderId == null || orderId <=0) throw new IllegalArgumentException(
                "BillingBL::hasOrderedTestsRows: Supplied LIS orderId was [NULL] or invalid");

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT COUNT(*) AS rowCount FROM "
                + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()
                + ".orderedTests WHERE orderId = ?";
        
        boolean hasRows = false;
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            pStmt.setInt(1, orderId);
            
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                hasRows = rs.getInt("rowCount") > 0;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("BillingBL::hasOrderedTestRows: Could not determine"
                    + " if rows exist for LIS orderId " + orderId.toString());
        }
        
        return hasRows;
    }
    
    /**
     * Returns true if the LIS order id has at least one "orderedTest" row.
     *  This is generated when the order is saved and is necessary for billing
     *  work. This can be checked right before daily processing is run
     *  to auto-generate this data if it does not exist
     * @param orderId
     * @return 
     */
    public static boolean hasOrderCptRows(Integer orderId)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (orderId == null || orderId <=0) throw new IllegalArgumentException(
                "BillingBL::hasOrderCptRows: Supplied LIS orderId was [NULL] or invalid");

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        // This query takes 15-30 seconds to run and, on rare occassion, times out for large tables
//        String sql = "SELECT COUNT(*) AS rowCount FROM "
//                + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()
//                + ".orderCptCodes WHERE orderedTestId IN (SELECT idorderedTests FROM orderedTests where orderId = ?)";
        
        String sql = "SELECT COUNT(*) AS rowCount FROM "
                + DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema()
                + ".orderCptCodes occ LEFT JOIN orderedTests ot ON ot.idorderedTests = occ.orderedTestId "
                + "WHERE ot.orderId = ?;";
        
        boolean hasRows = false;
        try (PreparedStatement pStmt = con.prepareCall(sql))
        {
            pStmt.setInt(1, orderId);
            
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                hasRows = rs.getInt("rowCount") > 0;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("BillingBL::hasOrderedTestRows: Could not determine"
                    + " if rows exist for LIS orderId " + orderId.toString());
        }
        
        return hasRows;
    }
    
    public static  Collection<RemitInfo> getUnprocessedRemitInfo()
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        return getUnprocessedRemitInfo(con, false);
    }
    
    
    public static  Collection<RemitInfo> getUnprocessedRemitInfo(Connection con, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("BillingBL::getUnprocessedRemitInfo:"
                    + " Received a [NULL] or invalid Connection object");
        }
        
        String sql = "SELECT * FROM " + RemitInfoDAO.table + " WHERE processed IS NULL";
        if (forUpdate) sql += " FOR UPDATE";
        
        List<RemitInfo> remitInfoRows = new ArrayList<>();
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                remitInfoRows.add(RemitInfoDAO.ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = ExceptionUtil.getMessage(
                    "BillingBL::getUnprocessedRemitInfo: Encountered error", ex);
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }
        return remitInfoRows;
    }
    
    public static  Collection<RemitDetail> getRemitDetailsForRemitInfo(Integer remitInfoId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (remitInfoId == null || remitInfoId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::getRemitDetailsForRemitInfo"
                    + " Received a [NULL] or invalid remitInfoId Integer argument");
        }
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        return getRemitDetailsForRemitInfo(con, remitInfoId, false);
    }
    
    public static Collection<RemitDetail> getRemitDetailsForRemitInfo(
            Connection con, Integer remitInfoId, boolean forUpdate)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (con == null || false == con.isValid(2))
        {
            throw new IllegalArgumentException("BillingBL::getRemitDetailsForRemitInfo:"
                    + " Received a [NULL] or invalid Connection object argument");
        }
        
        if (remitInfoId == null || remitInfoId <= 0)
        {
            throw new IllegalArgumentException("BillingBL::getRemitDetailsForRemitInfo"
                    + " Received a [NULL] or invalid remitInfoId Integer argument");
        }
        
        String sql = "SELECT * FROM " + RemitDetailDAO.table + " WHERE idRemitInfo = " + remitInfoId.toString();
        
        List<RemitDetail> remitDetails = new ArrayList<>();
        
        try (PreparedStatement pStmt = con.prepareStatement(sql))
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                remitDetails.add(RemitDetailDAO.ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            String errorMessage = ExceptionUtil.getMessage(
                    "BillingBL::getRemitDetailsForRemitInfo: Encountered error"
                            + "retrieving lines for remitInfoId " + remitInfoId.toString(), ex);
            System.out.println(errorMessage);
            throw new SQLException(errorMessage);
        }
        return remitDetails;
    }
    
    
    // Everything should originate with the RMI call.
    
    // The RMI should wrap everything in a transaction and call the appropriate
    // methods to create the event, apply the adjustment, log everything...
    
    // DONE - Atomic adjustments will not be wrapped in transactions.
    
    // DONE - The simple adjustment application-per-line should be used by all three methods
    // This should add to the ledger automatically, but we need to be able to 
    // include the ID (detailOrderId or detailCptCodeId) in there, which we should
    // have.
    
    // The auto-adjust strategies should take an input that describes the
    // adjustment amount (positive or negative), the strategy, and the things
    // to apply it to: either a detailOrderId by itself, if there's no rows
    // being excluded from the application, or a detailOrderId + a list of detailCptCodeIds
    // to directly apply it to. 
    
    // Method to apply adjustment to lines on a single order
    // if user-selected, requires a map of cpt line --> adjustment amount
    // if auto-apply (either one), requires a map of cpt lines to limit to (if limiting)
    
    // Method to apply adjustment to lines across multiple orders
    // If user-selected, requires map of order --> cpt line --> adjustment amount
    // If auto-apply (either one), requires a map of order --> cpt lines (if limiting) --> adjustment amount
    
    
    // RMI calls:
    // apply adjustment: amount, target (accession/dateOfService OR LIS order Id)
    
    // UserId
    // AdjustmentAmount,
    
}
