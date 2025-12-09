package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Batches;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

public class BatchesDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`batches`";
    private final ArrayList<String> fields = new ArrayList<>();

    public BatchesDAO()
    {
        fields.add("name");
        fields.add("description");
        fields.add("userId");
        fields.add("createdDate");
    }

    public Batches GetByName(String name) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        Batches batch = null;

        String query = "SELECT * FROM " + table
                + " WHERE `name` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, name);
        ResultSet rs = pStmt.executeQuery();

        if (rs.next())
        {
            batch = getFromResultSet(rs);
        }
        
        rs.close();
        pStmt.close();
        return batch;
    }
    
    public List<Batches> SearchByName(String name) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        List<Batches> batchList = new ArrayList<Batches>();

        String query = "SELECT * FROM " + table
                + " WHERE `name` LIKE ?";

        String searchParam = SQLUtil.createSearchParam(name);
        PreparedStatement pStmt = createStatement(con, query, searchParam); //con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();

        Batches batch;
        while (rs.next())
        {
            batch = getFromResultSet(rs);
            batchList.add(batch);
        }
        
        rs.close();
        pStmt.close();
        return batchList;
    }

    public Boolean insert(Batches batch) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(batch, pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }
    
    public Boolean update(Batches batch) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        String query = GenerateUpdateStatement(fields)
                    + " WHERE `idBatches` = " + batch.getIdBatches();
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetStatementValuesFromObject(batch, pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }

    public boolean delete(int idBatches) throws SQLException
    {
        String query = "DELETE FROM ? WHERE idBatches = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, table);
        pStmt.setInt(2, idBatches);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }

    private Batches getFromResultSet(ResultSet rs) throws SQLException
    {
        Batches batch = new Batches();
        batch.setIdBatches(rs.getInt("idBatches"));
        batch.setName(rs.getString("name"));
        batch.setDescription(rs.getString("description"));
        batch.setUserId(rs.getInt("userId"));
        batch.setCreatedDate(rs.getTimestamp("createdDate"));
        return batch;
    }

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

    private PreparedStatement SetStatementValuesFromObject(
            Batches batch,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetString(pStmt, ++i, batch.getName());
        SQLUtil.SafeSetString(pStmt, ++i, batch.getDescription());
        SQLUtil.SafeSetInteger(pStmt, ++i, batch.getUserId());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, batch.getCreatedDate());
        return pStmt;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return insert((Batches)obj);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return update((Batches)obj);
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            return delete(((Batches)obj).getIdBatches());
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        Batches batch = null;
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idBatches` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                batch = getFromResultSet(rs);
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return batch;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
