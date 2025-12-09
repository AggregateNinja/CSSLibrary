
package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientPropertyLookup;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClientPropertyLookupDAO implements IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientPropertyLookup`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public ClientPropertyLookupDAO()
    {
        fields.add("clientId");
        fields.add("clientPropertyId");
        fields.add("createdDate");
        fields.add("createdByUserId");
    }

    /**
     * Inserts a new association between a client and a client property
     * @param clientPropertyLookup
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public ClientPropertyLookup Insert(ClientPropertyLookup clientPropertyLookup)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (clientPropertyLookup == null)
        {
            throw new IllegalArgumentException("ClientPropertyLookupDAO::Insert: "
                    + "Received [NULL] ClientPropertyLookup object");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "INSERT INTO " + table
                + " (clientId, clientPropertyId, createdByUserId)"
                + " VALUES"
                + " (?, ?, ?)";
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i=0;
        pStmt.setInt(++i, clientPropertyLookup.getClientId());
        pStmt.setInt(++i, clientPropertyLookup.getClientPropertyId());
        pStmt.setInt(++i, clientPropertyLookup.getCreatedByUserId());
        pStmt.execute();

        return clientPropertyLookup;        
    }

    /**
     * Uses the unique clientId / clientPropertyId combination to remove a
     *  single clientPropertyLookup line.
     * @param clientPropertyLookup
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public void Delete(ClientPropertyLookup clientPropertyLookup)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (clientPropertyLookup == null)
        {
            throw new IllegalArgumentException("ClientPropertyLookupDAO::Delete: "
                    + "Received [NULL] ClientPropertyLookup object");
        }

        if (clientPropertyLookup.getClientId() == 0 || clientPropertyLookup.getClientPropertyId() == 0)
        {
            throw new IllegalArgumentException("ClientPropertyLookupDAO::Delete: "
                    + "Supplied ClientPropertyLookup does not have a clientId and/or"
                    + " clientPropertyId.");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "DELETE FROM " + table + " WHERE clientId = " + clientPropertyLookup.getClientId() +
                " AND clientPropertyId = " + clientPropertyLookup.getClientPropertyId();
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.execute();
    }
    
    public ClientPropertyLookup GetByClientIdPropertyId(int clientId, int clientPropertyId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        String sql = "SELECT * FROM " + table + " WHERE clientId = "
                + clientId + " AND clientPropertyId = " + clientPropertyId;
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        ClientPropertyLookup clientPropertyLookup = null;
        if (rs.next())
        {
            clientPropertyLookup = FromResultSet(rs);
        }
        return clientPropertyLookup;
    }

    private ClientPropertyLookup FromResultSet(ResultSet rs) throws SQLException
    {
        ClientPropertyLookup clientPropertyLookup = new ClientPropertyLookup();
        clientPropertyLookup.setClientId(rs.getInt("clientId"));
        clientPropertyLookup.setClientPropertyId(rs.getInt("clientPropertyId"));
        clientPropertyLookup.setCreatedDate(rs.getTimestamp("createdDate"));
        clientPropertyLookup.setCreatedByUserId(rs.getInt("createdByUserId"));
        return clientPropertyLookup;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
