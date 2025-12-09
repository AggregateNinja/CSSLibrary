package API.Billing;

import DOS.DetailCptCode;
import DOS.DetailCptModifier;
import DOS.DetailDiagnosisCode;
import DOS.DetailInsurance;
import DOS.DetailOrder;
import DOS.FeeScheduleAssignment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseBillingDetailPreview implements Serializable
{
    public static final long serialVersionUID = 42L;
    
    private FeeScheduleAssignment feeScheduleAssignment;
    private boolean feeScheduleUseCustomRule = false;
    
    private DetailOrder detailOrder;
    
    private List<DetailInsurance> detailInsurances = new ArrayList<>();
    private List<DetailCptCodeMap> detailCptCodes = new ArrayList<>();
    
    // List<DetailCptCode> --> List<detailCptModifiers>
    //                     \__ List<detailDiagnosisCodes>
    
    public static class DetailCptCodeMap implements Serializable
    {
        public static final long serialVersionUID = 42L;
        
        
        // Track the testIds that comprise a single CPT code. This should
        // be provided for in-house requests only:
        //public Set<Integer> testIds;
        public DetailCptCode detailCptCode;
        public List<DetailCptModifier> detailCptModifiers;
        public List<DetailDiagnosisCode> detailDiagnosisCodes;
        
        public DetailCptCodeMap(
            //Set<Integer> testIds,
            DetailCptCode detailCptCode,
            List<DetailCptModifier> detailCptModifiers,
            List<DetailDiagnosisCode> detailDiagnosisCodes)
        {
            //this.testIds = testIds;
            this.detailCptCode = detailCptCode;
            this.detailCptModifiers = detailCptModifiers;
            this.detailDiagnosisCodes = detailDiagnosisCodes;
        }
    }
    
    public void addDetailCptCode(
            //Set<Integer> testIds,
            DetailCptCode detailCptCode,
            List<DetailCptModifier> detailCptModifiers,
            List<DetailDiagnosisCode> detailDiagnosisCodes)
    {
        ////testIds,
        this.detailCptCodes.add(
                new DetailCptCodeMap(    
                        detailCptCode,
                        detailCptModifiers,
                        detailDiagnosisCodes));
    }
    
    public DetailOrder getDetailOrder()
    {
        return detailOrder;
    }

    public void setDetailOrder(DetailOrder detailOrder)
    {
        this.detailOrder = detailOrder;
    }

    public List<DetailInsurance> getDetailInsurances()
    {
        return detailInsurances;
    }

    public void setDetailInsurances(List<DetailInsurance> detailInsurances)
    {
        this.detailInsurances = detailInsurances;
    }

    public List<DetailCptCodeMap> getDetailCptCodeMap()
    {
        return detailCptCodes;
    }

    public void setDetailCptCodeMap(List<DetailCptCodeMap> detailCptCodes)
    {
        this.detailCptCodes = detailCptCodes;
    }
    
    public void addDetailInsurance(DetailInsurance detailInsurance)
    {
        this.detailInsurances.add(detailInsurance);
    }

    public FeeScheduleAssignment getFeeScheduleAssignment()
    {
        return feeScheduleAssignment;
    }

    public void setFeeScheduleAssignment(FeeScheduleAssignment feeScheduleAssignment)
    {
        this.feeScheduleAssignment = feeScheduleAssignment;
    }
    
    public boolean getFeeScheduleUseCustomRule()
    {
        return feeScheduleUseCustomRule;
    }
    
    public void setFeeScheduleUseCustomRule(boolean feeScheduleUseCustomRule)
    {
        this.feeScheduleUseCustomRule = feeScheduleUseCustomRule;
    }
}
