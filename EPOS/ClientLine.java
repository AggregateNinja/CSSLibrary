package EPOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: OrderLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import java.util.Date;

public class ClientLine
{
    int ClientNumber;
    String ClientName;
    String SortSequence;
    String ExternalId;
    String LicenseNumber;
    String ClientUPIN;
    String ClientOtherNumber;
    String ClientNPINumber;
    String ExtendedClientName;
    String Address1;
    String Address2;
    String CityStateZip;
    String ClientCountry;
    String Locality;
    String OtherName;
    String OtherClientName;
    String OtherAddress;
    String OtherCityStateZip;
    String OtherCountry;
    String OtherLocality;
    String ContactName;
    String Phone;
    String FAX;
    String Transmit;
    float BalanceForward;
    float CurrentBalance;
    float ThirtyDayBalance;
    float SixtyDayBalance;
    float NinetyDayBalance;
    float OverNinetyDayBalance;
    int MonthTests[] = new int[8];
    float MonthAmounts[] = new float[8];
    int YearTests[] = new int[8];
    float YearAmounts[] = new float[8];
    int LifeTests[] = new int[8];
    float LifeAmounts[] = new float[8];
    char DiscountType;
    int InHouseDiscount;
    float VolumeForDiscount;
    int ReferenceLabDiscount;
    int CustomTests[] = new int[6];
    int AutoTests[] = new int[6];
    Date AuthorizedStartDate;
    Date AuthorizedEndDate;
    int AuthorizedTests[] = new int[16];
    char ClientFeeSchedule;
    char ClientTransmission;
    char StatisticType;
    char BillingType;
    String Currency;
    int ClientStop;
    int ClientPickedTime;
    char SaturdayPickup;
    char ResultPrint;
    int SalesmanCode;
    int NumPerDegreeDifference;
    int PerForDegreeDiff[] = new int[9];
    int ClientCopies;
    int Route;
    String PickupDays;
    int Location;
    int HL7Enabled;
    String Password;
    String DaysOpen;
    int FreezerTime;
    int WebFlag;
    String ClientComment;
    int CreditTerms;
    String Email1;
    String Email2;
    int ResultReport[] = new int[3];

    public ClientLine()
    {
    }

    public ClientLine(int ClientNumber, String ClientName, String SortSequence, String ExternalId, String LicenseNumber, String ClientUPIN, String ClientOtherNumber, String ClientNPINumber, String ExtendedClientName, String Address1, String Address2, String CityStateZip, String ClientCountry, String Locality, String OtherName, String OtherClientName, String OtherAddress, String OtherCityStateZip, String OtherCountry, String OtherLocality, String ContactName, String Phone, String FAX, String Transmit, float BalanceForward, float CurrentBalance, float ThirtyDayBalance, float SixtyDayBalance, float NinetyDayBalance, float OverNinetyDayBalance, char DiscountType, int InHouseDiscount, float VolumeForDiscount, int ReferenceLabDiscount, Date AuthorizedStartDate, Date AuthorizedEndDate, char ClientFeeSchedule, char ClientTransmission, char StatisticType, char BillingType, String Currency, int ClientStop, int ClientPickedTime, char SaturdayPickup, char ResultPrint, int SalesmanCode, int NumPerDegreeDifference, int ClientCopies, int Route, String PickupDays, int Location, int HL7Enabled, String Password, String DaysOpen, int FreezerTime, int WebFlag, String ClientComment, int CreditTerms, String Email1, String Email2)
    {
        this.ClientNumber = ClientNumber;
        this.ClientName = ClientName;
        this.SortSequence = SortSequence;
        this.ExternalId = ExternalId;
        this.LicenseNumber = LicenseNumber;
        this.ClientUPIN = ClientUPIN;
        this.ClientOtherNumber = ClientOtherNumber;
        this.ClientNPINumber = ClientNPINumber;
        this.ExtendedClientName = ExtendedClientName;
        this.Address1 = Address1;
        this.Address2 = Address2;
        this.CityStateZip = CityStateZip;
        this.ClientCountry = ClientCountry;
        this.Locality = Locality;
        this.OtherName = OtherName;
        this.OtherClientName = OtherClientName;
        this.OtherAddress = OtherAddress;
        this.OtherCityStateZip = OtherCityStateZip;
        this.OtherCountry = OtherCountry;
        this.OtherLocality = OtherLocality;
        this.ContactName = ContactName;
        this.Phone = Phone;
        this.FAX = FAX;
        this.Transmit = Transmit;
        this.BalanceForward = BalanceForward;
        this.CurrentBalance = CurrentBalance;
        this.ThirtyDayBalance = ThirtyDayBalance;
        this.SixtyDayBalance = SixtyDayBalance;
        this.NinetyDayBalance = NinetyDayBalance;
        this.OverNinetyDayBalance = OverNinetyDayBalance;
        this.DiscountType = DiscountType;
        this.InHouseDiscount = InHouseDiscount;
        this.VolumeForDiscount = VolumeForDiscount;
        this.ReferenceLabDiscount = ReferenceLabDiscount;
        this.AuthorizedStartDate = AuthorizedStartDate;
        this.AuthorizedEndDate = AuthorizedEndDate;
        this.ClientFeeSchedule = ClientFeeSchedule;
        this.ClientTransmission = ClientTransmission;
        this.StatisticType = StatisticType;
        this.BillingType = BillingType;
        this.Currency = Currency;
        this.ClientStop = ClientStop;
        this.ClientPickedTime = ClientPickedTime;
        this.SaturdayPickup = SaturdayPickup;
        this.ResultPrint = ResultPrint;
        this.SalesmanCode = SalesmanCode;
        this.NumPerDegreeDifference = NumPerDegreeDifference;
        this.ClientCopies = ClientCopies;
        this.Route = Route;
        this.PickupDays = PickupDays;
        this.Location = Location;
        this.HL7Enabled = HL7Enabled;
        this.Password = Password;
        this.DaysOpen = DaysOpen;
        this.FreezerTime = FreezerTime;
        this.WebFlag = WebFlag;
        this.ClientComment = ClientComment;
        this.CreditTerms = CreditTerms;
        this.Email1 = Email1;
        this.Email2 = Email2;
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

    public Date getAuthorizedEndDate()
    {
        return AuthorizedEndDate;
    }

    public void setAuthorizedEndDate(Date AuthorizedEndDate)
    {
        this.AuthorizedEndDate = AuthorizedEndDate;
    }

    public Date getAuthorizedStartDate()
    {
        return AuthorizedStartDate;
    }

    public void setAuthorizedStartDate(Date AuthorizedStartDate)
    {
        this.AuthorizedStartDate = AuthorizedStartDate;
    }

    public int[] getAuthorizedTests()
    {
        return AuthorizedTests;
    }

    public void setAuthorizedTests(int[] AuthorizedTests)
    {
        this.AuthorizedTests = AuthorizedTests;
    }

    public int[] getAutoTests()
    {
        return AutoTests;
    }

    public void setAutoTests(int[] AutoTests)
    {
        this.AutoTests = AutoTests;
    }

    public float getBalanceForward()
    {
        return BalanceForward;
    }

    public void setBalanceForward(float BalanceForward)
    {
        this.BalanceForward = BalanceForward;
    }

    public char getBillingType()
    {
        return BillingType;
    }

    public void setBillingType(char BillingType)
    {
        this.BillingType = BillingType;
    }

    public String getCityStateZip()
    {
        return CityStateZip;
    }

    public void setCityStateZip(String CityStateZip)
    {
        this.CityStateZip = CityStateZip;
    }

    public String getClientComment()
    {
        return ClientComment;
    }

    public void setClientComment(String ClientComment)
    {
        this.ClientComment = ClientComment;
    }

    public int getClientCopies()
    {
        return ClientCopies;
    }

    public void setClientCopies(int ClientCopies)
    {
        this.ClientCopies = ClientCopies;
    }

    public String getClientCountry()
    {
        return ClientCountry;
    }

    public void setClientCountry(String ClientCountry)
    {
        this.ClientCountry = ClientCountry;
    }

    public char getClientFeeSchedule()
    {
        return ClientFeeSchedule;
    }

    public void setClientFeeSchedule(char ClientFeeSchedule)
    {
        this.ClientFeeSchedule = ClientFeeSchedule;
    }

    public String getClientNPINumber()
    {
        return ClientNPINumber;
    }

    public void setClientNPINumber(String ClientNPINumber)
    {
        this.ClientNPINumber = ClientNPINumber;
    }

    public String getClientName()
    {
        return ClientName;
    }

    public void setClientName(String ClientName)
    {
        this.ClientName = ClientName;
    }

    public int getClientNumber()
    {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber)
    {
        this.ClientNumber = ClientNumber;
    }

    public String getClientOtherNumber()
    {
        return ClientOtherNumber;
    }

    public void setClientOtherNumber(String ClientOtherNumber)
    {
        this.ClientOtherNumber = ClientOtherNumber;
    }

    public int getClientPickedTime()
    {
        return ClientPickedTime;
    }

    public void setClientPickedTime(int ClientPickedTime)
    {
        this.ClientPickedTime = ClientPickedTime;
    }

    public int getClientStop()
    {
        return ClientStop;
    }

    public void setClientStop(int ClientStop)
    {
        this.ClientStop = ClientStop;
    }

    public char getClientTransmission()
    {
        return ClientTransmission;
    }

    public void setClientTransmission(char ClientTransmission)
    {
        this.ClientTransmission = ClientTransmission;
    }

    public String getClientUPIN()
    {
        return ClientUPIN;
    }

    public void setClientUPIN(String ClientUPIN)
    {
        this.ClientUPIN = ClientUPIN;
    }

    public String getContactName()
    {
        return ContactName;
    }

    public void setContactName(String ContactName)
    {
        this.ContactName = ContactName;
    }

    public int getCreditTerms()
    {
        return CreditTerms;
    }

    public void setCreditTerms(int CreditTerms)
    {
        this.CreditTerms = CreditTerms;
    }

    public String getCurrency()
    {
        return Currency;
    }

    public void setCurrency(String Currency)
    {
        this.Currency = Currency;
    }

    public float getCurrentBalance()
    {
        return CurrentBalance;
    }

    public void setCurrentBalance(float CurrentBalance)
    {
        this.CurrentBalance = CurrentBalance;
    }

    public int[] getCustomTests()
    {
        return CustomTests;
    }

    public void setCustomTests(int[] CustomTests)
    {
        this.CustomTests = CustomTests;
    }

    public String getDaysOpen()
    {
        return DaysOpen;
    }

    public void setDaysOpen(String DaysOpen)
    {
        this.DaysOpen = DaysOpen;
    }

    public char getDiscountType()
    {
        return DiscountType;
    }

    public void setDiscountType(char DiscountType)
    {
        this.DiscountType = DiscountType;
    }

    public String getEmail1()
    {
        return Email1;
    }

    public void setEmail1(String Email1)
    {
        this.Email1 = Email1;
    }

    public String getEmail2()
    {
        return Email2;
    }

    public void setEmail2(String Email2)
    {
        this.Email2 = Email2;
    }

    public String getExtendedClientName()
    {
        return ExtendedClientName;
    }

    public void setExtendedClientName(String ExtendedClientName)
    {
        this.ExtendedClientName = ExtendedClientName;
    }

    public String getExternalId()
    {
        return ExternalId;
    }

    public void setExternalId(String ExternalId)
    {
        this.ExternalId = ExternalId;
    }

    public String getFAX()
    {
        return FAX;
    }

    public void setFAX(String FAX)
    {
        this.FAX = FAX;
    }

    public int getFreezerTime()
    {
        return FreezerTime;
    }

    public void setFreezerTime(int FreezerTime)
    {
        this.FreezerTime = FreezerTime;
    }

    public int getHL7Enabled()
    {
        return HL7Enabled;
    }

    public void setHL7Enabled(int HL7Enabled)
    {
        this.HL7Enabled = HL7Enabled;
    }

    public int getInHouseDiscount()
    {
        return InHouseDiscount;
    }

    public void setInHouseDiscount(int InHouseDiscount)
    {
        this.InHouseDiscount = InHouseDiscount;
    }

    public String getLicenseNumber()
    {
        return LicenseNumber;
    }

    public void setLicenseNumber(String LicenseNumber)
    {
        this.LicenseNumber = LicenseNumber;
    }

    public float[] getLifeAmounts()
    {
        return LifeAmounts;
    }

    public void setLifeAmounts(float[] LifeAmounts)
    {
        this.LifeAmounts = LifeAmounts;
    }

    public int[] getLifeTests()
    {
        return LifeTests;
    }

    public void setLifeTests(int[] LifeTests)
    {
        this.LifeTests = LifeTests;
    }

    public String getLocality()
    {
        return Locality;
    }

    public void setLocality(String Locality)
    {
        this.Locality = Locality;
    }

    public int getLocation()
    {
        return Location;
    }

    public void setLocation(int Location)
    {
        this.Location = Location;
    }

    public float[] getMonthAmounts()
    {
        return MonthAmounts;
    }

    public void setMonthAmounts(float[] MonthAmounts)
    {
        this.MonthAmounts = MonthAmounts;
    }

    public int[] getMonthTests()
    {
        return MonthTests;
    }

    public void setMonthTests(int[] MonthTests)
    {
        this.MonthTests = MonthTests;
    }

    public float getNinetyDayBalance()
    {
        return NinetyDayBalance;
    }

    public void setNinetyDayBalance(float NinetyDayBalance)
    {
        this.NinetyDayBalance = NinetyDayBalance;
    }

    public int getNumPerDegreeDifference()
    {
        return NumPerDegreeDifference;
    }

    public void setNumPerDegreeDifference(int NumPerDegreeDifference)
    {
        this.NumPerDegreeDifference = NumPerDegreeDifference;
    }

    public String getOtherAddress()
    {
        return OtherAddress;
    }

    public void setOtherAddress(String OtherAddress)
    {
        this.OtherAddress = OtherAddress;
    }

    public String getOtherCityStateZip()
    {
        return OtherCityStateZip;
    }

    public void setOtherCityStateZip(String OtherCityStateZip)
    {
        this.OtherCityStateZip = OtherCityStateZip;
    }

    public String getOtherClientName()
    {
        return OtherClientName;
    }

    public void setOtherClientName(String OtherClientName)
    {
        this.OtherClientName = OtherClientName;
    }

    public String getOtherCountry()
    {
        return OtherCountry;
    }

    public void setOtherCountry(String OtherCountry)
    {
        this.OtherCountry = OtherCountry;
    }

    public String getOtherLocality()
    {
        return OtherLocality;
    }

    public void setOtherLocality(String OtherLocality)
    {
        this.OtherLocality = OtherLocality;
    }

    public String getOtherName()
    {
        return OtherName;
    }

    public void setOtherName(String OtherName)
    {
        this.OtherName = OtherName;
    }

    public float getOverNinetyDayBalance()
    {
        return OverNinetyDayBalance;
    }

    public void setOverNinetyDayBalance(float OverNinetyDayBalance)
    {
        this.OverNinetyDayBalance = OverNinetyDayBalance;
    }

    public String getPassword()
    {
        return Password;
    }

    public void setPassword(String Password)
    {
        this.Password = Password;
    }

    public int[] getPerForDegreeDiff()
    {
        return PerForDegreeDiff;
    }

    public void setPerForDegreeDiff(int[] PerForDegreeDiff)
    {
        this.PerForDegreeDiff = PerForDegreeDiff;
    }

    public String getPhone()
    {
        return Phone;
    }

    public void setPhone(String Phone)
    {
        this.Phone = Phone;
    }

    public String getPickupDays()
    {
        return PickupDays;
    }

    public void setPickupDays(String PickupDays)
    {
        this.PickupDays = PickupDays;
    }

    public int getReferenceLabDiscount()
    {
        return ReferenceLabDiscount;
    }

    public void setReferenceLabDiscount(int ReferenceLabDiscount)
    {
        this.ReferenceLabDiscount = ReferenceLabDiscount;
    }

    public char getResultPrint()
    {
        return ResultPrint;
    }

    public void setResultPrint(char ResultPrint)
    {
        this.ResultPrint = ResultPrint;
    }

    public int[] getResultReport()
    {
        return ResultReport;
    }

    public void setResultReport(int[] ResultReport)
    {
        this.ResultReport = ResultReport;
    }

    public int getRoute()
    {
        return Route;
    }

    public void setRoute(int Route)
    {
        this.Route = Route;
    }

    public int getSalesmanCode()
    {
        return SalesmanCode;
    }

    public void setSalesmanCode(int SalesmanCode)
    {
        this.SalesmanCode = SalesmanCode;
    }

    public char getSaturdayPickup()
    {
        return SaturdayPickup;
    }

    public void setSaturdayPickup(char SaturdayPickup)
    {
        this.SaturdayPickup = SaturdayPickup;
    }

    public float getSixtyDayBalance()
    {
        return SixtyDayBalance;
    }

    public void setSixtyDayBalance(float SixtyDayBalance)
    {
        this.SixtyDayBalance = SixtyDayBalance;
    }

    public String getSortSequence()
    {
        return SortSequence;
    }

    public void setSortSequence(String SortSequence)
    {
        this.SortSequence = SortSequence;
    }

    public char getStatisticType()
    {
        return StatisticType;
    }

    public void setStatisticType(char StatisticType)
    {
        this.StatisticType = StatisticType;
    }

    public float getThirtyDayBalance()
    {
        return ThirtyDayBalance;
    }

    public void setThirtyDayBalance(float ThirtyDayBalance)
    {
        this.ThirtyDayBalance = ThirtyDayBalance;
    }

    public String getTransmit()
    {
        return Transmit;
    }

    public void setTransmit(String Transmit)
    {
        this.Transmit = Transmit;
    }

    public float getVolumeForDiscount()
    {
        return VolumeForDiscount;
    }

    public void setVolumeForDiscount(float VolumeForDiscount)
    {
        this.VolumeForDiscount = VolumeForDiscount;
    }

    public int getWebFlag()
    {
        return WebFlag;
    }

    public void setWebFlag(int WebFlag)
    {
        this.WebFlag = WebFlag;
    }

    public float[] getYearAmounts()
    {
        return YearAmounts;
    }

    public void setYearAmounts(float[] YearAmounts)
    {
        this.YearAmounts = YearAmounts;
    }

    public int[] getYearTests()
    {
        return YearTests;
    }

    public void setYearTests(int[] YearTests)
    {
        this.YearTests = YearTests;
    }
    
    
}
