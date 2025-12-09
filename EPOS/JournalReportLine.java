/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package EPOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */

import java.math.BigDecimal;
import java.util.Date;

public class JournalReportLine {
    
    private int ARNumber;
    private String LastName;
    private String FirstName;
    private Date DatePaid;
    private BigDecimal Paid;
    private BigDecimal WriteOff;
    private BigDecimal Transfered;
    private BigDecimal Billed;
    private int Insurance;
    private String User;
    private String JournalCode;
    private String Percentage;
    private int Salesman;
    private int ClientNumber;

    public JournalReportLine() {
    }

    public JournalReportLine(int ARNumber, String LastName, String FirstName, Date DatePaid, BigDecimal Paid, BigDecimal WriteOff, BigDecimal Transfered, BigDecimal Billed, int Insurance, String User, String JournalCode, String Percentage, int Salesman, int ClientNumber) {
        this.ARNumber = ARNumber;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.DatePaid = DatePaid;
        this.Paid = Paid;
        this.WriteOff = WriteOff;
        this.Transfered = Transfered;
        this.Billed = Billed;
        this.Insurance = Insurance;
        this.User = User;
        this.JournalCode = JournalCode;
        this.Percentage = Percentage;
        this.Salesman = Salesman;
        this.ClientNumber = ClientNumber;
    }

    public int getARNumber() {
        return ARNumber;
    }

    public void setARNumber(int ARNumber) {
        this.ARNumber = ARNumber;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public Date getDatePaid() {
        return DatePaid;
    }

    public void setDatePaid(Date DatePaid) {
        this.DatePaid = DatePaid;
    }

    public BigDecimal getPaid() {
        return Paid;
    }

    public void setPaid(BigDecimal Paid) {
        this.Paid = Paid;
    }

    public BigDecimal getWriteOff() {
        return WriteOff;
    }

    public void setWriteOff(BigDecimal WriteOff) {
        this.WriteOff = WriteOff;
    }

    public BigDecimal getTransfered() {
        return Transfered;
    }

    public void setTransfered(BigDecimal Transfered) {
        this.Transfered = Transfered;
    }

    public BigDecimal getBilled() {
        return Billed;
    }

    public void setBilled(BigDecimal Billed) {
        this.Billed = Billed;
    }

    public int getInsurance() {
        return Insurance;
    }

    public void setInsurance(int Insurance) {
        this.Insurance = Insurance;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String User) {
        this.User = User;
    }

    public String getJournalCode() {
        return JournalCode;
    }

    public void setJournalCode(String JournalCode) {
        this.JournalCode = JournalCode;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String Percentage) {
        this.Percentage = Percentage;
    }

    public int getSalesman() {
        return Salesman;
    }

    public void setSalesman(int Salesman) {
        this.Salesman = Salesman;
    }

    public int getClientNumber() {
        return ClientNumber;
    }

    public void setClientNumber(int ClientNumber) {
        this.ClientNumber = ClientNumber;
    }
    
    

}
