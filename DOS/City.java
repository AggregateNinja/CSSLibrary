package DOS;

public class City
{

    private static final long serialversionUID = 42L;

    private Integer idCities;
    private String name;
    private String abbr;
    private Integer active;

    public Integer getIdCities()
    {
        return idCities;
    }

    public void setIdCities(Integer idCities)
    {
        this.idCities = idCities;
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
