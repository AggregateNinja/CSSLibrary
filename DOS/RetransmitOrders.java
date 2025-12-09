/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
 * @date Mar 29, 2016
 */
@Entity 
@Table(name = "retransmitOrders")
public class RetransmitOrders implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRetransmitOrders")
    private Integer idRetransmitOrders;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "createdBy")
    private Integer createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdDate")
    private Date createdDate;
    @Column(name = "emr")
    private boolean emr;
    @Column(name = "print")
    private boolean print;
    @Column(name = "fax")
    private boolean fax;
    @Column(name = "transmit")
    private boolean transmit;
    @Column(name = "retransmissionRelease")
    private Integer retransmissionRelease;
    @Column(name = "retransmissionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retransmissionDate;
    @Basic(optional = false)
    @Column(name = "retransmitted")
    private boolean retransmitted;
    
    public RetransmitOrders() {}
    
    public RetransmitOrders(int idRetransmitOrders, int orderId, Integer createdBy, Date createdDate, boolean emr,
            boolean print, boolean fax, boolean transmit, Integer retransmissionRelease,
            Date retransmissionDate, boolean retransmitted)
    {
        this.idRetransmitOrders = idRetransmitOrders;
        this.orderId = orderId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.emr = emr;
        this.print = print;
        this.fax = fax;
        this.transmit = transmit;
        this.retransmissionRelease = retransmissionRelease;
        this.retransmissionDate = retransmissionDate;
        this.retransmitted = retransmitted;
    }

    public Integer getIdRetransmitOrders()
    {
        return idRetransmitOrders;
    }

    public void setIdRetransmitOrders(int idRetransmitOrders)
    {
        this.idRetransmitOrders = idRetransmitOrders;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public Integer getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    
    public boolean isEmr()
    {
        return emr;
    }

    public void setEmr(boolean emr)
    {
        this.emr = emr;
    }

    public boolean isPrint()
    {
        return print;
    }

    public void setPrint(boolean print)
    {
        this.print = print;
    }

    public boolean isFax()
    {
        return fax;
    }

    public void setFax(boolean fax)
    {
        this.fax = fax;
    }

    public boolean isTransmit()
    {
        return transmit;
    }

    public void setTransmit(boolean transmit)
    {
        this.transmit = transmit;
    }

    public boolean isRetransmitted()
    {
        return retransmitted;
    }

    public void setRetransmitted(boolean retransmitted)
    {
        this.retransmitted = retransmitted;
    }

    public Integer getRetransmissionRelease()
    {
        return retransmissionRelease;
    }

    public void setRetransmissionRelease(Integer retransmissionRelease)
    {
        this.retransmissionRelease = retransmissionRelease;
    }

    public Date getRetransmissionDate()
    {
        return retransmissionDate;
    }

    public void setRetransmissionDate(Date retransmissionDate)
    {
        this.retransmissionDate = retransmissionDate;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RetransmitOrders))
        {
            return false;
        }
        RetransmitOrders other = (RetransmitOrders) object;
        if ((this.idRetransmitOrders == null && other.idRetransmitOrders != null)
                || (this.idRetransmitOrders != null && !this.idRetransmitOrders.equals(other.idRetransmitOrders)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 56 * hash + Objects.hashCode(this.idRetransmitOrders);
        hash = 56 * hash + this.orderId;
        return hash;
    }

    @Override
    public String toString()
    {
        return "DOS.ReleaseOrders[ id=" + idRetransmitOrders + ", orderId=" + orderId +" ]";
    }
}
