package DOS;

import java.util.Date;
import java.io.Serializable;


public class PreauthResponseCommentLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpreauthResponseCommentLookup;
	private Integer preauthResponseId;
	private String comments;
	private String id;
	private String createdOn;



	public Integer getIdpreauthResponseCommentLookup()
	{
		return idpreauthResponseCommentLookup;
	}


	public void setIdpreauthResponseCommentLookup(Integer idpreauthResponseCommentLookup)
	{
		this.idpreauthResponseCommentLookup = idpreauthResponseCommentLookup;
	}


	public Integer getPreauthResponseId()
	{
		return preauthResponseId;
	}


	public void setPreauthResponseId(Integer preauthResponseId)
	{
		this.preauthResponseId = preauthResponseId;
	}


	public String getComments()
	{
		return comments;
	}


	public void setComments(String comments)
	{
		this.comments = comments;
	}


	public String getId()
	{
		return id;
	}


	public void setId(String id)
	{
		this.id = id;
	}


	public String getCreatedOn()
	{
		return createdOn;
	}


	public void setCreatedOn(String createdOn)
	{
		this.createdOn = createdOn;
	}
}