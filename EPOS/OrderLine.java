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

public class OrderLine
{
    private String Accession;
    private int ClientID;
    private int DoctorID;
    private int LocationID;
    private Date OrderDate;
    private Date SpecimenDate;
    private String PatientID;
    private String WorksheetComment;
    private String ReportedComment;
    private int Insurance;
    private int SecondaryInsurance;
    private String Medicare;
    private String Medicaid1;
    private String Medicaid2;
    private String Agreement1;
    private String Agreement2;
    private String Group1;
    private String Group2;
    private Boolean NoCharge;
    private Boolean History;
    private Boolean Billed;
    private Boolean Billable;
    private Boolean BillOnly;
    
    public OrderLine()
    {
    
    }
    
    public OrderLine(String Accession,
                     int ClientID,
                     int DoctorID,
                     int LocationID,
                     Date OrderDate,
                     Date SpecimenDate,
                     String PatientID)
    {
        this.Accession = Accession;
        this.ClientID = ClientID;
        this.DoctorID = DoctorID;        
        this.LocationID = LocationID;
        this.OrderDate = OrderDate;
        this.SpecimenDate = SpecimenDate;
        this.PatientID = PatientID;

    }

    public String getAccession()
    {
        return Accession;
    }

    public void setAccession(String accession)
    {
        this.Accession = accession;
    }

    public int getClientID()
    {
        return ClientID;
    }

    public void setClientID(int clientid)
    {
        this.ClientID = clientid;
    }

    public int getDoctorID()
    {
        return DoctorID;
    }

    public void setDoctorID(int doctorid)
    {
        this.DoctorID = doctorid;
    }

    public int getLocationID()
    {
        return LocationID;
    }

    public void setLocationID(int locationid)
    {
        this.LocationID = locationid;
    }

    public Date getOrderDate()
    {
        return OrderDate;
    }

    public void setOrderDate(Date orderdate)
    {
        this.OrderDate = orderdate;
    }

    public String getPatientID()
    {
        return PatientID;
    }

    public void setPatientID(String patientid)
    {
        this.PatientID = patientid;
    }

    public Date getSpecimenDate()
    {
        return SpecimenDate;
    }

    public void setSpecimenDate(Date specimendate)
    {
        this.SpecimenDate = specimendate;
    }

    public String getWorksheetComment()
    {
        return WorksheetComment;
    }

    public void setWorksheetComment(String WorksheetComment)
    {
        this.WorksheetComment = WorksheetComment;
    }

    public String getReportedComment()
    {
        return ReportedComment;
    }

    public void setReportedComment(String ReportedComment)
    {
        this.ReportedComment = ReportedComment;
    }

    public int getInsurance()
    {
        return Insurance;
    }

    public void setInsurance(int Insurance)
    {
        this.Insurance = Insurance;
    }

    public int getSecondaryInsurance()
    {
        return SecondaryInsurance;
    }

    public void setSecondaryInsurance(int SecondaryInsurance)
    {
        this.SecondaryInsurance = SecondaryInsurance;
    }

    public String getMedicare()
    {
        return Medicare;
    }

    public void setMedicare(String Medicare)
    {
        this.Medicare = Medicare;
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

    public Boolean getNoCharge()
    {
        return NoCharge;
    }

    public void setNoCharge(Boolean NoCharge)
    {
        this.NoCharge = NoCharge;
    }

    public Boolean getHistory()
    {
        return History;
    }

    public void setHistory(Boolean History)
    {
        this.History = History;
    }

    public Boolean getBilled()
    {
        return Billed;
    }

    public void setBilled(Boolean Billed)
    {
        this.Billed = Billed;
    }

    public Boolean getBillable()
    {
        return Billable;
    }

    public void setBillable(Boolean Billable)
    {
        this.Billable = Billable;
    }

    public Boolean getBillOnly()
    {
        return BillOnly;
    }

    public void setBillOnly(Boolean BillOnly)
    {
        this.BillOnly = BillOnly;
    }
    
    
}
