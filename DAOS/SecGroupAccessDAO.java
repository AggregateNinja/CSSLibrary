/*
 * Computer Service & Support, Inc.  All Rights Reserved Sep 3, 2014
 */
package DAOS;

import DAOS.IDAOS.DAOInterface;
import DAOS.IDAOS.IStructureCheckable;
import DOS.SecGroupAccess;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date: Sep 3, 2014 4:05:00 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: SecGroupAccessDAO.java (UTF-8)
 *
 * @Description:
 *
 */
public class SecGroupAccessDAO implements DAOInterface, IStructureCheckable {

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    private final String table = "`sec_groupAccess`";

    private final ArrayList<String> fields = new ArrayList<>();

    /*
     * All fields in table except id
     */
    public SecGroupAccessDAO() {
        fields.add("userGroup");
        fields.add("Home");
        fields.add("Patients");
        fields.add("Results");
        fields.add("Reports");
        fields.add("Configuration");
        fields.add("QC");
        fields.add("AdminSettings");
        fields.add("OrderEntry");
        fields.add("PatientConfiguration");
        fields.add("SubscriberConfiguration");
        fields.add("SchedulingCalendar");
        fields.add("WebOrderReview");
        fields.add("AdvancedOrders");
        fields.add("DiagnosisCodeMaintenance");
        fields.add("ResultInquiry");
        fields.add("ResultPosting");
        fields.add("AutomatedPosting");
        fields.add("Approval");
        fields.add("PrintTransmit");
        fields.add("WorkQueue");
        fields.add("TranslationalInterface");
        fields.add("ReferenceLabResults");
        fields.add("MicrobiologyResults");
        fields.add("Production");
        fields.add("Statistics");
        fields.add("Sales");
        fields.add("Clients");
        fields.add("Doctors");
        fields.add("Tests");
        fields.add("Insurance");
        fields.add("Remarks");
        fields.add("DrugsMetabolites");
        fields.add("Counselors");
        fields.add("Locations");
        fields.add("Departments");
        fields.add("Instruments");
        fields.add("Manufacturers");
        fields.add("Salesmen");
        fields.add("SalesGroups");
        fields.add("Microbiology");
        fields.add("UserMaintenance");
        fields.add("Preferences");
        fields.add("InterfaceSettings");
        fields.add("ProductionLog");
        fields.add("TestConfigurationLog");
        fields.add("Inventory");
        fields.add("ControlConfiguration");
        fields.add("RuleConfiguration");
        fields.add("RunApproval");
        fields.add("ReviewPreviousRuns");
        fields.add("QCReports");
        fields.add("ReadOnlyOrderEntry");
        fields.add("ReadOnlyPatientConfiguration");
        fields.add("ReadOnlySubscriberConfiguration");
        fields.add("ReadOnlyClients");
        fields.add("ReadOnlyDoctors");
        fields.add("ReadOnlyTests");
        fields.add("ReadOnlyInsurance");
        fields.add("ReadOnlyRemarks");
        fields.add("ReadOnlyDrugsMetabolites");
        fields.add("ReadOnlyCounselors");
        fields.add("ReadOnlyLocations");
        fields.add("ReadOnlyDepartments");
        fields.add("ReadOnlyInstruments");
        fields.add("ReadOnlyManufacturers");
        fields.add("ReadOnlySalesmen");
        fields.add("ReadOnlySalesGroups");
        fields.add("ReadOnlyMicrobiology");
        fields.add("Billing");
        fields.add("ClaimSubmission");
        fields.add("ReadOnlyClaimSubmission");
        fields.add("AccountInquiry");
        fields.add("ReadOnlyAccountInquiry");
        fields.add("AccountSummary");
        fields.add("ManualPayment");
        fields.add("AutomaticReconciliation");
        fields.add("ReadOnlyAutomaticReconciliation");
        fields.add("Events");
        fields.add("BillingReports");
        fields.add("Cpt");
        fields.add("DiagnosisValidity");
        fields.add("FeeSchedule");
        fields.add("BillingAccountInquiry");
        fields.add("BillingTransmit");
        
    }

    @Override
    public Boolean Insert(Serializable obj) {
        SecGroupAccess sga = (SecGroupAccess) obj;
        
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateInsertStatement(fields);
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromGroupAccess(sga, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
            
        }catch(SQLException ex){
            Logger.getLogger(SecGroupAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception inserting SecGroupAccess: " + ex.toString());
            return false;
        }
    }

    @Override
    public Boolean Update(Serializable obj) {
        SecGroupAccess sga = (SecGroupAccess) obj;
        
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `id` = "
                    + sga.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromGroupAccess(sga, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
            
        }catch(SQLException ex){
            Logger.getLogger(SecGroupAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception updateing SecGroupAccess: " + ex.toString());
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

    @Override
    public Serializable getByID(Integer ID) {
        try{
            return GetSecGroupAccessByID(ID);
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public Boolean UpdateByUserGroup(Serializable obj) {
        SecGroupAccess sga = (SecGroupAccess) obj;
        
        try{
            try {
                if (con.isClosed()) {
                    con = CheckDBConnection.Check(dbs, con);
                }
            } catch (SQLException sex) {
                System.out.println(sex.toString());
                return false;
            }
            
            String stmt = GenerateUpdateStatement(fields) + " WHERE `userGroup` = "
                    + sga.getId();
            PreparedStatement pStmt = con.prepareStatement(stmt);
            SetStatementFromGroupAccess(sga, pStmt);
            
            pStmt.executeUpdate();
            
            pStmt.close();
            
            return true;
            
        }catch(SQLException ex){
            Logger.getLogger(SecGroupAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception updateing SecGroupAccess: " + ex.toString());
            return false;
        }
    }
    
    public SecGroupAccess GetSecGroupAccessByID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SecGroupAccess sga = new SecGroupAccess();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `id` = " + ID;
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetGroupAccessFromResultSet(sga, rs);
            }
            
            rs.close();
            stmt.close();
            
            return sga;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
    
    public SecGroupAccess GetSecGroupAccessGroupID(int ID){
        try {
            if (con.isClosed()) {
                con = CheckDBConnection.Check(dbs, con);
            }
        } catch (SQLException sex) {
            System.out.println(sex.toString());
            return null;
        }
        
        try{
            SecGroupAccess sga = new SecGroupAccess();
            String query = "SELECT * FROM " + table + " "
                    + "WHERE `userGroup` = " + ID;
            
            Statement stmt = con.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            if(rs.next()){
                SetGroupAccessFromResultSet(sga, rs);
            }
            
            rs.close();
            stmt.close();
            
            return sga;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
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

    private SecGroupAccess SetGroupAccessFromResultSet(SecGroupAccess sga, ResultSet rs) throws SQLException
    {
        sga.setId(rs.getInt("id"));
        sga.setUserGroup(rs.getInt("userGroup"));
        sga.setHome(rs.getBoolean("Home"));
        sga.setPatients(rs.getBoolean("Patients"));
        sga.setResults(rs.getBoolean("Results"));
        sga.setReports(rs.getBoolean("Reports"));
        sga.setConfiguration(rs.getBoolean("Configuration"));
        sga.setQc(rs.getBoolean("QC"));
        sga.setAdminSettings(rs.getBoolean("AdminSettings"));
        sga.setOrderEntry(rs.getBoolean("OrderEntry"));
        sga.setPatientConfiguration(rs.getBoolean("PatientConfiguration"));
        sga.setSubscriberConfiguration(rs.getBoolean("SubscriberConfiguration"));
        sga.setSchedulingCalendar(rs.getBoolean("SchedulingCalendar"));
        sga.setWebOrderReview(rs.getBoolean("WebOrderReview"));
        sga.setAdvancedOrders(rs.getBoolean("AdvancedOrders"));
        sga.setDiagnosisCodeMaintenance(rs.getBoolean("DiagnosisCodeMaintenance"));
        sga.setResultInquiry(rs.getBoolean("ResultInquiry"));
        sga.setResultPosting(rs.getBoolean("ResultPosting"));
        sga.setAutomatedPosting(rs.getBoolean("AutomatedPosting"));
        sga.setApprovaL(rs.getBoolean("Approval"));
        sga.setPrintTransmit(rs.getBoolean("PrintTransmit"));
        sga.setWorkQueue(rs.getBoolean("WorkQueue"));
        sga.setTranslationalInterface(rs.getBoolean("TranslationalInterface"));
        sga.setReferenceLabResults(rs.getBoolean("ReferenceLabResults"));
        sga.setMicrobiologyResults(rs.getBoolean("MicrobiologyResults"));
        sga.setProduction(rs.getBoolean("Production"));
        sga.setStatistics(rs.getBoolean("Statistics"));
        sga.setSales(rs.getBoolean("Sales"));
        sga.setClients(rs.getBoolean("Clients"));
        sga.setDoctors(rs.getBoolean("Doctors"));
        sga.setTests(rs.getBoolean("Tests"));
        sga.setInsurance(rs.getBoolean("Insurance"));
        sga.setRemarks(rs.getBoolean("Remarks"));
        sga.setDrugsMetabolites(rs.getBoolean("DrugsMetabolites"));
        sga.setCounselors(rs.getBoolean("Counselors"));
        sga.setLocations(rs.getBoolean("Locations"));
        sga.setDepartments(rs.getBoolean("Departments"));
        sga.setInstruments(rs.getBoolean("Instruments"));
        sga.setManufacturers(rs.getBoolean("Manufacturers"));
        sga.setSalesmen(rs.getBoolean("Salesmen"));
        sga.setSalesGroups(rs.getBoolean("SalesGroups"));
        sga.setMicrobiology(rs.getBoolean("Microbiology"));
        sga.setUserMaintenance(rs.getBoolean("UserMaintenance"));
        sga.setPreferences(rs.getBoolean("Preferences"));
        sga.setInterfaceSettings(rs.getBoolean("InterfaceSettings"));
        sga.setProductionLog(rs.getBoolean("ProductionLog"));
        sga.setTestConfigurationLog(rs.getBoolean("TestConfigurationLog"));
        sga.setInventory(rs.getBoolean("Inventory"));
        sga.setControlConfiguration(rs.getBoolean("ControlConfiguration"));
        sga.setRuleConfiguration(rs.getBoolean("RuleConfiguration"));
        sga.setRunApproval(rs.getBoolean("RunApproval"));
        sga.setReviewPreviousRuns(rs.getBoolean("ReviewPreviousRuns"));
        sga.setQcReports(rs.getBoolean("QCReports"));
        
        sga.setOrderEntryRead(rs.getBoolean("ReadOnlyOrderEntry"));
        sga.setPatientConfigurationRead(rs.getBoolean("ReadOnlyPatientConfiguration"));
        sga.setSubscriberConfigurationRead(rs.getBoolean("ReadOnlySubscriberConfiguration"));
        
        sga.setClientsRead(rs.getBoolean("ReadOnlyClients"));
        sga.setDoctorsRead(rs.getBoolean("ReadOnlyDoctors"));
        sga.setTestsRead(rs.getBoolean("ReadOnlyTests"));
        sga.setInsuranceRead(rs.getBoolean("ReadOnlyInsurance"));
        sga.setRemarksRead(rs.getBoolean("ReadOnlyRemarks"));
        sga.setDrugsMetabolitesRead(rs.getBoolean("ReadOnlyDrugsMetabolites"));
        sga.setCounselorsRead(rs.getBoolean("ReadOnlyCounselors"));
        sga.setLocationsRead(rs.getBoolean("ReadOnlyLocations"));
        sga.setDepartmentsRead(rs.getBoolean("ReadOnlyDepartments"));
        sga.setInstrumentsRead(rs.getBoolean("ReadOnlyInstruments"));
        sga.setManufacturersRead(rs.getBoolean("ReadOnlyManufacturers"));
        sga.setSalesmenRead(rs.getBoolean("ReadOnlySalesmen"));
        sga.setSalesGroupsRead(rs.getBoolean("ReadOnlySalesGroups"));
        sga.setMicrobiologyRead(rs.getBoolean("ReadOnlyMicrobiology"));

        sga.setBilling(rs.getBoolean("Billing"));
        sga.setClaimSubmission(rs.getBoolean("ClaimSubmission"));
        sga.setReadOnlyClaimSubmission(rs.getBoolean("ReadOnlyClaimSubmission"));
        sga.setAccountInquiry(rs.getBoolean("AccountInquiry"));
        sga.setReadOnlyAccountInquiry(rs.getBoolean("ReadOnlyAccountInquiry"));
        sga.setAccountSummary(rs.getBoolean("AccountSummary"));
        sga.setManualPayment(rs.getBoolean("ManualPayment"));
        sga.setAutomaticReconciliation(rs.getBoolean("AutomaticReconciliation"));
        sga.setReadOnlyAutomaticReconciliation(rs.getBoolean("ReadOnlyAutomaticReconciliation"));
        sga.setEvents(rs.getBoolean("Events"));
        sga.setBillingReports(rs.getBoolean("BillingReports"));
        sga.setCpt(rs.getBoolean("Cpt"));
        sga.setDiagnosisValidity(rs.getBoolean("DiagnosisValidity"));
        sga.setFeeSchedule(rs.getBoolean("FeeSchedule"));
        sga.setBillingAccountInquiry(rs.getBoolean("BillingAccountInquiry"));
        sga.setBillingTransmit(rs.getBoolean("BillingTransmit"));
        
        return sga;
    }

    private PreparedStatement SetStatementFromGroupAccess(SecGroupAccess sga, PreparedStatement pStmt) throws SQLException
    {
        int i=0;
        SQLUtil.SafeSetInteger(pStmt, ++i, sga.getUserGroup());
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getHome(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getPatients(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getResults(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReports(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getConfiguration(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getQc(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAdminSettings(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getOrderEntry(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getPatientConfiguration(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSubscriberConfiguration(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSchedulingCalendar(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getWebOrderReview(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAdvancedOrders(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDiagnosisCodeMaintenance(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getResultInquiry(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getResultPosting(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAutomatedPosting(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getApprovaL(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getPrintTransmit(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getWorkQueue(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getTranslationalInterface(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReferenceLabResults(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getMicrobiologyResults(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getProduction(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getStatistics(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSales(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getClients(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDoctors(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getTests(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInsurance(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getRemarks(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDrugsMetabolites(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getCounselors(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getLocations(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDepartments(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInstruments(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getManufacturers(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSalesmen(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSalesGroups(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getMicrobiology(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getUserMaintenance(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getPreferences(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInterfaceSettings(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getProductionLog(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getTestConfigurationLog(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInventory(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getControlConfiguration(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getRuleConfiguration(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getRunApproval(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReviewPreviousRuns(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getQcReports(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getOrderEntryRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getPatientConfigurationRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSubscriberConfigurationRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getClientsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDoctorsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getTestsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInsuranceRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getRemarksRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDrugsMetabolitesRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getCounselorsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getLocationsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDepartmentsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getInstrumentsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getManufacturersRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSalesmenRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getSalesGroupsRead(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getMicrobiologyRead(), false);
        
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getBilling(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getClaimSubmission(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReadOnlyClaimSubmission(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAccountInquiry(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReadOnlyAccountInquiry(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAccountSummary(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getManualPayment(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getAutomaticReconciliation(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getReadOnlyAutomaticReconciliation(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getEvents(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getBillingReports(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getCpt(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getDiagnosisValidity(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getFeeSchedule(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getBillingAccountInquiry(), false);
        SQLUtil.SafeSetBoolean(pStmt, ++i, sga.getBillingTransmit(), false);
  
        return pStmt;
    }
    
    @Override
    public String structureCheck() {
        return DatabaseStructureCheck.structureCheck(fields, table, con);
    }

}
