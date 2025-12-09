/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package API.Billing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rob
 */
public class RequestHCFAs extends RequestStatements
{
    private static final long serialVersionUID = 42L;
    private final List<Integer> detailOrderIdList = new ArrayList<Integer>();

    public List<Integer> getDetailOrderIdList() {
        return detailOrderIdList;
    }
    
    public void addDetailOrderId(Integer detailOrderId) {
        this.detailOrderIdList.add(detailOrderId);
    }
    
    public void addAllDetailOrderIds(List<Integer> detailOrderIdList)
    {
        this.detailOrderIdList.addAll(detailOrderIdList);
    }
    
    public RequestHCFAs(Integer userId, List<Integer> detailOrderIdList)
    {
        super(StatementType.HCFA, userId);
        this.detailOrderIdList.addAll(detailOrderIdList);
    }
    
    public RequestHCFAs(Integer userId, List<Integer> detailOrderIdList, Date invoiceDate)
    {
        super(StatementType.HCFA, userId, invoiceDate);
        this.detailOrderIdList.addAll(detailOrderIdList);
    }
    
    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestHCFAs fromXML(String xml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
