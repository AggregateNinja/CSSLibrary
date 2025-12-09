package DOS;

import java.io.Serializable;

public class TestRemarkRange implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idRemarkRanges;
    private Integer testId;
    private Integer remarkId;
    private Integer rangeTypeId;
    private Integer rank;
    private Boolean isActive;

    public Integer getIdRemarkRanges()
    {
        return idRemarkRanges;
    }

    public void setIdRemarkRanges(Integer idRemarkRanges)
    {
        this.idRemarkRanges = idRemarkRanges;
    }

    public Integer getTestId()
    {
        return testId;
    }

    public void setTestId(Integer testId)
    {
        this.testId = testId;
    }

    public Integer getRemarkId()
    {
        return remarkId;
    }

    public void setRemarkId(Integer remarkId)
    {
        this.remarkId = remarkId;
    }

    public Integer getRangeTypeId()
    {
        return rangeTypeId;
    }

    public void setRangeTypeId(Integer rangeTypeId)
    {
        this.rangeTypeId = rangeTypeId;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public Boolean isActive()
    {
        return isActive;
    }

    public void setActive(Boolean isActive)
    {
        this.isActive = isActive;
    }
    
    public TestRemarkRange copy()
    {
        TestRemarkRange copy = new TestRemarkRange();
        copy.idRemarkRanges = this.idRemarkRanges;
        copy.isActive = this.isActive;
        copy.rangeTypeId = this.rangeTypeId;
        copy.rank = this.rank;
        copy.remarkId = this.remarkId;
        copy.testId = this.testId;
        return copy;
    }

}
