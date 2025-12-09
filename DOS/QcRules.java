/*
 * Computer Service & Support, Inc.  All Rights Reserved May 28, 2014
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   May 28, 2014  5:38:12 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcRules.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcRules")
@NamedQueries({
    @NamedQuery(name = "QcRules.findAll", query = "SELECT q FROM QcRules q")})
public class QcRules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcRules")
    private Integer idqcRules;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "runs")
    private int runs;
    @Basic(optional = false)
    @Column(name = "isPercentage")
    private boolean isPercentage;
    @Basic(optional = false)
    @Column(name = "isWestGuard")
    private boolean isWestGuard;
    @Basic(optional = false)
    @Column(name = "isCustom")
    private boolean isCustom;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "createdBy")
    private Integer createdBy;
    @Column(name = "value")
    private Integer value;
    @Column(name = "direction")
    private String direction;
    @Column(name = "type")
    private String type;

    public QcRules() {
    }

    public QcRules(Integer idqcRules) {
        this.idqcRules = idqcRules;
    }

    public QcRules(Integer idqcRules, String name, int runs, boolean isPercentage, boolean isWestGuard, boolean isCustom, Date created, Integer value, String direction, String type) {
        this.idqcRules = idqcRules;
        this.name = name;
        this.runs = runs;
        this.isPercentage = isPercentage;
        this.isWestGuard = isWestGuard;
        this.isCustom = isCustom;
        this.created = created;
        this.value = value;
        this.direction = direction;
        this.type = type;
    }

    public Integer getIdqcRules() {
        return idqcRules;
    }

    public void setIdqcRules(Integer idqcRules) {
        this.idqcRules = idqcRules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public boolean getIsPercentage() {
        return isPercentage;
    }

    public void setIsPercentage(boolean isPercentage) {
        this.isPercentage = isPercentage;
    }

    public boolean getIsWestGuard() {
        return isWestGuard;
    }

    public void setIsWestGuard(boolean isWestGuard) {
        this.isWestGuard = isWestGuard;
    }

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcRules != null ? idqcRules.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcRules)) {
            return false;
        }
        QcRules other = (QcRules) object;
        if ((this.idqcRules == null && other.idqcRules != null) || (this.idqcRules != null && !this.idqcRules.equals(other.idqcRules))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcRules[ idqcRules=" + idqcRules + " ]";
    }

}
