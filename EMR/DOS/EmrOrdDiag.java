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
 * @date:   Jun 4, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DOS
 * @file name: EmrOrdDiag.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "emrOrdDiag")
@NamedQueries(
{
    @NamedQuery(name = "EmrOrdDiag.findAll", query = "SELECT e FROM EmrOrdDiag e")
})
public class EmrOrdDiag implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Basic(optional = false)
    @Column(name = "DiagnosisCodes")
    private String diagnosisCodes;

    public EmrOrdDiag()
    {
    }

    public EmrOrdDiag(Integer id)
    {
        this.id = id;
    }

    public EmrOrdDiag(Integer id, int idOrders, String diagnosisCodes)
    {
        this.id = id;
        this.idOrders = idOrders;
        this.diagnosisCodes = diagnosisCodes;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public String getDiagnosisCodes()
    {
        return diagnosisCodes;
    }

    public void setDiagnosisCodes(String diagnosisCodes)
    {
        this.diagnosisCodes = diagnosisCodes;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrOrdDiag))
        {
            return false;
        }
        EmrOrdDiag other = (EmrOrdDiag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.EmrOrdDiag[ id=" + id + " ]";
    }

}
