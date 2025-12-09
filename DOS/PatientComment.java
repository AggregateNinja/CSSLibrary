/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 29, 2014
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Jul 29, 2014  3:21:28 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: PatientComment.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "patientComment", catalog = "vessel2", schema = "")
@NamedQueries({
    @NamedQuery(name = "PatientComment.findAll", query = "SELECT p FROM PatientComment p")})
public class PatientComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idpatients")
    private Integer idpatients;
    @Basic(optional = false)
    @Lob
    @Column(name = "comment")
    private String comment;

    public PatientComment() {
    }

    public PatientComment(Integer id) {
        this.id = id;
    }

    public PatientComment(Integer id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdpatients() {
        return idpatients;
    }

    public void setIdpatients(Integer idpatients) {
        this.idpatients = idpatients;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof PatientComment)) {
            return false;
        }
        PatientComment other = (PatientComment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.PatientComment[ id=" + id + " ]";
    }

}
