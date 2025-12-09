package DOS;

import java.io.Serializable;
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
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 3, 2016
 */
@Entity 
@Table(name = "printTransmitLog")
public class PrintTransmitLog implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprintTransmitLog")
    private Integer idprintTransmitLog;
    @Basic(optional = false)
    @Column(name = "action")
    private String action;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "orderId")
    private Integer orderId;
    @Column(name = "clientId")
    private Integer clientId;
    @Column(name = "releaseId")
    private Integer releaseId;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedDate")
    private Date updatedDate;
    @Basic(optional = false)
    @Column(name = "isError")
    private Boolean isError;
    @Basic(optional = false)
    @Column(name = "isRetransmit")
    private Boolean isRetransmit;
    @Basic(optional = false)
    @Column(name = "isRelease")
    private Boolean isRelease;
    
    public PrintTransmitLog() {}
    
    public PrintTransmitLog(
        Integer idprintTransmitLog,
        String action,
        String description,
        Integer userId,
        Integer orderId,
        Integer clientId,
        Integer releaseId,
        Date createdDate,
        Date updatedDate,
        Boolean isError,
        Boolean isRetransmit,
        Boolean isRelease)
    {
        this.idprintTransmitLog = idprintTransmitLog;
        this.action = action;
        this.description = description;
        this.userId = userId;
        this.orderId = orderId;
        this.clientId = clientId;
        this.releaseId = releaseId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isError = isError;
        this.isRetransmit = isRetransmit;
        this.isRelease = isRelease;
    }

    public Integer getIdprintTransmitLog()
    {
        return idprintTransmitLog;
    }

    public void setIdprintTransmitLog(Integer idprintTransmitLog)
    {
        this.idprintTransmitLog = idprintTransmitLog;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }
    
    public Integer getReleaseId()
    {
        return releaseId;
    }

    public void setReleaseId(Integer releaseId)
    {
        this.releaseId = releaseId;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Boolean isError()
    {
        return isError;
    }

    public void setIsError(Boolean isError)
    {
        this.isError = isError;
    }

    public Boolean isRetransmit()
    {
        return isRetransmit;
    }

    public void setIsRetransmit(Boolean isRetransmit)
    {
        this.isRetransmit = isRetransmit;
    }

    public Boolean isRelease()
    {
        return isRelease;
    }

    public void setIsRelease(Boolean isRelease)
    {
        this.isRelease = isRelease;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrintTransmitLog))
        {
            return false;
        }
        PrintTransmitLog other = (PrintTransmitLog) object;
        if ((this.idprintTransmitLog == null && other.idprintTransmitLog != null)
                || (this.idprintTransmitLog != null && !this.idprintTransmitLog.equals(other.idprintTransmitLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.PrintTransmitLog[ id=" + idprintTransmitLog + ", action=" + action + ", releaseId=" + releaseId +" ]";
    }
}
