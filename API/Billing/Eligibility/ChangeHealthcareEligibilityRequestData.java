//******************************************************************************
/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing.Eligibility;

import API.Billing.InsuranceAPIRequestData;
import java.io.Serializable;
import java.util.List;

//******************************************************************************
/**
 *
 * @author Mike Douglass
 */
public class ChangeHealthcareEligibilityRequestData implements InsuranceAPIRequestData, Serializable
{
    //**************************************************************************
    private static final long serialVersionUID = 42L;
    
    String controlNumber;
    String tradingPartnerServiceId;
    
    Provider provider;
    Subscriber subscriber;
    Dependents dependents;
    Encounter encounter;

    //**************************************************************************
    public void setRequestData(ChangeHealthcareEligibilityRequestData requestData)
    {
        this.controlNumber = requestData.controlNumber;
        this.tradingPartnerServiceId = requestData.tradingPartnerServiceId;
        this.provider = requestData.provider;
        this.subscriber = requestData.subscriber;                
        this.dependents = requestData.dependents;    
        this.encounter = requestData.encounter;
    }
    
    //**************************************************************************
     public static class Dependents
    {
        //**********************************************************************
        String memberId;
        String firstName;
        String lastName;
        String gender;
        String dateOfBirth;
        String ssn;
        String groupNumber;
        String idCard;

        //**********************************************************************
        public String getMemberId() 
        {
            return memberId;
        }

        //**********************************************************************
        public void setMemberId(String memberId) 
        {
            this.memberId = memberId;
        }

        //**********************************************************************
        public String getFirstName() 
        {
            return firstName;
        }

        //**********************************************************************
        public void setFirstName(String firstName) 
        {
            this.firstName = firstName;
        }

        //**********************************************************************
        public String getLastName() 
        {
            return lastName;
        }

        //**********************************************************************
        public void setLastName(String lastName) 
        {
            this.lastName = lastName;
        }

        //**********************************************************************
        public String getGender() 
        {
            return gender;
        }

        //**********************************************************************
        public void setGender(String gender) 
        {
            this.gender = gender;
        }

        //**********************************************************************
        public String getDateOfBirth() 
        {
            return dateOfBirth;
        }

        //**********************************************************************
        public void setDateOfBirth(String dateOfBirth) 
        {
            this.dateOfBirth = dateOfBirth;
        }

        //**********************************************************************
        public String getSsn() 
        {
            return ssn;
        }

        //**********************************************************************
        public void setSsn(String ssn) 
        {
            this.ssn = ssn;
        }

        //**********************************************************************
        public String getGroupNumber() 
        {
            return groupNumber;
        }

        //**********************************************************************
        public void setGroupNumber(String groupNumber) 
        {
            this.groupNumber = groupNumber;
        }

        //**********************************************************************
        public String getIdCard() 
        {
            return idCard;
        }

        //**********************************************************************
        public void setIdCard(String idCard) 
        {
            this.idCard = idCard;
        }
        //**********************************************************************
    }
     
    //**************************************************************************
    public static class Encounter
    {
        //**********************************************************************
        String beginningDateOfService;
        String dateOfService;
        Boolean dateRange;
        String endDateOfService;
        String[] serviceTypeCodes;

        //**********************************************************************
        public String getBeginningDateOfService() 
        {
            return beginningDateOfService;
        }

        //**********************************************************************
        public void setBeginningDateOfService(String beginningDateOfService) 
        {
            this.beginningDateOfService = beginningDateOfService;
        }

        //**********************************************************************
        public String getEndDateOfService() 
        {
            return endDateOfService;
        }

        //**********************************************************************
        public void setEndDateOfService(String endDateOfService) 
        {
            this.endDateOfService = endDateOfService;
        }

        //**********************************************************************
        public String[] getServiceTypeCodes() 
        {
            return serviceTypeCodes;
        }

        //**********************************************************************
        public void setServiceTypeCodes(String[] serviceTypeCodes) 
        {
            this.serviceTypeCodes = serviceTypeCodes;
        }
        
        //**********************************************************************
        public String getDateOfService()
        {
            return dateOfService;
        }
        
        //**********************************************************************
        public void setDateOfService(String dateOfService)
        {
            this.dateOfService = dateOfService;
        }
        
        //**********************************************************************
        public Boolean getDateRange()
        {
            return dateRange;
        }
        
        //**********************************************************************
        public void setDateRange(Boolean dateRange)
        {
            this.dateRange = dateRange;
        }
        //**********************************************************************
    }
    
    //**************************************************************************
    public static class Provider
    {
        //**********************************************************************
        String organizationName;
        String firstName;
        String lastName;
        String npi;
        String serviceProviderNumber;
        String payorId;
        String taxId;
        String providerCode;
        String referenceIdentification;

        //**********************************************************************
        public String getOrganizationName()
        {
            return organizationName;
        }

        //**********************************************************************
        public void setOrganizationName(String organizationName) 
        {
            this.organizationName = organizationName;
        }

        //**********************************************************************
        public String getFirstName() 
        {
            return firstName;
        }

        //**********************************************************************
        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        //**********************************************************************
        public String getLastName() 
        {
            return lastName;
        }

        //**********************************************************************
        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        //**********************************************************************
        public String getNpi()
        {
            return npi;
        }

        //**********************************************************************
        public void setNpi(String npi)
        {
            this.npi = npi;
        }

        //**********************************************************************
        public String getServiceProviderNumber()
        {
            return serviceProviderNumber;
        }

        //**********************************************************************
        public void setServiceProviderNumber(String serviceProviderNumber)
        {
            this.serviceProviderNumber = serviceProviderNumber;
        }

        //**********************************************************************
        public String getPayorId() 
        {
            return payorId;
        }

        //**********************************************************************
        public void setPayorId(String payorId) 
        {
            this.payorId = payorId;
        }

        //**********************************************************************
        public String getTaxId()
        {
            return taxId;
        }

        //**********************************************************************
        public void setTaxId(String taxId) 
        {
            this.taxId = taxId;
        }

        //**********************************************************************
        public String getProviderCode() 
        {
            return providerCode;
        }

        //**********************************************************************
        public void setProviderCode(String providerCode) 
        {
            this.providerCode = providerCode;
        }

        //**********************************************************************
        public String getReferenceIdentification() 
        {
            return referenceIdentification;
        }

        //**********************************************************************
        public void setReferenceIdentification(String referenceIdentification) 
        {
            this.referenceIdentification = referenceIdentification;
        }
        //**********************************************************************
    }
    
    //**************************************************************************
    public static class Subscriber
    {
        //**********************************************************************
        String memberId;
        String firstName;
        String lastName;
        String gender;
        String dateOfBirth;
        String ssn;
        String groupNumber;
        String idCard;

        //**********************************************************************
        public String getMemberId()
        {
            return memberId;
        }

        //**********************************************************************
        public void setMemberId(String memberId)
        {
            this.memberId = memberId;
        }

        //**********************************************************************
        public String getFirstName()
        {
            return firstName;
        }

        //**********************************************************************
        public void setFirstName(String firstName) 
        {
            this.firstName = firstName;
        }

        //**********************************************************************
        public String getLastName() 
        {
            return lastName;
        }

        //**********************************************************************
        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        //**********************************************************************
        public String getGender()
        {
            return gender;
        }

        //**********************************************************************
        public void setGender(String gender) 
        {
            this.gender = gender;
        }

        //**********************************************************************
        public String getDateOfBirth() 
        {
            return dateOfBirth;
        }

        //**********************************************************************
        public void setDateOfBirth(String dateOfBirth) 
        {
            this.dateOfBirth = dateOfBirth;
        }

        //**********************************************************************
        public String getSsn() 
        {
            return ssn;
        }

        //**********************************************************************
        public void setSsn(String ssn) 
        {
            this.ssn = ssn;
        }

        //**********************************************************************
        public String getGroupNumber() 
        {
            return groupNumber;
        }

        //**********************************************************************
        public void setGroupNumber(String groupNumber) 
        {
            this.groupNumber = groupNumber;
        }

        //**********************************************************************
        public String getIdCard()
        {
            return idCard;
        }

        //**********************************************************************
        public void setIdCard(String idCard) 
        {
            this.idCard = idCard;
        }
        //**********************************************************************
    }
   
    //**************************************************************************
    public String getControlNumber()
    {
        return controlNumber;
    }

    //**************************************************************************
    public void setControlNumber(String controlNumber)
    {
        this.controlNumber = controlNumber;
    }

    //**************************************************************************
    public Provider getProvider()
    {
        return provider;
    }
    
    //**************************************************************************
    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }
    
    //**************************************************************************
    public Subscriber getSubscriber()
    {
        return subscriber;
    }
    
    //**************************************************************************
    public void setSubscriber(Subscriber subscriber)
    {
        this.subscriber = subscriber;
    }
    
    //**************************************************************************
    public Dependents getDependents()
    {
        return dependents;
    }
    
    //**************************************************************************
    public void setDependents(Dependents dependents)
    {
        this.dependents = dependents;
    }
    
    //**************************************************************************
    public Encounter getEncounter()
    {
        return encounter;
    }
    
    //**************************************************************************
    public void setEncounter(Encounter encounter)
    {
        this.encounter = encounter;
    }

    //**************************************************************************
    public String getTradingPartnerServiceId()
    {
        return tradingPartnerServiceId;
    }
    
    //**************************************************************************
    public void setTradingPartnerServiceId(String tradingPartnerServiceId)
    {
        this.tradingPartnerServiceId = tradingPartnerServiceId;
    }
    
    //**************************************************************************
}

//******************************************************************************
//******************************************************************************
//******************************************************************************
