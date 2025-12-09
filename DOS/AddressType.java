package DOS;

import java.util.Objects;

public class AddressType
{

    private static final long serialversionUID = 42L;
    
    private Integer idaddressTypes;
    private String name;
    private Boolean isMailing;
    private Boolean isPhysical;
    private String description;
    private Integer active;

    public Integer getIdaddressTypes()
    {
        return idaddressTypes;
    }

    public void setIdaddressTypes(Integer idaddressTypes)
    {
        this.idaddressTypes = idaddressTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean isMailing()
    {
        return isMailing;
    }

    public void setIsMailing(Boolean isMailing)
    {
        this.isMailing = isMailing;
    }

    public Boolean isPhysical()
    {
        return isPhysical;
    }

    public void setIsPhysical(Boolean isPhysical)
    {
        this.isPhysical = isPhysical;
    }
    
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        if (this.name == null) return "[NULL]";
        if (this.name.length() == 0) return "[Empty]";
        return this.name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj instanceof AddressType == false) return false;
        AddressType other = (AddressType)obj;
        if (this.idaddressTypes == null || other.idaddressTypes == null) return false;
        return (this.idaddressTypes.equals(other.idaddressTypes));
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.idaddressTypes);
        return hash;
    }
}
