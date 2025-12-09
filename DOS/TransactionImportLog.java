/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Michael
 */
@Entity
@Table(name = "transactionImportLog")
@NamedQueries(
        {
            @NamedQuery(name = "TransactionImportLog.findAll", query = "SELECT t FROM TransactionImportLog t")
        })
public class TransactionImportLog implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "filename")
    private String filename;
    @Basic(optional = false)
    @Column(name = "lineNumber")
    private int lineNumber;
    @Basic(optional = false)
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Column(name = "cssMasterNumber")
    private Integer cssMasterNumber;
    @Column(name = "cssAccession")
    private Integer cssAccession;
    @Column(name = "cssTest")
    private String cssTest;
    @Column(name = "transDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "transAmount")
    private Double transAmount;
    @Column(name = "accession")
    private String accession;
    @Column(name = "idDetails")
    private Integer idDetails;
    @Column(name = "idTests")
    private Integer idTests;

    public TransactionImportLog()
    {
    }

    public TransactionImportLog(Integer id)
    {
        this.id = id;
    }

    public TransactionImportLog(Integer id, String filename, int lineNumber, Date dateCreated, String user, String status, String message, String type)
    {
        this.id = id;
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.dateCreated = dateCreated;
        this.user = user;
        this.status = status;
        this.message = message;
        this.type = type;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber)
    {
        this.lineNumber = lineNumber;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getCssMasterNumber()
    {
        return cssMasterNumber;
    }

    public void setCssMasterNumber(Integer cssMasterNumber)
    {
        this.cssMasterNumber = cssMasterNumber;
    }

    public Integer getCssAccession()
    {
        return cssAccession;
    }

    public void setCssAccession(Integer cssAccession)
    {
        this.cssAccession = cssAccession;
    }

    public String getCssTest()
    {
        return cssTest;
    }

    public void setCssTest(String cssTest)
    {
        this.cssTest = cssTest;
    }

    public Date getTransDate()
    {
        return transDate;
    }

    public void setTransDate(Date transDate)
    {
        this.transDate = transDate;
    }

    public Double getTransAmount()
    {
        return transAmount;
    }

    public void setTransAmount(Double transAmount)
    {
        this.transAmount = transAmount;
    }

    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public Integer getIdDetails()
    {
        return idDetails;
    }

    public void setIdDetails(Integer idDetails)
    {
        this.idDetails = idDetails;
    }

    public Integer getIdTests()
    {
        return idTests;
    }

    public void setIdTests(Integer idTests)
    {
        this.idTests = idTests;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransactionImportLog))
        {
            return false;
        }
        TransactionImportLog other = (TransactionImportLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.TransactionImportLog[ id=" + id + " ]";
    }

}
