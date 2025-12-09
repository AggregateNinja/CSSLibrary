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
 * @date:   Aug 25, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: QcTests.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "qcTests")
@NamedQueries(
{
    @NamedQuery(name = "QcTests.findAll", query = "SELECT q FROM QcTests q")
})
public class QcTests implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idqcTests")
    private Integer idqcTests;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Column(name = "reagent")
    private Integer reagent;
    @Column(name = "control1")
    private Integer control1;
    @Column(name = "control2")
    private Integer control2;
    @Column(name = "control3")
    private Integer control3;
    @Column(name = "control4")
    private Integer control4;
    @Column(name = "control5")
    private Integer control5;
    @Column(name = "controlRuns")
    private Integer controlRuns;

    public QcTests()
    {
    }

    public QcTests(Integer idqcTests)
    {
        this.idqcTests = idqcTests;
    }

    public QcTests(Integer idqcTests, int number)
    {
        this.idqcTests = idqcTests;
        this.number = number;
    }

    public Integer getIdqcTests()
    {
        return idqcTests;
    }

    public void setIdqcTests(Integer idqcTests)
    {
        this.idqcTests = idqcTests;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public Integer getReagent()
    {
        return reagent;
    }

    public void setReagent(Integer reagent)
    {
        this.reagent = reagent;
    }

    public Integer getControl1()
    {
        return control1;
    }

    public void setControl1(Integer control1)
    {
        this.control1 = control1;
    }

    public Integer getControl2()
    {
        return control2;
    }

    public void setControl2(Integer control2)
    {
        this.control2 = control2;
    }

    public Integer getControl3()
    {
        return control3;
    }

    public void setControl3(Integer control3)
    {
        this.control3 = control3;
    }

    public Integer getControl4()
    {
        return control4;
    }

    public void setControl4(Integer control4)
    {
        this.control4 = control4;
    }

    public Integer getControl5()
    {
        return control5;
    }

    public void setControl5(Integer control5)
    {
        this.control5 = control5;
    }

    public Integer getControlRuns()
    {
        return controlRuns;
    }

    public void setControlRuns(Integer controlRuns)
    {
        this.controlRuns = controlRuns;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idqcTests != null ? idqcTests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QcTests))
        {
            return false;
        }
        QcTests other = (QcTests) object;
        if ((this.idqcTests == null && other.idqcTests != null) || (this.idqcTests != null && !this.idqcTests.equals(other.idqcTests)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.QcTests[ idqcTests=" + idqcTests + " ]";
    }

}
