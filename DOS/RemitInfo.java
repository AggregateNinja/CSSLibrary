package DOS;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

public class RemitInfo implements Serializable
{
    private static final long serialversionUID = 42L;

    private Integer idRemitInfo;
    private String payorid;
    private String filename;
    private Date filedate;
    private String controlNumber;
    private String groupNumber;
    private String batchNumber;
    private String paymentMethod;
    private Date paymentDate;
    private String paymentNumber;
    private BigDecimal paymentAmount;
    private Date created;
    private Date processed;
    private Integer userid;
    private Boolean active;

    public Integer getIdRemitInfo()
    {
        return idRemitInfo;
    }

    public void setIdRemitInfo(Integer idRemitInfo)
    {
        this.idRemitInfo = idRemitInfo;
    }

    public String getPayorid()
    {
        return payorid;
    }

    public void setPayorid(String payorid)
    {
        this.payorid = payorid;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public Date getFiledate()
    {
        return filedate;
    }

    public void setFiledate(Date filedate)
    {
        this.filedate = filedate;
    }

    public String getControlNumber()
    {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber)
    {
        this.controlNumber = controlNumber;
    }

    public String getGroupNumber()
    {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber)
    {
        this.groupNumber = groupNumber;
    }

    public String getBatchNumber()
    {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber)
    {
        this.batchNumber = batchNumber;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public Date getPaymentDate()
    {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate)
    {
        this.paymentDate = paymentDate;
    }

    public String getPaymentNumber()
    {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber)
    {
        this.paymentNumber = paymentNumber;
    }

    public BigDecimal getPaymentAmount()
    {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Date getProcessed()
    {
        return processed;
    }

    public void setProcessed(Date processed)
    {
        this.processed = processed;
    }

    public Integer getUserid()
    {
        return userid;
    }

    public void setUserid(Integer userid)
    {
        this.userid = userid;
    }
    
    public Boolean isActive()
    {
        return active;
    }
    
    public void setActive(Boolean active)
    {
        this.active = active;
    }
    
    @Override
    public String toString()
    {
        return "(" + getPaymentNumber() + ") " + getControlNumber();
    }
}
