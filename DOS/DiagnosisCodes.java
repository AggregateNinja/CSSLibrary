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

/**
 * @date:   Jan 15, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: DiagnosisCodes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "diagnosisCodes")
@NamedQueries(
{
    @NamedQuery(name = "DiagnosisCodes.findAll", query = "SELECT d FROM DiagnosisCodes d")
})
public class DiagnosisCodes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDiagnosisCodes")
    private Integer idDiagnosisCodes;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    //@Column(name = "loinc")
    //private String loinc;
    @Column(name = "description")
    private String description;
    @Column(name = "FullDescription")
    private String fullDescription;
    //@Column(name = "cptCode")
    //private String cptCode;
    @Column(name = "validity")
    private String validity; // "I" = Incomplete, "C" = complete
    @Basic(optional = false)
    @Column(name = "type")   // The letter category of the code (e.g. G00.1 would be "G")
    private String type;
    @Basic(optional = false)
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    @Column(name = "createdBy")
    private Integer createdBy;
    //@Column(name = "dateUpdated")
    //@Temporal(TemporalType.TIMESTAMP)
    //private Date dateUpdated;
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "diagnosisCodeId")
    //private Collection<DiagnosisValidity> diagnosisValidityCollection;
    @Basic(optional = false)
    @Column(name = "version") // The ICD version (e.g. 9 or 10)
    private Integer version;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;
    @Basic(optional = false)
    @Column(name = "searchable")
    private Boolean searchable;
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Column(name = "deactivatedBy")
    private Integer deactivatedBy;
    @Basic(optional = false)
    @Column(name = "isHeader")
    private Boolean isHeader;

    public DiagnosisCodes()
    {
    }

    public DiagnosisCodes(Integer idDiagnosisCodes)
    {
        this.idDiagnosisCodes = idDiagnosisCodes;
    }

    public DiagnosisCodes(Integer idDiagnosisCodes, String code, Date dateCreated, Integer version)
    {
        this.idDiagnosisCodes = idDiagnosisCodes;
        this.code = code;
        this.dateCreated = dateCreated;
        this.version = version;        
    }

    public Integer getIdDiagnosisCodes()
    {
        return idDiagnosisCodes;
    }

    public void setIdDiagnosisCodes(Integer idDiagnosisCodes)
    {
        this.idDiagnosisCodes = idDiagnosisCodes;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getFullDescription()
    {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription)
    {
        this.fullDescription = fullDescription;
    }

    public String getValidity()
    {
        return validity;
    }

    public void setValidity(String validity)
    {
        this.validity = validity;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
    
    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public Integer getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }
    
    public Integer getVersion()
    {
        return version;
    }

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Boolean isSearchable()
    {
        return searchable;
    }

    public void setSearchable(Boolean searchable)
    {
        this.searchable = searchable;
    }

    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    public Integer getDeactivatedBy()
    {
        return deactivatedBy;
    }

    public void setDeactivatedBy(Integer deactivatedBy)
    {
        this.deactivatedBy = deactivatedBy;
    }

    public Boolean isHeader()
    {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader)
    {
        this.isHeader = isHeader;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idDiagnosisCodes != null ? idDiagnosisCodes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiagnosisCodes))
        {
            return false;
        }
        DiagnosisCodes other = (DiagnosisCodes) object;
        if ((this.idDiagnosisCodes == null && other.idDiagnosisCodes != null) || (this.idDiagnosisCodes != null && !this.idDiagnosisCodes.equals(other.idDiagnosisCodes)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.DiagnosisCodes[ idDiagnosisCodes=" + idDiagnosisCodes + " ]";
    }

    public DiagnosisCodes copy()
    {
        DiagnosisCodes copy = new DiagnosisCodes();
        copy.setIdDiagnosisCodes(idDiagnosisCodes);
        copy.setCode(code);
        copy.setDescription(description);
        copy.setFullDescription(fullDescription);
        copy.setValidity(validity);
        copy.setType(type);
        copy.setDateCreated(dateCreated);
        copy.setCreatedBy(createdBy);
        copy.setVersion(version);
        copy.setActive(active);
        copy.setSearchable(searchable);
        copy.setDeactivatedDate(deactivatedDate);
        copy.setDeactivatedBy(deactivatedBy);
        copy.setIsHeader(isHeader);
        return copy;
    }
}
