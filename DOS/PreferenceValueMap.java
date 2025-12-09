package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @date 10/26/2015
 * @author Robert Hussey <r.hussey@csslis.com>
 */
@Entity
@Table(name = "preferenceValueMap")
public class PreferenceValueMap implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPreferenceValueMap")
    private Integer idPreferenceValueMap;
    @Basic(optional = false)
    @Column(name = "preferenceKeyId")
    private Integer preferenceKeyId;
    @Basic(optional = false)
    @Column(name = "preferenceValueId")
    private Integer preferenceValueId;
    @Basic(optional = false)
    @Column(name = "editable")
    private Boolean editable;

    public Integer getIdPreferenceValueMap()
    {
        return idPreferenceValueMap;
    }

    public void setIdPreferenceValueMap(Integer idPreferenceValueMap)
    {
        this.idPreferenceValueMap = idPreferenceValueMap;
    }

    public Integer getPreferenceKeyId()
    {
        return preferenceKeyId;
    }

    public void setPreferenceKeyId(Integer preferenceKeyId)
    {
        this.preferenceKeyId = preferenceKeyId;
    }

    public Integer getPreferenceValueId()
    {
        return preferenceValueId;
    }

    public void setPreferenceValueId(Integer preferenceValueId)
    {
        this.preferenceValueId = preferenceValueId;
    }

    public Boolean isEditable()
    {
        return editable;
    }

    public void setEditable(Boolean editable)
    {
        this.editable = editable;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreferenceValueMap != null ? idPreferenceValueMap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        PreferenceValueMap other = (PreferenceValueMap) object;
        return (this.idPreferenceValueMap != null || other.idPreferenceValueMap == null) &&
               (this.idPreferenceValueMap == null || this.idPreferenceValueMap.equals(other.idPreferenceValueMap));
    }

    @Override
    public String toString() {
        return "DOS.PreferenceValueMap[ idPreferenceValueMap=" + idPreferenceValueMap + " ]";
    }
}
