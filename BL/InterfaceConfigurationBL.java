/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package BL;

import BOS.InterfaceConfigurationBO;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/10/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class InterfaceConfigurationBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);

    public InterfaceConfigurationBL() {
    }
    
    //<editor-fold defaultstate="collapsed" desc="Tests">
    
    /**
     * This can be run before/after a search to get the number of rows returned
     *
     * @param testNumber Test Number entered in for search
     * @param testName Test Name entered in search field
     * @param transIn Transformed In field
     * @param transOut TransformedOut Field
     * @param xrefName The cross reference id that is to be searched
     * @return The count of rows search returned
     */
    public int CountTestXrefSeachQuery(int testNumber, 
                                       String testName,
                                       String transIn, 
                                       String transOut, 
                                       int xrefName,
                                       String specName) {
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        //Build Query
        StringBuilder query = new StringBuilder();
        //Basic Select used every time
        query.append("SELECT COUNT(*) AS 'Count' "
                + "FROM testXref tx "
                + "JOIN tests t "
                + "    ON tx.testNumber = t.number AND t.active = TRUE "
                + "LEFT JOIN specimenTypes s "
                + "    ON t.specimenType = s.idspecimenTypes "
                + "WHERE tx.active = " + true + " "
                + "AND tx.name = " + xrefName + " "
                + "AND t.active = " + true + " ");
        //Now finish and build where accordingly
        if (testNumber > 0) {
            query.append(" AND tx.testNumber = " + testNumber + " ");
        }
        if (testName != null && testName.isEmpty() == false) {
            query.append(" AND t.name LIKE '" + testName + "%' ");
        }
        if (transIn != null && transIn.isEmpty() == false) {
            query.append(" AND tx.transformedIn LIKE '" + transIn + "%' ");
        }
        if (transOut != null && transOut.isEmpty() == false) {
            query.append(" AND tx.transformedOut LIKE '" + transOut + "%' ");
        }
        if(specName != null && specName.isEmpty() == false)
        {
            query.append(" AND s.name LIKE '" + specName + "%' ");
        }
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("Count");
            }
            rs.close();
            stmt.close();
            
            return count;
        } catch (SQLException sx) {
            System.out.println(sx.toString());
            return -1;
        }
    }
    
    public InterfaceConfigurationBO GetTestXref(int testNumber,
                                                String transIn, 
                                                String transOut, 
                                                int xrefName,
                                                String specName) {
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            InterfaceConfigurationBO icbo = new InterfaceConfigurationBO();
            
            StringBuilder query = new StringBuilder();
            //Basic Select used every time
            query.append("SELECT `tx`.`idTestXref` AS 'idTestXref', "
                    + "`tx`.`testNumber` AS 'testNumber', "
                    + "`t`.`name` AS 'testName', "
                    + "`tx`.`transformedIn` AS 'transIn', "
                    + "`tx`.`transformedOut` AS 'transOut', "
                    + "`tx`.`name` AS 'name',"
                    + "`tx`.`active` AS 'active' "
                    + "FROM `testXref` tx "
                    + "JOIN `tests` t "
                    + "ON `tx`.`testNumber` = `t`.`number` AND `t`.`active` = " + true + " "
                    + "LEFT JOIN `specimenTypes` s " 
                    + "ON t.`specimenType` = s.`idspecimenTypes` "
                    + "WHERE `tx`.`active` = " + true + " "
                    + "AND `tx`.`name` = " + xrefName + " "
                    + "AND `t`.`active` = " + true + " ");
            //Now finish and build where accordingly
            if (testNumber > 0) {
                query.append(" AND `tx`.`testNumber` = " + testNumber + " ");
            }
            if (transIn != null && transIn.isEmpty() == false) {
                query.append(" AND `tx`.`transformedIn` = '" + transIn + "' ");
            }
            if (transOut != null && transOut.isEmpty() == false) {
                query.append(" AND `tx`.`transformedOut` = '" + transOut + "' ");
            }
            if(specName != null && specName.isEmpty() == false)
            {
                query.append(" AND s.name LIKE '" + specName + "%' ");
            }
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            
            if (rs.next()) {
                icbo.setIdTestXref(rs.getInt("idTestXref"));
                icbo.setTestNumber(rs.getInt("testNumber"));
                icbo.setTestName(rs.getString("testName"));
                icbo.setTransIn(rs.getString("transIn"));
                icbo.setTransOut(rs.getString("transOut"));
                icbo.setName(rs.getInt("name"));
                icbo.setActive(rs.getBoolean("active"));
            }
            
            rs.close();
            stmt.close();
            
            return icbo;
        } catch (SQLException sx) {
            System.out.println(sx.toString());
            return null;
        }
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Clients">
    public int CountClientXrefSeachQuery(int testNumber, String testName,
                                         String transIn, int xrefName) {
        
        /*try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        //Build Query
        StringBuilder query = new StringBuilder();
        //Basic Select used every time
        query.append("SELECT COUNT(*) AS 'Count' "
                + "FROM testXref tx "
                + "JOIN tests t "
                + "    ON tx.testNumber = t.number AND t.active = TRUE "
                + "WHERE tx.active = " + true + " "
                + "AND tx.name = " + xrefName + " "
                + "AND t.active = " + true + " ");
        //Now finish and build where accordingly
        if (testNumber > 0) {
            query.append(" AND tx.testNumber = " + testNumber + " ");
        }
        if (testName != null && testName.isEmpty() == false) {
            query.append(" AND t.name LIKE '" + testName + "%' ");
        }
        if (transIn != null && transIn.isEmpty() == false) {
            query.append(" AND tx.transformedIn LIKE '" + transIn + "%' ");
        }
        if (transOut != null && transOut.isEmpty() == false) {
            query.append(" AND tx.transformedOut LIKE '" + transOut + "%' ");
        }
        
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            int count = 0;
            if (rs.next()) {
                count = rs.getInt("Count");
            }
            rs.close();
            stmt.close();
            
            return count;
        } catch (SQLException sx) {
            System.out.println(sx.toString());
            return -1;
        }*/
        
        return -1;
    }
    
    public InterfaceConfigurationBO GetClientXref(int testNumber,
                                                  String transIn, int xrefName) {
        
        /*try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            InterfaceConfigurationBO icbo = new InterfaceConfigurationBO();
            
            StringBuilder query = new StringBuilder();
            //Basic Select used every time
            query.append("SELECT `tx`.`idTestXref` AS 'idTestXref', "
                    + "`tx`.`testNumber` AS 'testNumber', "
                    + "`t`.`name` AS 'testName', "
                    + "`tx`.`transformedIn` AS 'transIn', "
                    + "`tx`.`transformedOut` AS 'transOut', "
                    + "`tx`.`name` AS 'name',"
                    + "`tx`.`active` AS 'active' "
                    + "FROM `testXref` tx "
                    + "JOIN `tests` t "
                    + "ON `tx`.`testNumber` = `t`.`number` AND `t`.`active` = " + true + " "
                    + "WHERE `tx`.`active` = " + true + " "
                    + "AND `tx`.`name` = " + xrefName + " "
                    + "AND `t`.`active` = " + true + " ");
            //Now finish and build where accordingly
            if (testNumber > 0) {
                query.append(" AND `tx`.`testNumber` = " + testNumber + " ");
            }
            if (transIn != null && transIn.isEmpty() == false) {
                query.append(" AND `tx`.`transformedIn` = '" + transIn + "' ");
            }
            if (transOut != null && transOut.isEmpty() == false) {
                query.append(" AND `tx`.`transformedOut` = '" + transOut + "' ");
            }
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query.toString());
            
            if (rs.next()) {
                icbo.setIdTestXref(rs.getInt("idTestXref"));
                icbo.setTestNumber(rs.getInt("testNumber"));
                icbo.setTestName(rs.getString("testName"));
                icbo.setTransIn(rs.getString("transIn"));
                icbo.setTransOut(rs.getString("transOut"));
                icbo.setName(rs.getInt("name"));
                icbo.setActive(rs.getBoolean("active"));
            }
            
            rs.close();
            stmt.close();
            
            return icbo;
        } catch (SQLException sx) {
            System.out.println(sx.toString());
            return null;
        }*/
        return null;
    }
//</editor-fold>
}
