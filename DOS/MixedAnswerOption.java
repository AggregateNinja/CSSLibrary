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
@Table(name = "mixedAnswerOptions", catalog = "css", schema = "")
public class MixedAnswerOption implements Serializable, Comparable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "display")
    private String display;    
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "isAbnormal")
    private boolean isAbnormal;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    
    public MixedAnswerOption() {};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
        if (!(object instanceof MixedAnswerOption)) {
            return false;
        }
        MixedAnswerOption other = (MixedAnswerOption) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.MixedAnswerOption[ id=" + id + " ]";
    }    

    @Override
    public int compareTo(Object o)
    {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1; 
        try
        {
            MixedAnswerOption compare = (MixedAnswerOption)o;
            return this.getName().compareTo(compare.getName());
            
        } catch (Exception ex) { return 0; }
    }
}
