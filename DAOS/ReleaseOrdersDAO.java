/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReleaseOrders;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import Utility.WriteTextFile;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static Utility.SQLUtil.createStatement;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 5, 2016 - Apr 21, 2016
 */
public class ReleaseOrdersDAO implements DAOInterface, IStructureCheckable  {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`releaseOrders`";
    private final int RETRY_ATTEMPTS = 3;
    private final int CONNECTION_VALIDATION_TIMEOUT = 3; // seconds

    private final List<String> fields = new ArrayList<>();

    public ReleaseOrdersDAO()
    {
        // all fields except id
        fields.add("releaseId");
        fields.add("orderId");
        fields.add("clientId");
        fields.add("queued");
        fields.add("inProcess");
    }
    
    public boolean InsertReleaseOrders(int releaseId, int orderId, int clientId, boolean queued, boolean inProcess)
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
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - Exception while checking database connection: " + ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, queued);
            SQLUtil.SafeSetBoolean(pStmt, ++i, inProcess);

            pStmt.executeUpdate();
            pStmt.close();

        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }
    
    public boolean InsertNewReleaseOrders(List<ReleaseOrders> releaseOrderList, boolean queue)
    {
        if (releaseOrderList == null || releaseOrderList.isEmpty())
        {
            // returning true when given nothing since we successfully inserted nothing
            return true;
        }
        
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - Exception while checking database connection: " + ex.toString());
            return false;
        }

        String stmt = "INSERT INTO " + table + "(";
        for (int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            if (i != fields.size() - 1)
            {
                stmt += ", ";
            }
        }
        stmt += ") ";
        
        String values = "VALUES ";
        for (ReleaseOrders relOrder : releaseOrderList)
        {
            values += "(";
            for (int i = 0; i < fields.size(); i++)
            {
                values += "?";
                if (i < fields.size() - 1)
                    values += ",";
            }
            values += "),";
        }
        values = values.replaceAll(",$", ";");
        stmt += values;
        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            for (ReleaseOrders relOrder : releaseOrderList)
            {
                SQLUtil.SafeSetInteger(pStmt, ++i, relOrder.getReleaseId());
                SQLUtil.SafeSetInteger(pStmt, ++i, relOrder.getOrderId());
                SQLUtil.SafeSetInteger(pStmt, ++i, relOrder.getClientId());
                SQLUtil.SafeSetBoolean(pStmt, ++i, queue);
                SQLUtil.SafeSetBoolean(pStmt, ++i, relOrder.isInProcess());
            }
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }
    
    public Integer InsertReleaseOrdersGetId(int releaseId, int orderId, int clientId, boolean queued, boolean inProcess)
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
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - Exception while checking database connection: " + ex.toString());
            return null;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, queued);
            SQLUtil.SafeSetBoolean(pStmt, ++i, inProcess);

            pStmt.executeUpdate();
            
            Integer idReleaseOrders;            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    idReleaseOrders = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return idReleaseOrders;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseOrdersDAO::InsertReleaseOrder - SQL statement: " + stmt.toString());
            return null;
        }
    }
    
    public boolean UpdateReleaseOrders(int idreleaseOrders, int releaseId, int orderId, int clientId, boolean queued, boolean inProcess)
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
            System.out.println("ReleaseOrdersDAO::UpdateReleaseOrders - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idreleaseOrders` = " + idreleaseOrders;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, orderId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, queued);
            SQLUtil.SafeSetBoolean(pStmt, ++i, inProcess);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::UpdateReleaseOrders - Exception while updating ReleaseOrders object: " + ex.toString());
            return false;
        }
    }
    
    public boolean DeleteReleaseOrders(int idreleaseOrders)
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
            System.out.println("ReleaseOrdersDAO::DeleteReleaseOrders - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "DELETE FROM " + table + " WHERE `idreleaseOrders` = " + idreleaseOrders;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::DeleteReleaseOrders - Exception while deleting ReleaseOrders (id=" + idreleaseOrders + "): " + ex.toString());
            return false;
        }
    }
    
    public ReleaseOrders GetReleaseOrdersByID(int idreleaseOrders)
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
            System.out.println("ReleaseOrdersDAO::GetReleaseOrdersByID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            ReleaseOrders ro = new ReleaseOrders();

            String stmt = "SELECT * FROM " + table
                    + " WHERE `idreleaseOrders` = " + idreleaseOrders;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                ro.setIdreleaseOrders(rs.getInt("idreleaseOrders"));
                ro.setReleaseId(rs.getInt("releaseId"));
                ro.setOrderId(rs.getInt("orderId"));
                ro.setClientId(rs.getInt("clientId"));
                ro.setQueued(rs.getBoolean("queued"));
                ro.setQueued(rs.getBoolean("inProcess"));
            }

            rs.close();
            pStmt.close();

            return ro;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleaseOrdersByID - Exception while getting PrintTransmitLog (id=" + idreleaseOrders + "): " + ex.toString());
            return null;
        }
    }
    
    public boolean AddAllOrdersToRelease(int releaseId, int clientId, List<Integer> orderIdList, boolean queued, boolean inProcess)
    {
        boolean error = false;
        for (Integer orderId : orderIdList)
        {
            if (orderId != null)
            {
                error = (error || InsertReleaseOrders(releaseId, orderId, clientId, queued, inProcess));
            }
        }
        return !error;
    }
    
    public List<Integer> GetAllOrderIdsForRelease(int releaseId)
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
            System.out.println("ReleaseOrdersDAO::GetAllOrderIdsForRelease - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<Integer> orderIdList = new ArrayList<Integer>();
        try
        {
            String stmt = "SELECT `orderId` FROM " + table
                    + " WHERE `releaseId` = " + releaseId;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                orderIdList.add(rs.getInt("orderId"));
            }

            rs.close();
            pStmt.close();

            return orderIdList;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetAllOrderIdsForRelease - Exception while getting order IDs (releaseId=" + releaseId + "): " + ex.toString());
            return orderIdList;
        }
    }
    
    public List<ReleaseOrders> GetReleaseOrdersForReleaseClient(String releaseId, String clientId)
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
            System.out.println("ReleaseOrdersDAO::GetReleaseOrdersForReleaseClient - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<ReleaseOrders> clientReleaseList = new ArrayList<ReleaseOrders>();
        try
        {
            String stmt = "SELECT `idreleaseOrders`,`releaseId`,`clientId`,`orderId`,`queued`,`inProcess` FROM " + table
                    + " WHERE `releaseId` = ?"
                    + " AND `clientId` = ?";
            
            PreparedStatement pStmt = createStatement(con, stmt, releaseId, clientId);//con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                ReleaseOrders relOrder = new ReleaseOrders();
                relOrder.setIdreleaseOrders(rs.getInt("idreleaseOrders"));
                relOrder.setClientId(rs.getInt("clientId"));
                relOrder.setReleaseId(rs.getInt("releaseId"));
                relOrder.setOrderId(rs.getInt("orderId"));
                relOrder.setQueued(rs.getBoolean("queued"));
                relOrder.setQueued(rs.getBoolean("inProcess"));
                clientReleaseList.add(relOrder);
            }

            rs.close();
            pStmt.close();

            return clientReleaseList;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleaseOrdersForReleaseClient - Exception while getting order IDs (releaseId=" + releaseId + "): " + ex.toString());
            return clientReleaseList;
        }
    }
    
    public Integer GetReleasesQueuedCount()
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
            System.out.println("ReleaseOrdersDAO::GetReleasesQueuedCount - Exception while checking database connection: " + ex.toString());
            return null;
        }

        Integer releaseCount = null;
        try
        {
            String stmt = "SELECT COUNT(DISTINCT `releaseId`) FROM " + table
                    + " WHERE `queued` = 1";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);
            if (rs.next())
            {
                releaseCount = rs.getInt(1);
            }

            rs.close();
            pStmt.close();

            return releaseCount;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleasesQueuedCount - Exception while getting release map: " + ex.toString());
            return releaseCount;
        }
    }
    
    public Integer GetReleasesInProcessCount()
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
            System.out.println("ReleaseOrdersDAO::GetReleasesInProcessCount - Exception while checking database connection: " + ex.toString());
            return null;
        }

        Integer releaseCount = null;
        try
        {
            String stmt = "SELECT COUNT(DISTINCT `releaseId`) FROM " + table
                    + " WHERE `inProcess` = 1";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);
            if (rs.next())
            {
                releaseCount = rs.getInt(1);
            }

            rs.close();
            pStmt.close();

            return releaseCount;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleasesInProcessCount - Exception while getting release map: " + ex.toString());
            return releaseCount;
        }
    }
    
    public Map<Integer, Map<Integer, List<ReleaseOrders>>> GetQueuedReleaseMap(WriteTextFile writeLog)
    {
        Map<Integer, Map<Integer, List<ReleaseOrders>>> releaseMap = new TreeMap<Integer, Map<Integer, List<ReleaseOrders>>>();
        for (int i = 0; i < RETRY_ATTEMPTS; i++)
        {
            try
            {
                if (con == null || (this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT)))
                {
                    this.con = CheckDBConnection.Check(this.dbs, this.con);
                }
                else
                {
                    break;
                }
            }
            catch (SQLException ex)
            {
                System.out.println(ex.toString());
                if (writeLog != null)
                {
                    writeLog.write("Connection attempt " + i + " failed : " + ex.toString());
                }
                if (i == RETRY_ATTEMPTS)
                {
                    System.out.println("ReleaseOrdersDAO::GetAllReleaseIdForOrderId - Exception while checking database connection: " + ex.toString());
                    return releaseMap;
                }
            }
        }
        try
        {
            if (con == null || (this.con.isClosed()) || (!this.con.isValid(CONNECTION_VALIDATION_TIMEOUT)))
            {
                if (writeLog != null)
                {
                    writeLog.write(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " - Could not establish a database connection");
                }
                return null;
            }
        }
        catch (SQLException ex)
        {
            if (writeLog != null)
            {
                writeLog.write("ReleaseOrdersDAO::GetQueuedReleaseMap - " + ex.toString() + " - " + ex.getMessage());
            }
            return null;
        }

        Map<Integer, List<ReleaseOrders>> clientReleaseMap = new TreeMap<Integer, List<ReleaseOrders>>();
        List<ReleaseOrders> clientReleaseList = new ArrayList<ReleaseOrders>();
        try
        {
            String stmt = "SELECT `releaseId`, `clientId`, `orderId`, `inProcess` FROM " + table
                    + " WHERE `queued` = 1";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);
            int releaseId, prevReleaseId = -1;
            int clientId, prevClientId = -1;
            boolean nextRelease = false;
            while (rs.next())
            {
                releaseId = rs.getInt("releaseId");
                clientId = rs.getInt("clientId");
                nextRelease = releaseId != prevReleaseId;
                
                if (nextRelease)
                {
                    clientReleaseMap = new HashMap<Integer, List<ReleaseOrders>>();
                }
                if (nextRelease || clientId != prevClientId)
                {
                    clientReleaseList = new ArrayList<ReleaseOrders>();
                }
                
                ReleaseOrders relOrder = new ReleaseOrders();
                relOrder.setClientId(clientId);
                relOrder.setIdreleaseOrders(releaseId);
                relOrder.setOrderId(rs.getInt("orderId"));
                relOrder.setQueued(true);
                relOrder.setInProcess(rs.getBoolean("inProcess"));
                
                clientReleaseList.add(relOrder);
                clientReleaseMap.put(clientId, clientReleaseList);
                releaseMap.put(releaseId, clientReleaseMap);
                prevReleaseId = releaseId;
                prevClientId = clientId;
            }

            rs.close();
            pStmt.close();

            return releaseMap;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetQueuedReleaseMap - Exception while getting release map: " + ex.toString());
            return releaseMap;
        }
    }
    
    public boolean IsReleaseFullyInProcess(int releaseId)
    {
        boolean fullyInProcess = false;
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleasesInProcessCount - Exception while checking database connection: " + ex.toString());
            return fullyInProcess;
        }

        Integer releaseCount = null;
        try
        {
            String stmt = "SELECT COUNT(`orderId`) FROM " + table
                    + " WHERE `releaseId` = " + releaseId
                    + " AND `queued` = 1 AND `inProcess` = 0";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);
            if (rs.next())
            {
                releaseCount = rs.getInt(1);
            }
            
            if (releaseCount != null && releaseCount > 0)
            {
                fullyInProcess = true;
            }

            rs.close();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetReleasesInProcessCount - Exception while getting release map: " + ex.toString());
        }
        return fullyInProcess;
    }
    
    public boolean SetReleaseClientInProcess(int releaseId, int clientId, boolean inProcess)
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
            System.out.println("ReleaseOrdersDAO::SetReleaseInProcess - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "UPDATE " + table + " SET"
                    + " `inProcess` = ?"
                    + " WHERE `releaseId` = ?"
                    + " AND `clientId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetBoolean(pStmt, ++i, inProcess);
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::SetReleaseInProcess - Exception while updating ReleaseOrders object: " + ex.toString());
            return false;
        }
    }
    
    public boolean FinishReleaseClient(int releaseId, int clientId)
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
            System.out.println("ReleaseOrdersDAO::FinishRelease - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "UPDATE " + table + " SET"
                    + " `queued` = 0,"
                    + " `inProcess` = 0"
                    + " WHERE `releaseId` = ?"
                    + " AND `clientId` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::FinishRelease - Exception while updating ReleaseOrders object: " + ex.toString());
            return false;
        }
    }
    
    public List<Integer> GetAllReleaseIdForOrderId(int orderId)
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
            System.out.println("ReleaseOrdersDAO::GetAllReleaseIdForOrderId - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<Integer> releaseIdList = new ArrayList<Integer>();
        try
        {
            String stmt = "SELECT `releaseId` FROM " + table
                    + " WHERE `orderId` = " + orderId;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                releaseIdList.add(rs.getInt("releaseId"));
            }

            rs.close();
            pStmt.close();

            return releaseIdList;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseOrdersDAO::GetAllReleaseIdForOrderId - Exception while getting release IDs (orderId=" + orderId + "): " + ex.toString());
            return releaseIdList;
        }
    }
    
    private String GenerateInsertStatement(List<String> fields)
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

    private String GenerateUpdateStatement(List<String> fields)
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
        if (obj instanceof ReleaseOrders)
        {
            try
            {
                ReleaseOrders ro = (ReleaseOrders) obj;
                return InsertReleaseOrders(ro.getReleaseId(), ro.getOrderId(), ro.getClientId(), ro.isQueued(), ro.isInProcess());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseOrdersDAO::Insert - Exception while inserting ReleaseOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseOrdersDAO::Insert - Cannot insert non-ReleaseOrders object using ReleaseOrdersDAO.");
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        if (obj instanceof ReleaseOrders)
        {
            try
            {
                ReleaseOrders ro = (ReleaseOrders) obj;
                return UpdateReleaseOrders(ro.getIdreleaseOrders(), ro.getReleaseId(), ro.getOrderId(), ro.getClientId(), ro.isQueued(), ro.isInProcess());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseOrdersDAO::Update - Exception while updating ReleaseOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseOrdersDAO::Update - Cannot update non-ReleaseOrders object using ReleaseOrdersDAO.");
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        if (obj instanceof ReleaseOrders)
        {
            try
            {
                ReleaseOrders ro = (ReleaseOrders) obj;
                return DeleteReleaseOrders(ro.getIdreleaseOrders());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseOrdersDAO::Delete - Exception while deleting ReleaseOrders object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseOrdersDAO::Delete - Cannot delete non-ReleaseOrders object using ReleaseOrdersDAO.");
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetReleaseOrdersByID(ID);
        }
        catch (Exception ex)
        {
            System.out.println("ReleaseOrdersDAO::getByID - Exception while getting ReleaseOrders (id=" + ID + "): " + ex.toString());
            return false;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
