package DOS;

import java.util.Date;
import java.io.Serializable;


public class ChartTypes implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idChartTypes;
	private String chartTypeName;
	private String chartTypeDescription;
	private Boolean active;



	public Integer getIdChartTypes()
	{
		return idChartTypes;
	}


	public void setIdChartTypes(Integer idChartTypes)
	{
		this.idChartTypes = idChartTypes;
	}


	public String getChartTypeName()
	{
		return chartTypeName;
	}


	public void setChartTypeName(String chartTypeName)
	{
		this.chartTypeName = chartTypeName;
	}


	public String getChartTypeDescription()
	{
		return chartTypeDescription;
	}


	public void setChartTypeDescription(String chartTypeDescription)
	{
		this.chartTypeDescription = chartTypeDescription;
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