/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 15, 2014
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
 * @date:   Aug 15, 2014  12:12:24 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: TranslationalQueue.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "translationalQueue")
@NamedQueries({
    @NamedQuery(name = "TranslationalQueue.findAll", query = "SELECT t FROM TranslationalQueue t")})
public class TranslationalQueue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "accession")
    private String accession;
    @Column(name = "idorders")
    private Integer idorders;
    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "transmitted")
    private boolean transmitted;
    @Column(name = "transmittedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transmittedDate;

    public TranslationalQueue() {
    }

    public TranslationalQueue(Integer id) {
        this.id = id;
    }

    public TranslationalQueue(Integer id, Date createdDate, boolean transmitted) {
        this.id = id;
        this.createdDate = createdDate;
        this.transmitted = transmitted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public Integer getIdorders() {
        return idorders;
    }

    public void setIdorders(Integer idorders) {
        this.idorders = idorders;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getTransmitted() {
        return transmitted;
    }

    public void setTransmitted(boolean transmitted) {
        this.transmitted = transmitted;
    }

    public Date getTransmittedDate() {
        return transmittedDate;
    }

    public void setTransmittedDate(Date transmittedDate) {
        this.transmittedDate = transmittedDate;
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
        if (!(object instanceof TranslationalQueue)) {
            return false;
        }
        TranslationalQueue other = (TranslationalQueue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.TranslationalQueue[ id=" + id + " ]";
    }

}
