
package DOS;

import Utility.Diff;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class ReportHeader
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "displayOrder")
    private Integer displayOrder;
    @Column(name = "reportColumnFormatId")
    private Integer reportColumnFormatId;
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

    @Diff(fieldName="displayOrder")
    public Integer getDisplayOrder()
    {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder)
    {
        this.displayOrder = displayOrder;
    }

    @Diff(fieldName="reportColumnFormatId")
    public Integer getReportColumnFormatId()
    {
        return reportColumnFormatId;
    }

    public void setReportColumnFormatId(Integer reportColumnFormatId)
    {
        this.reportColumnFormatId = reportColumnFormatId;
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
    
    public ReportHeader copy()
    {
        ReportHeader reportHeader = new ReportHeader();
        reportHeader.setId(id);
        reportHeader.setName(name);
        reportHeader.setDescription(description);
        reportHeader.setDisplayOrder(displayOrder);
        reportHeader.setReportColumnFormatId(reportColumnFormatId);
        reportHeader.setEditable(editable);
        reportHeader.setActive(active);
        reportHeader.setUpdatedOn(updatedOn);
        reportHeader.setUpdatedBy(updatedBy);
        return reportHeader;
    }
}
