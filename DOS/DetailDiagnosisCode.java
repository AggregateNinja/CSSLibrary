package DOS;

import DOS.IDOS.IBillingDO;
import Utility.Diff;
import java.io.Serializable;

public class DetailDiagnosisCode implements Serializable, IBillingDO
{

    private static final long serialversionUID = 42L;

    private Integer detailCptCodeId;
    private Integer diagnosisCodeId;

    @Diff(fieldName="detailCptCodeId")
    public Integer getDetailCptCodeId()
    {
        return detailCptCodeId;
    }

    public void setDetailCptCodeId(Integer detailCptCodeId)
    {
        this.detailCptCodeId = detailCptCodeId;
    }

    @Diff(fieldName="detailDiagnosisCodeId")
    public Integer getDiagnosisCodeId()
    {
        return diagnosisCodeId;
    }

    public void setDiagnosisCodeId(Integer diagnosisCodeId)
    {
        this.diagnosisCodeId = diagnosisCodeId;
    }

    @Override
    public String getTableName()
    {
        return "detailDiganosisCodes";
    }

    
}
