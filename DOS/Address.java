package DOS;

import Utility.Diff;
import java.util.Objects;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Address
{
    private static final long serialversionUID = 42L;

    private Integer idaddresses;
    private String address1;
    private String address2;
    private String address3;
    private Integer cityId;
    private Integer stateId;
    private Integer regionId;
    private Integer zipCodeId;
    private Integer countryId;
    private String clia;
    private String cola;
    private String npi;
    private String taxId;
    private String director;

    @Diff(fieldName="idaddresses", isUniqueId=true)
    public Integer getIdaddresses()
    {
        return idaddresses;
    }

    public void setIdaddresses(Integer idaddresses)
    {
        this.idaddresses = idaddresses;
    }

    @Diff(fieldName="address1")
    public String getAddress1()
    {
        return address1;
    }

    public void setAddress1(String address1)
    {
        this.address1 = address1;
    }

    @Diff(fieldName="address2")
    public String getAddress2()
    {
        return address2;
    }

    public void setAddress2(String address2)
    {
        this.address2 = address2;
    }

    @Diff(fieldName="address3")
    public String getAddress3()
    {
        return address3;
    }

    public void setAddress3(String address3)
    {
        this.address3 = address3;
    }

    @Diff(fieldName="cityId")
    public Integer getCityId()
    {
        return cityId;
    }

    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    @Diff(fieldName="stateId")
    public Integer getStateId()
    {
        return stateId;
    }

    public void setStateId(Integer stateId)
    {
        this.stateId = stateId;
    }

    @Diff(fieldName="regionId")
    public Integer getRegionId()
    {
        return regionId;
    }

    public void setRegionId(Integer regionId)
    {
        this.regionId = regionId;
    }
    
    @Diff(fieldName="zipCodeId")
    public Integer getZipCodeId()
    {
        return zipCodeId;
    }

    public void setZipCodeId(Integer zipCodeId)
    {
        this.zipCodeId = zipCodeId;
    }

    @Diff(fieldName="countryId")
    public Integer getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Integer countryId)
    {
        this.countryId = countryId;
    }
    
    @Diff(fieldName="clia")
    public String getClia()
    {
        return clia;
    }

    public void setClia(String clia)
    {
        this.clia = clia;
    }
    
    @Diff(fieldName="cola")
    public String getCola()
    {
        return cola;
    }

    public void setCola(String cola)
    {
        this.cola = cola;
    }  
    
    @Diff(fieldName="npi")
    public String getNpi()
    {
        return npi;
    }

    public void setNpi(String npi)
    {
        this.npi = npi;
    }
    
    @Diff(fieldName="taxId")
    public String getTaxId()
    {
        return taxId;
    }

    public void setTaxId(String taxId)
    {
        this.taxId = taxId;
    }
    
    @Diff(fieldName="director")
    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }
    
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.address1);
        hash = 79 * hash + Objects.hashCode(this.address2);
        hash = 79 * hash + Objects.hashCode(this.address3);
        hash = 79 * hash + Objects.hashCode(this.cityId);
        hash = 79 * hash + Objects.hashCode(this.stateId);
        hash = 79 * hash + Objects.hashCode(this.regionId);
        hash = 79 * hash + Objects.hashCode(this.zipCodeId);
        hash = 79 * hash + Objects.hashCode(this.countryId);
        hash = 79 * hash + Objects.hashCode(this.clia);
        hash = 79 * hash + Objects.hashCode(this.cola);
        hash = 79 * hash + Objects.hashCode(this.npi);
        hash = 79 * hash + Objects.hashCode(this.taxId);
        hash = 79 * hash + Objects.hashCode(this.director);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Address other = (Address) obj;
        
        return (this.hashCode() == other.hashCode());
    }
}
