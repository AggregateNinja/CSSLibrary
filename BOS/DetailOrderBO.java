
package BOS;

import BL.BillingBL;
import BOS.IBOS.IDetailOrderBO;
import DAOS.ClientDAO;
import DAOS.DetailCptCodeDAO;
import DAOS.DetailInsuranceDAO;
import DAOS.DetailOrderDAO;
import DAOS.DoctorDAO;
import DAOS.InsuranceDAO;
import DAOS.OrderDAO;
import DAOS.PatientDAO;
import DAOS.SubscriberDAO;
import DOS.Clients;
import DOS.CptModifier;
import DOS.DetailCptCode;
import DOS.DetailCptModifier;
import DOS.DetailInsurance;
import DOS.DetailOrder;
import DOS.DetailOrderEvent;
import DOS.DiagnosisCodes;
import DOS.Doctors;
import DOS.Insurances;
import DOS.Orders;
import DOS.Patients;
import DOS.Subscriber;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a single order in the billing system and related data.
 *  Lazily loads and returns data. Subsequent "get" calls will just return
 *  the cached data. To force re-query of the database, call invalidateData().
 * 
 * To pre-cache all available data, call loadAllData();
 * 
 * NOTE: This class is not currently thread-safe! It should NOT be used by
 *  two separate threads (e.g. writing to class members while other thread reads).
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class DetailOrderBO implements IDetailOrderBO, Serializable
{
    public static final long serialVersionUID = 42L;
    
    private final Integer detailOrderId;
    
    // ----- Cached Data -------------------------------------------------------
    private DetailOrder detailOrder = null;
    private Orders order;
    private Patients patient = null;
    private Subscriber subscriber = null;
    private Insurances rankOneInsurance = null;
    private Clients client = null;
    private Doctors doctor = null;
    
    // Keep a separate reference to the actively used insurance
    private DetailInsurance rankOneDetailInsurance = null;
    
    private Collection<DetailInsurance> detailInsurances = null;
    
    private Collection<DetailCptCode> detailCptCodes = null;
    
    // detailCptCodeId --> attached CptModifiers
    private Map<Integer, Collection<CptModifier>> cptModifierMap = null;
    
    // detailCptCodeId --> attached DiagnosisCodes
    private Map<Integer, Collection<DiagnosisCodes>> cptDiagnosisMap = null;
     
    private Collection<DetailOrderEvent> detailOrderEvents = null;
    
    // ----- Constructor -------------------------------------------------------
    private DetailOrderBO(){ this.detailOrderId = null;};
    
    public DetailOrderBO(Integer detailOrderId)
            throws IllegalArgumentException
    {
        if (detailOrderId == null)
        {
            throw new IllegalArgumentException("DetailOrderBO::Constructor"
                    + " received [NULL] detail order id argument");
        }
        this.detailOrderId = detailOrderId;
    }
    // -------------------------------------------------------------------------
    /**
     * Lazily loads and returns the detail order object
     * @return
     * @throws SQLException 
     */
    @Override
    public DetailOrder getDetailOrder() throws SQLException
    {
        if (this.detailOrder == null)
        {
            try
            {
                this.detailOrder = DetailOrderDAO.get(this.detailOrderId);
                if (this.detailOrder == null || this.detailOrder.getIddetailOrders() == null)
                {
                    throw new Exception("Error loading DetailOrder object for "
                            + this.detailOrderId.toString());
                }
            }
            catch (Exception ex)
            {
                throw new SQLException("DetailOrderBO::getDetailOrder exception:"
                        + " " + (ex.getMessage() == null? "[NULL] exception message" : ex.getMessage()));
            }
        }
        
        return this.detailOrder;
    }

    @Override
    public Collection<DetailInsurance> getDetailInsurances() throws SQLException
    {
        if (this.detailInsurances == null || this.rankOneDetailInsurance == null)
        {
            try
            {
                this.detailInsurances = DetailInsuranceDAO.getByDetailOrderId(detailOrderId);
                if (this.detailInsurances == null)
                {
                    throw new Exception("Error retrieving detail insurances for"
                            + " detail order Id " + this.detailOrderId.toString());
                }
                for (DetailInsurance detailInsurance : this.detailInsurances)
                {
                    if (detailInsurance.getRank().equals(1))
                    {
                        this.rankOneDetailInsurance = detailInsurance;
                    }
                    break;
                }
            }
            catch (Exception ex)
            {
                throw new SQLException("DetailOrderBO::getDetailInsurances exception:"
                    + " " + (ex.getMessage() == null? "[NULL] exception message" : ex.getMessage()));
            }
        }
        
        return this.detailInsurances;
    }

    @Override
    public BigDecimal getBillAmountTotal() throws SQLException
    {
        BigDecimal billAmountTotal = BigDecimal.ZERO;
        if (this.detailCptCodes == null)
        {
            getDetailCptCodes();
        }
        
        for (DetailCptCode detailCptCode : this.detailCptCodes)
        {
            billAmountTotal = billAmountTotal.add(detailCptCode.getBillAmount());
        }
        
        return billAmountTotal;
    }

    @Override
    public DetailInsurance getRankOneInsurance() throws Exception
    {
        if (this.rankOneDetailInsurance == null)
        {
            getDetailInsurances();
        }
        return this.rankOneDetailInsurance;
    }
    
    @Override
    public Collection<DetailCptCode> getDetailCptCodes() throws SQLException
    {
        if (this.detailCptCodes == null)
        {
            try
            {
                this.detailCptCodes = DetailCptCodeDAO.getByDetailOrderId(this.detailOrderId);
                if (this.detailCptCodes == null)
                {
                    throw new Exception("Error retrieving detail cpt codes for"
                            + " detail order Id " + this.detailOrderId.toString());
                }
            }
            catch (Exception ex)
            {
                throw new SQLException("DetailOrderBO::getDetailOrder exception:"
                    + " " + (ex.getMessage() == null? "[NULL] exception message" : ex.getMessage()));
            }
        }
        return this.detailCptCodes;
    }

    /**
     * Returns a list of Cpt Modifier objects that are currently attached
     *  to a detailCptCode line.
     * @param detailCptCodeId
     * @return Collection of Cpt Modifier objects
     * @throws java.lang.Exception
     */
    @Override
    public Collection<CptModifier> getModifiersForDetailCptCodeId(Integer detailCptCodeId) throws Exception
    {
        if (detailCptCodeId == null || detailCptCodeId <= 0)
        {
            throw new IllegalArgumentException("DetailOrderBO::getModifiersForDetailCptCodeId:"
                    + " Received a [NULL] or invalid detaolCptCodeId argument");
        }
        
        // If we don't have a cached map of modifiers
        if (this.cptModifierMap == null)
        {
            // Load the detail cpt codes if they're not present
            if (this.detailCptCodes == null)
            {
                try
                {
                    this.detailCptCodes =  getDetailCptCodes();
                    if (this.detailCptCodes == null)
                    {
                        throw new SQLException("DetailOrderBO::getModifiersForDetailCptCodeId:"
                                + " Error attempting to load and cache detailCptCodes to"
                                + " populate the modifiers. detailOrderId was "
                                + this.detailOrderId.toString());
                    }                    
                }
                catch (SQLException ex)
                {
                    throw new Exception("DetailOrderBO::getModifiersForDetailCptCodeId:"
                            + " Exception: " + (ex.getMessage() == null? "[NULL]" : ex.getMessage()));
                }
            }

            // For each detail cpt code line, retrieve a list of modifiers
            DAOS.CptModifierDAO cmdao = new DAOS.CptModifierDAO();
            for (DetailCptCode detailCptCode : this.detailCptCodes)
            {
                try
                {
                    Collection<DetailCptModifier> detailCptModifiers = 
                            DAOS.DetailCptModifierDAO.getByDetailCptCodeId(
                                    detailCptCode.getIddetailCptCodes());
                    
                    if (detailCptModifiers == null)
                    {
                        throw new SQLException("Exception retrieving"
                                + " DetailCptModifier rows for detailCptCodeId "
                                + detailCptCodeId.toString());
                    }
                    
                    List<CptModifier> cptModifiers = new LinkedList<>();
                    for (DetailCptModifier detailCptModifier : detailCptModifiers)
                    {
                        try
                        {
                            CptModifier modifier = cmdao.GetById(detailCptModifier.getCptModifierId());
                            if (modifier == null)
                            {
                                throw new SQLException("Could not retrieve Cpt Modifier object with cptModifierId "
                                        + detailCptModifier.getCptModifierId().toString());
                            }
                            cptModifiers.add(modifier);
                        }
                        catch (Exception ex)
                        {
                            throw new Exception("Exception loading Cpt Modifier: "
                                    + (ex.getMessage() == null ? "Exception [NULL]"
                                            : ex.getMessage()));
                        }
                    }
                    
                    if (this.cptModifierMap == null) this.cptModifierMap = new HashMap<>();
                    this.cptModifierMap.put(detailCptCode.getIddetailCptCodes(), cptModifiers);
                }
                catch (Exception ex)
                {
                    throw new Exception("DetailOrderBO::getModifiersForDetailCptCodeId:"
                            + " Exception: " + (ex.getMessage() == null? "[NULL]" : ex.getMessage()));
                }
            }
        }
        
        return this.cptModifierMap.get(detailCptCodeId);
    }

    @Override
    public Orders getOrder() throws Exception
    {
        if (this.order == null)
        {
            DetailOrder detailOrder = getDetailOrder();
            OrderDAO odao = new OrderDAO();
            this.order = odao.GetOrderById(detailOrder.getOrderId());
            if (this.order == null || this.order.getIdOrders() == null ||
                    this.order.getIdOrders().equals(0))
            {
                throw new SQLException("DetailOrderBO::getOrder:"
                        + " Could not retrieve order from detailOrderId: " + detailOrder.getIddetailOrders());
            }
        }
        return this.order;
    }
     
    @Override
    public Patients getPatient() throws Exception
    {
        if (this.patient == null)
        {
            this.order = getOrder();
            PatientDAO pdao = new PatientDAO();
            this.patient = pdao.GetPatientById(this.order.getPatientId());
            if (this.patient == null || this.patient.getIdPatients() == null ||
                    this.patient.getIdPatients().equals(0))
            {
                throw new SQLException("DetailOrderBO::getPatient:"
                        + " Could not retrieve patient from orderId " + String.valueOf(order.getPatientId()));
            }
        }
        return this.patient;
    }

    @Override
    public Clients getClient() throws Exception
    {
        if (this.client == null)
        {
            this.order = getOrder();
            ClientDAO cdao = new ClientDAO();
            this.client = cdao.GetClientById(this.order.getClientId());
            if (this.client == null || this.client.getIdClients() == null ||
                    this.client.getIdClients().equals(0))
            {
                throw new SQLException("DetailOrderBO::getClient:"
                        + " Could not retrieve client from orderId " + String.valueOf(order.getClientId()));
            }
        }
        return this.client;
    }

    /**
     * Can return a [NULL] value if no doctor is attached to the order.
     * @return
     * @throws Exception 
     */
    @Override
    public Doctors getDoctor() throws Exception
    {
        if (this.doctor == null)
        {
            this.order = getOrder();
            if (this.order.getDoctorId() != null && this.order.getDoctorId() > 0)
            {
                DoctorDAO ddao = new DoctorDAO();
                this.doctor = ddao.GetDoctorById(this.order.getDoctorId());
                if (this.doctor == null || this.doctor.getIddoctors() == null ||
                        this.doctor.getIddoctors().equals(0))
                {
                    throw new SQLException("DetailOrderBO::getDoctor:"
                            + " Could not retrieve doctor from orderId " + this.order.getIdOrders().toString());
                }
            }
        }
        return this.doctor;
    }

    
    /**
     * Retrieves all of the events the detail order is associated with in 
     *  chronological order.
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public Collection<DetailOrderEvent> getEvents() throws Exception
    {

        if (this.detailOrderEvents == null)
        {
            this.detailOrder = getDetailOrder();
            
            this.detailOrderEvents = BillingBL.getDetailOrderEvents(this.detailOrder.getIddetailOrders());
            if (this.detailOrderEvents == null)
            {
                throw new Exception("DetailOrderBO::getEvents:"
                        + " Received a [NULL] detailOrderEvents collection from BillingBL.getDetailOrderEvents");
            }            
        }
        
        return this.detailOrderEvents;
    }

    @Override
    public Collection<DetailCptCode> getDetailCptCodesWithBalance() throws Exception
    {
        Collection<DetailCptCode> detailCptCodesWithBalance = null;
        detailCptCodesWithBalance = getDetailCptCodes();
        for (DetailCptCode detailCptCode : detailCptCodes)
        {
            BigDecimal rowBalance = detailCptCode.getPaid().subtract(detailCptCode.getBillAmount());
            if (rowBalance.compareTo(BigDecimal.ZERO) < 0)
            {
                detailCptCodesWithBalance.add(detailCptCode);
            }
        }
        return detailCptCodesWithBalance;
    }

    /**
     * Removes any cached data. Any subsequent requests will query the database
     */
    @Override
    public void invalidateData()
    {
        
        this.detailOrder = null;
        this.order = null;
        this.patient = null;
        this.client = null;
        this.doctor = null;
        this.rankOneDetailInsurance = null;
        this.detailInsurances = null;
        this.detailCptCodes = null;
        this.cptModifierMap = null;
        this.cptDiagnosisMap = null;
        this.detailOrderEvents = null;
        
        // TODO: as data is added, include it in this method to force re-query
    }
    
    /**
     * Calls every getter method, caching whatever data was not already loaded.
     * Will not throw out currently cached data. If you want to reload everything,
     * call invalidateData() first.
     * 
     * @throws java.sql.SQLException
     */
    @Override
    public void loadAllData() throws Exception
    {
        getDetailOrder();
        getRankOneInsurance(); // <-- calls "getDetailInsurances"
        
        for (DetailCptCode detailCptCode : getDetailCptCodes())
        {
            getModifiersForDetailCptCodeId(detailCptCode.getIddetailCptCodes());
        }
        getOrder();
        getPatient();
        getClient();
        getDoctor();
        getEvents();
        // getDiagnosisCodesForDetailCptCode();
        // getAssignedDiagnosisCodeForDetailCptCode();
        // getDetailCptCodesWithBalances();
    }
    

    @Override
    public DiagnosisCodes getAssignedDiagnosisCodeForDetailCptCodeId(Integer detailCptCodeId) throws Exception
    {
        
        if (this.cptDiagnosisMap == null) this.cptDiagnosisMap = new HashMap<>();
        
        DiagnosisCodes assignedCode = null;
        // Just load all here
        for (DetailCptCode detailCptCode : getDetailCptCodes())
        {
            Collection<DiagnosisCodes> diagnosisCodes = BillingBL.getDiagnosisCodesForDetailCptCodeId(detailCptCodeId);
            this.cptDiagnosisMap.put(detailCptCode.getIddetailCptCodes(), diagnosisCodes);
            if (diagnosisCodes.size() > 0 && detailCptCode.getIddetailCptCodes().equals(detailCptCodeId))
            {
                // Just grab the "attached" one:
                assignedCode = (diagnosisCodes.iterator().next());
            }
        }
        return assignedCode;
    }
    
    // ----------------- TODO: implement these and un-deprecate them -----------
    @Deprecated
    @Override
    public Collection<DiagnosisCodes> getDiagnosisCodesForDetailCptCodeId(Integer detailCptCodeId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // -------------------------------------------------------------------------

    @Override
    public Subscriber getSubscriber() throws Exception {
        if (this.subscriber == null)
        {
            this.order = getOrder();
            SubscriberDAO sdao = new SubscriberDAO();
            this.subscriber = sdao.GetSubscriberById(this.order.getSubscriberId());
            if (this.subscriber == null || this.subscriber.getIdSubscriber()== null ||
                    this.subscriber.getIdSubscriber().equals(0))
            {
                throw new SQLException("DetailOrderBO::getSubscriber:"
                        + " Could not retrieve subscriber from orderId " + String.valueOf(order.getSubscriberId()));
            }
        }
        return this.subscriber;
    }

    @Override
    public Insurances getRankOneInsuranceInformation() throws Exception {
        if (this.rankOneInsurance == null)
        {
            this.rankOneDetailInsurance = getRankOneInsurance();
            InsuranceDAO insDAO = new InsuranceDAO();
            this.rankOneInsurance = insDAO.GetInsurance(this.rankOneDetailInsurance.getInsuranceId());
            if (this.rankOneInsurance == null || this.rankOneInsurance.getIdinsurances() == null || this.rankOneInsurance.getIdinsurances().equals(0))
            {
                throw new SQLException("DetailOrderBO::getRankOneInsuranceInformation:"
                    + " Could not retrieve insurance from detail insurance " + String.valueOf(this.rankOneDetailInsurance.getInsuranceId()));
            }
        }
        return this.rankOneInsurance;
    }
}
