/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing;

import API.BaseRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class RequestUpdateOrderBillableStatus extends BaseRequest<RequestUpdateOrderBillableStatus>
{
    private final Set<Integer> orderIds = new HashSet<>();
    private final Integer userId;
    
    public RequestUpdateOrderBillableStatus(Integer userId)
    {
        this.userId = userId;
    }
    
    public RequestUpdateOrderBillableStatus(Integer userId, Integer orderId)
    {
        this.userId = userId;
        if (orderId != null)
        {
            this.orderIds.add(orderId);
        }
    }

    public RequestUpdateOrderBillableStatus(Integer userId, Collection<Integer> orderIds)
    {
        this.userId = userId;
        if (this.orderIds != null && this.orderIds.isEmpty() == false)
        {
            this.orderIds.addAll(orderIds);
        }
    }
    
    public void addOrderId(Integer userId, Integer orderId)
    {
        if (orderId != null)
        {
            orderIds.add(orderId);
        }
    }
    
    public void addOrderIds(Collection<Integer> orderIds)
    {
        if (orderIds != null && orderIds.isEmpty() == false)
        {
            orderIds.addAll(orderIds);
        }
    }
    
    public Collection getOrderIds()
    {
        return this.orderIds;
    }
    
    public boolean hasOrderIds()
    {
        return (this.orderIds.isEmpty() == false);
    }
    
    public Integer getUserId()
    {
        return this.userId;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RequestUpdateOrderBillableStatus fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
