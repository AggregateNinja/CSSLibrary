/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.io.Serializable;
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
@Table(name = "aoeQuestions", catalog = "css", schema = "")
public class AOEQuestion implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column (name = "aoeGroupingTypeId")
    private Integer aoeGroupingTypeId;
    @Column(name = "question")
    private String question;
    @Column(name = "isDate")
    private boolean isDate;
    @Column(name = "isBool")
    private boolean isBool;
    @Column(name = "isNumber")
    private boolean isNumber;
    @Column(name = "isString")
    private boolean isString;
    @Column(name = "isMultiChoice")
    private boolean isMultiChoice;
    @Column(name = "minimumLength")
    private Integer minimumLength;
    @Column(name = "maximumLength")
    private Integer maximumLength;
    @Column(name = "minimumNumericValue")
    private Double minimumNumericValue;
    @Column(name = "maximumNumericValue")
    private Double maximumNumericValue;
    @Column(name = "isFloatingPoint")
    private boolean isFloatingPoint;
    @Basic(optional = false)
    @Column(name = "minimumDate", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date minimumDate;
    @Basic(optional = false)
    @Column(name = "maximumDate", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date maximumDate;        
    @Column(name = "allowFutureDate")
    private boolean allowFutureDate;
    @Column(name = "placeHolderCharacter")
    private String placeHolderCharacter;
    @Column(name = "paddedSize")
    private Integer paddedSize;
    @Column(name = "valueFormatMask")
    private String valueFormatMask;
    @Column(name = "isRightJustified")
    private boolean isRightJustified;
    @Column(name = "hidden")
    private boolean hidden;

    public AOEQuestion() {}
    
    public Integer getId()
    {
        return id;
    }
    
    public Integer getAoeGroupingTypeId()
    {
        return aoeGroupingTypeId;
    }
    
    public void setAoeGroupingTypeId(Integer id)
    {
        aoeGroupingTypeId = id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public boolean getIsDate()
    {
        return isDate;
    }
    
    public void setIsDate(boolean isDate)
    {
        this.isDate = isDate;
    }
    
    public boolean getIsBool()
    {
        return isBool;
    }
    
    public void setIsBool(boolean isBool)
    {
        this.isBool = isBool;
    }    
    
    public boolean getIsNumber()
    {
        return isNumber;
    }
    
    public void setIsNumber(boolean isNumber)
    {
        this.isNumber = isNumber;
    }    

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean getIsString() {
        return isString;
    }

    public void setIsString(boolean isString) {
        this.isString = isString;
    }
    
    public boolean getIsMultiChoice()
    {
        return isMultiChoice;
    }
    
    public void setIsMultiChoice(boolean isMultiChoice)
    {
        this.isMultiChoice = isMultiChoice;
    }
    
    public Integer getMinimumLength()
    {
        return minimumLength;
    }
    
    public void setMinimumLength(Integer minimumLength)
    {
        this.minimumLength = minimumLength;
    }
    
    public Integer getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(Integer maximumLength) {
        this.maximumLength = maximumLength;
    }

    public Double getMinimumNumericValue() {
        return minimumNumericValue;
    }

    public void setMinimumNumericValue(Double minimumNumericValue) {
        this.minimumNumericValue = minimumNumericValue;
    }

    public Double getMaximumNumericValue() {
        return maximumNumericValue;
    }

    public void setMaximumNumericValue(Double maximumNumericValue) {
        this.maximumNumericValue = maximumNumericValue;
    }
    
    public boolean isFloatingPoint()
    {
        return isFloatingPoint;
    }
    
    public void setFloatingPoint(boolean isFloatingPoint)
    {
        this.isFloatingPoint = isFloatingPoint;
    }

    public boolean isAllowFutureDate() {
        return allowFutureDate;
    }

    public void setAllowFutureDate(boolean allowFutureDate) {
        this.allowFutureDate = allowFutureDate;
    }

    public Date getMinimumDate() {
        return minimumDate;
    }

    public void setMinimumDate(Date minimumDate) {
        this.minimumDate = minimumDate;
    }

    public Date getMaximumDate() {
        return maximumDate;
    }

    public void setMaximumDate(Date maximumDate) {
        this.maximumDate = maximumDate;
    }

    public String getPlaceHolderCharacter() {
        return placeHolderCharacter;
    }

    public void setPlaceHolderCharacter(String placeHolderCharacter) {
        this.placeHolderCharacter = placeHolderCharacter;
    }

    public String getValueFormatMask() {
        return valueFormatMask;
    }

    public void setValueFormatMask(String valueFormatMask) {
        this.valueFormatMask = valueFormatMask;
    }

    public boolean isRightJustified() {
        return isRightJustified;
    }

    public void setIsRightJustified(boolean isRightJustified) {
        this.isRightJustified = isRightJustified;
    }

    public Integer getPaddedSize() {
        return paddedSize;
    }

    public void setPaddedSize(Integer paddedSize) {
        this.paddedSize = paddedSize;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
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
        if (!(object instanceof AOEQuestion)) {
            return false;
        }
        AOEQuestion other = (AOEQuestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DOS.AOEQuestion[ id=" + id + ", question=" + question +" ]";
    }    
    
}
