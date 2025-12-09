package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.FeeScheduleTestLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FeeScheduleTestLookupDAO implements IStructureCheckable
{

    private static final String table = "`feeScheduleTestLookup`";

    public static FeeScheduleTestLookup insert(FeeScheduleTestLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::Insert: Received a NULL FeeScheduleTestLookup object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  feeScheduleId,"
                + "  batteryNumber,"
                + "  panelNumber,"
                + "  testNumber,"
                + "  testAlias,"
                + "  feeScheduleActionId"
                + ")"
                + "VALUES (?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBatteryNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPanelNumber());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getTestAlias());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleActionId());

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
                throw new NullPointerException("FeeScheduleTestLookupDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdFeeScheduleTestLookup(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(FeeScheduleTestLookup obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::Update: Received a NULL FeeScheduleTestLookup object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " feeScheduleId = ?,"
                + " batteryNumber = ?,"
                + " panelNumber = ?,"
                + " testNumber = ?,"
                + " testAlias = ?,"
                + " feeScheduleActionId = ?"
                + " WHERE idFeeScheduleTestLookup = " + obj.getIdFeeScheduleTestLookup();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBatteryNumber());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getPanelNumber());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getTestNumber());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getTestAlias());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getFeeScheduleActionId());
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

    /**
     * Deletes the row from the database - NOTE: log the changes before calling
     *  this method!
     * 
     * @param feeScheduleTestLookupId
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static void delete(Integer feeScheduleTestLookupId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (feeScheduleTestLookupId == null)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::dekete: Received a NULL feeScheduleTestLookupId.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "DELETE FROM " + table + " WHERE idFeeScheduleTestLookup = ?";

        String sqlOutput = sql;
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, feeScheduleTestLookupId);
        sqlOutput = pStmt.toString();
        try
        {
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        finally
        {
            if (pStmt != null) pStmt.close();
        }
    }
    
    public static FeeScheduleTestLookup get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::Get: Received a NULL or empty FeeScheduleId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idFeeScheduleTestLookup = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        FeeScheduleTestLookup obj = null;
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

    public static FeeScheduleTestLookup getByFeeScheduleTestContext(
            Integer feeScheduleId,
            Integer batteryNumber,
            Integer panelNumber,
            Integer testNumber) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (feeScheduleId == null || feeScheduleId <= 0)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::getByFeeScheduleTest: "
                    + "Received a NULL or empty feeScheduleId.");
        }

        // There needs to be one piece of information to identify this test
        if (batteryNumber == null && panelNumber == null && testNumber == null)
        {
            throw new IllegalArgumentException("FeeScheduleTestLookupDAO::getByFeeScheduleTest: "
                    + "Received NULL batteryNumber, panelNumber, and testNumber");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE feeScheduleId = " + String.valueOf(feeScheduleId)
                + " AND batteryNumber " + (batteryNumber == null ? " IS NULL " : " = ? ")
                + " AND panelNumber " + (panelNumber == null ? " IS NULL " : " = ? ")
                + " AND testNumber " + (testNumber == null ? " IS NULL " : " = ? ");

        PreparedStatement pStmt = con.prepareStatement(sql);

        int i = 0;

        if (batteryNumber != null)
        {
            pStmt.setInt(++i, batteryNumber);
        }

        if (panelNumber != null)
        {
            pStmt.setInt(++i, panelNumber);
        }

        if (testNumber != null)
        {
            pStmt.setInt(++i, testNumber);
        }

        FeeScheduleTestLookup obj = null;
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
    
    private static FeeScheduleTestLookup ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        FeeScheduleTestLookup obj = new FeeScheduleTestLookup();
        obj.setIdFeeScheduleTestLookup(rs.getInt("idFeeScheduleTestLookup"));
        if (rs.getInt("feeScheduleId") > 0)
        {
            obj.setFeeScheduleId(rs.getInt("feeScheduleId"));
        }
        if (rs.getInt("batteryNumber") > 0)
        {
            obj.setBatteryNumber(rs.getInt("batteryNumber"));
        }
        if (rs.getInt("panelNumber") > 0)
        {
            obj.setPanelNumber(rs.getInt("panelNumber"));
        }
        if (rs.getInt("testNumber") > 0)
        {
            obj.setTestNumber(rs.getInt("testNumber"));
        }
        obj.setTestAlias(rs.getString("testAlias"));
        if (rs.getInt("feeScheduleActionId") > 0)
        {
            obj.setFeeScheduleActionId(rs.getInt("feeScheduleActionId"));
        }

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `feeScheduleTestLookup`.`idFeeScheduleTestLookup`,\n"
                + "    `feeScheduleTestLookup`.`feeScheduleId`,\n"
                + "    `feeScheduleTestLookup`.`batteryNumber`,\n"
                + "    `feeScheduleTestLookup`.`panelNumber`,\n"
                + "    `feeScheduleTestLookup`.`testNumber`,\n"
                + "    `feeScheduleTestLookup`.`testAlias`,\n"
                + "    `feeScheduleTestLookup`.`feeScheduleActionId`\n"
                + "FROM `css`.`feeScheduleTestLookup` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
