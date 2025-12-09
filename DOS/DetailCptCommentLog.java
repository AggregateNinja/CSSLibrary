package DOS;

import java.util.Date;
import java.io.Serializable;


public class DetailCptCommentLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer iddetailCptCommentLog;
	private Integer detailCptCodeId;
	private Integer detailCptCodeCommentId;
	private Integer commentId;
	private String action;
	private String comment;
	private Integer performedBy;
	private Date datePerformed;



	public Integer getIddetailCptCommentLog()
	{
		return iddetailCptCommentLog;
	}


	public void setIddetailCptCommentLog(Integer iddetailCptCommentLog)
	{
		this.iddetailCptCommentLog = iddetailCptCommentLog;
	}


	public Integer getDetailCptCodeId()
	{
		return detailCptCodeId;
	}


	public void setDetailCptCodeId(Integer detailCptCodeId)
	{
		this.detailCptCodeId = detailCptCodeId;
	}


	public Integer getDetailCptCodeCommentId()
	{
		return detailCptCodeCommentId;
	}


	public void setDetailCptCodeCommentId(Integer detailCptCodeCommentId)
	{
		this.detailCptCodeCommentId = detailCptCodeCommentId;
	}


	public Integer getCommentId()
	{
		return commentId;
	}


	public void setCommentId(Integer commentId)
	{
		this.commentId = commentId;
	}


	public String getAction()
	{
		return action;
	}


	public void setAction(String action)
	{
		this.action = action;
	}


	public String getComment()
	{
		return comment;
	}


	public void setComment(String comment)
	{
		this.comment = comment;
	}


	public Integer getPerformedBy()
	{
		return performedBy;
	}


	public void setPerformedBy(Integer performedBy)
	{
		this.performedBy = performedBy;
	}


	public Date getDatePerformed()
	{
		return datePerformed;
	}


	public void setDatePerformed(Date datePerformed)
	{
		this.datePerformed = datePerformed;
	}
}