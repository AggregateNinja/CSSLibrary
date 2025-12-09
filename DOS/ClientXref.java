//------------------------------------------------------------------------------
/*
 * Computer Service & Support, Inc.  All Rights Reserved Oct 23, 2014
 */
//------------------------------------------------------------------------------
package DOS;

//------------------------------------------------------------------------------
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

//------------------------------------------------------------------------------
/**
 * @date: Jan 7, 2015 11:20:30 AM
 * @author: Mike Douglass miked@csslis.com
 *
 * @project: CSSLibrary
 * @file name: ClientXref.java (UTF-8)
 *
 * @Description:
 * 
*/
//------------------------------------------------------------------------------
@Entity
@Table(name = "clientXref")
@NamedQueries(
{
    @NamedQuery(name = "ClientXref.findAll", query = "SELECT i FROM ClientXref i")
})
public class ClientXref implements Serializable
{

    //--------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "idxrefs")
    private Integer idxrefs;
    @Basic(optional = false)
    @Column(name = "idClients")
    private Integer idClients;
    @Basic(optional = false)
    @Column(name = "transformedIn")
    private String transformedIn;
    @Basic(optional = false)
    @Column(name = "transformedOut")
    private String transformedOut;
    @Column(name = "use1")
    private String use1;
    @Column(name = "use2")
    private String use2;
    @Column(name = "use3")
    private String use3;
    @Column(name = "use4")
    private String use4;
    @Column(name = "use5")
    private String use5;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    //--------------------------------------------------------------------------
    public ClientXref()
    {
    }

    //--------------------------------------------------------------------------
    public ClientXref(Integer id)
    {
        this.id = id;
    }

    //--------------------------------------------------------------------------
    public ClientXref(Integer id, String transformedIn, String transformedOut, Date created, boolean active)
    {
        this.id = id;
        this.transformedIn = transformedIn;
        this.transformedOut = transformedOut;
        this.created = created;
        this.active = active;
    }

    //--------------------------------------------------------------------------
    public Integer getId()
    {
        return id;
    }

    //--------------------------------------------------------------------------
    public void setId(Integer id)
    {
        this.id = id;
    }

    //--------------------------------------------------------------------------
    public Integer getIdxrefs()
    {
        return idxrefs;
    }

    //--------------------------------------------------------------------------
    public void setIdxrefs(Integer idxrefs)
    {
        this.idxrefs = idxrefs;
    }

    //--------------------------------------------------------------------------
    public Integer getIdClients()
    {
        return idClients;
    }

    //--------------------------------------------------------------------------
    public void setIdClients(Integer idClients)
    {
        this.idClients = idClients;
    }

    //--------------------------------------------------------------------------
    public String getTransformedIn()
    {
        return transformedIn;
    }

    //--------------------------------------------------------------------------
    public void setTransformedIn(String transformedIn)
    {
        this.transformedIn = transformedIn;
    }

    //--------------------------------------------------------------------------
    public String getTransformedOut()
    {
        return transformedOut;
    }

    //--------------------------------------------------------------------------
    public void setTransformedOut(String transformedOut)
    {
        this.transformedOut = transformedOut;
    }

    //--------------------------------------------------------------------------
    public String getUse1()
    {
        return use1;
    }

    //--------------------------------------------------------------------------
    public void setUse1(String use1)
    {
        this.use1 = use1;
    }

    //--------------------------------------------------------------------------
    public String getUse2()
    {
        return use2;
    }

    //--------------------------------------------------------------------------
    public void setUse2(String use2)
    {
        this.use2 = use2;
    }

    //--------------------------------------------------------------------------
    public String getUse3()
    {
        return use3;
    }

    //--------------------------------------------------------------------------
    public void setUse3(String use3)
    {
        this.use3 = use3;
    }

    //--------------------------------------------------------------------------
    public String getUse4()
    {
        return use4;
    }

    //--------------------------------------------------------------------------
    public void setUse4(String use4)
    {
        this.use4 = use4;
    }

    //--------------------------------------------------------------------------
    public String getUse5()
    {
        return use5;
    }

    //--------------------------------------------------------------------------
    public void setUse5(String use5)
    {
        this.use5 = use5;
    }

    //--------------------------------------------------------------------------
    public String getDescription()
    {
        return description;
    }

    //--------------------------------------------------------------------------
    public void setDescription(String description)
    {
        this.description = description;
    }

    //--------------------------------------------------------------------------
    public Date getCreated()
    {
        return created;
    }

    //--------------------------------------------------------------------------
    public void setCreated(Date created)
    {
        this.created = created;
    }

    //--------------------------------------------------------------------------
    public boolean getActive()
    {
        return active;
    }

    //--------------------------------------------------------------------------
    public void setActive(boolean active)
    {
        this.active = active;
    }

    //--------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    //--------------------------------------------------------------------------
    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClientXref)) 
        {
            return false;
        }
        
        ClientXref other = (ClientXref) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) 
        {
            return false;
        }
        
        return true;
    }

    //--------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return "DOS.ClientXref[ id=" + id + " ]";
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------