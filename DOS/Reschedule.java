package DOS;

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
 * @author TomR
 */
@Entity
@Table(name = "reschedules")
@NamedQueries({
    @NamedQuery(name = "reschedules.findAll", query = "SELECT r FROM reschedules r")})
public class Reschedule
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idReschedules")
    private Integer idReschedules;
    @Column(name = "phlebotomyId")
    private Integer phlebotomyId;
    @Column(name = "phebotomistId")
    private Integer phlebotomistId;
    @Column(name = "advancedOrderId")
    private Integer advancedOrderId;
    @Column(name = "clientId")
    private Integer clientId;
    @Column(name = "reassignedToPhlebotomistId")
    private Integer reassignedToPhlebotomistId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "originalDate")
    private Date originalDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rescheduledDate")
    private Date rescheduleDate;
    @Column(name = "rescheduleComment")
    private String rescheduleComment;
    @Basic(optional = false)
    @Column(name = "rescheduledBy")
    private int rescheduledBy;
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rescheduledOn")
    private Date rescheduledOn;
    
    private Type _type;
    
    public Reschedule() {}
    
    public enum Type
    {
        Undefined,
        Reschedule,
        NewDate,
        CancelledDate
    }

    public Integer getIdReschedules() {
        return idReschedules;
    }

    public void setIdReschedules(Integer idReschedules) {
        this.idReschedules = idReschedules;
    }

    public Integer getPhlebotomyId() {
        return phlebotomyId;
    }

    public void setPhlebotomyId(Integer phlebotomyId) {
        this.phlebotomyId = phlebotomyId;
    }

    public Integer getPhlebotomistId() {
        return phlebotomistId;
    }

    public void setPhlebotomistId(Integer phlebotomistId) {
        this.phlebotomistId = phlebotomistId;
    }
    
    public Integer getAdvancedOrderId() {
        return advancedOrderId;
    }
    
    public void setAdvancedOrderId(Integer advancedOrderId) {
        this.advancedOrderId = advancedOrderId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getReassignedToPhlebotomistId() {
        return reassignedToPhlebotomistId;
    }

    public void setReassignedToPhlebotomistId(Integer reassignedToPhlebotomistId) {
        if (phlebotomistId != null && reassignedToPhlebotomistId != null &&
                phlebotomistId.equals(reassignedToPhlebotomistId))
        {
            reassignedToPhlebotomistId = null;
        }
        this.reassignedToPhlebotomistId = reassignedToPhlebotomistId;
    }

    public Date getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(Date originalDate) {
        this.originalDate = originalDate;
    }

    public Date getRescheduleDate() {
        return rescheduleDate;
    }

    public void setRescheduleDate(Date rescheduleDate) {
        this.rescheduleDate = rescheduleDate;
    }

    public String getRescheduleComment() {
        return rescheduleComment;
    }

    public void setRescheduleComment(String rescheduleComment) {
        this.rescheduleComment = rescheduleComment;
    }

    public int getRescheduledBy() {
        return rescheduledBy;
    }

    public void setRescheduledBy(int rescheduledBy) {
        this.rescheduledBy = rescheduledBy;
    }

    public Date getRescheduledOn() {
        return rescheduledOn;
    }

    public void setRescheduledOn(Date rescheduledOn) {
        this.rescheduledOn = rescheduledOn;
    }

   
    
    public Type getType()
    {        
        if (originalDate != null && rescheduleDate != null) return Type.Reschedule;        
        if (originalDate == null && rescheduleDate != null) return Type.NewDate;
        if (originalDate != null && rescheduleDate == null) return Type.CancelledDate;
        return Type.Undefined;        
    }
    
    public boolean IsReassigned()
    {
        return (reassignedToPhlebotomistId != null && reassignedToPhlebotomistId > 0);
    }
    
    public boolean IsRescheduled()
    {
        return (originalDate != null && rescheduleDate != null && !originalDate.equals(rescheduleDate));
    }    
}
