
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
@Table(name = "`mixedAnswerTests`", catalog = "css", schema = "")
public class MixedAnswerTest implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "testId")
    private Integer testId;
    @Basic(optional = false)
    @Column(name = "mixedAnswerOptionId")
    private int mixedAnswerOptionId;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    
    public MixedAnswerTest() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public int getMixedAnswerOptionId() {
        return mixedAnswerOptionId;
    }

    public void setMixedAnswerOptionId(int mixedAnswerOptionId) {
        this.mixedAnswerOptionId = mixedAnswerOptionId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MixedAnswerTest)) {
            return false;
        }
        MixedAnswerTest other = (MixedAnswerTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MixedAnswerTest[ id=" + id + " ]";
    }
}