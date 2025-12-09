package DOS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SubmissionQueue implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idSubmissionQueue;
    private Integer submissionBatchId;
    private Integer detailOrderId;
    private Integer detailInsuranceId;
    private BigDecimal billAmount;
    private Boolean submitted;
    private Date submittedDate;
    private Boolean rejected;

    public Integer getIdSubmissionQueue()
    {
        return idSubmissionQueue;
    }

    public void setIdSubmissionQueue(Integer idSubmissionQueue)
    {
        this.idSubmissionQueue = idSubmissionQueue;
    }

    public Integer getSubmissionBatchId()
    {
        return submissionBatchId;
    }

    public void setSubmissionBatchId(Integer submissionBatchId)
    {
        this.submissionBatchId = submissionBatchId;
    }

    public Integer getDetailOrderId()
    {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId)
    {
        this.detailOrderId = detailOrderId;
    }

    public Integer getDetailInsuranceId()
    {
        return detailInsuranceId;
    }

    public void setDetailInsuranceId(Integer detailInsuranceId)
    {
        this.detailInsuranceId = detailInsuranceId;
    }

    public BigDecimal getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount)
    {
        this.billAmount = billAmount;
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

    public Boolean isRejected()
    {
        return rejected;
    }

    public void setRejected(Boolean rejected)
    {
        this.rejected = rejected;
    }    
}
