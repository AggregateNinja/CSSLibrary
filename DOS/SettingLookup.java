package DOS;

import BL.SettingsBL.SettingTypeDefinition;
import Utility.Diff;
import java.io.Serializable;
import java.util.Date;

/**
 * Generic data object represents a lookup row that maps a system object
 *  and its setting value to a setting row. The type is defined by the 
 *  generic argument.
 * 
 * @param <T>  Definition defines the details of the setting lookup table
 */
public class SettingLookup<T extends SettingTypeDefinition> implements Serializable
{
    private static final long serialversionUID = 42L;

    private T typeDefinition;
    
    private Integer id;
    private Integer lookupId;
    private Integer settingId;
    private String value;
    private Boolean active;
    private Date created;
    private Integer createdById;
    private Date updated;
    private Integer updatedById;

    public SettingLookup(T typeDefinition) throws IllegalArgumentException
    {
        if (typeDefinition == null)
        {
            throw new IllegalArgumentException("SettingLookup::default constructor:"
                    + " Received a [NULL] generic SettingTypeDefinition argument."
                    + " This is necessary to construct a SettingLookup object");
        }
        
        this.typeDefinition = typeDefinition;
    }

    /**
     *  Unique id of the setting lookup table
     * @return 
     */
    public Integer getId()
    {
        return id;
    }

    /**
     * Unique id of the setting lookup table
     * @param id 
     */
    public void setId(Integer id)
    {
        this.id = id;
    }

    /**
     * Unique identifier of the system object that is mapped to the setting
     * @return 
     */
    public Integer getLookupId()
    {
        return lookupId;
    }

    /**
     * Unique identifier of the system object that is mapped to the setting
     * @param lookupId
     */
    public void setLookupId(Integer lookupId)
    {
        this.lookupId = lookupId;
    }

    @Diff(fieldName="settingId")
    public Integer getSettingId()
    {
        return settingId;
    }

    public void setSettingId(Integer settingId)
    {
        this.settingId = settingId;
    }

    @Diff(fieldName="value")
    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @Diff(fieldName="active")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Integer createdById)
    {
        this.createdById = createdById;
    }

    @Diff(fieldName="updated")
    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }

    @Diff(fieldName="updatedById")
    public Integer getUpdatedById()
    {
        return updatedById;
    }

    public void setUpdatedById(Integer updatedById)
    {
        this.updatedById = updatedById;
    }
    
    public SettingTypeDefinition getSettingTypeDefinition()
    {
        return typeDefinition;
    }
}
