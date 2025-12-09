package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @date:   Apr 6, 2012
 * @author: CSS Dev
 * 
 * @project:  
 * @package: DOS
 * @file name: Results.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity
@Table(name = "results")
@NamedQueries(
{
    @NamedQuery(name = "Results.findAll", query = "SELECT r FROM Results r")
})
public class Results implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idResults")
    private Integer idResults;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "testId")
    private int testId;
    @Column(name = "panelId")
    private Integer panelId;
    @Column(name = "resultText")
    private String resultText;
    @Column(name = "resultRemark")
    private Integer resultRemark;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "resultChoice")
    private Integer resultChoice;
    @Column(name = "reportedBy")
    private Integer reportedBy;
    @Column(name = "dateReported")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReported;
    @Column(name = "isApproved")
    private boolean isApproved;
    @Column(name = "approvedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    @Column(name = "approvedBy")
    private Integer approvedBy;
    @Column(name = "isInvalidated")
    private boolean isInvalidated;
    @Column(name = "invalidatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invalidatedDate;
    @Column(name = "invalidatedBy")
    private Integer invalidatedBy;
    @Column(name = "isUpdated")
    private boolean isUpdated;
    @Column(name = "updatedBy")
    private Integer updatedBy;
    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "resultNo")
    private Double resultNo;
    @Column(name = "isAbnormal")
    private boolean isAbnormal;
    @Column(name = "isHigh")
    private boolean isHigh;
    @Column(name = "isLow")
    private boolean isLow;
    @Column(name = "isCIDHigh")
    private boolean isCIDHigh;
    @Column(name = "isCIDLow")
    private boolean isCIDLow;
    @Column(name = "noCharge")
    private boolean noCharge;
    @Column(name = "textAnswer")
    private String textAnswer;
    @Column(name = "hl7Transmitted")
    private boolean hl7Transmitted = false;
    @Column(name = "printAndTransmitted")
    private boolean printAndTransmitted = false;
    @Column(name = "pAndTDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pAndTDate;
    @Column(name = "optional")
    private boolean optional;

    public Results()
    {
    }

    public Results(Integer idResults)
    {
        this.idResults = idResults;
    }

    public Results(Integer idResults, int orderId, int testId)
    {
        this.idResults = idResults;
        this.orderId = orderId;
        this.testId = testId;
    }
    
    public Results(Results other)
    {
        if(this != other)
        {
            this.idResults = other.idResults;
            this.orderId = other.orderId;
            this.testId = other.testId;
            this.panelId = other.panelId;
            this.resultText = other.resultText;
            this.resultRemark = other.resultRemark;
            this.created = other.created;
            this.resultChoice = other.resultChoice;
            this.reportedBy = other.reportedBy;
            this.dateReported = other.dateReported;
            this.isApproved = other.isApproved;
            this.approvedDate = other.approvedDate;
            this.approvedBy = other.approvedBy;
            this.isInvalidated = other.isInvalidated;
            this.invalidatedDate = other.invalidatedDate;
            this.invalidatedBy = other.invalidatedBy;
            this.isUpdated = other.isUpdated;
            this.updatedBy = other.updatedBy;
            this.updatedDate = other.updatedDate;
            this.resultNo = other.resultNo;
            this.isAbnormal = other.isAbnormal;
            this.isHigh = other.isHigh;
            this.isLow = other.isLow;
            this.isCIDHigh = other.isCIDHigh;
            this.isCIDLow = other.isCIDLow;
            this.noCharge = other.noCharge;
            this.textAnswer = other.textAnswer;
            this.optional = other.optional;
        }
    }
    
    @Diff(fieldName="idResults", isUniqueId = true)
    public Integer getIdResults()
    {
        return idResults;
    }

    public void setIdResults(Integer idResults)
    {
        this.idResults = idResults;
    }

    @Diff(fieldName="orderId")
    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    @Diff(fieldName="testId")
    public int getTestId()
    {
        return testId;
    }

    public void setTestId(int testId)
    {
        this.testId = testId;
    }

    @Diff(fieldName="panelId")
    public Integer getPanelId()
    {
        return panelId;
    }

    public void setPanelId(Integer panelId)
    {
        this.panelId = panelId;
    }

    @Diff(fieldName="resultText")
    public String getResultText()
    {
        return resultText;
    }

    public void setResultText(String resultText)
    {
        this.resultText = resultText;
    }

    @Diff(fieldName="resultRemark")
    public Integer getResultRemark()
    {
        return resultRemark;
    }

    public void setResultRemark(Integer resultRemark)
    {
        this.resultRemark = resultRemark;
    }

    @Diff(fieldName="resultChoice")
    public Integer getResultChoice()
    {
        return resultChoice;
    }

    public void setResultChoice(Integer resultChoice)
    {
        this.resultChoice = resultChoice;
    }

    @Diff(fieldName="created")
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Diff(fieldName="reportedBy")
    public Integer getReportedBy()
    {
        return reportedBy;
    }

    public void setReportedBy(Integer reportedBy)
    {
        this.reportedBy = reportedBy;
    }

    @Diff(fieldName="dateReported")
    public Date getDateReported()
    {
        return dateReported;
    }

    public void setDateReported(Date dateReported)
    {
        this.dateReported = dateReported;
    }

    @Diff(fieldName="isApproved")
    public boolean getIsApproved()
    {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved)
    {
        this.isApproved = isApproved;
    }

    @Diff(fieldName="approvedDate")
    public Date getApprovedDate()
    {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate)
    {
        this.approvedDate = approvedDate;
    }

    @Diff(fieldName="approvedBy")
    public Integer getApprovedBy()
    {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy)
    {
        this.approvedBy = approvedBy;
    }

    @Diff(fieldName="isInvalidated")
    public boolean getIsInvalidated()
    {
        return isInvalidated;
    }

    public void setIsInvalidated(boolean isInvalidated)
    {
        this.isInvalidated = isInvalidated;
    }

    @Diff(fieldName="invalidatedDate")
    public Date getInvalidatedDate()
    {
        return invalidatedDate;
    }

    public void setInvalidatedDate(Date invalidatedDate)
    {
        this.invalidatedDate = invalidatedDate;
    }

    @Diff(fieldName="invalidatedBy")
    public Integer getInvalidatedBy()
    {
        return invalidatedBy;
    }

    public void setInvalidatedBy(Integer invalidatedBy)
    {
        this.invalidatedBy = invalidatedBy;
    }

    @Diff(fieldName="isUpdated")
    public boolean getIsUpdated()
    {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated)
    {
        this.isUpdated = isUpdated;
    }

    @Diff(fieldName="updatedBy")
    public Integer getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    @Diff(fieldName="updatedDate")
    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    @Diff(fieldName="resultNo")
    public Double getResultNo()
    {
        return resultNo;
    }

    public void setResultNo(Double resultNo)
    {
        this.resultNo = resultNo;
    }
    
    @Diff(fieldName="isAbnormal")
    public boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    @Diff(fieldName="isHigh")
    public boolean getIsHigh() {
        return isHigh;
    }

    public void setIsHigh(boolean isHigh) {
        this.isHigh = isHigh;
    }

    @Diff(fieldName="isLow")
    public boolean getIsLow() {
        return isLow;
    }

    public void setIsLow(boolean isLow) {
        this.isLow = isLow;
    }

    @Diff(fieldName="isCIDHigh")
    public boolean getIsCIDHigh() {
        return isCIDHigh;
    }

    public void setIsCIDHigh(boolean isCIDHigh) {
        this.isCIDHigh = isCIDHigh;
    }

    @Diff(fieldName="isCIDLow")
    public boolean getIsCIDLow() {
        return isCIDLow;
    }

    public void setIsCIDLow(boolean isCIDLow) {
        this.isCIDLow = isCIDLow;
    }
    
    @Diff(fieldName="textAnswer")
        public String getTextAnswer() {
        return textAnswer;
    }

    @Diff(fieldName="noCharge")
    public boolean isNoCharge()
    {
        return noCharge;
    }

    public void setNoCharge(boolean noCharge)
    {
        this.noCharge = noCharge;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
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
    
    @Diff(fieldName="printAndTransmitted")
    public boolean isPrintAndTransmitted()
    {
        return printAndTransmitted;
    }
    
    public void setPrintAndTransmitted(boolean printAndTransmitted)
    {
        this.printAndTransmitted = printAndTransmitted;
    }
    
    @Diff(fieldName="pAndTDate")
    public Date getPAndTDate()
    {
        return pAndTDate;
    }

    public void setPAndTDate(Date pAndTDate)
    {
        this.pAndTDate = pAndTDate;
    }

    @Diff(fieldName="optional")
    public boolean isOptional()
    {
        return optional;
    }

    public void setOptional(boolean optional)
    {
        this.optional = optional;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idResults != null ? idResults.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Results))
        {
            return false;
        }
        Results other = (Results) object;
        if ((this.idResults == null && other.idResults != null) || (this.idResults != null && !this.idResults.equals(other.idResults)))
        {
            return false;
        }
        if ((this.testId == 0 && other.testId != 0) || (this.testId != 0 && this.testId != other.testId))
        {
            return false;
        }
        if ((this.orderId == 0 && other.orderId != 0) || (this.orderId != 0 && this.orderId != other.orderId))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Results[ idResults=" + idResults + " ]";
    }

    public Results copy()
    {
        Results output = new Results();
        output.setIdResults(getIdResults());
        output.setOrderId(getOrderId());
        output.setTestId(getTestId());
        output.setPanelId(getPanelId());
        output.setResultText(getResultText());
        output.setResultRemark(getResultRemark());
        output.setCreated(getCreated());
        output.setResultChoice(getResultChoice());
        output.setReportedBy(getReportedBy());
        output.setDateReported(getDateReported());
        output.setIsApproved(getIsApproved());
        output.setApprovedDate(getApprovedDate());
        output.setApprovedBy(getApprovedBy());
        output.setIsInvalidated(getIsInvalidated());
        output.setInvalidatedDate(getInvalidatedDate());
        output.setInvalidatedBy(getInvalidatedBy());
        output.setIsUpdated(getIsUpdated());
        output.setUpdatedBy(getUpdatedBy());
        output.setUpdatedDate(getUpdatedDate());
        output.setResultNo(getResultNo());
        output.setIsAbnormal(getIsAbnormal());
        output.setIsHigh(getIsHigh());
        output.setIsLow(getIsLow());
        output.setIsCIDHigh(getIsCIDHigh());
        output.setIsCIDLow(getIsCIDLow());
        output.setNoCharge(isNoCharge());
        output.setTextAnswer(getTextAnswer());
        output.setHl7Transmitted(getHl7Transmitted());
        output.setPrintAndTransmitted(isPrintAndTransmitted());
        output.setPAndTDate(getPAndTDate());
        output.setOptional(isOptional());

        return output;
    }
}
