
package BL.AdvancedOrders;

/**
 * Used to call-back to the calendar when data changes
 * @author TomR
 */
public interface IScheduleListener
{
    public boolean NewCategory(int categoryId, String display);
    
    // Called back if a component needs the UI to refresh
    public void RequiresRefresh();
}
