
package EMR.DOS;

import java.util.Date;

public class EmrEventLog
{
    private Integer idEmrEventLog;
    private Integer emrEventTypeId;
    private Date created;
    private Integer userId;
    private Integer emrXrefId;   // EmrXrefs Interface identifier
    private String emrOrderId;   // The interface's "accession"
    private Integer emrIdOrders; // The unique database identifier of the emrorders.orders table
    private String description;  // System description
    private String userNote;     // User-entered description
    
    public Integer getIdEmrEventLog()
    {
        return idEmrEventLog;
    }

    public void setIdEmrEventLog(Integer idEmrEventLog)
    {
        this.idEmrEventLog = idEmrEventLog;
    }

    public Integer getEmrEventTypeId()
    {
        return emrEventTypeId;
    }

    public void setEmrEventTypeId(Integer emrEventTypeId)
    {
        this.emrEventTypeId = emrEventTypeId;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getEmrXrefId()
    {
        return emrXrefId;
    }

    public void setEmrXrefId(Integer xrefId)
    {
        this.emrXrefId = xrefId;
    }

    public String getEmrOrderId()
    {
        return emrOrderId;
    }

    public void setEmrOrderId(String emrOrderId)
    {
        this.emrOrderId = emrOrderId;
    }

    public Integer getEmrIdOrders()
    {
        return emrIdOrders;
    }

    public void setEmrIdOrders(Integer emrIdOrders)
    {
        this.emrIdOrders = emrIdOrders;
    }
    

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getUserNote()
    {
        return userNote;
    }

    public void setUserNote(String userNote)
    {
        this.userNote = userNote;
    }

}
