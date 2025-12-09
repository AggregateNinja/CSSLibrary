
package API.Billing;

import API.BaseRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * Returns the data that would represent billing detail lines. Can form the
 *  request for a saved order, or for an unsaved order by supplying all of
 *  the necessary data points.
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class RequestBillingDetailPreview extends BaseRequest<RequestOrderBillableStatus>
{
    public static final long serialVersionUID = 42L;
    
    // Two ways to make request: the Accession / Date of Service request is for external calls (API)
    // In-house can simply use the order's database identifier
    public enum Type implements Serializable
    {
        ACCESSION_DATEOFSERVICE,
        ORDERID
    }

    private Type requestType;
    
    private String accession;
    private Date dateOfService;
    
    private Integer userId;
    
    private Integer orderId;
    
    public RequestBillingDetailPreview(String accession, Date dateOfService, Integer userId)
            throws IllegalArgumentException
    {
        if (accession == null || accession.isEmpty()) throw new IllegalArgumentException(
            "RequestBillingDetailPreview::Saved order constructor: Received"
                    + " [NULL] String accession");
        
        if (dateOfService == null) throw new IllegalArgumentException(
            "RequestBillingDetailPreview::Saved order constructor: Received"
                    + " [NULL] dateOfService argument");
        
        this.accession = accession;
        this.dateOfService = dateOfService;
        this.userId = userId;
        this.requestType = Type.ACCESSION_DATEOFSERVICE;
    }
    
    public RequestBillingDetailPreview(
            Integer orderId,
            Integer userId)
            throws IllegalArgumentException
    {
        
        if (orderId == null) throw new IllegalArgumentException(
                "RequestOrderBillableStatus::In-house order constructor:"
                        + " Received [NULL] orderId");
        this.orderId = orderId;
        this.userId = userId;
        this.requestType = Type.ORDERID;
    }

    public Type getRequestType()
    {
        return requestType;
    }

    public String getAccession()
    {
        return accession;
    }

    public Date getDateOfService()
    {
        return dateOfService;
    }

    public Integer getOrderId()
    {
        return orderId;
    }
    
    public Integer getUserId()
    {
        return userId;
    }

    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestOrderBillableStatus fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
