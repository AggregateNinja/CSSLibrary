package DOS;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable
{
    private static final long serialversionUID = 42L;

    private Integer idEvents;
    private Integer eventTypeId;
    private Integer userId;
    private Date date;

    public Integer getIdEvents()
    {
        return idEvents;
    }

    public void setIdEvents(Integer idEvents)
    {
        this.idEvents = idEvents;
    }

    public Integer getEventTypeId()
    {
        return eventTypeId;
    }

    public void setEventTypeId(Integer eventTypeId)
    {
        this.eventTypeId = eventTypeId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

}
