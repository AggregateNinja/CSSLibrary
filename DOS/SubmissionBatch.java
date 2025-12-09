package DOS;

import java.io.Serializable;
import java.util.Date;

public class SubmissionBatch implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idSubmissionBatch;
    private Integer insuranceTypeId;
    private Integer insuranceSubmissionTypeId;
    private Integer insuranceSubmissionModeId;
    private Integer orderCount;
    private Integer isTestBatch;
    private Boolean submitted;
    private Date submittedDate;
    private Date completedDate;
    private Integer userId;
    private Date created;

    public Integer getIdSubmissionBatch()
    {
        return idSubmissionBatch;
    }

    public void setIdSubmissionBatch(Integer idSubmissionBatch)
    {
        this.idSubmissionBatch = idSubmissionBatch;
    }

    public Integer getInsuranceTypeId()
    {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Integer insuranceTypeId)
    {
        this.insuranceTypeId = insuranceTypeId;
    }

    public Integer getInsuranceSubmissionTypeId()
    {
        return insuranceSubmissionTypeId;
    }

    public void setInsuranceSubmissionTypeId(Integer insuranceSubmissionTypeId)
    {
        this.insuranceSubmissionTypeId = insuranceSubmissionTypeId;
    }

    public Integer getInsuranceSubmissionModeId()
    {
        return insuranceSubmissionModeId;
    }

    public void setInsuranceSubmissionModeId(Integer insuranceSubmissionModeId)
    {
        this.insuranceSubmissionModeId = insuranceSubmissionModeId;
    }

    public Integer getOrderCount()
    {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount)
    {
        this.orderCount = orderCount;
    }

    public Integer isTestBatch()
    {
        return isTestBatch;
    }

    public void setTestBatch(Integer isTestBatch)
    {
        this.isTestBatch = isTestBatch;
    }

    public Boolean isSubmitted()
    {
        return submitted;
    }

    public void setSubmitted(Boolean submitted)
    {
        this.submitted = submitted;
    }

    public Date getSubmittedDate()
    {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate)
    {
        this.submittedDate = submittedDate;
    }
    
    public Date getCompletedDate()
    {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate)
    {
        this.completedDate = completedDate;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
}
