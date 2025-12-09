/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "aoeOrderStatus", catalog = "css", schema = "")
public class AOEOrderStatus
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAoeOrderStatus", nullable = false)
    private Integer id;
    @Column(name = "orderId", nullable = false)
    private Integer orderId;
    @Column(name = "sendoutDepartmentNo", nullable = false)
    private Integer sendoutDepartmentNumber;
    @Column(name = "totalQuestionCount", nullable = false)
    private Integer totalQuestionCount;
    @Column(name = "unansweredQuestionCount", nullable = false)
    private Integer unansweredQuestionCount;
    @Column(name = "isMissingPatientData")
    private Boolean isMissingPatientData;
    @Column(name = "dataValid")
    private Boolean dataValid;
    @Column(name = "approved")
    private Boolean approved;

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Integer getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public void setTotalQuestionCount(Integer totalQuestionCount) {
        this.totalQuestionCount = totalQuestionCount;
    }

    public Integer getUnansweredQuestionCount()
    {
        return unansweredQuestionCount;
    }

    public void setUnansweredQuestionCount(Integer unansweredQuestionCount)
    {
        this.unansweredQuestionCount = unansweredQuestionCount;
    }

    public Boolean isMissingPatientData()
    {
        return isMissingPatientData;
    }

    public void setIsMissingPatientData(Boolean isMissingPatientData)
    {
        this.isMissingPatientData = isMissingPatientData;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer idOrders)
    {
        this.orderId = idOrders;
    }

    public Integer getSendoutDepartmentNumber()
    {
        return sendoutDepartmentNumber;
    }

    public void setSendoutDepartmentNumber(Integer sendoutDepartmentNumber) {
        this.sendoutDepartmentNumber = sendoutDepartmentNumber;
    }

    public Boolean isDataValid() {
        return dataValid;
    }

    public void setDataValid(Boolean dataValid) {
        this.dataValid = dataValid;
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
        if (!(object instanceof AOEAnswers)) {
            return false;
        }
        AOEAnswers other = (AOEAnswers) object;
        if ((this.id == null && other.getId() != null) || 
                (this.id != null && !this.id.equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.aoeOrderStatus[ id=" + id + ", idOrders=" + orderId +" ]";
    }        
    
}
