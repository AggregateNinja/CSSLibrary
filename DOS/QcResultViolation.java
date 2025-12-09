/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 8, 2014
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Jun 8, 2014  9:09:31 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: QcResultViolation.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "qcResultViolation", catalog = "vessel", schema = "")
@NamedQueries({
    @NamedQuery(name = "QcResultViolation.findAll", query = "SELECT q FROM QcResultViolation q")})
public class QcResultViolation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idqcResults")
    private Integer idqcResults;
    @Basic(optional = false)
    @Column(name = "idqcRules")
    private Integer idqcRules;

    public QcResultViolation() {
    }

    public QcResultViolation(Integer id) {
        this.id = id;
    }

    public QcResultViolation(Integer id, Integer idqcResults, Integer idqcRules) {
        this.id = id;
        this.idqcResults = idqcResults;
        this.idqcRules = idqcRules;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdqcResults() {
        return idqcResults;
    }

    public void setIdqcResults(Integer idqcResults) {
        this.idqcResults = idqcResults;
    }

    public Integer getIdqcRules() {
        return idqcRules;
    }

    public void setIdqcRules(Integer idqcRules) {
        this.idqcRules = idqcRules;
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
        if (!(object instanceof QcResultViolation)) {
            return false;
        }
        QcResultViolation other = (QcResultViolation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.QcResultViolation[ id=" + id + " ]";
    }

}
