
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.TransmissionType;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TransmissionTypeDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`transmissionTypes`";    
    private final List<String> fields = new LinkedList<>();
    
    public TransmissionTypeDAO()
    {
        fields.add("name");
        fields.add("description");
    }
    
    /**
     * Returns all transmission types
     * @param searchType
     * @param visibility
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<TransmissionType> GetTransmissionTypes() throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        List<TransmissionType> transmissionTypes = new LinkedList<>();
        while (rs.next())
        {
            transmissionTypes.add(GetAttachmentTypeFromResultSet(rs));
        }
        return transmissionTypes;        
    }
    
    /**
     * Returns an TransmissionType object with the provided unique identifier.
     * @param id
     * @param searchType
     * @return AttachmentType, NULL object if not found
     * @throws SQLException 
     */
    public TransmissionType GetTransmissionTypeById(int id) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE id = " + id;
        
        TransmissionType transmissionType = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            transmissionType = GetAttachmentTypeFromResultSet(rs);
        }
        return transmissionType;
    }
    
    public TransmissionType GetAttachmentTypeByName(String name) throws SQLException
    {
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        String sql = "SELECT * FROM " + table + " WHERE name = ?";
        
        TransmissionType transmissionType = null;
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next())
        {
            transmissionType = GetAttachmentTypeFromResultSet(rs);
        }
        return transmissionType;        
    }
    
    private PreparedStatement SetStatementFromObject(PreparedStatement pStmt, TransmissionType transmissionType)
            throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, transmissionType.getId());
        SQLUtil.SafeSetString(pStmt, ++i, transmissionType.getName());
        SQLUtil.SafeSetString(pStmt, ++i, transmissionType.getDescription());
        
        return pStmt;
    }
    
    private TransmissionType GetAttachmentTypeFromResultSet(ResultSet rs) throws SQLException
    {
        TransmissionType transmissionType = new TransmissionType();
        transmissionType.setId(rs.getInt("id"));
        transmissionType.setName(rs.getString("name"));
        return transmissionType;
    }    
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}