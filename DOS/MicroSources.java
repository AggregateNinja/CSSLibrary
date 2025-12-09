/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rypip_000
 */
@Entity
@Table(name = "microSources")
@NamedQueries({
    @NamedQuery(name = "MicroSources.findAll", query = "SELECT m FROM MicroSources m")})
public class MicroSources implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmicroSources")
    private Integer idmicroSources;
    @Basic(optional = false)
    @Column(name = "sourceName")
    private String sourceName;
    @Basic(optional = false)
    @Column(name = "sourceAbbr")
    private String sourceAbbr;
    @Column(name = "externalId")
    private String externalId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public MicroSources() {
    }

    public MicroSources(Integer idmicroSources) {
        this.idmicroSources = idmicroSources;
    }

    public MicroSources(Integer idmicroSources, String sourceName, String sourceAbbr, boolean active) {
        this.idmicroSources = idmicroSources;
        this.sourceName = sourceName;
        this.sourceAbbr = sourceAbbr;
        this.active = active;
    }

    public Integer getIdmicroSources() {
        return idmicroSources;
    }

    public void setIdmicroSources(Integer idmicroSources) {
        this.idmicroSources = idmicroSources;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceAbbr() {
        return sourceAbbr;
    }

    public void setSourceAbbr(String sourceAbbr) {
        this.sourceAbbr = sourceAbbr;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmicroSources != null ? idmicroSources.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroSources)) {
            return false;
        }
        MicroSources other = (MicroSources) object;
        if ((this.idmicroSources == null && other.idmicroSources != null) || (this.idmicroSources != null && !this.idmicroSources.equals(other.idmicroSources))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MicroSources[ idmicroSources=" + idmicroSources + " ]";
    }
    
}
