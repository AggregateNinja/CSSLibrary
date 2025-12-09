/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
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
 * @since Build {insert version here} 02/27/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "xrefs")
@NamedQueries({
    @NamedQuery(name = "Xrefs.findAll", query = "SELECT x FROM Xrefs x")})
public class Xrefs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idxrefs")
    private Integer idxrefs;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public Xrefs() {
    }

    public Xrefs(Integer idxrefs) {
        this.idxrefs = idxrefs;
    }

    public Xrefs(Integer idxrefs, String name, String type, Date created) {
        this.idxrefs = idxrefs;
        this.name = name;
        this.type = type;
        this.created = created;
    }

    public Integer getIdxrefs() {
        return idxrefs;
    }

    public void setIdxrefs(Integer idxrefs) {
        this.idxrefs = idxrefs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (idxrefs != null ? idxrefs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Xrefs)) {
            return false;
        }
        Xrefs other = (Xrefs) object;
        if ((this.idxrefs == null && other.idxrefs != null) || (this.idxrefs != null && !this.idxrefs.equals(other.idxrefs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Xrefs[ idxrefs=" + idxrefs + " ]";
    }

}
