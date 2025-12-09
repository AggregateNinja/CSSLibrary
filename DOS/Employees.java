
package DOS;

import Utility.Diff;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Employees.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "employees", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Employees.findAll", query = "SELECT e FROM Employees e")})
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemployees", nullable = false)
    private Integer idemployees;
    @Basic(optional = false)
    @Column(name = "firstName", nullable = false, length = 45)
    private String firstName;
    @Basic(optional = false)
    @Column(name = "lastName", nullable = false, length = 45)
    private String lastName;
    @Basic(optional = false)
    @Column(name = "department", nullable = false)
    private int department;
    @Column(name = "position", length = 45)
    private String position;
    @Column(name = "homePhone", length = 45)
    private String homePhone;
    @Column(name = "mobilePhone", length = 45)
    private String mobilePhone;
    @Column(name = "address", length = 45)
    private String address;
    @Column(name = "address2", length = 45)
    private String address2;
    @Column(name = "city", length = 45)
    private String city;
    @Column(name = "state", length = 2)
    private String state;
    @Column(name = "zip", length = 10)
    private String zip;
    @Column(name = "active")
    private Boolean active;    

    public Employees() {
    }

    public Employees(Integer idemployees) {
        this.idemployees = idemployees;
    }
    
    

    public Employees(Integer idemployees, String firstName, String lastName, int department) {
        this.idemployees = idemployees;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    public Integer getIdemployees() {
        return idemployees;
    }

    public void setIdemployees(Integer idemployees) {
        this.idemployees = idemployees;
    }

    @Diff(fieldName = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Diff(fieldName = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Diff(fieldName = "department")
    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    @Diff(fieldName = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Diff(fieldName = "homePhone")
    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    @Diff(fieldName = "mobilePhone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Diff(fieldName = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Diff(fieldName = "address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Diff(fieldName = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Diff(fieldName = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Diff(fieldName = "zip")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    @Diff(fieldName = "isActive")
    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        if (!(object instanceof Employees)) {
            return false;
        }
        Employees other = (Employees) object;
        if ((this.idemployees == null && other.idemployees != null) || (this.idemployees != null && !this.idemployees.equals(other.idemployees))) {
            return false;
        }
        return true;
    }
    


    @Override
    public String toString()
    {
        return (lastName != null ? lastName : "") + ", " + (firstName != null ? firstName : "");
    }

}
