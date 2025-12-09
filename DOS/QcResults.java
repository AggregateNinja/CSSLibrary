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
 * @file name: QcResults.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcResults")
@NamedQueries({
    @NamedQuery(name = "QcResults.findAll", query = "SELECT q FROM QcResults q")})
public class QcResults implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcResults")
    private Integer idqcResults;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Column(name = "idqcLot")
    private int idqcLot;
    @Basic(optional = false)
    @Column(name = "result")
    private Double result;
    @Basic(optional = false)
    @Column(name = "action")
    private String action;
    @Basic(optional = false)
    @Column(name = "forced")
    private boolean forced;
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "shift")
    private int shift;
    @Basic(optional = false)
    @Column(name = "dateRun")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRun;
    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "test")
    private int test;
    @Basic(optional = false)
    @Column(name = "idInst")
    private int idInst;
    @Basic(optional = false)
    @Column(name = "approvedBy")
    private int approvedBy;
    @Basic(optional = false)
    @Column(name = "approvedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;
    
    public QcResults() {
    }

    public QcResults(Integer idqcResults) {
        this.idqcResults = idqcResults;
    }

    public QcResults(Integer idqcResults, int level, int idqcLot, Double result, String action, boolean forced, int shift, Date dateRun, Date createdDate) {
        this.idqcResults = idqcResults;
        this.level = level;
        this.idqcLot = idqcLot;
        this.result = result;
        this.action = action;
        this.forced = forced;
        this.shift = shift;
        this.dateRun = dateRun;
        this.createdDate = createdDate;
    }

    public Integer getIdqcResults() {
        return idqcResults;
    }

    public void setIdqcResults(Integer idqcResults) {
        this.idqcResults = idqcResults;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIdqcLot()
    {
        return idqcLot;
    }

    public void setIdqcLot(int idqcLot)
    {
        this.idqcLot = idqcLot;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean getForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public Date getDateRun() {
        return dateRun;
    }

    public void setDateRun(Date dateRun) {
        this.dateRun = dateRun;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getTest()
    {
        return test;
    }

    public void setTest(int test)
    {
        this.test = test;
    }

    public int getIdInst()
    {
        return idInst;
    }

    public void setIdInst(int idInst)
    {
        this.idInst = idInst;
    }

    public int getApprovedBy()
    {
        return approvedBy;
    }

    public void setApprovedBy(int approvedBy)
    {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate()
    {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate)
    {
        this.approvedDate = approvedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcResults != null ? idqcResults.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcResults)) {
            return false;
        }
        QcResults other = (QcResults) object;
        if ((this.idqcResults == null && other.idqcResults != null) || (this.idqcResults != null && !this.idqcResults.equals(other.idqcResults))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcResults[ idqcResults=" + idqcResults + " ]";
    }

}
