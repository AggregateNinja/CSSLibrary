package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderUpdateQueue;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderUpdateQueueDAO implements IStructureCheckable
{

    private static final String table = Database.DatabaseSingleton.getDatabaseSingleton().getProperties().getCssSchema() + ".`orderUpdateQueue`";

    public static OrderUpdateQueue insert(OrderUpdateQueue obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderUpdateQueueDAO::Insert: Received a NULL OrderUpdateQueue object");
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
                + "  updateType,"
                // queuedDate is current_timestamp
                + "  queuedFrom,"
                + "  queuedById,"
                + "  complete,"
                + "  dateCompleted"
                + ")"
                + "VALUES (?,?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getUpdateType());
        // queuedDate
        SQLUtil.SafeSetString(pStmt, ++i, obj.getQueuedFrom());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQueuedById());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getComplete());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateCompleted()));

        try
        {
            Integer newId = null;
            pStmt.executeUpdate();
            /*
            ResultSet rs = pStmt.getGeneratedKeys();
            if (rs.next())
            {
                newId = rs.getInt(1);
            }
            pStmt.close();
            if (newId == null || newId <= 0)
            {
                throw new NullPointerException("OrderUpdateQueueDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdOrderUpdateQueue(newId);
            */
        }
        catch (Exception ex)
        {
            System.out.println((ex.getMessage()!=null?ex.getMessage():"")+ " " + pStmt.toString());
            throw ex;
        }
        finally
        {
            try
            {
                if (pStmt != null)
                {
                    pStmt.close();
                }
            }
            catch (SQLException ex) {}
        }
        return obj;
    }

    public static void update(OrderUpdateQueue obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderUpdateQueueDAO::Update: Received a NULL OrderUpdateQueue object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " orderId = ?,"
                + " updateType = ?,"
                // queuedDate not updateable
                + " queuedFrom = ?,"
                + " queuedById = ?,"
                + " complete = ?,"
                + " dateCompleted = ?"
                + " WHERE idOrderUpdateQueue = " + obj.getIdOrderUpdateQueue();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getUpdateType());
            // Not updating queuedDate
            SQLUtil.SafeSetString(pStmt, ++i, obj.getQueuedFrom());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQueuedById());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getComplete());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(obj.getDateCompleted()));
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = (ex.getMessage() != null?ex.getMessage():"") + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
        finally
        {
            try
            {
                if (pStmt != null)
                {
                    pStmt.close();
                }
            }
            catch (SQLException ex)
            {
            }
        }
    }

    public static OrderUpdateQueue get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("OrderUpdateQueueDAO::Get: Received a NULL or empty OrderUpdateQueue object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idOrderUpdateQueue = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderUpdateQueue obj = null;
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
        finally
        {
            try
            {
                if (pStmt != null) pStmt.close();
            }
            catch (SQLException ex) {}
        }
        return obj;
    }

    public static OrderUpdateQueue ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderUpdateQueue obj = new OrderUpdateQueue();
        obj.setIdOrderUpdateQueue(rs.getInt("idOrderUpdateQueue"));
        obj.setOrderId(SQLUtil.getInteger(rs, "orderId"));
        obj.setQueuedDate(rs.getDate("queuedDate"));
        obj.setQueuedFrom(rs.getString("queuedFrom"));
        obj.setQueuedById(rs.getInt("queuedById"));
        obj.setComplete(rs.getInt("complete"));
        obj.setDateCompleted(rs.getDate("dateCompleted"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderUpdateQueue`.`orderId`,\n"
                + "    `orderUpdateQueue`.`updateType`,\n"
                + "    `orderUpdateQueue`.`queuedDate`,\n"
                + "    `orderUpdateQueue`.`queuedFrom`,\n"
                + "    `orderUpdateQueue`.`queuedById`,\n"
                + "    `orderUpdateQueue`.`complete`,\n"
                + "    `orderUpdateQueue`.`dateCompleted`\n"
                + "FROM `css`.`orderUpdateQueue` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
