package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CompletedCalls;
import DOS.PendingCalls;
import DOS.Sequence;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
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
public class PendingCallsDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`pendingCalls`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public PendingCallsDAO()
    {
        fields.add("idOrders");
        fields.add("reason");
        fields.add("created");
        fields.add("isActive");
        fields.add("deactivated");
        fields.add("deactivatedBy");
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

        PendingCalls pending = (PendingCalls) obj;
        try
        {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromPendingCall(pending, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            
            // For strange bug that occurs for Premier
            if (message.contains("Duplicate entry"))
                return Update(pending);
            
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

            PendingCalls pending = (PendingCalls) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idOrders` = " + pending.getIdOrders();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromPendingCall(pending, pStmt);

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

            PendingCalls pending = (PendingCalls) obj;
            String stmt = "DELETE FROM " + table
                    + " WHERE `idOrders` = " + pending.getIdOrders();

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
            PendingCalls pending = new PendingCalls();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetPendingCallFromResultSet(pending, rs);
            }

            rs.close();
            stmt.close();

            return pending;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /*public ArrayList<PendingCalls> getAllActive(int idOrder)
    {
        ArrayList<PendingCalls> activePendingCalls = new ArrayList<>();
        
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + idOrder
                    + " AND `isActive` = 1";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                activePendingCalls.add(SetPendingCallFromResultSet(new PendingCalls(), rs));
            }

            rs.close();
            stmt.close();
        }
        catch (SQLException sex)
        {
            System.out.println(sex.getMessage());
        }
        
        return activePendingCalls;
    }
    */

    private PendingCalls SetPendingCallFromResultSet(PendingCalls obj, ResultSet rs) throws SQLException
    {
        //obj.setIdPendingCalls(rs.getInt("idPendingCalls"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setReason(rs.getString("reason"));
        obj.setCreated(rs.getTimestamp("created"));
        obj.setIsActive(rs.getInt("isActive"));
        obj.setDeactivated(rs.getTimestamp("deactivated"));
        obj.setDeactivatedBy(rs.getInt("deactivatedBy"));

        return obj;
    }

    private PreparedStatement SetStatementFromPendingCall(PendingCalls obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getIdOrders());
        pStmt.setString(2, obj.getReason());
        pStmt.setTimestamp(3, obj.getCreated() == null ? null : Convert.ToSQLDateTime(obj.getCreated()));
        pStmt.setInt(4, obj.getIsActive());
        pStmt.setTimestamp(5, obj.getDeactivated() == null ? null : Convert.ToSQLDateTime(obj.getDeactivated()));
        SQLUtil.SafeSetInteger(pStmt, 6, obj.getDeactivatedBy());
        return pStmt;
    }
    
    public static CompletedCalls ConvertToCompletedCall(PendingCalls obj, int UserID)
    {
        CompletedCalls cc = new CompletedCalls(null, obj.getIdOrders(), obj.getReason(), obj.getCreated(), UserID, new Date());
        return cc;
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
