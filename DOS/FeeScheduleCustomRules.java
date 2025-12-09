package DOS;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

public class FeeScheduleCustomRules implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idFeeScheduleCustomRule;
    private Integer feeScheduleId;
    private Integer testNumber;
    private String testAlias;
    private Integer cptCodeId;
    private Integer quantityFrom;
    private Integer quantityTo;
    private BigDecimal cost;
    private Integer addedBy;
    private Date createdDate;
    private Integer mod1;
    private Integer mod2;
    private Integer mod3;
    private Integer mod4;
    private Integer mod5;

    public Integer getIdFeeScheduleCustomRule() {
        return idFeeScheduleCustomRule;
    }

    public void setIdFeeScheduleCustomRule(Integer idFeeScheduleCustomRule) {
        this.idFeeScheduleCustomRule = idFeeScheduleCustomRule;
    }

    public Integer getFeeScheduleId() {
        return feeScheduleId;
    }

    public void setFeeScheduleId(Integer feeScheduleId) {
        this.feeScheduleId = feeScheduleId;
    }

    public Integer getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestAlias() {
        return testAlias;
    }

    public void setTestAlias(String testAlias) {
        this.testAlias = testAlias;
    }

    public Integer getCptCodeId() {
        return cptCodeId;
    }

    public void setCptCodeId(Integer cptCodeId) {
        this.cptCodeId = cptCodeId;
    }

    public Integer getQuantityFrom() {
        return quantityFrom;
    }

    public void setQuantityFrom(Integer quantityFrom) {
        this.quantityFrom = quantityFrom;
    }

    public Integer getQuantityTo() {
        return quantityTo;
    }

    public void setQuantityTo(Integer quantityTo) {
        this.quantityTo = quantityTo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getMod1() {
        return mod1;
    }

    public void setMod1(Integer mod1) {
        this.mod1 = mod1;
    }

    public Integer getMod2() {
        return mod2;
    }

    public void setMod2(Integer mod2) {
        this.mod2 = mod2;
    }

    public Integer getMod3() {
        return mod3;
    }

    public void setMod3(Integer mod3) {
        this.mod3 = mod3;
    }

    public Integer getMod4() {
        return mod4;
    }

    public void setMod4(Integer mod4) {
        this.mod4 = mod4;
    }

    public Integer getMod5() {
        return mod5;
    }

    public void setMod5(Integer mod5) {
        this.mod5 = mod5;
    }
}
