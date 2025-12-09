/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing.Eligibility;

import API.Billing.InsuranceAPIResponseData;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class PokitDokEligibilityResponseData implements InsuranceAPIResponseData, Serializable
{
    private static final long serialVersionUID = 42L;

    @Override
    public Coverage getCoverage() {
        return new PokitDokCoverage();
    }

    @Override
    public Subscriber getSubscriber() {
        return new PokitDokSubscriber();
    }

    @Override
    public Provider getProvider() {
        return new PokitDokProvider();
    }

    @Override
    public Map<String, List<String>> getErrors() {
        return new HashMap<String, List<String>>();
    }

    @Override
    public String getTransactionUUID() {
        if (data != null && data.application_data != null && data.application_data.transaction_uuid != null)
            return data.application_data.transaction_uuid;
        else return null;
    }

    @Override
    public void stripProviderData() {
        return;
    }
    
    public class PokitDokCoinsurance implements InsuranceAPIResponseData.Coinsurance, Serializable
    {
        private static final long serialVersionUID = 42L;
        PokitDokEligibilityResponseData.Data.Coinsurance pokitDokCoinsurance;
        
        public PokitDokCoinsurance(PokitDokEligibilityResponseData.Data.Coinsurance coinsurance)
        {
            this.pokitDokCoinsurance = coinsurance;
        }
        
        @Override
        public String getPlanDescription() {
            return this.pokitDokCoinsurance.getPlan_description();
        }

        @Override
        public List<InsuranceAPIResponseData.ServiceType> getServiceTypes() {
            List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            if (pokitDokCoinsurance.service_types != null)
            {
                for (String serviceTypeStr : pokitDokCoinsurance.service_types)
                {
                    PokitDokServiceType serviceType = new PokitDokServiceType(serviceTypeStr, PokitDokUtil.service_type.valueOf(serviceTypeStr).getCode());
                    serviceTypeList.add(serviceType);
                }
            }
            return serviceTypeList;
        }

        @Override
        public BigDecimal getBenefitAmount() {
            BigDecimal benefitAmount = new BigDecimal(pokitDokCoinsurance.getBenefit_percent());
            return benefitAmount;
        }

        @Override
        public String getCoverageLevel() {
            return this.pokitDokCoinsurance.getCoverage_level();
        }

        @Override
        public Boolean isInPlanNetwork() {
            switch (pokitDokCoinsurance.getIn_plan_network().toUpperCase())
            {
                case "YES":
                case "Y":
                case "TRUE":
                case "T":
                case "IN_PLAN":
                    return true;
                case "NO":
                case "N":
                case "FALSE":
                case "F":
                case "NOT_IN_PLAN":
                    return false;
                default:
                    return null;
            }
        }

        @Override
        public List<String> getMessages() {
            List<String> messageList = new ArrayList<String>();
            for (PokitDokEligibilityResponseData.Data.Message message : pokitDokCoinsurance.getMessages())
            {
                messageList.add(message.getMessage());
            }
            return messageList;
        }
        
    }
    
    public class PokitDokLimitation implements InsuranceAPIResponseData.Limitations, Serializable
    {
        private static final long serialVersionUID = 42L;
        PokitDokEligibilityResponseData.Data.Limitation pokitDokLimitation;
        
        public PokitDokLimitation(PokitDokEligibilityResponseData.Data.Limitation limitation)
        {
            this.pokitDokLimitation = limitation;
        }

        @Override
        public String getPlanDescription() {
            return this.pokitDokLimitation.getPlan_description();
        }

        @Override
        public List<InsuranceAPIResponseData.ServiceType> getServiceTypes() {
            List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            if (pokitDokLimitation.service_types != null)
            {
                for (String serviceTypeStr : pokitDokLimitation.service_types)
                {
                    PokitDokServiceType serviceType = new PokitDokServiceType(serviceTypeStr, PokitDokUtil.service_type.valueOf(serviceTypeStr).getCode());
                    serviceTypeList.add(serviceType);
                }
            }
            return serviceTypeList;
        }

        @Override
        public String getCoverageLevel() {
            return pokitDokLimitation.getCoverage_level();
        }

        @Override
        public List<String> getMessages() {
            List<String> messageList = new ArrayList<String>();
            for (PokitDokEligibilityResponseData.Data.Message message : pokitDokLimitation.getMessages())
            {
                messageList.add(message.getMessage());
            }
            return messageList;
        }
    }
    
    public class PokitDokCopay implements InsuranceAPIResponseData.Copay, Serializable
    {
        private static final long serialVersionUID = 42L;
        PokitDokEligibilityResponseData.Data.Copay pokitDokCopay;
        
        public PokitDokCopay(PokitDokEligibilityResponseData.Data.Copay copay)
        {
            this.pokitDokCopay = copay;
        }
        
        @Override
        public String getPlanDescription() {
            return pokitDokCopay.getPlan_description();
        }

        @Override
        public String getCoverageLevel() {
            return pokitDokCopay.getCoverage_level();
        }

        @Override
        public BigDecimal getAmount() {
            BigDecimal copayAmount = new BigDecimal(pokitDokCopay.getCopayment().amount);
            return copayAmount;
        }

        @Override
        public Boolean isInPlanNetwork() {
            switch (pokitDokCopay.getIn_plan_network().toUpperCase())
            {
                case "YES":
                case "Y":
                case "TRUE":
                case "T":
                case "IN_PLAN":
                    return true;
                case "NO":
                case "N":
                case "FALSE":
                case "F":
                case "NOT_IN_PLAN":
                    return false;
                default:
                    return null;
            }
        }

        @Override
        public List<InsuranceAPIResponseData.ServiceType> getServiceTypes() {
            List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            if (pokitDokCopay.service_types != null)
            {
                for (String serviceTypeStr : pokitDokCopay.service_types)
                {
                    PokitDokServiceType serviceType = new PokitDokServiceType(serviceTypeStr, PokitDokUtil.service_type.valueOf(serviceTypeStr).getCode());
                    serviceTypeList.add(serviceType);
                }
            }
            return serviceTypeList;
        }

        @Override
        public List<String> getMessages() {
            List<String> messageList = new ArrayList<String>();
            for (PokitDokEligibilityResponseData.Data.Message message : pokitDokCopay.getMessages())
            {
                messageList.add(message.getMessage());
            }
            return messageList;
        }
        
    }
    
    public class PokitDokDeductible implements InsuranceAPIResponseData.Deductible, Serializable
    {
        private static final long serialVersionUID = 42L;
        PokitDokEligibilityResponseData.Data.Deductible pokitDokDeductible;
        
        public PokitDokDeductible(PokitDokEligibilityResponseData.Data.Deductible deductible)
        {
            this.pokitDokDeductible = deductible;
        }
        
        @Override
        public String getPlanDescription() {
            return pokitDokDeductible.getPlan_description();
        }

        @Override
        public Date getEligibilityDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return sdf.parse(pokitDokDeductible.getEligibility_date());
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public List<InsuranceAPIResponseData.ServiceType> getServiceTypes() {
            List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            if (pokitDokDeductible != null)
            {
                for (String serviceTypeStr : pokitDokDeductible.getService_types())
                {
                    PokitDokServiceType serviceType = new PokitDokServiceType(serviceTypeStr, PokitDokUtil.service_type.valueOf(serviceTypeStr).getCode());
                    serviceTypeList.add(serviceType);
                }
            }
            return serviceTypeList;
        }

        @Override
        public BigDecimal getBenefitAmount() {
            BigDecimal benefitAmount = new BigDecimal(pokitDokDeductible.getBenefit_amount().getAmount());
            return benefitAmount;
        }

        @Override
        public String getCoverageLevel() {
            return pokitDokDeductible.getCoverage_level();
        }

        @Override
        public Boolean isInPlanNetwork() {
            switch (pokitDokDeductible.getIn_plan_network().toUpperCase())
            {
                case "YES":
                case "Y":
                case "TRUE":
                case "T":
                case "IN_PLAN":
                    return true;
                case "NO":
                case "N":
                case "FALSE":
                case "F":
                case "NOT_IN_PLAN":
                    return false;
                default:
                    return null;
            }
        }

        @Override
        public String getTimePeriod() {
            return pokitDokDeductible.getTime_period();
        }

        @Override
        public List<String> getMessages() {
            List<String> messageList = new ArrayList<String>();
            for (PokitDokEligibilityResponseData.Data.Message message : pokitDokDeductible.getMessages())
            {
                messageList.add(message.getMessage());
            }
            return messageList;
        }
    }
    
    public class PokitDokServiceType implements InsuranceAPIResponseData.ServiceType, Serializable
    {
        private static final long serialVersionUID = 42L;
        String serviceType;
        String serviceTypeCode;
        
        public PokitDokServiceType(String serviceType, String serviceTypeCode) {
            this.serviceType = serviceType;
            this.serviceTypeCode = serviceTypeCode;
        }
        
        @Override
        public String getServiceType() {
            return serviceType;
        }

        @Override
        public String getServiceTypeCode() {
            return serviceTypeCode;
        }
    }
    
    public class PokitDokCoverage implements Coverage, Serializable
    {
        private static final long serialVersionUID = 42L;
        @Override
        public Boolean isActive() {
            return data == null || data.coverage == null ? null : data.coverage.active;
        }

        @Override
        public String getPlanDescription() {
            return data == null || data.coverage == null ? null : data.coverage.plan_description;
        }

        @Override
        public String getPlanNumber() {
            return data == null || data.coverage == null ? null : data.coverage.plan_number;
        }

        @Override
        public Date getEligibilityBeginDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return data == null || data.coverage == null || data.coverage.eligibility_begin_date == null ? null : sdf.parse(data.coverage.eligibility_begin_date);
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public Date getEligibilityEndDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return data == null || data.coverage == null || data.coverage.eligibility_end_date == null ? null : sdf.parse(data.coverage.eligibility_end_date);
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public Date getPolicyEffectiveDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return data == null || data.coverage == null || data.coverage.policy_effective_date == null ? null : sdf.parse(data.coverage.policy_effective_date);
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public Date getPolicyExpirationDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return data == null || data.coverage == null || data.coverage.policy_expiration_date == null ? null : sdf.parse(data.coverage.policy_expiration_date);
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public List<InsuranceAPIResponseData.ServiceType> getServiceTypes() {
            List<InsuranceAPIResponseData.ServiceType> serviceTypeList = new ArrayList<InsuranceAPIResponseData.ServiceType>();
            if (data != null && data.service_types != null)
            {
                for (String serviceTypeStr : data.service_types)
                {
                    PokitDokServiceType serviceType = new PokitDokServiceType(serviceTypeStr, PokitDokUtil.service_type.valueOf(serviceTypeStr).getCode());
                    serviceTypeList.add(serviceType);
                }
            }
            return serviceTypeList;
        }

        @Override
        public String getGroupDescription() {
            return data == null || data.coverage == null ? null : data.coverage.group_description;
        }

        @Override
        public String getGroupNumber() {
            return data == null || data.coverage == null ? null : data.coverage.group_number;
        }

        @Override
        public String getInsuranceType() {
            return data == null || data.coverage == null ? null : data.coverage.insurance_type;
        }

        @Override
        public String getCoverageLevel() {
            return data == null || data.coverage == null ? null : data.coverage.level;
        }

        @Override
        public Date getServiceDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return data == null || data.coverage == null ? null : data.coverage.service_date == null ? null : sdf.parse(data.coverage.service_date);
            } catch (ParseException ex) {
                return null;
            }
        }

        @Override
        public List<Deductible> getDeductibles() {
            List<Deductible> deductibleList = new ArrayList<Deductible>();
            if (data == null || data.coverage == null) return deductibleList;
            for (PokitDokEligibilityResponseData.Data.Deductible deductible : data.coverage.deductibles)
            {
                deductibleList.add(new PokitDokDeductible(deductible));
            }
            return deductibleList;
        }

        @Override
        public List<Copay> getCopays() {
            List<Copay> copayList = new ArrayList<Copay>();
            if (data == null || data.coverage == null) return copayList;
            for (PokitDokEligibilityResponseData.Data.Copay copay : data.coverage.getCopay())
            {
                copayList.add(new PokitDokCopay(copay));
            }
            return copayList;
        }

        @Override
        public List<InsuranceAPIResponseData.Limitations> getLimitations() {
            List<InsuranceAPIResponseData.Limitations> limitList = new ArrayList<InsuranceAPIResponseData.Limitations>();
            if (data == null || data.coverage == null) return limitList;
            for (PokitDokEligibilityResponseData.Data.Limitation limit : data.coverage.getLimitations())
            {
                limitList.add(new PokitDokLimitation(limit));
            }
            return limitList;
        }

        @Override
        public List<Coinsurance> getCoinsurances() {
            List<Coinsurance> coinsuranceList = new ArrayList<Coinsurance>();
            if (data == null || data.coverage == null) return coinsuranceList;
            for (PokitDokEligibilityResponseData.Data.Coinsurance coinsurance : data.coverage.getCoinsurance())
            {
                coinsuranceList.add(new PokitDokCoinsurance(coinsurance));
            }
            return coinsuranceList;
        }

        @Override
        public Boolean isValidRequest() {
            return data == null ? null : data.valid_request == null ? null : !data.valid_request.toLowerCase().equals("false");
        }

        @Override
        public String getRejectReason() {
            return data == null ? null : data.reject_reason;
        }

        @Override
        public String getFollowupAction() {
            return data == null ? null : data.follow_up_action;
        }
        
    }
    
    public class PokitDokSubscriber implements InsuranceAPIResponseData.Subscriber, Serializable
    {
        private static final long serialVersionUID = 42L;
        
        @Override
        public String getGender() {
            return data == null || data.subscriber == null ? null : data.subscriber.getGender();
        }

        @Override
        public String getFirstName() {
            return data == null || data.subscriber == null ? null : data.subscriber.getFirst_name();
        }

        @Override
        public String getLastName() {
            return data == null || data.subscriber == null ? null : data.subscriber.getLast_name();
        }

        @Override
        public String getID() {
            return data == null || data.subscriber == null ? null : data.subscriber.getId();
        }

        @Override
        public String getGroupNumber() {
            return data == null || data.subscriber == null ? null : data.subscriber.getGroup_number();
        } 
        
        @Override
        public String getDateOfBirth()
        {
            return "";
        }
        
        @Override
        public String getEntityType()
        {
            return "";
        }
        
        @Override
        public String getEntityIdentifier()
        {
            return "";
        }
        
        @Override
        public String getMemberId()
        {
            return "";
        }
    }
    
    public class PokitDokProvider implements InsuranceAPIResponseData.Provider, Serializable
    {
        private static final long serialVersionUID = 42L;

        @Override
        public String getNPI() {
            return data == null || data.provider == null ? null : data.provider.getNpi();
        }

        @Override
        public String getFirstName() {
            return data == null || data.provider == null ? null : data.provider.getFirst_name();
        }

        @Override
        public String getLastName() {
            return data == null || data.provider == null ? null : data.provider.getLast_name();
        }

        @Override
        public String getOrganizationName() {
            return data == null || data.provider == null ? null : data.provider.getOrganization_name();
        }
        
        @Override
        public String getProviderName()
        {
            return "";
        }
    }

    public class Plan implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String plan_description;
        String plan_number;
        String group_description;
        String group_number;
        String insurance_type;

        public String getPlan_description()
        {
            return plan_description;
        }

        public String getPlan_number()
        {
            return plan_number;
        }

        public String getGroup_description()
        {
            return group_description;
        }

        public String getGroup_number()
        {
            return group_number;
        }

        public String getInsurance_type()
        {
            return insurance_type;
        }
    }

    public enum CoverageLevel
    {
        children_only,
        dependents_only,
        employee_and_children,
        employee_only,
        employee_and_spouse,
        family,
        individual,
        spouse_and_children,
        spouse_only;
    }
    
    public void setResponseData(PokitDokEligibilityResponseData responseData)
    {
        this.meta = responseData.meta;
        this.data = responseData.data;
    }
    
    Meta meta;
    Data data;

    public Meta getMeta()
    {
        return meta;
    }

    public void setMeta(Meta meta)
    {
        this.meta = meta;
    }

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }
    
    public class Meta implements Serializable
    {
        private static final long serialVersionUID = 42L;
        Integer processing_time;
        String application_mode;
        Integer credits_billed;
        Integer credits_remaining;
        Integer rate_limit_cap;
        Integer rate_limit_reset;
        Integer rate_limit_amount;
        String activity_id;
    }
    
    public class Data implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String client_id;
        BenefitRelatedEntities[] benefit_related_entities;
        Coverage coverage;
        String follow_up_action;
        Dependent dependent;
        Payer payer;
        Pharmacy pharmacy;
        Provider provider;
        String reject_reason;
        Subscriber subscriber;
        String trading_partner_id;
        String[] service_types;
        String[] service_type_codes;
        String valid_request;
        Summary summary;
        ApplicationData application_data;

        public class ApplicationData implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String patient_id;
            String location_id;
            String transaction_uuid;

            public String getPatient_id() {
                return patient_id;
            }

            public void setPatient_id(String patient_id) {
                this.patient_id = patient_id;
            }

            public String getLocation_id() {
                return location_id;
            }

            public void setLocation_id(String location_id) {
                this.location_id = location_id;
            }

            public String getTransaction_uuid() {
                return transaction_uuid;
            }

            public void setTransaction_uuid(String transaction_uuid) {
                this.transaction_uuid = transaction_uuid;
            }
        }
        
        public class BenefitRelatedEntities implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String entity_identifier_code;
            String entity_type;
            String first_name;
            String middle_name;
            String last_name;
            String organization_name;
            String suffix;
            String id_qualifier;
            String id;
            String relationship_code;
            Address address;
            Contact[] contacts;
            String provider_code;
            String taxonomy_code;
            String eligibility_or_benefit_information; // deductible, active_coverage, other_source_of_data
            String coverage_level;
            String[] service_types;
            String[] service_type_codes;
            MonetaryAmount benefit_amount;

            public String getEntity_identifier_code()
            {
                return entity_identifier_code;
            }

            public void setEntity_identifier_code(String entity_identifier_code)
            {
                this.entity_identifier_code = entity_identifier_code;
            }

            public String getEntity_type()
            {
                return entity_type;
            }

            public void setEntity_type(String entity_type)
            {
                this.entity_type = entity_type;
            }

            public String getFirst_name()
            {
                return first_name;
            }

            public void setFirst_name(String first_name)
            {
                this.first_name = first_name;
            }

            public String getMiddle_name()
            {
                return middle_name;
            }

            public void setMiddle_name(String middle_name)
            {
                this.middle_name = middle_name;
            }

            public String getLast_name()
            {
                return last_name;
            }

            public void setLast_name(String last_name)
            {
                this.last_name = last_name;
            }

            public String getOrganization_name()
            {
                return organization_name;
            }

            public void setOrganization_name(String organization_name)
            {
                this.organization_name = organization_name;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public String getId_qualifier()
            {
                return id_qualifier;
            }

            public void setId_qualifier(String id_qualifier)
            {
                this.id_qualifier = id_qualifier;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getRelationship_code()
            {
                return relationship_code;
            }

            public void setRelationship_code(String relationship_code)
            {
                this.relationship_code = relationship_code;
            }

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public Contact[] getContacts()
            {
                return contacts;
            }

            public void setContacts(Contact[] contacts)
            {
                this.contacts = contacts;
            }

            public String getProvider_code()
            {
                return provider_code;
            }

            public void setProvider_code(String provider_code)
            {
                this.provider_code = provider_code;
            }

            public String getTaxonomy_code()
            {
                return taxonomy_code;
            }

            public void setTaxonomy_code(String taxonomy_code)
            {
                this.taxonomy_code = taxonomy_code;
            }

            public String getEligibility_or_benefit_information()
            {
                return eligibility_or_benefit_information;
            }

            public void setEligibility_or_benefit_information(String eligibility_or_benefit_information)
            {
                this.eligibility_or_benefit_information = eligibility_or_benefit_information;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public MonetaryAmount getBenefit_amount()
            {
                return benefit_amount;
            }

            public void setBenefit_amount(MonetaryAmount benefit_amount)
            {
                this.benefit_amount = benefit_amount;
            }
        }

        public class Address implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String[] address_lines; // ['123 main st.', 'Suite 4']
            String city;
            String state;
            String zipcode;
            String country;

            public String[] getAddress_lines()
            {
                return address_lines;
            }

            public void setAddress_lines(String[] address_lines)
            {
                this.address_lines = address_lines;
            }

            public String getCity()
            {
                return city;
            }

            public void setCity(String city)
            {
                this.city = city;
            }

            public String getState()
            {
                return state;
            }

            public void setState(String state)
            {
                this.state = state;
            }

            public String getZipcode()
            {
                return zipcode;
            }

            public void setZipcode(String zipcode)
            {
                this.zipcode = zipcode;
            }

            public String getCountry()
            {
                return country;
            }

            public void setCountry(String country)
            {
                this.country = country;
            }
        }

        public class Contact implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String name;
            String id;
            String email;
            String phone;
            String url;
            String contact_type;
            Address address;
            String[] service_types;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            Message[] messages;
            ContactMethod[] contact_methods;

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail(String email)
            {
                this.email = email;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getContact_type()
            {
                return contact_type;
            }

            public void setContact_type(String contact_type)
            {
                this.contact_type = contact_type;
            }

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public ContactMethod[] getContact_methods()
            {
                return contact_methods;
            }

            public void setContact_methods(ContactMethod[] contact_methods)
            {
                this.contact_methods = contact_methods;
            }
        }

        public class ContactMethod implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String type;
            String value;

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String getValue()
            {
                return value;
            }

            public void setValue(String value)
            {
                this.value = value;
            }
        }

        public class Coverage implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String added_date;
            CoverageDetail[] coverage_details;
            String benefit_begin_date;
            String benefit_end_date;
            Boolean active;
            PlanBenefitDescription[] plan_benefit_description;
            String group_or_policy_number;
            String member_identification_number;
            String case_number;
            String family_unit_number;
            String class_of_contract_code;
            String contract_number;
            String medical_record_identification_num;
            String patient_account_number;
            String health_insurance_claim_number;
            String identification_card_serial_number;
            String identity_card_number;
            String issue_number;
            String insurance_policy_number;
            String plan_network_identification_number;
            String plan_network_description;
            String medicaid_recipient_identification_number;
            String prior_identifier_number;
            String agency_claim_number;
            String social_security_number;
            HealthcareFacility[] healthcare_facilities;
            Coinsurance[] coinsurance;
            Limitation[] limitations;
            Contact[] contacts;
            Copay[] copay;
            Deductible[] deductibles;
            Disclaimer disclaimer;
            CannotProcess[] cannot_process;
            OtherPayer[] other_payers;
            OutOfPocket out_of_packet;
            NonCovered non_covered;
            String[] service_types;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            String plan_date;
            PrimaryCareProvider primary_care_provider;
            Plan[] plans;
            String eligibility_begin_date;
            String eligibility_end_date;
            String policy_effective_date;
            String policy_expiration_date;
            String group_description;
            String group_number;
            String insurance_type;
            String level;
            String plan_begin_date;
            String plan_end_date;
            String plan_description;
            String plan_number;
            String quantity;
            String quantity_qualifier;
            String service_date;

            public String getAdded_date()
            {
                return added_date;
            }

            public void setAdded_date(String added_date)
            {
                this.added_date = added_date;
            }

            public CoverageDetail[] getCoverage_details()
            {
                return coverage_details;
            }

            public void setCoverage_details(CoverageDetail[] coverage_details)
            {
                this.coverage_details = coverage_details;
            }

            public String getBenefit_begin_date()
            {
                return benefit_begin_date;
            }

            public void setBenefit_begin_date(String benefit_begin_date)
            {
                this.benefit_begin_date = benefit_begin_date;
            }

            public String getBenefit_end_date()
            {
                return benefit_end_date;
            }

            public void setBenefit_end_date(String benefit_end_date)
            {
                this.benefit_end_date = benefit_end_date;
            }

            public Boolean isActive()
            {
                return active;
            }

            public void setActive(Boolean active)
            {
                this.active = active;
            }
            
            public PlanBenefitDescription[] getPlan_benefit_description()
            {
                return plan_benefit_description;
            }

            public void setPlan_benefit_description(PlanBenefitDescription[] plan_benefit_description)
            {
                this.plan_benefit_description = plan_benefit_description;
            }

            public String getGroup_or_policy_number()
            {
                return group_or_policy_number;
            }

            public void setGroup_or_policy_number(String group_or_policy_number)
            {
                this.group_or_policy_number = group_or_policy_number;
            }

            public String getMember_identification_number()
            {
                return member_identification_number;
            }

            public void setMember_identification_number(String member_identification_number)
            {
                this.member_identification_number = member_identification_number;
            }

            public String getCase_number()
            {
                return case_number;
            }

            public void setCase_number(String case_number)
            {
                this.case_number = case_number;
            }

            public String getFamily_unit_number()
            {
                return family_unit_number;
            }

            public void setFamily_unit_number(String family_unit_number)
            {
                this.family_unit_number = family_unit_number;
            }

            public String getClass_of_contract_code()
            {
                return class_of_contract_code;
            }

            public void setClass_of_contract_code(String class_of_contract_code)
            {
                this.class_of_contract_code = class_of_contract_code;
            }

            public String getContract_number()
            {
                return contract_number;
            }

            public void setContract_number(String contract_number)
            {
                this.contract_number = contract_number;
            }

            public String getMedical_record_identification_num()
            {
                return medical_record_identification_num;
            }

            public void setMedical_record_identification_num(String medical_record_identification_num)
            {
                this.medical_record_identification_num = medical_record_identification_num;
            }

            public String getPatient_account_number()
            {
                return patient_account_number;
            }

            public void setPatient_account_number(String patient_account_number)
            {
                this.patient_account_number = patient_account_number;
            }

            public String getHealth_insurance_claim_number()
            {
                return health_insurance_claim_number;
            }

            public void setHealth_insurance_claim_number(String health_insurance_claim_number)
            {
                this.health_insurance_claim_number = health_insurance_claim_number;
            }

            public String getIdentification_card_serial_number()
            {
                return identification_card_serial_number;
            }

            public void setIdentification_card_serial_number(String identification_card_serial_number)
            {
                this.identification_card_serial_number = identification_card_serial_number;
            }

            public String getIdentity_card_number()
            {
                return identity_card_number;
            }

            public void setIdentity_card_number(String identity_card_number)
            {
                this.identity_card_number = identity_card_number;
            }

            public String getIssue_number()
            {
                return issue_number;
            }

            public void setIssue_number(String issue_number)
            {
                this.issue_number = issue_number;
            }

            public String getInsurance_policy_number()
            {
                return insurance_policy_number;
            }

            public void setInsurance_policy_number(String insurance_policy_number)
            {
                this.insurance_policy_number = insurance_policy_number;
            }

            public String getPlan_network_identification_number()
            {
                return plan_network_identification_number;
            }

            public void setPlan_network_identification_number(String plan_network_identification_number)
            {
                this.plan_network_identification_number = plan_network_identification_number;
            }

            public String getPlan_network_description()
            {
                return plan_network_description;
            }

            public void setPlan_network_description(String plan_network_description)
            {
                this.plan_network_description = plan_network_description;
            }

            public String getMedicaid_recipient_identification_number()
            {
                return medicaid_recipient_identification_number;
            }

            public void setMedicaid_recipient_identification_number(String medicaid_recipient_identification_number)
            {
                this.medicaid_recipient_identification_number = medicaid_recipient_identification_number;
            }

            public String getPrior_identifier_number()
            {
                return prior_identifier_number;
            }

            public void setPrior_identifier_number(String prior_identifier_number)
            {
                this.prior_identifier_number = prior_identifier_number;
            }

            public String getAgency_claim_number()
            {
                return agency_claim_number;
            }

            public void setAgency_claim_number(String agency_claim_number)
            {
                this.agency_claim_number = agency_claim_number;
            }

            public String getSocial_security_number()
            {
                return social_security_number;
            }

            public void setSocial_security_number(String social_security_number)
            {
                this.social_security_number = social_security_number;
            }

            public HealthcareFacility[] getHealthcare_facilities()
            {
                return healthcare_facilities;
            }

            public void setHealthcare_facilities(HealthcareFacility[] healthcare_facilities)
            {
                this.healthcare_facilities = healthcare_facilities;
            }

            public Coinsurance[] getCoinsurance()
            {
                return coinsurance;
            }

            public void setCoinsurance(Coinsurance[] coinsurance)
            {
                this.coinsurance = coinsurance;
            }

            public Limitation[] getLimitations()
            {
                return limitations;
            }

            public void setLimitations(Limitation[] limitations)
            {
                this.limitations = limitations;
            }

            public Contact[] getContacts()
            {
                return contacts;
            }

            public void setContacts(Contact[] contacts)
            {
                this.contacts = contacts;
            }

            public Copay[] getCopay()
            {
                return copay;
            }

            public void setCopay(Copay[] copay)
            {
                this.copay = copay;
            }

            public Deductible[] getDeductibles()
            {
                return deductibles;
            }

            public void setDeductibles(Deductible[] deductibles)
            {
                this.deductibles = deductibles;
            }

            public Disclaimer getDisclaimer()
            {
                return disclaimer;
            }

            public void setDisclaimer(Disclaimer disclaimer)
            {
                this.disclaimer = disclaimer;
            }

            public CannotProcess[] getCannot_process()
            {
                return cannot_process;
            }

            public void setCannot_process(CannotProcess[] cannot_process)
            {
                this.cannot_process = cannot_process;
            }

            public OtherPayer[] getOther_payers()
            {
                return other_payers;
            }

            public void setOther_payers(OtherPayer[] other_payers)
            {
                this.other_payers = other_payers;
            }

            public OutOfPocket getOut_of_packet()
            {
                return out_of_packet;
            }

            public void setOut_of_packet(OutOfPocket out_of_packet)
            {
                this.out_of_packet = out_of_packet;
            }

            public NonCovered getNon_covered()
            {
                return non_covered;
            }

            public void setNon_covered(NonCovered non_covered)
            {
                this.non_covered = non_covered;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public String getPlan_date()
            {
                return plan_date;
            }

            public void setPlan_date(String plan_date)
            {
                this.plan_date = plan_date;
            }

            public PrimaryCareProvider getPrimary_care_provider()
            {
                return primary_care_provider;
            }

            public void setPrimary_care_provider(PrimaryCareProvider primary_care_provider)
            {
                this.primary_care_provider = primary_care_provider;
            }

            public Plan[] getPlans()
            {
                return plans;
            }

            public void setPlans(Plan[] plans)
            {
                this.plans = plans;
            }

            public String getEligibility_begin_date()
            {
                return eligibility_begin_date;
            }

            public void setEligibility_begin_date(String eligibility_begin_date)
            {
                this.eligibility_begin_date = eligibility_begin_date;
            }

            public String getEligibility_end_date()
            {
                return eligibility_end_date;
            }

            public void setEligibility_end_date(String eligibility_end_date)
            {
                this.eligibility_end_date = eligibility_end_date;
            }

            public String getPolicy_effective_date()
            {
                return policy_effective_date;
            }

            public void setPolicy_effective_date(String policy_effective_date)
            {
                this.policy_effective_date = policy_effective_date;
            }

            public String getPolicy_expiration_date()
            {
                return policy_expiration_date;
            }

            public void setPolicy_expiration_date(String policy_expiration_date)
            {
                this.policy_expiration_date = policy_expiration_date;
            }

            public String getGroup_description()
            {
                return group_description;
            }

            public void setGroup_description(String group_description)
            {
                this.group_description = group_description;
            }

            public String getGroup_number()
            {
                return group_number;
            }

            public void setGroup_number(String group_number)
            {
                this.group_number = group_number;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public String getLevel()
            {
                return level;
            }

            public void setLevel(String level)
            {
                this.level = level;
            }

            public String getPlan_begin_date()
            {
                return plan_begin_date;
            }

            public void setPlan_begin_date(String plan_begin_date)
            {
                this.plan_begin_date = plan_begin_date;
            }

            public String getPlan_end_date()
            {
                return plan_end_date;
            }

            public void setPlan_end_date(String plan_end_date)
            {
                this.plan_end_date = plan_end_date;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getPlan_number()
            {
                return plan_number;
            }

            public void setPlan_number(String plan_number)
            {
                this.plan_number = plan_number;
            }

            public String getQuantity()
            {
                return quantity;
            }

            public void setQuantity(String quantity)
            {
                this.quantity = quantity;
            }

            public String getQuantity_qualifier()
            {
                return quantity_qualifier;
            }

            public void setQuantity_qualifier(String quantity_qualifier)
            {
                this.quantity_qualifier = quantity_qualifier;
            }

            public String getService_date()
            {
                return service_date;
            }

            public void setService_date(String service_date)
            {
                this.service_date = service_date;
            }
        }

        public class CoverageDetail implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String status;
            String period_start_date;
            String period_end_date;
            Message[] messages;
            String group_or_policy_number;
            String plan_description;
            String insurance_type;
            String[] service_types;
            String[] service_type_codes;

            public String getStatus()
            {
                return status;
            }

            public void setStatus(String status)
            {
                this.status = status;
            }

            public String getPeriod_start_date()
            {
                return period_start_date;
            }

            public void setPeriod_start_date(String period_start_date)
            {
                this.period_start_date = period_start_date;
            }

            public String getPeriod_end_date()
            {
                return period_end_date;
            }

            public void setPeriod_end_date(String period_end_date)
            {
                this.period_end_date = period_end_date;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getGroup_or_policy_number()
            {
                return group_or_policy_number;
            }

            public void setGroup_or_policy_number(String group_or_policy_number)
            {
                this.group_or_policy_number = group_or_policy_number;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }
        }

        public class CannotProcess implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Message[] messages;
            String[] service_types;
            String[] service_type_codes;

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }
        }

        public class Message implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String message;

            public String getMessage()
            {
                return message;
            }

            public void setMessage(String message)
            {
                this.message = message;
            }
        }

        public class Disclaimer implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Message[] messages;

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }
        }

        public class PlanBenefitDescription implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Message[] messages;
            String coverage_level;
            String[] service_types;
            String[] service_type_codes;

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }
        }

        public class HealthcareFacility implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Address address;
            String name;
            String phone;
            String email;
            String url;
            String fax;
            String facility_type;
            String[] service_types;
            String[] service_type_codes;

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail(String email)
            {
                this.email = email;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getFax()
            {
                return fax;
            }

            public void setFax(String fax)
            {
                this.fax = fax;
            }

            public String getFacility_type()
            {
                return facility_type;
            }

            public void setFacility_type(String facility_type)
            {
                this.facility_type = facility_type;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }
        }

        public class Coinsurance implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String[] service_types;
            String[] service_type_codes;
            String description;
            String plan_description;
            String insurance_type;
            String authorization_required;
            String procedure_id;
            String procedure_id_qualifier; // e.g., 'cpt'
            Message[] messages;
            Delivery delivery;
            String benefit_percent;
            String coverage_level;
            String in_plan_network;
            String plan_begin_date;
            String plan_end_date;

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public String getAuthorization_required()
            {
                return authorization_required;
            }

            public void setAuthorization_required(String authorization_required)
            {
                this.authorization_required = authorization_required;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }

            public String getBenefit_percent()
            {
                return benefit_percent;
            }

            public void setBenefit_percent(String benefit_percent)
            {
                this.benefit_percent = benefit_percent;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String getPlan_begin_date()
            {
                return plan_begin_date;
            }

            public void setPlan_begin_date(String plan_begin_date)
            {
                this.plan_begin_date = plan_begin_date;
            }

            public String getPlan_end_date()
            {
                return plan_end_date;
            }

            public void setPlan_end_date(String plan_end_date)
            {
                this.plan_end_date = plan_end_date;
            }
        }

        public class Delivery implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String units;
            String sample_selection_modulus;
            String time_period_qualifier;
            String period_count;
            String delivery_frequency_code;
            String delivery_pattern_time_code;
            String quantity;
            String quantity_qualifier;

            public String getUnits()
            {
                return units;
            }

            public void setUnits(String units)
            {
                this.units = units;
            }

            public String getSample_selection_modulus()
            {
                return sample_selection_modulus;
            }

            public void setSample_selection_modulus(String sample_selection_modulus)
            {
                this.sample_selection_modulus = sample_selection_modulus;
            }

            public String getTime_period_qualifier()
            {
                return time_period_qualifier;
            }

            public void setTime_period_qualifier(String time_period_qualifier)
            {
                this.time_period_qualifier = time_period_qualifier;
            }

            public String getPeriod_count()
            {
                return period_count;
            }

            public void setPeriod_count(String period_count)
            {
                this.period_count = period_count;
            }

            public String getDelivery_frequency_code()
            {
                return delivery_frequency_code;
            }

            public void setDelivery_frequency_code(String delivery_frequency_code)
            {
                this.delivery_frequency_code = delivery_frequency_code;
            }

            public String getDelivery_pattern_time_code()
            {
                return delivery_pattern_time_code;
            }

            public void setDelivery_pattern_time_code(String delivery_pattern_time_code)
            {
                this.delivery_pattern_time_code = delivery_pattern_time_code;
            }

            public String getQuantity()
            {
                return quantity;
            }

            public void setQuantity(String quantity)
            {
                this.quantity = quantity;
            }

            public String getQuantity_qualifier()
            {
                return quantity_qualifier;
            }

            public void setQuantity_qualifier(String quantity_qualifier)
            {
                this.quantity_qualifier = quantity_qualifier;
            }
        }

        public class Limitation implements Serializable
        {
            private static final long serialVersionUID = 42L;
            MonetaryAmount benefit_amount;
            Delivery delivery;
            String plan_description;
            String description;
            Message[] messages;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            String time_period_qualifier;
            String coverage_level;
            String in_plan_network;
            String[] service_types;
            String latest_visit_or_consultation_date;
            String quantity;
            String quantity_qualifier;

            public MonetaryAmount getBenefit_amount()
            {
                return benefit_amount;
            }

            public void setBenefit_amount(MonetaryAmount benefit_amount)
            {
                this.benefit_amount = benefit_amount;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public String getTime_period_qualifier()
            {
                return time_period_qualifier;
            }

            public void setTime_period_qualifier(String time_period_qualifier)
            {
                this.time_period_qualifier = time_period_qualifier;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String getLatest_visit_or_consultation_date()
            {
                return latest_visit_or_consultation_date;
            }

            public void setLatest_visit_or_consultation_date(String latest_visit_or_consultation_date)
            {
                this.latest_visit_or_consultation_date = latest_visit_or_consultation_date;
            }

            public String getQuantity()
            {
                return quantity;
            }

            public void setQuantity(String quantity)
            {
                this.quantity = quantity;
            }

            public String getQuantity_qualifier()
            {
                return quantity_qualifier;
            }

            public void setQuantity_qualifier(String quantity_qualifier)
            {
                this.quantity_qualifier = quantity_qualifier;
            }
        }

        public class Copay implements Serializable
        {
            private static final long serialVersionUID = 42L;
            MonetaryAmount copayment;
            String coverage_level;
            String in_plan_network;
            String insurance_type;
            String description;
            String plan_description;
            String[] service_types;
            String[] service_type_codes;
            String authorization_required;
            Message[] messages;
            String procedure_id;
            String procedure_id_qualifier;
            Delivery delivery;

            public MonetaryAmount getCopayment()
            {
                return copayment;
            }

            public void setCopayment(MonetaryAmount copayment)
            {
                this.copayment = copayment;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getAuthorization_required()
            {
                return authorization_required;
            }

            public void setAuthorization_required(String authorization_required)
            {
                this.authorization_required = authorization_required;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }
        }

        public class MonetaryAmount implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String amount;
            String currency;

            public String getAmount()
            {
                return amount;
            }

            public void setAmount(String amount)
            {
                this.amount = amount;
            }

            public String getCurrency()
            {
                return currency;
            }

            public void setCurrency(String currency)
            {
                this.currency = currency;
            }
        }

        public class Deductible implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Message[] messages;
            String eligibility_date;
            Delivery delivery;
            String description;
            String plan_description;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            String insurance_type;
            MonetaryAmount benefit_amount;
            String coverage_level;
            String in_plan_network;
            String time_period;
            String[] service_types;

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getEligibility_date()
            {
                return eligibility_date;
            }

            public void setEligibility_date(String eligibility_date)
            {
                this.eligibility_date = eligibility_date;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public MonetaryAmount getBenefit_amount()
            {
                return benefit_amount;
            }

            public void setBenefit_amount(MonetaryAmount benefit_amount)
            {
                this.benefit_amount = benefit_amount;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String getTime_period()
            {
                return time_period;
            }

            public void setTime_period(String time_period)
            {
                this.time_period = time_period;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }
        }

        public class OtherPayer implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            String plan_description;
            String plan_number;
            String coordination_of_benefits;
            String coordination_of_benefits_date;
            String coverage_level;
            String id;
            String name;
            String subscriber;
            String[] service_types;
            String benefit_begin_date;

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getPlan_number()
            {
                return plan_number;
            }

            public void setPlan_number(String plan_number)
            {
                this.plan_number = plan_number;
            }

            public String getCoordination_of_benefits()
            {
                return coordination_of_benefits;
            }

            public void setCoordination_of_benefits(String coordination_of_benefits)
            {
                this.coordination_of_benefits = coordination_of_benefits;
            }

            public String getCoordination_of_benefits_date()
            {
                return coordination_of_benefits_date;
            }

            public void setCoordination_of_benefits_date(String coordination_of_benefits_date)
            {
                this.coordination_of_benefits_date = coordination_of_benefits_date;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getSubscriber()
            {
                return subscriber;
            }

            public void setSubscriber(String subscriber)
            {
                this.subscriber = subscriber;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String getBenefit_begin_date()
            {
                return benefit_begin_date;
            }

            public void setBenefit_begin_date(String benefit_begin_date)
            {
                this.benefit_begin_date = benefit_begin_date;
            }
        }

        public class OutOfPocket implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String description;
            String plan_description;
            String insurance_type;
            Message[] messages;
            Delivery delivery;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            MonetaryAmount benefit_amount;
            String coverage_level;
            String in_plan_network;
            String time_period;
            String[] service_types;

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public MonetaryAmount getBenefit_amount()
            {
                return benefit_amount;
            }

            public void setBenefit_amount(MonetaryAmount benefit_amount)
            {
                this.benefit_amount = benefit_amount;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String getTime_period()
            {
                return time_period;
            }

            public void setTime_period(String time_period)
            {
                this.time_period = time_period;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }
        }

        public class NonCovered implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Message[] messages;
            String in_plan_network;
            String description;
            String plan_description;
            String insurance_type;
            String coverage_level;
            MonetaryAmount benefit_amount;
            Delivery delivery;
            String time_period;
            String[] service_types;
            String[] service_type_codes;
            String procedure_id;
            String procedure_id_qualifier;
            String quantity;
            String quantity_qualifier;

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getIn_plan_network()
            {
                return in_plan_network;
            }

            public void setIn_plan_network(String in_plan_network)
            {
                this.in_plan_network = in_plan_network;
            }

            public String getDescription()
            {
                return description;
            }

            public void setDescription(String description)
            {
                this.description = description;
            }

            public String getPlan_description()
            {
                return plan_description;
            }

            public void setPlan_description(String plan_description)
            {
                this.plan_description = plan_description;
            }

            public String getInsurance_type()
            {
                return insurance_type;
            }

            public void setInsurance_type(String insurance_type)
            {
                this.insurance_type = insurance_type;
            }

            public String getCoverage_level()
            {
                return coverage_level;
            }

            public void setCoverage_level(String coverage_level)
            {
                this.coverage_level = coverage_level;
            }

            public MonetaryAmount getBenefit_amount()
            {
                return benefit_amount;
            }

            public void setBenefit_amount(MonetaryAmount benefit_amount)
            {
                this.benefit_amount = benefit_amount;
            }

            public Delivery getDelivery()
            {
                return delivery;
            }

            public void setDelivery(Delivery delivery)
            {
                this.delivery = delivery;
            }

            public String getTime_period()
            {
                return time_period;
            }

            public void setTime_period(String time_period)
            {
                this.time_period = time_period;
            }

            public String[] getService_types()
            {
                return service_types;
            }

            public void setService_types(String[] service_types)
            {
                this.service_types = service_types;
            }

            public String[] getService_type_codes()
            {
                return service_type_codes;
            }

            public void setService_type_codes(String[] service_type_codes)
            {
                this.service_type_codes = service_type_codes;
            }

            public String getProcedure_id()
            {
                return procedure_id;
            }

            public void setProcedure_id(String procedure_id)
            {
                this.procedure_id = procedure_id;
            }

            public String getProcedure_id_qualifier()
            {
                return procedure_id_qualifier;
            }

            public void setProcedure_id_qualifier(String procedure_id_qualifier)
            {
                this.procedure_id_qualifier = procedure_id_qualifier;
            }

            public String getQuantity()
            {
                return quantity;
            }

            public void setQuantity(String quantity)
            {
                this.quantity = quantity;
            }

            public String getQuantity_qualifier()
            {
                return quantity_qualifier;
            }

            public void setQuantity_qualifier(String quantity_qualifier)
            {
                this.quantity_qualifier = quantity_qualifier;
            }
        }

        public class PrimaryCareProvider implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String phone;
            String email;
            String url;
            String fax;
            Address address;
            String first_name;
            String middle_name;
            String last_name;
            String suffix;
            String organization_name;
            Message[] messages;
            String primary_care_provider_begin_date;
            String primary_care_provider_end_date;

            public String getPhone()
            {
                return phone;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail(String email)
            {
                this.email = email;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getFax()
            {
                return fax;
            }

            public void setFax(String fax)
            {
                this.fax = fax;
            }

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public String getFirst_name()
            {
                return first_name;
            }

            public void setFirst_name(String first_name)
            {
                this.first_name = first_name;
            }

            public String getMiddle_name()
            {
                return middle_name;
            }

            public void setMiddle_name(String middle_name)
            {
                this.middle_name = middle_name;
            }

            public String getLast_name()
            {
                return last_name;
            }

            public void setLast_name(String last_name)
            {
                this.last_name = last_name;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public String getOrganization_name()
            {
                return organization_name;
            }

            public void setOrganization_name(String organization_name)
            {
                this.organization_name = organization_name;
            }

            public Message[] getMessages()
            {
                return messages;
            }

            public void setMessages(Message[] messages)
            {
                this.messages = messages;
            }

            public String getPrimary_care_provider_begin_date()
            {
                return primary_care_provider_begin_date;
            }

            public void setPrimary_care_provider_begin_date(String primary_care_provider_begin_date)
            {
                this.primary_care_provider_begin_date = primary_care_provider_begin_date;
            }

            public String getPrimary_care_provider_end_date()
            {
                return primary_care_provider_end_date;
            }

            public void setPrimary_care_provider_end_date(String primary_care_provider_end_date)
            {
                this.primary_care_provider_end_date = primary_care_provider_end_date;
            }
        }

        public class Dependent implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Address address;
            String gender;
            String first_name;
            String middle_name;
            String last_name;
            String id;
            String relationship;
            String group_number;
            Rejections[] rejections;

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public String getGender()
            {
                return gender;
            }

            public void setGender(String gender)
            {
                this.gender = gender;
            }

            public String getFirst_name()
            {
                return first_name;
            }

            public void setFirst_name(String first_name)
            {
                this.first_name = first_name;
            }

            public String getMiddle_name()
            {
                return middle_name;
            }

            public void setMiddle_name(String middle_name)
            {
                this.middle_name = middle_name;
            }

            public String getLast_name()
            {
                return last_name;
            }

            public void setLast_name(String last_name)
            {
                this.last_name = last_name;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getRelationship()
            {
                return relationship;
            }

            public void setRelationship(String relationship)
            {
                this.relationship = relationship;
            }

            public String getGroup_number()
            {
                return group_number;
            }

            public void setGroup_number(String group_number)
            {
                this.group_number = group_number;
            }

            public Rejections[] getRejections()
            {
                return rejections;
            }

            public void setRejections(Rejections[] rejections)
            {
                this.rejections = rejections;
            }
        }
        
        public class Rejections implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String follow_up_action;
            String reject_reason;
            String valid_request;

            public String getFollow_up_action() {
                return follow_up_action;
            }

            public void setFollow_up_action(String follow_up_action) {
                this.follow_up_action = follow_up_action;
            }

            public String getReject_reason() {
                return reject_reason;
            }

            public void setReject_reason(String reject_reason) {
                this.reject_reason = reject_reason;
            }

            public String getValid_request() {
                return valid_request;
            }

            public void setValid_request(String valid_request) {
                this.valid_request = valid_request;
            }
        }

        public class Payer implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String id;
            String tax_id;
            String name;
            String phone;
            String email;
            String url;
            String fax;
            Rejections[] rejections;

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getTax_id()
            {
                return tax_id;
            }

            public void setTax_id(String tax_id)
            {
                this.tax_id = tax_id;
            }

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail(String email)
            {
                this.email = email;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getFax()
            {
                return fax;
            }

            public void setFax(String fax)
            {
                this.fax = fax;
            }

            public Rejections[] getRejections()
            {
                return rejections;
            }

            public void setRejections(Rejections[] rejections)
            {
                this.rejections = rejections;
            }
        }

        public class Pharmacy implements Serializable
        {
            private static final long serialVersionUID = 42L;
            BenefitsManager benefits_manager;
            String benefit_date;
            String is_eligible;
            String plan_number;
            PharmacyCopay copay;

            public BenefitsManager getBenefits_manager()
            {
                return benefits_manager;
            }

            public void setBenefits_manager(BenefitsManager benefits_manager)
            {
                this.benefits_manager = benefits_manager;
            }

            public String getBenefit_date()
            {
                return benefit_date;
            }

            public void setBenefit_date(String benefit_date)
            {
                this.benefit_date = benefit_date;
            }

            public String getIs_eligible()
            {
                return is_eligible;
            }

            public void setIs_eligible(String is_eligible)
            {
                this.is_eligible = is_eligible;
            }

            public String getPlan_number()
            {
                return plan_number;
            }

            public void setPlan_number(String plan_number)
            {
                this.plan_number = plan_number;
            }

            public PharmacyCopay getCopay()
            {
                return copay;
            }

            public void setCopay(PharmacyCopay copay)
            {
                this.copay = copay;
            }
        }

        public class BenefitsManager implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String name;
            String phone;
            String url;
            String coverage_description;

            public String getName()
            {
                return name;
            }

            public void setName(String name)
            {
                this.name = name;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone(String phone)
            {
                this.phone = phone;
            }

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getCoverage_description()
            {
                return coverage_description;
            }

            public void setCoverage_description(String coverage_description)
            {
                this.coverage_description = coverage_description;
            }
        }

        public class PharmacyCopay implements Serializable
        {
            private static final long serialVersionUID = 42L;
            MonetaryAmount copayment;
            String tier;
            String type;
            String[] notes;
            String supply;

            public MonetaryAmount getCopayment()
            {
                return copayment;
            }

            public void setCopayment(MonetaryAmount copayment)
            {
                this.copayment = copayment;
            }

            public String getTier()
            {
                return tier;
            }

            public void setTier(String tier)
            {
                this.tier = tier;
            }

            public String getType()
            {
                return type;
            }

            public void setType(String type)
            {
                this.type = type;
            }

            public String[] getNotes()
            {
                return notes;
            }

            public void setNotes(String[] notes)
            {
                this.notes = notes;
            }

            public String getSupply()
            {
                return supply;
            }

            public void setSupply(String supply)
            {
                this.supply = supply;
            }
        }

        public class Provider implements Serializable
        {
            private static final long serialVersionUID = 42L;
            String npi;
            String first_name;
            String middle_name;
            String last_name;
            String suffix;
            String pin;
            String organization_name;
            Rejections[] rejections;

            public String getNpi()
            {
                return npi;
            }

            public void setNpi(String npi)
            {
                this.npi = npi;
            }

            public String getFirst_name()
            {
                return first_name;
            }

            public void setFirst_name(String first_name)
            {
                this.first_name = first_name;
            }

            public String getMiddle_name()
            {
                return middle_name;
            }

            public void setMiddle_name(String middle_name)
            {
                this.middle_name = middle_name;
            }

            public String getLast_name()
            {
                return last_name;
            }

            public void setLast_name(String last_name)
            {
                this.last_name = last_name;
            }

            public String getSuffix()
            {
                return suffix;
            }

            public void setSuffix(String suffix)
            {
                this.suffix = suffix;
            }

            public String getPin()
            {
                return pin;
            }

            public void setPin(String pin)
            {
                this.pin = pin;
            }

            public String getOrganization_name()
            {
                return organization_name;
            }

            public void setOrganization_name(String organization_name)
            {
                this.organization_name = organization_name;
            }

            public Rejections[] getRejections()
            {
                return rejections;
            }

            public void setRejections(Rejections[] rejections)
            {
                this.rejections = rejections;
            }
        }

        public class Subscriber implements Serializable
        {
            private static final long serialVersionUID = 42L;
            Address address;
            String birthDate;
            String gender;
            String first_name;
            String last_name;
            String id;
            String group_number;
            Rejections[] rejections;

            public Address getAddress()
            {
                return address;
            }

            public void setAddress(Address address)
            {
                this.address = address;
            }

            public String getBirthDate()
            {
                return birthDate;
            }

            public void setBirthDate(String birthDate)
            {
                this.birthDate = birthDate;
            }

            public String getGender()
            {
                return gender;
            }

            public void setGender(String gender)
            {
                this.gender = gender;
            }

            public String getFirst_name()
            {
                return first_name;
            }

            public void setFirst_name(String first_name)
            {
                this.first_name = first_name;
            }

            public String getLast_name()
            {
                return last_name;
            }

            public void setLast_name(String last_name)
            {
                this.last_name = last_name;
            }

            public String getId()
            {
                return id;
            }

            public void setId(String id)
            {
                this.id = id;
            }

            public String getGroup_number()
            {
                return group_number;
            }

            public void setGroup_number(String group_number)
            {
                this.group_number = group_number;
            }

            public Rejections[] getRejections()
            {
                return rejections;
            }

            public void setRejections(Rejections[] rejections)
            {
                this.rejections = rejections;
            }
        }

        public class SummaryDeductible implements Serializable
        {
            private static final long serialVersionUID = 42L;
            IndividualOrFamilySummary individual;
            IndividualOrFamilySummary family;

            public IndividualOrFamilySummary getIndividual()
            {
                return individual;
            }

            public void setIndividual(IndividualOrFamilySummary individual)
            {
                this.individual = individual;
            }

            public IndividualOrFamilySummary getFamily()
            {
                return family;
            }

            public void setFamily(IndividualOrFamilySummary family)
            {
                this.family = family;
            }
        }

        public class IndividualOrFamilySummary implements Serializable
        {
            private static final long serialVersionUID = 42L;
            AmountSummary in_network;
            AmountSummary out_of_network;

            public AmountSummary getIn_network()
            {
                return in_network;
            }

            public void setIn_network(AmountSummary in_network)
            {
                this.in_network = in_network;
            }

            public AmountSummary getOut_of_network()
            {
                return out_of_network;
            }

            public void setOut_of_network(AmountSummary out_of_network)
            {
                this.out_of_network = out_of_network;
            }
        }

        public class AmountSummary implements Serializable
        {
            private static final long serialVersionUID = 42L;
            MonetaryAmount applied;
            MonetaryAmount limit;
            MonetaryAmount remaining;

            public MonetaryAmount getApplied()
            {
                return applied;
            }

            public void setApplied(MonetaryAmount applied)
            {
                this.applied = applied;
            }

            public MonetaryAmount getLimit()
            {
                return limit;
            }

            public void setLimit(MonetaryAmount limit)
            {
                this.limit = limit;
            }

            public MonetaryAmount getRemaining()
            {
                return remaining;
            }

            public void setRemaining(MonetaryAmount remaining)
            {
                this.remaining = remaining;
            }
        }
        
        public class Summary implements Serializable
        {
            private static final long serialVersionUID = 42L;
            SummaryDeductible deductible;
            SummaryDeductible out_of_pocket;

            public SummaryDeductible getDeductible()
            {
                return deductible;
            }

            public void setDeductible(SummaryDeductible deductible)
            {
                this.deductible = deductible;
            }

            public SummaryDeductible getOut_of_pocket()
            {
                return out_of_pocket;
            }

            public void setOut_of_pocket(SummaryDeductible out_of_pocket)
            {
                this.out_of_pocket = out_of_pocket;
            }
        }

        public String getClient_id()
        {
            return client_id;
        }

        public void setClient_id(String client_id)
        {
            this.client_id = client_id;
        }

        public BenefitRelatedEntities[] getBenefit_related_entities()
        {
            return benefit_related_entities;
        }

        public void setBenefit_related_entities(BenefitRelatedEntities[] benefit_related_entities)
        {
            this.benefit_related_entities = benefit_related_entities;
        }

        public Coverage getCoverage()
        {
            return coverage;
        }

        public void setCoverage(Coverage coverage)
        {
            this.coverage = coverage;
        }

        public String getFollow_up_action()
        {
            return follow_up_action;
        }

        public void setFollow_up_action(String follow_up_action)
        {
            this.follow_up_action = follow_up_action;
        }

        public Dependent getDependent()
        {
            return dependent;
        }

        public void setDependent(Dependent dependent)
        {
            this.dependent = dependent;
        }

        public Payer getPayer()
        {
            return payer;
        }

        public void setPayer(Payer payer)
        {
            this.payer = payer;
        }

        public Pharmacy getPharmacy()
        {
            return pharmacy;
        }

        public void setPharmacy(Pharmacy pharmacy)
        {
            this.pharmacy = pharmacy;
        }

        public Provider getProvider()
        {
            return provider;
        }

        public void setProvider(Provider provider)
        {
            this.provider = provider;
        }

        public String getReject_reason()
        {
            return reject_reason;
        }

        public void setReject_reason(String reject_reason)
        {
            this.reject_reason = reject_reason;
        }

        public Subscriber getSubscriber()
        {
            return subscriber;
        }

        public void setSubscriber(Subscriber subscriber)
        {
            this.subscriber = subscriber;
        }

        public String getTrading_partner_id()
        {
            return trading_partner_id;
        }

        public void setTrading_partner_id(String trading_partner_id)
        {
            this.trading_partner_id = trading_partner_id;
        }

        public String[] getService_types()
        {
            return service_types;
        }

        public void setService_types(String[] service_types)
        {
            this.service_types = service_types;
        }

        public String[] getService_type_codes()
        {
            return service_type_codes;
        }

        public void setService_type_codes(String[] service_type_codes)
        {
            this.service_type_codes = service_type_codes;
        }

        public String getValid_request()
        {
            return valid_request;
        }

        public void setValid_request(String valid_request)
        {
            this.valid_request = valid_request;
        }

        public Summary getSummary()
        {
            return summary;
        }

        public void setSummary(Summary summary)
        {
            this.summary = summary;
        }

        public ApplicationData getApplicationData()
        {
            return application_data;
        }

        public void setApplicationData(ApplicationData appData)
        {
            this.application_data = appData;
        }
    }
}
