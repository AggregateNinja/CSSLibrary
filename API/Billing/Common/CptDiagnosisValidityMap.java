
package API.Billing.Common;

import DOS.CptCode;
import DOS.DiagnosisCodes;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CptDiagnosisValidityMap implements Serializable
{
    private static final long serialVersionUID = 42L;
    private final CptCode cptCode;
    private List<DiagnosisCodeValidityMap> diagnosisCodeValidityMaps = new LinkedList<>();

    public CptDiagnosisValidityMap(CptCode cptCode, List<DiagnosisCodeValidityMap> diagnosisCodeValidityMaps)
    {
        this.cptCode = cptCode;
        if (diagnosisCodeValidityMaps == null) diagnosisCodeValidityMaps = new LinkedList<>();
        this.diagnosisCodeValidityMaps = diagnosisCodeValidityMaps;
    }

    public CptCode getCptCode()
    {
        return cptCode;
    }

    public List<DiagnosisCodeValidityMap> getDiagnosisCodeValidityMaps()
    {
        return diagnosisCodeValidityMaps;
    }

    public void add(DiagnosisCodeValidityMap diagnosisCodeValidityMap)
    {
        this.diagnosisCodeValidityMaps.add(diagnosisCodeValidityMap);
    }

    public boolean contains(DiagnosisCodes code)
    {
        if (code == null || code.getIdDiagnosisCodes() == null || code.getIdDiagnosisCodes().equals(0)) return false;
        // If any of the mappings have this diagnosis code
        for (DiagnosisCodeValidityMap diagnosisCodeValidityMap : this.diagnosisCodeValidityMaps)
        {
            if (diagnosisCodeValidityMap.containsDiagnosisCode(code)) return true;
        }
        return false;
    }

    public void sortValidFirst()
    {
        Collections.sort(this.diagnosisCodeValidityMaps);
    }
}
