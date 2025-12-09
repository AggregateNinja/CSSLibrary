/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import Utility.Diff;
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

/**
 *
 * @author eboss
 */
@Entity
@Table(name = "patientPrescriptions")
@NamedQueries({
    @NamedQuery(name = "PrescriptionLog.findAll", query = "SELECT l FROM patientPrescriptionLog l")})
public class PatientPrescriptionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPatientPrescriptionLog")
    private Integer idPatientPrescriptionLog;
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "drugId")
    private int drugId;
    @Basic(optional = false)
    @Column(name = "patientId")
    private int patientId;
    @Column(name = "action")
    private String action;
    @Column(name = "description")
    private String description;
    @Column(name = "performedByUserId")
    private int performedByUserId;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    public PatientPrescriptionLog() {
    
    }
    
    public Integer getIdPatientPrescriptionLog() {
        return idPatientPrescriptionLog;
    }

    public void setIdPatientPrescriptionLog(Integer idPatientPrescriptionLog) {
        this.idPatientPrescriptionLog = idPatientPrescriptionLog;
    }
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }
    
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }    
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getPerformedByUserId() {
        return performedByUserId;
    }

    public void setPerformedByUserId(int performedByUserId) {
        this.performedByUserId = performedByUserId;
    }
    
    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPatientPrescriptionLog != null ? idPatientPrescriptionLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientPrescriptionLog)) {
            return false;
        }
        PatientPrescriptionLog other = (PatientPrescriptionLog) object;
        if ((this.idPatientPrescriptionLog == null && other.idPatientPrescriptionLog != null) || (this.idPatientPrescriptionLog != null && !this.idPatientPrescriptionLog.equals(other.idPatientPrescriptionLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.PatientPrescriptionLog[ idPatientPrescriptionLog=" + idPatientPrescriptionLog + " ]";
    }
}
