/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/14/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class OrderDiagnosisLookup implements Serializable{

    private Integer idDiagnosisLookup;
    private Integer idOrders;
    private Integer idDiagnosisCodes;

    public OrderDiagnosisLookup() {
    }

    public OrderDiagnosisLookup(Integer idDiagnosisLookup, Integer idOrders, Integer idDiagnosisCodes) {
        this.idDiagnosisLookup = idDiagnosisLookup;
        this.idOrders = idOrders;
        this.idDiagnosisCodes = idDiagnosisCodes;
    }

    public Integer getIdDiagnosisLookup() {
        return idDiagnosisLookup;
    }

    public void setIdDiagnosisLookup(Integer idDiagnosisLookup) {
        this.idDiagnosisLookup = idDiagnosisLookup;
    }

    public Integer getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    public Integer getIdDiagnosisCodes() {
        return idDiagnosisCodes;
    }

    public void setIdDiagnosisCodes(Integer idDiagnosisCodes) {
        this.idDiagnosisCodes = idDiagnosisCodes;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDiagnosisLookup != null ? idDiagnosisLookup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDiagnosisLookup)) {
            return false;
        }
        OrderDiagnosisLookup other = (OrderDiagnosisLookup) object;
        if ((this.idDiagnosisLookup == null && other.idDiagnosisLookup != null) || (this.idDiagnosisLookup != null && !this.idDiagnosisLookup.equals(other.idDiagnosisLookup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderDiagnosisLookup[ idDiagnosisLookup=" + idDiagnosisLookup + " ]";
    }
}
