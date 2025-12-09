package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mixedAnswerResults", catalog = "css", schema = "")
public class MixedAnswerResult implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "resultId")
    private Integer resultId;
    @Basic(optional = false)
    @Column(name = "mixedAnswerOptionId")
    private int mixedAnswerOptionId;
    @Basic(optional = false)
    @Column(name = "invalidated")
    private boolean invalidated;
    @Column(name = "invalidatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date invalidatedDate;
    @Column(name = "invalidatedBy")
    private Integer invalidatedBy;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;
    @Column(name = "deletedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @Column(name = "deletedBy")
    private Integer deletedBy;
    @Basic(optional = false)
    @Column(name = "displayOrder")
    private int displayOrder;
    
    public MixedAnswerResult() {};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public int getMixedAnswerOptionId() {
        return mixedAnswerOptionId;
    }

    public void setMixedAnswerOptionId(int mixedAnswerOptionId) {
        this.mixedAnswerOptionId = mixedAnswerOptionId;
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(boolean invalidated) {
        this.invalidated = invalidated;
    }

    public Date getInvalidatedDate() {
        return invalidatedDate;
    }

    public void setInvalidatedDate(Date invalidatedDate) {
        this.invalidatedDate = invalidatedDate;
    }
    
    public Integer getInvalidatedBy() {
        return invalidatedBy;
    }

    public void setInvalidatedBy(Integer invalidatedBy) {
        this.invalidatedBy = invalidatedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Integer getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Integer deletedBy) {
        this.deletedBy = deletedBy;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MixedAnswerResult)) {
            return false;
        }
        MixedAnswerResult other = (MixedAnswerResult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MixedAnswerResult[ id=" + id + " ]";
    }
}
