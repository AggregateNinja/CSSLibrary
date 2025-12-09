/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
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
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 11/04/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "test_log")
@NamedQueries({
    @NamedQuery(name = "TestLog.findAll", query = "SELECT t FROM TestLog t")})
public class TestLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtest_log")
    private Integer idtestLog;
    @Column(name = "EventType")
    private String eventType;
    @Column(name = "ChangeType")
    private String changeType;
    @Column(name = "TestNumber")
    private Integer testNumber;
    @Column(name = "TestName")
    private String testName;
    @Column(name = "OldTestId")
    private Integer OldTestID;
    @Column(name = "NewTestID")
    private Integer NewTestID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PreLow")
    private Double preLow;
    @Column(name = "PreHigh")
    private Double preHigh;
    @Column(name = "PreAlertLow")
    private Double preAlertLow;
    @Column(name = "PreAlertHigh")
    private Double preAlertHigh;
    @Column(name = "PreCritLow")
    private Double preCritLow;
    @Column(name = "PreCritHigh")
    private Double preCritHigh;
    @Column(name = "PreRemark")
    private Integer preRemark;
    @Column(name = "Comment")
    private String comment;
    @Column(name = "User")
    private Integer User;
    @Basic(optional = false)
    @Column(name = "Created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public TestLog() {
    }

    public TestLog(Integer idtestLog) {
        this.idtestLog = idtestLog;
    }

    public TestLog(Integer idtestLog, Date created) {
        this.idtestLog = idtestLog;
        this.created = created;
    }

    public Integer getIdtestLog() {
        return idtestLog;
    }

    public void setIdtestLog(Integer idtestLog) {
        this.idtestLog = idtestLog;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Double getPreLow() {
        return preLow;
    }

    public void setPreLow(Double preLow) {
        this.preLow = preLow;
    }

    public Double getPreHigh() {
        return preHigh;
    }

    public void setPreHigh(Double preHigh) {
        this.preHigh = preHigh;
    }

    public Double getPreAlertLow() {
        return preAlertLow;
    }

    public void setPreAlertLow(Double preAlertLow) {
        this.preAlertLow = preAlertLow;
    }

    public Double getPreAlertHigh() {
        return preAlertHigh;
    }

    public void setPreAlertHigh(Double preAlertHigh) {
        this.preAlertHigh = preAlertHigh;
    }

    public Double getPreCritLow() {
        return preCritLow;
    }

    public void setPreCritLow(Double preCritLow) {
        this.preCritLow = preCritLow;
    }

    public Double getPreCritHigh() {
        return preCritHigh;
    }

    public void setPreCritHigh(Double preCritHigh) {
        this.preCritHigh = preCritHigh;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getOldTestID() {
        return OldTestID;
    }

    public void setOldTestID(Integer OldTestID) {
        this.OldTestID = OldTestID;
    }

    public Integer getNewTestID() {
        return NewTestID;
    }

    public void setNewTestID(Integer NewTestID) {
        this.NewTestID = NewTestID;
    }

    public Integer getPreRemark() {
        return preRemark;
    }

    public void setPreRemark(Integer preRemark) {
        this.preRemark = preRemark;
    }

    public Integer getUser() {
        return User;
    }

    public void setUser(Integer User) {
        this.User = User;
    }
     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtestLog != null ? idtestLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestLog)) {
            return false;
        }
        TestLog other = (TestLog) object;
        if ((this.idtestLog == null && other.idtestLog != null) || (this.idtestLog != null && !this.idtestLog.equals(other.idtestLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.TestLog[ idtestLog=" + idtestLog + " ]";
    }

}
