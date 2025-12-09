
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CptCode implements Serializable
{
    private static final long serialversionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCptCodes")
    private int idCptCodes;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
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

    public int getIdCptCodes()
    {
        return idCptCodes;
    }

    public void setIdCptCodes(int idCptCodes)
    {
        this.idCptCodes = idCptCodes;
    }

    @Diff(fieldName="code")
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    @Diff(fieldName="description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Diff(fieldName="active")
    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Diff(fieldName="createdById")
    public int getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(int createdById)
    {
        this.createdById = createdById;
    }

    @Diff(fieldName="createdDate")
    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    @Diff(fieldName="deactivatedById")
    public Integer getDeactivatedById()
    {
        return deactivatedById;
    }

    public void setDeactivatedById(Integer deactivatedById)
    {
        this.deactivatedById = deactivatedById;
    }
    
    @Diff(fieldName="deactivatedDate")
    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    @Override
    public String toString()
    {
        if (code == null) return "";
        return this.code;
    }
}
