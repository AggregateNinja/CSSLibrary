package DOS;

import java.util.Date;
import java.io.Serializable;


public class RemittanceAdviceRemarkCodes implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idRemittanceAdviceRemarkCodes;
	private String remarkCode;
	private Integer insuranceId;
	private String description;



	public Integer getIdRemittanceAdviceRemarkCodes()
	{
		return idRemittanceAdviceRemarkCodes;
	}


	public void setIdRemittanceAdviceRemarkCodes(Integer idRemittanceAdviceRemarkCodes)
	{
		this.idRemittanceAdviceRemarkCodes = idRemittanceAdviceRemarkCodes;
	}


	public String getRemarkCode()
	{
		return remarkCode;
	}


	public void setRemarkCode(String remarkCode)
	{
		this.remarkCode = remarkCode;
	}


	public Integer getInsuranceId()
	{
		return insuranceId;
	}


	public void setInsuranceId(Integer insuranceId)
	{
		this.insuranceId = insuranceId;
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