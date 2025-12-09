/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import API.BaseResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.css.rmi.common.PrintObject;

/**
 *
 * @author Rob
 */
public abstract class ResponseStatements extends BaseResponse<ResponseStatements>
{
    private static final long serialVersionUID = 42L;
    Map<Object, List<PrintObject>> printObjectMap = new TreeMap<Object, List<PrintObject>>();
    Map<Object, BigDecimal> balanceMap = new TreeMap<Object, BigDecimal>();
    private Date invoiceDate = null;
    Map<Object, Integer> invoiceNumberMap = new TreeMap<>();
    
    
    public void setInvoiceDate(Date invoiceDate)
    {
        this.invoiceDate = invoiceDate;
    }
    
    public Date getInvoiceDate()
    {
        return this.invoiceDate;
    }

    public Map<Object, BigDecimal> getBalanceMap() {
        return balanceMap;
    }

    public void setBalanceMap(Map<Object, BigDecimal> balanceMap) {
        this.balanceMap = balanceMap;
    }
    
    public void addPrintObject(Object identifier, PrintObject printObject)
    {
        List<PrintObject> printObjectList = printObjectMap.get(identifier);
        if (printObjectList == null) printObjectList = new ArrayList<PrintObject>();
        printObjectList.add(printObject);
        printObjectMap.put(identifier, printObjectList);
    }
    
    public Map<Object, List<PrintObject>> getPrintObjectMap()
    {
        return printObjectMap;
    }
    
    public void addInvoiceNumber(Object identifier, Integer invoiceNumber)
    {
        invoiceNumberMap.put(identifier, invoiceNumber);
    }
    
    public Map<Object, Integer> getInvoiceMap()
    {
        return invoiceNumberMap;
    }
    
    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseStatements fromXML(String xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
