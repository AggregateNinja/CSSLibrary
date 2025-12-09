
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Orders.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "orders", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrders", nullable = false)
    private Integer idOrders;
    @Column(name = "doctorId")
    private Integer doctorId;
    @Basic(optional = false)
    @Column(name = "clientId", nullable = false)
    private int clientId;
    @Basic(optional = false)
    @Column(name = "accession", nullable = false)
    private String accession;
    @Basic(optional = false)
    @Column(name = "locationId", nullable = false)
    private int locationId;
    @Basic(optional = false)
    @Column(name = "orderDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    @Basic(optional = false)
    @Column(name = "specimenDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date specimenDate;
    @Basic(optional = false)
    @Column(name = "patientId", nullable = false)
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
    private boolean isFasting;
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
    private boolean billOnly;
    @Column(name = "active")
    private boolean active;
    @Column(name = "hold")
    private boolean hold;
    @Column(name = "stage")
    private Integer stage;
    @Column(name = "holdComment")
    private String holdComment;
    @Column(name = "resultComment")
    private String resultComment;
    @Column(name = "internalComment")
    private String internalComment;
    @Column(name = "hl7Transmitted")
    private boolean hl7Transmitted = false;
    @Column(name = "payment")
    private Double payment;
    @Column(name = "billable")
    private Boolean billable;
    @Column(name = "emrOrderId")
    private String emrOrderId;
    @Column(name = "DOI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date DOI;
    @Column(name = "EOA")
    private String EOA;
    @Column(name = "ReleaseJobID")
    private Integer ReleaseJobID;
    @Column(name = "releaseDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;
    @Column(name = "reqId")
    private String reqId;
    @Basic(optional = false)
    @Column(name = "icdVersion")
    private Integer icdVersion;
    @Column(name = "allPAndT")
    private Boolean allPAndT;
    @Column(name = "retransmit")
    private Boolean retransmit;
    @Column(name = "miles")
    private Integer miles;
    @Column(name = "patientCount")
    private Integer patientCount;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "poNum")
    private String poNum;

    public Orders() {
    }

    public Orders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    public Orders(Integer idOrders, int clientId, String accession, int locationId, Date orderDate, Date specimenDate, int patientId) {
        this.idOrders = idOrders;
        this.clientId = clientId;
        this.accession = accession;
        this.locationId = locationId;
        this.orderDate = orderDate;
        this.specimenDate = specimenDate;
        this.patientId = patientId;
    }

    public Orders(Integer idOrders, 
                  Integer doctorId, 
                  int clientId, 
                  String accession, 
                  int locationId, 
                  Date orderDate, 
                  Date specimenDate, 
                  int patientId,
                  boolean isAdvancedOrder,
                  Integer insurance, 
                  Integer secondaryInsurance, 
                  String policyNumber, 
                  String groupNumber, 
                  String secondaryPolicyNumber, 
                  String secondaryGroupNumber, 
                  String medicareNumber, 
                  String medicaidNumber,
                  int icdVersion,
                  Integer miles,
                  Integer patientCount)
    {
        this.idOrders = idOrders;
        this.doctorId = doctorId;
        this.clientId = clientId;
        this.accession = accession;
        this.locationId = locationId;
        this.orderDate = orderDate;
        this.specimenDate = specimenDate;
        this.patientId = patientId;
        this.isAdvancedOrder = isAdvancedOrder;
        this.insurance = insurance;
        this.secondaryInsurance = secondaryInsurance;
        this.policyNumber = policyNumber;
        this.groupNumber = groupNumber;
        this.secondaryPolicyNumber = secondaryPolicyNumber;
        this.secondaryGroupNumber = secondaryGroupNumber;
        this.medicareNumber = medicareNumber;
        this.medicaidNumber = medicaidNumber;
        this.icdVersion = icdVersion;
        this.miles = miles;
        this.patientCount = patientCount;
    }
    
    @Diff(fieldName="idOrders", isUniqueId=true)
    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    @Diff(fieldName="doctorId", isUserVisible=true)
    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    @Diff(fieldName="clientId", isUserVisible=true)
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Diff(fieldName="accession")
    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    @Diff(fieldName="locationId",isUserVisible=true)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Diff(fieldName="orderDate", isUserVisible=true)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Diff(fieldName="specimenDate", isUserVisible=true)
    public Date getSpecimenDate() {
        return specimenDate;
    }

    public void setSpecimenDate(Date specimenDate) {
        this.specimenDate = specimenDate;
    }

    @Diff(fieldName="patientId", isUserVisible=true)
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Diff(fieldName="subscriberId", isUserVisible=true)
    public Integer getSubscriberId()
    {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId)
    {
        this.subscriberId = subscriberId;
    }

    @Diff(fieldName="isAdvancedOrder")
    public boolean getIsAdvancedOrder()
    {
        return isAdvancedOrder;
    }

    public void setIsAdvancedOrder(boolean isAdvancedOrder)
    {
        this.isAdvancedOrder = isAdvancedOrder;
    }

    @Diff(fieldName="room", isUserVisible=true)
    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    @Diff(fieldName="bed", isUserVisible=true)
    public String getBed()
    {
        return bed;
    }

    public void setBed(String bed)
    {
        this.bed = bed;
    }
    
    public void setIsFasting(boolean isFasting)
    {
        this.isFasting = isFasting;
    }
    
    @Diff(fieldName="isFasting", isUserVisible=true)
    public boolean getIsFasting()
    {
        return isFasting;
    }

    @Diff(fieldName="insurance", isUserVisible=true)
    public Integer getInsurance()
    {
        return insurance;
    }

    public void setInsurance(Integer insurance)
    {
        this.insurance = insurance;
    }

    @Diff(fieldName="secondaryInsurance", isUserVisible=true)
    public Integer getSecondaryInsurance()
    {
        return secondaryInsurance;
    }

    public void setSecondaryInsurance(Integer secondaryInsurance)
    {
        this.secondaryInsurance = secondaryInsurance;
    }

    @Diff(fieldName="policyNumber", isUserVisible=true)
    public String getPolicyNumber()
    {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber)
    {
        this.policyNumber = policyNumber;
    }

    @Diff(fieldName="groupNumber", isUserVisible=true)
    public String getGroupNumber()
    {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber = groupNumber;
    }

    @Diff(fieldName="secondaryPolicyNumber", isUserVisible=true)
    public String getSecondaryPolicyNumber()
    {
        return secondaryPolicyNumber;
    }

    public void setSecondaryPolicyNumber(String secondaryPolicyNumber)
    {
        this.secondaryPolicyNumber = secondaryPolicyNumber;
    }

    @Diff(fieldName="secondaryGroupNumber", isUserVisible=true)
    public String getSecondaryGroupNumber()
    {
        return secondaryGroupNumber;
    }

    public void setSecondaryGroupNumber(String secondaryGroupNumber)
    {
        this.secondaryGroupNumber = secondaryGroupNumber;
    }

    @Diff(fieldName="medicareNumber", isUserVisible=true)
    public String getMedicareNumber()
    {
        return medicareNumber;
    }

    public void setMedicareNumber(String medicareNumber)
    {
        this.medicareNumber = medicareNumber;
    }

    @Diff(fieldName="medicaidNumber", isUserVisible=true)
    public String getMedicaidNumber()
    {
        return medicaidNumber;
    }

    public void setMedicaidNumber(String medicaidNumber)
    {
        this.medicaidNumber = medicaidNumber;
    }

    @Diff(fieldName="reportType", isUserVisible = true)
    public Integer getReportType()
    {
        return reportType;
    }

    public void setReportType(Integer reportType)
    {
        this.reportType = reportType;
    }

    @Diff(fieldName="requisition", isUserVisible = true)
    public Integer getRequisition()
    {
        return requisition;
    }

    public void setRequisition(Integer requisition)
    {
        this.requisition = requisition;
    }

    @Diff(fieldName="billOnly")
    public boolean isBillOnly()
    {
        return billOnly;
    }

    public void setBillOnly(boolean billOnly)
    {
        this.billOnly = billOnly;
    }

    @Diff(fieldName="active")
    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Diff(fieldName="hold")
    public boolean isHold()
    {
        return hold;
    }

    public void setHold(boolean hold)
    {
        this.hold = hold;
    }

    @Diff(fieldName="stage")
    public Integer getStage()
    {
        return stage;
    }

    public void setStage(Integer stage)
    {
        this.stage = stage;
    }

    @Diff(fieldName="holdComment", isUserVisible=true)
    public String getHoldComment()
    {
        return holdComment;
    }

    public void setHoldComment(String holdComment)
    {
        this.holdComment = holdComment;
    }

    @Diff(fieldName="resultComment", isUserVisible=true)
    public String getResultComment()
    {
        return resultComment;
    }

    public void setResultComment(String resultComment)
    {
        this.resultComment = resultComment;
    }

    @Diff(fieldName="internalComment", isUserVisible=true)
    public String getInternalComment()
    {
        return internalComment;
    }

    public void setInternalComment(String internalComment)
    {
        this.internalComment = internalComment;
    }
    
    @Diff(fieldName="hl7Transmitted")
    public boolean getHl7Transmitted()
    {
        return hl7Transmitted;
    }
    
    public void setHl7Transmitted(boolean hl7Transmitted)
    {
        this.hl7Transmitted = hl7Transmitted;
    }

    @Diff(fieldName="payment", isUserVisible=true)
    public Double getPayment()
    {
        return payment;
    }

    public void setPayment(Double payment)
    {
        this.payment = payment;
    }

    @Diff(fieldName="billable", isUserVisible=true)
    public Boolean getBillable()
    {
        return billable;
    }

    public void setBillable(Boolean billable)
    {
        this.billable = billable;
    }

    @Diff(fieldName="emrOrderId")
    public String getEmrOrderId()
    {
        return emrOrderId;
    }

    public void setEmrOrderId(String emrOrderId)
    {
        this.emrOrderId = emrOrderId;
    }

    @Diff(fieldName="DOI", isUserVisible = true)
    public Date getDOI()
    {
        return DOI;
    }

    public void setDOI(Date DOI)
    {
        this.DOI = DOI;
    }

    @Diff(fieldName="EOA", isUserVisible = true)
    public String getEOA()
    {
        return EOA;
    }

    public void setEOA(String EOA)
    {
        this.EOA = EOA;
    }
    
    @Diff(fieldName="ReleaseJobID")
    public Integer getReleaseJobID()
    {
        return ReleaseJobID;
    }

    public void setReleaseJobID(Integer ReleaseJobID)
    {
        this.ReleaseJobID = ReleaseJobID;
    }
    
    @Diff(fieldName="releaseDate")
    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    @Diff(fieldName="reqId")
    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }
    
    @Diff(fieldName="icdVersion")
    public Integer getIcdVersion()
    {
        return icdVersion;
    }

    public void setIcdVersion(Integer icdVersion)
    {
        this.icdVersion = icdVersion;
    }    

    @Diff(fieldName="allPAndT")
    public Boolean isAllPAndT()
    {
        return allPAndT;
    }

    public void setAllPAndT(Boolean allPAndT)
    {
        this.allPAndT = allPAndT;
    }

    @Diff(fieldName="retransmit")
    public Boolean isRetransmit()
    {
        return retransmit;
    }

    public void setRetransmit(Boolean retransmit)
    {
        this.retransmit = retransmit;
    }

    @Diff(fieldName="miles", isUserVisible=true)
    public Integer getMiles()
    {
        return miles;
    }

    public void setMiles(Integer miles)
    {
        this.miles = miles;
    }

    @Diff(fieldName="patientCount", isUserVisible=true)
    public Integer getPatientCount()
    {
        return patientCount;
    }

    public void setPatientCount(Integer patientCount) 
    {
        this.patientCount = patientCount;
    }
    
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    @Diff(fieldName = "poNum")
    public String getPoNum()
    {
        return poNum;
    }
    
    public void setPoNum(String poNum)
    {
        this.poNum = poNum;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrders != null ? idOrders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.idOrders == null && other.idOrders != null) || (this.idOrders != null && !this.idOrders.equals(other.idOrders))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Orders[ idOrders=" + idOrders + " ]";
    }
    
    /**
     * Returns a new Orders object instance with this object's data.
     * 
     * @return Orders object copy
     */
    public Orders copy()
    {
        Orders copy = new Orders();
        copy.setIdOrders(idOrders);
        copy.setDoctorId(doctorId);
        copy.setClientId(clientId);
        copy.setAccession(accession);
        copy.setLocationId(locationId);
        copy.setOrderDate(orderDate);
        copy.setSpecimenDate(specimenDate);
        copy.setPatientId(patientId);
        copy.setSubscriberId(subscriberId);
        copy.setIsAdvancedOrder(isAdvancedOrder);
        copy.setRoom(room);
        copy.setBed(bed);
        copy.setIsFasting(isFasting);
        copy.setInsurance(insurance);
        copy.setSecondaryInsurance(secondaryInsurance);
        copy.setPolicyNumber(policyNumber);
        copy.setGroupNumber(groupNumber);
        copy.setSecondaryPolicyNumber(secondaryPolicyNumber);
        copy.setSecondaryGroupNumber(secondaryGroupNumber);
        copy.setMedicareNumber(medicareNumber);
        copy.setMedicaidNumber(medicaidNumber);
        copy.setReportType(reportType);
        copy.setRequisition(requisition);
        copy.setBillOnly(billOnly);
        copy.setActive(active);
        copy.setHold(hold);
        copy.setStage(stage);
        copy.setHoldComment(holdComment);
        copy.setResultComment(resultComment);
        copy.setInternalComment(internalComment);
        copy.setHl7Transmitted(hl7Transmitted);
        copy.setPayment(payment);
        copy.setBillable(billable);
        copy.setEmrOrderId(emrOrderId);
        copy.setDOI(DOI);
        copy.setEOA(EOA);
        copy.setReleaseJobID(ReleaseJobID);
        copy.setReleaseDate(releaseDate);
        copy.setReqId(reqId);
        copy.setIcdVersion(icdVersion);
        copy.setAllPAndT(allPAndT);
        copy.setRetransmit(retransmit);
        copy.setMiles(miles);
        copy.setPatientCount(patientCount);
        copy.setCreated(created);
        
        return copy;
    }

}
