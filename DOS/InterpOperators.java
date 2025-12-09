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
@Table(name="interpOperators")
public class InterpOperators implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idInterpOperators")
    private int idInterpOperators;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "isLogical")
    private boolean isLogical;
    @Basic(optional = false)
    @Column(name = "inactive")
    private boolean inactive;
    @Column(name = "inactivatedBy")
    private Integer inactivatedBy;
    @Column(name = "inactivatedDate")
    @Temporal(TemporalType.DATE)
    private Date inactivatedDate;

    public int getIdInterpOperators()
    {
        return idInterpOperators;
    }

    public void setIdInterpOperators(int idInterpOperators)
    {
        this.idInterpOperators = idInterpOperators;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isLogical()
    {
        return isLogical;
    }

    public void setIsLogical(boolean isLogical)
    {
        this.isLogical = isLogical;
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
