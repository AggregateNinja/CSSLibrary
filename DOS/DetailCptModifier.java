package DOS;

import DOS.IDOS.IBillingDO;
import Utility.Diff;
import java.io.Serializable;

public class DetailCptModifier implements Serializable, IBillingDO
{
	private static final long serialversionUID = 42L;

	private Integer detailCptCodeId;
	private Integer cptModifierId;


        @Diff(fieldName = "detailCptCodeId")
	public Integer getDetailCptCodeId()
	{
		return detailCptCodeId;
	}


	public void setDetailCptCodeId(Integer detailCptCodeId)
	{
		this.detailCptCodeId = detailCptCodeId;
	}


        @Diff(fieldName = "cptModifierId")
	public Integer getCptModifierId()
	{
		return cptModifierId;
	}


	public void setCptModifierId(Integer cptModifierId)
	{
		this.cptModifierId = cptModifierId;
	}

    @Override
    public String getTableName()
    {
        return "detailCptModifiers";
    }

        
}