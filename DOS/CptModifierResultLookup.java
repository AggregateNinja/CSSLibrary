
package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;

public class CptModifierResultLookup
{
    private static final long serialversionUID = 42L;
    @Basic(optional = false)
    @Column(name = "resultId")
    private int resultId;
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

    public int getResultId()
    {
        return resultId;
    }

    public void setResultId(int resultId)
    {
        this.resultId = resultId;
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
        
        if (other instanceof CptModifierResultLookup == false) return false;

        CptModifierResultLookup otherCpt = (CptModifierResultLookup)other;
        
        return otherCpt.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + this.resultId;
        hash = 79 * hash + this.cptModifierId;
        hash = 79 * hash + (this.active ? 1 : 0);
        return hash;
    }
}