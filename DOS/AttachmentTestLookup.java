
package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AttachmentTestLookup
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "testId")
    private int testId;
    @Column(name = "testNumber")
    private Integer testNumber;
    @Basic(optional=false)
    @Column(name = "attachmentTypeId")
    private Integer attachmentTypeId;
    @Basic(optional=false)
    @Column(name = "requiresAttachmentToPost")
    private boolean requiresAttachmentToPost;
    @Column(name = "missingAttachmentRemarkId")
    private Integer missingAttachmentRemarkId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;
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

    public int getTestId()
    {
        return testId;
    }

    public void setTestId(int testId)
    {
        this.testId = testId;
    }

    public Integer getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber)
    {
        this.testNumber = testNumber;
    }

    public Integer getAttachmentTypeId()
    {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId)
    {
        this.attachmentTypeId = attachmentTypeId;
    }

    public boolean requiresAttachmentToPost()
    {
        return requiresAttachmentToPost;
    }

    public void setRequiresAttachmentToPost(boolean requiresAttachmentToPost)
    {
        this.requiresAttachmentToPost = requiresAttachmentToPost;
    }

    public Integer getMissingAttachmentRemarkId()
    {
        return missingAttachmentRemarkId;
    }

    public void setMissingAttachmentRemarkId(Integer missingAttachmentRemarkId)
    {
        this.missingAttachmentRemarkId = missingAttachmentRemarkId;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
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
