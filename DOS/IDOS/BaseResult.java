/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS.IDOS;

import Utility.Diff;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Aanchal
 */
public class BaseResult {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idResults")
    private Integer idResults;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "testId")
    private int testId;
    @Column(name = "resultText")
    private String resultText;
    @Column(name = "resultRemark")
    private Integer resultRemark;
    @Column(name = "resultChoice")
    private Integer resultChoice;
    @Column(name = "isInvalidated")
    private boolean isInvalidated;
    @Column(name = "isApproved")
    private boolean isApproved;
    @Column(name = "resultNo")
    private Double resultNo;
    @Column(name = "isAbnormal")
    private boolean isAbnormal;
    @Column(name = "isHigh")
    private boolean isHigh;
    @Column(name = "isLow")
    private boolean isLow;
    @Column(name = "isCIDHigh")
    private boolean isCIDHigh;
    @Column(name = "isCIDLow")
    private boolean isCIDLow;

    /**
     * Default Constructor
     */
    public BaseResult() {

        idResults = 0;
        orderId = 0;
        testId = 0;
        resultText = "";
        resultRemark = 0;
        resultChoice = 0;
        isApproved = false;
        isInvalidated = false;
        resultNo = 0.0;
        isAbnormal = false;
        isHigh = false;
        isLow = false;
        isCIDHigh = false;
        isCIDLow = false;
    }

    /**
     * Getters and Setters for instance variables.
     *
     * @return
     */
    @Diff(fieldName = "idResults", isUniqueId = true)
    public Integer getIdResults() {
        return idResults;
    }

    public void setIdResults(Integer idResults) {
        this.idResults = idResults;
    }

    @Diff(fieldName = "orderId")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Diff(fieldName = "orderId")
    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    @Diff(fieldName = "resultText")
    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    @Diff(fieldName = "resultRemark")
    public Integer getResultRemark() {
        return resultRemark;
    }

    public void setResultRemark(Integer resultRemark) {
        this.resultRemark = resultRemark;
    }

    @Diff(fieldName = "resultChoice")
    public Integer getResultChoice() {
        return resultChoice;
    }

    public void setResultChoice(Integer resultChoice) {
        this.resultChoice = resultChoice;
    }

    @Diff(fieldName = "isApproved")
    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    @Diff(fieldName = "isInvalidated")
    public boolean isIsInvalidated() {
        return isInvalidated;
    }

    public void setIsInvalidated(boolean isInvalidated) {
        this.isInvalidated = isInvalidated;
    }

    @Diff(fieldName = "resultNo")
    public Double getResultNo() {
        return resultNo;
    }

    public void setResultNo(Double resultNo) {
        this.resultNo = resultNo;
    }

    @Diff(fieldName = "isAbnormal")
    public boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    @Diff(fieldName = "isHigh")
    public boolean getIsHigh() {
        return isHigh;
    }

    public void setIsHigh(boolean isHigh) {
        this.isHigh = isHigh;
    }

    @Diff(fieldName = "isLow")
    public boolean getIsLow() {
        return isLow;
    }

    public void setIsLow(boolean isLow) {
        this.isLow = isLow;
    }

    @Diff(fieldName = "isCIDHigh")
    public boolean getIsCIDHigh() {
        return isCIDHigh;
    }

    public void setIsCIDHigh(boolean isCIDHigh) {
        this.isCIDHigh = isCIDHigh;
    }

    @Diff(fieldName = "isCIDLow")
    public boolean getIsCIDLow() {
        return isCIDLow;
    }

    public void setIsCIDLow(boolean isCIDLow) {
        this.isCIDLow = isCIDLow;
    }

    @Override
    public String toString() {
        return getIdResults() + " " + getOrderId() + " " + getTestId();
    }

}
