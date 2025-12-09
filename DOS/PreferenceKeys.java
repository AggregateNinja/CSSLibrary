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
@Table(name = "preferenceKeys")
public class PreferenceKeys implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPreferenceKeys")
    private Integer idPreferenceKeys;
    @Basic(optional = false)
    @Column(name = "preferenceKey")
    private String preferenceKey;
    @Column(name = "display")
    private String display;
    @Basic(optional = false)
    @Column(name = "editable")
    private Boolean editable;

    public Integer getIdPreferenceKeys()
    {
        return idPreferenceKeys;
    }

    public void setIdPreferenceKeys(Integer idPreferenceKeys)
    {
        this.idPreferenceKeys = idPreferenceKeys;
    }

    public String getPreferenceKey()
    {
        return preferenceKey;
    }

    public void setPreferenceKey(String preferenceKey)
    {
        this.preferenceKey = preferenceKey;
    }

    public String getDisplay()
    {
        return display;
    }

    public void setDisplay(String display)
    {
        this.display = display;
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
        hash += (idPreferenceKeys != null ? idPreferenceKeys.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        PreferenceKeys other = (PreferenceKeys) object;
        return (this.idPreferenceKeys != null || other.idPreferenceKeys == null) && (this.idPreferenceKeys == null || this.idPreferenceKeys.equals(other.idPreferenceKeys));
    }

    @Override
    public String toString() {
        return "DOS.PreferenceKeys[ idPreferenceKeys=" + idPreferenceKeys + " ]";
    }
}
