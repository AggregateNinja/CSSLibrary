package DOS;

import DOS.IDOS.BaseInstRes;
import java.io.Serializable;
import java.util.Objects;
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
 * @date:   Nov 4, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: instRes_1.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "instRes_1")
@NamedQueries(
{
    @NamedQuery(name = "instRes_1.findAll", query = "SELECT i FROM instRes_1 i")
})
public class InstRes_1 extends BaseInstRes implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idinstresult")
    private Integer idinstresult;

    public InstRes_1()
    {
    }

    public Integer getIdinstresult()
    {
        return idinstresult;
    }

    public void setIdinstresult(Integer idinstresult)
    {
        this.idinstresult = idinstresult;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idinstresult);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final InstRes_1 other = (InstRes_1) obj;
        if (!Objects.equals(this.idinstresult, other.idinstresult))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "InstRes_1{" + "idinstresult=" + idinstresult + '}';
    }

}
