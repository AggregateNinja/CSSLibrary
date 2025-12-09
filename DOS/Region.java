package DOS;

public class Region
{

    private static final long serialversionUID = 42L;

    private Integer idregions;
    private String name;
    private Integer active;

    public Integer getIdregions()
    {
        return idregions;
    }

    public void setIdregions(Integer idregions)
    {
        this.idregions = idregions;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
