/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "phlebotomy")
@NamedQueries({
    @NamedQuery(name = "Phlebotomy.findAll", query = "SELECT p FROM Phlebotomy p")})
public class Phlebotomy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPhlebotomy")
    private Integer idPhlebotomy;
    @Column(name = "idAdvancedOrder")
    private Integer idAdvancedOrder;
    @Column(name = "idOrders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "occurrences")
    private Integer occurrences;
    @Basic(optional = false)
    @Column(name = "scheduleTypeId")
    private Integer scheduleTypeId;
    @Basic(optional = false)
    @Column(name = "frequency")
    private Integer frequency;
    @Column(name = "phlebotomist")
    private int phlebotomist;
    @Basic(optional = false)
    @Column(name = "zone")
    private String zone;
    @Basic(optional = false)
    @Column(name = "drawComment1")
    private String drawComment1;
    @Basic(optional = false)
    @Column(name = "drawComment2")
    private String drawComment2;
    @Column(name = "week")
    private Integer week;
    @Column(name = "monday")
    private Boolean monday;
    @Column(name = "tuesday")
    private Boolean tuesday;
    @Column(name = "wednesday")
    private Boolean wednesday;
    @Column(name = "thursday")
    private Boolean thursday;
    @Column(name = "friday")
    private Boolean friday;
    @Column(name = "saturday")
    private Boolean saturday;
    @Column(name = "sunday")
    private Boolean sunday;
    @Column(name = "inactive")
    private Boolean inactive;
    @Column(name = "redrawReasonId")
    private Integer redrawReasonId;
    
    public Phlebotomy() {
    }

    public Phlebotomy(Integer idPhlebotomy) {
        this.idPhlebotomy = idPhlebotomy;
    }

    public Integer getIdPhlebotomy() {
        return idPhlebotomy;
    }

    public void setIdPhlebotomy(Integer idPhlebotomy) {
        this.idPhlebotomy = idPhlebotomy;
    }

    public Integer getIdAdvancedOrder() {
        return idAdvancedOrder;
    }

    public void setIdAdvancedOrder(Integer idAdvancedOrder) {
        this.idAdvancedOrder = idAdvancedOrder;
    }

    public Integer getIdOrders() {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders) {
        this.idOrders = idOrders;
    }

    @Diff(fieldName="startDate")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    @Diff(fieldName="endDate")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Diff(fieldName="occurrences")
    public Integer getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Integer occurrences) {
        this.occurrences = occurrences;
    }

    @Diff(fieldName="scheduleTypeId")
    public Integer getScheduleTypeId() {
        return scheduleTypeId;
    }

    public void setScheduleTypeId(Integer scheduleTypeId) {
        this.scheduleTypeId = scheduleTypeId;
    }

    @Diff(fieldName="frequency")
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
    
    @Diff(fieldName="phlebotomist")
    public int getPhlebotomist() {
        return phlebotomist;
    }

    public void setPhlebotomist(int phlebotomist) {
        this.phlebotomist = phlebotomist;
    }

    @Diff(fieldName="zone")
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Diff(fieldName="drawComment1")
    public String getDrawComment1() {
        return drawComment1;
    }

    public void setDrawComment1(String drawComment1) {
        this.drawComment1 = drawComment1;
    }

    @Diff(fieldName="drawComment2")
    public String getDrawComment2() {
        return drawComment2;
    }

    public void setDrawComment2(String drawComment2) {
        this.drawComment2 = drawComment2;
    }
    
    @Diff(fieldName="week")
    public Integer getWeek() {
        return week;
    }
    
    public void setWeek(Integer week) {
        this.week = week;
    }

    @Diff(fieldName="isMonday")
    public Boolean isMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    @Diff(fieldName="isTuesday")
    public Boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    @Diff(fieldName="isWednesday")
    public Boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    @Diff(fieldName="isThursday")
    public Boolean isThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    @Diff(fieldName="isFriday")
    public Boolean isFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    @Diff(fieldName="isSaturday")
    public Boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    @Diff(fieldName="isSunday")
    public Boolean isSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    @Diff(fieldName="isInactive")
    public Boolean isInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }

    @Diff(fieldName="redrawReasonId")
    public Integer getRedrawReasonId() {
        return redrawReasonId;
    }

    public void setRedrawReasonId(Integer redrawReasonId) {
        this.redrawReasonId = redrawReasonId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPhlebotomy != null ? idPhlebotomy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        if (object == null) return false;
        
        if (!(object instanceof Phlebotomy)) {
            return false;
        }        
        Phlebotomy other = (Phlebotomy) object;
        
        if ((this.idPhlebotomy == null && other.idPhlebotomy != null) || (this.idPhlebotomy != null && !this.idPhlebotomy.equals(other.idPhlebotomy)))
        {
            return false;
        }
        
        if (this.idAdvancedOrder != null && !this.idAdvancedOrder.equals(other.idAdvancedOrder)) return false;
        if (other.idAdvancedOrder != null && !other.idAdvancedOrder.equals(this.idAdvancedOrder)) return false;

        if (this.idOrders != null && !this.idOrders.equals(other.idOrders)) return false;
        if (other.idOrders != null && !other.idOrders.equals(this.idOrders)) return false;        
        
        if (this.startDate != null && !this.startDate.equals(other.startDate)) return false;
        if (other.startDate != null && !other.startDate.equals(this.startDate)) return false;
        
        if (this.endDate != null && !this.endDate.equals(other.endDate)) return false;
        if (other.endDate != null && !other.endDate.equals(this.endDate)) return false;
        
        if (this.occurrences != null && !this.occurrences.equals(other.occurrences)) return false;
        if (other.occurrences != null && !other.occurrences.equals(this.occurrences)) return false;
        
        if (this.scheduleTypeId != null && !this.scheduleTypeId.equals(other.scheduleTypeId)) return false;
        if (other.scheduleTypeId != null && !other.scheduleTypeId.equals(this.scheduleTypeId)) return false;
        
        if (this.frequency != null && !this.frequency.equals(other.frequency)) return false;
        if (other.frequency != null && !other.frequency.equals(this.frequency)) return false;
        
        if (this.phlebotomist != other.phlebotomist) return false;
        
        if (this.zone != null && !this.zone.equals(other.zone)) return false;
        if (other.zone != null && !other.zone.equals(this.zone)) return false;
        
        if (this.drawComment1 != null && !this.drawComment1.equals(other.drawComment1)) return false;
        if (other.drawComment1 != null && !other.drawComment1.equals(this.drawComment1)) return false;
        
        if (this.drawComment2 != null && !this.drawComment2.equals(other.drawComment2)) return false;
        if (other.drawComment2 != null && !other.drawComment2.equals(this.drawComment2)) return false;
        
        if (this.week != null && !this.week.equals(other.week)) return false;
        if (other.endDate != null && !other.endDate.equals(this.endDate)) return false;
        
        if (this.monday != null && !this.monday.equals(other.monday)) return false;
        if (other.monday != null && !other.monday.equals(this.monday)) return false;
        
        if (this.tuesday != null && !this.tuesday.equals(other.tuesday)) return false;
        if (other.tuesday != null && !other.tuesday.equals(this.tuesday)) return false;
        
        if (this.wednesday != null && !this.wednesday.equals(other.wednesday)) return false;
        if (other.wednesday != null && !other.wednesday.equals(this.wednesday)) return false;
        
        if (this.thursday != null && !this.thursday.equals(other.thursday)) return false;
        if (other.thursday != null && !other.thursday.equals(this.thursday)) return false;
        
        if (this.friday != null && !this.friday.equals(other.friday)) return false;
        if (other.friday != null && !other.friday.equals(this.friday)) return false;
        
        if (this.saturday != null && !this.saturday.equals(other.saturday)) return false;
        if (other.saturday != null && !other.saturday.equals(this.saturday)) return false;
        
        if (this.sunday != null && !this.sunday.equals(other.sunday)) return false;
        if (other.sunday != null && !other.sunday.equals(this.sunday)) return false;        
        
        if (this.inactive != null && !this.inactive.equals(other.inactive)) return false;
        if (other.inactive != null && !other.inactive.equals(this.inactive)) return false;        
        
        return true;
    }
    
    /**
     * True if the schedule parts of this phlebotomy row match the parameter phlebotomy row
     *  Used to warn the user when changing an existing schedule.
     *  Only cares about dates! Doesn't consider hour/minutes on start/end dates.
     * 
     *  Excludes:
     *      Zone & draw comments
     * @param other
     * @return 
     */
    public boolean ScheduleEquals(Phlebotomy other)
    {
        if (other == null) return false;
        LocalDate localStartDate = new LocalDate(this.startDate);
        LocalDate otherLocalStartDate = new LocalDate(other.startDate);        
        if (!localStartDate.equals(otherLocalStartDate)) return false;
        if (!otherLocalStartDate.equals(localStartDate)) return false;
        
        LocalDate localEndDate = null;
        LocalDate otherLocalEndDate = null;        
        if (this.endDate != null) localEndDate = new LocalDate(this.endDate);
        if (other.endDate != null) otherLocalEndDate = new LocalDate(other.endDate);        
        if (localEndDate != null && !localEndDate.equals(otherLocalEndDate)) return false;
        if (otherLocalEndDate != null && !otherLocalEndDate.equals(localEndDate)) return false;
        
        int thisOccurrences = 0;
        if (this.occurrences != null)
        {
            thisOccurrences = this.occurrences;
        }
        int otherOccurrences = 0;
        if (other.occurrences != null)
        {
            otherOccurrences = other.occurrences;
        }
        if (thisOccurrences != otherOccurrences) return false;
        
        
        if (this.scheduleTypeId != null && !this.scheduleTypeId.equals(other.scheduleTypeId)) return false;
        if (other.scheduleTypeId != null && !other.scheduleTypeId.equals(this.scheduleTypeId)) return false;
        
        if (this.frequency != null && !this.frequency.equals(other.frequency)) return false;
        if (other.frequency != null && !other.frequency.equals(this.frequency)) return false;
        
        if (this.phlebotomist != other.phlebotomist) return false;
        
        if (this.week == null) this.week = 0;
        if (other.week == null) other.week = 0;
        if (!this.week.equals(other.week)) return false;
        if (!other.week.equals(this.week)) return false;
        
        if (this.monday != null && !this.monday.equals(other.monday)) return false;
        if (other.monday != null && !other.monday.equals(this.monday)) return false;
        
        if (this.tuesday != null && !this.tuesday.equals(other.tuesday)) return false;
        if (other.tuesday != null && !other.tuesday.equals(this.tuesday)) return false;
        
        if (this.wednesday != null && !this.wednesday.equals(other.wednesday)) return false;
        if (other.wednesday != null && !other.wednesday.equals(this.wednesday)) return false;
        
        if (this.thursday != null && !this.thursday.equals(other.thursday)) return false;
        if (other.thursday != null && !other.thursday.equals(this.thursday)) return false;
        
        if (this.friday != null && !this.friday.equals(other.friday)) return false;
        if (other.friday != null && !other.friday.equals(this.friday)) return false;
        
        if (this.saturday != null && !this.saturday.equals(other.saturday)) return false;
        if (other.saturday != null && !other.saturday.equals(this.saturday)) return false;
        
        if (this.sunday != null && !this.sunday.equals(other.sunday)) return false;
        if (other.sunday != null && !other.sunday.equals(this.sunday)) return false;        
        
        //if (this.inactive != null && !this.inactive.equals(other.inactive)) return false;
        //if (other.inactive != null && !other.inactive.equals(this.inactive)) return false;        
        
        return true;
    }
    
    /**
     * Returns a human-readable representation of this schedule type.
     * @return 
     */
    public String getDisplay()
    {
        Date startDate = getStartDate();
        String startDateStr = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        if (startDate != null)
        {
            startDateStr = " Starting " + sdf.format(getStartDate());
        }

        String frequencyStr = "";
        Integer frequency = getFrequency();
        if (frequency != null && frequency > 1)
        {
            frequencyStr = " every " + frequency;
        }
        
        String scheduleTypeStr = "";
        Integer scheduleType = getScheduleTypeId();
        if (scheduleType != null)
        {
            switch (scheduleType)
            {
                case 1: // Daily
                    if (frequencyStr.isEmpty())
                    {
                        scheduleTypeStr = " daily";
                    }
                    else
                    {
                        scheduleTypeStr = " days";
                    }
                    break;
                case 2: // Weekly
                    if (frequencyStr.isEmpty())
                    {
                        scheduleTypeStr = " weekly";
                    }
                    else
                    {
                        scheduleTypeStr = " weeks";
                    }
                    scheduleTypeStr += " on ";
                    if (isSunday()) scheduleTypeStr += "S";
                    if (isMonday()) scheduleTypeStr += "M";
                    if (isTuesday()) scheduleTypeStr += "T";
                    if (isWednesday()) scheduleTypeStr += "W";
                    if (isThursday()) scheduleTypeStr += "R";
                    if (isFriday()) scheduleTypeStr += "F";
                    if (isSaturday()) scheduleTypeStr += "Sa";
                    break;
                case 3: // Monthly (fixed or relative)
                    Integer week = getWeek();
                    scheduleTypeStr = "";
                    if (frequencyStr.isEmpty())
                    {
                        scheduleTypeStr = " monthly on ";
                    }
                    else
                    {
                        scheduleTypeStr = " months on ";
                    }
                    
                    if (week != null && week > 0)
                    {
                        if (week == 1)
                        {
                            scheduleTypeStr += " the 1st";
                        }
                        else if (week == 2)
                        {
                            scheduleTypeStr += " the 2nd";
                        }
                        else if (week == 3)
                        {
                            scheduleTypeStr += " the 3rd";
                        }
                        else if (week == 4)
                        {
                            scheduleTypeStr += " the 4th";
                        }
                        else
                        {
                            scheduleTypeStr += " the last";
                        }
                        if (isSunday()) scheduleTypeStr += " Sunday";
                        if (isMonday()) scheduleTypeStr += " Monday";
                        if (isTuesday()) scheduleTypeStr += " Tuesday";
                        if (isWednesday()) scheduleTypeStr += " Wednesday";
                        if (isThursday()) scheduleTypeStr += " Thursday";
                        if (isFriday()) scheduleTypeStr += " Friday";
                        if (isSaturday()) scheduleTypeStr += " Saturday";
                    }
                    else // Fixed day of month
                    {
                        SimpleDateFormat sdfDayOfMonth = new SimpleDateFormat("dd");
                        scheduleTypeStr += " " + sdfDayOfMonth.format(startDate);
                    }
                    break;
                case 4: // Yearly
                    if (frequencyStr.isEmpty())
                    {
                        scheduleTypeStr = " yearly";
                    }
                    else
                    {
                        scheduleTypeStr = " years";
                    }
                    break;
            }
        }
        
        String occurrencesStr = " until cancelled";
        Integer occurrences = getOccurrences();
        try
        {
            if ( occurrences != null && occurrences >= 0)
            {
                occurrencesStr = " " + occurrences + " time(s)";
            }
        } catch (Exception ex) {}
        
        if (startDateStr == null) startDateStr = "";
        if (frequencyStr == null) frequencyStr = "";
        if (scheduleTypeStr == null) scheduleTypeStr = "";
        if (occurrencesStr == null) occurrencesStr = "";
        String display = startDateStr + frequencyStr + scheduleTypeStr + occurrencesStr;
        return display;
    }

    @Override
    public String toString() {
        return "DOS.Phlebotomy[ idPhlebotomy=" + idPhlebotomy + " ]";
    }

}
