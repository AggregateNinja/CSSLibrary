/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 11, 2014
 */
package BL;

import DAOS.PreferencesDAO;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//------------------------------------------------------------------------------
/**
 * @date:   Nov 6, 2014  3:19:00 PM
 * @author: Michael Douglass miked@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: AutoGenomicsBL.java  (UTF-8)
 *
 * @Description: 
 *
 */
//------------------------------------------------------------------------------
public class AutoGenomicsBL
{
    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    //--------------------------------------------------------------------------
    public AutoGenomicsBL()
    {
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<Integer> GetAutoGenomicsInstruments()
    {
        try
        {
            PreferencesDAO pdao = new PreferencesDAO();
        
            String tmpNumbers = pdao.getString("AutoGenomicsInstruments");
            String[] tmpInstruments = tmpNumbers.split(",");
        
            ArrayList<Integer> instList = new ArrayList<>();
        
            for(String str : tmpInstruments)
            {
                try
                {
                    instList.add(Integer.parseInt(str));
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("AutoGenomicsBL::GetAutoGenomicsInstruments - " + nfe.toString());
                }
            }
        
            return instList;
        }
        catch(Exception ex)
        {
            System.out.println("AutoGenomicsBL::GetAutoGenomicsInstruments - " + ex.toString());
            return null;
        }
    }
    
    //--------------------------------------------------------------------------
    public String BuildAutoGenomicsFromStatement()
    {
        try
        {
            String retval;
        
            // Get an Array list of the instruments
            ArrayList<Integer> iList = GetAutoGenomicsInstruments();

            // Check and make sure that we were able to get the list of instuments
            if(iList.isEmpty())
            {
                return null;
            }
            // If there is only one instrument just build the simple from line
            else if(iList.size() == 1)
            {
                retval = "FROM `instRes_" + iList.get(0) + "` AS iRes";
            }
            else
            {
                retval = "FROM (";

                // Loop through the instrument list
                for(int idx = 0; idx < iList.size(); idx++)
                {
                    // If this is not the first one then we need to add UNION between
                    if(idx > 0)
                        retval += " UNION";
                    
                    retval += " SELECT * FROM `instRes_" + iList.get(idx) + "`";
                }
                
                retval += ") AS iRes";
            }
        
            return retval;
        }
        catch(Exception ex)
        {
            System.out.println("AutoGenomicsBL::BuildAutoGenomicsFromStatement - " + ex.toString());
            return null;            
        }   
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<String> GetAutoGenomicsPostedAnalytesByAccession(String accession)
    {
        try
        {
            if (con.isClosed()) 
            {
                con = CheckDBConnection.Check(dbs, con);
            }            
            
            ArrayList<String> retval = new ArrayList<>();
            
            String query = "SELECT DISTINCT `iRes`.`name` AS 'analyteName' " +
                           BuildAutoGenomicsFromStatement() + " " +
                           "WHERE `iRes`.`accession` = ? " +
                           "AND `iRes`.`posted` = true";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, accession);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                retval.add(rs.getString("analyteName"));
            }
            
            rs.close();
            pStmt.close();
            
            return retval;
        }
        catch(SQLException ex)
        {
            System.out.println("AutoGenomicsBL::GetAutoGenomicsPostedAnalytesByAccession - " + ex.toString());
            return null;            
        }        
    }
    
    //--------------------------------------------------------------------------
    public ArrayList<String> GetAutoGenomicsRequiredAnalytes(int tNumber)
    {
        try
        {
            if (con.isClosed()) 
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            ArrayList<String> retval = new ArrayList<>();
            
            String query = "SELECT DISTINCT `txrf`.`analyteName` " +
                            "FROM `translationalXref` `txrf` " +
                            "WHERE `txrf`.`testNumber` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, tNumber);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next())
            {
                retval.add(rs.getString("analyteName"));
            }
            
            rs.close();
            pStmt.close();
            
            return retval;
        }
        catch(SQLException ex)
        {
            System.out.println("AutoGenomicsBL::GetAutoGenomicsRequiredAnalytesForTest - " + ex.toString());
            return null;            
        }
    }
    
    //--------------------------------------------------------------------------
    public int GetTranslationalTestOrderedByAccession(String accession)
    {
        try
        {
            if (con.isClosed()) 
            {
                con = CheckDBConnection.Check(dbs, con);
            }
            
            int retval = 0;
            
            String query = "SELECT DISTINCT `tst`.`number` AS 'testNumber' " +
                    BuildAutoGenomicsFromStatement() + " " +
                    "LEFT JOIN `orders` `ord` ON `iRes`.`accession` = `ord`.`accession` " +
                    "INNER JOIN `results` `res` ON `ord`.`idOrders` = `res`.`orderId` " +
                    "INNER JOIN `tests` `tst` ON `res`.`testId` = `tst`.`idtests` " +
                    "LEFT JOIN `testXref` `xrf` ON `iRes`.`name` = `xrf`.`transformedOut` " +
                    "LEFT JOIN `preferences` `pre` ON `pre`.`key` = 'POCTest' AND " +
                    "       (`pre`.`value` = `res`.`testId` OR `pre`.`value` = `res`.`panelId`) " +
                    "LEFT JOIN `preferences` `tpre` ON `tpre`.`key` = 'TranslationalSoftwareDepartment' " +
                    "WHERE `pre`.`key` IS NULL " +
                    "AND `tst`.`testType` = 0 " +
                    "AND `tst`.`active` IS TRUE " +
                    "AND `iRes`.`accession` = ? " +
                    "AND `tst`.`department` = `tpre`.`value` " +
                    "GROUP BY `tst`.`number`";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, accession);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next())
            {
                retval = rs.getInt("testNumber");
            }
            else
            {
                retval = -1;
            }
            
            rs.close();
            pStmt.close();
            
            return retval;
        }
        catch(SQLException ex)
        {
            System.out.println("AutoGenomicsBL::GetTranslationalTestOrderedByAccession(" + accession + ") - " + ex.toString());
            return -1;            
        }        
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
