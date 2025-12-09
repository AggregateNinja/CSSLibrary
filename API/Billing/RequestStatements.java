/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseRequest;
import java.util.Date;

/**
 *
 * @author Rob
 */
public class RequestStatements extends BaseRequest<RequestStatements>
{
    public enum StatementType
    {
        Patient,
        Client,
        HCFA;
    }
    
    private static final long serialVersionUID = 42L;
    protected Date orderStartDate;
    protected final Boolean showPastTotals;
    protected final Date invoiceDate;
    protected final Integer userId;
    protected final String sortBy;
    protected final StatementType statementType;
    
    public Integer getUserId()
    {
        return this.userId;
    }

    public void setOrderStartDate(Date orderStartDate) {
        this.orderStartDate = orderStartDate;
    }

    public StatementType getStatementType() {
        return statementType;
    }
    
    public Date getInvoiceDate()
    {
        return invoiceDate;
    }
    
    public RequestStatements(StatementType statementType, Integer userId)
    {
        this.statementType = statementType;
        this.userId = userId;
        this.sortBy = "";
        this.orderStartDate = null;
        this.invoiceDate = new Date();
        this.showPastTotals = true;
    }
    
    public RequestStatements(StatementType statementType, Integer userId, Date invoiceDate)
    {
        this.statementType = statementType;
        this.userId = userId;
        this.sortBy = "";
        this.orderStartDate = null;
        this.invoiceDate = invoiceDate;
        this.showPastTotals = true;
    }
    
    public RequestStatements(StatementType statementType, Integer userId, Date orderStartDate, Date invoiceDate)
    {
        this.statementType = statementType;
        this.userId = userId;
        this.sortBy = "";
        this.orderStartDate = orderStartDate;
        this.invoiceDate = invoiceDate;
        this.showPastTotals = true;
    }
    public RequestStatements(StatementType statementType, Integer userId, Date invoiceDate, Boolean showPastTotals)
    {
        this.statementType = statementType;
        this.userId = userId;
        this.sortBy = "";
        this.orderStartDate = null;
        this.invoiceDate = invoiceDate;
        this.showPastTotals = showPastTotals;
    }
    public RequestStatements(StatementType statementType, Integer userId, Date invoiceDate, String sortBy, Boolean showPastTotals)
    {
        this.statementType = statementType;
        this.userId = userId;
        this.sortBy = sortBy;
        this.orderStartDate = null;
        this.invoiceDate = invoiceDate;
        this.showPastTotals = showPastTotals;
    }
    
    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestStatements fromXML(String xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Date getOrderStartDate() {
        return orderStartDate;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public Boolean getShowPastTotals()
    {
        return showPastTotals;
    }
}
