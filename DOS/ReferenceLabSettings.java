package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "referenceLabSettings", catalog = "css", schema = "")
public class ReferenceLabSettings
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "idDepartment")
    private Integer idDepartment;
    @Column(name = "idReqReport")
    private Integer idReqReport;
    @Column(name = "idManReport")
    private Integer idManReport;
    @Column(name = "idTestXRef")
    private Integer idTestXRef;
    @Column(name = "idClientXRef")
    private Integer idClientXRef;
    @Column(name = "idInsuranceXRef")
    private Integer idInsuranceXRef;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "displayName")
    private String displayName;
    @Column(name = "sendingApp")
    private String sendingApp;
    @Column(name = "sendingFac")
    private String sendingFac;
    @Column(name = "receivingApp")
    private String receivingApp;
    @Column(name = "receivingFac")
    private String receivingFac;
    @Column(name = "trueFormat")
    private String trueFormat;
    @Column(name = "falseFormat")
    private String falseFormat;
    @Column(name = "dateFormat")
    private String dateFormat;
    @Column(name = "resultTable")
    private String resultTable;
    @Column(name = "resultProcedure")
    private String resultProcedure;
    @Column(name = "orderTable")
    private String orderTable;
    @Column(name = "orderProcedure")
    private String orderProcedure;    
    @Column(name = "numericPaddingChar")
    private String numericPaddingChar;
    @Column(name = "textPaddingChar")
    private String textPaddingChar;    
    @Column(name = "suppressManifest")
    private Boolean suppressManifest;
    @Column(name = "suppressReq")
    private Boolean suppressReq;
    @Column(name = "clientBillingOnly")
    private Boolean clientBillingOnly;
    @Column(name = "suppressPatientDemo")
    private Boolean suppressPatientDemo;
    @Column(name = "breakDownPanels")
    private Boolean breakDownPanels;
    @Column(name = "printCommentOnReport")
    private Boolean printCommentOnReport;    
    @Column(name = "comment")
    private String comment;    
    @Column(name = "allowManualPosting")
    private Boolean allowManualPosting;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deactivatedDate")
    private Date deactivatedDate;    
    @Column(name = "active")
    private Boolean active;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getIdDepartment()
    {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment)
    {
        this.idDepartment = idDepartment;
    }

    public Integer getIdReqReport()
    {
        return idReqReport;
    }

    public void setIdReqReport(Integer idReqReport)
    {
        this.idReqReport = idReqReport;
    }

    public Integer getIdManReport()
    {
        return idManReport;
    }

    public void setIdManReport(Integer idManReport)
    {
        this.idManReport = idManReport;
    }

    public Integer getIdTestXRef()
    {
        return idTestXRef;
    }

    public void setIdTestXRef(Integer idTestXRef)
    {
        this.idTestXRef = idTestXRef;
    }

    public Integer getIdClientXRef()
    {
        return idClientXRef;
    }

    public void setIdClientXRef(Integer idClientXRef)
    {
        this.idClientXRef = idClientXRef;
    }

    public Integer getIdInsuranceXRef()
    {
        return idInsuranceXRef;
    }

    public void setIdInsuranceXRef(Integer idInsuranceXRef)
    {
        this.idInsuranceXRef = idInsuranceXRef;
    }

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getSendingApp()
    {
        return sendingApp;
    }

    public void setSendingApp(String sendingApp)
    {
        this.sendingApp = sendingApp;
    }

    public String getSendingFac()
    {
        return sendingFac;
    }

    public void setSendingFac(String sendingFac)
    {
        this.sendingFac = sendingFac;
    }

    public String getReceivingApp()
    {
        return receivingApp;
    }

    public void setReceivingApp(String receivingApp)
    {
        this.receivingApp = receivingApp;
    }

    public String getReceivingFac()
    {
        return receivingFac;
    }

    public void setReceivingFac(String receivingFac)
    {
        this.receivingFac = receivingFac;
    }

    public String getTrueFormat()
    {
        return trueFormat;
    }

    public void setTrueFormat(String trueFormat)
    {
        this.trueFormat = trueFormat;
    }

    public String getFalseFormat()
    {
        return falseFormat;
    }

    public void setFalseFormat(String falseFormat)
    {
        this.falseFormat = falseFormat;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    public String getResultTable()
    {
        return resultTable;
    }

    public void setResultTable(String resultTable)
    {
        this.resultTable = resultTable;
    }

    public String getResultProcedure()
    {
        return resultProcedure;
    }

    public void setResultProcedure(String resultProcedure)
    {
        this.resultProcedure = resultProcedure;
    }

    public String getOrderTable()
    {
        return orderTable;
    }

    public void setOrderTable(String orderTable)
    {
        this.orderTable = orderTable;
    }

    public String getOrderProcedure()
    {
        return orderProcedure;
    }

    public void setOrderProcedure(String orderProcedure)
    {
        this.orderProcedure = orderProcedure;
    }

    public String getNumericPaddingChar()
    {
        return numericPaddingChar;
    }

    public void setNumericPaddingChar(String numericPaddingChar)
    {
        this.numericPaddingChar = numericPaddingChar;
    }

    public String getTextPaddingChar()
    {
        return textPaddingChar;
    }

    public void setTextPaddingChar(String textPaddingChar)
    {
        this.textPaddingChar = textPaddingChar;
    }

    public Boolean isSuppressManifest()
    {
        return suppressManifest;
    }

    public void setSuppressManifest(Boolean suppressManifest)
    {
        this.suppressManifest = suppressManifest;
    }

    public Boolean isSuppressReq() {
        return suppressReq;
    }

    public void setSuppressReq(Boolean suppressReq) {
        this.suppressReq = suppressReq;
    }

    public Boolean isClientBillingOnly()
    {
        return clientBillingOnly;
    }

    public void setClientBillingOnly(Boolean clientBillingOnly)
    {
        this.clientBillingOnly = clientBillingOnly;
    }

    public Boolean isSuppressPatientDemo()
    {
        return suppressPatientDemo;
    }

    public void setSuppressPatientDemo(Boolean suppressPatientDemo)
    {
        this.suppressPatientDemo = suppressPatientDemo;
    }

    public Boolean isBreakDownPanels()
    {
        return breakDownPanels;
    }

    public void setBreakDownPanels(Boolean breakDownPanels)
    {
        this.breakDownPanels = breakDownPanels;
    }

    public Boolean isPrintCommentOnReport()
    {
        return printCommentOnReport;
    }

    public void setPrintCommentOnReport(Boolean printCommentOnReport)
    {
        this.printCommentOnReport = printCommentOnReport;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Boolean isAllowManualPosting() {
        return allowManualPosting;
    }

    public void setAllowManualPosting(Boolean allowManualPosting) {
        this.allowManualPosting = allowManualPosting;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
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
        if (!(object instanceof ReferenceLabSettings))
        {
            return false;
        }
        ReferenceLabSettings other = (ReferenceLabSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }    
    
    @Override
    public String toString()
    {
        return "DOS.ReferenceLabSettings[ id=" + id + ", display=" + displayName + " ]";
    }    
    
}