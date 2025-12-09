
package EPOS;

/**
 * @date:   Mar 26, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: EPOS
 * @file name: FeeLine.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



public class FeeLine {
    
    private int testNo;
    private int subtestNo;
    private String procLetter1;
    private String feeCode1;
    private String procCode1;
    private String mod1;
    private String procLetter2;
    private String feeCode2;
    private String procCode2;
    private String mod2;
    private String procLetter3;
    private String feeCode3;
    private String procCode3;
    private String mod3;
    private int feeA;
    private int feeb;
    private int feeC;
    private int feeD;
    private int feeE;
    private int feeF;
    private int feeG;

    public FeeLine() {
    }

    public FeeLine(int testNo, int subtestNo, String procLetter1, String feeCode1, String procCode1, String mod1, String procLetter2, String feeCode2, String procCode2, String mod2, String procLetter3, String feeCode3, String procCode3, String mod3, int feeA, int feeb, int feeC, int feeD, int feeE, int feeF, int feeG) {
        this.testNo = testNo;
        this.subtestNo = subtestNo;
        this.procLetter1 = procLetter1;
        this.feeCode1 = feeCode1;
        this.procCode1 = procCode1;
        this.mod1 = mod1;
        this.procLetter2 = procLetter2;
        this.feeCode2 = feeCode2;
        this.procCode2 = procCode2;
        this.mod2 = mod2;
        this.procLetter3 = procLetter3;
        this.feeCode3 = feeCode3;
        this.procCode3 = procCode3;
        this.mod3 = mod3;
        this.feeA = feeA;
        this.feeb = feeb;
        this.feeC = feeC;
        this.feeD = feeD;
        this.feeE = feeE;
        this.feeF = feeF;
        this.feeG = feeG;
    }

    public int getFeeA() {
        return feeA;
    }

    public void setFeeA(int feeA) {
        this.feeA = feeA;
    }

    public int getFeeC() {
        return feeC;
    }

    public void setFeeC(int feeC) {
        this.feeC = feeC;
    }

    public String getFeeCode1() {
        return feeCode1;
    }

    public void setFeeCode1(String feeCode1) {
        this.feeCode1 = feeCode1;
    }

    public String getFeeCode2() {
        return feeCode2;
    }

    public void setFeeCode2(String feeCode2) {
        this.feeCode2 = feeCode2;
    }

    public String getFeeCode3() {
        return feeCode3;
    }

    public void setFeeCode3(String feeCode3) {
        this.feeCode3 = feeCode3;
    }

    public int getFeeD() {
        return feeD;
    }

    public void setFeeD(int feeD) {
        this.feeD = feeD;
    }

    public int getFeeE() {
        return feeE;
    }

    public void setFeeE(int feeE) {
        this.feeE = feeE;
    }

    public int getFeeF() {
        return feeF;
    }

    public void setFeeF(int feeF) {
        this.feeF = feeF;
    }

    public int getFeeG() {
        return feeG;
    }

    public void setFeeG(int feeG) {
        this.feeG = feeG;
    }

    public int getFeeb() {
        return feeb;
    }

    public void setFeeb(int feeb) {
        this.feeb = feeb;
    }

    public String getMod1() {
        return mod1;
    }

    public void setMod1(String mod1) {
        this.mod1 = mod1;
    }

    public String getMod2() {
        return mod2;
    }

    public void setMod2(String mod2) {
        this.mod2 = mod2;
    }

    public String getMod3() {
        return mod3;
    }

    public void setMod3(String mod3) {
        this.mod3 = mod3;
    }

    public String getProcCode1() {
        return procCode1;
    }

    public void setProcCode1(String procCode1) {
        this.procCode1 = procCode1;
    }

    public String getProcCode2() {
        return procCode2;
    }

    public void setProcCode2(String procCode2) {
        this.procCode2 = procCode2;
    }

    public String getProcCode3() {
        return procCode3;
    }

    public void setProcCode3(String procCode3) {
        this.procCode3 = procCode3;
    }

    public String getProcLetter1() {
        return procLetter1;
    }

    public void setProcLetter1(String procLetter1) {
        this.procLetter1 = procLetter1;
    }

    public String getProcLetter2() {
        return procLetter2;
    }

    public void setProcLetter2(String procLetter2) {
        this.procLetter2 = procLetter2;
    }

    public String getProcLetter3() {
        return procLetter3;
    }

    public void setProcLetter3(String procLetter3) {
        this.procLetter3 = procLetter3;
    }

    public int getSubtestNo() {
        return subtestNo;
    }

    public void setSubtestNo(int subtestNo) {
        this.subtestNo = subtestNo;
    }

    public int getTestNo() {
        return testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }
    
    
    
}
