package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.OrderCptDiagnosisCode;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderCptDiagnosisCodeDAO implements IStructureCheckable
{

    private static final String table = "`orderCptDiagnosisCodes`";

    public static OrderCptDiagnosisCode insert(OrderCptDiagnosisCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }   
    
    public static OrderCptDiagnosisCode insert(Connection con, OrderCptDiagnosisCode obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("OrderCptDiagnosisCodeDAO::Insert: Received a NULL OrderCptDiagnosisCode object");
        }


        String sql = "INSERT INTO " + table
                + "("
                + "  `orderCptCodeId`,"
                + "  `diagnosisCodeId`,"
                + "  `rank`,"
                + "  `diagnosisValidityStatusId`,"
                + "  `attached`"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getOrderCptCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDiagnosisCodeId());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDiagnosisValidityStatusId());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getAttached());

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

    public static void update(OrderCptDiagnosisCode obj) throws SQLException, IllegalArgumentException, NullPointerException
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
                + " `diagnosisValidityStatusId` = ?,"
                + " `rank` = ?,"
                + " `attached` = ?"
                + " WHERE `orderCptCodeId` = " + obj.getOrderCptCodeId() + " AND `diagnosisCodeId` = " + obj.getDiagnosisCodeId();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDiagnosisValidityStatusId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getRank());
            SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getAttached());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static List<OrderCptDiagnosisCode> getAttachedByOrderCptId(Integer orderCptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0)
        {
            throw new IllegalArgumentException("OrderCptDiagnosisCodeDAO::Get: Received a NULL or empty OrderCptDiagnosisCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `attached` = 1 AND `orderCptCodeId` = " + String.valueOf(orderCptCodeId) + " ORDER BY rank ASC";

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<OrderCptDiagnosisCode> orderCptDiagnosisCodes = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                orderCptDiagnosisCodes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return orderCptDiagnosisCodes;
    }

    public static List<OrderCptDiagnosisCode> getAllByOrderCptId(Integer orderCptCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0)
        {
            throw new IllegalArgumentException("OrderCptDiagnosisCodeDAO::Get: Received a NULL or empty OrderCptDiagnosisCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `orderCptCodeId` = " + String.valueOf(orderCptCodeId) + " ORDER BY `rank` ASC";

        PreparedStatement pStmt = con.prepareStatement(sql);

        List<OrderCptDiagnosisCode> orderCptDiagnosisCodes = new ArrayList<>();
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                orderCptDiagnosisCodes.add(ObjectFromResultSet(rs));
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return orderCptDiagnosisCodes;
    }
    
    public static OrderCptDiagnosisCode getByOrderCptDiagnosisCode(Integer orderCptCodeId, Integer diagnosisCodeId) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (orderCptCodeId == null || orderCptCodeId <= 0)
        {
            throw new IllegalArgumentException("OrderCptDiagnosisCodeDAO::getByOrderCptDiagnosisCode: Received a NULL or empty OrderCptDiagnosisCode object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE `orderCptCodeId` = " + String.valueOf(orderCptCodeId);
        if (diagnosisCodeId != null && diagnosisCodeId > 0)
        {
            sql += " AND diagnosisCodeId = " + String.valueOf(diagnosisCodeId);
        }

        PreparedStatement pStmt = con.prepareStatement(sql);

        OrderCptDiagnosisCode orderCptDiagnosisCode = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                orderCptDiagnosisCode = ObjectFromResultSet(rs);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return orderCptDiagnosisCode;
    }

    private static OrderCptDiagnosisCode ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        OrderCptDiagnosisCode obj = new OrderCptDiagnosisCode();
        obj.setOrderCptCodeId(rs.getInt("orderCptCodeId"));
        obj.setDiagnosisCodeId(rs.getInt("diagnosisCodeId"));
        obj.setRank(rs.getInt("rank"));
        obj.setDiagnosisValidityStatusId(rs.getInt("diagnosisValidityStatusId"));
        obj.setAttached(rs.getBoolean("attached"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `orderCptDiagnosisCodes`.`orderCptCodeId`,\n"
                + "    `orderCptDiagnosisCodes`.`diagnosisCodeId`,\n"
                + "    `orderCptDiagnosisCodes`.`rank`,\n"
                + "    `orderCptDiagnosisCodes`.`diagnosisValidityStatusId`,\n"
                + "    `orderCptDiagnosisCodes`.`attached`\n"
                + "FROM `css`.`orderCptDiagnosisCodes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
