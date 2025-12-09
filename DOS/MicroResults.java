
package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "microResults")
@NamedQueries({
    @NamedQuery(name = "microResults.findAll", query = "SELECT * FROM microResults")})
public class MicroResults implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMicroResults")
    private Integer idMicroResults;
    @Basic(optional = false)
    @Column(name = "microOrderId")
    private Integer microOrderId;
    @Basic(optional = false)
    @Column(name = "microOrganismsId")
    private Integer microOrganismsId;
    @Basic(optional = false)
    @Column(name = "microAntibioticsId")
    private Integer microAntibioticsId;
    @Basic(optional = false)    
    @Column(name = "microSusceptibilityId")
    private Integer microSusceptibilityId;
    @Column(name = "resultCount")
    private String resultCount;
    @Basic(optional = false)
    @Column(name = "posted")
    private Boolean posted; 
    @Basic(optional = false)
    @Column(name = "postedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postedDate;
    @Basic(optional = false)
    @Column(name = "postedBy")
    private Integer postedBy;
    @Basic(optional = false)
    @Column(name = "invalidated")
    private Boolean invalidated;
    @Column(name = "invalidatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invalidatedDate;
    @Column(name = "invalidatedBy")
    private Integer invalidatedBy;
    
    public MicroResults() {
    }

    public MicroResults(Integer microOrderId, Integer microOrganismsId, Integer microAntibioticsId, Integer microSusceptibilityId, String resultCount, Boolean posted, Date postedDate, Integer postedBy) {
        this.microOrderId = microOrderId;
        this.microOrganismsId = microOrganismsId;
        this.microAntibioticsId = microAntibioticsId;
        this.microSusceptibilityId = microSusceptibilityId;
        this.resultCount = resultCount;
        this.posted = posted;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
    }

    public MicroResults(Integer microOrderId, Integer microOrganismsId, Integer microAntibioticsId, Integer microSusceptibilityId, String resultCount, Boolean posted, Date postedDate, Integer postedBy, Boolean invalidated, Date invalidatedDate, Integer invalidatedBy) {
        this.microOrderId = microOrderId;
        this.microOrganismsId = microOrganismsId;
        this.microAntibioticsId = microAntibioticsId;
        this.microSusceptibilityId = microSusceptibilityId;
        this.resultCount = resultCount;
        this.posted = posted;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.invalidated = invalidated;
        this.invalidatedDate = invalidatedDate;
        this.invalidatedBy = invalidatedBy;
    }
    
    

    public Integer getIdMicroResults() {
        return idMicroResults;
    }

    public void setIdMicroResults(Integer idMicroResults) {
        this.idMicroResults = idMicroResults;
    }

    public Integer getMicroOrderId() {
        return microOrderId;
    }

    public void setMicroOrderId(Integer microOrderId) {
        this.microOrderId = microOrderId;
    }

    public Integer getMicroOrganismsId() {
        return microOrganismsId;
    }

    public void setMicroOrganismsId(Integer microOrganismsId) {
        this.microOrganismsId = microOrganismsId;
    }

    public Integer getMicroAntibioticsId() {
        return microAntibioticsId;
    }

    public void setMicroAntibioticsId(Integer microAntibioticsId) {
        this.microAntibioticsId = microAntibioticsId;
    }

    public Integer getMicroSusceptibilityId() {
        return microSusceptibilityId;
    }

    public void setMicroSusceptibilityId(Integer microSusceptibilityId) {
        this.microSusceptibilityId = microSusceptibilityId;
    }

    public String getResultCount() {
        return resultCount;
    }

    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }

    public Boolean isPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Integer getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Integer postedBy) {
        this.postedBy = postedBy;
    }

    public Boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        this.invalidated = invalidated;
    }

    public Date getInvalidatedDate() {
        return invalidatedDate;
    }

    public void setInvalidatedDate(Date invalidatedDate) {
        this.invalidatedDate = invalidatedDate;
    }

    public Integer getInvalidatedBy() {
        return invalidatedBy;
    }

    public void setInvalidatedBy(Integer invalidatedBy) {
        this.invalidatedBy = invalidatedBy;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMicroResults != null ? idMicroResults.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroResults)) {
            return false;
        }
        MicroResults other = (MicroResults) object;
        if ((this.idMicroResults == null && other.idMicroResults != null) || (this.idMicroResults != null && !this.idMicroResults.equals(other.idMicroResults))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MicroResults[ idMicroResults=" + idMicroResults + " ]";
    }
    
}
