/*
 * Computer Service & Support, Inc.  All Rights Reserved May 28, 2014
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
 * @date:   May 28, 2014  5:38:12 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcControlValues.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcControlValues")
@NamedQueries({
    @NamedQuery(name = "QcControlValues.findAll", query = "SELECT q FROM QcControlValues q")})
public class QcControlValues implements Serializable {
    private static final double serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcControlValues")
    private Integer idqcControlValues;
    @Column(name = "lot")
    private Integer lot;
    @Basic(optional = false)
    @Column(name = "testDescription")
    private String testDescription;
    @Basic(optional = false)
    @Column(name = "std")
    private double std;
    @Basic(optional = false)
    @Column(name = "mean")
    private double mean;
    @Column(name = "mean-2std")
    private double mean_2std;
    @Column(name = "mean+2std")
    private double meanPlus2std;
    @Basic(optional = false)
    @Column(name = "units")
    private String units;
    @Basic(optional = false)
    @Column(name = "dateEntered")
    @Temporal(TemporalType.DATE)
    private Date dateEntered;
    @Column(name = "enteredBy")
    private Integer enteredBy;
    @Basic(optional = false)
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    @Column(name = "updatedBy")
    private Integer updatedBy;
    @Column(name = "controlNumber")
    private Integer controlNumber;

    public QcControlValues() {
    }

    public QcControlValues(Integer idqcControlValues) {
        this.idqcControlValues = idqcControlValues;
    }

    public QcControlValues(Integer idqcControlValues, String testDescription, double std, double mean, String units, Date dateEntered, Date dateUpdated) {
        this.idqcControlValues = idqcControlValues;
        this.testDescription = testDescription;
        this.std = std;
        this.mean = mean;
        this.units = units;
        this.dateEntered = dateEntered;
        this.dateUpdated = dateUpdated;
    }

    public Integer getIdqcControlValues() {
        return idqcControlValues;
    }

    public void setIdqcControlValues(Integer idqcControlValues) {
        this.idqcControlValues = idqcControlValues;
    }

    public Integer getLot() {
        return lot;
    }

    public void setLot(Integer lot) {
        this.lot = lot;
    }
    
    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public double getStd() {
        return std;
    }

    public void setStd(double std) {
        this.std = std;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMean_2std() {
        return mean_2std;
    }

    public void setMean_2std(double mean_2std) {
        this.mean_2std = mean_2std;
    }

    public double getMeanPlus2std() {
        return meanPlus2std;
    }

    public void setMeanPlus2std(double meanPlus2std) {
        this.meanPlus2std = meanPlus2std;
    }
    
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Date getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(Date dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Integer getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(Integer controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcControlValues != null ? idqcControlValues.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcControlValues)) {
            return false;
        }
        QcControlValues other = (QcControlValues) object;
        if ((this.idqcControlValues == null && other.idqcControlValues != null) || (this.idqcControlValues != null && !this.idqcControlValues.equals(other.idqcControlValues))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcControlValues[ idqcControlValues=" + idqcControlValues + " ]";
    }

}
