
package EMR.DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import EMR.DOS.EmrEventType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class EmrEventTypeDAO implements IDAO<EmrEventType>, IStructureCheckable
{
    private final String table = "`emrorders`.`eventTypes`";
    
    // System enum that maps to the database text.
    // If we decide to change the wording on an event type in the database, we just have
    // to change it in this enum instead of changing any/all the hard-coded strings
    public enum EventTypeEnum
    {
        ORDER_APPROVED("Order Approved"),
        ORDER_DELETED("Order Deleted"),
        INSURANCE_MAPPED("Existing Insurance Matched"),
        INSURANCE_ADDED("New Insurance Created"),
        INSURANCE_CLEARED("Insurance Cleared");
        
        private final String name;
        
        private EventTypeEnum(String name)
        {
            this.name = name;
        }
        
        public String getDatabaseName()
        {
            return this.name;
        }
    }
    
    @Override
    public EmrEventType Insert(EmrEventType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EmrEventType Update(EmrEventType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(EmrEventType obj) throws SQLException, IllegalArgumentException, NullPointerException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static EmrEventType GetByEnum(EventTypeEnum eventEnum)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (eventEnum == null) throw new IllegalArgumentException("EmrEventTypeDAO::GetByEnum: EventTypeEnum argument was NULL");
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) conn = CheckDBConnection.Check(dbs, conn);
        
        String eventName = eventEnum.getDatabaseName();
        
        String sql = "SELECT * FROM `emrorders`.`eventTypes` WHERE `name` = ?";
        PreparedStatement pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, eventName);
        ResultSet rs = pStmt.executeQuery();
        EmrEventType event = null;
        if (rs.next())
        {
            event = getFromResultSet(rs);
        }
        pStmt.close();
        return event;        
    }

    @Override
    public EmrEventType GetById(Integer id) throws SQLException, IllegalArgumentException, NullPointerException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) conn = CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM " + table + " WHERE idEmrEventTypes = " + id;
        PreparedStatement pStmt = conn.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        EmrEventType event = null;
        if (rs.next())
        {
            event = getFromResultSet(rs);
        }
        pStmt.close();
        return event;
    }
    
    public List<EmrEventType> getAll()
            throws SQLException
    {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection conn = dbs.getConnection(true);
        if (conn.isValid(2) == false) conn = CheckDBConnection.Check(dbs, conn);
        
        String sql = "SELECT * FROM " + table;
        PreparedStatement pStmt = conn.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<EmrEventType> eventTypes = new LinkedList<>();
        
        while (rs.next())
        {
            eventTypes.add(getFromResultSet(rs));
        }
        pStmt.close();
        return eventTypes;
    }
    
    private static EmrEventType getFromResultSet(ResultSet rs) throws SQLException
    {
        EmrEventType event = new EmrEventType();
        event.setIdEmrEventTypes(rs.getInt("idEventTypes"));
        event.setName(rs.getString("name"));
        event.setIsError(rs.getBoolean("isError"));
        event.setIsUserVisible(rs.getBoolean("isUserVisible"));
        return event;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `eventTypes`.`idEventTypes`,\n"
                + "    `eventTypes`.`name`,\n"
                + "    `eventTypes`.`isError`,\n"
                + "    `eventTypes`.`isUserVisible`\n"
                + "FROM `emrorders`.`eventTypes` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, Database.DatabaseSingleton.getDatabaseSingleton().getConnection(true));
    }
}
