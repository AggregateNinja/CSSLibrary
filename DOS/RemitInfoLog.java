package DOS;

import java.util.Date;
import java.io.Serializable;


public class RemitInfoLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idremitInfoLog;
	private Integer remitInfoId;
	private String action;
	private Date actionTime;
	private Integer user;



	public Integer getIdremitInfoLog()
	{
		return idremitInfoLog;
	}


	public void setIdremitInfoLog(Integer idremitInfoLog)
	{
		this.idremitInfoLog = idremitInfoLog;
	}


	public Integer getRemitInfoId()
	{
		return remitInfoId;
	}


	public void setRemitInfoId(Integer remitInfoId)
	{
		this.remitInfoId = remitInfoId;
	}


	public String getAction()
	{
		return action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public Date getActionTime()
	{
		return actionTime;
	}


	public void setActionTime(Date actionTime)
	{
		this.actionTime = actionTime;
	}


	public Integer getUser()
	{
		return user;
	}


	public void setUser(Integer user)
	{
		this.user = user;
	}
}