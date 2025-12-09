/*
 * Computer Service & Support, Inc.  All Rights Reserved May 28, 2014
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcRules;
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

/**
 * @date:   May 28, 2014  5:52:08 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcRulesDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class QcRulesDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`qcRules`";
    
    public boolean InsertRule(QcRules rule) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "INSERT INTO " + table + " ("
                    + "`name`, "
                    + "`runs`, "
                    + "`isPercentage`, "
                    + "`isWestGuard`, "
                    + "`isCustom`, "
                    + "`created`,"
                    + "`createdBy`,"
                    + "`value`,"
                    + "`direction`,"
                    + "`type) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, rule.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, rule.getRuns());
            SQLUtil.SafeSetBoolean(pStmt, 3, rule.getIsPercentage());
            SQLUtil.SafeSetBoolean(pStmt, 4, rule.getIsWestGuard());
            SQLUtil.SafeSetBoolean(pStmt, 5, rule.getIsCustom());
            SQLUtil.SafeSetDate(pStmt, 6, Convert.ToSQLDateTime(rule.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 7, rule.getCreatedBy());
            SQLUtil.SafeSetInteger(pStmt, 8, rule.getValue());
            SQLUtil.SafeSetString(pStmt, 9, rule.getDirection());
            SQLUtil.SafeSetString(pStmt, 10, rule.getType());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    public boolean UpdateRule(QcRules rule){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "UPDATE " + table + " SET"
                    + "`name` = ?, "
                    + "`runs` = ?, "
                    + "`isPercentage` = ?, "
                    + "`isWestGuard` = ?, "
                    + "`isCustom` = ?, "
                    + "`created` = ?, "
                    + "`createdBy` = ?, "
                    + "`value` = ?, "
                    + "`direction` = ?, "
                    + "`type` = ? "
                    + "WHERE `idqcRules` = " + rule.getIdqcRules();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetString(pStmt, 1, rule.getName());
            SQLUtil.SafeSetInteger(pStmt, 2, rule.getRuns());
            SQLUtil.SafeSetBoolean(pStmt, 3, rule.getIsPercentage());
            SQLUtil.SafeSetBoolean(pStmt, 4, rule.getIsWestGuard());
            SQLUtil.SafeSetBoolean(pStmt, 5, rule.getIsCustom());
            SQLUtil.SafeSetDate(pStmt, 6, Convert.ToSQLDateTime(rule.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 7, rule.getCreatedBy());
            SQLUtil.SafeSetInteger(pStmt, 8, rule.getValue());
            SQLUtil.SafeSetString(pStmt, 9, rule.getDirection());
            SQLUtil.SafeSetString(pStmt, 10, rule.getType());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            return false;
        }
    }
    
    public QcRules GetQcRuleByID(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcRules rule = new QcRules();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idqcRules` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                setRuleFromResultSet(rule, rs);
            }
            
            rs.close();
            stmt.close();
            
            return rule;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<QcRules> GetAllRules(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcRules> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcRules rule = new QcRules();
                setRuleFromResultSet(rule, rs);
                
                list.add(rule);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<QcRules> GetAllWestGardRules(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcRules> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " WHERE `isWestGuard` = true;";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcRules rule = new QcRules();
                setRuleFromResultSet(rule, rs);
                
                list.add(rule);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<QcRules> GetAllCustomRules(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcRules> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " WHERE `isCustom` = true;";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcRules rule = new QcRules();
                setRuleFromResultSet(rule, rs);
                
                list.add(rule);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    private QcRules setRuleFromResultSet(QcRules rule, ResultSet rs) throws SQLException {

        rule.setIdqcRules(rs.getInt("idqcRules"));
        rule.setName(rs.getString("name"));
        rule.setRuns(rs.getInt("runs"));
        rule.setIsPercentage(rs.getBoolean("isPercentage"));
        rule.setIsWestGuard(rs.getBoolean("isWestGuard"));
        rule.setIsCustom(rs.getBoolean("isCustom"));
        rule.setCreated(rs.getTimestamp("created"));
        rule.setCreatedBy(rs.getInt("createdBy"));
        rule.setValue(rs.getInt("value"));
        rule.setDirection(rs.getString("direction"));
        rule.setType(rs.getString("type"));
        
        return rule;
    }

    @Override
    public Boolean Insert(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `qcrules`.`idqcRules`,\n"
                + "    `qcrules`.`name`,\n"
                + "    `qcrules`.`runs`,\n"
                + "    `qcrules`.`isPercentage`,\n"
                + "    `qcrules`.`isWestGuard`,\n"
                + "    `qcrules`.`isCustom`,\n"
                + "    `qcrules`.`created`,\n"
                + "    `qcrules`.`createdBy`,\n"
                + "    `qcrules`.`value`,\n"
                + "    `qcrules`.`direction`,\n"
                + "    `qcrules`.`type`\n"
                + "FROM `css`.`qcrules` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
