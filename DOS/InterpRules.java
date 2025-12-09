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
@Table(name="interpRules")
public class InterpRules implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idInterpRules")
    private int idInterpRules;
    @Basic(optional = false)
    @Column(name = "number")
    private int number;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "inactive")
    private boolean inactive;
    @Column(name = "inactivatedBy")
    private Integer inactivatedBy;
    @Column(name = "inactivatedDate")
    @Temporal(TemporalType.DATE)
    private Date inactivatedDate;
    @Basic(optional = false)
    @Column(name = "ruleOrder")
    private int ruleOrder;

    public int getIdInterpRules()
    {
        return idInterpRules;
    }

    public void setIdInterpRules(int idInterpRules)
    {
        this.idInterpRules = idInterpRules;
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

    public void setInactivatedBy(Integer inactivatedBy)
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

    public int getRuleOrder()
    {
        return ruleOrder;
    }

    public void setRuleOrder(int ruleOrder)
    {
        this.ruleOrder = ruleOrder;
    }
}
