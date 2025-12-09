package API.Billing;

import API.BaseRequest;
import DOS.InsuranceSubmissionMode;
import DOS.InsuranceSubmissionType;
import DOS.InsuranceType;
import Utility.Pair;
import java.util.ArrayList;
import java.util.List;

public class RequestQueueClaimsForTransmission extends BaseRequest<RequestQueueClaimsForTransmission>
{
    private static final long serialVersionUID = 42L;
    
    InsuranceType insuranceType = null;
    InsuranceSubmissionType insuranceSubmissionType = null;
    InsuranceSubmissionMode insuranceSubmissionMode = null;
    
    // detailOrderId --> userId of the person submitting
    List<Pair<Integer,Integer>> detailOrderIdsUserIds = null;
    
    Boolean isTestBatch;
    
    public RequestQueueClaimsForTransmission(
            List<Pair<Integer,Integer>> detailOrderIdsUserIds,
            InsuranceType insuranceType,
            InsuranceSubmissionType insuranceSubmissionType,
            InsuranceSubmissionMode insuranceSubmissionMode,
            boolean isTestBatch,
            int userId
            )
    {
        super();
        this.detailOrderIdsUserIds = detailOrderIdsUserIds;
        this.insuranceType = insuranceType;
        this.insuranceSubmissionType = insuranceSubmissionType;
        this.insuranceSubmissionMode = insuranceSubmissionMode;
        this.isTestBatch = isTestBatch;
        
    }

    public InsuranceType getInsuranceType()
    {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType)
    {
        this.insuranceType = insuranceType;
    }

    public InsuranceSubmissionType getInsuranceSubmissionType()
    {
        return insuranceSubmissionType;
    }

    public void setInsuranceSubmissionType(InsuranceSubmissionType insuranceSubmissionType)
    {
        this.insuranceSubmissionType = insuranceSubmissionType;
    }

    public InsuranceSubmissionMode getInsuranceSubmissionMode()
    {
        return insuranceSubmissionMode;
    }

    public void setInsuranceSubmissionMode(InsuranceSubmissionMode insuranceSubmissionMode)
    {
        this.insuranceSubmissionMode = insuranceSubmissionMode;
    }

    public List<Pair<Integer,Integer>> getDetailOrderIdsUserIds()
    {
        return detailOrderIdsUserIds;
    }

    public void setDetailOrderIdUserIds(List<Pair<Integer,Integer>> detailOrderIdsUserIds)
    {
        this.detailOrderIdsUserIds = detailOrderIdsUserIds;
    }

    public void addDetailOrderIdUserId(Pair<Integer, Integer> detailOrderIdUserId)
    {
        if (this.detailOrderIdsUserIds == null) this.detailOrderIdsUserIds = new ArrayList<>();
        this.detailOrderIdsUserIds.add(detailOrderIdUserId);
    }

    public Boolean isTestBatch()
    {
        return isTestBatch;
    }

    public void setTestBatch(Boolean isTestBatch)
    {
        this.isTestBatch = isTestBatch;
    }

    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestQueueClaimsForTransmission fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
