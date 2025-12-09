
package API.Billing;

import API.BaseResponse;
import java.util.Map;

public class ResponseQueueClaimsForTransmission extends BaseResponse<ResponseQueueClaimsForTransmission>
{
    
    public ResponseQueueClaimsForTransmission()
    {
        super();
    }

    public ResponseQueueClaimsForTransmission(Map<String, String> responseFailureCodes)
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
