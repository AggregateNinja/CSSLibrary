package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

public class Adjustments implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idAdjustments;
    private Integer adjustmentTypeId;
    private BigDecimal amount;
    private Integer adjustmentApplicationMethodId;
    private Date date;
    private Integer userId;
    private String transactionId;
    private String batchId;
    private Integer paymentMethodId;
    private Date paymentDate;

    public Integer getIdAdjustments() {
        return idAdjustments;
    }

    public void setIdAdjustments(Integer idAdjustments) {
        this.idAdjustments = idAdjustments;
    }

    public Integer getAdjustmentTypeId() {
        return adjustmentTypeId;
    }

    public void setAdjustmentTypeId(Integer adjustmentTypeId) {
        this.adjustmentTypeId = adjustmentTypeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAdjustmentApplicationMethodId() {
        return adjustmentApplicationMethodId;
    }

    public void setAdjustmentApplicationMethodId(Integer adjustmentApplicationMethodId) {
        this.adjustmentApplicationMethodId = adjustmentApplicationMethodId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getTransactionId()
    {
        return transactionId;
    }
    
    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }
    
    public String getBatchId()
    {
        return batchId;
    }
    
    public void setBatchId(String batchId)
    {
        this.batchId = batchId;
    }
       
    public Integer getPaymentMethodId()
    {
        return paymentMethodId;
    }
    
    public void setPaymentMethodId(Integer paymentMethodId)
    {
        this.paymentMethodId = paymentMethodId;
    }
    
    public Date getPaymentDate()
    {
        return paymentDate;
    }
    
    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }
    
    @Override
    public String toString()
    {
        return getTransactionId();
    }
}
