package DOS;

import java.util.Date;
import java.io.Serializable;


public class DetailCptCodeComments implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idDetailCptCodeComments;
	private Integer detailCptCodeId;
	private Integer commentId;



	public Integer getIdDetailCptCodeComments()
	{
		return idDetailCptCodeComments;
	}


	public void setIdDetailCptCodeComments(Integer idDetailCptCodeComments)
	{
		this.idDetailCptCodeComments = idDetailCptCodeComments;
	}


	public Integer getDetailCptCodeId()
	{
		return detailCptCodeId;
	}


	public void setDetailCptCodeId(Integer detailCptCodeId)
	{
		this.detailCptCodeId = detailCptCodeId;
	}


	public Integer getCommentId()
	{
		return commentId;
	}


	public void setCommentId(Integer commentId)
	{
		this.commentId = commentId;
	}
}