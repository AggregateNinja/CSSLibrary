/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 *
 * @author Rob
 */
public class RequestInsuranceCoverageInformation extends BaseRequest<RequestInsuranceCoverageInformation> implements Serializable
{
    private static final long serialVersionUID = 42L;

    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestInsuranceCoverageInformation fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // TODO:: Eligibility
    public static enum API_PROVIDER
    {
        ChangeHealtcare,
        PokitDok,
        Infinix;
    }
    
    final API_PROVIDER apiProvider;
    final String firstName;
    final String lastName;
    final String birthDate;
    final String gender;
    final String groupNumber;
    final String memberId;
    final String providerFirstName;
    final String providerLastName;
    final String organizationName;
    final String npi;
    final String cptCode;
    final HashMap<String, String> cptModifierMap;
    final String tradingPartnerID;
    final String tradingPartnerName;
    final String dateOfService;
    final Integer userId;
    final String transactionUUID;
    
    public RequestInsuranceCoverageInformation(API_PROVIDER apiProvider, String firstName, String lastName,
                            String birthDate, String gender, String groupNumber, String memberId,
                            String providerFirstName, String providerLastName, String organizationName,
                            String npi, String cptCode, HashMap<String, String> cptModifierMap, String tradingPartnerID, String tradingPartnerName, String dateOfService,
                            Integer userId, String transactionUUID)
    {
        this.apiProvider = apiProvider;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.groupNumber = groupNumber;
        this.memberId = memberId;
        this.providerFirstName = providerFirstName;
        this.providerLastName = providerLastName;
        this.organizationName = organizationName;
        this.npi = npi;
        this.cptCode = cptCode;
        this.cptModifierMap = cptModifierMap;
        this.tradingPartnerID = tradingPartnerID;
        this.tradingPartnerName = tradingPartnerName;
        this.dateOfService = dateOfService;
        this.userId = userId;
        this.transactionUUID = transactionUUID;
    }

    public API_PROVIDER getApiProvider()
    {
        return apiProvider;
        //return API_PROVIDER.ChangeHealtcare;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getGender()
    {
        return gender;
    }

    public String getGroupNumber()
    {
        return groupNumber;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public String getProviderFirstName()
    {
        return providerFirstName;
    }

    public String getProviderLastName()
    {
        return providerLastName;
    }

    public String getOrganizationName()
    {
        return organizationName;
    }

    public String getNpi()
    {
        return npi;
    }

    public String getCptCode()
    {
        return cptCode;
    }
    
    public HashMap<String, String> getCptModifierMap()
    {
        return cptModifierMap;
    }

    public String getTradingPartnerID()
    {
        return tradingPartnerID;
    }
    
    public String getTradingPartnerName()
    {
        return tradingPartnerName;
    }
    
    public String getDateOfService()
    {
        return dateOfService;
    }
    
    public Integer getUserId()
    {
        return userId;
    }
    
    public String getTransactionUUID()
    {
        return transactionUUID;
    }
}
