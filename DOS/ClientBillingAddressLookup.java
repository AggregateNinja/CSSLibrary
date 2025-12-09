/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DOS;

import java.util.Date;

public class ClientBillingAddressLookup {
    
    private Integer idclientBillingAddressLookup;
    private Integer clientId;
    private String address;
    private String poBoxSuite;
    private String city;
    private String state;
    private String zip;
    private Date addedDate;
    private Integer addedBy;
    private Date deactivatedDate;
    private Integer deactivatedBy;
    private Boolean active;
    
    //<editor-fold defaultstate="collapsed" desc="Class getters/setters">
    public Integer getIdclientBillingAddressLookup()
    {
        return idclientBillingAddressLookup;
    }
    
    public void setIdclientBillingAddressLookup(Integer idclientBillingAddressLookup)
    {
        this.idclientBillingAddressLookup = idclientBillingAddressLookup;
    }
    
    public Integer getClientId()
    {
        return clientId;
    }
    
    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getPOBoxSuite()
    {
        return poBoxSuite;
    }
    
    public void setPOBoxSuite(String poBoxSuite)
    {
        this.poBoxSuite = poBoxSuite;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getState()
    {
        return state;
    }
    
    public void setState(String state)
    {
        this.state = state;
    }
    
    public String getZip()
    {
        return zip;
    }
    
    public void setZip(String zip)
    {
        this.zip = zip;
    }
    
    public Date getAddedDate()
    {
        return addedDate;
    }
    
    public void setAddedDate(Date addedDate)
    {
        this.addedDate = addedDate;
    }
    
    public Integer getAddedBy()
    {
        return addedBy;
    }
    
    public void setAddedBy(Integer addedBy)
    {
        this.addedBy = addedBy;
    }
    
    public Date getDeactivatedDate()
    {
        return deactivatedDate;
    }
    
    public void setDeactivatedDate(Date deactivatedDate)
    {
        this.deactivatedDate = deactivatedDate;
    }
    
    public Integer getDeactivatedBy()
    {
        return deactivatedBy;
    }
    
    public void setDeactivatedBy(Integer deactivatedBy)
    {
        this.deactivatedBy = deactivatedBy;
    }
    
    public Boolean isActive()
    {
        return active;
    }
    
    public void setActive(Boolean active)
    {
        this.active = active;
    }
    //</editor-fold>
}
