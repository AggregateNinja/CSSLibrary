package DOS;

import DOS.IDOS.BaseInstOrd;
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
 * @date:   Mar 26, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: InstOrd1.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */


@Entity 
@Table(name = "instOrd_1")
@NamedQueries(
{
    @NamedQuery(name = "InstOrd1.findAll", query = "SELECT i FROM InstOrd1 i")
})
public class InstOrd_1 extends BaseInstOrd implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    public InstOrd_1()
    {
        
    }
    
    public InstOrd_1(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
        if (!(object instanceof BaseInstOrd))
        {
            return false;
        }
        InstOrd_1 other = (InstOrd_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.IDOS.InstOrd1[ id=" + id + " ]";
    }
}
