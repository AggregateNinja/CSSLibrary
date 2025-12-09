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

public class PaymentLine 
{
    private int ARNumber;
    private String AccessionNumber;
    private int TestNumber;
    private int SubTestNumber;
    private BigDecimal PaymentAmount;
    private String UserName;
    private Date PaymentDate;

    public PaymentLine(){}
    
    public PaymentLine(int ARNumber, String AccessionNumber, int TestNumber, int SubTestNumber, BigDecimal PaymentAmount, String UserName, Date PaymentDate)
    {
        this.ARNumber = ARNumber;
        this.AccessionNumber = AccessionNumber;
        this.TestNumber = TestNumber;
        this.SubTestNumber = SubTestNumber;
        this.PaymentAmount = PaymentAmount;
        this.UserName = UserName;
        this.PaymentDate = PaymentDate;
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
    
    /* Access Functions for Payment Amount */
    public BigDecimal getPaymentAmount() 
    {
        return PaymentAmount;
    }
    
    public void setPaymentAmount(BigDecimal PaymentAmount) 
    {
        this.PaymentAmount = PaymentAmount;
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
    
    /* Access Functions for Payment Date */
    public Date getPaymentDate() 
    {
        return PaymentDate;
    }
    
    public void setPaymentDate(Date PaymentDate) 
    {
        this.PaymentDate = PaymentDate;
    }
}
