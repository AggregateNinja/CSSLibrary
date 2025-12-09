/*
 * Computer Service & Support, Inc.  All Rights Reserved Apr 13, 2015
 */

package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Apr 13, 2015  1:03:50 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ResultDetails.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "resultDetails")
@NamedQueries({
    @NamedQuery(name = "ResultDetails.findAll", query = "SELECT r FROM ResultDetails r")})
public class ResultDetails implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idResults")
    private Integer idResults;
    @Basic(optional = false)
    @Column(name = "queuePrinted")
    private boolean queuePrinted;
    @Column(name = "dateQueuePrinted")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateQueuePrinted;
    @Column(name = "queuePrintedBy")
    private Integer queuePrintedBy;
    @Column(name = "batch")
    private Integer batch;
    @Column(name = "isAddOn")
    private boolean isAddOn;
    @Column(name = "quantity")
    private Integer quantity;

    public ResultDetails() {
    }

    public ResultDetails(Integer idResults) {
        this.idResults = idResults;
    }

    public ResultDetails(Integer idResults, boolean queuePrinted) {
        this.idResults = idResults;
        this.queuePrinted = queuePrinted;
    }

    public Integer getIdResults() {
        return idResults;
    }

    public void setIdResults(Integer idResults) {
        this.idResults = idResults;
    }

    public boolean getQueuePrinted() {
        return queuePrinted;
    }

    public void setQueuePrinted(boolean queuePrinted) {
        this.queuePrinted = queuePrinted;
    }

    public Date getDateQueuePrinted() {
        return dateQueuePrinted;
    }

    public void setDateQueuePrinted(Date dateQueuePrinted) {
        this.dateQueuePrinted = dateQueuePrinted;
    }

    public Integer getQueuePrintedBy() {
        return queuePrintedBy;
    }

    public void setQueuePrintedBy(Integer queuePrintedBy) {
        this.queuePrintedBy = queuePrintedBy;
    }

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    public boolean isAddOn() {
        return isAddOn;
    }

    public void setIsAddOn(boolean isAddOn) {
        this.isAddOn = isAddOn;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResults != null ? idResults.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultDetails)) {
            return false;
        }
        ResultDetails other = (ResultDetails) object;
        if ((this.idResults == null && other.idResults != null) || (this.idResults != null && !this.idResults.equals(other.idResults))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.ResultDetails[ idResults=" + idResults + " ]";
    }

}
