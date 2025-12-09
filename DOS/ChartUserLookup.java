package DOS;

import java.util.Date;
import java.io.Serializable;


public class ChartUserLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idUserCharts;
	private Integer userId;
	private Integer chartId;
	private Boolean active;
	private Date dateCreated;



	public Integer getIdUserCharts()
	{
		return idUserCharts;
	}


	public void setIdUserCharts(Integer idUserCharts)
	{
		this.idUserCharts = idUserCharts;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public Integer getChartId()
	{
		return chartId;
	}


	public void setChartId(Integer chartId)
	{
		this.chartId = chartId;
	}


	public Boolean isActive()
	{
		return active;
	}


	public void setActive(Boolean active)
	{
		this.active = active;
	}


	public Date getDateCreated()
	{
		return dateCreated;
	}


	public void setDateCreated(Date dateCreated)
	{
		this.dateCreated = dateCreated;
	}
}