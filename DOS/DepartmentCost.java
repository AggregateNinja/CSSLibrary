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
@Table(name = "departmentCosts")
@NamedQueries({
    @NamedQuery(name = "departmentCosts.findAll", query = "SELECT * FROM departmentCosts dc")
})
public class DepartmentCost implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @Column(name = "idCosts", nullable = false)
    @Id
    private Integer idCosts;
    
    @Basic(optional = false)
    @Column(name = "departmentId", nullable = false)
    private Integer departmentId;
    
    @Basic(optional = false)
    @Column(name = "costTypeId", nullable = false)
    private Integer costTypeId;
    
    @Basic(optional = false)
    @Column(name = "cost", nullable = false)
    private double cost;
    
    @Column(name = "active", nullable = false)
    private boolean active;
    
    public DepartmentCost() {
        this.active = true;
    }    
    public DepartmentCost(Integer idCosts, Integer departmentId, Integer costTypeId, double cost) {
        this.idCosts = idCosts;
        this.departmentId = departmentId;
        this.costTypeId = costTypeId;
        this.cost = cost;
    }
    
    public Integer getIdCosts() {
        return this.idCosts;
    }
    public Integer getDepartmentId() {
        return this.departmentId;
    }
    public Integer getCostTypeId() {
        return this.costTypeId;
    }
    public double getCost() {
        return this.cost;
    }
    public boolean getActive() {
        return this.active;
    }
    
    public void setIdCosts(Integer idCosts) {
        this.idCosts = idCosts;
    }
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public void setCostTypeId(Integer costTypeId) {
        this.costTypeId = costTypeId;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.cost);
    }
}
