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
 * @file name: MissingInsurance.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "missingInsurance")
@NamedQueries(
{
    @NamedQuery(name = "MissingInsurance.findAll", query = "SELECT m FROM MissingInsurance m")
})
public class MissingInsurance implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmissingInsurance")
    private Integer idmissingInsurance;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "insuranceID")
    private String insuranceID;
    @Column(name = "insuranceName")
    private String insuranceName;
    @Column(name = "missingInsurancecol")
    private String missingInsurancecol;

    public MissingInsurance()
    {
    }

    public MissingInsurance(Integer idmissingInsurance)
    {
        this.idmissingInsurance = idmissingInsurance;
    }

    public MissingInsurance(Integer idmissingInsurance, int orderId)
    {
        this.idmissingInsurance = idmissingInsurance;
        this.orderId = orderId;
    }

    public Integer getIdmissingInsurance()
    {
        return idmissingInsurance;
    }

    public void setIdmissingInsurance(Integer idmissingInsurance)
    {
        this.idmissingInsurance = idmissingInsurance;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getInsuranceID()
    {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID)
    {
        this.insuranceID = insuranceID;
    }

    public String getInsuranceName()
    {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName)
    {
        this.insuranceName = insuranceName;
    }

    public String getMissingInsurancecol()
    {
        return missingInsurancecol;
    }

    public void setMissingInsurancecol(String missingInsurancecol)
    {
        this.missingInsurancecol = missingInsurancecol;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idmissingInsurance != null ? idmissingInsurance.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MissingInsurance))
        {
            return false;
        }
        MissingInsurance other = (MissingInsurance) object;
        if ((this.idmissingInsurance == null && other.idmissingInsurance != null) || (this.idmissingInsurance != null && !this.idmissingInsurance.equals(other.idmissingInsurance)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.MissingInsurance[ idmissingInsurance=" + idmissingInsurance + " ]";
    }

}
