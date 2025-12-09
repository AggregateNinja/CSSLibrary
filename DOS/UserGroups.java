/*
 * Copyright (C) 2013 CSS, Inc.  All Rights Reserved.
 * This software is the proprietary information of CSS, Inc.
 */

package DOS;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * {Insert Class description here}
 *
 * @version $Revision: 1.1.1.1 $
 * @since Build {insert version here} 04/02/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "userGroups")
@NamedQueries({
    @NamedQuery(name = "UserGroups.findAll", query = "SELECT u FROM UserGroups u")})
public class UserGroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduserGroups")
    private Integer iduserGroups;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "securityLevel")
    private int securityLevel;
    @Column(name = "description")
    private String description;


    public UserGroups() {
    }

    public UserGroups(Integer iduserGroups) {
        this.iduserGroups = iduserGroups;
    }

    public UserGroups(Integer iduserGroups, String name, int securityLevel) {
        this.iduserGroups = iduserGroups;
        this.name = name;
        this.securityLevel = securityLevel;
    }

    public Integer getIduserGroups() {
        return iduserGroups;
    }

    public void setIduserGroups(Integer iduserGroups) {
        this.iduserGroups = iduserGroups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduserGroups != null ? iduserGroups.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroups)) {
            return false;
        }
        UserGroups other = (UserGroups) object;
        if ((this.iduserGroups == null && other.iduserGroups != null) || (this.iduserGroups != null && !this.iduserGroups.equals(other.iduserGroups))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.UserGroups[ iduserGroups=" + iduserGroups + " ]";
    }

}
