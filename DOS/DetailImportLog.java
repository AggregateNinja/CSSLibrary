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
@Table(name = "detailImportLog")
@NamedQueries(
{
    @NamedQuery(name = "DetailImportLog.findAll", query = "SELECT d FROM DetailImportLog d")
})
public class DetailImportLog implements Serializable
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
    @Column(name = "cssMasterNumber")
    private Integer cssMasterNumber;
    @Column(name = "cssAccession")
    private Integer cssAccession;
    @Column(name = "cssTest")
    private String cssTest;
    @Column(name = "serviceDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceDate;
    @Column(name = "billDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDate;
    @Column(name = "billAmount")
    private Double billAmount;
    @Column(name = "transDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    @Column(name = "paidAmount")
    private Double paidAmount;
    @Column(name = "writeOffAmount")
    private Double writeOffAmount;
    @Column(name = "accession")
    private String accession;
    @Column(name = "idDetails")
    private Integer idDetails;
    @Column(name = "idResults")
    private Integer idResults;
    @Column(name = "idTests")
    private Integer idTests;

    public DetailImportLog()
    {
    }

    public DetailImportLog(Integer id)
    {
        this.id = id;
    }

    public DetailImportLog(Integer id, String filename, int lineNumber, Date dateCreated, String user, String status, String message)
    {
        this.id = id;
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.dateCreated = dateCreated;
        this.user = user;
        this.status = status;
        this.message = message;
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

    public Date getServiceDate()
    {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate)
    {
        this.serviceDate = serviceDate;
    }

    public Date getBillDate()
    {
        return billDate;
    }

    public void setBillDate(Date billDate)
    {
        this.billDate = billDate;
    }

    public Double getBillAmount()
    {
        return billAmount;
    }

    public void setBillAmount(Double billAmount)
    {
        this.billAmount = billAmount;
    }

    public Date getTransDate()
    {
        return transDate;
    }

    public void setTransDate(Date transDate)
    {
        this.transDate = transDate;
    }

    public Double getPaidAmount()
    {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount)
    {
        this.paidAmount = paidAmount;
    }

    public Double getWriteOffAmount()
    {
        return writeOffAmount;
    }

    public void setWriteOffAmount(Double writeOffAmount)
    {
        this.writeOffAmount = writeOffAmount;
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

    public Integer getIdResults()
    {
        return idResults;
    }

    public void setIdResults(Integer idResults)
    {
        this.idResults = idResults;
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
        if (!(object instanceof DetailImportLog))
        {
            return false;
        }
        DetailImportLog other = (DetailImportLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.DetailImportLog[ id=" + id + " ]";
    }

}
