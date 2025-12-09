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
public class InfinixPreauthorizationResponseData implements Serializable{
    
    private static final long serialVersionUID = 42L;
    
    public String patientId;
    public String caseStatus;
    public String sequenceDateStr;
    public String physicianNpi;
    public String sequenceId;
    public String sequenceDate;
    public String caseNumber;
    public String appointmentId;
    public String caseId;
    public String patientDateOfBirth;
    
    public List<CommentInformation> commentInformation;
    public List<ProcedureInformation> procedureInformation;
    public List<ProcedureAuthorizationInfo> procedureAuthorizationInfo;
    public List<InsuranceInformation> insuranceInformation;
    
    public String getPatientId() {return patientId;}
    public void setPatientId(String patientId) {this.patientId = patientId;}
    public String getCaseStatus() {return caseStatus;}
    public void setCaseStatus (String caseStatus) {this.caseStatus = caseStatus;}
    public String getSequenceDateStr() {return sequenceDateStr;}
    public void setSequenceDateStr(String sequenceDateStr) {this.sequenceDateStr = sequenceDateStr;}
    public String getPhysicianNpi() {return physicianNpi;}
    public void setPhysicianNpi(String physicianNpi) {this.physicianNpi = physicianNpi;}
    public String getSequenceId() {return  sequenceId;}
    public void setSequenceId(String sequenceId) {this.sequenceId = sequenceId;}
    public String getSequenceDate() {return sequenceDate;}
    public void setSequenceDate(String sequenceDate) {this.sequenceDate = sequenceDate;}
    public String getCaseNumber() {return caseNumber;}
    public void setCaseNumber(String caseNumber) {this.caseNumber = caseNumber;}
    public String getAppointmentId() {return appointmentId;}
    public void setAppointmentId(String appointmentId) {this.appointmentId = appointmentId;}
    public String getCaseId() {return caseId;}
    public void setCaseId(String caseId) {this.caseId = caseId;}
    public String getPatientDateOfBirth() {return patientDateOfBirth;}
    public void setPatientDateOfBirth(String patientDateOfBirth) {this.patientDateOfBirth = patientDateOfBirth;}
    
    public List<CommentInformation> getCommentInformation() {return commentInformation;}
    public void setCommentInformation(List<CommentInformation> commentInformation) {this.commentInformation = commentInformation;}
    public List<ProcedureInformation> getProcedureInformation() {return procedureInformation;}
    public void setProcedureInformation(List<ProcedureInformation> procedureInformation) {this.procedureInformation = procedureInformation;}
    public List<ProcedureAuthorizationInfo> getProcedureAuthorizationInfo() {return procedureAuthorizationInfo;}
    public void setProcedureAuthorizationInfo(List<ProcedureAuthorizationInfo> procedureAuthorizationInfo) {this.procedureAuthorizationInfo = procedureAuthorizationInfo;}
    public List<InsuranceInformation> getInsuranceInformation() {return insuranceInformation;}
    public void setInsuranceInformation(List<InsuranceInformation> insuranceInformation) {this.insuranceInformation = insuranceInformation;}
    
    public static class CommentInformation
    {
        public String comments;
        public String id;
        public String createdOn;
        
        public String getComments() {return comments;}
        public void setComments(String comments) {this.comments = comments;}
        public String getId() {return id;}
        public void setId(String id) {this.id = id;}
        public String getCreatedOn() {return createdOn;}
        public void setCreatedOn(String createdOn) {this.createdOn = createdOn;}
    }
    
    public static class ProcedureInformation
    {
        public String quantityType;
        public String modifier;
        public String unitsUsed;
        public String description;
        public String requestedQuantity;
        public String id;
        public String cptCode;
        public String bodyPart;
        
        public String getQuantityType() {return quantityType;}
        public void setQuantityType(String quantityType) {this.quantityType = quantityType;}
        public String getModifier() {return modifier;}
        public void setModifier(String modifier) {this.modifier = modifier;}
        public String getUnitsUsed() {return unitsUsed;}
        public void setUnitsUsed(String unitsUsed) {this.unitsUsed = unitsUsed;}
        public String getDescription() {return description;}
        public void setDescriptiob(String description) {this.description = description;}
        public String getRequestedQuantity() {return requestedQuantity;}
        public void setRequestedQuantity(String requestedQuantity) {this.requestedQuantity = requestedQuantity;}
        public String getId() {return id;}
        public void setId(String id) {this.id = id;}
        public String getCptCode() {return cptCode;}
        public void setCptCode(String cptCode) {this.cptCode = cptCode;}
        public String getBodyPart() {return bodyPart;}
        public void setBodyPart(String bodyPart) {this.bodyPart = bodyPart;}
    }
    
    public static class ProcedureAuthorizationInfo
    {
        public String expiryDate;
        public String unitsAuthorized;
        public String denialReason;
        public String authNumber;
        public String insuranceId;
        public String id;
        public String procedureId;
        public String trackingNumber;
        public String cptCode;
        public String effectiveDate;
        public String status;
        
        public String getExpiryDate() {return expiryDate;}
        public void setExpiryDate(String expiryDate) {this.expiryDate = expiryDate;}
        public String getUnitsAuthorized() {return unitsAuthorized;}
        public void setUnitsAuthorized(String unitsAuthorized) {this.unitsAuthorized = unitsAuthorized;}
        public String getDenialReason() {return denialReason;}
        public void setDenialReason(String denialReason) {this.denialReason = denialReason;}
        public String getAuthNumber() {return authNumber;}
        public void setAuthNumber (String authNumber) {this.authNumber = authNumber;}
        public String getInsuranceId() {return insuranceId;}
        public void setInsuranceId(String insuranceId) {this.insuranceId = insuranceId;}
        public String getId() {return id;}
        public void setId(String id) {this.id = id;}
        public String getProcedureId() {return procedureId;}
        public void setProcedureId(String procedureId) {this.procedureId = procedureId;}
        public String getTrackingNumber() {return trackingNumber;}
        public void setTrackingNumber(String trackingNumber) {this.trackingNumber = trackingNumber;}
        public String getCptCode() {return cptCode;}
        public void setCptCode(String cptCode) {this.cptCode = cptCode;}
        public String getEffectiveDate() {return effectiveDate;}
        public void setEffectiveDate(String effectiveDate) {this.effectiveDate = effectiveDate;}
        public String getStatus() {return status;}
        public void setStatus(String status) {this.status = status;}
    }
    
    public static class InsuranceInformation
    {
        public String payerType;
        public String payerName;
        public String insuranceId;
        public String memberId;
        
        public String getPayerType() {return payerType;}
        public void setpayerType(String payerType) {this.payerType = payerType;}
        public String getPayerName() {return payerName;}
        public void setPayerName(String payerName) {this.payerName = payerName;}
        public String getInsuranceId() {return insuranceId;}
        public void setInsuranceId(String insuranceId) {this.insuranceId = insuranceId;}
        public String getMemberId() {return memberId;}
        public void setMemberId (String memberId) {this.memberId = memberId;}
    }
}
