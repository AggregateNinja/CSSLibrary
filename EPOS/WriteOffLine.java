/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package EPOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 09/06/2013  
 * @author Michael Douglass <miked@csslis.com>
 */

import java.math.BigDecimal;
import java.util.Date;

public class WriteOffLine 
{
    private int ARNumber;
    private String AccessionNumber;
    private int TestNumber;
    private int SubTestNumber;
    private BigDecimal WriteOffAmount;
    private String UserName;
    private Date WriteOffDate;

    public WriteOffLine(){}
    
    public WriteOffLine(int ARNumber, String AccessionNumber, int TestNumber, int SubTestNumber, BigDecimal WriteOffAmount, String UserName, Date WriteOffDate)
    {
        this.ARNumber = ARNumber;
        this.AccessionNumber = AccessionNumber;
        this.TestNumber = TestNumber;
        this.SubTestNumber = SubTestNumber;
        this.WriteOffAmount = WriteOffAmount;
        this.UserName = UserName;
        this.WriteOffDate = WriteOffDate;
    }
    
    /* Access Functions for ARNumber */
    public int getARNumber() 
    {
        return ARNumber;
    }
    
    public void setARNumber(int ARNumber) 
    {
        this.ARNumber = ARNumber;
    }
    
    /* Access Functions for Accession Number */
    public String getAccessionNumber() 
    {
        return AccessionNumber;
    }
    
    public void setAccessionNumber(String AccessionNumber) 
    {
        this.AccessionNumber = AccessionNumber;
    }
    
    /* Access Functions for Test Number */
    public int getTestNumber() 
    {
        return TestNumber;
    }
    
    public void setTestNumber(int TestNumber) 
    {
        this.TestNumber = TestNumber;
    }
    
    /* Access Functions for Sub Test Number */
    public int getSubTestNumber() 
    {
        return SubTestNumber;
    }
    
    public void setSubTestNumber(int SubTestNumber) 
    {
        this.SubTestNumber = SubTestNumber;
    }
    
    /* Access Functions for WriteOff Amount */
    public BigDecimal getWriteOffAmount() 
    {
        return WriteOffAmount;
    }
    
    public void setWriteOffAmount(BigDecimal WriteOffAmount) 
    {
        this.WriteOffAmount = WriteOffAmount;
    }
    
    /* Access Functions for User Name */
    public String getUserName() 
    {
        return UserName;
    }
    
    public void setUserName(String UserName) 
    {
        this.UserName = UserName;
    }
    
    /* Access Functions for WriteOff Date */
    public Date getWriteOffDate() 
    {
        return WriteOffDate;
    }
    
    public void setWriteOffDate(Date WriteOffDate) 
    {
        this.WriteOffDate = WriteOffDate;
    }
}
