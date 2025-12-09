
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 16, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DOS
 * @file name: Panels.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "panels")
@NamedQueries({
    @NamedQuery(name = "Panels.findAll", query = "SELECT p FROM Panels p")})
public class Panels implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idpanels")
    private int idpanels;
    @Basic(optional = false)
    @Column(name = "subtestId")
    private int subtestId;
    @Basic(optional = false)
    @Column(name = "subtestOrder")
    private int subtestOrder;
    @Basic(optional = false)
    @Column(name = "optional")
    private boolean optional;

    public Panels() {
    }

    public Panels(Integer id) {
        this.id = id;
    }

    public Panels(Integer id, int idpanels, int subtestId, int subtestOrder) {
        this.id = id;
        this.idpanels = idpanels;
        this.subtestId = subtestId;
        this.subtestOrder = subtestOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdpanels() {
        return idpanels;
    }

    public void setIdpanels(int idpanels) {
        this.idpanels = idpanels;
    }

    public int getSubtestId() {
        return subtestId;
    }

    public void setSubtestId(int subtestId) {
        this.subtestId = subtestId;
    }

    public int getSubtestOrder() {
        return subtestOrder;
    }

    public void setSubtestOrder(int subtestOrder) {
        this.subtestOrder = subtestOrder;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Panels)) {
            return false;
        }
        Panels other = (Panels) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Panels[ id=" + id + " ]";
    }

}
