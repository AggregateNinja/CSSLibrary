/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;

import DOS.TransactionImportLog;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   Aug 19, 2014
 * @author: Michael Douglass
 * 
 * @project:
 * @package: DAOS
 * @file name: TransactionImportLogDAO.java
 * 
 * @Description:
 * 
 * Computer Service & Support, Inc. All Rights Reserved
 */

public class TransactionImportLogDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`transactionImportLog`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    
    //--------------------------------------------------------------------------
    public TransactionImportLogDAO()
    {
        fields.add("filename");
        fields.add("lineNumber");
        fields.add("dateCreated");
        fields.add("user");
        fields.add("status");
        fields.add("message");
        fields.add("type");
        fields.add("cssMasterNumber");
        fields.add("cssAccession");
        fields.add("cssTest");
        fields.add("transDate");
        fields.add("transAmount");
        fields.add("accession");
        fields.add("idDetails");
        fields.add("idTests");
    }
    
    //--------------------------------------------------------------------------
    @Override
    public Boolean Insert(Serializable obj)
    {
        TransactionImportLog til = (TransactionImportLog)obj;
        
        try
        {
            try
            {
                if(con.isClosed())
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
            SetStatement(til, pStmt);
            
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Update(Serializable obj)
    {
        TransactionImportLog til = (TransactionImportLog)obj;
        
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } 
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + til.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(til, pStmt);
            
            pStmt.executeUpdate();
            pStmt.close();
            
            return true;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //--------------------------------------------------------------------------
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            try
            {
                if(con.isClosed())
                {
                    con = CheckDBConnection.Check(dbs, con);
                }
            }
            catch(SQLException sex)
            {
                System.out.println(sex.toString());
                return null;
            }
            
            TransactionImportLog til = new TransactionImportLog();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `id` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                FromResultSet(til, rs);
            }
            
            rs.close();
            
            stmt.close();
            
            return til;            
        } 
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
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

    //--------------------------------------------------------------------------
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
    
    //--------------------------------------------------------------------------
    private TransactionImportLog FromResultSet(TransactionImportLog til, ResultSet rs) throws SQLException
    {
        til.setId(rs.getInt("id"));
        til.setFilename(rs.getString("filename"));
        til.setLineNumber(rs.getInt("lineNumber"));
        til.setDateCreated(rs.getTimestamp("dateCreated"));
        til.setUser(rs.getString("user"));
        til.setStatus(rs.getString("status"));
        til.setMessage(rs.getString("message"));
        til.setType(rs.getString("type"));
        til.setCssMasterNumber(rs.getInt("cssMasterNumber"));
        til.setCssAccession(rs.getInt("cssAccession"));
        til.setCssTest(rs.getString("cssTest"));
        til.setTransDate(rs.getTimestamp("transDate"));
        til.setTransAmount(rs.getDouble("transAmount"));
        til.setAccession(rs.getString("acession"));
        til.setIdDetails(rs.getInt("idDetails"));
        til.setIdTests(rs.getInt("idTests"));
        
        return til;
    }
    
    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(TransactionImportLog til, PreparedStatement pStmt) throws SQLException
    {
        
        pStmt.setString(1, til.getFilename());
        pStmt.setInt(2, til.getLineNumber());
        
        java.util.Date now = new java.util.Date();        
        pStmt.setDate(3, Convert.ToSQLDate(now));
        
        pStmt.setString(4, til.getUser());
        SQLUtil.SafeSetString(pStmt, 5, til.getStatus());
        SQLUtil.SafeSetString(pStmt, 6, til.getMessage());
        SQLUtil.SafeSetString(pStmt, 7, til.getType());
        SQLUtil.SafeSetInteger(pStmt, 8, til.getCssMasterNumber());
        SQLUtil.SafeSetInteger(pStmt, 9, til.getCssAccession());
        SQLUtil.SafeSetString(pStmt, 10, til.getCssTest());
        SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(til.getTransDate()));
        SQLUtil.SafeSetDouble(pStmt, 12, til.getTransAmount());
        SQLUtil.SafeSetString(pStmt, 13, til.getAccession());        
        SQLUtil.SafeSetInteger(pStmt, 14, til.getIdDetails());        
        SQLUtil.SafeSetInteger(pStmt, 15, til.getIdTests());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    
}
