/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DOS;

import java.io.Serializable;
import java.sql.Timestamp;
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

/**
 * @date: Apr 28, 2014
 * @author Derrick J. Piper <derrick@csslis.com>
 *
 * @file name: Sales.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
@Entity
@Table(name = "sales")
@NamedQueries(
        {
            @NamedQuery(name = "Sales.findAll", query = "SELECT s FROM Sales s")
        })
public class Sales implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsales")
    private Integer idsales;
    @Basic(optional = false)
    @Column(name = "idemployees")
    private int idemployees;
    @Basic(optional = false)
    @Column(name = "idterritory")
    private int idterritory;
    @Basic(optional = false)
    @Column(name = "byOrder")
    private boolean byOrder;
    @Basic(optional = false)
    @Column(name = "byTest")
    private boolean byTest;
    @Basic(optional = false)
    @Column(name = "byBilled")
    private boolean byBilled;
    @Basic(optional = false)
    @Column(name = "byReceived")
    private boolean byReceived;
    @Column(name = "percentage")
    private Double percentage;
    @Column(name = "amount")
    private Double amount;
    @Basic(optional = false)
    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdDate;

    public Sales()
    {
    }

    public Sales(Integer idsales)
    {
        this.idsales = idsales;
    }

    public Sales(Integer idsales, int idemployees, int idterritory, boolean byOrder, boolean byTest, boolean byBilled, boolean byReceived, Timestamp createdDate)
    {
        this.idsales = idsales;
        this.idemployees = idemployees;
        this.idterritory = idterritory;
        this.byOrder = byOrder;
        this.byTest = byTest;
        this.byBilled = byBilled;
        this.byReceived = byReceived;
        this.createdDate = createdDate;
    }

    public Integer getIdsales()
    {
        return idsales;
    }

    public void setIdsales(Integer idsales)
    {
        this.idsales = idsales;
    }

    public int getIdemployees()
    {
        return idemployees;
    }

    public void setIdemployees(int idemployees)
    {
        this.idemployees = idemployees;
    }

    public int getIdterritory()
    {
        return idterritory;
    }

    public void setIdterritory(int idterritory)
    {
        this.idterritory = idterritory;
    }

    public boolean getByOrder()
    {
        return byOrder;
    }

    public void setByOrder(boolean byOrder)
    {
        this.byOrder = byOrder;
    }

    public boolean getByTest()
    {
        return byTest;
    }

    public void setByTest(boolean byTest)
    {
        this.byTest = byTest;
    }

    public boolean getByBilled()
    {
        return byBilled;
    }

    public void setByBilled(boolean byBilled)
    {
        this.byBilled = byBilled;
    }

    public boolean getByReceived()
    {
        return byReceived;
    }

    public void setByReceived(boolean byReceived)
    {
        this.byReceived = byReceived;
    }

    public Double getPercentage()
    {
        return percentage;
    }

    public void setPercentage(Double percentage)
    {
        this.percentage = percentage;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idsales != null ? idsales.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sales))
        {
            return false;
        }
        Sales other = (Sales) object;
        if ((this.idsales == null && other.idsales != null) || (this.idsales != null && !this.idsales.equals(other.idsales)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.Sales[ idsales=" + idsales + " ]";
    }

}
