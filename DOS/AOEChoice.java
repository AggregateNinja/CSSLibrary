/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "aoeChoice", catalog = "css", schema = "")
public class AOEChoice
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "questionId")
    private Integer questionId;
    @Column(name = "choice")
    private String choice;
    @Column(name = "choiceOrder")
    private Integer choiceOrder;
    @Column(name = "refLabValue")
    private String refLabValue;

    public AOEChoice() {}
    
    public AOEChoice(Integer id,
            Integer questionId,
            String choice,
            Integer choiceOrder,
            String refLabValue)
    {
        this.id = id;
        this.questionId = questionId;
        this.choice = choice;
        this.choiceOrder = choiceOrder;
        this.refLabValue = refLabValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getChoiceOrder() {
        return choiceOrder;
    }

    public void setChoiceOrder(Integer choiceOrder) {
        this.choiceOrder = choiceOrder;
    }

    public String getRefLabValue() {
        return refLabValue;
    }

    public void setRefLabValue(String refLabValue) {
        this.refLabValue = refLabValue;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }    
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AOEChoice)) {
            return false;
        }
        AOEChoice other = (AOEChoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.AOETests[ id=" + id + ", questionId=" + questionId +" ]";
    }        
}
