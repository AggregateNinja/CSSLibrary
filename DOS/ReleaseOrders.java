/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 5, 2016 - Apr 21, 2016
 */
@Entity 
@Table(name = "releaseOrders")
public class ReleaseOrders implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreleaseOrders")
    private Integer idreleaseOrders;
    @Basic(optional = false)
    @Column(name = "releaseId")
    private int releaseId;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "clientId")
    private int clientId;
    @Basic(optional = false)
    @Column(name = "queued")
    private boolean queued;
    @Basic(optional = false)
    @Column(name = "inProcess")
    private boolean inProcess;
    
    public ReleaseOrders() {}
    
    public ReleaseOrders(int releaseId, int orderId, int clientId)
    {
        this.releaseId = releaseId;
        this.orderId = orderId;
        this.clientId = clientId;
        this.queued = false;
        this.inProcess = false;
    }
    
    public ReleaseOrders(int releaseId, int orderId, int clientId, boolean queued, boolean inProcess)
    {
        this.releaseId = releaseId;
        this.orderId = orderId;
        this.clientId = clientId;
        this.queued = queued;
        this.inProcess = inProcess;
    }

    public int getIdreleaseOrders()
    {
        return idreleaseOrders;
    }

    public void setIdreleaseOrders(int idreleaseOrders)
    {
        this.idreleaseOrders = idreleaseOrders;
    }

    public int getReleaseId()
    {
        return releaseId;
    }

    public void setReleaseId(int releaseId)
    {
        this.releaseId = releaseId;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public boolean isQueued()
    {
        return queued;
    }

    public void setQueued(boolean queued)
    {
        this.queued = queued;
    }

    public boolean isInProcess()
    {
        return inProcess;
    }

    public void setInProcess(boolean inProcess)
    {
        this.inProcess = inProcess;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReleaseOrders))
        {
            return false;
        }
        ReleaseOrders other = (ReleaseOrders) object;
        if ((this.idreleaseOrders == null && other.idreleaseOrders != null)
                || (this.idreleaseOrders != null && !this.idreleaseOrders.equals(other.idreleaseOrders)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.idreleaseOrders);
        hash = 37 * hash + this.releaseId;
        hash = 37 * hash + this.clientId;
        hash = 37 * hash + this.orderId;
        return hash;
    }

    @Override
    public String toString()
    {
        return "DOS.ReleaseOrders[ id=" + idreleaseOrders + ", releaseId=" + releaseId + ", clientId=" + clientId + ", orderId=" + orderId +" ]";
    }
}
