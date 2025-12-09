/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 10, 2014
 */

package BL;

import DOS.QcTests;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @date:   Jun 10, 2014  5:07:53 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcInventoryBL.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class QcInventoryBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public QcInventoryBL(){
        
    }
    
    public Map<Integer, String> XrefAutoCompleteSearch(int xref, String test, int specimen){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
        }
        
        try{
            Map<Integer, String> testMap = new HashMap<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `tx`.`transformedIn` AS 'Test', "
                    + "`t`.`number` AS 'Number' "
                    + "FROM `testXref` tx "
                    + "INNER JOIN `tests` t ON `tx`.`testNumber` = `t`.`number` "
                    + "LEFT JOIN `specimenTypes` s ON `t`.`specimenType` = `s`.`idspecimenTypes` "
                    + "WHERE `tx`.`name` = " + xref + " "
                    + "AND `tx`.`active` = " + true + " "
                    + "AND `tx`.`active` = " +true + " "
                    + "AND `t`.`active` = " + true + " "
                    + "AND `t`.`specimenType` = " + specimen + " "
                    + "AND `tx`.`transformedIn` LIKE '" + test + "%'";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                String name = rs.getString("Test");
                Integer num = rs.getInt("Number");
                
                testMap.put(num, name);
            }
            
            rs.close();
            stmt.close();
            
            return testMap;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public String GetDisplayNameForTest(QcTests qcTest)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return "SQL Connection Error";
        }
        
        try 
        {
            String out = "N/A";
            Statement stmt = con.createStatement();
            String query = "SELECT CONCAT(qcl.name, ' - [', t.number, '] ', t.name) AS 'display' FROM qcLot qcl, tests t "
                    + "WHERE qcl.idqcLot = " + qcTest.getReagent() 
                    + " AND t.number = " + qcTest.getNumber() + " AND t.active = true;";
            
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next())
            {
                out = rs.getString("display");
            }
            
            rs.close();
            stmt.close();
            return out;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return ex.getMessage();
        }
    }
    
    public ArrayList<String[]> GetLotSearch(String nameFragment, boolean Reagent, Integer specType)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try 
        {
            ArrayList<String[]> strList = new ArrayList<>();
            Statement stmt = con.createStatement();
            String query =  "SELECT " +
                            "lot.idqcLot AS 'id', " +
                            "lot.lotNumber AS 'Number', " +
                            "lot.name AS 'Name', " +
                            "DATE_FORMAT(lot.dateExpires, '%b %d, %Y') AS 'Expire', " +
                            "sp.name AS 'SpecType' " +
                            "FROM qcLot lot " +
                            "LEFT JOIN specimenTypes sp " +
                            "ON lot.idspecimenType = sp.idspecimentypes " +
                            "WHERE active = true " +
                            "AND lot.name LIKE '%" + nameFragment + "%' " +
                            (specType!=null?"AND lot.idspecimentype = " + specType + " ":"") +
                            "AND type = " + (Reagent?0:1);
            
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
                String[] str = new String[5];
                str[0] = rs.getString("id");
                str[1] = rs.getString("Number");
                str[2] = rs.getString("Name");
                str[3] = rs.getString("Expire");
                str[4] = rs.getString("SpecType");
                strList.add(str);
            }
            
            rs.close();
            stmt.close();
            return strList;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
}
