package BL;

import DAOS.AOEAnswersDAO;
import DAOS.AOEGroupingTypeDAO;
import DAOS.AOEOrderStatusDAO;
import DAOS.AOEQuestionDAO;
import DAOS.AOETestsDAO;
import DAOS.DepartmentDAO;
import DAOS.ReferenceLabApprovalLogDAO;
import DOS.AOEAnswers;
import DOS.AOEGroupingType;
import DOS.AOEOrderStatus;
import DOS.AOEQuestion;
import DOS.AOETests;
import DOS.Departments;
import DOS.ReferenceLabSettings;
import DOS.Tests;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author TomR
 */

public class AOEBL
{

    
    public AOEBL() {}
    
    /*
    public ArrayList<AOEQuestion> GetAOEQuestionsByTestID(int idTests)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        // TODO: added just to make this thing compile
        return new ArrayList<AOEQuestion>();
        //ArrayList<AOEQuestion, AOEAnswers> results = new HashMap<>();
    }*/
    
    /**
     * Returns distinct testNumbers that are associated with one more more
     *  send-out questions.
     * @param idOrders
     * @return 
     */
    public ArrayList<Integer> GetSendOutTestNumbersByOrderID(int idOrders)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String query = "SELECT DISTINCT "
            + " t.number"
            + " FROM results r"
            + " INNER JOIN tests t on r.testId = t.idtests "
            + " INNER JOIN aoeTests a on t.number = a.testNumber"
            + " WHERE t.active = 1 AND orderId = " + idOrders;
        
        AOETestsDAO aoetestsdao = new AOETestsDAO();
        ArrayList<Integer> testNumbers = new ArrayList<>();
        int testNumber;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                testNumbers.add(rs.getInt("number"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::GetAOETestsByOrderID: Unable to " +
                    "retrieve list of test numbers for orderID : " +
                    idOrders);
            return null;
        }
        return testNumbers;
    }
    
/**
     * Returns distinct testIds that are associated with one more more
     *  send-out questions.
     * @param idOrders
     * @return 
     */
    public ArrayList<Integer> GetSendOutTestIDsByOrderID(int idOrders)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        /*
        String query = "SELECT DISTINCT "
            + " r.testId"
            + " FROM results r"
            + " INNER JOIN aoeTests a on r.testId = a.idtests"
            + " WHERE orderId = " + idOrders;*/
        
        String query = "SELECT DISTINCT "
            + " r.testId"
            + " FROM results r"
            + " INNER JOIN orders o on r.orderId = o.idOrders"
            + " INNER JOIN tests t on r.testId = t.idtests"
            + " INNER JOIN departments d on t.department = d.idDepartment"
            + " WHERE d.ReferenceLab = 1 AND d.testXref IS NOT NULL AND o.idorders = " + idOrders;
        
        AOETestsDAO aoetestsdao = new AOETestsDAO();
        ArrayList<Integer> testIds = new ArrayList<>();
        int idTests;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                testIds.add(rs.getInt("testId"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::GetAOETestsByOrderID: Unable to " +
                    "retrieve list of AOETests objects for orderID : " +
                    idOrders);
            return null;
        }
        return testIds;
    }
        
    
    public ArrayList<AOETests> GetAOETestsByOrderID(int idOrders)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        String query = "SELECT DISTINCT "
            + " t.number "
            + " FROM results r"
            + " INNER JOIN tests t ON r.testId = t.testID"
            + " INNER JOIN aoeTests a on t.number = a.testNumber"
            + " WHERE t.active = 1 AND orderId = " + idOrders;
        
        AOETestsDAO aoetestsdao = new AOETestsDAO();
        ArrayList<AOETests> aoeTests = new ArrayList<>();
        int idTests;
        try
        {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                idTests = rs.getInt("number");
                aoeTests.addAll(aoetestsdao.GetAOETestsByTestNumber(idTests));                
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::GetAOETestsByOrderID: Unable to " +
                    "retrieve list of AOETests objects for orderID : " +
                    idOrders);
            return null;
        }
        return aoeTests;
        
    }
    
    /**
     * Return a mapping of questions and answers for the supplied order ID, using
     *  the test IDs found on the results table for the order.
     *   If there isn't a saved answer for the question, the AOEAnswers value
     *   will be null.
     * @param idOrders
     * @return 
     */
    public HashMap<AOEQuestion,AOEAnswers> GetAOEQuestionsAndAnswersByOrderID(int idOrders)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        HashMap<AOEQuestion, AOEAnswers> results = new HashMap<>();
        try {
            
            String query =                     
                "SELECT " +
                "q.id AS 'questionId', " +
                "a.id AS 'answerId' " +
                "FROM `aoeQuestions` q " +
                "INNER JOIN `aoeTests` at on at.questionId = q.id " +
                "INNER JOIN `tests` t on at.testNumber = t.number " +
                "INNER JOIN `results` r on t.idtests = r.testId " +                
                "LEFT OUTER JOIN `aoeAnswers` a on q.id = a.questionId " + 
                    "and a.idorders = r.orderId " +
                "WHERE t.active = 1 AND r.orderId = " + idOrders;
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            AOEQuestionDAO aoequestiondao = new AOEQuestionDAO();
            AOEAnswersDAO aoeanswerdao = new AOEAnswersDAO();
            AOEQuestion question;
            AOEAnswers answer;
            while (rs.next())
            {                
                question = aoequestiondao.GetByQuestionID(rs.getInt("questionId"));
                
                // Check if there's a valid answer for this question
                if (rs.getInt("answerId") != 0)
                {
                    answer = aoeanswerdao.GetById(rs.getInt("answerId"));

                    // Map answer to question
                    results.put(question,answer);
                }
                else
                {
                    // The question is not mapped to an answer
                    results.put(question, null);
                }                
            }            
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to obtain AOE " + 
                    "questions/answers for idOrders " + idOrders);
            return null;            
        }
        return results;
    }
    
    /**
     * Provides a unique list of reference lab departments associated with the tests for
     *   the supplied order Id.
     * @param idOrders Unique Id for order
     * @return ArrayList of Departments objects
     */
    public ArrayList<Departments> GetReferenceLabDepartmentsFromOrderId(int idOrders)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        TestBL testbl = new TestBL();
        DepartmentDAO departmentdao = new DepartmentDAO();
        
        ArrayList<Tests> allTests = testbl.GetAllTestsForOrder(idOrders);
        HashMap<Integer,Departments> hashDepartments = new HashMap<>();
        ArrayList<Departments> departments = new ArrayList<>();
        Departments currentDepartment;
        try
        {
            for (Tests t : allTests)
            {
                currentDepartment = departmentdao.GetDepartmentByID(t.getDepartment());
                
                // If it's a reference lab and isn't already in our list, add it.
                if (currentDepartment != null
                    && currentDepartment.isReferenceLab()
                    && currentDepartment.getTestXref() != null
                    && !hashDepartments.containsKey(currentDepartment.getIdDepartment()))
                {
                    hashDepartments.put(currentDepartment.getIdDepartment(),
                            currentDepartment);
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error attempting to check the reference lab " +
                    "departments for idOrders " + idOrders);
            return null;
        }
        
        if (hashDepartments.size() > 0)
        {
            Set<Integer> keys = hashDepartments.keySet();
            for (Integer id : keys)
            {
                departments.add(hashDepartments.get(id));
            }
        }
        return departments;
    }
    
    public AOEGroupingType GetGroupingTypeByQuestionId(int questionId)
    {
        AOEQuestionDAO aoequestionsdao = new AOEQuestionDAO();        
        AOEQuestion question = aoequestionsdao.GetByQuestionID(questionId);        
        AOEGroupingTypeDAO aoegroupingtypedao = new AOEGroupingTypeDAO();
        AOEGroupingType aoeGroupingType = null;
        if (question != null)
        {
            aoeGroupingType = aoegroupingtypedao.GetByID(question.getAoeGroupingTypeId());
        }
        
        return aoeGroupingType;        
    
    }
    
    /**
     * Gets a mapping of the reference lab department number and order
     *  status for the supplied order id
     * @param idorders
     * @return HashMap< DepartmentNumber, AOEOrderStatus>
     */
    public HashMap<Integer, AOEOrderStatus> GetAOEDepartmentOrderStatusByOrderId(int idorders)
    {
        ArrayList<Departments> departments = GetReferenceLabDepartmentsFromOrderId(idorders);
        AOEOrderStatusDAO aoeorderstatusdao = new AOEOrderStatusDAO();
        HashMap<Integer, AOEOrderStatus> results = new HashMap<>();
        for (Departments department : departments)
        {
            AOEOrderStatus orderStatus =
                    aoeorderstatusdao.GetByOrderIDDepartmentID(
                            idorders, department.getDeptNo());
            if (orderStatus != null)
            {
                results.put(department.getDeptNo(), orderStatus);
            }            
        }
        return results;
    }
    
    /**
     * Returns a list of reference lab departments. Does not check
     *  whether the client has an interface to those send out labs.
     * @return 
     */
    public ArrayList<Departments> GetAllReferenceLabDepartments()
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        ArrayList<Departments> departments = new ArrayList<>();        
        try
        {
            String query = 
                "SELECT"
                + " idDepartment FROM departments "
                + " WHERE ReferenceLab = 1 and testXref IS NOT NULL";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            DepartmentDAO departmentsdao = new DepartmentDAO();
            
            while (rs.next())
            {                
                departments.add(departmentsdao.GetDepartmentByID(rs.getInt("idDepartment")));      
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::GetAllReferenceLabDepartments : "
                    + " Unable to obtain reference lab departments ");
            return null;            
        }
        return departments;
    }
    
    /**
     *  Gets a hashset of department numbers that are marked as send-out and
     *   have an interface defined (testXref row)
     * @return 
     */
    public HashSet<Integer> GetAllReferenceLabDepartmentNumbers()
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        HashSet<Integer> departmentNumbers = new HashSet<>();
        try
        {
            String query = 
                "SELECT"
                + " idDepartment FROM departments "
                + " WHERE ReferenceLab = 1 and testXref IS NOT NULL";
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                departmentNumbers.add(rs.getInt("idDepartment"));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::GetAllReferenceLabDepartments : "
                    + " Unable to obtain reference lab departments ");
            return null;            
        }
        return departmentNumbers;        
    }
    
    
    /**
     * Flags the rows in the particular sendoutqueue table as transReady = true
     *  for the supplied order Id and department number. Must perform all validation before
     *  this method is called.
     * @param idOrders
     * @param departmentNumber
     * @return 
     */
    public boolean ApproveSendoutQueueRows(int idOrders, int departmentNumber)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            String query = 
                "UPDATE sendoutqueue_" + departmentNumber
                + " SET transReady = 1 WHERE "
                + " idOrders = " + idOrders;

            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            pStmt.close();
            
            query = 
                "UPDATE aoeOrderStatus"
                + " SET approved = 1 WHERE orderId = " + idOrders
                + " AND sendoutDepartmentNo = " + departmentNumber;
            
            pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::ApproveSendoutQueueRows : "
                    + " Unable to set sendoutqueue approval for orderId " + idOrders);
            return false;
        }
        return true;
    }
    
    public boolean ApproveSendoutQueueRows(int idOrders, int departmentNumber, Integer tubeTypeId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        try
        {
            String query = 
                "UPDATE sendoutqueue_" + departmentNumber + " sq"
                + " INNER JOIN results r ON sq.idResults = r.idResults"
                + " INNER JOIN tests t ON r.testId = t.idtests"
                + " SET transReady = 1" 
                + " WHERE sq.idOrders = " + idOrders;            
            if (tubeTypeId == 0) {
                query += " AND t.tubeTypeId IS NULL";
            } else {
                query += " AND t.tubeTypeId = " + tubeTypeId;
            }
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            pStmt.close();
            
            query = "SELECT COUNT(*) AS `OrderCount`, "
                + "SUM(sq.transReady) AS `TransReadyCount` "
                + "FROM sendoutqueue_9 sq " 
                + "WHERE sq.idOrders = " + idOrders;            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);            
            int orderCount = 0;
            int transReadyCount = 0;            
            while (rs.next()) {
                orderCount = rs.getInt("OrderCount");
                transReadyCount = rs.getInt("TransReadyCount");                
            }
            stmt.close();
            rs.close();
            
            if (orderCount == transReadyCount) {
                query = 
                "UPDATE aoeOrderStatus"
                + " SET approved = 1 WHERE orderId = " + idOrders
                + " AND sendoutDepartmentNo = " + departmentNumber;
            
                pStmt = con.prepareStatement(query);
                pStmt.executeUpdate();
                pStmt.close();
            }
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::ApproveSendoutQueueRows : "
                    + " Unable to set sendoutqueue approval for orderId " + idOrders);
            return false;
        }
        return true;
    }
    
    public void SetSendoutQueueRunNo(int deptNo, int orderId, int runNo)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        // Only update the ones that are set transReady.
        try
        {
            String query =         
                "UPDATE sendoutqueue_" + deptNo
                + " SET runNo = " + runNo
                + " WHERE transReady = 1 "
                + " AND runNo IS NULL "
                + " AND idOrders = " + orderId;            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            pStmt.close();            
        }
        catch (Exception ex)
        {
            System.out.println("AOEBL::SetSendoutQueueRunNo : "
                    + " Unable to set sendoutqueue approval for deptNo " + deptNo +
                    " and OrderId " + orderId);
        }
    }
    
    /**
     * Given a run number, resets the orders in the reference lab queue
     *  to be resent for the given department (uses ReferenceLabApprovalLog)
     * @param deptNo
     * @param runNo 
     * @return  
     */
    public ArrayList<Integer> FlagRunForResend(int deptNo, int runNo)
    {
        // Get the orders that were in the batch.
        ArrayList<Integer> resentOrderIds = new ArrayList<>();
        
        ReferenceLabApprovalLogDAO rlaldao =
                new ReferenceLabApprovalLogDAO();
        
        ArrayList<Integer> orderIdsForResend =
                rlaldao.GetOrderIdsForPrintRun(deptNo, deptNo);
        
        // For each order in the run
        for (Integer orderId : orderIdsForResend)
        {
            // If the order has been successfully re-flagged
            if (FlagOrderForResend(deptNo, orderId))
            {
                // Add it to our list of re-sent orderIds
                resentOrderIds.add(orderId);
            }
        }
        
        return resentOrderIds;
    }    
    
    /**
     * Given one or more orderIds, resets the orders in the reference lab
     *  queue to be resent for the given department.
     * @param deptNo
     * @param orderIds 
     * @return  ArrayList of orderIds of the records that have been re-flagged
     */
    public ArrayList<Integer> FlagOrdersForResend(int deptNo, ArrayList<Integer> orderIds)
    {
        ArrayList<Integer> resentOrderIds = new ArrayList<>();
        if (orderIds == null) return resentOrderIds;
        
        for (Integer orderId : orderIds)
        {
            // If this record has successfully been flagged
            if (FlagOrderForResend(deptNo, orderId))
            {
                // Add it to our list of re-sent orderIds
                resentOrderIds.add(orderId);
            }
        }
        return resentOrderIds;
    }
    
    /**
     * Given a single orderId, sets it so it will be picked up in the next
     *  send batch.
     * Returns a list of order Ids that have been reflagged for send.
     * @param deptNo
     * @param orderId
     * @return  ArrayList of orderId integers that were flagged
     */
    public boolean FlagOrderForResend(int deptNo, int orderId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        AOEOrderStatusDAO orderStatusDAO = new AOEOrderStatusDAO();
        AOEOrderStatus orderStatus = 
                orderStatusDAO.GetByOrderIDDepartmentID(orderId, deptNo);
        
        ArrayList<Integer> resentOrderIds = new ArrayList<>();
        
        // If the order status is no longer set to approved/ready, don't
        //  reflag the orders (return an empty list)
        if (!orderStatus.isApproved()) return false;
        
        // Only update the ones that are set transReady.
        try
        {
            // Only when the order status is set to ready
            String query =
                "UPDATE sendoutqueue_" + deptNo
                + " SET s.transReady = 1, s.transmitted = 0"
                + " WHERE idOrders = " + orderId;
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::SetSendoutQueueRunNo : "
                    + " Unable to set sendoutqueue approval for deptNo " + deptNo +
                    " and OrderId " + orderId);
            return false;
        }
        return true;
    }
    
    /**
     * Calls the procedure to add the sendout queue rows for the provided order,
     *  based on the reference lab settings.
     * 
     * @param settings 
     * @param orderId 
     * @return  True if successful, false otherwise
     */
    public boolean CallOrderProcedure(
            ReferenceLabSettings settings,
            int orderId)
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        
        if (settings == null)
        {
            System.out.println("AOEBL::CallOrderProcedure: " +
                    "Received NULL ReferenceLabSettings object!");
            return false;
        }
        
        Integer deptId = settings.getIdDepartment();
        if (deptId == null || deptId == 0)
        {
            System.out.println("AOEBL::CallOrderProcedure: " +
                    "Received NULL/empty idDepartment in ReferenceLabSettings object!");
            return false;
        }
        
        String orderProcedure = settings.getOrderProcedure();
        if (orderProcedure == null || orderProcedure.isEmpty())
        {
            System.out.println("AOEBL::Missing OrderProcedure " +
                    " in ReferenceLabSettings table. This should point to a " +
                    " valid stored procedure name to queue up reference lab results " +
                    " and re-flag the aoeOrderStatus table as unapproved for this department");
            return false;
        }
        
        String sql = "";
        boolean hadError = true;
        CallableStatement callable = null;
        ResultSet rs = null;
        try
        {
            sql = "CALL " + orderProcedure + "(?,?,?)";
            callable = con.prepareCall(sql);
            callable.setInt(1, orderId);
            callable.setInt(2, deptId);
            callable.registerOutParameter(3, java.sql.Types.BIT);
            
            rs = callable.executeQuery();
            if (rs.next())
            {
                // Procedure error flag
                hadError = rs.getBoolean(3);
            }
            callable.close();
            rs.close();
        }
        catch (SQLException ex)
        {
            System.out.println("AOEBL::CalOrderProcedure: " +
                    "Error running procedure: " + sql);
            try
            {
                if (callable != null) callable.close();
                if (rs != null) rs.close();
            }
            catch (SQLException ex2)
            {
                callable = null;
                rs = null;
            }
        }
        return hadError;
    }
}
