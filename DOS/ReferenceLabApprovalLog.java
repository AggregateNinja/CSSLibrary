/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "referenceLabApprovalLog", catalog = "css", schema = "")        
public class ReferenceLabApprovalLog implements Serializable
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
    @Column(name = "deptNo")
    private int deptNo;
    @Basic(optional = false)
    @Column(name = "idUser")
    private int idUser;
    @Basic(optional = false)
    @Column(name = "dateApproved")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateApproved;
    @Column(name = "rejectCodes")
    private String rejectCodes;    
    @Column(name = "runNo")
    private int runNo;

    
    public ReferenceLabApprovalLog() {}
    
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

    public String getRejectCodes()
    {
        return rejectCodes;
    }

    public void setRejectCodes(String rejectCodes)
    {
        this.rejectCodes = rejectCodes;
    }

    public int getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(int deptNo) {
        this.deptNo = deptNo;
    }

    public int getRunNo() {
        return runNo;
    }

    public void setRunNo(int runNo) {
        this.runNo = runNo;
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
        if (!(object instanceof ReferenceLabApprovalLog))
        {
            return false;
        }
        ReferenceLabApprovalLog other = (ReferenceLabApprovalLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.ReferenceLabApprovalLog[ id=" + id + " ]";
    }
    
}
