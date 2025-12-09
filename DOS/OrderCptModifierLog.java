package DOS;

import java.util.Date;
import java.io.Serializable;


public class OrderCptModifierLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer id;
	private Integer orderCptCodeId;
	private String action;
	private String field;
	private String preValue;
	private String postValue;
	private String description;
	private Integer performedByUserId;
	private Date date;
	private boolean isUserVisible;



	public Integer getId()
	{
		return id;
	}


	public void setId(Integer id)
	{
		this.id = id;
	}


	public Integer getOrderCptCodeId()
	{
		return orderCptCodeId;
	}


	public void setOrderCptCodeId(Integer orderCptCodeId)
	{
		this.orderCptCodeId = orderCptCodeId;
	}


	public String getAction()
	{
		return action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public String getField()
	{
		return field;
	}


	public void setField(String field)
	{
		this.field = field;
	}


	public String getPreValue()
	{
		return preValue;
	}


	public void setPreValue(String preValue)
	{
		this.preValue = preValue;
	}


	public String getPostValue()
	{
		return postValue;
	}


	public void setPostValue(String postValue)
	{
		this.postValue = postValue;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public Integer getPerformedByUserId()
	{
		return performedByUserId;
	}


	public void setPerformedByUserId(Integer performedByUserId)
	{
		this.performedByUserId = performedByUserId;
	}


	public Date getDate()
	{
		return date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}


	public boolean getIsUserVisible()
	{
		return isUserVisible;
	}


	public void setIsUserVisible(boolean isUserVisible)
	{
		this.isUserVisible = isUserVisible;
	}
}