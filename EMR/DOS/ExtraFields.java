package EMR.DOS;

import java.util.Date;
import java.io.Serializable;


public class ExtraFields implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idExtraFields;
	private Integer orderId;
	private String type;
	private String action;
	private String name;
	private String value;



	public Integer getIdExtraFields()
	{
		return idExtraFields;
	}


	public void setIdExtraFields(Integer idExtraFields)
	{
		this.idExtraFields = idExtraFields;
	}


	public Integer getOrderId()
	{
		return orderId;
	}


	public void setOrderId(Integer orderId)
	{
		this.orderId = orderId;
	}


	public String getType()
	{
		return type;
	}


	public void setType(String type)
	{
		this.type = type;
	}


	public String getAction()
	{
		return action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public String getValue()
	{
		return value;
	}


	public void setValue(String value)
	{
		this.value = value;
	}
}