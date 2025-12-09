/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 15, 2014
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.TranslationalQueue;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * @date: Aug 15, 2014 12:15:08 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: TranslationQueueDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class TranslationalQueueDAO implements DAOInterface, IStructureCheckable
{

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`translationalQueue`";

    private final ArrayList<String> fields = new ArrayList<>();

    /*
     * All fields in table except id
     */
    public TranslationalQueueDAO()
    {
        fields.add("accession");
        fields.add("idorders");
        fields.add("createdDate");
        fields.add("transmitted");
        fields.add("transmittedDate");
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        TranslationalQueue tq = (TranslationalQueue) obj;

        try
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

            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromQueue(tq, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TranslationalQueue.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception inserting TranslationaQueue: " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        TranslationalQueue tq = (TranslationalQueue) obj;

        try
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

            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + tq.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromQueue(tq, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TranslationalQueue.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception updating TranslationaQueue: " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        return false;
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetQueueByID(ID);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public TranslationalQueue GetQueueByAccession(String acc)
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
            TranslationalQueue tq = new TranslationalQueue();

            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetString(pStmt, 1, acc);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetQueueFromResultSet(tq, rs);
            }

            rs.close();
            pStmt.close();

            return tq;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<TranslationalQueue> GetAllPendingQueues()
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
            ArrayList<TranslationalQueue> list = new ArrayList<>();
            TranslationalQueue tq = new TranslationalQueue();

            String query = "SELECT * FROM " + table
                    + "WHERE `transmitted` = " + false;

            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                tq = new TranslationalQueue();
                SetQueueFromResultSet(tq, rs);
                list.add(tq);
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

    public Set<String> GetAllPendingAccessions()
    {
        Set<String> pendingAccessionSet = new HashSet<String>();
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
            return pendingAccessionSet;
        }

        try
        {
            String query = "SELECT DISTINCT accession FROM " + table
                    + "WHERE `transmitted` = " + false;

            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                pendingAccessionSet.add(rs.getString("accession"));
            }

            rs.close();
            pStmt.close();
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
        return pendingAccessionSet;
    }
    
    public ArrayList<TranslationalQueue> GetAllQueues()
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
            ArrayList<TranslationalQueue> list = new ArrayList<>();
            TranslationalQueue tq = new TranslationalQueue();

            String query = "SELECT * FROM " + table;

            PreparedStatement pStmt = con.prepareStatement(query);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next())
            {
                tq = new TranslationalQueue();
                SetQueueFromResultSet(tq, rs);
                list.add(tq);
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

    public TranslationalQueue GetQueueByID(int ID)
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
            TranslationalQueue tq = new TranslationalQueue();

            String query = "SELECT * FROM " + table
                    + "WHERE `id` = ?";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(pStmt, 1, ID);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next())
            {
                SetQueueFromResultSet(tq, rs);
            }

            rs.close();
            pStmt.close();

            return tq;
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean UpdateAccessionTransmitted(String accession, String transmittedDate, boolean transmitted)
    {
        try {
            String stmt = "UPDATE " + table + " SET `transmitted` = " + transmitted + ", `transmittedDate` = ? WHERE `accession` = ? AND `transmitted` != " + transmitted;
            PreparedStatement pStmt = createStatement(con, stmt, transmittedDate, accession);//con.prepareStatement(stmt);
            
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TranslationalQueue.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception updating translationalQueue: " + ex.toString());
            return false;
        }
        return true;
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

    private TranslationalQueue SetQueueFromResultSet(TranslationalQueue tq, ResultSet rs) throws SQLException
    {
        tq.setId(rs.getInt("id"));
        tq.setAccession(rs.getString("accession"));
        tq.setIdorders(rs.getInt("idorders"));
        tq.setCreatedDate(rs.getTimestamp("createdDate"));
        tq.setTransmitted(rs.getBoolean("transmitted"));
        tq.setTransmittedDate(rs.getTimestamp("transmittedDate"));

        return tq;
    }

    private PreparedStatement SetStatementFromQueue(TranslationalQueue tq, PreparedStatement pStmt) throws SQLException
    {
        SQLUtil.SafeSetString(pStmt, 1, tq.getAccession());
        SQLUtil.SafeSetInteger(pStmt, 2, tq.getIdorders());
        SQLUtil.SafeSetTimeStamp(pStmt, 3, tq.getCreatedDate());
        SQLUtil.SafeSetBoolean(pStmt, 4, tq.getTransmitted());
        SQLUtil.SafeSetTimeStamp(pStmt, 5, tq.getTransmittedDate());

        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
