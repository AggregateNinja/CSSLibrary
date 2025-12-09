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
 * @date:   Oct 16, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: BillingTestCrossReference.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "billingTestCrossReference")
@NamedQueries(
{
    @NamedQuery(name = "BillingTestCrossReference.findAll", query = "SELECT b FROM BillingTestCrossReference b")
})
public class BillingTestCrossReference implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbillingTestCrossReference")
    private Integer idbillingTestCrossReference;
    @Basic(optional = false)
    @Column(name = "idTests")
    private int idTests;
    @Basic(optional = false)
    @Column(name = "testNumber")
    private int testNumber;
    @Basic(optional = false)
    @Column(name = "subTestNumber")
    private int subTestNumber;

    public BillingTestCrossReference()
    {
    }

    public BillingTestCrossReference(Integer idbillingTestCrossReference)
    {
        this.idbillingTestCrossReference = idbillingTestCrossReference;
    }

    public BillingTestCrossReference(Integer idbillingTestCrossReference, int idTests, int testNumber, int subTestNumber)
    {
        this.idbillingTestCrossReference = idbillingTestCrossReference;
        this.idTests = idTests;
        this.testNumber = testNumber;
        this.subTestNumber = subTestNumber;
    }

    public Integer getIdbillingTestCrossReference()
    {
        return idbillingTestCrossReference;
    }

    public void setIdbillingTestCrossReference(Integer idbillingTestCrossReference)
    {
        this.idbillingTestCrossReference = idbillingTestCrossReference;
    }

    public int getIdTests()
    {
        return idTests;
    }

    public void setIdTests(int idTests)
    {
        this.idTests = idTests;
    }

    public int getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(int testNumber)
    {
        this.testNumber = testNumber;
    }

    public int getSubTestNumber()
    {
        return subTestNumber;
    }

    public void setSubTestNumber(int subTestNumber)
    {
        this.subTestNumber = subTestNumber;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idbillingTestCrossReference != null ? idbillingTestCrossReference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BillingTestCrossReference))
        {
            return false;
        }
        BillingTestCrossReference other = (BillingTestCrossReference) object;
        if ((this.idbillingTestCrossReference == null && other.idbillingTestCrossReference != null) || (this.idbillingTestCrossReference != null && !this.idbillingTestCrossReference.equals(other.idbillingTestCrossReference)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.BillingTestCrossReference[ idbillingTestCrossReference=" + idbillingTestCrossReference + " ]";
    }

}
