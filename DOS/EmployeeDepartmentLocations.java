/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date: January 22, 2018
 * @author Aanchal
 *
 * @package: DOS
 * @file name: EmployeeLocations.java
 *
 * @Description
 *
 * Computer Service & Support Inc. All Rights Reserved
 */
@Entity
@Table(name = "employeeDepartmentLocations", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "EmployeeDepartmentLocations.findAll", query = "SELECT e FROM EmployeeDepartmentLocations e")
})
public class EmployeeDepartmentLocations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    // Integer field that serves as a foreign key to Employees table
    @Basic(optional = false)
    @Column(name = "employeeDepartmentId", nullable = false)
    private Integer employeeDepartmentId;
    // Integer field that serves as a foreign key to Locations table
    @Basic(optional = false)
    @Column(name = "locationId", nullable = false)
    private Integer locationId;
    //bit field specifying default location for the employee
    @Column(name = "defaultLocation")
    private boolean defaultLocation;

    public EmployeeDepartmentLocations() {

    }

    public EmployeeDepartmentLocations(Integer employeeDepartmentId) {
        this.employeeDepartmentId = employeeDepartmentId;
    }

    public EmployeeDepartmentLocations(Integer employeeDepartmentId, Integer locationId, boolean defaultLoc) {
        this.employeeDepartmentId = employeeDepartmentId;
        this.locationId = locationId;
        this.defaultLocation = defaultLoc;
    }

    public Integer getEmployeeDepartmentId() {
        return employeeDepartmentId;
    }

    public void setEmployeeDepartmentId(Integer employeeDepartmentId) {
        this.employeeDepartmentId = employeeDepartmentId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public boolean getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(boolean defaultLoc) {
        this.defaultLocation = defaultLoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeDepartmentId != null ? employeeDepartmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locations)) {
            return false;
        }
        EmployeeDepartmentLocations other = (EmployeeDepartmentLocations) object;
        return (this.employeeDepartmentId != null || other.employeeDepartmentId == null) && (this.employeeDepartmentId == null || this.employeeDepartmentId.equals(other.employeeDepartmentId));
    }

    @Override
    public String toString() {
        return "(" + employeeDepartmentId + ") " + locationId;
    }

}
