/*
 * Computer Service & Support, Inc.  All Rights Reserved May 28, 2014
 */

package DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   May 28, 2014  5:38:12 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcLot.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcLot")
@NamedQueries({
    @NamedQuery(name = "QcLot.findAll", query = "SELECT q FROM QcLot q")})
public class QcLot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcLot")
    private Integer idqcLot;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "type")
    private Integer type;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "dateReceived")
    @Temporal(TemporalType.DATE)
    private Date dateReceived;
    @Basic(optional = false)
    @Column(name = "dateUsed")
    @Temporal(TemporalType.DATE)
    private Date dateUsed;
    @Basic(optional = false)
    @Column(name = "dateExpires")
    @Temporal(TemporalType.DATE)
    private Date dateExpires;
    @Column(name = "dateReconstitution")
    @Temporal(TemporalType.DATE)
    private Date dateReconstitution;
    @Column(name = "stability")
    private Integer stability;
    @Basic(optional = false)
    @Column(name = "quantity")
    private double quantity;
    @Basic(optional = false)
    @Column(name = "units")
    private int units;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @Basic(optional = false)
    @Column(name = "enteredBy")
    private Integer enteredBy;
    @Basic(optional = false)
    @Column(name = "enteredOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enteredOn;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Column(name = "activatedOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedOn;
    @Column(name = "activatedBy")
    private Integer activatedBy;
    @Basic(optional = false)
    @Column(name = "retired")
    private boolean retired;
    @Column(name = "retiredOn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date retiredOn;
    @Column(name = "retiredBy")
    private Integer retiredBy;
    @Basic(optional = false)
    @Column(name = "idspecimenType")
    private Integer idspecimenType;
    @Basic(optional = false)
    @Column(name = "lotNumber")
    private String lotNumber;
    @Column(name = "onlineCode")
    private String onlineCode;
    @Basic(optional = false)
    @Column(name = "percentageBased")
    private boolean percentageBased;
    @Column(name = "negative")
    private boolean negative;
    @Basic(optional = false)
    @Column(name = "idxrefs")
    private Integer idxrefs;
    @Basic(optional = false)
    @Column(name = "idmanufacturer")
    private Integer idmanufacturer;

    public QcLot() {
    }

    public QcLot(Integer idqcLot) {
        this.idqcLot = idqcLot;
    }

    public QcLot(Integer idqcLot, String name, int type, String description, Date dateReceived, Date dateUsed, Date dateExpires, double quantity, int units, boolean status, Date enteredOn, boolean active, boolean retired) {
        this.idqcLot = idqcLot;
        this.name = name;
        this.type = type;
        this.description = description;
        this.dateReceived = dateReceived;
        this.dateUsed = dateUsed;
        this.dateExpires = dateExpires;
        this.quantity = quantity;
        this.units = units;
        this.status = status;
        this.enteredOn = enteredOn;
        this.active = active;
        this.retired = retired;
    }

    public Integer getIdqcLot() {
        return idqcLot;
    }

    public void setIdqcLot(Integer idqcLot) {
        this.idqcLot = idqcLot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Date getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(Date dateUsed) {
        this.dateUsed = dateUsed;
    }

    public Date getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public Date getDateReconstitution() {
        return dateReconstitution;
    }

    public void setDateReconstitution(Date dateReconstitution) {
        this.dateReconstitution = dateReconstitution;
    }

    public Integer getStability() {
        return stability;
    }

    public void setStability(Integer stability) {
        this.stability = stability;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(Integer enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Date getEnteredOn() {
        return enteredOn;
    }

    public void setEnteredOn(Date enteredOn) {
        this.enteredOn = enteredOn;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Integer getActivatedBy() {
        return activatedBy;
    }

    public void setActivatedBy(Integer activatedBy) {
        this.activatedBy = activatedBy;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Date getRetiredOn() {
        return retiredOn;
    }

    public void setRetiredOn(Date retiredOn) {
        this.retiredOn = retiredOn;
    }

    public Integer getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Integer retiredBy) {
        this.retiredBy = retiredBy;
    }

    public Integer getIdspecimenType() {
        return idspecimenType;
    }

    public void setIdspecimenType(Integer idspecimenType) {
        this.idspecimenType = idspecimenType;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getOnlineCode()
    {
        return onlineCode;
    }

    public void setOnlineCode(String onlineCode)
    {
        this.onlineCode = onlineCode;
    }

    public boolean isPercentageBased() {
        return percentageBased;
    }

    public void setPercentageBased(boolean percentageBased) {
        this.percentageBased = percentageBased;
    }

    public boolean isNegative()
    {
        return negative;
    }

    public void setNegative(boolean negative)
    {
        this.negative = negative;
    }

    public Integer getIdxrefs() {
        return idxrefs;
    }

    public void setIdxrefs(Integer idxrefs) {
        this.idxrefs = idxrefs;
    }

    public Integer getIdmanufacturer() {
        return idmanufacturer;
    }

    public void setIdmanufacturer(Integer idmanufacturer) {
        this.idmanufacturer = idmanufacturer;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcLot != null ? idqcLot.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcLot)) {
            return false;
        }
        QcLot other = (QcLot) object;
        if ((this.idqcLot == null && other.idqcLot != null) || (this.idqcLot != null && !this.idqcLot.equals(other.idqcLot))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcLot[ idqcLot=" + idqcLot + " ]";
    }

}
