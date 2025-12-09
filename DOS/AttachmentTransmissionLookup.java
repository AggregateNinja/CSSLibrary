
package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AttachmentTransmissionLookup
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Column(name = "clientId")
    private int clientId;
    @Basic (optional = false)
    @Column(name = "attachmentTypeId")
    private int attachmentTypeId;
    @Basic (optional = false)
    @Column(name = "transmissionTypeId")
    private int transmissionTypeId;
    @Basic(optional = false)
    @Column(name = "printOrder")
    private int printOrder;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;
    @Column(name = "createdById")
    private int createdById;
    @Column(name = "modifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column(name = "modifiedById")
    private Integer modifiedById;
    @Basic(optional=false)
    @Column(name = "active")
    private boolean active;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public int getAttachmentTypeId()
    {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(int attachmentTypeId)
    {
        this.attachmentTypeId = attachmentTypeId;
    }

    public int getTransmissionTypeId()
    {
        return transmissionTypeId;
    }

    public void setTransmissionTypeId(int transmissionTypeId)
    {
        this.transmissionTypeId = transmissionTypeId;
    }

    public int getPrintOrder()
    {
        return printOrder;
    }

    public void setPrintOrder(int printOrder)
    {
        this.printOrder = printOrder;
    }
    
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public int getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(int createdById)
    {
        this.createdById = createdById;
    }

    public Date getModifiedDate()
    {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedById()
    {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById)
    {
        this.modifiedById = modifiedById;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
