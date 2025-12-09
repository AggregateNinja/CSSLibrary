package Utility;

import java.util.Date;

public class DateRange
{
    private Date startRange;
    private Date endRange;
    
    public DateRange(Date startRange, Date endRange)
    {
        this.startRange = startRange;
        this.endRange = endRange;
    }
    
    public DateRange() {}
    
    public boolean hasStartRange()
    {
        return this.startRange != null;
    }
    
    public boolean hasEndRange()
    {
        return this.endRange != null;
    }
    
    public Date getStartRange()
    {
        return this.startRange;
    }
    
    public Date getEndRange()
    {
        return this.endRange;
    }
    
    public void setStartRange(Date startRange)
    {
        this.startRange = startRange;
    }
    
    public void setEndRange(Date endRange)
    {
        this.endRange = endRange;
    }

}
