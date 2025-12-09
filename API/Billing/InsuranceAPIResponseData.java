/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rob
 */
public interface InsuranceAPIResponseData
{
    public String getTransactionUUID();
    public Coverage getCoverage();
    public Subscriber getSubscriber();
    public Provider getProvider();
    public Map<String, List<String>> getErrors();
    public void stripProviderData();
    
    public interface ServiceType
    {
        public String getServiceType();
        public String getServiceTypeCode();
    }
    
    public interface Error
    {
        public String getErrorField();
        public String getErrorMessage();
    }
    
    public interface Coverage
    {
        public Boolean isActive();
        public String getPlanDescription();
        public String getPlanNumber();
        public Date getEligibilityBeginDate();
        public Date getEligibilityEndDate();
        public Date getPolicyEffectiveDate();
        public Date getPolicyExpirationDate();
        public List<ServiceType> getServiceTypes();
        public String getGroupDescription();
        public String getGroupNumber();
        public String getInsuranceType();
        public String getCoverageLevel();
        public Date getServiceDate();
        public List<Deductible> getDeductibles();
        public List<Copay> getCopays();
        public List<Limitations> getLimitations();
        public List<Coinsurance> getCoinsurances();
        public Boolean isValidRequest();
        public String getRejectReason();
        public String getFollowupAction();
    }
    
    public interface Deductible
    {
        public String getPlanDescription();
        public Date getEligibilityDate();
        public List<ServiceType> getServiceTypes();
        public BigDecimal getBenefitAmount();
        public String getCoverageLevel();
        public Boolean isInPlanNetwork();
        public String getTimePeriod();
        public List<String> getMessages();
    }
    
    public interface Copay
    {
        public String getPlanDescription();
        public String getCoverageLevel();
        public BigDecimal getAmount();
        public Boolean isInPlanNetwork();
        public List<ServiceType> getServiceTypes();
        public List<String> getMessages();
    }
    
    public interface Limitations
    {
        public String getPlanDescription();
        public List<ServiceType> getServiceTypes();
        public String getCoverageLevel();
        public List<String> getMessages();
    }
    
    public interface Coinsurance
    {
        public String getPlanDescription();
        public List<ServiceType> getServiceTypes();
        public BigDecimal getBenefitAmount();
        public String getCoverageLevel();
        public Boolean isInPlanNetwork();
        public List<String> getMessages();
    }
    
    public interface Provider
    {
        public String getNPI();
        public String getFirstName();
        public String getLastName();
        public String getOrganizationName();
        public String getProviderName();
    }
    
    public interface Subscriber
    {
        public String getGender();
        public String getFirstName();
        public String getLastName();
        public String getID();
        public String getGroupNumber();
        public String getMemberId();
        public String getEntityIdentifier();
        public String getEntityType();
        public String getDateOfBirth();
    }
    
    // To be used with Infinix files
    public interface CommentInformation
    {
        public List<Comment> getComments();
    }
    
    // To be used with Infinix files
    public interface Comment
    {
        public int getId();
        public String getComments();
        public String getCreatedOn();
    }
    
    // To be used with Infinix files
    public interface InsuranceInformation
    {
        public String getMemberId();
        public String getPayerName();
        public String getPayerType();
        public String getInsuranceId();
    }
    
    // To be used with Infinix files
    public interface ProcedureInformation
    {
        public String getQuantityType();
        public String getModifier();
        public String getUnitsUsed();
        public String getDescription();
        public String getRequestedQuantity();
        public String getId();
        public String getCptCode();
        public String getBodyPart();
    }
    
    // To be used with Infinix files
    public interface ProcedureAuthorizationInfo
    {
        public String getExpiryDate();
        public String getUnitsAuthorized();
        public String getDenialReason();
        public String getAuthNumber();
        public String getInsuranceId();
        public String getId();
        public String getProcedureId();
        public String getTrackingNumber();
        public String getCptCode();
        public String getEffectiveDate();
        public String getStatus();
    }
    
    // To be used with Infinix files - that's just how they spell it
    public interface EligiblityBenefitsDetails
    {
        public String getOutofPocketMax();
        public String getEligibilityStatus();
        public String getAnnualDeductible();
        public String getNetworkStatus();
        public String getCoverageStatus();
        public String getPlanType();
        public String getPlanStart();
        public String getOmJobNumber();
        public String getProcedureAuthorizationId();
        public String getPlan();
        public String getPlanEnd();
    }
}
