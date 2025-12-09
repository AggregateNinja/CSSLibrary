/*
 * Computer Service & Support, Inc.  All Rights Reserved May 11, 2015
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @date:   May 11, 2015  5:15:06 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ClientCopy.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "clientCopy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientCopy.findAll", query = "SELECT c FROM ClientCopy c"),
    @NamedQuery(name = "ClientCopy.findById", query = "SELECT c FROM ClientCopy c WHERE c.id = :id"),
    @NamedQuery(name = "ClientCopy.findByFaxName", query = "SELECT c FROM ClientCopy c WHERE c.faxName = :faxName"),
    @NamedQuery(name = "ClientCopy.findByFaxNo", query = "SELECT c FROM ClientCopy c WHERE c.faxNo = :faxNo")})
public class ClientCopy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idClients")
    private Integer idClients;
    @Column(name = "faxName")
    private String faxName;
    @Column(name = "faxNo")
    private String faxNo;
    @Column(name = "doctor")
    private Integer doctor;
    @Column(name = "client")
    private Integer client;
    @Column(name = "faxMemo")
    private String faxMemo;
    @Column(name = "isCascading")
    private Boolean isCascading;

    public ClientCopy() {
    }

    public ClientCopy(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdClients() {
        return idClients;
    }

    public void setIdClients(Integer idClients) {
        this.idClients = idClients;
    }

    public String getFaxName() {
        return faxName;
    }

    public void setFaxName(String faxName) {
        this.faxName = faxName;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
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

    public Boolean getIsCascading()
    {
        return isCascading;
    }

    public void setIsCascading(Boolean isCascading)
    {
        this.isCascading = isCascading;
    }

    public String getFaxMemo()
    {
        return faxMemo;
    }

    public void setFaxMemo(String faxMemo)
    {
        this.faxMemo = faxMemo;
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
        if (!(object instanceof ClientCopy)) {
            return false;
        }
        ClientCopy other = (ClientCopy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.ClientCopy[ id=" + id + " ]";
    }

}
