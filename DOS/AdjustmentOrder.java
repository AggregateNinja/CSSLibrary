package DOS;

import java.io.Serializable;

public class AdjustmentOrder implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idAdjustmentOrders;
	private Integer adjustmentId;
	private Integer detailOrderId;
	private java.math.BigDecimal amount;

	public Integer getIdAdjustmentOrders()
	{
		return idAdjustmentOrders;
	}

	public void setIdAdjustmentOrders(Integer idAdjustmentOrders)
	{
		this.idAdjustmentOrders = idAdjustmentOrders;
	}


	public Integer getAdjustmentId()
	{
		return adjustmentId;
	}


	public void setAdjustmentId(Integer adjustmentId)
	{
		this.adjustmentId = adjustmentId;
	}


	public Integer getDetailOrderId()
	{
		return detailOrderId;
	}


	public void setDetailOrderId(Integer detailOrderId)
	{
		this.detailOrderId = detailOrderId;
	}


	public java.math.BigDecimal getAmount()
	{
		return amount;
	}


	public void setAmount(java.math.BigDecimal amount)
	{
		this.amount = amount;
	}
}