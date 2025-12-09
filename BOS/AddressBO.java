package BOS;

import BOS.IBOS.IAddressBO;
import DOS.Address;
import DOS.City;
import DOS.Country;
import DOS.Region;
import DOS.State;
import DOS.ZipCode;

/**
 * Wrapper class for DOs relevant to an Address record
 * @author Tom Rush <trush at csslis.com>
 */
public class AddressBO implements IAddressBO
{
    private static final long serialversionUID = 42L;
    
    Address address;
    City city;
    State state;
    Region region;
    ZipCode zipCode;
    Country country;

    @Override
    public Address getAddress()
    {
        return address;
    }

    @Override
    public void setAddress(Address address)
    {
        this.address = address;
    }

    @Override
    public City getCity()
    {
        return city;
    }

    @Override
    public void setCity(City city)
    {
        this.city = city;
    }

    @Override
    public State getState()
    {
        return state;
    }

    @Override
    public void setState(State state)
    {
        this.state = state;
    }

    @Override
    public Region getRegion()
    {
        return region;
    }

    @Override
    public void setRegion(Region region)
    {
        this.region = region;
    }

    @Override
    public ZipCode getZipCode()
    {
        return zipCode;
    }

    @Override
    public void setZipCode(ZipCode zipCode)
    {
        this.zipCode = zipCode;
    }

    @Override
    public Country getCountry()
    {
        return country;
    }

    @Override
    public void setCountry(Country country)
    {
        this.country = country;
    }
}
