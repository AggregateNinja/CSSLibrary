/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class RequestClientStatements extends RequestStatements
{
    private final Map<Object, BigDecimal> clientIdBalances = new LinkedHashMap<>();
    private boolean exportingStatements = false;
    
    public RequestClientStatements(Integer userId)
    {
        super(StatementType.Client, userId);
    }
    
    public RequestClientStatements(Integer userId, Date invoiceDate)
    {
        super(StatementType.Client, userId, invoiceDate);
    }
    
    public RequestClientStatements(Integer userId, Date invoiceDate, Boolean showPastTotals)
    {
        super(StatementType.Client, userId, invoiceDate, showPastTotals);
    }
    
    public RequestClientStatements(Integer userId, Date invoiceDate, String sortBy, Boolean showPastTotals)
    {
        super(StatementType.Client, userId, invoiceDate, sortBy, showPastTotals);
    }
    
    public void addClientOrder(Integer clientId, BigDecimal balance)
    {
        clientIdBalances.put(clientId, balance);
    }
    
    public Map<Object, BigDecimal> getClientIdBalances()
    {
        return this.clientIdBalances;
    }
    
    public void setExporting(boolean exporting)
    {
        this.exportingStatements = exporting;
    }
    
    public boolean isExporting()
    {
        return this.exportingStatements;
    }
}
