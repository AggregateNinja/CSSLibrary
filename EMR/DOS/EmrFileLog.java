package EMR.DOS;

import java.util.Date;

public class EmrFileLog
{

    private static final long serialversionUID = 42L;

    private Integer id;
    private String fileName;
    private String emrOrderId;
    private String description;
    private Integer idOrders;
    private Date created;
    private Integer emrInterface;
    private String controlId;
    private Boolean errorAcknowledged;
    private Integer acknowledgedById;
    private Date acknowledgedDate;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getEmrOrderId()
    {
        return emrOrderId;
    }

    public void setEmrOrderId(String emrOrderId)
    {
        this.emrOrderId = emrOrderId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getEmrInterface()
    {
        return emrInterface;
    }

    public void setEmrInterface(Integer emrInterface)
    {
        this.emrInterface = emrInterface;
    }

    public String getControlId()
    {
        return controlId;
    }

    public void setControlId(String controlId)
    {
        this.controlId = controlId;
    }

    public Boolean isErrorAcknowledged()
    {
        return errorAcknowledged;
    }

    public void setErrorAcknowledged(Boolean errorAcknowledged)
    {
        this.errorAcknowledged = errorAcknowledged;
    }

    public Integer getAcknowledgedById()
    {
        return acknowledgedById;
    }

    public void setAcknowledgedById(Integer acknowledgedById)
    {
        this.acknowledgedById = acknowledgedById;
    }

    public Date getAcknowledgedDate()
    {
        return acknowledgedDate;
    }

    public void setAcknowledgedDate(Date acknowledgedDate)
    {
        this.acknowledgedDate = acknowledgedDate;
    }
}
