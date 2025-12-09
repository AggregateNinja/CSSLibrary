package Utility;

import DOS.ExtraNormals;
import org.joda.time.LocalDate;

/**
 * Class containing any date related utility methods.
 * @author Nick Engell
 */
public class DateUtil 
{
    
    
    /**
    * Checks if a date is between a start date and an end date. Start and end are inclusive.
    */
    public static boolean dateBetween(LocalDate date, LocalDate start, LocalDate end)
    {
        // to be inclusive at both ends, the date should not be before the start and should not be after the end.
        return !(date.isBefore(start)) && !(date.isAfter(end));
    }
    
    /**
     * Checks if a date is between a low year, month, and day and an high year, month, and day with respect to the current date.
     */
    public static boolean dateBetween(LocalDate date, int lowY, int lowM, int lowD, int highY, int highM, int highD)
    {
        LocalDate now = new LocalDate();
        LocalDate start = now.minusYears(highY).minusMonths(highM).minusDays(highD);
        LocalDate end = now.minusYears(lowY).minusMonths(lowM).minusDays(lowD);

        return dateBetween(date, start, end);
        
    }
      
}
