package DOS;

import java.util.Date;
import java.io.Serializable;


public class DetailInsuranceItemAdjustments implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idDetailInsuranceItemAdjustments;
	private Integer detailInsuranceItemId;
	private Integer claimAdjustmentGroupCodeId;
	private Integer claimAdjustmentReasonCodeId;
	private Integer remittanceAdviceRemarkCodeId;



	public Integer getIdDetailInsuranceItemAdjustments()
	{
		return idDetailInsuranceItemAdjustments;
	}


	public void setIdDetailInsuranceItemAdjustments(Integer idDetailInsuranceItemAdjustments)
	{
		this.idDetailInsuranceItemAdjustments = idDetailInsuranceItemAdjustments;
	}


	public Integer getDetailInsuranceItemId()
	{
		return detailInsuranceItemId;
	}


	public void setDetailInsuranceItemId(Integer detailInsuranceItemId)
	{
		this.detailInsuranceItemId = detailInsuranceItemId;
	}


	public Integer getClaimAdjustmentGroupCodeId()
	{
		return claimAdjustmentGroupCodeId;
	}


	public void setClaimAdjustmentGroupCodeId(Integer claimAdjustmentGroupCodeId)
	{
		this.claimAdjustmentGroupCodeId = claimAdjustmentGroupCodeId;
	}


	public Integer getClaimAdjustmentReasonCodeId()
	{
		return claimAdjustmentReasonCodeId;
	}


	public void setClaimAdjustmentReasonCodeId(Integer claimAdjustmentReasonCodeId)
	{
		this.claimAdjustmentReasonCodeId = claimAdjustmentReasonCodeId;
	}


	public Integer getRemittanceAdviceRemarkCodeId()
	{
		return remittanceAdviceRemarkCodeId;
	}


	public void setRemittanceAdviceRemarkCodeId(Integer remittanceAdviceRemarkCodeId)
	{
		this.remittanceAdviceRemarkCodeId = remittanceAdviceRemarkCodeId;
	}
}