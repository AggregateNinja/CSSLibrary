
package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class CptModifier implements Serializable
{
    private static final long serialversionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCptModifiers")
    private int idCptModifiers;
    @Basic(optional = false)
    @Column(name = "modifier")
    private String modifier;
    @Column(name = "description")
    private String description;
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

    public int getIdCptModifiers()
    {
        return idCptModifiers;
    }

    public void setIdCptModifiers(int idCptModifiers)
    {
        this.idCptModifiers = idCptModifiers;
    }

    public String getModifier()
    {
        return modifier;
    }

    public void setModifier(String modifier)
    {
        this.modifier = modifier;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
    public String toString()
    {
        String display = this.modifier;
        if (display == null) display = "";
        if (this.description != null && this.description.trim().isEmpty() == false)
        {
            display += " " + this.description;
        }
        return display;
    }

    @Override
    public int hashCode()
    {
        return this.idCptModifiers;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final CptModifier other = (CptModifier) obj;
        return this.hashCode() == other.hashCode();
    }
    
    public CptModifier copy()
    {
        CptModifier copy = new CptModifier();
        copy.setIdCptModifiers(idCptModifiers);
        copy.setModifier(modifier);
        copy.setDescription(description);
        copy.setCreatedById(createdById);
        copy.setCreatedDate(createdDate);
        copy.setDeactivatedById(deactivatedById);
        copy.setDeactivatedDate(deactivatedDate);
        copy.setActive(active);
        return copy;
    }
}
