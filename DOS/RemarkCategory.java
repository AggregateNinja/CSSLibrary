package DOS;

import java.io.Serializable;

public class RemarkCategory implements Serializable
{
    private static final long serialversionUID = 42L;

    private Integer idremarkCategories;
    private String name;
    private String systemName;

    public Integer getIdremarkCategories()
    {
        return idremarkCategories;
    }

    public void setIdremarkCategories(Integer idremarkCategories)
    {
        this.idremarkCategories = idremarkCategories;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    @Override
    public String toString()
    {
        if (this.name == null) return "[No category name found]";
        return this.name;
    }
}
