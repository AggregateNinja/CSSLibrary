/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BL.AdvancedOrders;

import DOS.Employees;
import DOS.Phlebotomy;
import DOS.Reschedule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
import org.joda.time.DateTime;

/**
 *
 * @author TomR
 */
public class PhlebotomistScheduleDate extends ScheduleDate
{
    private final Employees _phlebotomist;
    ArrayList<Phlebotomy> _phlebotomies;
    
    // Only have one instance of a specific draw per day
    HashSet<Integer> _phlebotomyIdHash;
    
    // PhlebotomyId --> Reschedule
    TreeMap<Integer, Reschedule> _reschedules;
    
    /**
     * Represents a date / phlebotomist combination and all of the
     *  associated phlebotomy rows and reschedules.
     * @param registry
     * @param date
     * @param phlebotomist
     * @param drawCount 
     */
    public PhlebotomistScheduleDate(IScheduleRegistry registry,
            DateTime date,
            Employees phlebotomist,
            //int idPhlebotomy, // Used to insert new reschedule rows
            Phlebotomy phlebotomy,
            Reschedule reschedule,
            int drawCount            
            )
    {
        super(registry, date, reschedule, drawCount, Type.Phlebotomist);
        _phlebotomyIdHash = new HashSet<>();
        _phlebotomyIdHash.add(phlebotomy.getIdPhlebotomy());
        _phlebotomies = new ArrayList<>();
        _phlebotomies.add(phlebotomy);
        _reschedules = new TreeMap<>();
        if (reschedule != null)
        {
            _reschedules.put(phlebotomy.getIdPhlebotomy(), reschedule);
        }
        _phlebotomist = phlebotomist;        
    }

    public Integer GetPhlebotomistId()
    {
        return _phlebotomist.getIdemployees();
    }
    
    public void AddDraw(Phlebotomy phlebotomy, Reschedule reschedule)
    {
        
        if (!_phlebotomyIdHash.contains(phlebotomy.getIdPhlebotomy()))
        {
            _drawCount ++;
            _phlebotomyIdHash.add(phlebotomy.getIdPhlebotomy());
            _phlebotomies.add(phlebotomy);
            _reschedules.put(phlebotomy.getIdPhlebotomy(), reschedule);
        }        
    }
    
    @Override
    public String toString()
    {
        return "id:" + _id + ":" + _date + ":"+_phlebotomist.getLastName()+": reschedule? " + (_reschedule!=null);
    }

    /**
     * Returns the string that will be displayed in the calendar
     * @return 
     */
    @Override
    public String GetDisplay()
    {
        return _phlebotomist.getLastName() + ", " + _phlebotomist.getFirstName() + ": " + _phlebotomies.size();
    }

    @Override
    public int GetCategoryID() {
        return _phlebotomist.getIdemployees();
    }

    public ArrayList<Phlebotomy> GetPhlebotomyRows()
    {
        return _phlebotomies;
    }
    
    public Reschedule GetReschedule(int phlebotomyId)
    {
        return _reschedules.get(phlebotomyId);
    }
    
    public String GetPhlebotomistName()
    {
        return _phlebotomist.getFirstName() + " " + _phlebotomist.getLastName();
    }

}
