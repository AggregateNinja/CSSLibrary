
package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class AttachmentType
{
    private static final long serialversionUID = 42L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "departmentId")
    private Integer departmentId;
    @Basic(optional = false)
    @Column(name = "lisVisible")
    private boolean lisVisible;
    @Basic(optional = false)
    @Column(name = "webVisible")
    private boolean webVisible;
    @Basic(optional = false)
    @Column(name = "allowedExtensions")
    private String allowedExtensions;    
    @Basic(optional = false)
    @Column(name = "allowMultipleAttachments")
    private boolean allowMultipleAttachments;
    @Basic(optional = false)
    @Column(name = "maxFileSizeMB")
    private double maxFileSizeMB;
    @Basic(optional = false)
    @Column(name = "approveOnPost")
    private boolean approveOnPost;
    @Basic(optional = false)
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public Integer getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId)
    {
        this.departmentId = departmentId;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isLisVisible()
    {
        return lisVisible;
    }

    public void setLisVisible(boolean lisVisible)
    {
        this.lisVisible = lisVisible;
    }

    public boolean isWebVisible()
    {
        return webVisible;
    }

    public void setWebVisible(boolean webVisible)
    {
        this.webVisible = webVisible;
    }

    public String getAllowedExtensions()
    {
        return allowedExtensions;
    }

    public void setAllowedExtensions(String allowedExtensions)
    {
        this.allowedExtensions = allowedExtensions;
    }

    public boolean isAllowMultipleAttachments()
    {
        return allowMultipleAttachments;
    }

    public void setAllowMultipleAttachments(boolean allowMultipleAttachments)
    {
        this.allowMultipleAttachments = allowMultipleAttachments;
    }

    public double getMaxFileSizeMB()
    {
        return maxFileSizeMB;
    }

    public void setMaxFileSizeMB(double maxFileSizeMB)
    {
        this.maxFileSizeMB = maxFileSizeMB;
    }

    public boolean isApproveOnPost()
    {
        return approveOnPost;
    }

    public void setApproveOnPost(boolean approveOnPost)
    {
        this.approveOnPost = approveOnPost;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public int hashCode()
    {
        return this.id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        
        if (getClass() != obj.getClass()) return false;
        
        final AttachmentType other = (AttachmentType) obj;
        if (this.id != other.id) return false;
        return true;
    }
    
    
}
