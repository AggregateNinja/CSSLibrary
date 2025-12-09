package EPOS;

/**
 * @date:   Mar 12, 2012
 * @author: Keith
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: DetailOrderLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

import java.util.ArrayList;
import java.util.Date;

public class DetailOrderLine
{
    static public class Detail
    {
        private int TestNum;
        private int SubTestNum;
        private float AmountOfBill;
        private float AmountPaid;
        private float AmountApproved;
        private float WriteOffAmount;
        private int ProceedureCode;
        private int ProcModifier;
        private char ProcLetter;
        private char JournalCode;
        private int NumServices;
        private String PlaceOfService;
        private String DiagnosisCode;
        private String EmployAccidentOther;
        private Date DateBilled;
        private Date DatePayment;
        private String Comment;

        public Detail()
        {
        }

        public Detail(int TestNum, int SubTestNum, float AmountOfBill, float AmountPaid, float AmountApproved, float WriteOffAmount, int ProceedureCode, int ProcModifier, char ProcLetter, char JournalCode, int NumServices, String PlaceOfService, String DiagnosisCode, String EmployAccidentOther, Date DateBilled, Date DatePayment, String Comment)
        {
            this.TestNum = TestNum;
            this.SubTestNum = SubTestNum;
            this.AmountOfBill = AmountOfBill;
            this.AmountPaid = AmountPaid;
            this.AmountApproved = AmountApproved;
            this.WriteOffAmount = WriteOffAmount;
            this.ProceedureCode = ProceedureCode;
            this.ProcModifier = ProcModifier;
            this.ProcLetter = ProcLetter;
            this.JournalCode = JournalCode;
            this.NumServices = NumServices;
            this.PlaceOfService = PlaceOfService;
            this.DiagnosisCode = DiagnosisCode;
            this.EmployAccidentOther = EmployAccidentOther;
            this.DateBilled = DateBilled;
            this.DatePayment = DatePayment;
            this.Comment = Comment;
        }

        public float getAmountApproved()
        {
            return AmountApproved;
        }

        public void setAmountApproved(float AmountApproved)
        {
            this.AmountApproved = AmountApproved;
        }

        public float getAmountOfBill()
        {
            return AmountOfBill;
        }

        public void setAmountOfBill(float AmountOfBill)
        {
            this.AmountOfBill = AmountOfBill;
        }

        public float getAmountPaid()
        {
            return AmountPaid;
        }

        public void setAmountPaid(float AmountPaid)
        {
            this.AmountPaid = AmountPaid;
        }

        public String getComment()
        {
            return Comment;
        }

        public void setComment(String Comment)
        {
            this.Comment = Comment;
        }

        public Date getDateBilled()
        {
            return DateBilled;
        }

        public void setDateBilled(Date DateBilled)
        {
            this.DateBilled = DateBilled;
        }

        public Date getDatePayment()
        {
            return DatePayment;
        }

        public void setDatePayment(Date DatePayment)
        {
            this.DatePayment = DatePayment;
        }

        public String getDiagnosisCode()
        {
            return DiagnosisCode;
        }

        public void setDiagnosisCode(String DiagnosisCode)
        {
            this.DiagnosisCode = DiagnosisCode;
        }

        public String getEmployAccidentOther()
        {
            return EmployAccidentOther;
        }

        public void setEmployAccidentOther(String EmployAccidentOther)
        {
            this.EmployAccidentOther = EmployAccidentOther;
        }

        public char getJournalCode()
        {
            return JournalCode;
        }

        public void setJournalCode(char JournalCode)
        {
            this.JournalCode = JournalCode;
        }

        public int getNumServices()
        {
            return NumServices;
        }

        public void setNumServices(int NumServices)
        {
            this.NumServices = NumServices;
        }

        public String getPlaceOfService()
        {
            return PlaceOfService;
        }

        public void setPlaceOfService(String PlaceOfService)
        {
            this.PlaceOfService = PlaceOfService;
        }

        public char getProcLetter()
        {
            return ProcLetter;
        }

        public void setProcLetter(char ProcLetter)
        {
            this.ProcLetter = ProcLetter;
        }

        public int getProcModifier()
        {
            return ProcModifier;
        }

        public void setProcModifier(int ProcModifier)
        {
            this.ProcModifier = ProcModifier;
        }

        public int getProceedureCode()
        {
            return ProceedureCode;
        }

        public void setProceedureCode(int ProceedureCode)
        {
            this.ProceedureCode = ProceedureCode;
        }

        public int getSubTestNum()
        {
            return SubTestNum;
        }

        public void setSubTestNum(int SubTestNum)
        {
            this.SubTestNum = SubTestNum;
        }

        public int getTestNum()
        {
            return TestNum;
        }

        public void setTestNum(int TestNum)
        {
            this.TestNum = TestNum;
        }

        public float getWriteOffAmount()
        {
            return WriteOffAmount;
        }

        public void setWriteOffAmount(float WriteOffAmount)
        {
            this.WriteOffAmount = WriteOffAmount;
        }
        
        
    }
    
    private String ArNumber;
    private String Accession;
    private int Insurance;
    private ArrayList<Detail> Details = new ArrayList<Detail>();
    private int ClientNumber;
    private int DoctorNumber;
    private int LocationNumber;
    private Date DateOfService;

    public DetailOrderLine()
    {
    }

    public DetailOrderLine(String ArNumber, String Accession, int Insurance, ArrayList<Detail> Details, int ClientNumber, int DoctorNumber, int LocationNumber, Date DateOfService)
    {
        this.ArNumber = ArNumber;
        this.Accession = Accession;
        this.Insurance = Insurance;
        this.Details = Details;
        this.ClientNumber = ClientNumber;
        this.DoctorNumber = DoctorNumber;
        this.LocationNumber = LocationNumber;
        this.DateOfService = DateOfService;
    }

    public String getAccession()
    {
        return Accession;
    }

    public void setAccession(String Accession)
    {
        this.Accession = Accession;
    }

    public String getArNumber()
    {
        return ArNumber;
    }

    public void setArNumber(String ArNumber)
    {
        this.ArNumber = ArNumber;
    }

    public int getClientNumber()
    {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber)
    {
        this.ClientNumber = ClientNumber;
    }

    public ArrayList<Detail> getDetails()
    {
        return Details;
    }

    public void setDetails(ArrayList<Detail> Details)
    {
        this.Details = Details;
    }

    public int getDoctorNumber()
    {
        return DoctorNumber;
    }

    public void setDoctorNumber(int DoctorNumber)
    {
        this.DoctorNumber = DoctorNumber;
    }

    public int getInsurance()
    {
        return Insurance;
    }

    public void setInsurance(int Insurance)
    {
        this.Insurance = Insurance;
    }

    public int getLocationNumber()
    {
        return LocationNumber;
    }

    public void setLocationNumber(int LocationNumber)
    {
        this.LocationNumber = LocationNumber;
    }

    public Date getDateOfService()
    {
        return DateOfService;
    }

    public void setDateOfService(Date DateOfService)
    {
        this.DateOfService = DateOfService;
    }
    
    
}
