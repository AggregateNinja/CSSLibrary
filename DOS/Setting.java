package DOS;

import Utility.Diff;
import java.io.Serializable;

public class Setting implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idSettings;
    private Integer settingTypeId;
    private String name;
    private String description;
    private String defaultValue;
    private String systemName;
    private Integer settingValueTypeId;
    private Boolean isUserVisible;
    private Boolean allowBlank;
    private Integer sortOrder;

    @Diff(fieldName="idSettings", isUniqueId=true)
    public Integer getIdSettings()
    {
        return idSettings;
    }

    public void setIdSettings(Integer idSettings)
    {
        this.idSettings = idSettings;
    }

    @Diff(fieldName="settingTypeId")
    public Integer getSettingTypeId()
    {
        return settingTypeId;
    }

    public void setSettingTypeId(Integer settingTypeId)
    {
        this.settingTypeId = settingTypeId;
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

    @Diff(fieldName="defaultValue")
    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
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

    @Diff(fieldName="settingValueTypeId")
    public Integer getSettingValueTypeId()
    {
        return settingValueTypeId;
    }

    public void setSettingValueTypeId(Integer settingValueTypeId)
    {
        this.settingValueTypeId = settingValueTypeId;
    }

    @Diff(fieldName="isUserVisible")
    public Boolean getIsUserVisible()
    {
        return isUserVisible;
    }

    public void setIsUserVisible(Boolean isUserVisible)
    {
        this.isUserVisible = isUserVisible;
    }

    @Diff(fieldName="allowBlank")
    public Boolean isAllowBlank()
    {
        return allowBlank;
    }

    public void setAllowBlank(Boolean allowBlank)
    {
        this.allowBlank = allowBlank;
    }

    @Diff(fieldName="sortOrder")
    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }
    
}
