
package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "microInstrumentComments", catalog = "css", schema = "")
public class MicroComment implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "microOrderId", nullable = false)
    private Integer microOrderId;
    @Column(name = "microOrganismId")
    private Integer microOrganismId;
    @Column(name = "microInstrumentCommentId")
    private Integer microInstrumentCommentId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "commentTypeId")
    private Integer commentTypeId;
    @Column(name = "invalidated")
    private boolean invalidated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMicroOrderId() {
        return microOrderId;
    }

    public void setMicroOrderId(Integer microOrderId) {
        this.microOrderId = microOrderId;
    }

    public Integer getMicroOrganismId() {
        return microOrganismId;
    }

    public void setMicroOrganismId(Integer microOrganismId) {
        this.microOrganismId = microOrganismId;
    }

    public Integer getMicroInstrumentCommentId() {
        return microInstrumentCommentId;
    }

    public void setMicroInstrumentCommentId(Integer microInstrumentCommentId) {
        this.microInstrumentCommentId = microInstrumentCommentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(Integer commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(boolean invalidated) {
        this.invalidated = invalidated;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MicroComment)) {
            return false;
        }
        MicroComment other = (MicroComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.microInstrumentComments[ id=" + id + "]";
    }    
    
}
