/*
 * Copyright (C) 2014 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
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
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 01/29/2014  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "loginLog")
@NamedQueries({
    @NamedQuery(name = "LoginLog.findAll", query = "SELECT l FROM LoginLog l")})
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idloginLog")
    private Integer idloginLog;
    @Basic(optional = false)
    @Column(name = "idUsers")
    private int idUsers;
    @Basic(optional = false)
    @Column(name = "login")
    private boolean login;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    public LoginLog() {
    }

    public LoginLog(Integer idloginLog) {
        this.idloginLog = idloginLog;
    }

    public LoginLog(Integer idloginLog, int idUsers, boolean login, Date created) {
        this.idloginLog = idloginLog;
        this.idUsers = idUsers;
        this.login = login;
        this.created = created;
    }

    public Integer getIdloginLog() {
        return idloginLog;
    }

    public void setIdloginLog(Integer idloginLog) {
        this.idloginLog = idloginLog;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public boolean getLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idloginLog != null ? idloginLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginLog)) {
            return false;
        }
        LoginLog other = (LoginLog) object;
        if ((this.idloginLog == null && other.idloginLog != null) || (this.idloginLog != null && !this.idloginLog.equals(other.idloginLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.LoginLog[ idloginLog=" + idloginLog + " ]";
    }

}
