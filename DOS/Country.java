package DOS;

import java.util.Objects;

public class Country
{

    private static final long serialversionUID = 42L;

    private Integer idCountries;
    private String name;
    private String abbr;
    private Integer active;

    public Integer getIdCountries()
    {
        return idCountries;
    }

    public void setIdCountries(Integer idCountries)
    {
        this.idCountries = idCountries;
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

    @Override
    public String toString()
    {
        return abbr;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.abbr);
        return hash;
    }

    /**
     * Country comparison on abbreviation, which should be unique
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || obj instanceof Country == false) return false;
        Country other = (Country)obj;
        if (this.abbr == null || other.abbr == null) return false;
        
        return (this.abbr.equals(other.abbr));
    }
}
