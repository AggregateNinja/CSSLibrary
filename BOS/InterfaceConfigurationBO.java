/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package BOS;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 03/10/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
public class InterfaceConfigurationBO {

    private int idTestXref;
    private int testNumber;
    private String testName;
    private String transIn;
    private String transOut;
    private int name;
    private boolean active;

    public InterfaceConfigurationBO() {
    }

    public int getIdTestXref() {
        return idTestXref;
    }

    public void setIdTestXref(int idTestXref) {
        this.idTestXref = idTestXref;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTransIn() {
        return transIn;
    }

    public void setTransIn(String transIn) {
        this.transIn = transIn;
    }

    public String getTransOut() {
        return transOut;
    }

    public void setTransOut(String transOut) {
        this.transOut = transOut;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
}
