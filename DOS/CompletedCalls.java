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
 * @date:   Mar 4, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: CompletedCalls.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "completedCalls")
@NamedQueries(
{
    @NamedQuery(name = "CompletedCalls.findAll", query = "SELECT c FROM CompletedCalls c")
})
public class CompletedCalls implements Serializable 
{
    @Lob
    @Column(name = "comment")
    private String comment;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcompletedCalls")
    private Integer idcompletedCalls;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Basic(optional = false)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "user")
    private int user;
    @Basic(optional = false)
    @Column(name = "actionDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDateTime;
    @Column(name = "lastCall")
    private boolean lastCall;

    public CompletedCalls()
    {
    }

    public CompletedCalls(Integer idcompletedCalls)
    {
        this.idcompletedCalls = idcompletedCalls;
    }

    public CompletedCalls(Integer idcompletedCalls, int idOrders, String reason, Date created, int user, Date actionDateTime)
    {
        this.idcompletedCalls = idcompletedCalls;
        this.idOrders = idOrders;
        this.reason = reason;
        this.created = created;
        this.user = user;
        this.actionDateTime = actionDateTime;
    }

    public Integer getIdcompletedCalls()
    {
        return idcompletedCalls;
    }

    public void setIdcompletedCalls(Integer idcompletedCalls)
    {
        this.idcompletedCalls = idcompletedCalls;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public int getUser()
    {
        return user;
    }

    public void setUser(int user)
    {
        this.user = user;
    }

    public Date getActionDateTime()
    {
        return actionDateTime;
    }

    public void setActionDateTime(Date actionDateTime)
    {
        this.actionDateTime = actionDateTime;
    }
    
    public boolean getLastCall()
    {
        return lastCall;
    }

    public void setLastCall(boolean lastCall)
    {
        this.lastCall = lastCall;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idcompletedCalls != null ? idcompletedCalls.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompletedCalls))
        {
            return false;
        }
        CompletedCalls other = (CompletedCalls) object;
        if ((this.idcompletedCalls == null && other.idcompletedCalls != null) || (this.idcompletedCalls != null && !this.idcompletedCalls.equals(other.idcompletedCalls)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.CompletedCalls[ idcompletedCalls=" + idcompletedCalls + " ]";
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

}
