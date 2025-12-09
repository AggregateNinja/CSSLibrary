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
 * @file name: OrderEntryLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "orderEntryLog")
@NamedQueries(
{
    @NamedQuery(name = "OrderEntryLog.findAll", query = "SELECT o FROM OrderEntryLog o")
})
public class OrderEntryLog extends BaseLog implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idorderEntryLog")
    private Integer idorderEntryLog;

    public OrderEntryLog()
    {
    }

    public OrderEntryLog(Integer idresultPostLog)
    {
        this.idorderEntryLog = idresultPostLog;
    }

    public OrderEntryLog(Integer idresultPostLog, String action, Date createdDate)
    {
        this.idorderEntryLog = idresultPostLog;
        this.setAction(action);
        this.setCreatedDate(createdDate);
    }

    public Integer getIdorderEntryLog()
    {
        return idorderEntryLog;
    }

    public void setIdorderEntryLog(Integer idorderEntryLog)
    {
        this.idorderEntryLog = idorderEntryLog;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idorderEntryLog != null ? idorderEntryLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderEntryLog))
        {
            return false;
        }
        OrderEntryLog other = (OrderEntryLog) object;
        if ((this.idorderEntryLog == null && other.idorderEntryLog != null) || (this.idorderEntryLog != null && !this.idorderEntryLog.equals(other.idorderEntryLog)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.OrderEntryLog[ idorderEntryLog=" + idorderEntryLog + " ]";
    }

}
