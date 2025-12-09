package DOS;

import Utility.Diff;
import java.io.Serializable;

/**
 * @date:   Nov 26, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: AutoOrderTests.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */


public class AutoOrderTests implements Serializable 
{
    private static final long serialVersionUID = 1L;
    private int clientId;
    private int testNumber;

    public AutoOrderTests() {}
    public AutoOrderTests(int clientId, int testNumber)
    {
        this.clientId = clientId;
        this.testNumber = testNumber;
    }

    @Diff(fieldName = "clientId")
    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    @Diff(fieldName = "testNumber")
    public int getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(int testNumber)
    {
        this.testNumber = testNumber;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + this.clientId;
        hash = 29 * hash + this.testNumber;
        return hash;
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof AutoOrderTests))
        {
            return false;
        }
        AutoOrderTests other = (AutoOrderTests) object;
        return (other.clientId == this.clientId && other.testNumber == this.testNumber);
    }

    @Override
    public String toString()
    {
        return "DOS.AutoOrderTests[ clientId =" + this.clientId + ", testNumber = " + this.testNumber + " ]";
    }
}
