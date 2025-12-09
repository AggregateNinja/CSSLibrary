
package API;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseRequest<T> implements Serializable
{
    private static final long serialVersionUID = 42L;
    
    public String methodIdentifier = "";    // The unique descriptor of this method
    public String version = "1.0";          // The interface spec version
    public String origin = "";              // Description of the origin of the request
    public String requestor = "";           // Identifier of requestor from that origin
    public String requestId = "";           // Unique identifier for request
    public Date requestDateTime;
    
    public abstract String toXML();
    public abstract T fromXML(String xml);
}
