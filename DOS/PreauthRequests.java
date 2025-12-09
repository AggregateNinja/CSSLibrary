package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthRequests implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthRequests;
	private String apiProvider;
        private Integer orderId;
	private String appointmentId;
        private String caseNumber;
	private Date dateOfService;
	private String priority;
	private String physicianFirstName;
	private String physicianLastName;
	private String physicianNpi;
	private String patientFirstName;
	private String patientLastName;
	private String patientArNo;
	private Date patientDob;
	private String patientGender;
	private String patientNote;
	private String insuranceName;
	private String insuranceType;
	private String memberId;
	private String facilityCode;
	private String facilityName;
	private String facilityNpi;
	private Integer userId;
        private Date createdDate;
        private boolean active;


	public Integer getIdpreauthRequests()
	{
		return idpreauthRequests;
	}


	public void setIdpreauthRequests(Integer idpreauthRequests)
	{
		this.idpreauthRequests = idpreauthRequests;
	}


	public String getApiProvider()
	{
		return apiProvider;
	}


	public void setApiProvider(String apiProvider)
	{
		this.apiProvider = apiProvider;
	}
        
        public Integer getOrderId()
        {
            return orderId;
        }
        
        public void setOrderId(Integer orderId)
        {
            this.orderId = orderId;
        }

	public String getAppointmentId()
	{
		return appointmentId;
	}


	public void setAppointmentId(String appointmentId)
	{
		this.appointmentId = appointmentId;
	}
        
        public String getCaseNumber()
        {
            return caseNumber;
        }
        
        public void setCaseNumber(String caseNumber)
        {
            this.caseNumber = caseNumber;
        }

	public Date getDateOfService()
	{
		return dateOfService;
	}


	public void setDateOfService(Date dateOfService)
	{
		this.dateOfService = dateOfService;
	}


	public String getPriority()
	{
		return priority;
	}


	public void setPriority(String priority)
	{
		this.priority = priority;
	}


	public String getPhysicianFirstName()
	{
		return physicianFirstName;
	}


	public void setPhysicianFirstName(String physicianFirstName)
	{
		this.physicianFirstName = physicianFirstName;
	}


	public String getPhysicianLastName()
	{
		return physicianLastName;
	}


	public void setPhysicianLastName(String physicianLastName)
	{
		this.physicianLastName = physicianLastName;
	}


	public String getPhysicianNpi()
	{
		return physicianNpi;
	}


	public void setPhysicianNpi(String physicianNpi)
	{
		this.physicianNpi = physicianNpi;
	}


	public String getPatientFirstName()
	{
		return patientFirstName;
	}


	public void setPatientFirstName(String patientFirstName)
	{
		this.patientFirstName = patientFirstName;
	}


	public String getPatientLastName()
	{
		return patientLastName;
	}


	public void setPatientLastName(String patientLastName)
	{
		this.patientLastName = patientLastName;
	}


	public String getPatientArNo()
	{
		return patientArNo;
	}


	public void setPatientArNo(String patientArNo)
	{
		this.patientArNo = patientArNo;
	}


	public Date getPatientDob()
	{
		return patientDob;
	}


	public void setPatientDob(Date patientDob)
	{
		this.patientDob = patientDob;
	}


	public String getPatientGender()
	{
		return patientGender;
	}


	public void setPatientGender(String patientGender)
	{
		this.patientGender = patientGender;
	}


	public String getPatientNote()
	{
		return patientNote;
	}


	public void setPatientNote(String patientNote)
	{
		this.patientNote = patientNote;
	}


	public String getInsuranceName()
	{
		return insuranceName;
	}


	public void setInsuranceName(String insuranceName)
	{
		this.insuranceName = insuranceName;
	}


	public String getInsuranceType()
	{
		return insuranceType;
	}


	public void setInsuranceType(String insuranceType)
	{
		this.insuranceType = insuranceType;
	}


	public String getMemberId()
	{
		return memberId;
	}


	public void setMemberId(String memberId)
	{
		this.memberId = memberId;
	}


	public String getFacilityCode()
	{
		return facilityCode;
	}


	public void setFacilityCode(String facilityCode)
	{
		this.facilityCode = facilityCode;
	}


	public String getFacilityName()
	{
		return facilityName;
	}


	public void setFacilityName(String facilityName)
	{
		this.facilityName = facilityName;
	}


	public String getFacilityNpi()
	{
		return facilityNpi;
	}


	public void setFacilityNpi(String facilityNpi)
	{
		this.facilityNpi = facilityNpi;
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