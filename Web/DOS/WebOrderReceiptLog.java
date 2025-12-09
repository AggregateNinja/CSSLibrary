package Web.DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebOrderReceiptLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebOrderReceiptLog")
@NamedQueries(
{
    @NamedQuery(name = "WebOrderReceiptLog.findAll", query = "SELECT w FROM WebOrderReceiptLog w")
})
public class WebOrderReceiptLog implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idWebOrderReceiptLog")
    private Integer idWebOrderReceiptLog;
    @Basic(optional = false)
    @Column(name = "webAccession")
    private String webAccession;
    @Basic(optional = false)
    @Column(name = "webCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date webCreated;
    @Column(name = "idOrders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "user")
    private Integer user;
    @Column(name = "receiptedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptedDate;
    @Basic(optional = false)
    @Column(name = "webUser")
    private Integer webUser;

    public WebOrderReceiptLog()
    {
    }

    public WebOrderReceiptLog(Integer idWebOrderReceiptLog)
    {
        this.idWebOrderReceiptLog = idWebOrderReceiptLog;
    }

    public WebOrderReceiptLog(Integer idWebOrderReceiptLog, String webAccession, Date webCreated)
    {
        this.idWebOrderReceiptLog = idWebOrderReceiptLog;
        this.webAccession = webAccession;
        this.webCreated = webCreated;
    }

    public Integer getIdWebOrderReceiptLog()
    {
        return idWebOrderReceiptLog;
    }

    public void setIdWebOrderReceiptLog(Integer idWebOrderReceiptLog)
    {
        this.idWebOrderReceiptLog = idWebOrderReceiptLog;
    }

    public String getWebAccession()
    {
        return webAccession;
    }

    public void setWebAccession(String webAccession)
    {
        this.webAccession = webAccession;
    }

    public Date getWebCreated()
    {
        return webCreated;
    }

    public void setWebCreated(Date webCreated)
    {
        this.webCreated = webCreated;
    }

    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Date getReceiptedDate()
    {
        return receiptedDate;
    }

    public void setReceiptedDate(Date receiptedDate)
    {
        this.receiptedDate = receiptedDate;
    }

    public Integer getWebUser()
    {
        return webUser;
    }

    public void setWebUser(Integer webUser)
    {
        this.webUser = webUser;
    }
    
    public Integer getUser()
    {
        return user;
    }

    public void setUser(Integer user)
    {
        this.user = user;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idWebOrderReceiptLog != null ? idWebOrderReceiptLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebOrderReceiptLog))
        {
            return false;
        }
        WebOrderReceiptLog other = (WebOrderReceiptLog) object;
        if ((this.idWebOrderReceiptLog == null && other.idWebOrderReceiptLog != null) || (this.idWebOrderReceiptLog != null && !this.idWebOrderReceiptLog.equals(other.idWebOrderReceiptLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebOrderReceiptLog[ idWebOrderReceiptLog=" + idWebOrderReceiptLog + " ]";
    }

}
