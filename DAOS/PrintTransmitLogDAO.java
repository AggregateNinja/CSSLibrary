package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.PrintTransmitLog;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;

import static Utility.SQLUtil.createStatement;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 3, 2016
 */
public class PrintTransmitLogDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`printTransmitLog`";

    private final ArrayList<String> fields = new ArrayList<>();

    public PrintTransmitLogDAO()
    {
        // all fields except id
        fields.add("action");
        fields.add("description");
        fields.add("userId");
        fields.add("orderId");
        fields.add("clientId");
        fields.add("releaseId");
        fields.add("createdDate");
        fields.add("updatedDate");
        fields.add("isError");
        fields.add("isRetransmit");
        fields.add("isRelease");
    }

    public boolean InsertPrintTransmitLog(String action, String description, int userId, Integer orderId,
            Integer clientId, Integer releaseId, Date createdDate, Date updatedDate, Boolean isError, Boolean isRetransmit, Boolean isRelease)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::InsertPrintTransmitLog - Exception while checking database connection: " + ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, action);
            SQLUtil.SafeSetString(pStmt, ++i, description);
            SQLUtil.SafeSetInteger(pStmt, ++i, userId);
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(createdDate));
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(updatedDate));
            SQLUtil.SafeSetBoolean(pStmt, ++i, isError, false);
            SQLUtil.SafeSetBoolean(pStmt, ++i, isRetransmit, false);
            SQLUtil.SafeSetBoolean(pStmt, ++i, isRelease, false);

            pStmt.executeUpdate();
            pStmt.close();

        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::InsertPrintTransmitLog - SQLException while inserting: " + ex.toString());
            System.out.println("PrintTransmitLogDAO::InsertPrintTransmitLog - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }

    public boolean UpdatePrintTransmitLog(PrintTransmitLog ptl)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::UpdatePrintTransmitLog - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idprintTransmitLog` = " + ptl.getIdprintTransmitLog();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetString(pStmt, ++i, ptl.getAction());
            SQLUtil.SafeSetString(pStmt, ++i, ptl.getDescription());
            SQLUtil.SafeSetInteger(pStmt, ++i, ptl.getUserId());
            SQLUtil.SafeSetInteger(pStmt, ++i, ptl.getOrderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, ptl.getClientId());
            SQLUtil.SafeSetInteger(pStmt, ++i, ptl.getReleaseId());
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(ptl.getCreatedDate()));
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, Convert.ToSQLDateTime(ptl.getUpdatedDate()));
            SQLUtil.SafeSetBoolean(pStmt, ++i, ptl.isError());
            SQLUtil.SafeSetBoolean(pStmt, ++i, ptl.isRetransmit());
            SQLUtil.SafeSetBoolean(pStmt, ++i, ptl.isRelease());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::UpdatePrintTransmitLog - Exception while updating PrintTransmitLog object: " + ex.toString());
            return false;
        }
    }
    
    public boolean UpdatePrintTransmitLogByReleaseAction(int releaseId, String action, String newDescription, Integer orderId, Integer clientId, boolean isError, boolean isRelease, boolean isRetransmit)
    {
        PrintTransmitLog ptl = GetPrintTransmitLogByReleaseAction(releaseId, action);
        if (ptl == null)
        {
            return false;
        }
        ptl.setDescription(newDescription);
        ptl.setOrderId(orderId);
        ptl.setClientId(clientId);
        ptl.setIsError(isError);
        ptl.setIsRelease(isRelease);
        ptl.setIsRetransmit(isRetransmit);
        ptl.setUpdatedDate(new Date());
        return UpdatePrintTransmitLog(ptl);
    }

    public boolean DeletePrintTransmitLog(int idprintTransmitLog)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::DeletePrintTransmitLog - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "DELETE FROM " + table + " WHERE `idprintTransmitLog` = " + idprintTransmitLog;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::DeletePrintTransmitLog - Exception while deleting PrintTransmitLog (id=" + idprintTransmitLog + "): " + ex.toString());
            return false;
        }
    }

    public PrintTransmitLog GetLastCompletedReleaseLog()
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetLastCompletedReleaseLog - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            PrintTransmitLog ptl = new PrintTransmitLog();

            String stmt = "SELECT * FROM " + table + " tl\n" +
                          " WHERE tl.isRelease = 1 AND tl.updatedDate IS NOT NULL\n" +
                          " ORDER BY tl.updatedDate DESC LIMIT 1";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                SetPrintTransmitLogFromRecordSet(ptl, rs);
            }

            rs.close();
            pStmt.close();

            return ptl;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetLastCompletedReleaseLog - Exception while getting PrintTransmitLog: " + ex.toString());
            return null;
        }
    }

    public PrintTransmitLog GetLastStartedReleaseLog()
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetLastStartedReleaseLog - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            PrintTransmitLog ptl = new PrintTransmitLog();

            String stmt = "SELECT * FROM " + table + " tl\n" +
                          " WHERE tl.isRelease = 1 AND tl.createdDate IS NOT NULL AND tl.isError = 0\n" +
                          " ORDER BY tl.createdDate DESC LIMIT 1";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                SetPrintTransmitLogFromRecordSet(ptl, rs);
            }

            rs.close();
            pStmt.close();

            return ptl;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetLastStartedReleaseLog - Exception while getting PrintTransmitLog: " + ex.toString());
            return null;
        }
    }

    public PrintTransmitLog GetPrintTransmitLogByID(int idprintTransmitLog)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetPrintTransmitLogByID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            PrintTransmitLog ptl = new PrintTransmitLog();

            String stmt = "SELECT * FROM " + table
                    + " WHERE `idprintTransmitLog` = " + idprintTransmitLog;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                SetPrintTransmitLogFromRecordSet(ptl, rs);
            }

            rs.close();
            pStmt.close();

            return ptl;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetPrintTransmitLogByID - Exception while getting PrintTransmitLog (id=" + idprintTransmitLog + "): " + ex.toString());
            return null;
        }
    }

    public PrintTransmitLog GetPrintTransmitLogByReleaseAction(int releaseId, String action)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetPrintTransmitLogByReleaseAction - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            PrintTransmitLog ptl = new PrintTransmitLog();

            String stmt = "SELECT * FROM " + table
                    + " WHERE `releaseId` = " + releaseId
                    + " AND UPPER(`action`) = UPPER(?)";
            PreparedStatement pStmt = createStatement(con, stmt, action);//con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetPrintTransmitLogFromRecordSet(ptl, rs);
            }

            rs.close();
            pStmt.close();

            return ptl;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetPrintTransmitLogByReleaseAction - Exception while getting PrintTransmitLog (release=" + releaseId + "): " + ex.toString());
            return null;
        }
    }
    
    public List<PrintTransmitLog> GetAllPrintTransmitLogsForUserID(int userId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForUserID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<PrintTransmitLog> ptlList = new ArrayList<PrintTransmitLog>();
        try
        {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `userId` = " + userId
                    + " AND `isError` = 0";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                PrintTransmitLog ptl = new PrintTransmitLog();
                SetPrintTransmitLogFromRecordSet(ptl, rs);
                ptlList.add(ptl);
            }

            rs.close();
            pStmt.close();

            return ptlList;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForUserID - Exception while getting PrintTransmitLogs (userId=" + userId + "): " + ex.toString());
            return ptlList;
        }
    }
    
    public List<PrintTransmitLog> GetAllPrintTransmitLogsForOrderID(int orderId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForOrderID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<PrintTransmitLog> ptlList = new ArrayList<PrintTransmitLog>();
        try
        {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + orderId
                    + " AND `isError` = 0";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                PrintTransmitLog ptl = new PrintTransmitLog();
                SetPrintTransmitLogFromRecordSet(ptl, rs);
                ptlList.add(ptl);
            }

            rs.close();
            pStmt.close();

            return ptlList;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForOrderID - Exception while getting PrintTransmitLogs (orderId=" + orderId + "): " + ex.toString());
            return ptlList;
        }
    }
    
    public List<PrintTransmitLog> GetAllPrintTransmitLogsForReleaseID(int releaseId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForReleaseID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<PrintTransmitLog> ptlList = new ArrayList<PrintTransmitLog>();
        try
        {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `releaseId` = " + releaseId
                    + " AND `isError` = 0";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                PrintTransmitLog ptl = new PrintTransmitLog();
                SetPrintTransmitLogFromRecordSet(ptl, rs);
                ptlList.add(ptl);
            }

            rs.close();
            pStmt.close();

            return ptlList;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForReleaseID - Exception while getting PrintTransmitLogs (releaseId=" + releaseId + "): " + ex.toString());
            return ptlList;
        }
    }
    
    public List<PrintTransmitLog> GetAllPrintTransmitLogsForUserIDOrderID(int userId, int orderId)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForUserIDOrderID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<PrintTransmitLog> ptlList = new ArrayList<PrintTransmitLog>();
        try
        {
            String stmt = "SELECT * FROM " + table
                    + " WHERE `userId` = " + userId
                    + " AND `orderId` = " + orderId
                    + " AND `isError` = 0";
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                PrintTransmitLog ptl = new PrintTransmitLog();
                SetPrintTransmitLogFromRecordSet(ptl, rs);
                ptlList.add(ptl);
            }

            rs.close();
            pStmt.close();

            return ptlList;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForUserIDOrderID - "
                    + "Exception while getting PrintTransmitLogs (userId=" + userId + ", orderId=" + orderId + "): " + ex.toString());
            return ptlList;
        }
    }
    
    public List<PrintTransmitLog> GetAllPrintTransmitLogsForDateRange(DateTime start, DateTime end)
    {
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForDateRange - Exception while checking database connection: " + ex.toString());
            return null;
        }

        java.sql.Date startSQLDate = new java.sql.Date(start.toDate().getTime());
        java.sql.Date endSQLDate = new java.sql.Date(end.toDate().getTime());
        List<PrintTransmitLog> ptlList = new ArrayList<PrintTransmitLog>();
        try
        {
            String sql = "SELECT * FROM " + table
                    + " WHERE `createdDate` BETWEEN ? AND ? "
                    + " AND `isError` = 0";
            int i=0;
            PreparedStatement pStmt = con.prepareStatement(sql);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, startSQLDate);
            SQLUtil.SafeSetTimeStamp(pStmt, ++i, endSQLDate);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                PrintTransmitLog ptl = new PrintTransmitLog();
                SetPrintTransmitLogFromRecordSet(ptl, rs);
                ptlList.add(ptl);
            }

            rs.close();
            pStmt.close();

            return ptlList;
        }
        catch (SQLException ex)
        {
            System.out.println("PrintTransmitLogDAO::GetAllPrintTransmitLogsForDateRange - "
                    + "Exception while getting PrintTransmitLogs (start=" + start + ", end=" + end + "): " + ex.toString());
            return ptlList;
        }
    }
    
    private PrintTransmitLog SetPrintTransmitLogFromRecordSet(PrintTransmitLog ptl, ResultSet rs) throws SQLException
    {
        ptl.setIdprintTransmitLog(rs.getInt("idprintTransmitLog"));
        ptl.setAction(rs.getString("action"));
        ptl.setDescription(rs.getString("description"));
        ptl.setUserId(rs.getInt("userId"));
        ptl.setOrderId(rs.getInt("orderId"));
        ptl.setClientId(rs.getInt("clientId"));
        ptl.setReleaseId(rs.getInt("releaseId"));
        ptl.setCreatedDate(rs.getTimestamp("createdDate"));
        ptl.setUpdatedDate(rs.getTimestamp("updatedDate"));
        ptl.setIsError(rs.getBoolean("isError"));
        ptl.setIsRetransmit(rs.getBoolean("isRetransmit"));
        ptl.setIsRelease(rs.getBoolean("isRelease"));
        return ptl;
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
    public Boolean Insert(Serializable obj)
    {
        if (obj instanceof PrintTransmitLog)
        {
            try
            {
                PrintTransmitLog ptl = (PrintTransmitLog) obj;
                return InsertPrintTransmitLog(ptl.getAction(), ptl.getDescription(), ptl.getUserId(), ptl.getOrderId(),
                        ptl.getClientId(), ptl.getReleaseId(), ptl.getCreatedDate(), ptl.getUpdatedDate(), ptl.isError(), ptl.isRetransmit(), ptl.isRelease());
            }
            catch (Exception ex)
            {
                System.out.println("PrintTransmitLogDAO::Insert - Exception while inserting PrintTransmitLog object: " + ex.toString());
                return false;
            }
        }
        System.out.println("PrintTransmitLogDAO::Insert - Cannot insert non-PrintTransmitLog object using PrintTransmitLogDAO.");
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        if (obj instanceof PrintTransmitLog)
        {
            try
            {
                PrintTransmitLog ptl = (PrintTransmitLog) obj;
                return UpdatePrintTransmitLog(ptl);
            }
            catch (Exception ex)
            {
                System.out.println("PrintTransmitLogDAO::Update - Exception while updating PrintTransmitLog object: " + ex.toString());
                return false;
            }
        }
        System.out.println("PrintTransmitLogDAO::Update - Cannot update non-PrintTransmitLog object using PrintTransmitLogDAO.");
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        if (obj instanceof PrintTransmitLog)
        {
            try
            {
                PrintTransmitLog ptl = (PrintTransmitLog) obj;
                return DeletePrintTransmitLog(ptl.getIdprintTransmitLog());
            }
            catch (Exception ex)
            {
                System.out.println("PrintTransmitLogDAO::Delete - Exception while deleting PrintTransmitLog object: " + ex.toString());
                return false;
            }
        }
        System.out.println("PrintTransmitLogDAO::Delete - Cannot delete non-PrintTransmitLog object using PrintTransmitLogDAO.");
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetPrintTransmitLogByID(ID);
        }
        catch (Exception ex)
        {
            System.out.println("PrintTransmitLogDAO::getByID - Exception while getting PrintTransmitLog (id=" + ID + "): " + ex.toString());
            return false;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
