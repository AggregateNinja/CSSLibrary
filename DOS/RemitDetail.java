package DOS;

import java.util.Date;
import java.math.BigDecimal;
import java.sql.Time;


public class RemitDetail
{
	private static final long serialversionUID = 42L;

	private Integer idRemitDetails;
	private Integer idRemitInfo;
	private String arNo;
	private String policyNumber;
	private String traceNumber;
	private String claimStatusCode;
	private String lastName;
	private String firstName;
	private String middleName;
	private Date dateOfService;
	private Time timeOfService;
	private String cptCode;
	private String cptModifiers;
	private String adjustmentCode;
	private BigDecimal billedAmount;
	private BigDecimal paidAmount;
	private BigDecimal patientAmount;
	private BigDecimal adjustmentAmount;
	private String itemControlNumber;
	private Date created;
	private Date processed;
	private Integer userid;



	public Integer getIdRemitDetails()
	{
		return idRemitDetails;
	}


	public void setIdRemitDetails(Integer idRemitDetails)
	{
		this.idRemitDetails = idRemitDetails;
	}


	public Integer getIdRemitInfo()
	{
		return idRemitInfo;
	}


	public void setIdRemitInfo(Integer idRemitInfo)
	{
		this.idRemitInfo = idRemitInfo;
	}


	public String getArNo()
	{
		return arNo;
	}


	public void setArNo(String arNo)
	{
		this.arNo = arNo;
	}


	public String getPolicyNumber()
	{
		return policyNumber;
	}


	public void setPolicyNumber(String policyNumber)
	{
		this.policyNumber = policyNumber;
	}


	public String getTraceNumber()
	{
		return traceNumber;
	}


	public void setTraceNumber(String traceNumber)
	{
		this.traceNumber = traceNumber;
	}


	public String getClaimStatusCode()
	{
		return claimStatusCode;
	}


	public void setClaimStatusCode(String claimStatusCode)
	{
		this.claimStatusCode = claimStatusCode;
	}


	public String getLastName()
	{
		return lastName;
	}


	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}


	public String getFirstName()
	{
		return firstName;
	}


	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}


	public String getMiddleName()
	{
		return middleName;
	}


	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}


	public Date getDateOfService()
	{
		return dateOfService;
	}


	public void setDateOfService(Date dateOfService)
	{
		this.dateOfService = dateOfService;
	}


	public Time getTimeOfService()
	{
		return timeOfService;
	}


	public void setTimeOfService(Time timeOfService)
	{
		this.timeOfService = timeOfService;
	}


	public String getCptCode()
	{
		return cptCode;
	}


	public void setCptCode(String cptCode)
	{
		this.cptCode = cptCode;
	}


	public String getCptModifiers()
	{
		return cptModifiers;
	}


	public void setCptModifiers(String cptModifiers)
	{
		this.cptModifiers = cptModifiers;
	}


	public String getAdjustmentCode()
	{
		return adjustmentCode;
	}


	public void setAdjustmentCode(String adjustmentCode)
	{
		this.adjustmentCode = adjustmentCode;
	}


	public BigDecimal getBilledAmount()
	{
		return billedAmount;
	}


	public void setBilledAmount(BigDecimal billedAmount)
	{
		this.billedAmount = billedAmount;
	}


	public BigDecimal getPaidAmount()
	{
		return paidAmount;
	}


	public void setPaidAmount(BigDecimal paidAmount)
	{
		this.paidAmount = paidAmount;
	}


	public BigDecimal getPatientAmount()
	{
		return patientAmount;
	}


	public void setPatientAmount(BigDecimal patientAmount)
	{
		this.patientAmount = patientAmount;
	}


	public BigDecimal getAdjustmentAmount()
	{
		return adjustmentAmount;
	}


	public void setAdjustmentAmount(BigDecimal adjustmentAmount)
	{
		this.adjustmentAmount = adjustmentAmount;
	}


	public String getItemControlNumber()
	{
		return itemControlNumber;
	}


	public void setItemControlNumber(String itemControlNumber)
	{
		this.itemControlNumber = itemControlNumber;
	}


	public Date getCreated()
	{
		return created;
	}


	public void setCreated(Date created)
	{
		this.created = created;
	}


	public Date getProcessed()
	{
		return processed;
	}


	public void setProcessed(Date processed)
	{
		this.processed = processed;
	}


	public Integer getUserid()
	{
		return userid;
	}


	public void setUserid(Integer userid)
	{
		this.userid = userid;
	}
}