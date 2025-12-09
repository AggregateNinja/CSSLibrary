package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TomR
 */
public class AdvancedOrderLog
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAdvancedOrderLog")
    private Integer idAdvancedOrderLog;
    @Column(name = "advancedOrderId")
    private Integer advancedOrderId;
    @Column(name = "action")
    private String action;
    @Column(name = "orderId")
    private Integer orderId;
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    private Date scheduledFor;
    @Column(name = "userId")
    private Integer userId;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;
    @Basic(optional = false)
    @Column(name = "orderCreated")
    private Boolean orderCreated;
    @Column(name = "errorFlag")
    private Boolean errorFlag;
    
    public AdvancedOrderLog() {}
    
    public AdvancedOrderLog(
        Integer idAdvancedOrderLog,
        Integer advancedOrderId,
        String action,
        Integer orderId,
        Date scheduledFor,
        Integer userId,
        Date date,
        Boolean errorFlag)
    {
        this.idAdvancedOrderLog = idAdvancedOrderLog;
        this.advancedOrderId = advancedOrderId;
        this.action = action;
        this.orderId = orderId;
        this.scheduledFor = scheduledFor;
        this.userId = userId;
        this.date = date;
        this.errorFlag = errorFlag;        
    }

    public Integer getIdAdvancedOrderLog() {
        return idAdvancedOrderLog;
    }

    public void setIdAdvancedOrderLog(Integer idAdvancedOrderLog) {
        this.idAdvancedOrderLog = idAdvancedOrderLog;
    }

    public Integer getAdvancedOrderId() {
        return advancedOrderId;
    }

    public void setAdvancedOrderId(Integer advancedOrderId) {
        this.advancedOrderId = advancedOrderId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public Date getScheduledFor()
    {
        return this.scheduledFor;
    }

    public void setScheduledFor(Date date)
    {
        this.scheduledFor = date;
    }
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public Boolean isOrderCreated() {
        return orderCreated;
    }

    public void setOrderCreated(Boolean orderCreated) {
        this.orderCreated = orderCreated;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isErrorFlag() {
        return errorFlag;
    }

    public void setErrorFlag(Boolean errorFlag) {
        this.errorFlag = errorFlag;
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdvancedOrderLog))
        {
            return false;
        }
        AdvancedOrderLog other = (AdvancedOrderLog) object;
        if ((this.idAdvancedOrderLog == null && other.idAdvancedOrderLog != null)
                || (this.idAdvancedOrderLog != null && !this.idAdvancedOrderLog.equals(other.idAdvancedOrderLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.AdvancedOrderLog[ id=" + idAdvancedOrderLog + ", advancedOrderId=" + advancedOrderId + ", orderId =" + orderId +" ]";
    }    
    
}
