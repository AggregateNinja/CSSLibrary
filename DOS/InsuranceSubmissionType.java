package DOS;

import java.io.Serializable;

public class InsuranceSubmissionType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idInsuranceSubmissionTypes;
    private String name;
    private String systemName;
    private Integer active;

    public Integer getIdInsuranceSubmissionTypes()
    {
        return idInsuranceSubmissionTypes;
    }

    public void setIdInsuranceSubmissionTypes(Integer idInsuranceSubmissionTypes)
    {
        this.idInsuranceSubmissionTypes = idInsuranceSubmissionTypes;
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
