/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;

/**
 *
 * @author Ammar
 */
public class ClientCustomRangesPK implements Serializable {
    public static long serialVersionUID = 42L;
    private Integer clientId;
    private Integer testNumber;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClientCustomRangesPK)
        {
            ClientCustomRangesPK objClientRangePK = (ClientCustomRangesPK) obj;
            return objClientRangePK.clientId.equals(clientId) && objClientRangePK.testNumber.equals(testNumber);
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
