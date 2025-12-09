
package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents a single reference lab buffer result.
 * @author TomR
 */
public class RefRes implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrefresult")
    private Integer idRefResult;
    @Column(name = "orderId")
    private Integer orderId;
    @Column(name = "accession")
    private String accession;
    @Column(name = "resIdentifier")
    private String resIdentifier;
    @Column(name = "ordIdentifier")
    private String ordIdentifier;
    @Column(name = "ordTestNum")
    private Integer ordTestNum;
    @Column(name = "resTestNum")
    private Integer resTestNum;
    @Column(name = "name")
    private String name;
    @Column(name = "result")
    private String result;
    @Column(name = "comment")
    private String comment;
    @Column(name = "units")
    private String units;
    @Column(name = "range")
    private String range;
    @Column(name = "postedDate")
    private Date postedDate;
    @Column(name = "user")
    private String user;
    @Column(name = "posted")
    private Boolean posted;
    @Column(name = "fileName")
    private String fileName;
    @Column(name = "code")
    private String code;
    @Column(name = "isFinal")
    private Boolean isFinal;
    @Column(name = "isInvalidated")
    private Boolean isInvalidated;
    @Column(name = "abnormalFlag")
    private String abnormalFlag;
    
    private String tableName;
    
    private RefRes() {}
    public RefRes(String tableName)
    {
        this.tableName = tableName;
    }

    @Diff(fieldName="idrefresult")
    public Integer getIdRefResult() {
        return idRefResult;
    }

    public void setIdRefResult(Integer idRefResult) {
        this.idRefResult = idRefResult;
    }

    @Diff(fieldName="orderId")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Diff(fieldName="accession")
    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    @Diff(fieldName="resIdentifier")
    public String getResIdentifier() {
        return resIdentifier;
    }

    public void setResIdentifier(String resIdentifier) {
        this.resIdentifier = resIdentifier;
    }

    @Diff(fieldName="ordIdentifier")
    public String getOrdIdentifier() {
        return ordIdentifier;
    }

    public void setOrdIdentifier(String ordIdentifier) {
        this.ordIdentifier = ordIdentifier;
    }

    @Diff(fieldName="ordTestNum")
    public Integer getOrdTestNum() {
        return ordTestNum;
    }

    public void setOrdTestNum(Integer ordTestNum) {
        this.ordTestNum = ordTestNum;
    }

    @Diff(fieldName="resTestNum")
    public Integer getResTestNum() {
        return resTestNum;
    }

    public void setResTestNum(Integer resTestNum) {
        this.resTestNum = resTestNum;
    }

    @Diff(fieldName="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Diff(fieldName="result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Diff(fieldName="comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Diff(fieldName="units")
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Diff(fieldName="range")
    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Diff(fieldName="postedDate")
    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    @Diff(fieldName="user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @Diff(fieldName="posted")
    public Boolean isPosted() {
        return posted;
    }

    public void setPosted(Boolean posted) {
        this.posted = posted;
    }

    @Diff(fieldName="fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Diff(fieldName="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Diff(fieldName="isFinal")
    public Boolean isFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    @Diff(fieldName="isInvalidated")
    public Boolean isInvalidated() {
        return isInvalidated;
    }

    public void setIsInvalidated(Boolean isInvalidated) {
        this.isInvalidated = isInvalidated;
    }

    @Diff(fieldName="abnormalFlag")
    public String getAbnormalFlag() {
        return abnormalFlag;
    }

    public void setAbnormalFlag(String abnormalFlag) {
        this.abnormalFlag = abnormalFlag;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRefResult != null ? idRefResult.hashCode() : 0);
        return hash;                
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefRes))
        {
            return false;
        }
        RefRes other = (RefRes) object;
        if ((this.idRefResult == null && other.idRefResult != null) || (this.idRefResult != null && !this.idRefResult.equals(other.idRefResult)))
        {
            return false;
        }
        return true;        
    }
    
    @Override
    public String toString()
    {
        return "RefRes[table=" + tableName + " id=" + idRefResult + "]";
    }
    
    public RefRes copy()
    {
        RefRes rrCopy = new RefRes();
        rrCopy.setIdRefResult(idRefResult);
        rrCopy.setOrderId(orderId);
        rrCopy.setAccession(accession);
        rrCopy.setResIdentifier(resIdentifier);
        rrCopy.setOrdIdentifier(ordIdentifier);
        rrCopy.setOrdTestNum(ordTestNum);
        rrCopy.setResTestNum(resTestNum);
        rrCopy.setName(name);
        rrCopy.setResult(result);
        rrCopy.setComment(comment);
        rrCopy.setUnits(units);
        rrCopy.setRange(range);
        rrCopy.setPostedDate(postedDate);
        rrCopy.setUser(user);
        rrCopy.setPosted(posted);
        rrCopy.setFileName(fileName);
        rrCopy.setCode(code);
        rrCopy.setIsFinal(isFinal);
        rrCopy.setIsInvalidated(isInvalidated);
        rrCopy.setAbnormalFlag(abnormalFlag);
        return rrCopy;
    }
}
