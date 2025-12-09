
package API.Billing;

import API.BaseRequest;

/**
 * This is an in-house only API call
 * @author Tom Rush <trush at csslis.com>
 */
public class RequestCreateLISOrderBillingData extends BaseRequest<RequestOrderBillableStatus>
{
    private static final long serialVersionUID = 42L;
    
    private Integer orderId;
    private Integer userId;
    
    private RequestCreateLISOrderBillingData(){}
    
    public RequestCreateLISOrderBillingData(Integer orderId, Integer userId) throws IllegalArgumentException
    {
        if (orderId == null || orderId <= 0)
        {
            throw new IllegalArgumentException(
                    "RequestCreateLISOrderBillingData::Constructor: Received a [NULL] or invalid orderId");
        }
        
        if (userId == null || userId <= 0)
        {
            throw new IllegalArgumentException(
                    "RequestCreateLISOrderBillingData::Constructor: Received a [NULL] or invalid userId");            
        }
        
        this.orderId = orderId;
        this.userId = userId;
    }

    public Integer getOrderId()
    {
        return this.orderId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestOrderBillableStatus fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
