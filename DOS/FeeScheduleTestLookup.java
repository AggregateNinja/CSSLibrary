package DOS;

import Utility.Diff;

public class FeeScheduleTestLookup
{

    private static final long serialversionUID = 42L;

    private Integer idFeeScheduleTestLookup;
    private Integer feeScheduleId;
    private Integer batteryNumber;
    private Integer panelNumber;
    private Integer testNumber;
    private String testAlias;
    private Integer feeScheduleActionId;

    @Diff(isUniqueId = true, fieldName = "idFeeScheduleTestLookup")
    public Integer getIdFeeScheduleTestLookup()
    {
        return idFeeScheduleTestLookup;
    }

    public void setIdFeeScheduleTestLookup(Integer idFeeScheduleTestLookup)
    {
        this.idFeeScheduleTestLookup = idFeeScheduleTestLookup;
    }

    @Diff(fieldName = "feeScheduleId")
    public Integer getFeeScheduleId()
    {
        return feeScheduleId;
    }

    public void setFeeScheduleId(Integer feeScheduleAssignmentId)
    {
        this.feeScheduleId = feeScheduleAssignmentId;
    }

    public Integer getBatteryNumber()
    {
        return batteryNumber;
    }

    public void setBatteryNumber(Integer batteryNumber)
    {
        this.batteryNumber = batteryNumber;
    }

    public Integer getPanelNumber()
    {
        return panelNumber;
    }

    public void setPanelNumber(Integer panelNumber)
    {
        this.panelNumber = panelNumber;
    }

    public Integer getTestNumber()
    {
        return testNumber;
    }

    public void setTestNumber(Integer testNumber)
    {
        this.testNumber = testNumber;
    }

    @Diff(fieldName = "testAlias")
    public String getTestAlias()
    {
        return testAlias;
    }

    public void setTestAlias(String testAlias)
    {
        this.testAlias = testAlias;
    }

    @Diff(fieldName = "feeScheduleActionId")
    public Integer getFeeScheduleActionId()
    {
        return feeScheduleActionId;
    }

    public void setFeeScheduleActionId(Integer feeScheduleActionId)
    {
        this.feeScheduleActionId = feeScheduleActionId;
    }

    /**
     * Returns a new instance of the object, matching the data
     *
     * @return
     */
    public FeeScheduleTestLookup copy()
    {
        FeeScheduleTestLookup newLookup = new FeeScheduleTestLookup();
        newLookup.setIdFeeScheduleTestLookup(getIdFeeScheduleTestLookup());
        newLookup.setFeeScheduleId(getFeeScheduleId());
        newLookup.setBatteryNumber(getBatteryNumber());
        newLookup.setPanelNumber(getPanelNumber());
        newLookup.setTestNumber(getTestNumber());
        newLookup.setTestAlias(getTestAlias());
        newLookup.setFeeScheduleActionId(getFeeScheduleActionId());
        return newLookup;
    }

    @Override
    public String toString()
    {
        String output = "";
        output += "\nidFeeScheduleTestLookup=" + (idFeeScheduleTestLookup == null ? "[NULL]" : idFeeScheduleTestLookup.toString());
        output += "\nfeeScheduleId=" + (feeScheduleId == null ? "[NULL]" : feeScheduleId.toString());
        output += "\nbatteryNumber=" + (batteryNumber == null ? "[NULL]" : batteryNumber.toString());
        output += "\npanelNumber=" + (panelNumber == null ? "[NULL]" : panelNumber.toString());
        output += "\ntestNumber=" + (testNumber == null ? "[NULL]" : testNumber.toString());
        output += "\ntestAlias=" + (testAlias == null ? "[NULL]" : testAlias.toString());
        output += "\nfeeScheduleActionId=" + (feeScheduleActionId == null ? "[NULL]" : feeScheduleActionId.toString());

        return output;
    }
}
