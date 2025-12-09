
package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tubeType")
public class TubeType implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTubeType")
    private Integer idTubeType;
    @Basic(optional = false)
    @Column(name = "abbr")
    private String abbr;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = true)
    @Column(name = "idSpecimenTypes")
    private Integer idSpecimenTypes;

    public TubeType() {}

    public TubeType(String abbr, String name, Integer idSpecimenTypes)
    {
        this.abbr = abbr;
        this.name = name;
        this.idSpecimenTypes = idSpecimenTypes;               
    }

    public Integer getIdTubeType() {
        return idTubeType;
    }

    public void setIdTubeType(Integer idTubeType) {
        this.idTubeType = idTubeType;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdSpecimenTypes() {
        return idSpecimenTypes;
    }

    public void setIdSpecimenTypes(Integer idSpecimenTypes) {
        this.idSpecimenTypes = idSpecimenTypes;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTubeType != null ? idTubeType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TubeType)) {
            return false;
        }
        TubeType other = (TubeType) object;
        if ((this.idTubeType == null && other.idTubeType != null) || 
                (this.idTubeType != null && !this.idTubeType.equals(other.idTubeType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.TubeType[ idTubeType=" + idTubeType + " ]";
    }    
}
