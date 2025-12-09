package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Jan 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: PendingAdvancedOrders.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "pendingAdvancedOrders")
@NamedQueries(
{
    @NamedQuery(name = "PendingAdvancedOrders.findAll", query = "SELECT p FROM PendingAdvancedOrders p")
})
public class PendingAdvancedOrders implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idPhlebotomy")
    private Integer idPhlebotomy;

    public PendingAdvancedOrders()
    {
    }

    public PendingAdvancedOrders(Integer idPhlebotomy)
    {
        this.idPhlebotomy = idPhlebotomy;
    }

    public Integer getIdPhlebotomy()
    {
        return idPhlebotomy;
    }

    public void setIdPhlebotomy(Integer idPhlebotomy)
    {
        this.idPhlebotomy = idPhlebotomy;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idPhlebotomy != null ? idPhlebotomy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PendingAdvancedOrders))
        {
            return false;
        }
        PendingAdvancedOrders other = (PendingAdvancedOrders) object;
        if ((this.idPhlebotomy == null && other.idPhlebotomy != null) || (this.idPhlebotomy != null && !this.idPhlebotomy.equals(other.idPhlebotomy)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.PendingAdvancedOrders[ idPhlebotomy=" + idPhlebotomy + " ]";
    }

}
