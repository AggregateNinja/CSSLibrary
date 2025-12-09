/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 08/29/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "clientDoctorRelationship")
@NamedQueries({
    @NamedQuery(name = "ClientDoctorRelationship.findAll", query = "SELECT c FROM ClientDoctorRelationship c")})
public class ClientDoctorRelationship implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idclient_doctor_relationship")
    private Integer idclientDoctorRelationship;
    @Basic(optional = false)
    @Column(name = "idclients")
    private int idclients;
    @Basic(optional = false)
    @Column(name = "iddoctors")
    private int iddoctors;

    public ClientDoctorRelationship() {
    }

    public ClientDoctorRelationship(Integer idclientDoctorRelationship) {
        this.idclientDoctorRelationship = idclientDoctorRelationship;
    }

    public ClientDoctorRelationship(Integer idclientDoctorRelationship, int idclients, int iddoctors) {
        this.idclientDoctorRelationship = idclientDoctorRelationship;
        this.idclients = idclients;
        this.iddoctors = iddoctors;
    }

    public Integer getIdclientDoctorRelationship() {
        return idclientDoctorRelationship;
    }

    public void setIdclientDoctorRelationship(Integer idclientDoctorRelationship) {
        this.idclientDoctorRelationship = idclientDoctorRelationship;
    }

    public int getIdClients() {
        return idclients;
    }

    public void setIdClients(int idclients) {
        this.idclients = idclients;
    }

    public int getIdDoctors() {
        return iddoctors;
    }

    public void setIdDoctors(int iddoctors) {
        this.iddoctors = iddoctors;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idclientDoctorRelationship != null ? idclientDoctorRelationship.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientDoctorRelationship)) {
            return false;
        }
        ClientDoctorRelationship other = (ClientDoctorRelationship) object;
        if ((this.idclientDoctorRelationship == null && other.idclientDoctorRelationship != null) || (this.idclientDoctorRelationship != null && !this.idclientDoctorRelationship.equals(other.idclientDoctorRelationship))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.ClientDoctorRelationship[ idclientDoctorRelationship=" + idclientDoctorRelationship + " ]";
    }

}
