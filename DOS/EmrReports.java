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
 * @date:   Apr 9, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: EmrReports.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "emrReports")
@NamedQueries(
{
    @NamedQuery(name = "EmrReports.findAll", query = "SELECT e FROM EmrReports e")
})
public class EmrReports implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idemrReports")
    private Integer idemrReports;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Column(name = "releaseJobId")
    private Integer releaseJobId;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Lob
    @Column(name = "report")
    private byte[] report;

    public EmrReports()
    {
    }

    public EmrReports(Integer idemrReports)
    {
        this.idemrReports = idemrReports;
    }

    public EmrReports(Integer idemrReports, int idOrders, Date created, byte[] report)
    {
        this.idemrReports = idemrReports;
        this.idOrders = idOrders;
        this.created = created;
        this.report = report;
    }

    public Integer getIdemrReports()
    {
        return idemrReports;
    }

    public void setIdemrReports(Integer idemrReports)
    {
        this.idemrReports = idemrReports;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public Integer getReleaseJobId()
    {
        return releaseJobId;
    }

    public void setReleaseJobId(Integer releaseJobId)
    {
        this.releaseJobId = releaseJobId;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public byte[] getReport()
    {
        return report;
    }

    public void setReport(byte[] report)
    {
        this.report = report;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idemrReports != null ? idemrReports.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrReports))
        {
            return false;
        }
        EmrReports other = (EmrReports) object;
        if ((this.idemrReports == null && other.idemrReports != null) || (this.idemrReports != null && !this.idemrReports.equals(other.idemrReports)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.EmrReports[ idemrReports=" + idemrReports + " ]";
    }

}
