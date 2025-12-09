package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a reference lab test definition (not one contained in the LIS)
 *  Populated directly from the reference lab result files.
 * @author TomR
 */
@Entity
@Table(name = "refTests", catalog = "css", schema="")
public class RefTest implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRefTests")
    private Integer idRefTests;
    @Basic(optional = false)
    @Column(name = "departmentId")
    private int departmentId;
    @Basic(optional = false)
    @Column(name = "identifier")
    private String identifier;
    @Column(name = "name")
    private String name;
    @Column(name = "units")
    private String units;
    @Column(name = "range")
    private String range;
    @Column(name = "active")
    private Boolean active;

    public Integer getIdRefTests() {
        return idRefTests;
    }

    public void setIdRefTests(Integer idRefTests) {
        this.idRefTests = idRefTests;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int iddepartmentId) {
        this.departmentId = iddepartmentId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRefTests != null ? idRefTests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefTest))
        {
            return false;
        }
        RefTest other = (RefTest) object;
        if ((this.idRefTests == null && other.idRefTests != null) || (this.idRefTests != null && !this.idRefTests.equals(other.idRefTests)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "RefTest[ id=" + idRefTests + ", identifier: " + identifier + ", name=" + name + " ]";        
    }
}
