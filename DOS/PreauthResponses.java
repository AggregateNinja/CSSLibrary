package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthResponses implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthResponses;
	private String patientId;
	private String caseStatus;
	private String sequenceDateStr;
	private String physicianNpi;
	private String sequenceId;
	private String sequenceDate;
	private String caseNumber;
	private String appointmentId;
	private String caseId;
	private String patientDateOfBirth;
	private Integer userId;
        private Date createdDate;
        private boolean active;


	public Integer getIdpreauthResponses()
	{
		return idpreauthResponses;
	}


	public void setIdpreauthResponses(Integer idpreauthResponses)
	{
		this.idpreauthResponses = idpreauthResponses;
	}


	public String getPatientId()
	{
		return patientId;
	}


	public void setPatientId(String patientId)
	{
		this.patientId = patientId;
	}


	public String getCaseStatus()
	{
		return caseStatus;
	}


	public void setCaseStatus(String caseStatus)
	{
		this.caseStatus = caseStatus;
	}


	public String getSequenceDateStr()
	{
		return sequenceDateStr;
	}


	public void setSequenceDateStr(String sequenceDateStr)
	{
		this.sequenceDateStr = sequenceDateStr;
	}


	public String getPhysicianNpi()
	{
		return physicianNpi;
	}


	public void setPhysicianNpi(String physicianNpi)
	{
		this.physicianNpi = physicianNpi;
	}


	public String getSequenceId()
	{
		return sequenceId;
	}


	public void setSequenceId(String sequenceId)
	{
		this.sequenceId = sequenceId;
	}


	public String getSequenceDate()
	{
		return sequenceDate;
	}


	public void setSequenceDate(String sequenceDate)
	{
		this.sequenceDate = sequenceDate;
	}


	public String getCaseNumber()
	{
		return caseNumber;
	}


	public void setCaseNumber(String caseNumber)
	{
		this.caseNumber = caseNumber;
	}


	public String getAppointmentId()
	{
		return appointmentId;
	}


	public void setAppointmentId(String appointmentId)
	{
		this.appointmentId = appointmentId;
	}


	public String getCaseId()
	{
		return caseId;
	}


	public void setCaseId(String caseId)
	{
		this.caseId = caseId;
	}


	public String getPatientDateOfBirth()
	{
		return patientDateOfBirth;
	}


	public void setPatientDateOfBirth(String patientDateOfBirth)
	{
		this.patientDateOfBirth = patientDateOfBirth;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}
        
        public Date getCreatedDate()
        {
            return createdDate;
        }
        
        public void setCreatedDate(Date createdDate)
        {
            this.createdDate = createdDate;
        }
        
        public boolean isActive()
        {
            return active;
        }
        
        public void setActive(boolean active)
        {
            this.active = active;
        }
}