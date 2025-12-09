/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing.Eligibility;

import API.Billing.InsuranceAPIRequestData;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rob
 */
public class PokitDokEligibilityRequestData implements InsuranceAPIRequestData, Serializable
{
    private static final long serialVersionUID = 42L;
    String dateOfService;
    String cpt_code;
    Payer payer;
    Member member;
    Provider provider;
    ApplicationData application_data;
    String[] service_types;
    String procedure_id;
    String procedure_id_qualifier; // cpt, hcpcs, etc.
    String transaction_code; // default 'request', also could be 'cancellation'
    String trading_partner_id;
    List<String> tradingPartnerList;
    
    public void setRequestData(PokitDokEligibilityRequestData requestData)
    {
        this.dateOfService = requestData.dateOfService;
        this.cpt_code = requestData.cpt_code;
        this.member = requestData.member;
        this.payer = requestData.payer;
        this.procedure_id = requestData.procedure_id;
        this.procedure_id_qualifier = requestData.procedure_id_qualifier;
        this.provider = requestData.provider;
        this.service_types = requestData.service_types;
        this.trading_partner_id = requestData.trading_partner_id;
        this.transaction_code = requestData.transaction_code;
        this.application_data = requestData.application_data;
    }
    
    public static class ApplicationData
    {
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
    
    public static class Payer
    {
        String id;
        String tax_id;
        String name;
        String phone;
        String email;
        String url;
        String fax;

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
    }
    
    public static class Member
    {
        String birth_date; // iso8601 format
        String gender;
        String first_name;
        String middle_name;
        String suffix;
        String id; // member identifier - can be omitted if birth_date provided
        String last_name;
        String plan_start_date; // can provide results pertaining to certain date - iso8601 format
        String group_number;

        public String getBirth_date()
        {
            return birth_date;
        }

        public void setBirth_date(String birth_date)
        {
            this.birth_date = birth_date;
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

        public String getSuffix()
        {
            return suffix;
        }

        public void setSuffix(String suffix)
        {
            this.suffix = suffix;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getLast_name()
        {
            return last_name;
        }

        public void setLast_name(String last_name)
        {
            this.last_name = last_name;
        }

        public String getPlan_start_date()
        {
            return plan_start_date;
        }

        public void setPlan_start_date(String plan_start_date)
        {
            this.plan_start_date = plan_start_date;
        }

        public String getGroup_number()
        {
            return group_number;
        }

        public void setGroup_number(String group_number)
        {
            this.group_number = group_number;
        }
    }
    
    public static class Provider
    {
        String first_name;
        String middle_name;
        String last_name;
        String suffix;
        String pin; // personal identification number
        String npi;
        String organization_name; // ommit first and last name if sending organization name

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

        public String getNpi()
        {
            return npi;
        }

        public void setNpi(String npi)
        {
            this.npi = npi;
        }

        public String getOrganization_name()
        {
            return organization_name;
        }

        public void setOrganization_name(String organization_name)
        {
            this.organization_name = organization_name;
        }
    }

    public String getCpt_code()
    {
        return cpt_code;
    }

    public void setCpt_code(String cpt_code)
    {
        this.cpt_code = cpt_code;
    }

    public Payer getPayer()
    {
        return payer;
    }

    public void setPayer(Payer payer)
    {
        this.payer = payer;
    }

    public Member getMember()
    {
        return member;
    }

    public void setMember(Member member)
    {
        this.member = member;
    }

    public Provider getProvider()
    {
        return provider;
    }

    public void setProvider(Provider provider)
    {
        this.provider = provider;
    }

    public String[] getService_types()
    {
        return service_types;
    }

    public void setService_types(String[] service_types)
    {
        this.service_types = service_types;
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

    public String getTransaction_code()
    {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code)
    {
        this.transaction_code = transaction_code;
    }

    public String getTrading_partner_id()
    {
        return trading_partner_id;
    }

    public void setTrading_partner_id(String trading_partner_id)
    {
        this.trading_partner_id = trading_partner_id;
    }

    public ApplicationData getApplication_data() {
        return application_data;
    }

    public void setApplication_data(ApplicationData application_data) {
        this.application_data = application_data;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public void setDateOfService(String dateOfService) {
        this.dateOfService = dateOfService;
    }
}
