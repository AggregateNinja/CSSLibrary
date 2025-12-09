package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.AdjustmentEvent;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdjustmentEventDAO implements IStructureCheckable
{

    private static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`adjustmentEvents`";

    public static AdjustmentEvent insert(Connection con, AdjustmentEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AdjustmentEventDAO::Insert:"
                    + " Received a NULL AdjustmentEvent object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  adjustmentId,"
                + "  eventId,"
                + "  description,"
                + "  userId"
                + ")"
                + "VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
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
                throw new NullPointerException("AdjustmentEventDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdAdjustmentEvents(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(AdjustmentEvent obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("AdjustmentEventDAO::Update:"
                    + " Received a NULL AdjustmentEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " adjustmentId = ?,"
                + " eventId = ?,"
                + " description = ?,"
                + " userId = ?"
                + " WHERE idAdjustmentEvents = " + obj.getIdAdjustmentEvents();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getAdjustmentId());
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

    public static AdjustmentEvent get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("AdjustmentEventDAO::Get: Received a NULL or empty AdjustmentEvent object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idAdjustmentEvents = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        AdjustmentEvent obj = null;
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

    public static AdjustmentEvent ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        AdjustmentEvent obj = new AdjustmentEvent();
        obj.setIdAdjustmentEvents(rs.getInt("idAdjustmentEvents"));
        obj.setAdjustmentId(rs.getInt("adjustmentId"));
        obj.setEventId(rs.getInt("eventId"));
        obj.setDescription(rs.getString("description"));
        obj.setUserId(rs.getInt("userId"));
        return obj;
    }

    @Override
    public String structureCheck() {
        String query = "SELECT `adjustmentEvents`.`idAdjustmentEvents`,\n"
                + "    `adjustmentEvents`.`adjustmentId`,\n"
                + "    `adjustmentEvents`.`eventId`,\n"
                + "    `adjustmentEvents`.`description`,\n"
                + "    `adjustmentEvents`.`userId`\n"
                + "FROM `cssbilling`.`adjustmentEvents` LIMIT 1;";
     return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
