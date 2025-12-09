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
@Table(name = "aoeTests", catalog = "css", schema = "")
public class AOETests
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "testNumber")
    private Integer testNumber;   
    @Column(name = "questionId")
    private Integer questionId;
    @Column(name = "questionOrder")
    private Integer questionOrder;

    public AOETests() {}
    
    public AOETests(Integer id,
            Integer testNumber,
            Integer questionId,
            Integer questionOrder)
    {
        this.id = id;
        this.testNumber = testNumber;
        this.questionId = questionId;
        this.questionOrder = questionOrder;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getTestNumber()
    {
        return testNumber;
    }
    
    public void setTestNumber(Integer idTests)
    {
        this.testNumber= idTests;
    }
    
    public Integer getQuestionId()
    {
        return questionId;
    }
    
    public void setQuestionId(Integer questionId)
    {
        this.questionId = questionId;
    }
    
    public Integer getQuestionOrder()
    {
        return questionOrder;
    }
    
    public void setQuestionOrder(Integer questionOrder)
    {
        this.questionOrder = questionOrder;
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
        if (!(object instanceof AOETests)) {
            return false;
        }
        AOETests other = (AOETests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DAOS.AOETests[ id=" + id + ", testNumber=" + testNumber +" ]";
    }    
        
}
