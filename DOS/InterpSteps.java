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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Robert Hussey <r.hussey@csslis.com>
 * @date Dec 24, 2015
 */
@Entity
@Table(
    name = "interpSteps",
    uniqueConstraints=
        @UniqueConstraint(columnNames={"ruleId", "stepOrder"})
)
public class InterpSteps implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idInterpSteps")
    private int idInterpSteps;
    @Basic(optional = false)
    @Column(name = "ruleId")
    private int ruleId;
    @Column(name = "operatorId")
    private Integer operatorId;
    @Column(name = "testNum")
    private Integer testNum;
    @Column(name = "testAbbr")
    private String testAbbr;
    @Column(name = "variableId")
    private Integer variableId;
    @Column(name = "insuranceId")
    private Integer insuranceId;
    @Column(name = "clientNum")
    private Integer clientNum;
    @Column(name = "numericValue")
    private Double numericValue;
    @Column(name = "valueId")
    private Integer valueId;
    @Basic(optional = false)
    @Column(name = "stepOrder")
    private int stepOrder;

    public int getIdInterpSteps()
    {
        return idInterpSteps;
    }

    public void setIdInterpSteps(int idInterpSteps)
    {
        this.idInterpSteps = idInterpSteps;
    }

    public int getRuleId()
    {
        return ruleId;
    }

    public void setRuleId(int ruleId)
    {
        this.ruleId = ruleId;
    }

    public Integer getOperatorId()
    {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId)
    {
        this.operatorId = operatorId;
    }

    public Integer getTestNum()
    {
        return testNum;
    }

    public void setTestNum(Integer testNum)
    {
        this.testNum = testNum;
    }

    public String getTestAbbr()
    {
        return testAbbr;
    }

    public void setTestAbbr(String testAbbr)
    {
        this.testAbbr = testAbbr;
    }

    public Integer getVariableId()
    {
        return variableId;
    }

    public void setVariableId(Integer variableId)
    {
        this.variableId = variableId;
    }

    public Integer getInsuranceId()
    {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId)
    {
        this.insuranceId = insuranceId;
    }

    public Integer getClientNum()
    {
        return clientNum;
    }

    public void setClientNum(Integer clientNum)
    {
        this.clientNum = clientNum;
    }

    public Double getNumericValue()
    {
        return numericValue;
    }

    public void setNumericValue(Double numericValue)
    {
        this.numericValue = numericValue;
    }

    public Integer getValueId()
    {
        return valueId;
    }

    public void setValueId(Integer valueId)
    {
        this.valueId = valueId;
    }

    public int getStepOrder()
    {
        return stepOrder;
    }

    public void setStepOrder(int stepOrder)
    {
        this.stepOrder = stepOrder;
    }
}
