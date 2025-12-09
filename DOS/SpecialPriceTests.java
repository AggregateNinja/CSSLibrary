package DOS;

import Utility.Diff;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;


public class SpecialPriceTests implements Serializable
{
	private static final long serialversionUID = 42L;

	private Integer idspecialPriceTests;
	private Integer clientId;
	private Integer testNumber;
	private BigDecimal cost;


        // <editor-fold defaultstate="collapsed" desc="Class Properties">
	public Integer getIdspecialPriceTests()
	{
		return idspecialPriceTests;
	}

	public void setIdspecialPriceTests(Integer idspecialPriceTests)
	{
		this.idspecialPriceTests = idspecialPriceTests;
	}

        @Diff(fieldName = "clientId")
	public Integer getClientId()
	{
		return clientId;
	}

	public void setClientId(Integer clientId)
	{
		this.clientId = clientId;
	}

        @Diff(fieldName = "testNumber")
	public Integer getTestNumber()
	{
		return testNumber;
	}

	public void setTestNumber(Integer testNumber)
	{
		this.testNumber = testNumber;
	}

        @Diff(fieldName = "cost")
	public BigDecimal getCost()
	{
		return cost;
	}

	public void setCost(BigDecimal cost)
	{
		this.cost = cost;
	}
        //</editor-fold>
}