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
@Table(name = "employeeLocations", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "EmployeeLocations.findAll", query = "SELECT e FROM EmployeeLocations e")
})
public class EmployeeLocations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    // Integer field that serves as a foreign key to Employees table
    @Basic(optional = false)
    @Column(name = "employeeId", nullable = false)
    private Integer employeeId;
    // Integer field that serves as a foreign key to Locations table
    @Basic(optional = false)
    @Column(name = "locationId", nullable = false)
    private Integer locationId;
    //bit field specifying default location for the employee
    @Column(name = "defaultLocation")
    private boolean defaultLocation;

    public EmployeeLocations() {

    }

    public EmployeeLocations(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public EmployeeLocations(Integer employeeId, Integer locationId, boolean defaultLoc) {
        this.employeeId = employeeId;
        this.locationId = locationId;
        this.defaultLocation = defaultLoc;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locations)) {
            return false;
        }
        EmployeeLocations other = (EmployeeLocations) object;
        return (this.employeeId != null || other.employeeId == null) && (this.employeeId == null || this.employeeId.equals(other.employeeId));
    }

    @Override
    public String toString() {
        return "(" + employeeId + ") " + locationId;
    }

}
