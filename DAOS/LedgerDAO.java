package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Ledger;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LedgerDAO implements IStructureCheckable
{

    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`ledger`";

    public static Ledger insert(Ledger obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("LedgerDAO::Insert: Received a NULL Ledger object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentId,"
                + "  detailOrderId,"
                + "  detailCptCodeId,"
                + "  previousAmount,"
                + "  adjustmentAmount,"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
        pStmt.setBigDecimal(++i, obj.getPreviousAmount());
        pStmt.setBigDecimal(++i, obj.getAdjustmentAmount());

        try
        {
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
                System.out.println(ex.getMessage() + " " + pStmt.toString());
                throw ex;
        }
        return obj;
    }
    
    public static Ledger insert(Connection con, Ledger obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("LedgerDAO::Insert: Received a NULL Ledger object");
        }

        if (con.isValid(2) == false)
        {
            throw new IllegalArgumentException("LedgerDAO::Insert: Received an invalid connection object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentId,"
                + "  detailOrderId,"
                + "  detailCptCodeId,"
                + "  previousAmount,"
                + "  adjustmentAmount"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
        pStmt.setBigDecimal(++i, obj.getPreviousAmount());
        pStmt.setBigDecimal(++i, obj.getAdjustmentAmount());

        try
        {
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }
        return obj;
    }

    public static void update(Ledger obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null || obj.getIdLedger() == null || obj.getIdLedger().equals(0))
        {
            throw new IllegalArgumentException("LedgerDAO::Update:"
                    + " Received a NULL Ledger object or one with a null idLedger.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " adjustmentId = ?,"
                + " detailOrderId = ?,"
                + " detailCptCodeId = ?,"
                + " previousAmount = ?,"
                + " adjustmentAmount = ?,"
                + " WHERE idLedger = " + obj.getIdLedger().toString();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailCptCodeId());
            pStmt.setBigDecimal(++i, obj.getPreviousAmount());
            pStmt.setBigDecimal(++i, obj.getAdjustmentAmount());
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

    public static Ledger get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("LedgerDAO::Get:"
                    + " Received a NULL or empty Ledger object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idLedger = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Ledger obj = null;
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

    public static List<Ledger> getAllForDetailOrderId(Integer detailOrderId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailOrderId == null || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("LedgerDAO::getAllForDetailOrderId:"
                    + " Received a NULL or empty Ledger object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE detailOrderId = " + String.valueOf(detailOrderId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<Ledger> objList = new ArrayList<Ledger>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                Ledger obj = ObjectFromResultSet(rs);
                objList.add(obj);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return objList;
    }

    public static List<Ledger> getAllForDetailOrderIdDetailCptCodeId(Integer detailOrderId, Integer detailCptCodeId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (detailOrderId == null || detailOrderId <= 0)
        {
            throw new IllegalArgumentException("LedgerDAO::getAllForDetailOrderId:"
                    + " Received a NULL or empty detailOrderId object.");
        }
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("LedgerDAO::getAllForDetailOrderId:"
                    + " Received a NULL or empty detailCptCodeId object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE detailOrderId = " + String.valueOf(detailOrderId) + " AND detailCptCodeId = " + String.valueOf(detailCptCodeId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<Ledger> objList = new ArrayList<Ledger>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                Ledger obj = ObjectFromResultSet(rs);
                objList.add(obj);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return objList;
    }
    
    private static Ledger ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        Ledger obj = new Ledger();
        obj.setIdLedger(rs.getInt("idLedger"));
        obj.setAdjustmentId(rs.getInt("adjustmentId"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setDetailCptCodeId(rs.getInt("detailCptCodeId"));
        obj.setPreviousAmount(rs.getBigDecimal("previousAmount"));
        obj.setAdjustmentAmount(rs.getBigDecimal("adjustmentAmount"));
        obj.setCreated(rs.getTimestamp("created"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `ledger`.`idLedger`,\n"
                + "    `ledger`.`adjustmentId`,\n"
                + "    `ledger`.`detailOrderId`,\n"
                + "    `ledger`.`detailCptCodeId`,\n"
                + "    `ledger`.`previousAmount`,\n"
                + "    `ledger`.`adjustmentAmount`,\n"
                + "    `ledger`.`created`\n"
                + "FROM `cssbilling`.`ledger` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
