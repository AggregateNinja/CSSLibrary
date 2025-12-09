package DOS;

import java.io.Serializable;

public class DetailOrderEvent implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idDetailOrderEvents;
    private Integer detailOrderId;
    private Integer detailCptCodeId;
    private Integer eventId;
    private String description;
    private Integer userId;

    public Integer getIdDetailOrderEvents()
    {
        return idDetailOrderEvents;
    }

    public void setIdDetailOrderEvents(Integer idDetailOrderEvents)
    {
        this.idDetailOrderEvents = idDetailOrderEvents;
    }

    public Integer getDetailOrderId()
    {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId)
    {
        this.detailOrderId = detailOrderId;
    }

    public Integer getDetailCptCodeId()
    {
        return detailCptCodeId;
    }

    public void setDetailCptCodeId(Integer detailCptCodeId)
    {
        this.detailCptCodeId = detailCptCodeId;
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
