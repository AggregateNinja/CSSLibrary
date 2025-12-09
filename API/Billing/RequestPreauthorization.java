/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Matt
 */
public class RequestPreauthorization implements Serializable {
    
    public static enum API_PROVIDER{
        Infinix;
    }
    
    final API_PROVIDER apiProvider;
    final Integer orderId;
    final String appointmentId;
    final Date dateOfService;
    final String priority;
    
    final String physicianFirstName;
    final String physicianLastName;
    final String physicianNpi;
    
    final Date patientDob;
    final String patientFirstName;
    final String patientLastName;
    final String patientGender;
    final String patientNote;
    final String patientId;
    
    final String memberId;
    final String insuranceName;
    final String insuranceType;
    
    final HashMap<String, String> cptModifierMap;
    final String bodyPart;
    final String requestedQuantity;
    final String quantityType;
    final String unitsUsed;
    
    final String diagnosisCodeIds;

    final String facilityCode;
    final String facilityName;
    final String facilityNpi;
    
    final Integer userId;
    final String caseNumber;
    
    public RequestPreauthorization (API_PROVIDER apiProvider, Integer orderId, String appointmentId, Date dateOfService, String priority,
            String physicianFirstName, String physicianLastName, String physicianNpi,
            Date patientDob, String patientFirstName, String patientLastName, String patientGender, String patientNote, String patientId,
            String memberId, String insuranceName, String insuranceType,
            HashMap<String, String> cptModifierMap, String bodyPart, String requestedQuantity, String quantityType, String unitsUsed,
            String diagnosisCodeIds,
            String facilityCode, String facilityName, String facilityNpi, Integer userId, String caseNumber)
    {
        this.apiProvider = apiProvider;
        this.orderId = orderId;
        this.appointmentId = appointmentId;
        this.dateOfService = dateOfService;
        this.priority = priority;
        
        this.physicianFirstName = physicianFirstName;
        this.physicianLastName = physicianLastName;
        this.physicianNpi = physicianNpi;
        
        this.patientDob = patientDob;
        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientGender = patientGender;
        this.patientNote = patientNote;
        this.patientId = patientId;
        
        this.memberId = memberId;
        this.insuranceName = insuranceName;
        this.insuranceType = insuranceType;
        
        this.cptModifierMap = cptModifierMap;
        this.bodyPart = bodyPart;
        this.requestedQuantity = requestedQuantity;
        this.quantityType = quantityType;
        this.unitsUsed = unitsUsed;
        
        this.diagnosisCodeIds = diagnosisCodeIds;
        
        this.facilityCode = facilityCode;
        this.facilityName = facilityName;
        this.facilityNpi = facilityNpi;
        
        this.userId = userId;
        this.caseNumber = caseNumber;
    }
    
    // Encapsulated Fields
    
    public API_PROVIDER getAPIProvider() {return apiProvider;}
    public Integer getOrderId() {return orderId;}
    public String getAppointmentId() {return appointmentId;}
    public Date getDateOfService() {return dateOfService;}
    public String getPriority() {return priority;}
    
    public String getPhysicianFirstName() {return physicianFirstName;}
    public String getPhysicianLastName() {return physicianLastName;}
    public String getPhysicianNpi() {return physicianNpi;}
    
    public Date getPatientDob() {return patientDob;}
    public String getPatientFirstName() {return patientFirstName;}
    public String getPatientLastName() {return patientLastName;}
    public String getPatientGender() {return patientGender;}
    public String getPatientNote() {return patientNote;}
    public String getPatientId() {return patientId;}
    
    public String getMemberId() {return memberId;}
    public String getInsuranceName() {return insuranceName;}
    public String getInsuranceType() {return insuranceType;}
    
    public HashMap<String, String> getCptModifierMap() {return cptModifierMap;}
    public String getBodyPart() {return bodyPart;}
    public String getRequestedQuantity() {return requestedQuantity;}
    public String getQuantityType() {return quantityType;}
    public String getUnitsUsed() {return unitsUsed;}
    
    public String getDiagnosisCodeIds() {return diagnosisCodeIds;}
    
    public String getFacilityCode() {return facilityCode;}
    public String getFacilityName() {return facilityName;}
    public String getFacilityNpi() {return facilityNpi;}
    
    public Integer getUserId() {return userId;}
    public String getCaseNumber() {return caseNumber;}
}
