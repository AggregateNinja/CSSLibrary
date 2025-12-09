package DOS;

import java.io.Serializable;

public class SubmissionEvent implements Serializable
{
    private static final long serialversionUID = 42L;

    private Integer idSubmissionEvents;
    private Integer submissionBatchId;
    private Integer submissionQueueId;
    private Integer eventId;
    private String description;
    private Integer userId;

    public Integer getIdSubmissionEvents()
    {
        return idSubmissionEvents;
    }

    public void setIdSubmissionEvents(Integer idSubmissionEvents)
    {
        this.idSubmissionEvents = idSubmissionEvents;
    }

    public Integer getSubmissionBatchId()
    {
        return submissionBatchId;
    }

    public void setSubmissionBatchId(Integer submissionBatchId)
    {
        this.submissionBatchId = submissionBatchId;
    }

    public Integer getSubmissionQueueId()
    {
        return submissionQueueId;
    }

    public void setSubmissionQueueId(Integer submissionQueueId)
    {
        this.submissionQueueId = submissionQueueId;
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
