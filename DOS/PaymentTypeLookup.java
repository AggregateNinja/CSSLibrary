package DOS;

import java.util.Date;
import java.io.Serializable;


public class PaymentTypeLookup implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idpaymentTypeLookup;
	private String paymentType;



	public Integer getIdpaymentTypeLookup()
	{
		return idpaymentTypeLookup;
	}


	public void setIdpaymentTypeLookup(Integer idpaymentTypeLookup)
	{
		this.idpaymentTypeLookup = idpaymentTypeLookup;
	}


	public String getPaymentType()
	{
		return paymentType;
	}


	public void setPaymentType(String paymentType)
	{
		this.paymentType = paymentType;
	}
}