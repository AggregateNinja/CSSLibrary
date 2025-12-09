package DOS;

import DOS.IDOS.IBillingDO;
import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DetailCptCode implements Serializable, IBillingDO
{

    private static final long serialVersionUID = 42L;

    private Integer iddetailCptCodes;
    private Integer detailOrderId;
    private Integer testId;
    private Integer cptCodeId;
    private String alias;
    private Integer quantity;
    private BigDecimal billAmount;
    private BigDecimal paid;
    private Date lastPaymentDate;
    private Integer paymentTypeId;
    private Integer placeOfServiceId;
    private BigDecimal cost;
    private BigDecimal transferredAmount;
    private Integer transferredTo;

    @Diff(fieldName = "iddetailCptCodes", isUniqueId = true)
    public Integer getIddetailCptCodes()
    {
        return iddetailCptCodes;
    }

    public void setIddetailCptCodes(Integer iddetailCptCodes)
    {
        this.iddetailCptCodes = iddetailCptCodes;
    }

    @Diff(fieldName = "detailOrderId")
    public Integer getDetailOrderId()
    {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId)
    {
        this.detailOrderId = detailOrderId;
    }

    @Diff(fieldName = "testId")
    public Integer getTestId()
    {
        return testId;
    }

    public void setTestId(Integer testId)
    {
        this.testId = testId;
    }

    @Diff(fieldName = "cptCodeId")
    public Integer getCptCodeId()
    {
        return cptCodeId;
    }

    public void setCptCodeId(Integer cptCodeId)
    {
        this.cptCodeId = cptCodeId;
    }

    @Diff(fieldName = "alias")
    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    @Diff(fieldName = "quantity")
    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    @Diff(fieldName = "billAmount")
    public BigDecimal getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(BigDecimal billAmount)
    {
        this.billAmount = billAmount;
    }

    @Diff(fieldName = "paid")
    public BigDecimal getPaid()
    {
        return paid;
    }

    public void setPaid(BigDecimal paid)
    {
        this.paid = paid;
    }

    @Diff(fieldName = "lastPaymentDate")
    public Date getLastPaymentDate()
    {
        return lastPaymentDate;
    }
    
    @Diff(fieldName = "paymentTypeId")
    public Integer getPaymentTypeId()
    {
        return paymentTypeId;
    }
    
    public void setPaymentTypeId(Integer paymentTypeId)
    {
        this.paymentTypeId = paymentTypeId;
    }

    public void setLastPaymentDate(Date lastPaymentDate)
    {
        this.lastPaymentDate = lastPaymentDate;
    }

    @Diff(fieldName = "placeOfServiceId")
    public Integer getPlaceOfServiceId()
    {
        return placeOfServiceId;
    }

    public void setPlaceOfServiceId(Integer placeOfServiceId)
    {
        this.placeOfServiceId = placeOfServiceId;
    }
    
    @Diff(fieldName = "cost")
    public BigDecimal getCost()
    {
        return cost;
    }
    
    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }
    
    @Diff(fieldName = "transferredAmount")
    public BigDecimal getTransferredAmount()
    {
        return transferredAmount;
    }
    
    public void setTransferredAmount(BigDecimal transferredAmount)
    {
        this.transferredAmount = transferredAmount;
    }
    
    @Diff(fieldName = "transferredTo")
    public Integer getTransferredTo()
    {
        return transferredTo;
    }
    
    public void setTransferredTo(Integer transferredTo)
    {
        this.transferredTo = transferredTo;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
    
    public DetailCptCode copy()
    {
        DetailCptCode copy = new DetailCptCode();
        copy.setIddetailCptCodes(iddetailCptCodes);
        copy.setDetailOrderId(detailOrderId);
        copy.setTestId(testId);
        copy.setCptCodeId(cptCodeId);
        copy.setAlias(alias);
        copy.setQuantity(quantity);
        copy.setBillAmount(billAmount);
        copy.setPaid(paid);
        copy.setLastPaymentDate(lastPaymentDate);
        copy.setPlaceOfServiceId(placeOfServiceId);
        copy.setCost(cost);
        copy.setTransferredAmount(transferredAmount);
        copy.setTransferredTo(transferredTo);
        return copy;
    }

    @Override
    public String getTableName()
    {
        return "detailCptCodes";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DetailCptCode)
        {
            DetailCptCode detailCptOther = (DetailCptCode) obj;
            return detailCptOther.getIddetailCptCodes().intValue() == iddetailCptCodes.intValue();
        }
        return false;
    }
   
}
