package DOS;

public class ZipCode
{

    private static final long serialversionUID = 42L;

    private Integer idZipCodes;
    private String code;
    private String extension;
    private Integer active;

    public Integer getIdZipCodes()
    {
        return idZipCodes;
    }

    public void setIdZipCodes(Integer idZipCodes)
    {
        this.idZipCodes = idZipCodes;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
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
