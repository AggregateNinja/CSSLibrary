package DOS;

import java.util.Date;
import java.io.Serializable;

public class TransTypeLookup implements Serializable {

    private static final long serialversionUID = 42L;

    private Integer idTransTypeLookup;
    private Integer clientId;
    private Integer transTypeId;
    private Date addedDate;
    private Integer addedBy;
    private Date deactivatedDate;
    private Integer deactivatedBy;
    private Boolean active;

    public Integer getIdTransTypeLookup() {
        return idTransTypeLookup;
    }

    public void setIdTransTypeLookup(Integer idTransTypeLookup) {
        this.idTransTypeLookup = idTransTypeLookup;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getTransTypeId() {
        return transTypeId;
    }

    public void setTransTypeId(Integer transTypeId) {
        this.transTypeId = transTypeId;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    public Date getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public Integer getDeactivatedBy() {
        return deactivatedBy;
    }

    public void setDeactivatedBy(Integer deactivatedBy) {
        this.deactivatedBy = deactivatedBy;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
