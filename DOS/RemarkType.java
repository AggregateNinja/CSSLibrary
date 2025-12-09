
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class RemarkType implements Serializable
{
    // We don't have DO/DAOs for remarkCategory objects, so the searcing
    // in the DAO can be done using this enum
    public enum CategoryName
    {
        // The text is the system name
        RESULT("result"),
        BILLING("billing"),
        RESULT_INTERPRETATION("result_interpretation"),
        RESULT_ACTION("result_action");
        
        private final String categorySystemName;
        
        CategoryName(String categorySystemName)
        {
            this.categorySystemName = categorySystemName;
        }
        
        public String getCategorySystemName()
        {
            return this.categorySystemName;
        }
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "remarkCategoryId")
    private Integer remarkCategoryId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "editable")
    private Boolean editable;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "updatedOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Column(name = "updatedBy")
    private Integer updatedBy;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Diff(fieldName="remarkCategoryId")
    public Integer getRemarkCategoryId()
    {
        return remarkCategoryId;
    }

    public void setRemarkCategoryId(Integer remarkCategoryId)
    {
        this.remarkCategoryId = remarkCategoryId;
    }

    @Diff(fieldName="name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName="description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Whether this remark type can be changed by a user within the application
     * @return 
     */
    @Diff(fieldName="isEditable")
    public Boolean isEditable()
    {
        return editable;
    }

    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }

    @Diff(fieldName="isActive")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Date getUpdatedOn()
    {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn)
    {
        this.updatedOn = updatedOn;
    }

    public Integer getUpdatedBy()
    {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy)
    {
        this.updatedBy = updatedBy;
    }
    
    @Override
    public String toString()
    {
        return (name == null ? "" : name);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.id);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final RemarkType other = (RemarkType) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }
}
