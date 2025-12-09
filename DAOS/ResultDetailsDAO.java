/*
 * Computer Service & Support, Inc.  All Rights Reserved Apr 13, 2015
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.ResultDetails;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @date: Apr 13, 2015 1:07:05 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: ResultDetailsDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class ResultDetailsDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`resultDetails`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    public ResultDetailsDAO() {
        fields.add("idResults");
        fields.add("queuePrinted");
        fields.add("dateQueuePrinted");
        fields.add("queuePrintedBy");
        fields.add("batch");
        fields.add("isAddOn");
        fields.add("quantity");
    }

    @Override
    public Boolean Insert(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            ResultDetails resdtl = (ResultDetails) obj;
            if (resdtl.getQuantity() == null || resdtl.getQuantity() == 0)
            {
                resdtl.setQuantity(1);
            }
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            pStmt = SetStatement(resdtl, pStmt);
            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            ResultDetails resdtl = (ResultDetails) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idResults` = " + resdtl.getIdResults();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(resdtl, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    @Override
    public Boolean Delete(Serializable obj) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        return false;
    }
    
    public ResultDetails getByResultId(Integer resultId)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try {
            ResultDetails resdtl = new ResultDetails();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idResults` = " + resultId;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(resdtl, rs);
            }

            rs.close();
            stmt.close();

            return resdtl;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
    }

    @Override
    public Serializable getByID(Integer ID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try {
            ResultDetails resdtl = new ResultDetails();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idResults` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(resdtl, rs);
            }

            rs.close();
            stmt.close();

            return resdtl;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetIDsNotPrintedByDepartment(int department){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<Integer> resultIds = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `rd`.`idresults` AS 'IDS' "
                + "FROM " + table + " rd "
                + "LEFT JOIN `results` r " 
                + "	ON `rd`.`idResults` = `r`.`idResults` "
                + "LEFT JOIN `tests` t "
                + "	ON `r`.`testid` = `t`.`idtests` "
                + "WHERE `rd`.`queuePrinted` = false "
                + "AND `t`.`department` = " + department;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resultIds.add(rs.getInt("IDS"));
            }
            
            rs.close();
            stmt.close();
            
            return resultIds;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ResultDetails> GetNotPrintedByDepartment(int department){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<ResultDetails> details = new ArrayList<>();
            ResultDetails resdtl = new ResultDetails();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `rd`.* "
                + "FROM " + table + " rd "
                + "LEFT JOIN `results` r " 
                + "	ON `rd`.`idResults` = `r`.`idResults` "
                + "LEFT JOIN `tests` t "
                + "	ON `r`.`testid` = `t`.`idtests` "
                + "WHERE `rd`.`queuePrinted` = false "
                + "AND `t`.`department` = " + department;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resdtl = new ResultDetails();
                FromResultSet(resdtl, rs);
                details.add(resdtl);
            }
            
            rs.close();
            stmt.close();
            
            return details;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetIDsNotPrintedByInstrument(int instrument){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<Integer> resultIds = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `rd`.`idresults` AS 'IDS' "
                + "FROM " + table + " rd "
                + "LEFT JOIN `results` r " 
                + "	ON `rd`.`idResults` = `r`.`idResults` "
                + "LEFT JOIN `tests` t "
                + "	ON `r`.`testid` = `t`.`idtests` "
                + "WHERE `rd`.`queuePrinted` = false "
                + "AND `t`.`instrument` = " + instrument;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resultIds.add(rs.getInt("IDS"));
            }
            
            rs.close();
            stmt.close();
            
            return resultIds;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ResultDetails> GetNotPrintedByInstrument(int instrument){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<ResultDetails> details = new ArrayList<>();
            ResultDetails resdtl = new ResultDetails();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `rd`.* "
                + "FROM " + table + " rd "
                + "LEFT JOIN `results` r " 
                + "	ON `rd`.`idResults` = `r`.`idResults` "
                + "LEFT JOIN `tests` t "
                + "	ON `r`.`testid` = `t`.`idtests` "
                + "WHERE `rd`.`queuePrinted` = false "
                + "AND `t`.`instrument` = " + instrument;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resdtl = new ResultDetails();
                FromResultSet(resdtl, rs);
                details.add(resdtl);
            }
            
            rs.close();
            stmt.close();
            
            return details;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<Integer> GetIDsByBatch(int batch){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<Integer> resultIds = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idResults` AS 'IDS' "
                + "FROM " + table + " "
                + "WHERE `batch` = " + batch;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resultIds.add(rs.getInt("IDS"));
            }
            
            rs.close();
            stmt.close();
            
            return resultIds;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public ArrayList<ResultDetails> GetByBatch(int batch){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            ArrayList<ResultDetails> details = new ArrayList<>();
            ResultDetails resdtl = new ResultDetails();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * "
                + "FROM " + table + " "
                + "WHERE `batch` = " + batch;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                resdtl = new ResultDetails();
                FromResultSet(resdtl, rs);
                details.add(resdtl);
            }
            
            rs.close();
            stmt.close();
            
            return details;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    public boolean UpdateDetailsFromPrint(ArrayList<Integer> ids, int user, int batch, java.util.Date datePrinted){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try{
            ResultDetails rd = null;
            for(Integer id : ids){
                rd = new ResultDetails();
                rd.setIdResults(id);
                rd.setQueuePrinted(true);
                rd.setDateQueuePrinted(datePrinted);
                rd.setQueuePrintedBy(user);
                rd.setBatch(batch);
                Update(rd);
            }
            
            return true;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }

    //--------------------------------------------------------------------------
    private String GenerateInsertStatement(ArrayList<String> fields) {

        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if (i != fields.size() - 1) {
                stmt += ", ";
                values += ",";
            }
        }
        values += ")";
        return stmt + values;
    }

    //--------------------------------------------------------------------------
    private String GenerateUpdateStatement(ArrayList<String> fields) {

        String stmt = "UPDATE " + table + " SET";
        for (int i = 0; i < fields.size(); ++i) {
            stmt += " `" + fields.get(i) + "` = ?";
            if (i != fields.size() - 1) {
                stmt += ",";
            }
        }
        return stmt;
    }
    
    //--------------------------------------------------------------------------
    public static ResultDetails FromResultSet(ResultDetails obj, ResultSet rs) throws SQLException
    {
        obj.setIdResults(rs.getInt("idResults"));
        obj.setQueuePrinted(rs.getBoolean("queuePrinted"));
        obj.setDateQueuePrinted(rs.getTimestamp("dateQueuePrinted"));
        obj.setQueuePrintedBy(rs.getInt("queuePrintedBy"));
        obj.setBatch(rs.getInt("batch"));
        obj.setIsAddOn(rs.getBoolean("isAddOn"));
        obj.setQuantity(rs.getInt("quantity"));
        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(ResultDetails obj, PreparedStatement pStmt) throws SQLException
    {
        int i = 0;
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getIdResults());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.getQueuePrinted());
        SQLUtil.SafeSetTimeStamp(pStmt, ++i, obj.getDateQueuePrinted());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQueuePrintedBy());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getBatch());
        SQLUtil.SafeSetBoolean(pStmt, ++i, obj.isAddOn());
        SQLUtil.SafeSetInteger(pStmt, ++i, obj.getQuantity());
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
