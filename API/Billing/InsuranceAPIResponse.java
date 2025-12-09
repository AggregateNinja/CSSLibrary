/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */
package API.Billing;

/**
 *
 * @author Rob
 */
public interface InsuranceAPIResponse
{
    public InsuranceAPIResponseData getResponseData();

    public void setResponseData(InsuranceAPIResponseData responseData);
    public RequestInsuranceCoverageInformation.API_PROVIDER getApiProvider();
}
