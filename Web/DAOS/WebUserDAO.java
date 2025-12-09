package Web.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import Web.DOS.WebUsers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DAOS
 * @file name: WebLogDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class WebUserDAO implements DAOInterface, IStructureCheckable
{
    // Maybe one day this will change to use the Web DB connection...
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`WebUsers`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public WebUserDAO()
    {
        fields.add("typeId");
        fields.add("email");
        fields.add("password");
        fields.add("userSalt");
        fields.add("isVerified");
        fields.add("verificationCode");
        fields.add("dateCreated");
        fields.add("dateUpdated");
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
            WebUsers wl = (WebUsers)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebLog(wl, pStmt);
            
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
            WebUsers user = (WebUsers)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idUsers` = " + user.getIdUsers();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebLog(user, pStmt);
            
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
            WebUsers user = new WebUsers();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idUsers` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebLogFromResultSet(user, rs);
            }

            rs.close();
            stmt.close();

            return user;
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
    
    private WebUsers SetWebLogFromResultSet(WebUsers obj, ResultSet rs) throws SQLException
    {
        obj.setIdUsers(rs.getInt("idUsers"));
        obj.setTypeId(rs.getInt("typeId"));
        obj.setEmail(rs.getString("email"));
        obj.setPassword(rs.getString("password"));
        obj.setUserSalt(rs.getString("userSalt"));
        obj.setIsVerified(rs.getBoolean("isVerified"));
        obj.setVerificationCode(rs.getString("verificationCode"));
        obj.setDateCreated(rs.getDate("dateCreated"));
        obj.setDateUpdated(rs.getDate("dateUpdated"));
        return obj;
    }
    
    private PreparedStatement SetStatementFromWebLog(WebUsers obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setInt(1, obj.getTypeId());
        pStmt.setString(2, obj.getEmail());
        pStmt.setString(3, obj.getPassword());
        pStmt.setString(4, obj.getUserSalt());
        pStmt.setBoolean(5, obj.getIsVerified());
        pStmt.setString(6, obj.getVerificationCode());
        pStmt.setDate(7, Convert.ToSQLDate(obj.getDateCreated()));
        SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDate(obj.getDateUpdated()));
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
