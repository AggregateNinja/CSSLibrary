/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 13, 2014
 */

package DOS;

import static Utility.DateUtil.dateBetween;
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
import org.joda.time.LocalDate;

/**
 * @date:   Jun 13, 2014  2:56:14 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: ExtranormalsLog.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "extranormals_log")
@NamedQueries({
    @NamedQuery(name = "ExtranormalsLog.findAll", query = "SELECT e FROM extranormals_log e")})
public class ExtraNormalsLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "EventType")
    private String eventType;
    @Column(name = "ChangeType")
    private String changeType;
    @Basic(optional = false)
    @Column(name = "testNumber")
    private int testNumber;
    @Column(name = "testName")
    private String testName;
    @Column(name = "idtests")
    private Integer idtests;
    @Basic(optional = false)
    @Column(name = "oldIdExtraNormals")
    private int oldIdExtraNormals;
    @Basic(optional = false)
    @Column(name = "newIdExtraNormals")
    private int newIdExtraNormals;
    @Basic(optional = false)
    @Column(name = "species")
    private int species;
    @Basic(optional = false)
    @Column(name = "lowNormal")
    private double lowNormal;
    @Basic(optional = false)
    @Column(name = "highNormal")
    private double highNormal;
    @Basic(optional = false)
    @Column(name = "alertLow")
    private double alertLow;
    @Basic(optional = false)
    @Column(name = "alertHigh")
    private double alertHigh;
    @Basic(optional = false)
    @Column(name = "criticalLow")
    private double criticalLow;
    @Basic(optional = false)
    @Column(name = "criticalHigh")
    private double criticalHigh;
    
    @Basic(optional = false)
    @Column(name = "ageLow")
    private int ageLow;
    
    @Basic(optional = false)
    @Column(name = "ageUnitsLow")
    private int ageUnitsLow;
    
    @Basic(optional = false)
    @Column(name = "ageHigh")
    private int ageHigh;

    @Basic(optional = false)
    @Column(name = "ageUnitsHigh")
    private int ageUnitsHigh;
    
    @Column(name = "sex")
    private String sex;
    @Column(name = "printNormals")
    private String printNormals;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivatedDate;
    @Basic(optional = false)
    @Column(name = "user")
    private int user;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public ExtraNormalsLog() {
    }

    public ExtraNormalsLog(Integer id) {
        this.id = id;
    }

    public ExtraNormalsLog(Integer id, String eventType, int testNumber, int oldIdExtraNormals, int newIdExtraNormals, int species, double lowNormal, double highNormal, double alertLow, double alertHigh, double criticalLow, double criticalHigh, int ageLow, int ageUnitsLow, int ageHigh, int ageUnitsHigh, boolean active, int user, Date created) {
        this.id = id;
        this.eventType = eventType;
        this.testNumber = testNumber;
        this.oldIdExtraNormals = oldIdExtraNormals;
        this.newIdExtraNormals = newIdExtraNormals;
        this.species = species;
        this.lowNormal = lowNormal;
        this.highNormal = highNormal;
        this.alertLow = alertLow;
        this.alertHigh = alertHigh;
        this.criticalLow = criticalLow;
        this.criticalHigh = criticalHigh;
        this.ageLow = ageLow;
        this.ageUnitsLow = ageUnitsLow;
        this.ageHigh = ageHigh;
        this.ageUnitsHigh = ageUnitsHigh;
        this.active = active;
        this.user = user;
        this.created = created;
    }

    public ExtraNormalsLog(Integer id, String eventType, String changeType, int testNumber, String testName, Integer idtests, int oldIdExtraNormals, int newIdExtraNormals, int species, double lowNormal, double highNormal, double alertLow, double alertHigh, double criticalLow, double criticalHigh, int ageLow, int ageUnitsLow, int ageHigh, int ageUnitsHigh, String sex, String printNormals, boolean active, Date deactivatedDate, int user, Date created) {
        this.id = id;
        this.eventType = eventType;
        this.changeType = changeType;
        this.testNumber = testNumber;
        this.testName = testName;
        this.idtests = idtests;
        this.oldIdExtraNormals = oldIdExtraNormals;
        this.newIdExtraNormals = newIdExtraNormals;
        this.species = species;
        this.lowNormal = lowNormal;
        this.highNormal = highNormal;
        this.alertLow = alertLow;
        this.alertHigh = alertHigh;
        this.criticalLow = criticalLow;
        this.criticalHigh = criticalHigh;
        this.ageLow = ageLow;
        this.ageUnitsLow = ageUnitsLow;
        this.ageHigh = ageHigh;
        this.ageUnitsHigh = ageUnitsHigh;
        this.sex = sex;
        this.printNormals = printNormals;
        this.active = active;
        this.deactivatedDate = deactivatedDate;
        this.user = user;
        this.created = created;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getIdtests() {
        return idtests;
    }

    public void setIdtests(Integer idtests) {
        this.idtests = idtests;
    }

    public int getOldIdExtraNormals() {
        return oldIdExtraNormals;
    }

    public void setOldIdExtraNormals(int oldIdExtraNormals) {
        this.oldIdExtraNormals = oldIdExtraNormals;
    }

    public int getNewIdExtraNormals() {
        return newIdExtraNormals;
    }

    public void setNewIdExtraNormals(int newIdExtraNormals) {
        this.newIdExtraNormals = newIdExtraNormals;
    }

    public int getSpecies() {
        return species;
    }

    public void setSpecies(int species) {
        this.species = species;
    }

    public double getLowNormal() {
        return lowNormal;
    }

    public void setLowNormal(double lowNormal) {
        this.lowNormal = lowNormal;
    }

    public double getHighNormal() {
        return highNormal;
    }

    public void setHighNormal(double highNormal) {
        this.highNormal = highNormal;
    }

    public double getAlertLow() {
        return alertLow;
    }

    public void setAlertLow(double alertLow) {
        this.alertLow = alertLow;
    }

    public double getAlertHigh() {
        return alertHigh;
    }

    public void setAlertHigh(double alertHigh) {
        this.alertHigh = alertHigh;
    }

    public double getCriticalLow() {
        return criticalLow;
    }

    public void setCriticalLow(double criticalLow) {
        this.criticalLow = criticalLow;
    }

    public double getCriticalHigh() {
        return criticalHigh;
    }

    public void setCriticalHigh(double criticalHigh) {
        this.criticalHigh = criticalHigh;
    }

    public int getAgeLow() {
        return ageLow;
    }
    
    public void setAgeLow(int ageLow) {
        this.ageLow = ageLow;
    }

    public int getAgeHigh() {
        return ageHigh;
    }
        
    public void setAgeHigh(int ageHigh) {
        this.ageHigh = ageHigh;
    }
    
    public int getAgeUnitsLow() {
        return this.ageUnitsLow;
    }
    
    public int getAgeUnitsHigh() {
        return this.ageUnitsHigh;
    }
    
    public void setAgeUnitsLow(int ageUnitsLow) {
        this.ageUnitsLow = ageUnitsLow;
    }
    
    public void setAgeUnitsHigh(int ageUnitsHigh) {
        this.ageUnitsHigh = ageUnitsHigh;
    }
        
//     /**
//     * Checks if a date is between this ExtraNormalLog's low age year, month, and day and an high age year, month, and day with respect to the current date.
//     */
//    public boolean containsDate(LocalDate date) {
//        return false; //dateBetween(date, getAgeLow(), getAgeLowMonths(), getAgeLowDays(), getAgeHigh(), getAgeHighMonths(), getAgeHighDays());
//    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrintNormals() {
        return printNormals;
    }

    public void setPrintNormals(String printNormals) {
        this.printNormals = printNormals;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDeactivatedDate() {
        return deactivatedDate;
    }

    public void setDeactivatedDate(Date deactivatedDate) {
        this.deactivatedDate = deactivatedDate;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
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
        if (!(object instanceof ExtraNormalsLog)) {
            return false;
        }
        ExtraNormalsLog other = (ExtraNormalsLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.ExtranormalsLog[ id=" + id + " ]";
    }

}
