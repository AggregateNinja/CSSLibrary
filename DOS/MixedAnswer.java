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
@Table(name = "mixedAnswers", catalog = "css", schema = "")
public class MixedAnswer implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "mixedAnswerOptionSetId")
    private Integer mixedAnswerOptionSetId;
    @Basic(optional = false)
    @Column(name = "mixedAnswerOptionId")
    private int mixedAnswerOptionId;
    @Basic(optional = false)
    @Column(name = "answerOrder")
    private int answerOrder;
    
    public MixedAnswer() {};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMixedAnswerOptionSetId() {
        return mixedAnswerOptionSetId;
    }

    public void setMixedAnswerOptionSetId(Integer mixedAnswerOptionSetId) {
        this.mixedAnswerOptionSetId = mixedAnswerOptionSetId;
    }

    public int getMixedAnswerOptionId() {
        return mixedAnswerOptionId;
    }

    public void setMixedAnswerOptionId(int mixedAnswerOptionId) {
        this.mixedAnswerOptionId = mixedAnswerOptionId;
    }

    public int getAnswerOrder() {
        return answerOrder;
    }

    public void setAnswerOrder(int answerOrder) {
        this.answerOrder = answerOrder;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MixedAnswer)) {
            return false;
        }
        MixedAnswer other = (MixedAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MixedAnswer[ id=" + id + " ]";
    }    
}
