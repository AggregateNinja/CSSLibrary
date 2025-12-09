
package EMR.DOS;

import java.util.Date;

public class EmrIssue
{
    private Integer idEmrIssues;
    private Integer orderId;
    private Integer emrXrefId;
    private Date created;
    private Integer issueTypeId;
    private String vendorCode;
    private Integer databaseIdentifier;
    private String description;

    public Integer getIdEmrIssues()
    {
        return idEmrIssues;
    }

    public void setIdEmrIssues(Integer idEmrIssues)
    {
        this.idEmrIssues = idEmrIssues;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    public Integer getEmrXrefId()
    {
        return emrXrefId;
    }

    public void setEmrXrefId(Integer emrXrefId)
    {
        this.emrXrefId = emrXrefId;
    }
    
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getIssueTypeId()
    {
        return issueTypeId;
    }

    public void setIssueTypeId(Integer issueTypeId)
    {
        this.issueTypeId = issueTypeId;
    }

    public String getVendorCode()
    {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode)
    {
        this.vendorCode = vendorCode;
    }
    
    public Integer getDatabaseIdentifier()
    {
        return databaseIdentifier;
    }

    public void setDatabaseIdentifier(Integer databaseIdentifier)
    {
        this.databaseIdentifier = databaseIdentifier;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
}
