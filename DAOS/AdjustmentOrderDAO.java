package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentOrder;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdjustmentOrderDAO implements IStructureCheckable
{
    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustmentOrders`";

    public static AdjustmentOrder insert(Connection con, AdjustmentOrder obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AdjustmentOrderDAO::Insert:"
                    + "Received a NULL AdjustmentOrder object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentId,"
                + "  detailOrderId,"
                + "  amount"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getAmount());

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
                throw new NullPointerException("AdjustmentOrderDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdAdjustmentOrders(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(AdjustmentOrder obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException(
                    "AdjustmentOrderDAO::Update: Received a NULL AdjustmentOrder object.");
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
                + " amount = ?"
                + " WHERE idAdjustmentOrders = " + obj.getIdAdjustmentOrders();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetBigDecimal(pStmt, ++i, obj.getAmount());
            pStmt.setBigDecimal(++i, obj.getAmount());
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

    public static AdjustmentOrder get(Integer id)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("AdjustmentOrderDAO::Get:"
                    + " Received a NULL or empty AdjustmentOrder object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idAdjustmentOrders = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        AdjustmentOrder obj = null;
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

    private static AdjustmentOrder ObjectFromResultSet(ResultSet rs)
            throws SQLException, NullPointerException
    {
        AdjustmentOrder obj = new AdjustmentOrder();
        obj.setIdAdjustmentOrders(rs.getInt("idAdjustmentOrders"));
        obj.setAdjustmentId(rs.getInt("adjustmentId"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setAmount(rs.getBigDecimal("amount"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `adjustmentOrders`.`idAdjustmentOrders`,\n"
                + "    `adjustmentOrders`.`adjustmentId`,\n"
                + "    `adjustmentOrders`.`detailOrderId`,\n"
                + "    `adjustmentOrders`.`amount`\n"
                + "FROM `cssbilling`.`adjustmentOrders` LIMIT 1;";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
