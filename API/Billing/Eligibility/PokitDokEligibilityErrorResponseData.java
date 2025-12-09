/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing.Eligibility;

import API.Billing.InsuranceAPIResponseData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class PokitDokEligibilityErrorResponseData implements InsuranceAPIResponseData, Serializable
{
    private static final long serialVersionUID = 42L;
    Data data;

    public PokitDokEligibilityErrorResponseData()
    {
        super();
    }
    
    public PokitDokEligibilityErrorResponseData(String error)
    {
        super();
        data = new Data();
        data.errors = new Errors();
        data.errors.validation = new Validation();
        data.errors.validation.internal_validation_errors = new String[]{error};
    }
    
    @Override
    public InsuranceAPIResponseData.Coverage getCoverage() {
        return null;
    }
    
    @Override
    public InsuranceAPIResponseData.Subscriber getSubscriber() {
        return null;
    }
    
    @Override
    public InsuranceAPIResponseData.Provider getProvider() {
        return null;
    }

    
    @Override
    public Map<String, List<String>> getErrors() {
        Map<String, List<String>> errorFieldAndMessage = new HashMap<String, List<String>>();
        if (data != null && data.errors != null && data.errors.configuration != null && !data.errors.configuration.isEmpty())
        {
            List<String> errorMessageList = new ArrayList<String>();
            errorMessageList.addAll(Arrays.asList(data.errors.configuration));
            errorFieldAndMessage.put("Configuration", errorMessageList);
        }
        if (data != null && data.errors != null && data.errors.validation != null)
        {
            if (data.errors.validation.application_data != null)
            {
                if (data.errors.validation.application_data.location_id != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.application_data.location_id));
                    errorFieldAndMessage.put("Application Data - Location ID", errorMessageList);
                }
                if (data.errors.validation.application_data.patient_id != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.application_data.patient_id));
                    errorFieldAndMessage.put("Application Data - Patient ID", errorMessageList);
                }
                if (data.errors.validation.application_data.transaction_uuid != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.application_data.transaction_uuid));
                    errorFieldAndMessage.put("Application Data - Transaction UUID", errorMessageList);
                }
            }
            if (data.errors.validation.cpt_code != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.cpt_code));
                errorFieldAndMessage.put("CPT Code", errorMessageList);
            }
            if (data.errors.validation.member != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.member.getId()));
                errorFieldAndMessage.put("Member", errorMessageList);
            }
            if (data.errors.validation.payer != null)
            {
                if (data.errors.validation.payer.email != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.email));
                    errorFieldAndMessage.put("Payer - Email", errorMessageList);
                }
                if (data.errors.validation.payer.fax != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.fax));
                    errorFieldAndMessage.put("Payer - Fax", errorMessageList);
                }
                if (data.errors.validation.payer.id != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.id));
                    errorFieldAndMessage.put("Payer - ID", errorMessageList);
                }
                if (data.errors.validation.payer.name != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.name));
                    errorFieldAndMessage.put("Payer - Name", errorMessageList);
                }
                if (data.errors.validation.payer.phone != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.phone));
                    errorFieldAndMessage.put("Payer - Phone", errorMessageList);
                }
                if (data.errors.validation.payer.tax_id != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.tax_id));
                    errorFieldAndMessage.put("Payer - Tax ID", errorMessageList);
                }
                if (data.errors.validation.payer.url != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.payer.url));
                    errorFieldAndMessage.put("Payer - URL", errorMessageList);
                }
            }
            if (data.errors.validation.procedure_id != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.procedure_id));
                errorFieldAndMessage.put("Procedure ID", errorMessageList);
            }
            if (data.errors.validation.procedure_id_qualifier != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.procedure_id_qualifier));
                errorFieldAndMessage.put("Procedure ID Qualifier", errorMessageList);
            }
            if (data.errors.validation.provider != null)
            {
                if (data.errors.validation.provider.first_name != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.first_name));
                    errorFieldAndMessage.put("Provider - First Name", errorMessageList);
                }
                if (data.errors.validation.provider.last_name != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.last_name));
                    errorFieldAndMessage.put("Provider - Last Name", errorMessageList);
                }
                if (data.errors.validation.provider.middle_name != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.middle_name));
                    errorFieldAndMessage.put("Provider - Middle Name", errorMessageList);
                }
                if (data.errors.validation.provider.npi != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.npi));
                    errorFieldAndMessage.put("Provider - NPI", errorMessageList);
                }
                if (data.errors.validation.provider.organization_name != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.organization_name));
                    errorFieldAndMessage.put("Provider - Organization Name", errorMessageList);
                }
                if (data.errors.validation.provider.pin != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.pin));
                    errorFieldAndMessage.put("Provider - PIN", errorMessageList);
                }
                if (data.errors.validation.provider.suffix != null)
                {
                    List<String> errorMessageList = new ArrayList<String>();
                    errorMessageList.addAll(Arrays.asList(data.errors.validation.provider.suffix));
                    errorFieldAndMessage.put("Provider - Suffix", errorMessageList);
                }
            }
            if (data.errors.validation.service_types != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.service_types));
                errorFieldAndMessage.put("Service Types", errorMessageList);
            }
            if (data.errors.validation.trading_partner_id != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.trading_partner_id));
                errorFieldAndMessage.put("Trading Partner ID", errorMessageList);
            }
            if (data.errors.validation.transaction_code != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.transaction_code));
                errorFieldAndMessage.put("Transaction Code", errorMessageList);
            }
            if (data.errors.validation.internal_validation_errors != null)
            {
                List<String> errorMessageList = new ArrayList<String>();
                errorMessageList.addAll(Arrays.asList(data.errors.validation.internal_validation_errors));
                errorFieldAndMessage.put("Internal Error", errorMessageList);
            }
        }
        return errorFieldAndMessage;
    }

    @Override
    public String getTransactionUUID() {
        if (data != null && data.application_data != null && data.application_data.transaction_uuid != null)
            return data.application_data.transaction_uuid;
        else return null;
    }

    @Override
    public void stripProviderData() {
        if (this.data != null && this.data.errors != null && this.data.errors.configuration != null && !this.data.errors.configuration.isEmpty())
        {
            String error = data.errors.configuration;
            if (error.contains("Please Add A Provider via your platform dashboard"))
            {
                error = error.substring(0, error.indexOf("Please Add A Provider via your platform dashboard"));
            }
            this.data.errors.configuration = error;
        }
    }
    
    public class Data implements Serializable
    {
        private static final long serialVersionUID = 42L;
        Errors errors;
        ApplicationData application_data;

        public Errors getErrors() {
            return errors;
        }

        public void setErrors(Errors errors) {
            this.errors = errors;
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
    
    public static class ApplicationData implements Serializable
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
    
    public class Errors implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String configuration;
        Validation validation;

        public String getConfiguration() {
            return configuration;
        }

        public void setConfiguration(String configuration) {
            this.configuration = configuration;
        }

        public Validation getValidation() {
            return validation;
        }

        public void setValidation(Validation validation) {
            this.validation = validation;
        }
    }
    
    public static class Payer implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String[] id;
        String[] tax_id;
        String[] name;
        String[] phone;
        String[] email;
        String[] url;
        String[] fax;

        public String[] getId() {
            return id;
        }

        public void setId(String[] id) {
            this.id = id;
        }

        public String[] getTax_id() {
            return tax_id;
        }

        public void setTax_id(String[] tax_id) {
            this.tax_id = tax_id;
        }

        public String[] getName() {
            return name;
        }

        public void setName(String[] name) {
            this.name = name;
        }

        public String[] getPhone() {
            return phone;
        }

        public void setPhone(String[] phone) {
            this.phone = phone;
        }

        public String[] getEmail() {
            return email;
        }

        public void setEmail(String[] email) {
            this.email = email;
        }

        public String[] getUrl() {
            return url;
        }

        public void setUrl(String[] url) {
            this.url = url;
        }

        public String[] getFax() {
            return fax;
        }

        public void setFax(String[] fax) {
            this.fax = fax;
        }
    }
    
    public static class Member implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String[] birth_date; // iso8601 format
        String[] gender;
        String[] first_name;
        String[] middle_name;
        String[] suffix;
        String[] id; // member identifier - can be omitted if birth_date provided
        String[] last_name;
        String[] plan_start_date; // can provide results pertaining to certain date - iso8601 format
        String[] group_number;

        public String[] getBirth_date() {
            return birth_date;
        }

        public void setBirth_date(String[] birth_date) {
            this.birth_date = birth_date;
        }

        public String[] getGender() {
            return gender;
        }

        public void setGender(String[] gender) {
            this.gender = gender;
        }

        public String[] getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String[] first_name) {
            this.first_name = first_name;
        }

        public String[] getMiddle_name() {
            return middle_name;
        }

        public void setMiddle_name(String[] middle_name) {
            this.middle_name = middle_name;
        }

        public String[] getSuffix() {
            return suffix;
        }

        public void setSuffix(String[] suffix) {
            this.suffix = suffix;
        }

        public String[] getId() {
            return id;
        }

        public void setId(String[] id) {
            this.id = id;
        }

        public String[] getLast_name() {
            return last_name;
        }

        public void setLast_name(String[] last_name) {
            this.last_name = last_name;
        }

        public String[] getPlan_start_date() {
            return plan_start_date;
        }

        public void setPlan_start_date(String[] plan_start_date) {
            this.plan_start_date = plan_start_date;
        }

        public String[] getGroup_number() {
            return group_number;
        }

        public void setGroup_number(String[] group_number) {
            this.group_number = group_number;
        }
    }
    
    public static class Provider implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String[] first_name;
        String[] middle_name;
        String[] last_name;
        String[] suffix;
        String[] pin; // personal identification number
        String[] npi;
        String[] organization_name; // ommit first and last name if sending organization name

        public String[] getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String[] first_name) {
            this.first_name = first_name;
        }

        public String[] getMiddle_name() {
            return middle_name;
        }

        public void setMiddle_name(String[] middle_name) {
            this.middle_name = middle_name;
        }

        public String[] getLast_name() {
            return last_name;
        }

        public void setLast_name(String[] last_name) {
            this.last_name = last_name;
        }

        public String[] getSuffix() {
            return suffix;
        }

        public void setSuffix(String[] suffix) {
            this.suffix = suffix;
        }

        public String[] getPin() {
            return pin;
        }

        public void setPin(String[] pin) {
            this.pin = pin;
        }

        public String[] getNpi() {
            return npi;
        }

        public void setNpi(String[] npi) {
            this.npi = npi;
        }

        public String[] getOrganization_name() {
            return organization_name;
        }

        public void setOrganization_name(String[] organization_name) {
            this.organization_name = organization_name;
        }
    }
    
    public class Validation implements Serializable
    {
        private static final long serialVersionUID = 42L;
        String[] cpt_code;
        Payer payer;
        Validation.Member member;
        Provider provider;
        ApplicationData application_data;
        String[] service_types;
        String[] procedure_id;
        String[] procedure_id_qualifier;
        String[] transaction_code;
        String[] trading_partner_id;
        String[] internal_validation_errors;
        
        private class Member implements Serializable
        {
        private static final long serialVersionUID = 42L;
            String[] id;

            public String[] getId() {
                return id;
            }

            public void setId(String[] id) {
                this.id = id;
            }
        }

        public String[] getCpt_code() {
            return cpt_code;
        }

        public void setCpt_code(String[] cpt_code) {
            this.cpt_code = cpt_code;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Provider getProvider() {
            return provider;
        }

        public void setProvider(Provider provider) {
            this.provider = provider;
        }

        public ApplicationData getApplication_data() {
            return application_data;
        }

        public void setApplication_data(ApplicationData application_data) {
            this.application_data = application_data;
        }

        public String[] getService_types() {
            return service_types;
        }

        public void setService_types(String[] service_types) {
            this.service_types = service_types;
        }

        public String[] getProcedure_id() {
            return procedure_id;
        }

        public void setProcedure_id(String[] procedure_id) {
            this.procedure_id = procedure_id;
        }

        public String[] getProcedure_id_qualifier() {
            return procedure_id_qualifier;
        }

        public void setProcedure_id_qualifier(String[] procedure_id_qualifier) {
            this.procedure_id_qualifier = procedure_id_qualifier;
        }

        public String[] getTransaction_code() {
            return transaction_code;
        }

        public void setTransaction_code(String[] transaction_code) {
            this.transaction_code = transaction_code;
        }

        public String[] getTrading_partner_id() {
            return trading_partner_id;
        }

        public void setTrading_partner_id(String[] trading_partner_id) {
            this.trading_partner_id = trading_partner_id;
        }
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

        public Integer getProcessing_time() {
            return processing_time;
        }

        public void setProcessing_time(Integer processing_time) {
            this.processing_time = processing_time;
        }

        public String getApplication_mode() {
            return application_mode;
        }

        public void setApplication_mode(String application_mode) {
            this.application_mode = application_mode;
        }

        public Integer getCredits_billed() {
            return credits_billed;
        }

        public void setCredits_billed(Integer credits_billed) {
            this.credits_billed = credits_billed;
        }

        public Integer getCredits_remaining() {
            return credits_remaining;
        }

        public void setCredits_remaining(Integer credits_remaining) {
            this.credits_remaining = credits_remaining;
        }

        public Integer getRate_limit_cap() {
            return rate_limit_cap;
        }

        public void setRate_limit_cap(Integer rate_limit_cap) {
            this.rate_limit_cap = rate_limit_cap;
        }

        public Integer getRate_limit_reset() {
            return rate_limit_reset;
        }

        public void setRate_limit_reset(Integer rate_limit_reset) {
            this.rate_limit_reset = rate_limit_reset;
        }

        public Integer getRate_limit_amount() {
            return rate_limit_amount;
        }

        public void setRate_limit_amount(Integer rate_limit_amount) {
            this.rate_limit_amount = rate_limit_amount;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }
    }

    public void setResponseData(PokitDokEligibilityErrorResponseData responseData)
    {
        this.data = responseData.data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}

