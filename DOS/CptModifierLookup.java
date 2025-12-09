
package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;

public class CptModifierLookup implements Comparable
{
    private static final long serialversionUID = 42L;
    @Basic(optional = false)
    @Column(name = "cptLookupId")
    private int cptLookupId;
    @Basic(optional = false)
    @Column(name = "cptModifierId")
    private int cptModifierId;
    @Basic(optional = false)
    @Column(name = "createdById")
    private int createdById;
    @Basic(optional = false)
    @Column(name = "createdDate")
    Date createdDate;
    @Column(name = "deactivatedById")
    private Integer deactivatedById;
    @Column(name = "deactivatedDate")
    private Date deactivatedDate;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public int getCptLookupId()
    {
        return cptLookupId;
    }

    public void setCptLookupId(int cptLookupId)
    {
        this.cptLookupId = cptLookupId;
    }

    public int getCptModifierId()
    {
        return cptModifierId;
    }

    public void setCptModifierId(int cptModifierId)
    {
        this.cptModifierId = cptModifierId;
    }

    public int getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(int createdById)
    {
        this.createdById = createdById;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Integer getDeactivatedById()
    {
        return deactivatedById;
    }

    public void setDeactivatedById(Integer deactivatedById)
    {
        this.deactivatedById = deactivatedById;
    }

    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if (other == null) return false;
        
        if (other instanceof CptModifierLookup == false) return false;

        CptModifierLookup otherCpt = (CptModifierLookup)other;
        
        return otherCpt.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + this.cptLookupId;
        hash = 79 * hash + this.cptModifierId;
        hash = 79 * hash + (this.active ? 1 : 0);
        return hash;
    }

    @Override
    public int compareTo(Object o)
    {
        if (o == null) return -1;
        
        if (o instanceof CptModifierLookup == false) return -1;
        
        CptModifierLookup other = (CptModifierLookup)o;
        
        if (other.getCptModifierId() <= 0) return -1;
        if (other.getCptModifierId() == this.getCptModifierId()) return 0;
        if (other.getCptModifierId() < this.getCptModifierId()) return -1;
        if (other.getCptModifierId() > this.getCptModifierId()) return 1;
        
        return -1;
    }
}