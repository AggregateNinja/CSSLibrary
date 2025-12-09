

package EPOS;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/01/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class BulkDetailLine {
    
    public int ArNumber;
    public String Accession;
    public int Inusrance;
    public int TestNumber;
    public int SubTestNumber;
    public int ClientNumber;
    public int DoctorNumber;
    public BigDecimal AmountOfBill;
    public BigDecimal AmountPaid;
    public BigDecimal AmountApproved;
    public BigDecimal WriteOffAmount;
    public String ProcCode;
    public String ProcModifier;
    public String ProcLetter;
    public String JournalCode;
    public int NumberOfServices;
    public int PlaceOfService;
    public String DiagnosisCode;
    public String EmployAccidentOther;
    public int LocationNumber;
    public java.util.Date DateOfService;
    public java.util.Date DateBilled;
    public java.util.Date DatePayment;
    public String Comment;

    public BulkDetailLine() {
    }

    public BulkDetailLine(int ArNumber, String Accession, int Inusrance, int TestNumber, int SubTestNumber, int ClientNumber, int DoctorNumber, BigDecimal AmountOfBill, BigDecimal AmountPaid, BigDecimal AmountApproved, BigDecimal WriteOffAmount, String ProcCode, String ProcModifier, String ProcLetter, String JournalCode, int NumberOfServices, int PlaceOfService, String DiagnosisCode, String EmployAccidentOther, int LocationNumber, Date DateOfService, Date DateBilled, Date DatePayment, String Comment) {
        this.ArNumber = ArNumber;
        this.Accession = Accession;
        this.Inusrance = Inusrance;
        this.TestNumber = TestNumber;
        this.SubTestNumber = SubTestNumber;
        this.ClientNumber = ClientNumber;
        this.DoctorNumber = DoctorNumber;
        this.AmountOfBill = AmountOfBill;
        this.AmountPaid = AmountPaid;
        this.AmountApproved = AmountApproved;
        this.WriteOffAmount = WriteOffAmount;
        this.ProcCode = ProcCode;
        this.ProcModifier = ProcModifier;
        this.ProcLetter = ProcLetter;
        this.JournalCode = JournalCode;
        this.NumberOfServices = NumberOfServices;
        this.PlaceOfService = PlaceOfService;
        this.DiagnosisCode = DiagnosisCode;
        this.EmployAccidentOther = EmployAccidentOther;
        this.LocationNumber = LocationNumber;
        this.DateOfService = DateOfService;
        this.DateBilled = DateBilled;
        this.DatePayment = DatePayment;
        this.Comment = Comment;
    }

    public int getArNumber() {
        return ArNumber;
    }

    public void setArNumber(int ArNumber) {
        this.ArNumber = ArNumber;
    }

    public String getAccession() {
        return Accession;
    }

    public void setAccession(String Accession) {
        this.Accession = Accession;
    }

    public int getInusrance() {
        return Inusrance;
    }

    public void setInusrance(int Inusrance) {
        this.Inusrance = Inusrance;
    }

    public int getTestNumber() {
        return TestNumber;
    }

    public void setTestNumber(int TestNumber) {
        this.TestNumber = TestNumber;
    }

    public int getSubTestNumber() {
        return SubTestNumber;
    }

    public void setSubTestNumber(int SubTestNumber) {
        this.SubTestNumber = SubTestNumber;
    }

    public int getClientNumber() {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber) {
        this.ClientNumber = ClientNumber;
    }

    public int getDoctorNumber() {
        return DoctorNumber;
    }

    public void setDoctorNumber(int DoctorNumber) {
        this.DoctorNumber = DoctorNumber;
    }

    public BigDecimal getAmountOfBill() {
        return AmountOfBill;
    }

    public void setAmountOfBill(BigDecimal AmountOfBill) {
        this.AmountOfBill = AmountOfBill;
    }

    public BigDecimal getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(BigDecimal AmountPaid) {
        this.AmountPaid = AmountPaid;
    }

    public BigDecimal getAmountApproved() {
        return AmountApproved;
    }

    public void setAmountApproved(BigDecimal AmountApproved) {
        this.AmountApproved = AmountApproved;
    }

    public BigDecimal getWriteOffAmount() {
        return WriteOffAmount;
    }

    public void setWriteOffAmount(BigDecimal WriteOffAmount) {
        this.WriteOffAmount = WriteOffAmount;
    }

    public String getProcCode() {
        return ProcCode;
    }

    public void setProcCode(String ProcCode) {
        this.ProcCode = ProcCode;
    }

    public String getProcModifier() {
        return ProcModifier;
    }

    public void setProcModifier(String ProcModifier) {
        this.ProcModifier = ProcModifier;
    }

    public String getProcLetter() {
        return ProcLetter;
    }

    public void setProcLetter(String ProcLetter) {
        this.ProcLetter = ProcLetter;
    }

    public String getJournalCode() {
        return JournalCode;
    }

    public void setJournalCode(String JournalCode) {
        this.JournalCode = JournalCode;
    }

    public int getNumberOfServices() {
        return NumberOfServices;
    }

    public void setNumberOfServices(int NumberOfServices) {
        this.NumberOfServices = NumberOfServices;
    }

    public int getPlaceOfService() {
        return PlaceOfService;
    }

    public void setPlaceOfService(int PlaceOfService) {
        this.PlaceOfService = PlaceOfService;
    }

    public String getDiagnosisCode() {
        return DiagnosisCode;
    }

    public void setDiagnosisCode(String DiagnosisCode) {
        this.DiagnosisCode = DiagnosisCode;
    }

    public String getEmployAccidentOther() {
        return EmployAccidentOther;
    }

    public void setEmployAccidentOther(String EmployAccidentOther) {
        this.EmployAccidentOther = EmployAccidentOther;
    }

    public int getLocationNumber() {
        return LocationNumber;
    }

    public void setLocationNumber(int LocationNumber) {
        this.LocationNumber = LocationNumber;
    }

    public Date getDateOfService() {
        return DateOfService;
    }

    public void setDateOfService(Date DateOfService) {
        this.DateOfService = DateOfService;
    }

    public Date getDateBilled() {
        return DateBilled;
    }

    public void setDateBilled(Date DateBilled) {
        this.DateBilled = DateBilled;
    }

    public Date getDatePayment() {
        return DatePayment;
    }

    public void setDatePayment(Date DatePayment) {
        this.DatePayment = DatePayment;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }
    
    
}
