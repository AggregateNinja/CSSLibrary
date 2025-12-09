package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthResponseProcedureInformationLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthResponseProcedureInformationLookup;
	private Integer preauthResponseId;
	private String quantityType;
	private String modifier;
	private String unitsUsed;
	private String description;
	private String requestedQuantity;
	private String id;
	private String cptCode;
	private String bodyPart;



	public Integer getIdpreauthResponseProcedureInformationLookup()
	{
		return idpreauthResponseProcedureInformationLookup;
	}


	public void setIdpreauthResponseProcedureInformationLookup(Integer idpreauthResponseProcedureInformationLookup)
	{
		this.idpreauthResponseProcedureInformationLookup = idpreauthResponseProcedureInformationLookup;
	}


	public Integer getPreauthResponseId()
	{
		return preauthResponseId;
	}


	public void setPreauthResponseId(Integer preauthResponseId)
	{
		this.preauthResponseId = preauthResponseId;
	}


	public String getQuantityType()
	{
		return quantityType;
	}


	public void setQuantityType(String quantityType)
	{
		this.quantityType = quantityType;
	}


	public String getModifier()
	{
		return modifier;
	}


	public void setModifier(String modifier)
	{
		this.modifier = modifier;
	}


	public String getUnitsUsed()
	{
		return unitsUsed;
	}


	public void setUnitsUsed(String unitsUsed)
	{
		this.unitsUsed = unitsUsed;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public String getRequestedQuantity()
	{
		return requestedQuantity;
	}


	public void setRequestedQuantity(String requestedQuantity)
	{
		this.requestedQuantity = requestedQuantity;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getCptCode()
	{
		return cptCode;
	}


	public void setCptCode(String cptCode)
	{
		this.cptCode = cptCode;
	}


	public String getBodyPart()
	{
		return bodyPart;
	}


	public void setBodyPart(String bodyPart)
	{
		this.bodyPart = bodyPart;
	}
}