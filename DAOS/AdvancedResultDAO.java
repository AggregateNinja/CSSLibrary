package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IAdvancedResultDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.AdvancedResults;
import DOS.Results;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @date: Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project: CSSLibrary
 * @package: DAOS
 * @file name: AdvancedResultDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class AdvancedResultDAO implements IAdvancedResultDAO, DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`advancedResults`";
    /**
     * All fields except ID
     */
    private final ArrayList<String> fields = new ArrayList<String>();

    public AdvancedResultDAO() {
        fields.add("idAdvancedOrder");
        fields.add("testId");
        fields.add("panelId");
        fields.add("resultNo");
        fields.add("resultText");
        fields.add("resultRemark");
        fields.add("resultChoice");
        fields.add("created");
        fields.add("reportedBy");
        fields.add("dateReported");
        fields.add("noCharge");
        fields.add("hl7Transmitted");

    }

    @Override
    public boolean InsertAdvancedResults(AdvancedResults advResult) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);


            SetStatementFromAdvancedResults(advResult, pStmt);

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

    @Override
    public boolean UpdateAdvancedResults(AdvancedResults advResult) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {

            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idAdvancedResults` = " + advResult.getIdAdvancedResults();

            PreparedStatement pStmt = con.prepareStatement(stmt);

            SetStatementFromAdvancedResults(advResult, pStmt);

            pStmt.executeUpdate();

            pStmt.close();

            return true;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    @Override
    public AdvancedResults GetAdvancedResults(int IdAdvancedResult) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            AdvancedResults advResult = new AdvancedResults();
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `advResult` = " + IdAdvancedResult;

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                SetAdvancedResultsFromResultSet(advResult, rs);
            }

            rs.close();
            stmt.close();

            return advResult;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }

    @Override
    public List<AdvancedResults> GetAdvancedResultsFromAdvOrderId(int idAdvancedOrder) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<AdvancedResults> advres = new ArrayList<>();
            AdvancedResults advResult;
            Statement stmt = con.createStatement();

            String query = "SELECT * FROM " + table
                    + "WHERE `idAdvancedOrder` = " + idAdvancedOrder;

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                advResult = new AdvancedResults();
                SetAdvancedResultsFromResultSet(advResult, rs);
                advres.add(advResult);
            }

            rs.close();
            stmt.close();

            return advres;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }
    }
    
    /**
     * Deletes the advanced results rows for the provided advanced order.
     *  When an existing advanced order is updated, the tests for it are
     *  wiped out and the current tests are inserted.
     * @param advancedOrderId 
     */
    public boolean DeleteAdvancedResultsForAdvancedOrderId(Integer advancedOrderId)
    {
        if (advancedOrderId == null) return false;
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            
            String query = "DELETE FROM " + table
                    + "WHERE `idAdvancedOrder` = " + advancedOrderId;
            PreparedStatement pStmt = con.prepareStatement(query);
            pStmt.executeUpdate();
            
            pStmt.close();

            return true;
        } catch (Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return false;
        }        
    }
    
    public boolean PurgeResultByAdvancedResultID(int RESULTID) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            Statement stmt = null;

            String query = "DELETE FROM " + table + " WHERE "
                    + "`idAdvancedResults` = " + RESULTID;

            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);

            return (delete == 1);

        } catch (SQLException ex) {
            //TODO:  Add Exception Handling
            System.out.println("AdvancedResultDAO::PurgeResultByAdvancedResultID - " + ex.getMessage());
            return false;
        }
    }
    
    /**
     *  Gets all rows for the order that have the provided results' test as
     *   a parent (panelId).
     * 
     *  NOTE: ONLY retrieves the direct children of the panel, does not
     *   recursively return their children.
     *  Returns empty array if no children found, null on error
     * @param idAdvancedResults
     * @return 
     */
    public ArrayList<AdvancedResults> GetSubtestAdvancedResults(int idAdvancedResults)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        AdvancedResults advancedResult = null;
        try
        {
            advancedResult = GetAdvancedResults(idAdvancedResults);
        }
        catch (SQLException ex)
        {
            System.out.println("AdvancedResultDAO::GetSubtestAdvancedResults: " +
                    "Unable to get subtests for idAdvancedResults:" + idAdvancedResults);
            return null;
        }        
        if (advancedResult == null) return null;
        
        ArrayList<AdvancedResults> advancedResults = new ArrayList<>();
        
        // Get all the "child" tests for the provided result
        String sql = "SELECT * FROM " + table + 
                " WHERE idAdvancedOrder = " + advancedResult.getIdAdvancedOrder()
                + " AND panelId = " + advancedResult.getTestId();
        
        try
        {
            Statement stmt = con.createStatement();         
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                advancedResult = new AdvancedResults();
                SetAdvancedResultsFromResultSet(advancedResult, rs);
                advancedResults.add(advancedResult);
            }
            rs.close();
            stmt.close();
            
        }
        catch (SQLException ex)
        {
            System.out.println("ResultDAO::GetSubtestResults: Could not retrieve " +
                    "subtests for idresults: " + idAdvancedResults);
            return null;
        }

        return advancedResults;
    }    
    

    private AdvancedResults SetAdvancedResultsFromResultSet(AdvancedResults obj, ResultSet rs) throws SQLException {
        obj.setIdAdvancedResults(rs.getInt("idAdvancedResults"));
        obj.setIdAdvancedOrder(rs.getInt("idAdvancedOrder"));
        obj.setTestId(rs.getInt("testId"));
        obj.setPanelId(rs.getInt("panelId"));
        obj.setResultNo(rs.getDouble("resultNo"));
        obj.setResultText(rs.getString("resultText"));
        obj.setResultRemark(rs.getInt("resultRemark"));
        obj.setResultChoice(rs.getInt("resultChoice"));
        obj.setCreated(rs.getDate("created"));
        obj.setReportedBy(rs.getInt("reportedBy"));
        obj.setDateReported(rs.getDate("dateReported"));
        obj.setNoCharge(rs.getBoolean("noCharge"));
        obj.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));

        return obj;
    }

    private PreparedStatement SetStatementFromAdvancedResults(AdvancedResults obj, PreparedStatement pStmt) throws SQLException {
        pStmt.setInt(1, obj.getIdAdvancedOrder());
        pStmt.setInt(2, obj.getTestId());
        SQLUtil.SafeSetInteger(pStmt, 3, obj.getPanelId());
        SQLUtil.SafeSetDouble(pStmt, 4, obj.getResultNo());
        SQLUtil.SafeSetString(pStmt, 5, obj.getResultText());
        SQLUtil.SafeSetInteger(pStmt, 6, obj.getResultRemark());
        SQLUtil.SafeSetInteger(pStmt, 7, obj.getResultChoice());
        pStmt.setDate(8, Convert.ToSQLDate(obj.getCreated()));
        SQLUtil.SafeSetInteger(pStmt, 9, obj.getReportedBy());
        SQLUtil.SafeSetTimeStamp(pStmt, 10, obj.getDateReported());
        SQLUtil.SafeSetBoolean(pStmt, 11, obj.getNoCharge());
        SQLUtil.SafeSetBoolean(pStmt, 12, obj.getHl7Transmitted());
        return pStmt;
    }

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

    @Override
    public Boolean Insert(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
