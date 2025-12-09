package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Nov 26, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: CustomRequests.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "customRequests")
@NamedQueries(
{
    @NamedQuery(name = "CustomRequests.findAll", query = "SELECT c FROM CustomRequests c")
})
public class CustomRequests implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCustomRequests")
    private Integer idCustomRequests;
    @Basic(optional = false)
    @Column(name = "idclients")
    private int idclients;
    @Basic(optional = false)
    @Column(name = "idtests")
    private int idtests;

    public CustomRequests()
    {
    }

    public CustomRequests(Integer idCustomRequests)
    {
        this.idCustomRequests = idCustomRequests;
    }

    public Integer getIdCustomRequests()
    {
        return idCustomRequests;
    }

    public void setIdCustomRequests(Integer idCustomRequests)
    {
        this.idCustomRequests = idCustomRequests;
    }

    public int getIdclients()
    {
        return idclients;
    }

    public void setIdclients(int idclients)
    {
        this.idclients = idclients;
    }

    public int getIdtests()
    {
        return idtests;
    }

    public void setIdtests(int idtests)
    {
        this.idtests = idtests;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idCustomRequests != null ? idCustomRequests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomRequests))
        {
            return false;
        }
        CustomRequests other = (CustomRequests) object;
        if ((this.idCustomRequests == null && other.idCustomRequests != null) || (this.idCustomRequests != null && !this.idCustomRequests.equals(other.idCustomRequests)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.CustomRequests[ idCustomRequests=" + idCustomRequests + " ]";
    }

}
