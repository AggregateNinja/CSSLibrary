package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthResponseInsuranceInfoLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthResponseInsuranceInfoLookup;
	private Integer preauthResponseId;
	private String payerType;
	private String payerName;
	private String insuranceId;
	private String memberId;



	public Integer getIdpreauthResponseInsuranceInfoLookup()
	{
		return idpreauthResponseInsuranceInfoLookup;
	}


	public void setIdpreauthResponseInsuranceInfoLookup(Integer idpreauthResponseInsuranceInfoLookup)
	{
		this.idpreauthResponseInsuranceInfoLookup = idpreauthResponseInsuranceInfoLookup;
	}


	public Integer getPreauthResponseId()
	{
		return preauthResponseId;
	}


	public void setPreauthResponseId(Integer preauthResponseId)
	{
		this.preauthResponseId = preauthResponseId;
	}


	public String getPayerType()
	{
		return payerType;
	}


	public void setPayerType(String payerType)
	{
		this.payerType = payerType;
	}


	public String getPayerName()
	{
		return payerName;
	}


	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
	}


	public String getInsuranceId()
	{
		return insuranceId;
	}


	public void setInsuranceId(String insuranceId)
	{
		this.insuranceId = insuranceId;
	}


	public String getMemberId()
	{
		return memberId;
	}


	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}
}