package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The "normal/abnormal" flag associated with a reference lab result
 * @author TomR
 */
@Entity
@Table(name = "refFlags", catalog = "css", schema="")
public class RefFlag implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRefFlags")
    private Integer idRefFlags;
    @Basic(optional = false)
    @Column(name = "deptNo")
    private int deptNo;
    @Basic(optional = false)
    @Column(name = "flag")
    private String flag;
    @Column(name = "description")
    private String description;
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

    public Integer getIdRefFlags() {
        return idRefFlags;
    }

    public void setIdRefFlags(Integer idRefFlags) {
        this.idRefFlags = idRefFlags;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public boolean getIsHigh() {
        return isHigh;
    }

    public void setIsHigh(boolean isHigh) {
        this.isHigh = isHigh;
    }

    public boolean getIsLow() {
        return isLow;
    }

    public void setIsLow(boolean isLow) {
        this.isLow = isLow;
    }

    public boolean getIsCIDHigh() {
        return isCIDHigh;
    }

    public void setIsCIDHigh(boolean isCIDHigh) {
        this.isCIDHigh = isCIDHigh;
    }

    public boolean getIsCIDLow() {
        return isCIDLow;
    }

    public void setIsCIDLow(boolean isCIDLow) {
        this.isCIDLow = isCIDLow;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRefFlags != null ? idRefFlags.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefFile))
        {
            return false;
        }
        RefFlag other = (RefFlag) object;
        if ((this.idRefFlags == null && other.idRefFlags != null) || (this.idRefFlags != null && !this.idRefFlags.equals(other.idRefFlags)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "RefFlag[ id=" + idRefFlags + ", flag=" + flag + ", desc:" + description + " ]";
    }    
    
}
