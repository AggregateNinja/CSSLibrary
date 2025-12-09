package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.EventType;
import Database.CheckDBConnection;
import Database.DatabaseSingleton;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EventTypeDAO implements IStructureCheckable
{

    public static final String table = DatabaseSingleton.getDatabaseSingleton().getProperties().getBillingSchema() + ".`eventTypes`";

    public enum EventTypes
    {

        // String represents the system name
        ORDERS_ENTER_BILLING_SYSTEM("orders_in"),
        ORDERS_REMOVED_FROM_BILLING_SYSTEM("order_removed"),
        CLAIMS_SUBMITTED("claims_submitted"),
        CLAIMS_REJECTED("claims_rejected"),
        PAYMENT_APPLIED("payment"),
        WRITE_OFF("writeoff"),
        AMOUNT_CORRECTION("correction"),
        ORDER_INSURANCE_TRANSFER("transfer"),
        ORDERS_COMPLETED("completed"),
        ORDER_DATA_CHANGE("order_changed"),
        ORDER_COMMENT("order_comment"),
        SUBMISSION_COMMENT("submission_comment"),
        ADJUSTMENT_COMMENT("adjustment_comment");

        final String systemName;

        EventTypes(String systemName)
        {
            this.systemName = systemName;
        }
        
        public String getSystemName()
        {
            return systemName;
        }
    }

    public static EventType insert(EventType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EventTypesDAO::Insert: Received a NULL EventTypes object");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "INSERT INTO " + table
                + "("
                + "  name,"
                + "  eventCategoryId,"
                + "  systemName"
                + ")"
                + "VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventCategoryId());
        SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());

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
                throw new NullPointerException("EventTypesDAO::Insert: Cannot retrieve generated identifier for inserted row.");
            }
            obj.setIdEventTypes(newId);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage() + " " + pStmt.toString());
            throw ex;
        }

        return obj;
    }

    public static void update(EventType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (obj == null)
        {
            throw new IllegalArgumentException("EventTypeDAO::Update: Received a NULL EventType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " eventCategoryId = ?,"
                + " systemName = ?"
                + " WHERE idEventTypes = " + obj.getIdEventTypes();

        String sqlOutput = "";
        PreparedStatement pStmt = con.prepareStatement(sql);
        try
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, obj.getName());
            SQLUtil.SafeSetInteger(pStmt, ++i, obj.getEventCategoryId());
            SQLUtil.SafeSetString(pStmt, ++i, obj.getSystemName());
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

    public static EventType get(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("EventTypeDAO::Get: Received a NULL or empty EventType object.");
        }

        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE idEventTypes = " + String.valueOf(id);

        PreparedStatement pStmt = con.prepareStatement(sql);

        EventType obj = null;
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
    
    /**
     * Returns an EventType object based on the enum parameter.
     * @param eventTypeEnum
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public static EventType get(EventTypes eventTypeEnum) throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (eventTypeEnum == null)
        {
            throw new IllegalArgumentException("EventTypeDAO::Get(Enum): Received a NULL or empty EventType enum object.");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }

        String sql = "SELECT * FROM " + table + " WHERE systemName = ?";

        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, eventTypeEnum.systemName);
        EventType obj = null;
        try
        {
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                obj = ObjectFromResultSet(rs);
            }
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage() + " " + sql);
            throw new SQLException(ex.getMessage() + " " + sql);
        }
        pStmt.close();
        return obj;
    }

    private static EventType ObjectFromResultSet(ResultSet rs) throws SQLException, NullPointerException
    {
        EventType obj = new EventType();
        obj.setIdEventTypes(rs.getInt("idEventTypes"));
        obj.setName(rs.getString("name"));
        obj.setEventCategoryId(rs.getInt("eventCategoryId"));
        obj.setSystemName(rs.getString("systemName"));

        return obj;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `eventTypes`.`idEventTypes`,\n"
                + "    `eventTypes`.`name`,\n"
                + "    `eventTypes`.`eventCategoryId`,\n"
                + "    `eventTypes`.`systemName`\n"
                + "FROM `cssbilling`.`eventTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
