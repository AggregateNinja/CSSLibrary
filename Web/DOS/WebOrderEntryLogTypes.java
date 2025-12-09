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
 * @file name: WebOrderEntryLogTypes.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "WebOrderEntryLogTypes")
@NamedQueries(
{
    @NamedQuery(name = "WebOrderEntryLogTypes.findAll", query = "SELECT w FROM WebOrderEntryLogTypes w")
})
public class WebOrderEntryLogTypes implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOrderEntryLogTypes")
    private Integer idOrderEntryLogTypes;
    @Basic(optional = false)
    @Column(name = "typeName")
    private String typeName;
    @Column(name = "typeDescription")
    private String typeDescription;

    public WebOrderEntryLogTypes()
    {
    }

    public WebOrderEntryLogTypes(Integer idOrderEntryLogTypes)
    {
        this.idOrderEntryLogTypes = idOrderEntryLogTypes;
    }

    public WebOrderEntryLogTypes(Integer idOrderEntryLogTypes, String typeName)
    {
        this.idOrderEntryLogTypes = idOrderEntryLogTypes;
        this.typeName = typeName;
    }

    public Integer getIdOrderEntryLogTypes()
    {
        return idOrderEntryLogTypes;
    }

    public void setIdOrderEntryLogTypes(Integer idOrderEntryLogTypes)
    {
        this.idOrderEntryLogTypes = idOrderEntryLogTypes;
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

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idOrderEntryLogTypes != null ? idOrderEntryLogTypes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebOrderEntryLogTypes))
        {
            return false;
        }
        WebOrderEntryLogTypes other = (WebOrderEntryLogTypes) object;
        if ((this.idOrderEntryLogTypes == null && other.idOrderEntryLogTypes != null) || (this.idOrderEntryLogTypes != null && !this.idOrderEntryLogTypes.equals(other.idOrderEntryLogTypes)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Web.DOS.WebOrderEntryLogTypes[ idOrderEntryLogTypes=" + idOrderEntryLogTypes + " ]";
    }

}
