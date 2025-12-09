package Web.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import Web.DOS.WebLog;
import Web.DOS.WebOrderReceiptLog;
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
 * @file name: WebOrderReceiptLogDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class WebLogOrderReceiptLogDAO implements DAOInterface, IStructureCheckable
{

    // Maybe one day this will change to use the Web DB connection...
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`WebOrderReceiptLog`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public WebLogOrderReceiptLogDAO()
    {
        //fields.add("idWebOrderReceiptLog");
        fields.add("webAccession");
        fields.add("webCreated");
        fields.add("idOrders");
        fields.add("user");
        fields.add("receiptedDate");
        fields.add("webUser");
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
            WebOrderReceiptLog worl = (WebOrderReceiptLog)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebOrderReceiptLog(worl, pStmt);
            
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
            WebOrderReceiptLog wloe = (WebOrderReceiptLog)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idWebOrderReceiptLog` = " + wloe.getIdWebOrderReceiptLog();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebOrderReceiptLog(wloe, pStmt);
            
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
            WebOrderReceiptLog wloe = new WebOrderReceiptLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idWebOrderReceiptLog` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebOrderReceiptLogFromResultSet(wloe, rs);
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
    
    public WebOrderReceiptLog getByWebAccession(String Accnum)
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
            WebOrderReceiptLog wloe = new WebOrderReceiptLog();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `webAccession` = ?";

            stmt = createStatement(con, query, Accnum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                SetWebOrderReceiptLogFromResultSet(wloe, rs);
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
    
    public WebOrderReceiptLog getByOrderId(Integer ID)
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
            WebOrderReceiptLog wloe = new WebOrderReceiptLog();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebOrderReceiptLogFromResultSet(wloe, rs);
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
    
    private WebOrderReceiptLog SetWebOrderReceiptLogFromResultSet(WebOrderReceiptLog obj, ResultSet rs) throws SQLException
    {
        obj.setIdWebOrderReceiptLog(rs.getInt("idWebOrderReceiptLog"));
        obj.setWebAccession(rs.getString("webAccession"));
        obj.setWebCreated(rs.getDate("webCreated"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setUser(rs.getInt("user"));
        obj.setReceiptedDate(rs.getDate("receiptedDate"));
        obj.setWebUser(rs.getInt("webUser"));
        return obj;
    }

    private PreparedStatement SetStatementFromWebOrderReceiptLog(WebOrderReceiptLog obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setString(1, obj.getWebAccession());
        pStmt.setDate(2, Convert.ToSQLDate(obj.getWebCreated()));
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getIdOrders());
        SQLUtil.SafeSetInteger(pStmt, 4, obj.getUser());
        SQLUtil.SafeSetDate(pStmt, 5, obj.getReceiptedDate());
        pStmt.setInt(6, obj.getWebUser());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
