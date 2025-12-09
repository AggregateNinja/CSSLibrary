/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Ryan
 */
@Entity
@Table(name = "qcTestInfo")
@NamedQueries({
    @NamedQuery(name = "QcTestInfo.findAll", query = "SELECT q FROM QcTestInfo q")})
public class QcTestInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcTests")
    private Integer idqcTests;
    @Column(name = "reagent")
    private Integer reagent;
    @Column(name = "control1")
    private Integer control1;
    @Column(name = "control2")
    private Integer control2;
    @Column(name = "control3")
    private Integer control3;
    @Column(name = "control4")
    private Integer control4;
    @Column(name = "control5")
    private Integer control5;
    @Column(name = "control6")
    private Integer control6;
    @Column(name = "control7")
    private Integer control7;
    @Column(name = "control8")
    private Integer control8;
    @Column(name = "control9")
    private Integer control9;
    @Column(name = "control10")
    private Integer control10;
    @Column(name = "controlRuns")
    private Integer controlRuns;
    
    public QcTestInfo() {
    }

    public QcTestInfo(Integer idqcTests) {
        this.idqcTests = idqcTests;
    }

    public Integer getIdqcTests() {
        return idqcTests;
    }

    public void setIdqcTests(Integer idqcTests) {
        this.idqcTests = idqcTests;
    }

    public Integer getReagent() {
        return reagent;
    }

    public void setReagent(Integer reagent) {
        this.reagent = reagent;
    }

    public Integer getControl1() {
        return control1;
    }

    public void setControl1(Integer control1) {
        this.control1 = control1;
    }

    public Integer getControl2() {
        return control2;
    }

    public void setControl2(Integer control2) {
        this.control2 = control2;
    }

    public Integer getControl3() {
        return control3;
    }

    public void setControl3(Integer control3) {
        this.control3 = control3;
    }

    public Integer getControl4() {
        return control4;
    }

    public void setControl4(Integer control4) {
        this.control4 = control4;
    }

    public Integer getControl5() {
        return control5;
    }

    public void setControl5(Integer control5) {
        this.control5 = control5;
    }

    public Integer getControl6() {
        return control6;
    }

    public void setControl6(Integer control6) {
        this.control6 = control6;
    }

    public Integer getControl7() {
        return control7;
    }

    public void setControl7(Integer control7) {
        this.control7 = control7;
    }

    public Integer getControl8() {
        return control8;
    }

    public void setControl8(Integer control8) {
        this.control8 = control8;
    }

    public Integer getControl9() {
        return control9;
    }

    public void setControl9(Integer control9) {
        this.control9 = control9;
    }

    public Integer getControl10() {
        return control10;
    }

    public void setControl10(Integer control10) {
        this.control10 = control10;
    }

    
    
    public Integer getControlRuns() {
        return controlRuns;
    }

    public void setControlRuns(Integer controlRuns) {
        this.controlRuns = controlRuns;
    } 

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcTests != null ? idqcTests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcTestInfo)) {
            return false;
        }
        QcTestInfo other = (QcTestInfo) object;
        if ((this.idqcTests == null && other.idqcTests != null) || (this.idqcTests != null && !this.idqcTests.equals(other.idqcTests))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcTestInfo[ idqcTests=" + idqcTests + " ]";
    }
    
}
