/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "adjustments")
@NamedQueries({
    @NamedQuery(name = "Adjustments.findAll", query = "SELECT a FROM Adjustments a")})
public class AdjustmentsCSSSchema implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idadjustments")
    private Integer idadjustments;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entered;
    @Basic(optional = false)
    @Lob
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Column(name = "detail")
    private int detaillines;

    public AdjustmentsCSSSchema() {
    }

    public AdjustmentsCSSSchema(Integer idadjustments) {
        this.idadjustments = idadjustments;
    }

    public AdjustmentsCSSSchema(Integer idadjustments, BigDecimal amount, String type, Date entered, String reason, String user) {
        this.idadjustments = idadjustments;
        this.amount = amount;
        this.type = type;
        this.entered = entered;
        this.reason = reason;
        this.user = user;
    }

    public Integer getIdadjustments() {
        return idadjustments;
    }

    public void setIdadjustments(Integer idadjustments) {
        this.idadjustments = idadjustments;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getEntered() {
        return entered;
    }

    public void setEntered(Date entered) {
        this.entered = entered;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getDetaillines() {
        return detaillines;
    }

    public void setDetaillines(int detaillines) {
        this.detaillines = detaillines;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idadjustments != null ? idadjustments.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AdjustmentsCSSSchema)) {
            return false;
        }
        AdjustmentsCSSSchema other = (AdjustmentsCSSSchema) object;
        if ((this.idadjustments == null && other.idadjustments != null) || (this.idadjustments != null && !this.idadjustments.equals(other.idadjustments))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Adjustments[ idadjustments=" + idadjustments + " ]";
    }

}
