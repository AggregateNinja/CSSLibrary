package EPOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith Maggio
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: PatientLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import java.util.Date;

public class PatientLine 
{
    private String MasterNumber;
    private String LastName;
    private String FirstName;
    private char MiddleInitial;
    private char PatientSex;
    private int SocialSecurityNumber;
    private Date DateofBirth;
    //private String DateofBirth;
    private String Address1;
    private String Address2;
    private String City;
    private String State;
    private int ZipCode;
    private String HomePhone;
    private String SubscriberId;
    private String SubscriberRelationship;
    private int Counselor;
    private int species;


    public PatientLine()
    {
    }

    public PatientLine(String MasterNumber, 
                       String LastName, 
                       String FirstName, 
                       char MiddleInitial, 
                       char PatientSex, 
                       int SocialSecurityNumber, 
                       Date DateofBirth,
                       //String DateOfBirth,
                       String Address1, 
                       String Address2, 
                       String City, 
                       String State, 
                       int ZipCode, 
                       String HomePhone, 
                       String SubscriberId, 
                       String SubscriberRelationship,
                       int Counselor,
                       int species)
    {
        this.MasterNumber = MasterNumber;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.MiddleInitial = MiddleInitial;
        this.PatientSex = PatientSex;
        this.SocialSecurityNumber = SocialSecurityNumber;
        this.DateofBirth = DateofBirth;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.City = City;
        this.State = State;
        this.ZipCode = ZipCode;
        this.HomePhone = HomePhone;
        this.SubscriberId = SubscriberId;
        this.SubscriberRelationship = SubscriberRelationship;
        this.Counselor = Counselor;
        this.species = species;
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

    public Date getDateofBirth()
    {
        return DateofBirth;
    }

    public void setDateofBirth(Date DateofBirth)
    {
        this.DateofBirth = DateofBirth;
    }

    /*public String getDateofBirth()
    {
        return DateofBirth;
    }

    public void setDateofBirth(String DateofBirth)
    {
        this.DateofBirth = DateofBirth;
    }*/
    
    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getHomePhone()
    {
        return HomePhone;
    }

    public void setHomePhone(String HomePhone)
    {
        this.HomePhone = HomePhone;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public String getMasterNumber()
    {
        return MasterNumber;
    }

    public void setMasterNumber(String MasterNumber)
    {
        this.MasterNumber = MasterNumber;
    }

    public char getMiddleInitial()
    {
        return MiddleInitial;
    }

    public void setMiddleInitial(char MiddleInitial)
    {
        this.MiddleInitial = MiddleInitial;
    }

    public char getPatientSex()
    {
        return PatientSex;
    }

    public void setPatientSex(char PatientSex)
    {
        this.PatientSex = PatientSex;
    }

    public int getSocialSecurityNumber()
    {
        return SocialSecurityNumber;
    }

    public void setSocialSecurityNumber(int SocialSecurityNumber)
    {
        this.SocialSecurityNumber = SocialSecurityNumber;
    }

    public String getState()
    {
        return State;
    }

    public void setState(String State)
    {
        this.State = State;
    }

    public String getSubscriberId()
    {
        return SubscriberId;
    }

    public void setSubscriberId(String SubscriberId)
    {
        this.SubscriberId = SubscriberId;
    }

    public String getSubscriberRelationship()
    {
        return SubscriberRelationship;
    }

    public void setSubscriberRelationship(String SubscriberRelationship)
    {
        this.SubscriberRelationship = SubscriberRelationship;
    }

    public int getZipCode()
    {
        return ZipCode;
    }

    public void setZipCode(int ZipCode)
    {
        this.ZipCode = ZipCode;
    }
    
    public int getCounselor()
    {
        return Counselor;
    }

    public void setCounselor(int Counselor)
    {
        this.Counselor = Counselor;
    }

    public int getSpecies()
    {
        return species;
    }

    public void setSpecies(int species)
    {
        this.species = species;
    }

}
