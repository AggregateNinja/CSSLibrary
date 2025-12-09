package DOS;

import Utility.Diff;
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
 * @date:   Jun 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Drugs.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "drugs")
@NamedQueries(
{
    @NamedQuery(name = "Drugs.findAll", query = "SELECT d FROM Drugs d"),
    @NamedQuery(name = "Drugs.findByIddrugs", query = "SELECT d FROM Drugs d WHERE d.iddrugs = :iddrugs"),
    @NamedQuery(name = "Drugs.findByGenericName", query = "SELECT d FROM Drugs d WHERE d.genericName = :genericName")
})
public class Drugs implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddrugs")
    private Integer iddrugs;
    @Basic(optional = false)
    @Column(name = "genericName")
    private String genericName;
    @Column(name = "substance3")
    private Integer substance3;
    @Column(name = "substance2")
    private Integer substance2;
    @Column(name = "substance1")
    private Integer substance1;

    public Drugs()
    {
    }

    public Drugs(Integer iddrugs)
    {
        this.iddrugs = iddrugs;
    }

    public Drugs(Integer iddrugs, String genericName)
    {
        this.iddrugs = iddrugs;
        this.genericName = genericName;
    }

    public Drugs(Integer iddrugs, String genericName, Integer substance3, Integer substance2, Integer substance1)
    {
        this.iddrugs = iddrugs;
        this.genericName = genericName;
        this.substance3 = substance3;
        this.substance2 = substance2;
        this.substance1 = substance1;
    }

    public Integer getIddrugs()
    {
        return iddrugs;
    }

    public void setIddrugs(Integer iddrugs)
    {
        this.iddrugs = iddrugs;
    }

    @Diff(fieldName="genericName")
    public String getGenericName()
    {
        return genericName;
    }

    public void setGenericName(String genericName)
    {
        this.genericName = genericName;
    }

    @Diff(fieldName="substance3")
    public Integer getSubstance3()
    {
        return substance3;
    }

    public void setSubstance3(Integer substance3)
    {
        this.substance3 = substance3;
    }

    @Diff(fieldName="substance2")
    public Integer getSubstance2()
    {
        return substance2;
    }

    public void setSubstance2(Integer substance2)
    {
        this.substance2 = substance2;
    }

    @Diff(fieldName="substance1")
    public Integer getSubstance1()
    {
        return substance1;
    }

    public void setSubstance1(Integer substance1)
    {
        this.substance1 = substance1;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (iddrugs != null ? iddrugs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drugs))
        {
            return false;
        }
        Drugs other = (Drugs) object;
        if ((this.iddrugs == null && other.iddrugs != null) || (this.iddrugs != null && !this.iddrugs.equals(other.iddrugs)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Drugs[ iddrugs=" + iddrugs + " ]";
    }
    
    /**
     * Returns a new instance with the same data as the current DO
     * @return 
     */
    public Drugs copy()
    {
        Drugs newDrugObj = new Drugs();
        newDrugObj.setIddrugs(this.getIddrugs());
        newDrugObj.setGenericName(this.getGenericName());
        newDrugObj.setSubstance1(this.getSubstance1());
        newDrugObj.setSubstance2(this.getSubstance2());
        newDrugObj.setSubstance3(this.getSubstance3());
        return newDrugObj;
    }

}
