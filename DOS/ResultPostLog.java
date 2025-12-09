package DOS;

import DOS.IDOS.BaseLog;
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
 * @date:   Sep 27, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: ResultPostLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "resultPostLog")
@NamedQueries(
{
    @NamedQuery(name = "ResultPostLog.findAll", query = "SELECT r FROM ResultPostLog r")
})
public class ResultPostLog extends BaseLog
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idresultPostLog")
    private Integer idresultPostLog;

    public ResultPostLog()
    {
    }

    public ResultPostLog(Integer idresultPostLog)
    {
        this.idresultPostLog = idresultPostLog;
    }

    public ResultPostLog(Integer idresultPostLog, String action, Date createdDate)
    {
        this.idresultPostLog = idresultPostLog;
        this.setAction(action);
        this.setCreatedDate(createdDate);
    }

    public Integer getIdresultPostLog()
    {
        return idresultPostLog;
    }

    public void setIdresultPostLog(Integer idresultPostLog)
    {
        this.idresultPostLog = idresultPostLog;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idresultPostLog != null ? idresultPostLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultPostLog))
        {
            return false;
        }
        ResultPostLog other = (ResultPostLog) object;
        if ((this.idresultPostLog == null && other.idresultPostLog != null) || (this.idresultPostLog != null && !this.idresultPostLog.equals(other.idresultPostLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.ResultPostLog[ idresultPostLog=" + idresultPostLog + " ]";
    }

}
