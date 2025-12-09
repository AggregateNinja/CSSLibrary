
package API.Billing;

import API.Billing.Common.TestContextCptMap;
import API.BaseResponse;
import API.Billing.Common.CptDiagnosisValidityMap;
import API.Billing.Common.CptModifierMap;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ResponseTestAutoDiagValidity extends BaseResponse<ResponseTestAutoDiagValidity> implements Serializable
{
    private static final long serialVersionUID = 42L;
    // List<TestContext> --> List<CptCodes> --> List<CptModifiers>
    //                                    \
    //                                     \-->  List<DiagnosisCodes + DiagnosisValidityStatus>
    

    // Mapping of text context to potentially multiple cpt codes
    private List<TestContextCptMap> testContextCptMaps = new LinkedList<>();
    
    // Mapping of a single cpt code to zero or more modifiers
    private List<CptModifierMap> cptModifierMaps = new LinkedList<>();
    
    // Mapping of a cpt code to zero or more diagnosis validity mappings (DiagnosisCode --> DiagnosisValidityStatus)
    private List<CptDiagnosisValidityMap> cptDiagnosisValidityMaps = new LinkedList<>();
    
    
    public ResponseTestAutoDiagValidity()
    {
        super();
    }

    public ResponseTestAutoDiagValidity(Map<String, String> responseFailureCodes)
    {
        super(responseFailureCodes);
    }
    
    public List<TestContextCptMap> getTestContextCptMaps()
    {
        return this.testContextCptMaps;
    }
    
    public void setTestContextCptMaps(List<TestContextCptMap> testContextCptMaps)
    {
        this.testContextCptMaps = testContextCptMaps;
    }

    public List<CptModifierMap> getCptModifierMaps()
    {
        return cptModifierMaps;
    }

    public void setCptModifierMaps(List<CptModifierMap> cptModifierMaps)
    {
        this.cptModifierMaps = cptModifierMaps;
    }

    public void addCptModifierMap(CptModifierMap cptModifierMap)
    {
        this.cptModifierMaps.add(cptModifierMap);
    }
    
    public List<CptDiagnosisValidityMap> getCptDiagnosisValidityMaps()
    {
        return cptDiagnosisValidityMaps;
    }

    public void setCptDiagnosisValidityMaps(List<CptDiagnosisValidityMap> cptDiagnosisValidityMaps)
    {
        this.cptDiagnosisValidityMaps = cptDiagnosisValidityMaps;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseTestAutoDiagValidity fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
