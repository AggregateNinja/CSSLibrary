package DOS;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @date:   Jun 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Substances.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "substances")
@NamedQueries(
{
    @NamedQuery(name = "Substances.findAll", query = "SELECT s FROM Substances s"),
    @NamedQuery(name = "Substances.findByIdsubstances", query = "SELECT s FROM Substances s WHERE s.idsubstances = :idsubstances"),
    @NamedQuery(name = "Substances.findBySubstance", query = "SELECT s FROM Substances s WHERE s.substance = :substance")
})
public class Substances implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubstances")
    private Integer idsubstances;
    @Basic(optional = false)
    @Column(name = "substance")
    private String substance;

    public Substances()
    {
    }

    public Substances(Integer idsubstances)
    {
        this.idsubstances = idsubstances;
    }

    public Substances(Integer idsubstances, String substance)
    {
        this.idsubstances = idsubstances;
        this.substance = substance;
    }

    public Integer getIdsubstances()
    {
        return idsubstances;
    }

    public void setIdsubstances(Integer idsubstances)
    {
        this.idsubstances = idsubstances;
    }

    public String getSubstance()
    {
        return substance;
    }

    public void setSubstance(String substance)
    {
        this.substance = substance;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idsubstances != null ? idsubstances.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Substances))
        {
            return false;
        }
        Substances other = (Substances) object;
        if ((this.idsubstances == null && other.idsubstances != null) || (this.idsubstances != null && !this.idsubstances.equals(other.idsubstances)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Substances[ idsubstances=" + idsubstances + " ]";
    }

}
