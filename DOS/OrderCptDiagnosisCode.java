package DOS;

import Utility.Diff;

public class OrderCptDiagnosisCode
{

    private static final long serialversionUID = 42L;

    private Integer orderCptCodeId;
    private Integer diagnosisCodeId;
    private Integer rank;
    private Integer diagnosisValidityStatusId;
    private Boolean attached;

    @Diff(fieldName="orderCptCodeId")
    public Integer getOrderCptCodeId()
    {
        return orderCptCodeId;
    }

    public void setOrderCptCodeId(Integer orderCptCodeId)
    {
        this.orderCptCodeId = orderCptCodeId;
    }

    @Diff(fieldName="diagnosisCodeId")
    public Integer getDiagnosisCodeId()
    {
        return diagnosisCodeId;
    }

    public void setDiagnosisCodeId(Integer diagnosisCodeId)
    {
        this.diagnosisCodeId = diagnosisCodeId;
    }

    @Diff(fieldName="rank")
    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    @Diff(fieldName="diagnosisValidityStatusId")
    public Integer getDiagnosisValidityStatusId()
    {
        return diagnosisValidityStatusId;
    }

    public void setDiagnosisValidityStatusId(Integer diagnosisValidityStatusId)
    {
        this.diagnosisValidityStatusId = diagnosisValidityStatusId;
    }

    @Diff(fieldName="attached")
    public Boolean getAttached()
    {
        return attached;
    }

    public void setAttached(Boolean attached)
    {
        this.attached = attached;
    }
}
