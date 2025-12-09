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
 * Represents a single reference lab result. One or more are attached to
 *  a "regular" (results table) result.
 * @author TomR
 */
@Entity
@Table(name = "refResults", catalog = "css", schema="")
public class RefResult implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRefResults")
    private Integer idRefResults;
    @Basic(optional = false)
    @Column(name = "resultId")
    private Integer resultId;
    @Basic(optional = false)
    @Column(name = "refTestId")
    private Integer refTestId;
    @Column(name = "resultNo")
    private Double resultNo;
    @Column(name = "resultText")
    private String resultText;
    @Column(name = "resultRemark")
    private Integer resultRemark;
    @Column(name = "resultChoice")
    private Integer resultChoice;
    @Column(name = "textComment")
    private String textComment;
    @Column(name = "refFlagId")
    private Integer refFlagId;
    @Basic(optional = false)
    @Column(name = "refFileId")
    private Integer refFileId;
    @Column(name = "invalidated")
    private Boolean invalidated;
    
    public RefResult() {}

    public Integer getIdRefResults() {
        return idRefResults;
    }

    public void setIdRefResults(Integer idRefResults) {
        this.idRefResults = idRefResults;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getRefTestId() {
        return refTestId;
    }

    public void setRefTestId(Integer refTestId) {
        this.refTestId = refTestId;
    }

    public Double getResultNo() {
        return resultNo;
    }

    public void setResultNo(Double resultNo) {
        this.resultNo = resultNo;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public Integer getResultRemark() {
        return resultRemark;
    }

    public void setResultRemark(Integer resultRemark) {
        this.resultRemark = resultRemark;
    }

    public Integer getResultChoice() {
        return resultChoice;
    }

    public void setResultChoice(Integer resultChoice) {
        this.resultChoice = resultChoice;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public Integer getRefFlagId() {
        return refFlagId;
    }

    public void setRefFlagId(Integer refFlagId) {
        this.refFlagId = refFlagId;
    }

    public Integer getRefFileId() {
        return refFileId;
    }

    public void setRefFileId(Integer refFileId) {
        this.refFileId = refFileId;
    }

    public Boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Boolean invalidated) {
        this.invalidated = invalidated;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idRefResults != null ? idRefResults.hashCode() : 0);
        return hash;                
    }
    
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RefResult))
        {
            return false;
        }
        RefResult other = (RefResult) object;
        if ((this.idRefResults == null && other.idRefResults != null) || (this.idRefResults != null && !this.idRefResults.equals(other.idRefResults)))
        {
            return false;
        }
        return true;        
    }
    
    @Override
    public String toString()
    {
        return "RefResult[id=" + idRefResults + "]";
    }
    // Equals / toString
    

}
