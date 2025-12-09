package EMR.DAOS;

import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.OrderComment;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class OrderCommentDAO implements IStructureCheckable
{

    private static final String table = "`emrorders`.`orderComment`";

    public static OrderComment insert(OrderComment obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCommentDAO::Insert: Received a NULL OrderComment object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  orderId,"
                + "  advancedOrder,"
                + "  comment"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isAdvancedOrder());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());

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
                throw new NullPointerException("OrderCommentDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdorderComment(newId);
        } catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(OrderComment obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCommentDAO::Update: Received a NULL OrderComment object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " orderId = ?,"
                + " advancedOrder = ?,"
                + " comment = ?"
                + " WHERE idorderComment = " + obj.getIdorderComment();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isAdvancedOrder());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getComment());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        } catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static OrderComment get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("OrderCommentDAO::Get: Received a NULL or empty OrderComment object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idorderComment = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderComment obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }

        return obj;
    }
    

    public static Collection<OrderComment> getByEmrIdOrders(Integer emrIdOrders)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (emrIdOrders == null || emrIdOrders <= 0)
        {
            throw new IllegalArgumentException("OrderCommentDAO::getByEmrOrderId:"
                    + " Received a NULL or empty emrOrderId argument.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderId = " + String.valueOf(emrIdOrders);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderComment obj = null;
        List<EMR.DOS.OrderComment> emrOrderComments = new LinkedList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                emrOrderComments.add(ObjectFromResultSet(rs));
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        return emrOrderComments;
    }    

    private static OrderComment ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderComment obj = new OrderComment();
        obj.setIdorderComment(rs.getInt("idorderComment"));
        obj.setOrderId(rs.getInt("orderId"));
        obj.setAdvancedOrder(rs.getBoolean("advancedOrder"));
        obj.setComment(rs.getString("comment"));

        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `orderComment`.`idorderComment`,\n"
                + "    `orderComment`.`orderId`,\n"
                + "    `orderComment`.`advancedOrder`,\n"
                + "    `orderComment`.`comment`\n"
                + "FROM `emrorders`.`orderComment` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
