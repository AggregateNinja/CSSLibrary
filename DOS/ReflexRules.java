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
 * @since Build {insert version here} 10/15/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "reflexRules")
@NamedQueries({
    @NamedQuery(name = "ReflexRules.findAll", query = "SELECT r FROM ReflexRules r")})
public class ReflexRules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreflexRules")
    private Integer idreflexRules;
    @Column(name = "idtests")
    private Integer idTests;
    @Column(name = "isHigh")
    private Integer isHigh;
    @Column(name = "isLow")
    private Integer isLow;
    @Column(name = "isCIDHigh")
    private Integer isCIDHigh;
    @Column(name = "isCIDLow")
    private Integer isCIDLow;
    @Column(name = "relexRemark")
    private Integer reflexRemark;
    @Column(name = "remarkTest")
    private Integer remarkTest;
    @Column(name = "active")
    private boolean active;

    public ReflexRules() {
    }

    public ReflexRules(Integer idreflexRules) {
        this.idreflexRules = idreflexRules;
    }

    public Integer getIdreflexRules() {
        return idreflexRules;
    }

    public void setIdreflexRules(Integer idreflexRules) {
        this.idreflexRules = idreflexRules;
    }

    public Integer getIdTests() {
        return idTests;
    }

    public void setIdTests(Integer idTests) {
        this.idTests = idTests;
    }

    public Integer getIsHigh() {
        return isHigh;
    }

    public void setIsHigh(Integer isHigh) {
        this.isHigh = isHigh;
    }

    public Integer getIsLow() {
        return isLow;
    }

    public void setIsLow(Integer isLow) {
        this.isLow = isLow;
    }

    public Integer getIsCIDHigh() {
        return isCIDHigh;
    }

    public void setIsCIDHigh(Integer isCIDHigh) {
        this.isCIDHigh = isCIDHigh;
    }

    public Integer getIsCIDLow() {
        return isCIDLow;
    }

    public void setIsCIDLow(Integer isCIDLow) {
        this.isCIDLow = isCIDLow;
    }

    public Integer getReflexRemark() {
        return reflexRemark;
    }

    public void setReflexRemark(Integer reflexRemark) {
        this.reflexRemark = reflexRemark;
    }

    public Integer getRemarkTest() {
        return remarkTest;
    }

    public void setRemarkTest(Integer remarkTest) {
        this.remarkTest = remarkTest;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreflexRules != null ? idreflexRules.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReflexRules)) {
            return false;
        }
        ReflexRules other = (ReflexRules) object;
        if ((this.idreflexRules == null && other.idreflexRules != null) || (this.idreflexRules != null && !this.idreflexRules.equals(other.idreflexRules))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.ReflexRules[ idreflexRules=" + idreflexRules + " ]";
    }

}
