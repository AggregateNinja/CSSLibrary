/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing;

/**
 *
 * @author Rob
 */
public interface InsuranceAPIRequest
{
    public InsuranceAPIResponse request(InsuranceAPIRequestData request);

    public InsuranceAPIRequestData getRequestData();

    public void setRequestData(InsuranceAPIRequestData requestData);
}
