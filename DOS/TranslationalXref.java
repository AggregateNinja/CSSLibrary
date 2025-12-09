/*
 * Computer Service & Support, Inc.  All Rights Reserved Aug 15, 2014
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
 * @date:   Nov 6, 2014  11:43:00 AM
 * @author: Michael Douglass miked@csslis.com
 * 
 * @project: CSSLibrary
 * @file name: TranslationalXref.java  (UTF-8)
 *
 * @Description: 
 *
 */
@Entity
@Table(name = "translationalXref")
@NamedQueries(
{
    @NamedQuery(name = "TranslationalXref.findAll", query = "SELECT t FROM TranslationalXref t")
})

//------------------------------------------------------------------------------
public class TranslationalXref implements Serializable
{
    //--------------------------------------------------------------------------
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "idxrefs")
    private Integer idxrefs;
    @Basic(optional = false)
    @Column(name = "testNumber")
    private int testNumber;
    @Basic(optional = false)
    @Column(name = "analyteName")
    private String analyteName;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    //--------------------------------------------------------------------------
    public TranslationalXref()
    {
    }

    //--------------------------------------------------------------------------
    public TranslationalXref(Integer id)
    {
        this.id = id;
    }

    //--------------------------------------------------------------------------
    public TranslationalXref(Integer id, int testNumber, String analyteName, Date created, boolean active)
    {
        this.id = id;
        this.testNumber = testNumber;
        this.analyteName = analyteName;
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
    public int getTestNumber()
    {
        return testNumber;
    }

    //--------------------------------------------------------------------------
    public void setTestNumber(int testNumber)
    {
        this.testNumber = testNumber;
    }

    //--------------------------------------------------------------------------
    public String getAnalyteName()
    {
        return analyteName;
    }

    //--------------------------------------------------------------------------
    public void setAnalyteName(String analyteName)
    {
        this.analyteName = analyteName;
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
        if (!(object instanceof TranslationalXref))
        {
            return false;
        }
        TranslationalXref other = (TranslationalXref) object;
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
        return "DOS.TranslationalXref[ id=" + id + " ]";
    }
}

//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------