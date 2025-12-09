/*
 * Computer Service and Support Inc
 */

package API.Billing;

import DAOS.AdjustmentApplicationMethodsDAO;
import DOS.AdjustmentType;
import DOS.PaymentMethod;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author Rob
 */
public class RequestManualAdjustmentsForDetailCptLines extends RequestAdjustments
{
    private final List<Pair<Integer, Pair<BigDecimal, Boolean>>> detailCptPaymentList;

    public RequestManualAdjustmentsForDetailCptLines(AdjustmentType adjustmentType, BigDecimal totalAmount, 
            AdjustmentApplicationMethodsDAO.ApplicationMethods method, boolean isTestBatch, int userId, List<Pair<Integer, Pair<BigDecimal, Boolean>>> detailCptPaymentList) {
        super(adjustmentType, totalAmount, method, isTestBatch, userId, null, null, null, null);
        this.detailCptPaymentList = detailCptPaymentList;
    }
    
    public RequestManualAdjustmentsForDetailCptLines(AdjustmentType adjustmentType, BigDecimal totalAmount, 
            AdjustmentApplicationMethodsDAO.ApplicationMethods method, boolean isTestBatch, int userId, List<Pair<Integer, Pair<BigDecimal, Boolean>>> detailCptPaymentList,
            Date paymentDate, PaymentMethod paymentMethod, String batchNumber, String checkNumber) {
        super(adjustmentType, totalAmount, method, isTestBatch, userId, paymentDate, paymentMethod, batchNumber, checkNumber);
        this.detailCptPaymentList = detailCptPaymentList;
    }

    public List<Pair<Integer, Pair<BigDecimal, Boolean>>> getDetailCptPaymentList()
    {
        return detailCptPaymentList;
    }
}
