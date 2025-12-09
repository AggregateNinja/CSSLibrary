package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.BatchOrders;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
//import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
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
import java.sql.SQLIntegrityConstraintViolationException;

public class BatchOrdersDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`batchOrders`";
    private final ArrayList<String> fields = new ArrayList<>();
    private final ArrayList<String> updateFields = new ArrayList<>();

    public BatchOrdersDAO()
    {
        fields.add("batchId");
        fields.add("accession");
        fields.add("userId");
        fields.add("well");
        fields.add("active");
        fields.add("filename");
        fields.add("createdDate");
        
        updateFields.add("userId");
        updateFields.add("well");
        updateFields.add("active");
        updateFields.add("filename");
        updateFields.add("createdDate");
    }

    public ArrayList<BatchOrders> GetByBatchId(String batchId, boolean active) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        ArrayList<BatchOrders> batchOrders = new ArrayList<>();

        String query = "SELECT * FROM " + table
                + " WHERE `batchId` = ? AND `active` = ?";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, batchId);
        pStmt.setBoolean(2, active);
        ResultSet rs = pStmt.executeQuery();

        while (rs.next())
        {
            BatchOrders batchOrder = getFromResultSet(rs);
            if (batchOrder != null && batchOrder.getIdBatchOrders() != null && batchOrder.getIdBatchOrders() > 0)
            {
                batchOrders.add(batchOrder);
            }
        }
        rs.close();
        pStmt.close();
        return batchOrders;
    }
    
    public List<BatchOrders> GetByAccession(String accession, Boolean active) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        List<BatchOrders> batchOrderList = new ArrayList<BatchOrders>();
        BatchOrders batchOrder = null;

        String query = "SELECT * FROM " + table
                + " WHERE `accession` = ?";
        if (active != null)
        {
            query += " AND `active` = " + active.booleanValue();
        }

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, accession);
        ResultSet rs = pStmt.executeQuery();

        while (rs.next())
        {
            batchOrder = getFromResultSet(rs);
            batchOrderList.add(batchOrder);
        }
        rs.close();
        pStmt.close();
        return batchOrderList;
    }
    
    public BatchOrders GetByAccessionBatchID(String accession, Integer batchID, Boolean active) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        BatchOrders batchOrder = null;

        String query = "SELECT * FROM " + table
                + " WHERE `accession` = ? AND `batchID` = ?";
        if (active != null)
        {
            query += " AND `active` = " + active.booleanValue();
        }

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, accession);
        pStmt.setInt(2, batchID);
        ResultSet rs = pStmt.executeQuery();

        if (rs.next())
        {
            batchOrder = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return batchOrder;
    }
    
    public BatchOrders GetLastBatch(String accession) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        BatchOrders batchOrder = null;

        String query = "SELECT * FROM " + table
                + " WHERE `accession` = ? AND `active` = 1 ORDER BY `createdDate` DESC LIMIT 1";

        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, accession);
        ResultSet rs = pStmt.executeQuery();

        if (rs.next())
        {
            batchOrder = getFromResultSet(rs);
        }
        rs.close();
        pStmt.close();
        return batchOrder;
    }
    
    public List<BatchOrders> SearchByAccession(String accession, Boolean active) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        List<BatchOrders> batchOrderList = new ArrayList<BatchOrders>();

        String query = "SELECT * FROM " + table
                + " WHERE `accession` LIKE ?";
        if (active != null)
        {
            query += " AND `active` = " + active;
        }

        String searchParam = SQLUtil.createSearchParam(accession);
        PreparedStatement pStmt = createStatement(con, query, searchParam);//con.prepareStatement(query);
        ResultSet rs = pStmt.executeQuery();

        BatchOrders batchOrder;
        while (rs.next())
        {
            batchOrder = getFromResultSet(rs);
            batchOrderList.add(batchOrder);
        }
        
        rs.close();
        pStmt.close();
        return batchOrderList;
    }

    public Boolean insert(BatchOrders batchOrder) throws SQLException, SQLIntegrityConstraintViolationException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        String query = GenerateInsertStatement(fields);
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetInsertStatementValuesFromObject(batchOrder, pStmt);
        System.out.println(pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }
    
    public Boolean update(BatchOrders batchOrder) throws SQLException
    {
        if (con.isClosed())
        {
            con = CheckDBConnection.Check(dbs, con);
        }
        String query = GenerateUpdateStatement(updateFields)
                    + " WHERE `idBatchOrders` = " + batchOrder.getIdBatchOrders();
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt = SetUpdateStatementValuesFromObject(batchOrder, pStmt);
        pStmt.executeUpdate();
        pStmt.close();
        return true;
    }

    public boolean delete(int idBatchOrders) throws SQLException
    {
        String query = "DELETE FROM ? WHERE idBatchOrders = ?";
        PreparedStatement pStmt = con.prepareStatement(query);
        pStmt.setString(1, table);
        pStmt.setInt(2, idBatchOrders);
        int affected = pStmt.executeUpdate();
        return (affected > 0);
    }

    private BatchOrders getFromResultSet(ResultSet rs) throws SQLException
    {
        BatchOrders batchOrder = new BatchOrders();
        batchOrder.setIdBatchOrders(rs.getInt("idBatchOrders"));
        batchOrder.setBatchId(rs.getInt("batchId"));
        batchOrder.setAccession(rs.getString("accession"));
        batchOrder.setUserId(rs.getInt("userId"));
        batchOrder.setWell(rs.getString("well"));
        batchOrder.setActive(rs.getBoolean("active"));
        batchOrder.setFilename(rs.getString("filename"));
        batchOrder.setCreatedDate(rs.getTimestamp("createdDate"));
        return batchOrder;
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
        values += ")";// ON DUPLICATE KEY UPDATE `userId` = ?, `well` = ?, `active` = ?, `filename` = ?, `createdDate` = ?";
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

    private PreparedStatement SetInsertStatementValuesFromObject(
            BatchOrders batchOrder,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, batchOrder.getBatchId());
        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getAccession());
        SQLUtil.SafeSetInteger(pStmt, ++i, batchOrder.getUserId());
        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getWell());
        SQLUtil.SafeSetBoolean(pStmt, ++i, batchOrder.getActive());
        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getFilename());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, batchOrder.getCreatedDate());
//        SQLUtil.SafeSetInteger(pStmt, ++i, batchOrder.getUserId());
//        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getWell());
//        SQLUtil.SafeSetBoolean(pStmt, ++i, batchOrder.getActive());
//        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getFilename());
//        SQLUtil.SafeSetTimeStamp(pStmt, ++i, batchOrder.getCreatedDate());
        return pStmt;
    }

    private PreparedStatement SetUpdateStatementValuesFromObject(
            BatchOrders batchOrder,
            PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, batchOrder.getUserId());
        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getWell());
        SQLUtil.SafeSetBoolean(pStmt, ++i, batchOrder.getActive());
        SQLUtil.SafeSetString(pStmt, ++i, batchOrder.getFilename());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, batchOrder.getCreatedDate());
        return pStmt;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        try
        {
            return insert((BatchOrders)obj);
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        try
        {
            return update((BatchOrders)obj);
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        try
        {
            return delete(((BatchOrders)obj).getIdBatchOrders());
        }
        catch (ClassCastException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NullPointerException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        BatchOrders batchOrders = null;
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.setInt(1, ID);
            ResultSet rs = pStmt.executeQuery();
            if (rs.next())
            {
                batchOrders = getFromResultSet(rs);
            }
            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            Logger.getLogger(BatchOrdersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return batchOrders;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
