/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BL.AdvancedOrders;

import DOS.Reschedule;

/**
 * Callbacks to the ScheduleManager from the PhlebotomistRangeSchedules
 * @author TomR
 */
public interface IScheduleRegistry
{
    public int GetNextAvailableID();
    public boolean RegisterScheduleDate(int id, ScheduleDate sd);
    public ScheduleDate GetScheduleDateById(int id);
    public void CreateOrUpdatePhlebotomistRangeSchedule(int phlebotomistId, Reschedule r) throws Exception;
}