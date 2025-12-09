/*
 * Computer Service & Support, Inc.  All Rights Reserved Jun 4, 2014
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
 * @date:   Jun 4, 2014  4:31:45 PM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SysOps.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "sys_ops")
@NamedQueries({
    @NamedQuery(name = "SysOps.findAll", query = "SELECT s FROM SysOps s")})
public class SysOps implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "setting")
    private String setting;
    @Basic(optional = false)
    @Column(name = "option")
    private String option;

    public SysOps() {
    }

    public SysOps(Integer id) {
        this.id = id;
    }

    public SysOps(Integer id, String name, String setting) {
        this.id = id;
        this.name = name;
        this.setting = setting;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }
    
    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
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
        if (!(object instanceof SysOps)) {
            return false;
        }
        SysOps other = (SysOps) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.SysOps[ id=" + id + " ]";
    }

}
