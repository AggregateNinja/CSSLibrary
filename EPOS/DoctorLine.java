package EPOS;

import java.math.BigInteger;

/**
 * @date:   Mar 13, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary
 * @package: EPOS
 * @file name: DoctorLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class DoctorLine 
{
    private int Number;
    private String FirstName;
    private String LastName;
    private Long NPI;
    private String UPIN;
    private String Address1;
    private String Address2;
    private String City;
    private String State;
    private String Zip;
    private String ExternalId;
    private String CrossRef;
    private String Phone;
    private String Fax;
    private String Email;

    public DoctorLine()
    {
    }

    public DoctorLine(int Number, String FirstName, String LastName, Long NPI, String UPIN, String Address1, String Address2, String City, String State, String Zip, String ExternalId, String CrossRef)
    {
        this.Number = Number;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.NPI = NPI;
        this.UPIN = UPIN;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.City = City;
        this.State = State;
        this.Zip = Zip;
        this.ExternalId = ExternalId;
        this.CrossRef = CrossRef;
    }

    public String getAddress1()
    {
        return Address1;
    }

    public void setAddress1(String Address1)
    {
        this.Address1 = Address1;
    }

    public String getAddress2()
    {
        return Address2;
    }

    public void setAddress2(String Address2)
    {
        this.Address2 = Address2;
    }

    public String getCity()
    {
        return City;
    }

    public void setCity(String City)
    {
        this.City = City;
    }

    public String getExternalId()
    {
        return ExternalId;
    }

    public void setExternalId(String ExternalId)
    {
        this.ExternalId = ExternalId;
    }

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public Long getNPI()
    {
        return NPI;
    }

    public void setNPI(Long NPI)
    {
        this.NPI = NPI;
    }

    public int getNumber()
    {
        return Number;
    }

    public void setNumber(int Number)
    {
        this.Number = Number;
    }

    public String getState()
    {
        return State;
    }

    public void setState(String State)
    {
        this.State = State;
    }

    public String getUPIN()
    {
        return UPIN;
    }

    public void setUPIN(String UPIN)
    {
        this.UPIN = UPIN;
    }

    public String getZip()
    {
        return Zip;
    }

    public void setZip(String Zip)
    {
        this.Zip = Zip;
    }
    
    public String getCrossRef()
    {
        return CrossRef;
    }

    public void setCrossRef(String CrossRef)
    {
        this.CrossRef = CrossRef;
    }

    public String getPhone()
    {
        return Phone;
    }

    public void setPhone(String Phone)
    {
        this.Phone = Phone;
    }

    public String getFax()
    {
        return Fax;
    }

    public void setFax(String Fax)
    {
        this.Fax = Fax;
    }

    public String getEmail()
    {
        return Email;
    }

    public void setEmail(String Email)
    {
        this.Email = Email;
    }
    
    
}
