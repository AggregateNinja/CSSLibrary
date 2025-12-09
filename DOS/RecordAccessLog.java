package DOS;

import java.util.Date;
import java.io.Serializable;


public class RecordAccessLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idrecordAccessLog;
	private Integer userId;
	private Date time;
	private String module;
	private String accession;
	private String patientArNo;
	private String patientFirstName;
	private String patientMiddleName;
	private String patientLastName;
	private String subscriberArNo;
	private String subscriberFirstName;
	private String subscriberMiddleName;
	private String subscriberLastName;



	public Integer getIdrecordAccessLog()
	{
		return idrecordAccessLog;
	}


	public void setIdrecordAccessLog(Integer idrecordAccessLog)
	{
		this.idrecordAccessLog = idrecordAccessLog;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public Date getTime()
	{
		return time;
	}


	public void setTime(Date time)
	{
		this.time = time;
	}


	public String getModule()
	{
		return module;
	}


	public void setModule(String module)
	{
		this.module = module;
	}


	public String getAccession()
	{
		return accession;
	}


	public void setAccession(String accession)
	{
		this.accession = accession;
	}


	public String getPatientArNo()
	{
		return patientArNo;
	}


	public void setPatientArNo(String patientArNo)
	{
		this.patientArNo = patientArNo;
	}


	public String getPatientFirstName()
	{
		return patientFirstName;
	}


	public void setPatientFirstName(String patientFirstName)
	{
		this.patientFirstName = patientFirstName;
	}


	public String getPatientMiddleName()
	{
		return patientMiddleName;
	}


	public void setPatientMiddleName(String patientMiddleName)
	{
		this.patientMiddleName = patientMiddleName;
	}


	public String getPatientLastName()
	{
		return patientLastName;
	}


	public void setPatientLastName(String patientLastName)
	{
		this.patientLastName = patientLastName;
	}


	public String getSubscriberArNo()
	{
		return subscriberArNo;
	}


	public void setSubscriberArNo(String subscriberArNo)
	{
		this.subscriberArNo = subscriberArNo;
	}


	public String getSubscriberFirstName()
	{
		return subscriberFirstName;
	}


	public void setSubscriberFirstName(String subscriberFirstName)
	{
		this.subscriberFirstName = subscriberFirstName;
	}


	public String getSubscriberMiddleName()
	{
		return subscriberMiddleName;
	}


	public void setSubscriberMiddleName(String subscriberMiddleName)
	{
		this.subscriberMiddleName = subscriberMiddleName;
	}


	public String getSubscriberLastName()
	{
		return subscriberLastName;
	}


	public void setSubscriberLastName(String subscriberLastName)
	{
		this.subscriberLastName = subscriberLastName;
	}
}