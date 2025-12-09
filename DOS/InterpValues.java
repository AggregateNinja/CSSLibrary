/*
 * Computer Service & Support, Inc.  All Rights Reserved.
 */

package DOS;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Dec 24, 2015
 */
@Entity
@Table(name="InterpValues")
public class InterpValues implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idInterpValues")
    private int idInterpValues;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "variableId")
    private Integer variableId;

    public int getIdInterpValues()
    {
        return idInterpValues;
    }

    public void setIdInterpValues(int idInterpValues)
    {
        this.idInterpValues = idInterpValues;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getVariableId()
    {
        return variableId;
    }

    public void setVariableId(int variableId)
    {
        this.variableId = variableId;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
