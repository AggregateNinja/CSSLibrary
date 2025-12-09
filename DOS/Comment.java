package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idComments;
    private Integer commentTypeId;
    private Integer eventId;
    private Integer remarkId;
    private String comment;
    private Integer internal;
    private Integer createdById;
    private Date created;
    private Integer modifiedById;
    private Date modified;
    private boolean active;

    @Diff(fieldName = "idComments", isUniqueId = true)
    public Integer getIdComments()
    {
        return idComments;
    }

    public void setIdComments(Integer idComments)
    {
        this.idComments = idComments;
    }
    
    @Diff(fieldName = "commentTypeId")
    public Integer getCommentTypeId()
    {
        return commentTypeId;
    }
    
    public void setCommentTypeId(Integer commentTypeId)
    {
        this.commentTypeId = commentTypeId;
    }

    @Diff(fieldName = "eventId")
    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId(Integer eventId)
    {
        this.eventId = eventId;
    }

    @Diff(fieldName = "remarkId")
    public Integer getRemarkId()
    {
        return remarkId;
    }

    public void setRemarkId(Integer remarkId)
    {
        this.remarkId = remarkId;
    }

    @Diff(fieldName = "comment")
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    @Diff(fieldName = "internal")
    public Integer getInternal()
    {
        return internal;
    }

    public void setInternal(Integer internal)
    {
        this.internal = internal;
    }
    
    @Diff(fieldName = "createdById")
    public Integer getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Integer createdById)
    {
        this.createdById = createdById;
    }

    @Diff(fieldName = "created")
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    @Diff(fieldName = "modifiedById")
    public Integer getModifiedById()
    {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById)
    {
        this.modifiedById = modifiedById;
    }

    @Diff(fieldName = "modified")
    public Date getModified()
    {
        return modified;
    }

    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    @Diff(fieldName = "active")
    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
}
