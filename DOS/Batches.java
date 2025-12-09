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

@Entity
@Table(name = "batches", catalog = "css", schema = "")
public class Batches implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBatches")
    private Integer idBatches;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "userId")
    private int userId;
    @Column(name = "createdDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;
    
    public Batches() {};

    public Integer getIdBatches()
    {
        return idBatches;
    }

    public void setIdBatches(Integer idBatches)
    {
        this.idBatches = idBatches;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBatches != null ? idBatches.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Batches)) {
            return false;
        }
        Batches other = (Batches) object;
        if ((this.idBatches == null && other.idBatches != null) || (this.idBatches != null && !this.idBatches.equals(other.idBatches))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Batches[ id=" + idBatches + " ]";
    }    
}
