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
 * @file name: MissingDoctors.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "missingDoctors")
@NamedQueries(
{
    @NamedQuery(name = "MissingDoctors.findAll", query = "SELECT m FROM MissingDoctors m")
})
public class MissingDoctors implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMissingDoctors")
    private Integer idMissingDoctors;
    @Basic(optional = false)
    @Column(name = "orderId")
    private int orderId;
    @Column(name = "doctorID")
    private String doctorID;
    @Column(name = "doctorName")
    private String doctorName;

    public MissingDoctors()
    {
    }

    public MissingDoctors(Integer idMissingDoctors)
    {
        this.idMissingDoctors = idMissingDoctors;
    }

    public MissingDoctors(Integer idMissingDoctors, int orderId)
    {
        this.idMissingDoctors = idMissingDoctors;
        this.orderId = orderId;
    }

    public Integer getIdMissingDoctors()
    {
        return idMissingDoctors;
    }

    public void setIdMissingDoctors(Integer idMissingDoctors)
    {
        this.idMissingDoctors = idMissingDoctors;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getDoctorID()
    {
        return doctorID;
    }

    public void setDoctorID(String doctorID)
    {
        this.doctorID = doctorID;
    }

    public String getDoctorName()
    {
        return doctorName;
    }

    public void setDoctorName(String doctorName)
    {
        this.doctorName = doctorName;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idMissingDoctors != null ? idMissingDoctors.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MissingDoctors))
        {
            return false;
        }
        MissingDoctors other = (MissingDoctors) object;
        if ((this.idMissingDoctors == null && other.idMissingDoctors != null) || (this.idMissingDoctors != null && !this.idMissingDoctors.equals(other.idMissingDoctors)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "EMR.DOS.MissingDoctors[ idMissingDoctors=" + idMissingDoctors + " ]";
    }

}
