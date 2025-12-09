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
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "cpt")
@NamedQueries({
    @NamedQuery(name = "Cpt.findAll", query = "SELECT c FROM Cpt c")})
public class Cpt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcpt")
    private Integer idcpt;
    @Basic(optional = false)
    @Column(name = "cpt")
    private String cpt;
    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "modifier")
    private String modifier;
    @Column(name = "iddetailline")
    //@ManyToOne(optional = false)
    private Integer detaillines;

    public Cpt() {
    }

    public Cpt(Integer idcpt) {
        this.idcpt = idcpt;
    }

    public Cpt(Integer idcpt, String cpt, int quantity) {
        this.idcpt = idcpt;
        this.cpt = cpt;
        this.quantity = quantity;
    }

    public Integer getIdcpt() {
        return idcpt;
    }

    public void setIdcpt(Integer idcpt) {
        this.idcpt = idcpt;
    }

    public String getCpt() {
        return cpt;
    }

    public void setCpt(String cpt) {
        this.cpt = cpt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Integer getDetaillines() {
        return detaillines;
    }

    public void setDetaillines(Integer detaillines) {
        this.detaillines = detaillines;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcpt != null ? idcpt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cpt)) {
            return false;
        }
        Cpt other = (Cpt) object;
        if ((this.idcpt == null && other.idcpt != null) || (this.idcpt != null && !this.idcpt.equals(other.idcpt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Cpt[ idcpt=" + idcpt + " ]";
    }

}
