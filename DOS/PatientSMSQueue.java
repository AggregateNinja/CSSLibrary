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
 * @author Edd
 */
@Entity
@Table(name = "patientSMSQueue")
public class PatientSMSQueue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idSMSQueue")
    private Integer idSMSQueue;
    
    @Basic(optional = false)
    @Column(name = "orderId")
    private Integer orderId;
    
    @Basic(optional = false)
    @Column(name = "patientId")
    private Integer patientId;
    
    @Basic(optional = false)
    @Column(name = "messageTypeId")
    private Integer messageTypeId;
    
    @Column(name = "sent")
    private Boolean sent;
    
    @Basic(optional = false)
    @Column(name = "dateCreated", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    
    // log data
    private Integer userId;
    private Integer logTypeId;
    
    private Patients patient;
    
    public PatientSMSQueue(Integer idSMSQueue, Integer orderId, Integer patientId, Boolean sent, Date dateCreated, Integer userId, Patients patient) {
        this.idSMSQueue = idSMSQueue;
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = sent;
        this.dateCreated = dateCreated;
        this.userId = userId;
        this.patient = patient;
    }
    
    public PatientSMSQueue(Integer idSMSQueue, Integer orderId, Integer patientId, Boolean sent, Date dateCreated, Integer userId) {
        this.idSMSQueue = idSMSQueue;
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = sent;
        this.dateCreated = dateCreated;
        this.userId = userId;
    }
    
    public PatientSMSQueue(Integer idSMSQueue, Integer orderId, Integer patientId, Boolean sent, Date dateCreated) {
        this.idSMSQueue = idSMSQueue;
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = sent;
        this.dateCreated = dateCreated;
    }
    
    public PatientSMSQueue(Integer orderId, Integer patientId) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = false;
    }
    
    public PatientSMSQueue(Integer orderId, Integer patientId, Integer userId, String arNo, String lastName, String firstName, String middleName, String phone) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = false;
        
        this.userId = userId;
        
        this.patient = new Patients(patientId, arNo, lastName, firstName, middleName, phone);
    }
    
    public PatientSMSQueue(Integer orderId, Integer patientId, Integer userId, String arNo, String lastName, String firstName, String middleName, String phone, Integer messageTypeId) {
        this.orderId = orderId;
        this.patientId = patientId;
        this.sent = false;
        
        this.userId = userId;
        
        this.messageTypeId = messageTypeId;
        
        this.patient = new Patients(patientId, arNo, lastName, firstName, middleName, phone);
    }
    
    public PatientSMSQueue() {
        
    }
    
    public Integer getIdSMSQueue() {
        return this.idSMSQueue;
    }
    public Integer getOrderId() {
        return this.orderId;
    }
    public Integer getPatientId() {
        return this.patientId;
    }
    public Integer getMessageTypeId() {
        return this.messageTypeId;
    }
    public Boolean getSent() {
        return this.sent;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    public Integer getUserId() {
        return this.userId;
    }
    public Patients getPatient() {
        return this.patient;
    }
    public Integer getLogTypeId() {
        return this.logTypeId;
    }
    
    public void setIdSMSQueue(Integer idSMSQueue) {
        this.idSMSQueue = idSMSQueue;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
    public void setMessageTypeId(Integer messageTypeId) {
        this.messageTypeId = messageTypeId;
    }
    public void setSent(Boolean sent) {
        this.sent = sent;
    }
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public void setPatient(Patients patient) {
        this.patient = patient;
    }
    public void setLogTypeId(Integer logTypeId) {
        this.logTypeId = logTypeId;
    }
}
