package DOS;

import DOS.IDOS.IBillingDO;
import Utility.Diff;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DetailOrder implements Serializable, IBillingDO
{
    private static final long serialversionUID = 42L;

    private Integer iddetailOrders;
    private Integer orderId;
    private Date dateOfService;
    private BigDecimal balance;
    private Integer feeScheduleId;
    private Date lastSubmittedOn;
    private Date lastPaymentDate;
    private Boolean active;
    private Date created;
    private String poNum;
    

    @Diff(fieldName = "iddetailOrders", isUniqueId = true)
    public Integer getIddetailOrders()
    {
        return iddetailOrders;
    }

    public void setIddetailOrders(Integer iddetailOrders)
    {
        this.iddetailOrders = iddetailOrders;
    }

    @Diff(fieldName = "orderId")
    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }
    
    @Diff(fieldName = "dateOfService", isUserVisible=true)
    public Date getDateOfService()
    {
        return dateOfService;
    }

    public void setDateOfService(Date dateOfService)
    {
        this.dateOfService = dateOfService;
    }
    
    @Diff(fieldName = "balance", isUserVisible=true)
    public BigDecimal getBalance()
    {
        return balance;
    }
    
    public void setBalance(BigDecimal balance)
    {
        this.balance = balance;
    }

    @Diff(fieldName = "feeScheduleId", isUserVisible=true)
    public Integer getFeeScheduleId()
    {
        return feeScheduleId;
    }

    public void setFeeScheduleId(Integer feeScheduleId)
    {
        this.feeScheduleId = feeScheduleId;
    }

    @Diff(fieldName = "lastSubmittedDate", isUserVisible=true)
    public Date getLastSubmittedOn()
    {
        return lastSubmittedOn;
    }

    public void setLastSubmittedOn(Date lastSubmittedOn)
    {
        this.lastSubmittedOn = lastSubmittedOn;
    }

    @Diff(fieldName = "lastPaymentDate", isUserVisible=true)
    public Date getLastPaymentDate()
    {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate)
    {
        this.lastPaymentDate = lastPaymentDate;
    }

    @Diff(fieldName = "active")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
    
    @Diff(fieldName = "created")
    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
    
    @Diff(fieldName = "poNum")
    public String getPoNum()
    {
        return poNum;
    }
    
    public void setPoNum(String poNum)
    {
        this.poNum = poNum;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public String getTableName()
    {
        return "detailOrders";
    }

}
