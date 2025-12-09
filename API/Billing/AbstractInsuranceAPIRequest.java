/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing;

import java.io.Serializable;

/**
 *
 * @author Rob
 */
public abstract class AbstractInsuranceAPIRequest implements InsuranceAPIRequest, Serializable
{
    private static final long serialVersionUID = 42L;
    
    private InsuranceAPIRequestData requestData;

    @Override
    public InsuranceAPIRequestData getRequestData()
    {
        return requestData;
    }

    @Override
    public void setRequestData(InsuranceAPIRequestData requestData)
    {
        this.requestData = requestData;
    }
}
