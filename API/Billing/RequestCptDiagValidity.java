
package API.Billing;

import API.BaseRequest;
import API.Billing.Common.CptModifierMap;
import DOS.CptCode;
import DOS.CptModifier;
import DOS.DiagnosisCodes;
import java.util.LinkedList;
import java.util.List;

public class RequestCptDiagValidity extends BaseRequest<RequestCptDiagValidity>
{
    private static final long serialVersionUID = 42L;
    private Integer insuranceId;
    private Integer clientId;
    private Integer patientId;
    
    List<DiagnosisCodes> diagnosisCodes = new LinkedList<>();
    List<CptModifierMap> cptModifierMaps = new LinkedList<>();
    
    public RequestCptDiagValidity() {};
    
    public void setInsuranceId(Integer insuranceId)
    {
        this.insuranceId = insuranceId;
    }
    
    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }
    
    public void setPatientId(Integer patientId)
    {
        this.patientId = patientId;
    }
    
    public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes)
    {
        this.diagnosisCodes = diagnosisCodes;
    }
    
    /**
     * Cptmodifiers are not currently required for validity, but might be nice
     *  to supply in the future for rules that may take modifiers into account 
     *  when determining validity.
     * 
     * @param cptCode
     * @param modifiers 
     */
    public void addCptModifierMap(CptCode cptCode, List<CptModifier> modifiers)
    {
        this.cptModifierMaps.add(new CptModifierMap(cptCode, modifiers));
    }
    
    public void addCptModifierMap(CptModifierMap cptModifierMap)
    {
        this.cptModifierMaps.add(cptModifierMap);
    }

    public Integer getInsuranceId()
    {
        return insuranceId;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public Integer getPatientId()
    {
        return patientId;
    }

    public List<CptModifierMap> getCptModifierMaps()
    {
        return cptModifierMaps;
    }

    public List<DiagnosisCodes> getDiagnosisCodes()
    {
        return diagnosisCodes;
    }

    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestCptDiagValidity fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
