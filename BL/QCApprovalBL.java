package BL;

import DAOS.QcRulesDAO;
import DOS.QcControlValues;
import DOS.QcRules;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @date:   Jun 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: BL
 * @file name: QCApprovalBL.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class QCApprovalBL 
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public ArrayList<QcControlValues> GetControlValuesForLot(int LotID)
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
            String query = "SELECT ctrl.* " +
            "FROM qcControlValues ctrl " +
            "INNER JOIN qcLot lot " +
            "ON lot.idqcLot = ctrl.lot " +
            "WHERE lot.active = true " +
            "AND lot.retired = false " +
            "AND lot.idqcLot = " + LotID;
            
            ArrayList<QcControlValues> controlList = new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                QcControlValues ctrl = new QcControlValues();
                ctrl.setIdqcControlValues(rs.getInt("idqcControlValues"));
                ctrl.setLot(rs.getInt("lot"));
                ctrl.setTestDescription(rs.getString("testDescription"));
                ctrl.setStd(rs.getDouble("std"));
                ctrl.setMean(rs.getDouble("mean"));
                ctrl.setMeanPlus2std(rs.getDouble("mean+2std"));
                ctrl.setMean_2std(rs.getDouble("mean-2std"));
                ctrl.setUnits(rs.getString("units"));
                ctrl.setDateEntered(rs.getDate("dateEntered"));
                ctrl.setEnteredBy(rs.getInt("enteredBy"));
                ctrl.setDateUpdated(rs.getDate("dateEntered"));
                ctrl.setUpdatedBy(rs.getInt("updatedBy"));
                ctrl.setControlNumber(rs.getInt("controlNumber"));
                controlList.add(ctrl);
            }
            
            rs.close();
            stmt.close();
            
            return controlList;
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
        
    }
    
    public ArrayList<QcRules> GetRulesForInstLevelAndLotId(Integer idInst, int level, int qcLotId)
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
            String query = "SELECT r.* " +
            "FROM qcInstrumentInfo ii " +
            "INNER JOIN qcRules r " +
            "ON ii.idqcRules = r.idqcRules " +
            "INNER JOIN qcLot l " +
            "ON l.idqcLot = " + qcLotId + " " +
            "INNER JOIN qcInstControls ic " +
            "ON ii.idqcInstControls = ic.id ";
            if (idInst != null)
            {
                query += "AND ic.idInst = " + idInst + " ";
            }
            query += "AND ic.idqcLot = l.idqcLot AND ic.level = " + level;
            if (idInst != null)
            {
                query += " WHERE ii.idInst = " + idInst;
            }
            
            ArrayList<QcRules> ruleList = new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                QcRules rule = new QcRules();
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
                ruleList.add(rule);
            }
            
            rs.close();
            stmt.close();
            
            return ruleList;
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
        
    }
    
    public ArrayList<QcRules> GetRulesForInstLevelAndControlOnlineCode(int idInst, int level, int onlineCode)
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
            String query = "SELECT r.* " +
            "FROM qcInstrumentInfo ii " +
            "INNER JOIN qcRules r " +
            "ON ii.idqcRules = r.idqcRules " +
            "INNER JOIN qcLot l " +
            "ON l.onlineCode = " + onlineCode + " " +
            "INNER JOIN qcInstControls ic " +
            "ON ii.idqcInstControls = ic.id AND ic.idInst = " + idInst + " " +
            "AND ic.idqcLot = l.idqcLot AND ic.level = " + level;
            
            ArrayList<QcRules> ruleList = new ArrayList<>();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                QcRules rule = new QcRules();
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
                ruleList.add(rule);
            }
            
            rs.close();
            stmt.close();
            
            return ruleList;
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
        
    }
}
