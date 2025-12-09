package DOS;

import java.io.Serializable;

public class PaymentMethod implements Serializable
{
    private static final long serialVersionUID = 42L;

    private Integer idPaymentMethods;
    private String name;
    private String systemName;
    private Integer active;

    public Integer getIdPaymentMethods()
    {
        return idPaymentMethods;
    }

    public void setIdPaymentMethods(Integer idPaymentMethods)
    {
        this.idPaymentMethods = idPaymentMethods;
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
