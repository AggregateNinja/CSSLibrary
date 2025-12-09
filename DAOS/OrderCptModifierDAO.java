package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderCptModifier;
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

public class OrderCptModifierDAO implements IStructureCheckable
{

    private static final String table = "`orderCptModifiers`";

    public static OrderCptModifier insert(OrderCptModifier obj) throws SQLException, IllegalArgumentException, NullPointerException
    {

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }    
    
    public static OrderCptModifier insert(Connection con, OrderCptModifier obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCptModifierDAO::Insert: Received a NULL OrderCptModifier object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  orderCptCodeId,"
                + "  cptModifierId"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCptId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptModifierId());

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

    public static OrderCptModifier get(Integer orderCptCodeId, Integer cptModifierId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0 || cptModifierId == null || cptModifierId <= 0)
        {
            throw new IllegalArgumentException("OrderCptModifierDAO::Get: Received a NULL or empty id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderCptCodeId = " + String.valueOf(orderCptCodeId) + " AND cptModifierId = " + String.valueOf(cptModifierId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderCptModifier obj = null;
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
    
    public static int delete(Integer orderCptCodeId, Integer cptModifierId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0 || cptModifierId == null || cptModifierId <= 0)
        {
            throw new IllegalArgumentException("OrderCptModifierDAO::Delete: Received a NULL or empty id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "DELETE FROM " + table + " WHERE orderCptCodeId = " + String.valueOf(orderCptCodeId) + " AND cptModifierId = " + String.valueOf(cptModifierId);
        PreparedStatement pStmt = con.prepareStatement(sql);
        return pStmt.executeUpdate();
    }

    public static List<OrderCptModifier> GetAllCptModifiers(Integer orderCptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0)
        {
            throw new IllegalArgumentException("OrderCptModifierDAO::Get: Received a NULL or empty id object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE orderCptCodeId = " + String.valueOf(orderCptCodeId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<OrderCptModifier> modList = new ArrayList<OrderCptModifier>();
        OrderCptModifier obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                obj = ObjectFromResultSet(rs);
                modList.add(obj);
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return modList;
    }

    private static OrderCptModifier ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderCptModifier obj = new OrderCptModifier();
        obj.setOrderCptId(rs.getInt("orderCptCodeId"));
        obj.setCptModifierId(rs.getInt("cptModifierId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderCptModifiers`.`orderCptCodeId`,\n"
                + "    `orderCptModifiers`.`cptModifierId`\n"
                + "FROM `css`.`orderCptModifiers` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
