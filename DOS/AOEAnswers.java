/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TomR
 */
@Entity
@Table(name = "aoeAnswers", catalog = "css", schema = "")
public class AOEAnswers
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "idorders", nullable = false)
    private Integer idOrders;
    @Column(name = "questionId", nullable = false)
    private Integer questionId;
    @Column(name = "answerText")
    private String answerText;
    @Column(name = "answerNumber")
    private Double answerNumber;
    @Column(name = "answerBool")
    private Boolean answerBool;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "answerDate")
    private Date answerDate;
    @Column(name = "aoeChoiceId")
    private Integer aoeChoiceId;
    @Column(name = "refLabAnswer")
    private String refLabAnswer;
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "idusers")
    private Integer idusers;
    
    public AOEAnswers() {}
    
    public AOEAnswers(Integer id,
            Integer idOrders,
            Integer questionId,
            String answerText,
            Double answerNumber,
            Boolean answerBool,
            Date answerDate,
            Integer aoeChoiceId,
            String refLabAnswer,
            Date created,
            Integer idusers)
    {
        this.id = id;
        this.idOrders = idOrders;
        this.questionId = questionId;
        this.answerText = answerText;
        this.answerNumber = answerNumber;
        this.answerBool = answerBool;
        this.answerDate = answerDate;
        this.aoeChoiceId = aoeChoiceId;
        this.refLabAnswer = refLabAnswer;
        this.created = created;
        this.idusers = idusers;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }
    
    public void setIdusers(Integer idusers)
    {
        this.idusers = idusers;
    }
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getIdOrders()
    {
        return idOrders;
    }
    
    public void setIdOrders(Integer idOrders)
    {
        this.idOrders = idOrders;
    }
    
    public Integer getQuestionId()
    {
        return questionId;
    }
    
    public void setQuestionId(Integer questionId)
    {
        this.questionId = questionId;
    }
    
    public String getAnswerText()
    {
        return this.answerText;
    }    
    
    public void setAnswerText(String answerText)
    {
        this.answerText = answerText;
    }
    
    public Double getAnswerNumber()
    {
        return this.answerNumber;
    }
    
    public void setAnswerNumber(Double answerNumber)
    {
        this.answerNumber = answerNumber; 
    }
    
    public Boolean getAnswerBool()
    {
        return this.answerBool;
    }
    
    public void setAnswerBool(Boolean answerBool)
    {
        this.answerBool = answerBool;
    }
    
    public Integer getAoeChoiceId()
    {
        return this.aoeChoiceId;
    }
        
    public void setAoeChoiceId(Integer aoeChoiceId)
    {
        this.aoeChoiceId = aoeChoiceId;
    }
    
    public String getRefLabAnswer() {
        return refLabAnswer;
    }

    public void setRefLabAnswer(String refLabAnswer) {
        this.refLabAnswer = refLabAnswer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getIdusers() {
        return idusers;
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
        if (!(object instanceof AOEAnswers)) {
            return false;
        }
        AOEAnswers other = (AOEAnswers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.AOEAnswers[ id=" + id + ", idOrders=" + idOrders +" ]";
    }        
}
