package Web.DOS;

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
 * @date:   Jun 2, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Web.DOS
 * @file name: WebLogTypes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebLogTypes")
@NamedQueries(
{
    @NamedQuery(name = "WebLogTypes.findAll", query = "SELECT w FROM WebLogTypes w")
})
public class WebLogTypes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTypes")
    private Integer idTypes;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public WebLogTypes()
    {
    }

    public WebLogTypes(Integer idTypes)
    {
        this.idTypes = idTypes;
    }

    public WebLogTypes(Integer idTypes, String name)
    {
        this.idTypes = idTypes;
        this.name = name;
    }

    public Integer getIdTypes()
    {
        return idTypes;
    }

    public void setIdTypes(Integer idTypes)
    {
        this.idTypes = idTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        if (!(object instanceof WebLogTypes))
        {
            return false;
        }
        WebLogTypes other = (WebLogTypes) object;
        if ((this.idTypes == null && other.idTypes != null) || (this.idTypes != null && !this.idTypes.equals(other.idTypes)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebLogTypes[ idTypes=" + idTypes + " ]";
    }

}
