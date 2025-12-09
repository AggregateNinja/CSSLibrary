/*
 * Computer Service & Support, Inc. All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ammar
 */

@Entity
@Table(name = "clientCustomReflexRules", catalog = "css", schema = "")
public class ClientCustomReflexRules implements Serializable {
    
    public static long serialVersionUID = 42L;
    
    @Id
    @Basic(optional=false)
    @Column(name="idClientCustomReflexRules")
    private Integer idClientCustomReflexRules;
    
    @Column(name="clientId")
    private Integer clientId;
    
    @Column(name="testNumber")
    private Integer testNumber;
    
    @Column(name="isHigh")
    private Integer isHigh;
    
    @Column(name="isLow")
    private Integer isLow;
    
    @Column(name="isCIDHigh")
    private Integer isCIDHigh;
    
    @Column(name="isCIDLow")
    private Integer isCIDLow;
    
    @Column(name="reflexRemark")
    private Integer reflexRemark;
    
    @Column(name="remarkTest")
    private Integer remarkTest;
    
    @Basic(optional=false)
    @Column(name="active")
    private Boolean active; 
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        ClientCustomReflexRules.serialVersionUID = serialVersionUID;
    }
    
    public Integer getIdClientCustomReflexRules() {
        return idClientCustomReflexRules;
    }

    public void setIdClientCustomReflexRules(Integer idClientCustomReflexRules) {
        this.idClientCustomReflexRules = idClientCustomReflexRules;
    }
    
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

    public Integer getIsHigh() {
        return isHigh;
    }

    public void setIsHigh(Integer isHigh) {
        this.isHigh = isHigh;
    }

    public Integer getIsLow() {
        return isLow;
    }

    public void setIsLow(Integer isLow) {
        this.isLow = isLow;
    }

    public Integer getIsCIDHigh() {
        return isCIDHigh;
    }

    public void setIsCIDHigh(Integer isCIDHigh) {
        this.isCIDHigh = isCIDHigh;
    }

    public Integer getIsCIDLow() {
        return isCIDLow;
    }

    public void setIsCIDLow(Integer isCIDLow) {
        this.isCIDLow = isCIDLow;
    }

    public Integer getReflexRemark() {
        return reflexRemark;
    }

    public void setReflexRemark(Integer reflexRemark) {
        this.reflexRemark = reflexRemark;
    }

    public Integer getRemarkTest() {
        return remarkTest;
    }

    public void setRemarkTest(Integer remarkTest) {
        this.remarkTest = remarkTest;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}