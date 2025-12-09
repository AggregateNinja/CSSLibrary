/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ReleaseResults;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 5, 2016 - Apr 21, 2016
 */
public class ReleaseResultsDAO implements DAOInterface, IStructureCheckable  {
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`releaseResults`";

    private final List<String> fields = new ArrayList<>();

    public ReleaseResultsDAO()
    {
        // all fields except id
        fields.add("releaseId");
        fields.add("clientId");
        fields.add("complete");
        fields.add("error");
        fields.add("releasePrint");
        fields.add("releaseFax");
        fields.add("faxed");
        fields.add("printed");
        fields.add("transmitted");
    }
    
    public boolean InsertReleaseResults(int releaseId, int clientId, boolean complete, boolean error,
            String releasePrint, String releaseFax, boolean faxed, boolean printed, boolean transmitted)
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
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - Exception while checking database connection: " + ex.toString());
            return false;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, complete);
            SQLUtil.SafeSetBoolean(pStmt, ++i, error);
            SQLUtil.SafeSetString(pStmt, ++i, releasePrint);
            SQLUtil.SafeSetString(pStmt, ++i, releaseFax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);

            pStmt.executeUpdate();
            pStmt.close();

        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }
    
    public boolean InsertReleaseResultsUpdateDuplicate(int releaseId, int clientId, boolean complete,
            boolean error, String releasePrint, String releaseFax, boolean faxed, boolean printed, boolean transmitted)
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
            System.out.println("ReleaseResultsDAO::InsertReleaseResultsUpdateDuplicate - Exception while checking database connection: " + ex.toString());
            return false;
        }

        System.out.println("ReleaseResultsDAO::InsertReleaseResultsUpdateDuplicate - " + releasePrint + " - " + releaseFax);
        String stmt = GenerateInsertStatement(fields);
        stmt += " ON DUPLICATE KEY UPDATE `complete` = ?, `error` = ?, `releasePrint` = ?, `releaseFax` = ?, `faxed` = ?, `printed` = ?, `transmitted` = ?";

        try (PreparedStatement pStmt = con.prepareStatement(stmt))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, complete);
            SQLUtil.SafeSetBoolean(pStmt, ++i, error);
            SQLUtil.SafeSetString(pStmt, ++i, releasePrint);
            SQLUtil.SafeSetString(pStmt, ++i, releaseFax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);
            SQLUtil.SafeSetBoolean(pStmt, ++i, complete);
            SQLUtil.SafeSetBoolean(pStmt, ++i, error);
            SQLUtil.SafeSetString(pStmt, ++i, releasePrint);
            SQLUtil.SafeSetString(pStmt, ++i, releaseFax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);
            
            pStmt.executeUpdate();
            pStmt.close();
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::InsertReleaseResultsUpdateDuplicate - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseResultsDAO::InsertReleaseResultsUpdateDuplicate - SQL statement: " + stmt.toString());
            return false;
        }
        return true;
    }
    
    public Integer InsertReleaseResultsGetId(int releaseId, int clientId, boolean complete,
            boolean error, String releasePrint, String releaseFax, boolean faxed, boolean printed, boolean transmitted)
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
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - Exception while checking database connection: " + ex.toString());
            return null;
        }

        String stmt = GenerateInsertStatement(fields);

        try (PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, complete);
            SQLUtil.SafeSetBoolean(pStmt, ++i, error);
            SQLUtil.SafeSetString(pStmt, ++i, releasePrint);
            SQLUtil.SafeSetString(pStmt, ++i, releaseFax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);

            pStmt.executeUpdate();
            
            Integer idReleaseResults;
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    idReleaseResults = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return idReleaseResults;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - SQLException while inserting: " + ex.toString());
            System.out.println("ReleaseResultsDAO::InsertReleaseResult - SQL statement: " + stmt.toString());
            return null;
        }
    }
    
    public boolean UpdateReleaseResults(int idreleaseResults, int releaseId, int clientId,
            boolean complete, boolean error, String releasePrint, String releaseFax, boolean faxed, boolean printed, boolean transmitted)
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
            System.out.println("ReleaseResultsDAO::UpdateReleaseResults - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idreleaseResults` = " + idreleaseResults;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, complete);
            SQLUtil.SafeSetBoolean(pStmt, ++i, error);
            SQLUtil.SafeSetString(pStmt, ++i, releasePrint);
            SQLUtil.SafeSetString(pStmt, ++i, releaseFax);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::UpdateReleaseResults - Exception while updating ReleaseResults object: " + ex.toString());
            return false;
        }
    }
    
    public boolean DeleteReleaseResults(int idreleaseResults)
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
            System.out.println("ReleaseResultsDAO::DeleteReleaseResults - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "DELETE FROM " + table + " WHERE `idreleaseResults` = " + idreleaseResults;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.executeUpdate();
            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::DeleteReleaseResults - Exception while deleting ReleaseResults (id=" + idreleaseResults + "): " + ex.toString());
            return false;
        }
    }
    
    public ReleaseResults GetReleaseResultsByID(int idreleaseResults)
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
            System.out.println("ReleaseResultsDAO::GetReleaseResultsByID - Exception while checking database connection: " + ex.toString());
            return null;
        }

        try
        {
            ReleaseResults ro = new ReleaseResults();

            String stmt = "SELECT * FROM " + table
                    + " WHERE `idreleaseResults` = " + idreleaseResults;
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            if (rs.next())
            {
                ro.setIdreleaseResults(rs.getInt("idreleaseResults"));
                ro.setReleaseId(rs.getInt("releaseId"));
                ro.setClientId(rs.getInt("clientId"));
                ro.setComplete(rs.getBoolean("complete"));
                ro.setError(rs.getBoolean("error"));
                ro.setReleasePrint(rs.getString("releasePrint"));
                ro.setReleaseFax(rs.getString("releaseFax"));
            }

            rs.close();
            pStmt.close();

            return ro;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::GetReleaseResultsByID - Exception while getting PrintTransmitLog (id=" + idreleaseResults + "): " + ex.toString());
            return null;
        }
    }
    
    public List<Integer> GetAllClientIdsForRelease(int releaseId)
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
            System.out.println("ReleaseResultsDAO::GetAllClientIdsForRelease - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<Integer> clientIdList = new ArrayList<Integer>();
        try
        {
            String stmt = "SELECT `clientId` FROM " + table
                    + " WHERE `releaseId` = " + releaseId;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            ResultSet rs = pStmt.executeQuery(stmt);

            while (rs.next())
            {
                clientIdList.add(rs.getInt("clientId"));
            }

            rs.close();
            pStmt.close();

            return clientIdList;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::GetAllClientIdsForRelease - Exception while getting order IDs (releaseId=" + releaseId + "): " + ex.toString());
            return clientIdList;
        }
    }
    
    public List<Integer> GetAllReleaseIdForClientId(int clientId)
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
            System.out.println("ReleaseResultsDAO::GetAllReleaseIdForClientId - Exception while checking database connection: " + ex.toString());
            return null;
        }

        List<Integer> releaseIdList = new ArrayList<Integer>();
        try
        {
            String stmt = "SELECT `releaseId` FROM " + table
                    + " WHERE `clientId` = " + clientId;
            
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
            System.out.println("ReleaseResultsDAO::GetAllReleaseIdForClientId - Exception while getting release IDs (orderId=" + clientId + "): " + ex.toString());
            return releaseIdList;
        }
    }
    
    public boolean SetReleaseClientFaxed(boolean faxed, int releaseId, int clientId)
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
            System.out.println("ReleaseResultsDAO::SetReleaseClientFaxed - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "INSERT INTO " + table + " (`releaseId`, `clientId`, `faxed`) VALUES (?, ?, ?)"
                    + " ON DUPLICATE KEY UPDATE `faxed` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, faxed);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::SetReleaseClientFaxed - Exception while updating ReleaseResults object: " + ex.toString());
            return false;
        }
    }
    
    public boolean SetReleaseClientPrinted(boolean printed, int releaseId, int clientId)
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
            System.out.println("ReleaseResultsDAO::SetReleaseClientPrinted - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "INSERT INTO " + table + " (`releaseId`, `clientId`, `transmitted`) VALUES (?, ?, ?)"
                    + " ON DUPLICATE KEY UPDATE `printed` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            SQLUtil.SafeSetBoolean(pStmt, ++i, printed);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::SetReleaseClientPrinted - Exception while updating ReleaseResults object: " + ex.toString());
            return false;
        }
    }
    
    public boolean SetReleaseClientTransmitted(boolean transmitted, int releaseId, int clientId)
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
            System.out.println("ReleaseResultsDAO::SetReleaseClientTransmitted - Exception while checking database connection: " + ex.toString());
            return false;
        }

        try
        {
            String stmt = "INSERT INTO " + table + " (`releaseId`, `clientId`, `transmitted`) VALUES (?, ?, ?)"
                    + " ON DUPLICATE KEY UPDATE `transmitted` = ?";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            int i = 0;
            SQLUtil.SafeSetInteger(pStmt, ++i, releaseId);
            SQLUtil.SafeSetInteger(pStmt, ++i, clientId);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);
            SQLUtil.SafeSetBoolean(pStmt, ++i, transmitted);
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            System.out.println("ReleaseResultsDAO::SetReleaseClientTransmitted - Exception while updating ReleaseResults object: " + ex.toString());
            return false;
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
        if (obj instanceof ReleaseResults)
        {
            try
            {
                ReleaseResults ro = (ReleaseResults) obj;
                return InsertReleaseResults(ro.getReleaseId(), ro.getClientId(), ro.isComplete(), ro.isError(), ro.getReleasePrint(), ro.getReleaseFax(), ro.isFaxed(), ro.isPrinted(), ro.isTransmitted());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseResultsDAO::Insert - Exception while inserting ReleaseResults object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseResultsDAO::Insert - Cannot insert non-ReleaseResults object using ReleaseResultsDAO.");
        return false;
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        if (obj instanceof ReleaseResults)
        {
            try
            {
                ReleaseResults ro = (ReleaseResults) obj;
                return UpdateReleaseResults(ro.getIdreleaseResults(), ro.getReleaseId(), ro.getClientId(), ro.isComplete(), ro.isError(), ro.getReleasePrint(), ro.getReleaseFax(), ro.isFaxed(), ro.isPrinted(), ro.isTransmitted());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseResultsDAO::Update - Exception while updating ReleaseResults object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseResultsDAO::Update - Cannot update non-ReleaseResults object using ReleaseResultsDAO.");
        return false;
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        if (obj instanceof ReleaseResults)
        {
            try
            {
                ReleaseResults ro = (ReleaseResults) obj;
                return DeleteReleaseResults(ro.getIdreleaseResults());
            }
            catch (Exception ex)
            {
                System.out.println("ReleaseResultsDAO::Delete - Exception while deleting ReleaseResults object: " + ex.toString());
                return false;
            }
        }
        System.out.println("ReleaseResultsDAO::Delete - Cannot delete non-ReleaseResults object using ReleaseResultsDAO.");
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetReleaseResultsByID(ID);
        }
        catch (Exception ex)
        {
            System.out.println("ReleaseResultsDAO::getByID - Exception while getting ReleaseResults (id=" + ID + "): " + ex.toString());
            return false;
        }
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
