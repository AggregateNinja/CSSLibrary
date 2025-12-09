/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing;

import API.Billing.RequestInsuranceCoverageInformation.API_PROVIDER;
import java.io.Serializable;

/**
 *
 * @author Rob
 */
public abstract class AbstractInsuranceAPIResponse implements InsuranceAPIResponse, Serializable
{
    private static final long serialVersionUID = 42L;
    
    private InsuranceAPIResponseData responseData = null;
    private final API_PROVIDER apiProvider;

    public AbstractInsuranceAPIResponse(API_PROVIDER apiProvider)
    {
        this.apiProvider = apiProvider;
    }

    @Override
    public API_PROVIDER getApiProvider()
    {
        return apiProvider;
    }
    
    @Override
    public InsuranceAPIResponseData getResponseData()
    {
        return responseData;
    }

    @Override
    public void setResponseData(InsuranceAPIResponseData responseData)
    {
        this.responseData = responseData;
    }
}
