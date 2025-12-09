package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class FeeScheduleAssignment implements Serializable
{

    public static final long serialversionUID = 42L;

    private Integer idFeeScheduleAssignments;
    private Integer feeScheduleId;
    private Integer billingPayorId;
    private float rateAdjustment;
    private Date startDate;
    private Date endDate;

    @Diff(fieldName = "idFeeScheduleAssignments", isUniqueId = true)
    public Integer getIdFeeScheduleAssignments()
    {
        return idFeeScheduleAssignments;
    }

    public void setIdFeeScheduleAssignments(Integer idFeeScheduleAssignments)
    {
        this.idFeeScheduleAssignments = idFeeScheduleAssignments;
    }
    
    @Diff(fieldName = "feeScheduleId")
    public Integer getFeeScheduleId()
    {
        return feeScheduleId;
    }

    public void setFeeScheduleId(Integer feeScheduleId)
    {
        this.feeScheduleId = feeScheduleId;
    }

    @Diff(fieldName = "billingPayorId")
    public Integer getBillingPayorId()
    {
        return billingPayorId;
    }

    public void setBillingPayorId(Integer billingPayorId)
    {
        this.billingPayorId = billingPayorId;
    }

    @Diff(fieldName = "rateAdjustment")
    public float getRateAdjustment()
    {
        return rateAdjustment;
    }

    public void setRateAdjustment(float rateAdjustment)
    {
        this.rateAdjustment = rateAdjustment;
    }

    @Diff(fieldName = "startDate")
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    @Diff(fieldName = "endDate")
    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public FeeScheduleAssignment copy()
    {
        FeeScheduleAssignment newCopy = new FeeScheduleAssignment();
        newCopy.setIdFeeScheduleAssignments(idFeeScheduleAssignments);
        newCopy.setFeeScheduleId(feeScheduleId);
        newCopy.setBillingPayorId(billingPayorId);
        newCopy.setRateAdjustment(rateAdjustment);
        newCopy.setStartDate(startDate);
        newCopy.setEndDate(endDate);
        return newCopy;
    }
}
