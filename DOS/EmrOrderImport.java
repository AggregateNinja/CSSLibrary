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
 * @file name: EmrOrderImport.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "emrOrderImport")
@NamedQueries(
{
    @NamedQuery(name = "EmrOrderImport.findAll", query = "SELECT e FROM EmrOrderImport e")
})
public class EmrOrderImport implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemrOrderImport")
    private Integer idemrOrderImport;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Basic(optional = false)
    @Column(name = "processed")
    private boolean processed;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public EmrOrderImport()
    {
    }

    public EmrOrderImport(Integer idemrOrderImport)
    {
        this.idemrOrderImport = idemrOrderImport;
    }

    public EmrOrderImport(Integer idemrOrderImport, int idOrders, boolean processed, Date created)
    {
        this.idemrOrderImport = idemrOrderImport;
        this.idOrders = idOrders;
        this.processed = processed;
        this.created = created;
    }

    public Integer getIdemrOrderImport()
    {
        return idemrOrderImport;
    }

    public void setIdemrOrderImport(Integer idemrOrderImport)
    {
        this.idemrOrderImport = idemrOrderImport;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public boolean getProcessed()
    {
        return processed;
    }

    public void setProcessed(boolean processed)
    {
        this.processed = processed;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idemrOrderImport != null ? idemrOrderImport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrOrderImport))
        {
            return false;
        }
        EmrOrderImport other = (EmrOrderImport) object;
        if ((this.idemrOrderImport == null && other.idemrOrderImport != null) || (this.idemrOrderImport != null && !this.idemrOrderImport.equals(other.idemrOrderImport)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.EmrOrderImport[ idemrOrderImport=" + idemrOrderImport + " ]";
    }

}
