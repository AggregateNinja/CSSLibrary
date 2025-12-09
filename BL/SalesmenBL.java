/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 23, 2014
 */

package BL;

import DAOS.EmployeesDAO;
import DAOS.SalesmenDAO;
import DOS.Employees;
import DOS.Salesmen;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @date:   Jul 23, 2014  1:20:50 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SalesmenBL.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class SalesmenBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public SalesmenBL(){
        
    }
    
    /**
     * Gets a list of all Salesmen Names, formats them as
     * <LastName, FirstName>, and adds them to an ArrayList<String>
     * @return ArrayList<String> of Salemen names. <Last, First>
     */
    public ArrayList<String> GetAllSalesmenNamesLastFirst(){
        try{
            ArrayList<String> names = new ArrayList<>();
            ArrayList<Salesmen> salesmen = new ArrayList<>();
            SalesmenDAO sdao = new SalesmenDAO();
            salesmen = sdao.GetAllSalesmen();
            Employees emp;
            EmployeesDAO edao = new EmployeesDAO();
            
            for(Salesmen sm : salesmen){
                emp = new Employees();
                emp = edao.GetEmployeeByID(sm.getEmployeeID());
                names.add(emp.getLastName().trim()+", "+emp.getFirstName().trim());
            }

            return names;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * Gets a list of all Salesmen Names, formats them as
     *<FirstName LastName>, and adds them to an ArrayList<String>
     * @return ArrayList<String> of Salemen names. <FirstName LastName>
     */
    public ArrayList<String> GetAllSalesmenNames(){
        try{
            ArrayList<String> names = new ArrayList<>();
            ArrayList<Salesmen> salesmen = new ArrayList<>();
            SalesmenDAO sdao = new SalesmenDAO();
            salesmen = sdao.GetAllSalesmen();
            Employees emp;
            EmployeesDAO edao = new EmployeesDAO();
            
            for(Salesmen sm : salesmen){
                emp = new Employees();
                emp = edao.GetEmployeeByID(sm.getEmployeeID());
                names.add(emp.getFirstName().trim() + " " + emp.getLastName().trim());
            }

            return names;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Salesmen GetSalesmenFromEmployeeName(String name, boolean activeOnly){
        String first = "";
        String last = "";
        
        //First check to see if it is Last, First
        if(name.contains(",")){
            int coma = name.indexOf(',');
            last = name.substring(0, coma);
            first = name.substring(coma+1, name.length());
        }else{//It is First Last
            int spc = name.indexOf(' ');
            first = name.substring(0, spc);
            last = name.substring(spc, name.length());
        }
        
        try{
            EmployeesDAO edao = new EmployeesDAO();
            Employees emp = edao.GetEmployeeByName(first.trim(), last.trim(), activeOnly);
            
            SalesmenDAO sdao = new SalesmenDAO();
            Salesmen sg = sdao.GetSalesmenByEmployeeID(emp.getIdemployees());
            
            return sg;
        }catch(Exception ex){
            System.out.println("SalesmenBL::GetSalesmenFromEmployeeName - "+ex.toString());
            return null;
        }
    }
    
    public int GroupMemeberCount(int grpId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            int members = 0;
                       
            String query = "SELECT COUNT(*) FROM salesGroupLookup sgl WHERE sgl.salesGroupId = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetInteger(pStmt, 1, grpId);
            
            ResultSet rs = pStmt.executeQuery();
            
            if(rs.next()){
                members = rs.getInt(1);
            }
            
            rs.close();
            pStmt.close();
            
            return members;
        }catch(Exception ex){
            System.out.println("SalesmenBL::GroupMemeberCount - "+ex.toString());
            return -1;
        }
    }
}
