package DOS;

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

/**
 * @date: Apr 21, 2014
 * @author: Keith Maggio <keith@csslis.com>
 *
 * @project:
 * @package: DOS
 * @file name: EmrXref.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc. All Rights Reserved
 */
@Entity
@Table(name = "emrXref")
@NamedQueries(
        {
            @NamedQuery(name = "EmrXref.findAll", query = "SELECT e FROM EmrXref e")
        })
public class EmrXref implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmrXref")
    private Integer idEmrXref;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "isBiDirectional")
    private boolean isBiDirectional;
    @Column(name = "testInterface")
    private Integer testInterface;
    @Column(name = "clientInterface")
    private Integer clientInterface;
    @Column(name = "insuranceInterface")
    private Integer insuranceInterface;
    @Column(name = "doctorInterface")
    private Integer doctorInterface;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;
    @Basic(optional = false)
    @Column(name = "doctorRequired")
    private boolean doctorRequired;
    @Basic(optional = false)
    @Column(name = "isSendout")
    private boolean isSendout;
    @Column(name = "autoApproval")
    private boolean autoApproval;

    public EmrXref()
    {
    }

    public EmrXref(Integer idEmrXref)
    {
        this.idEmrXref = idEmrXref;
    }

    public EmrXref(Integer idEmrXref, String name, boolean isBiDirectional, Date created, boolean active)
    {
        this.idEmrXref = idEmrXref;
        this.name = name;
        this.isBiDirectional = isBiDirectional;
        this.created = created;
        this.active = active;
    }

    public Integer getIdEmrXref()
    {
        return idEmrXref;
    }

    public void setIdEmrXref(Integer idEmrXref)
    {
        this.idEmrXref = idEmrXref;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean getIsBiDirectional()
    {
        return isBiDirectional;
    }

    public void setIsBiDirectional(boolean isBiDirectional)
    {
        this.isBiDirectional = isBiDirectional;
    }

    public Integer getTestInterface()
    {
        return testInterface;
    }

    public void setTestInterface(Integer testInterface)
    {
        this.testInterface = testInterface;
    }

    public Integer getClientInterface()
    {
        return clientInterface;
    }

    public void setClientInterface(Integer clientInterface)
    {
        this.clientInterface = clientInterface;
    }

    public Integer getInsuranceInterface()
    {
        return insuranceInterface;
    }

    public void setInsuranceInterface(Integer insuranceInterface)
    {
        this.insuranceInterface = insuranceInterface;
    }

    public Integer getDoctorInterface()
    {
        return doctorInterface;
    }

    public void setDoctorInterface(Integer doctorInterface)
    {
        this.doctorInterface = doctorInterface;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public boolean getActive()
    {
        return active;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean getDoctorRequired()
    {
        return doctorRequired;
    }

    public void setDoctorRequired(boolean doctorRequired)
    {
        this.doctorRequired = doctorRequired;
    }

    public boolean getIsSendout()
    {
        return isSendout;
    }
    
    public void setIsSendout(boolean isSendout)
    {
        this.isSendout = isSendout;
    }

    public boolean getAutoApproval() {
        return autoApproval;
    }

    public void setAutoApproval(boolean autoApproval) {
        this.autoApproval = autoApproval;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idEmrXref != null ? idEmrXref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmrXref))
        {
            return false;
        }
        EmrXref other = (EmrXref) object;
        if ((this.idEmrXref == null && other.idEmrXref != null) || (this.idEmrXref != null && !this.idEmrXref.equals(other.idEmrXref)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

}
