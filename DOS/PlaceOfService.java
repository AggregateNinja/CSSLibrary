package DOS;

import java.io.Serializable;

public class PlaceOfService implements Serializable
{

    private static final long serialversionUID = 42L;

    private Integer idPlaceOfService;
    private String code;
    private String name;
    private Integer isUserVisible;

    public Integer getIdPlaceOfService()
    {
        return idPlaceOfService;
    }

    public void setIdPlaceOfService(Integer idPlaceOfService)
    {
        this.idPlaceOfService = idPlaceOfService;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getIsUserVisible()
    {
        return isUserVisible;
    }

    public void setIsUserVisible(Integer isUserVisible)
    {
        this.isUserVisible = isUserVisible;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
