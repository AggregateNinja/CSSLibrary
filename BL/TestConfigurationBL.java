package BL;

import DAOS.PanelDAO;
import DAOS.TestDAO;
import DOS.Panels;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:   May 12, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: BL
 * @file name: TestConfigurationBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class TestConfigurationBL 
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public TestConfigurationBL()
    {
        
    }
    
    public boolean UpdateParentPanels(Integer ID)
    {
        /*PanelDAO pDAO = new PanelDAO();
        TestDAO tDAO = new TestDAO();
        
        try
        {
            if(pDAO.IsTestIDSubtest(ID.intValue()) == false)
            {
                return true;
            }
            
            ArrayList<Panels> parentPanels = (ArrayList<Panels>) pDAO.GetParentPanels(ID);
            for(Panels p : parentPanels)
            {
                
            }
            
            return true;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(TestConfigurationBL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }*/
        return false;
    }
    
    public int PanelsPendingResults(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int pending = 0;
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT COUNT(DISTINCT r.orderid) "
                + "FROM results r "
                + "LEFT JOIN panels p "
                + "	ON r.panelid = p.idpanels "
                + "WHERE r.panelid = " + id + " " 
                + "AND r.dateReported IS NULL "
                + "AND r.isApproved = FALSE";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                pending = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            
            return pending;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    public ArrayList<Integer> AccessionsWithPendingPanel(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            ArrayList<Integer> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT DISTINCT o.accession "
                + "FROM results r "
                + "LEFT JOIN panels p "
                + "	ON r.panelid = p.idpanels "
                + "LEFT JOIN orders o "
                + "	ON r.orderid = o.idorders"
                + "WHERE r.panelid = " + id + " " 
                + "AND r.dateReported IS NULL "
                + "AND r.isApproved = FALSE";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                int x = rs.getInt(1);
                
                list.add(x);
            }
            
            rs.close();
            stmt.close();
            
            return list;
 
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public int PanelOrderCount(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int pending = 0;
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT COUNT(DISTINCT r.orderid) "
                + "FROM results r "
                + "LEFT JOIN panels p "
                + "	ON r.panelid = p.idpanels "
                + "WHERE r.panelid = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                pending = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            
            return pending;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return -1;
        }
    }
}
