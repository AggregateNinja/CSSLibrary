/*
 * Computer Service & Support, Inc.  All Rights Reserved May 12, 2015
 */

package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.CarbonCopies;
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

import static Utility.SQLUtil.createStatement;

/**
 * @date:   May 12, 2015  9:30:12 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: CarbonCopiesDAO.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class CarbonCopiesDAO implements DAOInterface, IStructureCheckable {

    //--------------------------------------------------------------------------
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`carbonCopies`";

    private final ArrayList<String> fields = new ArrayList<>();
    ArrayList<Integer> generatedIDs = new ArrayList<>();

    //--------------------------------------------------------------------------
    public CarbonCopiesDAO() {
        fields.add("idOrders");
        fields.add("faxName");
        fields.add("faxNumber");
        fields.add("doctor");
        fields.add("client");
        fields.add("faxMemo");
        fields.add("created");
        fields.add("faxed");
        fields.add("isCascading");
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
            CarbonCopies cc = (CarbonCopies) obj;
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatement(cc, pStmt);
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
            CarbonCopies cc = (CarbonCopies) obj;
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `id` = " + cc.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatement(cc, pStmt);

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
        try{
            CarbonCopies cc = (CarbonCopies) obj;
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `id` = " + cc.getId();
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
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
            return null;
        }
        try{
            CarbonCopies cc = new CarbonCopies();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `id` = " + ID;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Fetches a CarbonCopies row by orderID and faxName
     * @param OrderID Integer of idorders
     * @param faxName String of FaxName
     * @return 
     */
    public CarbonCopies GetByOrderIDAndFaxName(Integer OrderID, String faxName) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            CarbonCopies cc = new CarbonCopies();
            PreparedStatement stmt = null; //con.createStatement();

            String query = "SELECT * FROM " + table
                    + " WHERE `idOrders` = " + OrderID
                    + " AND `faxName` = ?";

            stmt = createStatement(con, query, faxName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FromResultSet(cc, rs);
            }

            rs.close();
            stmt.close();

            return cc;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Updates a CarbonCopies row to marked as faxed
     * @param ID int id of a single row
     * @return true is successful, false if failed
     */
    public boolean SetFaxedByID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            Statement stmt = con.createStatement();

            String query = "UPDATE " + table
                    + " SET `faxed` = " + true
                    + " WHERE `id` = " + ID;

            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    /**
     * Updates all CarbonCopies for an idOrders as faxed 
     * @param ID int idOrders
     * @return true is successful, false if failed
     */
    public boolean SetFaxedByOrderID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            Statement stmt = con.createStatement();

            String query = "UPDATE " + table
                    + " SET `faxed` = " + true
                    + " WHERE `idOrders` = " + ID;

            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return false;
        }
    }
    
    /**
     * Gets all CarbonCopies for an idOrders
     * @param orderID int idOrders
     * @return ArrayList<CarbonCopies>
     */
    public ArrayList<CarbonCopies> GetAllByOrderID(int orderID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        try{
            ArrayList<CarbonCopies> list = new ArrayList<>();
            CarbonCopies cc= null;
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `idOrders` = " + orderID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                cc = new CarbonCopies();
                cc = FromResultSet(cc, rs);
                list.add(cc);
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Deletes all CarbonCopies rows for a particular Order
     * @param idOrders int idOrders
     * @return true is successful, false if failed
     */
    public Boolean DeleteByOrder(int idOrders) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        try{
            Statement stmt = con.createStatement();
            String query = "DELETE FROM " + table + " "
                    + "WHERE `idOrders` = " + idOrders;
            
            int result = stmt.executeUpdate(query);
            
            stmt.close();
            
            return true;
        }catch (Exception ex) {
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
    private CarbonCopies FromResultSet(CarbonCopies obj, ResultSet rs) throws SQLException {
        obj.setId(rs.getInt("id"));
        obj.setIdOrders(rs.getInt("idOrders"));
        obj.setFaxName(rs.getString("faxName"));
        obj.setFaxNumber(rs.getString("faxNumber"));
        obj.setDoctor(rs.getInt("doctor"));
        obj.setClient(rs.getInt("client"));
        obj.setFaxMemo(rs.getString("faxMemo"));
        obj.setCreated(rs.getTimestamp("created"));
        obj.setFaxed(rs.getBoolean("faxed"));
        obj.setIsCascading(rs.getBoolean("isCascading"));

        return obj;
    }

    //--------------------------------------------------------------------------
    private PreparedStatement SetStatement(CarbonCopies obj, PreparedStatement pStmt) throws SQLException {
        SQLUtil.SafeSetInteger(pStmt, 1, obj.getIdOrders());
        SQLUtil.SafeSetString(pStmt, 2, obj.getFaxName());
        SQLUtil.SafeSetString(pStmt, 3, obj.getFaxNumber());
        SQLUtil.SafeSetInteger(pStmt, 4, obj.getDoctor());
        SQLUtil.SafeSetInteger(pStmt, 5, obj.getClient());
        SQLUtil.SafeSetString(pStmt, 6, obj.getFaxMemo());
        SQLUtil.SafeSetTimeStamp(pStmt, 7, obj.getCreated());
        SQLUtil.SafeSetBoolean(pStmt, 8, obj.getFaxed());
        SQLUtil.SafeSetBoolean(pStmt, 9, obj.getIsCascading());
        
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
