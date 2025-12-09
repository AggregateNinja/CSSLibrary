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
@Table(name = "microSites")
@NamedQueries({
    @NamedQuery(name = "MicroSites.findAll", query = "SELECT m FROM MicroSites m")})
public class MicroSites implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmicroSites")
    private Integer idmicroSites;
    @Basic(optional = false)
    @Column(name = "siteName")
    private String siteName;
    @Basic(optional = false)
    @Column(name = "siteAbbr")
    private String siteAbbr;
    @Column(name = "externalId")
    private String externalId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public MicroSites() {
    }

    public MicroSites(Integer idmicroSites) {
        this.idmicroSites = idmicroSites;
    }

    public MicroSites(Integer idmicroSites, String siteName, String siteAbbr, boolean active) {
        this.idmicroSites = idmicroSites;
        this.siteName = siteName;
        this.siteAbbr = siteAbbr;
        this.active = active;
    }

    public Integer getIdmicroSites() {
        return idmicroSites;
    }

    public void setIdmicroSites(Integer idmicroSites) {
        this.idmicroSites = idmicroSites;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteAbbr() {
        return siteAbbr;
    }

    public void setSiteAbbr(String siteAbbr) {
        this.siteAbbr = siteAbbr;
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
        hash += (idmicroSites != null ? idmicroSites.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroSites)) {
            return false;
        }
        MicroSites other = (MicroSites) object;
        if ((this.idmicroSites == null && other.idmicroSites != null) || (this.idmicroSites != null && !this.idmicroSites.equals(other.idmicroSites))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MicroSites[ idmicroSites=" + idmicroSites + " ]";
    }
    
}
