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
 * @since Build {insert version here} 04/23/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "reporteddate")
@NamedQueries({
    @NamedQuery(name = "Reporteddate.findAll", query = "SELECT r FROM Reporteddate r")})
public class Reporteddate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idreporteddate")
    private Integer idreporteddate;
    @Basic(optional = false)
    @Column(name = "dateReported")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReported;

    public Reporteddate() {
    }

    public Reporteddate(Integer idreporteddate) {
        this.idreporteddate = idreporteddate;
    }

    public Reporteddate(Integer idreporteddate, Date dateReported) {
        this.idreporteddate = idreporteddate;
        this.dateReported = dateReported;
    }

    public Integer getIdreporteddate() {
        return idreporteddate;
    }

    public void setIdreporteddate(Integer idreporteddate) {
        this.idreporteddate = idreporteddate;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreporteddate != null ? idreporteddate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reporteddate)) {
            return false;
        }
        Reporteddate other = (Reporteddate) object;
        if ((this.idreporteddate == null && other.idreporteddate != null) || (this.idreporteddate != null && !this.idreporteddate.equals(other.idreporteddate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Reporteddate[ idreporteddate=" + idreporteddate + " ]";
    }

}
