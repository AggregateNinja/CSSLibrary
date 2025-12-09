package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.DetailOrderEvent;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailOrderEventDAO implements IStructureCheckable
{

    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`detailOrderEvents`";

    public static DetailOrderEvent insert(DetailOrderEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }    
    
    public static DetailOrderEvent insert(Connection con, DetailOrderEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailOrderEventDAO::Insert:"
                    + " Received a NULL DetailOrderEvent object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  detailOrderId,"
                + "  detailCptCodeId,"
                + "  eventId,"
                + "  description,"
                + "  userId"
                + ")"
                + "VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
        SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getDetailCptCodeId()); // NULLable
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());

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
                throw new NullPointerException("DetailOrderEventDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdDetailOrderEvents(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(DetailOrderEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("DetailOrderEventDAO::Update: Received a NULL DetailOrderEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " detailOrderId = ?,"
                + " detailCptCodeId = ?,"
                + " eventId = ?,"
                + " description = ?,"
                + " userId = ?"
                + " WHERE idDetailOrderEvents = " + obj.getIdDetailOrderEvents();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getDetailOrderId());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, obj.getDetailCptCodeId()); // NULLable
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
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

    public static DetailOrderEvent get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("DetailOrderEventDAO::Get: Received a NULL or empty DetailOrderEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idDetailOrderEvents = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        DetailOrderEvent obj = null;
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

    public static DetailOrderEvent ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        DetailOrderEvent obj = new DetailOrderEvent();
        obj.setIdDetailOrderEvents(rs.getInt("idDetailOrderEvents"));
        obj.setDetailOrderId(rs.getInt("detailOrderId"));
        obj.setDetailCptCodeId(SQLUtil.getInteger(rs, "detailCptCodeId"));
        obj.setEventId(rs.getInt("eventId"));
        obj.setDescription(rs.getString("description"));
        obj.setUserId(rs.getInt("userId"));
        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `detailOrderEvents`.`idDetailOrderEvents`,\n"
                + "    `detailOrderEvents`.`detailOrderId`,\n"
                + "    `detailOrderEvents`.`detailCptCodeId`,\n"
                + "    `detailOrderEvents`.`eventId`,\n"
                + "    `detailOrderEvents`.`description`,\n"
                + "    `detailOrderEvents`.`userId`\n"
                + "FROM `cssbilling`.`detailOrderEvents` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
