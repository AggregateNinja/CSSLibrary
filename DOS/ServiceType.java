package DOS;

import Utility.Diff;
import java.io.Serializable;

public class ServiceType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idServiceTypes;
    private String abbr;
    private String name;
    private String systemName;
    private Integer active;

    @Diff(fieldName="idServiceTypes", isUniqueId = true)
    public Integer getIdServiceTypes()
    {
        return idServiceTypes;
    }

    public void setIdServiceTypes(Integer idServiceTypes)
    {
        this.idServiceTypes = idServiceTypes;
    }

    @Diff(fieldName="abbr")
    public String getAbbr()
    {
        return abbr;
    }

    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }

    @Diff(fieldName="name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName="systemName")
    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    @Diff(fieldName="active")
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
        return name;
    }
}
