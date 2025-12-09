
package API.Billing.Common;

import DOS.DiagnosisCodes;
import DOS.DiagnosisValidityStatus;
import java.io.Serializable;

public class DiagnosisCodeValidityMap implements Serializable, Comparable<DiagnosisCodeValidityMap>
{
    private static final long serialVersionUID = 42L;
    
    private final DiagnosisCodes diagnosisCode;
    private final DiagnosisValidityStatus diagnosisValidityStatus;

    public DiagnosisCodeValidityMap(DiagnosisCodes diagnosisCode, DiagnosisValidityStatus diagnosisValidityStatus)
    {
        this.diagnosisCode = diagnosisCode;
        this.diagnosisValidityStatus = diagnosisValidityStatus;
    }

    /**
     * Sort valid status first, invalid last.
     * @param other
     * @return 
     */
    @Override
    public int compareTo(DiagnosisCodeValidityMap other)
    {
        return this.diagnosisValidityStatus.compareTo(other.getDiagnosisValidityStatus());
    }

    public boolean containsDiagnosisCode(DiagnosisCodes otherDiagnosisCode)
    {
        if (diagnosisCode == null) return false;
        return this.diagnosisCode.equals(otherDiagnosisCode);
    }

    public DiagnosisCodes getDiagnosisCode()
    {
        return diagnosisCode;
    }

    public DiagnosisValidityStatus getDiagnosisValidityStatus()
    {
        return diagnosisValidityStatus;
    }        
}