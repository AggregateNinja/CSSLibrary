/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseResponse;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class ResponseInsuranceTransfer extends BaseResponse<ResponseQueueClaimsForTransmission>
{
    
    public ResponseInsuranceTransfer()
    {
        super();
    }

    public ResponseInsuranceTransfer(Map<String, String> responseFailureCodes)
    {
        super(responseFailureCodes);
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseQueueClaimsForTransmission fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
