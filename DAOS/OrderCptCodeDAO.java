package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderCptCode;
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

public class OrderCptCodeDAO implements IStructureCheckable
{

    private static final String table = "`orderCptCodes`";

    public static OrderCptCode insert(OrderCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }
    
    public static OrderCptCode insert(Connection con, OrderCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCptCodesDAO::Insert: Received a NULL OrderCptCodes object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  `orderedTestId`,"
                + "  `cptCodeId`"
                + ")"
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderedTestId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());

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
                throw new NullPointerException("OrderCptCodesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdorderCptCodes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(OrderCptCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCptCodesDAO::Update: Received a NULL OrderCptCodes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " `orderedTestId` = ?,"
                + " `cptCodeId` = ?"
                + " WHERE `idorderCptCodes` = " + obj.getIdorderCptCodes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderedTestId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getCptCodeId());
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

    public static OrderCptCode get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("OrderCptCodesDAO::Get: Received a NULL or empty OrderCptCodes object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `idorderCptCodes` = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderCptCode obj = null;
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

    public static List<OrderCptCode> GetAllOrderCptCode(Integer orderdTestId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderdTestId == null || orderdTestId <= 0)
        {
            throw new IllegalArgumentException("OrderCptCodesDAO::GetOrderCptCode: Received a NULL or empty orderdTestId object and cptCodeId object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `orderedTestId` = " + String.valueOf(orderdTestId);

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<OrderCptCode> orderCptCodeList = new ArrayList<OrderCptCode>();
        OrderCptCode obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                obj = ObjectFromResultSet(rs);
                orderCptCodeList.add(obj);
            }
            rs.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return orderCptCodeList;
    }
    
    public static OrderCptCode GetOrderCptCode(Integer orderdTestId, Integer cptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderdTestId == null || orderdTestId <= 0)
        {
            throw new IllegalArgumentException("OrderCptCodesDAO::GetOrderCptCode: Received a NULL or empty orderdTestId object and cptCodeId object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `orderedTestId` = " + String.valueOf(orderdTestId);
        if (cptCodeId != null && cptCodeId > 0)
        {
            sql += " AND `cptCodeId` = " + String.valueOf(cptCodeId);
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderCptCode obj = null;
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
    
    private static OrderCptCode ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderCptCode obj = new OrderCptCode();
        obj.setIdorderCptCodes(rs.getInt("idorderCptCodes"));
        obj.setOrderedTestId(rs.getInt("orderedTestId"));
        obj.setCptCodeId(rs.getInt("cptCodeId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderCptCodes`.`idorderCptCodes`,\n"
                + "    `orderCptCodes`.`orderedTestId`,\n"
                + "    `orderCptCodes`.`cptCodeId`\n"
                + "FROM `css`.`orderCptCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
