package DOS;

import java.util.Date;
import java.io.Serializable;


public class ClaimAdjustmentReasonCodes implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idClaimAdjustmentReasonCodes;
	private String reasonCode;
	private Integer insuranceId;
	private String description;



	public Integer getIdClaimAdjustmentReasonCodes()
	{
		return idClaimAdjustmentReasonCodes;
	}


	public void setIdClaimAdjustmentReasonCodes(Integer idClaimAdjustmentReasonCodes)
	{
		this.idClaimAdjustmentReasonCodes = idClaimAdjustmentReasonCodes;
	}


	public String getReasonCode()
	{
		return reasonCode;
	}


	public void setReasonCode(String reasonCode)
	{
		this.reasonCode = reasonCode;
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
        
        @Override
        public String toString()
        {
            return "(" + reasonCode + ") " + description;
        }
}