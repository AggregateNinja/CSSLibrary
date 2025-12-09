/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 8, 2014
 */

package DOS;

import Utility.Diff;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @date:   Jul 8, 2014  10:19:59 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: Salesmen.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "salesmen")
@NamedQueries({
    @NamedQuery(name = "Salesmen.findAll", query = "SELECT s FROM Salesmen s")})
public class Salesmen implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalesmen")
    private Integer idsalesmen;
    @Basic(optional = false)
    @Column(name = "employeeID")
    private int employeeID;
    @Basic(optional = false)
    @Column(name = "commisionRate")
    private BigDecimal commisionRate;
    @Basic(optional = false)
    @Column(name = "territory")
    private int territory;
    @Basic(optional = false)
    @Column(name = "classification")
    private int classification;
    @Basic(optional = false)
    @Column(name = "salesgroup")
    private int salesGroup;
    @Basic(optional = false)
    @Column(name = "byOrders")
    private boolean byOrders;
    @Basic(optional = false)
    @Column(name = "byTests")
    private boolean byTests;
    @Basic(optional = false)
    @Column(name = "byBilled")
    private boolean byBilled;
    @Basic(optional = false)
    @Column(name = "byReceived")
    private boolean byReceived;
    @Basic(optional = false)
    @Column(name = "byGroup")
    private boolean byGroup;
    @Basic(optional = false)
    @Column(name = "byPercentage")
    private boolean byPercentage;
    @Basic(optional = false)
    @Column(name = "byAmount")
    private boolean byAmount;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private int createdBy;

    public Salesmen() {
    }

    public Salesmen(Integer idsalesmen) {
        this.idsalesmen = idsalesmen;
    }

    public Salesmen(Integer idsalesmen, BigDecimal commisionRate, int classification, boolean byOrders, boolean byTests, boolean byBilled, boolean byReceived, boolean byGroup, boolean byPercentage, boolean byAmount, Date created, int employeeID, int createdBy) {
        this.idsalesmen = idsalesmen;
        this.commisionRate = commisionRate;
        this.classification = classification;
        this.byOrders = byOrders;
        this.byTests = byTests;
        this.byBilled = byBilled;
        this.byReceived = byReceived;
        this.byGroup = byGroup;
        this.byPercentage = byPercentage;
        this.byAmount = byAmount;
        this.created = created;
        this.employeeID = employeeID;
        this.createdBy = createdBy;
    }

    public Integer getIdsalesmen() {
        return idsalesmen;
    }

    public void setIdsalesmen(Integer idsalesmen) {
        this.idsalesmen = idsalesmen;
    }

    @Diff(fieldName="commissionRate")
    public BigDecimal getCommisionRate() {
        return commisionRate;
    }

    public void setCommisionRate(BigDecimal commisionRate) {
        this.commisionRate = commisionRate;
    }

    @Diff(fieldName="territory")
    public int getTerritory() {
        return territory;
    }

    public void setTerritory(int territory) {
        this.territory = territory;
    }

    @Diff(fieldName="classification")
    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    @Diff(fieldName="salesGroup")
    public int getSalesGroup() {
        return salesGroup;
    }

    public void setSalesGroup(int salesGroup) {
        this.salesGroup = salesGroup;
    }

    @Diff(fieldName="byOrders")
    public boolean getByOrders() {
        return byOrders;
    }

    public void setByOrders(boolean byOrders) {
        this.byOrders = byOrders;
    }

    @Diff(fieldName="byTests")
    public boolean getByTests() {
        return byTests;
    }

    public void setByTests(boolean byTests) {
        this.byTests = byTests;
    }

    @Diff(fieldName="byBilled")
    public boolean getByBilled() {
        return byBilled;
    }

    public void setByBilled(boolean byBilled) {
        this.byBilled = byBilled;
    }

    @Diff(fieldName="byReceived")
    public boolean getByReceived() {
        return byReceived;
    }

    public void setByReceived(boolean byReceived) {
        this.byReceived = byReceived;
    }

    @Diff(fieldName="byGroup")
    public boolean getByGroup() {
        return byGroup;
    }

    public void setByGroup(boolean byGroup) {
        this.byGroup = byGroup;
    }

    @Diff(fieldName="byPercentage")
    public boolean getByPercentage() {
        return byPercentage;
    }

    public void setByPercentage(boolean byPercentage) {
        this.byPercentage = byPercentage;
    }

    @Diff(fieldName="byAmount")
    public boolean getByAmount() {
        return byAmount;
    }

    public void setByAmount(boolean byAmount) {
        this.byAmount = byAmount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Diff(fieldName="employeeID")
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    @Diff(fieldName="createdBy")
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsalesmen != null ? idsalesmen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salesmen)) {
            return false;
        }
        Salesmen other = (Salesmen) object;
        if ((this.idsalesmen == null && other.idsalesmen != null) || (this.idsalesmen != null && !this.idsalesmen.equals(other.idsalesmen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Salesmen[ idsalesmen=" + idsalesmen + " ]";
    }

}
