package DOS;

import java.io.Serializable;

public class TestRangeType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idRangeTypes;
    private Integer rangeNumber;
    private String rangeName;
    private String systemName;
    private Boolean isActive;

    public Integer getIdRangeTypes()
    {
        return idRangeTypes;
    }

    public void setIdRangeTypes(Integer idRangeTypes)
    {
        this.idRangeTypes = idRangeTypes;
    }

    public Integer getRangeNumber()
    {
        return rangeNumber;
    }

    public void setRangeNumber(Integer rangeNumber)
    {
        this.rangeNumber = rangeNumber;
    }

    public String getRangeName()
    {
        return rangeName;
    }

    public void setRangeName(String rangeName)
    {
        this.rangeName = rangeName;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public Boolean isIsActive()
    {
        return isActive;
    }

    public void setIsActive(Boolean isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        if (rangeName == null)
        {
            return "[No range name]";
        }
        return rangeName.toString();
    }

}
