package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 13, 2012
 * @author: CSS Dev
 * 
 * @project:  
 * @package: DOS
 * @file name: Doctors.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity
@Table(name = "doctors")
@NamedQueries(
{
    @NamedQuery(name = "Doctors.findAll", query = "SELECT d FROM Doctors d")
})
public class Doctors implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddoctors")
    private Integer iddoctors;
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
    private Long npi;
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
	@Column(name = "phone")
    private String phone;
	@Column(name = "fax")
    private String fax;
	@Column(name = "email")
    private String email;
    @Column(name = "externalId")
    private String externalId;
    @Column(name = "crossref")
    private String crossref;
    @Column(name = "active")
    private Boolean active;

    public Doctors()
    {
    }

    public Doctors(Integer iddoctors)
    {
        this.iddoctors = iddoctors;
    }

    public Doctors(Integer iddoctors, int number, String firstName, String lastName)
    {
        this.iddoctors = iddoctors;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getIddoctors()
    {
        return iddoctors;
    }

    public void setIddoctors(Integer iddoctors)
    {
        this.iddoctors = iddoctors;
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

    public Long getNpi()
    {
        return npi;
    }

    public void setNpi(Long npi)
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
	
    public String getExternalId()
    {
        return externalId;
    }

    public void setExternalId(String externalId)
    {
        this.externalId = externalId;
    }

    public String getCrossref()
    {
        return crossref;
    }

    public void setCrossref(String crossref)
    {
        this.crossref = crossref;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (iddoctors != null ? iddoctors.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Doctors))
        {
            return false;
        }
        Doctors other = (Doctors) object;
        if ((this.iddoctors == null && other.iddoctors != null) || (this.iddoctors != null && !this.iddoctors.equals(other.iddoctors)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        String name = getFirstName() + " " + getLastName();
        return "(" + number + ") " + (firstName != null ? name : (lastName != null ? lastName : "")); 
    }

}
