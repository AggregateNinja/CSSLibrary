
package API.Billing;

import API.BaseRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RequestTestCptCodes extends BaseRequest<RequestTestCptCodes>
{
    private static final long serialVersionUID = 42L;
    private Integer insuranceId;
    private Integer clientId;
    private Integer patientId;
    private Date orderDate;
    
    // The directly ordered tests.
    // Response will return test contexts
    List<Integer> testNumbers = new LinkedList<>();

    @Override
    public String toXML()
    {
        return getXStream().toXML(this);
    }

    @Override
    public RequestTestCptCodes fromXML(String xml)
    {
        return (RequestTestCptCodes)getXStream().fromXML(xml);
    }

    private XStream getXStream()
    {
        XStream xs = new XStream(new PureJavaReflectionProvider(), new DomDriver());
        xs.aliasSystemAttribute(null, "class");
        xs.alias("cptcoderequest", RequestTestCptCodes.class);
        return xs;
    }
    
    public void setInsuranceId(Integer insuranceId)
    {
        this.insuranceId = insuranceId;
    }
    
    public void setClientId(Integer clientId)
    {
        this.clientId = clientId;
    }
    
    public void setPatientId(Integer patientId)
    {
        this.patientId = patientId;
    }
    
    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }
    
    public Integer getInsuranceId()
    {
        return this.insuranceId;
    }
    
    public Integer getClientId()
    {
        return this.clientId;
    }
    
    public Integer getPatientId()
    {
        return this.patientId;
    }
    
    public Date getOrderDate()
    {
        return this.orderDate;
    }
    
    /**
     * Only supply the test that the user put directly on the order.
     * @param testNumber 
     */
    public void addDirectlyOrderedTestNumber(Integer testNumber)
    {
        testNumbers.add(testNumber);
    }

    public List<Integer> getTestNumbers()
    {
        return testNumbers;
    }

    public void setTestNumbers(List<Integer> testNumbers)
    {
        this.testNumbers = testNumbers;
    }
}
