/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/14/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "prescriptions")
@NamedQueries({
    @NamedQuery(name = "Prescriptions.findAll", query = "SELECT p FROM Prescriptions p")})
public class Prescriptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprescriptions")
    private Integer idprescriptions;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "drugId")
    private int drugId;

    public Prescriptions() {
    }

    public Prescriptions(Integer idprescriptions) {
        this.idprescriptions = idprescriptions;
    }

    public Prescriptions(Integer idprescriptions, int orderId) {
        this.idprescriptions = idprescriptions;
        this.orderId = orderId;
    }

    public Integer getIdprescriptions() {
        return idprescriptions;
    }

    public void setIdprescriptions(Integer idprescriptions) {
        this.idprescriptions = idprescriptions;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprescriptions != null ? idprescriptions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prescriptions)) {
            return false;
        }
        Prescriptions other = (Prescriptions) object;
        if ((this.idprescriptions == null && other.idprescriptions != null) || (this.idprescriptions != null && !this.idprescriptions.equals(other.idprescriptions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Prescriptions[ idprescriptions=" + idprescriptions + " ]";
    }

}
