package DOS;

import Utility.Diff;
import java.io.Serializable;

public class BillingPayor implements Serializable
{

    public enum PayorType
    {
        INSURANCE,
        CLIENT,
        PATIENT
    }
    
    private static final long serialversionUID = 42L;

    private Integer idbillingPayors;
    private Integer insuranceId;
    private Integer clientId;
    private Integer patientId;

    @Diff(fieldName="idbillingPayors", isUniqueId=true)
    public Integer getIdbillingPayors()
    {
        return idbillingPayors;
    }

    public void setIdbillingPayors(Integer idbillingPayors)
    {
        this.idbillingPayors = idbillingPayors;
    }

    @Diff(fieldName="insuranceId")
    public Integer getInsuranceId()
    {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId)
    {
        this.insuranceId = insuranceId;
    }

    @Diff(fieldName="clientId")
    public Integer getClientId()
    {
        return clientId;
    }

    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }

    @Diff(fieldName="patientId")
    public Integer getPatientId()
    {
        return patientId;
    }

    public void setPatientId(Integer patientId)
    {
        this.patientId = patientId;
    }
    
    public PayorType getPayorType()
    {
        if (this.insuranceId != null) return PayorType.INSURANCE;
        if (this.clientId != null) return PayorType.CLIENT;
        if (this.patientId != null) return PayorType.PATIENT;
        
        return null;
    }
}
