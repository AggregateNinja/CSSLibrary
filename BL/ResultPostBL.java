/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */
package BL;

import DAOS.CalculationsDAO;
import DAOS.ClientCustomRangesDAO;
import DAOS.ClientCustomReflexRulesDAO;
import DAOS.ExtraNormalsDAO;
import DAOS.LogDAO;
import DAOS.OrderDAO;
import DAOS.PanelDAO;
import DAOS.PatientDAO;
import DAOS.PreferencesDAO;
import DAOS.ReflexMultichoiceDAO;
import DAOS.ReflexRulesDAO;
import DAOS.RemarkDAO;
import DAOS.ResultDAO;
import DAOS.ResultDetailsDAO;
import DAOS.SysLogDAO;
import DAOS.TestDAO;
import DAOS.TestLogDAO;
import DOS.Calculations;
import DOS.ClientCustomRanges;
import DOS.ClientCustomReflexRules;
import DOS.ExtraNormals;
import DOS.Orders;
import DOS.Panels;
import DOS.Patients;
import DOS.ReflexMultichoice;
import DOS.ReflexRules;
import DOS.Remarks;
import DOS.ResultDetails;
import DOS.ResultPostLog;
import DOS.Results;
import DOS.Tests;
import Database.CheckDBConnection;
import Utility.Convert;
import Utility.DateUtil;
import Utility.SQLUtil;
import bsh.Interpreter;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.joda.time.LocalDate;
import org.joda.time.Years;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/02/2014
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class ResultPostBL {

    public enum UpdateHeaderResult {

        HeaderResultIdsUpdated,
        DeletedOptionalResultIds
    }

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    Interpreter INTERPRETER;
    
    CalculationsDAO calcdao = new CalculationsDAO();
    ExtraNormalsDAO endao = new ExtraNormalsDAO();
    LogDAO logdao = new LogDAO();
    OrderDAO odao = new OrderDAO();
    PanelDAO pandao = new PanelDAO();
    PatientDAO patdao = new PatientDAO();
    PreferencesDAO prefdao = new PreferencesDAO();
    ReflexMultichoiceDAO rmcdao = new ReflexMultichoiceDAO();
    ReflexRulesDAO rrdao = new ReflexRulesDAO();
    RemarkDAO rmdao = new RemarkDAO();
    ResultDAO rdao = new ResultDAO();
    ResultDetailsDAO rddao = new ResultDetailsDAO();
    TestDAO tdao = new TestDAO();
    TestLogDAO tlogdao = new TestLogDAO();
    
    Integer gfrIncompleteRemarkNo = prefdao.getInteger("GFRIncompleteRemark");
    String CalcDivideByZeroResult = prefdao.getString("CalculationDivideByZeroResult", "Indeterminate");
    Integer GFRwhiteTestNumber = prefdao.getInteger("GFRWhiteCalculationTestNumber");
    Integer GFRblackTestNumber = prefdao.getInteger("GFRBlackCalculationTestNumber");
    Integer GFRMinorTestNumber = prefdao.getInteger("GFRMinorCalculationTestNumber");
    // See if there are any custom calculations defined for the lab
    String customTestNumberCSV = prefdao.getString("CustomCalcTestNumberCSV");
    //Get ApproveOnPost Preference
    Boolean approveOnPost = prefdao.getBoolean("ApproveOnPost");
    String saltsSpices = prefdao.getString("SaltsAndSpices");
    Integer NegRemarkID = prefdao.getInteger("Negative");
    Integer PosRemarkID = prefdao.getInteger("Positive");
    // Get the preference to tell if we are going to check just by count
    Boolean blnCheckByCount = prefdao.getBoolean("TranslationalCheckByCount");
    
    Double GFRLeadingCoefficient = prefdao.getDouble("GFRLeadingCoefficient");
    Double GFRBlackMultiplier = prefdao.getDouble("GFRBlackMultiplier");
    Double GFRFemaleMultiplier = prefdao.getDouble("GFRFemaleMultiplier");
    Integer GFREquation = prefdao.getInteger("GFREquation");
    Boolean overlappingPanels = prefdao.getBoolean("OverlappingPanelsEnabled");

    public ResultPostBL() {   
    }

    public ArrayList<Results> PanelComplete(Results result) throws SQLException {
        try {
            if (result.getPanelId() != null) {
                ArrayList<Results> resultRows = new ArrayList<>();
                Tests panel = tdao.GetTestByID(result.getPanelId());
                Tests test = tdao.GetTestByID(result.getTestId());

                //First loop for normal panels
                ArrayList<Tests> subtestsArray = tdao.GetSubtestsIncludingInactive(panel);
                Tests[] subtests = subtestsArray.toArray(new Tests[subtestsArray.size()]);
                //Tests[] subtests = tdao.GetSubtests(panel.getNumber());
                int numReported = 0;

                ArrayList<Results> list = (ArrayList<Results>) rdao.GetReportedResultByOrderID(result.getOrderId());

                //Tests resultTest;
                for (Tests t : subtests) {
                    for (Results r : list) {
                        //resultTest = tdao.GetTestByID(r.getTestId());
                        //if (t.getNumber() == resultTest.getNumber())
                        //{
                        //    numReported++;
                        //}
                        //resultTest = null;

                        if (t.getIdtests() == r.getTestId()) {
                            numReported++;
                        }
                    }
                }

                if (numReported == subtests.length) {
                    int id = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getPanelId());
                    Results pHeader = rdao.GetResultByID(id);
                    pHeader.setDateReported(result.getDateReported());
                    pHeader.setReportedBy(result.getReportedBy());
                    rdao.UpdateResult(pHeader);
                    //Add First Panel
                    resultRows.add(pHeader);

                    //Second loop if it is a header test
                    if (test.getHeader()) {
                        ArrayList<Tests> subtests2Array = tdao.GetSubtestsIncludingInactive(test);
                        Tests[] subtests2 = subtests2Array.toArray(new Tests[subtests2Array.size()]);
                        //Tests[] subtests2 = tdao.GetSubtests(test.getNumber());
                        int numReported2 = 0;

                        ArrayList<Results> list2 = (ArrayList<Results>) rdao.GetReportedResultByOrderID(result.getOrderId());

                        for (Tests t : subtests) {
                            for (Results r : list2) {
                                //resultTest = tdao.GetTestByID(r.getTestId());
                                //if (t.getNumber() == resultTest.getNumber())
                                //{
                                //    numReported2++;
                                //}
                                //resultTest = null;

                                if (t.getIdtests() == r.getTestId()) {
                                    numReported2++;
                                }
                            }
                        }

                        if (numReported2 == subtests2.length) {
                            int id2 = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId());
                            Results pHeader2 = rdao.GetResultByID(id2);
                            pHeader2.setDateReported(result.getDateReported());
                            pHeader2.setReportedBy(result.getReportedBy());
                            rdao.UpdateResult(pHeader2);
                            resultRows.add(pHeader2);
                        }
                    }
                }

                return resultRows;
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Results> CreatePostedPanels(ArrayList<Integer> OrderIDs, Integer UserID, Boolean ApproveOnPost) throws SQLException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        ArrayList<Results> panelList = new ArrayList<>();
        ArrayList<Results> ordPanels = new ArrayList<>();

        for (Integer orderID : OrderIDs) {
            ordPanels = GetPanelsOnOrder(orderID);
            for (Results r : ordPanels) {
                System.out.println("ParentTest: " + r.getTestId());
                if (getIsResulted(r, UserID, ApproveOnPost)) {
                    if (ApproveOnPost == true) {
                        System.out.println("Mark as Posted & Approved: " + r.getTestId());
                        r.setIsApproved(true);
                        r.setApprovedBy(UserID);
                        r.setApprovedDate(new Date());
                    } else {
                        System.out.println("Mark as Posted: " + r.getTestId());
                    }
                    r.setReportedBy(UserID);
                    r.setDateReported(new Date());
                    boolean update = rdao.UpdateResult(r);
                    if (update == false) {
                        System.out.println("Unable to update parent panel " + r.getTestId() + " for Order " + r.getOrderId());
                    }
                }
            }

        }

        return panelList;
    }

    private boolean getIsResulted(Results parentResult, Integer UserId, Boolean ApproveOnPost) throws SQLException {
        Results result;
        ArrayDeque<Results> rqueue = new ArrayDeque<Results>();
        boolean isReported = true;

        ArrayList<Results> pRes = GetOrderedResultsUnderPanelHeaderShallow(parentResult.getOrderId(), parentResult.getTestId());
        for (Results r : pRes) {
            rqueue.addFirst(r);
        }

        while (rqueue.isEmpty() == false) {
            //Get the result
            result = rqueue.pop();
            System.out.println("\tTest: " + result.getTestId());
            //Is it reported?
            if (result.getDateReported() == null || (result.getReportedBy() == null || result.getReportedBy() == 0)) {
                //If not, is it a panel?
                if (pandao.CheckIfPanelHeader(result.getTestId())) {
                    System.out.println("\tPanel: " + result.getTestId());
                    //traverse it. Did the traverse come back true? 
                    if (getIsResulted(result, UserId, ApproveOnPost)) {
                        if (ApproveOnPost == true) {
                            // If yes, set it Reported & Approved
                            System.out.println("\tPanel: " + result.getTestId() + " Marked as posted & approved");
                            result.setIsApproved(true);
                            result.setApprovedBy(UserId);
                            result.setApprovedDate(new Date());
                        } else {
                            // If yes, set it Reported
                            System.out.println("\tPanel: " + result.getTestId() + " Marked as posted");
                        }

                        result.setReportedBy(UserId);
                        result.setDateReported(new Date());
                        boolean update = rdao.UpdateResult(result);
                        if (update == false) {
                            System.out.println("Unable to update panel " + result.getTestId() + " for Order " + result.getOrderId());
                            return false;
                        }
                    } else {
                        // If not, return false
                        isReported = false;
                    }

                } //If not, return false
                else {
                    isReported = false;
                }
            }
        }
        return isReported;
    }

    public ArrayList<Results> GetPanelsOnOrder(Integer OrderID) throws SQLException {
        Results res;
        ArrayList<Results> panelRes = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        final String query = "SELECT r.* "
                + "FROM `results` r "
                + "JOIN `tests` t ON r.`testId` = t.`idTests` "
                + "WHERE t.`testType` = 0 "
                + "AND r.`panelId` IS NULL "
                + "AND r.`reportedBy` IS NULL "
                + "AND r.`dateReported` IS NULL "
                + "AND r.`approvedDate` IS NULL "
                + "AND r.`invalidatedDate` IS NULL "
                + "AND r.`orderId` = " + OrderID;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

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

            panelRes.add(res);
        }

        rs.close();
        stmt.close();

        return panelRes;
    }

    public ArrayList<Results> GetOrderedResultsUnderPanelHeaderShallow(Integer OrderID, Integer PanelID) throws SQLException {
        Results res;
        ArrayList<Results> panelRes = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        final String query = "SELECT r.* "
                + "FROM `results` r "
                + "JOIN `tests` t ON r.`testId` = t.`idTests` "
                + "WHERE t.`header` != 1 "
                + "AND r.`panelId` = " + PanelID + " " +//"AND r.`panelId` = " + PanelID + " " +
                "AND r.`orderId` = " + OrderID;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

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

            panelRes.add(res);
        }

        rs.close();
        stmt.close();

        return panelRes;
    }

    public boolean UpdatePanelHeaders(int orderId, int userId) {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        if (orderId < 1 || userId < 1) {
            return false;
        }

        try {
            String sql = "CALL UpdatePanelHeaders(?,?,?)";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, orderId);
            callable.setInt(2, userId);
            callable.registerOutParameter(3, java.sql.Types.VARCHAR);

            callable.executeQuery();
            callable.close();
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::UpdatePanelHeaders:"
                    + ex.toString());
            return false;
        }
        return true;
    }

    public boolean UpdateBatteryHeaders(int orderId, int userId) {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        if (orderId < 1 || userId < 1) {
            return false;
        }

        try {
            String sql = "CALL UpdateBatteryHeaders(?,?,?)";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, orderId);
            callable.setInt(2, userId);
            callable.registerOutParameter(3, java.sql.Types.VARCHAR);

            callable.executeQuery();
            callable.close();
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::UpdateBatteryHeaders:"
                    + ex.toString());
            return false;
        }
        return true;
    }

    /**
     * *
     * Update panel and battery headers for an order. If deleteOptional is true,
     * any un-reported optional tests will be marked deleted when their
     * containing panel would otherwise be approved.
     *
     * @param orderId
     * @param userId
     * @param approveOnPost
     * @param approveOnly
     * @param isHeader
     * @param deleteOptional
     * @return affectedResultIds String
     */
    public HashMap<UpdateHeaderResult, String> UpdateHeaders(int orderId, int userId, boolean approveOnPost,
            boolean approveOnly, boolean isHeader, boolean deleteOptional) {
        HashMap<UpdateHeaderResult, String> output = new HashMap<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        String affectedResultIds = null;

        if (orderId < 1 || userId < 1) {
            return new HashMap<>();
        }

        try {
            String sql = "CALL UpdateHeaders(?,?,?,?,?,?,?,?)";
            CallableStatement callable = con.prepareCall(sql);
            callable.setInt(1, orderId);
            callable.setInt(2, userId);
            callable.setBoolean(3, approveOnPost);
            callable.setBoolean(4, approveOnly);
            callable.setBoolean(5, isHeader);
            callable.setBoolean(6, deleteOptional);
            callable.registerOutParameter(7, java.sql.Types.VARCHAR); // CSV ResultIds of panels that have been updated
            callable.registerOutParameter(8, java.sql.Types.VARCHAR); // CSV ResultIds of optional results that were marked deleted            

            callable.executeQuery();
            callable.close();

            output.put(UpdateHeaderResult.HeaderResultIdsUpdated, callable.getString(7));
            output.put(UpdateHeaderResult.DeletedOptionalResultIds, callable.getString(8));
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::UpdateHeaders:"
                    + ex.toString());
            return new HashMap();
        }
        return output;
    }

    /**
     * Returns a list of Tests objects using a CSV string of result Ids.
     *
     * @param resultIdCSV
     * @return
     * @throws NullPointerException
     * @throws SQLException
     */
    public List<Tests> GetTestsFromResultIdCSV(String resultIdCSV) throws NullPointerException, SQLException {
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        String sql = "SELECT t.* "
                + " FROM tests t "
                + " INNER JOIN results r ON t.idtests = r.testId "
                + " WHERE r.idresults IN (" + resultIdCSV + ")";

        PreparedStatement pStmt = con.prepareCall(sql);

        ResultSet rs = pStmt.executeQuery();

        ArrayList<Tests> tests = new ArrayList<>();
        while (rs.next()) {
            Tests test = new Tests();
            tests.add(tdao.SetTestFromResultSet(test, rs));
        }
        return tests;
    }

    /**
     * Runs procedure to update an order's panel headers and check its optional
     * test status. Returns a map representing the resultIds of the panels that
     * were reported and/or approved, and the resultIds of any optional tests
     * marked as deleted
     *
     * @param orderId
     * @param userId
     * @param approveOnPost
     * @param approveOnly
     * @return
     */
    public HashMap<UpdateHeaderResult, String> UpdatePanelAndBatteryHeaders(int orderId, int userId, boolean approveOnPost, boolean approveOnly, boolean deleteOptional) {
        // Check the panels first
        HashMap<UpdateHeaderResult, String> output = UpdateHeaders(orderId, userId, approveOnPost, approveOnly, false, deleteOptional);

        // Get the panels affected and/or the optional tests deleted
        if (output == null) {
            output = new HashMap<>();
        }
        String panelsAffected = output.get(UpdateHeaderResult.HeaderResultIdsUpdated);
        if (panelsAffected == null) {
            panelsAffected = "";
        }
        String optionalTestsDeleted = output.get(UpdateHeaderResult.DeletedOptionalResultIds);
        if (optionalTestsDeleted == null) {
            optionalTestsDeleted = "";
        }

        String panelsAffected2 = "";
        String optionalTestsDeleted2 = "";

        // Only check batteries if panels were closed out by the other call
        if (panelsAffected.length() > 0) {
            output = UpdateHeaders(orderId, userId, approveOnPost, approveOnly, true, deleteOptional);
            if (output == null) {
                output = new HashMap<>();
            }
            // Get the panels affected and/or the optional tests deleted
            panelsAffected2 = output.get(UpdateHeaderResult.HeaderResultIdsUpdated);
            if (panelsAffected2 == null) {
                panelsAffected2 = "";
            }
            optionalTestsDeleted2 = output.get(UpdateHeaderResult.DeletedOptionalResultIds);
            if (optionalTestsDeleted2 == null) {
                optionalTestsDeleted2 = "";
            }
        }

        // Add commas where necessary when combining lists
        if (panelsAffected.length() > 0 && panelsAffected2.length() > 0) {
            panelsAffected += ",";
        }
        if (optionalTestsDeleted.length() > 0 && optionalTestsDeleted2.length() > 0) {
            optionalTestsDeleted += ",";
        }

        String combinedAffectedPanels = panelsAffected + panelsAffected2;
        String combinedOptionalTestsDeleted = optionalTestsDeleted + optionalTestsDeleted2;

        // Return the combined results of the panels and battery runs
        HashMap<UpdateHeaderResult, String> combinedOutput = new HashMap<>();
        combinedOutput.put(UpdateHeaderResult.HeaderResultIdsUpdated, combinedAffectedPanels);
        combinedOutput.put(UpdateHeaderResult.DeletedOptionalResultIds, combinedOptionalTestsDeleted);

        return combinedOutput;
    }

    /**
     * Updates the panel and battery headers for an order Calls stored
     * procedures
     *
     * @param orderId
     * @param userId
     * @return
     */
    public boolean UpdateHeaders(int orderId, int userId) {
        boolean panelHeaderSuccess = UpdatePanelHeaders(orderId, userId);
        return (panelHeaderSuccess && UpdateBatteryHeaders(orderId, userId));
    }

    public ArrayList<Integer> PanelApproved(Results result) throws SQLException {
        try {
            if (result.getPanelId() != null) {
                ArrayList<Integer> resultRows = new ArrayList<Integer>();
                if (result.getPanelId() == null) {
                    return null;
                }
                Tests panel = tdao.GetTestByID(result.getPanelId());
                Tests test = tdao.GetTestByID(result.getTestId());

                //First loop for normal panels
                ArrayList<Tests> subtests = tdao.GetSubtestsIncludingInactive(panel);
                //Tests[] subtests = tdao.GetSubtests(panel.getNumber());
                int numReported = 0;

                ArrayList<Results> list = (ArrayList<Results>) rdao.GetApprovedResultByOrderID(result.getOrderId());

                //Tests resultTest;
                for (Tests t : subtests) {
                    for (Results r : list) {
                        //resultTest = tdao.GetTestByID(r.getTestId());
                        //if (t.getNumber() == resultTest.getNumber())
                        //{
                        //    numReported++;
                        //}
                        //resultTest = null;

                        if (t.getIdtests() == r.getTestId()) {
                            numReported++;
                        }
                    }
                }

                if (numReported == subtests.size()) {
                    int id = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getPanelId());
                    Results pHeader = rdao.GetResultByID(id);
                    pHeader.setApprovedDate(result.getApprovedDate());
                    pHeader.setApprovedBy(result.getApprovedBy());
                    pHeader.setIsApproved(true);
                    rdao.UpdateResult(pHeader);
                    //Add First Panel
                    resultRows.add(pHeader.getIdResults());

                    //Second loop if it is a header test
                    if (test.getHeader()) {
                        ArrayList<Tests> subtests2 = tdao.GetSubtestsIncludingInactive(test);
                        //Tests[] subtests2 = tdao.GetSubtests(test.getNumber());
                        int numReported2 = 0;

                        ArrayList<Results> list2 = (ArrayList<Results>) rdao.GetApprovedResultByOrderID(result.getOrderId());

                        for (Tests t : subtests) {
                            for (Results r : list2) {
                                //resultTest = tdao.GetTestByID(r.getTestId());
                                //if (t.getNumber() == resultTest.getNumber())
                                //{
                                //    numReported2++;
                                //}
                                //resultTest = null;
                                if (t.getIdtests() == r.getTestId()) {
                                    numReported2++;
                                }
                            }
                        }

                        if (numReported2 == subtests2.size()) {
                            int id2 = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId());
                            Results pHeader2 = rdao.GetResultByID(id2);
                            pHeader2.setApprovedDate(result.getApprovedDate());
                            pHeader2.setApprovedBy(result.getApprovedBy());
                            pHeader2.setIsApproved(true);
                            rdao.UpdateResult(pHeader2);
                            resultRows.add(pHeader2.getIdResults());
                        }
                    }
                }

                return resultRows;
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Integer> BatteryApproved(Results result) {

        java.util.Date date = new java.util.Date();
        ArrayList<Integer> batteryRows = new ArrayList<Integer>();
        ArrayList<Results> batteries = GetBatteriesOnOrder(result.getOrderId());

        //Now check each Battery to see if they are complete
        for (Results res : batteries) {
            //Get all Tests/Panels in Battery
            Tests[] components = tdao.GetSubtestsByHeaderID(res.getTestId());
            //variable to hold reported components
            int reported = 0;
            //ArrayList holding all reported results for this order
            ArrayList<Results> approvedResults
                    = (ArrayList<Results>) rdao.GetApprovedResultByOrderID(result.getOrderId());

            //Now loop through componenets and results to count reported components
            //Tests resultTest;
            for (Tests t : components) {
                for (Results r : approvedResults) {
                    // Need to compare by number, not by ID
                    //resultTest = tdao.GetTestByID(r.getTestId());                    
                    //if (t.getNumber() == resultTest.getNumber())
                    //{
                    //Found a reported component, increment reported count
                    //    reported++;                        
                    //}

                    if (t.getIdtests() == r.getTestId()) {
                        reported++;
                    }
                    //resultTest = null;
                }
            }

            //Now check to see if the number of reported components = the
            //total number of components in the Battery.  If so it is complete.
            if (reported == components.length) {
                try {
                    res.setApprovedBy(result.getApprovedBy());
                    res.setIsApproved(true);
                    res.setApprovedDate(date);

                    //Update/Post the Battery
                    rdao.UpdateResult(res);
                    //Add the idResults to ArrayList for Logging
                    batteryRows.add(res.getIdResults());
                } catch (SQLException sx) {
                    System.out.println("ResultPostBL::BatteryApproved - " + sx.toString());
                    return null;
                }
            }
        }

        return batteryRows;
    }

    public ArrayList<Integer> PanelReported(Results result, int user, Boolean approveOnPost) throws SQLException {
        try {
            if (result.getPanelId() != null) {
                ArrayList<Integer> resultRows = new ArrayList<Integer>();
                Tests panel = tdao.GetTestByID(result.getPanelId());
                Tests test = tdao.GetTestByID(result.getTestId());

                //First loop for normal panels
                ArrayList<Tests> subtests = tdao.GetSubtestsIncludingInactive(panel);
                //Tests[] subtests = tdao.GetSubtests(panel.getNumber());
                int numReported = 0;

                ArrayList<Results> list = (ArrayList<Results>) rdao.GetReportedResultByOrderID(result.getOrderId());

                //Tests resultTest;
                for (Tests t : subtests) {
                    for (Results r : list) {
                        //resultTest = tdao.GetTestByID(r.getTestId());
                        //if (t.getNumber() == resultTest.getNumber())
                        //{
                        //    numReported++;
                        //}
                        //resultTest = null;

                        if (t.getIdtests() == r.getTestId()) {
                            numReported++;
                        }
                    }
                }

                if (numReported == subtests.size()) {
                    int id = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getPanelId());
                    Results pHeader = rdao.GetResultByID(id);
                    pHeader.setDateReported(result.getDateReported());
                    pHeader.setReportedBy(result.getReportedBy());
                    if (approveOnPost == true) {
                        pHeader.setApprovedBy(user);
                        pHeader.setIsApproved(true);
                        pHeader.setApprovedDate(result.getDateReported());
                    }
                    rdao.UpdateResult(pHeader);
                    //Add First Panel
                    resultRows.add(pHeader.getIdResults());

                    //Second loop / check header if it is a header test
                    if (test.getHeader()) {
                        //Tests[] subtests2 = tdao.GetSubtests(test.getNumber());
                        ArrayList<Tests> subtests2 = tdao.GetSubtestsIncludingInactive(test);
                        int numReported2 = 0;

                        ArrayList<Results> list2 = (ArrayList<Results>) rdao.GetReportedResultByOrderID(result.getOrderId());

                        for (Tests t : subtests) {
                            for (Results r : list2) {
                                //resultTest = tdao.GetTestByID(r.getTestId());
                                //if (t.getNumber() == resultTest.getNumber())
                                //{
                                //    numReported2++;
                                //}
                                //resultTest = null;

                                if (t.getIdtests() == r.getTestId()) {
                                    numReported2++;
                                }
                            }
                        }

                        if (numReported2 == subtests2.size()) {
                            int id2 = rdao.GetResultIdByOrderIdTestId(result.getOrderId(), result.getTestId());
                            Results pHeader2 = rdao.GetResultByID(id2);
                            pHeader.setDateReported(result.getDateReported());
                            pHeader.setReportedBy(result.getReportedBy());
                            rdao.UpdateResult(pHeader2);
                            resultRows.add(pHeader2.getIdResults());
                        }
                    }
                }

                return resultRows;
            }
            return null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Integer> BatteryReported(Results result, int user, Boolean approveOnPost) {

        java.util.Date date = new java.util.Date();
        ArrayList<Integer> batteryRows = new ArrayList<Integer>();
        ArrayList<Results> batteries = GetBatteriesOnOrder(result.getOrderId());

        //Now check each Battery to see if they are complete
        for (Results res : batteries) {
            //Get all Tests/Panels in Battery
            Tests[] components = tdao.GetSubtestsByHeaderID(res.getTestId());
            //variable to hold reported components
            int reported = 0;
            //ArrayList holding all reported results for this order
            ArrayList<Results> reportedResults
                    = (ArrayList<Results>) rdao.GetReportedResultByOrderID(result.getOrderId());

            //Now loop through componenets and results to count reported components
            //Tests resultTest;
            for (Tests t : components) {
                for (Results r : reportedResults) {
                    // Need to compare by number, not by ID
                    //resultTest = tdao.GetTestByID(r.getTestId());                    
                    //if (t.getNumber() == resultTest.getNumber())
                    //{
                    //Found a reported component, increment reported count
                    //    reported++;                        
                    //}
                    //resultTest = null;

                    if (t.getIdtests() == r.getTestId()) {
                        //Found a reported component, increment reported count
                        reported++;
                    }
                }
            }

            //Now check to see if the number of reported components = the
            //total number of components in the Battery.  If so it is complete.
            if (reported == components.length) {
                try {
                    //Populate/Set the reported fields
                    res.setReportedBy(user);
                    res.setDateReported(date);
                    //Populate/Set the Approved fields if they ApproveOnPost
                    if (approveOnPost) {
                        res.setApprovedBy(user);
                        res.setIsApproved(true);
                        res.setApprovedDate(date);
                    }
                    //Update/Post the Battery
                    rdao.UpdateResult(res);
                    //Add the idResults to ArrayList for Logging
                    batteryRows.add(res.getIdResults());
                } catch (SQLException sx) {
                    System.out.println("ResultPostBL::BatteryReported - " + sx.toString());
                    return null;
                }
            }
        }

        return batteryRows;
    }

    /**
     * Checks if this result is part of a calculation. Very broad search for a
     * quick true/false
     *
     * @param result Result class object
     * @return boolean True if result is in any calculation, false if not.
     */
    public boolean IsResultInCalc(Results result) {
        try {
            //A calculation must be in a panel.  Automatically know it isn't 
            //part of a calc if not in a panel
            if (result.getPanelId() == null) {
                return false;
            }

            //Since it is part of a panel, now just a broad check to see if
            //any calculation uses this result
            if (calcdao.TestPartOfACalculation(result.getTestId()) == false) {
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    /**
     * Check to see if this panel test has a calculation
     *
     * @param test
     * @return
     */
    public boolean PanelHasCalc(int ID) {
        try {
            int test = tdao.GetTestNumberByID(ID);
            Tests[] subTests = tdao.GetSubtests(test);

            for (Tests t : subTests) {
                if (t.getTestType() == 2) {
                    return true;
                }
            }

            return false;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public boolean PanelHasUnresultedCalcs(int orderId, int panelId) {
        Results res;
        ArrayList<Results> panelRes = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        String sql = " SELECT COUNT(*) FROM results r "
                + " INNER JOIN tests t on r.testid = t.idtests "
                + " WHERE r.orderID = " + orderId
                + " AND t.testType = 2 "
                + " AND r.resultNo IS NULL "
                + " AND r.panelId = " + panelId;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return (count > 0);
        } catch (SQLException ex) {
            System.out.println("Unable to determine whether panel has unreported calculations");
            // Default to true if there's a problem; worst case
            // scenario we check for finished calculations when we don't need to.
            return true;
        }
    }

    public List<Results> GetCalculationResultsInOrder(int orderId) {
        List<Results> calcResultList = new ArrayList<>();
        Results res;
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        String sql = " SELECT r.* FROM results r "
                + " INNER JOIN tests t on r.testid = t.idtests "
                + " WHERE r.orderID = " + orderId
                + " AND t.testType = 2 ";

        try {
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

                calcResultList.add(res);
            }
        } catch (SQLException ex) {
            System.out.println("Exception while retrieving calculation results for order: " + ex.getMessage());
        }
        return calcResultList;
    }

    public Set<Integer> GetCalculationResultIDDependencies(int orderId, int testId, int panelId)
            throws SQLException {
        TestBL tbl = new TestBL();

        List<Integer> creatinineTestNumbers = tbl.GetGFRCreatinineTestNumbers();

        //Integer creatinineTestNumber = prefdao.getInteger("GFRCreatinineTestNumber");
        Set<Integer> dependResultIdSet = new HashSet<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);
        String sql;

        // get the results with creatinine test number if gfr
        if (IsGFRCalculation(testId)) {
            String testNumbers = "";
            for (Integer testNumber : creatinineTestNumbers) {
                if (testNumbers.isEmpty() == false) {
                    testNumbers += ",";
                }
                testNumbers += testNumber.toString();
            }

            sql = " SELECT idResults FROM results r "
                    + " INNER JOIN tests t on r.testid = t.idTests "
                    + " WHERE r.orderID = " + orderId
                    + " AND t.number in (" + testNumbers + ")"
                    + " AND r.panelId = " + panelId;
        } // get the results associated with the test in the calculations table
        else {
            // Joining on test number
            sql = "SELECT idResults FROM results r "
                    + " INNER JOIN tests t ON r.testid = t.idtests " //this is the test used by the calculation (e.g. Creatinine)
                    + " INNER JOIN tests t2 ON t2.number = t.number " // get all of the tests for this number
                    + " INNER JOIN calculations c ON t2.idtests = c.resultValueId "
                    + " WHERE r.orderID = " + orderId
                    + " AND c.idtests = " + testId
                    + " AND c.resultValueId IS NOT NULL "
                    + " AND r.panelId = " + panelId;
        }

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                dependResultIdSet.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println("Exception while retrieving calculation result id dependencies for order: " + ex.getMessage());
        }
        return dependResultIdSet;
    }

    public boolean IsGFRCalculation(int testId) throws SQLException {
        boolean isGFR = false;
        TestBL tbl = new TestBL();
        List<Integer> creatinineTestNumbers = tbl.GetGFRCreatinineTestNumbers();
        //Integer creatinineTestNumber = prefdao.getInteger("GFRCreatinineTestNumber");

        Tests test = tdao.GetTestByID(testId);

        // check if test number matches gfr white or gfr black
        if (GFRwhiteTestNumber != null
                && GFRblackTestNumber != null
                && creatinineTestNumbers != null
                && creatinineTestNumbers.size() > 0
                && (GFRwhiteTestNumber == test.getNumber()
                || GFRblackTestNumber == test.getNumber())) {
            isGFR = true;
        }
        return isGFR;
    }

    public ArrayList<Tests> GetUnresultedCalculationsInPanel(int orderId, int panelId) {
        ArrayList<Tests> testResults = new ArrayList<>();
        Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
        Connection con = dbs.getConnection(true);

        String sql = " SELECT testId FROM results r "
                + " INNER JOIN tests t on r.testid = t.idtests "
                + " WHERE r.orderID = " + orderId
                + " AND t.testType = 2 "
                + " AND r.resultNo IS NULL "
                + " AND r.panelId = " + panelId;

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int idtests = rs.getInt(1);
                testResults.add(tdao.GetTestByID(idtests));
            }
            return testResults;
        } catch (SQLException ex) {
            System.out.println("Unable to determine whether panel has unreported calculations");
            // Default to true if there's a problem; worst case
            // scenario we check for finished calculations when we don't need to.
            return new ArrayList<>();
        }
    }

    public ArrayList<Tests> GetCalculationsInPanel(Tests panel, Tests[] subtests) {
        try {
            ArrayList<Tests> calcs = new ArrayList<>();

            for (Tests t : subtests) {
                if (t.getTestType() == 2) {
                    calcs.add(t);
                }
            }

            return calcs;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Results> CalculationsComplete(int orderId, int panelId, int userId, boolean approveOnPost) {

        java.util.Date curDate = new java.util.Date();
        try {
            //if (result.getPanelId() != null)
            //{
            ArrayList<Results> resultRows = new ArrayList<>();

            ArrayList<Tests> calcs = GetUnresultedCalculationsInPanel(orderId, panelId);

            String CALCULATION = "";

            Set<Integer> customCalcTestNumbers = new HashSet<>();
            if (customTestNumberCSV != null && customTestNumberCSV.isEmpty() == false) {
                // Build a list of test #s used in custom calculations
                try {
                    String[] testNumbersString = customTestNumberCSV.split(",");

                    for (String testNumberString : testNumbersString) {
                        try {
                            customCalcTestNumbers.add(Integer.parseInt(testNumberString));
                        } catch (Exception ex) {
                            System.out.println("ResultPostBL::"
                                    + "Error parsing preference 'CustomCalcTestNumberCSV'"
                                    + ": this should be a comma-separated"
                                    + " list of integers representing the"
                                    + " custom test numbers");

                            if (ex.getMessage() != null) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("ResultPostBL::"
                            + "Error parsing preference 'CustomCalcTestNumberCSV'"
                            + ": this should be a comma-separated"
                            + " list of integers representing the"
                            + " custom test numbers");
                    if (ex.getMessage() != null) {
                        System.out.println(ex.getMessage());
                    }
                }
            }

            //Be prepared for more than one calculation in a panel
            for (Tests t : calcs) {
                    // Check for custom calc, otherwise check for GFR,
                // otherwise check for regular calculation.

                if (t == null || t.getIdtests() == null || t.getIdtests().equals(0)) {
                    System.out.println("ResultPostBL::CalculationsComplete:"
                            + " Retrieved calculations for orderId "
                            + String.valueOf(orderId) + " panelId "
                            + String.valueOf(panelId) + " had a test that"
                            + " had a [NULL] idTests field");
                    continue;
                }

                if (customCalcTestNumbers.isEmpty() == false && customCalcTestNumbers.contains(t.getNumber())) {
                    CallableStatement callable = null;
                    Results customCalcResult = null;
                    try {
                        customCalcResult = rdao.GetResultByOrderIdPanelIdTestId(
                                orderId,
                                panelId,
                                t.getIdtests());

                        if (customCalcResult == null || customCalcResult.getIdResults() == null
                                || customCalcResult.getIdResults() <= 0) {
                            throw new SQLException("ResultPostBL::CalculationsComplete:"
                                    + " Could not get result line for calculation. OrderId = " + String.valueOf(orderId)
                                    + " panelId " + String.valueOf(panelId));
                        }
                    } catch (SQLException ex) {
                        // todo log
                        System.out.println("ResultPostBL::CalculationsComplete: "
                                + "Could not retrieve result line for orderId " + String.valueOf(orderId)
                                + " panelId " + String.valueOf(panelId) + " calc testId " + String.valueOf(t.getIdtests()));
                        continue;
                    }

                    // Call the custom calculation stored procedure here
                    try {
                        String sql = "CALL css.CustomCalc(?,?,?,?,?,?,?,?)";

                        callable = con.prepareCall(sql);
                        callable.setString(1, ""); // Called from
                        callable.setInt(2, orderId);
                        callable.setInt(3, panelId);
                        callable.setInt(4, t.getIdtests());
                        callable.setInt(5, userId);
                        callable.registerOutParameter(6, java.sql.Types.DOUBLE);  // Result
                        callable.registerOutParameter(7, java.sql.Types.VARCHAR); // Error message(s)
                        callable.registerOutParameter(8, java.sql.Types.TINYINT); // Result finalized by proc

                        callable.executeQuery();
                        String errorMessages = callable.getString(7);

                        // No errors
                        if (errorMessages == null || errorMessages.isEmpty()) {
                            double result = callable.getDouble(6);
                            // do some checking on result
                            if (result <= 0) {
                                throw new Exception("blah");
                            }

                                // If the procedure is not finalizing the result,
                            // do that here.
                            if (callable.getBoolean(8) == false) {
                                    // Get the correct number of decimal positions based
                                // on the calculation test.
                                if (t.getNoRounding()) {
                                    result = NoRounding(result, t);
                                } else {
                                    result = round(result, t);
                                }

                                //Have the calculation complete, just need to post it, and add the id to the array
                                customCalcResult.setResultNo(result);
                                customCalcResult.setResultRemark(null);
                                customCalcResult.setResultText(result + "");
                                customCalcResult = SetResultAbnormalInformation(t, customCalcResult);

                                //Now add passed in values & update result row
                                customCalcResult.setDateReported(curDate);
                                customCalcResult.setReportedBy(userId);

                                if (approveOnPost) {
                                    customCalcResult.setIsApproved(true);
                                    customCalcResult.setApprovedDate(curDate);
                                    customCalcResult.setApprovedBy(userId);
                                }

                                rdao.UpdateResult(customCalcResult);
                                resultRows.add(customCalcResult);
                            }
                        } else {
                            throw new Exception("Custom calculation error response: " + errorMessages);
                        }
                    } catch (Exception ex) {
                        // todo: logging
                    } finally {
                        try {
                            if (callable != null) {
                                callable.close();
                            }
                        } catch (SQLException ex) {
                            System.out.println("ResultPostBL::CompleteCalcs:"
                                    + " Custom calc handling could not close"
                                    + " out callable statement");
                        }
                    }
                } else if (GFRwhiteTestNumber != null && GFRblackTestNumber != null
                        && (GFRwhiteTestNumber == t.getNumber()
                        || GFRblackTestNumber == t.getNumber())) {
                    // Grab the results line for this GFR test
                    int GFRTestID = t.getIdtests();
                    Results gfrResult = rdao.GetResultByOrderIdPanelIdTestId(
                            orderId,
                            panelId,
                            GFRTestID);

                    //Results gfrResult = rdao.GetResultByOrderIDTestID(result.getOrderId(), GFRTestID);
                    Double calcResult = GetGFRResultByResultID(gfrResult.getIdResults());

                    // if gfr calculation could not be completed, set incomplete remark if available
                    if (calcResult == null) {
                        if (gfrIncompleteRemarkNo != null) {
                            Remarks gfrIncompleteRemark = rmdao.GetRemark(gfrIncompleteRemarkNo);
                            if (gfrIncompleteRemark != null && gfrIncompleteRemark.getIdremarks() != 0) {
                                gfrResult.setResultRemark(gfrIncompleteRemark.getIdremarks());
                            }
                        }
                    } else {
                            // Get the correct number of decimal positions based
                        // on the calculation test.
                        if (t.getNoRounding()) {
                            calcResult = NoRounding(calcResult, t);
                        } else {
                            calcResult = round(calcResult, t);
                        }

                        //Have the calculation complete, just need to post it, and add the id to the array
                        gfrResult.setResultNo(calcResult);
                        gfrResult.setResultRemark(null);
                        gfrResult.setResultText(calcResult + "");
                        gfrResult = SetResultAbnormalInformation(t, gfrResult);
                    }
                    //Now add passed in values & update result row
                    gfrResult.setDateReported(curDate);
                    gfrResult.setReportedBy(userId);

                    if (approveOnPost) {
                        gfrResult.setIsApproved(true);
                        gfrResult.setApprovedDate(curDate);
                        gfrResult.setApprovedBy(userId);
                    }

                    rdao.UpdateResult(gfrResult);
                    resultRows.add(gfrResult);

                } else if (GFRMinorTestNumber != null && t.getNumber() == GFRMinorTestNumber) {

                    // Grab the results line for this GFR test
                    int GFRTestID = t.getIdtests();
                    Results gfrResult = rdao.GetResultByOrderIdPanelIdTestId(
                            orderId,
                            panelId,
                            GFRTestID);

                    //Results gfrResult = rdao.GetResultByOrderIDTestID(result.getOrderId(), GFRTestID);
                    Double calcResult = GetGFRMinorResultByResultID(gfrResult.getIdResults());

                    // if gfr calculation could not be completed, set incomplete remark if available
                    if (calcResult == null) {
                        
                        if (gfrIncompleteRemarkNo != null) {
                            Remarks gfrIncompleteRemark = rmdao.GetRemark(gfrIncompleteRemarkNo);
                            if (gfrIncompleteRemark != null && gfrIncompleteRemark.getIdremarks() != 0) {
                                gfrResult.setResultRemark(gfrIncompleteRemark.getIdremarks());
                            }
                        }
                    } else {
                            // Get the correct number of decimal positions based
                        // on the calculation test.
                        if (t.getNoRounding()) {
                            calcResult = NoRounding(calcResult, t);
                        } else {
                            calcResult = round(calcResult, t);
                        }

                        //Have the calculation complete, just need to post it, and add the id to the array
                        gfrResult.setResultNo(calcResult);
                        gfrResult.setResultRemark(null);
                        gfrResult.setResultText(calcResult + "");
                        gfrResult = SetResultAbnormalInformation(t, gfrResult);
                    }
                    //Now add passed in values & update result row
                    gfrResult.setDateReported(curDate);
                    gfrResult.setReportedBy(userId);

                    if (approveOnPost) {
                        gfrResult.setIsApproved(true);
                        gfrResult.setApprovedDate(curDate);
                        gfrResult.setApprovedBy(userId);
                    }

                    rdao.UpdateResult(gfrResult);
                    resultRows.add(gfrResult);

                } else // ==== NOT a GFR calculation; proceed normally =======
                {
                    //System.out.println("Starting calculation checks");
                    CALCULATION = "";
                    ArrayList<Results> resList = new ArrayList<>();
                    boolean complete = true;
                    boolean ready = true;
                    double calculatedResult = 0;
                    ArrayList<Calculations> steps = calcdao.GetCalculationsByTestID(t.getIdtests());

                        //System.out.println("found " + steps.size() + " steps");
                    //First loop to see if all test results are posted
                    for (Calculations c : steps) {
                        //See if this step is a test result
                        if (c.getResultValueId() != 0) {
                            Integer resultValueNumber = tdao.GetTestByID(c.getResultValueId()).getNumber();

                            // Get the test that sits under the same panel as the calc
                            Results t_res = rdao.GetResultByOrderIdPanelIdTestNumber(orderId, panelId, resultValueNumber);

                            if (t_res.getResultText() != null) {
                                //System.out.println("Adding result " + t_res.getIdResults() + " to list");
                                if (!resList.contains(t_res)) {
                                    resList.add(t_res);
                                }
                            } else // A required result value is null
                            {
                                complete = false;
                                break;
                            }

                            /*
                             else if (c.getNumericValue() > 0.0001)
                             {
                             System.out.println("Numeric value hit");
                             }
                             else
                             {
                             System.out.println("Complete set to false");
                             complete = false;
                             break;
                             }*/
                        }
                    }
                    if (complete) {
                            //Since all needed results are present, perform calculation
                        //Counts which step of equation you are on
                        int stepCount = 1;
                        //counter for the loop
                        int x = 0;
                        int totalSteps = steps.size();
                        Calculations cal = new Calculations();
                        double tempValue = 0;
                        String tempOper = "";
                        boolean done = false;
                        //Get proper step
                        while (!done) {
                            // Bounds check and exit if done.
                            if (steps.size() == x) {
                                break;
                            }

                            cal = steps.get(x);
                            if (cal.getStep() == stepCount) {
                                if (cal.getResultValueId() == 0 && cal.getNumericValue() == 0) {
                                    switch (cal.getOperator().charAt(0)) {
                                        case '?':
                                            CALCULATION += "Math.sqrt(";
                                            break;
                                        case 'M':
                                            CALCULATION += "Math.pow(";
                                            break;
                                        case ',':
                                            CALCULATION += ",";
                                            break;
                                        default:
                                            CALCULATION += cal.getOperator().charAt(0);
                                            break;
                                    }

                                    stepCount++;
                                    //System.out.println("ResultValueID and NumericValue zero");
                                } else if (cal.getResultValueId() != 0) {//If this step uses a result value get it
                                    //System.out.println("Found resultvalueID");
                                    Integer requiredByCalcTestNumber = tdao.GetTestByID(cal.getResultValueId()).getNumber();
                                    for (Results r : resList) {
                                        Integer resultTestNumber = tdao.GetTestByID(r.getTestId()).getNumber();

                                        if (resultTestNumber.equals(requiredByCalcTestNumber)) //if (r.getTestId() == cal.getResultValueId())
                                        {
                                            //System.out.println("Matched testId " + r.getTestId() + " to result value ID");
                                            if (stepCount == 1) {
                                                    //tempValue = r.getResultNo();
                                                //tempOper = cal.getOperator();
                                                CALCULATION += "" + r.getResultNo() + "" + cal.getOperator().charAt(0);
                                                stepCount++;
                                                System.out.println(CALCULATION);
                                            } else {
                                                //switch(tempOper.charAt(0)){
                                                if (cal.getOperator() == null || cal.getOperator().equals("")) {
                                                    CALCULATION += "" + r.getResultNo();
                                                    //System.out.println(CALCULATION);
                                                } else {
                                                    switch (cal.getOperator().charAt(0)) {
                                                        case '+':
                                                            //    tempValue = tempValue + r.getResultNo();
                                                            CALCULATION += r.getResultNo() + "+";
                                                            break;
                                                        case '-':
                                                            //    tempValue = tempValue - r.getResultNo();
                                                            CALCULATION += r.getResultNo() + "-";
                                                            break;
                                                        case '/':
                                                            //    tempValue = tempValue / r.getResultNo();
                                                            CALCULATION += r.getResultNo() + "/";
                                                            break;
                                                        case '*':
                                                            //    tempValue = tempValue * r.getResultNo();
                                                            CALCULATION += r.getResultNo() + "*";
                                                            break;
                                                        case '(':
                                                            CALCULATION += r.getResultNo() + " ( ";
                                                            break;
                                                        case ')':
                                                            CALCULATION += r.getResultNo() + " ) ";
                                                            break;
                                                        case 'M':
                                                            CALCULATION += r.getResultNo() + "Math.pow(";
                                                            break;
                                                        case '?':
                                                            CALCULATION += r.getResultNo() + "Math.sqrt(";
                                                            break;
                                                        case ',':
                                                            CALCULATION += r.getResultNo() + ",";
                                                            break;
                                                        default:
                                                            CALCULATION += "" + r.getResultNo();
                                                            break;
                                                            //case '=':
                                                        //    tempValue = calculatedResult;
                                                        //    CALCULATION += tempValue + " + ";
                                                        //    break;
                                                        //case '(':
                                                        //    CALCULATION += tempValue + " ( ";
                                                        //    break;
                                                        //case ')':
                                                        //    CALCULATION += tempValue + " ) ";
                                                        //    break;
                                                        }
                                                    //System.out.println(CALCULATION);
                                                }
                                                    //Get operator for next pass
                                                //tempOper = cal.getOperator();
                                                //increment to next step for calculation
                                                stepCount++;
                                                if (stepCount > totalSteps) {
                                                    //calculatedResult = tempValue;
                                                    done = true;
                                                }
                                            }
                                        }
                                    }
                                } else if (cal.getNumericValue() != 0) { //If this step uses a flat numeric value
                                    //System.out.println("Found numeric value");
                                    if (stepCount == 1) {
                                            //tempValue = cal.getNumericValue();
                                        //tempOper = cal.getOperator();
                                        CALCULATION += "" + cal.getNumericValue() + "" + cal.getOperator().charAt(0);
                                        stepCount++;
                                        //System.out.println(CALCULATION);
                                    } else {
                                        //switch (tempOper.charAt(0)) {
                                        if (cal.getOperator() == null || cal.getOperator().equals("")) {
                                            CALCULATION += "" + cal.getNumericValue();
                                            //System.out.println(CALCULATION);
                                        } else {
                                            switch (cal.getOperator().charAt(0)) {
                                                case '+':
                                                    //    tempValue = tempValue + cal.getNumericValue();
                                                    CALCULATION += cal.getNumericValue() + "+";
                                                    break;
                                                case '-':
                                                    //    tempValue = tempValue - cal.getNumericValue();
                                                    CALCULATION += cal.getNumericValue() + "-";
                                                    break;
                                                case '/':
                                                    //    tempValue = tempValue / cal.getNumericValue();
                                                    CALCULATION += cal.getNumericValue() + "/";
                                                    break;
                                                case '*':
                                                    //    tempValue = tempValue * cal.getNumericValue();
                                                    CALCULATION += cal.getNumericValue() + "*";
                                                    break;
                                                case '(':
                                                    CALCULATION += cal.getNumericValue() + " ( ";
                                                    break;
                                                case ')':
                                                    CALCULATION += cal.getNumericValue() + " ) ";
                                                    break;
                                                case 'M':
                                                    CALCULATION += cal.getNumericValue() + "Math.pow(";
                                                    break;
                                                case '?':
                                                    CALCULATION += cal.getNumericValue() + "Math.sqrt(";
                                                    break;
                                                case ',':
                                                    CALCULATION += cal.getNumericValue() + ",";
                                                    break;
                                                default:
                                                    CALCULATION += "" + cal.getNumericValue();
                                                    break;
                                                    //case '=':
                                                //    tempValue = calculatedResult;
                                                //    CALCULATION += tempValue + " = ";
                                                //    break;
                                                //case '(':
                                                //    CALCULATION += tempValue + " ( ";
                                                //    break;
                                                //case ')':
                                                //    CALCULATION += tempValue + " ) ";
                                                //    break;
                                                }
                                            //System.out.println(CALCULATION);
                                        }
                                            //Get operator for next pass
                                        //tempOper = cal.getOperator();
                                        //increment to next step for calculation
                                        stepCount++;
                                        if (stepCount > totalSteps) {
                                            //calculatedResult = tempValue;
                                            done = true;
                                        }
                                    }
                                }
                            } else {
                                //Increment until you find the right step
                                x++;
                            }
                        }

                            //System.out.println("Done with calc: " + CALCULATION);
                        //New Bean Shell Evaluation of expression
                        if (INTERPRETER == null) {
                            INTERPRETER = new Interpreter();
                        }
                        
                        String calc_res_str = INTERPRETER.eval(CALCULATION).toString();
                        
                        Results m_res = rdao.GetResultByOrderIdTestNumber(orderId, t.getNumber());
                        
                        if(calc_res_str.contains("Infinity")){
                            m_res.setResultText(CalcDivideByZeroResult);
                        }
                        else {
                            double calc_res = Double.parseDouble(calc_res_str);
                            calculatedResult = round(calc_res, t);
                            m_res.setResultNo(calculatedResult);
                            m_res.setResultText(calculatedResult + "");
                        }
                        
                        
                            //Have the calculation complete, just need to post it, and add the id to the array

                        
                            //Results m_res = rdao.GetResultByOrderIDTestID(result.getOrderId(), t.getIdtests());

                        //System.out.println("Grabbed result " + m_res.getIdResults());
                        
                        m_res = SetResultAbnormalInformation(t, m_res);

                        //Now add passed in values & update result row
                        m_res.setDateReported(curDate);
                        m_res.setReportedBy(userId);

                        if (approveOnPost) {
                            m_res.setIsApproved(true);
                            m_res.setApprovedDate(curDate);
                            m_res.setApprovedBy(userId);
                        }

                        rdao.UpdateResult(m_res);

                        resultRows.add(m_res);
                    }
                }
            }
            return resultRows;
            //}
            //return null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public ArrayList<Results> CalculationsComplete(Results result, java.util.Date curDate, int user, boolean approveOnPost) {
        // This should compare test numbers for everything (where
        //  tests are not deactivated).

        try {
            if (result.getPanelId() != null) {
                ArrayList<Results> resultRows = new ArrayList<>();
                Tests panel = tdao.GetTestByID(result.getPanelId());
                // Tests[] subtests = tdao.GetSubtests(panel.getNumber());
                ArrayList<Tests> calcs = GetUnresultedCalculationsInPanel(result.getOrderId(), panel.getIdtests());
                //ArrayList<Tests> calcs = GetCalculationsInPanel(panel, subtests);
                String CALCULATION = "";

                // See if there are any custom calculations defined for the lab
                Set<Integer> customCalcTestNumbers = new HashSet<>();
                if (customTestNumberCSV != null && customTestNumberCSV.isEmpty() == false) {
                    // Build a list of test #s used in custom calculations
                    try {
                        String[] testNumbersString = customTestNumberCSV.split(",");

                        for (String testNumberString : testNumbersString) {
                            try {
                                customCalcTestNumbers.add(Integer.parseInt(testNumberString));
                            } catch (Exception ex) {
                                System.out.println("ResultPostBL::"
                                        + "Error parsing preference 'CustomCalcTestNumberCSV'"
                                        + ": this should be a comma-separated"
                                        + " list of integers representing the"
                                        + " custom test numbers");

                                if (ex.getMessage() != null) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("ResultPostBL::"
                                + "Error parsing preference 'CustomCalcTestNumberCSV'"
                                + ": this should be a comma-separated"
                                + " list of integers representing the"
                                + " custom test numbers");
                        if (ex.getMessage() != null) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }

                //Be prepared for more than one calculation in a panel
                for (Tests t : calcs) {
                    // Check for custom calc, otherwise check for GFR,
                    // otherwise check for regular calculation.

                    if (t == null || t.getIdtests() == null || t.getIdtests().equals(0)) {
                        System.out.println("ResultPostBL::CalculationsComplete:"
                                + " Retrieved calculations for resultId "
                                + String.valueOf(result.getIdResults())
                                + " had a [NULL] idTests field");
                        continue;
                    }

                    if (customCalcTestNumbers.isEmpty() == false && customCalcTestNumbers.contains(t.getNumber())) {
                        CallableStatement callable = null;

                        Results calcResult = rdao.GetResultByOrderIdPanelIdTestId(
                                result.getOrderId(),
                                result.getPanelId(),
                                t.getIdtests());

                        // Call the custom calculation stored procedure here
                        try {
                            String sql = "CALL css.CustomCalc(?,?,?,?,?,?,?,?)";

                            callable = con.prepareCall(sql);
                            callable.setString(1, ""); // Called from
                            callable.setInt(2, result.getOrderId());
                            callable.setInt(3, result.getPanelId());
                            callable.setInt(4, t.getIdtests());
                            callable.setInt(5, user);
                            callable.registerOutParameter(6, java.sql.Types.DOUBLE);  // Result
                            callable.registerOutParameter(7, java.sql.Types.VARCHAR); // Error message(s)
                            callable.registerOutParameter(8, java.sql.Types.TINYINT); // Result finalized by proc

                            callable.executeQuery();
                            String errorMessages = callable.getString(7);

                            // No errors
                            if (errorMessages == null || errorMessages.isEmpty()) {
                                double resultNo = callable.getDouble(6);
                                // do some checking on result
                                if (resultNo <= 0) {
                                    throw new Exception("Result returned from calculation was <= 0");
                                }

                                // If the procedure is not finalizing the result,
                                // do that here.
                                if (callable.getBoolean(8) == false) {
                                    // Get the correct number of decimal positions based
                                    // on the calculation test.
                                    if (t.getNoRounding()) {
                                        resultNo = NoRounding(resultNo, t);
                                    } else {
                                        resultNo = round(resultNo, t);
                                    }

                                    //Have the calculation complete, just need to post it, and add the id to the array
                                    calcResult.setResultNo(resultNo);
                                    calcResult.setResultRemark(null);
                                    calcResult.setResultText(resultNo + "");
                                    calcResult = SetResultAbnormalInformation(t, calcResult);

                                    //Now add passed in values & update result row
                                    calcResult.setDateReported(curDate);
                                    calcResult.setReportedBy(user);

                                    if (approveOnPost) {
                                        calcResult.setIsApproved(true);
                                        calcResult.setApprovedDate(curDate);
                                        calcResult.setApprovedBy(user);
                                    }

                                    rdao.UpdateResult(calcResult);
                                    resultRows.add(calcResult);
                                }
                            } else {
                                throw new Exception("Couldn't perform custom calc: " + errorMessages);
                            }
                        } catch (Exception ex) {
                            String calculationTestId = (t.getIdtests() != null ? t.getIdtests().toString() : "[NULL]");

                            String errorMessage = "ResultPostBL::CalculationComplete(Results,java.util.Date,int,boolean)"
                                    + " Encountered error processing custom calc test id " + calculationTestId
                                    + " while checking testId " + String.valueOf(result.getTestId());
                            System.out.println(errorMessage + (ex.getMessage() != null ? " " + ex.getMessage() : ""));
                        } finally {
                            try {
                                if (callable != null) {
                                    callable.close();
                                }
                            } catch (SQLException ex) {
                                System.out.println("ResultPostBL::CompleteCalcs:"
                                        + " Custom calc handling could not close"
                                        + " out callable statement");
                            }
                        }
                    } else if (GFRwhiteTestNumber != null && GFRblackTestNumber != null
                            && (GFRwhiteTestNumber == t.getNumber()
                            || GFRblackTestNumber == t.getNumber())) {
                        // Grab the results line for this GFR test
                        int GFRTestID = t.getIdtests();
                        Results gfrResult = rdao.GetResultByOrderIdPanelIdTestId(
                                result.getOrderId(),
                                result.getPanelId(),
                                GFRTestID);

                        //Results gfrResult = rdao.GetResultByOrderIDTestID(result.getOrderId(), GFRTestID);
                        Double calcResult = GetGFRResultByResultID(gfrResult.getIdResults());

                        // if gfr calculation could not be completed, set incomplete remark if available
                        if (calcResult == null) {
                            if (gfrIncompleteRemarkNo != null) {
                                Remarks gfrIncompleteRemark = rmdao.GetRemark(gfrIncompleteRemarkNo);
                                if (gfrIncompleteRemark != null && gfrIncompleteRemark.getIdremarks() != 0) {
                                    gfrResult.setResultRemark(gfrIncompleteRemark.getIdremarks());
                                }
                            }
                        } else {
                            // Get the correct number of decimal positions based
                            // on the calculation test.
                            if (t.getNoRounding()) {
                                calcResult = NoRounding(calcResult, t);
                            } else {
                                calcResult = round(calcResult, t);
                            }

                            //Have the calculation complete, just need to post it, and add the id to the array
                            gfrResult.setResultNo(calcResult);
                            gfrResult.setResultRemark(null);
                            gfrResult.setResultText(calcResult + "");
                            gfrResult = SetResultAbnormalInformation(t, gfrResult);
                        }
                        //Now add passed in values & update result row
                        gfrResult.setDateReported(curDate);
                        gfrResult.setReportedBy(user);

                        if (approveOnPost) {
                            gfrResult.setIsApproved(true);
                            gfrResult.setApprovedDate(curDate);
                            gfrResult.setApprovedBy(user);
                        }

                        rdao.UpdateResult(gfrResult);
                        resultRows.add(gfrResult);

                    } 
                    // making a separate condition,to make sure the calculation works for
                    //  GFRMinorTestNumber even if GFRwhiteTestNumber or GFRblacktest number is not set up                     
                    else if (GFRMinorTestNumber != null && t.getNumber() == GFRMinorTestNumber) {

                        // Grab the results line for this GFR test
                        int GFRTestID = t.getIdtests();
                        Results gfrResult = rdao.GetResultByOrderIdPanelIdTestId(
                                result.getOrderId(),
                                result.getPanelId(),
                                GFRTestID);

                        //Results gfrResult = rdao.GetResultByOrderIDTestID(result.getOrderId(), GFRTestID);
                        Double calcResult = GetGFRMinorResultByResultID(gfrResult.getIdResults());

                        // if gfr calculation could not be completed, set incomplete remark if available
                        if (calcResult == null) {
                            if (gfrIncompleteRemarkNo != null) {
                                Remarks gfrIncompleteRemark = rmdao.GetRemark(gfrIncompleteRemarkNo);
                                if (gfrIncompleteRemark != null && gfrIncompleteRemark.getIdremarks() != 0) {
                                    gfrResult.setResultRemark(gfrIncompleteRemark.getIdremarks());
                                }
                            }
                        } else {
                            // Get the correct number of decimal positions based
                            // on the calculation test.
                            if (t.getNoRounding()) {
                                calcResult = NoRounding(calcResult, t);
                            } else {
                                calcResult = round(calcResult, t);
                            }

                            //Have the calculation complete, just need to post it, and add the id to the array
                            gfrResult.setResultNo(calcResult);
                            gfrResult.setResultRemark(null);
                            gfrResult.setResultText(calcResult + "");
                            gfrResult = SetResultAbnormalInformation(t, gfrResult);
                        }
                        //Now add passed in values & update result row
                        gfrResult.setDateReported(curDate);
                        gfrResult.setReportedBy(user);

                        if (approveOnPost) {
                            gfrResult.setIsApproved(true);
                            gfrResult.setApprovedDate(curDate);
                            gfrResult.setApprovedBy(user);
                        }

                        rdao.UpdateResult(gfrResult);
                        resultRows.add(gfrResult);

                    } else // ==== NOT a GFR calculation; proceed normally =======
                    {
                        //System.out.println("Starting calculation checks");
                        CALCULATION = "";
                        ArrayList<Results> resList = new ArrayList<>();
                        boolean complete = true;
                        boolean ready = true;
                        double calculatedResult = 0;
                        ArrayList<Calculations> steps = calcdao.GetCalculationsByTestID(t.getIdtests());

                        //System.out.println("found " + steps.size() + " steps");
                        //First loop to see if all test results are posted
                        for (Calculations c : steps) {
                            //See if this step is a test result
                            if (c.getResultValueId() != 0) {
                                Integer resultValueNumber = tdao.GetTestByID(c.getResultValueId()).getNumber();

                                // Get the test that sits under the same panel as the calc
                                Results t_res = rdao.GetResultByOrderIdPanelIdTestNumber(result.getOrderId(), result.getPanelId(), resultValueNumber);

                                if (t_res.getResultText() != null) {
                                    //System.out.println("Adding result " + t_res.getIdResults() + " to list");
                                    if (!resList.contains(t_res)) {
                                        resList.add(t_res);
                                    }
                                } else // A required result value is null
                                {
                                    complete = false;
                                    break;
                                }

                                /*
                                 else if (c.getNumericValue() > 0.0001)
                                 {
                                 System.out.println("Numeric value hit");
                                 }
                                 else
                                 {
                                 System.out.println("Complete set to false");
                                 complete = false;
                                 break;
                                 }*/
                            }
                        }
                        if (complete) {
                            //Since all needed results are present, perform calculation
                            //Counts which step of equation you are on
                            int stepCount = 1;
                            //counter for the loop
                            int x = 0;
                            int totalSteps = steps.size();
                            Calculations cal = new Calculations();
                            double tempValue = 0;
                            String tempOper = "";
                            boolean done = false;
                            //Get proper step
                            while (!done) {
                                // Bounds check and exit if done.
                                if (steps.size() == x) {
                                    break;
                                }

                                cal = steps.get(x);
                                if (cal.getStep() == stepCount) {
                                    if (cal.getResultValueId() == 0 && cal.getNumericValue() == 0) {
                                        switch (cal.getOperator().charAt(0)) {
                                            case '?':
                                                CALCULATION += "Math.sqrt(";
                                                break;
                                            case 'M':
                                                CALCULATION += "Math.pow(";
                                                break;
                                            case ',':
                                                CALCULATION += ",";
                                                break;
                                            default:
                                                CALCULATION += cal.getOperator().charAt(0);
                                                break;
                                        }

                                        stepCount++;
                                        //System.out.println("ResultValueID and NumericValue zero");
                                    } else if (cal.getResultValueId() != 0) {//If this step uses a result value get it
                                        //System.out.println("Found resultvalueID");
                                        Integer requiredByCalcTestNumber = tdao.GetTestByID(cal.getResultValueId()).getNumber();
                                        for (Results r : resList) {
                                            Integer resultTestNumber = tdao.GetTestByID(r.getTestId()).getNumber();

                                            if (resultTestNumber.equals(requiredByCalcTestNumber)) //if (r.getTestId() == cal.getResultValueId())
                                            {
                                                //System.out.println("Matched testId " + r.getTestId() + " to result value ID");
                                                if (stepCount == 1) {
                                                    //tempValue = r.getResultNo();
                                                    //tempOper = cal.getOperator();
                                                    CALCULATION += "" + r.getResultNo() + "" + cal.getOperator().charAt(0);
                                                    stepCount++;
                                                    System.out.println(CALCULATION);
                                                } else {
                                                    //switch(tempOper.charAt(0)){
                                                    if (cal.getOperator() == null || cal.getOperator().equals("")) {
                                                        CALCULATION += "" + r.getResultNo();
                                                        //System.out.println(CALCULATION);
                                                    } else {
                                                        switch (cal.getOperator().charAt(0)) {
                                                            case '+':
                                                                //    tempValue = tempValue + r.getResultNo();
                                                                CALCULATION += r.getResultNo() + "+";
                                                                break;
                                                            case '-':
                                                                //    tempValue = tempValue - r.getResultNo();
                                                                CALCULATION += r.getResultNo() + "-";
                                                                break;
                                                            case '/':
                                                                //    tempValue = tempValue / r.getResultNo();
                                                                CALCULATION += r.getResultNo() + "/";
                                                                break;
                                                            case '*':
                                                                //    tempValue = tempValue * r.getResultNo();
                                                                CALCULATION += r.getResultNo() + "*";
                                                                break;
                                                            case '(':
                                                                CALCULATION += r.getResultNo() + " ( ";
                                                                break;
                                                            case ')':
                                                                CALCULATION += r.getResultNo() + " ) ";
                                                                break;
                                                            case 'M':
                                                                CALCULATION += r.getResultNo() + "Math.pow(";
                                                                break;
                                                            case '?':
                                                                CALCULATION += r.getResultNo() + "Math.sqrt(";
                                                                break;
                                                            case ',':
                                                                CALCULATION += r.getResultNo() + ",";
                                                                break;
                                                            default:
                                                                CALCULATION += "" + r.getResultNo();
                                                                break;
                                                            //case '=':
                                                            //    tempValue = calculatedResult;
                                                            //    CALCULATION += tempValue + " + ";
                                                            //    break;
                                                            //case '(':
                                                            //    CALCULATION += tempValue + " ( ";
                                                            //    break;
                                                            //case ')':
                                                            //    CALCULATION += tempValue + " ) ";
                                                            //    break;
                                                        }
                                                        //System.out.println(CALCULATION);
                                                    }
                                                    //Get operator for next pass
                                                    //tempOper = cal.getOperator();
                                                    //increment to next step for calculation
                                                    stepCount++;
                                                    if (stepCount > totalSteps) {
                                                        //calculatedResult = tempValue;
                                                        done = true;
                                                    }
                                                }
                                            }
                                        }
                                    } else if (cal.getNumericValue() != 0) { //If this step uses a flat numeric value
                                        //System.out.println("Found numeric value");
                                        if (stepCount == 1) {
                                            //tempValue = cal.getNumericValue();
                                            //tempOper = cal.getOperator();
                                            CALCULATION += "" + cal.getNumericValue() + "" + cal.getOperator().charAt(0);
                                            stepCount++;
                                            //System.out.println(CALCULATION);
                                        } else {
                                            //switch (tempOper.charAt(0)) {
                                            if (cal.getOperator() == null || cal.getOperator().equals("")) {
                                                CALCULATION += "" + cal.getNumericValue();
                                                //System.out.println(CALCULATION);
                                            } else {
                                                switch (cal.getOperator().charAt(0)) {
                                                    case '+':
                                                        //    tempValue = tempValue + cal.getNumericValue();
                                                        CALCULATION += cal.getNumericValue() + "+";
                                                        break;
                                                    case '-':
                                                        //    tempValue = tempValue - cal.getNumericValue();
                                                        CALCULATION += cal.getNumericValue() + "-";
                                                        break;
                                                    case '/':
                                                        //    tempValue = tempValue / cal.getNumericValue();
                                                        CALCULATION += cal.getNumericValue() + "/";
                                                        break;
                                                    case '*':
                                                        //    tempValue = tempValue * cal.getNumericValue();
                                                        CALCULATION += cal.getNumericValue() + "*";
                                                        break;
                                                    case '(':
                                                        CALCULATION += cal.getNumericValue() + " ( ";
                                                        break;
                                                    case ')':
                                                        CALCULATION += cal.getNumericValue() + " ) ";
                                                        break;
                                                    case 'M':
                                                        CALCULATION += cal.getNumericValue() + "Math.pow(";
                                                        break;
                                                    case '?':
                                                        CALCULATION += cal.getNumericValue() + "Math.sqrt(";
                                                        break;
                                                    case ',':
                                                        CALCULATION += cal.getNumericValue() + ",";
                                                        break;
                                                    default:
                                                        CALCULATION += "" + cal.getNumericValue();
                                                        break;
                                                    //case '=':
                                                    //    tempValue = calculatedResult;
                                                    //    CALCULATION += tempValue + " = ";
                                                    //    break;
                                                    //case '(':
                                                    //    CALCULATION += tempValue + " ( ";
                                                    //    break;
                                                    //case ')':
                                                    //    CALCULATION += tempValue + " ) ";
                                                    //    break;
                                                }
                                                //System.out.println(CALCULATION);
                                            }
                                            //Get operator for next pass
                                            //tempOper = cal.getOperator();
                                            //increment to next step for calculation
                                            stepCount++;
                                            if (stepCount > totalSteps) {
                                                //calculatedResult = tempValue;
                                                done = true;
                                            }
                                        }
                                    }
                                } else {
                                    //Increment until you find the right step
                                    x++;
                                }
                            }

                            //System.out.println("Done with calc: " + CALCULATION);
                            //New Bean Shell Evaluation of expression
                            if (INTERPRETER == null) {
                                INTERPRETER = new Interpreter();
                            }
                            
                            String calc_res_str = INTERPRETER.eval(CALCULATION).toString();
                            
                            Results m_res = rdao.GetResultByOrderIdTestNumber(result.getOrderId(), t.getNumber());
                            
                            if(calc_res_str.contains("Infinity")) {
                                m_res.setResultText(CalcDivideByZeroResult);
                            }
                            else {
                                double calc_res = Double.parseDouble(calc_res_str);
                                // Round the calculated result based on calculation test setting
                                if (t.getNoRounding()) {
                                    calculatedResult = NoRounding(calc_res, t);
                                } else {
                                    calculatedResult = round(calc_res, t);
                                }
                                m_res.setResultNo(calculatedResult);
                                m_res.setResultText(calculatedResult + "");
                            }

                            m_res = SetResultAbnormalInformation(t, m_res);

                            //Now add passed in values & update result row
                            m_res.setDateReported(curDate);
                            m_res.setReportedBy(user);

                            if (approveOnPost) {
                                m_res.setIsApproved(true);
                                m_res.setApprovedDate(curDate);
                                m_res.setApprovedBy(user);
                            }

                            rdao.UpdateResult(m_res);

                            resultRows.add(m_res);

                        }
                    }
                }

                return resultRows;
            }

            return null;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    //See if calculations can be performed, if so perform return.
    public ArrayList<Results> CalculationsComplete(Results result, java.util.Date curDate, int user) {
        return CalculationsComplete(result, curDate, user, false);
    }

    /**
     * Rounds a result number to the decimal position in the Tests Object
     *
     * @param result double result value
     * @param test Tests class object
     * @return double
     */
    public double round(double result, Tests test) {
        int decimals = test.getDecimalPositions();

        double multipicationFactor = Math.pow(10, decimals);
        double interestedInZeroDPs = result * multipicationFactor;
        return Math.round(interestedInZeroDPs) / multipicationFactor;
    }

    /**
     * This method just drops extra decimal positions instead of rounding. For
     * instance if needed to 2 decimal positions 5.56889 would return 5.56
     *
     * @param result The double value being cut
     * @param test The Tests object this result belongs to
     * @return double value of converted result(double)
     */
    public double NoRounding(double result, Tests test) {
        int decimals = test.getDecimalPositions();
        double finalRes = 0;

        switch (decimals) {
            case 0:
                int x = (int) result;
                finalRes = x;
                break;
            case 1:
                DecimalFormat decFormat = new DecimalFormat("#.0");
                decFormat.setRoundingMode(RoundingMode.DOWN);
                String converted = decFormat.format(result);
                try {
                    finalRes = Double.parseDouble(converted);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 2:
                DecimalFormat decFormat2 = new DecimalFormat("#.00");
                decFormat2.setRoundingMode(RoundingMode.DOWN);
                String converted2 = decFormat2.format(result);
                try {
                    finalRes = Double.parseDouble(converted2);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 3:
                DecimalFormat decFormat3 = new DecimalFormat("#.000");
                decFormat3.setRoundingMode(RoundingMode.DOWN);
                String converted3 = decFormat3.format(result);
                try {
                    finalRes = Double.parseDouble(converted3);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 4:
                DecimalFormat decFormat4 = new DecimalFormat("#.0000");
                decFormat4.setRoundingMode(RoundingMode.DOWN);
                String converted4 = decFormat4.format(result);
                try {
                    finalRes = Double.parseDouble(converted4);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 5:
                DecimalFormat decFormat5 = new DecimalFormat("#.00000");
                decFormat5.setRoundingMode(RoundingMode.DOWN);
                String converted5 = decFormat5.format(result);
                try {
                    finalRes = Double.parseDouble(converted5);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 6:
                DecimalFormat decFormat6 = new DecimalFormat("#.000000");
                decFormat6.setRoundingMode(RoundingMode.DOWN);
                String converted6 = decFormat6.format(result);
                try {
                    finalRes = Double.parseDouble(converted6);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 7:
                DecimalFormat decFormat7 = new DecimalFormat("#.0000000");
                decFormat7.setRoundingMode(RoundingMode.DOWN);
                String converted7 = decFormat7.format(result);
                try {
                    finalRes = Double.parseDouble(converted7);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 8:
                DecimalFormat decFormat8 = new DecimalFormat("#.00000000");
                decFormat8.setRoundingMode(RoundingMode.DOWN);
                String converted8 = decFormat8.format(result);
                try {
                    finalRes = Double.parseDouble(converted8);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 9:
                DecimalFormat decFormat9 = new DecimalFormat("#.000000000");
                decFormat9.setRoundingMode(RoundingMode.DOWN);
                String converted9 = decFormat9.format(result);
                try {
                    finalRes = Double.parseDouble(converted9);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            case 10:
                DecimalFormat decFormat10 = new DecimalFormat("#.0000000000");
                decFormat10.setRoundingMode(RoundingMode.DOWN);
                String converted10 = decFormat10.format(result);
                try {
                    finalRes = Double.parseDouble(converted10);
                } catch (Exception ex) {
                    System.out.println("Exception parsing Double: " + result + " - "
                            + ex.toString());
                }
                break;
            default:
                finalRes = result;
                break;
        }

        return finalRes;
    }

    /**
     * Returns a numeric operator such as >,< >= etc. from a string
     * representation of a double result value.
     *
     * @param result String representation of double numeric result value
     * @return String of preceeding signs
     */
    public String GetPreceedingSign(String result) {
        int i = 0;
        while (!Character.isDigit(result.charAt(i))) {
            i++;
        }
        if (i == 0) {
            return null;
        } else if (result.substring(0, i).matches("[<>=]+")) {
            return result.substring(0, i);
        } else {
            return null;
        }
    }

    /**
     * Formats a numeric result according to it's assigned decimal positions and
     * to it's Rounding type.
     *
     * @param test Tests object the represents what the result is for
     * @param numResult String value of the numeric result.(It may contain
     * operators)
     * @return Double value of the Formatted Result.
     */
    public Double FormatNumericResult(Tests test, String numResult) {

        Double result = null;

        if (numResult != null && numResult.trim().isEmpty() == false && test.getResultType().equals("Numeric")) {

            String strippedNum = numResult.replaceAll("[<>=]", "");
            int requiredPositions = test.getDecimalPositions();
            int decimalPlaces = 0;
            if (strippedNum.indexOf('.') > 0) {
                int integerPlaces = strippedNum.indexOf('.');
                decimalPlaces = strippedNum.length() - integerPlaces - 1;
            }

            if (decimalPlaces > requiredPositions) {
                if (test.getNoRounding()) {
                    try {
                        Double res = Double.parseDouble(strippedNum);
                        res = NoRounding(res, test);
                        result = res;
                    } catch (NumberFormatException ex) {
                        System.out.println("Exception Stripping Result::ResulPostBL - " + ex.toString());
                        return null;
                    }
                } else {
                    try {
                        Double res = Double.parseDouble(strippedNum);
                        res = round(res, test);
                        result = res;
                    } catch (NumberFormatException ex) {
                        System.out.println("Exception Rounding Result::ResulPostBL - " + ex.toString());
                        return null;
                    }
                }
            } else {
                Double res = Double.parseDouble(strippedNum);
                result = res;
            }
        }

        return result;
    }

    /**
     * Attempts to get the number of digits after the decimal point Zero
     * otherwise
     *
     * @param numberText
     * @return
     */
    private Integer GetDecimalPlaces(String numberText) {
        Integer decimalPlaces = 0;
        if (numberText.indexOf('.') > 0) {
            int integerPlaces = numberText.indexOf('.');
            decimalPlaces = numberText.length() - integerPlaces - 1;
        }
        return decimalPlaces;
    }

    /**
     * Attempts to get the number of trailing zeros in a string-formatted double
     * value.
     *
     * @param numberText
     * @return
     */
    private Integer GetTrailingZeroes(String numberText) {
        Integer trailingZeroesCount = null;
        boolean allZeros = true;
        Integer decimalIndex = numberText.indexOf('.');
        if (decimalIndex != null) {
            String decimals = numberText.substring(decimalIndex + 1);
            char[] decArry = decimals.toCharArray();
            int lastNonZero = 0;
            Integer firstZero = null;
            int cnt = 1;

            for (char c : decArry) {
                if (c == '0') {
                    if (firstZero == null) {
                        firstZero = cnt;
                    }
                } else {
                    lastNonZero = cnt;
                    firstZero = null;
                    allZeros = false;
                }
                cnt++;
            }

            if (firstZero != null) {
                trailingZeroesCount = (GetDecimalPlaces(numberText) - firstZero) + 1;
            }
        }
        return trailingZeroesCount;
    }

    /**
     * Formats a numeric result according to its assigned decimal positions and
     * rounding type.
     *
     * @param test Tests object the represents what the result is for
     * @param numResult String value of the numeric result.(It may contain
     * operators)
     * @return String value of the Formatted Result.
     */
    public String FormatNumericTextResult(Tests test, String numResult) {

        Double result = null;
        String str_res = null;
        boolean allZeros = true;

        if (test.getResultType().equals("Numeric")) {

            String strippedNum = numResult.replaceAll("[<>=]", "");
            int requiredPositions = test.getDecimalPositions();
            int decimalPlaces = 0;
            Integer trailingZerosCount = null;

            //Get Decimal Position Information
            if (strippedNum.indexOf('.') > 0) {
                int integerPlaces = strippedNum.indexOf('.');
                decimalPlaces = strippedNum.length() - integerPlaces - 1;

                //Get trailing zero information
                String decimals = strippedNum.substring(integerPlaces + 1);
                char[] decArry = decimals.toCharArray();
                int lastNonZero = 0;
                Integer firstZero = null;
                int cnt = 1;

                for (char c : decArry) {
                    if (c == '0') {
                        if (firstZero == null) {
                            firstZero = cnt;
                        }
                    } else {
                        lastNonZero = cnt;
                        firstZero = null;
                        allZeros = false;
                    }
                    cnt++;
                }

                if (firstZero != null) {
                    trailingZerosCount = (decimalPlaces - firstZero) + 1;
                }

            }

            if (decimalPlaces > requiredPositions) {
                if (test.getNoRounding()) {
                    try {
                        Double res = Double.parseDouble(strippedNum);
                        res = NoRounding(res, test);
                        result = res;
                        str_res = String.valueOf(res);

                        // Added to remove the ".0" from the resultText
                        // if no decimal positions are required
                        if (requiredPositions == 0) {
                            Integer decimalIndex = str_res.indexOf('.');
                            if (decimalIndex > -1) {
                                str_res = str_res.substring(0, decimalIndex);
                            }
                        }

                        if (trailingZerosCount != null) {
                            int decDifference = decimalPlaces - requiredPositions;
                            if (decDifference < trailingZerosCount) {
                                String newRes = String.valueOf(result);
                                int intPlaces = newRes.indexOf('.');
                                int decPlaces = newRes.length() - intPlaces - 1;
                                if (decPlaces < requiredPositions) {
                                    //int toAdd = requiredPositions - decPlaces;
                                    trailingZerosCount = (allZeros) ? trailingZerosCount - 1 : trailingZerosCount;
                                    for (int i = 0; i < requiredPositions - decPlaces && i < trailingZerosCount; i++) {
                                        newRes = newRes + "0";
                                    }
                                }

                                str_res = newRes;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Exception Stripping Result::ResulPostBL - " + ex.toString());
                        return null;
                    }
                } else // Result number does not exceed max decimal positions
                {
                    try {
                        Double res = Double.parseDouble(strippedNum);
                        res = round(res, test);
                        result = res;
                        str_res = String.valueOf(res);

                        // Added to remove the ".0" from the resultText
                        // if no decimal positions are required
                        if (requiredPositions == 0) {
                            Integer decimalIndex = str_res.indexOf('.');
                            if (decimalIndex > -1) {
                                str_res = str_res.substring(0, decimalIndex);
                            }
                        }

                        if (trailingZerosCount != null) {
                            int decDifference = decimalPlaces - requiredPositions;
                            if (decDifference < trailingZerosCount) {
                                String newRes = String.valueOf(result);
                                int intPlaces = newRes.indexOf('.');
                                int decPlaces = newRes.length() - intPlaces - 1;
                                if (decPlaces < requiredPositions) {
                                    trailingZerosCount = (allZeros) ? trailingZerosCount - 1 : trailingZerosCount;
                                    for (int i = 0; i < requiredPositions - decPlaces && i < trailingZerosCount; i++) {
                                        newRes = newRes + "0";
                                    }
                                }

                                str_res = newRes;
                            }
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Exception Rounding Result::ResulPostBL - " + ex.toString());
                        return null;
                    }
                }
            } else {
                Double res = Double.parseDouble(strippedNum);
                result = res;

                if (trailingZerosCount != null) {

                    String newRes = String.valueOf(result);
                    int intPlaces = newRes.indexOf('.');
                    int decPlaces = (newRes.length() - intPlaces) - 1;
                    if (decPlaces < decimalPlaces) {
                        trailingZerosCount = (allZeros) ? trailingZerosCount - 1 : trailingZerosCount;
                        for (int i = 0; i < requiredPositions - decPlaces && i < trailingZerosCount; i++) {
                            newRes = newRes + "0";
                        }
                    }
                    str_res = newRes;
                } else {

                    if (strippedNum.charAt(strippedNum.length() - 1) == '.') {
                        String strippedAll = strippedNum.replace(".", "");
                        str_res = strippedAll;
                    } else {
                        str_res = strippedNum;
                    }
                }
            }

            // Pad out the text to the required number of decimal places
            // -----------------------------------------------------------------
            if (requiredPositions > 0) {
                int decimalIndex = str_res.indexOf(".");

                // Add decimal point if it does not exist
                if (decimalIndex == -1) {
                    decimalIndex = str_res.length();
                    str_res += ".";
                }

                // Grab text after decimal
                String decimalCharacters = str_res.substring(decimalIndex + 1, str_res.length());

                // If it's less than the number required, pad
                int zerosToPad = requiredPositions - decimalCharacters.length();
                for (int i = 0; i < zerosToPad; i++) {
                    str_res += "0";
                }
            }
            // -----------------------------------------------------------------
        }

        return str_res;
    }

    /**
     * Set all the abnormal flags on a Results class object based on the Test
     * Object.
     *
     * @param test Tests object for which the Results is for
     * @param result Results object populated with all result values
     * @return Results object with boolean values set for abnormal flags.
     */
    public Results SetResultAbnormalInformation(Tests test, Results result) {
        
        if (test.getResultType().equals("Numeric")) {
            double LowNormal = 0;
            double HighNormal = 0;
            double AlertLow = 0;
            double AlertHigh = 0;
            double CriticalLow = 0;
            double CriticalHigh = 0;

            try {
                // These values should match
                if (!test.getIdtests().equals(result.getTestId())) {
                    ResultPostLog rpl = new ResultPostLog();
                    rpl.setAction("Test Notification");
                    rpl.setDescription("T/R abnormal info mismatch - testid:" + test.getIdtests() + " resultId:" + result.getIdResults());
                    rpl.setCreatedDate(new java.util.Date());
                    logdao.InsertLog(LogDAO.LogTable.ResultPost, rpl);
                }
            } catch (Exception ex) {
                System.out.println("ResultPostBL::SetResultAbnormalInformation: Error checking abnormal test match");
            }

            try {
                Tests temp = tdao.GetTestByID(result.getTestId());
                if (temp != null && temp.getIdtests() != null && temp.getIdtests() != 0) {
                    test = temp;
                }
            } catch (Exception ex) {
                System.out.println("ResultPostBL::SetResultAbnormalInformation: Error loading test from result object");
            }
            
            // get the order for the client id and set the client custom ranges if they exist
            try {
                Orders resultOrder = odao.GetOrderById(result.getOrderId());
                if (resultOrder != null && resultOrder.getIdOrders() != null && resultOrder.getIdOrders().intValue() != 0)
                {
                    ClientCustomRanges ccr = ClientCustomRangesDAO.get(resultOrder.getClientId(), test.getNumber());
                    if (ccr != null)
                    {
                        if (ccr.getLowNormal() != null) test.setLowNormal(ccr.getLowNormal());
                        if (ccr.getHighNormal() != null) test.setHighNormal(ccr.getHighNormal());
                        if (ccr.getAlertLow() != null) test.setAlertLow(ccr.getAlertLow());
                        if (ccr.getAlertHigh() != null) test.setAlertHigh(ccr.getAlertHigh());
                        if (ccr.getCriticalLow() != null) test.setCriticalLow(ccr.getCriticalLow());
                        if (ccr.getCriticalHigh() != null) test.setCriticalHigh(ccr.getCriticalHigh());
                    }
                }
            } catch (Exception ex) {
                System.out.println("ResultPostBL::SetResultAbnormalInformation: Error loading order and client custom ranges from result object");
            }

            if (test.getExtraNormals()) {
                try {
                    // Get all extranormal rows for the test
                    Orders ord = odao.GetOrderById(result.getOrderId());
                    Patients pat = patdao.GetPatientById(ord.getPatientId());
                    String sex = "null";
                        if (pat.getSex().toUpperCase().startsWith("M")) {
                            sex = "Male";
                        } else if (pat.getSex().toUpperCase().startsWith("F")) {
                            sex = "Female";
                        }
                    ArrayList<ExtraNormals> extraNormals = endao.GetNormalsFromStats(test.getIdtests(), pat.getDob(), ord.getSpecimenDate(), sex);//xnmDAO.GetNormalsByTestID(test.getIdtests());
                    if (extraNormals == null) {
                        extraNormals = new ArrayList<>();
                    }

                    // There are extra normals, so grab patient demographics
                    if (extraNormals.isEmpty() == false) {
                        for (ExtraNormals extraNormal : extraNormals) {
                            int extraNormalType = extraNormal.getType();
                            if (extraNormalType >= 1 && extraNormalType <= 3) {
                                    LowNormal = extraNormal.getLowNormal();
                                    HighNormal = extraNormal.getHighNormal();
                                    AlertLow = extraNormal.getAlertLow();
                                    AlertHigh = extraNormal.getAlertHigh();
                                    CriticalLow = extraNormal.getCriticalLow();
                                    CriticalHigh = extraNormal.getCriticalHigh();
                                    break;
                                
                            }
                            else // Error State ================================
                            {
                                int userId = Preferences.userRoot().getInt("id", 0);
                                SysLogDAO.Add(userId, "Extra normal type was not 1, 2, or 3", "testId "
                                        + test.getIdtests() + " orderid " + result.getOrderId());
                            }
                        } // Extra normal loop
                    } else // No extra normals. Use regular test ranges.
                    {
                        LowNormal = test.getLowNormal();
                        HighNormal = test.getHighNormal();
                        AlertLow = test.getAlertLow();
                        AlertHigh = test.getAlertHigh();
                        CriticalLow = test.getCriticalLow();
                        CriticalHigh = test.getCriticalHigh();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ResultPostBL.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.toString());
                }
            } else {
                LowNormal = test.getLowNormal();
                HighNormal = test.getHighNormal();
                AlertLow = test.getAlertLow();
                AlertHigh = test.getAlertHigh();
                CriticalLow = test.getCriticalLow();
                CriticalHigh = test.getCriticalHigh();
            }

            result.setIsAbnormal(false);
            result.setIsCIDHigh(false);
            result.setIsHigh(false);
            result.setIsCIDLow(false);
            result.setIsLow(false);

            if (result.getResultNo() < LowNormal) {
                result.setIsLow(true);
                result.setIsAbnormal(true);
                result.setIsHigh(false);
                result.setIsCIDLow(false);
                result.setIsCIDHigh(false);
                //Check Extened Options
                if (test.getLowRemark() != null && test.getLowRemark() != 0) {
                    result.setResultRemark(test.getLowRemark());
                }
            }

            if (result.getResultNo() > HighNormal) {
                result.setIsHigh(true);
                result.setIsAbnormal(true);
                result.setIsLow(false);
                result.setIsCIDLow(false);
                result.setIsCIDHigh(false);
                //Check Extened Options
                if (test.getHighRemark() != null && test.getHighRemark() != 0) {
                    result.setResultRemark(test.getHighRemark());
                }
            }

            if (result.getResultNo() < CriticalLow) {
                result.setIsCIDLow(true);
                result.setIsAbnormal(true);
                result.setIsHigh(false);
                result.setIsLow(false);
                result.setIsCIDHigh(false);
                //Check Extened Options
                if (test.getCritLowRemark() != null && test.getCritLowRemark() != 0) {
                    result.setResultRemark(test.getCritLowRemark());
                }
            }

            if (result.getResultNo() > CriticalHigh) {
                result.setIsCIDHigh(true);
                result.setIsAbnormal(true);
                result.setIsHigh(false);
                result.setIsCIDLow(false);
                result.setIsHigh(false);
                //Check Extened Options
                if (test.getCritHighRemark() != null && test.getCritHighRemark() != 0) {
                    result.setResultRemark(test.getCritHighRemark());
                }
            }

            if (result.getResultNo() >= LowNormal && result.getResultNo() <= HighNormal
                    && result.getResultNo() >= CriticalLow && result.getResultNo() <= CriticalHigh) {
                result.setIsCIDHigh(false);
                result.setIsAbnormal(false);
                result.setIsHigh(false);
                result.setIsCIDLow(false);
                result.setIsLow(false);
                //Check Extended Options
                if (test.getNormalRemark() != null && test.getNormalRemark() != 0) {
                    result.setResultRemark(test.getNormalRemark());
                }
            }

            //Check if the result is beyond the Min and Max reportable range
            if (test.getMinReportable() != null && result.getResultNo() < test.getMinReportable()) {
                String strippedNum = test.getMaxLowResult().replaceAll("[<>=]", "");
                double numRes = FormatNumericResult(test, test.getMaxLowResult());
                String preceedingSigns = "";
                //Get any preceeding signs / operators
                if (test.getMaxLowResult().length() > strippedNum.length()) {
                    int i = 0;
                    while (!Character.isDigit(test.getMaxLowResult().charAt(i))) {
                        i++;
                    }
                    if (i > 0) {
                        preceedingSigns = test.getMaxLowResult().substring(0, i);
                    }
                }
                //Set the default result values
                result.setResultNo(numRes);

                if (test.getMaxLowResult() != null && test.getMaxLowResult().isEmpty() == false) {
                    result.setResultText(test.getMaxLowResult());
                } else {
                    result.setResultText(preceedingSigns + numRes);
                }
            }

            if (test.getMaxReportable() != null && result.getResultNo() > test.getMaxReportable()) {
                String strippedNum = "";// = test.getMaxHighResult().replaceAll("[<>=]", "");
                try {
                    strippedNum = test.getMaxHighResult().replaceAll("[<>=]", "");
                } catch (Exception ex) {
                    System.out.println("Could not parse max high result value for test " + test.getIdtests().toString());
                }
                //String strippedNum = test.getMaxHighResult().replaceAll("[<>=]", "");
                double numRes = FormatNumericResult(test, test.getMaxHighResult());
                String preceedingSigns = null;
                //Get any preceeding signs / operators
                if (test.getMaxHighResult().length() > strippedNum.length()) {
                    int i = 0;
                    while (!Character.isDigit(test.getMaxHighResult().charAt(i))) {
                        i++;
                    }
                    if (i > 0) {
                        preceedingSigns = test.getMaxHighResult().substring(0, i);
                    }
                }
                //Set the default result values
                result.setResultNo(numRes);

                if (test.getMaxHighResult() != null && test.getMaxHighResult().isEmpty() == false) {
                    result.setResultText(test.getMaxHighResult());
                } else {
                    result.setResultText(preceedingSigns + numRes);
                }
            }

            //Now Check to see if we need to clear out results based on test info
            if (test.getLowShowNumeric() == false && result.getIsLow() == true) {
                result.setResultNo(null);
                result.setResultText(null);
            }
            if (test.getHighShowNumeric() == false && result.getIsHigh() == true) {
                result.setResultNo(null);
                result.setResultText(null);
            }
            if (test.getCritLowShowNumeric() == false && result.getIsCIDLow() == true) {
                result.setResultNo(null);
                result.setResultText(null);
            }
            if (test.getCritHighShowNumeric() == false && result.getIsCIDHigh() == true) {
                result.setResultNo(null);
                result.setResultText(null);
            }
            //Check if the result is normal
            if (test.getNormalShowNumeric() == false && result.getIsAbnormal() == false) {
                result.setResultNo(null);
                result.setResultText(null);
            }

            // Check if a remark attached to this result is abnormal
            if (result.getResultRemark() != null && result.getResultRemark() > 0) {
                try {
                    Remarks remark = rmdao.GetRemarkFromID(result.getResultRemark());
                    if (remark == null || remark.getIdremarks() == null || remark.getIdremarks() <= 0) {
                        throw new SQLException("Remark object returned"
                                + " was [NULL] or invalid");
                    }

                    if (remark.getIsAbnormal()) {
                        result.setIsAbnormal(true);
                    }
                } catch (Exception ex) {
                    String resultIdStr = "[NULL]";
                    String orderIdStr = "[NULL";
                    if (result.getIdResults() != null && result.getIdResults() > 0) {
                        resultIdStr = result.getIdResults().toString();
                    }

                    if (result.getOrderId() > 0) {
                        orderIdStr = String.valueOf(result.getOrderId());
                    }

                    String errorMessage = "ResultPostBL::SetResultAbnormalInformation:"
                            + " Exception while checking remark "
                            + result.getResultRemark().toString()
                            + " for abnormal status. ResultId " + resultIdStr
                            + " OrderId " + orderIdStr + ".";

                    System.out.println(errorMessage + "   " + ex.getMessage());
                    SysLogDAO.Add(1, errorMessage, ex.getMessage());
                }
            }

            return result;
        }

        return result;
    }

    /**
     * New Method for Posting/Approving Panel and Battery headers on post. This
     * is directly taken from the SupportSuite that always fixes the panel
     * headers.
     *
     * @param orderId The Integer orderid that is to be scanned for any
     * panel/battery updates.
     * @param user The Users that is running the post
     * @param approveOnPost Boolean value of the corresponding preference
     * @return
     */
    public ArrayList<Integer> PostPendingHeaders(Integer orderId, int user, Boolean approveOnPost) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Integer> results = new ArrayList<>();
            ArrayList<Integer> batteries = new ArrayList<>();
            Results res = new Results();
            java.util.Date date = new java.util.Date();
            ArrayList<Integer> batteryHeaders = new ArrayList<>();

            String select = "SELECT DISTINCT r.idresults, r.orderid , r.panelid, r.testid, t.name, t.number, t.header, r.dateReported, r.approvedDate, r.created "
                    + "FROM results r "
                    + "LEFT JOIN tests t on r.testid = t.idtests "
                    + "WHERE (r.dateReported IS NULL OR r.approvedDate IS NULL) "
                    + "AND r.orderid = " + orderId + " "
                    + "AND t.testType = 0";

            PreparedStatement pStmt = con.prepareStatement(select);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {

                //If it is a battery header hold it until after all other panels
                //are gone through
                if (rs.getBoolean("header") == true) {
                    batteryHeaders.add(rs.getInt("r.idresults"));
                    continue;
                }

                //Now go through looking at panels only
                int unreported = FindUnreportedSubtests(rs.getInt("orderid"),
                        rs.getInt("testid"));

                if (unreported > 0) {

                } else {
                    int unapproved = FindUnapprovedSubtests(rs.getInt("orderid"),
                            rs.getInt("testid"));

                    if (unapproved > 0) {

                    } else {
                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(user);
                        }
                        if (approveOnPost == true) {
                            res.setIsApproved(true);
                            res.setApprovedDate(date);
                            res.setApprovedBy(user);
                        }

                        rdao.UpdateResult(res);

                        results.add(res.getIdResults());
                    }
                }
            }

            rs.close();
            pStmt.close();

            //Now Go though and check the saved Battery headers
            batteries = PostPendingBatteries(batteryHeaders, user, approveOnPost);

            for (Integer bat : batteries) {
                results.add(bat);
            }

            return results;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                System.out.println(stack.toString());
            }
            return null;
        }
    }

    public ArrayList<Integer> PostPendingBatteries(ArrayList<Integer> idResults, int user, Boolean approveOnPost) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Integer> results = new ArrayList<>();
            Results res = new Results();
            java.util.Date date = new java.util.Date();

            String select = "SELECT DISTINCT r.idresults, r.orderid , r.panelid, r.testid, t.name, t.number, t.header, r.dateReported, r.approvedDate, r.created "
                    + "FROM results r "
                    + "LEFT JOIN tests t on r.testid = t.idtests "
                    //+ "WHERE (r.dateReported IS NULL OR r.approvedDate IS NULL) "
                    + "WHERE r.dateReported IS NULL "
                    + "AND r.idresults IN (" + IntArrayToInClause(idResults) + ") "
                    + "AND t.testType = 0 "
                    + "AND t.header = true";

            PreparedStatement pStmt = con.prepareStatement(select);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {

                //Now go through looking at panels only
                int unreported = FindUnreportedSubtests(rs.getInt("orderid"),
                        rs.getInt("panelid"));

                if (unreported > 0) {

                } else {
                    int unapproved = FindUnapprovedSubtests(rs.getInt("orderid"),
                            rs.getInt("panelid"));

                    if (unapproved > 0) {

                    } else {
                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(user);
                        }
                        if (approveOnPost == true) {
                            res.setIsApproved(true);
                            res.setApprovedDate(date);
                            res.setApprovedBy(user);
                        }

                        rdao.UpdateResult(res);

                        results.add(res.getIdResults());
                    }
                }
            }

            rs.close();
            pStmt.close();

            return results;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                System.out.println(stack.toString());
            }
            return null;
        }
    }

    /**
     * Scans the results for Panel and Battery Headers that have been not marked
     * as reported or approved for some reason, and updates them all. This is
     * done by a month and year. Used by the SupportSuite.
     *
     * @param month 1-12
     * @param year 2013, 2014, 2015, etc.
     * @return ArrayList<Integer> or all idresults updated.
     */
    public ArrayList<Integer> CleanPendingHeaders(int month, int year) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Integer> results = new ArrayList<>();
            ArrayList<Integer> batteries = new ArrayList<>();
            Results res = new Results();
            java.util.Date date = new java.util.Date();
            ArrayList<Integer> batteryHeaders = new ArrayList<>();

            if (approveOnPost == null) {
                approveOnPost = false;
            }

            String select = "SELECT DISTINCT r.idresults, r.orderid , r.panelid, r.testid, t.name, t.number, t.header, r.dateReported, r.approvedDate, r.created "
                    + "FROM results r "
                    + "LEFT JOIN tests t on r.testid = t.idtests "
                    + "WHERE (r.dateReported IS NULL OR r.approvedDate IS NULL) "
                    + "AND r.created " + Convert.BetweenMonthWhere(month, year) + " "
                    + "AND t.testType = 0";

            PreparedStatement pStmt = con.prepareStatement(select);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {

                //If it is a battery header hold it until after all other panels
                //are gone through
                if (rs.getBoolean("header") == true) {
                    batteryHeaders.add(rs.getInt("r.idresults"));
                    continue;
                }

                //Now go through looking at panels only
                int unreported = FindUnreportedSubtests(rs.getInt("orderid"),
                        rs.getInt("testid"));

                if (unreported > 0) {
                    System.out.println("Order ID: " + rs.getInt("orderid")
                            + " - Still has pending results");
                } else {
                    int unapproved = FindUnapprovedSubtests(rs.getInt("orderid"),
                            rs.getInt("testid"));

                    if (approveOnPost == false && unapproved > 0) {
                        System.out.println("Order ID: " + rs.getInt("orderid")
                                + " - Still has unapproved results");

                        //Just set the reported flag, not the approved flag
                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(2);
                        }

                        rdao.UpdateResult(res);
                        System.out.println("Result ID: " + rs.getInt("idresults")
                                + " has been updated! Posted but NOT Approved.");
                        results.add(res.getIdResults());
                    } else {
                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(2);
                        }
                        res.setIsApproved(true);
                        res.setApprovedDate(date);
                        res.setApprovedBy(2);

                        rdao.UpdateResult(res);
                        System.out.println("Result ID: " + rs.getInt("idresults")
                                + " has been updated! Posted and Approved.");
                        results.add(res.getIdResults());
                    }
                }
            }

            rs.close();
            pStmt.close();

            //Now Go though and check the saved Battery headers
            if (batteryHeaders.isEmpty()) {
                System.out.println("There are no Battery Headers Pending!");
            } else {
                batteries = CleanPendingBatteries(batteryHeaders);

                for (Integer bat : batteries) {
                    results.add(bat);
                }
            }

            return results;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                System.out.println(stack.toString());
            }
            return null;
        }
    }

    public ArrayList<Integer> CleanPendingBatteries(ArrayList<Integer> idResults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Integer> results = new ArrayList<>();
            Results res = new Results();
            java.util.Date date = new java.util.Date();

            if (approveOnPost == null) {
                approveOnPost = false;
            }

            String select = "SELECT DISTINCT r.idresults, r.orderid , r.panelid, r.testid, t.name, t.number, t.header, r.dateReported, r.approvedDate, r.created "
                    + "FROM results r "
                    + "LEFT JOIN tests t on r.testid = t.idtests "
                    + "WHERE (r.dateReported IS NULL OR r.approvedDate IS NULL) "
                    + "AND r.idresults IN (" + IntArrayToInClause(idResults) + ") "
                    + "AND t.testType = 0 "
                    + "AND t.header = true";

            PreparedStatement pStmt = con.prepareStatement(select);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                //Now go through looking at panels only
                int unreported = FindUnreportedSubtests(rs.getInt("orderid"),
                        rs.getInt("panelid"));

                if (unreported > 0) {
                    System.out.println("Order ID: " + rs.getInt("orderid")
                            + " - Still has pending results - Battery");
                } else {
                    int unapproved = FindUnapprovedSubtests(rs.getInt("orderid"),
                            rs.getInt("testid"));

                    if (approveOnPost == false && unapproved > 0) {
                        System.out.println("Order ID: " + rs.getInt("orderid")
                                + " - All Posted but has Unapproved results - Battery");

                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(2);
                        }

                        rdao.UpdateResult(res);
                        System.out.println("Result ID: " + rs.getInt("idresults")
                                + " has been updated! Posted but NOT Approved - Battery");
                        results.add(res.getIdResults());
                    } else {
                        res = new Results();
                        res = rdao.GetResultByID(rs.getInt("idresults"));
                        if (res == null || res.getIdResults() == null) {
                            System.out.println("Failed to find result id " + rs.getInt("idresults"));
                            continue;
                        }
                        if (res.getDateReported() == null) {
                            res.setDateReported(date);
                            res.setReportedBy(2);
                        }
                        res.setIsApproved(true);
                        res.setApprovedDate(date);
                        res.setApprovedBy(2);

                        rdao.UpdateResult(res);
                        System.out.println("Result ID: " + rs.getInt("idresults")
                                + " has been updated! Posted and Approved - Battery");
                        results.add(res.getIdResults());
                    }
                }
            }

            rs.close();
            pStmt.close();

            return results;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            StackTraceElement[] stackTrace = ex.getStackTrace();
            for (StackTraceElement stack : stackTrace) {
                System.out.println(stack.toString());
            }
            return null;
        }
    }

    public int FindUnreportedSubtests(int ordId, int panelId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            int pending = 0;
            String query = "SELECT COUNT(*) from `results` r "
                    + "	LEFT JOIN `tests` t on t.`idtests` = r.`testid` "
                    + "	WHERE r.`orderid` = ? "
                    + "	AND r.`panelid` = ? "
                    + "	AND r.`dateReported` IS NULL";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(pStmt, 1, ordId);
            SQLUtil.SafeSetInteger(pStmt, 2, panelId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                pending = rs.getInt(1);
            }

            rs.close();
            pStmt.close();

            return pending;
        } catch (Exception ex) {
            System.out.println("ResultPostBL::FindUnreportedSubtests - " + ex.toString());
            return -1;
        }
    }

    public int FindUnapprovedSubtests(int ordId, int panelId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return -1;
        }

        try {
            int pending = 0;
            String query = "SELECT COUNT(*) from `results` r "
                    + "	JOIN `tests` t on t.`idtests` = r.`testid` "
                    + "	WHERE r.`orderid` = ? "
                    + "	AND r.`panelid` = ? "
                    + "	AND r.`approvedDate` IS NULL";

            PreparedStatement pStmt = con.prepareStatement(query);

            SQLUtil.SafeSetInteger(pStmt, 1, ordId);
            SQLUtil.SafeSetInteger(pStmt, 2, panelId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                pending = rs.getInt(1);
            }

            rs.close();
            pStmt.close();

            return pending;
        } catch (Exception ex) {
            System.out.println("ResultPostBL::FindUnreportedSubtests - " + ex.toString());
            return -1;
        }
    }

    public String IntArrayToInClause(ArrayList<Integer> ints) {
        String intList = "";

        for (Integer num : ints) {
            intList += num + ", ";
        }

        if (intList.length() > 0) {
            intList = intList.substring(0, intList.length() - 2);
        }

        return intList;
    }

    public ArrayList<Integer> GetSaltAndSpicePanels() {
        String delims = "[,]";

        try {
            String[] tokens = saltsSpices.split(delims);

            ArrayList<Integer> panels = new ArrayList<>();

            for (String str : tokens) {
                try {
                    panels.add(Integer.parseInt(str));
                } catch (NumberFormatException nfe) {
                    System.out.println("ResultPostBL::GetSaltAndSpicePanels - " + nfe.toString());
                }
            }

            return panels;
        } catch (Exception ex) {
            return null;
        }
    }

    public Results PostSaltAndSpices(Results res, ArrayList<Integer> saltsSpices) {
        try {
            Tests ssPanel = tdao.GetTestByID(res.getPanelId());

            //Now see if this test is in a Salt Or Spices Panel
            if (ssPanel.getIdtests() != null && saltsSpices.contains(ssPanel.getNumber())) {
                //It is a Salt or Spice, now get the rest of the results
                //and see if they are posted.

                // Uses the tests definition, ignoring the active status.
                ArrayList<Tests> subtestsArrayList = tdao.GetSubtestsIncludingInactive(ssPanel);
                Tests[] subtests = subtestsArrayList.toArray(new Tests[subtestsArrayList.size()]);

                //Tests[] subtests = tdao.GetSubtests(ssPanel.getNumber());
                int numReported = 0;

                ArrayList<Results> list = (ArrayList<Results>) rdao.GetReportedResultByOrderID(res.getOrderId());
                ArrayList<Results> pnlResults = new ArrayList<>();

                //This checks each test in the panel to see if it has been posted.
                for (Tests t : subtests) {
                    for (Results r : list) {
                        //Tests resultRowTest = tdao.GetTestByID(r.getTestId());

                        //if (t.getNumber() == resultRowTest.getNumber())
                        //{
                        //    pnlResults.add(r);
                        //    numReported++;
                        //}
                        if (t.getIdtests() == r.getTestId()) {
                            pnlResults.add(r);
                            numReported++;
                        }
                    }
                }

                //If they are all reported except the first subtest, me must post it
                if ((numReported == subtests.length - 1) || (numReported == subtests.length)) {
                    //Get the test for the first subtest

                    // This is NOT necessarily the remark row! It's just the
                    // first result for the panel!
                    int ssRemID = pandao.GetSubTestID(ssPanel.getIdtests(), 1);
                    Tests ssRemTest = tdao.GetTestByID(ssRemID);
                    // Get the result row based on the order ID and test Number
                    Results ssRemRes = rdao.GetResultByOrderIDTestNumber(res.getOrderId(), ssRemTest.getNumber());

                    if (ssRemRes == null || ssRemRes.getIdResults() == null) {
                        System.out.println("Call to GetResultByOrderIDTestNumber for orderId "
                                + res.getOrderId() + " / testNumber " + ssRemTest.getNumber()
                                + " returned a null result. The result line may not exist");
                        return null;
                    }
                    //Results ssRemRes = rdao.GetResultByOrderIDTestID(res.getOrderId(), ssRemID);

                    // If the first row isn't reported,
                    //Double check that ssRemRes is not posted
                    if (ssRemRes.getDateReported() == null) {
                        boolean finalResult = false;

                        // For each result marked reported in the panel
                        for (Results ssres : pnlResults) {
                            // If that result is positive
                            if (ssres.getResultRemark() == PosRemarkID) {
                                finalResult = true;
                                break;
                            }
                        }

                        if (finalResult == false) {
                            ssRemRes.setResultRemark(NegRemarkID);
                        } else {
                            ssRemRes.setResultRemark(PosRemarkID);
                        }

                        return ssRemRes;
                    } else {
                        //Remark test already posted
                        return null;
                    }
                } else {
                    //All the tests are not reported yet
                    return null;
                }
            } else {
                //Not a SaltAndSpice test
                return null;
            }
        } catch (Exception ex) {
            System.out.println("ResultPostBL::PostSaltAndSpices - " + ex.toString());
            return null;
        }
    }

    /**
     * Returns all Battery Headers for an Order by orderId
     *
     * @param orderId int value of orderId from Result Row
     * @return ArrayList<Results> all Battery Result Rows for orderId
     */
    public ArrayList<Results> GetBatteriesOnOrder(int orderId) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        try {
            ArrayList<Results> batts = new ArrayList<>();
            ArrayList<Integer> resList = new ArrayList<>();

            String query = "SELECT DISTINCT r.idresults, "
                    + "r.orderid, "
                    + "r.panelid, "
                    + "r.testid, "
                    + "t.`name`, "
                    + "t.number, "
                    + "t.header, "
                    + "r.dateReported, "
                    + "r.approvedDate, "
                    + "r.created "
                    + "FROM results r "
                    + "LEFT JOIN tests t "
                    + "	ON r.testid = t.idtests "
                    + "WHERE r.orderid = ? "
                    + "AND t.testType = 0 "
                    + "AND t.header = true";

            PreparedStatement pStmt = con.prepareStatement(query);
            SQLUtil.SafeSetInteger(pStmt, 1, orderId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                resList.add(rs.getInt(1));
            }

            rs.close();
            pStmt.close();


            for (Integer rid : resList) {
                batts.add(rdao.GetResultByID(rid));
            }

            return batts;

        } catch (Exception ex) {
            System.out.println("ResultPostBL::GetBatteriesOnOrder - " + ex.toString());
            return null;
        }
    }

    /**
     * Checks to see if this Result completes the Genetic test/panel. If so put
     * it in the translational queue.
     *
     * @param orderId
     * @param testNumber
     * @return boolean true if completes
     */
    public boolean CompleteTranslationalOrder(int orderId, int testNumber) {
        try {
            AutoGenomicsBL abl = new AutoGenomicsBL();

            // Get the Accession Number from the Result Set
            Orders ord = odao.GetOrderById(orderId);

            String accessionNumber = ord.getAccession();

            // Get the Test Number that was ordered from the instrument
            //int testNumber = abl.GetTranslationalTestOrderedByAccession(accessionNumber);
            if (testNumber > 0) {
                // Get a list of the analytes that are in the AutoGenomics Instruments
                ArrayList<String> postedAnalytesList = abl.GetAutoGenomicsPostedAnalytesByAccession(accessionNumber);

                // Get a list of the analytes that should be done for this test
                ArrayList<String> requiredAnalytesList = abl.GetAutoGenomicsRequiredAnalytes(testNumber);

                // If Either of the lists are null then we have a problem so return false
                if ((postedAnalytesList == null) || (requiredAnalytesList == null)) {
                    return false;
                }

                if (blnCheckByCount == true) {
                    if (postedAnalytesList.size() >= requiredAnalytesList.size()) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (postedAnalytesList.size() < requiredAnalytesList.size()) {
                        return false;
                    }

                    // Sort and compare both of the lists
                    Collections.sort(postedAnalytesList);
                    Collections.sort(requiredAnalytesList);

                    return postedAnalytesList.equals(requiredAnalytesList);
                }
            } else {
                return false;
            }
        } catch (SQLException sex) {
            System.out.println("ResultPostBL::CompleteTranslationalOrder(SQL) - " + sex.toString());
            return false;
        } catch (Exception ex) {
            System.out.println("ResultPostBL::CompleteTranslationalOrder - " + ex.toString());
            return false;
        }
    }

    /**
     * Finds the Test ID for for a test number and accession combo even if the
     * Test is deactivated.
     *
     * @param testNum int value of the Test Number
     * @param accession String value of the accession
     * @return Integer of the idTests
     */
    public Integer GetTestIdForPosting(int testNum, String accession) {

        TestBL testBL = new TestBL();

        return testBL.GetTestIDWhenOrdered(testNum, accession);
        //return GetTestIDWhenOrdered( testNum, accession);
        /*
         if (result == null)
         {
            
         }
        
         Integer ID = null;
         OrderDAO odao = new OrderDAO();
         ResultDAO rdao = new ResultDAO();
         TestDAO tdao = new TestDAO();
         Tests test = null;
         List<Results> resList = new ArrayList<>();
        
         try{
         Orders order = odao.GetOrder(accession);
         resList = rdao.GetResultsByOrderID(order.getIdOrders());
            
         for(Results res : resList){
         test = new Tests();
         test = tdao.GetTestByID(res.getTestId());
         if(test.getNumber() == testNum){
         ID = test.getIdtests();
         break;
         }
         }
            
         }catch(Exception ex){
         System.out.println("ResultPostBL::GetTestIdForPosting - "+ex.toString());
         ID = null;
         }
        
         return ID;
         */
    }

    /**
     * Finds the Test from a test number and accession combo even if the Test is
     * deactivated.
     *
     * @param testNum int value of the Test Number
     * @param accession String value of the accession
     * @return Integer of the idTests
     */
    public Tests GetTestForPosting(int testNum, String accession) {

        Tests test = null;
        List<Results> resList = new ArrayList<>();

        try {
            Orders order = odao.GetOrder(accession);
            resList = rdao.GetResultsByOrderID(order.getIdOrders());

            for (Results res : resList) {
                test = new Tests();
                test = tdao.GetTestByID(res.getTestId());
                if (test.getNumber() == testNum) {
                    break;
                }
            }

        } catch (Exception ex) {
            System.out.println("ResultPostBL::GetTestIdForPosting - " + ex.toString());
            test = null;
        }

        return test;

    }

    /**
     * Calls stored procedures (UpdatePanelHeaders/UpdateBatteryHeaders) to
     * update the reported or approved status of a header for an order.
     *
     * Procedures log any changes made and return strings representing the
     * comma-separated idResults that were modified.
     *
     * @param idOrders
     * @param idUsers
     * @return
     */
    public String UpdateHeadersByOrderID(int idOrders, int idUsers) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        String affectedresults = "";

        try {
            // Panel headers
            String query = "CALL UpdatePanelHeaders("
                    + idOrders + ", "
                    + idUsers + ", ''";

            PreparedStatement pStmt = con.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                affectedresults = rs.getString(1);
            }

            rs.close();
            pStmt.close();

            // Battery headers
            query = "CALL UpdateBatteryHeaders("
                    + idOrders + ", "
                    + idUsers + ", ''";

            pStmt = con.prepareStatement(query);
            rs = pStmt.executeQuery();

            if (rs.next()) {
                affectedresults += rs.getString(1);
            }

            rs.close();
            pStmt.close();
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::UpdateHeadersByOrderID: "
                    + " Order Id: " + idOrders + ".   "
                    + ex.toString());
            return null;
        }

        return affectedresults;
    }

    /**
     * Provided the idResult of a GFR calculation test (either black/white),
     * returns the calculation result value. The calling code should handle
     * decimal precision and rounding rules.
     *
     * Data Requirements: Creatinine numeric test result (in the same panel as
     * GFR test) patient age, sex (M/F), preference rows for GFR calculations
     * and creatinine test.
     *
     * NOTE: preference rows need to use test numbers, not test id, as IDs for
     * active tests will change.
     *
     * @param idResults The result row of the GFR calculation to be calculated.
     * @return Double of calculation result based in which ethnicity the GFR
     * test is for.
     */
    public Double GetGFRResultByResultID(int idResults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Date dob;
        int age;
        String gender;
        double genderMultiplier;
        double ethnicityMultiplier;
        double creatinineResult;
        
        // Return the result depending on which GFR calculation was supplied
        // (white/black ethnicity)
        boolean useWhiteResult;

        Double result = null; // The final result to be returned

        try {
            // Grab the required test numbers (creatinine and the two GFRs)
            TestBL tbl = new TestBL();

            List<Integer> creatinineTestNumbers = tbl.GetGFRCreatinineTestNumbers();

            //Integer creatinineTestNumber = prefDAO.getInteger("GFRCreatinineTestNumber");            

            // The defaults for the leading coefficient and multipliers can be
            // overridden if the following preferences are present. If they are [NULL],
            // the default is used.            
            
            if (creatinineTestNumbers == null
                    || creatinineTestNumbers.isEmpty()
                    || GFRwhiteTestNumber == null
                    || GFRblackTestNumber == null) {
                System.out.println("Cannot find preference row for one or more of the following: "
                        + "GFRCreatinineTestNumber, GFRWhiteCalculationTestNumber, "
                        + "GFRBlackCalculationTestNumber. These need to be defined to perform the GFR");
                return null;
            }

            Results resultRow = rdao.GetResultByID(idResults);
            Tests gfrTest = tdao.GetTestByID(resultRow.getTestId());

            if (gfrTest == null) {
                System.out.println("Can't retrieve GFR test for result ID"
                        + idResults);
                return null;
            }

            int gfrTestNumber = gfrTest.getNumber();

            // Figure out which ethnicity we're calculating.
            // If the result row supplied doesn't point to either of the
            // GFR calculation tests (white/black), exit
            if (gfrTestNumber == GFRwhiteTestNumber) {
                useWhiteResult = true;
            } else if (gfrTestNumber == GFRblackTestNumber) {
                useWhiteResult = false;
            } else {
                System.out.println("Supplied result row (idresults: "
                        + idResults + " / idtests: " + gfrTest.getIdtests()
                        + ") does not point to either of the GFR calculation "
                        + "tests as defined in the preferences table.");
                return null;
            }

            String creatinineTestNumberList = "";
            for (Integer creatinineTestNumber : creatinineTestNumbers) {
                if (creatinineTestNumberList.isEmpty() == false) {
                    creatinineTestNumberList += ",";
                }
                creatinineTestNumberList += creatinineTestNumber;
            }

            // Get the creatinine row for this GFR test based on
            // test number (not id)
            String sql
                    = "SELECT"
                    + " r.resultNo," // Test result
                    + " p.dob,"
                    + " p.sex"
                    + " FROM results r"
                    + " INNER JOIN tests t ON r.testId = t.idTests"
                    + " INNER JOIN orders o ON r.orderId = o.idOrders"
                    + " INNER JOIN patients p on o.patientId = p.idPatients"
                    + " WHERE r.orderId = " + resultRow.getOrderId()
                    + " AND t.number in (" + creatinineTestNumberList + ") "
                    + " AND (r.resultText IS NULL OR r.resultText != 'DELETED') "
                    + " AND r.isInvalidated != 1";

            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                // Default Formula is as follows:
                // ---------------------------------------------------------
                // 186 * (creatinine ^ -1.154) * (age ^ -0.203) * genderMultiplier * raceMultiplier
                // 
                // Where Gender multiplier is 0.742 if female, 1.0 if male
                // and race multiplier is 1.21 if black, 1.0 if white                    

                // The leading coefficient and multipliers can be overridden via preferences
                // ===== Leading Coeffficient ====
                if (GFRLeadingCoefficient == null) {
                    GFRLeadingCoefficient = 186.0d;
                }

                // ============= Age =============
                dob = rs.getTimestamp("dob");
                if (dob == null) {
                    return null;
                }
                Years years = Years.yearsBetween(
                        new LocalDate(dob),
                        new LocalDate());
                age = years.getYears();

                // =========== Gender =============
                gender = rs.getString("sex");
                if (gender == null) {
                    return null;
                }

                if (gender.equals("Male")) {
                    genderMultiplier = 1.0d;
                } else if (gender.equals("Female")) {
                    if (GFRFemaleMultiplier == null) {
                        // Use default
                        genderMultiplier = 0.742d;
                    } else {
                        genderMultiplier = GFRFemaleMultiplier;
                    }

                } else // Gender is of an unanticipated type.
                {
                    System.out.println("Gender not set to 'Male' or 'Female'");
                    return null;
                }

                // ======== Creatinine result ========
                creatinineResult = rs.getDouble("resultNo");
                if (creatinineResult == 0) {
                    return null;
                }

                // ============= Ethnicity =============                    
                if (useWhiteResult) {
                    ethnicityMultiplier = 1.0d;
                } else // black result
                {
                    if (GFRBlackMultiplier == null) {
                        ethnicityMultiplier = 1.21d;
                    } else {
                        ethnicityMultiplier = GFRBlackMultiplier;
                    }
                }                
                
                if (GFREquation != null && GFREquation == 2) {
                    /*
                    GFR = 
                        141 
                        * min(Scr/κ,1) * α 
                        * max(Scr/κ, 1) - 1.209 
                        * 0.993 * Age 
                        * 1.018 [if female] * 1.159 [if black]

                        Scr = serum creatinine (mg/dL)
                        κ 	= 0.7 for females and 0.9 for males
                        α 	= -0.329 for females and -0.411 for males
                        min = the minimum of Scr/κ or 1
                        max = the maximum of Scr/κ or 1
                    */
                    
                    Double GFRKappaMaleMultiplier = PreferencesDAO.GetDouble("GFRKappaMaleMultiplier");
                    Double GFRAlphaMaleMultiplier = PreferencesDAO.GetDouble("GFRAlphaMaleMultiplier");
                    Double GFRKappaFemaleMultiplier = PreferencesDAO.GetDouble("GFRKappaFemaleMultiplier");
                    Double GFRAlphaFemaleMultiplier = PreferencesDAO.GetDouble("GFRAlphaFemaleMultiplier");

                    double genderMultiplierKappa, genderMultiplierAlpha;
                    if (gender.equals("Male")) {

                        if (GFRKappaMaleMultiplier == null) {
                            genderMultiplierKappa = 0.9d;
                        } else {
                            genderMultiplierKappa = GFRKappaMaleMultiplier;
                        }

                        if (GFRAlphaMaleMultiplier == null) {
                            genderMultiplierAlpha = -0.411d;
                        } else {
                            genderMultiplierAlpha = GFRAlphaMaleMultiplier;
                        }

                    } else{
                        if (GFRKappaFemaleMultiplier == null) {
                            genderMultiplierKappa = 0.7d;
                        } else {
                            genderMultiplierKappa = GFRKappaFemaleMultiplier;
                        }

                        if (GFRAlphaFemaleMultiplier == null) {
                            genderMultiplierAlpha = -0.329d;
                        } else {
                            genderMultiplierAlpha = GFRAlphaFemaleMultiplier;
                        }

                    }

                    result = GFRLeadingCoefficient
                            * Math.pow(Math.min(creatinineResult/genderMultiplierKappa, 1.0d), genderMultiplierAlpha)
                            * Math.pow(Math.max(creatinineResult/genderMultiplierKappa, 1.0d), -1.209d)
                            * Math.pow(0.993d, age)
                            * genderMultiplier
                            * ethnicityMultiplier;
//                } else if (GFREquation != null && GFREquation == 3) {
//                    
//                    if (gender.equals("Male")) {
//                        genderMultiplier = 1.0d;
//                    } else if (gender.equals("Female")) {
//                        genderMultiplier = 0.742d;
//                    } else {
//                        System.out.println("Gender not set to 'Male' or 'Female'");
//                        return null;
//                    }
//                    
//                    result = 175.0d 
//                            * Math.pow(creatinineResult, -1.154d)
//                            * Math.pow(age, -0.203)
//                            *
//                            ;
////                    Test #: 951002 and 951003
////                    Calc: 175.0 * (Creatinine ^ -1.154) * (Age ^ -0.203) * GenderMultiplier * RaceMultiplier
////                    GenderMultiplier: 0.742 if female, 1.0 if male
////                    RaceMultiplier: 1.21 if black, 1.0 if white
//                    
                    
                } else {
                    
                    result
                        = GFRLeadingCoefficient
                        * Math.pow(creatinineResult, -1.154d)
                        * Math.pow(age, -0.203d)
                        * genderMultiplier
                        * ethnicityMultiplier;
                }
                
                // per Ryan: the decimal precision should depend on
                // how the GFR test is defined, so precision is not checked
                // here. Calling code can run SetResultAbnormalInformation
                // which will set the abnormal information and affect the
                // precision formatting.
                if (result.isInfinite() || result.isNaN()) {
                    String err = "Result is "
                            + (result.isInfinite() ? "Infinity " : "NaN ")
                            + ". Check the patient's DOB. Input values were creatinine "
                            + creatinineResult + " age " + age + " genderMultiplier "
                            + genderMultiplier + " ethnicityMultiplier " + ethnicityMultiplier;
                    throw new SQLException(err);
                }

                return result;
            }

            rs.close();
            pStmt.close();
        } catch (SQLException ex) {
            Preferences prefs = Preferences.userRoot();
            int userId = prefs.getInt("id", 0);
            if (userId == 0) {
                userId = 1;
            }
            SysLogDAO.Add(userId, "Error performing GFR calculation for resultId: " + idResults, ex.getMessage());
            System.out.println("Error while performing GFR calculation for "
                    + "GFR calculation result ID: " + idResults);
            result = null;
        }
        return result; // null on error
    }

    /**
     * Provided a Results object of a GFR calculation test (either black/white),
     * returns the calculation result value. The calling code should handle
     * decimal precision and rounding rules.
     *
     * Data Requirements: Creatinine numeric test result id (in the same panel
     * as GFR test) patient age, sex (M/F), preference rows for GFR calculations
     * and creatinine test.
     *
     * NOTE: preference rows need to use test numbers, not test id, as IDs for
     * active tests will change.
     *
     * @param gfrRes The result object of the GFR calculation to be calculated.
     * @param creatinineRes The result object of the associated creatinine
     * result
     * @return Double of calculation result based in which ethnicity the GFR
     * test is for.
     */
    public Double GetGFRResultByResults(Results gfrRes, Results creatinineRes) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }

        Date dob;
        int age;
        String gender;
        double genderMultiplier;
        double ethnicityMultiplier;
        double creatinineResult;

        // Return the result depending on which GFR calculation was supplied
        // (white/black ethnicity)
        boolean useWhiteResult;

        Double result = null; // The final result to be returned

        try {
            // Grab the required test numbers (creatinine and the two GFRs)
            TestBL tbl = new TestBL();
            List<Integer> creatinineTestNumbers = tbl.GetGFRCreatinineTestNumbers();
            //Integer creatinineTestNumber = prefDAO.getInteger("GFRCreatinineTestNumber");            

            if (creatinineTestNumbers == null
                    || creatinineTestNumbers.isEmpty()
                    || GFRwhiteTestNumber == null
                    || GFRblackTestNumber == null) {
                System.out.println("Cannot find preference row for one or more of the following: "
                        + "GFRCreatinineTestNumber, GFRWhiteCalculationTestNumber, "
                        + "GFRBlackCalculationTestNumber . These need to be defined to perform the GFR");
                return null;
            }

            // The defaults for the leading coefficient and multipliers can be
            // overridden if the following preferences are present. If they are [NULL],
            // the default is used.
            
            Tests gfrTest = tdao.GetTestByID(gfrRes.getTestId());

            if (gfrTest == null) {
                System.out.println("Can't retrieve GFR test for result ID"
                        + gfrRes.getIdResults());
                return null;
            }

            int gfrTestNumber = gfrTest.getNumber();

            // Figure out which ethnicity we're calculating.
            // If the result row supplied doesn't point to either of the
            // GFR calculation tests (white/black), exit
            if (gfrTestNumber == GFRwhiteTestNumber) {
                useWhiteResult = true;
            } else if (gfrTestNumber == GFRblackTestNumber) {
                useWhiteResult = false;
            } else {
                System.out.println("Supplied result (idresults: "
                        + gfrRes.getIdResults() + " / idtests: " + gfrTest.getIdtests()
                        + ") does not point to either of the GFR calculation "
                        + "tests as defined in the preferences table.");
                return null;
            }

            Orders order = odao.GetOrderById(creatinineRes.getOrderId());
            Patients patient = patdao.GetPatientById(order.getPatientId());

            // Default Formula is as follows:
            // ---------------------------------------------------------
            // 186 * (creatinine ^ -1.154) * (age ^ -0.203) * genderMultiplier * raceMultiplier
            // 
            // Where Gender multiplier is 0.742 if female, 1.0 if male
            // and race multiplier is 1.21 if black, 1.0 if white                    
            // ===== Leading Coeffficient ====
            if (GFRLeadingCoefficient == null) {
                GFRLeadingCoefficient = 186.0d;
            }
            // ============= Age =============
            dob = patient.getDob();
            if (dob == null) {
                return null;
            }
            Years years = Years.yearsBetween(
                    new LocalDate(dob),
                    new LocalDate());
            age = years.getYears();

            // =========== Gender =============
            gender = patient.getSex();
            if (gender == null) {
                return null;
            }

            if (gender.equals("Male")) {
                genderMultiplier = 1.0d;
            } else if (gender.equals("Female")) {
                if (GFRFemaleMultiplier == null) {
                    // Use default
                    genderMultiplier = 0.742d;
                } else {
                    genderMultiplier = GFRFemaleMultiplier;
                }
            } else // Gender is of an unanticipated type.
            {
                System.out.println("Gender not set to 'Male' or 'Female'");
                return null;
            }

            // ======== Creatinine result ========
            creatinineResult = creatinineRes.getResultNo();
            if (creatinineResult == 0) {
                return null;
            }

            // ============= Ethnicity =============                    
            if (useWhiteResult) {
                ethnicityMultiplier = 1.0d;
            } else // black result
            {
                if (GFRBlackMultiplier == null) {
                    ethnicityMultiplier = 1.21d;
                } else {
                    ethnicityMultiplier = GFRBlackMultiplier;
                }
            }
            
            if (GFREquation != null && GFREquation == 2) {
                Double GFRKappaMaleMultiplier = PreferencesDAO.GetDouble("GFRKappaMaleMultiplier");
                Double GFRAlphaMaleMultiplier = PreferencesDAO.GetDouble("GFRAlphaMaleMultiplier");
                Double GFRKappaFemaleMultiplier = PreferencesDAO.GetDouble("GFRKappaFemaleMultiplier");
                Double GFRAlphaFemaleMultiplier = PreferencesDAO.GetDouble("GFRAlphaFemaleMultiplier");

                double genderMultiplierKappa, genderMultiplierAlpha;
                if (gender.equals("Male")) {

                    if (GFRKappaMaleMultiplier == null) {
                        genderMultiplierKappa = 0.9d;
                    } else {
                        genderMultiplierKappa = GFRKappaMaleMultiplier;
                    }

                    if (GFRAlphaMaleMultiplier == null) {
                        genderMultiplierAlpha = -0.411d;
                    } else {
                        genderMultiplierAlpha = GFRAlphaMaleMultiplier;
                    }

                } else{
                    if (GFRKappaFemaleMultiplier == null) {
                        genderMultiplierKappa = 0.7d;
                    } else {
                        genderMultiplierKappa = GFRKappaFemaleMultiplier;
                    }

                    if (GFRAlphaFemaleMultiplier == null) {
                        genderMultiplierAlpha = -0.329d;
                    } else {
                        genderMultiplierAlpha = GFRAlphaFemaleMultiplier;
                    }

                }
               
//                result = GFRLeadingCoefficient
//                        * (Math.min(creatinineResult/genderMultiplierKappa, 1.0d) * genderMultiplierAlpha)
//                        * (Math.max(creatinineResult/genderMultiplierKappa, 1.0d) - 1.209d)
//                        * (0.993d * age)
//                        * genderMultiplier
//                        * ethnicityMultiplier;
                result = GFRLeadingCoefficient
                            * Math.pow(Math.min(creatinineResult/genderMultiplierKappa, 1.0d), genderMultiplierAlpha)
                            * Math.pow(Math.max(creatinineResult/genderMultiplierKappa, 1.0d), -1.209d)
                            * Math.pow(0.993d, age)
                            * genderMultiplier
                            * ethnicityMultiplier;
            } else {
                result
                    = GFRLeadingCoefficient
                    * Math.pow(creatinineResult, -1.154d)
                    * Math.pow(age, -0.203d)
                    * genderMultiplier
                    * ethnicityMultiplier;
            }

            

            // per Ryan: the decimal precision should depend on
            // how the GFR test is defined, so precision is not checked
            // here. Calling code can run SetResultAbnormalInformation
            // which will set the abnormal information and affect the
            // precision formatting.
            if (result.isInfinite() || result.isNaN()) {
                String err = "Result is " + (result.isInfinite() ? "Infinity " : "NaN ")
                        + ". Check the patient's DOB. Input values were"
                        + " creatinine " + creatinineResult
                        + " age " + age
                        + " genderMultiplier " + genderMultiplier
                        + " ethnicityMultiplier " + ethnicityMultiplier;
                throw new SQLException(err);
            }

            return result;
        } catch (SQLException ex) {
            Preferences prefs = Preferences.userRoot();
            int userId = prefs.getInt("id", 0);
            if (userId == 0) {
                userId = 1;
            }
            SysLogDAO.Add(userId, "Error performing GFR calculation for resultId: " + gfrRes.getIdResults(), ex.getMessage());
            System.out.println("Error while performing GFR calculation for "
                    + "GFR calculation result ID: " + gfrRes.getIdResults());
            result = null;
        }
        return result; // null on error
    }

    /**
     * Based on GFRResultByResultId
     * Calculation for minors
     */
    public Double GetGFRMinorResultByResultID(int idResults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        double height;        
        double creatinineResult;
        Double result = null; // The final result to be returned

        // Grab the required test numbers (creatinine and the two GFRs)
        TestBL tbl = new TestBL();
        try {
            List<Integer> creatinineTestNumbers = tbl.GetGFRCreatinineTestNumbers();
            //Integer for GFR Minor Calculation Test Number
            if (creatinineTestNumbers == null || GFRMinorTestNumber == null) {
                System.out.println("Cannot find preference row for one or both of the following: "
                        + "GFRCreatinineTestNumber, GFRMinorCalculationTestNumber These need to be defined to perform the GFR");
                return null;
            }

            Results resultRow = rdao.GetResultByID(idResults);
            Tests gfrTest = tdao.GetTestByID(resultRow.getTestId());

            if (gfrTest == null) {
                System.out.println("Can't retrieve GFR test for result ID"
                        + idResults);
                return null;
            }

            String creatinineTestNumberList = "";
            for (Integer creatinineTestNumber : creatinineTestNumbers) {
                if (creatinineTestNumberList.isEmpty() == false) {
                    creatinineTestNumberList += ",";
                }
                creatinineTestNumberList += creatinineTestNumber;
            }

            // Get the creatinine row for this GFR test based on
            // test number (not id)
            String sql
                    = "SELECT"
                    + " r.resultNo," // Test result
                    + " p.dob,"
                    + " p.height "
                    + " FROM results r"
                    + " INNER JOIN tests t ON r.testId = t.idTests"
                    + " INNER JOIN orders o ON r.orderId = o.idOrders"
                    + " INNER JOIN patients p on o.patientId = p.idPatients"
                    + " WHERE r.orderId = " + resultRow.getOrderId()
                    + " AND t.number in (" + creatinineTestNumberList + ") "
                    + " AND (r.resultText IS NULL OR r.resultText != 'DELETED') "
                    + " AND r.isInvalidated != 1";

            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                //============== Height =============
                int ht = rs.getInt("height");                
                height =  2.54 * ht;
                
                // ======== Creatinine result ========
                creatinineResult = rs.getDouble("resultNo");
                if (creatinineResult == 0) {
                    return null;
                }
                
                result =  0.413 * (height / creatinineResult );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ResultPostBL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public Integer CopyReferenceLabCommentToResult(
            int departmentNo,
            int idrefresult,
            int idresults) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        // Should either be 1 or 0
        Integer affectedRows = 0;
        try {
            // Call stored procedure so everything stays on the server.
            // Panel headers
            String query = "CALL CopyReferenceLabCommentToResult("
                    + departmentNo + ", "
                    + idrefresult + ", "
                    + idresults + ")";

            PreparedStatement pStmt = con.prepareStatement(query);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                affectedRows = rs.getInt(1);
            }
            rs.close();
            pStmt.close();
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::CopyReferenceLabCommentToResult: "
                    + "Error attempting to copy reference lab comment."
                    + "departmentNo:" + departmentNo + " idrefresult:"
                    + idrefresult + " idresults:" + idresults);
        }
        return affectedRows;
    }

    public int ReflexResult(Results res) {
        
        Orders resultOrder = null;
        try
        {
            int orderId = res.getOrderId();
            resultOrder = odao.GetOrderById(orderId);
        }
        catch (Exception ex)
        {
            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:Obtaining Result Order", ex.getMessage());
        }

        //Since it has reflex rules set up variables
        int reflexed = 0;
        List<ClientCustomReflexRules> clientRules;
        
        Integer ruleTestNumber = null;
        try
        {
            Tests test = tdao.GetTestByID(res.getTestId());
            ruleTestNumber = test.getNumber();
        }
        catch (Exception ex)
        {
            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:Obtaining Result Order", ex.getMessage());
        }
        
        if (resultOrder != null && ruleTestNumber != null)
        {
            try {
                clientRules = ClientCustomReflexRulesDAO.getAllForTestNumberAndClientId(ruleTestNumber, resultOrder.getClientId(), true);
            } catch (SQLException ex) {
                SysLogDAO.Add(1, "ResultPostBL::ReflexResult:Obtaining Custom Reflexes", ex.getMessage());
                clientRules = new ArrayList<ClientCustomReflexRules>();
            }
        }
        else
        {
            clientRules = new ArrayList<ClientCustomReflexRules>();
        }
        
        ArrayList<ReflexMultichoice> lstReflexMulti = rmcdao.getReflexesForTest(res.getTestId());
        
        //First check if there are any Reflex Rules to even evaluate
        if (rrdao.ReflexRuleCount(res.getTestId()) < 1 && (lstReflexMulti == null || lstReflexMulti.size() < 1) && (clientRules == null || clientRules.isEmpty())) {
            return 0;
        }
        if (clientRules == null || clientRules.isEmpty()) // use the reflex rules from the test
        {
            ArrayList<ReflexRules> rules = rrdao.GetRulesByTestID(res.getTestId());
            if (rules.size() > 0) {
                for (ReflexRules rule : rules) {
                    if (res.getIsCIDLow() && rule.getIsCIDLow() != null && rule.getIsCIDLow() != 0) {
                        try {
                            //Tests test = tdao.GetTestByID(rule.getIsCIDLow());
                            Tests test = tdao.GetNewestVersionOfTest(rule.getIsCIDLow());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:CIDLow: Error", ex.getMessage());
                        }
                    } else if (res.getIsCIDHigh() && rule.getIsCIDHigh() != null && rule.getIsCIDHigh() != 0) {
                        try {
                            //Tests test = tdao.GetTestByID(rule.getIsCIDHigh());
                            Tests test = tdao.GetNewestVersionOfTest(rule.getIsCIDHigh());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:CIDHigh: Error", ex.getMessage());
                        }

                    } else if (res.getIsLow() && rule.getIsLow() != null && rule.getIsLow() != 0) {
                        try {
                            //Tests test = tdao.GetTestByID(rule.getIsLow());
                            Tests test = tdao.GetNewestVersionOfTest(rule.getIsLow());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:isLow: Error", ex.getMessage());
                        }

                    } else if (res.getIsHigh() && rule.getIsHigh() != null && rule.getIsHigh() != 0) {
                        try {
                            //Tests test = tdao.GetTestByID(rule.getIsHigh());
                            Tests test = tdao.GetNewestVersionOfTest(rule.getIsHigh());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:isHigh: Error", ex.getMessage());
                        }
                    } else if (res.getResultRemark() != null && res.getResultRemark() != 0
                            && rule.getReflexRemark() != null && rule.getReflexRemark() != 0
                            && res.getResultRemark().equals(rule.getReflexRemark())) {
                        try {
                            //Tests test = tdao.GetTestByID(rule.getRemarkTest());
                            Tests test = tdao.GetNewestVersionOfTest(rule.getRemarkTest());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:Remark Error", ex.getMessage());
                        }
                    }
                }
            } else {
                // check for reflexMultiChoice rules                
                for (ReflexMultichoice rule : lstReflexMulti) {
                    if (res.getResultChoice() != null && rule.getMultichoiceId() != null && rule.getReflexedTestId() != null && res.getResultChoice().equals(rule.getMultichoiceId())) {
                        try {
                            Tests test = tdao.GetNewestVersionOfTest(rule.getReflexedTestId());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        } catch (SQLException ex) {
                            SysLogDAO.Add(1, "ResultPostBL::ReflexResult:ReflexMultiChoice Error ", ex.getMessage());
                        }
                    }
                }
                
            }
        }
        else // use the client custom reflex rules
        {
            for (ClientCustomReflexRules rule : clientRules) {
                if (res.getIsCIDLow() && rule.getIsCIDLow() != null && rule.getIsCIDLow() != 0) {
                    try {
                        //Tests test = tdao.GetTestByID(rule.getIsCIDLow());
                        Tests test = tdao.GetTestByNumber(rule.getIsCIDLow());
                        CreateAndInsertReflexedResults(test, res);
                        reflexed++;
                    } catch (Exception ex) {
                        SysLogDAO.Add(1, "ResultPostBL::ReflexResult:CIDLow: Error", ex.getMessage());
                    }
                } else if (res.getIsCIDHigh() && rule.getIsCIDHigh() != null && rule.getIsCIDHigh() != 0) {
                    try {
                        //Tests test = tdao.GetTestByID(rule.getIsCIDHigh());
                        Tests test = tdao.GetTestByNumber(rule.getIsCIDHigh());
                        CreateAndInsertReflexedResults(test, res);
                        reflexed++;
                    } catch (Exception ex) {
                        SysLogDAO.Add(1, "ResultPostBL::ReflexResult:CIDHigh: Error", ex.getMessage());
                    }

                } else if (res.getIsLow() && rule.getIsLow() != null && rule.getIsLow() != 0) {
                    try {
                        //Tests test = tdao.GetTestByID(rule.getIsLow());
                        Tests test = tdao.GetTestByNumber(rule.getIsLow());
                        CreateAndInsertReflexedResults(test, res);
                        reflexed++;
                    } catch (Exception ex) {
                        SysLogDAO.Add(1, "ResultPostBL::ReflexResult:isLow: Error", ex.getMessage());
                    }

                } else if (res.getIsHigh() && rule.getIsHigh() != null && rule.getIsHigh() != 0) {
                    try {
                        //Tests test = tdao.GetTestByID(rule.getIsHigh());
                        Tests test = tdao.GetTestByNumber(rule.getIsHigh());
                        CreateAndInsertReflexedResults(test, res);
                        reflexed++;
                    } catch (Exception ex) {
                        SysLogDAO.Add(1, "ResultPostBL::ReflexResult:isHigh: Error", ex.getMessage());
                    }
                } else if (res.getResultRemark() != null && res.getResultRemark() != 0
                        && rule.getReflexRemark() != null && rule.getReflexRemark() != 0) {
                    try {
                        Remarks remark = rmdao.GetRemark(rule.getReflexRemark(), true);
                        if (remark != null && remark.getIdremarks() != null && res.getResultRemark().intValue() == remark.getIdremarks().intValue())
                        {
                            Tests test = tdao.GetTestByNumber(rule.getRemarkTest());
                            CreateAndInsertReflexedResults(test, res);
                            reflexed++;
                        }
                    } catch (SQLException ex) {
                        SysLogDAO.Add(1, "ResultPostBL::ReflexResult:Remark Error", ex.getMessage());
                    }
                }
            }
        }
        return reflexed;
    }

    /**
     * Sets the add-on flag on a result detail line. Used to mark reflexed
     * results.
     *
     * @param idResult The Result ID (not result detail Id) of the result
     */
    private void FlagAsAddOn(int idResults, boolean isAddOn) {
        if (idResults == 0) {
            System.out.println("ResultPostBL::FlagAsAddOn: Passed a null or "
                    + "un-saved result object when setting add-on flag");
            return;
        }

        Object detailObj = rddao.getByID(idResults);
        if (detailObj != null && detailObj instanceof ResultDetails) {
            try {
                ResultDetails details = (ResultDetails) detailObj;
                if (details.getIdResults() == null) {
                    details = new ResultDetails();
                }

                if (details.getIdResults().equals(0)) {
                    details.setIdResults(idResults);
                }
                details.setIsAddOn(isAddOn);
                if (!rddao.Update(details)) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                System.out.println("ResultPostBL::FlagAsAddOn: Unable to set "
                        + " result details add-on flag for result Id: " + String.valueOf(idResults));
            }
        }
    }

    private void CreateAndInsertReflexedResults(Tests test, Results res) {
        overlappingPanels = (overlappingPanels != null && overlappingPanels == true);

        //If it is a single test insert it and go to next rule
        ArrayList<Results> newResults = new ArrayList<>();
        if (test.getTestType() == 1) {
            Results reflexRes = CreateReflexedResult(res);
            reflexRes.setTestId(test.getIdtests());
            newResults.add(reflexRes);
        } else if (test.getTestType() == 0) //This is a panel/battery
        {
            Results reflexRes = CreateReflexedResult(res);

            reflexRes.setTestId(test.getIdtests());
            newResults.add(reflexRes); //Add the initial Panel/Header test
            ArrayList<Tests> subtests = tdao.GetSubtestsIncludingInactive(test);
            //Build all the results that need to be added
            for (Tests t : subtests) {
                if (t.getTestType() != 0) { //For single tests
                    reflexRes = CreateReflexedResult(res);
                    reflexRes.setTestId(t.getIdtests());
                    reflexRes.setPanelId(test.getIdtests());
                    newResults.add(reflexRes);
                } else if (t.getTestType() == 0) {
                    reflexRes = CreateReflexedResult(res);
                    reflexRes.setTestId(t.getIdtests());
                    reflexRes.setPanelId(test.getIdtests());
                    newResults.add(reflexRes);
                    ArrayList<Tests> sub = tdao.GetSubtestsIncludingInactive(t);
                    for (Tests t2 : sub) {
                        reflexRes = CreateReflexedResult(res);
                        reflexRes.setTestId(t2.getIdtests());
                        reflexRes.setPanelId(t.getIdtests());
                        newResults.add(reflexRes);
                    }
                }
            }
        }

        //Insert all new results and flags them all as add-ons
        for (Results r : newResults) {
            try {
                int orderId = r.getOrderId();
                int testId = r.getTestId();
                Integer panelId = r.getPanelId();
                Panels panel = null;
                // Ensure that the result does not already exist on the order
                // before attempting to insert. If client doesn't support
                // overlapping panels, just check for the test.
                if (!overlappingPanels) {
                    panelId = null;
                }
                if (!TestExistsOnOrder(panelId, testId, orderId, overlappingPanels)) {
                    // Is this optional?
                    if (r.getPanelId() != null && r.getPanelId().equals(0) == false) {
                        try {
                            panel = pandao.GetPanel(r.getPanelId(), r.getTestId());
                            if (panel == null) {
                                throw new SQLException(
                                        "Null panel object returned for panelId: "
                                        + r.getPanelId() + " testId: "
                                        + r.getTestId());
                            }
                            r.setOptional(panel.isOptional());
                        } catch (SQLException ex) {
                            System.out.println("Error checking panel for optional setting. " + ex.getMessage());
                            SysLogDAO.Add(1, "ResultPostBL::CreateAndInsertReflexedResults: "
                                    + "Error checking panel for optional setting", ex.getMessage());
                        }
                    }

                    Integer newResultId = rdao.InsertResultGetID(r);
                    if (newResultId == null || newResultId == 0) {
                        throw new SQLException();
                    }

                    // Add on flag exists on result details table
                    FlagAsAddOn(newResultId, true);
                }
            } catch (SQLException ex) {
                System.out.println("Could not INSERT reflex test: " + ex.toString());
            }
        }
    }

    /**
     * Returns true if the test number / panel number combination exists on this
     * order and is not deleted/deactivated.
     *
     * If panelId is supplied, it will check to see if the panelId/testId
     * combination is present. If panelId is null and exactMatch is False, it
     * will just check for the test. If panelId is null and exactMatch is True,
     * it will consider NULL panels when making the match
     *
     * e.g. If ExactMatch is true and test 123 exists on the order under a
     * panel. If you provide test 123 without a panel as a parameter, this
     * method will return False. If ExactMatch was False, it would see the test
     * as present and would return True.
     *
     * @param panelId Can be null if the test is not part of a panel
     * @param testId
     * @param orderId
     * @return
     */
    private boolean TestExistsOnOrder(Integer panelId, int testId, int orderId, boolean exactMatch) {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return false;
        }

        try {
            // Get the panel number and test number
            Tests test = tdao.GetTestByID(testId);
            Tests panel = null;
            if (panelId != null && panelId != 0) {
                panel = tdao.GetTestByID(panelId);
            }

            // Check for the presence of this test/panel combination on the order
            // using test number, and excluding deleted or invalidated results.
            String sql = "SELECT COUNT(*) "
                    + " FROM results r "
                    + " INNER JOIN tests t ON r.testId = t.idtests "
                    + " LEFT JOIN tests t2 ON r.panelId = t2.idtests "
                    + " WHERE "
                    + " (r.resultText IS NULL OR (r.resultText IS NOT NULL AND r.resultText != 'DELETED')) "
                    + " AND (r.isInvalidated IS NULL OR (r.isInvalidated IS NOT NULL AND r.isInvalidated = 0)) "
                    + " AND r.orderId = " + String.valueOf(orderId)
                    + " AND t.number = " + String.valueOf(test.getNumber());

            if (panel != null && panel.getNumber() > 0) {
                sql += " AND t2.number = " + String.valueOf(panel.getNumber());
            } else // No panel
            {
                if (exactMatch) {
                    sql += " AND t2.number IS NULL";
                }
            }

            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            boolean testExists = false;

            if (rs.next()) {
                int count = rs.getInt(1);
                testExists = (count > 0);
            }
            return testExists;
        } catch (SQLException ex) {
            System.out.println("ResultPostBL::TestExistsOnOrder: Error attempting to find presence of test: "
                    + "PanelId: " + ((panelId == null) ? "null" : panelId.toString()) + ", TestId: " + String.valueOf(testId));
        }
        return false;
    }

    private Results CreateReflexedResult(Results res) {
        Results reflex = new Results();
        java.util.Date now = new java.util.Date();
        reflex.setOrderId(res.getOrderId());
        reflex.setCreated(now);
        reflex.setIsApproved(false);
        reflex.setIsInvalidated(false);
        reflex.setIsUpdated(false);
        reflex.setUpdatedDate(now);
        reflex.setIsAbnormal(false);
        reflex.setIsHigh(false);
        reflex.setIsLow(false);
        reflex.setIsCIDHigh(false);
        reflex.setIsCIDLow(false);
        reflex.setNoCharge(false);
        reflex.setHl7Transmitted(false);
        reflex.setPrintAndTransmitted(false);

        return reflex;
    }

    /**
     * Looks to see if there are any no-charged remarks on the order, sets
     * result(s) to no-charged accordingly, and logs the action(s) taken. Rules:
     * - A single test with a no-charge remark gets no-charged - A single test
     * with a no-charge remark under a panel causes the panel header to become
     * no-charged. If noChargeSubtests is true, it also sets the test itself to
     * no-charged. - A single test with a no-charge remark directly under a
     * battery gets no-charged
     *
     * Note: If a no-charge remark is removed and this method is run again, it
     * will not remove no-charge flags, as there's currently no way to tell
     * whether that no-charge flag was done manually or automatically.
     *
     * @param idorders Order Id to process
     * @param userId The current user's Id
     * @param noChargeSubtest If true, test AND panel header are no-charged (if
     * applicable)
     * @return
     */
    public boolean HandleNoChargeRemarks(int idorders, int userId, boolean noChargeSubtest) {
        try {
            if (con.isValid(2) == false) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }

        // For each result that has a no-charge remark, get its containing panel
        // result id (if available) whether that parent panel is a battery, and
        // information about that result row
        String sql = "SELECT"
                + " (SELECT idresults FROM results WHERE orderId = r.orderid AND testId = r.panelId AND isInvalidated = 0) AS ParentResultId,"
                + " (CASE WHEN t.testType = 0 AND t.header = 1 THEN TRUE ELSE FALSE END) AS ParentIsBattery,"
                + " t.testType, "
                + " r.idresults AS TestResultId, "
                + " r.noCharge, "
                + " rem.remarkNo, "
                + " t.number, "
                + " t.idtests "
                + " FROM results r"
                + " INNER JOIN remarks rem ON r.resultRemark = rem.idremarks"
                + " INNER JOIN tests t ON r.testId = t.idtests "
                + " WHERE r.isApproved = 1 AND rem.noCharge = 1 AND r.orderid = " + idorders + " AND r.isInvalidated = 0";

        try {
            PreparedStatement pStmt = con.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {

                // The parent of the result row with a no-charge remark
                Integer parentResultId = rs.getInt("ParentResultId");

                // Whether the parent test is a battery
                Boolean parentIsBattery = rs.getBoolean("ParentIsBattery");

                // The result row with the no-charge remark
                Integer testResultId = rs.getInt("TestResultId");

                // Other info about the result with the no-charge remark
                Integer testNumber = rs.getInt("number");
                Integer testId = rs.getInt("idtests");
                Integer remarkNo = rs.getInt("remarkNo");
                Integer testType = rs.getInt("testType");
                Boolean testAlreadyNoCharged = rs.getBoolean("noCharge");

                ResultPostLog rpl = new ResultPostLog();
                rpl.setIdOrders(idorders);
                rpl.setIdResults(testResultId);
                rpl.setIdTests(testId);
                rpl.setIdUser(userId);
                rpl.setCreatedDate(new java.util.Date());

                // ---------------  Single no-charged test: --------------------
                // -------------------------------------------------------------
                if (testResultId > 0 && parentResultId == 0) {
                    // No need to update
                    if (testAlreadyNoCharged) {
                        continue;
                    }

                    Results result = rdao.GetResultByID(testResultId);
                    result.setNoCharge(true);
                    boolean success = rdao.UpdateResult(result);
                    if (!success) {
                        System.out.println("Unable to set no-charge flag to single"
                                + " test for resultId " + result.getIdResults());
                        continue;
                    }
                    rpl.setAction("Test No-Charged From Remark");
                    rpl.setDescription("Test # " + testNumber + ", Remark # " + remarkNo);
                } // -----------  No-charged test in a non-battery panel: --------
                // -------------------------------------------------------------
                else if (testResultId > 0 && parentResultId > 0 && parentIsBattery == false) {
                    // Whether we need to log changes:
                    boolean resultUpdated = false;

                    boolean success;

                    // Mark the panel header as no-charged
                    Results panelResult = rdao.GetResultByID(parentResultId);
                    if (panelResult.isNoCharge() == false) {
                        panelResult.setNoCharge(true);
                        success = rdao.UpdateResult(panelResult);
                        if (!success) {
                            System.out.println("Unable to set no-charge flag to panel "
                                    + " header for resultId " + panelResult.getIdResults());
                            continue;
                        }
                        resultUpdated = true;
                    }

                    // If breaking down panels for billing, also set the result
                    // itself to no-charged
                    if (noChargeSubtest) {
                        Results testResult = rdao.GetResultByID(testResultId);
                        if (testAlreadyNoCharged == false) {
                            testResult.setNoCharge(true);
                            success = rdao.UpdateResult(testResult);
                            if (!success) {
                                System.out.println("Unabkle to set no-charge flag to "
                                        + "test number panel header for resultId = "
                                        + testResult.getIdResults());
                                continue;
                            }
                            resultUpdated = true;
                        }
                    }
                    // If nothing changed, there's nothing to log
                    if (resultUpdated == false) {
                        continue;
                    }
                    rpl.setAction("Panel Header No-Charged From Subtest Remark");
                    rpl.setDescription("Subtest # " + testNumber + ", Remark # " + remarkNo);
                } // --- No-charged single test directly under a battery header:--
                // -------------------------------------------------------------
                else if (testResultId > 0 && parentResultId > 0
                        && parentIsBattery == true && testType == 1) {
                    // No need to update:
                    if (testAlreadyNoCharged) {
                        continue;
                    }

                    Results result = rdao.GetResultByID(testResultId);
                    result.setNoCharge(true);
                    boolean success = rdao.UpdateResult(result);
                    if (!success) {
                        System.out.println("Unable to set no-charge flag to "
                                + "single test for resultId " + result.getIdResults());
                        continue;
                    }
                    rpl.setAction("Test No-Charged From Remark");
                    rpl.setDescription("Test # " + testNumber + ", Remark # " + remarkNo);
                }

                logdao.InsertLog(LogDAO.LogTable.ResultPost, rpl);
            }
        } catch (SQLException ex) {
            System.out.println("Error while attempting to process no-charge"
                    + " remarks " + ex.getMessage());
            return false;
        }
        return true;
    }
}
