
package API.Billing;

import API.BaseRequest;
import java.io.Serializable;
import java.util.Date;

public class RequestBillingOrderSummary extends BaseRequest
{
    /**
     * Request can be made with 
     */
    public enum Type implements Serializable
    {
        ACCESSION_ORDERDATE,
        ACCESSION_SPECIMENDATE,
        CSS_ORDERID,                // In-house only
        CSSBILLING_DETAILORDERID    // In-house only
    }
    
    private Type requestType;
    private Integer cssOrderId;
    private String accession;
    private Date orderDate;
    private Date specimenDate;
    private Integer cssbillingDetailOrderId;
    
    public RequestBillingOrderSummary(RequestBillingOrderSummary.Type type, String accession, Date date)
            throws IllegalArgumentException
    {
        if (type == null)
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructur:"
                    + " Acession/Date call Received a [NULL] request type enum argument.");
        }
        
        if (accession == null || accession.isEmpty())
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructor:"
                    + " Accession/Date call received a [NULL] or empty accession argument."
                    + " RequestType:" + type.toString());
        }
        
        if (date == null)
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructor:"
                    + " Accession/Date call received a [NULL] date argument."
                    + " RequestType:" + type.toString());
        }
        
        if (type.equals(Type.CSSBILLING_DETAILORDERID) || type.equals(Type.CSS_ORDERID))
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructor:"
                    + " Accession/Date call received the wrong request type:" + type.toString());
        }
        
        this.requestType = type;
        this.accession = accession;
        
        if (type.equals(Type.ACCESSION_ORDERDATE))
        {
            this.orderDate = date;
        }
        else if (type.equals(Type.ACCESSION_SPECIMENDATE))
        {
            this.specimenDate = date;
        }
    }
    
    public RequestBillingOrderSummary(RequestBillingOrderSummary.Type type, Integer id)
            throws IllegalArgumentException
    {
        if (type == null)
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructur:"
                    + " Internal ID call Received a [NULL] request type enum argument.");
        }

        
        if (id == null || id <= 0)
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructor:"
                    + " Internal ID call received a [NULL] or <= identifier argument."
                    + " RequestType:" + type.toString());
        }
        
        if (type.equals(Type.ACCESSION_ORDERDATE) || type.equals(Type.ACCESSION_SPECIMENDATE))
        {
            throw new IllegalArgumentException("RequestBillingOrderStatus::Constructor:"
                    + " Internal ID call received the wrong request type:" + type.toString());
        }
        
        this.requestType = type;
        if (type.equals(Type.CSS_ORDERID))
        {
            this.cssOrderId = id;
        }
        else if (type.equals(Type.CSSBILLING_DETAILORDERID))
        {
            this.cssbillingDetailOrderId = id;
        }
    }

    public Type getRequestType()
    {
        return requestType;
    }

    public Integer getCssOrderId()
    {
        return cssOrderId;
    }

    public String getAccession()
    {
        return accession;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public Date getSpecimenDate()
    {
        return specimenDate;
    }

    public Integer getCssbillingDetailOrderId()
    {
        return cssbillingDetailOrderId;
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
