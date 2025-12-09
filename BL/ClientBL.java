
package BL;

import DAOS.ClientDAO;
import DAOS.ClientPropertyDAO;
import DAOS.ClientPropertyLookupDAO;
import DOS.ClientProperty;
import DOS.ClientPropertyLookup;
import DOS.Clients;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author TomR
 */
public class ClientBL
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public enum DataType
    {
        ActiveOnly,
        InactiveOnly,
        All;
    }
    
    public enum PropertyType
    {
        UserVisibleOnly,
        All
    }
    
    /**
     * Returns client properties by unique database identifier
     * @param clientId
     * @param dataType
     * @param propertyType
     * @return Empty collection if no properties present
     * @throws java.sql.SQLException 
     */
    public Collection<ClientProperty> GetPropertiesByClientId(Integer clientId,
            DataType dataType, PropertyType propertyType)
            throws IllegalArgumentException, SQLException, NullPointerException
    {
        if (clientId == null) throw new IllegalArgumentException(
                "ClientBL::GetPropertiesByClientId: Received [NULL] clientId");
        
        if (clientId == 0) throw new IllegalArgumentException(
            "ClientBL::GetPropertiesByClientId: Received clientId of zero (0)");
        
        if (dataType == null) throw new IllegalArgumentException(
            "ClientBL::GetPropertiesByClientId: Received [NULL] DataType");
        
        if (propertyType == null) throw new IllegalArgumentException(
            "ClientBL::GetPropertiesByClientId: Received [NULL] PropertyType");
        
        String sql =
                  "SELECT * FROM clientProperties cp"
                + " INNER JOIN clientPropertyLookup cpl ON cp.id = cpl.clientPropertyId"
                + " WHERE cpl.clientId = " + clientId;
        
        if (propertyType == PropertyType.UserVisibleOnly)
        {
            sql += " AND cp.isUserVisible = 1";
        }
        
        switch (dataType)
        {
            case ActiveOnly:
                sql += " AND cp.active = 1";
                break;
            case InactiveOnly:
                sql += " AND cp.active = 0";
                break;
        }
        
        // Alpha sort by name
        sql += " ORDER BY cp.name ASC";
        
        LinkedList<ClientProperty> clientProperties = new LinkedList<>();        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            clientProperties.add(ClientPropertyDAO.FromResultSet(rs));
        }
        return clientProperties;
    }
    
    /**
     * Returns client properties that meet the data type / property type
     *  criteria provided
     * @param dataType
     * @param propertyType
     * @return
     * @throws SQLException
     * @throws IllegalArgumentException
     * @throws NullPointerException 
     */
    public Collection<ClientProperty> GetClientProperties(
            DataType dataType, PropertyType propertyType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (dataType == null) throw new IllegalArgumentException(
            "ClientBL::GetClientProperties: Received [NULL] DataType");
        
        if (propertyType == null) throw new IllegalArgumentException(
            "ClientBL::GetClientProperties: Received [NULL] PropertyType");
        
        String sql =
                  "SELECT * FROM `clientProperties`"
                + " WHERE 1=1 ";
        
        if (propertyType == PropertyType.UserVisibleOnly)
        {
            sql += " AND `isUserVisible` = 1";
        }
        
        switch (dataType)
        {
            case ActiveOnly:
                sql += " AND `active` = 1";
                break;
            case InactiveOnly:
                sql += " AND `active` = 0";
                break;
        }
        
        // Alpha sort by name
        sql += " ORDER BY `name` ASC";
        
        LinkedList<ClientProperty> clientProperties = new LinkedList<>();        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            clientProperties.add(ClientPropertyDAO.FromResultSet(rs));
        }
        return clientProperties;
    }
    
    /**
     * Returns true if a client property name exists in the database, given
     *  the data and property type.
     * @param name
     * @param dataType
     * @param propertyType
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean ClientPropertyNameExists(
            String name, DataType dataType, PropertyType propertyType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (dataType == null) throw new IllegalArgumentException(
            "ClientBL::ClientPropertyNameExists: Received [NULL] DataType");
        
        if (propertyType == null) throw new IllegalArgumentException(
            "ClientBL::ClientPropertyNameExists: Received [NULL] PropertyType");
        
        String sql =
                  "SELECT COUNT(*) FROM `clientProperties` cp"
                + " WHERE `name` = ?";
        
        if (propertyType == PropertyType.UserVisibleOnly)
        {
            sql += " AND cp.isUserVisible = 1";
        }
        
        switch (dataType)
        {
            case ActiveOnly:
                sql += " AND cp.active = 1";
                break;
            case InactiveOnly:
                sql += " AND cp.active = 0";
                break;
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();
        int count = 0;
        if (rs.next())
        {
            count = rs.getInt(1);
        }
        return (count > 0);
    }
    
    /**
     * Returns true if the given client has the given client property associated with it.
     * @param clientPropertyId
     * @param clientId
     * @return 
     * @throws java.sql.SQLException 
     */
    public boolean ClientHasClientProperty(int clientPropertyId, int clientId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        boolean hasClientPropertyId = false;
        
        if (clientPropertyId == 0) throw new IllegalArgumentException(
                "ClientBL::ClientHasClientProperty: Passed a zero client property Id");
        
        if (clientId == 0) throw new IllegalArgumentException(
                "ClientBL::ClientHasClientProperty: sshPassed a zero client Id");
        
        String sql = "SELECT clientId FROM `clientPropertyLookup`"
                + " WHERE clientPropertyId = " + clientPropertyId
                + " AND clientId = " + clientId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        if (rs.next())
        {
            hasClientPropertyId = true;
        }
        
        return hasClientPropertyId;
    }
    
    /**
     * Removes any existing client property lookup associations and inserts
     *  the provided "selected client properties"
     * @param userId
     * @param clientId 
     * @param selectedClientProperties Only provide the selected client properties
     * @throws java.sql.SQLException 
     */
    public void SaveClientProperties(int userId,
            int clientId, Collection<ClientProperty> selectedClientProperties)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (clientId <=0) throw new IllegalArgumentException("ClientBL::SaveClientProperties:"
                + " Received invalid clientId: " + clientId);
        
        if (selectedClientProperties == null) throw new IllegalArgumentException(
            "ClientBL::SaveClientProperties: Received [NULL] client property collection.");
        
        Iterator<ClientProperty> selectedIter = selectedClientProperties.iterator();
        
        // Get rid of any current visible associations
        DeleteClientPropertyAssociations(clientId, PropertyType.UserVisibleOnly);
        
        // Insert new
        ClientPropertyLookupDAO cpldao = new ClientPropertyLookupDAO();
        while (selectedIter.hasNext())
        {
            ClientProperty selectedClientProperty = selectedIter.next();
            ClientPropertyLookup cpl = new ClientPropertyLookup();
            cpl.setClientId(clientId);
            cpl.setClientPropertyId(selectedClientProperty.getId());
            cpl.setCreatedByUserId(userId);
            cpldao.Insert(cpl);
        }
    }
    
    /**
     * Gets rid of any client properties that fit the data type / property type
     * @param clientId 
     * @param propertyType 
     * @throws java.sql.SQLException 
     */
    public void DeleteClientPropertyAssociations(int clientId, PropertyType propertyType)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        String sql = "DELETE `cpl` FROM `clientPropertyLookup` cpl"
                + " INNER JOIN `clientProperties` cp ON cpl.clientPropertyId = cp.id"
                + " WHERE clientId = " + clientId;
        
        if (propertyType == PropertyType.UserVisibleOnly)
        {
            sql += " AND cp.isUserVisible = 1";
        }
        PreparedStatement pStmt = con.prepareCall(sql);
        pStmt.execute();
    }
    
    /**
     * Returns a list of any client currently associated with the client property
     * id supplied.
     * @param clientPropertyId
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<Clients> GetClientsUsingProperty(int clientPropertyId)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        
        if (clientPropertyId == 0) throw new IllegalArgumentException(
                "ClientBL::GetClientsUsingProperty: Passed a zero client property Id");
        
        String sql = "SELECT clientId FROM `clientPropertyLookup`"
                + " WHERE clientPropertyId = " + clientPropertyId;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        LinkedList<Clients> clientList = new LinkedList<>();
        ClientDAO cdao = new ClientDAO();
        while (rs.next())
        {
            Integer clientId = rs.getInt(1);
            if (clientId == 0) throw new SQLException(
                    "ClientBL::GetClientsUsingProperty:"
                    + " Received [NULL] clientId in clientPropertyLookup table!");
            
            Clients client = cdao.GetClientById(clientId);
            if (client == null || client.getIdClients() == null ||
                    client.getIdClients() == 0)
            {
                throw new SQLException("ClientBL::GetClientsUsingProperty:"
                        + " Cannot retrieve client record for clientId " + clientId);
            }
            
            clientList.add(client);
        }
        
        return clientList;
    }
}
