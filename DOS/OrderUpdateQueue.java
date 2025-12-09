package DOS;

import java.util.Date;

public class OrderUpdateQueue
{
    private static final long serialversionUID = 42L;

    private Integer idOrderUpdateQueue;
    private Integer orderId;
    private String updateType;
    private String queuedFrom;
    private Date queuedDate;
    private Integer queuedById;
    private Integer complete;
    private Date dateCompleted;

    public Integer getIdOrderUpdateQueue()
    {
        return idOrderUpdateQueue;
    }

    public void setIdOrderUpdateQueue(Integer idOrderUpdateQueue)
    {
        this.idOrderUpdateQueue = idOrderUpdateQueue;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public String getUpdateType()
    {
        return updateType;
    }

    public void setUpdateType(String updateType)
    {
        this.updateType = updateType;
    }

    public Date getQueuedDate()
    {
        return queuedDate;
    }

    public void setQueuedDate(Date queuedDate)
    {
        this.queuedDate = queuedDate;
    }
  
    public String getQueuedFrom()
    {
        return queuedFrom;
    }

    public void setQueuedFrom(String queuedFrom)
    {
        this.queuedFrom = queuedFrom;
    }

    public Integer getQueuedById()
    {
        return queuedById;
    }

    public void setQueuedById(Integer queuedById)
    {
        this.queuedById = queuedById;
    }

    public Integer getComplete()
    {
        return complete;
    }

    public void setComplete(Integer complete)
    {
        this.complete = complete;
    }

    public Date getDateCompleted()
    {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted)
    {
        this.dateCompleted = dateCompleted;
    }
}
