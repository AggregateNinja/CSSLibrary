/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 11, 2014
 */

package BL;

import DAOS.DepartmentDAO;
import DAOS.InstOrdDAO;
import DAOS.OrderDAO;
import DAOS.PreferencesDAO;
import DAOS.TranslationalQueueDAO;
import DOS.Departments;
import DOS.IDOS.BaseInstOrd;
import DOS.Instruments;
import DOS.Orders;
import DOS.TranslationalQueue;
import Database.CheckDBConnection;
import Utility.SQLUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @date:   Aug 11, 2014  4:14:13 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: InstrumentInterfaceBL.java  (UTF-8)
 *
 * @Description: 
 *
 */

public class InstrumentInterfaceBL {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public InstrumentInterfaceBL(){
        
    }
    
    public int DeleteRowsWithNoAccession(String table){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try {
            String query = "DELETE " + table + " "
                    + "FROM " + table + " "
                    + "LEFT JOIN orders  ON " + table + ".accession = orders.accession "
                    + "WHERE orders.accession IS NULL ";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            int num = pStmt.executeUpdate();
            
            pStmt.close();
            
            return num;
        }catch(Exception ex){
            System.out.println("InstrumentInterfaceBL::DeleteRowsWithNoAccession - " + ex.toString());
            return -1;
        }
    }
    
    /**
     * Used for the Thermo Fisher Endura Interface to replace 'N/F' results
     * with 0's
     * @param table
     * @return 
     */
    public int SetNFtoZero(String table){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            String query = "UPDATE `" + table + "` SET "
                    + "`result` = 0 "
                    + "WHERE `result` = 'N/F'";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            int count = pStmt.executeUpdate();
            
            pStmt.close();
            
            return count;
        }catch(Exception ex){
            System.out.println("InstrumentInterfaceBL::SetNFtoZero - " + ex.toString());
            return -1;
        }
    }
    
    public int CompleteForTranslational(ArrayList<String> accessions){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try{
            java.util.Date date = new java.util.Date();
            int completed = 0;
            
            for(String acc : accessions){
                //All exspected analytes for this accession
                ArrayList<String> expected = GetAnalytesForTranslationalAccession(acc);
                
                //Get all completed analytes for the accession from the Autogenomic
                //Instrument tables.
                ArrayList<String> posted = GetAnalytesFromAutoGenomics(acc);
                
                //First check to see if there is enough results to be complete
                if(posted.size() < expected.size()){
                    //Not Complete
                }else if(TranslationalCompare(posted, expected)){
                    //It is complete!!!
                    //Make ready to trasmit
                    TranslationalQueue tq = new TranslationalQueue();
                    TranslationalQueueDAO tqdao = new TranslationalQueueDAO();
                    OrderDAO odao = new OrderDAO();
                    Orders order = odao.GetOrder(acc);
                    tq.setAccession(acc);
                    tq.setIdorders(order.getIdOrders());
                    tq.setCreatedDate(date);
                    tq.setTransmitted(false);
                    
                    tqdao.Insert(tq);
                    completed++;
                }  
            }
            
            
            return completed;
        }catch(Exception ex){
            System.out.println("InstrumentInterfaceBL::CompleteForTranslational - " + ex.toString());
            return -1;
        }
    }
    
    public ResultSet GetResultsFromAutoGenomics(String acc){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            String query;
            PreferencesDAO pdao = new PreferencesDAO();
            String insts = pdao.getString("AutoGenomicsInstruments");
            String delims = "[,]";
            String[] tokens = insts.split(delims);
            ArrayList<Integer> InstList = new ArrayList<>();
            for(String str : tokens){
                InstList.add(Integer.parseInt(str));
            }
            
            switch (InstList.size()){
                case 1:
                    query = "SELECT DISTINCT "
                            + "o.`accession` AS 'accession', "
                            + "t.`number` AS 'number', "
                            + "t.`name` AS 'testName', "
                            //+ "i.`name` AS 'analyte', "
                            + "x.`transformedOut` AS 'analyte', "
                            + "i.`comment` AS 'result' "
                            + "FROM `instRes_" + InstList.get(0) + "` AS i "
                            + "LEFT JOIN `orders` o "
                            + "    ON i.`accession` = o.`accession` "
                            + "LEFT JOIN `results` r "
                            + "    ON o.`idorders` = r.`orderId` "
                            + "LEFT JOIN `testXref` x "
                            + "    ON i.`name` = x.`transformedOut` "
                            + "LEFT JOIN `tests` t "
                            + "    ON x.`testNumber` = t.`number` "
                            + "WHERE i.`accession` = ?";
                    break;
                case 2:
                    query = "SELECT DISTINCT "
                            + "o.`accession` AS 'accession', "
                            + "t.`number` AS 'number', "
                            + "t.`name` AS 'testName', "
                            //+ "i.`name` AS 'analyte', "
                            + "x.`transformedOut` AS 'analyte', "
                            + "i.`comment` AS 'result' "
                            + "FROM (SELECT * FROM `instRes_" + InstList.get(0) + "` "
                            + "    UNION "
                            + "      SELECT * FROM `instRes_" + InstList.get(1) + "` "
                            + ") AS i "
                            + "LEFT JOIN `orders` o "
                            + "    ON i.`accession` = o.`accession` "
                            + "LEFT JOIN `results` r "
                            + "    ON o.`idorders` = r.`orderId` "
                            + "LEFT JOIN `testXref` x "
                            + "    ON i.`name` = x.`transformedOut` "
                            + "LEFT JOIN `tests` t "
                            + "    ON x.`testNumber` = t.`number` "
                            + "WHERE i.`accession` = ?";
                    break;
                default:
                    throw new Exception("Unable to find AutoGenomics Instrument(s).");
            }
            
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, acc);
            
            ResultSet rs = pStmt.executeQuery();
            
            return rs;
        }catch(Exception ex){
            System.out.println("InstrumentInterfaceBL::GetResultsFromAutoGenomics - "+ex.toString());
            return null;
        }
    }
    
    public ArrayList<String> GetAnalytesFromAutoGenomics(String acc){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            String query;
            ArrayList<String> analytes = new ArrayList<>();
            PreferencesDAO pdao = new PreferencesDAO();
            String insts = pdao.getString("AutoGenomicsInstruments");
            String delims = "[,]";
            String[] tokens = insts.split(delims);
            ArrayList<Integer> InstList = new ArrayList<>();
            for(String str : tokens){
                InstList.add(Integer.parseInt(str));
            }
            
            switch (InstList.size()){
                case 1:
                    query = "SELECT DISTINCT "
                            + "i.`name` AS 'analyte' "
                            + "FROM `instRes_" + InstList.get(0) + "` AS i "
                            + "LEFT JOIN `orders` o "
                            + "    ON i.`accession` = o.`accession` "
                            + "LEFT JOIN `results` r "
                            + "    ON o.`idorders` = r.`orderId` "
                            + "LEFT JOIN `testXref` x "
                            + "    ON i.`name` = x.`transformedOut` "
                            + "LEFT JOIN `tests` t "
                            + "    ON x.`testNumber` = t.`number` "
                            + "WHERE i.`accession` = ? "
                            + "AND i.posted = " + true;
                    break;
                case 2:
                    query = "SELECT DISTINCT "
                            + "i.`name` AS 'analyte' "
                            + "FROM (SELECT * FROM `instRes_" + InstList.get(0) + "` "
                            + "    UNION "
                            + "      SELECT * FROM `instRes_" + InstList.get(1) + "` "
                            + ") AS i "
                            + "LEFT JOIN `orders` o "
                            + "    ON i.`accession` = o.`accession` "
                            + "LEFT JOIN `results` r "
                            + "    ON o.`idorders` = r.`orderId` "
                            + "LEFT JOIN `testXref` x "
                            + "    ON i.`name` = x.`transformedOut` "
                            + "LEFT JOIN `tests` t "
                            + "    ON x.`testNumber` = t.`number` "
                            + "WHERE i.`accession` = ?"
                            + "AND i.posted = " + true;
                    break;
                default:
                    throw new Exception("Unable to find AutoGenomics Instrument(s).");
            }
            
            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetString(pStmt, 1, acc);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next()){
                analytes.add(rs.getString("analyte"));
            }
            
            rs.close();
            pStmt.close();
            
            return analytes;
        }catch(Exception ex){
            System.out.println("InstrumentInterfaceBL::GetResultsFromAutoGenomics - "+ex.toString());
            return null;
        }
    }
    
    public ArrayList<String> GetAnalytesForTranslationalAccession(String acc) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            PreferencesDAO pdao = new PreferencesDAO();
            Integer xref = pdao.getInteger("TranslationalSoftwareInterface");
            Integer dept = pdao.getInteger("TranslationalSoftwareDepartment");
            DepartmentDAO ddao = new DepartmentDAO();
            Departments dpt = ddao.GetDepartmentByID(dept);
            
            ArrayList<String> analytes = new ArrayList<>();

            String query = "SELECT "
                    + "x.`transformedOut` AS 'analyte' "
                    + "FROM `results` r "
                    + "LEFT JOIN `orders` o "
                    + "    ON r.`orderid` = o.`idorders` "
                    + "LEFT JOIN `tests` t "
                    + "	ON r.`testid` = t.`idtests` "
                    + "LEFT JOIN `testXref` x "
                    + "    ON t.`number` = x.`testNumber` "
                    + "WHERE o.`accession` = ? "
                    + "AND t.`department` = ? "
                    + "AND t.`testType` != 0 "
                    + "AND x.`name` = ?";
            
            PreparedStatement pStmt = con.prepareStatement(query);
            
            SQLUtil.SafeSetString(pStmt, 1, acc);
            SQLUtil.SafeSetInteger(pStmt, 2, dpt.getIdDepartment());
            SQLUtil.SafeSetInteger(pStmt, 3, xref);
            
            ResultSet rs = pStmt.executeQuery();
            
            while(rs.next()){
                analytes.add(rs.getString("analyte"));
            }
            
            rs.close();
            pStmt.close();
            
            return analytes;
            
        } catch (Exception ex) {
            System.out.println("InstrumentInterfaceBL::GetAnalytesForTranslationalAccession - "
                    + ex.toString());
            return null;
        }
    }
    
    
    private boolean TranslationalCompare(ArrayList<String> reported, ArrayList<String> expected){
        
        ArrayList<String> inBoth = new ArrayList<>();
        
        for(String str : expected){
            if(reported.contains(str))
                inBoth.add(str);
        }
        
        return (inBoth.size() == expected.size() ? true : false);
    }
    
    public ArrayList<BaseInstOrd> GetUnsentOrders(Instruments inst)
    {
        if (inst == null) return null;
        String ordTable = inst.getOrdTable();
        if (ordTable == null || ordTable.isEmpty())
        {
            String msg = "Attempting to check the instOrd table for pending " +
                    "results, but the instrument field is not set!";
            System.out.println(msg);
            return null;
        }
        
        try
        {
            if (con.isClosed())
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return null;
        }
        
        ArrayList<BaseInstOrd> unsentRows;
        try
        {
            InstOrdDAO iodao = new InstOrdDAO();
            
            // This gets the rows in Accession order
            unsentRows = iodao.GetAllUnsent(inst.getOrdTable());
            if (unsentRows == null) throw new SQLException();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to retrieve order buffer rows for instrument " + inst.getIdInst());
            return null;
        }

        return unsentRows;
    }
    
    /**
     * Returns whether there are unsent orders in the instOrd buffer for
     *  the supplied instrument.
     * @param inst
     * @return NULL on error
     */
    public Boolean HasPendingOrders(Instruments inst)
    {
        if (inst == null) return null;
        String ordTable = inst.getOrdTable();
        if (ordTable == null || ordTable.isEmpty())
        {
            String msg = "Attempting to check the instOrd table for pending " +
                    "results, but the instrument field is not set!";
            System.out.println(msg);
            return null;
        }
        
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
            String query = "SELECT COUNT(*) FROM " + inst.getOrdTable() + " WHERE SENT = 0";
            PreparedStatement pStmt = con.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();
            Integer count = null;
            if (rs != null && rs.next())
            {
                count = rs.getInt(1);
            }
            
            return (count != null && count > 0);
        }
        catch (SQLException ex)
        {
            String msg = "Error attempting to retrieve pending orders for instrument " + inst.getInstName();
            return null;
        }        
    }
}
