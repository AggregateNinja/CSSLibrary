package DAOS;

import DAOS.IDAOS.IDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientProperty;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * Allows client rows to have zero-to-many properties associated with them via
 * the clientPropertyLookup table. Rows flagged as system properties are
 * available as choices to users, but cannot be deactivated or altered.
 * Properties flagged as not visible do not appear at all through the UI and
 * can be used as system flags for CSS.
 * @author TomR
 */
public class ClientPropertyDAO implements IDAO<ClientProperty>, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientProperties`";
    private final ArrayList<String> fields = new ArrayList<>();
    
    public ClientPropertyDAO()
    {
        fields.add("name");
        fields.add("description");        
        fields.add("isUserSelectable");
        fields.add("isUserEditable");
        fields.add("isUserVisible");
        fields.add("active");
        fields.add("createdByUserId");
        fields.add("createdDate");
    }

    /**
     * Inserts a new clientProperties row. Returns the object with the new
     *  database identifier attached.
     * @param clientProperty Object to use for insert
     * @return Object with unique database identifier attached
     * @throws SQLException On SQL error trying to insert/retrieve unique identifier
     * @throws IllegalArgumentException On invalid/null parameters
     * @throws NullPointerException 
     */
    @Override
    public ClientProperty Insert(ClientProperty clientProperty) 
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (clientProperty == null)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::Insert: "
                    + "Received [NULL] ClientProperty object");
        }
        
        if (clientProperty.getId() > 0)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::Insert: "
                    + "Supplied ClientProperty already has "
                    + "a unique database identifier ("
                    +clientProperty.getId() +") Use Update method instead");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "INSERT INTO " + table +
                " (name,description,isUserSelectable,isUserEditable,isUserVisible,active,createdByUserId)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pStmt = con.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        int i = 0;
        pStmt.setString(++i, clientProperty.getName());
        SQLUtil.SafeSetString(pStmt, ++i, clientProperty.getDescription());
        pStmt.setBoolean(++i, clientProperty.isUserSelectable());
        pStmt.setBoolean(++i, clientProperty.isUserEditable());
        pStmt.setBoolean(++i, clientProperty.isUserVisible());
        pStmt.setBoolean(++i, clientProperty.isActive());
        pStmt.setInt(++i, clientProperty.getCreatedByUserId());
        
        pStmt.execute();
        Integer clientPropertyId = null;
        try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
        {
            if (generatedKeys.next())
            {
                clientPropertyId = generatedKeys.getInt(1);
                if (clientPropertyId == null)
                    throw new SQLException("Unique identifier returned NULL");
            }
            else
            {
                throw new SQLException("Insert failed, no ID obtained.");
            }
        }
        clientProperty.setId(clientPropertyId);
        return clientProperty;
    }

    /**
     * Updates an existing clientProperties row, using the unique database
     *  identifier of the supplied object. Returns the object as it was passed
     *  in.
     * @param clientProperty
     * @return Same object that was passed in
     * @throws SQLException On SQL error trying to update
     * @throws IllegalArgumentException On invalid/null parameters
     * @throws NullPointerException 
     */
    @Override
    public ClientProperty Update(ClientProperty clientProperty)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (clientProperty == null)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::Update: "
                    + "Received [NULL] ClientProperty object");
        }
        
        if (clientProperty.getId() == 0)
        {
            throw new IllegalArgumentException("ClientPropertYDAO::Update: "
                    + "Supplied ClientProperty does not have a unique"
                    + "identifier. Use Insert method instead");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "UPDATE " + table + " SET "
                + " name = ?,"
                + " description = ?,"
                + " isUserSelectable = ?,"
                + " isUserEditable = ?,"
                + " isUserVisible = ?,"
                + " active = ?"
                + " WHERE id = " + clientProperty.getId();
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        int i = 0;
        pStmt.setString(++i, clientProperty.getName());
        SQLUtil.SafeSetString(pStmt, ++i, clientProperty.getDescription());
        pStmt.setBoolean(++i, clientProperty.isUserSelectable());
        pStmt.setBoolean(++i, clientProperty.isUserEditable());
        pStmt.setBoolean(++i, clientProperty.isUserVisible());
        pStmt.setBoolean(++i, clientProperty.isActive());        
        pStmt.executeUpdate();
        return clientProperty;        
    }

    /**
     * Marks the property row as no longer active (not available for assignment
     *  on new clients) using the unique identifier.
     * @param clientProperty
     * @throws SQLException On SQL error trying to update
     * @throws IllegalArgumentException On invalid/null parameters
     * @throws NullPointerException  
     */
    @Override
    public void Delete(ClientProperty clientProperty)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (clientProperty == null)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::Delete: "
                    + "Received [NULL] ClientProperty object");
        }
        
        if (clientProperty.getId() == 0)
        {
            throw new IllegalArgumentException("ClientPropertYDAO::Delete: "
                    + "Supplied ClientProperty does not have a unique"
                    + "identifier");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }        
        
        clientProperty.setActive(false);
        Update(clientProperty);
    }
    
    /**
     * Returns ClientProperty object with the given name and active status
     *  matching the activeOnly parameter.
     * NULL returned if no row matches the supplied name.
     * @param name
     * @param activeOnly
     * @return
     * @throws SQLException On SQL error trying to retrieve row
     * @throws IllegalArgumentException On invalid/null parameters
     * @throws NullPointerException  
     */
    public ClientProperty GetByName(String name, boolean activeOnly)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (name == null)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::GetByName: "
                    + "Received [NULL] name object");
        }
        
        if (name.isEmpty())
        {
            throw new IllegalArgumentException("ClientPropertyDAO::GetByName: "
                    + "Received empty name object");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT * FROM " + table + " WHERE `name` = ?";
        if (activeOnly) sql += " AND active = 1";
        
        PreparedStatement pStmt = createStatement(con, sql, name);//con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        ClientProperty clientProperty = null;
        if (rs.next())
        {
             clientProperty = FromResultSet(rs);
        }
        return clientProperty;
    }
    
    /**
     * Returns ClientProperty object using supplied unique database identifier.
     * NULL returned if no row matches the supplied ID. Does not care whether
     * the row is marked "active" or not.
     * @param ID
     * @return
     * @throws SQLException On SQL error trying to retrieve row
     * @throws IllegalArgumentException On invalid/null parameters
     * @throws NullPointerException  
     */
    @Override
    public ClientProperty GetById(Integer ID)
            throws SQLException, IllegalArgumentException, NullPointerException
    {
        if (ID == null)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::GetById: "
                    + "Received [NULL] ClientProperty object");
        }
        
        if (ID == 0)
        {
            throw new IllegalArgumentException("ClientPropertyDAO::GetById: "
                    + "Received ClientProperty object with ID of zero (0)");
        }
        
        if (con.isValid(2) == false)
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        
        String sql = "SELECT * FROM " + table + " WHERE id=" + ID;
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        ResultSet rs = pStmt.executeQuery();
        
        ClientProperty clientProperty = null;
        if (rs.next())
        {
             clientProperty = FromResultSet(rs);
        }
        return clientProperty;
    }

    public static ClientProperty FromResultSet(ResultSet rs) throws SQLException
    {
        ClientProperty clientProperty = new ClientProperty();
        clientProperty.setId(rs.getInt("id"));
        clientProperty.setName(rs.getString("name"));
        clientProperty.setDescription(rs.getString("description"));
        clientProperty.setUserSelectable(rs.getBoolean("isUserSelectable"));
        clientProperty.setUserEditable(rs.getBoolean("isUserEditable"));
        clientProperty.setUserVisible(rs.getBoolean("isUserVisible"));
        clientProperty.setActive(rs.getBoolean("active"));
        clientProperty.setCreatedByUserId(rs.getInt("createdByUserId"));
        clientProperty.setCreatedDate(rs.getTimestamp("createdDate"));
        
        return clientProperty;
    }
	
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
