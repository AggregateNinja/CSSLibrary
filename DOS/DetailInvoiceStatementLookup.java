package DOS;

import java.util.Date;
import java.io.Serializable;


public class DetailInvoiceStatementLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer iddetailInvoiceStatementLookup;
	private Integer invoiceNum;
	private Date invoiceDate;
	private Integer detailOrderId;



	public Integer getIddetailInvoiceStatementLookup()
	{
		return iddetailInvoiceStatementLookup;
	}


	public void setIddetailInvoiceStatementLookup(Integer iddetailInvoiceStatementLookup)
	{
		this.iddetailInvoiceStatementLookup = iddetailInvoiceStatementLookup;
	}


	public Integer getInvoiceNum()
	{
		return invoiceNum;
	}


	public void setInvoiceNum(Integer invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}


	public Date getInvoiceDate()
	{
		return invoiceDate;
	}


	public void setInvoiceDate(Date invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}


	public Integer getDetailOrderId()
	{
		return detailOrderId;
	}


	public void setDetailOrderId(Integer detailOrderId)
	{
		this.detailOrderId = detailOrderId;
	}
}