/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 *
 * @author Ammar
 */
@Entity
@IdClass(ClientCustomRangesPK.class)
public class ClientCustomRanges implements Serializable {
    public static long serialVersionUID = 42L;
    
    @Id
    @Basic(optional=false)
    @Column(name="clientId")
    private Integer clientId;
    @Id
    @Basic(optional=false)
    @Column(name="testNumber")
    private Integer testNumber;
    @Basic(optional=false)
    @Column(name="lowNormal")
    private Double lowNormal;
    @Basic(optional=false)
    @Column(name="highNormal")
    private Double highNormal;
    @Basic(optional=false)
    @Column(name="alertLow")
    private Double alertLow;
    @Basic(optional=false)
    @Column(name="alertHigh")
    private Double alertHigh;
    @Basic(optional=false)
    @Column(name="criticalLow")
    private Double criticalLow;
    @Basic(optional=false)
    @Column(name="criticalHigh")
    private Double criticalHigh;
    @Column(name="printNormals")
    private String printNormals;
    @Column(name="units")
    private String units;
    @Column(name="useMaximums")
    private Boolean useMaximums;
    @Column(name="maxLowResult")
    private String maxLowResult;
    @Column(name="maxHighResult")
    private String maxHighResult;
    @Column(name="maxLowNumeric")
    private Double maxLowNumeric;
    @Column(name="maxHighNumeric")
    private Double maxHighNumeric;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    public Double getLowNormal() {
        return lowNormal;
    }

    public void setLowNormal(Double lowNormal) {
        this.lowNormal = lowNormal;
    }

    public Double getHighNormal() {
        return highNormal;
    }

    public void setHighNormal(Double highNormal) {
        this.highNormal = highNormal;
    }

    public Double getAlertLow() {
        return alertLow;
    }

    public void setAlertLow(Double alertLow) {
        this.alertLow = alertLow;
    }

    public Double getAlertHigh() {
        return alertHigh;
    }

    public void setAlertHigh(Double alertHigh) {
        this.alertHigh = alertHigh;
    }

    public Double getCriticalLow() {
        return criticalLow;
    }

    public void setCriticalLow(Double criticalLow) {
        this.criticalLow = criticalLow;
    }

    public Double getCriticalHigh() {
        return criticalHigh;
    }

    public void setCriticalHigh(Double criticalHigh) {
        this.criticalHigh = criticalHigh;
    }

    public String getPrintNormals() {
        return printNormals;
    }

    public void setPrintNormals(String printNormals) {
        this.printNormals = printNormals;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Boolean isUseMaximums() {
        return useMaximums;
    }

    public void setUseMaximums(Boolean useMaximums) {
        this.useMaximums = useMaximums;
    }

    public String getMaxLowResult() {
        return maxLowResult;
    }

    public void setMaxLowResult(String maxLowResult) {
        this.maxLowResult = maxLowResult;
    }

    public String getMaxHighResult() {
        return maxHighResult;
    }

    public void setMaxHighResult(String maxHighResult) {
        this.maxHighResult = maxHighResult;
    }

    public Double getMaxLowNumeric() {
        return maxLowNumeric;
    }

    public void setMaxLowNumeric(Double maxLowNumeric) {
        this.maxLowNumeric = maxLowNumeric;
    }

    public Double getMaxHighNumeric() {
        return maxHighNumeric;
    }

    public void setMaxHighNumeric(Double maxHighNumeric) {
        this.maxHighNumeric = maxHighNumeric;
    }
}
