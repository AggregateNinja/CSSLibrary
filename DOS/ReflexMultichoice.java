package DOS;

import java.util.Date;
import java.io.Serializable;


public class ReflexMultichoice implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idReflexMultichoice;
        private Integer choiceOrder;
	private Integer testId;
	private Boolean isAbnormal;
	private Integer multichoiceId;
	private Integer notMultichoiceId;
	private Integer hasRemarkId;
	private Integer putRemarkId;
	private Integer reflexedTestId;
	private Integer createdBy;
	private Date createdDate;
	private Integer deactivatedBy;
	private Date deactivatedDate;
	private Boolean active;



	public Integer getIdReflexMultichoice()
	{
            return idReflexMultichoice;
	}


	public void setIdReflexMultichoice(Integer idReflexMultichoice)
	{
            this.idReflexMultichoice = idReflexMultichoice;
	}

        public Integer getChoiceOrder()
        {
            return choiceOrder;
        }
        
        public void setChoiceOrder (Integer choiceOrder)
        {
            this.choiceOrder = choiceOrder;
        }

	public Integer getTestId()
	{
            return testId;
	}


	public void setTestId(Integer testId)
	{
            this.testId = testId;
	}


	public Boolean isIsAbnormal()
	{
            return isAbnormal;
	}


	public void setIsAbnormal(Boolean isAbnormal)
	{
            this.isAbnormal = isAbnormal;
	}


	public Integer getMultichoiceId()
	{
            return multichoiceId;
	}


	public void setMultichoiceId(Integer multichoiceId)
	{
            this.multichoiceId = multichoiceId;
	}


	public Integer getNotMultichoiceId()
	{
            return notMultichoiceId;
	}


	public void setNotMultichoiceId(Integer notMultichoiceId)
	{
            this.notMultichoiceId = notMultichoiceId;
	}


	public Integer getHasRemarkId()
	{
            return hasRemarkId;
	}


	public void setHasRemarkId(Integer hasRemarkId)
	{
            this.hasRemarkId = hasRemarkId;
	}


	public Integer getPutRemarkId()
	{
            return putRemarkId;
	}


	public void setPutRemarkId(Integer putRemarkId)
	{
            this.putRemarkId = putRemarkId;
	}


	public Integer getReflexedTestId()
	{
            return reflexedTestId;
	}


	public void setReflexedTestId(Integer reflexedTestId)
	{
            this.reflexedTestId = reflexedTestId;
	}


	public Integer getCreatedBy()
	{
            return createdBy;
	}


	public void setCreatedBy(Integer createdBy)
	{
            this.createdBy = createdBy;
	}


	public Date getCreatedDate()
	{
            return createdDate;
	}


	public void setCreatedDate(Date createdDate)
	{
            this.createdDate = createdDate;
	}


	public Integer getDeactivatedBy()
	{
            return deactivatedBy;
	}


	public void setDeactivatedBy(Integer deactivatedBy)
	{
            this.deactivatedBy = deactivatedBy;
	}


	public Date getDeactivatedDate()
	{
            return deactivatedDate;
	}


	public void setDeactivatedDate(Date deactivatedDate)
	{
            this.deactivatedDate = deactivatedDate;
	}


	public Boolean isActive()
	{
            return active;
	}


	public void setActive(Boolean active)
	{
            this.active = active;
	}
}