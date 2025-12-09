package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
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
 * @file name: AdvancedResults.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "advancedResults")
@NamedQueries(
{
    @NamedQuery(name = "AdvancedResults.findAll", query = "SELECT a FROM AdvancedResults a")
})
public class AdvancedResults implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdvancedResults")
    private int idAdvancedResults;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "idAdvancedOrder")
    private int idAdvancedOrder;
    @Basic(optional = false)
    @Column(name = "testId")
    private int testId;
    @Column(name = "panelId")
    private Integer panelId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "resultNo")
    private Double resultNo;
    @Column(name = "resultText")
    private String resultText;
    @Column(name = "resultRemark")
    private Integer resultRemark;
    @Column(name = "resultChoice")
    private Integer resultChoice;
    @Column(name = "reportedBy")
    private Integer reportedBy;
    @Column(name = "dateReported")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReported;
    @Column(name = "noCharge")
    private Boolean noCharge;
    @Column(name = "hl7Transmitted")
    private Boolean hl7Transmitted;

    public AdvancedResults()
    {
    }

    public AdvancedResults(int idAdvancedResults, Date created, int idAdvancedOrder, int testId)
    {
        this.idAdvancedResults = idAdvancedResults;
        this.created = created;
        this.idAdvancedOrder = idAdvancedOrder;
        this.testId = testId;
    }

    public AdvancedResults(int idAdvancedResults, Date created)
    {
        this.idAdvancedResults = idAdvancedResults;
        this.created = created;
    }

    public int getIdAdvancedResults()
    {
        return idAdvancedResults;
    }

    public void setIdAdvancedResults(int idResults)
    {
        this.idAdvancedResults = idResults;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public int getIdAdvancedOrder()
    {
        return idAdvancedOrder;
    }

    public void setIdAdvancedOrder(int idAdvancedOrder)
    {
        this.idAdvancedOrder = idAdvancedOrder;
    }

    public int getTestId()
    {
        return testId;
    }

    public void setTestId(int testId)
    {
        this.testId = testId;
    }

    public Integer getPanelId()
    {
        return panelId;
    }

    public void setPanelId(Integer panelId)
    {
        this.panelId = panelId;
    }

    public Double getResultNo()
    {
        return resultNo;
    }

    public void setResultNo(Double resultNo)
    {
        this.resultNo = resultNo;
    }

    public String getResultText()
    {
        return resultText;
    }

    public void setResultText(String resultText)
    {
        this.resultText = resultText;
    }

    public Integer getResultRemark()
    {
        return resultRemark;
    }

    public void setResultRemark(Integer resultRemark)
    {
        this.resultRemark = resultRemark;
    }

    public Integer getResultChoice()
    {
        return resultChoice;
    }

    public void setResultChoice(Integer resultChoice)
    {
        this.resultChoice = resultChoice;
    }

    public Integer getReportedBy()
    {
        return reportedBy;
    }

    public void setReportedBy(Integer reportedBy)
    {
        this.reportedBy = reportedBy;
    }

    public Date getDateReported()
    {
        return dateReported;
    }

    public void setDateReported(Date dateReported)
    {
        this.dateReported = dateReported;
    }

    public Boolean getNoCharge()
    {
        return noCharge;
    }

    public void setNoCharge(Boolean noCharge)
    {
        this.noCharge = noCharge;
    }

    public Boolean getHl7Transmitted()
    {
        return hl7Transmitted;
    }

    public void setHl7Transmitted(Boolean hl7Transmitted)
    {
        this.hl7Transmitted = hl7Transmitted;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += idAdvancedResults + (created != null ? created.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedResults))
        {
            return false;
        }
        AdvancedResults other = (AdvancedResults) object;
        if (this.hashCode() != other.hashCode())
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.AdvancedResults[ idAdvancedResults=" + idAdvancedResults + " ]";
    }

}
