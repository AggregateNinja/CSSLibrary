
package API.Billing;

import API.BaseResponse;
import java.util.LinkedList;
import java.util.List;

/**
 * This is an in-house only API call.
 * @author Tom Rush <trush at csslis.com>
 */
public class ResponseCreateLISOrderBillingData extends BaseResponse<ResponseCreateLISOrderBillingData>
{
    /**
     * There can be a non-exception failure to produce data
     */
    public enum Response
    {
        ORDER_NOT_FOUND,
        ORDER_MISSING_DATA,
        FEE_SCHEDULE_MISSING_DATA,
        SUCCESS
    }
    
    private Response response;
    
    // List of messages detailing what went wrong (should be presentable to the user)
    private List<String> userMessages = new LinkedList<>();
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Response getResponse()
    {
        return response;
    }

    public List<String> getUserMessages()
    {
        return userMessages;
    }

    public void setResponse(Response response)
    {
        this.response = response;
    }

    public void setUserMessages(List<String> userMessages)
    {
        this.userMessages = userMessages;
    }
    
    public void addUserMessage(String userMessage)
    {
        if (userMessage != null && userMessage.isEmpty() == false)
        {
            this.userMessages.add(userMessage);
        }
    }

    @Override
    public ResponseCreateLISOrderBillingData fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
