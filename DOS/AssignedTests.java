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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "assignedTests")
@NamedQueries({
    @NamedQuery(name = "AssignedTests.findAll", query = "SELECT a FROM AssignedTests a")})
public class AssignedTests implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idassignedTests")
    private Integer idassignedTests;
    @Column(name = "idTests")
    private Integer idTests;
    @Column(name = "idassignedWorksheets")
    private Integer idassignedWorksheets;

    public AssignedTests() {
    }

    public AssignedTests(Integer idassignedTests) {
        this.idassignedTests = idassignedTests;
    }

    public Integer getIdassignedTests() {
        return idassignedTests;
    }

    public void setIdassignedTests(Integer idassignedTests) {
        this.idassignedTests = idassignedTests;
    }
    
    public Integer getIdTests() {
        return idTests;
    }

    public void setIdTests(Integer iTests) {
        this.idTests = idTests;
    }

    public Integer getIdassignedWorksheets() {
        return idassignedWorksheets;
    }

    public void setIdassignedWorksheets(Integer idassignedWorksheets) {
        this.idassignedWorksheets = idassignedWorksheets;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idassignedTests != null ? idassignedTests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignedTests)) {
            return false;
        }
        AssignedTests other = (AssignedTests) object;
        if ((this.idassignedTests == null && other.idassignedTests != null) || (this.idassignedTests != null && !this.idassignedTests.equals(other.idassignedTests))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.AssignedTests[ idassignedTests=" + idassignedTests + " ]";
    }

}
