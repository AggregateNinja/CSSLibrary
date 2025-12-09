
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DAOS.PanelDAO;
import DAOS.SysLogDAO;
import DOS.Panels;
import DOS.ReportHeader;
import java.sql.*;
import DOS.Tests;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Utility.SQLUtil.createStatement;

/**
 * @date:   Mar 13, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: TestDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



public class TestDAO implements DAOInterface, IStructureCheckable
{
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`tests`";
    
    public enum ActiveStatus
    {
        ACTIVE_ONLY,
        INACTIVE_ONLY,
        ANY
    }
    
    public enum OrderableStatus
    {
        ORDERABLE_ONLY,
        UNORDERABLE_ONLY,
        ANY
    }
    
    public enum OrderBy
    {
        ABBR,
        NUMBER,
        NAME
    }
    
    /**
     * All fields except idTests
     */
    private final ArrayList<String> fields = new ArrayList<String>();
    
    public TestDAO()
    {
        fields.add("number");
        fields.add("name");
        fields.add("abbr");
        fields.add("testType");
        fields.add("resultType");
        fields.add("lowNormal");
        fields.add("highNormal");
        fields.add("alertLow");
        fields.add("alertHigh");
        fields.add("criticalLow");
        fields.add("criticalHigh");
        fields.add("printNormals");
        fields.add("units");
        fields.add("remark");
        fields.add("department");
        fields.add("instrument");
        fields.add("onlineCode1");
        fields.add("onlineCode2");
        fields.add("created");
        fields.add("relatedDrug");
        fields.add("decimalPositions");
        fields.add("printOrder");
        fields.add("reportHeaderId");
        fields.add("specimenType");
        fields.add("loinc");
        fields.add("billingOnly");
        fields.add("labelPrint");
        fields.add("tubeTypeId");
        fields.add("headerPrint");
        fields.add("active");
        fields.add("deactivatedDate");
        fields.add("testComment");
        fields.add("extraNormals");
        fields.add("AOE");
        fields.add("cycles");
        fields.add("stat");
        fields.add("header");
        fields.add("maxReportable");
        fields.add("minReportable");
        fields.add("useMaximums");
        fields.add("maxLowResult");
        fields.add("maxHighResult");
        fields.add("maxLowNumeric");
        fields.add("maxHighNumeric");
        fields.add("printedName");
        fields.add("printOnReport");
        fields.add("noRounding");
        fields.add("lowRemark");
        fields.add("lowShowNumeric");
        fields.add("highRemark");
        fields.add("highShowNumeric");
        fields.add("alertLowRemark");
        fields.add("alertLowShowNumeric");
        fields.add("alertHighRemark");
        fields.add("alertHighShowNumeric");
        fields.add("critLowRemark");
        fields.add("critLowShowNumeric");
        fields.add("critHighRemark");
        fields.add("critHighShowNumeric");
        fields.add("normalRemark");
        fields.add("normalShowNumeric");
        fields.add("isOrderable");
        fields.add("placeOfServiceId");
        fields.add("cost");
    }
    
    private void CheckDBConnection(){
        dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        con = dbs.getConnection(true);
    }
    
    
    public Integer InsertTestGetNewID(Tests test)
    {
        try
        {
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt,PreparedStatement.RETURN_GENERATED_KEYS);            
            //String stmt = GenerateInsertStatement(fields);
            
            //PreparedStatement pStmt = con.prepareStatement(stmt);
            int i=0;
            pStmt.setInt(++i,test.getNumber());
            pStmt.setString(++i,test.getName());
            pStmt.setString(++i,test.getAbbr());
            pStmt.setInt(++i,test.getTestType());
            pStmt.setString(++i,test.getResultType());
            pStmt.setDouble(++i,test.getLowNormal());
            pStmt.setDouble(++i,test.getHighNormal());
            pStmt.setDouble(++i,test.getAlertLow());
            pStmt.setDouble(++i,test.getAlertHigh());
            pStmt.setDouble(++i,test.getCriticalLow());
            pStmt.setDouble(++i,test.getCriticalHigh());
            pStmt.setString(++i,test.getPrintNormals());
            pStmt.setString(++i,test.getUnits());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRemark());
            pStmt.setInt(++i,test.getDepartment());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getInstrument());
            pStmt.setString(++i,test.getOnlineCode1());
            pStmt.setString(++i,test.getOnlineCode2());
            pStmt.setDate(++i,Convert.ToSQLDate(test.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRelatedDrug());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getDecimalPositions());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, test.getPrintOrder());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getReportHeaderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, ++i, test.getLoinc());
            pStmt.setBoolean(++i, test.getBillingOnly());
            pStmt.setBoolean(++i, test.getLabelPrint());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getTubeTypeId());
            pStmt.setBoolean(++i, test.getHeaderPrint());
            pStmt.setBoolean(++i, test.getActive());
            pStmt.setDate(++i, Convert.ToSQLDate(test.getCreated()));
            pStmt.setBytes(++i, test.getTestComment());
            pStmt.setBoolean(++i, test.getExtraNormals());
            pStmt.setBoolean(++i, test.getAOE());
            pStmt.setInt(++i, test.getCycles());
            pStmt.setBoolean(++i, test.getStat());
            pStmt.setBoolean(++i, test.getHeader());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxReportable());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMinReportable());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getUseMaximums());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxLowResult());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxHighResult());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxLowNumeric());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxHighNumeric());
            SQLUtil.SafeSetString(pStmt, ++i, test.getPrintedName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getPrintOnReport());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNoRounding());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getNormalRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNormalShowNumeric());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.isOrderable());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getPlaceOfServiceId());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getCost());
            
            pStmt.executeUpdate();
            ResultSet rs = pStmt.getGeneratedKeys();
            Integer key = -1;
            if (rs != null && rs.next())
            {
                key = rs.getInt(1);
            }
            pStmt.close();
            
            return key;
        }catch(Exception ex)
        {
            System.out.println(test.getNumber() + ":" + test.getSubtest() + " " + ex.toString());
            return null;
        }
    }
    
    
    
    public boolean InsertTest(Tests test)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            String stmt = GenerateInsertStatement(fields);
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            int i=0;
            pStmt.setInt(++i,test.getNumber());
            pStmt.setString(++i,test.getName());
            pStmt.setString(++i,test.getAbbr());
            pStmt.setInt(++i,test.getTestType());
            pStmt.setString(++i,test.getResultType());
            pStmt.setDouble(++i,test.getLowNormal());
            pStmt.setDouble(++i,test.getHighNormal());
            pStmt.setDouble(++i,test.getAlertLow());
            pStmt.setDouble(++i,test.getAlertHigh());
            pStmt.setDouble(++i,test.getCriticalLow());
            pStmt.setDouble(++i,test.getCriticalHigh());
            pStmt.setString(++i,test.getPrintNormals());
            pStmt.setString(++i,test.getUnits());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRemark());
            pStmt.setInt(++i,test.getDepartment());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getInstrument());
            pStmt.setString(++i,test.getOnlineCode1());
            pStmt.setString(++i,test.getOnlineCode2());
            pStmt.setDate(++i,Convert.ToSQLDate(test.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRelatedDrug());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getDecimalPositions());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, test.getPrintOrder());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getReportHeaderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, ++i, test.getLoinc());
            pStmt.setBoolean(++i, test.getBillingOnly());
            pStmt.setBoolean(++i, test.getLabelPrint());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getTubeTypeId());
            pStmt.setBoolean(++i, test.getHeaderPrint());
            pStmt.setBoolean(++i, test.getActive());
            pStmt.setDate(++i, Convert.ToSQLDate(test.getCreated()));
            pStmt.setBytes(++i, test.getTestComment());
            pStmt.setBoolean(++i, test.getExtraNormals());
            pStmt.setBoolean(++i, test.getAOE());
            pStmt.setInt(++i, test.getCycles());
            pStmt.setBoolean(++i, test.getStat());
            pStmt.setBoolean(++i, test.getHeader());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxReportable());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMinReportable());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getUseMaximums());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxLowResult());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxHighResult());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxLowNumeric());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxHighNumeric());
            SQLUtil.SafeSetString(pStmt, ++i, test.getPrintedName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getPrintOnReport());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNoRounding());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getNormalRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNormalShowNumeric());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.isOrderable());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getPlaceOfServiceId());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getCost());
            
            String temp = pStmt.toString();
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            System.out.println(test.getNumber() + ":" + test.getSubtest() + " " + ex.toString());
            return false;
        }
    }
    
    public boolean UpdateTest(Tests test)
    {    
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
        
        try
        {
            String stmt = GenerateUpdateStatement(fields)
                    + " WHERE `idtests` = " + test.getIdtests();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            int i=0;
            pStmt.setInt(++i,test.getNumber());
            pStmt.setString(++i,test.getName());
            pStmt.setString(++i,test.getAbbr());
            pStmt.setInt(++i,test.getTestType());
            pStmt.setString(++i,test.getResultType());
            pStmt.setDouble(++i,test.getLowNormal());
            pStmt.setDouble(++i,test.getHighNormal());
            pStmt.setDouble(++i,test.getAlertLow());
            pStmt.setDouble(++i,test.getAlertHigh());
            pStmt.setDouble(++i,test.getCriticalLow());
            pStmt.setDouble(++i,test.getCriticalHigh());
            pStmt.setString(++i,test.getPrintNormals());
            pStmt.setString(++i,test.getUnits());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRemark());
            pStmt.setInt(++i,test.getDepartment());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getInstrument());
            pStmt.setString(++i,test.getOnlineCode1());
            pStmt.setString(++i,test.getOnlineCode2());
            pStmt.setDate(++i,Convert.ToSQLDate(test.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getRelatedDrug());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getDecimalPositions());
            SQLUtil.SafeSetRangeInteger(pStmt, ++i, test.getPrintOrder());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getReportHeaderId());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getSpecimenType());
            SQLUtil.SafeSetString(pStmt, ++i, test.getLoinc());
            pStmt.setBoolean(++i, test.getBillingOnly());
            pStmt.setBoolean(++i, test.getLabelPrint());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getTubeTypeId());
            pStmt.setBoolean(++i, test.getHeaderPrint());
            pStmt.setBoolean(++i, test.getActive());
            pStmt.setDate(++i, Convert.ToSQLDate(test.getCreated()));
            pStmt.setBytes(++i, test.getTestComment());
            pStmt.setBoolean(++i, test.getExtraNormals());
            pStmt.setBoolean(++i, test.getAOE());
            pStmt.setInt(++i, test.getCycles());
            pStmt.setBoolean(++i, test.getStat());
            pStmt.setBoolean(++i, test.getHeader());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxReportable());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMinReportable());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getUseMaximums());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxLowResult());
            SQLUtil.SafeSetString(pStmt, ++i, test.getMaxHighResult());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxLowNumeric());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getMaxHighNumeric());
            SQLUtil.SafeSetString(pStmt, ++i, test.getPrintedName());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getPrintOnReport());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNoRounding());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getAlertHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getAlertHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritLowRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritLowShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getCritHighRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getCritHighShowNumeric());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getNormalRemark());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.getNormalShowNumeric());
            SQLUtil.SafeSetBoolean(pStmt, ++i, test.isOrderable());
            SQLUtil.SafeSetInteger(pStmt, ++i, test.getPlaceOfServiceId());
            SQLUtil.SafeSetDouble(pStmt, ++i, test.getCost());
                        
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            System.out.println(test.getNumber() + ":" + test.getSubtest() + " " + ex.toString());
            return false;
        }
    }
    
    /**
     * 
     * @param num
     * @param sub Ignored.
     * @return
     * @throws SQLException
     * @deprecated Subtests no longer exist in new DB Schema.
     */
    @Deprecated
    public Tests GetTestByNumber(int num, int sub)
    {
        return GetTestByNumber(num);
    }
    
    /**
     *  Returns non-calculation test by test number.
     * 
     * @param num
     * @return 
     */
    public Tests GetTestByNumber(int num)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests test = new Tests();
            Statement stmt = con.createStatement();
            
            // Tom: this is excluding calculation tests, but the method
            //  name gives no indication of this! TODO: refactor this in 
            //  the code to be named something more descriptive
            String query = "SELECT * FROM " + table
                    + " WHERE `number` = " + num
                    + " AND `active` = " + true
                    + " AND `testType` != 2";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            
            rs.close();
            stmt.close();
            
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Returns all non-calculation tests IDs (active or inactive) by number.
     * @param num
     * @return 
     */
    public ArrayList<Integer> GetTestsIDsByNumber(int num)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Integer> ids = new ArrayList<>();
            Tests test = new Tests();
            Statement stmt = con.createStatement();
            
            // Tom: this is excluding calculation tests, but the method
            //  name gives no indication of this! TODO: refactor this in 
            //  the code to be named something more descriptive
            String query = "SELECT * FROM " + table
                    + " WHERE `number` = " + num
                    + " AND `testType` != 2";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                ids.add(rs.getInt("idtests"));
            }
            
            rs.close();
            stmt.close();
            
            return ids;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Gets the active test based on the number and orderable status. If no test
     *  is found that matches the criteria, returns [NULL].
     * @param testNumber
     * @param orderableStatus
     * @return 
     * @throws java.sql.SQLException 
     */
    public static Tests getTestByNumber(Integer testNumber, OrderableStatus orderableStatus)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (testNumber == null || testNumber <= 0)
        {
            throw new IllegalArgumentException("TestDAO::getTestByNumber"
                    + " (Integer, OrderableStatus): received a [NULL] or invalid testNumber");
        }
        
        if (orderableStatus == null)
        {
            throw new IllegalArgumentException("TestDAO::getTestByNumber"
                    + " (Integer, OrderableStatus): received a [NULL] OrderableStatus argument");
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);

        String sql = "SELECT * FROM `tests` WHERE active = b'1' AND `number` = ?";
        
        if (orderableStatus == OrderableStatus.ORDERABLE_ONLY)
        {
            sql += " AND isOrderable = b'1'";
        }
        
        if (orderableStatus == OrderableStatus.UNORDERABLE_ONLY)
        {
            sql += " AND isOrderable = b'0'";
        }
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        
        pStmt.setInt(1, testNumber);
        
        ResultSet rs = pStmt.executeQuery();
        
        Tests test = null;
        
        if (rs.next())
        {
            test = SetTestFromResultSet(test, rs);
            if (test == null || test.getIdtests() == null || test.getIdtests().equals(0))
            {
                String preparedStatementStr = "[NULL]"; 
                try
                {
                    preparedStatementStr = pStmt.toString();
                    if (preparedStatementStr == null) preparedStatementStr = "[NULL]";
                } catch (Exception ex) {}
                throw new SQLException("TestDAO::getTestByNumber"
                        + " (Integer, OrderableStatus): Could not build Tests"
                        + " object from resultset from statement:" + preparedStatementStr);
            }
        }
        rs.close();
        pStmt.close();        
        return test;
    }
    
    public Tests GetTestOrCalcByNumber(int num)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests test = new Tests();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `number` = " + num
                    + " AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            
            rs.close();
            stmt.close();
            
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests GetCalculationByNumber(int num)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests test = new Tests();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `number` = " + num
                    + " AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            
            rs.close();
            stmt.close();
            
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests GetTestByName(String name)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests test = new Tests();
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `name` = ?"
                    + " AND `active` = " + true
                    + " AND `testType` != 2";
            
            stmt = createStatement(con, query, name);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            
            rs.close();
            stmt.close();
            
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Tests> GetPanelHeadersByAccession(String accession) {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Tests> panelHeaders = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query =
                "SELECT"
                + " t.*"
                + " FROM tests t"
                + " INNER JOIN results r ON t.idtests = r.testId "
                + " INNER JOIN orders o ON r.orderId = o.idOrders "
                + " WHERE o.accession = " + accession
                + " AND t.testType = 0";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Tests newTest = new Tests();
                SetTestFromResultSet(newTest, rs);
                panelHeaders.add(newTest);
            }
            
            rs.close();
            stmt.close();
            
            return panelHeaders;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Tests> GetPanelHeadersByAccessionInstrument(String accession, String instrumentId) {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Tests> panelHeaders = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query =
                "SELECT"
                + " t.*"
                + " FROM tests t"
                + " INNER JOIN results r ON t.idtests = r.testId "
                + " INNER JOIN orders o ON r.orderId = o.idOrders "
                + " WHERE o.accession = " + accession
                + " AND t.instrument = " + instrumentId
                + " AND t.testType = 0";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Tests newTest = new Tests();
                SetTestFromResultSet(newTest, rs);
                panelHeaders.add(newTest);
            }
            
            rs.close();
            stmt.close();
            
            return panelHeaders;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ArrayList<Tests> GetSubtestsIncludingInactive(Tests test)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            ArrayList<Tests> subtests = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query =
                "SELECT"
                + " subtests.*"
                + " FROM tests panelTest"
                + " INNER JOIN panels p ON panelTest.idtests = p.idpanels"
                + " INNER JOIN tests subtests ON p.subtestId = subtests.idtests"
                //+ " WHERE subtests.testType != 2 " // Non-calculation
                + " WHERE panelTest.idtests = " + test.getIdtests()
                + " ORDER BY p.subtestOrder";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Tests newTest = new Tests();
                SetTestFromResultSet(newTest, rs);
                subtests.add(newTest);
            }
            
            rs.close();
            stmt.close();
            
            return subtests;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }        
        
    }
    
    public Tests[] GetSubtests(int TestNumber)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests header = this.GetTestByNumber(TestNumber);
            
            if(header == null || header.getIdtests() == null)
                return null;
            
            return GetSubtests(header);
            
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests[] GetSubtestsByHeaderID(Integer ID)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests header = this.GetTestByID(ID);
            
            if(header == null || header.getIdtests() == null)
                return null;
            
            return GetSubtests(header);
            
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests[] GetSubtests(Tests header)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try 
        {
            PanelDAO pDAO = new PanelDAO();
            ArrayList< Panels > panelList = (ArrayList< Panels >) pDAO.GetPanels(header.getIdtests());
            ArrayList< Tests > testList = new ArrayList<  >();
            
            Collections.sort(panelList, new Comparator<Panels>()
            {

                @Override
                public int compare(Panels t1, Panels t2)
                {
                    return t1.getSubtestOrder() - t2.getSubtestOrder();
                }
                
            });
            
            for(Panels p : panelList)
            {
                testList.add(GetTestByID(p.getSubtestId()));
            }
            
            Tests[] outtests = new Tests[testList.size()];
            outtests = testList.toArray(outtests);
            return outtests;
        }
        catch (Exception ex)
        {
            Logger.getLogger(TestDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Tests GetTestByID(int ID)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            Tests test = new Tests();
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `idtests` = " + ID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            
            rs.close();
            stmt.close();
            
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * 
     * @param testNo
     * @param subNo Ignored.
     * @return
     * @deprecated Subtests no longer exist in new DB Schema. Use {@link #GetTestID(int) }
     */
    @Deprecated
    public int GetTestID(int testNo, int subNo)
    {
        return GetTestID(testNo);
    }
    
    public int GetTestID(int testNo)
    {
        return GetTestID(testNo, true);
    }
    
    public int GetTestID(int testNo, boolean active)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        int id = 0;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idtests` FROM "
                    + table
                    + " WHERE `number` = " + testNo
                    + " AND `active` = " + active;
                    //+ " AND `testType` != 2";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                id = rs.getInt("idtests");
            }
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return -1;
        }
        
    }
    
    /**
     * 
     * @param testId
     * @return
     * @deprecated No longer needs to return an array, due to Subtests being cut from the schema. Use {@link #GetTestNumberByID(int) }
     */
    @Deprecated
    public int[] GetTestNumber(int testId)
    {
        int[] tests = new int[2];
        tests[0] = GetTestNumberByID(testId);
        tests[1] = 0;
        return tests;
    }
    
    public int GetTestNumberByID(int testId)
    {
        
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        int tests = -1;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `number` FROM "
                    + table
                    + " WHERE `idtests` = " + testId;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                tests = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            
            return tests;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return -1;
        }
        
    }
    
    public HashMap GetAllTestNames()
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        HashMap map = new HashMap();
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idtests`, `name` FROM "
                    + table + " AND `active` = " + true
                    + " AND `testType` != 2";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Object key = rs.getInt("idtests");
                Object val = rs.getString("name");
                if (!map.containsKey(key)) 
                {
                    map.put(key, val);
                }
            }
            
            rs.close();
            stmt.close();
            
            return map;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public HashMap GetAllTestNumbersAndNames() 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        HashMap map = new HashMap();
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `number`, `name` FROM "
                    + table + " WHERE `active` = " + true
                    + " AND `testType` != 2";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Object key = rs.getInt("number");
                Object val = rs.getString("name");
                if (!map.containsKey(key)) 
                {
                    map.put(key, val);
                }
            }
            
            rs.close();
            stmt.close();
            
            return map;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet GetTestResultSetByName(String tName)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT `number` AS 'Number',"
                    + " `name` AS 'Name'"
                    + " FROM " + table 
                    + " WHERE `name` LIKE ? "
                    + " AND `active` = " + true 
                    + " AND `testType` != 2 "
                    + " ORDER BY `name`";
            
            String nameParam = SQLUtil.createSearchParam(tName);
            stmt = createStatement(con, query, nameParam);
            ResultSet rs = stmt.executeQuery();
            
            return rs;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Map<Integer, Tests> GetInstrumentTestMapForOnlineCode(String onlineCode)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        Map<Integer, Tests> testMap = new HashMap<Integer, Tests>();
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + " WHERE `onlineCode1` = ?";
            
            stmt = createStatement(con, query, onlineCode);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next())
            {
                Tests test = new Tests();
                SetTestFromResultSet(test, rs);
                testMap.put(test.getInstrument(), test);
            }
            
            rs.close();
            stmt.close();
            
            return testMap;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return testMap;
        }
    }
    
    public Tests GetTestByOnlineCodeAndInstrument(int instrument, String onlineCode)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        Tests test = new Tests();
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT *"
                    + " FROM tests t"
                    + " WHERE t.instrument = " + instrument
                    + " AND t.onlineCode1 = ?"
                    + " AND t.active = 1";
            
            stmt = createStatement(con, query, onlineCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
            {
                SetTestFromResultSet(test, rs);
            }
            rs.close();
            stmt.close();
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests GetTestByTestNumberOrderId(int testNumber, int orderId)
    {
        
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        Tests test = null;
        try
        {
            Statement stmt = con.createStatement();
            String query = "SELECT `idtests`"
                    + " FROM tests t"
                    + " INNER JOIN results r on t.idtests = r.testId"
                    + " WHERE r.orderId = " + orderId
                    + " AND t.number = " + testNumber;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if (rs.next())
            {
                test = GetTestByID(rs.getInt("idtests"));
            }
            rs.close();
            stmt.close();
            return test;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }        
        
    }
    
    public LinkedList<Tests> GetAllTestNames(String Containing, boolean filterSubtests)
    {
        
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        LinkedList<Tests> found = new LinkedList<Tests>();
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idtests`, `number`, `name` FROM "
                    + table + " WHERE INSTR(`name`, '" + Containing + "')"
                    + (filterSubtests==true?" AND subtest = 0":"") + ""
                    + " AND `active` = " + true 
                    + " AND `testType` != 2; ";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Tests t = new Tests();
                t.setIdtests(rs.getInt("idtests"));
                t.setName(rs.getString("name"));
                t.setNumber(rs.getInt("number"));
                found.add(t);
            }
            
            rs.close();
            stmt.close();

            return found;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return found;
        }
    }
    
    /**
     * Tests to see if a Test Number exists in the database. If isActive is null, it will check for all active and inactive tests.
     * @param Number int The test number to lookup.
     * @param isActive Boolean If true, only look for active tests. If false, only look for inactive tests. If null, look for all.
     * @return If true, the test exists. If false, the test is not in the Database. If null, an error occurred.
     */
    public Boolean TestExists(int Number, Boolean isActive)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        int tests = -1;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT COUNT(*) FROM "
                    + table
                    + " WHERE `number` = " + Number
                    + (isActive != null?" AND `active` = " + isActive : "");
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                tests = rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
            
            return tests > 0;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet SearchByTestName(String name, boolean CaseSensitive, String... OtherColumns)
    {
       try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
       
       String addedCols = "";
       for(String col : OtherColumns)
       {
           if(col != null && col.isEmpty() == false)
           {
               addedCols += ", " + col;
           }
       }
       
        try
        { 
            PreparedStatement stmt = null; // con.createStatement();
            
            String query = "SELECT `number` AS 'Number', `name` AS 'Name' "
                    + addedCols
                    + " FROM " + table + " ";
            if(CaseSensitive)
            {
                query += "WHERE `name` LIKE ? ";
            }
            else
            {
                query += "WHERE LOWER(`name`) LIKE LOWER(?) ";
            }
            query += "AND `active` = 1";
            ResultSet rs;

            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();
            
            return rs;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet SearchByTestName(String name) throws SQLException
    {
        return SearchByTestName(name, false);
    }
    
    public List<Tests> SearchByTestNameInstrumentAndSpecimen(String name, Integer instrument, Integer specimenType)
    {
       try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
       
        try
        { 
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE LOWER(`name`) LIKE LOWER(?) AND `active` = 1 ";
            if (instrument != null && instrument != 0)
            {
                query += "AND `instrument` = " + instrument + " ";
            }
            if (specimenType != null && specimenType != 0)
            {
                query += "AND `specimenType` = " + specimenType;
            }
            ResultSet rs;
            
            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);

            rs = stmt.executeQuery();
            
            List<Tests> testList = new ArrayList<Tests>();
            while (rs.next())
            {
                Tests test = new Tests();
                SetTestFromResultSet(test, rs);
                testList.add(test);
            }
            rs.close();
            stmt.close();
            return testList;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public List<Tests> SearchByTestNameAndResultType(String name, String resultType)
    {
       try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
       
        try
        { 
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT * FROM " + table + " "
                    + "WHERE LOWER(`name`) LIKE LOWER(?) AND `active` = 1 ";
            if (resultType != null && !resultType.trim().isEmpty())
            {
                query += "AND REPLACE(`resultType`, ' ', '') = REPLACE('" + resultType + "', ' ', '') ";
            }
            ResultSet rs;
            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);

            rs = stmt.executeQuery();
            
            List<Tests> testList = new ArrayList<Tests>();
            while (rs.next())
            {
                Tests test = new Tests();
                SetTestFromResultSet(test, rs);
                testList.add(test);
            }
            rs.close();
            stmt.close();
            return testList;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet SearchTestAndDeptByTestName(String name, boolean CaseSensitive, boolean orderableOnly)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        { 
            PreparedStatement stmt = null; //con.createStatement();
            
            String query = "SELECT "
                    + "t.`idTests` AS `idTests`, "
                    + "t.`number` AS 'Number', " 
                    + "t.`name` AS 'Name', " 
                    + "t.`testType` AS 'TestType', "
                    + "d.`deptName` AS 'Department', " 
                    + "d.`promptPOC` AS 'PainManagement', "
                    + "st.`name` AS 'SpecimenType' "
                    + "FROM " + table + " t "
                    + "LEFT OUTER JOIN departments d "
                    + "ON d.`idDepartment` = t.`department` "
                    + "LEFT OUTER JOIN specimenTypes st "
                    + "ON st.`idspecimenTypes` = t.`specimenType` ";
            if(CaseSensitive)
            {
                query += "WHERE t.`name` LIKE ? ";
            }
            else
            {
                query += "WHERE LOWER(t.`name`) LIKE LOWER(?) ";
            }
            if (orderableOnly)
            {
                query += " AND isOrderable = 1 ";
            }
            query += "AND t.`active` = 1";
            ResultSet rs;
            
            String nameParam = SQLUtil.createSearchParam(name);
            stmt = createStatement(con, query, nameParam);
            rs = stmt.executeQuery();
            
            return rs;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public ResultSet SearchTestAndDeptByTestName(String name, boolean orderableOnly) throws SQLException
    {
        return SearchTestAndDeptByTestName(name, false, orderableOnly);
    }
    
    public Boolean isTestPainManagement(Integer TestNumber)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        { 
            Boolean res = null;
            Statement stmt = con.createStatement();
            
            String query = "SELECT d. `promptPOC` = 1 AS 'isPMTest' "
                    + "FROM " + table + " t "
                    + "LEFT OUTER JOIN departments d "
                    + "ON d.`idDepartment` = t.`department` "
                    + "WHERE t.`number` = " + TestNumber + " "
                    + "AND `active` = 1";
            ResultSet rs;

            rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                res = rs.getBoolean("isPMTest");
            }
            
            stmt.close();
            rs.close();
            
            return res;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Boolean isTestBillOnly(Integer testID)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        { 
            Boolean res = null;
            Statement stmt = con.createStatement();
            
            String query = "SELECT `billingOnly` "
                    + "FROM " + table + " t "
                    + "WHERE `idTests` = " + testID;
            ResultSet rs;

            rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                res = rs.getBoolean("billingOnly");
            }
            
            stmt.close();
            rs.close();
            
            return res;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public java.sql.Date DeactivateByID(int ID) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try
        {
            java.util.Date uDate = new java.util.Date();
            java.sql.Date sDate = Convert.ToSQLDate(uDate);
            
            String stmt = "UPDATE " + table + "SET "
                    + "`active` = ?, "
                    + "`deactivatedDate` = ? "
                    + "WHERE `active` = ? "
                    + "AND `idtests` = " + ID;
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            
            pStmt.setBoolean(1, false);
            pStmt.setDate(2, sDate);
            pStmt.setBoolean(3, true);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return sDate;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public List<Tests> GetAllPanelHeaders()
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        ArrayList<Tests> found = new ArrayList<>();
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idtests`, `number`, `name` FROM " + table
                    + " WHERE `testType` = 0"
                    + " AND `active` = " + true;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next())
            {
                Tests t = new Tests();
                t.setIdtests(rs.getInt("idtests"));
                t.setName(rs.getString("name"));
                t.setNumber(rs.getInt("number"));
                found.add(t);
            }
            
            rs.close();
            stmt.close();

            return found;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return found;
        }
    }
    
    public int GetNextAvailableTestNumber() 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        try
        {
            int number = 0;
            Statement stmt = con.createStatement();
            
            String query = "SELECT MIN(t1.number + 1) AS 'NextNumber' "
                    + "FROM " + table + " t1 "
                    + "LEFT JOIN " + table + " t2 "
                    + "ON t1.number + 1 = t2.number "
                    + "WHERE t2.number IS NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                number = rs.getInt("NextNumber");
            }
            
            rs.close();
            stmt.close();
            
            return number;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }
    
    /**
     * Gets the next text number excluding the provided list of numbers.
     * @param exclude
     * @return 
     */
    public int GetNextAvailableTestNumberExcluding(ArrayList<Integer> exclude)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
        
        if (exclude == null || exclude.isEmpty()) 
            return GetNextAvailableTestNumber();
        
        String excludeSQL ="";
        for (Integer excludeNumber: exclude)
        {
            if (!excludeSQL.isEmpty())
            {
                excludeSQL += ",";
            }
            excludeSQL += excludeNumber.toString();
        }
        
        try
        {
            int number = 0;
            Statement stmt = con.createStatement();
            
            String query = "SELECT MIN(t1.number + 1) AS 'NextNumber' "
                    + "FROM " + table + " t1 "
                    + "LEFT JOIN " + table + " t2 "
                    + "ON t1.number + 1 = t2.number "
                    + "WHERE t2.number IS NULL AND "
                    + "t1.number NOT IN (" + excludeSQL + ")";
                    
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                number = rs.getInt("NextNumber");
            }
            
            rs.close();
            stmt.close();
            
            return number;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }        
        
    }
    
    /**
     * Gives you a tests online code by test number and instrument id.
     * Originally made for Automated Result Posting for the ability of
     * deleteing/removing Result Rows from instRes_* tables manually.
     * @param instId idInstruments for which you are looking
     * @param number test number being searched for
     * @return String onlineCode1
     */
    public String GetOnlineCode1ByInstAndNum(int instId, int number){
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            String onlineCode = null;
            Statement stmt = con.createStatement();
            
            String query = "SELECT `onlineCode1` FROM " + table + " "
                    + "WHERE `active` = " + true + " "
                    + "AND `number` = " + number + " "
                    + "AND `instrument` = " + instId;
            
            ResultSet rs;

            rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                onlineCode = rs.getString("onlineCode1");
            }
            
            stmt.close();
            rs.close();
            
            return onlineCode;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public List<Tests> GetTestsUsingReportHeader(ReportHeader reportHeader)
            throws IllegalArgumentException, SQLException
    {
        if (reportHeader == null) throw new IllegalArgumentException(
            "TestDAO::GetTestsUsingReportHeader: Received NULL parameter ReportHeader object");
        
        LinkedList<Tests> tests = new LinkedList<>();
        String sql = "SELECT * FROM tests WHERE reportHeaderId = " + reportHeader.getId();
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            tests.add(SetTestFromResultSet(new Tests(), rs));
        }
        return tests;
    }
    
    /**
     * Returns a test object for any test that contains the parameter test number.
     * 
     * If the test is in a panel, it would return an object for that panel test.
     * 
     * If the test is in a panel in a battery, it would also return an object for
     *  the battery.
     * 
     * If alsoGetArgumentTest is true, a test object will be retrieved for the
     *  test number provided as an argument.
     * 
     * 
     * @param orderableStatus
     * @param activeStatus
     * @param orderBy
     * @param testNumber
     * @param alsoGetArgumentTest
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public List<Tests> GetTestsContaining(
            OrderableStatus orderableStatus,
            ActiveStatus activeStatus,
            OrderBy orderBy,
            int testNumber,
            boolean alsoGetArgumentTest)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (orderableStatus == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter OrderableStatus");
        
        if (activeStatus == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter ActiveStatus");
        
        if (orderBy == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter OrderBy");
        
        if (testNumber <= 0) throw new IllegalArgumentException(
                "TestDAO::GetTestsContaining: Received testNumber argument of " + testNumber);
        
        String activeCheck = " (t.active = 1 OR t.active = 0)";
        if (activeStatus.equals(ActiveStatus.ACTIVE_ONLY)) activeCheck = " t.active = 1";
        if (activeStatus.equals(ActiveStatus.INACTIVE_ONLY)) activeCheck = " t.active = 0";
        
        String orderableCheck = " (t.isOrderable = 1 OR t.isOrderable = 0)";
        if (orderableStatus.equals(OrderableStatus.ORDERABLE_ONLY)) orderableCheck = " t.isOrderable = 1";
        if (orderableStatus.equals(OrderableStatus.UNORDERABLE_ONLY)) orderableCheck = " t.isOrderable = 0";
        
        String orderByStr = "";
        if (orderBy.equals(OrderBy.ABBR)) orderByStr = " ORDER BY abbr ASC";
        if (orderBy.equals(OrderBy.NUMBER)) orderByStr = " ORDER BY number ASC";
        if (orderBy.equals(OrderBy.NAME)) orderByStr = " ORDER BY name ASC";
        
        LinkedList<Tests> tests = new LinkedList<>();
        
        String sql = "SELECT * FROM"
        + " (";
        
        // Check if the user wants to be returned a test object for the argument
        // test number they've provided
        if (alsoGetArgumentTest)
        {
                // The argument test itself
                sql += " SELECT t.* FROM tests t";
                sql += " WHERE t.number = " + testNumber;
                sql += " AND" + activeCheck;
                sql += " AND" + orderableCheck;
                sql += " UNION ";
        }
                // Batteries containing the panel
                sql += " SELECT t.* FROM tests t";
                sql += " INNER JOIN panels p ON t.idtests = p.idpanels";
                sql += " INNER JOIN tests t2 ON p.subtestid = t2.idtests";
                sql += " WHERE t2.number = " + testNumber + " AND t.header = 1";
                sql += " AND t2.testType = 0";
                sql += " AND" + activeCheck;
                sql += " AND" + orderableCheck;
                sql += " UNION";
        
                // Panels containing the test
                sql += " SELECT t.* FROM tests t";
                sql += " INNER JOIN panels p ON t.idtests = p.idpanels";
                sql += " INNER JOIN tests t2 ON p.subtestid = t2.idtests";
                sql += " WHERE t2.number = " + testNumber + " AND t.header = 0";
                sql += " AND" + activeCheck;
                sql += " AND" + orderableCheck;
                sql += " UNION";
                // Batteries containing the test
                sql += " SELECT t.* FROM tests t";
                sql += " INNER JOIN panels p ON t.idtests = p.idpanels";
                sql += " INNER JOIN tests t2 ON p.subtestid = t2.idtests";
                sql += " INNER JOIN panels p2 ON t2.idtests = p2.idpanels";
                sql += " INNER JOIN tests t3 ON p2.subtestid = t3.idtests";
                sql += " WHERE t3.number = " + testNumber + " AND t.header = 1";
                sql += " AND" + activeCheck;
                sql += " AND" + orderableCheck;
        sql += " ) inPanels " + orderByStr + ";";
        
        PreparedStatement pStmt = con.prepareCall(sql);
        
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            Tests test = new Tests();
            tests.add(SetTestFromResultSet(test, rs));
        }
        rs.close();
        pStmt.close();
        return tests;
    }
    
    /**
     * Gets test objects for all tests that meet the argument criteria
     * @param orderableStatus
     * @param activeStatus
     * @param orderBy
     * @return
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public List<Tests> GetTests(
            OrderableStatus orderableStatus,
            ActiveStatus activeStatus,
            OrderBy orderBy)
            throws IllegalArgumentException, SQLException
    {
        if (orderableStatus == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter OrderableStatus");
        
        if (activeStatus == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter ActiveStatus");
        
        if (orderBy == null) throw new IllegalArgumentException(
                "TestDAO::GetTests: Received NULL parameter OrderBy");
        
        LinkedList<Tests> tests = new LinkedList<>();
        String sql = "SELECT * FROM tests WHERE 1=1 ";
        if (orderableStatus == OrderableStatus.ORDERABLE_ONLY)
        {
            sql += " AND isOrderable = 1";
        }
        if (orderableStatus == OrderableStatus.UNORDERABLE_ONLY)
        {
            sql += " AND isOrderable = 0";
        }
        
        if (activeStatus == ActiveStatus.ACTIVE_ONLY)
        {
            sql += " AND active = 1";
        }
        
        if (activeStatus == ActiveStatus.INACTIVE_ONLY)
        {
            sql += " AND active = 0";
        }
        
        if (orderBy == OrderBy.NAME)
        {
            sql += " ORDER BY name ASC ";
        }
        else if (orderBy == OrderBy.NUMBER)
        {
            sql += " ORDER BY number ASC ";
        }
        else if (orderBy == OrderBy.ABBR)
        {
            sql += " ORDER BY abbr ASC ";
        }
        
        PreparedStatement pStmt = con.prepareCall(sql);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            tests.add(SetTestFromResultSet(new Tests(), rs));
        }
        rs.close();
        pStmt.close();
        return tests;        
    }
    
    /**
     * Get the specimen type from the tests table for the row with the given instrument
     * and onlineCode1. If multiple rows match, then the first row returned will be used.
     * @param instrument - the instrument id to be searched for
     * @param onlineCode - the online code of the test to be searched for
     * @return 
     */
    public Integer GetSpecimenTypeByOnlineCodeAndInstrument(int instrument, String onlineCode)
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        Integer specimenType = null;
        try
        {
            PreparedStatement stmt = null; //con.createStatement();
            String query = "SELECT `specimenType`"
                    + " FROM " + table + " t"
                    + " WHERE t.instrument = " + instrument
                    + " AND t.onlineCode1 = ?"
                    + " AND t.active = 1";
            
            stmt = createStatement(con, query, onlineCode);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next())
            {
                specimenType = rs.getInt("specimenType");
            }
            rs.close();
            stmt.close();
            return specimenType;
        }
        catch(Exception ex)
        {
            System.out.println(ex.toString());
            return specimenType;
        }
    }
    
    
    /**
     * Returns the active version for the provided identifier
     * 
     * @param testId
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public Tests GetNewestVersionOfTest(Integer testId)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        if (testId == null || testId.equals(0)) 
            throw new IllegalArgumentException("TestDAO::GetNewestVersionOfTest: Received NULL or invalid test id argument");
        
        Tests test = GetTestByID(testId);
        return GetNewestVersionOfTest(test);
    }
    
    /**
     * Returns the active version of the test for the provided object
     * 
     * @param test
     * @return
     * @throws SQLException
     * @throws NullPointerException
     * @throws IllegalArgumentException 
     */
    public Tests GetNewestVersionOfTest(Tests test)
            throws SQLException, NullPointerException, IllegalArgumentException
    {
        
        if (test == null || test.getIdtests() == null || test.getIdtests().equals(0)) 
                 throw new IllegalArgumentException(
                         "TestDAO::GetNewestVersionOfTest: Received NULL or invalid test object argument");
        
        String sql = "SELECT * FROM tests WHERE active = 1 AND number = ?";
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1,test.getNumber());
        ResultSet rs = pStmt.executeQuery();
        int i = 0;
        Tests newTest = new Tests();
        while (rs.next())
        {
            ++i;
            newTest = SetTestFromResultSet(test, rs);
        }
        
        // Record instances where there is a duplicate active test
        if (i > 1)
        {
            SysLogDAO.Add(1, "TestDAO::GetNewestVersionOfTest: More than one active test found for number " + test.getNumber(), "");
        }
        rs.close();
        pStmt.close();
        return newTest;
    }
    
    /**
     * Returns a list of tests that the argument test is contained within
     * 
     *  ActiveStatus and OrderableStatus arguments are required.
     * 
     *  Returns an empty list if the argument test is not contained in a panel
     *   or battery matching the active/orderable status settings.
     * 
     * @param testId
     * @param activeStatus
     * @param orderableStatus
     * @return
     * @throws IllegalArgumentException
     * @throws NullPointerException
     * @throws SQLException 
     */
    public static List<Tests> getParentTests(
            Integer testId,
            ActiveStatus activeStatus,
            OrderableStatus orderableStatus)
            throws IllegalArgumentException, NullPointerException, SQLException
    {
        if (testId == null || testId <= 0)
        {
            throw new IllegalArgumentException("TestDAO::getParentTests: "
                    + "Received a [NULL] or invalid testId");
        }
        
        if (activeStatus == null)
        {
            throw new IllegalArgumentException("TestDAO::getParentTests: "
                    + "Received a [NULL] TestStatus enum argument");
        }
        
        if (orderableStatus == null)
        {
            throw new IllegalArgumentException("TestDAO::getParentTests: "
                    + "Received a [NULL] OrderableStatus enum argument");            
        }
        
        String sql = "SELECT t.*"
                + " FROM tests t "
                + " INNER JOIN panels p ON t.idtests = p.idpanels"
                + " WHERE p.subtestId = ?";
        
        switch (activeStatus)
        {
            case ACTIVE_ONLY:
                sql += " AND t.active = 1";
                break;
                
            case INACTIVE_ONLY:
                sql += " AND t.active = 0";
                break;
        }
        
        switch (orderableStatus)
        {
            case ORDERABLE_ONLY:
                sql += " AND t.isOrderable = 1";
                break;
            case UNORDERABLE_ONLY:
                sql += " AND t.isOrderable = 0";
        }
        
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        if (con.isValid(2) == false) con = CheckDBConnection.Check(dbs, con);
        
        List<Tests> parentTests = new ArrayList<>();
        
        PreparedStatement pStmt = con.prepareStatement(sql);
        pStmt.setInt(1, testId);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next())
        {
            parentTests.add(SetTestFromResultSet(new Tests(), rs));
        }
        rs.close();
        pStmt.close();
        return parentTests;
    }
    
    /**
     * Returns a tree of tests with the argument test as its root
     * 
     * @param testNumber
     * @return 
     */
    public TreeNode<Tests> getTestTree(Integer testNumber)
            throws IllegalArgumentException, NullPointerException
    {
        if (testNumber == null)
        {
            throw new IllegalArgumentException("TestDAO::getTestTree:"
                    + " Received a [NULL] testNumber argument");
        }
        TestDAO tdao = new TestDAO();
        Tests rootTest = tdao.GetTestByNumber(testNumber);
        TreeNode rootNode = new TreeNode(rootTest);
        ArrayList<Tests> subtests = tdao.GetSubtestsIncludingInactive(rootTest);
        for (Tests subtest : subtests)
        {
            TreeNode childNode = rootNode.addChild(subtest);
            ArrayList<Tests> subSubtests = tdao.GetSubtestsIncludingInactive(subtest);
            for (Tests subSubtest : subSubtests)
            {
                childNode.addChild(subSubtest);
            }
        }
        return rootNode;
    }
    
    public static Tests SetTestFromResultSet(Tests test, ResultSet rs) throws SQLException
    {
        test.setIdtests(rs.getInt("idtests"));
        test.setNumber(rs.getInt("number"));
        test.setName(rs.getString("name"));
        test.setAbbr(rs.getString("abbr"));
        test.setTestType(rs.getInt("testType"));
        test.setResultType(rs.getString("resultType"));
        test.setLowNormal(rs.getDouble("lowNormal"));
        test.setHighNormal(rs.getDouble("highNormal"));
        test.setAlertLow(rs.getDouble("alertLow"));
        test.setAlertHigh(rs.getDouble("alertHigh"));
        test.setCriticalLow(rs.getDouble("criticalLow"));
        test.setCriticalHigh(rs.getDouble("criticalHigh"));
        test.setPrintNormals(rs.getString("printNormals"));
        test.setUnits(rs.getString("units"));
        test.setRemark(rs.getInt("remark"));
        test.setDepartment(rs.getInt("department"));
        test.setInstrument(rs.getInt("instrument"));
        test.setOnlineCode1(rs.getString("onlineCode1"));
        test.setOnlineCode2(rs.getString("onlineCode2"));
        test.setCreated(rs.getDate("created"));
        test.setRelatedDrug(rs.getInt("relatedDrug"));
        test.setDecimalPositions(rs.getInt("decimalPositions"));
        test.setPrintOrder(rs.getInt("printOrder"));
        test.setReportHeaderId(rs.getInt("reportHeaderId"));
        test.setSpecimenType(rs.getInt("specimenType"));
        test.setLoinc(rs.getString("loinc"));
        test.setBillingOnly(rs.getBoolean("billingOnly"));
        test.setLabelPrint(rs.getBoolean("labelPrint"));
        test.setTubeTypeId((rs.getInt("tubeTypeId")));
        test.setHeaderPrint(rs.getBoolean("headerPrint"));
        test.setActive(rs.getBoolean("active"));
        test.setDeactivatedDate(rs.getDate("deactivatedDate"));
        test.setTestComment(rs.getBytes("testComment"));
        test.setExtraNormals(rs.getBoolean("extraNormals"));
        test.setAOE(rs.getBoolean("AOE"));
        test.setCycles(rs.getInt("cycles"));
        test.setStat(rs.getBoolean("stat"));
        test.setHeader(rs.getBoolean("header"));
        test.setMaxReportable(rs.getDouble("maxReportable"));
        if(test.getMaxReportable() == 0)
            test.setMaxReportable(null);
        test.setMinReportable(rs.getDouble("minReportable"));
        if(test.getMinReportable() == 0)
            test.setMinReportable(null);
        test.setUseMaximums(rs.getBoolean("useMaximums"));
        test.setMaxLowResult(rs.getString("maxLowResult"));
        test.setMaxHighResult(rs.getString("maxHighResult"));
        test.setMaxLowNumeric(rs.getDouble("maxLowNumeric"));
        test.setMaxHighNumeric(rs.getDouble("maxHighNumeric"));
        test.setPrintedName(rs.getString("printedName"));
        test.setPrintOnReport(rs.getBoolean("printOnReport"));
        test.setNoRounding(rs.getBoolean("noRounding"));
        test.setLowRemark(rs.getInt("lowRemark"));
        test.setLowShowNumeric(rs.getBoolean("lowShowNumeric"));
        test.setHighRemark(rs.getInt("highRemark"));
        test.setHighShowNumeric(rs.getBoolean("highShowNumeric"));
        test.setAlertLowRemark(rs.getInt("alertLowRemark"));
        test.setAlertLowShowNumeric(rs.getBoolean("alertLowShowNumeric"));
        test.setAlertHighRemark(rs.getInt("alertHighRemark"));
        test.setAlertHighShowNumeric(rs.getBoolean("alertHighShowNumeric"));
        test.setCritLowRemark(rs.getInt("critLowRemark"));
        test.setCritLowShowNumeric(rs.getBoolean("critLowShowNumeric"));
        test.setCritHighRemark(rs.getInt("critHighRemark"));
        test.setCritHighShowNumeric(rs.getBoolean("critHighShowNumeric"));
        test.setNormalRemark(rs.getInt("normalRemark"));
        test.setNormalShowNumeric(rs.getBoolean("normalShowNumeric"));
        test.setIsOrderable(rs.getBoolean("isOrderable"));
        test.setPlaceOfServiceId(rs.getInt("placeOfServiceId"));
        if (test.getPlaceOfServiceId() == 0)
            test.setPlaceOfServiceId(null);
        test.setCost(rs.getDouble("cost"));
        
        return test;
    }
    
    private String GenerateInsertStatement(ArrayList<String> fields) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        String stmt = "INSERT INTO " + table + "(";
        String values = ") values (";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "`";
            values += "?";
            if(i != fields.size() - 1)
            {
                stmt += ", ";
                values +=",";
            }
        }
        values += ")";
        return stmt + values;
    }
    
    private String GenerateUpdateStatement(ArrayList<String> fields) 
    {
        try {
            if (con.isClosed()) {
                CheckDBConnection();
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        String stmt = "UPDATE " + table + " SET";
        for(int i = 0; i < fields.size(); ++i)
        {
            stmt += " `" + fields.get(i) + "` = ?";
            if(i != fields.size() - 1)
            {
                stmt += ",";
            }
        }
        return stmt;
    }

    @Override
    public Boolean Insert(Serializable obj)
    {
        return InsertTest((Tests)obj);
    }

    @Override
    public Boolean Update(Serializable obj)
    {
        return UpdateTest((Tests)obj);
    }

    @Override
    public Boolean Delete(Serializable obj)
    {
        int ID = ((Tests)obj).getIdtests();
        return (DeactivateByID(ID) != null ? true : null);
    }

    @Override
    public Serializable getByID(Integer ID)
    {
        return GetTestByID(ID);
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }
}
