package DOS;

import Utility.Diff;
import java.io.Serializable;

public class SubmissionStatus implements Serializable
{
    private static final long serialversionUID = 42L;

    private Integer idSubmissionStatus;
    private String abbr;
    private String name;
    private String systemName;
    private Integer canSend;
    private Integer active;

    @Diff(fieldName="idSubmissionStatus", isUniqueId=true)
    public Integer getIdSubmissionStatus()
    {
        return idSubmissionStatus;
    }

    public void setIdSubmissionStatus(Integer idSubmissionStatus)
    {
        this.idSubmissionStatus = idSubmissionStatus;
    }

    @Diff(fieldName="abbr")
    public String getAbbr()
    {
        return abbr;
    }

    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }

    @Diff(fieldName="name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Diff(fieldName="systemName")
    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    @Diff(fieldName="canSend")
    public Integer getCanSend()
    {
        return canSend;
    }

    public void setCanSend(Integer canSend)
    {
        this.canSend = canSend;
    }

    @Diff(fieldName="active")
    public Integer getActive()
    {
        return active;
    }

    public void setActive(Integer active)
    {
        this.active = active;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
