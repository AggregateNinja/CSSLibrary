package EMR.DOS;

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
 * @date:   Jul 8, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: EMR.DOS
 * @file name: MissingTests.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "missingTests")
@NamedQueries(
{
    @NamedQuery(name = "MissingTests.findAll", query = "SELECT m FROM MissingTests m")
})
public class MissingTests implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMissingTests")
    private Integer idMissingTests;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "testNumber")
    private Integer testNumber;
    @Basic(optional = false)
    @Column(name = "testName")
    private String testName;

    public MissingTests()
    {
    }

    public MissingTests(Integer idMissingTests)
    {
        this.idMissingTests = idMissingTests;
    }

    public MissingTests(Integer idMissingTests, int orderId, String testName)
    {
        this.idMissingTests = idMissingTests;
        this.orderId = orderId;
        this.testName = testName;
    }

    public Integer getIdMissingTests()
    {
        return idMissingTests;
    }

    public void setIdMissingTests(Integer idMissingTests)
    {
        this.idMissingTests = idMissingTests;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public Integer getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber)
    {
        this.testNumber = testNumber;
    }

    public String getTestName()
    {
        return testName;
    }

    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idMissingTests != null ? idMissingTests.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MissingTests))
        {
            return false;
        }
        MissingTests other = (MissingTests) object;
        if ((this.idMissingTests == null && other.idMissingTests != null) || (this.idMissingTests != null && !this.idMissingTests.equals(other.idMissingTests)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.MissingTests[ idMissingTests=" + idMissingTests + " ]";
    }

}
