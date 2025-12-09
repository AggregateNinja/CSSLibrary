package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @date:   Jul 28, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: LabelForms.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "labelForms")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "LabelForms.findAll", query = "SELECT l FROM LabelForms l"),
    @NamedQuery(name = "LabelForms.findById", query = "SELECT l FROM LabelForms l WHERE l.id = :id"),
    @NamedQuery(name = "LabelForms.findByName", query = "SELECT l FROM LabelForms l WHERE l.name = :name"),
    @NamedQuery(name = "LabelForms.findByLanguage", query = "SELECT l FROM LabelForms l WHERE l.language = :language")
})
public class LabelForms implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "language")
    private String language;
    @Basic(optional = false)
    @Lob
    @Column(name = "form")
    private String form;
    @Column(name = "visible")
    private boolean visible;

    public LabelForms()
    {
    }

    public LabelForms(Integer id)
    {
        this.id = id;
    }

    public LabelForms(Integer id, String name, String form)
    {
        this.id = id;
        this.name = name;
        this.form = form;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getForm()
    {
        return form;
    }

    public void setForm(String form)
    {
        this.form = form;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LabelForms))
        {
            return false;
        }
        LabelForms other = (LabelForms) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return name;
        //return "DOS.LabelForms[ id=" + id + ", " + name + " ]";
    }

}
