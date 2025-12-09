
package API.Billing;

import API.BaseResponse;
import API.Billing.Common.CptDiagnosisValidityMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResponseCptDiagValidity extends BaseResponse<ResponseCptDiagValidity>
{
    private static final long serialVersionUID = 42L;
    private List<CptDiagnosisValidityMap> cptDiagnosisValidityMaps = new LinkedList<>();

    public ResponseCptDiagValidity()
    {
        super();
    }

    public ResponseCptDiagValidity(Map<String, String> responseFailureCodes)
    {
        super(responseFailureCodes);
    }

    public List<CptDiagnosisValidityMap> getCptDiagnosisMaps()
    {
        return cptDiagnosisValidityMaps;
    }

    public void setCptDiagnosisMaps(List<CptDiagnosisValidityMap> cptDiagnosisValidityMaps)
    {
        this.cptDiagnosisValidityMaps = cptDiagnosisValidityMaps;
    }
    
    public void addCptDiagnosisMap(CptDiagnosisValidityMap cptDiagnosisValidityMap)
    {
        if (cptDiagnosisValidityMap == null) return;
        if (this.cptDiagnosisValidityMaps == null) this.cptDiagnosisValidityMaps = new LinkedList<>();
        this.cptDiagnosisValidityMaps.add(cptDiagnosisValidityMap);
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseCptDiagValidity fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
