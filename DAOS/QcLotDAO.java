/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.QcLot;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;

import static Utility.SQLUtil.createStatement;

/**
 *
 * @author Ryan
 */
public class QcLotDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    String table = "`qcLot`";
    
    /**
     * Insert a lot into the table.  Reagent or Control
     * @param lot QcLot Class Object
     * @return boolean True if successful, false if not.
     */
    public boolean InsertLot (QcLot lot){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String stmt = " INSERT INTO " + table + " ("
                    + "`name`, "
                    + "`type`, "
                    + "`description`, "
                    + "`dateReceived`, "
                    + "`dateUsed`, "
                    + "`dateExpires`, "
                    + "`dateReconstitution`, "
                    + "`stability`, "
                    + "`quantity`, "
                    + "`units`, "
                    + "`status`, "
                    + "`enteredBy`, "
                    + "`enteredOn`, "
                    + "`active`, "
                    + "`activatedOn`, "
                    + "`activatedBy`, "
                    + "`retired`, "
                    + "`retiredOn`, "
                    + "`retiredBy`,"
                    + "`idspecimenType`,"
                    + "`lotNumber`,"
                    + "`onlineCode`,"
                    + "`percentageBased`,"
                    + "`negative`,"
                    + "`idxrefs`, "
                    + "`idmanufacturer` ) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, lot.getName());
            pStmt.setInt(2, lot.getType());
            //SQLUtil.SafeSetRangeInteger(pStmt, 2, lot.getType());
            SQLUtil.SafeSetString(pStmt, 3, lot.getDescription());
            SQLUtil.SafeSetDate(pStmt, 4, Convert.ToSQLDate(lot.getDateReceived()));
            SQLUtil.SafeSetDate(pStmt, 5, Convert.ToSQLDate(lot.getDateUsed()));
            SQLUtil.SafeSetDate(pStmt, 6, Convert.ToSQLDate(lot.getDateExpires()));
            SQLUtil.SafeSetDate(pStmt, 7, Convert.ToSQLDate(lot.getDateReconstitution()));
            SQLUtil.SafeSetInteger(pStmt, 8, lot.getStability());
            SQLUtil.SafeSetDouble(pStmt, 9, lot.getQuantity());
            SQLUtil.SafeSetInteger(pStmt, 10, lot.getUnits());
            SQLUtil.SafeSetBoolean(pStmt, 11, lot.getStatus());
            SQLUtil.SafeSetInteger(pStmt, 12, lot.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 13, Convert.ToSQLDateTime(lot.getEnteredOn()));
            SQLUtil.SafeSetBoolean(pStmt, 14, lot.getActive());
            SQLUtil.SafeSetDate(pStmt, 15, Convert.ToSQLDateTime(lot.getActivatedOn()));
            SQLUtil.SafeSetInteger(pStmt, 16, lot.getActivatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, lot.getRetired());
            SQLUtil.SafeSetDate(pStmt, 18, Convert.ToSQLDateTime(lot.getRetiredOn()));
            SQLUtil.SafeSetInteger(pStmt, 19, lot.getRetiredBy());
            SQLUtil.SafeSetInteger(pStmt, 20, lot.getIdspecimenType());
            SQLUtil.SafeSetString(pStmt, 21, lot.getLotNumber());
            SQLUtil.SafeSetString(pStmt, 22, lot.getOnlineCode());
            SQLUtil.SafeSetBoolean(pStmt, 23, lot.isPercentageBased());
            SQLUtil.SafeSetBoolean(pStmt, 24, lot.isNegative());
            SQLUtil.SafeSetInteger(pStmt, 25, lot.getIdxrefs());
            SQLUtil.SafeSetInteger(pStmt, 26, lot.getIdmanufacturer());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    /**
     * Insert a lot into the table and return the identifier.  Reagent or Control
     * @param lot QcLot Class Object
     * @return boolean the new identifier if successful, null otherwise
     */
    public Integer InsertLotGetID (QcLot lot){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            con.setAutoCommit(false);
            Savepoint save1 = con.setSavepoint();
            String stmt = " INSERT INTO " + table + " ("
                    + "`name`, "
                    + "`type`, "
                    + "`description`, "
                    + "`dateReceived`, "
                    + "`dateUsed`, "
                    + "`dateExpires`, "
                    + "`dateReconstitution`, "
                    + "`stability`, "
                    + "`quantity`, "
                    + "`units`, "
                    + "`status`, "
                    + "`enteredBy`, "
                    + "`enteredOn`, "
                    + "`active`, "
                    + "`activatedOn`, "
                    + "`activatedBy`, "
                    + "`retired`, "
                    + "`retiredOn`, "
                    + "`retiredBy`,"
                    + "`idspecimenType`,"
                    + "`lotNumber`,"
                    + "`onlineCode`,"
                    + "`percentageBased`,"
                    + "`negative`,"
                    + "`idxrefs`, "
                    + "`idmanufacturer` ) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement pStmt = con.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS);

            SQLUtil.SafeSetString(pStmt, 1, lot.getName());
            pStmt.setInt(2, lot.getType());
            //SQLUtil.SafeSetRangeInteger(pStmt, 2, lot.getType());
            SQLUtil.SafeSetString(pStmt, 3, lot.getDescription());
            SQLUtil.SafeSetDate(pStmt, 4, Convert.ToSQLDate(lot.getDateReceived()));
            SQLUtil.SafeSetDate(pStmt, 5, Convert.ToSQLDate(lot.getDateUsed()));
            SQLUtil.SafeSetDate(pStmt, 6, Convert.ToSQLDate(lot.getDateExpires()));
            SQLUtil.SafeSetDate(pStmt, 7, Convert.ToSQLDate(lot.getDateReconstitution()));
            SQLUtil.SafeSetInteger(pStmt, 8, lot.getStability());
            SQLUtil.SafeSetDouble(pStmt, 9, lot.getQuantity());
            SQLUtil.SafeSetInteger(pStmt, 10, lot.getUnits());
            SQLUtil.SafeSetBoolean(pStmt, 11, lot.getStatus());
            SQLUtil.SafeSetInteger(pStmt, 12, lot.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 13, Convert.ToSQLDateTime(lot.getEnteredOn()));
            SQLUtil.SafeSetBoolean(pStmt, 14, lot.getActive());
            SQLUtil.SafeSetDate(pStmt, 15, Convert.ToSQLDateTime(lot.getActivatedOn()));
            SQLUtil.SafeSetInteger(pStmt, 16, lot.getActivatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, lot.getRetired());
            SQLUtil.SafeSetDate(pStmt, 18, Convert.ToSQLDateTime(lot.getRetiredOn()));
            SQLUtil.SafeSetInteger(pStmt, 19, lot.getRetiredBy());
            SQLUtil.SafeSetInteger(pStmt, 20, lot.getIdspecimenType());
            SQLUtil.SafeSetString(pStmt, 21, lot.getLotNumber());
            SQLUtil.SafeSetString(pStmt, 22, lot.getOnlineCode());
            SQLUtil.SafeSetBoolean(pStmt, 23, lot.isPercentageBased());
            SQLUtil.SafeSetBoolean(pStmt, 24, lot.isNegative());
            SQLUtil.SafeSetInteger(pStmt, 25, lot.getIdxrefs());
            SQLUtil.SafeSetInteger(pStmt, 26, lot.getIdmanufacturer());

            int executeVal = pStmt.executeUpdate();
            Integer identifier = null;
            if (executeVal == 0)
            {
                System.out.println("Could not insert QcLot with lot number = " + lot.getLotNumber());
                con.rollback(save1);
            }
            else
            {
                ResultSet rs = pStmt.getGeneratedKeys();
                if (rs.next()) {
                    identifier = rs.getInt(1);
                }
                else
                {
                    con.rollback(save1);
                }
            }

            con.commit();
            pStmt.close();
            con.setAutoCommit(true);

            return identifier;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Updates a lot in the table by ID.  Reagent or Control.
     * @param lot QcLot Class Object
     * @return boolean True if successful, false if not. 
     */
    public boolean UpdateLot(QcLot lot){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try {
            String stmt = " UPDATE " + table + " SET "
                    + "`name` = ?, "
                    + "`type` = ?, "
                    + "`description` = ?, "
                    + "`dateReceived` = ?, "
                    + "`dateUsed` = ?, "
                    + "`dateExpires` = ?, "
                    + "`dateReconstitution` = ?, "
                    + "`stability` = ?, "
                    + "`quantity` = ?, "
                    + "`units` = ?, "
                    + "`status` = ?, "
                    + "`enteredBy` = ?, "
                    + "`enteredOn` = ?, "
                    + "`active` = ?, "
                    + "`activatedOn` = ?, "
                    + "`activatedBy` = ?, "
                    + "`retired` = ?, "
                    + "`retiredOn` = ?, "
                    + "`retiredBy` = ?,"
                    + "`idspecimenType` = ?, "
                    + "`lotNumber` = ?,"
                    + "`onlineCode` = ?,"
                    + "`percentageBased` = ?, "
                    + "`negative` = ?, "
                    + "`idxrefs` = ?, " 
                    + "`idmanufacturer` = ? "
                    + "WHERE `idqcLot` = " + lot.getIdqcLot();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SQLUtil.SafeSetString(pStmt, 1, lot.getName());
            pStmt.setInt(2, lot.getType());
            //SQLUtil.SafeSetInteger(pStmt, 2, lot.getType());
            SQLUtil.SafeSetString(pStmt, 3, lot.getDescription());
            SQLUtil.SafeSetDate(pStmt, 4, Convert.ToSQLDate(lot.getDateReceived()));
            SQLUtil.SafeSetDate(pStmt, 5, Convert.ToSQLDate(lot.getDateUsed()));
            SQLUtil.SafeSetDate(pStmt, 6, Convert.ToSQLDate(lot.getDateExpires()));
            SQLUtil.SafeSetDate(pStmt, 7, Convert.ToSQLDate(lot.getDateReconstitution()));
            SQLUtil.SafeSetInteger(pStmt, 8, lot.getStability());
            SQLUtil.SafeSetDouble(pStmt, 9, lot.getQuantity());
            SQLUtil.SafeSetInteger(pStmt, 10, lot.getUnits());
            SQLUtil.SafeSetBoolean(pStmt, 11, lot.getStatus());
            SQLUtil.SafeSetInteger(pStmt, 12, lot.getEnteredBy());
            SQLUtil.SafeSetDate(pStmt, 13, Convert.ToSQLDateTime(lot.getEnteredOn()));
            SQLUtil.SafeSetBoolean(pStmt, 14, lot.getActive());
            SQLUtil.SafeSetDate(pStmt, 15, Convert.ToSQLDateTime(lot.getActivatedOn()));
            SQLUtil.SafeSetInteger(pStmt, 16, lot.getActivatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, lot.getRetired());
            SQLUtil.SafeSetDate(pStmt, 18, Convert.ToSQLDateTime(lot.getRetiredOn()));
            SQLUtil.SafeSetInteger(pStmt, 19, lot.getRetiredBy());
            SQLUtil.SafeSetInteger(pStmt, 20, lot.getIdspecimenType());
            SQLUtil.SafeSetString(pStmt, 21, lot.getLotNumber());
            SQLUtil.SafeSetString(pStmt, 22, lot.getOnlineCode());
            SQLUtil.SafeSetBoolean(pStmt, 23, lot.isPercentageBased());
            SQLUtil.SafeSetBoolean(pStmt, 24, lot.isNegative());
            SQLUtil.SafeSetInteger(pStmt, 25, lot.getIdxrefs());
            SQLUtil.SafeSetInteger(pStmt, 26, lot.getIdmanufacturer());

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }
    
    public QcLot GetLotByName(String name){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcLot lot = new QcLot();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `name` = ?;";
            
            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                lot = QcLotFromResultSet(lot, rs);
            }
            
            rs.close();
            stmt.close();
            
            return lot;
        }catch(Exception ex){
            System.out.println("Exception fetching Lot by name: " + ex.toString());
            return null;
        }
    }
    
    public QcLot GetLotByNumber(String number){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcLot lot = new QcLot();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `lotNumber` = ? "
                    + "AND `active` = 1;";
            
            stmt = createStatement(con, query, number);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                lot = QcLotFromResultSet(lot, rs);
            }
            
            rs.close();
            stmt.close();
            
            return lot;
        }catch(Exception ex){
            System.out.println("Exception fetching Lot by number: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Names of Active Controls AND Reagents.
     * The only method that returns both types.
     * @return ArrayList<String> Names
     */
    public ArrayList<String> GetAllActiveLotNames(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<String> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `name` FROM " + table + " "
                    + "WHERE `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Active Names: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Selects and populates a QcLot object by it's table ID
     * @param id int table id
     * @return QcLot Object populated
     */
    public QcLot GetLotbyId(int id){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idqcLot` = " + id;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                QcLotFromResultSet(lot,rs);
            }
            
            rs.close();
            stmt.close();
            
            return lot;
        }catch(Exception ex){
            System.out.println("Exception Fetching by ID: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Names of Active Controls or Reagents.
     * @param type int Represents lot type, Reagent = 0, Control = 1
     * @return ArrayList<String> Names
     */
    public ArrayList<String> GetAllActiveLotNames(int type){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<String> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `name` FROM " + table + " "
                    + "WHERE `type` = " + type + " "
                    + "AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Active Names: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Names of Controls or Reagents. Regardless of active status
     * @param type int Represents lot type, Reagent = 0, Control = 1
     * @return ArrayList<String> Names
     */
    public ArrayList<String> GetAllLotNames(int type){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<String> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `name` FROM " + table + " "
                    + "WHERE `type` = " + type;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Names: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Active Controls or Reagents.
     * @param type int Represents lot type, Reagent = 0, Control = 1
     * @return ArrayList<QcLot> List of QcLot class objects
     */
    public ArrayList<QcLot> GetAllActiveLots(int type){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcLot> list = new ArrayList<>();
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `type` = " + type + " "
                    + "AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                lot= new QcLot();
                QcLotFromResultSet(lot,rs); 
                list.add(lot);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Active Lots: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Active Controls or Reagents.
     * @return ArrayList<QcLot> List of QcLot class objects
     */
    public ArrayList<QcLot> GetAllActiveLots(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return new ArrayList<QcLot>();
        }
        
        ArrayList<QcLot> list = new ArrayList<>();
        try{
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true + " "
                    + "ORDER BY `name` ASC";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                lot= new QcLot();
                QcLotFromResultSet(lot,rs); 
                list.add(lot);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Active Lots: " + ex.toString());
            return list;
        }
    }
    
    /**
     * Returns all Retired Controls or Reagents.
     * @param type int Represents lot type, Reagent = 0, Control = 1
     * @return ArrayList<QcLot> List of QcLot class objects
     */
    public ArrayList<QcLot> GetAllRetiredLots(int type){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcLot> list = new ArrayList<>();
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `type` = " + type + " "
                    + "AND `retired` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                lot= new QcLot();
                QcLotFromResultSet(lot,rs); 
                list.add(lot);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Retired Lots: " + ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all Retired Controls or Reagents.
     * @return ArrayList<QcLot> List of QcLot class objects
     */
    public ArrayList<QcLot> GetAllRetiredLots(){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return new ArrayList<QcLot>();
        }
        
        ArrayList<QcLot> list = new ArrayList<>();
        try{
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `retired` = " + true + " "
                    + "ORDER BY `name` ASC";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                lot= new QcLot();
                QcLotFromResultSet(lot,rs); 
                list.add(lot);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Retired Lots: " + ex.toString());
            return list;
        }
    }
    
    /**
     * Returns all Active Controls or Reagents based on specimen type.
     * @param type int Represents lot type, Reagent = 0, Control = 1
     * @param specimen int Represents idspecimenTypes from specimenTpes table
     * @return ArrayList<QcLot> List of QcLot class objects
     */
    public ArrayList<QcLot> GetAllActiveLotsBySpecimenType(int type, int specimen){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<QcLot> list = new ArrayList<>();
            QcLot lot = new QcLot();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `active` = " + true + " "
                    + "AND `type` = " + type + " "
                    + "AND `idspecimenType` = " + specimen;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                lot= new QcLot();
                QcLotFromResultSet(lot,rs); 
                list.add(lot);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("Exception Fetching All Active Lots By Specimen: " + ex.toString());
            return null;
        }
    }
    
    
    
    public QcLot QcLotFromResultSet(QcLot lot, ResultSet rs) throws SQLException{
        lot.setIdqcLot(rs.getInt("idqcLot"));
        lot.setName(rs.getString("name"));
        lot.setType(rs.getInt("type"));
        lot.setDescription(rs.getString("description"));
        lot.setDateReceived(rs.getDate("dateReceived"));
        lot.setDateUsed(rs.getDate("dateUsed"));
        lot.setDateExpires(rs.getDate("dateExpires"));
        lot.setDateReconstitution(rs.getDate("dateReconstitution"));
        lot.setStability(rs.getInt("stability"));
        lot.setQuantity(rs.getDouble("quantity"));
        lot.setUnits(rs.getInt("units"));
        lot.setStatus(rs.getBoolean("status"));
        lot.setEnteredBy(rs.getInt("enteredBy"));
        lot.setEnteredOn(rs.getTimestamp("enteredOn"));
        lot.setActive(rs.getBoolean("active"));
        lot.setActivatedOn(rs.getTimestamp("activatedOn"));
        lot.setActivatedBy(rs.getInt("activatedBy"));
        lot.setRetired(rs.getBoolean("retired"));
        lot.setRetiredOn(rs.getTimestamp("retiredOn"));
        lot.setRetiredBy(rs.getInt("retiredBy"));
        lot.setIdspecimenType(rs.getInt("idspecimenType"));
        lot.setLotNumber(rs.getString("lotNumber"));
        lot.setOnlineCode(rs.getString("onlineCode"));
        lot.setPercentageBased(rs.getBoolean("percentageBased"));
        lot.setNegative(rs.getBoolean("negative"));
        lot.setIdxrefs(rs.getInt("idxrefs"));
        lot.setIdmanufacturer(rs.getInt("idmanufacturer"));
        
        return lot;
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
        String query = "SELECT `qcLot`.`idqcLot`,\n"
                + "    `qcLot`.`name`,\n"
                + "    `qcLot`.`type`,\n"
                + "    `qcLot`.`description`,\n"
                + "    `qcLot`.`dateReceived`,\n"
                + "    `qcLot`.`dateUsed`,\n"
                + "    `qcLot`.`dateExpires`,\n"
                + "    `qcLot`.`dateReconstitution`,\n"
                + "    `qcLot`.`stability`,\n"
                + "    `qcLot`.`quantity`,\n"
                + "    `qcLot`.`units`,\n"
                + "    `qcLot`.`status`,\n"
                + "    `qcLot`.`enteredBy`,\n"
                + "    `qcLot`.`enteredOn`,\n"
                + "    `qcLot`.`active`,\n"
                + "    `qcLot`.`activatedOn`,\n"
                + "    `qcLot`.`activatedBy`,\n"
                + "    `qcLot`.`retired`,\n"
                + "    `qcLot`.`retiredOn`,\n"
                + "    `qcLot`.`retiredBy`,\n"
                + "    `qcLot`.`idspecimenType`,\n"
                + "    `qcLot`.`lotNumber`,\n"
                + "    `qcLot`.`onlineCode`,\n"
                + "    `qcLot`.`percentageBased`,\n"
                + "    `qcLot`.`negative`,\n"
                + "    `qcLot`.`idxrefs`,\n"
                + "    `qcLot`.`idmanufacturer`\n"
                + "FROM `css`.`qcLot` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
    
}
