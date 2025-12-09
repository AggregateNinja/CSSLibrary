package DOS;

import java.io.Serializable;

public class InsuranceSubmissionMode implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idInsuranceSubmissionModes;
    private String name;
    private String systemName;
    private Integer active;

    public Integer getIdInsuranceSubmissionModes()
    {
        return idInsuranceSubmissionModes;
    }

    public void setIdInsuranceSubmissionModes(Integer idInsuranceSubmissionModes)
    {
        this.idInsuranceSubmissionModes = idInsuranceSubmissionModes;
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
