/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import Utility.Diff;
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
 * @since Build {insert version here} 06/14/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "metabolitematrix")
@NamedQueries({
    @NamedQuery(name = "Metabolitematrix.findAll", query = "SELECT m FROM Metabolitematrix m")})
public class Metabolitematrix implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmetaboliteMatrix")
    private Integer idmetaboliteMatrix;
    @Column(name = "metabolite")
    private int metabolite;
    @Column(name = "substance")
    private int substance;

    public Metabolitematrix() {
    }

    public Metabolitematrix(Integer idmetaboliteMatrix) {
        this.idmetaboliteMatrix = idmetaboliteMatrix;
    }

    public Integer getIdmetaboliteMatrix() {
        return idmetaboliteMatrix;
    }

    public void setIdmetaboliteMatrix(Integer idmetaboliteMatrix) {
        this.idmetaboliteMatrix = idmetaboliteMatrix;
    }

    @Diff(fieldName="metabolite")
    public int getMetabolite() {
        return metabolite;
    }

    public void setMetabolite(int metabolite) {
        this.metabolite = metabolite;
    }

    @Diff(fieldName="substance")
    public int getSubstabance() {
        return substance;
    }

    public void setSubstabance(int substabance) {
        this.substance = substabance;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmetaboliteMatrix != null ? idmetaboliteMatrix.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Metabolitematrix)) {
            return false;
        }
        Metabolitematrix other = (Metabolitematrix) object;
        if ((this.idmetaboliteMatrix == null && other.idmetaboliteMatrix != null) || (this.idmetaboliteMatrix != null && !this.idmetaboliteMatrix.equals(other.idmetaboliteMatrix))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Metabolitematrix[ idmetaboliteMatrix=" + idmetaboliteMatrix + " ]";
    }
    
}
