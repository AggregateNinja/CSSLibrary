
package API.Billing;

import API.BaseResponse;
import BOS.IBOS.IDetailOrderBO;
import java.util.Map;

/**
 * From an in-house perspective, this just returns an iDetailOrderBO, which
 *  is just a convenient way to get billing data.
 * 
 * But if/when we have external requests, this response class can be used
 *  to return an XML response that fits the request's version number and only
 *  supplies certain pieces of data.
 * 
 */
public class ResponseBillingOrderSummary extends BaseResponse
{
    private IDetailOrderBO iDetailOrderBO;
    
    public ResponseBillingOrderSummary()
    {
        super();
    }

    public ResponseBillingOrderSummary(Map responseFailureCodes)
    {
        super(responseFailureCodes);
    }
    
    public void setDetailOrdersBO(IDetailOrderBO iDetailOrderBO)
    {
        this.iDetailOrderBO = iDetailOrderBO;
    }
    
    public IDetailOrderBO getDetailOrderBO()
    {
        return this.iDetailOrderBO;
    }
    
    @Override
    public String toXML()
    {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
