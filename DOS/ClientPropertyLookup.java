
package DOS;

import java.util.Date;

public class ClientPropertyLookup 
{
    
    public int clientId;
    public int clientPropertyId;
    public Date createdDate;
    public int createdByUserId;

    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public int getClientPropertyId()
    {
        return clientPropertyId;
    }

    public void setClientPropertyId(int clientPropertyId)
    {
        this.clientPropertyId = clientPropertyId;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public int getCreatedByUserId()
    {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId)
    {
        this.createdByUserId = createdByUserId;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 41 * hash + this.clientId;
        hash = 41 * hash + this.clientPropertyId;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ClientPropertyLookup other = (ClientPropertyLookup) obj;
        return other.hashCode() == this.hashCode();
    }
}
