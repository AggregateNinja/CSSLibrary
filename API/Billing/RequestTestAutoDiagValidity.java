
package API.Billing;

import API.BaseRequest;
import DOS.DiagnosisCodes;
import DOS.Orders;
import DOS.Patients;
import DOS.Subscriber;
import DOS.TestContext;
import java.util.Date;
import java.util.List;

public class RequestTestAutoDiagValidity extends BaseRequest<RequestTestAutoDiagValidity>
{
    private static final long serialVersionUID = 42L;
    
    // If the order is saved, everything will be pulled using the accession:
    private String accession = null;
    private Date dateOfService = null;
    
    // If the order is not saved, we will need the following:
    private Orders order;
    private Patients patient;
    private Subscriber subscriber;
    private List<TestContext> testContexts;
    private List<DiagnosisCodes> diagnosisCodes;
    
    private RequestTestAutoDiagValidity() {};
    
    /**
     * "API" constructor
     * @param accession 
     * @param dateOfService 
     */
    public RequestTestAutoDiagValidity(String accession, Date dateOfService)
    {
        this.accession = accession;
        this.dateOfService = dateOfService;
    }
    
    /**
     * @param order
     * @param patient
     * @param subscriber
     * @param diagnosisCodes
     * @param testContexts 
     */
    public RequestTestAutoDiagValidity(
            Orders order,
            Patients patient,
            Subscriber subscriber,
            List<DiagnosisCodes> diagnosisCodes,
            List<TestContext> testContexts)
    {
        this.order = order;
        this.patient = patient;
        this.subscriber = subscriber;
        this.diagnosisCodes = diagnosisCodes;
        this.testContexts = testContexts;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestTestAutoDiagValidity fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getAccession()
    {
        return accession;
    }

    public Date getDateOfService()
    {
        return dateOfService;
    }

    public Orders getOrder()
    {
        return order;
    }

    public Patients getPatient()
    {
        return patient;
    }

    public Subscriber getSubscriber()
    {
        return subscriber;
    }

    public List<TestContext> getTestContexts()
    {
        return testContexts;
    }

    public List<DiagnosisCodes> getDiagnosisCodes()
    {
        return diagnosisCodes;
    }
}
