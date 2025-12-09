
package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AttachmentLookup
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "patientId")
    private Integer patientId;
    @Column(name = "resultId")
    private Integer resultId;
    @Basic(optional=false)
    @Column(name = "attachmentTypeId")
    private Integer attachmentTypeId;
    @Basic(optional=false)
    @Column(name = "attachmentDataId")
    private int attachmentDataId;
    @Basic(optional=false)
    @Column(name = "attachmentMetadataId")
    private Integer attachmentMetadataId;
    @Column(name = "description")
    private String description;
    @Column(name = "isWebAttachment")
    private boolean isWebAttachment;
    @Column(name = "approvedForTransmission")
    private boolean approvedForTransmission;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "attachedDate")
    private Date attachedDate;
    @Basic(optional=false)
    @Column(name = "attachedById")
    private int attachedById;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modifiedDate")
    private Date modifiedDate;
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

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public Integer getPatientId()
    {
        return patientId;
    }

    public void setPatientId(Integer patientId)
    {
        this.patientId = patientId;
    }

    public Integer getResultId()
    {
        return resultId;
    }

    public void setResultId(Integer resultId)
    {
        this.resultId = resultId;
    }

    public Integer getAttachmentTypeId()
    {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId)
    {
        this.attachmentTypeId = attachmentTypeId;
    }

    public int getAttachmentDataId()
    {
        return attachmentDataId;
    }

    public void setAttachmentDataId(int attachmentDataId)
    {
        this.attachmentDataId = attachmentDataId;
    }

    public Integer getAttachmentMetadataId()
    {
        return attachmentMetadataId;
    }

    public void setAttachmentMetadataId(Integer attachmentMetaDataId)
    {
        this.attachmentMetadataId = attachmentMetaDataId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isApprovedForTransmission()
    {
        return approvedForTransmission;
    }

    public void setApprovedForTransmission(boolean approvedForTransmission)
    {
        this.approvedForTransmission = approvedForTransmission;
    }
    
    public boolean isIsWebAttachment()
    {
        return isWebAttachment;
    }

    public void setIsWebAttachment(boolean isWebAttachment)
    {
        this.isWebAttachment = isWebAttachment;
    }

    public Date getAttachedDate()
    {
        return attachedDate;
    }

    public void setAttachedDate(Date attachedDate)
    {
        this.attachedDate = attachedDate;
    }

    public int getAttachedById()
    {
        return attachedById;
    }

    public void setAttachedById(int attachedById)
    {
        this.attachedById = attachedById;
    }

    public Date getModifiedDate()
    {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
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
