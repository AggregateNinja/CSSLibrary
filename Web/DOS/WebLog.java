package Web.DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebLog")
@NamedQueries(
{
    @NamedQuery(name = "WebLog.findAll", query = "SELECT w FROM WebLog w")
})
public class WebLog implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLogs")
    private Integer idLogs;
    @Basic(optional = false)
    @Column(name = "userId")
    private int userId;
    @Basic(optional = false)
    @Column(name = "typeId")
    private int typeId;
    @Basic(optional = false)
    @Column(name = "logDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;

    public WebLog()
    {
    }

    public WebLog(Integer idLogs)
    {
        this.idLogs = idLogs;
    }

    public WebLog(Integer idLogs, int userId, Date logDate)
    {
        this.idLogs = idLogs;
        this.userId = userId;
        this.logDate = logDate;
    }

    public Integer getIdLogs()
    {
        return idLogs;
    }

    public void setIdLogs(Integer idLogs)
    {
        this.idLogs = idLogs;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    
    public int getTypeId()
    {
        return typeId;
    }

    public void setTypeId(int typeId)
    {
        this.typeId = typeId;
    }

    public Date getLogDate()
    {
        return logDate;
    }

    public void setLogDate(Date logDate)
    {
        this.logDate = logDate;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idLogs != null ? idLogs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebLog))
        {
            return false;
        }
        WebLog other = (WebLog) object;
        if ((this.idLogs == null && other.idLogs != null) || (this.idLogs != null && !this.idLogs.equals(other.idLogs)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebLog[ idLogs=" + idLogs + " ]";
    }

}
