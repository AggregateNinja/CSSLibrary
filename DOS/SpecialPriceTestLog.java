package DOS;

import java.util.Date;
import java.io.Serializable;


public class SpecialPriceTestLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idspecialPriceTestLog;
	private Integer specialPriceTestId;
	private String action;
	private Integer clientId;
	private Integer testNumber;
	private java.math.BigDecimal cost;
	private Integer userId;
	private Date date;



	public Integer getIdspecialPriceTestLog()
	{
		return idspecialPriceTestLog;
	}


	public void setIdspecialPriceTestLog(Integer idspecialPriceTestLog)
	{
		this.idspecialPriceTestLog = idspecialPriceTestLog;
	}


	public Integer getSpecialPriceTestId()
	{
		return specialPriceTestId;
	}


	public void setSpecialPriceTestId(Integer specialPriceTestId)
	{
		this.specialPriceTestId = specialPriceTestId;
	}


	public String getAction()
	{
		return action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public Integer getClientId()
	{
		return clientId;
	}


	public void setClientId(Integer clientId)
	{
		this.clientId = clientId;
	}


	public Integer getTestNumber()
	{
		return testNumber;
	}


	public void setTestNumber(Integer testNumber)
	{
		this.testNumber = testNumber;
	}


	public java.math.BigDecimal getCost()
	{
		return cost;
	}


	public void setCost(java.math.BigDecimal cost)
	{
		this.cost = cost;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public Date getDate()
	{
		return date;
	}


	public void setDate(Date date)
	{
		this.date = date;
	}
}