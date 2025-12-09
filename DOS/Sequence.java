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
 * @date:   Feb 13, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Sequence.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "sequence")
@NamedQueries(
{
    @NamedQuery(name = "Sequence.findAll", query = "SELECT s FROM Sequence s")
})
public class Sequence implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsequence")
    private Integer idsequence;
    @Basic(optional = false)
    @Column(name = "tag")
    private String tag;
    @Basic(optional = false)
    @Column(name = "iteration")
    private int iteration;

    public Sequence()
    {
    }

    public Sequence(Integer idsequence)
    {
        this.idsequence = idsequence;
    }

    public Sequence(Integer idsequence, String tag, int iteration)
    {
        this.idsequence = idsequence;
        this.tag = tag;
        this.iteration = iteration;
    }

    public Integer getIdSequence()
    {
        return idsequence;
    }

    public void setIdSequence(Integer idsequence)
    {
        this.idsequence = idsequence;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public int getIteration()
    {
        return iteration;
    }

    public void setIteration(int iteration)
    {
        this.iteration = iteration;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idsequence != null ? idsequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sequence))
        {
            return false;
        }
        Sequence other = (Sequence) object;
        if ((this.idsequence == null && other.idsequence != null) || (this.idsequence != null && !this.idsequence.equals(other.idsequence)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Sequence[ idsequence=" + idsequence + " ]";
    }

}
