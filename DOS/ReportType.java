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
 * @date:   Sep 19, 2013
 * @author: Keith Maggio <keith@csslis.com>
 * 
 * @project:  
 * @package: DOS
 * @file name: ReportType.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */

@Entity 
@Table(name = "reportType")
@NamedQueries(
{
    @NamedQuery(name = "ReportType.findAll", query = "SELECT r FROM ReportType r")
})
public class ReportType implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreportType")
    protected Integer idreportType;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "filePath")
    private String filePath;
    @Basic(optional = false)
    @Column(name = "selectable")
    private Boolean selectable;
    @Basic(optional = false)
    @Column(name = "format")
    private String format;

    public ReportType()
    {
    }

    public ReportType(Integer idreportType)
    {
        this.idreportType = idreportType;
    }

    public ReportType(Integer idreportType, int number, String filePath)
    {
        this.idreportType = idreportType;
        this.number = number;
        this.filePath = filePath;
    }

    public Integer getIdreportType()
    {
        return idreportType;
    }

    public void setIdreportType(Integer idreportType)
    {
        this.idreportType = idreportType;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String path)
    {
        this.filePath = path;
    }

    public Boolean isSelectable()
    {
        return selectable;
    }

    public void setSelectable(Boolean selectable)
    {
        this.selectable = selectable;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (idreportType != null ? idreportType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportType))
        {
            return false;
        }
        ReportType other = (ReportType) object;
        if ((this.idreportType == null && other.idreportType != null) || (this.idreportType != null && !this.idreportType.equals(other.idreportType)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "DOS.ReportType[ idreportType=" + idreportType + " ]";
    }

}
