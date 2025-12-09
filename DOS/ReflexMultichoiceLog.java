package DOS;

import java.util.Date;
import java.io.Serializable;


public class ReflexMultichoiceLog implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idReflexMultichoiceLog;
	private Integer testId;
	private Integer reflexTestId;
	private Integer resultPostLogId;
	private String reason;
	private Integer userId;
	private Date reflexDate;



	public Integer getIdReflexMultichoiceLog()
	{
		return idReflexMultichoiceLog;
	}


	public void setIdReflexMultichoiceLog(Integer idReflexMultichoiceLog)
	{
		this.idReflexMultichoiceLog = idReflexMultichoiceLog;
	}


	public Integer getTestId()
	{
		return testId;
	}


	public void setTestId(Integer testId)
	{
		this.testId = testId;
	}


	public Integer getReflexTestId()
	{
		return reflexTestId;
	}


	public void setReflexTestId(Integer reflexTestId)
	{
		this.reflexTestId = reflexTestId;
	}


	public Integer getResultPostLogId()
	{
		return resultPostLogId;
	}


	public void setResultPostLogId(Integer resultPostLogId)
	{
		this.resultPostLogId = resultPostLogId;
	}


	public String getReason()
	{
		return reason;
	}


	public void setReason(String reason)
	{
		this.reason = reason;
	}


	public Integer getUserId()
	{
		return userId;
	}


	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}


	public Date getReflexDate()
	{
		return reflexDate;
	}


	public void setReflexDate(Date reflexDate)
	{
		this.reflexDate = reflexDate;
	}
}