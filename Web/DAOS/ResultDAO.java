package Web.DAOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: OrderDAO.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import DAOS.*;
import DAOS.IDAOS.IResultDAO;
import DAOS.IDAOS.IStructureCheckable;
import DOS.Results;
import Database.CheckDBConnection;
import Database.DatabaseStructureCheck;
import Utility.Convert;
import Utility.SQLUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//import DAOS.RemarkDAO;

import static Utility.SQLUtil.createStatement;

public class ResultDAO implements IResultDAO, IStructureCheckable
{
    Web.Database.WebDatabaseSingleton dbs = Web.Database.WebDatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    private final String table = "`results`";
    
    public boolean InsertResult(Results result) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        //if(GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId()) != 0)
        //    return UpdateResult(result);
        String stmt = "INSERT INTO " + table + "("
                    + " `orderId`,"
                    + " `testId`,"
                    + " `panelId`,"
                    + " `resultNo`,"
                    + " `resultText`,"
                    + " `resultRemark`,"
                    + " `resultChoice`,"
                    + " `created`,"
                    + " `reportedBy`,"
                    + " `dateReported`,"
                    + " `isApproved`,"
                    + " `approvedDate`,"
                    + " `approvedby`,"
                    + " `isInvalidated`,"
                    + " `invalidatedDate`,"
                    + " `invalidatedBy`,"
                    + " `isUpdated`,"
                    + " `updatedBy`,"
                    + " `updatedDate`,"
                    + " `isAbnormal`,"
                    + " `isLow`,"
                    + " `isHigh`,"
                    + " `isCIDLow`,"
                    + " `isCIDHigh`,"
                    + " `textAnswer`,"
                    + " `hl7Transmitted`,"
                    + " `printAndTransmitted`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        PreparedStatement pStmt = null;
                
        try
        {
            
            pStmt = con.prepareStatement(stmt);
            
            //int debug = 0;
            //if(result.getTestId() == 2677)
                //++debug;
            /*pStmt.setInt(1,remark.getRemarkNo());
            pStmt.setInt(2,remark.getRemarkName());
            pStmt.setString(3,remark.getRemarkAbbr());
            pStmt.setInt(4,remark.getRemarkType());
            pStmt.setBytes(5,remark.getRemarkText());
            pStmt.setInt(6,remark.getIsAbnormal());
            pStmt.setInt(7,remark.getRemarkDepartment());
            pStmt.setInt(8,remark.getNoCharge());*/
            
        
            pStmt.setInt(1,result.getOrderId());
            pStmt.setInt(2,result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            pStmt.setTimestamp(8, Convert.ToSQLDateTime(result.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, result.getReportedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 10, result.getDateReported());
            SQLUtil.SafeSetBoolean(pStmt, 11, result.getIsApproved());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, result.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, 13, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 14, result.getIsInvalidated());
            SQLUtil.SafeSetTimeStamp(pStmt, 15, result.getInvalidatedDate());
            SQLUtil.SafeSetInteger(pStmt, 16, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 18, result.getUpdatedBy());
            /*
            if(result.getUpdatedDate() == null)
                pStmt.setNull(18, java.sql.Types.TIMESTAMP);
            else
            {
                //pStmt.setTimestamp(18,Convert.ToSQLDateTime(result.getUpdatedDate()));
                java.util.Date now = new java.util.Date();
                pStmt.setTimestamp(18,Convert.ToSQLDateTime(now));
            }
            */
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(19,Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 24, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 25, result.getTextAnswer());
            pStmt.setBoolean(26, result.getHl7Transmitted());
            pStmt.setBoolean(27, result.isPrintAndTransmitted());
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add logging for failures
            System.out.println(message);
            if(pStmt != null)
            {
                pStmt.close();
            }
            return false;
        }
    }
    
    public boolean UpdateResult(Results result) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
                        
            String stmt = "UPDATE " + table + " SET"
                    + " `orderId` = ?,"
                    + " `testId` = ?,"
                    + " `panelId` = ?,"
                    + " `resultNo` = ?,"
                    + " `resultText` = ?,"
                    + " `resultRemark` = ?,"
                    + " `resultChoice` = ?,"
                    + " `reportedBy` = ?,"
                    + " `dateReported` = ?,"
                    + " `isApproved` = ?,"
                    + " `approvedDate` = ?,"
                    + " `approvedby` = ?,"
                    + " `isInvalidated` = ?,"
                    + " `invalidatedDate` = ?,"
                    + " `invalidatedBy` = ?,"
                    + " `isUpdated` = ?,"
                    + " `updatedBy` = ?,"
                    + " `updatedDate` = ? ,"
                    + " `isAbnormal` = ?,"
                    + " `isLow` = ?,"
                    + " `isHigh` = ?,"
                    + " `isCIDLow` = ?,"
                    + " `isCIDHigh` = ?,"
                    + " `textAnswer` = ?,"
                    + " `hl7Transmitted` = ?,"
                    + " `printAndTransmitted` = ? "
                    + "WHERE `idResults` = " + result.getIdResults();
            
            PreparedStatement pStmt = con.prepareStatement(stmt);
            int debug = 0;
            if(result.getTestId() == 2677)
                ++debug;
            
            pStmt.setInt(1,result.getOrderId());
            pStmt.setInt(2,result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            SQLUtil.SafeSetInteger(pStmt, 8, result.getReportedBy());
            if(result.getDateReported() != null){
                SQLUtil.SafeSetTimeStamp(pStmt, 9, Convert.ToSQLDateTime(result.getDateReported()));
            }else{
                pStmt.setNull(9, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetBoolean(pStmt, 10, result.getIsApproved());
            if(result.getApprovedDate() != null){
                SQLUtil.SafeSetTimeStamp(pStmt, 11, Convert.ToSQLDateTime(result.getApprovedDate()));
            }else{
                pStmt.setNull(11, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 12, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 13, result.getIsInvalidated());
            if(result.getInvalidatedDate() != null){
                SQLUtil.SafeSetTimeStamp(pStmt, 14, Convert.ToSQLDateTime(result.getInvalidatedDate()));
            }else{
                pStmt.setNull(14, java.sql.Types.TIMESTAMP);
            }
            SQLUtil.SafeSetInteger(pStmt, 15, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 16, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 17, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(18,Convert.ToSQLDateTime(now));
            //SQLUtil.SafeSetTimeStamp(pStmt, 18, Convert.ToSQLDateTime(result.getUpdatedDate()));
            /*
            if(result.getUpdatedDate() == null)
                pStmt.setNull(18, java.sql.Types.TIMESTAMP);
            else
                pStmt.setTimestamp(18,Convert.ToSQLDateTime(result.getUpdatedDate()));
            */
            /*Who in the hell did this?
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
            pStmt.setTimestamp(18,Convert.ToSQLDateTime(now));
            pStmt.setTimestamp(18, currentTimestamp);
            */ 
            //pStmt.setInt(19, result.getIsAbnormal());
            //pStmt.setInt(20, result.getIsLow());
            //pStmt.setInt(21, result.getIsHigh());
            //pStmt.setInt(22, result.getIsCIDLow());
            //pStmt.setInt(23, result.getIsCIDHigh());
            //pStmt.setString(24, result.getTextAnswer());
            SQLUtil.SafeSetBoolean(pStmt, 19, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 24, result.getTextAnswer());
            pStmt.setBoolean(25, result.getHl7Transmitted());
            pStmt.setBoolean(26, result.isPrintAndTransmitted());
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
        }catch(Exception ex)
        {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }
    
    /*public Results GetResult(int AccessionNumber, int TestNumber) throws SQLException
    {
        try
        {
            Results order = new Results();
            Statement stmt  = con.createStatement();
            
            String query = "SELECT * FROM " + table
                    + "WHERE `accession` = " + AccessionNumber;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                order.setAccession(rs.getInt("accession"));
                order.setClientId(rs.getInt("clientId"));
                order.setLocationId(rs.getInt("locationId"));
                order.setDoctorId(rs.getInt("doctorId"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setSpecimenDate(rs.getDate("specimenDate"));
                order.setPatientId(rs.getInt("patientId"));
                order.setSpecies(rs.getInt("species"));
            }
            
            rs.close();
            stmt.close();
            
            return order;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling.
            String message = ex.toString();
            System.out.println(message);
            return null;
        }    
    }*/
    
    public Results GetResultByID(int resultID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Results res = new Results();
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `idResults` = " + resultID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                res = new Results();
                
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
                
            }
            
            rs.close();
            stmt.close();
        
            return res;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public List<Results> GetResultsByOrderID(int orderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        List<Results> rl = new ArrayList<Results>();
        Results res;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `orderId` = " + orderID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res = new Results();
                
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
                
                rl.add(res);
            }
            
            rs.close();
            stmt.close();
        
            return rl;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public List<Results> GetActiveResultsByOrderID(int orderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        List<Results> rl = new ArrayList<Results>();
        Results res;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table + " r " +
                    " WHERE `orderId` = " + orderID
                    + " AND (r.resultText IS NULL OR (r.resultText IS NOT NULL "
                    + " AND r.resultText != 'DELETED')) "
                    + " AND (r.isInvalidated IS NULL OR (r.isInvalidated IS NOT NULL "
                    + " AND r.isInvalidated = 0))";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res = new Results();
                
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
                
                rl.add(res);
            }
            
            rs.close();
            stmt.close();
        
            return rl;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public List<Integer> GetAllTestIdsByOrderId(Integer OrderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        List<Integer> rl = new ArrayList<Integer>();
        int res;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `testId` FROM " + table +
                    " WHERE `orderId` = " + OrderID ;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res = 0;
                
                res = rs.getInt("testId");
                
                rl.add(res);
            }
            
            rs.close();
            stmt.close();
        
            return rl;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public Results GetResultByOrderIDTestID(int orderID, int testID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        Results res = new Results();
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `orderId` = " + orderID +
                    " AND `testId` = " + testID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
            }
            
            rs.close();
            stmt.close();
        
            return res;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public List<Results> GetReportedResultByOrderID(int orderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        List<Results> rl = new ArrayList<Results>();
        Results res;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `orderId` = " + orderID +
                    " AND `dateReported` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res = new Results();
                
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
                
                rl.add(res);
            }
            
            rs.close();
            stmt.close();
        
            return rl;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
     public List<Results> GetApprovedResultByOrderID(int orderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
                
        List<Results> rl = new ArrayList<Results>();
        Results res;
        
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT * FROM " + table +
                    " WHERE `orderId` = " + orderID +
                    " AND `approvedDate` IS NOT NULL";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next())
            {
                res = new Results();
                
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));
                
                rl.add(res);
            }
            
            rs.close();
            stmt.close();
        
            return rl;
            
        }catch(Exception ex2)
        {
            System.out.println(ex2.toString());
            return null;
        }
    }
    
    public int GetResultIdByOrderIdTestId(int OrderID, int TestID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        int id;
        String ids = "";
        try
        {
            Statement stmt = con.createStatement();
            
            String query = "SELECT `idResults` FROM "
                    + table +
                    "WHERE `orderId` = " + OrderID +
                    " AND `testId` = " + TestID;
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next())
            {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            }
            else
            {
                id = 0;
            }
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex)
        {
            System.out.println(ex.toString());
            return 0;
        }
    }
    
    public boolean ResultExists(int OrderID, int TestID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID +
                    " AND `testid` = " + TestID);
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            //TODO:  Add Exception Handling
            return false;
        }    
    }
    
    public boolean ResultExists(int OrderID, int TestID, String ResultText)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            //stmt = con.createStatement();
            String query = "SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID +
                    " AND `testid` = " + TestID +
                    " AND `resultText` = ?;";
            stmt = createStatement(con, query, ResultText);
            rs = stmt.executeQuery();
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            //TODO:  Add Exception Handling
            return false;
        }    
    }
    
    public boolean ResultExists(int OrderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID);
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            //TODO:  Add Exception Handling
            return false;
        }    
    }
    
    public boolean ResultExistsCum(int OrderID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            Statement stmt = null;
            ResultSet rs = null;
            int rowCount = -1;
        
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) FROM " + table + " WHERE "
                    + "`orderid` = " + OrderID + 
                    " AND `dateReported` IS NOT NULL");
            rs.next();
            rowCount = rs.getInt(1);
            
            rs.close();
            stmt.close();
            
            if (rowCount > 0)
                return true;
            else
                return false;
        }catch(Exception ex)
        {
            //TODO:  Add Exception Handling
            return false;
        }    
    }
    
    public boolean PurgeResultByResultID(int RESULTID)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }
                
        try
        {
            Statement stmt = null;
            
            String query = "DELETE FROM " + table + " WHERE "
                    + "`idResults` = " + RESULTID;
            
            stmt = con.createStatement();
            int delete = stmt.executeUpdate(query);
            
            if (delete == 1)
                return true;
            else
                return false;
        }
        catch(Exception ex)
        {
            //TODO:  Add Exception Handling
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
     * @param idResults
     * @return 
     */
    public ArrayList<Results> GetSubtestResults(int idResults)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        Results res = GetResultByID(idResults);
        if (res == null) return null;
        
        ArrayList<Results> results = new ArrayList<>();
        
        // Get all the "child" tests for the provided result
        String sql = "SELECT * FROM " + table + 
                " WHERE orderId = " + res.getOrderId()
                + " AND panelId = " + res.getTestId();
        
        try
        {
            Statement stmt = con.createStatement();         
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                res = new Results();
                res.setIdResults(rs.getInt("idResults"));
                res.setOrderId(rs.getInt("orderId"));
                res.setTestId(rs.getInt("testId"));
                res.setPanelId(rs.getInt("panelId"));
                res.setResultNo(rs.getDouble("resultNo"));
                res.setResultText(rs.getString("resultText"));
                res.setResultRemark(rs.getInt("resultRemark"));
                res.setResultChoice(rs.getInt("resultChoice"));
                res.setCreated(rs.getTimestamp("created"));
                res.setReportedBy(rs.getInt("reportedBy"));
                res.setDateReported(rs.getTimestamp("dateReported"));
                res.setIsApproved(rs.getBoolean("isApproved"));
                res.setApprovedDate(rs.getTimestamp("approvedDate"));
                res.setApprovedBy(rs.getInt("approvedBy"));
                res.setIsInvalidated(rs.getBoolean("isInvalidated"));
                res.setInvalidatedDate(rs.getTimestamp("invalidatedDate"));
                res.setInvalidatedBy(rs.getInt("invalidatedBy"));
                res.setIsUpdated(rs.getBoolean("isUpdated"));
                res.setUpdatedBy(rs.getInt("updatedBy"));
                res.setUpdatedDate(rs.getTimestamp("updatedDate"));
                res.setIsAbnormal(rs.getBoolean("isAbnormal"));
                res.setIsHigh(rs.getBoolean("isHigh"));
                res.setIsLow(rs.getBoolean("isLow"));
                res.setIsCIDHigh(rs.getBoolean("isCIDHigh"));
                res.setIsCIDLow(rs.getBoolean("isCIDLow"));
                res.setHl7Transmitted(rs.getBoolean("hl7Transmitted"));

                results.add(res);
            }
            rs.close();
            stmt.close();
            
        }
        catch (SQLException ex)
        {
            //TODO:  Add Exception Handling
            return null;
        }

        return results;
    }
    
    public int GetIDForDetailImport(int arNo, String acc, int test, int subtest) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }
                
        int id;
        String ids = "";
        
        try{
        Statement stmt = null;
        ResultSet rs = null;
        
        stmt = con.createStatement();
        
        /*rs = stmt.executeQuery("SELECT r.idResults"
            + " FROM results r"
            + " LEFT OUTER JOIN"
            + " orders o ON r.orderId = o.idOrders"
            + " LEFT OUTER JOIN"
            + " tests t ON r.testId = t.idTests"
            + " LEFT OUTER JOIN"
            + " patients p ON o.patientId = p.idPatients"
            + " WHERE p.arNo = " + arNo
            + " AND o.accession = " + acc
            + " AND t.number = " + test
            + " AND t.subtest = " + subtest);*/
        
        /*rs = stmt.executeQuery(
                "SELECT r.idResults " +
                "FROM results r " +
                "LEFT OUTER JOIN " +
                "tests t ON r.testId = t.idTests, " +
                "( " +
                "  SELECT o.idOrders " +
                "  FROM orders o " +
                "  LEFT OUTER JOIN " + 
                "  patients p ON o.patientId = p.idPatients " +
                "  WHERE o.accession = " + acc +
                " AND p.arNo = " + arNo +
                ") ords " +
                "WHERE ords.idOrders = r.orderId " +
                "AND t.number = " + test + " " +
                "AND t.subtest = " + subtest + ";"
                );*/
        
        rs = stmt.executeQuery(
                "SELECT r.idResults " +
                "FROM results r, " +
                "( " +
                "  SELECT t.idTests " +
                "  FROM tests t " +
                "  WHERE t.number = " + test + " " +
                "  AND t.subtest = " + subtest + " " +
                ") tst, " +
                "( " +
                "  SELECT o.idOrders " +
                "  FROM orders o, " +
                "  ( " +
                "    SELECT p.idPatients " +
                "    FROM patients p " +
                "    WHERE p.arNo = " + arNo + " " +
                "  ) pat " +
                "  WHERE o.accession = \"" + acc + "\" " +
                "  AND o.patientId = pat.idPatients " +
                ") ords " +
                "WHERE ords.idOrders = r.orderId " +
                "AND tst.idTests = r.testId;"
                );
        
        if(rs.next())
            {
                ids = rs.getString(1);
                id = Integer.parseInt(ids);
            }
            else
            {
                id = 0;
            }
            
            rs.close();
            stmt.close();
            
            return id;
        }catch(Exception ex){
            System.out.println("Could not retrieve ResultID: " + ex.toString());
            return 0;
        }
    }
    
    @Override
    public Boolean IsTestNoCharge(Integer OrderID, Integer TestID)
    {
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
            ResultSet rs = null;
            Boolean noCharge = null;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT `noCharge` FROM " + table +
                " WHERE `orderId` = " + OrderID + " AND  `testId` = " + TestID);
            if(rs.next())
            {
                noCharge = rs.getBoolean("noCharge");
            }

            rs.close();
            stmt.close();

            return noCharge;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::IsTestNoCharge - " + ex.getMessage());
            return false;
        }
    }
    
    @Override
    public Boolean IsTestNoCharge(Integer resultID)
    {
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
            ResultSet rs = null;
            Boolean noCharge = null;

            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT `noCharge` FROM " + table +
                " WHERE `idResults` = " + resultID);
            if(rs.next())
            {
                noCharge = rs.getBoolean("noCharge");
            }

            rs.close();
            stmt.close();

            return noCharge;
        } catch (Exception ex) {
            //TODO:  Add Exception Handling
            System.out.println("ResultDAO::IsTestNoCharge - " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Boolean UpdateNoChargeFlag(Integer OrderID, Integer TestID, Boolean value) throws SQLException {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {


            String stmt = "UPDATE " + table + " SET"
                    + " `noCharge` = ? "
                    + "WHERE `orderId` = " + OrderID
                    + " AND `testId` = " + TestID;

            PreparedStatement pStmt = con.prepareStatement(stmt);

            pStmt.setBoolean(1, value);
            int retVal = pStmt.executeUpdate();

            pStmt.close();

            return retVal > 0;
        } catch (Exception ex) {
            String message = ex.toString();
            //TODO:  Add exception handleing
            System.out.println(message);
            return false;
        }
    }

    @Override
    public Integer InsertResultGetID(Results result) throws SQLException
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        String stmt = "INSERT INTO " + table + "("
                    + " `orderId`,"
                    + " `testId`,"
                    + " `panelId`,"
                    + " `resultNo`,"
                    + " `resultText`,"
                    + " `resultRemark`,"
                    + " `resultChoice`,"
                    + " `created`,"
                    + " `reportedBy`,"
                    + " `dateReported`,"
                    + " `isApproved`,"
                    + " `approvedDate`,"
                    + " `approvedby`,"
                    + " `isInvalidated`,"
                    + " `invalidatedDate`,"
                    + " `invalidatedBy`,"
                    + " `isUpdated`,"
                    + " `updatedBy`,"
                    + " `updatedDate`,"
                    + " `isAbnormal`,"
                    + " `isLow`,"
                    + " `isHigh`,"
                    + " `isCIDLow`,"
                    + " `isCIDHigh`,"
                    + " `textAnswer`,"
                    + " `hl7Transmitted`,"
                    + " `printAndTransmitted`)"
                    + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        

                
        try
        {
            
            PreparedStatement pStmt = con.prepareStatement(stmt,
                          Statement.RETURN_GENERATED_KEYS);            

            pStmt.setInt(1,result.getOrderId());
            pStmt.setInt(2,result.getTestId());
            SQLUtil.SafeSetInteger(pStmt, 3, result.getPanelId());
            SQLUtil.SafeSetDouble(pStmt, 4, result.getResultNo());
            SQLUtil.SafeSetString(pStmt, 5, result.getResultText());
            SQLUtil.SafeSetInteger(pStmt, 6, result.getResultRemark());
            SQLUtil.SafeSetInteger(pStmt, 7, result.getResultChoice());
            pStmt.setTimestamp(8, Convert.ToSQLDateTime(result.getCreated()));
            SQLUtil.SafeSetInteger(pStmt, 9, result.getReportedBy());
            SQLUtil.SafeSetTimeStamp(pStmt, 10, result.getDateReported());
            SQLUtil.SafeSetBoolean(pStmt, 11, result.getIsApproved());
            SQLUtil.SafeSetTimeStamp(pStmt, 12, result.getApprovedDate());
            SQLUtil.SafeSetInteger(pStmt, 13, result.getApprovedBy());
            SQLUtil.SafeSetBoolean(pStmt, 14, result.getIsInvalidated());
            SQLUtil.SafeSetTimeStamp(pStmt, 15, result.getInvalidatedDate());
            SQLUtil.SafeSetInteger(pStmt, 16, result.getInvalidatedBy());
            SQLUtil.SafeSetBoolean(pStmt, 17, result.getIsUpdated());
            SQLUtil.SafeSetInteger(pStmt, 18, result.getUpdatedBy());
            java.util.Date now = new java.util.Date();
            pStmt.setTimestamp(19,Convert.ToSQLDateTime(now));
            SQLUtil.SafeSetBoolean(pStmt, 20, result.getIsAbnormal());
            SQLUtil.SafeSetBoolean(pStmt, 21, result.getIsLow());
            SQLUtil.SafeSetBoolean(pStmt, 22, result.getIsHigh());
            SQLUtil.SafeSetBoolean(pStmt, 23, result.getIsCIDLow());
            SQLUtil.SafeSetBoolean(pStmt, 24, result.getIsCIDHigh());
            SQLUtil.SafeSetString(pStmt, 25, result.getTextAnswer());
            pStmt.setBoolean(26, result.getHl7Transmitted());
            pStmt.setBoolean(27, result.isPrintAndTransmitted());
            
            int affectedRows = pStmt.executeUpdate();            
            if (affectedRows == 0)
            {
                throw new SQLException("Insert failed, no rows affected.");
            }
            
            Integer idResults;            
            try (ResultSet generatedKeys = pStmt.getGeneratedKeys()) 
            {
                if (generatedKeys.next())
                {
                    idResults = generatedKeys.getInt(1);
                }
                else
                {
                    throw new SQLException("Insert failed, no ID obtained.");
                }
            }
            pStmt.close();
            
            return idResults;
            
        } catch (Exception ex) {
            String message = ex.toString();
            System.out.println(message);
            return null;
        }        
        
    }
    
    @Override
    public String structureCheck() {
        String query = "SELECT `results`.`idResults`,\n"
                + "    `results`.`orderId`,\n"
                + "    `results`.`testId`,\n"
                + "    `results`.`panelId`,\n"
                + "    `results`.`resultNo`,\n"
                + "    `results`.`resultText`,\n"
                + "    `results`.`resultRemark`,\n"
                + "    `results`.`resultChoice`,\n"
                + "    `results`.`created`,\n"
                + "    `results`.`reportedBy`,\n"
                + "    `results`.`dateReported`,\n"
                + "    `results`.`isApproved`,\n"
                + "    `results`.`approvedDate`,\n"
                + "    `results`.`approvedBy`,\n"
                + "    `results`.`isInvalidated`,\n"
                + "    `results`.`invalidatedDate`,\n"
                + "    `results`.`invalidatedBy`,\n"
                + "    `results`.`isUpdated`,\n"
                + "    `results`.`updatedBy`,\n"
                + "    `results`.`updatedDate`,\n"
                + "    `results`.`isAbnormal`,\n"
                + "    `results`.`isHigh`,\n"
                + "    `results`.`isLow`,\n"
                + "    `results`.`isCIDHigh`,\n"
                + "    `results`.`isCIDLow`,\n"
                + "    `results`.`textAnswer`,\n"
                + "    `results`.`noCharge`,\n"
                + "    `results`.`hl7Transmitted`,\n"
                + "    `results`.`printAndTransmitted`\n"
                + "FROM `cssweb`.`results` LIMIT 1";
        return DatabaseStructureCheck.structureCheck(query, table, con);
    }
}
