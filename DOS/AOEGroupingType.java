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
import javax.persistence.Table;

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "aoeGroupingType", catalog = "css", schema = "")
public class AOEGroupingType implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column (name = "name")
    private String name;
    @Column (name = "requiresPatientHeight")
    private Boolean requiresPatientHeight;
    @Column (name = "requiresPatientWeight")
    private Boolean requiresPatientWeight;
    
    public AOEGroupingType() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean requiresPatientHeight() {
        return requiresPatientHeight;
    }

    public void setRequiresPatientHeight(Boolean requirespatientHeight) {
        this.requiresPatientHeight = requirespatientHeight;
    }

    public Boolean requiresPatientWeight() {
        return requiresPatientWeight;
    }

    public void setRequiresPatientWeight(Boolean requiresPatientWeight) {
        this.requiresPatientWeight = requiresPatientWeight;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AOEGroupingType)) {
            return false;
        }
        AOEGroupingType other = (AOEGroupingType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.AOEGroupingType[ id=" + id + ", name=" + name +" ]";
    }        
    
}
