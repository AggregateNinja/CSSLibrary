/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "assignedWorksheets")
@NamedQueries({
    @NamedQuery(name = "AssignedWorksheets.findAll", query = "SELECT a FROM AssignedWorksheets a")})
public class AssignedWorksheets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idassignedWorksheets")
    private Integer idassignedWorksheets;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "department")
    private int department;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public AssignedWorksheets() {
    }

    public AssignedWorksheets(Integer idassignedWorksheets) {
        this.idassignedWorksheets = idassignedWorksheets;
    }

    public AssignedWorksheets(Integer idassignedWorksheets, int number, String name, Date created) {
        this.idassignedWorksheets = idassignedWorksheets;
        this.number = number;
        this.name = name;
        this.created = created;
    }

    public Integer getIdassignedWorksheets() {
        return idassignedWorksheets;
    }

    public void setIdassignedWorksheets(Integer idassignedWorksheets) {
        this.idassignedWorksheets = idassignedWorksheets;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idassignedWorksheets != null ? idassignedWorksheets.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssignedWorksheets)) {
            return false;
        }
        AssignedWorksheets other = (AssignedWorksheets) object;
        if ((this.idassignedWorksheets == null && other.idassignedWorksheets != null) || (this.idassignedWorksheets != null && !this.idassignedWorksheets.equals(other.idassignedWorksheets))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.AssignedWorksheets[ idassignedWorksheets=" + idassignedWorksheets + " ]";
    }

}
