package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Rob
 */
public class DiagnosisValidityLookup implements Serializable 
{
    private static final long serialVersionUID = 42L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiagnosisValidityLookup")
    private Integer idDiagnosisValidityLookup;
    @Column(name = "diagnosisValidityId")
    @Basic(optional = false)
    private Integer diagnosisValidityId;
    @Column(name = "cptCodeId")
    @Basic(optional = false)
    private Integer cptCodeId;
    @Basic(optional = false)
    @Column(name = "diagnosisCodeId")
    private Integer diagnosisCodeId;
    @Basic(optional = false)
    @Column(name = "validityStatusId")
    private Integer validityStatusId;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "startDate")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @Column(name = "endDate")
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;

    @Diff(fieldName = "idDiagnosisValidityLookup", isUniqueId = true)
    public Integer getIdDiagnosisValidityLookup()
    {
        return idDiagnosisValidityLookup;
    }

    public void setIdDiagnosisValidityLookup(Integer idDiagnosisValidityLookup)
    {
        this.idDiagnosisValidityLookup = idDiagnosisValidityLookup;
    }

    @Diff(fieldName = "diagnosisValidityId", isUniqueId = false)
    public Integer getDiagnosisValidityId()
    {
        return diagnosisValidityId;
    }

    public void setDiagnosisValidityId(Integer diagnosisValidityId)
    {
        this.diagnosisValidityId = diagnosisValidityId;
    }

    @Diff(fieldName = "cptCodeId", isUniqueId = false)
    public Integer getCptCodeId()
    {
        return cptCodeId;
    }

    public void setCptCodeId(Integer cptCodeId)
    {
        this.cptCodeId = cptCodeId;
    }

    @Diff(fieldName = "diagnosisCodeId", isUniqueId = false)
    public Integer getDiagnosisCodeId()
    {
        return diagnosisCodeId;
    }

    public void setDiagnosisCodeId(Integer diagnosisCodeId)
    {
        this.diagnosisCodeId = diagnosisCodeId;
    }

    @Diff(fieldName = "validityStatusId", isUniqueId = false)
    public Integer getValidityStatusId()
    {
        return validityStatusId;
    }

    public void setValidityStatusId(Integer validityStatusId)
    {
        this.validityStatusId = validityStatusId;
    }

    @Diff(fieldName = "startDate", isUniqueId = false)
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    @Diff(fieldName = "endDate", isUniqueId = false)
    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    @Diff(fieldName = "active", isUniqueId = false)
    public Boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }
    
    public DiagnosisValidityLookup copy()
    {
        DiagnosisValidityLookup newCopy = new DiagnosisValidityLookup();
        newCopy.setIdDiagnosisValidityLookup(idDiagnosisValidityLookup);
        newCopy.setActive(active);
        newCopy.setCptCodeId(cptCodeId);
        newCopy.setDiagnosisCodeId(diagnosisCodeId);
        newCopy.setDiagnosisValidityId(diagnosisValidityId);
        newCopy.setEndDate(endDate);
        newCopy.setStartDate(startDate);
        newCopy.setValidityStatusId(validityStatusId);
        return newCopy;
    }
}
