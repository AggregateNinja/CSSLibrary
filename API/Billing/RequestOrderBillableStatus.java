
package API.Billing;

import API.BaseRequest;
import DOS.Clients;
import DOS.DiagnosisCodes;
import DOS.Doctors;
import DOS.Orders;
import DOS.Patients;
import DOS.Results;
import DOS.Subscriber;
import DOS.TestContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Requests the billing status of an order and any validation messages for
 *  the user. Can be run for a saved or unsaved order, depending on the
 *  constructor for the request.
 */
public class RequestOrderBillableStatus extends BaseRequest<RequestOrderBillableStatus>
{
    private static final long serialVersionUID = 42L;
    public enum Type implements Serializable
    {
        SAVED_ORDER,
        UNSAVED_ORDER
    }
    
    private Type requestType;
    
    private String accession;
    private Date dateOfService;
    
    private Orders order;
    private Clients client;
    private Patients patient;
    private Subscriber subscriber;
    private Doctors doctor;
    //private List<TestContext> testContexts;
    //private List<Results> results;
    private Map<Results, TestContext> resultTestContextMap;
    private List<DiagnosisCodes> diagnosisCodes;
    
    private Integer userId;
    
    private RequestOrderBillableStatus() {};

    /**
     * Saved order constructor. API calls will not have our internal order ID,
     *  so accession/date of service is used. DoS can either be Order Date or
     *  Specimen Date, depending on the "LastDateOfServiceUseSpecimenDate"
     *  preference value.
     * @param accession
     * @param dateOfService
     * @param userId
     * @throws IllegalArgumentException 
     */
    public RequestOrderBillableStatus(String accession, Date dateOfService, Integer userId)
            throws IllegalArgumentException
    {
        if (accession == null || accession.isEmpty()) throw new IllegalArgumentException(
                "RequestOrderBillableStatus::Saved order constructor: Received [NULL] String accession");
        this.accession = accession;
        this.dateOfService = dateOfService;
        this.userId = userId;
        this.requestType =Type.SAVED_ORDER;
    }
    
    /**
     * Unsaved order - provide the data as parameters.
     *  
     * @param userId
     * @param order
     * @param client
     * @param patient
     * @param subscriber
     * @param doctor
     * @param resultTestContextMap
     * @param diagnosisCodes
     * @throws IllegalArgumentException 
     */
    public RequestOrderBillableStatus(
            Integer userId,
            Orders order,
            Clients client,
            Patients patient,
            Subscriber subscriber,
            Doctors doctor,
            Map<Results, TestContext> resultTestContextMap,
            //List<TestContext> testContexts, // This should be replaced with result lines, and we can get the test contexts and no-charged status from that
            List<DiagnosisCodes> diagnosisCodes)
            throws IllegalArgumentException
    {
        this.userId = userId;
        this.order = order;
        this.client = client;
        this.patient = patient;
        this.subscriber = subscriber;
        this.doctor = doctor;
        //this.testContexts = testContexts;
        //this.results = results;
        this.resultTestContextMap = resultTestContextMap;
        this.diagnosisCodes = diagnosisCodes;
        this.requestType = Type.UNSAVED_ORDER;
    }

    public Type getRequestType()
    {
        return requestType;
    }

    public String getAccession()
    {
        return accession;
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

    public Map<Results, TestContext> getResultTestContextMap()
    {
        return this.resultTestContextMap;
    }
    
    public List<DiagnosisCodes> getDiagnosisCodes()
    {
        return diagnosisCodes;
    }

    public Date getDateOfService()
    {
        return dateOfService;
    }

    public Clients getClient()
    {
        return client;
    }

    public Doctors getDoctor()
    {
        return doctor;
    }
    
    public Integer getUserId()
    {
        return this.userId;
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
