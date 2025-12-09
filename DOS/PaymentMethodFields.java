package DOS;

import java.util.Date;
import java.io.Serializable;

public class PaymentMethodFields implements Serializable {

    private static final long serialVersionUID = 42L;

    private Integer idPaymentMethodFields;
    private Integer paymentMethodId;
    private String name;
    private Integer isRequired;

    public Integer getIdPaymentMethodFields() {
        return idPaymentMethodFields;
    }

    public void setIdPaymentMethodFields(Integer idPaymentMethodFields) {
        this.idPaymentMethodFields = idPaymentMethodFields;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }
}
