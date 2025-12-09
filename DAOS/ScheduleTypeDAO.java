
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ScheduleType;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`scheduleType`";
    
    public ScheduleTypeDAO() {}
    
    public ScheduleType GetById(int scheduleTypeId) throws SQLException
    {
        String sql = "SELECT * from " + table + " WHERE idscheduleType = " + scheduleTypeId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ScheduleType st = new ScheduleType();
        if (rs.next())
        {
            st = FromRecordSet(st, rs);
        }
        if (st.getId() == null) return null;
        return st;
    }
    
    private List<ScheduleType> GetAll() throws SQLException
    {
        String sql = "SELECT * FROM " + table;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ArrayList<ScheduleType> scheduleTypes = new ArrayList<>();
        while (rs.next())
        {
            ScheduleType st = FromRecordSet(new ScheduleType(), rs);
            scheduleTypes.add(st);
        }
        return scheduleTypes;
    }
    
    private ScheduleType FromRecordSet(ScheduleType st, ResultSet rs) throws SQLException
    {
        st.setId(rs.getInt("idscheduleType"));
        st.setName(rs.getString("name"));
        return st;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `scheduleType`.`idscheduleType`,\n"
                + "    `scheduleType`.`name`\n"
                + "FROM `css`.`scheduleType` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}