/*
 * Computer Service and Support Inc
 */

package API.Billing;

import API.BaseRequest;
import DAOS.AdjustmentApplicationMethodsDAO;
import DAOS.PaymentMethodDAO;
import DOS.AdjustmentType;
import DOS.PaymentMethod;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Rob
 */
public class RequestAdjustments extends BaseRequest<RequestAdjustments>
{
    private static final long serialVersionUID = 42L;
    
    private final Date paymentDate;
    private final PaymentMethod paymentMethod;
    private final String batchNumber;
    private final String checkNumber;
    AdjustmentType adjustmentType;
    BigDecimal totalAmount;
    AdjustmentApplicationMethodsDAO.ApplicationMethods method;
    boolean isTestBatch;
    private final int userId;
    
    public RequestAdjustments(
            AdjustmentType adjustmentType,
            BigDecimal totalAmount,
            AdjustmentApplicationMethodsDAO.ApplicationMethods method,
            boolean isTestBatch,
            int userId,
            Date paymentDate,
            PaymentMethod paymentMethod,
            String batchNumber, String checkNumber)
    {
        super();
        this.adjustmentType = adjustmentType;
        this.totalAmount = totalAmount;
        this.method = method;
        this.isTestBatch = isTestBatch;
        this.userId = userId;
        this.paymentDate = paymentDate == null ? new Date() : paymentDate;
        PaymentMethod cash = null;
        if (paymentMethod == null || paymentMethod.getIdPaymentMethods() == null || paymentMethod.getIdPaymentMethods().intValue() == 0)
        {
            try
            {
                cash = PaymentMethodDAO.get("cash");
            }
            catch (SQLException ex)
            {
                cash = new PaymentMethod();
                cash.setIdPaymentMethods(4);
                cash.setActive(1);
                cash.setName("Cash");
                cash.setSystemName("cash");
            }
        }
        this.paymentMethod = paymentMethod == null ? cash : paymentMethod;
        this.batchNumber = (batchNumber == null || batchNumber.isEmpty()) ? UUID.randomUUID().toString().substring(0, 8) : batchNumber;
        this.checkNumber = (checkNumber == null || checkNumber.isEmpty()) ? null : checkNumber;
    }
    
    public int getUserId()
    {
        return userId;
    }

    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public AdjustmentApplicationMethodsDAO.ApplicationMethods getMethod() {
        return method;
    }

    public void setMethod(AdjustmentApplicationMethodsDAO.ApplicationMethods method) {
        this.method = method;
    }

    public boolean isIsTestBatch() {
        return isTestBatch;
    }

    public void setIsTestBatch(boolean isTestBatch) {
        this.isTestBatch = isTestBatch;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getCheckNumber() {
        return checkNumber;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RequestAdjustments fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
