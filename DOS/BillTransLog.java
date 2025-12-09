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
 * @date:   Feb 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: TransLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "transLog")
@NamedQueries(
{
    @NamedQuery(name = "TransLog.findAll", query = "SELECT t FROM TransLog t")
})
public class BillTransLog implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTransLog")
    private Integer idTransLog;
    @Column(name = "ReleaseJobID")
    private Integer releaseJobID;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "transDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    @Column(name = "transmitted")
    private Boolean transmitted;
    @Column(name = "billDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDate;
    @Column(name = "billingInterface")
    private Boolean billingInterface;
    @Column(name = "idUser")
    private Integer idUser;

    public BillTransLog()
    {
    }

    public BillTransLog(Integer idTransLog)
    {
        this.idTransLog = idTransLog;
    }

    public BillTransLog(Integer idTransLog, int idOrders, Date transDate)
    {
        this.idTransLog = idTransLog;
        this.idOrders = idOrders;
        this.transDate = transDate;
    }

    public Integer getIdTransLog()
    {
        return idTransLog;
    }

    public void setIdTransLog(Integer idTransLog)
    {
        this.idTransLog = idTransLog;
    }

    public Integer getReleaseJobID()
    {
        return releaseJobID;
    }

    public void setReleaseJobID(Integer releaseJobID)
    {
        this.releaseJobID = releaseJobID;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public Date getTransDate()
    {
        return transDate;
    }

    public void setTransDate(Date transDate)
    {
        this.transDate = transDate;
    }

    public Boolean getTransmitted()
    {
        return transmitted;
    }

    public void setTransmitted(Boolean transmitted)
    {
        this.transmitted = transmitted;
    }

    public Boolean getBillingInterface()
    {
        return billingInterface;
    }

    public void setBillingInterface(Boolean billingInterface)
    {
        this.billingInterface = billingInterface;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getBillDate()
    {
        return billDate;
    }

    public void setBillDate(Date billDate)
    {
        this.billDate = billDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += 87 + (idTransLog != null ? idTransLog.hashCode() : 0) + (created != null ? created.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillTransLog))
        {
            return false;
        }
        BillTransLog other = (BillTransLog) object;
        if ((this.idTransLog == null && other.idTransLog != null) || (this.idTransLog != null && !this.idTransLog.equals(other.idTransLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.TransLog[ idTransLog=" + idTransLog + " ]";
    }

}
