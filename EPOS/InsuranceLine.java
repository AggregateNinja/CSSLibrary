package EPOS;

/**
 * @date:   Mar 13, 2012
 * @author: Keith Maggio
 * 
 * @project: CSS Library 
 * @package: EPOS
 * @file name: InsuranceLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

public class InsuranceLine 
{
    int Number;
    String Abbreviation;
    String Name;
    String ExtraName;
    String Address1;
    String Address2;
    String City;
    String State;
    String PayorId;
    String Zip;
    String FeeType;
    int ProcedureCode;
    String[] BillingCodes = new String[20];
    String Password;
    String ExtraService;
    String Type;
    String Active;
    String Phone;
    String PaySource;
    String InsuranceType;
    boolean InternalUseOnly;
    String ReceiverId;
    String TransmitInsNumber;
    String CommercialInsurance;
    String RedForm;
    String AcceptAssignment;
    String SourceId1;
    String SourceId2;
    String DoctorId;
    int NumericType;
    int Id;

    public InsuranceLine()
    {
    }

    public InsuranceLine(int Number, String Abbreviation, String Name, String ExtraName, String Address1, String Address2, String City, String State, String PayorId, String Zip, String FeeType, int ProcedureCode, String Password, String ExtraService, String Type, String Active, String Phone, String PaySource, String InsuranceType, boolean InternalUseOnly, String ReceiverId, String TransmitInsNumber, String CommercialInsurance, String RedForm, String AcceptAssignment, String SourceId1, String SourceId2, String DoctorId, int NumericType, int Id)
    {
        this.Number = Number;
        this.Abbreviation = Abbreviation;
        this.Name = Name;
        this.ExtraName = ExtraName;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.City = City;
        this.State = State;
        this.PayorId = PayorId;
        this.Zip = Zip;
        this.FeeType = FeeType;
        this.ProcedureCode = ProcedureCode;
        this.Password = Password;
        this.ExtraService = ExtraService;
        this.Type = Type;
        this.Active = Active;
        this.Phone = Phone;
        this.PaySource = PaySource;
        this.InsuranceType = InsuranceType;
        this.InternalUseOnly = InternalUseOnly;
        this.ReceiverId = ReceiverId;
        this.TransmitInsNumber = TransmitInsNumber;
        this.CommercialInsurance = CommercialInsurance;
        this.RedForm = RedForm;
        this.AcceptAssignment = AcceptAssignment;
        this.SourceId1 = SourceId1;
        this.SourceId2 = SourceId2;
        this.DoctorId = DoctorId;
        this.NumericType = NumericType;
        this.Id = Id;
    }

    public String getAbbreviation()
    {
        return Abbreviation;
    }

    public void setAbbreviation(String Abbreviation)
    {
        this.Abbreviation = Abbreviation;
    }

    public String getAcceptAssignment()
    {
        return AcceptAssignment;
    }

    public void setAcceptAssignment(String AcceptAssignment)
    {
        this.AcceptAssignment = AcceptAssignment;
    }

    public String getActive()
    {
        return Active;
    }

    public void setActive(String Active)
    {
        this.Active = Active;
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

    public String[] getBillingCodes()
    {
        return BillingCodes;
    }

    public void setBillingCodes(String[] BillingCodes)
    {
        this.BillingCodes = BillingCodes;
    }

    public String getCity()
    {
        return City;
    }

    public void setCity(String City)
    {
        this.City = City;
    }

    public String getCommercialInsurance()
    {
        return CommercialInsurance;
    }

    public void setCommercialInsurance(String CommercialInsurance)
    {
        this.CommercialInsurance = CommercialInsurance;
    }

    public String getDoctorId()
    {
        return DoctorId;
    }

    public void setDoctorId(String DoctorId)
    {
        this.DoctorId = DoctorId;
    }

    public String getExtraName()
    {
        return ExtraName;
    }

    public void setExtraName(String ExtraName)
    {
        this.ExtraName = ExtraName;
    }

    public String getExtraService()
    {
        return ExtraService;
    }

    public void setExtraService(String ExtraService)
    {
        this.ExtraService = ExtraService;
    }

    public String getFeeType()
    {
        return FeeType;
    }

    public void setFeeType(String FeeType)
    {
        this.FeeType = FeeType;
    }

    public int getId()
    {
        return Id;
    }

    public void setId(int Id)
    {
        this.Id = Id;
    }

    public String getInsuranceType()
    {
        return InsuranceType;
    }

    public void setInsuranceType(String InsuranceType)
    {
        this.InsuranceType = InsuranceType;
    }

    public boolean isInternalUseOnly()
    {
        return InternalUseOnly;
    }

    public void setInternalUseOnly(boolean InternalUseOnly)
    {
        this.InternalUseOnly = InternalUseOnly;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public int getNumber()
    {
        return Number;
    }

    public void setNumber(int Number)
    {
        this.Number = Number;
    }

    public int getNumericType()
    {
        return NumericType;
    }

    public void setNumericType(int NumericType)
    {
        this.NumericType = NumericType;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String Password)
    {
        this.Password = Password;
    }

    public String getPaySource()
    {
        return PaySource;
    }

    public void setPaySource(String PaySource)
    {
        this.PaySource = PaySource;
    }

    public String getPayorId()
    {
        return PayorId;
    }

    public void setPayorId(String PayorId)
    {
        this.PayorId = PayorId;
    }

    public String getPhone()
    {
        return Phone;
    }

    public void setPhone(String Phone)
    {
        this.Phone = Phone;
    }

    public int getProcedureCode()
    {
        return ProcedureCode;
    }

    public void setProcedureCode(int ProcedureCode)
    {
        this.ProcedureCode = ProcedureCode;
    }

    public String getReceiverId()
    {
        return ReceiverId;
    }

    public void setReceiverId(String ReceiverId)
    {
        this.ReceiverId = ReceiverId;
    }

    public String getRedForm()
    {
        return RedForm;
    }

    public void setRedForm(String RedForm)
    {
        this.RedForm = RedForm;
    }

    public String getSourceId1()
    {
        return SourceId1;
    }

    public void setSourceId1(String SourceId1)
    {
        this.SourceId1 = SourceId1;
    }

    public String getSourceId2()
    {
        return SourceId2;
    }

    public void setSourceId2(String SourceId2)
    {
        this.SourceId2 = SourceId2;
    }

    public String getState()
    {
        return State;
    }

    public void setState(String State)
    {
        this.State = State;
    }

    public String getTransmitInsNumber()
    {
        return TransmitInsNumber;
    }

    public void setTransmitInsNumber(String TransmitInsNumber)
    {
        this.TransmitInsNumber = TransmitInsNumber;
    }

    public String getType()
    {
        return Type;
    }

    public void setType(String Type)
    {
        this.Type = Type;
    }

    public String getZip()
    {
        return Zip;
    }

    public void setZip(String Zip)
    {
        this.Zip = Zip;
    }
    
    
}
