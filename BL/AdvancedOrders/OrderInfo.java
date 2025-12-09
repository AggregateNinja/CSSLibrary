
package BL.AdvancedOrders;

import DOS.AdvancedOrders;
import DOS.Orders;
import DOS.Patients;

/**
 *
 * @author TomR
 */
public class OrderInfo implements Comparable
{
    Orders _order;
    AdvancedOrders _advancedOrder;
    Patients _patient;
    String _rescheduleDisplay;
    
    public OrderInfo(AdvancedOrders advancedOrder, Patients patient, String rescheduleDisplay, Orders order)
    {
        _order = order;
        _advancedOrder = advancedOrder;
        _patient = patient;
        if (rescheduleDisplay == null)
        {
            _rescheduleDisplay = "";
        }
        else
        {
            _rescheduleDisplay = rescheduleDisplay;
        }
    }
    
    @Override
    public int compareTo(Object otherObj)
    {
        OrderInfo other = (OrderInfo)otherObj;
        
        return GetDisplay().compareTo(other.GetDisplay());
    }
    
    public AdvancedOrders GetAdvancedOrder()
    {
        return _advancedOrder;
    }
    
    public Patients GetPatient()
    {
        return _patient;
    }
    
    public Orders GetOrder()
    {
        return _order;
    }
    
    /**
     * Order display. Also used for sorting.
     * @return String display representing the order information
     */
    public String GetDisplay()
    {
        if (_order == null)
        {
            return _patient.getLastName() + ", " + _patient.getFirstName() + " " + _rescheduleDisplay;
        }
        else
        {
            return _order.getAccession() + ": " + _patient.getLastName() + ", " + _patient.getFirstName() + " " + _rescheduleDisplay;
        }
    }    
}
