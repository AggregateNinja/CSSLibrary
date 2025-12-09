package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthRequestCptLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthRequestCptLookup;
	private Integer preauthRequestId;
	private String cptCode;
	private String modifier;



	public Integer getIdpreauthRequestCptLookup()
	{
		return idpreauthRequestCptLookup;
	}


	public void setIdpreauthRequestCptLookup(Integer idpreauthRequestCptLookup)
	{
		this.idpreauthRequestCptLookup = idpreauthRequestCptLookup;
	}


	public Integer getPreauthRequestId()
	{
		return preauthRequestId;
	}


	public void setPreauthRequestId(Integer preauthRequestId)
	{
		this.preauthRequestId = preauthRequestId;
	}


	public String getCptCode()
	{
		return cptCode;
	}


	public void setCptCode(String cptCode)
	{
		this.cptCode = cptCode;
	}


	public String getModifier()
	{
		return modifier;
	}


	public void setModifier(String modifier)
	{
		this.modifier = modifier;
	}
}