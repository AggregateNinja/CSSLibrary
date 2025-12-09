/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
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
 * @since Build {insert version here} 10/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "calulations")
@NamedQueries({
    @NamedQuery(name = "Calulations.findAll", query = "SELECT c FROM Calulations c")})
public class Calculations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcalulations")
    private Integer idcalulations;
    @Column(name = "idtests")
    private Integer idtests;
    @Column(name = "step")
    private int step;
    @Column(name = "resultValueId")
    private Integer resultValueId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "numericValue")
    private Double numericValue;
    @Column(name = "operator")
    private String operator;

    public Calculations() {
    }

    public Calculations(Integer idcalulations) {
        this.idcalulations = idcalulations;
    }

    public Calculations(Integer idcalulations, int step) {
        this.idcalulations = idcalulations;
        this.step = step;
    }

    public Integer getIdcalulations() {
        return idcalulations;
    }

    public void setIdcalulations(Integer idcalulations) {
        this.idcalulations = idcalulations;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public Double getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(Double numericValue) {
        this.numericValue = numericValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getIdtests() {
        return idtests;
    }

    public void setIdtests(Integer idtests) {
        this.idtests = idtests;
    }

    public Integer getResultValueId() {
        return resultValueId;
    }

    public void setResultValueId(Integer resultValueId) {
        this.resultValueId = resultValueId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcalulations != null ? idcalulations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Calculations)) {
            return false;
        }
        Calculations other = (Calculations) object;
        if ((this.idcalulations == null && other.idcalulations != null) || (this.idcalulations != null && !this.idcalulations.equals(other.idcalulations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Calulations[ idcalulations=" + idcalulations + " ]";
    }

}
