package DOS;

import java.io.Serializable;
import java.util.Date;

public class Ledger implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idLedger;
    private Integer adjustmentId;
    private Integer detailOrderId;
    private Integer detailCptCodeId;
    private java.math.BigDecimal previousAmount;
    private java.math.BigDecimal adjustmentAmount;
    private Date created;

    public Integer getIdLedger()
    {
        return idLedger;
    }

    public void setIdLedger(Integer idLedger)
    {
        this.idLedger = idLedger;
    }

    public Integer getAdjustmentId()
    {
        return adjustmentId;
    }

    public void setAdjustmentId(Integer adjustmentId)
    {
        this.adjustmentId = adjustmentId;
    }

    public Integer getDetailOrderId()
    {
        return detailOrderId;
    }

    public void setDetailOrderId(Integer detailOrderId)
    {
        this.detailOrderId = detailOrderId;
    }

    public Integer getDetailCptCodeId()
    {
        return detailCptCodeId;
    }

    public void setDetailCptCodeId(Integer detailCptCodeId)
    {
        this.detailCptCodeId = detailCptCodeId;
    }

    public java.math.BigDecimal getPreviousAmount()
    {
        return previousAmount;
    }

    public void setPreviousAmount(java.math.BigDecimal previousAmount)
    {
        this.previousAmount = previousAmount;
    }

    public java.math.BigDecimal getAdjustmentAmount()
    {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(java.math.BigDecimal adjustmentAmount)
    {
        this.adjustmentAmount = adjustmentAmount;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }
}
