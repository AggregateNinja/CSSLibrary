/*
 * Computer Service & Support, Inc.  All Rights Reserved Sep 3, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.TranslationalXref;
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
import java.util.logging.Level;
import java.util.logging.Logger;

//------------------------------------------------------------------------------
/**
 * @date: Nov 6, 2014 11:52:00 PM
 * @author: Michael Douglass miked@csslis.com
 *
 * @project: CSSLibrary
 * @file name: TranslationsXrefDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class TranslationalXrefDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`translationalXref`";

    private final ArrayList<String> fields = new ArrayList<>();

    /* All fields in table except id */
    public TranslationalXrefDAO()
    {
        fields.add("idxrefs");
        fields.add("testNumber");
        fields.add("analyteName");
        fields.add("created");
        fields.add("active");
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Insert(Serializable obj)
    {        
        try
        {
            TranslationalXref txr = (TranslationalXref) obj;
            
            try
            {
                if(con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            }
            catch (SQLException sex)
            {
                System.out.println("TranslationalXrefDAO::Insert - " + sex.toString());
                return false;
            }

            String stmt = GenerateInsertStatement(fields);
            try (PreparedStatement pStmt = con.prepareStatement(stmt))
            {
                SetStatementFromTranslationalXref(txr, pStmt);
                
                pStmt.executeUpdate();
            }
            
            return true;
        }
        catch(SQLException sex)
        {
            Logger.getLogger(TranslationalXref.class.getName()).log(Level.SEVERE, null, sex);
            System.out.println("Exception inserting TranslationalXref: " + sex.toString());
            return false;
        }   
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Update(Serializable obj)
    {
        try
        { 
            TranslationalXref txr = (TranslationalXref) obj;
            
            try
            {
                if(con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            }
            catch (SQLException sex)
            {
                System.out.println("TranslationalXrefDAO::Update - " + sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = " + txr.getId();
            try (PreparedStatement pStmt = con.prepareStatement(stmt))
            {
                SetStatementFromTranslationalXref(txr, pStmt);
                
                pStmt.executeUpdate();
            }
            
            return true;
        } 
        catch (SQLException sex)
        {
            Logger.getLogger(TranslationalXref.class.getName()).log(Level.SEVERE, null, sex);
            System.out.println("Exception updating TranslationalXref: " + sex.toString());
            return false;
        }
    }

    //--------------------------------------------------------------------------
    @Override
    public Boolean Delete(Serializable obj)
    {
        try 
        {
            if (con.isClosed()) 
                con = CheckDBConnection.Check(dbs, con);
        } 
        catch (SQLException sex) 
        {
            System.out.println("TranslationalXrefDAO::Delete - " + sex.toString());
            return false;
        }
        
        return false;
    }

    //--------------------------------------------------------------------------
    @Override
    public Serializable getByID(Integer ID)
    {
        try
        {
            return GetTranslationalXrefById(ID);
        }
        catch(Exception sex)
        {
            System.out.println("TranslationalXrefDAO::getById - " + sex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public TranslationalXref GetTranslationalXrefById(Integer ID)
    {
        try
        {
            try
            {
                if(con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            }
            catch(SQLException sex)
            {
                System.out.println("TranslationalXrefDAO::GetTranslationalXrefById - " + sex.toString());
                return null;            
            }
            
            TranslationalXref txr = new TranslationalXref();
            String query = "SELECT * FROM " + table + " WHERE `id` = " + ID;
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next())
            {
                SetTranslationalXrefFromResultSet(txr, rs);
            }
                     
            rs.close();
            pStmt.close();
            
            return txr;
        }
        catch(Exception ex)
        {
            System.out.println("TranslationalXrefDAO::GetTranslationalXrefById - " + ex.toString());
            return null;                
        }
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<TranslationalXref> GetTranslationalXrefsByTestNumber(Integer TestNumber)
    {
        try
        {
            try
            {
                if(con.isClosed())
                    con = CheckDBConnection.Check(dbs, con);
            }
            catch(SQLException sex)
            {
                System.out.println("TranslationalXrefDAO::GetTranslationalXrefsByTestNumber - " + sex.toString());
                return null;            
            }
            
            String query = "SELECT * FROM " + table + " WHERE `testNumber` = " + TestNumber;
 
            PreparedStatement pStmt = con.prepareStatement(query);
            
            ResultSet rs = pStmt.executeQuery();

            ArrayList<TranslationalXref> txrList = new ArrayList<>();
            TranslationalXref txr;
            
            while(rs.next())
            {
                txr = new TranslationalXref();
                
                SetTranslationalXrefFromResultSet(txr, rs);
                
                txrList.add(txr);
            }
            
            rs.close();
            pStmt.close();
            
            return txrList;
        }
        catch(Exception ex)
        {
            System.out.println("TranslationalXrefDAO::GetTranslationalXrefsByTestNumber - " + ex.toString());
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
    private TranslationalXref SetTranslationalXrefFromResultSet(TranslationalXref txr, ResultSet rs) throws SQLException
    {
        txr.setId(rs.getInt("id"));
        txr.setIdxrefs(rs.getInt("idxrefs"));
        txr.setTestNumber(rs.getInt("testNumber"));
        txr.setAnalyteName(rs.getString("analyeName"));
        txr.setCreated(rs.getDate("created"));
        txr.setActive(rs.getBoolean("active"));
       
        return txr;
    }
    
    //--------------------------------------------------------------------------
    private PreparedStatement SetStatementFromTranslationalXref(TranslationalXref txr, PreparedStatement pStmt) throws SQLException
    {
        SQLUtil.SafeSetInteger(pStmt, 1, txr.getIdxrefs());
        SQLUtil.SafeSetInteger(pStmt, 2, txr.getTestNumber());
        SQLUtil.SafeSetString(pStmt, 3, txr.getAnalyteName());
        SQLUtil.SafeSetTimeStamp(pStmt, 4, Convert.ToSQLDateTime(txr.getCreated()));
        SQLUtil.SafeSetBoolean(pStmt, 5, txr.getActive(), false);
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
    
    //--------------------------------------------------------------------------
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
