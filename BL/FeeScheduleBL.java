
package BL;

import DAOS.FeeScheduleTestLookupDAO;
import DAOS.TestDAO;
import DOS.FeeSchedule;
import DOS.FeeScheduleTestLookup;
import DOS.TestContext;
import DOS.Tests;
import Database.CheckDBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.SQLException;

public class FeeScheduleBL
{
    public enum BillingType
    {
        INSURANCE(1),
        CLIENT(2),
        PATIENT(3);
        
        private final int index;
        
        BillingType(int index)
        {
            this.index = index;
        }
        
        public int getIndex()
        {
            return this.index;
        }
        
        public static BillingType getByIndex(int index)
        {
            for (BillingType type : BillingType.values())
            {
                if (type.getIndex() == index)
                {
                    return type;
                }
            }
            return null;
        }
    }
    /**
     * Based on how the order is billed (insurance, client, patient),
     *  return the currently-assigned fee schedule or NULL if there is no
     *  active fee schedule assigned.
     * 
     * @param type
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    /*
    public static FeeSchedule getActiveFeeSchedule(BillingType type, Integer identifier)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        FeeScheduleAssignment activeAssignment = getActiveFeeScheduleAssignment(type, identifier);
        if (activeAssignment != null)
        {
            return FeeScheduleDAO.get(activeAssignment.getFeeScheduleId());
        }
        return null;
    }
    */
    /**
     * Returns the fee schedule assignment currently assigned to the argument
     *  entity or NULL if there is no active fee schedule assignment
     * @param type
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    /*
    public static FeeScheduleAssignment getActiveFeeScheduleAssignment(
            BillingType type, Integer identifier)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (type == null)
        {
            throw new IllegalArgumentException("FeeScheduleBL::getActiveFeeSchedule:"
                    + " Received a NULL BillingType enum");
        }
        if (identifier == null || identifier <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleBL::getActiveFeeSchedule:"
                    + " Received a NULL or invalid identifier integer");
        }
        
        BillingPayor billingPayor = null;
        switch (type)
        {
            case INSURANCE:
                billingPayor = BillingBL.getOrCreateBillingPayorForInsuranceId(identifier);
                break;
            case CLIENT:
                billingPayor = BillingBL.getOrCreateBillingPayorForClientId(identifier);
                break;
            case PATIENT:
                billingPayor = BillingBL.getOrCreateBillingPayorForPatientId(identifier);
        }
        
        if (billingPayor == null || billingPayor.getIdbillingPayor() == null ||
                billingPayor.getIdbillingPayor() <= 0)
        {
            throw new SQLException("FeeScheduleBL::getActiveFeeScheduleAssignment"
                    + " Returned BillingPayorObject for " + type.toString()
                    + " " + identifier.toString() + " was [NULL]");
        }
        return FeeScheduleAssignmentDAO.getByBillingPayorIdStatus(
                billingPayor.getIdbillingPayor(), FeeScheduleAssignmentDAO.Status.ACTIVE).get(0);
    }
    */
    /**
     * Uses the type of test passed in to return the fee schedule test
     *  line for that row.
     * @param feeSchedule
     * @param testNumber
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static FeeScheduleTestLookup getLookupByScheduleTestnumber(FeeSchedule feeSchedule, Integer testNumber)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (feeSchedule == null || feeSchedule.getIdFeeSchedules() == null || feeSchedule.getIdFeeSchedules() <= 0)
        {
            throw new IllegalArgumentException("");
        }
        
        if (testNumber == null || testNumber <= 0)
        {
            throw new IllegalArgumentException("");
        }
        
        TestDAO tdao = new TestDAO();
        Tests test = tdao.GetTestByNumber(testNumber);
        TestContext context = new TestContext();
        if (test.getHeader())
        {
            context.setBatteryNumber(testNumber);
        }
        else if (test.getTestType() == 0)
        {
            context.setPanelNumber(testNumber);
        }
        else
        {
            context.setTestNumber(testNumber);
        }
        
        return FeeScheduleTestLookupDAO.getByFeeScheduleTestContext(
                feeSchedule.getIdFeeSchedules(),
                context.getBatteryNumber(),
                context.getPanelNumber(),
                context.getTestNumber());
    }
    
    /**
     * Returns the unique database identifier of the newly created fee schedule
     * @param sourceFeeScheduleId
     * @param newFeeScheduleAbbr
     * @param newFeeScheduleName
     * @param newFeeScheduleDescription
     * @param userId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public static Integer copyFeeSchedule(
            Integer sourceFeeScheduleId,
            String newFeeScheduleAbbr,
            String newFeeScheduleName,
            String newFeeScheduleDescription,
            Integer userId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (sourceFeeScheduleId == null || sourceFeeScheduleId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleBL::copyFeeSchedule:"
                    + " Received a [NULL] or invalid FeeScheduleId");
        }
        
        if (newFeeScheduleAbbr == null || newFeeScheduleAbbr.isEmpty())
        {
            throw new IllegalArgumentException("FeeScheduleBL::copyFeeSchedule:"
                    + " Received a [NULL] or empty fee schedule abbreviation");
        }
        
        if (newFeeScheduleName == null || newFeeScheduleName.isEmpty())
        {
            throw new IllegalArgumentException("FeeScheduleBL::copyFeeSchedule:"
                    + " Received a [NULL] or empty fee schedule name");
        }
        
        if (userId == null || userId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleBL::copyFeeSchedule:"
                    + " Received a [NULL] or invalid userId");
        }
        
        String sql = "CALL CopyFeeSchedule(?,?,?,?,?,?,?,?)";
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);        
        
        CallableStatement callable = con.prepareCall(sql);

        callable.setInt(1, sourceFeeScheduleId);
        callable.setString(2, newFeeScheduleAbbr);
        callable.setString(3, newFeeScheduleName);
        callable.setString(4, newFeeScheduleDescription);
        callable.setInt(5, userId);
        callable.registerOutParameter(6, java.sql.Types.TINYINT);
        callable.registerOutParameter(7, java.sql.Types.VARCHAR);
        callable.registerOutParameter(8, java.sql.Types.INTEGER);
        
        callable.executeQuery();
        Integer errorFlag = callable.getInt(6);
        String errorMessage = callable.getString(7);
        Integer newFeeScheduleId = callable.getInt(8);
        callable.close();
        
        if (errorFlag == 1)
        {
            String procedureCall = "[NULL]";
            try
            {
                procedureCall = callable.toString();
            }
            catch (Exception ex){}
            
            if (errorMessage == null) errorMessage = "[NULL]";
            throw new SQLException("FeeScheduleBL::copyFeeSchedule: Error"
                    + " from proc CopyFeeSchedule for call: " + procedureCall + " : " + errorMessage);
        }
        
        if (newFeeScheduleId <=0)
        {
            String procedureCall = "[NULL]";
            try
            {
                procedureCall = callable.toString();
            }
            catch (Exception ex){}            
            throw new SQLException("FeeScheduleBL::copyFeeSchedule: Procedure call "
                    + procedureCall + " did not return the unique identifier of the created fee schedule.");
        }
        
        return newFeeScheduleId;
    }
    
    /**
     * Returns a fee schedule assignment for the provide entity with a start
     *  date in the future (based on the server time); if one does not exist,
     *  returns NULL
     * 
     * @param billingType
     * @param identifier
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    /*
    public static FeeScheduleAssignment getFutureAssignment(BillingType billingType, Integer identifier)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (billingType == null) throw new  IllegalArgumentException(
                "FeeScheduleBL::getFutureAssignment: Received a NULL billingType enum argument");
        
        if (identifier == null || identifier <= 0) throw new IllegalArgumentException(
                "FeeScheduleBL::getFutureAssignment: Received a NULL or invalid identifier");
        
        String sql = "SELECT * FROM feeScheduleAssignments WHERE startDate > NOW() AND ";
        switch (billingType)
        {
            case INSURANCE:
                sql += " insuranceId = ";
                break;
            case CLIENT:
                sql += " clientId = ";
                break;
            case PATIENT:
                sql += " patientId = ";
                break;
            default:
                throw new IllegalArgumentException("FeeScheduleBL::getFutureAssignment: Received an invalid BillingType enumeration");
        }
        sql += identifier.toString();
        
        Connection conn = DatabaseSingleton.getDatabaseSingleton().getConnection(true);
        PreparedStatement pStmt = conn.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        FeeScheduleAssignment assignment = null;
        int i = 0;
        while (rs.next())
        {
            ++i;
            assignment = FeeScheduleAssignmentDAO.ObjectFromResultSet(rs);
        }
        
        if (i > 1)
        {
            throw new SQLException("FeeScheduleBL::getFutureAssignment: Billing Type "
                    + billingType.toString() + " identifier " + identifier.toString()
                    + " returned more than one future assignment!");
        }
        return assignment;
    }
    */

}
