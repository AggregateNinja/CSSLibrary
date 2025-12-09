package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @date:   Aug 16, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DAOS
 * @file name: Testcatmap.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "testcatmap")
@NamedQueries(
{
    @NamedQuery(name = "Testcatmap.findAll", query = "SELECT t FROM Testcatmap t")
})
public class Testcatmap implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtestcatmap")
    private Integer idtestcatmap;
    @Column(name = "testId")
    private Integer testId;
    @Column(name = "testcategoryId")
    private Integer testcategoryId;
    @Column(name = "position")
    private Integer position;
    @Column(name = "indent")
    private Integer indent;
    @Column(name = "text")
    private String text;

    public Testcatmap()
    {
    }

    public Testcatmap(Integer idtestcatmap)
    {
        this.idtestcatmap = idtestcatmap;
    }

    public Integer getIdtestcatmap()
    {
        return idtestcatmap;
    }

    public void setIdtestcatmap(Integer idtestcatmap)
    {
        this.idtestcatmap = idtestcatmap;
    }

    public Integer getTestId()
    {
        return testId;
    }

    public void setTestId(Integer testId)
    {
        this.testId = testId;
    }

    public Integer getTestcategoryId()
    {
        return testcategoryId;
    }

    public void setTestcategoryId(Integer testcategoryId)
    {
        this.testcategoryId = testcategoryId;
    }

    public Integer getPosition()
    {
        return position;
    }

    public void setPosition(Integer position)
    {
        this.position = position;
    }

    public Integer getIndent()
    {
        return indent;
    }

    public void setIndent(Integer indent)
    {
        this.indent = indent;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idtestcatmap != null ? idtestcatmap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testcatmap))
        {
            return false;
        }
        Testcatmap other = (Testcatmap) object;
        if ((this.idtestcatmap == null && other.idtestcatmap != null) || (this.idtestcatmap != null && !this.idtestcatmap.equals(other.idtestcatmap)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DAOS.Testcatmap[ idtestcatmap=" + idtestcatmap + " ]";
    }

}
