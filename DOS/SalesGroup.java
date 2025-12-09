/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 8, 2014
 */

package DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jul 8, 2014  10:19:59 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: SalesGroup.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "salesGroup")
@NamedQueries({
    @NamedQuery(name = "SalesGroup.findAll", query = "SELECT s FROM SalesGroup s")})
public class SalesGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "groupName")
    private String groupName;
    @Basic(optional = false)
    @Column(name = "groupLeader")
    private Integer groupLeader;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private int createdBy;

    public SalesGroup() {
    }

    public SalesGroup(Integer id) {
        this.id = id;
    }

    public SalesGroup(Integer id, String groupName, Date created, int createdBy) {
        this.id = id;
        this.groupName = groupName;
        this.created = created;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(Integer groupLeader) {
        this.groupLeader = groupLeader;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
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
        if (!(object instanceof SalesGroup)) {
            return false;
        }
        SalesGroup other = (SalesGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.SalesGroup[ id=" + id + " ]";
    }

}
