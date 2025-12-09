package DOS;

import java.io.Serializable;
import java.util.Date;

public class InsuranceType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idInsuranceTypes;
    private String name;
    private String systemName;
    private Integer createdById;
    private Date created;
    private Integer active;

    public Integer getIdInsuranceTypes()
    {
        return idInsuranceTypes;
    }

    public void setIdInsuranceTypes(Integer idInsuranceTypes)
    {
        this.idInsuranceTypes = idInsuranceTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public Integer getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Integer createdById)
    {
        this.createdById = createdById;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getActive()
    {
        return active;
    }

    public void setActive(Integer active)
    {
        this.active = active;
    }
    
    @Override
    public String toString()
    {
        return this.name;
    }
}
