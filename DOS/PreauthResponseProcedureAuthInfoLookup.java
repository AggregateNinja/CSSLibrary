package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthResponseProcedureAuthInfoLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthResponseProcedureAuthInfoLookup;
	private Integer preauthResponseId;
	private String expiryDate;
	private String unitsAuthorized;
	private String denialReason;
	private String authNumber;
	private String insuranceId;
	private String id;
	private String trackingNumber;
	private String cptCode;
	private String effectiveDate;
	private String status;



	public Integer getIdpreauthResponseProcedureAuthInfoLookup()
	{
		return idpreauthResponseProcedureAuthInfoLookup;
	}


	public void setIdpreauthResponseProcedureAuthInfoLookup(Integer idpreauthResponseProcedureAuthInfoLookup)
	{
		this.idpreauthResponseProcedureAuthInfoLookup = idpreauthResponseProcedureAuthInfoLookup;
	}


	public Integer getPreauthResponseId()
	{
		return preauthResponseId;
	}


	public void setPreauthResponseId(Integer preauthResponseId)
	{
		this.preauthResponseId = preauthResponseId;
	}


	public String getExpiryDate()
	{
		return expiryDate;
	}


	public void setExpiryDate(String expiryDate)
	{
		this.expiryDate = expiryDate;
	}


	public String getUnitsAuthorized()
	{
		return unitsAuthorized;
	}


	public void setUnitsAuthorized(String unitsAuthorized)
	{
		this.unitsAuthorized = unitsAuthorized;
	}


	public String getDenialReason()
	{
		return denialReason;
	}


	public void setDenialReason(String denialReason)
	{
		this.denialReason = denialReason;
	}


	public String getAuthNumber()
	{
		return authNumber;
	}


	public void setAuthNumber(String authNumber)
	{
		this.authNumber = authNumber;
	}


	public String getInsuranceId()
	{
		return insuranceId;
	}


	public void setInsuranceId(String insuranceId)
	{
		this.insuranceId = insuranceId;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getTrackingNumber()
	{
		return trackingNumber;
	}


	public void setTrackingNumber(String trackingNumber)
	{
		this.trackingNumber = trackingNumber;
	}


	public String getCptCode()
	{
		return cptCode;
	}


	public void setCptCode(String cptCode)
	{
		this.cptCode = cptCode;
	}


	public String getEffectiveDate()
	{
		return effectiveDate;
	}


	public void setEffectiveDate(String effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}


	public String getStatus()
	{
		return status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}
}