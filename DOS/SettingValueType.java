package DOS;

import Utility.Diff;
import java.io.Serializable;

public class SettingValueType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idSettingValueTypes;
    private String name;
    private String systemName;

    @Diff(fieldName="idSettingValueTypes", isUniqueId=true)
    public Integer getIdSettingValueTypes()
    {
        return idSettingValueTypes;
    }

    public void setIdSettingValueTypes(Integer idSettingValueTypes)
    {
        this.idSettingValueTypes = idSettingValueTypes;
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
}
