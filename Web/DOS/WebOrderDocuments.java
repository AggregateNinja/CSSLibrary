/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Web.DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author eboss
 */
@Entity 
@Table(name = "WebOrderDocuments")
public class WebOrderDocuments implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDocuments")
    private Integer idDocuments;
    @Basic(optional = false)
    @Column(name = "webIdOrders")
    private Integer webIdOrders;
    @Basic(optional = false)
    @Column(name = "webAccession")
    private String webAccession;
    @Column(name = "webUserId")
    private Integer webUserId;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateUpdated;
    @Column(name = "isActive")
    private boolean isActive;
    @Column(name = "isSent")
    private boolean isSent;
    @Column(name = "dateSent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;
    @Column(name = "sentDir")
    private String sentDir;
    @Column(name = "avalonIdOrders")
    private Integer avalonIdOrders;
    @Column(name = "avalonAccession")
    private String avalonAccession;
    
    public WebOrderDocuments() {
        
    }
    
    @Diff(fieldName="idDocuments")
    public Integer getIdDocuments()
    {
        return this.idDocuments;
    }
    public void setIdDocuments(Integer id)
    {
        this.idDocuments = id;
    }
    @Diff(fieldName="webIdOrders")
    public Integer getWebIdOrders()
    {
        return this.webIdOrders;
    }
    public void setWebIdOrders(Integer id)
    {
        this.webIdOrders = id;
    }
    @Diff(fieldName="webAccession")
    public String getWebAccession()
    {
        return this.webAccession;
    }
    public void setWebAccession(String accession)
    {
        this.webAccession = accession;
    }
    @Diff(fieldName="webUserId")
    public Integer getWebUserId()
    {
        return this.webUserId;
    }
    public void setWebUserId(Integer id)
    {
        this.webUserId = id;
    }
    @Diff(fieldName="fileName")
    public String getFileName()
    {
        return this.fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    @Diff(fieldName="typeName")
    public String getTypeName()
    {
        return this.typeName;
    }
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    @Diff(fieldName="dateCreated")
    public Date getDateCreated()
    {
        return dateCreated;
    }
    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }
    @Diff(fieldName="dateUpdated")
    public Date getDateUpdated()
    {
        return dateUpdated;
    }
    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }
    @Diff(fieldName="isActive")
    public boolean getIsActive()
    {
        return isActive;
    }
    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
    }
    @Diff(fieldName="isSent")
    public boolean getIsSent()
    {
        return isSent;
    }
    public void setIsSent(boolean isSent)
    {
        this.isSent = isSent;
    }
    @Diff(fieldName="dateSent")
    public Date getDateSent()
    {
        return dateSent;
    }
    public void setDateSent(Date dateSent)
    {
        this.dateSent = dateSent;
    }
    @Diff(fieldName="sentDir")
    public String getSentDir()
    {
        return this.sentDir;
    }
    public void setSentDir(String sentDir)
    {
        this.sentDir = sentDir;
    }
    @Diff(fieldName="avalonIdOrders")
    public Integer getAvalonIdOrders()
    {
        return this.avalonIdOrders;
    }
    public void setAvalonIdOrders(Integer id)
    {
        this.avalonIdOrders = id;
    }
    @Diff(fieldName="avalonAccession")
    public String getAvalonAccession()
    {
        return this.avalonAccession;
    }
    public void setAvalonAccession(String accession)
    {
        this.avalonAccession = accession;
    }
}
