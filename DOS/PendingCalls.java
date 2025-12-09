package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Mar 4, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: PendingCalls.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "pendingCalls")
@NamedQueries(
{
    @NamedQuery(name = "PendingCalls.findAll", query = "SELECT p FROM PendingCalls p")
})
public class PendingCalls implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idOrders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "isActive")
    private Integer isActive;
    @Basic(optional = false)
    @Column(name = "deactivated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivated;
    @Basic(optional = false)
    @Column(name = "deactivatedBy")
    private Integer deactivatedBy;

    public PendingCalls()
    {
    }

    public PendingCalls(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public PendingCalls(Integer idOrders, String reason, Date created)
    {
        this.idOrders = idOrders;
        this.reason = reason;
        this.created = created;
        this.isActive = 1;
        this.deactivated = null;
        this.deactivatedBy = null;
    }

    // <editor-fold defaultstate="collapsed" desc="Class Getters & Setters">
    
    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    public Integer getIsActive()
    {
        return isActive;
    }
    
    public void setIsActive(Integer isActive)
    {
        this.isActive = isActive;
    }
    
    public Date getDeactivated()
    {
        return deactivated;
    }
    
    public void setDeactivated(Date deactivated)
    {
        this.deactivated = deactivated;
    }
    
    public Integer getDeactivatedBy()
    {
        return deactivatedBy;
    }
    
    public void setDeactivatedBy(Integer deactivatedBy)
    {
        this.deactivatedBy = deactivatedBy;
    }
    //</editor-fold>

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idOrders != null ? idOrders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PendingCalls))
        {
            return false;
        }
        PendingCalls other = (PendingCalls) object;
        if ((this.idOrders == null && other.idOrders != null) || (this.idOrders != null && !this.idOrders.equals(other.idOrders)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.PendingCalls[ idOrders=" + idOrders + " ]";
    }

}
