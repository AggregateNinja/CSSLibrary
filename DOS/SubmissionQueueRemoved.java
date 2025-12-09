package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;


public class SubmissionQueueRemoved implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idSubmissionQueueRemoved;
	private Integer idSubmissionQueue;
	private Integer submissionBatchId;
	private Integer detailOrderId;
	private Integer detailInsuranceId;
	private BigDecimal billAmount;
	private Boolean submitted;
	private Date submittedDate;
	private Boolean rejected;
	private Integer user;
	private Date removedDate;



	public Integer getIdSubmissionQueueRemoved()
	{
		return idSubmissionQueueRemoved;
	}


	public void setIdSubmissionQueueRemoved(Integer idSubmissionQueueRemoved)
	{
		this.idSubmissionQueueRemoved = idSubmissionQueueRemoved;
	}


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


	public Boolean getSubmitted()
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


	public Boolean getRejected()
	{
		return rejected;
	}


	public void setRejected(Boolean rejected)
	{
		this.rejected = rejected;
	}


	public Integer getUser()
	{
		return user;
	}


	public void setUser(Integer user)
	{
		this.user = user;
	}


	public Date getRemovedDate()
	{
		return removedDate;
	}


	public void setRemovedDate(Date removedDate)
	{
		this.removedDate = removedDate;
	}
}