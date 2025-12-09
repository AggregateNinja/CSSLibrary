package DOS;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @date:   Nov 4, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: Requisitions.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "requisitions")
@NamedQueries(
{
    @NamedQuery(name = "Requisitions.findAll", query = "SELECT r FROM Requisitions r")
})
public class Requisitions implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idOrders")
    private Integer idOrders;
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @Column(name = "idUser")
    private Integer idUser;
    @Basic(optional = false)
    @Column(name = "fileType")
    private String fileType;
    @Column(name = "reportName")
    private String reportName;
    @Basic(optional = false)
    @Lob
    @Column(name = "data")
    private byte[] data;

    public Requisitions()
    {
    }

    public Requisitions(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Requisitions(Integer idOrders, Date created, Integer idUser, String fileType, byte[] data)
    {
        this.idOrders = idOrders;
        this.created = created;
        this.idUser = idUser;
        this.fileType = fileType;
        this.data = data;
    }

    public Integer getIdOrders()
    {
        return idOrders;
    }

    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public String getFileType()
    {
        return fileType;
    }

    public void setFileType(String fileType)
    {
        this.fileType = fileType;
    }
    
    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idOrders);
        hash = 97 * hash + Objects.hashCode(this.created);
        hash = 97 * hash + Objects.hashCode(this.idUser);
        hash = 97 * hash + Objects.hashCode(this.fileType);
        hash = 97 * hash + Objects.hashCode(this.reportName);
        hash = 97 * hash + Arrays.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Requisitions other = (Requisitions) obj;
        if (!Objects.equals(this.idOrders, other.idOrders))
        {
            return false;
        }
        if (!Objects.equals(this.created, other.created))
        {
            return false;
        }
        if (!Objects.equals(this.idUser, other.idUser))
        {
            return false;
        }
        if (!Objects.equals(this.fileType, other.fileType))
        {
            return false;
        }
        if (!Objects.equals(this.reportName, other.reportName))
        {
            return false;
        }
        if (!Arrays.equals(this.data, other.data))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Requisitions{" + "idOrders=" + idOrders + ", created=" + created + ", idUser=" + idUser + ", fileType=" + fileType + '}';
    }

    
}
