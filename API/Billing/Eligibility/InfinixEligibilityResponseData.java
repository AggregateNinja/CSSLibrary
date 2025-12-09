/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing.Eligibility;

import API.Billing.InsuranceAPIResponseData;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matt
 */
public class InfinixEligibilityResponseData implements InsuranceAPIResponseData, Serializable{
    private static final long serialVersionUID = 42L;
    
    //<editor-fold defaultstate="collapsed" desc="ChangeHealthCare Overriden Methods">
    @Override
    public Coverage getCoverage() 
    {
//        if(getError() != null)
//        {
//            return null;
//        }
//        else
//        {
            return new InfinixCoverage();
//        }
    }
    
    @Override
    public void stripProviderData() {};
    
    @Override
    public Map<String, List<String>> getErrors()
    {
        return null;
    }

    @Override
    public String getTransactionUUID() {return this.getControlNumber();}

    @Override
    public Subscriber getSubscriber() {return null;}

    @Override
    public Provider getProvider() {return null;}
    //</editor-fold>
    
    public String controlNumber;
    
    public String caseId;
    public String patientId;
    public String caseNumber;
    public String caseStatus;
    public String physicianNPI;
    public String appointmentId;
    public String patientDateOfBirth;
    
    CommentInformation commentInformation;
    List<InsuranceInformation> insuranceInformation;
    List<ProcedureInformation> procedureInformation;
    List<ProcedureAuthorizationInfo> procedureAuthorizationInfo;
    List<EligiblityBenefitsDetails> eligiblityBenefitsDetails;
    
    public String getControlNumber() {return appointmentId;}
    public void setControlNumber(String appointmentId) {this.appointmentId = appointmentId;}
    public String getCaseId() {return caseId;}
    public void setCaseId(String caseId) {this.caseId = caseId;}
    public String getPatientId() {return patientId;}
    public void setPatientId(String patientId) {this.patientId = patientId;}
    public String getCaseNumber() {return caseNumber;}
    public void setCaseNumber(String caseNumber) {this.caseNumber = caseNumber;}
    public String getPhysicianNPI() {return physicianNPI;}
    public void setPhysicianNPI(String physicianNPI) {this.physicianNPI = physicianNPI;}
    public String getAppointmentId() {return appointmentId;}
    public void setAppointmentId(String appointmentId) {this.appointmentId = appointmentId;}
    public String getPatientDateOfBirth() {return patientDateOfBirth;}
    public void setPatientDateofBirth(String patientDateOfBirth) {this.patientDateOfBirth = patientDateOfBirth;}
    public String getCaseStatus() {return caseStatus;}
    public void setCaseStatus(String caseStatus) {this.caseStatus = caseStatus;}
    
    public CommentInformation getCommentInformation() {return commentInformation;}
    public void setCommentInformation (CommentInformation commentInformation) {this.commentInformation = commentInformation;}
    public List<InsuranceInformation> getInsuranceInformation() {return insuranceInformation;}
    public void setInsuranceInformation(List<InsuranceInformation> insuranceInformation) {this.insuranceInformation = insuranceInformation;}
    public List<ProcedureInformation> getProcedureInformation() {return procedureInformation;}
    public void setProcedureInformation(List<ProcedureInformation> procedureInformation) {this.procedureInformation = procedureInformation;}
    public List<ProcedureAuthorizationInfo> getProcedureAuthorizationInfo() {return procedureAuthorizationInfo;}
    public void setProcedureAuthorizationInfo(List<ProcedureAuthorizationInfo> procedureAuthorizationInfo) {this.procedureAuthorizationInfo = procedureAuthorizationInfo;};
    public List<EligiblityBenefitsDetails> getEligibltyBenefitsDetails() {return eligiblityBenefitsDetails;}
    public void setEligibltyBenefitsDetails(List<EligiblityBenefitsDetails> eligibltyBenefitsDetails) {this.eligiblityBenefitsDetails = eligibltyBenefitsDetails;}
    
    public class InfinixCoverage implements InsuranceAPIResponseData.Coverage
    {

        @Override
        public Boolean isActive() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getEligibilityStatus().equalsIgnoreCase("Eligible");
            else
                return false;
        }

        @Override
        public String getPlanDescription() {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            if (insuranceInformation != null && !insuranceInformation.isEmpty())
                return insuranceInformation.get(0).payerName;
            else
                return "";
        }

        @Override
        public String getPlanNumber() {
            if (insuranceInformation != null && !insuranceInformation.isEmpty())
                return insuranceInformation.get(0).memberId;
            else
                return "";
        }

        @Override
        public Date getEligibilityBeginDate() {
            return null;
        }

        @Override
        public Date getEligibilityEndDate() {
            return null;
        }

        @Override
        public Date getPolicyEffectiveDate() {
            try 
            {
                if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                    return new Date(Long.parseLong(eligiblityBenefitsDetails.get(0).planStart));
                else
                    return null;
            } 
            catch (NumberFormatException ex) 
            {
                return null;
            }
        }

        @Override
        public Date getPolicyExpirationDate() {
            try 
            {
                if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                    return new Date(Long.parseLong(eligiblityBenefitsDetails.get(0).planEnd));
                else
                    return null;
            } 
            catch (NumberFormatException ex) 
            {
                return null;
            }
        }

        @Override
        public List<ServiceType> getServiceTypes() {
            List<ServiceType> list = new ArrayList<>();
            
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
            {
                EligiblityBenefitsDetails benefits = eligiblityBenefitsDetails.get(0);
                
                list.add(new InfinixServiceType("Out of Pocket Max: " + (benefits.getOutofPocketMax() == null ? "N/A" : benefits.getOutofPocketMax()), ""));                
                list.add(new InfinixServiceType("Annual Deductible: " + (benefits.getAnnualDeductible() == null ? "N/A" : benefits.getAnnualDeductible()), ""));
                list.add(new InfinixServiceType("Network Status: " + (benefits.getNetworkStatus() == null ? "N/A" : benefits.getNetworkStatus()), ""));
                list.add(new InfinixServiceType("Coverage Status: " + (benefits.getCoverageStatus() == null ? "N/A" : benefits.getCoverageStatus()), ""));
                list.add(new InfinixServiceType("Plan Type: " + (benefits.getPlanType() == null ? "N/A" : benefits.getPlanType()), ""));
                list.add(new InfinixServiceType("Plan: " + (benefits.getPlan() == null ? "N/A" : benefits.getPlan()), ""));
            }
            else
            {
                list.add(new InfinixServiceType("Out of Pocket Max: N/A", ""));                
                list.add(new InfinixServiceType("Annual Deductible: N/A", ""));
                list.add(new InfinixServiceType("Network Status: N/A", ""));
                list.add(new InfinixServiceType("Coverage Status: N/A", ""));
                list.add(new InfinixServiceType("Plan Type: N/A", ""));
                list.add(new InfinixServiceType("Plan: N/A", ""));
            }
            
            return list;
        }

        @Override
        public String getGroupDescription() {
            if(procedureInformation != null && !procedureInformation.isEmpty())
            {
                return procedureInformation.get(0).getDescription();
            }
            
            return null;
        }

        @Override
        public String getGroupNumber() {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            return caseNumber;
        }

        @Override
        public String getInsuranceType() {
            if (insuranceInformation != null && !insuranceInformation.isEmpty())
                return insuranceInformation.get(0).payerType;
            else
                return "";
        }

        @Override
        public String getCoverageLevel() {return "";}

        @Override
        public Date getServiceDate() {return null;}

        @Override
        public List<Deductible> getDeductibles() {return null;}

        @Override
        public List<Copay> getCopays() {return null;}

        @Override
        public List<Limitations> getLimitations() {return null;}

        @Override
        public List<Coinsurance> getCoinsurances() {return null;}

        @Override
        public Boolean isValidRequest() {
            Boolean retval = false;
            
            if(procedureInformation != null && !procedureInformation.isEmpty())
            {
                retval = true;
            }
            
            return retval;
        }

        @Override
        public String getRejectReason() {
            if (commentInformation != null && commentInformation.getCommentInformation() != null)
            {
                List<Comment> comments = commentInformation.getCommentInformation();
                for (Comment c : comments)
                {
                    String[] split = c.comments.split("; ");
                    for (String s : split)
                    {
                        String[] split1 = s.split("->");
                        if (split1[0].equals("Reason"))
                            return split1[1];
                    }
                }
                
                // If the loop has not returned, reason is null
                return null;
            }
            else
                return null;
        }

        @Override
        public String getFollowupAction() {return null;}
        
    }
    
    public class InfinixServiceType implements InsuranceAPIResponseData.ServiceType, Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        String serviceType;
        String serviceTypeCode;
        
        public InfinixServiceType(String serviceType, String serviceTypeCode) 
        {
            this.serviceType = serviceType;
            this.serviceTypeCode = serviceTypeCode;
        }
        
        @Override
        public String getServiceType() {return serviceType;}
        
        @Override
        public String getServiceTypeCode() {return serviceTypeCode;}
    }
    
    public class InfinixCommentInformation implements InsuranceAPIResponseData.CommentInformation
    {

        @Override
        public List<InsuranceAPIResponseData.Comment> getComments() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class InfinixComment implements InsuranceAPIResponseData.Comment
    {

        @Override
        public int getId() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getComments() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getCreatedOn() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public class InfinixInsuranceInformation implements InsuranceAPIResponseData.InsuranceInformation
    {
        private static final long serialVersionUID = 42L;
        
        @Override
        public String getMemberId() {return insuranceInformation.get(0).getMemberId();}

        @Override
        public String getPayerName() {return insuranceInformation.get(0).getPayerName();}

        @Override
        public String getPayerType() {return insuranceInformation.get(0).getPayerType();}

        @Override
        public String getInsuranceId() {return insuranceInformation.get(0).getInsuranceId();}
        
    }
    
    public class InfinixProcedureInformation implements InsuranceAPIResponseData.ProcedureInformation
    {
        private static final long serialVersionUID = 42L;

        @Override
        public String getQuantityType() {return procedureInformation.get(0).getQuantityType();}

        @Override
        public String getModifier() {return procedureInformation.get(0).getModifier();}
        
        @Override
        public String getUnitsUsed() {return procedureInformation.get(0).getUnitsUsed();}

        @Override
        public String getDescription() {return procedureInformation.get(0).getDescription();}
        
        @Override 
        public String getRequestedQuantity() {return procedureInformation.get(0).getRequestedQuantity();}
        
        @Override
        public String getId() {return procedureInformation.get(0).getId();}

        @Override
        public String getCptCode() {return procedureInformation.get(0).getCptCode();}

        @Override
        public String getBodyPart() {return procedureInformation.get(0).getBodyPart();}
        
    }
    
    public class InfinixProcedureAuthorizationInfo implements InsuranceAPIResponseData.ProcedureAuthorizationInfo
    {
        private static final long serialVersionUID = 42L;

        @Override
        public String getExpiryDate() {return procedureAuthorizationInfo.get(0).getExpiryDate();}

        @Override
        public String getUnitsAuthorized() {return procedureAuthorizationInfo.get(0).getUnitsAuthorized();}
        
        @Override
        public String getDenialReason() {return procedureAuthorizationInfo.get(0).getDenialReason();}

        @Override
        public String getAuthNumber() {return procedureAuthorizationInfo.get(0).getAuthNumber();}

        @Override
        public String getInsuranceId() {return procedureAuthorizationInfo.get(0).getInsuranceId();}
                
        @Override
        public String getId() {return procedureAuthorizationInfo.get(0).getId();}
        
        @Override
        public String getProcedureId() {return procedureAuthorizationInfo.get(0).getProcedureId();}

        @Override
        public String getTrackingNumber() {return procedureAuthorizationInfo.get(0).getTrackingNumber();}

        @Override
        public String getCptCode() {return procedureAuthorizationInfo.get(0).getCptCode();}
        
        @Override
        public String getEffectiveDate() {return  procedureAuthorizationInfo.get(0).getEffectiveDate();}

        @Override
        public String getStatus() {return procedureAuthorizationInfo.get(0).getStatus();}
    }
    
    public class InfinixEligiblityBenefitsDetails implements InsuranceAPIResponseData.EligiblityBenefitsDetails
    {

        @Override
        public String getOutofPocketMax() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getOutofPocketMax();
            else
                return null;
        }

        @Override
        public String getEligibilityStatus() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getEligibilityStatus();
            else
                return null;
        }

        @Override
        public String getAnnualDeductible() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getAnnualDeductible();
            else
                return null;
        }

        @Override
        public String getNetworkStatus() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getNetworkStatus();
            else
                return null;
        }

        @Override
        public String getCoverageStatus() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getCoverageStatus();
            else
                return null;
        }

        @Override
        public String getPlanType() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getPlanType();
            else
                return null;
        }

        @Override
        public String getPlanStart() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getPlanStart();
            else
                return null;
        }

        @Override
        public String getOmJobNumber() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getOmJobNumber();
            else
                return null;
        }

        @Override
        public String getProcedureAuthorizationId() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getProcedureAuthorizationId();
            else
                return null;
        }

        @Override
        public String getPlan() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getPlan();
            else
                return null;
        }

        @Override
        public String getPlanEnd() {
            if (eligiblityBenefitsDetails != null && !eligiblityBenefitsDetails.isEmpty())
                return eligiblityBenefitsDetails.get(0).getPlanEnd();
            else
                return null;
        }
        
    }
    
    public class Address implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String address1;
        String address2;
        String city;
        String state;
        String postalCode;        
        Providers provider;
        
        //**********************************************************************
        public String getAddress1() {return address1;}
        
        //**********************************************************************
        public void setAddress1(String address1){this.address1 = address1;}
    
        //**********************************************************************
        public String getAddress2(String address2) {return address2;}
        
        //**********************************************************************
        public void setAddress2(String address2) {this.address2 = address2;}
        
        //**********************************************************************
        public String getCity() {return city;}
        
        //**********************************************************************
        public void setCity(String city) {this.city = city;}
        
        //**********************************************************************
        public String getState() {return state;}
        
        //**********************************************************************
        public void setState(String state) {this.state = state;}
        
        //**********************************************************************
        public String getPostalCode() {return postalCode;}
        
        //**********************************************************************
        public void setPostalCode(String postalCode) {this.postalCode = postalCode;}
        
        //**********************************************************************
        public Providers getProviders() {return provider;}
        
        //**********************************************************************
        public void setProviders(Providers provider) {this.provider = provider;}
        
        //**********************************************************************
    }   
    
    //**************************************************************************
    public class Providers implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String providerName;
        String npi;
        
        //**********************************************************************
        public String getProviderName(){return providerName;}  
        
        //**********************************************************************
        public void setProviderName(String providerName){this.providerName = providerName;}
        
        //**********************************************************************
        public String getNpi(){return npi;}     
        
        //**********************************************************************
        public void setNpi(String npi){this.npi = npi;}   
        
        //**********************************************************************
    }
    
    public class Subscribers implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String memberId;
        String firstName;
        String lastName;
        String middleName;
        String gender;
        String entityIdentifier;
        String entityType;
        String dateOfBirth;
        String groupNumber;
        String relationToSubscriber;
        String insuredIndicator;
        String maintenanceTypeCode;
        String maintenanceReasonCode;        
        Address address;
        Providers provider;
        
        //**********************************************************************
        public String getMemberId(){return memberId;}      
        
        //**********************************************************************
        public void setMemberId(String memberId){this.memberId = memberId;}
        
        //**********************************************************************
        public String getFirstName(){return firstName;}
        
        //**********************************************************************
        public void setFirstName(String firstName){this.firstName = firstName;}
        
        //**********************************************************************
        public String getLastName(){return lastName;}    
        
        //**********************************************************************
        public void setLastName(String lastName){this.lastName = lastName;}
        
        //**********************************************************************
        public String getMiddleName() {return middleName;}
        
        //**********************************************************************
        public void setMiddleName(String middleName) {this.middleName = middleName;}
        
        //**********************************************************************
        public String getGender() {return gender;}
        
        //**********************************************************************
        public void setGender(String gender) {this.gender = gender;}
        
        //**********************************************************************
        public String getEntityIdentifier(){return entityIdentifier;}        
        
        //**********************************************************************
        public void setEntityIdentifier(String entityIdentifier){this.entityIdentifier = entityIdentifier;}
        
        //**********************************************************************
        public String getEntityType(){return entityType;}        
        
        //**********************************************************************
        public void setEntityType(String entityType){this.entityType = entityType;}
        
        //**********************************************************************
        public String getDateOfBirth(){return dateOfBirth;}        
        
        //**********************************************************************
        public void setDateOfBirth(String dateOfBirth){this.dateOfBirth = dateOfBirth;}      
        
        //**********************************************************************
        public String getGroupNumber() {return groupNumber;}
        
        //**********************************************************************
        public void setGroupNumber(String groupNumber) {this.groupNumber = groupNumber;}
        
        //**********************************************************************
        public String getRelationToSubscriber() {return relationToSubscriber;}
        
        //**********************************************************************
        public void setRelationToSubscriber(String relationToSubscriber) {this.relationToSubscriber = relationToSubscriber;}
        
        //**********************************************************************
        public String getInsuredIndicator() {return insuredIndicator;}
        
        //**********************************************************************
        public void setInsuredIndicator(String insuredIndicator) {this.insuredIndicator = insuredIndicator;}
        
        //**********************************************************************
        public String getMaintenanceTypeCode() {return maintenanceTypeCode;}
        
        //**********************************************************************
        public void setMaintenanceTypeCode(String maintenanceTypeCode) {this.maintenanceTypeCode = maintenanceTypeCode;}
        
        //**********************************************************************
        public String getMaintenanceReasonCode() {return maintenanceReasonCode;}
        
        //**********************************************************************
        public void setMaintenanceReasonCode(String maintenanceReasonCode) {this.maintenanceReasonCode = maintenanceReasonCode;}
        
        //**********************************************************************
        public Address getAddress() {return address;}
        
        //**********************************************************************
        public void setAddress(Address address) {this.address = address;}    
        
        //**********************************************************************
    }
    
    public class CommentInformation implements Serializable
    {
        List<Comment> commentInformation;
        
        public List<Comment> getCommentInformation() {return commentInformation;}
    }
    
    public class Comment implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        int id;
        String comments;
        String createdOn;
        
        public int getId() {return id;}
        public String getComments() {return comments;}
        public String getCreatedOn() {return createdOn;}
    }
    
    public class InsuranceInformation implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        String memberId;
        String payerName;
        String payerType;
        String insuranceId;
        
        public String getMemberId() {return memberId;}
        public String getPayerName() {return payerName;}
        public String getPayerType() {return payerType;}
        public String getInsuranceId() {return insuranceId;}
    }
    
    public class ProcedureInformation implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        String quantityType;
        String modifier;
        String unitsUsed;
        String description;
        String requestedQuantity;
        String id;
        String cptCode;
        String bodyPart;
        
        public String getQuantityType() {return quantityType;}
        public String getModifier() {return modifier;}
        public String getUnitsUsed() {return unitsUsed;}
        public String getDescription() {return description;}
        public String getRequestedQuantity() {return requestedQuantity;}
        public String getId() {return id;}
        public String getCptCode() {return cptCode;}
        public String getBodyPart() {return bodyPart;}
    }
    
    public class ProcedureAuthorizationInfo implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        String expiryDate;
        String unitsAuthorized;
        String denialReason;
        String authNumber;
        String insuranceId;
        String id;
        String procedureId;
        String trackingNumber;
        String cptCode;
        String effectiveDate;
        String status;
        
        public String getExpiryDate() {return expiryDate;}
        public String getUnitsAuthorized() {return unitsAuthorized;}
        public String getDenialReason() {return denialReason;}
        public String getAuthNumber() {return authNumber;}
        public String getInsuranceId() {return insuranceId;}
        public String getId() {return id;}
        public String getProcedureId() {return procedureId;}
        public String getTrackingNumber() {return trackingNumber;}
        public String getCptCode() {return cptCode;}
        public String getEffectiveDate() {return effectiveDate;}
        public String getStatus() {return status;}
    }
    
    // That's just how they spell it
    public class EligiblityBenefitsDetails implements Serializable
    {
        private static final long serialVersionUID = 42L;
        
        String outofPocketMax;
        String eligibilityStatus;
        String annualDeductible;
        String networkStatus;
        String coverageStatus;
        String planType;
        String planStart;
        String omJobNumber;
        String procedureAuthorizationId;
        String plan;
        String planEnd;
        
        public String getOutofPocketMax() {return outofPocketMax;}
        public String getEligibilityStatus() {return eligibilityStatus;}
        public String getAnnualDeductible() {return annualDeductible;}
        public String getNetworkStatus() {return networkStatus;}
        public String getCoverageStatus() {return coverageStatus;}
        public String getPlanType() {return planType;}
        public String getPlanStart() {return planStart;}
        public String getOmJobNumber() {return omJobNumber;}
        public String getProcedureAuthorizationId() {return procedureAuthorizationId;}
        public String getPlan() {return plan;}
        public String getPlanEnd() {return planEnd;}
    }
}
