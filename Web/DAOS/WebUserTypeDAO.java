package Web.DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Web.DOS.WebLog;
import Web.DOS.WebUserTypes;
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

public class WebUserTypeDAO implements DAOInterface, IStructureCheckable
{
    // Maybe one day this will change to use the Web DB connection...
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`WebUserTypes`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public WebUserTypeDAO()
    {
        fields.add("typeName");
        fields.add("typeDescription");
        fields.add("dateCreated");
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
            WebUserTypes wut = (WebUserTypes)obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebUserTypes(wut, pStmt);
            
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
            WebUserTypes wut = (WebUserTypes)obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idTypes` = " + wut.getIdTypes();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatementFromWebUserTypes(wut, pStmt);
            
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
            WebUserTypes wut = new WebUserTypes();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idTypes` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next())
            {
                SetWebUserTypesFromResultSet(wut, rs);
            }

            rs.close();
            stmt.close();

            return wut;
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
    
    private WebUserTypes SetWebUserTypesFromResultSet(WebUserTypes obj, ResultSet rs) throws SQLException
    {
        obj.setIdTypes(rs.getInt("idTypes"));
        obj.setTypeName(rs.getString("typeName"));
        obj.setTypeDescription(rs.getString("typeDescription"));
        obj.setDateCreated(rs.getDate("dateCreated"));
        return obj;
    }
    
    private PreparedStatement SetStatementFromWebUserTypes(WebUserTypes obj, PreparedStatement pStmt) throws SQLException
    {
        pStmt.setString(1, obj.getTypeName());
        pStmt.setString(2, obj.getTypeDescription());
        pStmt.setDate(3, Convert.ToSQLDate(obj.getDateCreated()));
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
