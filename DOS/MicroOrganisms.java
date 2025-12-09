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
@Table(name = "microOrganisms")
@NamedQueries({
    @NamedQuery(name = "MicroOrganisms.findAll", query = "SELECT m FROM MicroOrganisms m")})
public class MicroOrganisms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmicroOrganisms")
    private Integer idmicroOrganisms;
    @Basic(optional = false)
    @Column(name = "organismName")
    private String organismName;
    @Basic(optional = false)
    @Column(name = "organismAbbr")
    private String organismAbbr;
    @Column(name = "externalId")
    private String externalId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public MicroOrganisms() {
    }

    public MicroOrganisms(Integer idmicroOrganisms) {
        this.idmicroOrganisms = idmicroOrganisms;
    }

    public MicroOrganisms(Integer idmicroOrganisms, String organismName, String organismAbbr, boolean active) {
        this.idmicroOrganisms = idmicroOrganisms;
        this.organismName = organismName;
        this.organismAbbr = organismAbbr;
        this.active = active;
    }

    public Integer getIdmicroOrganisms() {
        return idmicroOrganisms;
    }

    public void setIdmicroOrganisms(Integer idmicroOrganisms) {
        this.idmicroOrganisms = idmicroOrganisms;
    }

    public String getOrganismName() {
        return organismName;
    }

    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    public String getOrganismAbbr() {
        return organismAbbr;
    }

    public void setOrganismAbbr(String organismAbbr) {
        this.organismAbbr = organismAbbr;
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
        hash += (idmicroOrganisms != null ? idmicroOrganisms.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroOrganisms)) {
            return false;
        }
        MicroOrganisms other = (MicroOrganisms) object;
        if ((this.idmicroOrganisms == null && other.idmicroOrganisms != null) || (this.idmicroOrganisms != null && !this.idmicroOrganisms.equals(other.idmicroOrganisms))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return organismName;
    }
    
}
