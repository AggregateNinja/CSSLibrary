/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 10, 2014
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
 * @date:   Jun 10, 2014  1:06:52 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcInstControls.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcInstControls")
@NamedQueries({
    @NamedQuery(name = "QcInstControls.findAll", query = "SELECT q FROM QcInstControls q")})
public class QcInstControls implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "level")
    private Integer level;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "updatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "idInst")
    private Integer idInst;
    @Column(name = "idqcLot")
    private Integer idqcLot;
    @Column(name = "createdBy")
    private Integer createdBy;
    @Column(name = "updatedBy")
    private Integer updatedBy;
    @Column(name = "specimenType")
    private Integer specimenType;

    public QcInstControls() {
    }

    public QcInstControls(Integer id) {
        this.id = id;
    }

    public QcInstControls(Integer id, Date created, Date updatedDate) {
        this.id = id;
        this.created = created;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getIdInst() {
        return idInst;
    }

    public void setIdInst(Integer idInst) {
        this.idInst = idInst;
    }

    public Integer getIdqcLot() {
        return idqcLot;
    }

    public void setIdqcLot(Integer idqcLot) {
        this.idqcLot = idqcLot;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getSpecimenType()
    {
        return specimenType;
    }

    public void setSpecimenType(Integer specimenType)
    {
        this.specimenType = specimenType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcInstControls)) {
            return false;
        }
        QcInstControls other = (QcInstControls) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcInstControls[ id=" + id + " ]";
    }

}
