/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import Database.CheckDBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @date: 9/10/2014
 * @author: Derrick Piper <derrick@csslis.com>
 * @project: Avalon LIS
 * @package: BL
 * @file name: UserGroupAccessBL.java
 * @Description:
 * Computer Service & Support, Inc. All Rights Reserved
 */
public class UserGroupAccessBL
{
    //private Object pStmt;

    public static enum AccessButtons
    {

        // Toolbar
        Home("Home"),
        Patients("Patients"),
        Results("Results"),
        Reports("Reports"),
        Configuration("Configuration"),
        QC("QC"),
        Billing("Billing"),
        AdminSettings("AdminSettings"),
        
        // Patients
        OrderEntry("OrderEntry"),
        PatientConfiguration("PatientConfiguration"),
        SubscriberConfiguration("SubscriberConfiguration"),
        SchedulingCalendar("SchedulingCalendar"),
        WebOrderReview("WebOrderReview"),
        AdvancedOrders("AdvancedOrders"),
        BatchLabelPrint("BatchLabelPrint"),
        
        // Results
        ResultInquiry("ResultInquiry"),
        ResultPosting("ResultPosting"),
        AutomatedPosting("AutomatedPosting"),
        Approval("Approval"),
        PrintTransmit("PrintTransmit"),
        WorkQueue("WorkQueue"),
        TranslationalInterface("TranslationalInterface"),
        ReferenceLabResults("ReferenceLabResults"),
        MicrobiologyResults("MicrobiologyResults"),
        
        // Reports
        Production("Production"),
        Statistics("Statistics"),
        Sales("Sales"),

        // Configuration
        Clients("Clients"),
        Cpt("Cpt"),
        DiagnosisValidity("DiagnosisValidity"),
        Doctors("Doctors"),
        Tests("Tests"),
        Insurance("Insurance"),
        Remarks("Remarks"),
        DrugsMetabolites("DrugsMetabolites"),
        Counselors("Counselors"),
        Locations("Locations"),
        Departments("Departments"),
        Instruments("Instruments"),
        Manufacturers("Manufacturers"),
        Salesmen("Salesmen"),
        SalesGroups("SalesGroups"),
        Microbiology("Microbiology"),
        DiagnosisCodes("DiagnosisCodeMaintenance"),
        
        // QC
        Inventory("Inventory"),
        ControlConfiguration("ControlConfiguration"),
        RuleConfiguration("RuleConfiguration"),
        RunApproval("RunApproval"),
        ReviewPreviousRuns("ReviewPreviousRuns"),
        QCReports("QCReports"),
        
        // Admin
        UserMaintenance("UserMaintenance"),
        Preferences("Preferences"),
        InterfaceSettings("InterfaceSettings"),
        ProductionLog("ProductionLog"),
        TestConfigurationLog("TestConfigurationLog"),

        // Billing sidebar
        BillingTransmit("BillingTransmit"),
        ClaimSubmission("ClaimSubmission"),
        AccountInquiry("AccountInquiry"),
        AccountSummary("AccountSummary"),
        ManualPayment("ManualPayment"),
        AutomaticReconciliation("AutomaticReconciliation"),
        Events("Events"),
        BillingReports("BillingReports");
        
        String value;

        AccessButtons(String v)
        {
            this.value = v;
        }

        @Override
        public String toString()
        {
            return value;
        }
    }

    Database.DatabaseSingleton dbs = Database.DatabaseSingleton.getDatabaseSingleton();
    Connection con = dbs.getConnection(true);
    
    public Boolean ReadOnlyAccess(Integer userID, AccessButtons access) throws SQLException
    {
        try
        {
            if (con.isClosed() || !con.isValid(2))
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.toString());
            return false;
        }
        Boolean retValue = false;
        Statement pStmt = con.createStatement();
        
        // if ReadOnly<AccessButton> exists and is not 0 then access should be read only
        String query
                = "SELECT sec.`ReadOnly" + access.toString() + "` FROM sec_groupAccess sec "
                + "LEFT JOIN userGroups ug "
                + "ON sec.userGroup = ug.iduserGroups "
                + "LEFT JOIN users u "
                + "ON u.ugroup = ug.iduserGroups "
                + "WHERE u.idUser = " + userID;

        try
        {
            ResultSet rs = pStmt.executeQuery(query);
            if(rs.next())
            {
                retValue = rs.getBoolean(1);
            }
            return retValue;
        }
        catch (SQLException ex)
        {
            // most likely the read only column didn't exist for this access button
            return false;
        }
    }
    
    public Boolean CheckUserAccess(Integer userID, AccessButtons access) throws SQLException
    {
        try
        {
            if (con.isClosed() || !con.isValid(2))
            {
                con = CheckDBConnection.Check(dbs, con);
            }
        }
        catch (SQLException sex)
        {
            System.out.println(sex.toString());
            return false;
        }
        Boolean retValue = false;
        Statement pStmt = con.createStatement();
        
        String query
                = "SELECT sec.`" + access.toString() + "` FROM sec_groupAccess sec "
                + "LEFT JOIN userGroups ug "
                + "ON sec.userGroup = ug.iduserGroups "
                + "LEFT JOIN users u "
                + "ON u.ugroup = ug.iduserGroups "
                + "WHERE u.idUser = " + userID;

        ResultSet rs = pStmt.executeQuery(query);
        if(rs.next())
        {
            retValue = rs.getBoolean(1);
        }
        return retValue;
    }
}