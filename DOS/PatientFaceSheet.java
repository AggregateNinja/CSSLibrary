/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/05/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "patientFaceSheet")
@NamedQueries({
    @NamedQuery(name = "PatientFaceSheet.findAll", query = "SELECT p FROM PatientFaceSheet p")})
public class PatientFaceSheet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idpatients")
    private Integer idpatients;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Lob
    @Column(name = "faceSheet")
    private byte[] faceSheet;

    public PatientFaceSheet() {
    }

    public PatientFaceSheet(Integer idpatients) {
        this.idpatients = idpatients;
    }

    public PatientFaceSheet(Integer idpatients, String type, byte[] faceSheet) {
        this.idpatients = idpatients;
        this.type = type;
        this.faceSheet = faceSheet;
    }

    public Integer getIdpatients() {
        return idpatients;
    }

    public void setIdpatients(Integer idpatients) {
        this.idpatients = idpatients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getFaceSheet() {
        return faceSheet;
    }

    public void setFaceSheet(byte[] faceSheet) {
        this.faceSheet = faceSheet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpatients != null ? idpatients.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientFaceSheet)) {
            return false;
        }
        PatientFaceSheet other = (PatientFaceSheet) object;
        if ((this.idpatients == null && other.idpatients != null) || (this.idpatients != null && !this.idpatients.equals(other.idpatients))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.PatientFaceSheet[ idpatients=" + idpatients + " ]";
    }

}
