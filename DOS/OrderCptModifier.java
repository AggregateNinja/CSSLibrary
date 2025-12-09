package DOS;

import Utility.Diff;
import java.io.Serializable;

public class OrderCptModifier implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer orderCptId;
    private Integer cptModifierId;

    @Diff(fieldName = "orderCptId", isUniqueId = false)
    public Integer getOrderCptId()
    {
        return orderCptId;
    }

    public void setOrderCptId(Integer orderCptId)
    {
        this.orderCptId = orderCptId;
    }

    @Diff(fieldName = "cptModifierId")
    public Integer getCptModifierId()
    {
        return cptModifierId;
    }

    public void setCptModifierId(Integer cptModifierId)
    {
        this.cptModifierId = cptModifierId;
    }
}
