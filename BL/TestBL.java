

package BL;

import DAOS.CalculationsDAO;
import DAOS.PanelDAO;
import DAOS.PreferencesDAO;
import DAOS.TestDAO;
import DAOS.TestLogDAO;
import DOS.Calculations;
import DOS.Panels;
import DOS.Preferences;
import DOS.TestLog;
import DOS.Tests;
import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ryan
 */

public class TestBL {
    
    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public TestBL(){
        
    }
    
    public boolean IsTestBattery(int testid){
        return true;
    }
    
    /**
     * This method has been developed for use with Calculations, but may be 
     * used for other purposes. It returns an ArrayList of Tests objects, these
     * tests are in the same panels as the test passed in, even if multiple the 
     * test passed in is in multiple panels.
     * @param test Tests object 
     * @return ArrayList<Tests> of all tests in the same Panel(s) as passed in test.
     */
    public ArrayList<Tests> TestsInPanelWith(Tests test){
        try{
            ArrayList<Tests> testList = new ArrayList<>();
            ArrayList<Panels> panelList = new ArrayList<>();
            Set<Integer> testHash = new HashSet<>();
            PanelDAO pdao = new PanelDAO();
            TestDAO tdao = new TestDAO();
            
            //Get the panels this Calulation/Test is part of
            panelList = (ArrayList<Panels>) pdao.GetParentPanels(test.getIdtests());
            
            //Now get all tests in those panels
            for(Panels p : panelList){
                ArrayList<Panels> plist = new ArrayList<>();
                Tests t = new Tests();
                plist = (ArrayList<Panels>) pdao.GetPanels(p.getIdpanels());
                
                for(Panels pan : plist){
                    t = new Tests();
                    t = tdao.GetTestByID(pan.getSubtestId());
                    //Make sure the same test isn't added twice
                    if(testHash.add(t.getIdtests())){
                        testList.add(t);
                    }
                } 
            }
            
            //Remove the instances of the past in tests
            testList.removeAll(Collections.singleton(test));
            
            return testList;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    /**
     * Construct a Test calculation as a string
     * @param num Calculation test number
     * @return String of the calculation
     */
    public String CalculationToString(int num) {
        try {
            TestDAO tdao = new TestDAO();
            CalculationsDAO cdao = new CalculationsDAO();
            Tests calc = tdao.GetTestOrCalcByNumber(num);
            ArrayList<Calculations> steps = cdao.GetCalculationsByTestID(calc.getIdtests());
            
            String CALC = "";
            int totalSteps = steps.size();
            int count = 1;
            String operator = "";
            
            while (count <= totalSteps) {
                for (Calculations c : steps) {
                    if (c.getStep() == count) {
                        
                        if(c.getOperator() == null || c.getOperator().isEmpty())
                            operator = "";
                        else
                            operator = c.getOperator();
                        
                        if(c.getNumericValue() == 0.0 && c.getResultValueId() == 0){
                            CALC += " " + operator;
                            count++;
                        }else if (c.getResultValueId() != 0.0) { //First check if it is a value from another tests result
                            Tests tmp = tdao.GetTestByID(c.getResultValueId().intValue());
                            CALC += "   <" + tmp.getAbbr() + "  -# " + tmp.getNumber()
                                    + ">   " + operator;
                            count++;
                        } else if(c.getNumericValue() != 0) {
                            CALC += c.getNumericValue() + " " + operator;
                            count++;
                        }
                    }
                }
            }
            
            return CALC;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Tests GetActiveTestFromID(int id){
        try{
            int number = 0;
            Tests test = new Tests();
            TestDAO tdao = new TestDAO();
            
            //First get the Test Number
            number = tdao.GetTestNumberByID(id);
            
            //Second get the active test with the fetched number
            test = tdao.GetTestOrCalcByNumber(number);
            
            return test;
        }catch(Exception ex){
            System.out.println("TestBL::GetActiveTestFromID - "+ex.toString());
            return null;
        }
    }
    
    public Tests GetTestFromPanelByOrder(int panelId, int position){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            TestDAO tdao = new TestDAO();
            PanelDAO pdao = new PanelDAO();
            Tests test = tdao.GetTestByID(pdao.GetSubTestID(panelId, position));
            
            return test;
        }catch(Exception ex){
            System.out.println("TestBL::GetTestFromPanelByOrder - "+ex.toString());
            return null;
        }
    }
    
    /**
     * Returns the test ids associated with Point of Care. User can specify
     *  whether to return the panel header row. Null on error.
     * @param includePanelRow
     * @return 
     */
    public HashSet<Integer> getPOCTestIDs(boolean includePanelRow)
    {
        HashSet<Integer> results = new HashSet();
        
        // POC panel row defined by the client preference table
        PreferencesDAO pDao = new PreferencesDAO();
        Preferences p = pDao.GetPreference("POCTest");
        Integer pocTestId = -1;
        if (p != null)
        {
            try
            {
                pocTestId = Integer.parseInt(p.getValue());
                PanelDAO panelDAO = new PanelDAO();
                results = panelDAO.GetSubTestIDs(pocTestId);
                if (includePanelRow) results.add(pocTestId);
            }
            catch (Exception ex) { return null; }
            
        }
        return results;        
    }
    
    /**
     * Retrieves the Active Batteries that Contain a specific testId
     * @param oldId int idtests of previous version(Deactivated) test
     * @param newId int idtests of newly Updated Test
     * @param userID User's ID who is making these changes
     * @return List<Integer> of updated tests/Panels
     */
    public List<Integer> UpdatePanelsForTestChange(int oldId, int newId, int userID){
        
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try {
            List<Integer> parents = new ArrayList<>();
            List<Integer> grandParents = new ArrayList<>();
            List<Integer> newPanelIDs = new ArrayList<>();
            
            //Get any Panels this test belongs in
            parents = GetActiveParentPanles(oldId);
            
            //Now get any Batteries that each panel is in
            for(Integer panelID : parents){
                grandParents = GetActiveGrandParentPanles(panelID);
                
                //First have to make a new parent
                Integer n_parentId = CopyPanelWithNewTest(panelID, oldId, newId, userID);
                newPanelIDs.add(n_parentId);
                
                //Now we have to make new batteries for each Grandparent
                for(Integer batteryId : grandParents){
                    Integer n_grandParentId = CopyPanelWithNewTest(batteryId, panelID, n_parentId, userID);
                    newPanelIDs.add(n_grandParentId);
                }
            }

            return newPanelIDs;
        } catch (Exception ex) {
            System.out.println("TestBL::ActiveBatteriesThatContain - " + ex.toString());
            return null;
        }
    }
    
    /**
     * Fetches the Active Panels that contain the passed in test id
     * @param testid int idTests or subtestId
     * @return 
     */
    public List<Integer> GetActiveParentPanles(int testid){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            List<Integer> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT p.idpanels "
                + "FROM panels p "
                + "LEFT JOIN tests t "
                + "	ON p.subtestId = t.idtests "
                + "LEFT JOIN tests t2 " 
                + "	ON p.idpanels = t2.idtests "
                + "WHERE p.subtestId = " + testid + " "
                + "AND t2.active = true";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()){
                Integer i = rs.getInt(1);
                if(i != null && i > 0){
                    list.add(i);
                }
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("TestBL::GetActiveParentPanels - "+ex.toString());
            return null;
        }
    }
    
    
    /**
     * Gets passed in Active panelid and returns ALL Active Battery ids that contain
     * the Active Panelid.
     * @param panelIds List<Integer> of idpanels
     * @return 
     */
    private List<Integer> GetActiveGrandParentPanles(List<Integer> panelIds){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            List<Integer> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            String inClause = "";
            int count = 0;
          
            for (Integer x : panelIds) {
                if(count == 0){
                    inClause = String.valueOf(x);
                } else {
                    inClause += ","+String.valueOf(x);
                }
            }
            
            String query = "SELECT p.idpanels "
                + "FROM panels p "
                + "LEFT JOIN tests t "
                + "	ON p.subtestId = t.idtests "
                + "LEFT JOIN tests t2 " 
                + "	ON p.idpanels = t2.idtests "
                + "WHERE p.subtestId IN (" + inClause +") " 
                + "AND t2.active = true";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()){
                Integer i = rs.getInt(1);
                if(i != null && i > 0){
                    list.add(i);
                }
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("TestBL::GetActiveGrandParentPanles - "+ex.toString());
            return null;
        }
    }
    
    /**
     * Gets passed Active panel id's and returns Active Battery ids that contain
     * the Active Panel.
     * @param panelId int of idpanel
     * @return 
     */
    private List<Integer> GetActiveGrandParentPanles(int panelId){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            List<Integer> list = new ArrayList<>();
            Statement stmt = con.createStatement();
            
            String query = "SELECT p.idpanels "
                + "FROM panels p "
                + "LEFT JOIN tests t "
                + "	ON p.subtestId = t.idtests "
                + "LEFT JOIN tests t2 " 
                + "	ON p.idpanels = t2.idtests "
                + "WHERE p.subtestId = " + panelId
                + " AND t2.active = true";
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()){
                Integer i = rs.getInt(1);
                if(i != null && i > 0){
                    list.add(i);
                }
            }
            
            rs.close();
            stmt.close();
            
            return list;
        }catch(Exception ex){
            System.out.println("TestBL::GetActiveGrandParentPanles - "+ex.toString());
            return null;
        }
    }
    
    public Integer GetTestIDWhenOrdered(Integer testNumber, String accession)
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
            Statement stmt = con.createStatement();
            String query = "select" +
                " tl.oldtestid" +
                " from orders o "+
                " inner join results r on r.orderId = o.idorders" +
                " left join test_log tl on r.testId = tl.oldtestid" +
                " left join tests t on tl.oldtestid = t.idtests" +
                " where tl.created > r.created and tl.oldtestid != tl.newtestid" +
                " and tl.testnumber = "  + testNumber.toString() +
                " and o.accession = " + accession +
                " order by tl.created asc limit 1";
            
            ResultSet rs = stmt.executeQuery(query);
            Integer result = null;
            while (rs.next())
            {
                result = rs.getInt(1);
            }
            
            TestDAO testDao = new TestDAO();
            if (result == null)
            {
                result = testDao.GetTestByNumber(testNumber).getIdtests();
            }
            
            rs.close();
            stmt.close();
            
            return result;
            
        }catch(Exception ex){
            System.out.println("TestBL::GetActiveGrandParentPanles - "+ex.toString());
            return null;
        }        
    }
    
    /**
     * Copies an Existing Panel while substituting one of it's subtests. It 
     * deactivates the existing panel as well.
     * @param oldPanelId The current Panel ID that needs to be copied
     * @param oldTestId The subtest that needs to be replaced
     * @param newTestId The new subtest that replaces the old subtest
     * @return The new Panel's ID
     */
    private Integer CopyPanelWithNewTest(int oldPanelId, int oldTestId, int newTestId, int userId){
        try{
            //Init Data Access Objects
            PanelDAO pdao = new PanelDAO();
            TestDAO tdao = new TestDAO();
            
            //First need to make a new Test Panel
            Tests o_Test = tdao.GetTestByID(oldPanelId);
            Tests n_Test = new Tests();
            //Deactivate old panel
            tdao.DeactivateByID(o_Test.getIdtests());
            //Log the table change
            CopyPanelWithNewTestLogger("DEACTIVATE",0,0,o_Test,null,userId);
            //Insert and create a new Panel Test
            boolean testCreated = tdao.InsertTest(o_Test);
            //Verify that it was created, and get the new Tests Object
            if(testCreated){
                n_Test = tdao.GetTestByNumber(o_Test.getNumber());
            } else {
                System.out.println("TestBL::CopyPanelWithNewTest -- New Test couldn't be created!");
                return null;
            }
            //Log the new test insertion
            CopyPanelWithNewTestLogger("INSERTED",oldPanelId,0,null,n_Test,userId);
            
            //Now we need to copy the Panels information except substituting the new IDs
            List<Panels> subtestList = new ArrayList<>();
            subtestList = pdao.GetPanels(oldPanelId);
            Panels panel = new Panels();
            //Now loop through list inserting new Panels with updated information
            for(Panels sub : subtestList){
                panel = new Panels();
                panel = sub;
                panel.setIdpanels(n_Test.getIdtests());
                
                //Check if this loop contain the new subtest
                if(panel.getSubtestId() == oldTestId){
                    panel.setSubtestId(newTestId);
                }
                
                //Insert updated Panels
                pdao.InsertPanel(panel);
            }
            
            //Return the new Panel/Test ID
            return n_Test.getIdtests();
        }catch(Exception ex){
            System.out.println("TestBL::CopyPanelWithNewTest - "+ex.toString());
            return null;
        }
    }
    
    private void CopyPanelWithNewTestLogger(String ACTION, int oldTestId, int newTestId, Tests oldTest, Tests newTest, int userId){
        try{
            TestLogDAO tldao = new TestLogDAO();
            TestLog log = new TestLog();
            java.util.Date now = new java.util.Date();
            
            switch(ACTION){
                case "DEACTIVATE":
                    log.setEventType("Panel Deactivated by Subtest Update");
                    log.setTestNumber(oldTest.getNumber());
                    log.setTestName(oldTest.getName());
                    log.setOldTestID(oldTest.getIdtests());
                    log.setNewTestID(oldTest.getIdtests());
                    break;
                case "INSERTED":
                    log.setEventType("Panel Copied/Inserted by Subtest Update");
                    log.setTestNumber(newTest.getNumber());
                    log.setTestName(newTest.getName());
                    log.setOldTestID(oldTestId);
                    log.setNewTestID(newTest.getIdtests());
                    break;
                    
            }
            
            log.setUser(userId);
            log.setCreated(now);
            tldao.InsertTestLog(log);
            
        }catch(SQLException ex){
            System.out.println("TestBL::CopyPanelWithNewTestLogger - "+ex.toString());
        }
    }
    
    /**
     * Returns true if the supplied *active* Test Number has any orders attached, 
     *  regardless of status.
     * @param testNumber
     * @return 
     */
    public Boolean HasOrders(int testNumber)
    {
        // Check reference lab boolean
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
            // Grab the active test for this number
            TestDAO testdao = new TestDAO();
            Tests test = testdao.GetTestByNumber(testNumber);
            
            // Error; could not grab the test for this number.
            if (test == null || test.getIdtests() == null) return null;
            
            Statement stmt = con.createStatement();
            String query = "SELECT COUNT(*)" +
                    " FROM orders WHERE testId =  " + 
                    String.valueOf(test.getIdtests());

            ResultSet rs = stmt.executeQuery(query);
            Integer result = null;
            while (rs.next())
            {
                result = rs.getInt(1);
            }

            return (result > 0);
        }
        catch (SQLException ex) { return null;} 
    }
    
    /**
     * Checks the department for the supplied test ID and returns true if
     *  it is a send-out lab.
     * @param idTests
     * @return 
     */
    public boolean IsSendOutTest(int idTests)
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
            Statement stmt = con.createStatement();
            
            String query = "SELECT" +
                    " ReferenceLab FROM departments d" +
                    " inner join tests t" +
                    " on t.department = d.idDepartment" +
                    " WHERE t.idtests = " + String.valueOf(idTests);
            
            ResultSet rs = stmt.executeQuery(query);
            Integer result = null;
            while (rs.next())
            {
                result = rs.getInt(1);
            }
            
            return (result != null);
        }
        catch (SQLException ex) {} 
        return false;
    }
    
    
    /**
     * Given an order id, gets every unique test attached, regardless of status.
     * 
     * @param idOrders
     * @return ArrayList of unique Tests attached to the order; null on error
     */
    public ArrayList<Tests> GetAllTestsForOrder(int idOrders)
    {
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        TestDAO testdao = new TestDAO();
        ArrayList<Tests> tests = new ArrayList<>(); // The list of results
        
        try
        {
            String sql =
                "SELECT DISTINCT"
                + " t.idTests"
                + " FROM tests t"
                + " INNER JOIN results r on r.testId = t.idtests"
                + " WHERE r.orderId = " + idOrders;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Integer result = null;
            while (rs.next())
            {
                tests.add(testdao.GetTestByID(rs.getInt(1)));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to get all tests for order " + idOrders);
            return null;
        }        
        return tests;
    }

    /**
     * Returns the Creatinine number or numbers associated with the GFR. 
     * 
     * First checks for the preference GFRCreatinineTestNumber. If present but
     *  NULL, tries to get the GFRCreatinineTestAbbr which will retrieve any
     *  test number defined with that test abbreviation
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    public List<Integer> GetGFRCreatinineTestNumbers() throws SQLException
    {
        List<Integer> creatinineTestNumbers = new LinkedList<>();
        
        // Is the single creatinine test number defined?
        // (this is how things were working before labs started running
        //  creatinine tests on multiple instruments)
        PreferencesDAO pdao = new PreferencesDAO();
        Integer creatinineTestNumber = pdao.getInteger("GFRCreatinineTestNumber");
        if (creatinineTestNumber != null && creatinineTestNumber > 0)
        {
            creatinineTestNumbers.add(creatinineTestNumber);
            return creatinineTestNumbers;
        }
        
        // Otherwise see if there's a test abbreviation associated with the
        // creatinine test and retrive all test(s) matching that.
        String creatinineTestAbbr = pdao.getString("GFRCreatinineTestAbbr");
        if (creatinineTestAbbr != null && creatinineTestAbbr.trim().isEmpty() == false)
        {
            // Get the distinct test numbers for tests that match this abbr
            String sql = "SELECT DISTINCT(number) AS creatinineAbbr FROM tests WHERE abbr = ? AND active = 1";
            PreparedStatement pStmt = con.prepareStatement(sql);
            pStmt.setString(1, creatinineTestAbbr);
            
            ResultSet rs = pStmt.executeQuery();
            while (rs.next())
            {
                creatinineTestNumbers.add(rs.getInt("creatinineAbbr"));
            }
            pStmt.close();
            rs.close();
        }
        return creatinineTestNumbers;
    }
}
