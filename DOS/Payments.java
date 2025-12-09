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
@Table(name = "payments")
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p")})
public class Payments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransactions")
    private Integer idtransactions;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "amount")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "entered")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entered;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Column(name = "detail")
    private int detaillines;

    public Payments() {
    }

    public Payments(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public Payments(Integer idtransactions, BigDecimal amount, Date entered, String user) {
        this.idtransactions = idtransactions;
        this.amount = amount;
        this.entered = entered;
        this.user = user;
    }

    public Integer getIdtransactions() {
        return idtransactions;
    }

    public void setIdtransactions(Integer idtransactions) {
        this.idtransactions = idtransactions;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getEntered() {
        return entered;
    }

    public void setEntered(Date entered) {
        this.entered = entered;
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
        hash += (idtransactions != null ? idtransactions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.idtransactions == null && other.idtransactions != null) || (this.idtransactions != null && !this.idtransactions.equals(other.idtransactions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Payments[ idtransactions=" + idtransactions + " ]";
    }

}
