package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthRequestDiagnosisLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthRequestDiagnosisLookup;
	private Integer preauthRequestId;
	private String diagnosisCode;



	public Integer getIdpreauthRequestDiagnosisLookup()
	{
		return idpreauthRequestDiagnosisLookup;
	}


	public void setIdpreauthRequestDiagnosisLookup(Integer idpreauthRequestDiagnosisLookup)
	{
		this.idpreauthRequestDiagnosisLookup = idpreauthRequestDiagnosisLookup;
	}


	public Integer getPreauthRequestId()
	{
		return preauthRequestId;
	}


	public void setPreauthRequestId(Integer preauthRequestId)
	{
		this.preauthRequestId = preauthRequestId;
	}


	public String getDiagnosisCode()
	{
		return diagnosisCode;
	}


	public void setDiagnosisCode(String diagnosisCode)
	{
		this.diagnosisCode = diagnosisCode;
	}
}