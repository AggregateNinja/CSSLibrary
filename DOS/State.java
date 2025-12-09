package DOS;

public class State
{

    private static final long serialversionUID = 42L;

    private Integer idStates;
    private String name;
    private String abbr;
    private Integer active;

    public Integer getIdStates()
    {
        return idStates;
    }

    public void setIdStates(Integer idStates)
    {
        this.idStates = idStates;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAbbr()
    {
        return abbr;
    }

    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }

    public Integer getActive()
    {
        return active;
    }

    public void setActive(Integer active)
    {
        this.active = active;
    }
}
