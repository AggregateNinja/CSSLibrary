
package DOS;

import Utility.Diff;
import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Remarks.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "remarks", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Remarks.findAll", query = "SELECT r FROM Remarks r")})
public class Remarks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idremarks", nullable = false)
    private Integer idremarks;
    @Basic(optional = false)
    @Column(name = "remarkNo", nullable = false)
    private int remarkNo;
    @Basic(optional = false)
    @Column(name = "remarkName", nullable = false, length = 50)
    private String remarkName;
    @Basic(optional = false)
    @Column(name = "remarkAbbr", nullable = false, length = 50)
    private String remarkAbbr;
    @Column(name = "remarkType")
    private Integer remarkType;
    @Basic(optional = false)
    @Lob
    @Column(name = "remarkText", nullable = false)
    private byte[] remarkText;
    @Basic(optional = false)
    @Column(name = "isAbnormal", nullable = false)
    private boolean isAbnormal;
    @Column(name = "remarkDepartment")
    private Integer remarkDepartment;
    @Basic(optional = false)
    @Column(name = "noCharge", nullable = false)
    private boolean noCharge;
    @Column(name = "testId", nullable = true)
    private Integer testId;

    public Remarks() {
    }

    public Remarks(Integer idremarks) {
        this.idremarks = idremarks;
    }

    public Remarks(Integer idremarks, int remarkNo, String remarkName, String remarkAbbr, int remarkType, byte[] remarkText, boolean isAbnormal, boolean noCharge) {
        this.idremarks = idremarks;
        this.remarkNo = remarkNo;
        this.remarkName = remarkName;
        this.remarkAbbr = remarkAbbr;
        this.remarkType = remarkType;
        this.remarkText = remarkText;
        this.isAbnormal = isAbnormal;
        this.noCharge = noCharge;
    }

    public Integer getIdremarks() {
        return idremarks;
    }

    public void setIdremarks(Integer idremarks) {
        this.idremarks = idremarks;
    }

    @Diff(fieldName="remarkNo")
    public int getRemarkNo() {
        return remarkNo;
    }

    public void setRemarkNo(int remarkNo) {
        this.remarkNo = remarkNo;
    }

    @Diff(fieldName="remarkName")
    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    @Diff(fieldName="remarkAbbr")
    public String getRemarkAbbr() {
        return remarkAbbr;
    }

    public void setRemarkAbbr(String remarkAbbr) {
        this.remarkAbbr = remarkAbbr;
    }

    @Diff(fieldName="remarkType")
    public Integer getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    @Diff(fieldName="remarkText")
    public byte[] getRemarkText() {
        return remarkText;
    }

    public void setRemarkText(byte[] remarkText) {
        this.remarkText = remarkText;
    }

    @Diff(fieldName="isAbnormal")
    public boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    @Diff(fieldName="remarkDepartment")
    public Integer getRemarkDepartment() {
        return remarkDepartment;
    }

    public void setRemarkDepartment(Integer remarkDepartment) {
        this.remarkDepartment = remarkDepartment;
    }

    @Diff(fieldName="noCharge")
    public boolean getNoCharge() {
        return noCharge;
    }

    public void setNoCharge(boolean noCharge) {
        this.noCharge = noCharge;
    }

    @Diff(fieldName="testId")
    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idremarks != null ? idremarks.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Remarks)) {
            return false;
        }
        Remarks other = (Remarks) object;
        if ((this.idremarks == null && other.idremarks != null) || (this.idremarks != null && !this.idremarks.equals(other.idremarks))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "(" + remarkNo + ") " + remarkName;
    }

}
