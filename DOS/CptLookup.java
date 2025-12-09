package DOS;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class CptLookup implements Comparable
{
    private static final long serialversionUID = 42L;
    
    @Basic(optional = false)
    @Column(name = "idCptLookup")
    private int idCptLookup;
    @Basic(optional = false)
    @Column(name = "insuranceId")
    private int insuranceId;
    @Basic(optional = false)
    @Column(name = "testNumber")
    private int testNumber;
    @Basic(optional = false)
    @Column(name = "cptCodeId")
    private int cptCodeId;
    @Basic(optional = false)
    @Column(name = "cost")
    private BigDecimal cost;
    @Basic(optional = false)
    @Column(name = "quantity")
    private Integer quantity;
    @Basic(optional = false)
    @Column(name = "breakDown")
    private boolean breakDown;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startDate")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endDate")
    private Date endDate;
    @Basic(optional = false)
    @Column(name = "createdById")
    private int createdById;
    @Basic(optional = false)
    @Column(name = "createdDate")
    Date createdDate;
    @Column(name = "deactivatedById")
    private Integer deactivatedById;
    @Column(name = "deactivatedDate")
    private Date deactivatedDate;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    /**
     * Copy constructor
     * @param lookup
     */
    public CptLookup(CptLookup lookup)
    {
        this.idCptLookup = lookup.getIdCptLookup();
        this.insuranceId = lookup.getInsuranceId();
        this.testNumber = lookup.getTestNumber();
        this.cptCodeId = lookup.getCptCodeId();
        this.breakDown = lookup.isBreakDown();
        this.cost = lookup.getCost();
        this.quantity = lookup.getQuantity();
        this.startDate = lookup.getStartDate();
        this.endDate = lookup.getEndDate();
        this.createdDate = lookup.getCreatedDate();
        this.createdById = lookup.getCreatedById();
        this.deactivatedDate = lookup.getDeactivatedDate();
        this.deactivatedById = lookup.getDeactivatedById();
        this.active = lookup.isActive();
    }
    
    public CptLookup()
    {
        
    }

    public int getIdCptLookup()
    {
        return idCptLookup;
    }

    public void setIdCptLookup(int idCptLookup)
    {
        this.idCptLookup = idCptLookup;
    }

    public int getInsuranceId()
    {
        return insuranceId;
    }

    public void setInsuranceId(int insuranceId)
    {
        this.insuranceId = insuranceId;
    }

    public int getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(int testNumber)
    {
        this.testNumber = testNumber;
    }

    public int getCptCodeId()
    {
        return cptCodeId;
    }

    public void setCptCodeId(int cptCodeId)
    {
        this.cptCodeId = cptCodeId;
    }

    public BigDecimal getCost()
    {
        return cost;
    }

    public void setCost(BigDecimal cost)
    {
        this.cost = cost;
    }

    public Integer getQuantity()
    {
        return quantity;
    }

    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public boolean isBreakDown()
    {
        return breakDown;
    }

    public void setBreakDown(boolean breakDown)
    {
        this.breakDown = breakDown;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public int getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(int createdById)
    {
        this.createdById = createdById;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Integer getDeactivatedById()
    {
        return deactivatedById;
    }

    public void setDeactivatedById(Integer deactivatedById)
    {
        this.deactivatedById = deactivatedById;
    }

    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + this.idCptLookup;
        hash = 37 * hash + this.insuranceId;
        hash = 37 * hash + this.testNumber;
        hash = 37 * hash + this.cptCodeId;
        hash = 37 * hash + Objects.hashCode(this.cost);
        hash = 37 * hash + (this.breakDown ? 1 : 0);
        hash = 37 * hash + this.quantity;
        hash = 37 * hash + Objects.hashCode(this.startDate);
        hash = 37 * hash + Objects.hashCode(this.endDate);
        hash = 37 * hash + this.createdById;
        hash = 37 * hash + Objects.hashCode(this.createdDate);
        hash = 37 * hash + Objects.hashCode(this.deactivatedById);
        hash = 37 * hash + Objects.hashCode(this.deactivatedDate);
        hash = 37 * hash + (this.active ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;        
        if (getClass() != obj.getClass()) return false;
        final CptLookup other = (CptLookup) obj;
        
        if (other.getIdCptLookup() == this.getIdCptLookup()) return true;
        
        if (this.insuranceId != other.insuranceId) return false;        
        if (this.testNumber != other.testNumber) return false;
        if (this.cptCodeId != other.cptCodeId) return false;
        if (this.breakDown != other.breakDown) return false;
        if (this.quantity != other.quantity) return false;
        if (!Objects.equals(this.startDate, other.startDate)) return false;
        if (!Objects.equals(this.endDate, other.endDate)) return false;
        if (!Objects.equals(this.createdDate, other.createdDate)) return false;
        if (this.createdById != other.createdById) return false;
        if (this.active != other.active) return false;
        if (!Objects.equals(this.deactivatedDate, other.deactivatedDate)) return false;
        return (this.deactivatedById != other.deactivatedById);
    }

    @Override
    public int compareTo(Object otherObj)
    {
        if (otherObj == null) return -1;
        if (getClass() != otherObj.getClass()) return -1;
        final CptLookup otherCpt = (CptLookup)otherObj;
        if (otherCpt.equals(this)) return 0;
        
        if (this.getInsuranceId() < otherCpt.getInsuranceId()) return -1;
        if (this.getTestNumber() < otherCpt.getTestNumber()) return -1;
        if (this.getCptCodeId()< otherCpt.getCptCodeId()) return -1;
        
        return 1;
    }
}