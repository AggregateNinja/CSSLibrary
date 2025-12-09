package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author TomR
 */
public class AdvancedDiagnosisCodes
{
    private static final long serialVersionUID = 1L;    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdvancedDiagnosisCodes")
    private Integer idAdvancedDiagnosisCodes;
    @Column(name = "advancedOrderId")
    private Integer advancedOrderId;
    @Column(name = "diagnosisCodeId")
    private Integer diagnosisCodeId;
    
    public AdvancedDiagnosisCodes() {}
    
    public AdvancedDiagnosisCodes(
        Integer idAdvancedDiagnosisCodes,
        Integer advancedOrderId,
        Integer diagnosisCodeId)
    {
        this.idAdvancedDiagnosisCodes = idAdvancedDiagnosisCodes;
        this.advancedOrderId = advancedOrderId;
        this.diagnosisCodeId = diagnosisCodeId;
    }

    public Integer getIdAdvancedDiagnosisCodes() {
        return idAdvancedDiagnosisCodes;
    }

    public void setIdAdvancedDiagnosisCodes(Integer idAdvancedDiagnosisCodes) {
        this.idAdvancedDiagnosisCodes = idAdvancedDiagnosisCodes;
    }

    public Integer getAdvancedOrderId() {
        return advancedOrderId;
    }

    public void setAdvancedOrderId(Integer advancedOrderId) {
        this.advancedOrderId = advancedOrderId;
    }

    public Integer getDiagnosisCodeId() {
        return diagnosisCodeId;
    }

    public void setDiagnosisCodeId(Integer diagnosisCodeId) {
        this.diagnosisCodeId = diagnosisCodeId;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedDiagnosisCodes))
        {
            return false;
        }
        AdvancedDiagnosisCodes other = (AdvancedDiagnosisCodes) object;
        if ((this.idAdvancedDiagnosisCodes == null && other.idAdvancedDiagnosisCodes != null)
                || (this.idAdvancedDiagnosisCodes != null && !this.idAdvancedDiagnosisCodes.equals(other.idAdvancedDiagnosisCodes)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.AdvancedDiagnosisCode[ id=" + idAdvancedDiagnosisCodes + ", advancedOrderId=" + advancedOrderId + ", diagnosisCodeId =" + diagnosisCodeId + " ]";
    }    
        
    
}
