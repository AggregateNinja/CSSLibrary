package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Aug 14, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: GeneticReport.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "GeneticReport")
@NamedQueries(
{
    @NamedQuery(name = "GeneticReport.findAll", query = "SELECT g FROM GeneticReport g")
})
public class GeneticReport implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idorders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "idpatients")
    private Integer idPatients;
    @Column(name = "reportName")
    private String reportName;
    @Basic(optional = false)
    @Lob
    @Column(name = "report")
    private byte[] report;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public GeneticReport()
    {
    }

    public GeneticReport(Integer id)
    {
        this.id = id;
    }

    public GeneticReport(Integer id, byte[] report, Date created)
    {
        this.id = id;
        this.report = report;
        this.created = created;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Integer getIdPatients()
    {
        return idPatients;
    }

    public void setIdPatients(Integer idPatients)
    {
        this.idPatients = idPatients;
    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public byte[] getReport()
    {
        return report;
    }

    public void setReport(byte[] report)
    {
        this.report = report;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
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
        if (!(object instanceof GeneticReport))
        {
            return false;
        }
        GeneticReport other = (GeneticReport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.GeneticReport[ id=" + id + " ]";
    }

}
