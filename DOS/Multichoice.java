
package DOS;

import java.io.Serializable;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Multichoice.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "multichoice", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Multichoice.findAll", query = "SELECT m FROM Multichoice m")})
public class Multichoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idMultiChoice", nullable = false)
    private Integer idMultiChoice;
    @Basic(optional = false)
    @Column(name = "testId", nullable = false)
    private int testId;
    @Basic(optional = false)
    @Column(name = "choice", nullable = false, length = 255)
    private String choice;
    @Basic(optional = false)
    @Column(name = "isAbnormal", nullable = false)
    private boolean isAbnormal;
    @Basic(optional = false)
    @Column(name = "choiceOrder", nullable = false)
    private int choiceOrder;

    public Multichoice() {
    }

    public Multichoice(Integer idMultiChoice) {
        this.idMultiChoice = idMultiChoice;
    }

    public Multichoice(Integer idMultiChoice, int testId, String choice, boolean isAbnormal, int choiceOrder) {
        this.idMultiChoice = idMultiChoice;
        this.testId = testId;
        this.choice = choice;
        this.isAbnormal = isAbnormal;
        this.choiceOrder = choiceOrder;
    }

    public Integer getIdMultiChoice() {
        return idMultiChoice;
    }

    public void setIdMultiChoice(Integer idMultiChoice) {
        this.idMultiChoice = idMultiChoice;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public boolean getIsAbnormal() {
        return isAbnormal;
    }

    public void setIsAbnormal(boolean isAbnormal) {
        this.isAbnormal = isAbnormal;
    }

    public int getChoiceOrder() {
        return choiceOrder;
    }

    public void setChoiceOrder(int choiceOrder) {
        this.choiceOrder = choiceOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultiChoice != null ? idMultiChoice.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multichoice)) {
            return false;
        }
        Multichoice other = (Multichoice) object;
        if ((this.idMultiChoice == null && other.idMultiChoice != null) || (this.idMultiChoice != null && !this.idMultiChoice.equals(other.idMultiChoice))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.Multichoice[ idMultiChoice=" + idMultiChoice + " ]";
    }

}
