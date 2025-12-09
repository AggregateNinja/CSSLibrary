package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;


public class DetailCptCodeLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer iddetailCptCodeLog;
	private Integer detailCptCodeId;
	private Integer userId;
	private Date time;
	private Integer prevCptCodeId;
	private Integer newCptCodeId;
	private Integer prevQuantity;
	private Integer newQuantity;
	private BigDecimal prevBillAmount;
	private BigDecimal newBillAmount;
	private BigDecimal prevPaidAmount;
	private BigDecimal newPaidAmount;
	private Date prevPaymentDate;
	private Date newPaymentDate;
	private Integer prevPlaceOfServiceId;
	private Integer newPlaceOfServiceId;
        private String prevDiagnosisCodeIds;
        private String newDiagnosisCodeIds;
        private String prevCptModifierIds;
        private String newCptModifierIds;
        private String prevClaimAdjustmentGroupCodeIds;
        private String newClaimAdjustmentGroupCodeIds;
        private String prevClaimAdjustmentReasonCodeIds;
        private String newClaimAdjustmentReasonCodeIds;

	public Integer getIddetailCptCodeLog()
	{
		return iddetailCptCodeLog;
	}


	public void setIddetailCptCodeLog(Integer iddetailCptCodeLog)
	{
		this.iddetailCptCodeLog = iddetailCptCodeLog;
	}


	public Integer getDetailCptCodeId()
	{
		return detailCptCodeId;
	}


	public void setDetailCptCodeId(Integer detailCptCodeId)
	{
		this.detailCptCodeId = detailCptCodeId;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public Date getTime()
	{
		return time;
	}


	public void setTime(Date time)
	{
		this.time = time;
	}


	public Integer getPrevCptCodeId()
	{
		return prevCptCodeId;
	}


	public void setPrevCptCodeId(Integer prevCptCodeId)
	{
		this.prevCptCodeId = prevCptCodeId;
	}


	public Integer getNewCptCodeId()
	{
		return newCptCodeId;
	}


	public void setNewCptCodeId(Integer newCptCodeId)
	{
		this.newCptCodeId = newCptCodeId;
	}


	public Integer getPrevQuantity()
	{
		return prevQuantity;
	}


	public void setPrevQuantity(Integer prevQuantity)
	{
		this.prevQuantity = prevQuantity;
	}


	public Integer getNewQuantity()
	{
		return newQuantity;
	}


	public void setNewQuantity(Integer newQuantity)
	{
		this.newQuantity = newQuantity;
	}


	public BigDecimal getPrevBillAmount()
	{
		return prevBillAmount;
	}


	public void setPrevBillAmount(BigDecimal prevBillAmount)
	{
		this.prevBillAmount = prevBillAmount;
	}


	public BigDecimal getNewBillAmount()
	{
		return newBillAmount;
	}


	public void setNewBillAmount(BigDecimal newBillAmount)
	{
		this.newBillAmount = newBillAmount;
	}


	public BigDecimal getPrevPaidAmount()
	{
		return prevPaidAmount;
	}


	public void setPrevPaidAmount(BigDecimal prevPaidAmount)
	{
		this.prevPaidAmount = prevPaidAmount;
	}


	public BigDecimal getNewPaidAmount()
	{
		return newPaidAmount;
	}


	public void setNewPaidAmount(BigDecimal newPaidAmount)
	{
		this.newPaidAmount = newPaidAmount;
	}


	public Date getPrevPaymentDate()
	{
		return prevPaymentDate;
	}


	public void setPrevPaymentDate(Date prevPaymentDate)
	{
		this.prevPaymentDate = prevPaymentDate;
	}


	public Date getNewPaymentDate()
	{
		return newPaymentDate;
	}


	public void setNewPaymentDate(Date newPaymentDate)
	{
		this.newPaymentDate = newPaymentDate;
	}


	public Integer getPrevPlaceOfServiceId()
	{
		return prevPlaceOfServiceId;
	}


	public void setPrevPlaceOfServiceId(Integer prevPlaceOfServiceId)
	{
		this.prevPlaceOfServiceId = prevPlaceOfServiceId;
	}


	public Integer getNewPlaceOfServiceId()
	{
		return newPlaceOfServiceId;
	}


	public void setNewPlaceOfServiceId(Integer newPlaceOfServiceId)
	{
		this.newPlaceOfServiceId = newPlaceOfServiceId;
	}
        
        public String getPrevDiagnosisCodeIds()
        {
            return prevDiagnosisCodeIds;
        }
        
        public void setPrevDiagnosisCodeIds(String prevDiagnosisCodeIds)
        {
            this.prevDiagnosisCodeIds = prevDiagnosisCodeIds;
        }
        
        public String getNewDiagnosisCodeIds()
        {
            return newDiagnosisCodeIds;
        }
        
        public void setNewDiagnosisCodeIds(String newDiagnosisCodeIds)
        {
            this.newDiagnosisCodeIds = newDiagnosisCodeIds;
        }
        
        public String getPrevCptModifierIds()
        {
            return prevCptModifierIds;
        }
        
        public void setPrevCptModifierIds(String prevCptModifierIds)
        {
            this.prevCptModifierIds = prevCptModifierIds;
        }
        
        public String getNewCptModifierIds()
        {
            return newCptModifierIds;
        }
        
        public void setNewCptModifierIds(String newCptModifierIds)
        {
            this.newCptModifierIds = newCptModifierIds;
        }
        
        public String getPrevClaimAdjustmentGroupCodeIds()
        {
            return prevClaimAdjustmentGroupCodeIds;
        }
        
        public void setPrevClaimAdjustmentGroupCodeIds(String prevClaimAdjustmentGroupCodeIds)
        {
            this.prevClaimAdjustmentGroupCodeIds = prevClaimAdjustmentGroupCodeIds;
        }
        
        public String getNewClaimAdjustmentGroupCodeIds()
        {
            return newClaimAdjustmentGroupCodeIds;
        }
        
        public void setNewClaimAdjustmentGroupCodeIds(String newClaimAdjustmentGroupCodeIds)
        {
            this.newClaimAdjustmentGroupCodeIds = newClaimAdjustmentGroupCodeIds;
        }
        
        public String getPrevClaimAdjustmentReasonCodeIds()
        {
            return prevClaimAdjustmentReasonCodeIds;
        }
        
        public void setPrevClaimAdjustmentReasonCodeIds(String prevClaimAdjustmentReasonCodeIds)
        {
            this.prevClaimAdjustmentReasonCodeIds = prevClaimAdjustmentReasonCodeIds;
        }
        
        public String getNewClaimAdjustmentReasonCodeIds()
        {
            return newClaimAdjustmentReasonCodeIds;
        }
        
        public void setNewClaimAdjustmentReasonCodeIds(String newClaimAdjustmentReasonCodeIds)
        {
            this.newClaimAdjustmentReasonCodeIds = newClaimAdjustmentReasonCodeIds;
        }
}