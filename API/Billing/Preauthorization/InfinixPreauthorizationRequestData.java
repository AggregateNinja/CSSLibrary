/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing.Preauthorization;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Matt
 */
public class InfinixPreauthorizationRequestData implements Serializable {
    
    private static final long serialVersionUID = 42L;
    
    CaseInformation caseInformation;
    PhysicianInformation physicianInformation;
    PatientInformation patientInformation;
    List<InsuranceInformation> insuranceInformation;
    List<ProcedureInformation> procedureInformation;
    List<DiagnosisInformation> diagnosisInformation;
    FacilityInformation facilityInformation;
    
    public void setRequestData(InfinixPreauthorizationRequestData data)
    {
        caseInformation = data.caseInformation;
        physicianInformation = data.physicianInformation;
        patientInformation = data.patientInformation;
        insuranceInformation = data.insuranceInformation;
        procedureInformation = data.procedureInformation;
        diagnosisInformation = data.diagnosisInformation;
        facilityInformation = data.facilityInformation;
    }
    
    public CaseInformation getCaseInformation() {return caseInformation;}
    public void setCaseInformation(CaseInformation caseInformation) {this.caseInformation = caseInformation;}
    public PhysicianInformation getPhysicianInformation() {return physicianInformation;}
    public void setPhysicianInformation(PhysicianInformation physicianInformation) {this.physicianInformation = physicianInformation;}
    public PatientInformation getPatientInformation() {return patientInformation;}
    public void setPatientInformation(PatientInformation patientInformation) {this.patientInformation = patientInformation;}
    public List<InsuranceInformation> getInsuranceInformation() {return insuranceInformation;}
    public void setInsuranceInformation(List<InsuranceInformation> insuranceInformation) {this.insuranceInformation = insuranceInformation;}
    public List<ProcedureInformation> getProcedureInformation() {return procedureInformation;}
    public void setProcedureInformation(List<ProcedureInformation> procedureInformation) {this.procedureInformation = procedureInformation;}
    public List<DiagnosisInformation> getDiagnosisInformation() {return diagnosisInformation;}
    public void setDiagnosisInformation(List<DiagnosisInformation> diagnosisInformation) {this.diagnosisInformation = diagnosisInformation;}
    public FacilityInformation getFacilityInformation() {return facilityInformation;}
    public void setFacilityInformation(FacilityInformation facilityInformation) {this.facilityInformation = facilityInformation;}
    
    public static class CaseInformation
    {
        String appointmentId;
        String dateOfService;
        String priority;
        
        public String getAppointmentId() {return appointmentId;}
        public void setAppointmentId(String appointmentId) {this.appointmentId = appointmentId;}
        public String getDateOfService() {return dateOfService;}
        public void setDateOfService(String dateOfService) {this.dateOfService = dateOfService;}
        public String getPriority() {return priority;}
        public void setPriority(String priority) {this.priority = priority;}
    }
    
    public static class PhysicianInformation
    {
        String firstName;
        String lastName;
        String npi;
        
        public String getFirstName() {return firstName;}
        public void setFirstName(String firstName) {this.firstName = firstName;}
        public String getLastName() {return lastName;}
        public void setLastName(String lastName) {this.lastName = lastName;}
        public String getNpi() {return npi;}
        public void setNpi(String npi) {this.npi = npi;}
    }
    
    public static class PatientInformation
    {
        String dob;
        String firstName;
        String lastName;
        String gender;
        String note;
        String patientId;
        
        public String getDob() {return dob;}
        public void setDob(String dob) {this.dob = dob;}
        public String getFirstName() {return firstName;}
        public void setFirstName(String firstName) {this.firstName = firstName;}
        public String getLastName() {return lastName;}
        public void setLastName(String lastName) {this.lastName = lastName;}
        public String getGender() {return gender;}
        public void setGender(String gender) {this.gender = gender;}
        public String getNote() {return note;}
        public void setNote(String note) {this.note = note;}
        public String getPatientId() {return patientId;}
        public void setPatientId(String patientId) {this.patientId = patientId;}
    }
    
    public static class InsuranceInformation
    {
        String memberId;
        String name;
        String type;
        
        public String getMemberId() {return memberId;}
        public void setMemberId(String memberId) {this.memberId = memberId;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public String getType() {return type;}
        public void setType(String type) {this.type = type;}
    }
    
    public static class ProcedureInformation
    {
        String cptCode;
        String modifier;
        String bodyPart;
        String requestedQuantity;
        String quantityType;
        String unitsUsed;
        
        public String getCptCode() {return cptCode;}
        public void setCptCode(String cptCode) {this.cptCode = cptCode;}
        public String getModifier() {return modifier;}
        public void setModifier(String modifier) {this.modifier = modifier;}
        public String getBodyPart() {return bodyPart;}
        public void setBodyPart(String bodyPart) {this.bodyPart = bodyPart;}
        public String getRequestedQuantity() {return requestedQuantity;}
        public void setRequestedQuantity(String requestedQuantity) {this.requestedQuantity = requestedQuantity;}
        public String getQuantityType() {return quantityType;}
        public void setQuantityType(String quantityType) {this.quantityType = quantityType;}
        public String getUnitsUsed() {return unitsUsed;}
        public void setUnitsUsed(String unitsUsed) {this.unitsUsed = unitsUsed;}
    }
    
    public static class DiagnosisInformation
    {
        String icdCode;
        
        public String getIcdCode() {return icdCode;}
        public void setIcdCode(String icdCode) {this.icdCode = icdCode;}
    }
    
    public static class FacilityInformation
    {
        String code;
        String name;
        String npi;
        
        public String getCode() {return code;}
        public void setCode(String code) {this.code = code;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public String getNpi() {return npi;}
        public void setNpi(String npi) {this.npi = npi;}
    }
}
