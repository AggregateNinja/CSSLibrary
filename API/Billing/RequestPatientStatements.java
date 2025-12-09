/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Rob
 */
public class RequestPatientStatements extends RequestStatements
{
    private final Map<Object, BigDecimal> subscriberBalanceMap = new HashMap<Object, BigDecimal>();
    private boolean exportingStatements = false;
    
    public RequestPatientStatements(Integer userId)
    {
        super(StatementType.Patient, userId);
    }
    
    public RequestPatientStatements(Integer userId, Date invoiceDate)
    {
        super(StatementType.Patient, userId, invoiceDate);
    }
    
    public void addSubscriberOrder(String subscriberArNo, BigDecimal balance)
    {
        subscriberBalanceMap.put(subscriberArNo, balance);
    }

    public Map<Object, BigDecimal> getSubscriberBalanceMap() {
        return subscriberBalanceMap;
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
