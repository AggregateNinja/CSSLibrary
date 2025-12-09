/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing.Eligibility;
import API.Billing.InsuranceAPIRequestData;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Matt
 */
public class InfinixEligibilityRequestData implements InsuranceAPIRequestData, Serializable{
    
    private static final long serialVersionUID = 42L;
    
    CaseInformation caseInformation;
    PatientInformation patientInformation;
    List<InsuranceInformation> insuranceInformation;
    List<ProcedureInformation> procedureInformation;
    ProviderInformation providerInformation;
    
    public void setRequestData(InfinixEligibilityRequestData requestData)
    {
        caseInformation = requestData.caseInformation;
        patientInformation = requestData.patientInformation;
        insuranceInformation = requestData.insuranceInformation;
        procedureInformation = requestData.procedureInformation;
        providerInformation = requestData.providerInformation;
    }
    
    public CaseInformation getCaseInformation() {return caseInformation;}
    public void setCaseInformation(CaseInformation caseInformation) {this.caseInformation = caseInformation;}
    public PatientInformation getPatientInformation() {return patientInformation;}
    public void setPatientInformation(PatientInformation patientInformation) {this.patientInformation = patientInformation;}
    public List<InsuranceInformation> getInsuranceInformation() {return insuranceInformation;}
    public void setInsuranceInformation(List<InsuranceInformation> insuranceInformation) {this.insuranceInformation = insuranceInformation;}
    public List<ProcedureInformation> getProcedureInformation() {return procedureInformation;}
    public void setProcedureInformation(List<ProcedureInformation> procedureInformation) {this.procedureInformation = procedureInformation;}
    public ProviderInformation getProviderInformation() {return providerInformation;}
    public void setProviderInformation(ProviderInformation providerInformation) {this.providerInformation = providerInformation;}
    
    public static class CaseInformation
    {
        String accessionNumber;
        String dateOfService;
        String placeOfService;
        
        public String getAccessionNumber() {return accessionNumber;}
        public void setAccessionNumber(String accessionNumber) {this.accessionNumber = accessionNumber;}
        public String getDateOfService() {return dateOfService;}
        public void setDateOfService(String dateOfService) {this.dateOfService = dateOfService;}
        public String getPlaceOfService() {return placeOfService;}
        public void setPlaceOfService(String placeOfService) {this.placeOfService = placeOfService;}
    }
    
    public static class PatientInformation
    {
        String dateOfBirth;
        String firstName;
        String lastName;
        String gender;
        String address;
        String city;
        String state;
        String zipCode;
        
        public String getDateOfBirth() {return dateOfBirth;}
        public void setDateOfBirth(String dateOfBirth) {this.dateOfBirth = dateOfBirth;}
        public String getFirstName() {return firstName;}
        public void setFirstName(String firstName) {this.firstName = firstName;}
        public String getLastName() {return lastName;}
        public void setLastName(String lastName) {this.lastName = lastName;}
        public String getGender() {return gender;}
        public void setGender(String gender) {this.gender = gender;}
        public String getAddress() {return address;}
        public void setAddress(String address) {this.address = address;}
        public String getCity() {return city;}
        public void setCity(String city) {this.city = city;}
        public String getState() {return state;}
        public void setState(String state) {this.state = state;}
        public String getZipCode() {return zipCode;}
        public void setZipCode(String zipCode) {this.zipCode = zipCode;}
    }
    
    public static class InsuranceInformation
    {
        String payerName;
        String payerType;
        String memberId;
        
        public String getPayerName() {return payerName;}
        public void setPayerName(String payerName) {this.payerName = payerName;}
        public String getPayerType() {return payerType;}
        public void setPayerType(String payerType) {this.payerType = payerType;}
        public String getMemberId() {return memberId;}
        public void setMemberId(String memberId) {this.memberId = memberId;}
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
        public String quantityType() {return quantityType;}
        public void setQuantityType(String quantityType) {this.quantityType = quantityType;}
        public String getUnitsUsed() {return unitsUsed;}
        public void setUnitsUsed(String unitsUsed) {this.unitsUsed = unitsUsed;}
    }
    
    public static class ProviderInformation
    {
        String providerCode;
        String providerName;
        String providerNPI;
        
        public String getProviderCode() {return providerCode;}
        public void setProviderCode(String providerCode) {this.providerCode = providerCode;}
        public String getProviderName() {return providerName;}
        public void setProviderName(String providerName) {this.providerName = providerName;}
        public String getProviderNPI() {return providerNPI;}
        public void setProviderNPI(String providerNPI) {this.providerNPI = providerNPI;}
    }
}
