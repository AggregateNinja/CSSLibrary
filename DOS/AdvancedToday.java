/*
 * Computer Service & Support, Inc.  All Rights Reserved Apr 16, 2015
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Apr 16, 2015  3:55:00 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: AdvancedToday.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "advancedToday")
@NamedQueries({
    @NamedQuery(name = "AdvancedToday.findAll", query = "SELECT a FROM AdvancedToday a")})
public class AdvancedToday implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idemployees")
    private Integer idemployees;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private Integer idOrders;

    public AdvancedToday() {
    }

    public AdvancedToday(Integer idemployees) {
        this.idemployees = idemployees;
    }

    public AdvancedToday(Integer idemployees, Integer idOrders) {
        this.idemployees = idemployees;
        this.idOrders = idOrders;
    }

    public Integer getIdemployees() {
        return idemployees;
    }

    public void setIdemployees(Integer idemployees) {
        this.idemployees = idemployees;
    }

    public int getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemployees != null ? idemployees.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedToday)) {
            return false;
        }
        AdvancedToday other = (AdvancedToday) object;
        if ((this.idemployees == null && other.idemployees != null) || (this.idemployees != null && !this.idemployees.equals(other.idemployees))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.AdvancedToday[ idemployees=" + idemployees + " ]";
    }

}
