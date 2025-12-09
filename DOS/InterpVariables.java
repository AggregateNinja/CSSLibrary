/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Dec 24, 2015
 */
@Entity
@Table(name="interpVariables")
public class InterpVariables implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idInterpVariables")
    private int idInterpVariables;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "isDemographic")
    private boolean isDemographic;
    @Basic(optional = false)
    @Column(name = "inactive")
    private boolean inactive;
    @Column(name = "inactivatedBy")
    private Integer inactivatedBy;
    @Column(name = "inactivatedDate")
    @Temporal(TemporalType.DATE)
    private Date inactivatedDate;

    public int getIdInterpVariables()
    {
        return idInterpVariables;
    }

    public void setIdInterpVariables(int idInterpVariables)
    {
        this.idInterpVariables = idInterpVariables;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isDemographic()
    {
        return isDemographic;
    }

    public void setIsDemographic(boolean isDemographic)
    {
        this.isDemographic = isDemographic;
    }
    
    public boolean isInactive()
    {
        return inactive;
    }

    public void setInactive(boolean inactive)
    {
        this.inactive = inactive;
    }

    public int getInactivatedBy()
    {
        return inactivatedBy;
    }

    public void setInactivatedBy(int inactivatedBy)
    {
        this.inactivatedBy = inactivatedBy;
    }

    public Date getInactivatedDate()
    {
        return inactivatedDate;
    }

    public void setInactivatedDate(Date inactivatedDate)
    {
        this.inactivatedDate = inactivatedDate;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
