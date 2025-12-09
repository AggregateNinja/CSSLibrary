/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseRequest;
import java.io.Serializable;

/**
 *
 * @author Rob
 */
public class RequestTradingPartners extends BaseRequest<RequestTradingPartners> implements Serializable
{

    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestTradingPartners fromXML(String xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static enum API_PROVIDER
    {
        // TODO:: Eligibility
        PokitDok;
    }
    
    final API_PROVIDER apiProvider;
    final String insuranceName;
    final Integer insuranceNumber;
    
    public RequestTradingPartners(API_PROVIDER apiProvider, String insuranceName)
    {
        this.apiProvider = apiProvider;
        this.insuranceName = insuranceName;
        this.insuranceNumber = null;
    }
    
    public RequestTradingPartners(API_PROVIDER apiProvider, Integer insuranceNumber)
    {
        this.apiProvider = apiProvider;
        this.insuranceName = null;
        this.insuranceNumber = insuranceNumber;
    }

    public API_PROVIDER getApiProvider()
    {
        return apiProvider;
    }
    
    public String getInsuranceName()
    {
        return insuranceName;
    }
    
    public Integer getInsuranceNumber()
    {
        return insuranceNumber;
    }
}
