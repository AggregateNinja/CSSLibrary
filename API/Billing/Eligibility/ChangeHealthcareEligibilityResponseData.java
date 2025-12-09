//******************************************************************************
/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing.Eligibility;

import API.Billing.InsuranceAPIResponseData;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//******************************************************************************
/**
 *
 * @author Mike Douglass
 */
public class ChangeHealthcareEligibilityResponseData implements InsuranceAPIResponseData, Serializable
{
    //**************************************************************************
    private static final long serialVersionUID = 42L;

    //**************************************************************************
    @Override
    public Coverage getCoverage() 
    {
        if(getError() != null)
        {
            return null;
        }
        else
        {
            return new ChangeHealthcareCoverage();
        }
    }

    //**************************************************************************
    @Override
    public Map<String, List<String>> getErrors() 
    {
        Map<String, List<String>> errorFieldAndMessage = new HashMap<String, List<String>>();
        
        List<String> errorMessageList = new ArrayList<String>();
        String errorCode = "";
        
        if(getError() != null)
        {
            Error[] errList = getError();
       
            for(Error err:errList)
            {
                if(err != null && err.getField() != null && !err.getField().equals(""))
                {
                    errorMessageList.add(err.getDescription());
                }
                else if(err != null && err.getCode() != null && !err.getCode().equals(""))
                {
                    errorCode = err.getCode();
                    errorMessageList.add(err.getDescription());
                }
            }
        }
        
        if(getBenefitsInformation() != null)
        {
            BenefitsInformation[] bList = getBenefitsInformation();
                
            for(BenefitsInformation bInfo:bList)
            {
                if(bInfo != null && bInfo.code.equals("V"))
                {
                    if(bInfo.getAdditionalInformation() != null)
                    {
                        AdditionalInformation[] aList = bInfo.getAdditionalInformation();
                        
                        for(AdditionalInformation aInfo:aList)
                        {
                            errorCode = bInfo.getName();
                            
                            errorMessageList.add(aInfo.getDescription());
                        }                        
                    }
                }
            }
        }

        errorFieldAndMessage.put(errorCode, errorMessageList);
        
        return errorFieldAndMessage;
    }

    //**************************************************************************
    @Override
    public Subscriber getSubscriber(){return new ChangeHealthcareSubscriber();}

    //**************************************************************************
    @Override
    public Provider getProvider(){return new ChangeHealthcareProvider();}

    //**************************************************************************
    @Override
    public String getTransactionUUID(){return this.getControlNumber();}

    //**************************************************************************
    @Override
    public void stripProviderData(){}

    //**************************************************************************
    String controlNumber;
    String reassociationKey;
    String tradingPartnerServiceId;    
    Providers provider;
    Subscribers subscriber;
    SubscriberTraceNumbers[] subscriberTraceNumbers;
    Payer payer;
    PlanInformation planInformation;    
    PlanDateInformation planDateInformation;   
    PlanStatus[] planStatus;
    BenefitsInformation[] benefitsInformation;    
    Error[] errors;
    
    //**************************************************************************
    public String getControlNumber(){return controlNumber;} 
    
    //**************************************************************************
    public void setControlNumber(String controlNumber){this.controlNumber = controlNumber;}    
    
    //**************************************************************************
    public String getReassociationKey(){return reassociationKey;}  
    
    //**************************************************************************
    public void setReassociationKey(String reassociationKey){this.reassociationKey = reassociationKey;}    
    
    //**************************************************************************
    public String getTradingPartnerServiceId(){return tradingPartnerServiceId;}   
    
    //**************************************************************************
    public void setTradingPartnerServiceId(String tradingPartnerServiceId){this.tradingPartnerServiceId = tradingPartnerServiceId;}
    
    //**************************************************************************
    public Providers getProviders(){return provider;}    
    
    //**************************************************************************
    public void setProviders(Providers provider){this.provider = provider;}
    
    //**************************************************************************
    public Subscribers getSubscribers(){return subscriber;}    
    
    //**************************************************************************
    public void setSubscribers(Subscribers subscriber){this.subscriber = subscriber;}
    
    //**************************************************************************
    public SubscriberTraceNumbers[] getSubscriberTraceNumbers(){return subscriberTraceNumbers;}
    
    //**************************************************************************
    public void setSubscriberTraceNumbers(SubscriberTraceNumbers[] subscriberTraceNumbers){this.subscriberTraceNumbers = subscriberTraceNumbers;}
    
    //**************************************************************************
    public Payer getPayer(){return payer;}    
    
    //**************************************************************************
    public void setPayer(Payer payer){this.payer = payer;}
    
  //**************************************************************************
    public PlanInformation getPlanInformation() {return planInformation;}
    
    //**************************************************************************
    public void setPlanInformation(PlanInformation planInformation) {this.planInformation = planInformation;}
    
    //**************************************************************************
    public PlanDateInformation getPlanDateInformation() {return planDateInformation;}
    
    //**************************************************************************
    public void setPlanDateInformation(PlanDateInformation planDateInformation) {this.planDateInformation = planDateInformation;}
    
    //**************************************************************************
    public PlanStatus[] getPlanStatus() {return planStatus;}
    
    //**************************************************************************
    public void setPlanStatus(PlanStatus[] planStatus) {this.planStatus = planStatus;}
    
    //**************************************************************************
    public BenefitsInformation[] getBenefitsInformation() {return benefitsInformation;}
    
    //**************************************************************************
    public void setBenefitsInformation(BenefitsInformation[] benefitsInformation) {this.benefitsInformation = benefitsInformation;}            
    
    //**************************************************************************
    public Error[] getError(){return errors;}    
    
    //**************************************************************************
    public void setError(Error[] errors){this.errors = errors;}  
    
    //**************************************************************************
    public void setResponseData(ChangeHealthcareEligibilityResponseData responseData)
    {
        this.controlNumber = responseData.controlNumber;
        this.reassociationKey = responseData.reassociationKey;
        this.tradingPartnerServiceId = responseData.tradingPartnerServiceId;
        
        this.provider = responseData.provider;
        this.subscriber = responseData.subscriber;
        this.subscriberTraceNumbers = responseData.subscriberTraceNumbers;
        this.payer = responseData.payer;
        this.errors = responseData.errors;
    }    
    
    //**************************************************************************
    public class ChangeHealthcareSubscriber implements InsuranceAPIResponseData.Subscriber, Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        //**********************************************************************
        @Override
        public String getGender(){return subscriber.getGender();}

        //**********************************************************************
        @Override
        public String getFirstName(){return subscriber.getFirstName();}

        //**********************************************************************
        @Override
        public String getLastName(){return subscriber.getLastName();}

        //**********************************************************************
        @Override
        public String getID(){return subscriber.getMemberId();}

        //**********************************************************************
        @Override
        public String getGroupNumber(){return subscriber.getGroupNumber();} 
        
        //**********************************************************************
        @Override
        public String getDateOfBirth(){return subscriber.getDateOfBirth();}
        
        //**********************************************************************
        @Override
        public String getEntityType(){return subscriber.getEntityType();}
        
        //**********************************************************************
        @Override
        public String getEntityIdentifier(){return subscriber.getEntityIdentifier();}
        
        //**********************************************************************
        @Override
        public String getMemberId(){return subscriber.getMemberId();}   
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class ChangeHealthcareProvider implements InsuranceAPIResponseData.Provider, Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        //**********************************************************************
        @Override
        public String getNPI(){return provider.getNpi();}

        //**********************************************************************
        @Override
        public String getFirstName(){return "";}

        //**********************************************************************
        @Override
        public String getLastName(){return "";}

        //**********************************************************************
        @Override
        public String getOrganizationName(){return provider.getProviderName();}
        
        //**********************************************************************
        @Override
        public String getProviderName(){return provider.getProviderName();}
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class ChangeHealthcareCoverage implements InsuranceAPIResponseData.Coverage, Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        //**********************************************************************
        @Override
        public Boolean isActive()
        {
            Boolean retval = false;
            
            if(getPlanStatus() != null)
            {
                PlanStatus[] pList = getPlanStatus();
                
                for(PlanStatus pInfo:pList)
                {
                    if(pInfo.getStatusCode().equals("1"))
                    {
                        return true;
                    }
                }
            }

            return retval;
        }
        
        //**********************************************************************
        @Override
        public String getPlanDescription()
        {
            String retval = "";
            
            if(getBenefitsInformation() != null)
            {
                BenefitsInformation[] bList = getBenefitsInformation();
                
                for(BenefitsInformation bInfo:bList)
                {
                    System.out.println("Plan Description:" + bInfo.insuranceTypeCode);
                    System.out.println("Plan retval:" + retval);
                    if(getTradingPartnerServiceId().trim().equals("CMSMED"))
                    {
                        if(bInfo.getCode().equals("1") && bInfo.getInsuranceTypeCode().equals("MB"))
                        {
                            //return bInfo.planCoverage;
                            retval += bInfo.planCoverage;
                            retval += ": ";
                    
                        }
                    }
                    else
                    {
                        if(bInfo.getCode().equals("1"))
                        {
                            retval += bInfo.planCoverage;
                            retval += ": ";
                            //return bInfo.planCoverage;
                        }
                    }
                }
            }
            
            return retval;
        }
        
        //**********************************************************************
        @Override
        public String getPlanNumber()
        {
            if(subscriber != null && subscriber.getMemberId() != null)
            {
                return subscriber.getMemberId();
            }
            return null;
        }
        
        //**********************************************************************
        @Override
        public Date getEligibilityBeginDate()
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try 
            {
                return planDateInformation == null || planDateInformation.eligibilityBegin == null ? null : sdf.parse(planDateInformation.eligibilityBegin);
            } 
            catch (ParseException ex) 
            {
                return null;
            }
        }
        
        //**********************************************************************
        @Override
        public Date getEligibilityEndDate()
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try 
            {
                return planDateInformation == null || planDateInformation.planEnd == null ? null : sdf.parse(planDateInformation.planEnd);
            } 
            catch (ParseException ex) 
            {
                return null;
            }
        }
        
        //**********************************************************************
        @Override
        public Date getPolicyEffectiveDate()
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try 
            {
                return planDateInformation == null || planDateInformation.planBegin == null ? null : sdf.parse(planDateInformation.planBegin);
            } 
            catch (ParseException ex) 
            {
                return null;
            }
        }                
        
        //**********************************************************************
        @Override
        public Date getPolicyExpirationDate()
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try 
            {
                return planDateInformation == null || planDateInformation.planEnd == null ? null : sdf.parse(planDateInformation.planEnd);
            } 
            catch (ParseException ex) 
            {
                return null;
            }
        }
        
        //**********************************************************************
        @Override
        public List<ServiceType> getServiceTypes()
        {
             List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            
            if(getBenefitsInformation() != null)
            {
                BenefitsInformation[] bList = getBenefitsInformation();
                
                for(BenefitsInformation bInfo:bList)
                {
                    if(getTradingPartnerServiceId().trim().equals("CMSMED"))
                    {
                        if(bInfo != null && bInfo.getCode().equals("1") && bInfo.getInsuranceTypeCode().equals("MB"))
                        {
                            String[] sTypes = bInfo.getServiceTypes();
                            String[] sCodes = bInfo.getServiceTypeCodes();
                        
                            if(sTypes != null && sCodes != null)
                            {
                                for(int idx = 0; idx < sTypes.length; idx++)
                                {
                                    ChangeHealthcareServiceType serviceType = new ChangeHealthcareServiceType(sTypes[idx], sCodes[idx]);
                                    serviceTypeList.add(serviceType);
                                }
                            }
                        }
                    }
                    else
                    {
                        if(bInfo != null && bInfo.code.equals("1"))
                        {
                            String[] sTypes = bInfo.getServiceTypes();
                            String[] sCodes = bInfo.getServiceTypeCodes();
                        
                            if(sTypes != null && sCodes != null)
                            {
                                for(int idx = 0; idx < sTypes.length; idx++)
                                {
                                    ChangeHealthcareServiceType serviceType = new ChangeHealthcareServiceType(sTypes[idx], sCodes[idx]);
                                    serviceTypeList.add(serviceType);
                                }
                            }
                        }                        
                    }
                }
            }
                        
            return serviceTypeList;
        }
        
        //**********************************************************************
        @Override
        public String getGroupDescription()
        {
            if(planInformation != null && planInformation.getPlanNetworkIdNumber() != null)
            {
                return planInformation.getPlanNetworkIdNumber();
            }
            
            return null;
        }
        
        //**********************************************************************
        @Override
        public String getGroupNumber()
        {
            if(subscriber != null && subscriber.getGroupNumber() != null)
            {
                return subscriber.getGroupNumber();
            }
            return null;
        }
        
        //**********************************************************************
        @Override
        public String getInsuranceType()
        {
            String retval = "";
            
            if(getBenefitsInformation() != null)
            {
                BenefitsInformation[] bList = getBenefitsInformation();
                
                for(BenefitsInformation bInfo:bList)
                {
                    System.out.println("Benefits Info:" + bInfo.insuranceTypeCode);
                    System.out.println("Benefits return:" + retval);
                    if(getTradingPartnerServiceId().trim().equals("CMSMED"))
                    {                        
                        if(bInfo != null && bInfo.getCode().equals("1") && bInfo.getInsuranceTypeCode().equals("MB"))
                        {
                            //return bInfo.insuranceType;
                            retval += bInfo.insuranceType;
                            retval += ": ";                    
                        }
                    }
                    else
                    {
                        if(bInfo != null && bInfo.code.equals("1"))
                        {
                            retval += bInfo.insuranceType;
                            retval += ": ";
                            //return bInfo.insuranceType;
                        }
                    }
                }
            }
            
            return retval;        
        }
        
        //**********************************************************************
        @Override
        public String getCoverageLevel(){return "";}
        
        //**********************************************************************
        @Override
        public Date getServiceDate(){return null;}
        
        //**********************************************************************
        @Override
        public List<Deductible> getDeductibles(){return null;}
        
        //**********************************************************************
        @Override
        public List<Copay> getCopays(){return null;}
        
        //**********************************************************************
        @Override
        public List<Limitations> getLimitations(){return null;}
        
        //**********************************************************************
        @Override
        public List<Coinsurance> getCoinsurances(){return null;}
        
        //**********************************************************************
        @Override
        public Boolean isValidRequest()
        {
            Boolean retval = false;
            
            if(planInformation != null)
            {
                retval = true;
            }
            
            return retval;
        }
        
        //**********************************************************************
        @Override
        public String getRejectReason(){return null;}
        
        //**********************************************************************
        @Override
        public String getFollowupAction(){return null;}
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class ChangeHealthcareServiceType implements InsuranceAPIResponseData.ServiceType, Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        String serviceType;
        String serviceTypeCode;
        
        //**********************************************************************
        public ChangeHealthcareServiceType(String serviceType, String serviceTypeCode) 
        {
            this.serviceType = serviceType;
            this.serviceTypeCode = serviceTypeCode;
        }
        
        //**********************************************************************
        @Override
        public String getServiceType() {return serviceType;}

        //**********************************************************************
        @Override
        public String getServiceTypeCode() {return serviceTypeCode;}
        
        //**********************************************************************
    }
     
    //**************************************************************************
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
    
    //**************************************************************************
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
    
    //**************************************************************************
    public class SubscriberTraceNumbers implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String traceTypeCode;
        String traceType;
        String referenceIdentification;
        String originatingCompanyIdentifier;
        String secondaryReferenceIdentification;
        
        //**********************************************************************
        public String getTraceTypeCode(){return traceTypeCode;}    
        
        //**********************************************************************
        public void setTraceTypeCode(String traceTypeCode){this.traceTypeCode = traceTypeCode;}
        
        //**********************************************************************
        public String getTraceType(){return traceType;}  
        
        //**********************************************************************
        public void setTraceType(String traceType){this.traceType = traceType;}
        
        //**********************************************************************
        public String getReferenceIdentification(){return referenceIdentification;}   
        
        //**********************************************************************
        public void setReferenceIdentification(String referenceIdentification){this.referenceIdentification = referenceIdentification;}
        
        //**********************************************************************
        public String getOriginatingCompanyIdentifier(){return originatingCompanyIdentifier;}    
        
        //**********************************************************************
        public void setOriginatingCompanyIdentifier(String originatingCompanyIdentifier){this.originatingCompanyIdentifier = originatingCompanyIdentifier;} 
        
        //**********************************************************************
        public String getSecondaryReferenceIdentification(){return secondaryReferenceIdentification;}
        
        //**********************************************************************
        public void setSecondaryReferenceIdentification(String secondaryReferenceIdentification) {this.secondaryReferenceIdentification = secondaryReferenceIdentification;}   
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class Payer implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String entityIdentifier;
        String entityType;
        String name;
        String federalTaxpayersIdNumber;
        String payorIdentification;
        ContactInformation contactInformation;
        
        //**********************************************************************
        public String getEntityIdentifier(){return entityIdentifier;}     
        
        //**********************************************************************
        public void setEntityIdentifier(String entityIdentifier){this.entityIdentifier = entityIdentifier;}
        
        //**********************************************************************
        public String getEntityType(){return entityType;}  
        
        //**********************************************************************
        public void setEntityType(String entityType){this.entityType = entityType;}
        
        //**********************************************************************
        public String getName(){return name;}   
        
        //**********************************************************************
        public void setName(String name){this.name = name;}
        
        //**********************************************************************
        public String getFederalTaxpayersIdNumber() {return federalTaxpayersIdNumber;}
        
        //**********************************************************************
        public void setFederalTaxpayersIdNumber(String federalTaxpayersIdNumber) {this.federalTaxpayersIdNumber = federalTaxpayersIdNumber;}
        
        //**********************************************************************
        public String getPayorIdentification(){return payorIdentification;}       
        
        //**********************************************************************
        public void setPayorIdentification(String payorIdentification){this.payorIdentification = payorIdentification;}
        
        //**********************************************************************
        public ContactInformation getContactInformation(){return contactInformation;}        
        
        //**********************************************************************
        public void setContactInformation(ContactInformation contactInformation){this.contactInformation = contactInformation;}
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class ContactInformation implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;

        String name;
        Contacts[] contacts;

        //**********************************************************************
        public String getName() {return name;}
        
        //**********************************************************************
        public void setName(String name) {this.name = name;}
        
        //**********************************************************************
        public Contacts[] getContacts(){return contacts;}
        
        //**********************************************************************
        public void setContacts(Contacts[] contacts){this.contacts = contacts;}
        
        //**********************************************************************
    }    
    
    //**************************************************************************
    public class Contacts implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;

        String communicationMode;
        String communicationNumber;

        //**********************************************************************
        public String getCommunicationMode(){return communicationMode;}
        
        //**********************************************************************
        public void setCommunicationMode(String communicationMode){this.communicationMode = communicationMode;}

        //**********************************************************************
        public String getCommunicationNumber(){return communicationNumber;}
        
        //**********************************************************************
        public void setCommunicationNumber(String communicationNumber){this.communicationNumber = communicationNumber;}
        
        //**********************************************************************
    }    
    
    //**************************************************************************
    public class Error implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String field;
        String description;
        String code;
        String followupAction;
        String location;
        
        //**********************************************************************
        public String getField(){return field;}  
        
        //**********************************************************************
        public void setField(String field){this.field = field;}
        
        //**********************************************************************
        public String getDescription(){return description;}        
        
        //**********************************************************************
        public void setDescription(String description){this.description = description;}
        
        //**********************************************************************
        public String getCode(){return code;}  
        
        //**********************************************************************
        public void setCode(String code){this.code = code;}
        
        //**********************************************************************        
        public String getFollowupAction(){return followupAction;}  
        
        //**********************************************************************
        public void setFollowupAction(String followupAction){this.followupAction = followupAction;}
        
        //**********************************************************************
        public String getLocation(){return location;}        
        
        //**********************************************************************
        public void setLocation(String location){this.location = location;}  
        
        //**********************************************************************
    }   
    
    //**************************************************************************
    public class PlanInformation implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String planInformation;
        String planNetworkIdNumber;
        
        //**********************************************************************
        public String getPlanInformation() {return planInformation;}
        
        //**********************************************************************
        public void setPlanInformation(String planInformation) {this.planInformation = planInformation;}
        
        //**********************************************************************
        public String getPlanNetworkIdNumber() {return planNetworkIdNumber;}
        
        //**********************************************************************
        public void setPlanNetworkIdNumber(String planNetworkIdNumber) {this.planNetworkIdNumber = planNetworkIdNumber;}   
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class PlanStatus implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String statusCode;
        String status;
        String planDetails;
        String[] serviceTypeCodes;
        
        //**********************************************************************
        public String getStatusCode(){return statusCode;}
        
        //**********************************************************************
        public void setStatusCode(String statusCode) {this.statusCode = statusCode;}
        
        //**********************************************************************
        public String getStatus() {return status;}
        
        //**********************************************************************
        public void setStatus(String status) {this.status = status;}
        
        //**********************************************************************
        public String getPlanDetails() {return planDetails;}
        
        //**********************************************************************
        public void setPlanDetails(String planDetails) {this.planDetails = planDetails;}
        
        //**********************************************************************
        public String[] getServiceTypeCodes() {return serviceTypeCodes;}
        
        //**********************************************************************
        public void setServiceTypeCodes(String[] serviceTypeCodes) {this.serviceTypeCodes = serviceTypeCodes;}
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class BenefitsInformation implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String code;
        String name;
        String[] serviceTypeCodes;
        String[] serviceTypes;
        String insuranceTypeCode;
        String insuranceType;
        String planCoverage;
	String coverageLevelCode;
	String coverageLevel;
	String timeQualifierCode;
	String timeQualifier;
	String benefitAmount;
	String inPlanNetworkIndicatorCode;
	String inPlanNetworkIndicator;   
        AdditionalInformation[] additionalInformation;
        
        //**********************************************************************
        public String getCode() {return code;}
        
        //**********************************************************************
        public void setCode(String code) {this.code = code;}
        
        //**********************************************************************
        public String getName() {return name;}
        
        //**********************************************************************
        public void setName(String name) {this.name = name;}
        
        //**********************************************************************
        public String[] getServiceTypeCodes() {return serviceTypeCodes;}
        
        //**********************************************************************
        public void setServiceTypeCodes(String[] serviceTypeCodes) {this.serviceTypeCodes = serviceTypeCodes;}
        
        //**********************************************************************
        public String[] getServiceTypes() {return serviceTypes;}
        
        //**********************************************************************
        public void setServiceTypes(String[] serviceTypes) {this.serviceTypes = serviceTypes;}
        
        //**********************************************************************
        public String getInsuranceTypeCode() {return insuranceTypeCode;}
        
        //**********************************************************************
        public void setInsuranceTypeCode(String insuranceTypeCode) {this.insuranceTypeCode = insuranceTypeCode;}
        
        //**********************************************************************
        public String getInsuranceType() {return insuranceType;}
        
        //**********************************************************************
        public void setInsuranceType(String insuranceType) {this.insuranceType = insuranceType;}
        
        //**********************************************************************
        public String getPlanCoverage() {return planCoverage;}
        
        //**********************************************************************
        public void setPlanCoverage(String planCoverage) {this.planCoverage = planCoverage;}
        
        //**********************************************************************
        public String getCoverageLevelCode() {return coverageLevelCode;}
        
        //**********************************************************************
        public void setCoverageLevelCode(String coverageLevelCode){this.coverageLevelCode = coverageLevelCode;}
        
        //**********************************************************************
        public String getCoverageLevel() {return coverageLevel;}
        
        //**********************************************************************
        public void setCoverageLevel(String coverageLevel) {this.coverageLevel = coverageLevel;}
        
        //**********************************************************************
        public String getTimeQualifierCode() {return timeQualifierCode;}
        
        //**********************************************************************
        public void setTimeQualifierCode(String timeQualifierCode) {this.timeQualifierCode = timeQualifierCode;}
        
        //**********************************************************************
        public String getTimeQualifier() {return timeQualifier;}
        
        //**********************************************************************
        public void setTimeQualifier(String timeQualifier) {this.timeQualifier = timeQualifier;}
        
        //**********************************************************************
        public String getBenefitAmount() {return benefitAmount;}
        
        //**********************************************************************
        public void setBenefitAmount(String benefitAmount) {this.benefitAmount = benefitAmount;}
        
        //**********************************************************************
        public String getInPlanNetworkIndicatorCode() {return inPlanNetworkIndicatorCode;}
        
        //**********************************************************************
        public void setInPlanNetworkIndicatorCode(String inPlanNetworkIndicatorCode) {this.inPlanNetworkIndicatorCode = inPlanNetworkIndicatorCode;}
        
        //**********************************************************************
        public String getInPlanNetworkIndicator() {return inPlanNetworkIndicator;}
        
        //**********************************************************************
        public void setInPlanNetworkIndicator(String inPlanNetworkIndicator) {this.inPlanNetworkIndicator = inPlanNetworkIndicator;}
        
        //**********************************************************************
        public AdditionalInformation[] getAdditionalInformation() {return additionalInformation;}
        
        //**********************************************************************
        public void setAdditionalInformation(AdditionalInformation[] additionalInformation) {this.additionalInformation = additionalInformation;}
        
        //**********************************************************************
    }
    
    //**************************************************************************
    public class PlanDateInformation implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String planBegin;
        String planEnd;
        String eligibilityBegin;
        
        //**********************************************************************
        public String getPlanBegin() {return planBegin;}
        
        //**********************************************************************
        public void setPlanBegin(String planBegin) {this.planBegin = planBegin;}
        
        //**********************************************************************
        public String getPlanEnd() {return planEnd;}
        
        //**********************************************************************
        public void setPlanEnd(String planEnd) {this.planEnd = planEnd;}
        
        //**********************************************************************
        public String getEligibilityBegin() {return eligibilityBegin;}
        
        //**********************************************************************
        public void setEligibilityBegin(String eligibilityBegin) {this.eligibilityBegin = eligibilityBegin;}
        
        //**********************************************************************
    }  
    
    //**************************************************************************
    public class AdditionalInformation implements Serializable
    {
        //**********************************************************************
        private static final long serialVersionUID = 42L;
        
        String description;
        
        //**********************************************************************
        public String getDescription() {return description;}
        
        //**********************************************************************
        public void setDescription(String description) {this.description = description;}
        
        //**********************************************************************
    }
    
    //**************************************************************************
}

//******************************************************************************
//******************************************************************************
//******************************************************************************