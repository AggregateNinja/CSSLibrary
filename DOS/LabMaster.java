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
 * @date:   Oct 17, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: LabMaster.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "labMaster")
@NamedQueries(
{
    @NamedQuery(name = "LabMaster.findAll", query = "SELECT l FROM LabMaster l")
})
public class LabMaster implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlabMaster")
    private Integer idlabMaster;
    @Basic(optional = false)
    @Column(name = "labName")
    private String labName;
    @Column(name = "facilityName")
    private String facilityName;
    @Basic(optional = false)
    @Column(name = "address1")
    private String address1;
    @Basic(optional = false)
    @Column(name = "address2")
    private String address2;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "zip")
    private String zip;
    @Column(name = "physicalAddress1")
    private String physicalAddress1;
    @Column(name = "physicalAddress2")
    private String physicalAddress2;
    @Column(name = "physicalCity")
    private String physicalCity;
    @Column(name = "physicalState")
    private String physicalState;
    @Column(name = "physicalZip")
    private String physicalZip;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Column(name = "locality")
    private String locality;
    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "fax")
    private String fax;
    @Basic(optional = false)
    @Column(name = "CLIANumber")
    private String cLIANumber;
    @Column(name = "COLANumber")
    private String cOLANumber;
    @Basic(optional = false)
    @Column(name = "NPINumber")
    private int nPINumber;
    @Basic(optional = false)
    @Column(name = "taxID")
    private String taxID;
    @Column(name = "providerID")
    private String providerID;
    @Basic(optional = false)
    @Column(name = "labDirector")
    private String labDirector;
    @Basic(optional = false)
    @Column(name = "contact")
    private String contact;
    @Column(name = "website")
    private String website;
    @Column(name = "email")
    private String email;
    @Column(name = "logoPath")
    private String logoPath;
    @Column(name = "logoPath2")
    private String logoPath2;
    @Column(name = "avalonVersion")
    private String avalonVersion;
    
    public LabMaster()
    {
    }

    public LabMaster(Integer idlaboratoryMaster)
    {
        this.idlabMaster = idlaboratoryMaster;
    }

    public LabMaster(Integer idlaboratoryMaster, String labName, String address1, String address2, String city, String state, String zip, String country, String phone, String fax, String cLIANumber, int nPINumber, String taxID, String labDirector, String contact, String avalonVersion)
    {
        this.idlabMaster = idlaboratoryMaster;
        this.labName = labName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
        this.cLIANumber = cLIANumber;
        this.nPINumber = nPINumber;
        this.taxID = taxID;
        this.labDirector = labDirector;
        this.contact = contact;
        this.avalonVersion = avalonVersion;
    }

    public Integer getIdlabMaster()
    {
        return idlabMaster;
    }

    public void setIdlabMaster(Integer idlaboratoryMaster)
    {
        this.idlabMaster = idlaboratoryMaster;
    }

    public String getLabName()
    {
        return labName;
    }

    public void setLabName(String labName)
    {
        this.labName = labName;
    }

    public String getFacilityName()
    {
        return facilityName;
    }

    public void setFacilityName(String facilityName)
    {
        this.facilityName = facilityName;
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

    public String getPhysicalAddress1()
    {
        return physicalAddress1;
    }

    public void setPhysicalAddress1(String physicalAddress1)
    {
        this.physicalAddress1 = physicalAddress1;
    }

    public String getPhysicalAddress2()
    {
        return physicalAddress2;
    }

    public void setPhysicalAddress2(String physicalAddress2)
    {
        this.physicalAddress2 = physicalAddress2;
    }

    public String getPhysicalCity()
    {
        return physicalCity;
    }

    public void setPhysicalCity(String physicalCity)
    {
        this.physicalCity = physicalCity;
    }

    public String getPhysicalState()
    {
        return physicalState;
    }

    public void setPhysicalState(String physicalState)
    {
        this.physicalState = physicalState;
    }

    public String getPhysicalZip()
    {
        return physicalZip;
    }

    public void setPhysicalZip(String physicalZip)
    {
        this.physicalZip = physicalZip;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getLocality()
    {
        return locality;
    }

    public void setLocality(String locality)
    {
        this.locality = locality;
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

    public String getCLIANumber()
    {
        return cLIANumber;
    }

    public void setCLIANumber(String cLIANumber)
    {
        this.cLIANumber = cLIANumber;
    }

    public String getCOLANumber()
    {
        return cOLANumber;
    }

    public void setCOLANumber(String cOLANumber)
    {
        this.cOLANumber = cOLANumber;
    }

    public int getNPINumber()
    {
        return nPINumber;
    }

    public void setNPINumber(int nPINumber)
    {
        this.nPINumber = nPINumber;
    }

    public String getTaxID()
    {
        return taxID;
    }

    public void setTaxID(String taxID)
    {
        this.taxID = taxID;
    }

    public String getProviderID()
    {
        return providerID;
    }

    public void setProviderID(String providerID)
    {
        this.providerID = providerID;
    }

    public String getLabDirector()
    {
        return labDirector;
    }

    public void setLabDirector(String labDirector)
    {
        this.labDirector = labDirector;
    }

    public String getContact()
    {
        return contact;
    }

    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLogoPath()
    {
        return logoPath;
    }

    public void setLogoPath(String logoPath)
    {
        this.logoPath = logoPath;
    }

    public String getLogoPath2()
    {
        return logoPath2;
    }

    public void setLogoPath2(String logoPath2)
    {
        this.logoPath2 = logoPath2;
    }

    public String getAvalonVersion() {
        return avalonVersion;
    }

    public void setAvalonVersion(String avalonVersion) {
        this.avalonVersion = avalonVersion;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idlabMaster != null ? idlabMaster.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabMaster))
        {
            return false;
        }
        LabMaster other = (LabMaster) object;
        if ((this.idlabMaster == null && other.idlabMaster != null) || (this.idlabMaster != null && !this.idlabMaster.equals(other.idlabMaster)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.LabMaster[ idlaboratoryMaster=" + idlabMaster + " ]";
    }

}
