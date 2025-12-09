
package API;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseResponse<T> implements Serializable
{
    private static final long serialVersionUID = 42L;
    
    public String methodIdentifier = "";    // Returns the name of the method that this is being returned from
    public String version = "";             // Returns the version of this method
    public String origin = "LabNameHere";
    public String responder = "AvalonLIS";
    public String requestId = "";           // Matches the request id
    public Date responseDateTime;           // Server time of response
    
    // Whether the request could be performed. Returns SUCCESS if the request
    // was well-formed, and contains every piece of necessary data to begin.
    public static final int SUCCESS_STATUS = 1;
    public static final int FAILURE_STATUS = 0;    
    protected Integer responseStatus;
    
    // Maps a response code to an optional message
    protected Map<String, String> responseFailureCodes;
    
    /**
     * Default constructor for successful request (no response codes necessary)
     */
    public BaseResponse()
    {
        this.responseStatus = SUCCESS_STATUS;
        this.responseFailureCodes = new HashMap<>();
    }
    
    public BaseResponse(Map<String, String> responseFailureCodes)
    {
        this.responseStatus = FAILURE_STATUS;
        
        // If it's a failed response but no response code is provided, use
        // a default 'unspecified error' code
        if (responseFailureCodes == null || responseFailureCodes.isEmpty())
        {
            this.responseFailureCodes.put("X1", "Unspecified error");
        }
        else
        {
            this.responseFailureCodes = responseFailureCodes;
        }
    }
    
    public abstract String toXML();
    public abstract T fromXML(String xml);
    
    public Integer getResponseStatus()
    {
        return this.responseStatus;
    }
    
    public Map<String, String> getResponseFailureCodes()
    {
        return this.responseFailureCodes;
    }
}
