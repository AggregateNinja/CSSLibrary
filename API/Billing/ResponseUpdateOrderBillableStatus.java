/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package API.Billing;

import API.BaseResponse;
import Utility.Pair;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class ResponseUpdateOrderBillableStatus extends BaseResponse<ResponseUpdateOrderBillableStatus>
{
    // A list of (orderId --> unbillable reason) pairs
    // If an order Id is associated with an empty collection,
    // it means the order was billalbe
    Collection<Pair<Integer, Collection<String>>> results = new LinkedList<>();
    
    // Any orderIds that were not able to have their billable status updated.
    Collection<Integer> errors = new LinkedList<>();
    
    public ResponseUpdateOrderBillableStatus()
    {}
    
    public void addOutcome(Integer orderId, Collection<String> unbillableReasons)
    {
        this.results.add(new Pair(orderId, unbillableReasons));
    }
    
    /**
     * Adds to the list of order Ids that could not have their billable
     *  status updated due to an error.
     * @param orderId 
     */
    public void addError(Integer orderId)
    {
        this.errors.add(orderId);
    }
    
    /**
     * Returns a collection of (orderId --> unbillable reason(s)) pairs.
     * If an orderId is associated with an empty collection, it was made billable
     * @return 
     */
    public Collection<Pair<Integer, Collection<String>>> getUpdateResults()
    {
        return this.results;
    }
    
    public boolean anyErrors()
    {
        return (errors.isEmpty() == false);
    }
    
    public Collection<Integer> getErrorOrderIds()
    {
        return this.errors;
    }
    
    @Override
    public String toXML()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ResponseUpdateOrderBillableStatus fromXML(String xml)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
