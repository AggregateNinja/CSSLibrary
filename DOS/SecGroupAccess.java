/*
 * Computer Service & Support, Inc.  All Rights Reserved Sep 3, 2014
 */
package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date: Sep 3, 2014 1:52:32 PM
 * @author: Ryan Piper ryan@csslis.com
 *
 * @project: CSSLibrary
 * @file name: SecgroupAccess.java (UTF-8)
 *
 * @Description:
 *
 */
@Entity
@Table(name = "sec_groupAccess")
@NamedQueries(
{
    @NamedQuery(name = "SecgroupAccess.findAll", query = "SELECT s FROM SecgroupAccess s")
})
public class SecGroupAccess implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "userGroup")
    private int userGroup;
    @Column(name = "Home")
    private Boolean home;
    @Column(name = "Patients")
    private Boolean patients;
    @Column(name = "Results")
    private Boolean results;
    @Column(name = "Reports")
    private Boolean reports;
    @Column(name = "Configuration")
    private Boolean configuration;
    @Column(name = "QC")
    private Boolean qc;
    @Column(name = "Admin Settings")
    private Boolean adminSettings;
    @Column(name = "Order Entry")
    private Boolean orderEntry;
    @Column(name = "Patient Configuration")
    private Boolean patientConfiguration;
    @Column(name = "Subscriber Configuration")
    private Boolean subscriberConfiguration;
    @Column(name = "Scheduling Calendar")
    private Boolean schedulingCalendar;
    @Column(name = "Web Order Review")
    private Boolean webOrderReview;
    @Column(name = "Advanced Orders")
    private Boolean advancedOrders;
    @Column(name = "Diagnosis Code Maintenance")
    private Boolean diagnosisCodeMaintenance;
    @Column(name = "Result Inquiry")
    private Boolean resultInquiry;
    @Column(name = "Result Posting")
    private Boolean resultPosting;
    @Column(name = "Automated Posting")
    private Boolean automatedPosting;
    @Column(name = "ApprovaL")
    private Boolean approvaL;
    @Column(name = "Print & Transmit")
    private Boolean printTransmit;
    @Column(name = "WorkQueue")
    private Boolean workQueue;
    @Column(name = "Translational Interface")
    private Boolean translationalInterface;
    @Column(name = "Reference Lab Results")
    private Boolean referenceLabResults;
    @Column(name = "Microbiology Results")
    private Boolean microbiologyResults;
    @Column(name = "Production")
    private Boolean production;
    @Column(name = "Statistics")
    private Boolean statistics;
    @Column(name = "Sales")
    private Boolean sales;
    @Column(name = "Clients")
    private Boolean clients;
    @Column(name = "Doctors")
    private Boolean doctors;
    @Column(name = "Tests")
    private Boolean tests;
    @Column(name = "Insurance")
    private Boolean insurance;
    @Column(name = "Remarks")
    private Boolean remarks;
    @Column(name = "Drugs & Metabolites")
    private Boolean drugsMetabolites;
    @Column(name = "Counselors")
    private Boolean counselors;
    @Column(name = "Locations")
    private Boolean locations;
    @Column(name = "Departments")
    private Boolean departments;
    @Column(name = "Instruments")
    private Boolean instruments;
    @Column(name = "Manufacturers")
    private Boolean manufacturers;
    @Column(name = "Salesmen")
    private Boolean salesmen;
    @Column(name = "Sales Groups")
    private Boolean salesGroups;
    @Column(name = "Microbiology")
    private Boolean microbiology;
    @Column(name = "User Maintenance")
    private Boolean userMaintenance;
    @Column(name = "Preferences")
    private Boolean preferences;
    @Column(name = "Interface Settings")
    private Boolean interfaceSettings;
    @Column(name = "Production Log")
    private Boolean productionLog;
    @Column(name = "Test Configuration Log")
    private Boolean testConfigurationLog;
    @Column(name = "Inventory")
    private Boolean inventory;
    @Column(name = "Control Configuration")
    private Boolean controlConfiguration;
    @Column(name = "Rule Configuration")
    private Boolean ruleConfiguration;
    @Column(name = "Run Approval")
    private Boolean runApproval;
    @Column(name = "Review Previous Runs")
    private Boolean reviewPreviousRuns;
    @Column(name = "Q.C.Reports")
    private Boolean qcReports;
    @Column(name = "Read Only Order Entry")
    private Boolean orderEntryRead;
    @Column(name = "Read Only Patient Configuration")
    private Boolean patientConfigurationRead;
    @Column(name = "Read Only Subscriber Configuration")
    private Boolean subscriberConfigurationRead;
    @Column(name = "Read Only Clients")
    private Boolean clientsRead;
    @Column(name = "Read Only Doctors")
    private Boolean doctorsRead;
    @Column(name = "Read Only Tests")
    private Boolean testsRead;
    @Column(name = "Read Only Insurance")
    private Boolean insuranceRead;
    @Column(name = "Read Only Remarks")
    private Boolean remarksRead;
    @Column(name = "Read Only Drugs & Metabolites")
    private Boolean drugsMetabolitesRead;
    @Column(name = "Read Only Counselors")
    private Boolean counselorsRead;
    @Column(name = "Read Only Locations")
    private Boolean locationsRead;
    @Column(name = "Read Only Departments")
    private Boolean departmentsRead;
    @Column(name = "Read Only Instruments")
    private Boolean instrumentsRead;
    @Column(name = "Read Only Manufacturers")
    private Boolean manufacturersRead;
    @Column(name = "Read Only Salesmen")
    private Boolean salesmenRead;
    @Column(name = "Read Only Sales Groups")
    private Boolean salesGroupsRead;
    @Column(name = "Read Only Microbiology")
    private Boolean microbiologyRead;
    @Column(name = "Billing")
    private Boolean billing;
    @Column(name = "Claim Submission")
    private Boolean claimSubmission;
    @Column(name = "Read Only Claim Submission")
    private Boolean readOnlyClaimSubmission;
    @Column(name = "Account Inquiry")
    private Boolean accountInquiry;
    @Column(name = "Read Only Account Inquiry")
    private Boolean readOnlyAccountInquiry;
    @Column(name = "Account Summary")
    private Boolean accountSummary;
    @Column(name = "Manual Payment")
    private Boolean manualPayment;
    @Column(name = "Automatic Reconciliation")
    private Boolean automaticReconciliation;
    @Column(name = "Read Only Automatic Reconciliation")
    private Boolean readOnlyAutomaticReconciliation;
    @Column(name = "Events")
    private Boolean events;
    @Column(name = "Billing Reports")
    private Boolean billingReports;
    @Column(name = "Cpt")
    private Boolean cpt;
    @Column(name = "Diagnosis Validity")
    private Boolean diagnosisValidity;
    @Column(name = "Fee Schedule")
    private Boolean feeSchedule;
    @Column(name = "Billing Account Inquiry")
    private Boolean billingAccountInquiry;
    @Column(name = "Billing Transmit")
    private Boolean billingTransmit;

    public SecGroupAccess()
    {
    }

    public SecGroupAccess(Integer id)
    {
        this.id = id;
    }

    public SecGroupAccess(Integer id, int userGroup)
    {
        this.id = id;
        this.userGroup = userGroup;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getUserGroup()
    {
        return userGroup;
    }

    public void setUserGroup(int userGroup)
    {
        this.userGroup = userGroup;
    }

    public Boolean getHome()
    {
        return home;
    }

    public void setHome(Boolean home)
    {
        this.home = home;
    }

    public Boolean getPatients()
    {
        return patients;
    }

    public void setPatients(Boolean patients)
    {
        this.patients = patients;
    }

    public Boolean getResults()
    {
        return results;
    }

    public void setResults(Boolean results)
    {
        this.results = results;
    }

    public Boolean getReports()
    {
        return reports;
    }

    public void setReports(Boolean reports)
    {
        this.reports = reports;
    }

    public Boolean getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(Boolean configuration)
    {
        this.configuration = configuration;
    }

    public Boolean getQc()
    {
        return qc;
    }

    public void setQc(Boolean qc)
    {
        this.qc = qc;
    }

    public Boolean getAdminSettings()
    {
        return adminSettings;
    }

    public void setAdminSettings(Boolean adminSettings)
    {
        this.adminSettings = adminSettings;
    }

    public Boolean getOrderEntry()
    {
        return orderEntry;
    }

    public void setOrderEntry(Boolean orderEntry)
    {
        this.orderEntry = orderEntry;
    }

    public Boolean getPatientConfiguration()
    {
        return patientConfiguration;
    }

    public void setPatientConfiguration(Boolean patientConfiguration)
    {
        this.patientConfiguration = patientConfiguration;
    }

    public Boolean getSubscriberConfiguration()
    {
        return subscriberConfiguration;
    }

    public void setSubscriberConfiguration(Boolean subscriberConfiguration)
    {
        this.subscriberConfiguration = subscriberConfiguration;
    }

    public Boolean getSchedulingCalendar()
    {
        return schedulingCalendar;
    }

    public void setSchedulingCalendar(Boolean schedulingCalendar)
    {
        this.schedulingCalendar = schedulingCalendar;
    }

    public Boolean getWebOrderReview()
    {
        return webOrderReview;
    }

    public void setWebOrderReview(Boolean webOrderReview)
    {
        this.webOrderReview = webOrderReview;
    }

    public Boolean getAdvancedOrders()
    {
        return advancedOrders;
    }

    public void setAdvancedOrders(Boolean advancedOrders)
    {
        this.advancedOrders = advancedOrders;
    }

    public Boolean getDiagnosisCodeMaintenance()
    {
        return diagnosisCodeMaintenance;
    }

    public void setDiagnosisCodeMaintenance(Boolean diagnosisCodeMaintenance)
    {
        this.diagnosisCodeMaintenance = diagnosisCodeMaintenance;
    }

    public Boolean getResultInquiry()
    {
        return resultInquiry;
    }

    public void setResultInquiry(Boolean resultInquiry)
    {
        this.resultInquiry = resultInquiry;
    }

    public Boolean getResultPosting()
    {
        return resultPosting;
    }

    public void setResultPosting(Boolean resultPosting)
    {
        this.resultPosting = resultPosting;
    }

    public Boolean getAutomatedPosting()
    {
        return automatedPosting;
    }

    public void setAutomatedPosting(Boolean automatedPosting)
    {
        this.automatedPosting = automatedPosting;
    }

    public Boolean getApprovaL()
    {
        return approvaL;
    }

    public void setApprovaL(Boolean approvaL)
    {
        this.approvaL = approvaL;
    }

    public Boolean getPrintTransmit()
    {
        return printTransmit;
    }

    public void setPrintTransmit(Boolean printTransmit)
    {
        this.printTransmit = printTransmit;
    }

    public Boolean getWorkQueue()
    {
        return workQueue;
    }

    public void setWorkQueue(Boolean workQueue)
    {
        this.workQueue = workQueue;
    }

    public Boolean getTranslationalInterface()
    {
        return translationalInterface;
    }

    public void setTranslationalInterface(Boolean translationalInterface)
    {
        this.translationalInterface = translationalInterface;
    }

    public Boolean getReferenceLabResults()
    {
        return referenceLabResults;
    }

    public void setReferenceLabResults(Boolean referenceLabResults)
    {
        this.referenceLabResults = referenceLabResults;
    }

    public Boolean getMicrobiologyResults()
    {
        return microbiologyResults;
    }

    public void setMicrobiologyResults(Boolean microbiologyResults)
    {
        this.microbiologyResults = microbiologyResults;
    }

    public Boolean getProduction()
    {
        return production;
    }

    public void setProduction(Boolean production)
    {
        this.production = production;
    }

    public Boolean getStatistics()
    {
        return statistics;
    }

    public void setStatistics(Boolean statistics)
    {
        this.statistics = statistics;
    }

    public Boolean getSales()
    {
        return sales;
    }

    public void setSales(Boolean sales)
    {
        this.sales = sales;
    }

    public Boolean getClients()
    {
        return clients;
    }

    public void setClients(Boolean clients)
    {
        this.clients = clients;
    }

    public Boolean getDoctors()
    {
        return doctors;
    }

    public void setDoctors(Boolean doctors)
    {
        this.doctors = doctors;
    }

    public Boolean getTests()
    {
        return tests;
    }

    public void setTests(Boolean tests)
    {
        this.tests = tests;
    }

    public Boolean getInsurance()
    {
        return insurance;
    }

    public void setInsurance(Boolean insurance)
    {
        this.insurance = insurance;
    }

    public Boolean getRemarks()
    {
        return remarks;
    }

    public void setRemarks(Boolean remarks)
    {
        this.remarks = remarks;
    }

    public Boolean getDrugsMetabolites()
    {
        return drugsMetabolites;
    }

    public void setDrugsMetabolites(Boolean drugsMetabolites)
    {
        this.drugsMetabolites = drugsMetabolites;
    }

    public Boolean getCounselors()
    {
        return counselors;
    }

    public void setCounselors(Boolean counselors)
    {
        this.counselors = counselors;
    }

    public Boolean getLocations()
    {
        return locations;
    }

    public void setLocations(Boolean locations)
    {
        this.locations = locations;
    }

    public Boolean getDepartments()
    {
        return departments;
    }

    public void setDepartments(Boolean departments)
    {
        this.departments = departments;
    }

    public Boolean getInstruments()
    {
        return instruments;
    }

    public void setInstruments(Boolean instruments)
    {
        this.instruments = instruments;
    }

    public Boolean getManufacturers()
    {
        return manufacturers;
    }

    public void setManufacturers(Boolean manufacturers)
    {
        this.manufacturers = manufacturers;
    }

    public Boolean getSalesmen()
    {
        return salesmen;
    }

    public void setSalesmen(Boolean salesmen)
    {
        this.salesmen = salesmen;
    }

    public Boolean getSalesGroups()
    {
        return salesGroups;
    }

    public void setSalesGroups(Boolean salesGroups)
    {
        this.salesGroups = salesGroups;
    }

    public Boolean getMicrobiology()
    {
        return microbiology;
    }

    public void setMicrobiology(Boolean microbiology)
    {
        this.microbiology = microbiology;
    }

    public Boolean getUserMaintenance()
    {
        return userMaintenance;
    }

    public void setUserMaintenance(Boolean userMaintenance)
    {
        this.userMaintenance = userMaintenance;
    }

    public Boolean getPreferences()
    {
        return preferences;
    }

    public void setPreferences(Boolean preferences)
    {
        this.preferences = preferences;
    }

    public Boolean getInterfaceSettings()
    {
        return interfaceSettings;
    }

    public void setInterfaceSettings(Boolean interfaceSettings)
    {
        this.interfaceSettings = interfaceSettings;
    }

    public Boolean getProductionLog()
    {
        return productionLog;
    }

    public void setProductionLog(Boolean productionLog)
    {
        this.productionLog = productionLog;
    }

    public Boolean getTestConfigurationLog()
    {
        return testConfigurationLog;
    }

    public void setTestConfigurationLog(Boolean testConfigurationLog)
    {
        this.testConfigurationLog = testConfigurationLog;
    }

    public Boolean getInventory()
    {
        return inventory;
    }

    public void setInventory(Boolean inventory)
    {
        this.inventory = inventory;
    }

    public Boolean getControlConfiguration()
    {
        return controlConfiguration;
    }

    public void setControlConfiguration(Boolean controlConfiguration)
    {
        this.controlConfiguration = controlConfiguration;
    }

    public Boolean getRuleConfiguration()
    {
        return ruleConfiguration;
    }

    public void setRuleConfiguration(Boolean ruleConfiguration)
    {
        this.ruleConfiguration = ruleConfiguration;
    }

    public Boolean getRunApproval()
    {
        return runApproval;
    }

    public void setRunApproval(Boolean runApproval)
    {
        this.runApproval = runApproval;
    }

    public Boolean getReviewPreviousRuns()
    {
        return reviewPreviousRuns;
    }

    public void setReviewPreviousRuns(Boolean reviewPreviousRuns)
    {
        this.reviewPreviousRuns = reviewPreviousRuns;
    }

    public Boolean getQcReports()
    {
        return qcReports;
    }

    public void setQcReports(Boolean qcReports)
    {
        this.qcReports = qcReports;
    }

    public Boolean getOrderEntryRead()
    {
        return orderEntryRead;
    }

    public void setOrderEntryRead(Boolean orderEntryRead)
    {
        this.orderEntryRead = orderEntryRead;
    }

    public Boolean getPatientConfigurationRead()
    {
        return patientConfigurationRead;
    }

    public void setPatientConfigurationRead(Boolean patientConfigurationRead)
    {
        this.patientConfigurationRead = patientConfigurationRead;
    }

    public Boolean getSubscriberConfigurationRead()
    {
        return subscriberConfigurationRead;
    }

    public void setSubscriberConfigurationRead(Boolean subscriberConfigurationRead)
    {
        this.subscriberConfigurationRead = subscriberConfigurationRead;
    }

    public Boolean getClientsRead()
    {
        return clientsRead;
    }

    public void setClientsRead(Boolean clientsRead)
    {
        this.clientsRead = clientsRead;
    }

    public Boolean getDoctorsRead()
    {
        return doctorsRead;
    }

    public void setDoctorsRead(Boolean doctorsRead)
    {
        this.doctorsRead = doctorsRead;
    }

    public Boolean getTestsRead()
    {
        return testsRead;
    }

    public void setTestsRead(Boolean testsRead)
    {
        this.testsRead = testsRead;
    }

    public Boolean getInsuranceRead()
    {
        return insuranceRead;
    }

    public void setInsuranceRead(Boolean insuranceRead)
    {
        this.insuranceRead = insuranceRead;
    }

    public Boolean getRemarksRead()
    {
        return remarksRead;
    }

    public void setRemarksRead(Boolean remarksRead)
    {
        this.remarksRead = remarksRead;
    }

    public Boolean getDrugsMetabolitesRead()
    {
        return drugsMetabolitesRead;
    }

    public void setDrugsMetabolitesRead(Boolean drugsMetabolitesRead)
    {
        this.drugsMetabolitesRead = drugsMetabolitesRead;
    }

    public Boolean getCounselorsRead()
    {
        return counselorsRead;
    }

    public void setCounselorsRead(Boolean counselorsRead)
    {
        this.counselorsRead = counselorsRead;
    }

    public Boolean getLocationsRead()
    {
        return locationsRead;
    }

    public void setLocationsRead(Boolean locationsRead)
    {
        this.locationsRead = locationsRead;
    }

    public Boolean getDepartmentsRead()
    {
        return departmentsRead;
    }

    public void setDepartmentsRead(Boolean departmentsRead)
    {
        this.departmentsRead = departmentsRead;
    }

    public Boolean getInstrumentsRead()
    {
        return instrumentsRead;
    }

    public void setInstrumentsRead(Boolean instrumentsRead)
    {
        this.instrumentsRead = instrumentsRead;
    }

    public Boolean getManufacturersRead()
    {
        return manufacturersRead;
    }

    public void setManufacturersRead(Boolean manufacturersRead)
    {
        this.manufacturersRead = manufacturersRead;
    }

    public Boolean getSalesmenRead()
    {
        return salesmenRead;
    }

    public void setSalesmenRead(Boolean salesmenRead)
    {
        this.salesmenRead = salesmenRead;
    }

    public Boolean getSalesGroupsRead()
    {
        return salesGroupsRead;
    }

    public void setSalesGroupsRead(Boolean salesGroupsRead)
    {
        this.salesGroupsRead = salesGroupsRead;
    }

    public Boolean getMicrobiologyRead()
    {
        return microbiologyRead;
    }

    public void setMicrobiologyRead(Boolean microbiologyRead)
    {
        this.microbiologyRead = microbiologyRead;
    }

    public Boolean getBilling()
    {
        return billing;
    }

    public void setBilling(Boolean billing)
    {
        this.billing = billing;
    }

    public Boolean getClaimSubmission()
    {
        return claimSubmission;
    }

    public void setClaimSubmission(Boolean claimSubmission)
    {
        this.claimSubmission = claimSubmission;
    }

    public Boolean getReadOnlyClaimSubmission()
    {
        return readOnlyClaimSubmission;
    }

    public void setReadOnlyClaimSubmission(Boolean readOnlyClaimSubmission)
    {
        this.readOnlyClaimSubmission = readOnlyClaimSubmission;
    }

    public Boolean getAccountInquiry()
    {
        return accountInquiry;
    }

    public void setAccountInquiry(Boolean accountInquiry)
    {
        this.accountInquiry = accountInquiry;
    }

    public Boolean getReadOnlyAccountInquiry()
    {
        return readOnlyAccountInquiry;
    }

    public void setReadOnlyAccountInquiry(Boolean readOnlyAccountInquiry)
    {
        this.readOnlyAccountInquiry = readOnlyAccountInquiry;
    }

    public Boolean getAccountSummary()
    {
        return accountSummary;
    }

    public void setAccountSummary(Boolean accountSummary)
    {
        this.accountSummary = accountSummary;
    }

    public Boolean getManualPayment()
    {
        return manualPayment;
    }

    public void setManualPayment(Boolean manualPayment)
    {
        this.manualPayment = manualPayment;
    }

    public Boolean getAutomaticReconciliation()
    {
        return automaticReconciliation;
    }

    public void setAutomaticReconciliation(Boolean automaticReconciliation)
    {
        this.automaticReconciliation = automaticReconciliation;
    }

    public Boolean getReadOnlyAutomaticReconciliation()
    {
        return readOnlyAutomaticReconciliation;
    }

    public void setReadOnlyAutomaticReconciliation(Boolean readOnlyAutomaticReconciliation)
    {
        this.readOnlyAutomaticReconciliation = readOnlyAutomaticReconciliation;
    }

    public Boolean getEvents()
    {
        return events;
    }

    public void setEvents(Boolean events)
    {
        this.events = events;
    }

    public Boolean getBillingReports()
    {
        return billingReports;
    }

    public void setBillingReports(Boolean billingReports)
    {
        this.billingReports = billingReports;
    }
    
    public Boolean getCpt()
    {
        return cpt;
    }
    
    public void setCpt(Boolean cpt)
    {
        this.cpt = cpt;
    }
    
    public Boolean getDiagnosisValidity()
    {
        return diagnosisValidity;
    }
    
    public void setDiagnosisValidity(Boolean diagnosisValidity)
    {
        this.diagnosisValidity = diagnosisValidity;
    }
    
    public Boolean getFeeSchedule()
    {
        return feeSchedule;
    }
    
    public void setFeeSchedule(Boolean feeSchedule)
    {
        this.feeSchedule = feeSchedule;
    }
    
    public Boolean getBillingAccountInquiry()
    {
        return billingAccountInquiry;
    }
    
    public void setBillingAccountInquiry(Boolean billingAccountInquiry)
    {
        this.billingAccountInquiry = billingAccountInquiry;
    }
    
    public Boolean getBillingTransmit()
    {
        return billingTransmit;
    }
    
    public void setBillingTransmit(Boolean billingTransmit)
    {
        this.billingTransmit = billingTransmit;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecGroupAccess))
        {
            return false;
        }
        SecGroupAccess other = (SecGroupAccess) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.SecgroupAccess[ id=" + id + " ]";
    }

}
