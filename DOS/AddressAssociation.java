package DOS;

import java.util.Date;


/**
 * Address associations provide search suggestions for addresses, tying together
 *  city, state, zip, region, country information that has already been entered
 *  into the system.
 * 
 * @author Tom Rush <trush at csslis.com>
 */
public class AddressAssociation
{

    private static final long serialversionUID = 42L;

    private Integer idaddressAssociations;
    private Integer cityId;
    private Integer stateId;
    private Integer zipCodeId;
    private Integer regionId;
    private Integer countryId;
    private Boolean active;
    private Date created;
    private Integer createdById;
    private Date lastModified;
    private Integer lastModifiedById;

    public Integer getIdaddressAssociations()
    {
        return idaddressAssociations;
    }

    public void setIdaddressAssociations(Integer idaddressAssociations)
    {
        this.idaddressAssociations = idaddressAssociations;
    }

    public Integer getCityId()
    {
        return cityId;
    }

    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    public Integer getStateId()
    {
        return stateId;
    }

    public void setStateId(Integer stateId)
    {
        this.stateId = stateId;
    }

    public Integer getZipCodeId()
    {
        return zipCodeId;
    }

    public void setZipCodeId(Integer zipCodeId)
    {
        this.zipCodeId = zipCodeId;
    }

    public Integer getRegionId()
    {
        return regionId;
    }

    public void setRegionId(Integer regionId)
    {
        this.regionId = regionId;
    }

    public Integer getCountryId()
    {
        return countryId;
    }

    public void setCountryId(Integer countryId)
    {
        this.countryId = countryId;
    }

    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public Integer getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Integer createdById)
    {
        this.createdById = createdById;
    }

    public Date getLastModified()
    {
        return lastModified;
    }

    public void setLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
    }

    public Integer getLastModifiedById()
    {
        return lastModifiedById;
    }

    public void setLastModifiedById(Integer lastModifiedById)
    {
        this.lastModifiedById = lastModifiedById;
    }
}
