package DOS;

import java.util.Date;
import java.io.Serializable;


public class Charts implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idCharts;
	private Integer chartTypeId;
	private String chartName;
	private String chartDescription;
	private Boolean active;
	private Date dateCreated;



	public Integer getIdCharts()
	{
		return idCharts;
	}


	public void setIdCharts(Integer idCharts)
	{
		this.idCharts = idCharts;
	}


	public Integer getChartTypeId()
	{
		return chartTypeId;
	}


	public void setChartTypeId(Integer chartTypeId)
	{
		this.chartTypeId = chartTypeId;
	}


	public String getChartName()
	{
		return chartName;
	}


	public void setChartName(String chartName)
	{
		this.chartName = chartName;
	}


	public String getChartDescription()
	{
		return chartDescription;
	}


	public void setChartDescription(String chartDescription)
	{
		this.chartDescription = chartDescription;
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