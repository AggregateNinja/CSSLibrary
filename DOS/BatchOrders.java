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
@Table(name = "batchOrders", catalog = "css", schema = "")
public class BatchOrders implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idBatchOrders")
    private Integer idBatchOrders;
    @Basic(optional = false)
    @Column(name = "batchId")
    private int batchId;
    @Basic(optional = false)
    @Column(name = "accession")
    private String accession;
    @Basic(optional = false)
    @Column(name = "userId")
    private int userId;
    @Column(name = "well")
    private String well;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Column(name = "filename")
    private String filename;
    @Column(name = "createdDate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdDate;
    
    public BatchOrders() {};

    public Integer getIdBatchOrders() {
        return idBatchOrders;
    }

    public void setIdBatchOrders(int idBatchOrders) {
        this.idBatchOrders = idBatchOrders;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public String getAccession()
    {
        return accession;
    }

    public void setAccession(String accession)
    {
        this.accession = accession;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getWell()
    {
        return well;
    }

    public void setWell(String well)
    {
        this.well = well;
    }
    
    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
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
        hash += (idBatchOrders != null ? idBatchOrders.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BatchOrders)) {
            return false;
        }
        BatchOrders other = (BatchOrders) object;
        if ((this.idBatchOrders == null && other.idBatchOrders != null) || (this.idBatchOrders != null && !this.idBatchOrders.equals(other.idBatchOrders))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.BatchOrders[ id=" + idBatchOrders + " ]";
    }    
}
