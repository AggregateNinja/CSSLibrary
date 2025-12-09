/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package EPOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/14/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import java.util.Date;

public class ArchiveOrderLine {

    private String ExportKey;
    private String Accession;
    private int ClientNumber;
    private int PatientAge;
    private String PatientPhone;
    private int InsuranceNumber;
    private int SubscriberARNumber;
    private int PatientZip;
    private int DoctorNumber;
    private int RelationshipCode;
    private String FaxNumber;
    private int PatientARNumber;
    private char PatientSex;
    private String BillType;
    private String ReqFlag;
    private String Species;
    private int Location;
    private char PatientMiddleInitial;
    private String PatientLastName;
    private String PatientFirstName;
    private String PatientId;
    private String PatientAddress;
    private String PatientCity;
    private String PatientState;
    private String MedicaidId;
    private String MedicareNumber;
    private String Phlebot;
    private String ResultComment;
    private String WorksheetComment;
    private String EAO;
    private String AuxCode;
    private Date OrderDate;
    private Date SpecimenDate;
    private Date DOB;
    private Date ExtraOrderDate;
    private Date ExtraSpecimenDate;
    private Date ExtraDOB;
    private Date ExtraReportedDate;
    private String OrderCommentPointer;
    private String ReportType;
    private String CallComment1;
    private String CallComment2;

    public ArchiveOrderLine() {
    }

    public ArchiveOrderLine(String ExportKey, String Accession, int ClientNumber, int PatientAge, String PatientPhone, int InsuranceNumber, int SubscriberARNumber, int PatientZip, int DoctorNumber, int RelationshipCode, String FaxNumber, int PatientARNumber, char PatientSex, String BillType, String ReqFlag, String Species, int Location, char PatientMiddleInitial, String PatientLastName, String PatientFirstName, String PatientId, String PatientAddress, String PatientCity, String PatientState, String MedicaidId, String MedicareNumber, String Phlebot, String ResultComment, String WorksheetComment, String EAO, String AuxCode, Date OrderDate, Date SpecimenDate, Date DOB, Date ExtraOrderDate, Date ExtraSpecimenDate, Date ExtraDOB, Date ExtraReportedDate, String OrderCommentPointer, String ReportType, String CallComment1, String CallComment2) {
        this.ExportKey = ExportKey;
        this.Accession = Accession;
        this.ClientNumber = ClientNumber;
        this.PatientAge = PatientAge;
        this.PatientPhone = PatientPhone;
        this.InsuranceNumber = InsuranceNumber;
        this.SubscriberARNumber = SubscriberARNumber;
        this.PatientZip = PatientZip;
        this.DoctorNumber = DoctorNumber;
        this.RelationshipCode = RelationshipCode;
        this.FaxNumber = FaxNumber;
        this.PatientARNumber = PatientARNumber;
        this.PatientSex = PatientSex;
        this.BillType = BillType;
        this.ReqFlag = ReqFlag;
        this.Species = Species;
        this.Location = Location;
        this.PatientMiddleInitial = PatientMiddleInitial;
        this.PatientLastName = PatientLastName;
        this.PatientFirstName = PatientFirstName;
        this.PatientId = PatientId;
        this.PatientAddress = PatientAddress;
        this.PatientCity = PatientCity;
        this.PatientState = PatientState;
        this.MedicaidId = MedicaidId;
        this.MedicareNumber = MedicareNumber;
        this.Phlebot = Phlebot;
        this.ResultComment = ResultComment;
        this.WorksheetComment = WorksheetComment;
        this.EAO = EAO;
        this.AuxCode = AuxCode;
        this.OrderDate = OrderDate;
        this.SpecimenDate = SpecimenDate;
        this.DOB = DOB;
        this.ExtraOrderDate = ExtraOrderDate;
        this.ExtraSpecimenDate = ExtraSpecimenDate;
        this.ExtraDOB = ExtraDOB;
        this.ExtraReportedDate = ExtraReportedDate;
        this.OrderCommentPointer = OrderCommentPointer;
        this.ReportType = ReportType;
        this.CallComment1 = CallComment1;
        this.CallComment2 = CallComment2;
    }

    public String getExportKey() {
        return ExportKey;
    }

    public void setExportKey(String ExportKey) {
        this.ExportKey = ExportKey;
    }

    public String getAccession() {
        return Accession;
    }

    public void setAccession(String Accession) {
        this.Accession = Accession;
    }

    public int getClientNumber() {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber) {
        this.ClientNumber = ClientNumber;
    }

    public int getPatientAge() {
        return PatientAge;
    }

    public void setPatientAge(int PatientAge) {
        this.PatientAge = PatientAge;
    }

    public String getPatientPhone() {
        return PatientPhone;
    }

    public void setPatientPhone(String PatientPhone) {
        this.PatientPhone = PatientPhone;
    }

    public int getInsuranceNumber() {
        return InsuranceNumber;
    }

    public void setInsuranceNumber(int InsuranceNumber) {
        this.InsuranceNumber = InsuranceNumber;
    }

    public int getSubscriberARNumber() {
        return SubscriberARNumber;
    }

    public void setSubscriberARNumber(int SubscriberARNumber) {
        this.SubscriberARNumber = SubscriberARNumber;
    }

    public int getPatientZip() {
        return PatientZip;
    }

    public void setPatientZip(int PatientZip) {
        this.PatientZip = PatientZip;
    }

    public int getDoctorNumber() {
        return DoctorNumber;
    }

    public void setDoctorNumber(int DoctorNumber) {
        this.DoctorNumber = DoctorNumber;
    }

    public int getRelationshipCode() {
        return RelationshipCode;
    }

    public void setRelationshipCode(int RelationshipCode) {
        this.RelationshipCode = RelationshipCode;
    }

    public String getFaxNumber() {
        return FaxNumber;
    }

    public void setFaxNumber(String FaxNumber) {
        this.FaxNumber = FaxNumber;
    }

    public int getPatientARNumber() {
        return PatientARNumber;
    }

    public void setPatientARNumber(int PatientARNumber) {
        this.PatientARNumber = PatientARNumber;
    }

    public char getPatientSex() {
        return PatientSex;
    }

    public void setPatientSex(char PatientSex) {
        this.PatientSex = PatientSex;
    }

    public String getBillType() {
        return BillType;
    }

    public void setBillType(String BillType) {
        this.BillType = BillType;
    }

    public String getReqFlag() {
        return ReqFlag;
    }

    public void setReqFlag(String ReqFlag) {
        this.ReqFlag = ReqFlag;
    }

    public String getSpecies() {
        return Species;
    }

    public void setSpecies(String Species) {
        this.Species = Species;
    }

    public int getLocation() {
        return Location;
    }

    public void setLocation(int Location) {
        this.Location = Location;
    }

    public char getPatientMiddleInitial() {
        return PatientMiddleInitial;
    }

    public void setPatientMiddleInitial(char PatientMiddleInitial) {
        this.PatientMiddleInitial = PatientMiddleInitial;
    }

    public String getPatientLastName() {
        return PatientLastName;
    }

    public void setPatientLastName(String PatientLastName) {
        this.PatientLastName = PatientLastName;
    }

    public String getPatientFirstName() {
        return PatientFirstName;
    }

    public void setPatientFirstName(String PatientFirstName) {
        this.PatientFirstName = PatientFirstName;
    }

    public String getPatientId() {
        return PatientId;
    }

    public void setPatientId(String PatientId) {
        this.PatientId = PatientId;
    }

    public String getPatientAddress() {
        return PatientAddress;
    }

    public void setPatientAddress(String PatientAddress) {
        this.PatientAddress = PatientAddress;
    }

    public String getPatientCity() {
        return PatientCity;
    }

    public void setPatientCity(String PatientCity) {
        this.PatientCity = PatientCity;
    }

    public String getPatientState() {
        return PatientState;
    }

    public void setPatientState(String PatientState) {
        this.PatientState = PatientState;
    }

    public String getMedicaidId() {
        return MedicaidId;
    }

    public void setMedicaidId(String MedicaidId) {
        this.MedicaidId = MedicaidId;
    }

    public String getMedicareNumber() {
        return MedicareNumber;
    }

    public void setMedicareNumber(String MedicareNumber) {
        this.MedicareNumber = MedicareNumber;
    }

    public String getPhlebot() {
        return Phlebot;
    }

    public void setPhlebot(String Phlebot) {
        this.Phlebot = Phlebot;
    }

    public String getResultComment() {
        return ResultComment;
    }

    public void setResultComment(String ResultComment) {
        this.ResultComment = ResultComment;
    }

    public String getWorksheetComment() {
        return WorksheetComment;
    }

    public void setWorksheetComment(String WorksheetComment) {
        this.WorksheetComment = WorksheetComment;
    }

    public String getEAO() {
        return EAO;
    }

    public void setEAO(String EAO) {
        this.EAO = EAO;
    }

    public String getAuxCode() {
        return AuxCode;
    }

    public void setAuxCode(String AuxCode) {
        this.AuxCode = AuxCode;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date OrderDate) {
        this.OrderDate = OrderDate;
    }

    public Date getSpecimenDate() {
        return SpecimenDate;
    }

    public void setSpecimenDate(Date SpecimenDate) {
        this.SpecimenDate = SpecimenDate;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public Date getExtraOrderDate() {
        return ExtraOrderDate;
    }

    public void setExtraOrderDate(Date ExtraOrderDate) {
        this.ExtraOrderDate = ExtraOrderDate;
    }

    public Date getExtraSpecimenDate() {
        return ExtraSpecimenDate;
    }

    public void setExtraSpecimenDate(Date ExtraSpecimenDate) {
        this.ExtraSpecimenDate = ExtraSpecimenDate;
    }

    public Date getExtraDOB() {
        return ExtraDOB;
    }

    public void setExtraDOB(Date ExtraDOB) {
        this.ExtraDOB = ExtraDOB;
    }

    public Date getExtraReportedDate() {
        return ExtraReportedDate;
    }

    public void setExtraReportedDate(Date ExtraReportedDate) {
        this.ExtraReportedDate = ExtraReportedDate;
    }

    public String getOrderCommentPointer() {
        return OrderCommentPointer;
    }

    public void setOrderCommentPointer(String OrderCommentPointer) {
        this.OrderCommentPointer = OrderCommentPointer;
    }

    public String getReportType() {
        return ReportType;
    }

    public void setReportType(String ReportType) {
        this.ReportType = ReportType;
    }

    public String getCallComment1() {
        return CallComment1;
    }

    public void setCallComment1(String CallComment1) {
        this.CallComment1 = CallComment1;
    }

    public String getCallComment2() {
        return CallComment2;
    }

    public void setCallComment2(String CallComment2) {
        this.CallComment2 = CallComment2;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.ExportKey != null ? this.ExportKey.hashCode() : 0);
        hash = 59 * hash + (this.Accession != null ? this.Accession.hashCode() : 0);
        hash = 59 * hash + this.ClientNumber;
        hash = 59 * hash + this.PatientAge;
        hash = 59 * hash + (this.PatientPhone != null ? this.PatientPhone.hashCode() : 0);
        hash = 59 * hash + this.InsuranceNumber;
        hash = 59 * hash + this.SubscriberARNumber;
        hash = 59 * hash + this.PatientZip;
        hash = 59 * hash + this.DoctorNumber;
        hash = 59 * hash + this.RelationshipCode;
        hash = 59 * hash + (this.FaxNumber != null ? this.FaxNumber.hashCode() : 0);
        hash = 59 * hash + this.PatientARNumber;
        hash = 59 * hash + this.PatientSex;
        hash = 59 * hash + (this.BillType != null ? this.BillType.hashCode() : 0);
        hash = 59 * hash + (this.ReqFlag != null ? this.ReqFlag.hashCode() : 0);
        hash = 59 * hash + (this.Species != null ? this.Species.hashCode() : 0);
        hash = 59 * hash + this.Location;
        hash = 59 * hash + this.PatientMiddleInitial;
        hash = 59 * hash + (this.PatientLastName != null ? this.PatientLastName.hashCode() : 0);
        hash = 59 * hash + (this.PatientFirstName != null ? this.PatientFirstName.hashCode() : 0);
        hash = 59 * hash + (this.PatientId != null ? this.PatientId.hashCode() : 0);
        hash = 59 * hash + (this.PatientAddress != null ? this.PatientAddress.hashCode() : 0);
        hash = 59 * hash + (this.PatientCity != null ? this.PatientCity.hashCode() : 0);
        hash = 59 * hash + (this.PatientState != null ? this.PatientState.hashCode() : 0);
        hash = 59 * hash + (this.MedicaidId != null ? this.MedicaidId.hashCode() : 0);
        hash = 59 * hash + (this.MedicareNumber != null ? this.MedicareNumber.hashCode() : 0);
        hash = 59 * hash + (this.Phlebot != null ? this.Phlebot.hashCode() : 0);
        hash = 59 * hash + (this.ResultComment != null ? this.ResultComment.hashCode() : 0);
        hash = 59 * hash + (this.WorksheetComment != null ? this.WorksheetComment.hashCode() : 0);
        hash = 59 * hash + (this.EAO != null ? this.EAO.hashCode() : 0);
        hash = 59 * hash + (this.AuxCode != null ? this.AuxCode.hashCode() : 0);
        hash = 59 * hash + (this.OrderDate != null ? this.OrderDate.hashCode() : 0);
        hash = 59 * hash + (this.SpecimenDate != null ? this.SpecimenDate.hashCode() : 0);
        hash = 59 * hash + (this.DOB != null ? this.DOB.hashCode() : 0);
        hash = 59 * hash + (this.ExtraOrderDate != null ? this.ExtraOrderDate.hashCode() : 0);
        hash = 59 * hash + (this.ExtraSpecimenDate != null ? this.ExtraSpecimenDate.hashCode() : 0);
        hash = 59 * hash + (this.ExtraDOB != null ? this.ExtraDOB.hashCode() : 0);
        hash = 59 * hash + (this.ExtraReportedDate != null ? this.ExtraReportedDate.hashCode() : 0);
        hash = 59 * hash + (this.OrderCommentPointer != null ? this.OrderCommentPointer.hashCode() : 0);
        hash = 59 * hash + (this.ReportType != null ? this.ReportType.hashCode() : 0);
        hash = 59 * hash + (this.CallComment1 != null ? this.CallComment1.hashCode() : 0);
        hash = 59 * hash + (this.CallComment2 != null ? this.CallComment2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArchiveOrderLine other = (ArchiveOrderLine) obj;
        if ((this.ExportKey == null) ? (other.ExportKey != null) : !this.ExportKey.equals(other.ExportKey)) {
            return false;
        }
        if (this.Accession != other.Accession) {
            return false;
        }
        if (this.ClientNumber != other.ClientNumber) {
            return false;
        }
        if (this.PatientAge != other.PatientAge) {
            return false;
        }
        if ((this.PatientPhone == null) ? (other.PatientPhone != null) : !this.PatientPhone.equals(other.PatientPhone)) {
            return false;
        }
        if (this.InsuranceNumber != other.InsuranceNumber) {
            return false;
        }
        if (this.SubscriberARNumber != other.SubscriberARNumber) {
            return false;
        }
        if (this.PatientZip != other.PatientZip) {
            return false;
        }
        if (this.DoctorNumber != other.DoctorNumber) {
            return false;
        }
        if (this.RelationshipCode != other.RelationshipCode) {
            return false;
        }
        if ((this.FaxNumber == null) ? (other.FaxNumber != null) : !this.FaxNumber.equals(other.FaxNumber)) {
            return false;
        }
        if (this.PatientARNumber != other.PatientARNumber) {
            return false;
        }
        if (this.PatientSex != other.PatientSex) {
            return false;
        }
        if ((this.BillType == null) ? (other.BillType != null) : !this.BillType.equals(other.BillType)) {
            return false;
        }
        if ((this.ReqFlag == null) ? (other.ReqFlag != null) : !this.ReqFlag.equals(other.ReqFlag)) {
            return false;
        }
        if ((this.Species == null) ? (other.Species != null) : !this.Species.equals(other.Species)) {
            return false;
        }
        if (this.Location != other.Location) {
            return false;
        }
        if (this.PatientMiddleInitial != other.PatientMiddleInitial) {
            return false;
        }
        if ((this.PatientLastName == null) ? (other.PatientLastName != null) : !this.PatientLastName.equals(other.PatientLastName)) {
            return false;
        }
        if ((this.PatientFirstName == null) ? (other.PatientFirstName != null) : !this.PatientFirstName.equals(other.PatientFirstName)) {
            return false;
        }
        if ((this.PatientId == null) ? (other.PatientId != null) : !this.PatientId.equals(other.PatientId)) {
            return false;
        }
        if ((this.PatientAddress == null) ? (other.PatientAddress != null) : !this.PatientAddress.equals(other.PatientAddress)) {
            return false;
        }
        if ((this.PatientCity == null) ? (other.PatientCity != null) : !this.PatientCity.equals(other.PatientCity)) {
            return false;
        }
        if ((this.PatientState == null) ? (other.PatientState != null) : !this.PatientState.equals(other.PatientState)) {
            return false;
        }
        if ((this.MedicaidId == null) ? (other.MedicaidId != null) : !this.MedicaidId.equals(other.MedicaidId)) {
            return false;
        }
        if ((this.MedicareNumber == null) ? (other.MedicareNumber != null) : !this.MedicareNumber.equals(other.MedicareNumber)) {
            return false;
        }
        if ((this.Phlebot == null) ? (other.Phlebot != null) : !this.Phlebot.equals(other.Phlebot)) {
            return false;
        }
        if ((this.ResultComment == null) ? (other.ResultComment != null) : !this.ResultComment.equals(other.ResultComment)) {
            return false;
        }
        if ((this.WorksheetComment == null) ? (other.WorksheetComment != null) : !this.WorksheetComment.equals(other.WorksheetComment)) {
            return false;
        }
        if ((this.EAO == null) ? (other.EAO != null) : !this.EAO.equals(other.EAO)) {
            return false;
        }
        if ((this.AuxCode == null) ? (other.AuxCode != null) : !this.AuxCode.equals(other.AuxCode)) {
            return false;
        }
        if (this.OrderDate != other.OrderDate && (this.OrderDate == null || !this.OrderDate.equals(other.OrderDate))) {
            return false;
        }
        if (this.SpecimenDate != other.SpecimenDate && (this.SpecimenDate == null || !this.SpecimenDate.equals(other.SpecimenDate))) {
            return false;
        }
        if (this.DOB != other.DOB && (this.DOB == null || !this.DOB.equals(other.DOB))) {
            return false;
        }
        if (this.ExtraOrderDate != other.ExtraOrderDate && (this.ExtraOrderDate == null || !this.ExtraOrderDate.equals(other.ExtraOrderDate))) {
            return false;
        }
        if (this.ExtraSpecimenDate != other.ExtraSpecimenDate && (this.ExtraSpecimenDate == null || !this.ExtraSpecimenDate.equals(other.ExtraSpecimenDate))) {
            return false;
        }
        if (this.ExtraDOB != other.ExtraDOB && (this.ExtraDOB == null || !this.ExtraDOB.equals(other.ExtraDOB))) {
            return false;
        }
        if (this.ExtraReportedDate != other.ExtraReportedDate && (this.ExtraReportedDate == null || !this.ExtraReportedDate.equals(other.ExtraReportedDate))) {
            return false;
        }
        if ((this.OrderCommentPointer == null) ? (other.OrderCommentPointer != null) : !this.OrderCommentPointer.equals(other.OrderCommentPointer)) {
            return false;
        }
        if ((this.ReportType == null) ? (other.ReportType != null) : !this.ReportType.equals(other.ReportType)) {
            return false;
        }
        if ((this.CallComment1 == null) ? (other.CallComment1 != null) : !this.CallComment1.equals(other.CallComment1)) {
            return false;
        }
        if ((this.CallComment2 == null) ? (other.CallComment2 != null) : !this.CallComment2.equals(other.CallComment2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ArchiveOrderLine{" + "ExportKey=" + ExportKey + ", Accession=" + Accession + ", ClientNumber=" + ClientNumber + ", PatientAge=" + PatientAge + ", PatientPhone=" + PatientPhone + ", InsuranceNumber=" + InsuranceNumber + ", SubscriberARNumber=" + SubscriberARNumber + ", PatientZip=" + PatientZip + ", DoctorNumber=" + DoctorNumber + ", RelationshipCode=" + RelationshipCode + ", FaxNumber=" + FaxNumber + ", PatientARNumber=" + PatientARNumber + ", PatientSex=" + PatientSex + ", BillType=" + BillType + ", ReqFlag=" + ReqFlag + ", Species=" + Species + ", Location=" + Location + ", PatientMiddleInitial=" + PatientMiddleInitial + ", PatientLastName=" + PatientLastName + ", PatientFirstName=" + PatientFirstName + ", PatientId=" + PatientId + ", PatientAddress=" + PatientAddress + ", PatientCity=" + PatientCity + ", PatientState=" + PatientState + ", MedicaidId=" + MedicaidId + ", MedicareNumber=" + MedicareNumber + ", Phlebot=" + Phlebot + ", ResultComment=" + ResultComment + ", WorksheetComment=" + WorksheetComment + ", EAO=" + EAO + ", AuxCode=" + AuxCode + ", OrderDate=" + OrderDate + ", SpecimenDate=" + SpecimenDate + ", DOB=" + DOB + ", ExtraOrderDate=" + ExtraOrderDate + ", ExtraSpecimenDate=" + ExtraSpecimenDate + ", ExtraDOB=" + ExtraDOB + ", ExtraReportedDate=" + ExtraReportedDate + ", OrderCommentPointer=" + OrderCommentPointer + ", ReportType=" + ReportType + ", CallComment1=" + CallComment1 + ", CallComment2=" + CallComment2 + '}';
    }
    
}
