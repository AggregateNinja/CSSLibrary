/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 8, 2014
 */

package DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jul 8, 2014  10:19:59 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: Territories.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "territories")
@NamedQueries({
    @NamedQuery(name = "Territories.findAll", query = "SELECT t FROM Territories t")})
public class Territory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idterritory")
    private Integer idterritory;
    @Basic(optional = false)
    @Column(name = "territoryName")
    private String territoryName;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private int createdBy;

    public Territory() {
    }

    public Territory(Integer idterritory) {
        this.idterritory = idterritory;
    }

    public Territory(Integer idterritory, String territoryName, String description, Date created, int createdBy) {
        this.idterritory = idterritory;
        this.territoryName = territoryName;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
    }

    public Integer getIdterritory() {
        return idterritory;
    }

    public void setIdterritory(Integer idterritory) {
        this.idterritory = idterritory;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idterritory != null ? idterritory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Territory)) {
            return false;
        }
        Territory other = (Territory) object;
        if ((this.idterritory == null && other.idterritory != null) || (this.idterritory != null && !this.idterritory.equals(other.idterritory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Territories[ idterritory=" + idterritory + " ]";
    }

}
