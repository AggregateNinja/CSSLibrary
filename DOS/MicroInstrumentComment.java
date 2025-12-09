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
public class MicroInstrumentComment implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "instrumentId", nullable = false)
    private int instrumentId;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "commentCode", nullable = false)
    private String commentCode;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "associationCode")
    private String associationCode;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getInstrumentId()
    {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId)
    {
        this.instrumentId = instrumentId;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getCommentCode()
    {
        return commentCode;
    }

    public void setCommentCode(String commentCode)
    {
        this.commentCode = commentCode;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getAssociationCode() {
        return associationCode;
    }

    public void setAssociationCode(String associationCode) {
        this.associationCode = associationCode;
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
        if (!(object instanceof MicroInstrumentComment)) {
            return false;
        }
        MicroInstrumentComment other = (MicroInstrumentComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.microInstrumentComments[ id=" + id + ", commentCode =" + commentCode +" ]";
    }    
    
}
