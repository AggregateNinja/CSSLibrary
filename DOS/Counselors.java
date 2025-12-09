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
 * @date:   Aug 5, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Counselors.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "counselors")
@NamedQueries(
{
    @NamedQuery(name = "Counselors.findAll", query = "SELECT c FROM Counselors c")
})
public class Counselors implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcounselors")
    private Integer idcounselors;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @Column(name = "firstName")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "NPI")
    private Integer npi;
    @Column(name = "UPIN")
    private String upin;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;
    @Column(name = "externalId")
    private String externalId;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;

    public Counselors()
    {
    }

    public Counselors(Integer idcounselors)
    {
        this.idcounselors = idcounselors;
    }

    public Counselors(Integer idcounselors, int number, String firstName, String lastName)
    {
        this.idcounselors = idcounselors;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getIdcounselors()
    {
        return idcounselors;
    }

    public void setIdcounselors(Integer idcounselors)
    {
        this.idcounselors = idcounselors;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public Integer getNpi()
    {
        return npi;
    }

    public void setNpi(Integer npi)
    {
        this.npi = npi;
    }

    public String getUpin()
    {
        return upin;
    }

    public void setUpin(String upin)
    {
        this.upin = upin;
    }

    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }
    
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }
    
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idcounselors != null ? idcounselors.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Counselors))
        {
            return false;
        }
        Counselors other = (Counselors) object;
        if ((this.idcounselors == null && other.idcounselors != null) || (this.idcounselors != null && !this.idcounselors.equals(other.idcounselors)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Counselors[ idcounselors=" + idcounselors + " ]";
    }

}
