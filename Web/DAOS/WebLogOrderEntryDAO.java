package Web.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Web.DOS.WebLog;
import Web.DOS.WebLogOrderEntry;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 * @date:   Jun 3, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DAOS
 * @file name: WebLogOrderEntryDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class WebLogOrderEntryDAO implements DAOInterface, IStructureCheckable
{

    // Maybe one day this will change to use the Web DB connection...
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`WebLogOrderEntry`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public WebLogOrderEntryDAO()
    {
        fields.add("orderId");
        fields.add("advancedOrderId");
        fields.add("advancedOrderOnly");
        fields.add("isNewPatient");
        fields.add("isNewSubscriber");
        fields.add("subscriberChanged");
        fields.add("accession");
        fields.add("logId");
        fields.add("orderEntryLogType");
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
            WebLogOrderEntry wloe = (WebLogOrderEntry)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebLogOrderEntry(wloe, pStmt);
            
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
            WebLogOrderEntry wloe = (WebLogOrderEntry)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idOrderEntryLog` = " + wloe.getIdOrderEntryLog();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebLogOrderEntry(wloe, pStmt);
            
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
            WebLogOrderEntry wloe = new WebLogOrderEntry();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrderEntryLog` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebLogOrderEntryFromResultSet(wloe, rs);
            }

            rs.close();
            stmt.close();

            return wloe;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public WebLogOrderEntry getByAccession(String Accnum)
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
            WebLogOrderEntry wloe = new WebLogOrderEntry();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `accession` = ?";

            stmt = createStatement(con, query, Accnum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                SetWebLogOrderEntryFromResultSet(wloe, rs);
            }

            rs.close();
            stmt.close();

            return wloe;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public WebLogOrderEntry getMostRecentByAccession(String Accnum)
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
            WebLogOrderEntry wloe = new WebLogOrderEntry();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `accession` = ?"
                    + " ORDER BY idOrderEntryLog DESC LIMIT 1";

            stmt = createStatement(con, query, Accnum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                SetWebLogOrderEntryFromResultSet(wloe, rs);
            }

            rs.close();
            stmt.close();

            return wloe;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public WebLogOrderEntry getByOrderId(Integer ID)
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
            WebLogOrderEntry wloe = new WebLogOrderEntry();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `orderId` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebLogOrderEntryFromResultSet(wloe, rs);
            }

            rs.close();
            stmt.close();

            return wloe;
        }
        catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
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
    
    private WebLogOrderEntry SetWebLogOrderEntryFromResultSet(WebLogOrderEntry obj, ResultSet rs) throws SQLException
    {
        obj.setIdOrderEntryLog(rs.getInt("idOrderEntryLog"));
        obj.setLogId(rs.getInt("logId"));
        obj.setorderEntryLogType(rs.getInt("orderEntryLogType"));
        obj.setOrderId(rs.getInt("orderId"));
        obj.setAdvancedOrderId(rs.getInt("advancedOrderId"));
        obj.setAccession(rs.getString("accession"));
        obj.setAdvancedOrderOnly(rs.getBoolean("advancedOrderOnly"));
        obj.setIsNewPatient(rs.getBoolean("isNewPatient"));
        obj.setIsNewSubscriber(rs.getBoolean("isNewSubscriber"));
        obj.setSubscriberChanged(rs.getBoolean("subscriberChanged"));
        return obj;
    }

    private PreparedStatement SetStatementFromWebLogOrderEntry(WebLogOrderEntry obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getLogId());
        pStmt.setInt(2, obj.getOrderEntryLogType());
        pStmt.setInt(3, obj.getOrderId());
        pStmt.setInt(4, obj.getAdvancedOrderId());
        pStmt.setString(5, obj.getAccession());
        pStmt.setBoolean(6, obj.getAdvancedOrderOnly());
        pStmt.setBoolean(7, obj.getIsNewPatient());
        pStmt.setBoolean(8, obj.getIsNewSubscriber());
        pStmt.setBoolean(9, obj.getSubscriberChanged());
        return pStmt;
    }

    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
}
