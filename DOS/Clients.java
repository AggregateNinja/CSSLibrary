package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @date:   Mar 21, 2012
 * @author: CSS Dev
 * 
 * @project:  
 * @package: DOS
 * @file name: Clients.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity
@Table(name = "clients")
@NamedQueries(
{
    @NamedQuery(name = "Clients.findAll", query = "SELECT c FROM Clients c")
})
public class Clients implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClients")
    private Integer idClients;
    @Basic(optional = false)
    @Column(name = "clientNo")
    private int clientNo;
    @Column(name = "clientName")
    private String clientName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "clientStreet")
    private String clientStreet;
    @Column(name = "clientStreet2")
    private String clientStreet2;
    @Column(name = "clientCity")
    private String clientCity;
    @Column(name = "clientState")
    private String clientState;
    @Column(name = "clientZip")
    private String clientZip;
    @Column(name = "phoneNo")
    private String phoneNo;
    @Column(name = "faxNo")
    private String faxNo;
    @Column(name = "transmitNo")
    private String transmitNo;
    @Column(name = "licenseNo")
    private String licenseNo;
    @Column(name = "upin")
    private String upin;
    @Column(name = "otherId")
    private String otherId;
    @Column(name = "npi")
    private String npi;
    @Basic(optional = false)
    @Column(name = "webEnabled")
    private boolean webEnabled;
    @Column(name = "resultCopies")
    private Integer resultCopies;
    @Column(name = "feeSchedule")
    private Integer feeSchedule;
    @Column(name = "route")
    private Integer route;
    @Column(name = "stopNo")
    private String stopNo;
    @Column(name = "pickupTime")
    @Temporal(TemporalType.TIME)
    private Date pickupTime;
    @Column(name = "salesmen")
    private Integer salesmen;
    @Basic(optional = false)
    @Column(name = "location")
    private int location;
    @Column(name = "resReport1")
    private String resReport1;
    @Column(name = "resReport2")
    private String resReport2;
    @Column(name = "resReport3")
    private String resReport3;
    @Basic(optional = false)
    @Column(name = "billType")
    private int billType;
    @Basic(optional = false)
    @Column(name = "resPrint")
    private int resPrint;
    @Basic(optional = false)
    @Column(name = "transType")
    private int transType;
    @Basic(optional = false)
    @Column(name = "statCode")
    private int statCode;
    @Basic(optional = false)
    @Column(name = "contact1")
    private String contact1;
    @Basic(optional = false)
    @Column(name = "contact2")
    private String contact2;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "hl7Enabled")
    private Boolean hl7Enabled;
    @Basic(optional = false)
    @Lob
    @Column(name = "cliComment")
    private byte[] cliComment;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "clientType")
    private int clientType;
    @Column(name = "procedureset")
    private Integer procedureset;
    @Column(name = "percentDiscount")
    private Long percentDiscount;
    @Column(name = "discountVolume")
    private Integer discountVolume;
    @Column(name = "referenceDiscount")
    private Long referenceDiscount;
    @Column(name = "defaultReportType")
    private Integer defaultReportType;
    @Column(name = "billingId")
    private Integer billingId;
    @Column(name = "transmitEMRPdf")
    private Boolean transmitEMRPdf;
    @Column(name = "pdfToOBX")
    private Boolean pdfToOBX;
    @Column(name = "emrInterface")
    private Integer emrInterface;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "ppsBilling")
    private Boolean ppsBilling;
    @Column(name = "stmtTerms")
    private Integer stmtTerms;
    @Column(name = "stmtComment")
    private String stmtComment;
    
    public Clients()
    {
    }

    public Clients(Integer idClients)
    {
        this.idClients = idClients;
    }

    public Clients(Integer idClients, int clientNo, String clientName, String clientStreet, boolean webEnabled, int location, int billType, int resPrint, int transType, int statCode, String contact1, String contact2, Boolean hl7Enabled, byte[] cliComment, int clientType)
    {
        this.idClients = idClients;
        this.clientNo = clientNo;
        this.clientName = clientName;
        this.clientStreet = clientStreet;
        this.webEnabled = webEnabled;
        this.location = location;
        this.billType = billType;
        this.resPrint = resPrint;
        this.transType = transType;
        this.statCode = statCode;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.hl7Enabled = hl7Enabled;
        this.cliComment = cliComment;
        this.clientType = clientType;
    }

    @Diff(fieldName="idClients", isUniqueId=true)
    public Integer getIdClients()
    {
        return idClients;
    }

    public void setIdClients(Integer idClients)
    {
        this.idClients = idClients;
    }

    @Diff(fieldName="clientNo")
    public int getClientNo()
    {
        return clientNo;
    }

    public void setClientNo(int clientNo)
    {
        this.clientNo = clientNo;
    }

    @Diff(fieldName="clientName")
    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    @Diff(fieldName="clientStreet")
    public String getClientStreet()
    {
        return clientStreet;
    }

    public void setClientStreet(String clientStreet)
    {
        this.clientStreet = clientStreet;
    }

    @Diff(fieldName="clientStreet2")
    public String getClientStreet2()
    {
        return clientStreet2;
    }

    public void setClientStreet2(String clientStreet2)
    {
        this.clientStreet2 = clientStreet2;
    }

    @Diff(fieldName="clientCity")
    public String getClientCity()
    {
        return clientCity;
    }

    public void setClientCity(String clientCity)
    {
        this.clientCity = clientCity;
    }

    @Diff(fieldName="clientState")
    public String getClientState()
    {
        return clientState;
    }

    public void setClientState(String clientState)
    {
        this.clientState = clientState;
    }

    @Diff(fieldName="clientZip")
    public String getClientZip()
    {
        return clientZip;
    }

    public void setClientZip(String clientZip)
    {
        this.clientZip = clientZip;
    }

    @Diff(fieldName="phoneNo")
    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    @Diff(fieldName="faxNo")
    public String getFaxNo()
    {
        return faxNo;
    }

    public void setFaxNo(String faxNo)
    {
        this.faxNo = faxNo;
    }

    @Diff(fieldName="transmitNo")
    public String getTransmitNo()
    {
        return transmitNo;
    }

    public void setTransmitNo(String transmitNo)
    {
        this.transmitNo = transmitNo;
    }

    @Diff(fieldName="licenseNo")
    public String getLicenseNo()
    {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo)
    {
        this.licenseNo = licenseNo;
    }

    @Diff(fieldName="uPin")
    public String getUpin()
    {
        return upin;
    }

    public void setUpin(String upin)
    {
        this.upin = upin;
    }

    @Diff(fieldName="otherId")
    public String getOtherId()
    {
        return otherId;
    }

    public void setOtherId(String otherId)
    {
        this.otherId = otherId;
    }

    @Diff(fieldName="npi")
    public String getNpi()
    {
        return npi;
    }

    public void setNpi(String npi)
    {
        this.npi = npi;
    }

    @Diff(fieldName="webEnabled")
    public boolean getWebEnabled()
    {
        return webEnabled;
    }

    public void setWebEnabled(boolean webEnabled)
    {
        this.webEnabled = webEnabled;
    }

    @Diff(fieldName="resultCopies")
    public Integer getResultCopies()
    {
        return resultCopies;
    }

    public void setResultCopies(Integer resultCopies)
    {
        this.resultCopies = resultCopies;
    }

    @Diff(fieldName="feeSchedule")
    public Integer getFeeSchedule()
    {
        return feeSchedule;
    }

    public void setFeeSchedule(Integer feeSchedule)
    {
        this.feeSchedule = feeSchedule;
    }

    @Diff(fieldName="route")
    public Integer getRoute()
    {
        return route;
    }

    public void setRoute(Integer route)
    {
        this.route = route;
    }

    @Diff(fieldName="stopNo")
    public String getStopNo()
    {
        return stopNo;
    }

    public void setStopNo(String stopNo)
    {
        this.stopNo = stopNo;
    }

    @Diff(fieldName="pickupTime")
    public Date getPickupTime()
    {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime)
    {
        this.pickupTime = pickupTime;
    }

    @Diff(fieldName="salesmen")
    public Integer getSalesmen()
    {
        return salesmen;
    }

    public void setSalesmen(Integer salesmen)
    {
        this.salesmen = salesmen;
    }

    @Diff(fieldName="location")
    public int getLocation()
    {
        return location;
    }

    public void setLocation(int location)
    {
        this.location = location;
    }

    @Diff(fieldName="resReport1")
    public String getResReport1()
    {
        return resReport1;
    }

    public void setResReport1(String resReport1)
    {
        this.resReport1 = resReport1;
    }

    @Diff(fieldName="resReport2")
    public String getResReport2()
    {
        return resReport2;
    }

    public void setResReport2(String resReport2)
    {
        this.resReport2 = resReport2;
    }

    @Diff(fieldName="resReport3")
    public String getResReport3()
    {
        return resReport3;
    }

    public void setResReport3(String resReport3)
    {
        this.resReport3 = resReport3;
    }

    @Diff(fieldName="billType")
    public int getBillType()
    {
        return billType;
    }

    public void setBillType(int billType)
    {
        this.billType = billType;
    }

    @Diff(fieldName="resPrint")
    public int getResPrint()
    {
        return resPrint;
    }

    public void setResPrint(int resPrint)
    {
        this.resPrint = resPrint;
    }

    @Diff(fieldName="transType")
    public int getTransType()
    {
        return transType;
    }

    public void setTransType(int transType)
    {
        this.transType = transType;
    }

    @Diff(fieldName="statCode")
    public int getStatCode()
    {
        return statCode;
    }

    public void setStatCode(int statCode)
    {
        this.statCode = statCode;
    }

    @Diff(fieldName="contact1")
    public String getContact1()
    {
        return contact1;
    }

    public void setContact1(String contact1)
    {
        this.contact1 = contact1;
    }

    @Diff(fieldName="contact2")
    public String getContact2()
    {
        return contact2;
    }

    public void setContact2(String contact2)
    {
        this.contact2 = contact2;
    }

    @Diff(fieldName="hl7Enabled")
    public Boolean getHl7Enabled()
    {
        return hl7Enabled;
    }

    public void setHl7Enabled(Boolean hl7Enabled)
    {
        this.hl7Enabled = hl7Enabled;
    }

    @Diff(fieldName="cliComment")
    public byte[] getCliComment()
    {
        return cliComment;
    }

    public void setCliComment(byte[] cliComment)
    {
        this.cliComment = cliComment;
    }

    @Diff(fieldName="password")
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Diff(fieldName="clientType")
    public int getClientType()
    {
        return clientType;
    }

    public void setClientType(int clientType)
    {
        this.clientType = clientType;
    }

    @Diff(fieldName="procedureset")
    public Integer getProcedureset()
    {
        return procedureset;
    }

    public void setProcedureset(Integer procedureset)
    {
        this.procedureset = procedureset;
    }

    @Diff(fieldName="percentDiscount")
    public Long getPercentDiscount()
    {
        return percentDiscount;
    }

    public void setPercentDiscount(Long percentDiscount)
    {
        this.percentDiscount = percentDiscount;
    }

    @Diff(fieldName="discountVolume")
    public Integer getDiscountVolume()
    {
        return discountVolume;
    }

    public void setDiscountVolume(Integer discountVolume)
    {
        this.discountVolume = discountVolume;
    }

    @Diff(fieldName="referenceDiscount")
    public Long getReferenceDiscount()
    {
        return referenceDiscount;
    }

    public void setReferenceDiscount(Long referenceDiscount)
    {
        this.referenceDiscount = referenceDiscount;
    }

    @Diff(fieldName="defaultReportType")
    public Integer getDefaultReportType()
    {
        return defaultReportType;
    }

    public void setDefaultReportType(Integer defaultReportType)
    {
        this.defaultReportType = defaultReportType;
    }

    @Diff(fieldName="billingId")
    public Integer getBillingId()
    {
        return billingId;
    }

    public void setBillingId(Integer billingId)
    {
        this.billingId = billingId;
    }

    @Diff(fieldName="email")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Diff(fieldName="lastName")
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Diff(fieldName="firstName")
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Diff(fieldName="title")
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Diff(fieldName="transmitEMRPdf")
    public Boolean getTransmitEMRPdf()
    {
        return transmitEMRPdf;
    }

    public void setTransmitEMRPdf(Boolean transmitEMRPdf)
    {
        this.transmitEMRPdf = transmitEMRPdf;
    }

    @Diff(fieldName="pdfToOBX")
    public Boolean isPdfToOBX()
    {
        return pdfToOBX;
    }

    public void setPdfToOBX(Boolean pdfToOBX)
    {
        this.pdfToOBX = pdfToOBX;
    }

    @Diff(fieldName="emrInterface")
    public Integer getEmrInterface()
    {
        return emrInterface;
    }

    public void setEmrInterface(Integer emrInterface)
    {
        this.emrInterface = emrInterface;
    }

    @Diff(fieldName="active")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
    
    @Diff(fieldName = "ppsBilling")
    public Boolean isPPSBilling()
    {
        return ppsBilling;
    }
    
    public void setPPSBilling(Boolean ppsBilling)
    {
        this.ppsBilling = ppsBilling;
    }
    
    @Diff(fieldName = "stmtTerms")
    public Integer getStmtTerms()
    {
        return stmtTerms;
    }
    
    public void setStmtTerms(Integer stmtTerms)
    {
        this.stmtTerms = stmtTerms;
    }
    
    @Diff(fieldName = "stmtComment")
    public String getStmtComment()
    {
        return stmtComment;
    }
    
    public void setStmtComment(String stmtComment)
    {
        this.stmtComment =  stmtComment;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idClients != null ? idClients.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clients))
        {
            return false;
        }
        Clients other = (Clients) object;
        if ((this.idClients == null && other.idClients != null) || (this.idClients != null && !this.idClients.equals(other.idClients)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "(" + clientNo + ") " + (clientName != null ? clientName : (lastName != null ? lastName : ""));
    }

}
