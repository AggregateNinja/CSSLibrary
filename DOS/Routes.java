/*
 * Computer Service & Support, Inc.  All Rights Reserved Jul 8, 2014
 */

package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jul 8, 2014  10:19:58 AM
 * @author: Ryan Piper ryan@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: Routes.java  (UTF-8)
 *
 * @Description: 
 *
 */

@Entity
@Table(name = "routes")
@NamedQueries({
    @NamedQuery(name = "Routes.findAll", query = "SELECT r FROM Routes r")})
public class Routes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idroutes")
    private Integer idroutes;
    @Basic(optional = false)
    @Column(name = "routeName")
    private String routeName;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "createdBy")
    private int createdBy;

    public Routes() {
    }

    public Routes(Integer idroutes) {
        this.idroutes = idroutes;
    }

    public Routes(Integer idroutes, String routeName, String description, Date created, int createdBy) {
        this.idroutes = idroutes;
        this.routeName = routeName;
        this.description = description;
        this.created = created;
        this.createdBy = createdBy;
    }

    public Integer getIdroutes() {
        return idroutes;
    }

    public void setIdroutes(Integer idroutes) {
        this.idroutes = idroutes;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (idroutes != null ? idroutes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routes)) {
            return false;
        }
        Routes other = (Routes) object;
        if ((this.idroutes == null && other.idroutes != null) || (this.idroutes != null && !this.idroutes.equals(other.idroutes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.Routes[ idroutes=" + idroutes + " ]";
    }

}
