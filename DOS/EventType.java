package DOS;

import java.io.Serializable;

public class EventType implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idEventTypes;
    private String name;
    private Integer eventCategoryId;
    private String systemName;

    public Integer getIdEventTypes()
    {
        return idEventTypes;
    }

    public void setIdEventTypes(Integer idEventTypes)
    {
        this.idEventTypes = idEventTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getEventCategoryId()
    {
        return eventCategoryId;
    }

    public void setEventCategoryId(Integer eventCategoryId)
    {
        this.eventCategoryId = eventCategoryId;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }    
}
