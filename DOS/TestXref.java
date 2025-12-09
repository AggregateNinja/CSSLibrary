/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
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
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 02/27/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "testXref")
@NamedQueries({
    @NamedQuery(name = "TestXref.findAll", query = "SELECT t FROM TestXref t")})
public class TestXref implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtestXref")
    private Integer idtestXref;
    @Basic(optional = false)
    @Column(name = "name")
    private int name;
    @Basic(optional = false)
    @Column(name = "testNumber")
    private int testNumber;
    @Column(name = "transformedIn")
    private String transformedIn;
    @Column(name = "transformedOut")
    private String transformedOut;
    @Column(name = "use1")
    private String use1;
    @Column(name = "use2")
    private String use2;
    @Column(name = "use3")
    private String use3;
    @Column(name = "use4")
    private String use4;
    @Column(name = "use5")
    private String use5;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "active")
    private boolean active;

    public TestXref() {
    }

    public TestXref(Integer idtestXref) {
        this.idtestXref = idtestXref;
    }

    public TestXref(Integer idtestXref, int name, int testNumber, Date created) {
        this.idtestXref = idtestXref;
        this.name = name;
        this.testNumber = testNumber;
        this.created = created;
    }

    @Diff(fieldName = "idtestXref", isUniqueId = true)
    public Integer getIdtestXref() {
        return idtestXref;
    }

    public void setIdtestXref(Integer idtestXref) {
        this.idtestXref = idtestXref;
    }

    @Diff(fieldName = "name")
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Diff(fieldName = "testNumber")
    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    @Diff(fieldName = "transformedIn")
    public String getTransformedIn() {
        return transformedIn;
    }

    public void setTransformedIn(String transformedIn) {
        this.transformedIn = transformedIn;
    }

    @Diff(fieldName = "transformedOut")
    public String getTransformedOut() {
        return transformedOut;
    }

    public void setTransformedOut(String transformedOut) {
        this.transformedOut = transformedOut;
    }

    @Diff(fieldName = "use1")
    public String getUse1() {
        return use1;
    }

    public void setUse1(String use1) {
        this.use1 = use1;
    }

    @Diff(fieldName = "use2")
    public String getUse2() {
        return use2;
    }

    public void setUse2(String use2) {
        this.use2 = use2;
    }

    @Diff(fieldName = "use3")
    public String getUse3() {
        return use3;
    }

    public void setUse3(String use3) {
        this.use3 = use3;
    }

    @Diff(fieldName = "use4")
    public String getUse4() {
        return use4;
    }

    public void setUse4(String use4) {
        this.use4 = use4;
    }

    @Diff(fieldName = "use5")
    public String getUse5() {
        return use5;
    }

    public void setUse5(String use5) {
        this.use5 = use5;
    }

    @Diff(fieldName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Diff(fieldName = "active")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
     

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtestXref != null ? idtestXref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestXref)) {
            return false;
        }
        TestXref other = (TestXref) object;
        if ((this.idtestXref == null && other.idtestXref != null) || (this.idtestXref != null && !this.idtestXref.equals(other.idtestXref))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.TestXref[ idtestXref=" + idtestXref + " ]";
    }

}
