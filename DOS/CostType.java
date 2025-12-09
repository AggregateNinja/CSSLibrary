/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Edd
 */

@Entity
@Table(name = "costTypes")
@NamedQueries({
    @NamedQuery(name = "CostTypes.findAll", query = "SELECT * FROM costTypes c")
})
public class CostType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "idCostTypes")
    @Id
    private Integer idCostTypes;
    
    @Basic(optional = false)
    @Column(name = "costType", nullable = false)
    private String costType;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    public CostType() {
        this.active = true;
    }
    public CostType(Integer idCostTypes, String costType) {
        this.idCostTypes = idCostTypes;
        this.costType = costType;
        this.active = true;
    }
    public CostType(Integer idCostTypes, String costType, String description) {
        this.idCostTypes = idCostTypes;
        this.costType = costType;
        this.description = description;
        this.active = true;
    }
    public CostType(Integer idCostTypes, String costType, String description, boolean active) {
        this.idCostTypes = idCostTypes;
        this.costType = costType;
        this.description = description;
        this.active = active;
    }

    public Integer getIdCostTypes() {
        return this.idCostTypes;
    }
    public String getCostType() {
        return this.costType;
    }
    public String getDescription() {
        return this.description;
    }
    public boolean getActive() {
        return this.active;
    }    
    
    public void setIdCostTypes(Integer idCostTypes) {
        this.idCostTypes = idCostTypes;
    }
    public void setCostType(String costType) {
        this.costType = costType;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return this.costType;
    }
    
}
