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
 * @date:   Nov 25, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: ClientCounselorRelationship.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "clientCounselorRelationship")
@NamedQueries(
{
    @NamedQuery(name = "ClientCounselorRelationship.findAll", query = "SELECT c FROM ClientCounselorRelationship c")
})
public class ClientCounselorRelationship implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClientCounselorRelationship")
    private Integer idClientCounselorRelationship;
    @Basic(optional = false)
    @Column(name = "idclients")
    private int idclients;
    @Basic(optional = false)
    @Column(name = "idcounselors")
    private int idcounselors;
    
    public ClientCounselorRelationship()
    {
    }

    public ClientCounselorRelationship(Integer idClientCounselorRelationship)
    {
        this.idClientCounselorRelationship = idClientCounselorRelationship;
    }

    public Integer getIdClientCounselorRelationship()
    {
        return idClientCounselorRelationship;
    }

    public void setIdClientCounselorRelationship(Integer idClientCounselorRelationship)
    {
        this.idClientCounselorRelationship = idClientCounselorRelationship;
    }

    public int getIdClients()
    {
        return idclients;
    }

    public void setIdClients(int idclients)
    {
        this.idclients = idclients;
    }

    public int getIdCounselors()
    {
        return idcounselors;
    }

    public void setIdCounselors(int idcounselors)
    {
        this.idcounselors = idcounselors;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idClientCounselorRelationship != null ? idClientCounselorRelationship.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientCounselorRelationship))
        {
            return false;
        }
        ClientCounselorRelationship other = (ClientCounselorRelationship) object;
        if ((this.idClientCounselorRelationship == null && other.idClientCounselorRelationship != null) || (this.idClientCounselorRelationship != null && !this.idClientCounselorRelationship.equals(other.idClientCounselorRelationship)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.ClientCounselorRelationship[ idClientCounselorRelationship=" + idClientCounselorRelationship + " ]";
    }

}
