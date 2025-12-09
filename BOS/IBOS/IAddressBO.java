
package BOS.IBOS;

import DOS.Address;
import DOS.City;
import DOS.Country;
import DOS.Region;
import DOS.State;
import DOS.ZipCode;


public interface IAddressBO
{
    public Address getAddress();
    public City getCity();
    public State getState();
    public Region getRegion();
    public ZipCode getZipCode();
    public Country getCountry();
    
    public void setAddress(Address address);
    public void setCity(City city);
    public void setState(State state);
    public void setRegion(Region region);
    public void setZipCode(ZipCode zipCode);
    public void setCountry(Country country);
}
