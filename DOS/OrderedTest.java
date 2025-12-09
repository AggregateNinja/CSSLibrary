package DOS;

import Utility.Diff;
import java.io.Serializable;

public class OrderedTest implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idorderedTests;
    private Integer orderId;
    private Integer resultId;
    private Integer batteryNumber;
    private Integer panelNumber;
    private Integer testNumber;
    private Integer directlyOrdered;

    @Diff(fieldName = "idorderedTests", isUniqueId = true)
    public Integer getIdorderedTests()
    {
        return idorderedTests;
    }

    public void setIdorderedTests(Integer idorderedTests)
    {
        this.idorderedTests = idorderedTests;
    }

    @Diff(fieldName = "orderId")
    public Integer getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Integer orderId)
    {
        this.orderId = orderId;
    }

    @Diff(fieldName = "resultId")
    public Integer getResultId()
    {
        return resultId;
    }

    public void setResultId(Integer resultId)
    {
        this.resultId = resultId;
    }

    @Diff(fieldName = "batteryNumber")
    public Integer getBatteryNumber()
    {
        return batteryNumber;
    }

    public void setBatteryNumber(Integer batteryNumber)
    {
        this.batteryNumber = batteryNumber;
    }

    @Diff(fieldName = "panelNumber")
    public Integer getPanelNumber()
    {
        return panelNumber;
    }

    public void setPanelNumber(Integer panelNumber)
    {
        this.panelNumber = panelNumber;
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

    @Diff(fieldName = "directlyOrdered")
    public Integer getDirectlyOrdered()
    {
        return directlyOrdered;
    }

    public void setDirectlyOrdered(Integer directlyOrdered)
    {
        this.directlyOrdered = directlyOrdered;
    }
    
}
