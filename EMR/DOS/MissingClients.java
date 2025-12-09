package EMR.DOS;

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
 * @date:   Jul 8, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DOS
 * @file name: MissingClients.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "missingClients")
@NamedQueries(
{
    @NamedQuery(name = "MissingClients.findAll", query = "SELECT m FROM MissingClients m")
})
public class MissingClients implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMissingClients")
    private Integer idMissingClients;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "clientID")
    private String clientID;
    @Column(name = "clientName")
    private String clientName;

    public MissingClients()
    {
    }

    public MissingClients(Integer idMissingClients)
    {
        this.idMissingClients = idMissingClients;
    }

    public MissingClients(Integer idMissingClients, int orderId)
    {
        this.idMissingClients = idMissingClients;
        this.orderId = orderId;
    }

    public Integer getIdMissingClients()
    {
        return idMissingClients;
    }

    public void setIdMissingClients(Integer idMissingClients)
    {
        this.idMissingClients = idMissingClients;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public String getClientName()
    {
        return clientName;
    }

    public void setClientName(String clientName)
    {
        this.clientName = clientName;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idMissingClients != null ? idMissingClients.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MissingClients))
        {
            return false;
        }
        MissingClients other = (MissingClients) object;
        if ((this.idMissingClients == null && other.idMissingClients != null) || (this.idMissingClients != null && !this.idMissingClients.equals(other.idMissingClients)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.MissingClients[ idMissingClients=" + idMissingClients + " ]";
    }

}
