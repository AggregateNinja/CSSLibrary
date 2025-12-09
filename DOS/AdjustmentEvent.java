package DOS;

import java.io.Serializable;

public class AdjustmentEvent implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idAdjustmentEvents;
    private Integer adjustmentId;
    private Integer eventId;
    private String description;
    private Integer userId;

    public Integer getIdAdjustmentEvents()
    {
        return idAdjustmentEvents;
    }

    public void setIdAdjustmentEvents(Integer idAdjustmentEvents)
    {
        this.idAdjustmentEvents = idAdjustmentEvents;
    }

    public Integer getAdjustmentId()
    {
        return adjustmentId;
    }

    public void setAdjustmentId(Integer adjustmentId)
    {
        this.adjustmentId = adjustmentId;
    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId(Integer eventId)
    {
        this.eventId = eventId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }    
}
