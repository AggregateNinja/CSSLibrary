package DOS;

import java.util.Date;
import java.io.Serializable;


public class ClaimAdjustmentGroupCodes implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idClaimAdjustmentGroupCodes;
	private String groupCode;
	private Integer insuranceId;
	private String description;



	public Integer getIdClaimAdjustmentGroupCodes()
	{
		return idClaimAdjustmentGroupCodes;
	}


	public void setIdClaimAdjustmentGroupCodes(Integer idClaimAdjustmentGroupCodes)
	{
		this.idClaimAdjustmentGroupCodes = idClaimAdjustmentGroupCodes;
	}


	public String getGroupCode()
	{
		return groupCode;
	}


	public void setGroupCode(String groupCode)
	{
		this.groupCode = groupCode;
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
            return "(" + groupCode + ") " + description;
        }
}