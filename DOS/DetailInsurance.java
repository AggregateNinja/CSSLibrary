package DOS;

import DOS.IDOS.IBillingDO;
import Utility.Diff;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DetailInsurance implements Serializable, IBillingDO
{
    
    private static final long serialversionUID = 42L;

    private Integer iddetailInsurances;
    private Integer detailOrderId;
    private Integer submissionStatusId;
    private Integer insuranceId;
    private Integer insuranceTypeId;
    private Integer insuranceSubmissionTypeId;
    private Integer insuranceSubmissionModeId;
    private String identifier;
    private String groupIdentifier;
    private Integer rank;
    private Integer timesBilled;

    @Diff(fieldName = "iddetailInsurances", isUniqueId = true)
    public Integer getIddetailInsurances()
    {
        return iddetailInsurances;
    }

    public void setIddetailInsurances(Integer iddetailInsurances)
    {
        this.iddetailInsurances = iddetailInsurances;
    }

    @Diff(fieldName = "detailOrderId")
    public Integer getDetailOrderId()
    {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId)
    {
        this.detailOrderId = detailOrderId;
    }

    @Diff(fieldName = "submissionStatusId")
    public Integer getSubmissionStatusId()
    {
        return submissionStatusId;
    }

    public void setSubmissionStatusId(Integer submissionStatusId)
    {
        this.submissionStatusId = submissionStatusId;
    }

    @Diff(fieldName = "insuranceId")
    public Integer getInsuranceId()
    {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId)
    {
        this.insuranceId = insuranceId;
    }

    @Diff(fieldName = "insuranceTypeId")
    public Integer getInsuranceTypeId()
    {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Integer insuranceTypeId)
    {
        this.insuranceTypeId = insuranceTypeId;
    }

    @Diff(fieldName = "insuranceSubmissionTypeId")
    public Integer getInsuranceSubmissionTypeId()
    {
        return insuranceSubmissionTypeId;
    }

    public void setInsuranceSubmissionTypeId(Integer insuranceSubmissionTypeId)
    {
        this.insuranceSubmissionTypeId = insuranceSubmissionTypeId;
    }

    @Diff(fieldName = "insuranceSubmissionModeId")
    public Integer getInsuranceSubmissionModeId()
    {
        return insuranceSubmissionModeId;
    }

    public void setInsuranceSubmissionModeId(Integer insuranceSubmissionModeId)
    {
        this.insuranceSubmissionModeId = insuranceSubmissionModeId;
    }

    @Diff(fieldName = "identifier")
    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    @Diff(fieldName = "groupIdentifier")
    public String getGroupIdentifier()
    {
        return groupIdentifier;
    }

    public void setGroupIdentifier(String groupIdentifier)
    {
        this.groupIdentifier = groupIdentifier;
    }

    @Diff(fieldName = "rank")
    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    @Diff(fieldName = "timesBilled")
    public Integer getTimesBilled()
    {
        return timesBilled;
    }

    public void setTimesBilled(Integer timesBilled)
    {
        this.timesBilled = timesBilled;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public DetailInsurance copy()
    {
        DetailInsurance copy = new DetailInsurance();
        copy.setIddetailInsurances(iddetailInsurances);
        copy.setDetailOrderId(detailOrderId);
        copy.setSubmissionStatusId(submissionStatusId);
        copy.setInsuranceId(insuranceId);
        copy.setInsuranceTypeId(insuranceTypeId);
        copy.setInsuranceSubmissionTypeId(insuranceSubmissionTypeId);
        copy.setInsuranceSubmissionModeId(insuranceSubmissionModeId);
        copy.setIdentifier(identifier);
        copy.setGroupIdentifier(groupIdentifier);
        copy.setRank(rank);
        copy.setTimesBilled(timesBilled);
        return copy;
    }

    @Override
    public String getTableName()
    {
        return "detailInsurances";
    }

}
