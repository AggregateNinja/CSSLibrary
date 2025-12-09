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
    @NamedQuery(name = "Prescriptions.findAll", query = "SELECT p FROM patientPrescriptions p")})
public class PatientPrescriptions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPatientPrescriptions")
    private Integer idPatientPrescriptions;
    @Basic(optional = false)
    @Column(name = "patientId")
    private int patientId;
    @Column(name = "drugId")
    private int drugId;
    @Column(name = "isActive")
    private Boolean isActive;
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    public PatientPrescriptions() {
    }

    public PatientPrescriptions(Integer idPatientPrescriptions) {
        this.idPatientPrescriptions = idPatientPrescriptions;
    }

    public PatientPrescriptions(Integer idPatientPrescriptions, int patientId) {
        this.idPatientPrescriptions = idPatientPrescriptions;
        this.patientId = patientId;
    }
    
    public PatientPrescriptions(int patientId, int drugId) {
        this.patientId = patientId;
        this.drugId = drugId;
    }

    public Integer getIdPatientPrescriptions() {
        return idPatientPrescriptions;
    }

    public void setIdPatientPrescriptions(Integer idPatientPrescriptions) {
        this.idPatientPrescriptions = idPatientPrescriptions;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }
    
    @Diff(fieldName="isActive")
    public boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(boolean isActive)
    {
        this.isActive = isActive;
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
        hash += (idPatientPrescriptions != null ? idPatientPrescriptions.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientPrescriptions)) {
            return false;
        }
        PatientPrescriptions other = (PatientPrescriptions) object;
        if ((this.idPatientPrescriptions == null && other.idPatientPrescriptions != null) || (this.idPatientPrescriptions != null && !this.idPatientPrescriptions.equals(other.idPatientPrescriptions))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.PatientPrescriptions[ idPatientPrescriptions=" + idPatientPrescriptions + " ]";
    }
    
}
