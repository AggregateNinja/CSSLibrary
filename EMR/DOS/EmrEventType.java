
package EMR.DOS;

public class EmrEventType
{
    private static final long serialVersionUID = 1L;
    private Integer idEmrEventTypes;
    private String name;
    private boolean isError;
    private boolean isUserVisible;

    public Integer getIdEmrEventTypes()
    {
        return idEmrEventTypes;
    }

    public void setIdEmrEventTypes(Integer idEmrEventTypes)
    {
        this.idEmrEventTypes = idEmrEventTypes;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isIsError()
    {
        return isError;
    }

    public void setIsError(boolean isError)
    {
        this.isError = isError;
    }

    public boolean isUserVisible()
    {
        return isUserVisible;
    }

    public void setIsUserVisible(boolean isUserVisible)
    {
        this.isUserVisible = isUserVisible;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
