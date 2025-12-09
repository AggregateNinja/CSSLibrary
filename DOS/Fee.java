
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 26, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DOS
 * @file name: Fee.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "fee")
@NamedQueries({
    @NamedQuery(name = "Fee.findAll", query = "SELECT f FROM Fee f")})
public class Fee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFee")
    private Integer idFee;
    @Basic(optional = false)
    @Column(name = "feeName")
    private String feeName;
    @Basic(optional = false)
    @Column(name = "test")
    private int test;
    @Basic(optional = false)
    @Column(name = "fee")
    private double fee;
    @Column(name = "panel")
    private Integer panel;

    public Fee() {
    }

    public Fee(Integer idFee) {
        this.idFee = idFee;
    }

    public Fee(Integer idFee, String feeName, int test, double fee) {
        this.idFee = idFee;
        this.feeName = feeName;
        this.test = test;
        this.fee = fee;
    }

    public Integer getIdFee() {
        return idFee;
    }

    public void setIdFee(Integer idFee) {
        this.idFee = idFee;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Integer getPanel() {
        return panel;
    }

    public void setPanel(Integer panel) {
        this.panel = panel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFee != null ? idFee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fee)) {
            return false;
        }
        Fee other = (Fee) object;
        if ((this.idFee == null && other.idFee != null) || (this.idFee != null && !this.idFee.equals(other.idFee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Fee[ idFee=" + idFee + " ]";
    }

}
