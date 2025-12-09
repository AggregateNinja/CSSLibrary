package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;

import DOS.DetailImportLog;

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
 * @file name: DetailImportLogDAO.java
 * 
 * @Description:
 * 
 * Computer Service & Support, Inc. All Rights Reserved
 */

public class DetailImportLogDAO implements DAOInterface, IStructureCheckable
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`detailImportLog`";
    
    private final ArrayList<String> fields = new ArrayList<>();
    //ArrayList<Integer> generatedIDs = new ArrayList<>();
    
    //--------------------------------------------------------------------------
    public DetailImportLogDAO()
    {
       fields.add("filename");
       fields.add("lineNumber");
       fields.add("dateCreated");
       fields.add("user");
       fields.add("status");
       fields.add("message");
       fields.add("cssMasterNumber");
       fields.add("cssAccession");
       fields.add("cssTest");
       fields.add("serviceDate");
       fields.add("billDate");
       fields.add("billAmount");
       fields.add("transDate");
       fields.add("paidAmount");
       fields.add("writeOffAmount");
       fields.add("accession");
       fields.add("idDetails");
       fields.add("idResults");
       fields.add("idTests");
    }
    
    //--------------------------------------------------------------------------
    //public ArrayList<Integer> GetGeneratedIDs()
    //{
        //return generatedIDs;
    //}
    
    //--------------------------------------------------------------------------
    @Override
    public Boolean Insert(Serializable obj)
    {
        DetailImportLog dil = (DetailImportLog)obj;
        
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
            
            String stmt = GenerateInsertStatement(fields);
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(dil, pStmt);
            
            pStmt.executeUpdate();
            
            //ResultSet rs = pStmt.getGeneratedKeys();
            //if(rs.next())
            //{
                //generatedIDs.add(rs.getInt(1));
            //}
            
            //rs.close();
            pStmt.close();
            
            //return !generatedIDs.isEmpty();
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
        DetailImportLog dil = (DetailImportLog)obj;
        
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
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + dil.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SetStatement(dil, pStmt);
            
            pStmt.executeUpdate();
            
            //ResultSet rs = pStmt.getGeneratedKeys();
            //if(rs.next())
            //{
                //generatedIDs.add(rs.getInt(1));
            //}
            
            //rs.close();
            pStmt.close();
            
            //return !generatedIDs.isEmpty();
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
            
            DetailImportLog dil = new DetailImportLog();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                FromResultSet(dil,rs);
            }
            
            rs.close();
            stmt.close();
            
            return dil;
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
    private DetailImportLog FromResultSet(DetailImportLog dil, ResultSet rs) throws SQLException
    {
        dil.setId(rs.getInt("id"));
        dil.setFilename(rs.getString("filename"));
        dil.setLineNumber(rs.getInt("lineNumber"));
        dil.setDateCreated(rs.getTimestamp("dateCreated"));
        dil.setUser(rs.getString("user"));
        dil.setStatus(rs.getString("status"));
        dil.setMessage(rs.getString("message"));
        dil.setCssMasterNumber(rs.getInt("cssMasterNumber"));
        dil.setCssAccession(rs.getInt("cssAccession"));
        dil.setCssTest(rs.getString("cssTest"));
        dil.setServiceDate(rs.getTimestamp("serviceDate"));
        dil.setBillDate(rs.getTimestamp("billDate"));
        dil.setBillAmount(rs.getDouble("billAmount"));
        dil.setTransDate(rs.getTimestamp("transDate"));
        dil.setPaidAmount(rs.getDouble("paidAmount"));
        dil.setWriteOffAmount(rs.getDouble("writeOffAmount"));
        dil.setAccession(rs.getString("accession"));
        dil.setIdDetails(rs.getInt("idDetails"));
        dil.setIdResults(rs.getInt("idResults"));
        dil.setIdTests(rs.getInt("idTests"));
        
        return dil;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(DetailImportLog dil, PreparedStatement pStmt) throws SQLException
    {
        //pStmt.setInt(1, dil.getId());
        pStmt.setString(1, dil.getFilename());
        //SQLUtil.SafeSetString(pStmt, 1, dil.getFilename());
        pStmt.setInt(2, dil.getLineNumber());
        
        java.util.Date now = new java.util.Date();
        pStmt.setDate(3, Convert.ToSQLDate(now));

        pStmt.setString(4, dil.getUser());
        SQLUtil.SafeSetString(pStmt, 5, dil.getStatus());
        SQLUtil.SafeSetString(pStmt, 6, dil.getMessage());
        SQLUtil.SafeSetInteger(pStmt, 7, dil.getCssMasterNumber());
        SQLUtil.SafeSetInteger(pStmt, 8, dil.getCssAccession());
        SQLUtil.SafeSetString(pStmt, 9, dil.getCssTest());
        SQLUtil.SafeSetTimeStamp(pStmt, 10, Convert.ToSQLDateTime(dil.getServiceDate()));
        SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(dil.getBillDate()));
        SQLUtil.SafeSetDouble(pStmt, 12, dil.getBillAmount());
        SQLUtil.SafeSetTimeStamp(pStmt, 13, Convert.ToSQLDateTime(dil.getTransDate()));
        SQLUtil.SafeSetDouble(pStmt, 14, dil.getPaidAmount());
        SQLUtil.SafeSetDouble(pStmt, 15, dil.getWriteOffAmount());
        SQLUtil.SafeSetString(pStmt, 16, dil.getAccession());
        SQLUtil.SafeSetInteger(pStmt, 17, dil.getIdDetails());
        SQLUtil.SafeSetInteger(pStmt, 18, dil.getIdResults());
        SQLUtil.SafeSetInteger(pStmt, 19, dil.getIdTests());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------

}
