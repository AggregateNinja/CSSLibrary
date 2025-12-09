package DOS;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @date:   Aug 16, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: Testcategory.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "testcategory")
@NamedQueries(
{
    @NamedQuery(name = "Testcategory.findAll", query = "SELECT t FROM Testcategory t")
})
public class Testcategory implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtestcategory")
    private Integer idtestcategory;
    @Column(name = "testId")
    private Integer testId;
    @Column(name = "label")
    private String label;

    public Testcategory()
    {
    }

    public Testcategory(Integer idtestcategory)
    {
        this.idtestcategory = idtestcategory;
    }

    public Integer getIdtestcategory()
    {
        return idtestcategory;
    }

    public void setIdtestcategory(Integer idtestcategory)
    {
        this.idtestcategory = idtestcategory;
    }

    public Integer getTestId()
    {
        return testId;
    }

    public void setTestId(Integer testId)
    {
        this.testId = testId;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idtestcategory != null ? idtestcategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testcategory))
        {
            return false;
        }
        Testcategory other = (Testcategory) object;
        if ((this.idtestcategory == null && other.idtestcategory != null) || (this.idtestcategory != null && !this.idtestcategory.equals(other.idtestcategory)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DAOS.Testcategory[ idtestcategory=" + idtestcategory + " ]";
    }

}
