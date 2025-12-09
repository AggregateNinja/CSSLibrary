
package BL.AdvancedOrders;

import BL.AdvancedOrderBL;
import DAOS.AdvancedOrderDAO;
import DAOS.EmployeesDAO;
import DAOS.PhlebotomyDAO;
import DAOS.RescheduleDAO;
import DOS.AdvancedOrders;
import DOS.Employees;
import DOS.Phlebotomy;
import DOS.Reschedule;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
//import org.openide.util.Exceptions;

/**
 * Represents the phlebotomy schedule for a given date range and optionally
 *   for different phlebotomists
 * @author TomR
 */
public class ScheduleManager implements IScheduleRegistry
{
    private DateTime _startDate;
    private DateTime _endDate;
    
    // The phlebotomist schedules we need to load (selected by the user)
    private final HashSet<Integer> _phlebotomistsToLoad; 
    
    // Each phlebotomist's schedule. Integer is EmployeeId of phlebotomist
    private TreeMap<Integer, PhlebotomistRangeSchedule> _phlebotomistSchedules;
    
    // The main registry for schedule objects, which map to calendar Actions
    // (the colored bars in the calendar that represent a phlebotomist's draws)
    // The integer here is the ID that is generated for the scheduleDate
    //  and used as a unique identifier by the calendar for the Action
    private TreeMap<Integer, ScheduleDate> _registry;
    
    // Keeps track of the highest unique ID for schedule objects. NOT thread-safe!
    private int _highestUsedId;
    
    private ArrayList<IScheduleListener> _listeners;
    
    /**
     * Handles the creation and management of phlebotomist schedules.
     *  The start and end date ranges define the time periods that the
     *  manager should consider.
     * @param startDate
     * @param endDate
     * @param phlebotomistIds
     * @param listeners 
     * @param patientId 
     * @param clientId 
     * @param doctorId 
     * @throws java.lang.Exception 
     */
    public ScheduleManager(
            DateTime startDate,
            DateTime endDate,
            ArrayList<IScheduleListener> listeners,
            HashSet<Integer> phlebotomistIds,
            Integer patientId,
            Integer clientId,
            Integer doctorId) throws Exception
    {
        _listeners = new ArrayList<>();
        if (listeners != null && listeners.size() > 0)
        {
            _listeners = listeners;
        }
        _registry = new TreeMap<>();
        _highestUsedId = 1;
        _phlebotomistsToLoad = phlebotomistIds;
        LoadSchedule(
                startDate,
                endDate,
                phlebotomistIds,
                patientId,
                clientId,
                doctorId
                );
    }
    
    /**
     * Provides callbacks for schedule events
     * @param listener 
     */
    public void AddScheduleListener(IScheduleListener listener)
    {
        if (listener != null)
        {
            _listeners.add(listener);
        }
    }
    
    /**
     * Given a unique schedule identifier, returns a ScheduleDate object
     *  which links a Phlebotomist + Date + Orders for that date.
     * @param id
     * @return 
     */
    @Override
    public ScheduleDate GetScheduleDateById(int id)
    {
        return _registry.get(id);
    }
    
    public void ClearListeners()
    {
        _listeners = new ArrayList<>();
    }
    
    public void RemoveScheduleListener(IScheduleListener listener)
    {
        if (_listeners.contains(listener))
        {
            _listeners.remove(listener);
        }
    }
    
    public void ClearScheduleData()
    {
        _registry.clear();
        _startDate = null;
        _endDate = null;
        _phlebotomistSchedules.clear();
        _highestUsedId = 1;
        System.gc();
    }
    
    public void LoadSchedule(
            DateTime calendarStartDate, 
            DateTime calendarEndDate,
            HashSet<Integer> phlebotomistIds,
            Integer patientId,
            Integer clientId,
            Integer doctorId) throws Exception
    {
        if (calendarStartDate == null || calendarEndDate == null || 
                calendarStartDate.isAfter(calendarEndDate.toInstant()))
        {
            throw new Exception("Received a null date range or a start date after the end range");
        }
        
        if (_phlebotomistSchedules == null) _phlebotomistSchedules = new TreeMap<>();
        else
        {
            _phlebotomistSchedules.clear();
            System.gc();
        }

        _startDate = calendarStartDate;
        _endDate = calendarEndDate;
        
        // If there are no passed-in phlebotomists, just don't load anything.
        if (phlebotomistIds == null || phlebotomistIds.isEmpty()) return;        
        
        AdvancedOrderBL aobl = new AdvancedOrderBL();
        
        // These are being returned ordered by phlebotomistId
        ArrayList<Phlebotomy> phlebotomyRows =
                aobl.GetPhlebotomyRowsInRange(
                        calendarStartDate.toDate(),
                        calendarEndDate.toDate(),
                        phlebotomistIds,
                        patientId,
                        clientId,
                        doctorId);
        
        // Think of this loop as: for each order
        // whose schedule overlaps the provided range:
        for (Phlebotomy p : phlebotomyRows)
        {
            GeneratePhlebotomyScheduleByPhlebotomy( p, calendarStartDate, calendarEndDate);
        }
        
        // Reschedules/reassigns are normally handled when the schedules
        // are being generated,but we need the handle the case where there are
        // no schedules for the given range, but something has been 
        // rescheduled/reassigned into the range                
        ArrayList<Reschedule> reschedules = 
                aobl.GetNewOrRescheduledIntoRange(
                        calendarStartDate.toDate(),
                        calendarEndDate.toDate(),
                        _phlebotomistsToLoad);
        
        for (Reschedule r : reschedules)
        {
            // We need to make a schedule for this phlebotomist
            if (!_phlebotomistSchedules.containsKey(r.getPhlebotomistId()))
            {
                GeneratePhlebotomyScheduleByReschedule(r, calendarStartDate, calendarEndDate);
            }
            // Else? Update?
        }
        
        ArrayList<Reschedule> reassigns =
                    aobl.GetReassignedTo(
                            calendarStartDate.toDate(),
                            calendarEndDate.toDate(),
                            _phlebotomistsToLoad);                

        for (Reschedule r : reassigns)
        {
            if (!_phlebotomistSchedules.containsKey(r.getReassignedToPhlebotomistId()))
            {
                GeneratePhlebotomyScheduleByReschedule(r, calendarStartDate, calendarEndDate);
            }
        }
        
        // Finalizes all loaded schedules (determines whether there are
        //   any rows outside if their normal schedules that should be
        //   included).
        PhlebotomistRangeSchedule pSchedule;
        for (Integer i : _phlebotomistSchedules.keySet())
        {
            pSchedule = _phlebotomistSchedules.get(i);
            pSchedule.FinalizeScheduleAndCleanUp();
        }
    }
    

    /**
     * If when making a phlebotomist's schedule it is determined that there
     *  is a reassigned order, this method is called so that the
     *  ScheduleManager can create or update that phlebotomist's schedule.
     * @param phlebotomistId
     * @param r 
     * @throws java.lang.Exception 
     */
    @Override
    public void CreateOrUpdatePhlebotomistRangeSchedule(int phlebotomistId, Reschedule r) throws Exception
    {
        // If the phlebotomist is not part of the schedules we're loading
        //   in the manager, ignore them.
        if (_phlebotomistsToLoad == null ||
                _phlebotomistsToLoad.isEmpty() ||
                !_phlebotomistsToLoad.contains(phlebotomistId)) return;        
        
        PhlebotomistRangeSchedule pSchedule;

        // See if the phlebotomist already exists in our map
        if (_phlebotomistSchedules.containsKey(phlebotomistId))
        {
            // We already have the phlebotomist; get the existing
            // schedule and add this order:
            pSchedule = _phlebotomistSchedules.get(phlebotomistId);
            pSchedule.AddScheduleForOrder(r);
        }
        else
        {
            // Create a schedule for the phlebotomist and add it to our map
            pSchedule = new PhlebotomistRangeSchedule(r, this, _startDate, _endDate);
            _phlebotomistSchedules.put(r.getReassignedToPhlebotomistId(), pSchedule);

            // Notify all of the listeners that there's a new phlebotomist
            //  in range:
            for (IScheduleListener listener : _listeners)
            {
                listener.NewCategory(pSchedule.GetPhlebotomistID(),pSchedule.GetDisplay());
            }
        }        
    }    
    
    /**
     * Builds a schedule from a reschedule object
     * 
     * If an order is not normally scheduled in the provided date range, but it has
     *  been rescheduled in, we will need to build the schedule using the
     *  reschedule object instead of the normal phlebotomy object
     * @param r
     * @param calendarStartDate
     * @param calendarEndDate 
     */
    private void GeneratePhlebotomyScheduleByReschedule(Reschedule r, DateTime calendarStartDate, DateTime calendarEndDate) throws Exception
    {
        Integer phlebotomistId;
        
        // Determine whether to create a schedule for the row this
        // is being reassigned to, or if it's not being reassigned, the original phleobotmist.
        if (r.getReassignedToPhlebotomistId() != null && r.getReassignedToPhlebotomistId() != 0)
        {
            phlebotomistId = r.getReassignedToPhlebotomistId();
        }
        else
        {
            phlebotomistId = r.getPhlebotomistId();
        }
        
        // If the phlebotomist is not part of the schedules we're loading
        //   in the manager, ignore it.
        if (_phlebotomistsToLoad == null ||
                _phlebotomistsToLoad.isEmpty() ||
                !_phlebotomistsToLoad.contains(phlebotomistId)) return;
        
        
        // Have to get the associated phlebotomy row here.        
        PhlebotomyDAO pdao = new PhlebotomyDAO();
        Phlebotomy p= null;

        p = pdao.GetPhlebotomy(r.getPhlebotomyId());

        PhlebotomistRangeSchedule pSchedule;
        // See if the phlebotomist already exists in our map
        if (_phlebotomistSchedules.containsKey(phlebotomistId))
        {
            // We already have the phlebotomist; get the existing
            // schedule and add this order:
            pSchedule = _phlebotomistSchedules.get(phlebotomistId);

            pSchedule.AddScheduleForOrder(p, calendarStartDate, calendarEndDate);
        }
        else
        {
            // Create a schedule for the phlebotomist and
            // add it to our map
            pSchedule = new PhlebotomistRangeSchedule(r, this, calendarStartDate, calendarEndDate);
            _phlebotomistSchedules.put(phlebotomistId, pSchedule);

            // Notify all of the listeners that there's a new phlebotomist
            //  in range:
            for (IScheduleListener listener : _listeners)
            {
                listener.NewCategory(pSchedule.GetPhlebotomistID(),pSchedule.GetDisplay());
            }
        }
    }
    
    /**
     * Generates the schedules for a phlebotomy row, which defines a schedule
     * @param p
     * @param calendarStartDate
     * @param calendarEndDate 
     */
    private void GeneratePhlebotomyScheduleByPhlebotomy(Phlebotomy p, DateTime calendarStartDate, DateTime calendarEndDate) throws Exception
    {
        // If the phlebotomist is not part of the schedules we're loading
        //   in the manager, ignore it.
        if (_phlebotomistsToLoad == null ||
                _phlebotomistsToLoad.isEmpty() ||
                !_phlebotomistsToLoad.contains(p.getPhlebotomist())) return;
        
        PhlebotomistRangeSchedule pSchedule;
        // See if the phlebotomist already exists in our map            
        if (_phlebotomistSchedules.containsKey(p.getPhlebotomist()))
        {
            // We already have the phlebotomist; get the existing
            // schedule and add this order:
            pSchedule = _phlebotomistSchedules.get(p.getPhlebotomist());
            pSchedule.AddScheduleForOrder(p, calendarStartDate, calendarEndDate);
        }
        else
        {
            // Create a schedule for the phlebotomist and
            // add it to our map
             pSchedule = new PhlebotomistRangeSchedule(p, this, calendarStartDate, calendarEndDate);
            _phlebotomistSchedules.put(p.getPhlebotomist(), pSchedule);

            // Notify all of the listeners that there's a new phlebotomist
            //  in range:
            for (IScheduleListener listener : _listeners)
            {
                listener.NewCategory(pSchedule.GetPhlebotomistID(),pSchedule.GetDisplay());
            }
        }
    }    

    /**
     * Provided an id (this maps to the calendar's Action IDs), retrieves
     * the PhlebotomistScheduleDate object which is used for the calendar
     * @param id
     * @return 
     */
    public ScheduleDate GetScheduledDateFromID(int id) throws Exception
    {
        try
        {
            return (ScheduleDate)_registry.get(id);
        }
        catch (Exception ex)
        {
            throw new Exception("Unable to cast registry object to ScheduledDate " + ex.getMessage());
        }
        
    }
    
    /**
     * Gets all of the schedule dates for the given schedule
     * @return 
     */
    public ArrayList<ScheduleDate> GetScheduledDates()
    {
        ArrayList<ScheduleDate> scheduleDates = new ArrayList<>();
        for (Map.Entry<Integer, ScheduleDate> entry : _registry.entrySet())
        {
            scheduleDates.add(entry.getValue());
        }
        return scheduleDates;
        //return _registry.values().toArray();
    }
    
    /**
     * Called from PhlebotomistScheduleManagers to get unique identifiers
     * for the date/phlebotomist/order combinations being generated. These
     *  are used to uniquely identify them as Actions in the calendar
     *  (they map to the calendar's Action Ids). They are not saved, 
     *  and are re-generated any time a new section of the calendar is shown.
     * 
     *  WARNING: this is NOT currently thread-safe!
     * @return 
     */
    @Override
    public int GetNextAvailableID()
    {
        _highestUsedId ++;
        return _highestUsedId;
    }

    @Override
    public boolean RegisterScheduleDate(int id, ScheduleDate sd)
    {
        // This will be used to register PhlebotomistScheduleDate objects
        //  with the calendar control.
        return (_registry.put(sd.GetID(), sd) != null);
    }
    
    /**
     * Returns the provided phlebotomist's schedule
     * @param phlebotomistId
     * @return 
     */
    public PhlebotomistRangeSchedule GetPhlebotomistSchedule(int phlebotomistId)
    {
        return (_phlebotomistSchedules.get(phlebotomistId));
    }
    
    /**
     * Determines whether any of the orders within the provided
     *  phlebotomist schedule are already ordered on the provided date. This
     *  is used to prevent rescheduling an order to a date where it's already
     *  scheduled.
     * 
     *  Returns a string->arraylist of Advanced Orders where String 
     *    is the phlebotomist's name
     *   
     *  Returns
     * @param scheduleDate
     * @return 
     */
    public HashMap<String, ArrayList<AdvancedOrders>> GetExistingOrdersForDate(
            PhlebotomistScheduleDate psd,
            LocalDate newDate) throws Exception
    {
        
        HashMap<String, ArrayList<AdvancedOrders>> 
                results = new HashMap<>();
        
        // Create a hashset for quick lookups
        HashSet<Integer> orderIds = new HashSet<>();
        ArrayList<Phlebotomy> phlebotomies = psd.GetPhlebotomyRows();
        for (Phlebotomy p : phlebotomies)
        {
            orderIds.add(p.getIdAdvancedOrder());            
        }
        
        EmployeesDAO edao = new EmployeesDAO();
        AdvancedOrderDAO aodao = new AdvancedOrderDAO();
        String phlebotomistName;
        ArrayList<AdvancedOrders> drawsOnDate;
        try
        {
            // Get all of the orders currently scheduled for the check date:
            AdvancedOrderBL aobl = new AdvancedOrderBL();
            HashMap<Integer, ArrayList<Integer>> phlebotomistOrders = aobl.GetDaysOrders(newDate.toDate());
            for (Integer phlebotomistId : phlebotomistOrders.keySet())
            {
                Employees phlebotomist = edao.GetEmployeeByID(phlebotomistId);
                phlebotomistName = phlebotomist.getLastName() + ", " + phlebotomist.getFirstName();
                drawsOnDate = new ArrayList<>();
                
                ArrayList<Integer> existingOrderIds = phlebotomistOrders.get(phlebotomistId);
                for (Integer existingOrderId : existingOrderIds)
                {
                    // If one of the orders that's being rescheduled already
                    // exists for this date:                    
                    if (orderIds.contains(existingOrderId))
                    {
                        AdvancedOrders ao = aodao.GetAdvancedOrder(existingOrderId);
                        drawsOnDate.add(ao);
                    }
                }
                
                // Only add if there's a matching order
                if (!drawsOnDate.isEmpty())
                {
                    results.put(phlebotomistName, drawsOnDate);
                }                
            }            
        }
        catch (SQLException ex)
        {
            System.out.println("ScheduleManager::GetExistingOrdersForDate : " +
                    "Unable to retrieve existing orders for date " + newDate.toString());
            return null;
        }
        return results;
    }
    
    /**
     *  Called when the user drags and drops a phlebotomist's schedule
     *   to another part of the calendar.
     *  New phlebotomist Id is optional (if supplied, this is also a reassignment)
     * @param id
     * @param newDate
     * @param newPhlebotomistID
     * @return 
     */
    public boolean Reschedule(List<AdvancedOrders> advancedOrders, ScheduleDate sd, DateTime newDate, Integer newPhlebotomistID, int userId, HashSet<Integer> excludeAdvancedOrderIds) throws Exception
    {
        if (excludeAdvancedOrderIds == null) excludeAdvancedOrderIds = new HashSet<>();
        
        PhlebotomistScheduleDate psd = (PhlebotomistScheduleDate)sd;
        
        List<Phlebotomy> phlebotomies = new ArrayList<Phlebotomy>();
        
        if (advancedOrders != null)
        {
            PhlebotomyDAO pdao = new PhlebotomyDAO();
            phlebotomies = new ArrayList<Phlebotomy>();
            for (AdvancedOrders ao : advancedOrders)
            {
                phlebotomies.add(pdao.GetPhlebotomyByAdvancedOrderId(ao.getIdAdvancedOrders()));
            }
        }
        else
        {
            phlebotomies  = psd.GetPhlebotomyRows();
        }

        for (Phlebotomy p : phlebotomies)
        {
            // Ignore if the advanced order was supplied in the list to exclude
            if (excludeAdvancedOrderIds.contains(p.getIdAdvancedOrder()))
            {
                continue;
            }
            
            Reschedule r = psd.GetReschedule(p.getIdPhlebotomy());
            
            // TODO: set reschedule comment here once the comment dialog is implemented
            
            // This isn't already a reschedule
            if (r == null)
            {
                r = new Reschedule();
                r.setPhlebotomyId(p.getIdPhlebotomy());
                r.setAdvancedOrderId(p.getIdAdvancedOrder());
                r.setPhlebotomistId(p.getPhlebotomist());
                if (newDate != null)
                {
                    r.setRescheduleDate(newDate.toDate());
                }                
                r.setOriginalDate(sd.GetHashDate().toDate());
                
                // This isn't also a re-assign
                if (newPhlebotomistID != null)
                {
                   r.setReassignedToPhlebotomistId(newPhlebotomistID);
                }
             
                r.setRescheduledOn(new Date());
                r.setRescheduledBy(userId);                
            }
            else // already have a reschedule row; just update it
            {
                LocalDate originalLocalDate = new LocalDate(r.getOriginalDate());
                LocalDate newLocal = new LocalDate(newDate);
                
                // If the original date matches the reschedule date, it's
                // not a reschedule and the reschedule date should be null.
                if (originalLocalDate.equals(newLocal))
                {
                    r.setRescheduleDate(null);
                }
                else
                {
                    if (newDate != null)
                    {
                        r.setRescheduleDate(newDate.toDate());
                    }                    
                }
                
                // If this is also being reassigned, set that:
                if (newPhlebotomistID != null)
                {
                    // If it's being "reassigned" to the same person,
                    // it's not being reassigned and should be null.
                    if (r.getPhlebotomistId().equals(newPhlebotomistID))
                    {
                        r.setReassignedToPhlebotomistId(null);
                    }
                    else
                    {
                        r.setReassignedToPhlebotomistId(newPhlebotomistID);                        
                    }
                }
                r.setRescheduledOn(new Date());
                r.setRescheduledBy(userId);
                
                // If the user is setting the schedule back to the way it
                //  originally was, get rid of the reschedule row altogether
                //  (it would cause problems and slows down the app to process these)
                if (r.IsReassigned()== false &&
                        r.IsRescheduled() == false)
                {
                    RemoveReschedule(r);
                    continue;
                }
            }
            
            if (!InsertOrUpdateReschedule(r))
            {
                String rescheduleIdStr = "[null]";
                if (r.getIdReschedules() != null && r.getIdReschedules() > 0)
                {
                    rescheduleIdStr = r.getIdReschedules().toString();
                }
                throw new Exception("Unable to insert or update reschedule Id: " + rescheduleIdStr);
            }
        }
        return true;
    }
    
    /**
     * Deletes a reschedule row. Called if a user is rescheduling something
     *  back to where it originally was.
     * @param reschedule
     * @return 
     */
    private boolean RemoveReschedule(Reschedule reschedule)
    {
        if (reschedule == null) return false;
        RescheduleDAO rescheduledao = new RescheduleDAO();
        if (rescheduledao.DeleteRescheduleById(reschedule.getIdReschedules()))
        {
            return true;
        }
        else
        {
            System.out.println("ScheduleManager::RemoveReschedule: Unable to delete " +
                    "reschedule for idReschedules " + reschedule.getIdReschedules());
            return false;
        }
    }
    
    private boolean InsertOrUpdateReschedule(Reschedule reschedule) throws Exception
    {
        if (reschedule == null) return false;

        RescheduleDAO rescheduledao = new RescheduleDAO();
        
            // Update existing
            if (reschedule.getIdReschedules() != null)
            {
                rescheduledao.UpdateReschedule(reschedule);
            }
            else // Insert new
            {
                rescheduledao.InsertReschedule(reschedule);
            }                
        
        return true;
    }
}
