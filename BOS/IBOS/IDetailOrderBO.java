
package BOS.IBOS;

import DOS.Clients;
import DOS.CptModifier;
import DOS.DetailCptCode;
import DOS.DetailInsurance;
import DOS.DetailOrder;
import DOS.DetailOrderEvent;
import DOS.DiagnosisCodes;
import DOS.Doctors;
import DOS.Insurances;
import DOS.Orders;
import DOS.Patients;
import DOS.Subscriber;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * Commonly used methods for accessing billing data related to a single order
 * 
 * The "invalidateData" method should force the implementing class to discard
 *  any cached data and re-query the database for the next call(s)
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public interface IDetailOrderBO
{
    public void invalidateData();
    public void loadAllData() throws Exception;
    
    public DetailOrder getDetailOrder() throws Exception;
    public Collection<DetailInsurance> getDetailInsurances() throws Exception;
    public Collection<DetailCptCode> getDetailCptCodes() throws Exception;
    public Collection<CptModifier> getModifiersForDetailCptCodeId(Integer detailCptCodeId) throws Exception;
    public Collection<DiagnosisCodes> getDiagnosisCodesForDetailCptCodeId(Integer detailCptCodeId) throws Exception;
    public DiagnosisCodes getAssignedDiagnosisCodeForDetailCptCodeId(Integer detailCptCodeId) throws Exception;
    public Collection<DetailCptCode> getDetailCptCodesWithBalance() throws Exception;
    public Orders getOrder() throws Exception;
    public Patients getPatient() throws Exception;
    public Subscriber getSubscriber() throws Exception;
    public Clients getClient() throws Exception;
    public Doctors getDoctor() throws Exception;
    public Collection<DetailOrderEvent> getEvents() throws Exception;
    
    public BigDecimal getBillAmountTotal() throws Exception;
    public DetailInsurance getRankOneInsurance() throws Exception;
    public Insurances getRankOneInsuranceInformation() throws Exception;
    
    // TODO: retrieve history for line, etc.
}
