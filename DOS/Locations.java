
package DOS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * @date:   Mar 3, 2012
 * @author: Ryan
 * 
 * @project: CSSLibrary 
 * @package: DAOS
 * @file name: Locations.java
 *
 * @Description:
 *
 * Computer Service & Support, Inc.  All Rights Reserved
 */



@Entity
@Table(name = "locations", catalog = "css", schema = "")
@NamedQueries({
    @NamedQuery(name = "Locations.findAll", query = "SELECT l FROM Locations l")})
public class Locations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idLocation", nullable = false)
    private Integer idLocation;
    @Basic(optional = false)
    @Column(name = "locationNo", nullable = false)
    private int locationNo;
    @Basic(optional = false)
    @Column(name = "locationName", nullable = false, length = 45)
    private String locationName;
    @Basic(optional = false)
    @Column(name = "address1", nullable = false, length = 55)
    private String address1;
    @Basic(optional = false)
    @Column(name = "address2", nullable = false, length = 55)
    private String address2;
    @Basic(optional = false)
    @Column(name = "city", nullable = false, length = 55)
    private String city;
    @Basic(optional = false)
    @Column(name = "state", nullable = false, length = 2)
    private String state;
    @Basic(optional = false)
    @Column(name = "zip", nullable = false)
    private String zip;
    @Basic(optional = false)
    @Column(name = "phone", nullable = false)
    private String phone;
    @Basic(optional = false)
    @Column(name = "fax", nullable = false)
    private String fax;
    @Basic(optional = false)
    @Column(name = "director", nullable = false, length = 55)
    private String director;
    @Basic(optional = false)
    @Column(name = "npi", nullable = false, length = 15)
    private String npi;
    @Basic(optional = false)
    @Column(name = "taxId", nullable = false, length = 15)
    private String taxId;
    @Basic(optional = false)
    @Column(name = "medicareNo", nullable = false, length = 15)
    private String medicareNo;
    @Basic(optional = false)
    @Column(name = "active")
    private Boolean active;
    @Basic(optional = false)
    @Column(name = "deactivatedBy")
    private Integer deactivatedBy;
    @Basic(optional = false)
    @Column(name = "deactivatedDate")
    @Temporal(TemporalType.DATE)
    private Date deactivatedDate;
    

    public Locations() {
    }

    public Locations(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public Locations(Integer idLocation, int locationNo, String locationName, String address1, String address2, String city, String state, String zip, String phone, String fax, String director, String npi, String taxId, String medicareNo, Boolean active, Integer deactivatedBy, Date deactivatedDate) {
        this.idLocation = idLocation;
        this.locationNo = locationNo;
        this.locationName = locationName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.fax = fax;
        this.director = director;
        this.npi = npi;
        this.taxId = taxId;
        this.medicareNo = medicareNo;
        this.active = active;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public int getLocationNo() {
        return locationNo;
    }

    public void setLocationNo(int locationNo) {
        this.locationNo = locationNo;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getNpi() {
        return npi;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getMedicareNo() {
        return medicareNo;
    }

    public void setMedicareNo(String medicareNo) {
        this.medicareNo = medicareNo;
    }
    
    public Boolean getActive(){
        return active;
    }
    
    public void setActive(Boolean active){
        this.active = active;
    }
    
    public Integer getDeactivatedBy(){
        return deactivatedBy;
    }
    
    public void setDeactivatedBy(Integer deactivatedBy){
        this.deactivatedBy = deactivatedBy;
    }
    
    public Date getDeactivatedDate(){
        return deactivatedDate;
    }
    
    public void setDeactivatedDate(Date deactivatedDate){
        this.deactivatedDate = deactivatedDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLocation != null ? idLocation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locations)) {
            return false;
        }
        Locations other = (Locations) object;
        if ((this.idLocation == null && other.idLocation != null) || (this.idLocation != null && !this.idLocation.equals(other.idLocation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        
        return "(" + locationNo + ") " + locationName;
        //return "DAOS.Locations[ idLocation=" + idLocation + " ]";
    }

}
