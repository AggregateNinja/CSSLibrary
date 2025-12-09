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
@Table(name = "microAntibiotics")
@NamedQueries({
    @NamedQuery(name = "MicroAntibiotics.findAll", query = "SELECT m FROM MicroAntibiotics m")})
public class MicroAntibiotics implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmicroAntibiotics")
    private Integer idmicroAntibiotics;
    @Basic(optional = false)
    @Column(name = "antibioticName")
    private String antibioticName;
    @Basic(optional = false)
    @Column(name = "antibioticAbbr")
    private String antibioticAbbr;
    @Column(name = "externalId")
    private String externalId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public MicroAntibiotics() {
    }

    public MicroAntibiotics(Integer idmicroAntibiotics) {
        this.idmicroAntibiotics = idmicroAntibiotics;
    }

    public MicroAntibiotics(Integer idmicroAntibiotics, String antibioticName, String antibioticAbbr, boolean active) {
        this.idmicroAntibiotics = idmicroAntibiotics;
        this.antibioticName = antibioticName;
        this.antibioticAbbr = antibioticAbbr;
        this.active = active;
    }

    public Integer getIdmicroAntibiotics() {
        return idmicroAntibiotics;
    }

    public void setIdmicroAntibiotics(Integer idmicroAntibiotics) {
        this.idmicroAntibiotics = idmicroAntibiotics;
    }

    public String getAntibioticName() {
        return antibioticName;
    }

    public void setAntibioticName(String antibioticName) {
        this.antibioticName = antibioticName;
    }

    public String getAntibioticAbbr() {
        return antibioticAbbr;
    }

    public void setAntibioticAbbr(String antibioticAbbr) {
        this.antibioticAbbr = antibioticAbbr;
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
        hash += (idmicroAntibiotics != null ? idmicroAntibiotics.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroAntibiotics)) {
            return false;
        }
        MicroAntibiotics other = (MicroAntibiotics) object;
        if ((this.idmicroAntibiotics == null && other.idmicroAntibiotics != null) || (this.idmicroAntibiotics != null && !this.idmicroAntibiotics.equals(other.idmicroAntibiotics))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getAntibioticName();
    }
    
}
