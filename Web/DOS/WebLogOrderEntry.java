package Web.DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebLogOrderEntry.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebLogOrderEntry")
@NamedQueries(
{
    @NamedQuery(name = "WebLogOrderEntry.findAll", query = "SELECT w FROM WebLogOrderEntry w")
})
public class WebLogOrderEntry implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrderEntryLog")
    private Integer idOrderEntryLog;
    @Column(name = "orderId")
    private Integer orderId;
    @Column(name = "advancedOrderId")
    private Integer advancedOrderId;
    @Basic(optional = false)
    @Column(name = "advancedOrderOnly")
    private boolean advancedOrderOnly;
    @Basic(optional = false)
    @Column(name = "isNewPatient")
    private boolean isNewPatient;
    @Basic(optional = false)
    @Column(name = "isNewSubscriber")
    private boolean isNewSubscriber;
    @Basic(optional = false)
    @Column(name = "subscriberChanged")
    private boolean subscriberChanged;
    @Column(name = "accession")
    private String accession;
    @Basic(optional = false)
    @Column(name = "logId")
    private Integer logId;
    @Basic(optional = false)
    @Column(name = "orderEntryLogType")
    private Integer orderEntryLogType;

    public WebLogOrderEntry()
    {
    }

    public WebLogOrderEntry(Integer idOrderEntryLog)
    {
        this.idOrderEntryLog = idOrderEntryLog;
    }

    public WebLogOrderEntry(Integer idOrderEntryLog, boolean advancedOrderOnly, boolean isNewPatient, boolean isNewSubscriber, boolean subscriberChanged)
    {
        this.idOrderEntryLog = idOrderEntryLog;
        this.advancedOrderOnly = advancedOrderOnly;
        this.isNewPatient = isNewPatient;
        this.isNewSubscriber = isNewSubscriber;
        this.subscriberChanged = subscriberChanged;
    }

    public Integer getIdOrderEntryLog()
    {
        return idOrderEntryLog;
    }

    public void setIdOrderEntryLog(Integer idOrderEntryLog)
    {
        this.idOrderEntryLog = idOrderEntryLog;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getAdvancedOrderId()
    {
        return advancedOrderId;
    }

    public void setAdvancedOrderId(Integer advancedOrderId)
    {
        this.advancedOrderId = advancedOrderId;
    }

    public boolean getAdvancedOrderOnly()
    {
        return advancedOrderOnly;
    }

    public void setAdvancedOrderOnly(boolean advancedOrderOnly)
    {
        this.advancedOrderOnly = advancedOrderOnly;
    }

    public boolean getIsNewPatient()
    {
        return isNewPatient;
    }

    public void setIsNewPatient(boolean isNewPatient)
    {
        this.isNewPatient = isNewPatient;
    }

    public boolean getIsNewSubscriber()
    {
        return isNewSubscriber;
    }

    public void setIsNewSubscriber(boolean isNewSubscriber)
    {
        this.isNewSubscriber = isNewSubscriber;
    }

    public boolean getSubscriberChanged()
    {
        return subscriberChanged;
    }

    public void setSubscriberChanged(boolean subscriberChanged)
    {
        this.subscriberChanged = subscriberChanged;
    }

    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public Integer getLogId()
    {
        return logId;
    }

    public void setLogId(Integer logId)
    {
        this.logId = logId;
    }

    public Integer getOrderEntryLogType()
    {
        return orderEntryLogType;
    }

    public void setorderEntryLogType(Integer orderEntryLogType)
    {
        this.orderEntryLogType = orderEntryLogType;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idOrderEntryLog != null ? idOrderEntryLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebLogOrderEntry))
        {
            return false;
        }
        WebLogOrderEntry other = (WebLogOrderEntry) object;
        if ((this.idOrderEntryLog == null && other.idOrderEntryLog != null) || (this.idOrderEntryLog != null && !this.idOrderEntryLog.equals(other.idOrderEntryLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebLogOrderEntry[ idOrderEntryLog=" + idOrderEntryLog + " ]";
    }

}
