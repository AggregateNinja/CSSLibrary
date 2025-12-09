/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
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
 *
 * @author Ryan
 */
@Entity
@Table(name = "qcSteps")
@NamedQueries({
    @NamedQuery(name = "QcSteps.findAll", query = "SELECT q FROM QcSteps q")})
public class QcSteps implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcSteps")
    private Integer idqcSteps;
    @Basic(optional = false)
    @Column(name = "idqcRules")
    private Integer idqcRules;
    @Column(name = "direction")
    private String direction;
    @Column(name = "operator")
    private String operator;
    @Column(name = "value")
    private Double value;
    @Column(name = "sign")
    private String sign;
    @Column(name = "coefficient")
    private Double coefficient;
    @Basic(optional = false)
    @Column(name = "runNumber")
    private int runNumber;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "createdBy")
    private Integer createdBy;
    
    public QcSteps() {
    }

    public QcSteps(Integer idqcSteps) {
        this.idqcSteps = idqcSteps;
    }

    public QcSteps(Integer idqcSteps, int runNumber, Date created) {
        this.idqcSteps = idqcSteps;
        this.runNumber = runNumber;
        this.created = created;
    }

    public Integer getIdqcSteps() {
        return idqcSteps;
    }

    public void setIdqcSteps(Integer idqcSteps) {
        this.idqcSteps = idqcSteps;
    }

    public Integer getIdqcRules()
    {
        return idqcRules;
    }

    public void setIdqcRules(Integer idqcRules)
    {
        this.idqcRules = idqcRules;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public int getRunNumber() {
        return runNumber;
    }

    public void setRunNumber(int runNumber) {
        this.runNumber = runNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcSteps != null ? idqcSteps.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcSteps)) {
            return false;
        }
        QcSteps other = (QcSteps) object;
        if ((this.idqcSteps == null && other.idqcSteps != null) || (this.idqcSteps != null && !this.idqcSteps.equals(other.idqcSteps))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcSteps[ idqcSteps=" + idqcSteps + " ]";
    }
    
}
