package BL.AdvancedOrders;

import DOS.Reschedule;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 *
 * @author TomR
 */
public abstract class ScheduleDate
{
    protected final int _id;
    protected final DateTime _date;
    private final Type _type;    
    protected final Reschedule _reschedule;
    protected int _drawCount;
    private boolean _rescheduled = false;
    private boolean _new = false;
    private boolean _canceled = false;
    private boolean _reassigned = false;
    
    // Currently only using phlebotomist view
    public enum Type
    {
        Phlebotomist,
        Client
    }
    
    public ScheduleDate(IScheduleRegistry registry,
            DateTime date,
            Reschedule reschedule,
            int drawCount,
            Type type)
    {
        _id = registry.GetNextAvailableID();
        _type = type;
        _date = date;
        _reschedule = reschedule;
        //SetRescheduleFlags();
    }
    
    public abstract String GetDisplay();
    public abstract int GetCategoryID();
    
    /*
    public void SetRescheduleFlags()
    {
        if (_reschedule != null)
        {
            Date originalDate = _reschedule.getOriginalDate();
            Date rescheduleDate = _reschedule.getRescheduledOn();
            Integer reassignedToPhlebotomistId = _reschedule.getReassignedToPhlebotomistId();
            
            if (reassignedToPhlebotomistId != null) _reassigned = true;            
            if (originalDate != null && rescheduleDate != null) _rescheduled = true;            
            if (originalDate == null && rescheduleDate != null) _new = true;            
            if (originalDate != null && rescheduleDate == null) _canceled = true;            
        }        
    }*/
    /*
    public boolean IsRescheduled()
    {
        return _rescheduled;
    }
    
    public boolean IsNew()
    {
        return _new;
    }
    
    public boolean IsCanceled()
    {
        return _canceled;
    }
    
    public boolean IsReassigned()
    {
        return _reassigned;
    }*/
    
    public int GetID()
    {
        return _id;
    }
    
    public DateTime GetDate()
    {
        return _date;
    }
    
    public LocalDate GetHashDate()
    {
        return new LocalDate(_date);
    }
    
    public int GetDrawCount()
    {
        return _drawCount;
    }
    /*
    public Reschedule GetReschedule()
    {
        return _reschedule;
    }*/
}
