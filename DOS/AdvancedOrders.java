package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: AdvancedOrders.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "advancedOrders")
@NamedQueries(
{
    @NamedQuery(name = "AdvancedOrders.findAll", query = "SELECT a FROM AdvancedOrders a")
})
public class AdvancedOrders implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdvancedOrders")
    private int idAdvancedOrders;
    @Column(name = "doctorId")
    private Integer doctorId;
    @Basic(optional = false)
    @Column(name = "clientId")
    private int clientId;
    @Basic(optional = false)
    @Column(name = "accession")
    private String accession;
    @Basic(optional = false)
    @Column(name = "locationId")
    private int locationId;
    @Basic(optional = false)
    @Column(name = "orderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @Column(name = "specimenDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date specimenDate;
    @Basic(optional = false)
    @Column(name = "patientId")
    private int patientId;
    @Column(name = "subscriberId")
    private Integer subscriberId;
    @Column(name = "isAdvancedOrder")
    private boolean isAdvancedOrder;
    @Column(name = "room")
    private String room;
    @Column(name = "bed")
    private String bed;
    @Column(name = "isFasting")
    private Boolean isFasting;
    @Column(name = "insurance")
    private Integer insurance;
    @Column(name = "secondaryInsurance")
    private Integer secondaryInsurance;
    @Column(name = "policyNumber")
    private String policyNumber;
    @Column(name = "groupNumber")
    private String groupNumber;
    @Column(name = "secondaryPolicyNumber")
    private String secondaryPolicyNumber;
    @Column(name = "secondaryGroupNumber")
    private String secondaryGroupNumber;
    @Column(name = "medicareNumber")
    private String medicareNumber;
    @Column(name = "medicaidNumber")
    private String medicaidNumber;
    @Column(name = "reportType")
    private Integer reportType;
    @Column(name = "requisition")
    private Integer requisition;
    @Column(name = "billOnly")
    private Boolean billOnly;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "hold")
    private Boolean hold;
    @Column(name = "stage")
    private Integer stage;
    @Column(name = "holdComment")
    private String holdComment;
    @Column(name = "resultComment")
    private String resultComment;
    @Column(name = "internalComment")
    private String internalComment;
    @Column(name = "hl7Transmitted")
    private Boolean hl7Transmitted;
    @Column(name = "payment")
    private Double payment;
    @Column(name = "billable")
    private Boolean billable;
    @Column(name = "emrOrderId")
    private String emrOrderId;
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "DOI")
    private Date DOI;
    @Column(name = "EOA")
    private String EOA;
    @Column(name = "ReleaseJobID")
    private Integer releaseJobID;
    @Column(name = "releaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    @Basic(optional = false)
    @Column(name = "inactive")
    private Boolean inactive;
    
    public AdvancedOrders()
    {
    }

    public AdvancedOrders(int idAdvancedOrders, Date orderDate, int clientId, String accession, int locationId, Date specimenDate, int patientId)
    {
        this.idAdvancedOrders = idAdvancedOrders;
        this.orderDate = orderDate;
        this.clientId = clientId;
        this.accession = accession;
        this.locationId = locationId;
        this.specimenDate = specimenDate;
        this.patientId = patientId;
    }

    public int getIdAdvancedOrders() {
        return idAdvancedOrders;
    }

    public void setIdAdvancedOrders(int idAdvancedOrders) {
        this.idAdvancedOrders = idAdvancedOrders;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getSpecimenDate() {
        return specimenDate;
    }

    public void setSpecimenDate(Date specimenDate) {
        this.specimenDate = specimenDate;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Boolean getIsAdvancedOrder() {
        return isAdvancedOrder;
    }

    public void setIsAdvancedOrder(Boolean isAdvancedOrder) {
        this.isAdvancedOrder = isAdvancedOrder;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public Boolean getIsFasting() {
        return isFasting;
    }

    public void setIsFasting(Boolean isFasting) {
        this.isFasting = isFasting;
    }

    public Integer getInsurance() {
        return insurance;
    }

    public void setInsurance(Integer insurance) {
        this.insurance = insurance;
    }

    public Integer getSecondaryInsurance() {
        return secondaryInsurance;
    }

    public void setSecondaryInsurance(Integer secondaryInsurance) {
        this.secondaryInsurance = secondaryInsurance;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getSecondaryPolicyNumber() {
        return secondaryPolicyNumber;
    }

    public void setSecondaryPolicyNumber(String secondaryPolicyNumber) {
        this.secondaryPolicyNumber = secondaryPolicyNumber;
    }

    public String getSecondaryGroupNumber() {
        return secondaryGroupNumber;
    }

    public void setSecondaryGroupNumber(String secondaryGroupNumber) {
        this.secondaryGroupNumber = secondaryGroupNumber;
    }

    public String getMedicareNumber() {
        return medicareNumber;
    }

    public void setMedicareNumber(String medicareNumber) {
        this.medicareNumber = medicareNumber;
    }

    public String getMedicaidNumber() {
        return medicaidNumber;
    }

    public void setMedicaidNumber(String medicaidNumber) {
        this.medicaidNumber = medicaidNumber;
    }

    public Integer getReportType() {
        return reportType;
    }

    public void setReportType(Integer reportType) {
        this.reportType = reportType;
    }

    public Integer getRequisition() {
        return requisition;
    }

    public void setRequisition(Integer requisition) {
        this.requisition = requisition;
    }

    public Boolean isBillOnly() {
        return billOnly;
    }

    public void setBillOnly(Boolean billOnly) {
        this.billOnly = billOnly;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isHold() {
        return hold;
    }

    public void setHold(Boolean hold) {
        this.hold = hold;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getHoldComment() {
        return holdComment;
    }

    public void setHoldComment(String holdComment) {
        this.holdComment = holdComment;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    public String getInternalComment() {
        return internalComment;
    }

    public void setInternalComment(String internalComment) {
        this.internalComment = internalComment;
    }

    public Boolean getHl7Transmitted() {
        return hl7Transmitted;
    }

    public void setHl7Transmitted(Boolean hl7Transmitted) {
        this.hl7Transmitted = hl7Transmitted;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Boolean isBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getEmrOrderId() {
        return emrOrderId;
    }

    public void setEmrOrderId(String emrOrderId) {
        this.emrOrderId = emrOrderId;
    }

    public Date getDOI() {
        return DOI;
    }

    public void setDOI(Date DOI) {
        this.DOI = DOI;
    }

    public String getEOA() {
        return EOA;
    }

    public void setEOA(String EOA) {
        this.EOA = EOA;
    }

    public Integer getReleaseJobID() {
        return releaseJobID;
    }

    public void setReleaseJobID(Integer releaseJobID) {
        this.releaseJobID = releaseJobID;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean isInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += idAdvancedOrders + (orderDate != null ? orderDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedOrders))
        {
            return false;
        }
        AdvancedOrders other = (AdvancedOrders) object;
        if (this.hashCode() != other.hashCode())
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.AdvancedOrders[ idAdvancedOrders=" + idAdvancedOrders + " ]";
    }

}
