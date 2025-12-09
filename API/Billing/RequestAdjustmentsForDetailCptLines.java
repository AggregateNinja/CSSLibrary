/*
 * Computer Service and Support Inc
 */

package API.Billing;

import API.Billing.Common.AdjustmentPayment;
import DAOS.AdjustmentApplicationMethodsDAO;
import DOS.AdjustmentType;
import DOS.PaymentMethod;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rob
 */
public class RequestAdjustmentsForDetailCptLines extends RequestAdjustments
{
    private final List<AdjustmentPayment> adjustmentPaymentList;

    public RequestAdjustmentsForDetailCptLines(AdjustmentType adjustmentType, BigDecimal totalAmount, 
            AdjustmentApplicationMethodsDAO.ApplicationMethods method, boolean isTestBatch, int userId, List<AdjustmentPayment> adjustmentPaymentList) {
        super(adjustmentType, totalAmount, method, isTestBatch, userId, null, null, null, null);
        this.adjustmentPaymentList = adjustmentPaymentList;
    }
    
    public RequestAdjustmentsForDetailCptLines(AdjustmentType adjustmentType, BigDecimal totalAmount, 
            AdjustmentApplicationMethodsDAO.ApplicationMethods method, boolean isTestBatch, int userId, List<AdjustmentPayment> adjustmentPaymentList,
            Date paymentDate, PaymentMethod paymentMethod, String batchNumber, String checkNumber) {
        super(adjustmentType, totalAmount, method, isTestBatch, userId, paymentDate, paymentMethod, batchNumber, checkNumber);
        this.adjustmentPaymentList = adjustmentPaymentList;
    }

    public List<AdjustmentPayment> getAdjustmentPaymentList() {
        return adjustmentPaymentList;
    }

}
