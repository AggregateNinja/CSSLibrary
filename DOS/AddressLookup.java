package DOS;

import Utility.Diff;
import java.util.Date;

public class AddressLookup
{

    private static final long serialversionUID = 42L;

    // NOTE: if any fields are added, include them in the 'copy' method, below
    
    private Integer uniqueDatabaseId; // The unique id of the addressLookup table
    private Integer objectId;         // The unique id of the entity this address belongs to (e.g. for Locations it would be idlocations)
    private Integer addressId;
    private Integer addressTypeId;
    private Boolean assigned;
    private Boolean active;
    private Date createdDate;
    private Integer createdById;
    private Date modifiedDate;
    private Integer modifiedById;

    @Diff(fieldName="lookupId", isUniqueId=true)
    public Integer getUniqueDatabaseId()
    {
        return uniqueDatabaseId;
    }

    public void setUniqueDatabaseId(Integer uniqueDatabaseId)
    {
        this.uniqueDatabaseId = uniqueDatabaseId;
    }

    @Diff(fieldName="objectId")
    public Integer getObjectId()
    {
        return objectId;
    }

    public void setObjectId(Integer objectId)
    {
        this.objectId = objectId;
    }

    @Diff(fieldName="addressId")
    public Integer getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Integer addressId)
    {
        this.addressId = addressId;
    }

    @Diff(fieldName="addressTypeId")
    public Integer getAddressTypeId()
    {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId)
    {
        this.addressTypeId = addressTypeId;
    }

    @Diff(fieldName="assigned")
    public Boolean isAssigned()
    {
        return assigned;
    }

    public void setAssigned(Boolean assigned)
    {
        this.assigned = assigned;
    }

    @Diff(fieldName="active")
    public Boolean isActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public Integer getCreatedById()
    {
        return createdById;
    }

    public void setCreatedById(Integer createdById)
    {
        this.createdById = createdById;
    }

    public Date getModifiedDate()
    {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedById()
    {
        return modifiedById;
    }

    public void setModifiedById(Integer modifiedById)
    {
        this.modifiedById = modifiedById;
    }
    
    /**
     * Creates a verbatim copy of the object
     *  (including unique Id, created/modified dates, etc.)
     * @return 
     */
    public AddressLookup copy()
    {
        AddressLookup copyObj = new AddressLookup();
        
        copyObj.setUniqueDatabaseId(uniqueDatabaseId);
        copyObj.setObjectId(objectId);
        copyObj.setAddressId(addressId);
        copyObj.setAddressTypeId(addressTypeId);
        copyObj.setAssigned(assigned);
        copyObj.setActive(active);
        copyObj.setCreatedDate(createdDate);
        copyObj.setCreatedById(createdById);
        copyObj.setModifiedDate(modifiedDate);
        copyObj.setModifiedById(modifiedById);
        
        return copyObj;
    }
}
