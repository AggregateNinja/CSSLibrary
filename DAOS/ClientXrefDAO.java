//------------------------------------------------------------------------------
/*
 * Computer Service & Support, Inc.  All Rights Reserved Oct 23, 2014
 */
//------------------------------------------------------------------------------
package DAOS;

//------------------------------------------------------------------------------
import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ClientXref;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//------------------------------------------------------------------------------
/**
 *
 * @author Michael
 */
//------------------------------------------------------------------------------
public class ClientXrefDAO implements DAOInterface, IStructureCheckable
{

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`clientXref`";
    private final ArrayList<String> fields = new ArrayList<>();

    //--------------------------------------------------------------------------
    public ClientXrefDAO()
    {
        fields.add("idxrefs");
        fields.add("idClients");
        fields.add("transformedIn");
        fields.add("transformedOut");
        fields.add("use1");
        fields.add("use2");
        fields.add("use3");
        fields.add("use4");
        fields.add("use5");
        fields.add("description");
        fields.add("created");
        fields.add("active");
    }

    //--------------------------------------------------------------------------
    private void CheckDBConnection()
    {
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        try
        {
            ClientXref cliXref = (ClientXref) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(cliXref, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        try
        {
            ClientXref cliXref = (ClientXref) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + cliXref.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(cliXref, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }

        return false;
    }

    //--------------------------------------------------------------------------
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ClientXref cliXref = new ClientXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(cliXref, rs);
            }

            rs.close();
            pStmt.close();

            return cliXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    public ClientXref GetClientXrefByNumber(int number)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ClientXref cliXref = new ClientXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `clientNo` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, number);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(cliXref, rs);
            }

            rs.close();
            pStmt.close();

            return cliXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    public ClientXref GetClientXrefByClientIDAndXrefId(int num, int id)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ClientXref cliXref = new ClientXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `idClients` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setInt(2, num);
            pStmt.setBoolean(3, true);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(cliXref, rs);
            }

            rs.close();
            pStmt.close();

            return cliXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    public ClientXref GetClientXrefByTransInAndXrefId(String in, int id)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ClientXref cliXref = new ClientXref();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `transformedIn` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            SQLUtil.SafeSetString(pStmt, 2, in);
            pStmt.setBoolean(3, true);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetXrefFromResultSet(cliXref, rs);
            }

            rs.close();
            pStmt.close();

            return cliXref;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    public ArrayList<ClientXref> GetAllByXref(int id)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            ClientXref cliXref = new ClientXref();
            ArrayList<ClientXref> list = new ArrayList<>();
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setBoolean(2, true);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                cliXref = new ClientXref();
                SetXrefFromResultSet(cliXref, rs);
                list.add(cliXref);
            }

            rs.close();
            pStmt.close();

            return list;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    public ResultSet GetRSAllByXref(int id)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }

        try
        {
            String query = "SELECT * FROM " + table
                    + " WHERE `idxrefs` = ?"
                    + " AND `active` = ?";
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, id);
            pStmt.setBoolean(2, true);

            ResultSet rs = pStmt.executeQuery();

            return rs;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    //--------------------------------------------------------------------------
    private String GenerateInsertStatement(ArrayList<String> fields)
    {
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    //--------------------------------------------------------------------------
    private String GenerateUpdateStatement(ArrayList<String> fields)
    {
        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    //--------------------------------------------------------------------------
    private ClientXref SetXrefFromResultSet(ClientXref xref, ResultSet rs) throws SQLException
    {
        xref.setId(rs.getInt("id"));
        xref.setIdxrefs(rs.getInt("idxrefs"));
        xref.setIdClients(rs.getInt("idClients"));
        xref.setTransformedIn(rs.getString("transformedIn"));
        xref.setTransformedOut(rs.getString("transformedOut"));
        xref.setUse1(rs.getString("use1"));
        xref.setUse2(rs.getString("use2"));
        xref.setUse3(rs.getString("use3"));
        xref.setUse4(rs.getString("use4"));
        xref.setUse5(rs.getString("use5"));
        xref.setDescription(rs.getString("description"));
        xref.setCreated(rs.getTimestamp("created"));
        xref.setActive(rs.getBoolean("active"));

        return xref;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(ClientXref obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdxrefs());
        pStmt.setInt(2, obj.getIdClients());
        SQLUtil.SafeSetString(pStmt, 3, obj.getTransformedIn());
        SQLUtil.SafeSetString(pStmt, 4, obj.getTransformedOut());
        SQLUtil.SafeSetString(pStmt, 5, obj.getUse1());
        SQLUtil.SafeSetString(pStmt, 6, obj.getUse2());
        SQLUtil.SafeSetString(pStmt, 7, obj.getUse3());
        SQLUtil.SafeSetString(pStmt, 8, obj.getUse4());
        SQLUtil.SafeSetString(pStmt, 9, obj.getUse5());
        SQLUtil.SafeSetString(pStmt, 10, obj.getDescription());
        SQLUtil.SafeSetTimeStamp(pStmt, 11, obj.getCreated());
        SQLUtil.SafeSetBoolean(pStmt, 12, obj.getActive());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
