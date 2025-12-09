
package DOS;

import java.io.Serializable;

public class PaymentMethodDetails implements Serializable
{
    private static final long serialVersionUID = 42L;
    private Integer idPaymentMethodDetails;
    private Integer paymentDetailId;
    private Integer paymentMethodFieldId;
    private String value;
    
    public Integer getIdPaymentMethodDetail()
    {
        return this.idPaymentMethodDetails;
    }
    
    public void setIdPaymentMethodDetail(Integer idPaymentMethodDetails)
    {
        this.idPaymentMethodDetails = idPaymentMethodDetails;
    }
    
    public Integer getPaymentDetailId()
    {
        return this.paymentDetailId;
    }
    
    public void setPaymentDetailId(Integer paymentDetailId)
    {
        this.paymentDetailId = paymentDetailId;
    }
    
    public Integer getPaymentMethodFieldId()
    {
        return this.paymentMethodFieldId;
    }
    
    public void setPaymentMethodFieldId(Integer paymentMethodFieldId)
    {
        this.paymentMethodFieldId = paymentMethodFieldId;
    }
    
    public String getValue()
    {
        return this.value;
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    @Override
    public String toString()
    {
        return getIdPaymentMethodDetail().toString();
    }
}
