/*
 * Computer Service & Support, Inc.  All Rights Reserved May 11, 2015
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @date:   May 11, 2015  5:15:06 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: CarbonCopies.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "carbonCopies")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarbonCopies.findAll", query = "SELECT c FROM carbonCopies c"),
    @NamedQuery(name = "CarbonCopies.findById", query = "SELECT c FROM carbonCopies c WHERE c.id = :id"),
    @NamedQuery(name = "CarbonCopies.findByFaxName", query = "SELECT c FROM carbonCopies c WHERE c.faxName = :faxName"),
    @NamedQuery(name = "CarbonCopies.findByFaxNumber", query = "SELECT c FROM carbonCopies c WHERE c.faxNumber = :faxNumber"),
    @NamedQuery(name = "CarbonCopies.findByFaxMemo", query = "SELECT c FROM carbonCopies c WHERE c.faxMemo = :faxMemo"),
    @NamedQuery(name = "CarbonCopies.findByCreated", query = "SELECT c FROM carbonCopies c WHERE c.created = :created"),
    @NamedQuery(name = "CarbonCopies.findByFaxed", query = "SELECT c FROM carbonCopies c WHERE c.faxed = :faxed")})
public class CarbonCopies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idOrders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "faxName")
    private String faxName;
    @Basic(optional = false)
    @Column(name = "faxNumber")
    private String faxNumber;
    @Column(name = "doctor")
    private Integer doctor;
    @Column(name = "client")
    private Integer client;
    @Column(name = "faxMemo")
    private String faxMemo;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "faxed")
    private boolean faxed;
    @Column(name = "isCascading")
    private Boolean isCascading;

    public CarbonCopies() {
    }

    public CarbonCopies(Integer id) {
        this.id = id;
    }

    public CarbonCopies(Integer id, String faxName, String faxNumber, Date created, boolean faxed) {
        this.id = id;
        this.faxName = faxName;
        this.faxNumber = faxNumber;
        this.created = created;
        this.faxed = faxed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    public String getFaxName() {
        return faxName;
    }

    public void setFaxName(String faxName) {
        this.faxName = faxName;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public Integer getDoctor() {
        return doctor;
    }

    public void setDoctor(Integer doctor) {
        this.doctor = doctor;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getFaxMemo() {
        return faxMemo;
    }

    public void setFaxMemo(String faxMemo) {
        this.faxMemo = faxMemo;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean getFaxed() {
        return faxed;
    }

    public void setFaxed(boolean faxed) {
        this.faxed = faxed;
    }

    public Boolean getIsCascading()
    {
        return isCascading;
    }

    public void setIsCascading(Boolean isCascading)
    {
        this.isCascading = isCascading;
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
        if (!(object instanceof CarbonCopies)) {
            return false;
        }
        CarbonCopies other = (CarbonCopies) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.CarbonCopies[ id=" + id + " ]";
    }

}
