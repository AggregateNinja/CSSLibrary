package EPOS;

import java.util.Date;

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

public class SubscriberLine 
{
    private int MasterNumber;
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
    private int InsuranceNumber;
    private int SecondaryInsNumber;
    private String Medicare;
    private String Medicaid1;
    private String Medicaid2;
    private String Agreement1;
    private String Agreement2;
    private String Group1;
    private String Group2;
    

    public SubscriberLine()
    {
    }

    public SubscriberLine(int MasterNumber, String LastName, String FirstName, char MiddleInitial, char PatientSex, int SocialSecurityNumber, Date DateofBirth, String Address1, String Address2, String City, String State, int ZipCode, String HomePhone, int InsuranceNumber, int SecondaryInsNumber, String Medicare, String Medicaid1, String Medicaid2, String Agreement1, String Agreement2, String Group1, String Group2)
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
        this.InsuranceNumber = InsuranceNumber;
        this.SecondaryInsNumber = SecondaryInsNumber;
        this.Medicare = Medicare;
        this.Medicaid1 = Medicaid1;
        this.Medicaid2 = Medicaid2;
        this.Agreement1 = Agreement1;
        this.Agreement2 = Agreement2;
        this.Group1 = Group1;
        this.Group2 = Group2;
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

    public String getAgreement1()
    {
        return Agreement1;
    }

    public void setAgreement1(String Agreement1)
    {
        this.Agreement1 = Agreement1;
    }

    public String getAgreement2()
    {
        return Agreement2;
    }

    public void setAgreement2(String Agreement2)
    {
        this.Agreement2 = Agreement2;
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

    public String getFirstName()
    {
        return FirstName;
    }

    public void setFirstName(String FirstName)
    {
        this.FirstName = FirstName;
    }

    public String getGroup1()
    {
        return Group1;
    }

    public void setGroup1(String Group1)
    {
        this.Group1 = Group1;
    }

    public String getGroup2()
    {
        return Group2;
    }

    public void setGroup2(String Group2)
    {
        this.Group2 = Group2;
    }

    public String getHomePhone()
    {
        return HomePhone;
    }

    public void setHomePhone(String HomePhone)
    {
        this.HomePhone = HomePhone;
    }

    public int getInsuranceNumber()
    {
        return InsuranceNumber;
    }

    public void setInsuranceNumber(int InsuranceNumber)
    {
        this.InsuranceNumber = InsuranceNumber;
    }

    public String getLastName()
    {
        return LastName;
    }

    public void setLastName(String LastName)
    {
        this.LastName = LastName;
    }

    public int getMasterNumber()
    {
        return MasterNumber;
    }

    public void setMasterNumber(int MasterNumber)
    {
        this.MasterNumber = MasterNumber;
    }

    public String getMedicaid1()
    {
        return Medicaid1;
    }

    public void setMedicaid1(String Medicaid1)
    {
        this.Medicaid1 = Medicaid1;
    }

    public String getMedicaid2()
    {
        return Medicaid2;
    }

    public void setMedicaid2(String Medicaid2)
    {
        this.Medicaid2 = Medicaid2;
    }

    public String getMedicare()
    {
        return Medicare;
    }

    public void setMedicare(String Medicare)
    {
        this.Medicare = Medicare;
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

    public int getSecondaryInsNumber()
    {
        return SecondaryInsNumber;
    }

    public void setSecondaryInsNumber(int SecondaryInsNumber)
    {
        this.SecondaryInsNumber = SecondaryInsNumber;
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

    public int getZipCode()
    {
        return ZipCode;
    }

    public void setZipCode(int ZipCode)
    {
        this.ZipCode = ZipCode;
    }

    
}
