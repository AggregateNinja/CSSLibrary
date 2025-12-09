package DOS;

import java.util.Date;
import java.io.Serializable;

public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer idPaymentDetails;
    private Integer adjustmentId;
    private Integer billingPayorId;
    private Date paymentReceivedDate;
    private Integer paymentMethodId;

    public Integer getIdPaymentDetails() {
        return idPaymentDetails;
    }

    public void setIdPaymentDetails(Integer idPaymentDetails) {
        this.idPaymentDetails = idPaymentDetails;
    }

    public Integer getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Integer adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Integer getBillingPayorId() {
        return billingPayorId;
    }

    public void setBillingPayorId(Integer billingPayorId) {
        this.billingPayorId = billingPayorId;
    }

    public Date getPaymentReceivedDate() {
        return paymentReceivedDate;
    }

    public void setPaymentReceivedDate(Date paymentReceivedDate) {
        this.paymentReceivedDate = paymentReceivedDate;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}
