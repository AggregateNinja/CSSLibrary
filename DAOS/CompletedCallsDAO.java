package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CompletedCalls;
import DOS.PendingCalls;
import DOS.Sequence;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * @date: Mar 4, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DAOS
 * @file name: PendingCallsDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class CompletedCallsDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`completedCalls`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public CompletedCallsDAO()
    {
        fields.add("idOrders");
        fields.add("reason");
        fields.add("created");
        fields.add("comment");
        fields.add("user");
        fields.add("actionDateTime");
        fields.add("lastCall");
    }

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
            CompletedCalls completed = (CompletedCalls) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);


            SetStatmentFromCompletedCall(completed, pStmt);

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

            CompletedCalls completed = (CompletedCalls) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idcompletedCalls` = " + completed.getIdcompletedCalls();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatmentFromCompletedCall(completed, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

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

        try
        {

            CompletedCalls completed = (CompletedCalls) obj;
            String stmt = "DELETE FROM " + table
                    + " WHERE `idcompletedCalls` = " + completed.getIdOrders();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeQuery();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

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
            CompletedCalls completed = new CompletedCalls();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idcompletedCalls` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetCompletedCallFromResultSet(completed, rs);
            }

            rs.close();
            stmt.close();

            return completed;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<CompletedCalls> getByOrderID(Integer OrderID)
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
            ArrayList<CompletedCalls> cclist = new ArrayList<>();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + OrderID;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                CompletedCalls completed = new CompletedCalls();
                SetCompletedCallFromResultSet(completed, rs);
                cclist.add(completed);
            }

            rs.close();
            stmt.close();

            return cclist;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public CompletedCalls getLastCall(int idOrder)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE idOrders = " + idOrder;
            ResultSet rs = stmt.executeQuery(query);
            
            CompletedCalls completed = null;
            while (rs.next())
            {
                if (rs.last())
                {
                    completed = new CompletedCalls();
                    SetCompletedCallFromResultSet(completed, rs);
                }
            }
            return completed;
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return null;
        }
    }
    
    private CompletedCalls SetCompletedCallFromResultSet(CompletedCalls obj, ResultSet rs) throws SQLException
    {
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setReason(rs.getString("reason"));
        obj.setCreated(rs.getDate("created"));
        obj.setComment(rs.getString("comment"));
        obj.setUser(rs.getInt("user"));
        obj.setActionDateTime(rs.getTimestamp("actionDateTime"));
        obj.setLastCall(rs.getBoolean("lastCall"));

        return obj;
    }

    private PreparedStatement SetStatmentFromCompletedCall(CompletedCalls obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdOrders());
        pStmt.setString(2, obj.getReason());
        pStmt.setTimestamp(3, Convert.ToSQLDateTime(obj.getCreated()));
        pStmt.setString(4, obj.getComment());
        pStmt.setInt(5, obj.getUser());
        pStmt.setTimestamp(6, Convert.ToSQLDateTime(obj.getActionDateTime()));
        pStmt.setBoolean(7, obj.getLastCall());
        return pStmt;
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
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
