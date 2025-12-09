package EMR.DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @date:   Jul 10, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DOS
 * @file name: EmrApprovalLog.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "emrApprovalLog")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "EmrApprovalLog.findAll", query = "SELECT e FROM EmrApprovalLog e"),
    @NamedQuery(name = "EmrApprovalLog.findById", query = "SELECT e FROM EmrApprovalLog e WHERE e.id = :id"),
    @NamedQuery(name = "EmrApprovalLog.findByIdOrders", query = "SELECT e FROM EmrApprovalLog e WHERE e.idOrders = :idOrders"),
    @NamedQuery(name = "EmrApprovalLog.findByIdUser", query = "SELECT e FROM EmrApprovalLog e WHERE e.idUser = :idUser"),
    @NamedQuery(name = "EmrApprovalLog.findByDateApproved", query = "SELECT e FROM EmrApprovalLog e WHERE e.dateApproved = :dateApproved"),
    @NamedQuery(name = "EmrApprovalLog.findByApprovedAcc", query = "SELECT e FROM EmrApprovalLog e WHERE e.approvedAcc = :approvedAcc"),
    @NamedQuery(name = "EmrApprovalLog.findByApprovedPatID", query = "SELECT e FROM EmrApprovalLog e WHERE e.approvedPatID = :approvedPatID"),
    @NamedQuery(name = "EmrApprovalLog.findByRejectCodes", query = "SELECT e FROM EmrApprovalLog e WHERE e.rejectCodes = :rejectCodes")
})
public class EmrApprovalLog implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idOrders")
    private int idOrders;
    @Basic(optional = false)
    @Column(name = "idUser")
    private int idUser;
    @Basic(optional = false)
    @Column(name = "dateApproved")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;
    @Column(name = "approvedAcc")
    private String approvedAcc;
    @Column(name = "approvedPatID")
    private String approvedPatID;
    @Column(name = "approvedSubID")
    private String approvedSubID;
    @Column(name = "rejectCodes")
    private String rejectCodes;
    @Column(name = "emrOrderId")
    private String emrOrderId;
    @Column(name = "emrPatientId")
    private String emrSubscriberId;
    @Column(name = "emrSubscriberId")
    private String emrPatientId;
    @Column(name = "dailyRunNo")
    private int dailyRunNo;
    @Column(name = "idEmrXref")
    private Integer idEmrXref;
    @Column(name = "idClients")
    private Integer idClients;
    

    public EmrApprovalLog()
    {
    }

    public EmrApprovalLog(Integer id)
    {
        this.id = id;
    }

    public EmrApprovalLog(Integer id, int idOrders, int idUser, Date dateApproved)
    {
        this.id = id;
        this.idOrders = idOrders;
        this.idUser = idUser;
        this.dateApproved = dateApproved;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public int getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(int idOrders)
    {
        this.idOrders = idOrders;
    }

    public int getIdUser()
    {
        return idUser;
    }

    public void setIdUser(int idUser)
    {
        this.idUser = idUser;
    }

    public Date getDateApproved()
    {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved)
    {
        this.dateApproved = dateApproved;
    }

    public String getApprovedAcc()
    {
        return approvedAcc;
    }

    public void setApprovedAcc(String approvedAcc)
    {
        this.approvedAcc = approvedAcc;
    }

    public String getApprovedPatID()
    {
        return approvedPatID;
    }

    public void setApprovedPatID(String approvedPatID)
    {
        this.approvedPatID = approvedPatID;
    }

    public String getApprovedSubID()
    {
        return approvedSubID;
    }
    
    public void setApprovedSubID(String approvedSubID)
    {
        this.approvedSubID = approvedSubID;
    }
    
    public String getRejectCodes()
    {
        return rejectCodes;
    }

    public void setRejectCodes(String rejectCodes)
    {
        this.rejectCodes = rejectCodes;
    }

    public String getEmrOrderId() {
        return emrOrderId;
    }

    public void setEmrOrderId(String emrOrderId) {
        this.emrOrderId = emrOrderId;
    }

    public String getEmrPatientId() {
        return emrPatientId;
    }

    public void setEmrPatientId(String emrPatientId) {
        this.emrPatientId = emrPatientId;
    }
    
    public String getEmrSubscriberId() {
        return emrSubscriberId;
    }

    public void setEmrSubscriberId(String emrSubscriberId) {
        this.emrSubscriberId = emrSubscriberId;
    }

    public int getDailyRunNo() {
        return dailyRunNo;
    }

    public void setDailyRunNo(int dailyRunNo) {
        this.dailyRunNo = dailyRunNo;
    }

    public Integer getIdEmrXref() {
        return idEmrXref;
    }

    public void setIdEmrXref(Integer idEmrXref) {
        this.idEmrXref = idEmrXref;
    }

    public Integer getIdClients() {
        return idClients;
    }

    public void setIdClients(Integer idClients) {
        this.idClients = idClients;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrApprovalLog))
        {
            return false;
        }
        EmrApprovalLog other = (EmrApprovalLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.EmrApprovalLog[ id=" + id + " ]";
    }

}
