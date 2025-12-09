/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Feb 5, 2016 - Apr 28, 2016
 */
@Entity 
@Table(name = "releaseResults")
public class ReleaseResults implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreleaseResults")
    private Integer idreleaseResults;
    @Basic(optional = false)
    @Column(name = "releaseId")
    private int releaseId;
    @Basic(optional = false)
    @Column(name = "clientId")
    private int clientId;
    @Basic(optional = false)
    @Column(name = "complete")
    private boolean complete;
    @Column(name = "error")
    private boolean error;
    @Column(name = "releasePrint")
    private String releasePrint;
    @Column(name = "releaseFax")
    private String releaseFax;
    @Column(name = "faxed")
    private boolean faxed;
    @Column(name = "printed")
    private boolean printed;
    @Column(name = "transmitted")
    private boolean transmitted;
    
    public ReleaseResults() {}
    
    public ReleaseResults(int releaseId, int clientId, boolean complete, boolean error, String releasePrint,
            String releaseFax, boolean faxed, boolean printed, boolean transmitted)
    {
        this.releaseId = releaseId;
        this.clientId = clientId;
        this.complete = complete;
        this.error = error;
        this.releasePrint = releasePrint;
        this.releaseFax = releaseFax;
        this.faxed = faxed;
        this.printed = printed;
        this.transmitted = transmitted;
    }

    public Integer getIdreleaseResults()
    {
        return idreleaseResults;
    }

    public void setIdreleaseResults(Integer idreleaseResults)
    {
        this.idreleaseResults = idreleaseResults;
    }

    public int getReleaseId()
    {
        return releaseId;
    }

    public void setReleaseId(int releaseId)
    {
        this.releaseId = releaseId;
    }

    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }

    public boolean isComplete()
    {
        return complete;
    }

    public void setComplete(boolean complete)
    {
        this.complete = complete;
    }

    public String getReleasePrint()
    {
        return releasePrint;
    }

    public void setReleasePrint(String releasePrint)
    {
        this.releasePrint = releasePrint;
    }

    public String getReleaseFax()
    {
        return releaseFax;
    }

    public void setReleaseFax(String releaseFax)
    {
        this.releaseFax = releaseFax;
    }

    public boolean isFaxed()
    {
        return faxed;
    }

    public void setFaxed(boolean faxed)
    {
        this.faxed = faxed;
    }

    public boolean isPrinted()
    {
        return printed;
    }

    public void setPrinted(boolean printed)
    {
        this.printed = printed;
    }

    public boolean isTransmitted()
    {
        return transmitted;
    }

    public void setTransmitted(boolean transmitted)
    {
        this.transmitted = transmitted;
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof ReleaseResults))
        {
            return false;
        }
        ReleaseResults other = (ReleaseResults) object;
        if ((this.idreleaseResults == null && other.idreleaseResults != null)
                || (this.idreleaseResults != null && !this.idreleaseResults.equals(other.idreleaseResults)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.idreleaseResults);
        hash = 37 * hash + this.releaseId;
        hash = 37 * hash + this.clientId;
        return hash;
    }

    @Override
    public String toString()
    {
        return "DOS.ReleaseOrders[ id=" + idreleaseResults + ", releaseId=" + releaseId + ", clientId=" + clientId +" ]";
    }
}
