package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderedTest;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderedTestDAO implements IStructureCheckable
{

    private static final String table = "`orderedTests`";
    

    public static OrderedTest insert(OrderedTest obj) throws SQLException, IllegalArgumentException, NullPointerException
    {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }

    public static OrderedTest insert(Connection con, OrderedTest obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderedTestsDAO::Insert: Received a NULL OrderedTests object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  orderId,"
                + "  resultId,"
                + "  batteryNumber,"
                + "  panelNumber,"
                + "  testNumber,"
                + "  directlyOrdered"
                + ")"
                + "VALUES (?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBatteryNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPanelNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getDirectlyOrdered());

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
                throw new NullPointerException("OrderedTestsDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdorderedTests(newId);
            rs.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(OrderedTest obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderedTestsDAO::Update: Received a NULL OrderedTest object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " orderId = ?,"
                + " resultId = ?,"
                + " batteryNumber = ?,"
                + " panelNumber = ?,"
                + " testNumber = ?,"
                + " directlyOrdered = ?"
                + " WHERE idorderedTests = " + obj.getIdorderedTests();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getResultId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBatteryNumber());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPanelNumber());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getDirectlyOrdered());
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

    public static OrderedTest get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("OrderedTestsDAO::Get: Received a NULL or empty OrderedTest object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idorderedTests = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderedTest obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
            rs.close();
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
     * Uses the database singleton connection
     * @param orderedTestId
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void delete(Integer orderedTestId)
        throws SQLException, IllegalArgumentException, NullPointerException
    {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        delete(con, orderedTestId);
    }    
    
    /**
     * Hard-deletes an ordered test row. NOTE: cascade delete will delete
     * any corresponding orderedCptCode, orderedCptModifier, and 
     * orderedCptDiagnosisCode rows.
     * 
     * @param con
     * @param orderedTestId
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void delete(Connection con, Integer orderedTestId)
        throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderedTestId == null || orderedTestId <= 0) 
        {
            throw new IllegalArgumentException("OrderedTestDAO::delete:"
                    + " Received a [NULL] or invalid orderedTestId argument");
        }
        
        String sql = "DELETE FROM " + table + " WHERE idorderedTests = ?";
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, orderedTestId);
        
        int affected = pStmt.executeUpdate();
        
        if (affected == 0)
        {
            throw new SQLException("OrderedTestDAO::delete: Zero rows were affected"
                + " when attempting to delete " + orderedTestId.toString());
        }
        pStmt.close();        
    }
    

    public static OrderedTest GetTest(Integer orderId, Integer batteryNumber, Integer panelNumber, Integer testNumber) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException("OrderedTestsDAO::GetTest: Received a NULL or empty orderId object.");
        }
        
        if ((batteryNumber == null || batteryNumber <= 0) && (panelNumber == null || panelNumber <= 0) && (testNumber == null || testNumber <= 0))
        {
            throw new IllegalArgumentException("OrderedTestsDAO::GetTest: Received a NULL or emptry object for all test variables.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(orderId);
        if (batteryNumber != null && batteryNumber > 0)
        {
            sql += " AND batteryNumber = " + String.valueOf(batteryNumber);
        }
        else if (batteryNumber == null)
        {
            sql += " AND batteryNumber IS NULL";
        }
        if (panelNumber != null && panelNumber > 0)
        {
            sql += " AND panelNumber = " + String.valueOf(panelNumber);
        }
        else if (panelNumber == null)
        {
            sql += " AND panelNumber IS NULL";
        }
        if (testNumber != null && testNumber > 0)
        {
            sql += " AND testNumber = " + String.valueOf(testNumber);
        }
        else if (testNumber == null)
        {
            sql += " AND testNumber IS NULL";
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderedTest obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }

    public static List<OrderedTest> GetAllTests(Integer orderId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException("OrderedTestsDAO::GetTest: Received a NULL or empty orderId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(orderId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<OrderedTest> orderedTestList = new ArrayList<OrderedTest>();
        OrderedTest obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                obj = ObjectFromResultSet(rs);
                orderedTestList.add(obj);
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return orderedTestList;
    }
    
    private static OrderedTest ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderedTest obj = new OrderedTest();
        obj.setIdorderedTests(rs.getInt("idorderedTests"));
        obj.setOrderId(rs.getInt("orderId"));
        obj.setResultId(rs.getInt("resultId"));
        obj.setBatteryNumber(rs.getInt("batteryNumber"));
        obj.setPanelNumber(rs.getInt("panelNumber"));
        obj.setTestNumber(rs.getInt("testNumber"));
        obj.setDirectlyOrdered(rs.getInt("directlyOrdered"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `orderedTests`.`idorderedTests`,\n"
                + "    `orderedTests`.`orderId`,\n"
                + "    `orderedTests`.`resultId`,\n"
                + "    `orderedTests`.`batteryNumber`,\n"
                + "    `orderedTests`.`panelNumber`,\n"
                + "    `orderedTests`.`testNumber`,\n"
                + "    `orderedTests`.`directlyOrdered`\n"
                + "FROM `css`.`orderedTests` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
