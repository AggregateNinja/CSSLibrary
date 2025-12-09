package DOS;

import Utility.Diff;
import java.io.Serializable;

public class SettingType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idSettingTypes;
    private String name;
    private String description;
    private String systemName;

    @Diff(fieldName="idSettingTypes", isUniqueId=true)
    public Integer getIdSettingTypes()
    {
        return idSettingTypes;
    }

    public void setIdSettingTypes(Integer idSettingTypes)
    {
        this.idSettingTypes = idSettingTypes;
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

    @Diff(fieldName="description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
}
