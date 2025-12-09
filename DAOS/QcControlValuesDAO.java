/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 9, 2014
 */

package DAOS;

import DAOS.IDAOS.IStructureCheckable;
import DOS.QcControlValues;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @date:   Jun 9, 2014  9:47:22 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcControlValuesDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class QcControlValuesDAO implements IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    String table = "`qcControlValues`";
    
    public boolean InsertValue(QcControlValues value){
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
                    + "`lot`, "
                    + "`testDescription`, "
                    + "`std`, "
                    + "`mean`, "
                    + "`mean-2std`, "
                    + "`mean+2std`, "
                    + "`units`, "
                    + "`dateEntered`, "
                    + "`enteredBy`, "
                    + "`dateUpdated`, "
                    + "`updatedBy`, "
                    + "`controlNumber`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, value.getLot());
            SQLUtil.SafeSetString(pStmt, 2, value.getTestDescription());
            SQLUtil.SafeSetRangeDouble(pStmt, 3, value.getStd());
            SQLUtil.SafeSetRangeDouble(pStmt,4, value.getMean());
            SQLUtil.SafeSetRangeDouble(pStmt,5, value.getMean_2std());
            SQLUtil.SafeSetRangeDouble(pStmt,6, value.getMeanPlus2std());
            SQLUtil.SafeSetString(pStmt, 7, value.getUnits());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDate(value.getDateEntered()));
            SQLUtil.SafeSetInteger(pStmt, 9, value.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 10, Convert.ToSQLDate(value.getDateUpdated()));
            SQLUtil.SafeSetInteger(pStmt, 11, value.getUpdatedBy());
            SQLUtil.SafeSetInteger(pStmt, 12, value.getControlNumber());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public Integer InsertValueGetNewID(QcControlValues value){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            String stmt = "INSERT INTO " + table + " ("
                    + "`lot`, "
                    + "`testDescription`, "
                    + "`std`, "
                    + "`mean`, "
                    + "`mean-2std`, "
                    + "`mean+2std`, "
                    + "`units`, "
                    + "`dateEntered`, "
                    + "`enteredBy`, "
                    + "`dateUpdated`, "
                    + "`updatedBy`, "
                    + "`controlNumber`) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement pStmt = con.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
            
            SQLUtil.SafeSetInteger(pStmt, 1, value.getLot());
            SQLUtil.SafeSetString(pStmt, 2, value.getTestDescription());
            SQLUtil.SafeSetRangeDouble(pStmt, 3, value.getStd());
            SQLUtil.SafeSetRangeDouble(pStmt,4, value.getMean());
            SQLUtil.SafeSetRangeDouble(pStmt,5, value.getMean_2std());
            SQLUtil.SafeSetRangeDouble(pStmt,6, value.getMeanPlus2std());
            SQLUtil.SafeSetString(pStmt, 7, value.getUnits());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDate(value.getDateEntered()));
            SQLUtil.SafeSetInteger(pStmt, 9, value.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 10, Convert.ToSQLDate(value.getDateUpdated()));
            SQLUtil.SafeSetInteger(pStmt, 11, value.getUpdatedBy());
            SQLUtil.SafeSetInteger(pStmt, 12, value.getControlNumber());
            
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            Integer key = -1;
            if (rs != null && rs.next())
            {
                key = rs.getInt(1);
            }
            pStmt.close();
            
            return key;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean UpdateValue(QcControlValues value){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "UPDATE " + table + " SET "
                    + "`lot` = ?, "
                    + "`testDescription` = ?, "
                    + "`std` = ?, "
                    + "`mean` = ?, "
                    + "`mean-2std` = ?, "
                    + "`mean+2std` = ?, "
                    + "`units` = ?, "
                    + "`dateEntered` = ?, "
                    + "`enteredBy` = ?, "
                    + "`dateUpdated` = ?, "
                    + "`updatedBy` = ?, "
                    + "`controlNumber` = ? "
                    + "WHERE `idqcControlValues` = " + value.getIdqcControlValues();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            SQLUtil.SafeSetInteger(pStmt, 1, value.getLot());
            SQLUtil.SafeSetString(pStmt, 2, value.getTestDescription());
            SQLUtil.SafeSetRangeDouble(pStmt, 3, value.getStd());
            SQLUtil.SafeSetRangeDouble(pStmt,4, value.getMean());
            SQLUtil.SafeSetRangeDouble(pStmt,5, value.getMean_2std());
            SQLUtil.SafeSetRangeDouble(pStmt,6, value.getMeanPlus2std());
            SQLUtil.SafeSetString(pStmt, 7, value.getUnits());
            SQLUtil.SafeSetDate(pStmt, 8, Convert.ToSQLDate(value.getDateEntered()));
            SQLUtil.SafeSetInteger(pStmt, 9, value.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 10, Convert.ToSQLDate(value.getDateUpdated()));
            SQLUtil.SafeSetInteger(pStmt, 11, value.getUpdatedBy());
            SQLUtil.SafeSetInteger(pStmt, 12, value.getControlNumber());
            
            pStmt.executeUpdate();

            pStmt.close();

            return true;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public QcControlValues GetControlValueByTestAndLot(String test, int lot){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcControlValues value = new QcControlValues();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `testDescription` = '" + test +"' "
                    + "AND `lot` = " + lot;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                value = SetQcControlValuesFromResultSet(value, rs);
            }
            
            rs.close();
            stmt.close();
            
            return value;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public List<QcControlValues> GetAllQcControlValues() {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            List<QcControlValues> cvList = new ArrayList<>();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcControlValues value = new QcControlValues();
                value = SetQcControlValuesFromResultSet(value, rs);
                cvList.add(value);
            }
            
            rs.close();
            stmt.close();
            
            return cvList;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    
    public ArrayList<QcControlValues> GetAllControlValuesByLotID(int LotID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcControlValues> cvList = new ArrayList<>();
            
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `lot` = " + LotID + " "
                    + "ORDER BY `testDescription` ASC";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                QcControlValues value = new QcControlValues();
                value = SetQcControlValuesFromResultSet(value, rs);
                cvList.add(value);
            }
            
            rs.close();
            stmt.close();
            
            return cvList;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public QcControlValues GetControlValueByID(Integer ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcControlValues value = new QcControlValues();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idqcControlValues` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                value = SetQcControlValuesFromResultSet(value, rs);
            }
            
            rs.close();
            stmt.close();
            
            return value;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public boolean DeleteById(Integer qcCVId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            String stmt = "DELETE FROM " + table + " "
                    + "WHERE `idqcControlValues` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetInteger(pStmt, 1, qcCVId);
            
            pStmt.executeUpdate();

            pStmt.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
            return false;
        }
        return true;
    }
    
    public QcControlValues SetQcControlValuesFromResultSet(QcControlValues value, ResultSet rs) throws SQLException{
        value.setIdqcControlValues(rs.getInt("idqcControlValues"));
        value.setLot(rs.getInt("lot"));
        value.setTestDescription(rs.getString("testDescription"));
        value.setStd(rs.getDouble("std"));
        value.setMean(rs.getDouble("mean"));
        value.setMean_2std(rs.getDouble("mean-2std"));
        value.setMeanPlus2std(rs.getDouble("mean+2std"));
        value.setUnits(rs.getString("units"));
        value.setDateEntered(rs.getDate("dateEntered"));
        value.setEnteredBy(rs.getInt("enteredBy"));
        value.setDateUpdated(rs.getDate("dateUpdated"));
        value.setUpdatedBy(rs.getInt("updatedBy"));
        value.setControlNumber(rs.getInt("controlNumber"));

        return value;
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `qcControlValues`.`idqcControlValues`,\n"
                + "    `qcControlValues`.`lot`,\n"
                + "    `qcControlValues`.`testDescription`,\n"
                + "    `qcControlValues`.`std`,\n"
                + "    `qcControlValues`.`mean`,\n"
                + "    `qcControlValues`.`mean-2std`,\n"
                + "    `qcControlValues`.`mean+2std`,\n"
                + "    `qcControlValues`.`units`,\n"
                + "    `qcControlValues`.`dateEntered`,\n"
                + "    `qcControlValues`.`enteredBy`,\n"
                + "    `qcControlValues`.`dateUpdated`,\n"
                + "    `qcControlValues`.`updatedBy`,\n"
                + "    `qcControlValues`.`controlNumber`\n"
                + "FROM `css`.`qcControlValues` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
