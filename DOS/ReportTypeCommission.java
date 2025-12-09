/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date July 17, 2018 
 * @author Ed Bossmeyer - ebossmeyer@csslis.com
 * @project: CSSLibrary
 * @description: Used in Salesmen configuration to assign commissions to salesmen based on report type
 */
public class ReportTypeCommission extends ReportType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    @Basic(optional = false)
    @Column(name = "idCommissions")
    private Integer idCommissions;
    
    @Basic(optional = false)
    @Column(name = "reportTypeId")
    private Integer reportTypeId;
    
    @Basic(optional = true)
    @Column(name = "salesmanId")
    private Integer salesmanId;
    
    @Basic(optional = false)
    @Column(name = "minPayment")
    private BigDecimal minPayment;
    
    @Basic(optional = false)
    @Column(name = "commissionRate")
    private BigDecimal commissionRate;
    
    @Basic(optional = false)
    @Column(name = "isActive")
    private Boolean isActive;
    
    @Column(name = "dateCreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    
    public ReportTypeCommission()
    {
    }

    public ReportTypeCommission(Integer idreportType)
    {
        super(idreportType);
        this.reportTypeId = idreportType;
    }
    
    public Integer getIdCommissions() {
        return this.idCommissions;
    }
    public Integer getReportTypeId() {
        return this.reportTypeId;
    }
    public Integer getSalesmanId() {
        return this.salesmanId;
    }
    public BigDecimal getMinPayment() {
        return this.minPayment;
    }
    public BigDecimal getCommissionRate() {
        return this.commissionRate;
    }
    public Boolean getIsActive() {
        return this.isActive;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    
    public void setIdCommissions(Integer idCommissions) {
        this.idCommissions = idCommissions;
    }
    public void setReportTypeId(Integer reportTypeId) {
        this.reportTypeId = reportTypeId;
    }
    public void setSalesmanId(Integer salesmanId) {
        this.salesmanId = salesmanId;
    }
    public void setMinPayment(BigDecimal minPayment) {
        this.minPayment = minPayment;
    }
    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
