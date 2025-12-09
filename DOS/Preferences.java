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
 * @date:   Oct 15, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Preferences.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "preferences")
@NamedQueries(
{
    @NamedQuery(name = "Preferences.findAll", query = "SELECT p FROM Preferences p")
})
public class Preferences implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpreferences")
    private Integer idpreferences;
    @Basic(optional = false)
    @Column(name = "key")
    private String key;
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Column(name = "table")
    private String table;

    public Preferences()
    {
    }

    public Preferences(Integer idpreferences)
    {
        this.idpreferences = idpreferences;
    }

    public Preferences(Integer idpreferences, String key, String type)
    {
        this.idpreferences = idpreferences;
        this.key = key;
        this.type = type;
    }

    public Integer getIdpreferences()
    {
        return idpreferences;
    }

    public void setIdpreferences(Integer idpreferences)
    {
        this.idpreferences = idpreferences;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getTable()
    {
        return table;
    }

    public void setTable(String table)
    {
        this.table = table;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idpreferences != null ? idpreferences.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preferences))
        {
            return false;
        }
        Preferences other = (Preferences) object;
        if ((this.idpreferences == null && other.idpreferences != null) || (this.idpreferences != null && !this.idpreferences.equals(other.idpreferences)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Preferences[ idpreferences=" + idpreferences + " ]";
    }

}
