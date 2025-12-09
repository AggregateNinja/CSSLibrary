package DOS;

import DOS.IDOS.IDO;
import Utility.Diff;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 16, 2012
 * @author: CSS Dev
 * 
 * @project:  
 * @package: DOS
 * @file name: Insurances.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity
@Table(name = "insurances")
@NamedQueries(
{
    @NamedQuery(name = "Insurances.findAll", query = "SELECT i FROM Insurances i")
})
public class Insurances implements Serializable, IDO
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idinsurances")
    private Integer idinsurances;
    @Basic(optional = false)
    @Column(name = "number")
    private Integer number;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "abbreviation")
    private String abbreviation;
    @Basic(optional = false)
    @Column(name = "insuranceTypeId")
    private Integer insuranceTypeId;
    @Basic(optional = false)
    @Column(name = "insuranceSubmissionTypeId")
    private Integer insuranceSubmissionTypeId;
    @Basic(optional = false)
    @Column(name = "insuranceSubmissionModeId")    
    private Integer insuranceSubmissionModeId;
    @Column(name = "address")
    private String address;
    @Column(name = "address2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zip")
    private String zip;
    @Basic(optional = false)
    @Column(name = "countryId")
    private Integer countryId;
    @Column(name = "phone")
    private String phone;
    @Column(name = "fax")
    private String fax;
    @Column(name = "receiverid")
    private String receiverid;
    @Column(name = "sourceid1")
    private String sourceid1;
    @Column(name = "sourceid2")
    private String sourceid2;
    @Column(name = "payorid")
    private String payorId;
    @Basic(optional = false)
    @Column(name = "serviceTypeId")
    private Integer serviceTypeId;
    @Column(name = "accept_assignment")
    private String acceptAssignment;
    @Basic(optional =false)
    @Column(name = "active")
    private Boolean active;
    @Column(name = "billSendout")
    private Boolean billSendout;
    
    // Default to main schema
    private DatabaseSchema schema = DatabaseSchema.CSS;
    
    public Insurances()
    {
    }

    public Insurances(Integer idinsurances)
    {
        this.idinsurances = idinsurances;
    }

    /**
     * Copy constructor
     * @param original 
     */
    public Insurances(Insurances original)
    {
        this.idinsurances = original.getIdinsurances();
        this.name = original.getName();
        this.number = original.getNumber();
        this.abbreviation = original.getAbbreviation();
        this.insuranceTypeId = original.getInsuranceTypeId();
        this.insuranceSubmissionTypeId = original.getInsuranceSubmissionTypeId();
        this.insuranceSubmissionModeId = original.getInsuranceSubmissionModeId();
        this.address = original.getAddress();
        this.address2 = original.getAddress2();
        this.city = original.getCity();
        this.state = original.getState();
        this.zip = original.getZip();
        this.countryId = original.getCountryId();
        this.phone = original.getPhone();
        this.fax = original.getFax();
        this.receiverid = original.getReceiverid();
        this.sourceid1 = original.getSourceid1();
        this.sourceid2 = original.getSourceid2();
        this.payorId = original.getPayorId();
        this.serviceTypeId = original.getServiceTypeId();
        this.acceptAssignment = original.getAcceptAssignment();
        this.active = original.isActive();
        this.billSendout = original.isBillSendout();
    }

    @Diff(fieldName = "idInsurances")
    public Integer getIdinsurances()
    {
        return idinsurances;
    }

    public void setIdinsurances(Integer idinsurances)
    {
        this.idinsurances = idinsurances;
    }

    @Diff(fieldName = "name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName = "number")
    public Integer getNumber()
    {
        return number;
    }

    public void setNumber(Integer number)
    {
        this.number = number;
    }
    
    @Diff(fieldName = "abbreviation")
    public String getAbbreviation()
    {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation)
    {
        this.abbreviation = abbreviation;
    }

    @Diff(fieldName = "insuranceTypeId")
    public Integer getInsuranceTypeId()
    {
        return insuranceTypeId;
    }

    public void setInsuranceTypeId(Integer insuranceTypeId)
    {
        this.insuranceTypeId = insuranceTypeId;
    }

    @Diff(fieldName = "insuranceSubmissionTypeId")
    public Integer getInsuranceSubmissionTypeId()
    {
        return insuranceSubmissionTypeId;
    }

    public void setInsuranceSubmissionTypeId(Integer insuranceSubmissionTypeId)
    {
        this.insuranceSubmissionTypeId = insuranceSubmissionTypeId;
    }

    @Diff(fieldName = "insuranceSubmissionModeId")
    public Integer getInsuranceSubmissionModeId()
    {
        return insuranceSubmissionModeId;
    }

    public void setInsuranceSubmissionModeId(Integer insuranceSubmissionModeId)
    {
        this.insuranceSubmissionModeId = insuranceSubmissionModeId;
    }
    
    @Diff(fieldName = "address")
    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Diff(fieldName = "address2")
    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    @Diff(fieldName = "city")
    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    @Diff(fieldName = "state")
    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    @Diff(fieldName = "zip")
    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    @Diff(fieldName = "countryId")
    public Integer getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Integer countryId)
    {
        this.countryId = countryId;
    }
    
    @Diff(fieldName = "phone")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Diff(fieldName = "fax")
    public String getFax()
    {
        return fax;
    }

    public void setFax(String fax)
    {
        this.fax = fax;
    }
    
    @Diff(fieldName = "receiverId")
    public String getReceiverid()
    {
        return receiverid;
    }

    public void setReceiverid(String receiverid)
    {
        this.receiverid = receiverid;
    }

    @Diff(fieldName = "sourceid1")
    public String getSourceid1()
    {
        return sourceid1;
    }

    public void setSourceid1(String sourceid1)
    {
        this.sourceid1 = sourceid1;
    }

    @Diff(fieldName = "sourceid2")
    public String getSourceid2()
    {
        return sourceid2;
    }

    public void setSourceid2(String sourceid2)
    {
        this.sourceid2 = sourceid2;
    }

    @Diff(fieldName = "payorid")
    public String getPayorId()
    {
        return payorId;
    }

    public void setPayorId(String payorId)
    {
        this.payorId = payorId;
    }

    @Diff(fieldName = "serviceTypeId")
    public Integer getServiceTypeId()
    {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId)
    {
        this.serviceTypeId = serviceTypeId;
    }
    
    @Diff(fieldName = "acceptAssignment")
    public String getAcceptAssignment()
    {
        return acceptAssignment;
    }

    public void setAcceptAssignment(String acceptAssignment)
    {
        this.acceptAssignment = acceptAssignment;
    }

    @Diff(fieldName = "active")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
    
    @Diff(fieldName = "billSendout")
    public Boolean isBillSendout()
    {
        return billSendout;
    }
    
    public void setBillSendout(Boolean billSendout)
    {
        this.billSendout = billSendout;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idinsurances != null ? idinsurances.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Insurances))
        {
            return false;
        }
        Insurances other = (Insurances) object;
        if ((this.idinsurances == null && other.idinsurances != null) || (this.idinsurances != null && !this.idinsurances.equals(other.idinsurances)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "(" + idinsurances + ") " + (name != null ? name : "");
        //return "DOS.Insurances[ Schema=" + this.schema.name() + " idinsurances=" + idinsurances + " ]";
    }

    @Override
    public DatabaseSchema getDatabaseSchema()
    {
        return this.schema;
    }

    @Override
    public void setDatabaseSchema(DatabaseSchema schema)
    {
        this.schema = schema;
    }

}
