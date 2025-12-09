/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

/**
 *
 * @author Rob
 */
public class ResponseClientStatements extends ResponseStatements
{
    private boolean exported;
    
    public void setExported(boolean exported)
    {
        this.exported = exported;
    }
    
    public boolean isExported()
    {
        return this.exported;
    }
}
