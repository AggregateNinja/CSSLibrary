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
@Table(name = "qcInstrumentInfo")
@NamedQueries({
    @NamedQuery(name = "QcInstrumentInfo.findAll", query = "SELECT q FROM QcInstrumentInfo q")})
public class QcInstrumentInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcInstrumentInfo")
    private Integer idqcInstrumentInfo;
    @Column(name = "idInst")
    private Integer idInst;
    @Basic(optional = false)
    @Column(name = "idqcRules")
    private Integer idqcRules;
    @Column(name = "idqcInstControls")
    private int idqcInstControls;


    public QcInstrumentInfo() {
    }

    public QcInstrumentInfo(Integer idqcInstrumentInfo) {
        this.idqcInstrumentInfo = idqcInstrumentInfo;
    }

    public QcInstrumentInfo(Integer idqcInstrumentInfo, int idqcInstControls) {
        this.idqcInstrumentInfo = idqcInstrumentInfo;
        this.idqcInstControls = idqcInstControls;
    }

    public Integer getIdqcInstrumentInfo() {
        return idqcInstrumentInfo;
    }

    public void setIdqcInstrumentInfo(Integer idqcInstrumentInfo) {
        this.idqcInstrumentInfo = idqcInstrumentInfo;
    }

    public Integer getIdInst() {
        return idInst;
    }

    public void setIdInst(Integer idInst) {
        this.idInst = idInst;
    }

    public Integer getIdqcRules() {
        return idqcRules;
    }

    public void setIdqcRules(Integer idqcRules) {
        this.idqcRules = idqcRules;
    }

    public int getIdqcInstControls() {
        return idqcInstControls;
    }

    public void setIdqcInstControls(int idqcInstControls) {
        this.idqcInstControls = idqcInstControls;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqcInstrumentInfo != null ? idqcInstrumentInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcInstrumentInfo)) {
            return false;
        }
        QcInstrumentInfo other = (QcInstrumentInfo) object;
        if ((this.idqcInstrumentInfo == null && other.idqcInstrumentInfo != null) || (this.idqcInstrumentInfo != null && !this.idqcInstrumentInfo.equals(other.idqcInstrumentInfo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcInstrumentInfo[ idqcInstrumentInfo=" + idqcInstrumentInfo + " ]";
    }
    
}
