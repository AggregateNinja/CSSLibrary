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
@Table(name = "preferenceValues")
public class PreferenceValues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPreferenceValues")
    private Integer idPreferenceValues;
    @Basic(optional = false)
    @Column(name = "value")
    private String value;
    @Column(name = "display")
    private String display;

    public Integer getIdPreferenceValues()
    {
        return idPreferenceValues;
    }

    public void setIdPreferenceValues(Integer idPreferenceValues)
    {
        this.idPreferenceValues = idPreferenceValues;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getDisplay()
    {
        return display;
    }

    public void setDisplay(String display)
    {
        this.display = display;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPreferenceValues != null ? idPreferenceValues.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        PreferenceValues other = (PreferenceValues) object;
        return (this.idPreferenceValues != null || other.idPreferenceValues == null) &&
               (this.idPreferenceValues == null || this.idPreferenceValues.equals(other.idPreferenceValues));
    }

    @Override
    public String toString() {
        return "DOS.PreferenceValues[ idPreferenceValues=" + idPreferenceValues + " ]";
    }
}
