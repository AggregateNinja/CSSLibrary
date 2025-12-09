package DOS;

import Utility.Diff;
import java.io.Serializable;

public class OrderCptCode implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idorderCptCodes;
    private Integer orderedTestId;
    private Integer cptCodeId;

    @Diff(fieldName = "idorderCptCodes", isUniqueId = true)
    public Integer getIdorderCptCodes()
    {
        return idorderCptCodes;
    }

    public void setIdorderCptCodes(Integer idorderCptCodes)
    {
        this.idorderCptCodes = idorderCptCodes;
    }

    @Diff(fieldName = "orderedTestId")
    public Integer getOrderedTestId()
    {
        return orderedTestId;
    }

    public void setOrderedTestId(Integer orderedTestId)
    {
        this.orderedTestId = orderedTestId;
    }

    @Diff(fieldName = "cptCodeId")
    public Integer getCptCodeId()
    {
        return cptCodeId;
    }

    public void setCptCodeId(Integer cptCodeId)
    {
        this.cptCodeId = cptCodeId;
    }
}
