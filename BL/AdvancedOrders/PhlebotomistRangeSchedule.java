package BL.AdvancedOrders;

import BL.AdvancedOrderBL;
import DAOS.EmployeesDAO;
import DAOS.PhlebotomyDAO;
import DOS.Employees;
import DOS.Phlebotomy;
import DOS.Reschedule;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
//import org.openide.util.Exceptions;

/**
 *  Represents a single phlebotomist's schedule over a specified range of dates
 * @author TomR
 */
public class PhlebotomistRangeSchedule
{
    // Reschedule data concerning this phlebotomist and this schedule range:
    HashMap<LocalDate, Reschedule> _canceledOrRescheduledOut;
    HashMap<LocalDate, ArrayList<Reschedule>> _rescheduledWithinRange;
    ArrayList<Reschedule> _newOrRescheduledIn; // Can just be appeneded
    ArrayList<Reschedule> _reassignedTo; // Can just be appended
    HashMap<LocalDate,ArrayList<Reschedule>> _reassignedFrom;
    
    // Mapping of a date to the ScheduleDate ID. Important:
    private TreeMap<LocalDate, Integer> _dateToScheduleId;
    
    private Employees _phlebotomist;
    private final IScheduleRegistry _registry;
    private int _drawCount = 0;
    
    /**
     * Creates a schedule based on a phlebotomy row, which contains the
     *  schedule definition.
     * @param phlebotomy
     * @param registry
     * @param calendarStartDate
     * @param calendarEndDate 
     */
    public PhlebotomistRangeSchedule(Phlebotomy phlebotomy, IScheduleRegistry registry, DateTime calendarStartDate, DateTime calendarEndDate) throws Exception
    {
        if (phlebotomy == null || registry == null) throw new Exception("Passed null phlebotomy or registry object");
        _registry = registry;
        ClearScheduleData(); // Initializes local members
        LoadPhlebotomist(phlebotomy.getPhlebotomist());
        
        // Load all reschedule data for this timeframe/phlebotomist:
        LoadAllRescheduleDataForPhlebotomist(calendarStartDate, calendarEndDate);
        
        AddScheduleForOrder(phlebotomy, calendarStartDate, calendarEndDate);        
    }
    
    /**
     * Creates a schedule based on a Reschedule object. This is called when a
     *  phlebotomist wasn't normally scheduled in the given range, but because
     *  of a reschedule or a reassignment, they are now part of the range.
     * 
     *  We needed the ability to create a schedule even though there isn't
     *   a phlebotomy row to work from.
     * @param reschedule
     * @param registry
     * @param calendarStartDate
     * @param calendarEndDate 
     */
    public PhlebotomistRangeSchedule(Reschedule reschedule, IScheduleRegistry registry, DateTime calendarStartDate, DateTime calendarEndDate) throws Exception
    {
        // For when a phlebotomist's only draws come from reschedules.
        _registry = registry;
        ClearScheduleData();
        if (reschedule.getReassignedToPhlebotomistId() == null)
        {
            LoadPhlebotomist(reschedule.getPhlebotomistId());
        }
        else
        {
            LoadPhlebotomist(reschedule.getReassignedToPhlebotomistId());
        }
        
        // Load all reschedule data for this timeframe/phlebotomist:
        LoadAllRescheduleDataForPhlebotomist(calendarStartDate, calendarEndDate);        
        
        AddScheduleForOrder(reschedule);
        
    }
    
    /**
     * Loads any deviations from the normal schedule (reschedules/reassignments)
     *  for the phlebotomist in the provided range. These are considered when
     *  building this phlebotomist's schedule.
     * @param calendarStartDate
     * @param calendarEndDate 
     */
    private void LoadAllRescheduleDataForPhlebotomist(DateTime calendarStartDate, DateTime calendarEndDate)
    {
        // There are 4 different classes of things we neeed to consider with
        // regard to reschedule objects:
        
        // 1) Draws that have been rescheduled out of the date range:
        //      These will not be shown in the calendar
        // 2) Draws that have been rescheduled within the date range
        //      These will appear in the calendar moved to a different spot
        // 3) Draws that have been rescheduled into the date range
        //      These will appear in the calendar
        // 4) Draws that have been reassigned to this phlebotomist
        //      These will appear in the calendar
        
        AdvancedOrderBL aobl = new AdvancedOrderBL();
        
        // Get dates that were created by the dynamic schedule but were canceled,
        //  OR dates that were scheduled but have been rescheduled out of the range.
        //  These are days that shouldn't show on the calendar
        HashMap<LocalDate, Reschedule> canceledOrRescheduledOut =
                aobl.GetCanceledOrRescheduledOutOfRange(
                        calendarStartDate.toDate(),//startDate,
                        calendarEndDate.toDate(),//endDate,
                        _phlebotomist.getIdemployees(), null);
        if (canceledOrRescheduledOut == null) canceledOrRescheduledOut = new HashMap<>();
        _canceledOrRescheduledOut = canceledOrRescheduledOut;
        
        // Get any reschedule where the date being rescheduled is within the range
        // and the date it's being rescheduled to is within the range
        // These are dates that need to be moved from one spot to another for this phlebotomist
        HashMap<LocalDate, ArrayList<Reschedule>> rescheduledWithinRange = aobl.GetOrderReschedulesWithinRange(
                        calendarStartDate.toDate(),
                        calendarEndDate.toDate(),
                        _phlebotomist.getIdemployees(), null);        
        if (rescheduledWithinRange == null) rescheduledWithinRange = new HashMap<>();
        _rescheduledWithinRange = rescheduledWithinRange;
        
        // Get dates that were created within the calendar (not part of the generated schedule)
        //  OR dates that were scheduled outside of the range but rescheduled in.
        ArrayList<Reschedule> newOrRescheduledIn =
                aobl.GetNewOrRescheduledIntoRange(
                        calendarStartDate.toDate(),//startDate,
                        calendarEndDate.toDate(), //)endDate,
                        _phlebotomist.getIdemployees(), null);            
        if (newOrRescheduledIn == null) newOrRescheduledIn = new ArrayList<>();
        _newOrRescheduledIn = newOrRescheduledIn;
        
        // Now check to see if anyone else's draws have been reassigned to
        //  this phlebotomist
       ArrayList<Reschedule> reassignedTo = 
                aobl.GetReassignedTo(calendarStartDate.toDate(),
                    calendarEndDate.toDate(),
                    _phlebotomist.getIdemployees());                
        if (reassignedTo == null) reassignedTo = new ArrayList<>();
        _reassignedTo = reassignedTo;
        
        
        HashMap<LocalDate,ArrayList<Reschedule>> reassignedFrom =
                aobl.GetReassignedFrom(calendarStartDate.toDate(),
                        calendarEndDate.toDate(),
                        _phlebotomist.getIdemployees());
        if (reassignedFrom == null) reassignedFrom = new HashMap<>();
        _reassignedFrom = reassignedFrom;
    }
    
    /**
     * Gets the phlebotomist's data and saves to the local member
     * @param idEmployees 
     */
    private void LoadPhlebotomist(int idEmployees) throws Exception
    {
        EmployeesDAO employeedao = new EmployeesDAO();

        Employees phlebotomist = employeedao.GetEmployeeByID(idEmployees);
        if (phlebotomist != null)
        {
            _phlebotomist = phlebotomist;
        }

    }
    
    /**
     * If this phlebotomist has schedules reassigned to them, add the data
     *   based on the reschedule rows.
     * @param reschedule 
     */
    public void AddScheduleForOrder(Reschedule reschedule) throws Exception
    {
        PhlebotomistScheduleDate scheduleDate;
        PhlebotomyDAO phlebotomydao = new PhlebotomyDAO();
        LocalDate hashDate;
        try
        {
            Phlebotomy phlebotomy = phlebotomydao.GetPhlebotomy(reschedule.getPhlebotomyId());
            
            DateTime dateTime;
            if (reschedule.getRescheduleDate() != null)
            {
                // If it's rescheduled, use that date instead
                hashDate = new LocalDate(reschedule.getRescheduleDate());
            }
            else
            {
                hashDate = new LocalDate(reschedule.getOriginalDate());
            }
            
            // If we don't have a schedule for this date, create a new one
            Integer existingId = _dateToScheduleId.get(hashDate);
            if (existingId == null) // Create new
            {                
                // Add it to the registry
                scheduleDate = new PhlebotomistScheduleDate(
                    _registry, hashDate.toDateTimeAtStartOfDay(), _phlebotomist, phlebotomy, reschedule, 1);
                _registry.RegisterScheduleDate(scheduleDate.GetID(), scheduleDate);

                _dateToScheduleId.put(hashDate, scheduleDate.GetID());
            }
            else
            {
                // There's already a schedule for this date. Append the new order
                ScheduleDate sd = _registry.GetScheduleDateById(existingId);
                if (sd == null)
                {
                    throw new Exception("Phlebotomist range schedule has action id that cannot be found in the schedule manager registry. HashDate: " + hashDate.toString());
                }
                PhlebotomistScheduleDate psd = (PhlebotomistScheduleDate)sd;
                psd.AddDraw(phlebotomy, reschedule);
            }
            _drawCount ++;
        }
        catch (SQLException ex)
        {
            String phlebotomyIdStr = "[null]";
            if (reschedule != null && reschedule.getPhlebotomyId() != null && reschedule.getPhlebotomyId() > 0)
            {
                phlebotomyIdStr = reschedule.getPhlebotomyId().toString();
            }
            throw new Exception("SQL error while retrieving phlebotomy schedule. Phlebotomy Id: " + phlebotomyIdStr);
        }        
    }
    
    /**
     * Takes an order (in the form of a phlebotomy row) and generates
     *  this phlebotomis's schedule for the given date range, taking into consideration
     *  things like reschedules. 
     * @param phlebotomy
     * @param calendarRangeStartDate
     * @param calendarRangeEndDate
     */
    public final void AddScheduleForOrder(Phlebotomy phlebotomy,
            DateTime calendarRangeStartDate, DateTime calendarRangeEndDate) throws Exception
    {
        AdvancedOrderBL aobl = new AdvancedOrderBL();
        Date startDate = phlebotomy.getStartDate();
        Date endDate = phlebotomy.getEndDate();
        //Integer orderId = phlebotomy.getIdOrders();
        Integer clientId = null;
        
        int drawCount = 0;

        // Dynamically generates the order dates based on the schedule criteria:
        ArrayList<Date> dates =
                aobl.GetOrderDates(phlebotomy,
                        calendarRangeStartDate,
                        calendarRangeEndDate);
        
        if (dates == null) dates = new ArrayList<>();
        
        Reschedule reschedule = null;
        PhlebotomistScheduleDate scheduleDate = null;
        // For each date in the order's dynamically-generated schedule
        for (Date date : dates)
        {                
            DateTime dateTime = new DateTime(date);
            // Strip out the time and make it immutable for the hash
            LocalDate hashDate = new LocalDate(dateTime);

            // If this date was originally scheduled but was canceled or
            //  moved out of the range we care about, ignore it.
            reschedule = _canceledOrRescheduledOut.get(hashDate);
            if (reschedule != null)
            {
                // Note: Not currently doing anything with these
                //  They aren't being shown.             
            }
            else // Otherwise, we need to add the row to our data
            {
                // See if this draw was reassigned to someone else:
                ArrayList<Reschedule> reschedules = _reassignedFrom.get(hashDate);
                boolean reassigned = false;
                if (reschedules != null)
                {
                    for (Reschedule r : reschedules)
                    {
                        // Create the schedule for the person it was reassigned to.
                        if (r.getPhlebotomyId().equals(phlebotomy.getIdPhlebotomy()))
                        {
                            CreateReassignedSchedule(r.getReassignedToPhlebotomistId(), r);
                            reassigned = true;
                        }

                    }
                    // If so, don't add them to our data.                    
                    if (reassigned)
                    {
                        continue;
                    }
                }
                
                // See if there is a reschedule for the generated date.
                // (reschedules always trump the generated schedule)
                reschedules = _rescheduledWithinRange.get(hashDate);
                if (reschedules != null)
                {
                    for (Reschedule r : reschedules)
                    {
                        if (r.getPhlebotomyId().equals(phlebotomy.getIdPhlebotomy()))
                        {
                            reschedule = r;
                            break;
                        }
                    }
                    // Change the originally scheduled date to the rescheduled date
                    if (reschedule != null)
                    {
                        hashDate = new LocalDate(reschedule.getRescheduleDate());    
                    }
                }

                // Done handling reschedules for this specific date.
                //  Now add this data to the calendar via a PhlebotomistScheduleDate object
                //  which holds the order information for a specific phlebotomist/date combo.

                // If we don't have a schedule for this date, create a new one
                Integer existingId = _dateToScheduleId.get(hashDate);
                if (existingId == null) // Create new
                {
                    if (reschedule != null) dateTime = hashDate.toDateTimeAtStartOfDay();
                    // Add it to the registry
                    scheduleDate = new PhlebotomistScheduleDate(
                        _registry, dateTime, _phlebotomist, phlebotomy, reschedule, 1);
                    _registry.RegisterScheduleDate(scheduleDate.GetID(), scheduleDate);

                    _dateToScheduleId.put(hashDate, scheduleDate.GetID());
                }
                else
                {
                    // There's already a schedule for this date. Append the new order
                    ScheduleDate sd = _registry.GetScheduleDateById(existingId);
                    if (sd == null)
                    {
                        throw new Exception("The phlebotomist range schedule has"
                                + " an action id that is not registered with the"
                                + " schedule manager registry!");
                    }
                    PhlebotomistScheduleDate psd = (PhlebotomistScheduleDate)sd;
                    psd.AddDraw(phlebotomy, reschedule);
                }
                _drawCount ++;
            }
        }
    
    }
    
    /**
     * Builds a phlebotomist's schedule for an order that has been
     *  reassigned to them.
     * @param phlebotomistId
     * @param r 
     */
    public void CreateReassignedSchedule(int phlebotomistId, Reschedule r) throws Exception
    {
        // Call back to the manager to create a new schedule for the other phlebotomist
        // or append the orders to the existing one.
        try
        {
            _registry.CreateOrUpdatePhlebotomistRangeSchedule(phlebotomistId,r);
        }
        catch (Exception ex)
        {
            String phlebotmistIdStr = "[null]";
            String rescheduleIdStr = "[null]";
            if (phlebotomistId > 0)
            {
                phlebotmistIdStr = String.valueOf(phlebotomistId);
            }
            
            if (r != null && r.getIdReschedules() != null && r.getIdReschedules() > 0)
            {
                rescheduleIdStr = r.getIdReschedules().toString();
            }
            throw new Exception("Unable to create or update schedule for reassign. PhlebotomistId= " + phlebotmistIdStr + ", rescheduleId= " + rescheduleIdStr);
        }
    }
    
    /**
     * After the normal schedule has been generated, take care of
     *  rows rescheduled in or reassigned to this phlebotomist.
     * @throws java.sql.SQLException
     */
    public void FinalizeScheduleAndCleanUp() throws SQLException
    {
        // Now just add the new rows to the schedule and the re-assigned rows
        PhlebotomistScheduleDate psd;
        PhlebotomyDAO phlebotomydao = new PhlebotomyDAO();
        Phlebotomy p;
        
        // Handle rows that are outside of the range of the current calendar
        //  view but were rescheduled into the visible range:

        for (Reschedule r : _newOrRescheduledIn)
        {
            p = phlebotomydao.GetPhlebotomy(r.getPhlebotomyId());
            DateTime newDate = new DateTime(r.getRescheduleDate());

            // Need to determine whether this date already has a PhlebotomistScheduleDate
            Integer existingId = _dateToScheduleId.get(new LocalDate(newDate));

            if (existingId != null)
            {
                // Append schedule
                psd = (PhlebotomistScheduleDate)_registry.GetScheduleDateById(existingId);
                psd.AddDraw(p, r); 
            }
            else
            {
                // Add new to the registry
                psd = new PhlebotomistScheduleDate(
                    _registry, newDate, _phlebotomist, p, r, 1);
                _registry.RegisterScheduleDate(psd.GetID(), psd);
                _dateToScheduleId.put(new LocalDate(newDate), psd.GetID());
                _drawCount ++;                    
            }
        }                        

        
        // Handle rows that have been reassigned to this phlebotomist within
        //  the viewable calendar range:
        for (Reschedule r : _reassignedTo)
        {
            p = phlebotomydao.GetPhlebotomy(r.getPhlebotomyId());

            LocalDate scheduleDateHash;
            DateTime scheduleDateTime;
            if (r.getRescheduleDate() != null)
            {
                scheduleDateHash = new LocalDate(r.getRescheduleDate());
                scheduleDateTime = new DateTime(r.getRescheduleDate());
            }
            else
            {
                scheduleDateHash = new LocalDate(r.getOriginalDate());
                scheduleDateTime = new DateTime(r.getOriginalDate());
            }

            // Need to determine whether this date already has a PhlebotomistScheduleDate
            Integer existingId = _dateToScheduleId.get(scheduleDateHash);

            if (existingId != null)
            {
                // Append schedule
                psd = (PhlebotomistScheduleDate)_registry.GetScheduleDateById(existingId);
                psd.AddDraw(p, r);

            }
            else
            {
                // Add new to the registry
                psd = new PhlebotomistScheduleDate(
                    _registry, scheduleDateTime, _phlebotomist, p, r, 1);
                _registry.RegisterScheduleDate(psd.GetID(), psd);
                _dateToScheduleId.put(scheduleDateHash, psd.GetID());
                _drawCount ++;
            }
        }
    }
    
    /**
     * Wipes out any data for this phlebotomist's schedule
     */
    public final void ClearScheduleData()
    {  
        _dateToScheduleId = new TreeMap<>();
        _drawCount = 0; 
    }
    
    public Employees GetPhlebotomist()
    {
        return _phlebotomist;
    }
    
    public int GetPhlebotomistID()
    {
        return _phlebotomist.getIdemployees();
    }
    
    /**
     * Phlebotomist last name & first name
     * @return 
     */
    public String GetDisplay()
    {
        return _phlebotomist.getLastName() + ", " + _phlebotomist.getFirstName();
    }
    
}