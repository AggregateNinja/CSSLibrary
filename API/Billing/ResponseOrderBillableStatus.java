
package API.Billing;

import API.BaseResponse;
import Utility.Pair;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResponseOrderBillableStatus extends BaseResponse<ResponseOrderBillableStatus>
{
    private static final long serialVersionUID = 42L;
    
    // A list of string --> string, where the first string is the type
    // of validation problem (e.g. "Fee Schedule Missing Tests", "Missing Order Data")
    // and the second is a description of the item (e.g. one of the missing fee
    // schedule tests)
    private List<Pair<String, String>> validationMessages = new LinkedList<>();
    
    public ResponseOrderBillableStatus()
    {
        super();
    }
    
    public ResponseOrderBillableStatus(Map<String, String> responseFailureCodes)
    {
        super(responseFailureCodes);
    }
    
    /**
     * Replaces the current messages with the supplied list
     * @param validationMessages 
     */
    public void setValidationMessages(List<Pair<String, String>> validationMessages)
    {
        if (validationMessages != null)
        {
            this.validationMessages = validationMessages;
        }
    }
    
    public void addValidationMessage(String type, String validationMessage)
    {
        if (validationMessage != null && validationMessage.isEmpty() == false)
        {
            this.validationMessages.add(new Pair(type, validationMessage));
        }
    }
    
    public void addValidationMessages(List<Pair<String, String>> validationMessages)
    {
        this.validationMessages.addAll(validationMessages);
    }
    
    public Boolean isBillable()
    {
        return  (this.validationMessages != null && this.validationMessages.isEmpty());
    }
    
    /**
     * If not billable, provides a list of strings for the user explaining why
     * @return 
     */
    public List<Pair<String, String>> getValidationPairs()
    {
        return this.validationMessages;
    }
    
    public List<String> getValidationMessages()
    {
        List<String> messages = new LinkedList<>();
        if (this.validationMessages != null)
        {
            for (Pair<String, String> pair : this.validationMessages)
            {
                messages.add(pair.getSecond());
            }
        }
        return messages;
    }
    

    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ResponseOrderBillableStatus fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}