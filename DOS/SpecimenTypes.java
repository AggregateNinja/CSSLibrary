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
 * @since Build {insert version here} 10/17/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "specimenTypes")
@NamedQueries({
    @NamedQuery(name = "SpecimenTypes.findAll", query = "SELECT s FROM SpecimenTypes s")})
public class SpecimenTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idspecimenTypes")
    private Integer idspecimenTypes;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    
    public SpecimenTypes() {
    }

    public SpecimenTypes(Integer idspecimenTypes) {
        this.idspecimenTypes = idspecimenTypes;
    }

    public SpecimenTypes(Integer idspecimenTypes, String name) {
        this.idspecimenTypes = idspecimenTypes;
        this.name = name;
    }

    public Integer getIdspecimenTypes() {
        return idspecimenTypes;
    }

    public void setIdspecimenTypes(Integer idspecimenTypes) {
        this.idspecimenTypes = idspecimenTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idspecimenTypes != null ? idspecimenTypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpecimenTypes)) {
            return false;
        }
        SpecimenTypes other = (SpecimenTypes) object;
        if ((this.idspecimenTypes == null && other.idspecimenTypes != null) || (this.idspecimenTypes != null && !this.idspecimenTypes.equals(other.idspecimenTypes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return getName();
        //return "DOS.SpecimenTypes[ idspecimenTypes=" + idspecimenTypes + " ]";
    }

}
