package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.Event;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventDAO implements IStructureCheckable
{

    // Events are held on the billing schema
    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`events`";

    /**
     * Uses the database singleton connection
     * @param obj
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static Event insert(Event obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        return insert(con, obj);
    }
    
    
    public static Event insert(Connection con, Event obj)
            throws SQLException, IllegalArgumentException, NullPointerException
    {

        if (obj == null)
        {
            throw new IllegalArgumentException("EventDAO::Insert:"
                    + " Received a NULL Event object");
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  eventTypeId,"
                + "  userId,"
                + "  date"
                + ")"
                + "VALUES (?,?,NOW())";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventTypeId());
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
                throw new NullPointerException("EventDAO::Insert:"
                        + " Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdEvents(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(Event obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EventDAO::Update: Received a NULL Event object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " eventTypeId = ?,"
                + " userId = ?,"
                + " WHERE idEvent = " + obj.getIdEvents();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventTypeId());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getUserId());
            sqlOutput = pStmt.toString();
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (Exception ex)
        {
            String errorMsg = ex.getMessage() + " " + sqlOutput;
            System.out.println(errorMsg);
            throw new SQLException(errorMsg);
        }
    }

    public static Event get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("EventDAO::Get: Received a NULL or empty Event object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idEvent = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        Event obj = null;
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

    public static Event ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        Event obj = new Event();
        obj.setIdEvents(rs.getInt("idEvent"));
        obj.setEventTypeId(rs.getInt("eventTypeId"));
        obj.setUserId(rs.getInt("userId"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `events`.`idEvents`,\n"
                + "    `events`.`eventTypeId`,\n"
                + "    `events`.`userId`,\n"
                + "    `events`.`date`\n"
                + "FROM `cssbilling`.`events` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
