package Billing.DOS;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @date:   Aug 1, 2014
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: Billing.DOS
 * @file name: AccessionXref.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "accessionXref")
@XmlRootElement
@NamedQueries(
{
    @NamedQuery(name = "AccessionXref.findAll", query = "SELECT a FROM AccessionXref a"),
    @NamedQuery(name = "AccessionXref.findById", query = "SELECT a FROM AccessionXref a WHERE a.id = :id"),
    @NamedQuery(name = "AccessionXref.findByAvalonAccession", query = "SELECT a FROM AccessionXref a WHERE a.avalonAccession = :avalonAccession"),
    @NamedQuery(name = "AccessionXref.findByBillingAccession", query = "SELECT a FROM AccessionXref a WHERE a.billingAccession = :billingAccession"),
    @NamedQuery(name = "AccessionXref.findByOrderDate", query = "SELECT a FROM AccessionXref a WHERE a.orderDate = :orderDate")
})
public class AccessionXref implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "avalonAccession")
    private String avalonAccession;
    @Basic(optional = false)
    @Column(name = "billingAccession")
    private String billingAccession;
    @Column(name = "orderDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    public AccessionXref()
    {
    }

    public AccessionXref(Integer id)
    {
        this.id = id;
    }

    public AccessionXref(Integer id, String avalonAccession, String billingAccession)
    {
        this.id = id;
        this.avalonAccession = avalonAccession;
        this.billingAccession = billingAccession;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAvalonAccession()
    {
        return avalonAccession;
    }

    public void setAvalonAccession(String avalonAccession)
    {
        this.avalonAccession = avalonAccession;
    }

    public String getBillingAccession()
    {
        return billingAccession;
    }

    public void setBillingAccession(String billingAccession)
    {
        this.billingAccession = billingAccession;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
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
        if (!(object instanceof AccessionXref))
        {
            return false;
        }
        AccessionXref other = (AccessionXref) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Billing.DOS.AccessionXref[ id=" + id + " ]";
    }

}
