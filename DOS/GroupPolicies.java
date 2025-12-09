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
 * @since Build {insert version here} 08/27/2013  
 * @author Ryan S. Piper <Ryan@csslis.com >
 */
@Entity
@Table(name = "groupPolicies")
@NamedQueries({
    @NamedQuery(name = "GroupPolicies.findAll", query = "SELECT g FROM GroupPolicies g")})
public class GroupPolicies implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgroupPolicies")
    private Integer idgroupPolicies;
    @Basic(optional = false)
    @Column(name = "module")
    private String module;
    @Column(name = "group1")
    private Boolean group1;
    @Column(name = "group2")
    private Boolean group2;
    @Column(name = "group3")
    private Boolean group3;
    @Column(name = "group4")
    private Boolean group4;
    @Column(name = "group5")
    private Boolean group5;
    @Column(name = "group6")
    private Boolean group6;
    @Column(name = "group7")
    private Boolean group7;
    @Column(name = "group8")
    private Boolean group8;
    @Column(name = "group9")
    private Boolean group9;
    @Column(name = "group10")
    private Boolean group10;

    public GroupPolicies() {
    }

    public GroupPolicies(Integer idgroupPolicies) {
        this.idgroupPolicies = idgroupPolicies;
    }

    public GroupPolicies(Integer idgroupPolicies, String module) {
        this.idgroupPolicies = idgroupPolicies;
        this.module = module;
    }

    public Integer getIdgroupPolicies() {
        return idgroupPolicies;
    }

    public void setIdgroupPolicies(Integer idgroupPolicies) {
        this.idgroupPolicies = idgroupPolicies;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getGroup1() {
        return group1;
    }

    public void setGroup1(Boolean group1) {
        this.group1 = group1;
    }

    public Boolean getGroup2() {
        return group2;
    }

    public void setGroup2(Boolean group2) {
        this.group2 = group2;
    }

    public Boolean getGroup3() {
        return group3;
    }

    public void setGroup3(Boolean group3) {
        this.group3 = group3;
    }

    public Boolean getGroup4() {
        return group4;
    }

    public void setGroup4(Boolean group4) {
        this.group4 = group4;
    }

    public Boolean getGroup5() {
        return group5;
    }

    public void setGroup5(Boolean group5) {
        this.group5 = group5;
    }

    public Boolean getGroup6() {
        return group6;
    }

    public void setGroup6(Boolean group6) {
        this.group6 = group6;
    }

    public Boolean getGroup7() {
        return group7;
    }

    public void setGroup7(Boolean group7) {
        this.group7 = group7;
    }

    public Boolean getGroup8() {
        return group8;
    }

    public void setGroup8(Boolean group8) {
        this.group8 = group8;
    }

    public Boolean getGroup9() {
        return group9;
    }

    public void setGroup9(Boolean group9) {
        this.group9 = group9;
    }

    public Boolean getGroup10() {
        return group10;
    }

    public void setGroup10(Boolean group10) {
        this.group10 = group10;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgroupPolicies != null ? idgroupPolicies.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupPolicies)) {
            return false;
        }
        GroupPolicies other = (GroupPolicies) object;
        if ((this.idgroupPolicies == null && other.idgroupPolicies != null) || (this.idgroupPolicies != null && !this.idgroupPolicies.equals(other.idgroupPolicies))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.GroupPolicies[ idgroupPolicies=" + idgroupPolicies + " ]";
    }

}
