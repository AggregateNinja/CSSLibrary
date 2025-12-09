/*
 * Computer Service & Support, Inc.  All Rights Reserved May 28, 2014
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
 * @date:   May 28, 2014  5:38:12 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcCrossRef.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcCrossRef")
@NamedQueries({
    @NamedQuery(name = "QcCrossRef.findAll", query = "SELECT q FROM QcCrossRef q")})
public class QcCrossRef implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcCrossRef")
    private Integer idqcCrossRef;
    @Basic(optional = false)
    @Column(name = "crossReference")
    private String crossReference;
    @Basic(optional = false)
    @Column(name = "controlLevel")
    private int controlLevel;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private int createdBy;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "idInst")
    private int idInst;

    public QcCrossRef() {
    }

    public QcCrossRef(Integer idqcCrossRef) {
        this.idqcCrossRef = idqcCrossRef;
    }

    public QcCrossRef(Integer idqcCrossRef, String crossReference, int controlLevel, Date created) {
        this.idqcCrossRef = idqcCrossRef;
        this.crossReference = crossReference;
        this.controlLevel = controlLevel;
        this.created = created;
    }

    public Integer getIdqcCrossRef() {
        return idqcCrossRef;
    }

    public void setIdqcCrossRef(Integer idqcCrossRef) {
        this.idqcCrossRef = idqcCrossRef;
    }

    public String getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(String crossReference) {
        this.crossReference = crossReference;
    }

    public int getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(int controlLevel) {
        this.controlLevel = controlLevel;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getIdInst() {
        return idInst;
    }

    public void setIdInst(int idInst) {
        this.idInst = idInst;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcCrossRef != null ? idqcCrossRef.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcCrossRef)) {
            return false;
        }
        QcCrossRef other = (QcCrossRef) object;
        if ((this.idqcCrossRef == null && other.idqcCrossRef != null) || (this.idqcCrossRef != null && !this.idqcCrossRef.equals(other.idqcCrossRef))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcCrossRef[ idqcCrossRef=" + idqcCrossRef + " ]";
    }

}
