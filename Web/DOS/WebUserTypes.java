package Web.DOS;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebUserTypes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebUserTypes")
@NamedQueries(
{
    @NamedQuery(name = "WebUserTypes.findAll", query = "SELECT w FROM WebUserTypes w")
})
public class WebUserTypes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTypes")
    private Integer idTypes;
    @Basic(optional = false)
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "typeDescription")
    private String typeDescription;
    @Basic(optional = false)
    @Column(name = "dateCreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public WebUserTypes()
    {
    }

    public WebUserTypes(Integer idTypes)
    {
        this.idTypes = idTypes;
    }

    public WebUserTypes(Integer idTypes, String typeName, Date dateCreated)
    {
        this.idTypes = idTypes;
        this.typeName = typeName;
        this.dateCreated = dateCreated;
    }

    public Integer getIdTypes()
    {
        return idTypes;
    }

    public void setIdTypes(Integer idTypes)
    {
        this.idTypes = idTypes;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getTypeDescription()
    {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription)
    {
        this.typeDescription = typeDescription;
    }

    public Date getDateCreated()
    {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idTypes != null ? idTypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebUserTypes))
        {
            return false;
        }
        WebUserTypes other = (WebUserTypes) object;
        if ((this.idTypes == null && other.idTypes != null) || (this.idTypes != null && !this.idTypes.equals(other.idTypes)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebUserTypes[ idTypes=" + idTypes + " ]";
    }

}
