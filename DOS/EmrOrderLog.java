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
 * @date:   Apr 21, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: EmrOrderLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "emrOrderLog")
@NamedQueries(
{
    @NamedQuery(name = "EmrOrderLog.findAll", query = "SELECT e FROM EmrOrderLog e")
})
public class EmrOrderLog implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemrOrderLog")
    private Integer idemrOrderLog;
    @Basic(optional = false)
    @Column(name = "idEmrXref")
    private int idEmrXref;
    @Basic(optional = false)
    @Column(name = "emrIdOrders")
    private int emrIdOrders;
    @Column(name = "lisIdOrders")
    private Integer lisIdOrders;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "error")
    private String error;
    @Column(name = "description")
    private String description;

    public EmrOrderLog()
    {
    }

    public EmrOrderLog(Integer idemrOrderLog)
    {
        this.idemrOrderLog = idemrOrderLog;
    }

    public EmrOrderLog(Integer idemrOrderLog, int idEmrXref, int emrIdOrders, String status, Date created)
    {
        this.idemrOrderLog = idemrOrderLog;
        this.idEmrXref = idEmrXref;
        this.emrIdOrders = emrIdOrders;
        this.status = status;
        this.created = created;
    }

    public Integer getIdemrOrderLog()
    {
        return idemrOrderLog;
    }

    public void setIdemrOrderLog(Integer idemrOrderLog)
    {
        this.idemrOrderLog = idemrOrderLog;
    }

    public int getIdEmrXref()
    {
        return idEmrXref;
    }

    public void setIdEmrXref(int idEmrXref)
    {
        this.idEmrXref = idEmrXref;
    }

    public int getEmrIdOrders()
    {
        return emrIdOrders;
    }

    public void setEmrIdOrders(int emrIdOrders)
    {
        this.emrIdOrders = emrIdOrders;
    }

    public Integer getLisIdOrders()
    {
        return lisIdOrders;
    }

    public void setLisIdOrders(Integer lisIdOrders)
    {
        this.lisIdOrders = lisIdOrders;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idemrOrderLog != null ? idemrOrderLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrOrderLog))
        {
            return false;
        }
        EmrOrderLog other = (EmrOrderLog) object;
        if ((this.idemrOrderLog == null && other.idemrOrderLog != null) || (this.idemrOrderLog != null && !this.idemrOrderLog.equals(other.idemrOrderLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.EmrOrderLog[ idemrOrderLog=" + idemrOrderLog + " ]";
    }

}
