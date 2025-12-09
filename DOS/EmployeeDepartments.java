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
 * @since Build {insert version here} 01/27/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "employeeDepartments")
@NamedQueries({
    @NamedQuery(name = "EmployeeDepartments.findAll", query = "SELECT e FROM EmployeeDepartments e")})
public class EmployeeDepartments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemployeeDepartments")
    private Integer idemployeeDepartments;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "defaultUserGroup")
    private Integer defaultUserGroup;

    public EmployeeDepartments() {
    }

    public EmployeeDepartments(Integer idemployeeDepartments) {
        this.idemployeeDepartments = idemployeeDepartments;
    }

    public EmployeeDepartments(Integer idemployeeDepartments, String name) {
        this.idemployeeDepartments = idemployeeDepartments;
        this.name = name;
    }

    public Integer getIdemployeeDepartments() {
        return idemployeeDepartments;
    }

    public void setIdemployeeDepartments(Integer idemployeeDepartments) {
        this.idemployeeDepartments = idemployeeDepartments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefaultUserGroup() {
        return defaultUserGroup;
    }

    public void setDefaultUserGroup(Integer defaultUserGroup) {
        this.defaultUserGroup = defaultUserGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idemployeeDepartments != null ? idemployeeDepartments.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeDepartments)) {
            return false;
        }
        EmployeeDepartments other = (EmployeeDepartments) object;
        if ((this.idemployeeDepartments == null && other.idemployeeDepartments != null) || (this.idemployeeDepartments != null && !this.idemployeeDepartments.equals(other.idemployeeDepartments))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.EmployeeDepartments[ idemployeeDepartments=" + idemployeeDepartments + " ]";
    }

}
